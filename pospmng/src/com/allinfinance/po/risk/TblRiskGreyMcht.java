package com.allinfinance.po.risk;

import java.io.Serializable;
import com.allinfinance.po.risk.TblRiskGreyMchtPK;


public class TblRiskGreyMcht implements Serializable {
	private static final long serialVersionUID = 1L;
	// primary key
	private TblRiskGreyMchtPK id;
	// fields
	private java.lang.String remarkInfo;
	private java.lang.String crtOpr;
	private java.lang.String crtTime;
	private java.lang.String resved;
	private java.lang.String resved1;
	/**
	 * @return the id
	 */
	public TblRiskGreyMchtPK getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(TblRiskGreyMchtPK id) {
		this.id = id;
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
	 * @return the crtOpr
	 */
	public java.lang.String getCrtOpr() {
		return crtOpr;
	}
	/**
	 * @param crtOpr the crtOpr to set
	 */
	public void setCrtOpr(java.lang.String crtOpr) {
		this.crtOpr = crtOpr;
	}
	/**
	 * @return the crtTime
	 */
	public java.lang.String getCrtTime() {
		return crtTime;
	}
	/**
	 * @param crtTime the crtTime to set
	 */
	public void setCrtTime(java.lang.String crtTime) {
		this.crtTime = crtTime;
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