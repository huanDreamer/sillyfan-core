package top.sillyfan.common.util;

/**
 * 计算文件大小
 */
public final class FileSizeUtil {

    static public final long KB = 1024;
    static public final long MB = 1024 * KB;
    static public final long GB = 1024 * MB;

    /**
     * 计算bytes大小
     *
     * @param bytes
     * @return long
     */
    public static long sizeB(long bytes) {
        return bytes;
    }

    /**
     * 计算kb大小
     *
     * @param bytes
     * @return long
     */
    public static final long sizeKB(long bytes) {
        return bytes / KB;
    }

    /**
     * 计算mb大小
     *
     * @param bytes
     * @return long
     */
    public static final long sizeMB(long bytes) {
        return bytes / MB;
    }

    /**
     * 计算gb大小
     *
     * @param bytes
     * @return long
     */
    public static final long sizeGB(long bytes) {
        return bytes / GB;
    }
}
