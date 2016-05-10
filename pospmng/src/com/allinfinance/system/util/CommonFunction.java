package com.allinfinance.system.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.allinfinance.common.StringUtil;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.frontend.hessian.server.IFrontEndHessianServer;

/**
 * Title:通用方法
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-3-9
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author
 * 
 * @version 1.0
 */
public class CommonFunction {

	private static SimpleDateFormat showDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private static SimpleDateFormat sysDateFormat = new SimpleDateFormat(
			"yyyyMMddHHmmss");

	private static SimpleDateFormat sysTimeFormat = new SimpleDateFormat(
			"HHmmss");

	private static SimpleDateFormat sysDateFormat8 = new SimpleDateFormat(
			"yyyyMMdd");

	private static SimpleDateFormat showDateFormatZHCN = new SimpleDateFormat(
			"yyyy年MM月dd日 HH时mm分ss秒");

	private static ICommQueryDAO commQueryDAO = null;// (ICommQueryDAO)
														// ContextUtil.getBean("CommQueryDAO");
	private static ICommQueryDAO commGWQueryDAO=null;
	private static ICommQueryDAO commDFQueryDAO = null;// (ICommQueryDAO)
														// ContextUtil.getBean("commDFQueryDAO");
	private static ICommQueryDAO commMisQueryDAO = null;
//	private static ICommQueryDAO commQuery_frontDAO = null;

	private static ICommQueryDAO commTMSQueryDAO = null;
	private static ICommQueryDAO commPOSPQueryDAO = null;
	
	private static IFrontEndHessianServer frontService = null;
	private static IFrontEndHessianServer batchService = null;

	/**
	 * @return the frontService
	 */
	public static IFrontEndHessianServer getFrontService() {
		if (frontService == null) {
			frontService = (IFrontEndHessianServer) ContextUtil
					.getBean("frontServiceClient");
		}
		return frontService;
	}
	
	/**
	 * @return the frontService
	 */
	public static IFrontEndHessianServer getBatchService() {
		if (batchService == null) {
			batchService = (IFrontEndHessianServer) ContextUtil
					.getBean("batchServiceClient");
		}
		return batchService;
	}
	

	/**
	 * @return the commQuery_frontDAO
	 */
//	public static ICommQueryDAO getCommQuery_frontDAO() {
//		if (commQuery_frontDAO == null) {
//			commQuery_frontDAO = (ICommQueryDAO) ContextUtil
//					.getBean("commQuery_frontDAO");
//		}
//		return commQuery_frontDAO;
//	}

	/**
	 * 获得系统当前日期时间
	 * 
	 * @return
	 */
	public static String getCurrentDateTime() {
		return sysDateFormat.format(new Date());
	}

	/**
	 * 获得系统当前时间
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		return sysTimeFormat.format(new Date());
	}

	/**
	 * 获得系统当前日期
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		return sysDateFormat8.format(new Date());
	}

	/**
	 * 获得向前系统时间用于显示
	 * 
	 * @return
	 */
	public static String getCurrentDateTimeForShow() {
		return showDateFormat.format(new Date());
	}

	/**
	 * 获得中文系统时间
	 * 
	 * @return
	 */
	public static String getCurrentDateTimeZHCN() {
		return showDateFormatZHCN.format(new Date());
	}

