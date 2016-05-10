package com.allinfinance.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.directwebremoting.util.Logger;

/**
 * 产生验证码
 * 
 * @author kchen
 * 
 */
public class ValidateCode extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(ValidateCode.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 清空缓冲区
		response.reset();
		// 设置响应类型
		response.setContentType("image/png");
		// 创建图片缓冲区
		BufferedImage image = new BufferedImage(70, 20,
				BufferedImage.TYPE_INT_RGB);

		Graphics g = image.getGraphics();
		// 随机产生一个比较接近白色的底色
		g.setColor(this.getRandColor(230, 250));
		// 填充底色
		g.fillRect(0, 0, 70, 20);
		// 增加干扰线
		Random r = new Random();
		for (int i = 0; i < 133; i++) {
			g.setColor(this.getRandColor(150, 210));
			int x1 = r.nextInt(70);
			int y1 = r.nextInt(20);
			int x2 = r.nextInt(12);
			int y2 = r.nextInt(12);
			g.drawLine(x1, y1, x2 + x1, y2 + y1);
		}

		String vCode = "";
		g.setFont(new Font("Times New Roman", Font.BOLD, 19));
		// 产生5个验证字符
		for (int i = 0; i < 4; i++) {
			g.setColor(this.getRandColor(50, 150));
			int num = this.getCode();
			char temp;
			if (num > 10) { // 返回的是字符
				temp = (char) num;
				vCode += temp;
				g.drawString(String.valueOf(temp), 14 * i + 6, 16);
			} else {// 返回的是数字
				vCode += num;
				g.drawString(String.valueOf(num), 14 * i + 6, 16);
			}

		}
		logger.info(vCode);
		request.getSession().setAttribute("vC", vCode);

		ServletOutputStream sos = response.getOutputStream();
		ImageIO.write(image, "PNG", sos);
		sos.close();
	}

	private Color getRandColor(int low, int high) {
		Random ran = new Random();
		int r = low + ran.nextInt(high - low);
		int g = low + ran.nextInt(high - low);
		int b = low + ran.nextInt(high - low);

		return new Color(r, g, b);
	}

	private int getCode() {
		/*
		 * 1 : 0-9 2 : a-z 3 : A-Z
		 */
		Random r = new Random();
		int code = 0;

		int flag = r.nextInt(3);
		if (flag == 0) {
			code = r.nextInt(6)+3;
		} else if (flag == 1) {
			code = getCodeByScope('a', 'z');
		} else {
			code = getCodeByScope('A', 'Z');
		}
		return code;
	}

	/*private int getCodeByScope(int low, int high) {
		Random ran = new Random();
		return low + ran.nextInt(high - low);

	}*/
	
	private int getCodeByScope(int low, int high) {
		Random ran = new Random();
		int temp=low + ran.nextInt(high - low);
		if(temp==105||temp==108||temp==111||temp==73||temp==76||temp==79||temp==122||temp==90||
				temp==110||temp==109||temp==118||temp==119||temp==78||temp==77||temp==86||temp==87
				||temp==103||temp==113||temp==106){
			return getCodeByScope( low,  high);
		}else{
			return temp;
		}
	}

}
