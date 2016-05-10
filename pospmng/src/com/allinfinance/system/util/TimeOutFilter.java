
package com.allinfinance.system.util;

import java.io.IOException;
import java.io.PrintWriter;

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

public class TimeOutFilter implements Filter{

	private static Logger log = Logger.getLogger(TimeOutFilter.class);
	String txnDesc = SysParamUtil.getParam("SESSION_TIMEOUT");
//	String txnDesc = "session 操作超时！";
//	protected String timeoutPage = null;
	
	public void destroy() {
//		this.timeoutPage = null;
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		String url = request.getRequestURL().toString();
		String res = CommonFunction.urlToRoleId(url);
		log.info("*************web.xml.Filter***********");
		if((res.matches("T\\d+$")||res.contains("Action"))&&!res.equals("main"))
		{
			/*//1.判断请求来源
			if (null == request.getHeader("referer")) {
				//判断是否为子页面，该系统定义子页面为编号+两位数字
				//这里将子页面自动放行，适用于window.open()方式打开的窗口
				if (res.length() != 8 || !res.substring(6, 8).matches("[0-9]{2}$")) {
					log.info("illegal access(referer)!");
					response.sendRedirect(request.getContextPath()+"/redirect.asp");
				}
			}*/
			
			//2.判断请求合法性
			HttpSession session = request.getSession();
			String oprId = (String)session.getAttribute("oprId");
			
			if(oprId == null ) {
				log.info("************web.Filter:Session timeOut***********");
				if(res.matches("T\\d+$")){
//				response.sendRedirect(request.getContextPath()+"/redirect.asp");
//				response.sendRedirect(request.getContextPath()+"/page/system/main.jsp");/
//				response.getOutputStream().print("<script>window.top.location.href='" + request.getContextPath() + "/page/system/main.jsp';" + " </script>" );
					PrintWriter out = response.getWriter(); 
//					log.info(request.getContextPath());
					out.print(
						"<script>alert('" +txnDesc+"'); " +
						"window.top.location.href='" + request.getContextPath() + "';" + " </script>");
					out.close();  
//					response.getOutputStream().print(
//						"<script>alert('" +txnDesc+"'); " +
//						"window.top.location.href='" + request.getContextPath() + "/page/system/login.jsp';" + " </script>" );
				
				}
			}else {
				arg2.doFilter(arg0, arg1);
			}
		}else{
			arg2.doFilter(arg0, arg1);
		}
	}
	

	public void init(FilterConfig arg0) throws ServletException {
//		this.timeoutPage = arg0.getInitParameter("timeoutPage");
	}

}
