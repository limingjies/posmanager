package com.allinfinance.system.util;

import java.util.ResourceBundle;

/**
 * Title: 系统交易信息
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-16
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class StatusUtil {
	
private static String STATUS_FILE = "Status";
	
	private static ResourceBundle BUNDLE = ResourceBundle.getBundle(STATUS_FILE);
	
	/**
	 * 获得交易信息
	 * @param key
	 * @return
	 */
	public static String getNextStatus(String key) {
		return BUNDLE.getString(key);
	}
}
