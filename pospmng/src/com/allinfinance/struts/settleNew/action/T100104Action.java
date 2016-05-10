
package com.allinfinance.struts.settleNew.action;



import com.allinfinance.bo.settleNew.T100104BO;
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
public class T100104Action extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private T100104BO t100604BO = (T100104BO) ContextUtil.getBean("T100104BO");
	private String settleDate;
	
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
		rspCode=t100604BO.start(settleDate.replace("-", ""));
		return rspCode;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	
	
	/**
	 * 重置批量
	 * @date 2011-03-07
	 */
	/*public String rest() {
		T80603BO t0601003bo = (T80603BO) ContextUtil.getBean("T80603BO");
		try {
			rspCode = t0601003bo.update(restFlag,restBatId,restGrpId);
		} catch (Exception e) {
			e.printStackTrace();
			rspCode = "重置批量失败！";
		}
		return rspCode;
	}*/
	
	
	
	
}
