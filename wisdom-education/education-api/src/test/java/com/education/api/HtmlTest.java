package com.education.api;

import com.education.common.utils.RequestUtils;
// import gui.ava.html.image.generator.HtmlImageGenerator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.*;
import java.util.HashMap;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/2/24 9:42
 */
public class HtmlTest {




        private HashMap map = new HashMap();
        public void testMap () {
            Thread t1 = new Thread() { public void run() { for (int i = 0; i < 50000; i++) { map.put(new Integer(i), i); }
            System.out.println("t1 over"); } };
            Thread t2 = new Thread() { public void run() { for (int i = 0; i < 50000; i++)
            { map.put(new Integer(i), i); } System.out.println("t2 over"); } };
            Thread t3 = new Thread() { public void run() { for (int i = 0; i < 50000; i++)
            { map.put(new Integer(i), i); } System.out.println("t3 over"); } };
            Thread t4 = new Thread() { public void run() { for (int i = 0; i < 50000; i++)
            { map.put(new Integer(i), i); } System.out.println("t4 over"); } };
            Thread t5 = new Thread() { public void run() { for (int i = 0; i < 50000; i++)
            { map.put(new Integer(i), i); } System.out.println("t5 over"); } };
            Thread t6 = new Thread() { public void run() { for (int i = 0; i < 50000; i++)
            { map.get(new Integer(i)); } System.out.println("t6 over"); } };
            Thread t7 = new Thread() { public void run() { for (int i = 0; i < 50000; i++)
            { map.get(new Integer(i)); } System.out.println("t7 over"); } };
            Thread t8 = new Thread() { public void run() { for (int i = 0; i < 50000; i++)
            { map.get(new Integer(i)); } System.out.println("t8 over"); } };
            Thread t9 = new Thread() { public void run() { for (int i = 0; i < 50000; i++)
            { map.get(new Integer(i)); } System.out.println("t9 over"); } };
            Thread t10 = new Thread() { public void run() { for (int i = 0; i < 50000; i++)
            { map.get(new Integer(i)); } System.out.println("t10 over"); } }; t1.start(); t2.start();
            t3.start(); t4.start(); t5.start(); t6.start(); t7.start(); t8.start(); t9.start();
            t10.start(); }






    public static void main(String[] args) {

            new HtmlTest().testMap();
       /* String charset = "UTF-8";
        String saveImageLocation = "E:\\save11.png";
       String htmlFilePath = "E:\\uploads\\html\\ctExamReport\\eeff9ebe00dc4fd1854e95f767993bf9\\hpk_print.html";

        String htmText = getHtmlContent(htmlFilePath, charset);
        html2Img(htmText, saveImageLocation);*/

      //  HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
      //  imageGenerator.loadUrl(fileUrl);
        //加载html模版
      //  imageGenerator.loadHtml(htmText);

        //把html写入到图片
       // imageGenerator.saveAsImage(saveImageLocation);

    }

    static final String fileUrl = "http://app.education.gxsrkj.com/#/";


    /**
     *
     * @Description 读取HTML文件，获取字符内容
     * @param filePath
     * @param charset
     * @return
     */
    public static String getHtmlContent(String filePath, String charset){
        String line = null;
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;

        try {
            InputStream inputStream = RequestUtils.getInputStreamFromUrl(fileUrl);
            // InputStream inputStream = RequestUtils.getInputStreamFromUrl(filePath);
          //  reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), charset));
            reader = new BufferedReader(new InputStreamReader(inputStream, charset));
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("读取HTML文件，获取字符内容异常");
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭流异常");
            }
        }
        return sb.toString();
    }

    /**
     *
     * @Description HTML转Image
     * @param htmText HTML文本字符串
     * @return 希望生成的Image Location
     */
    public static String html2Img(String htmText, String saveImageLocation){
       /* HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
        try {
          //  imageGenerator.loadHtml(htmText);
            imageGenerator.loadHtml(htmText);
           // Thread.sleep(100);
            imageGenerator.getBufferedImage();
          //  Thread.sleep(200);
            imageGenerator.saveAsImage(saveImageLocation);
            //imageGenerator.saveAsHtmlWithMap("hello-world.html", saveImageLocation);
            //不需要转换位图的，下面三行可以不要
           // BufferedImage sourceImg = ImageIO.read(new File(saveImageLocation));
          //  sourceImg = transform_Gray24BitMap(sourceImg);
           // ImageIO.write(sourceImg, "BMP", new File(saveImageLocation));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("将HTML文件转换成图片异常");
        }
        return saveImageLocation;*/
       return null;
    }



    /**
     *
     * @Description 转换成24位图的BMP
     * @param image
     * @return
     */
    public static BufferedImage transform_Gray24BitMap(BufferedImage image){
        int h = image.getHeight();
        int w = image.getWidth();
        int[] pixels = new int[w * h]; // 定义数组，用来存储图片的像素
        int gray;
        PixelGrabber pg = new PixelGrabber(image, 0, 0, w, h, pixels, 0, w);
        try {
            pg.grabPixels(); // 读取像素值
        } catch (InterruptedException e) {
            throw new RuntimeException("转换成24位图的BMP时，处理像素值异常");
        }

        for (int j = 0; j < h; j++){ // 扫描列
            for (int i = 0; i < w; i++) { // 扫描行
                // 由红，绿，蓝值得到灰度值
                gray = (int) (((pixels[w * j + i] >> 16) & 0xff) * 0.8);
                gray += (int) (((pixels[w * j + i] >> 8) & 0xff) * 0.1);
                gray += (int) (((pixels[w * j + i]) & 0xff) * 0.1);
                pixels[w * j + i] = (255 << 24) | (gray << 16) | (gray << 8) | gray;
            }
        }

        MemoryImageSource s= new MemoryImageSource(w,h,pixels,0,w);
        Image img = Toolkit.getDefaultToolkit().createImage(s);
        BufferedImage buf = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);//如果要转换成别的位图，改这个常量即可
        buf.createGraphics().drawImage(img, 0, 0, null);
        return buf;
    }

}
