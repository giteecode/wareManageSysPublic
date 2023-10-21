package com.education.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * 图片生成器工具类
 * @author zjt
 * @create_at 2022年3月3日 0003 10:20
 * @since 1.0.5
 */
public class ImageGenerate {

    private static final Logger logger = LoggerFactory.getLogger(ImageGenerate.class);

    /**
     * 字节数组生成图片
     * @param data 字节数组
     * @param imagePath 文件存储路径 例如：D:\\test
     * @param imageType 图片类型 例如.png、 .jpg
     */
    public static void byteToImage(byte[] data, String imagePath, String imageType) {
        String imageId = ObjectUtils.generateUuId();
        FileImageOutputStream imageOutput = null;
        try {
            imageOutput = new FileImageOutputStream(new File(imagePath + File.separator + imageId + imageType));
            imageOutput.write(data, 0, data.length);//将byte写入硬盘
        } catch (IOException e) {
            logger.error("byte to image error", e);
        } finally {
            if (imageOutput != null) {
                try {
                    imageOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