	/**
	 * 获得给定时间的前一天
	 * 
	 * @param date
	 *            格式为yyMMdd exp 20120301
	 * @return String yyMMdd exp 20120229
	 */
	public static String getDateYestoday(String date) {
		Calendar cal = Calendar.getInstance();
		date = date.trim();
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(4, 6));
		int day = Integer.parseInt(date.substring(6, date.length()));
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, day);
		// 日期的前一天
		cal.add(Calendar.DATE, -1);

		String mon = String.valueOf(cal.get(Calendar.MONTH) + 1);
		if (mon.length() == 1) {
			mon = "0" + mon;
		}
		String d = String.valueOf(cal.get(Calendar.DATE));
		if (d.length() == 1) {
			d = "0" + d;
		}
		String yestoday = String.valueOf(cal.get(Calendar.YEAR)) + mon + d;
		return yestoday.trim();
	}

	/**
	 * 获得给定时间的前十天
	 * 
	 * @param date
	 *            格式为yyMMdd exp 20120301
	 * @return String yyMMdd exp 20120229
	 */
	public static String getDateTen(String date) {
		Calendar cal = Calendar.getInstance();
		date = date.trim();
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(4, 6));
		int day = Integer.parseInt(date.substring(6, date.length()));
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DATE, day);
		// 日期的前十天
		cal.add(Calendar.DATE, -10);

		String mon = String.valueOf(cal.get(Calendar.MONTH) + 1);
		if (mon.length() == 1) {
			mon = "0" + mon;
		}
		String d = String.valueOf(cal.get(Calendar.DATE));
		if (d.length() == 1) {
			d = "0" + d;
		}
		String yestoday = String.valueOf(cal.get(Calendar.YEAR)) + mon + d;
		return yestoday.trim();
	}

	/**
	 * 填补字符串
	 * 
	 * @param str
	 * @param fill
	 * @param len
	 * @param isEnd
	 * @return
	 */
	public static String fillString(String str, char fill, int len,
			boolean isEnd) {
		// int fillLen = 0;
		// if (StringUtil.isNull(str)) {
		// fillLen = len;
		// str = "";
		// } else {
		// fillLen = len - str.getBytes().length;
		// }
		int fillLen = len - str.getBytes().length;
		if (len <= 0) {
			return str;
		}
		for (int i = 0; i < fillLen; i++) {
			if (isEnd) {
				str += fill;
			} else {
				str = fill + str;
			}
		}
		return str;
	}

	/**
	 * 填补字符串(中文字符扩充)
	 * 
	 * @param str
	 * @param fill
	 * @param len
	 * @param isEnd
	 * @return
	 */
	public static String fillStringForChinese(String str, char fill, int len,
			boolean isEnd) {
		int num = 0;
		Pattern p = Pattern.compile("^[\u4e00-\u9fa5]");
		for (int i = 0; i < str.length(); i++) {
			Matcher m = p.matcher(str.substring(i, i + 1));
			if (m.find()) {
				num++;
			}
		}
		int fillLen = len - (str.length() + num);
		if (len <= 0) {
			return str;
		}
		for (int i = 0; i < fillLen; i++) {
			if (isEnd) {
				str += fill;
			} else {
				str = fill + str;
			}
		}
		return str;
	}

	/**
	 * 根据数据库中定义的长度扩充
	 * 
	 * @param str
	 * @param fill
	 * @param len
	 * @param isEnd
	 * @return 2011-8-26下午12:45:37
	 */
	public static String fillStringByDB(String str, char fill, int len,
			boolean isEnd) {

		// 由于informix这里报错，所以先注释了
		// String sql = "select lengthb('" + str + "') from dual";
		//
		// List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		// int length = 0;
		// if (null != list && !list.isEmpty() &&
		// !StringUtil.isNull(list.get(0))) {
		// BigDecimal bg = (BigDecimal) list.get(0);
		// length = bg.intValue();
		// }
		int length = 0;
		for (int i = 0; i < len - length; i++) {
			if (isEnd) {
				str += fill;
			} else {
				str = fill + str;
			}
		}

		return str;
	}

	/**
	 * 获得本行及一下机构MAP
	 * 
	 * @param brhId
	 * @param brhMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getBelowBrhMap(String brhId,
			Map<String, String> brhMap) {
		String sql = "SELECT BRH_ID,BRH_NAME FROM TBL_BRH_INFO WHERE UP_BRH_ID = '"
				+ brhId + "' ";
		List<Object[]> dataList = getCommQueryDAO().findBySQLQuery(sql);
		for (Object[] brhInfo : dataList) {
			String tempBrhId = brhInfo[0].toString();
			String tempBrhName = brhInfo[1].toString();
			brhMap.put(tempBrhId, tempBrhName);
			brhMap = getBelowBrhMap(tempBrhId, brhMap);
		}
		return brhMap;
	}

	/**
	 * 获得本行及一下机构MAP
	 * 
	 * @param brhId
	 * @param brhMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getBelowBrhMap(Map<String, String> brhMap) {
		String sql = "SELECT BRH_ID,BRH_NAME,CUP_BRH_ID FROM TBL_BRH_INFO WHERE BRH_ID in "
				+ getBelowBrhInfo(brhMap)
				+ " or UP_BRH_ID in "
				+ getBelowBrhInfo(brhMap) + " ";
		List<Object[]> dataList = getCommQueryDAO().findBySQLQuery(sql);
		if (brhMap.size() == dataList.size()) {
			return brhMap;
		}
		for (Object[] brhInfo : dataList) {
			String tempBrhId = brhInfo[0].toString();
			String tempBrhName = brhInfo[1].toString();
			brhMap.put(tempBrhId, tempBrhName);
		}
		brhMap = getBelowBrhMap(brhMap);
		return brhMap;
	}

	/**
	 * 获得本行及一下机构MAP2
	 * 
	 * @param brhId
	 * @param brhMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getBelowBrhMap2(String brhId) {
		Map<String, String> brhMap = new HashMap<String, String>();
		String sql = "SELECT BRH_ID,BRH_NAME FROM TBL_BRH_INFO   start with BRH_ID = '"
				+ brhId + "' connect by prior  BRH_ID = UP_BRH_ID";
		// "UP_BRH_ID = '" + brhId + "' ";
		List<Object[]> dataList = getCommQueryDAO().findBySQLQuery(sql);
		for (Object[] brhInfo : dataList) {
			String tempBrhId = brhInfo[0].toString();
			String tempBrhName = brhInfo[1].toString();
			brhMap.put(tempBrhId, tempBrhName);
			// brhMap = getBelowBrhMap(tempBrhId,brhMap);
		}
		return brhMap;
	}

	/**
	 * 获得本行及下属机构编号信息
	 * 
	 * @param brhMap
	 * @return
	 */
	public static String getBelowBrhInfo(Map<String, String> brhMap) {
		String belowBrhInfo = "(";
		Iterator<String> iter = brhMap.keySet().iterator();
		while (iter.hasNext()) {
			String brhId = iter.next();
			belowBrhInfo += "'" + brhId + "'";
			if (iter.hasNext()) {
				belowBrhInfo += ",";
			}
		}
		belowBrhInfo += ")";
		return belowBrhInfo;
	}

	/**
	 * 用机构号，获得以下商户
	 * 
	 * @param brhId
	 * @param brhMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getBelowMchtByBrhId(String brhId) {
		// String
		// sqls="SELECT MCHT_NO,MCHT_NM FROM TBL_MCHT_BASE_INF where MCHT_NO";
		Map<String, String> mchtMap = new HashMap<String, String>();
		String sql = "SELECT MCHT_NO,MCHT_NM FROM TBL_MCHT_BASE_INF  where bank_no in "
				+ getBelowBrhInfo(getBelowBrhMap2(brhId));
		List<Object[]> dataList = getCommQueryDAO().findBySQLQuery(sql);
		for (Object[] brhInfo : dataList) {
			String tempBrhId = brhInfo[0].toString();
			String tempBrhName = brhInfo[1].toString();
			mchtMap.put(tempBrhId, tempBrhName);
			// brhMap = getBelowBrhMap2(tempBrhId,brhMap);
		}
		return getBelowBrhInfo(mchtMap);
	}

	/**
	 * 获得指定日期的偏移日期
	 * 
	 * @param refDate
	 *            参照日期
	 * @param offSize
	 *            偏移日期
	 * @return
	 */
	public static String getOffSizeDate(String refDate, String offSize) {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.set(Integer.parseInt(refDate.substring(0, 4)),
				Integer.parseInt(refDate.substring(4, 6)) - 1,
				Integer.parseInt(refDate.substring(6, 8)));
		calendar.add(Calendar.DATE, Integer.parseInt(offSize));
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		String retDate = String.valueOf(calendar.get(Calendar.DATE));
		if (Integer.parseInt(month) < 10) {
			month = "0" + month;
		}
		if (Integer.parseInt(retDate) < 10) {
			retDate = "0" + retDate;
		}
		return year + month + retDate;
	}

	/**
	 * 将金额元转分
	 * 
	 * @param str
	 * @return
	 */
	public static String transYuanToFen(String str) {
		if (str == null || "".equals(str.trim()))
			return "";
		BigDecimal bigDecimal = new BigDecimal(str.trim());
		return bigDecimal.movePointRight(2).toString();
	}

	/**
	 * 将金额分转元
	 * 
	 * @param str
	 * @return
	 */
	public static String transFenToYuan(String str) {
		if (str == null || "".equals(str.trim()))
			return "";
		BigDecimal bigDecimal = new BigDecimal(str.trim());
		return bigDecimal.movePointLeft(2).toString();
	}

	/**
	 * 获得指定个数的随机数组合
	 * 
	 * @param len
	 * @return 2010-8-19上午10:51:15
	 */
	public static String getRandomNum(int len) {
		String ran = "";
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			ran += String.valueOf(random.nextInt(10));
		}
		return ran;
	}

	/**
	 * 判断字符串是否全部由数字组成
	 * 
	 * @param str
	 * @return 2010-8-26下午02:20:28
	 */
	public static boolean isMoney(String str) {

		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * 判断字符串是否全部由数字组成
	 * 
	 * @param str
	 * @return 2010-8-26下午02:20:28
	 */
	public static boolean isAllDigit(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i)))
				return false;
		}
		return true;
	}

	public static Date getCurrentTs() {
		Date now = new Date();
		return new Timestamp(now.getTime());
	}

	public static ICommQueryDAO getCommQueryDAO() {
		if (commQueryDAO == null) {
			commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
		}
		return commQueryDAO;
	}
	
	public static ICommQueryDAO getCommTMSQueryDAO() {
		if (commTMSQueryDAO == null) {
			commTMSQueryDAO = (ICommQueryDAO) ContextUtil.getBean("commTMSQueryDAO");
		}
		return commTMSQueryDAO;
	}
	
	public static ICommQueryDAO getcommGWQueryDAO() {
		
		if (commGWQueryDAO == null) {
			commGWQueryDAO = (ICommQueryDAO) ContextUtil.getBean("commGWQueryDAO");
		}
		return commGWQueryDAO;
	}
	public static ICommQueryDAO getcommPOSPQueryDAO() {
		
		if (commPOSPQueryDAO == null) {
			commPOSPQueryDAO = (ICommQueryDAO) ContextUtil.getBean("commPOSPQueryDAO");
		}
		return commPOSPQueryDAO;
	}

	public static void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		CommonFunction.commQueryDAO = commQueryDAO;
	}

	public static ICommQueryDAO getCommDFQueryDAO() {
		return commDFQueryDAO;
	}

	public static void setCommDFQueryDAO(ICommQueryDAO commDFQueryDAO) {
		CommonFunction.commDFQueryDAO = commDFQueryDAO;
	}

	public static ICommQueryDAO getCommMisQueryDAO() {
		if (commMisQueryDAO == null) {
			commMisQueryDAO = (ICommQueryDAO) ContextUtil.getBean("commMisQueryDAO");
		}
		return commMisQueryDAO;
	}

	public static void setCommMisQueryDAO(ICommQueryDAO commMisQueryDAO) {
		CommonFunction.commMisQueryDAO = commMisQueryDAO;
	}
	
	/**
	 * trim给定对象的field 仅对private field String有效(不含static)
	 * 
	 * @param obj
	 * @return 2011-6-22下午03:46:12
	 */
	public static Object trimObject(Object obj) {

		try {
			Method[] methods = obj.getClass().getMethods();
			for (Method m : methods) {
				if (m.getName().startsWith("get")) {
					// 允许个别字段转换失败
					try {
						if (String.class == m.getReturnType()) {
							String value = (String) m.invoke(obj,
									new Object[] {});
							if (!StringUtil.isNull(value)) {
								obj.getClass()
										.getMethod(
												"s" + m.getName().substring(1),
												String.class)
										.invoke(obj, value.trim());
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 获得指定个数转换为Double
	 * 
	 * @param len
	 * @return 2010-8-19上午10:51:15
	 */

	public static Double getDValue(String value, Double _default) {
		if (StringUtil.isNotEmpty(value)) {
			return Double.valueOf(value);
		}
		return _default;
	}

	/**
	 * 获得指定个数转换为BigDecimal
	 * 
	 * @param len
	 * @return 2010-8-19上午10:51:15
	 */
	public static BigDecimal getBValue(String value, BigDecimal _default) {
		if (StringUtil.isNotEmpty(value)) {
			try {
				return new BigDecimal(value.trim());
			} catch (Exception ex) {
				return _default;
			}
		} else
			return _default;
	}

	/**
	 * 获得指定个数转换为int
	 * 
	 * @param len
	 * @return 2010-8-19上午10:51:15
	 */
	public static Integer getInt(String value, int _default) {
		if (StringUtil.isNotEmpty(value)) {
			try {
				return Integer.parseInt(value.trim());
			} catch (Exception ex) {
				return _default;
			}
		} else
			return _default;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String formate8Date(String str) {
		if (str.length() == 8) {
			return str.substring(0, 4) + "-" + str.substring(4, 6) + "-"
					+ str.substring(6, 8);
		}
		return str;
	}

	public static String getCurrDate(String format) {
		SimpleDateFormat formater = new SimpleDateFormat(format);
		return formater.format(new Date());
	}

	/**
	 * 16进制
	 * 
	 * @param c
	 * @return 2011-7-27上午11:50:28
	 */
	private static byte toByte(char c) {
		byte b = (byte) "0123456789abcdef".indexOf(c);
		return b;
	}

	/**
	 * 16进制转BCD
	 * 
	 * @param hex
	 * @return 2011-7-27上午11:49:20
	 */
	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	/**
	 * BCD转成16进制
	 * 
	 * @param bArray
	 * @return 2011-7-27上午11:47:56
	 */
	public static String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	public static String urlToRoleId(String url) {
		try {
			String[] array = url.split("/");
			String[] result = array[array.length - 1].split("\\.");
			String res = result[0];
			return res;
		} catch (Exception e) {
			return url;
		}
	}

	public static String transMoney(double n) {
		try {
			String[] fraction = { "角", "分" };
			String[] digit = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
			String[][] unit = { { "元", "万", "亿" }, { "", "拾", "佰", "仟" } };

			String head = n < 0 ? "负" : "";
			n = Math.abs(n);

			String s = "";

			for (int i = 0; i < fraction.length; i++) {
				s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i])
						.replaceAll("(零.)+", "");
			}
			if (s.length() < 1) {
				s = "整";
			}
			int integerPart = (int) Math.floor(n);

			for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
				String p = "";
				for (int j = 0; j < unit[1].length && n > 0; j++) {
					p = digit[integerPart % 10] + unit[1][j] + p;
					integerPart = integerPart / 10;
				}
				s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零")
						+ unit[0][i] + s;
			}
			return head
					+ s.replaceAll("(零.)*零元", "元").replaceAll("(零.)+", "")
							.replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String insertString(String src, String fill) {
		String tmp = "";
		for (int i = 0; i < src.length(); i++) {
			tmp += fill;
			tmp += src.substring(i, i + 1);
		}
		return tmp;
	}

	/**
	 * get random number
	 * 
	 * @param count
	 *            the count of number
	 * @return
	 */
	public static synchronized String getRandom(int count) {

		String randomNum = "";

		Random random = new Random();

		for (int i = 0; i < count; i++) {

			randomNum += random.nextInt(10);

		}

		return randomNum;
	}

	/** sql：本机构下属机构(?,?,?...) 注：不包含本机构 */
	public static String getStringBelowBrhIds(String brhId) {
		Map<String, String> brhMap = new LinkedHashMap<String, String>();
		return CommonFunction.getBelowBrhInfo(CommonFunction.getBelowBrhMap(
				brhId, brhMap));
	}

	/** 金额格式转换，三位加逗号 */
	public static String moneyFormat(String money) {
		if (!StringUtil.isNotEmpty(money)) {
			return money;
		}
		DecimalFormat df = new DecimalFormat("0.00");

		String head = "-".equals(money.substring(0, 1)) ? "-" : "";
		money = "-".equals(money.substring(0, 1)) ? money.substring(1) : money;

		money = df.format(Double.parseDouble(money.toString()));
		// int i=money.length();
		String inter = money.substring(0, money.indexOf("."));
		String doubl = money.substring(money.indexOf("."));
		char[] tempInter = new char[inter.length()];
		StringBuffer temp = new StringBuffer("");
		for (int j = 0; j < inter.length(); j++) {
			char c = inter.charAt(inter.length() - j - 1);
			tempInter[j] = c;
		}
		for (int i = 0; i < tempInter.length; i++) {
			if (i != 0 && i % 3 == 0) {
				temp.append(",");
			}
			temp.append(tempInter[i]);
		}
		StringBuffer result = new StringBuffer("");
		for (int l = 0; l < temp.length(); l++) {
			char c = temp.charAt(temp.length() - l - 1);
			result.append(c);
		}
		return head + result.toString() + doubl;
	}

	/** 日期格式转换 */
	public static String dateFormat(String date) {
		if (date.length() == 10) {
			return date.substring(0, 2) + "-" + date.substring(2, 4) + ' '
					+ date.substring(4, 6) + ':' + date.substring(6, 8) + ':'
					+ date.substring(8, 10);
		} else if (date.length() == 8) {
			return date.substring(0, 4) + "-" + date.substring(4, 6) + "-"
					+ date.substring(6, 8);
		} else if (date.length() == 6) {
			return date.substring(0, 2) + ":" + date.substring(2, 4) + ":"
					+ date.substring(4, 6);
		} else if (date.length() == 4) {
			return date.substring(0, 2) + ":" + date.substring(2, 4);
		} else if (date.length() == 14) {
			return date.substring(0, 4) + "-" + date.substring(4, 6) + "-"
					+ date.substring(6, 8) + ' ' + date.substring(8, 10) + ":"
					+ date.substring(10, 12) + ":" + date.substring(12, 14);
			/*
			 * try { date =
			 * showDateFormatZHCN.format(sysDateFormat.parse(date)); } catch
			 * (ParseException e) { e.printStackTrace(); } return date;
			 */
		} else {
			return date;
		}
	}

	/**
	 * 8位时间段格式转换
	 * 
	 * @param time
	 *            06001830
	 * @return 06:00 - 18:30
	 */
	public static String timeRangeFormat(String time) {
		if (time.length() == 8) {
			return time.substring(0, 2) + ":" + time.substring(2, 4) + " - "
					+ time.substring(4, 6) + ":" + time.substring(6, 8);
		} else {
			return time;
		}
	}

	/**
	 * @函数功能: BCD码转为10进制串(阿拉伯数据)
	 * @输入参数: BCD码
	 * @输出结果: 10进制串
	 */
	public static String bcd2Str(byte[] bytes) {
		StringBuffer temp = new StringBuffer(bytes.length * 2);

		for (int i = 0; i < bytes.length; i++) {
			temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
			temp.append((byte) (bytes[i] & 0x0f));
		}
		return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
				.toString().substring(1) : temp.toString();
	}

	public static byte[] str2Bcd(String asc) {
		int len = asc.length();
		int mod = len % 2;
		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}
		byte abt[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}
		byte bbt[] = new byte[len];
		abt = asc.getBytes();
		int j, k;
		for (int p = 0; p < asc.length() / 2; p++) {
			if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
				j = abt[2 * p] - '0';
			} else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
				j = abt[2 * p] - 'a' + 0x0a;
			} else {
				j = abt[2 * p] - 'A' + 0x0a;
			}
			if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
				k = abt[2 * p + 1] - '0';
			} else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
				k = abt[2 * p + 1] - 'a' + 0x0a;
			} else {
				k = abt[2 * p + 1] - 'A' + 0x0a;
			}
			int a = (j << 4) + k;
			byte b = (byte) a;
			bbt[p] = b;
		}
		return bbt;
	}

	/** 5位时间_格式转换【针对营业时间 by xupengfei】 */
	public static String timeFormat(String time) {
		if (time.length() == 5) {
			return time.substring(0, 2) + time.substring(3, 5);
		} else {
			return time;
		}
	}

	/**
	 * 判断当前时间与给定时间的差值是否在给定限时内
	 * 
	 * @param refTime
	 *            给定时间
	 * @param lockTime
	 *            给定限时
	 * @return A#B (如果在给定时限内 0#离限时还剩时间；如果不在限时内 1#0)
	 */
	public static String lockFlag(String refTime, String lockTime) {
		long between = 0;
		try {
			Date begin = sysDateFormat.parse(refTime);
			Date end = new Date();
			between = (end.getTime() - begin.getTime());// 得到两者的毫秒数
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (between / (60 * 60 * 1000) < Integer.parseInt(lockTime)) {
			between = Integer.parseInt(lockTime) * 60 * 60 * 1000 - between;
			long hour = between / (60 * 60 * 1000);
			long min = ((between / (60 * 1000)) - hour * 60);
			long s = (between / 1000 - hour * 60 * 60 - min * 60);
			return "0#" + hour + "小时" + min + "分钟" + s + "秒";
		}
		return "1#0";
	}

	/** 按字节截取 by caotz 
	 * @throws UnsupportedEncodingException */
	public static String mySubstr(String str, int begin, int end) throws UnsupportedEncodingException {
		byte[] tmp = str.getBytes("GBK");
		byte[] newByte = new byte[end - begin];
		System.arraycopy(tmp, begin, newByte, 0, end - begin);
		return new String(newByte,"GBK");
	}

	/**
	 * （左边或右边）去掉指定字符串
	 * 
	 * @param str
	 * @param fill
	 *            指定字符
	 * @param isEnd
	 *            true：去右边 false：去左边
	 * @return
	 */
	public static String cutString(String str, char fill, boolean isEnd) {

		char[] c = str.toCharArray();
		if (isEnd) {
			for (int i = str.length() - 1; i > 0; i--) {
				if (fill == c[i]) {
					str = str.substring(0, i);
				} else {
					return str;
				}
			}
		} else {
			for (int i = 0; i < str.length(); i++) {
				if (fill == c[i]) {
					str = str.substring(i);
				} else {
					return str;
				}
			}
		}

		return str;
	}

}
