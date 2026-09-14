import { contextBridge, ipcRenderer } from 'electron';

type Snapshot = unknown;
type AuditEntry = unknown;

const api = {
  snapshot: (): Promise<Snapshot> => ipcRenderer.invoke('ameya-bridge/snapshot'),
  onSnapshot: (cb: (snap: Snapshot) => void): void => {
    ipcRenderer.on('ameya-bridge/snapshot', (_evt, snap) => cb(snap));
  },
  audit: (limit?: number): Promise<AuditEntry[]> =>
    ipcRenderer.invoke('ameya-bridge/audit', limit),
  clearAuditLog: (): Promise<{ ok: boolean; error?: string }> =>
    ipcRenderer.invoke('ameya-bridge/clear-audit-log'),
  rawAuditLog: (): Promise<string> =>
    ipcRenderer.invoke('ameya-bridge/raw-audit-log'),
  trustedDevices: (): Promise<unknown[]> =>
    ipcRenderer.invoke('ameya-bridge/trusted-devices'),
  generatePairingPayload: (): Promise<unknown> =>
    ipcRenderer.invoke('ameya-bridge/generate-pairing'),
  revokeDevice: (deviceId: string): void => {
    ipcRenderer.send('ameya-bridge/revoke-device', deviceId);
  },
  toggleAgent: (enabled: boolean): void => {
    ipcRenderer.send('ameya-bridge/agent-control', enabled);
  },
  emergencyStop: (): void => {
    ipcRenderer.send('ameya-bridge/emergency-stop');
  },
  resume: (): void => {
    ipcRenderer.send('ameya-bridge/resume');
  },
  restartHelper: (): void => {
    ipcRenderer.send('ameya-bridge/restart-helper');
  }
};

contextBridge.exposeInMainWorld('bridge', api);
