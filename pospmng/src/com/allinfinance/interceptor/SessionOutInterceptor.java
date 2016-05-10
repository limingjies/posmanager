package com.allinfinance.interceptor;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.allinfinance.common.CommonsConstants;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.struts.system.action.LoginAction;
import com.allinfinance.struts.system.action.LogoutAction;
import com.allinfinance.system.util.SysParamUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.allinfinance.struts.system.action.ResetPwdAction;

public class SessionOutInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger
			.getLogger(SessionOutInterceptor.class);

	private String txnDesc = SysParamUtil.getParam("SESSION_TIMEOUT");
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String intercept(ActionInvocation actionInvocation) throws Exception {

		logger.info("*******struts.xml.Interceptor*******");
		// 获取session
		Map session = actionInvocation.getInvocationContext().getSession();
		HttpSession sessions =ServletActionContext.getRequest().getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		// 获取拦截器对象
		Object action = (Action) actionInvocation.getAction();
		// 如果获取的拦截器为 AdminLoginAction或者UserLoginAction,则不进行拦截
		if (action instanceof LoginAction || action instanceof ResetPwdAction||action instanceof LogoutAction) {
			return actionInvocation.invoke();
		}
		// 得到session中的用户
		String oprId = (String)session.get("oprId");
		// 如果用户为空则跳到LOGIN页面，否则退出拦截
		if (oprId == null) {
			String flag = "";
			PrintWriter pw = response.getWriter();
			//ajax请求
			if (request.getHeader("X-Requested-With") != null
					&& request.getHeader("X-Requested-With").equalsIgnoreCase(
							"XMLHttpRequest")) {
//				logger.info("用户没登录或登录过期，不能访问");
				logger.info("*******struts.Interceptor:Session timeOut*******");
				response.setCharacterEncoding("text/html;charset=utf-8");
				response.setContentType("text/html;charset=utf-8");
				flag = "9999";
				pw.write(flag);
				//传统请求
			}else{
				pw.print(
						"<script>alert('" +txnDesc+"'); " +
						"window.top.location.href='" + request.getContextPath() + "';" + " </script>");
				pw.close();  
			}
			return null;
			// return "sessionAlreadyOut";

		} else {
			String userSingalFlag = SysParamUtil.getParam(SysParamConstants.SINGAL_USER);
			if(userSingalFlag.equals(CommonsConstants.SINGAL_USER)) {
				Map<String, String> userlistMap =  (Map<String, String>) sessions.getServletContext().getAttribute(SysParamConstants.USER_LIST);
				if (userlistMap ==  null ) {
					userlistMap = new  HashMap<String, String>();
				}
					
				boolean  temp =  false ;
				if(StringUtil.isNotEmpty(userlistMap.get(oprId))
						&&userlistMap.get(oprId).equals(sessions.getId())){
					temp=true;
				}
				
				// 存在了第二次登陆，销毁session
				if (!temp){
//					sessions.invalidate();
					String flag = "";
					PrintWriter pw = response.getWriter();
					logger.info("*******struts.Interceptor:login singal out*******");
					response.setCharacterEncoding("text/html;charset=utf-8");
					response.setContentType("text/html;charset=utf-8");
					flag = "8888";
					pw.write(flag);
					return null;
				}
				return actionInvocation.invoke();
			}
			return actionInvocation.invoke();
		}

	}

}
