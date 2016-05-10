package com.allinfinance.po.risk;

import java.io.Serializable;
import com.allinfinance.po.risk.TblBankCardBlackOptLogPK;


public class TblBankCardBlackOptLog implements Serializable {
	private static final long serialVersionUID = 1L;
	// primary key
	private TblBankCardBlackOptLogPK id;
	// fields
	private java.lang.String oprId;
	private java.lang.String optFlag;
	private java.lang.String remarkInfo;
	private java.lang.String resved;
	private java.lang.String resved1;
	/**
	 * @return the id
	 */
	public TblBankCardBlackOptLogPK getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(TblBankCardBlackOptLogPK id) {
		this.id = id;
	}
	/**
	 * @return the oprId
	 */
	public java.lang.String getOprId() {
		return oprId;
	}
	/**
	 * @param oprId the oprId to set
	 */
	public void setOprId(java.lang.String oprId) {
		this.oprId = oprId;
	}
	/**
	 * @return the optFlag
	 */
	public java.lang.String getOptFlag() {
		return optFlag;
	}
	/**
	 * @param optFlag the optFlag to set
	 */
	public void setOptFlag(java.lang.String optFlag) {
		this.optFlag = optFlag;
	}
	/**
	 * @return the remarkInfo
	 */
	public java.lang.String getRemarkInfo() {
		return remarkInfo;
	}
	/**
	 * @param remarkInfo the remarkInfo to set
	 */
	public void setRemarkInfo(java.lang.String remarkInfo) {
		this.remarkInfo = remarkInfo;
	}
	/**
	 * @return the resved
	 */
	public java.lang.String getResved() {
		return resved;
	}
	/**
	 * @param resved the resved to set
	 */
	public void setResved(java.lang.String resved) {
		this.resved = resved;
	}
	/**
	 * @return the resved1
	 */
	public java.lang.String getResved1() {
		return resved1;
	}
	/**
	 * @param resved1 the resved1 to set
	 */
	public void setResved1(java.lang.String resved1) {
		this.resved1 = resved1;
	}
	
}