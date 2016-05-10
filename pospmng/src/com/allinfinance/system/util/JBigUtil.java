package com.allinfinance.system.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.sun.jna.Platform;

public class JBigUtil {

    private static Logger log = Logger.getLogger(JBigUtil.class);
    public static final int BYTE_OF_BIT_COUNT = 8;
    private static byte[] arr = new byte[]{(byte) 0x80, 0x40, 0x20, 0x10, 0x8, 0x4, 0x2, 0x1};

    /**
     * 16进制的字符串表示转成字节数组
     *
     * @param hexString 16进制格式的字符串
     * @return 转换后的字节数组
     **/
    public static byte[] hexStr2ByteArray(String hexString) {
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {
            //因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            //将hex 转换成byte   "&" 操作为了防止负数的自动扩展
            // hex转换成byte 其实只占用了4位，然后把高位进行右移四位
            // 然后“|”操作  低四位 就能得到 两个 16进制数转换成一个byte.
            //
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    public static byte[] jbig2Image(byte[] src, String outImageType) {
        BufferedImage image = jbig2Image(src);
        ByteArrayOutputStream output = new ByteArrayOutputStream(5000);
        try {
            ImageIO.write(image, outImageType, output);
        } catch (IOException e) {
            log.error("", e);
        }
        return output.toByteArray();
    }

    public static BufferedImage jbig2Image(byte[] srcs) {
        return drawImage(getWidth(srcs), getHeight(srcs), jbig2image(srcs));
    }

    private static int readColor(int i, int j, int width, byte[] srcs) {
        int index = (j * width) + i;
        int bitIndex = index % BYTE_OF_BIT_COUNT;
        int byteIndex = index / BYTE_OF_BIT_COUNT;
        int result = (srcs[byteIndex] & arr[bitIndex]) >> (7 - bitIndex);
        return result == 0 ? Color.WHITE.getRGB() : Color.BLACK.getRGB();
    }

    private static BufferedImage drawImage(int width, int height, byte[] srcs1) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                image.setRGB(i, j, readColor(i, j, width, srcs1));
            }
        }
        return image;
    }

    private static int getInt(byte[] b, int off) {
        return ((b[off + 3] & 0xFF) << 0) +
                ((b[off + 2] & 0xFF) << 8) +
                ((b[off + 1] & 0xFF) << 16) +
                ((b[off + 0]) << 24);
    }

    private static int getWidth(byte[] src) {
        return getInt(src, 4);
    }

    private static int getHeight(byte[] src) {
        return getInt(src, 8);
    }

    static {
        String name;
        if (Platform.isWindows()) {
            name = Platform.is64Bit() ? "libjbig64b.dll" : "libjbig32b.dll";
        } else if (Platform.isLinux()) {
            name = Platform.is64Bit() ? "libjbig64b.so" : "libjbig32b.so";
        } else {
            name = Platform.is64Bit() ? "libjbig64b.so" : "libjbig32b.so";
        }
        log.info("jbig载入的库=========>"+ name);
        System.load(JBigUtil.class.getClassLoader().getResource(name).getFile());
    }

    private static native byte[] jbig2image(byte[] src);
}
