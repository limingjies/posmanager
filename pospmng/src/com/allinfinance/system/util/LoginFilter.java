/**
 * 
 */
package com.allinfinance.system.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;








import com.allinfinance.common.CommonsConstants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;

/**
 * @author by caotz
 *
 */
public class LoginFilter implements Filter {

//	private static Logger log = Logger.getLogger(LoginFilter.class);
	
	public void destroy() {

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		
			String txnDesc = SysParamUtil.getParam("SINGAL_USER_INFO");
			
			HttpServletRequest request = (HttpServletRequest) arg0;    
		   	HttpServletResponse response = (HttpServletResponse) arg1;    
		   	HttpSession session = request.getSession();    
		   	
		   	String url = request.getRequestURL().toString();
		   	String res = CommonFunction.urlToRoleId(url);
		   	String userSingalFlag = SysParamUtil.getParam(SysParamConstants.SINGAL_USER);
			if(userSingalFlag.equals(CommonsConstants.SINGAL_USER)&&res.matches("T\\d+$")) {
			       
				Map<String, String> userlistMap =  (Map<String, String>) session.getServletContext().getAttribute(SysParamConstants.USER_LIST);
				if (userlistMap ==  null ) {
					userlistMap = new  HashMap<String, String>();
				}
					
				boolean  temp =  false ;    
			    
				if(StringUtil.isNotEmpty(userlistMap.get((String) session.getAttribute("oprId")))
						&&userlistMap.get((String) session.getAttribute("oprId")).equals(session.getId())){
					temp=true;
				}
			       
				// 存在了第二次登陆，销毁session
				if (!temp){
//					session.invalidate();
					PrintWriter out = response.getWriter();
					out.print("<script>alert('" +txnDesc+"'); " +
							"window.top.location.href='" + request.getContextPath() + "/'; </script>");
					out.close();
				}else{
					arg2.doFilter(arg0, arg1);
				}
			}else {
//				log.info("多用户登录模式");
				arg2.doFilter(arg0, arg1);
//				arg2.doFilter(request, response);
			}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
