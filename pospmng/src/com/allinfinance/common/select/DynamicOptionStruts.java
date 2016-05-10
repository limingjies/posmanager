package com.allinfinance.common.select;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * 设计思路：
 * 1.数据动态获取，前台传入前端匹配的参数
 * 2.不再使用ID作为和后台匹配的条件，而直接使用method name
 * 3.参数包括start,limit,inputValue,methodName
 * 
 */
public class DynamicOptionStruts extends ActionSupport{

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(DynamicOptionStruts.class);
	
	public String loadComboStruts() {
		
		String jsonData = "{data:[{'valueField':'','displayField':'没有找到可选内容'}]}";
		
		try {
			
			HttpServletRequest request = ServletActionContext.getRequest();
			Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
			if (null == operator) {
				writeMessage(jsonData);
			}
			int startInt = 0;
			int limitInt = Constants.QUERY_SELECT_COUNT;
			if (!StringUtil.isNull(start)) {
				startInt = Integer.parseInt(start);
			}
			if (!StringUtil.isNull(limit)) {
				limitInt = Integer.parseInt(limit);
			}
			if(inputValue.indexOf("_AS") != -1) {
				writeMessage(DynamicOptionHandler.handle(startInt, limitInt, inputValue.substring(3), methodName + "_as", operator, request));
			} else {
				writeMessage(DynamicOptionHandler.handle(startInt, limitInt, inputValue, methodName, operator, request));
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return SUCCESS;
	}

	private void writeMessage(String jsonData) throws IOException {
		
		PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
		printWriter.write(jsonData);
		printWriter.flush();
		printWriter.close();
	}
	
	private String start;
	private String limit;
	private String inputValue;
	private String methodName;

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getInputValue() {
		return inputValue;
	}

	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
}
