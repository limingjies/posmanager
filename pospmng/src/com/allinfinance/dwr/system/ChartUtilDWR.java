/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-9-29       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 allinfinance, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai allinfinance Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with allinfinance.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.allinfinance.dwr.system;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.allinfinance.chart.ChartGenerator;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Title: 图表工具
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-9-29
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class ChartUtilDWR {
	
	private static Logger log = Logger.getLogger(ChartUtilDWR.class);
	/**
	 * 系统图表工具
	 * @param methodName
	 * @return
	 * 2010-9-29下午05:07:55
	 */
	public String load(String methodName,String param,HttpServletRequest request,
			HttpServletResponse response) {
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		Object[] params = new Object[]{operator,param};
		try {
			String charData = (String)ChartGenerator.class.getMethod(methodName,
			        new Class[] { Object[].class }).invoke(ChartGenerator.class,
			        new Object[] {params});
			log.info("图表信息：[ " + charData + " ]");
//			print();
			return charData;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			log.error("加载图表信息出错：[ " + e.getMessage() + " ]");
			return "-1";
		} catch (SecurityException e) {
			e.printStackTrace();
			log.error("加载图表信息出错：[ " + e.getMessage() + " ]");
			return "-1";
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			log.error("加载图表信息出错：[ " + e.getMessage() + " ]");
			return "-1";
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			log.error("加载图表信息出错：[ " + e.getMessage() + " ]");
			return "-1";
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			log.error("加载图表信息出错：[ " + e.getMessage() + " ]");
			return "-1";
		} catch (Exception e) {
			e.printStackTrace();
			log.error("加载图表信息出错：[ " + e.getMessage() + " ]");
			return "-1";
		}
	}
	
	public void print() throws IOException, TemplateException {
		
		Configuration configuration = new Configuration();
		
		Template template = configuration.getTemplate("templates/example1.ftl");
		
		Map<String, String> rootMap = new HashMap<String, String>();
		
		rootMap.put("name", "测试");
		
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		
		BufferedWriter out = new BufferedWriter(new PrintWriter(arrayOutputStream));
		
		template.process(rootMap, out);
		
		System.out.print(arrayOutputStream.toString());
	}
}
