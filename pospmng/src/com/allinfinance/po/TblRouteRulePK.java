package com.allinfinance.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblRouteRulePK implements Serializable{

	private String brhId;
	private String accpId;
	private String ruleId;
	
	
	/**
	 * 
	 */
	public TblRouteRulePK() {
//		super();
	}
	/**
	 * @return the brhId
	 */
	public String getBrhId() {
		return brhId;
	}
	/**
	 * @param brhId the brhId to set
	 */
	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}
	/**
	 * @return the accpId
	 */
	public String getAccpId() {
		return accpId;
	}
	/**
	 * @param accpId the accpId to set
	 */
	public void setAccpId(String accpId) {
		this.accpId = accpId;
	}
	/**
	 * @return the ruleId
	 */
	public String getRuleId() {
		return ruleId;
	}
	/**
	 * @param ruleId the ruleId to set
	 */
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
	
	
}
