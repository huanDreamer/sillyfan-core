package top.sillyfan.common.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public final class ImageUtils {

    /**
     * 获取图片的后缀名
     *
     * @param filename
     * @return String
     */
    public static String suffix(String filename) {
        return filename.substring(filename.lastIndexOf('.'));
    }

    /**
     * 是否支持该图片的格式
     *
     * @param filename
     * @param types
     * @return boolean
     * @throws IOException
     */
    public static boolean isSupported(String filename, ImageType... types) {

        // 支持类型为空
        if (null == types || types.length == 0) {
            return false;
        }

        // 图片后缀(图片类型)
        String suffix = suffix(filename);

        // 循环支持的类型 找到知道满足其中一种类型
        for (ImageType type : types) {
            if (type.match(suffix)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取图片宽
     *
     * @param buffered
     * @return int
     * @throws IOException
     */
    public static int width(BufferedImage buffered) throws IOException {
        return buffered.getWidth();
    }

    /**
     * 获取图片高
     *
     * @param buffered
     * @return int
     * @throws IOException
     */
    public static int height(BufferedImage buffered) throws IOException {
        return buffered.getHeight();
    }

    /**
     * 判断图片尺寸（宽高）满足指定尺寸大小
     *
     * @param width
     * @param height
     * @param is
     * @return boolean
     * @throws IOException
     * @throws IOException
     */
    public static boolean isEqualSize(Integer width, Integer height,
                                      InputStream is) throws IOException {

        BufferedImage buffered = ImageIO.read(is);

        return width == width(buffered) && height == height(buffered);
    }

    public enum ImageType {
        JPG(".jpg"), JEPG(".jepg"), PNG(".png"), GIF(".gif"), BMP(".bmp");

        private String name;

        ImageType(String name) {
            this.name = name;
        }

        public boolean match(String type) {
            if (null == type) {
                return false;
            }

            return this.name.toLowerCase().equals(type.toLowerCase());
        }

    }
}
