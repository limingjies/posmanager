package com.allinfinance.struts.agentpay.action;

import com.allinfinance.bo.agentpay.T90502BO;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T90502Action extends BaseAction {

	T90502BO t90502BO = (T90502BO) ContextUtil.getBean("T90502BO");

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {

		if("accept".equals(method)) {
			log("复审通过。操作员编号：" + operator.getOprId());
			rspCode = accept();
		} else if("refuse".equals(method)) {
			log("复审拒绝。操作员编号：" + operator.getOprId());
			rspCode = refuse();
		} else if("back".equals(method)) {
			log("复审退回。操作员编号：" + operator.getOprId());
			rspCode = back();
		}

		return rspCode;
	}
	
	
	private String accept(){
		return t90502BO.accept(batchId);
	}
	private String refuse(){
		
		return t90502BO.refuse(batchId, refuseInfo);
	}

	private String back(){
		
		return null;
	}
	
	public String batchId;
	public String refuseInfo;

	public T90502BO getT90502BO() {
		return t90502BO;
	}

	public void setT90502BO(T90502BO t90502bo) {
		t90502BO = t90502bo;
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
