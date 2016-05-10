package com.allinfinance.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblRuleMchtRelPK implements Serializable{

	private String ruleCode;
	private String firstMchtCd;
	
	
	
	/**
	 * 
	 */
	public TblRuleMchtRelPK() {
//		super();
	}


	/**
	 * @return the ruleCode
	 */
	public String getRuleCode() {
		return ruleCode;
	}


	/**
	 * @param ruleCode the ruleCode to set
	 */
	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}


	/**
	 * @return the firstMchtCd
	 */
	public String getFirstMchtCd() {
		return firstMchtCd;
	}


	/**
	 * @param firstMchtCd the firstMchtCd to set
	 */
	public void setFirstMchtCd(String firstMchtCd) {
		this.firstMchtCd = firstMchtCd;
	}


	
}
