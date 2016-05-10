package com.allinfinance.po;

import java.io.Serializable;

public class TblRiskBlackMcht implements Serializable {
	private static final long serialVersionUID = 1L;


	protected void initialize() {
	}

	/**
	 * 
	 */
	public TblRiskBlackMcht() {
	}


	// primary key
	private java.lang.String mchtNo;

	// fields
	private java.lang.String crtOprId;
	private java.lang.String crtDateTime;
	private java.lang.String resved;
	private java.lang.String resved1;


	public java.lang.String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(java.lang.String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public java.lang.String getCrtOprId() {
		return crtOprId;
	}

	public void setCrtOprId(java.lang.String crtOprId) {
		this.crtOprId = crtOprId;
	}

	public java.lang.String getCrtDateTime() {
		return crtDateTime;
	}

	public void setCrtDateTime(java.lang.String crtDateTime) {
		this.crtDateTime = crtDateTime;
	}

	public java.lang.String getResved() {
		return resved;
	}

	public void setResved(java.lang.String resved) {
		this.resved = resved;
	}

	public java.lang.String getResved1() {
		return resved1;
	}

	public void setResved1(java.lang.String resved1) {
		this.resved1 = resved1;
	}

	

	

}