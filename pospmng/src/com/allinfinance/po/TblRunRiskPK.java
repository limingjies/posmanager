package com.allinfinance.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblRunRiskPK implements Serializable{

	private java.lang.String riskLvl;
	private java.lang.String cardAccpId;
	private int ruleId;
	public java.lang.String getRiskLvl() {
		return riskLvl;
	}
	public void setRiskLvl(java.lang.String riskLvl) {
		this.riskLvl = riskLvl;
	}
	public java.lang.String getCardAccpId() {
		return cardAccpId;
	}
	public void setCardAccpId(java.lang.String cardAccpId) {
		this.cardAccpId = cardAccpId;
	}
	
	public int getRuleId() {
		return ruleId;
	}
	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}
	public TblRunRiskPK() {
	}
	
	
	
	
	
	
}
