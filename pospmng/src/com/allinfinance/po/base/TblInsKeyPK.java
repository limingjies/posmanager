package com.allinfinance.po.base;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblInsKeyPK implements Serializable{

	private String insIdCd;
	private String firstMchtNo;
	
	
	/**
	 * 
	 */
	public TblInsKeyPK() {
//		super();
	}


	public String getInsIdCd() {
		return insIdCd;
	}


	public void setInsIdCd(String insIdCd) {
		this.insIdCd = insIdCd;
	}


	public String getFirstMchtNo() {
		return firstMchtNo;
	}


	public void setFirstMchtNo(String firstMchtNo) {
		this.firstMchtNo = firstMchtNo;
	}

	
	
	
}
