package com.allinfinance.struts.risk.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.JBigUtil;
import com.opensymphony.xwork2.ActionSupport;

public class T402111Action extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private Logger log = LoggerFactory.getLogger(BaseController.class);
	private static Logger log = Logger.getLogger(T402111Action.class);

	private String method;
	private String instDate;
	private String sysSeqNum;
	public String CHECK_IMAGE = "";

	@Override
	public String execute() throws Exception {
		if ("posOrder".equals(method)) {
			try {
				String sql = "select x.card_accp_name, x.card_accp_id, x.card_accp_term_id, '01', x.pan, y.txn_name, substr(x.fld_reserved, 3, 6), x.term_ssn, x.authr_id_resp, x.inst_date, x.retrivl_ref ,to_number(trim(x.amt_trans))/100,z.f62 from tbl_n_txn x, tbl_txn_name y , tbl_qgd z where x.txn_num=y.txn_num and (x.key_revsal=z.key_orgtxn and x.sys_seq_num=z.o_sys_seq_num and substr(x.inst_date, 1, 8) = z.inst_date) ";
				String where = "and x.inst_date='" + instDate
						+ "' and x.SYS_SEQ_NUM='" + sysSeqNum + "'";
				@SuppressWarnings("unchecked")
				List<Object[]> list = CommonFunction.getCommQueryDAO()
						.findBySQLQuery(sql + where);

				log.info("pos签购单");
				HttpServletRequest request = ServletActionContext.getRequest();
				HttpServletResponse response = ServletActionContext
						.getResponse();
				drawImage(request, response, list);

			} catch (Exception e) {

				e.printStackTrace();
			}
			return null;
		} else if ("doladPos".equals(method)) {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			String sql = "select x.card_accp_name, x.card_accp_id, x.card_accp_term_id, '01', x.pan, y.txn_name, substr(x.fld_reserved, 3, 6), x.term_ssn, x.authr_id_resp, x.inst_date, x.retrivl_ref ,to_number(trim(x.amt_trans))/100,z.f62 from tbl_n_txn x, tbl_txn_name y , tbl_qgd z where x.txn_num=y.txn_num and (x.key_revsal=z.key_orgtxn and x.sys_seq_num=z.o_sys_seq_num and substr(x.inst_date, 1, 8) = z.inst_date) ";
			String where = "and x.inst_date='" + instDate
					+ "' and x.SYS_SEQ_NUM='" + sysSeqNum + "'";
			@SuppressWarnings("unchecked")
			List<Object[]> list = CommonFunction.getCommQueryDAO()
					.findBySQLQuery(sql + where);

			downloadImage(request, response, list);
		}

		return SUCCESS;

	}

	// show
	protected void drawImage(HttpServletRequest req, HttpServletResponse resp,
			List<Object[]> list) throws ServletException, IOException {
		HttpSession session = req.getSession();
		resp.reset();
		resp.setContentType("image/png");
		writeImageToStream(resp, session, list);
	}

	protected void writeImageToStream(HttpServletResponse resp,
			HttpSession session, List<Object[]> list) throws IOException {
		ServletOutputStream outputStream;
		BufferedImage image = drawImage(session, list);
		outputStream = resp.getOutputStream();
		try {

			ImageIO.write(image, "PNG", outputStream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			outputStream.close();
		}

	}

	private BufferedImage drawImage(HttpSession session, List<Object[]> list)
			throws IOException {
		BufferedImage image = ImageIO.read(session.getServletContext()
				.getResourceAsStream("img/bgImg.png"));
		Graphics2D graphics = image.createGraphics();
		graphics.setColor(Color.black);

		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, this.getClass()
					.getClassLoader().getResource("simsun.ttc").openStream());
			// Font font = new Font("宋体", Font.PLAIN, 15);
			font = font.deriveFont(Font.PLAIN, 15);
		} catch (Exception e) {
			log.error("", e);
			font = new Font("新宋体", Font.PLAIN, 15);
		}

		log.info("字体名称:"+ font.getFontName());

		graphics.setFont(font);

		// 商户名称
		graphics.drawString(list.get(0)[0].toString(), 90, 160);
		// 商户编号
		graphics.drawString(list.get(0)[1].toString(), 140, 180);
		// 终端号
		graphics.drawString(list.get(0)[2].toString(), 140, 220);
		// 操作员
		graphics.drawString(list.get(0)[3].toString(), 300, 220);
		// 卡号
		graphics.drawString(list.get(0)[4].toString(), 140, 320);
		// 交易类型
		graphics.drawString(list.get(0)[5].toString(), 140, 360);
		// 有效期
		/*
		 * graphics.drawString(session.getAttribute("EXP_DATE").toString(), 300,
		 * 360);
		 */
		// 批次号
		graphics.drawString(list.get(0)[6].toString(), 140, 410);
		// 凭证号
		graphics.drawString(list.get(0)[7].toString(), 300, 410);
		// 授权号
		graphics.drawString(list.get(0)[8].toString(), 140, 460);
		// 日期时间
		graphics.drawString(
				CommonFunction.dateFormat(list.get(0)[9].toString()), 300, 460);
		// 参考号
		graphics.drawString(list.get(0)[10].toString(), 140, 510);
		// 金额
		graphics.drawString(list.get(0)[11].toString(), 140, 560);
		// 备注
		// graphics.drawString(list.get(0)[12].toString(), 140, 610);
		// 编号
		// graphics.drawString(list.get(0)[13].toString(), 200, 770);
		CHECK_IMAGE = list.get(0)[12].toString();
		BufferedImage nameImg = drawNameImg(session);

		graphics.drawImage(nameImg, 100, 700, Color.white, null);
		graphics.dispose();

		return image;
	}

	private BufferedImage drawNameImg(HttpSession session) {
		return JBigUtil.jbig2Image(JBigUtil.hexStr2ByteArray(CHECK_IMAGE));
	}

	public static void main(String[] args) {

		System.out.println(CommonFunction.dateFormat("20150729170646"));

	}

	protected String downloadImage(HttpServletRequest req,
			HttpServletResponse resp, List<Object[]> list)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		resp.reset();
		String string = "application/octet-stream";
		resp.setContentType(string);
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader(
				"Content-Disposition",
				"attachment; filename=\""
						+ URLEncoder.encode(
								new String((list.get(0)[1]).toString()),
								"UTF-8") + ".jpg\"");
		writeImageToStream(resp, session, list);
		return null;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getInstDate() {
		return instDate;
	}

	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}

	public String getSysSeqNum() {
		return sysSeqNum;
	}

	public void setSysSeqNum(String sysSeqNum) {
		this.sysSeqNum = sysSeqNum;
	}

}
