
package com.allinfinance.struts.settleNew.action;



import com.allinfinance.bo.settleNew.T100201BO;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

/**
 * Title: 
 * 
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-8-10
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author caotz
 * 
 * @version 1.0
 */
public class T100201Action extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private String transCode;
	private String transDate;
	
	private T100201BO t100201BO = (T100201BO) ContextUtil.getBean("T100201BO");
	
	
	protected String subExecute() throws Exception {
		if ("start".equals(method)) {
			return start();
		}
		return rspCode;
	}

	/**
	 * 启动单个任务
	 * @throws Exception 
	 * @date 2011-03-07
	 */
	public String start() throws Exception {
		rspCode=t100201BO.start(transCode,transDate.replace("-", ""));
		return rspCode;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}


	
	
	
	
	
	
}
