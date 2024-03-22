package com.ruoyi.common.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.ruoyi.common.config.FileDirConfig;
import com.ruoyi.common.utils.uuid.IdUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class QRUtils {

    public static String qrout(String content) throws Exception{
        File file = new File(FileDirConfig.FILE_TEMP);
        if (!file.exists()){
            file.mkdirs();
        }
        //生成二维码
        int width = 200;
        int height = 200;
        Map<EncodeHintType, Object> config = new HashMap<>();
        config.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        config.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        config.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, config);

        String guid = IdUtils.fastUUID();
        String path = FileDirConfig.FILE_TEMP+ guid;
        OutputStream out = new FileOutputStream(new File(path));
        MatrixToImageWriter.writeToStream(bitMatrix,"png",out);
        out.close();
        return path ;
    }

    public static String qrLogoOut(String content, String logoUrl) throws Exception{
        //生成二维码
        int width = 200;
        int height = 200;
        Map<EncodeHintType, Object> config = new HashMap<>();
        config.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        config.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        config.put(EncodeHintType.MARGIN, 0);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, config);
        BufferedImage matrixImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // 读取二维码图片，并构建绘图对象
        Graphics2D g2 = matrixImage.createGraphics();
        // 消除锯齿。不设置的话，圆弧效果会出现锯齿
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int matrixWidth = matrixImage.getWidth();
        int matrixHeight = matrixImage.getHeight();

        // 读取logo图片
        BufferedImage logo = ImageIO.read(new URL(logoUrl));

        // 在底图（源二维码）上绘制logo。logo大小为地图的2/5
//        g2.drawImage(logo, matrixWidth / 5 * 2, matrixHeight / 5 * 2,
//                matrixWidth / 5, matrixHeight / 5, null);//绘制
        // 采用平滑缩放的方式，来解决logo质量变差的问题
        g2.drawImage(logo.getScaledInstance(matrixWidth / 5,matrixWidth / 5, Image.SCALE_SMOOTH),
                matrixWidth / 5 * 2, matrixHeight / 5 * 2, null);

        // 圆滑的粗线条
        // 参见：https://blog.csdn.net/li_tengfei/article/details/6098093
        BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        // 设置画笔的画出的线条
        g2.setStroke(stroke);

        // 指定弧度的圆角矩形（空心的，白色边的）
        RoundRectangle2D.Double round = new RoundRectangle2D.Double(matrixWidth / 5 * 2, matrixHeight / 5 * 2,
                matrixWidth / 5, matrixHeight / 5, 5, 5);
        g2.setColor(Color.white);
        // 用于绘制logo外层的白边
        g2.draw(round);

        // 圆滑的细线条
        BasicStroke stroke2 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke2);
        // 设置圆角矩形
        RoundRectangle2D.Double round2 = new RoundRectangle2D.Double(matrixWidth / 5 * 2 + 2, matrixHeight / 5 * 2 + 2,
                matrixWidth / 5 - 4, matrixHeight / 5 - 4, 5, 5);
        g2.setColor(new Color(211, 211, 211));
        // 绘制logo内部的灰色边框
        g2.draw(round2);

        g2.dispose();
        matrixImage.flush();

        // 输出测试
        File file = new File(FileDirConfig.FILE_TEMP);
        if (!file.exists()){
            file.mkdirs();
        }
        String guid = IdUtils.fastUUID();
        String path = FileDirConfig.FILE_TEMP+ guid;
        OutputStream out = new FileOutputStream(new File(path));
        ImageIO.write(matrixImage, "png", out);
        out.close();
        return path ;
    }

}
