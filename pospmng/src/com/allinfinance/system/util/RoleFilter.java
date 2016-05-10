/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2011-9-16       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2011 allinfinance, Inc. All rights reserved.
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
package com.allinfinance.system.util;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.allinfinance.common.Constants;
import com.allinfinance.dwr.mchnt.T20100;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-9-16
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @version 1.0
 */
public class RoleFilter implements Filter{

	private static Logger log = Logger.getLogger(RoleFilter.class);

	
	public void destroy() {
		
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		String url = request.getRequestURL().toString();
		String res = CommonFunction.urlToRoleId(url);
		if(res.matches("T\\d+$"))
		{
			//1.判断请求来源
			if (null == request.getHeader("referer")) {
				//判断是否为子页面，该系统定义子页面为编号+两位数字
				//这里将子页面自动放行，适用于window.open()方式打开的窗口
				if (res.length() < 8 || !res.substring(res.length()-2).matches("[0-9]{2}$")) {
					log.info("illegal access(referer)!");
					response.sendRedirect(request.getContextPath()+"/redirect.asp");
				}
			}
			
			//2.判断请求合法性
			HttpSession session = request.getSession();
			HashSet<String> set = (HashSet<String>)session.getAttribute(Constants.USER_AUTH_SET);
			String resTmp=res.length()>7?res.substring(1,res.length()-2):res.substring(1);
			if(set == null || (!set.contains(resTmp)&&(!"T00001".equals(res)))) {
//			if(set == null){
				log.info("illegal access!");
				response.sendRedirect(request.getContextPath()+"/redirect.asp");
			}else {
				arg2.doFilter(request, response);
			}
		}
		else
			arg2.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
