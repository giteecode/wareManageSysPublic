package com.education.common.model;

import com.education.common.cache.CacheBean;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 图片验证码工具类
 * @author zengjintao
 * @version 1.0
 * @create_at 2017年4月22日 下午4:54:32
 */
public class Captcha {
	
    private static final int WIDTH = 114;
	private static final int HEIGHT = 35;
	protected static final Random random = new Random(System.nanoTime());
	// 验证码随机字符数组
	protected static final char[] charArray = "3456789ABCDEFGHJKMNPQRSTUVWXY".toCharArray();
	// 验证码字体
	protected static final Font[] RANDOM_FONT = new Font[] {
		new Font(Font.DIALOG, Font.BOLD, 33),
		new Font(Font.DIALOG_INPUT, Font.BOLD, 34),
		new Font(Font.SERIF, Font.BOLD, 33),
		new Font(Font.SANS_SERIF, Font.BOLD, 34),
		new Font(Font.MONOSPACED, Font.BOLD, 34)
	};


	private CacheBean cacheBean;
	private String key;

	public Captcha(CacheBean cacheBean, String key) {
		this.cacheBean = cacheBean;
		this.key = key;
	}
	
	public void render(HttpServletResponse response) {
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		setBackground(graphics);//设置背景
		setBorder(graphics);
		setRandomLine(graphics);
		String num = serRandomNum(graphics);
		cacheBean.put(key, num, 60);
		response.setHeader("Pragma","no-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		try {
			ImageIO.write(image, "jpg", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 生成随机数
	 * @param graphics
	 * @return
	 */
	private String serRandomNum(Graphics graphics) {
		graphics.setFont(RANDOM_FONT[new Random().nextInt(RANDOM_FONT.length)]);
		int x = 8;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			graphics.setColor(getRandColor(25, 130));
			String value = String.valueOf(charArray[random.nextInt(charArray.length)]);
			sb.append(value);
			graphics.drawString(value, x, 28);
			x += 28;
		}
		return sb.toString();
	}

	/**
	 * 设置线条
	 * @param graphics
	 */
	private void setRandomLine(Graphics graphics) {
		graphics.setColor(getRandColor(20, 250));
		for (int i = 0; i < 15; i++) {
			int x1 = new Random().nextInt(WIDTH);
			int x2 = new Random().nextInt(WIDTH);
			int y1 = new Random().nextInt(HEIGHT);
			int y2 = new Random().nextInt(HEIGHT);
			graphics.drawLine(x1, y1, x2, y2);
		}
	}

	/*
	 * 给定范围获得随机颜色
	 */
	protected Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * 设置边框
	 * @param graphics
	 */
	private void setBorder(Graphics graphics) {
		graphics.setColor(getRandColor(210, 250));
		graphics.drawRect(1, 1, WIDTH - 2, HEIGHT - 2);
	}

	/**
	 * 设置背景颜色
	 * @param graphics
	 */
	private void setBackground(Graphics graphics) {
		graphics.setColor(getRandColor(210, 250));
		graphics.fillRect(0,0, WIDTH, HEIGHT);
	}

}
