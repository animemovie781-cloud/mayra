namespace AmeyaBridgeHelper.Services;

internal static class HealthService
{
    public static object Ping() => new
    {
        status = "ok",
        helper = "AmeyaBridgeHelper",
        platform = "windows",
        pid = Environment.ProcessId,
        version = "0.1.0"
    };
}
