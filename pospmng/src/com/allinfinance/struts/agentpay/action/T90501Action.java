package com.allinfinance.struts.agentpay.action;

import com.allinfinance.bo.agentpay.T90501BO;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T90501Action extends BaseAction {

	T90501BO t90501BO = (T90501BO) ContextUtil.getBean("T90501BO");

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {

		if("accept".equals(method)) {
			log("初审通过。操作员编号：" + operator.getOprId());
			rspCode = accept();
		} else if("refuse".equals(method)) {
			log("初审拒绝。操作员编号：" + operator.getOprId());
			rspCode = refuse();
		} else if("back".equals(method)) {
			log("初审退回。操作员编号：" + operator.getOprId());
			rspCode = back();
		}

		return rspCode;
	}
	
	
	private String accept(){
		return t90501BO.accept(batchId);
	}
	private String refuse(){
		
		return t90501BO.refuse(batchId, refuseInfo);
	}

	private String back(){
		
		return null;
	}
	
	public String batchId;
	public String refuseInfo;

	public T90501BO getT90501BO() {
		return t90501BO;
	}

	public void setT90501BO(T90501BO t90501bo) {
		t90501BO = t90501bo;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getRefuseInfo() {
		return refuseInfo;
	}

	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}

}
