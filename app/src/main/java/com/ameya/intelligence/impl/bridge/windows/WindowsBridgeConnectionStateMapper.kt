package com.ameya.intelligence.impl.bridge.windows

import com.ameya.intelligence.domain.models.ConnectionState

internal fun WindowsBridgeConnectionState.toChatConnectionState(): ConnectionState = when (this) {
    WindowsBridgeConnectionState.CONNECTED,
    WindowsBridgeConnectionState.PAUSED -> ConnectionState.CONNECTED
    WindowsBridgeConnectionState.CONNECTING,
    WindowsBridgeConnectionState.RECONNECTING,
    WindowsBridgeConnectionState.CLOSING -> ConnectionState.CONNECTING
    WindowsBridgeConnectionState.DISCONNECTED,
    WindowsBridgeConnectionState.ERROR -> ConnectionState.DISCONNECTED
}
