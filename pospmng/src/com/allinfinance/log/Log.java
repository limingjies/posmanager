package com.allinfinance.log;

import org.apache.log4j.Logger;

import com.allinfinance.system.util.CommonFunction;

/**
 * Title:系统日志
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-3-8
 * 
 * 
 * @version 1.0
 */
public class Log {
	
	private static Logger log = Logger.getLogger("ext");
	
	public static void log(String info) {
		log.info("##########[ "  + CommonFunction.getCurrentDateTimeForShow() + " ]"+ info + "##########");
	}
}
