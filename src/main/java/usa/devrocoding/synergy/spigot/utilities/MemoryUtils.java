package usa.devrocoding.synergy.spigot.utilities;

public class MemoryUtils {

    public static long getFreeMemory() {
        return bytesToMegabytes(Runtime.getRuntime().freeMemory());
    }

    public static long getMemoryUsed() {
        return getTotalMemory() - getFreeMemory();
    }

    public static long getTotalMemoryAllocated() {
        return bytesToMegabytes(Runtime.getRuntime().maxMemory());
    }

    public static long getTotalMemory() {
        return bytesToMegabytes(Runtime.getRuntime().totalMemory());
    }

    private static long bytesToMegabytes(long bytes) {
        return bytes / 1048576L;
    }
}
