package com.allinfinance.system.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
	
	static String temp = null ;
	static int index = 0 ;
	
	private static Pattern P1 = Pattern.compile("[\u4E00-\u9FA5]+");
	//营业执照编号(理想：15位；现实：15~20位)
	private static Pattern P2 = Pattern.compile("[A-Za-z0-9]{15,20}");
	//税务登记证号码(理想：15位；现实：15~20位)
//	private static Pattern P3 = Pattern.compile("[A-Za-z0-9]{15,20}");
//	private static Pattern P4 = Pattern.compile("[\u4E00-\u9FA5]+");
	//证件号码
	private static Pattern P5 = Pattern.compile("\\w{1,20}");
//	private static Pattern P6 = Pattern.compile(".{1,15}");
	//电话
	private static Pattern P7 = Pattern.compile("((1\\d{10})|((\\d{4}-|\\d{3}-)?(\\d{8}|\\d{7})))(，((1\\d{10})|((\\d{4}-|\\d{3}-)?(\\d{8}|\\d{7}))))*");
	//经营面积
	private static Pattern P8 = Pattern.compile("[1-9]\\d{0,9}");
//	private static Pattern P9 = Pattern.compile(".{1,40}");
	//帐号
	private static Pattern P10 = Pattern.compile("[0-9]{1,40}");
	//开户行行号
	private static Pattern P11 = Pattern.compile("[0-9]{12}");
	//日期（例如：2014/8/19）
	private static Pattern P13 = Pattern.compile("(\\d{4}|\\d{2})/((1[0-2])|(0[1-9]))/(([12][0-9])|(3[01])|(0?[1-9]))");
	//费率（按百分比:float%||(float%,float封顶))||(按笔:单笔float元)           float-->(0.00% ~ 100.00%)
	private static Pattern P14 = Pattern.compile("(单笔[1-9][0-9]*(\\.[0-9]{1,2})?元)|(([1-9][0-9]|[0-9])(\\.[0-9]{1,2})?)%(，[1-9][0-9]*(\\.[0-9]{1,2})?封顶)?");
	//商户路由规则（现废除为无效规则）
	private static Pattern P15 = Pattern.compile("XCKJ02|TL001|TL003|TL005，TL012|TL012，TL005|TL006，TL013|TL013，TL006");

	public static boolean M1(String str) {
		index = str.length();
		Matcher m = P1.matcher(str);
		while (m.find())
        {
            temp = m.group(0);
            index += temp.length();
        }
		if(index >= 1&&index <= 60){
			return true;
		} else
			return false;
	}
	public static boolean M2(String str) {
		return P2.matcher(str).matches();
	}
/*	public static boolean M3(String str) {
		return P3.matcher(str).matches();
	}*/
	public static boolean M4(String str) {
		index = str.length();
		Matcher m = P1.matcher(str);
		while (m.find())
        {
            temp = m.group(0);
            index += temp.length();
        }
		if(index >= 1&&index <= 10){
			return true;
		} else
			return false;
	}
	public static boolean M5(String str) {
		return P5.matcher(str).matches();
	}
	public static boolean M6(String str) {
		index = str.length();
		Matcher m = P1.matcher(str);
		while (m.find())
        {
            temp = m.group(0);
            index += temp.length();
        }
		if(index >= 1&&index <= 30){
			return true;
		} else
			return false;
	}
	public static boolean M7(String str) {
		return P7.matcher(str).matches();
	}
	public static boolean M8(String str) {
		return P8.matcher(str).matches();
	}
	public static boolean M9(String str) {
		index = str.length();
		Matcher m = P1.matcher(str);
		while (m.find())
        {
            temp = m.group(0);
            index += temp.length();
        }
		if(index >= 1&&index <= 80){
			return true;
		} else
			return false;
	}
	public static boolean M10(String str) {
		return P10.matcher(str).matches();
	}
	public static boolean M11(String str) {
		return P11.matcher(str).matches();
	}
	public static boolean M12(String str) {
		index = str.length();
		Matcher m = P1.matcher(str);
		while (m.find())
        {
            temp = m.group(0);
            index += temp.length();
        }
		if(index >= 1&&index <= 40){
			return true;
		} else
			return false;
	}
	public static boolean M13(String str) {
		return P13.matcher(str).matches();
	}
	public static boolean M14(String str) {
		return P14.matcher(str).matches();
	}
	public static boolean M15(String str) {
		return P15.matcher(str).matches();
	}
}

