package com.allinfinance.po.risk;

import java.io.Serializable;
import com.allinfinance.po.risk.TblWhiteMchtCheckPK;


public class TblWhiteMchtCheck implements Serializable {
	private static final long serialVersionUID = 1L;

	// primary key
	private TblWhiteMchtCheckPK id;

	// fields
	private java.lang.String checkOpr;
	private java.lang.String checkStatus;
	private java.lang.String mchtCaseDesp;
	private java.lang.String refuseReason;
	private java.lang.String dayAveAmt;
	private java.lang.String monAveAmt;
	private java.lang.String sigMinAmt;
	private java.lang.String sigMaxAmt;
	private java.lang.String servDisp;
	private java.lang.String applyReason;
	private java.lang.String applyOpr;
	private java.lang.String applyTime;
	private java.lang.String misc;
	/**
	 * @return the id
	 */
	public TblWhiteMchtCheckPK getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(TblWhiteMchtCheckPK id) {
		this.id = id;
	}
	/**
	 * @return the checkOpr
	 */
	public java.lang.String getCheckOpr() {
		return checkOpr;
	}
	/**
	 * @param checkOpr the checkOpr to set
	 */
	public void setCheckOpr(java.lang.String checkOpr) {
		this.checkOpr = checkOpr;
	}
	/**
	 * @return the refuseReason
	 */
	public java.lang.String getRefuseReason() {
		return refuseReason;
	}
	/**
	 * @param refuseReason the refuseReason to set
	 */
	public void setRefuseReason(java.lang.String refuseReason) {
		this.refuseReason = refuseReason;
	}
	/**
	 * @return the dayAveAmt
	 */
	public java.lang.String getDayAveAmt() {
		return dayAveAmt;
	}
	/**
	 * @param dayAveAmt the dayAveAmt to set
	 */
	public void setDayAveAmt(java.lang.String dayAveAmt) {
		this.dayAveAmt = dayAveAmt;
	}
	/**
	 * @return the monAveAmt
	 */
	public java.lang.String getMonAveAmt() {
		return monAveAmt;
	}
	/**
	 * @param monAveAmt the monAveAmt to set
	 */
	public void setMonAveAmt(java.lang.String monAveAmt) {
		this.monAveAmt = monAveAmt;
	}
	/**
	 * @return the sigMinAmt
	 */
	public java.lang.String getSigMinAmt() {
		return sigMinAmt;
	}
	/**
	 * @param sigMinAmt the sigMinAmt to set
	 */
	public void setSigMinAmt(java.lang.String sigMinAmt) {
		this.sigMinAmt = sigMinAmt;
	}
	/**
	 * @return the sigMaxAmt
	 */
	public java.lang.String getSigMaxAmt() {
		return sigMaxAmt;
	}
	/**
	 * @param sigMaxAmt the sigMaxAmt to set
	 */
	public void setSigMaxAmt(java.lang.String sigMaxAmt) {
		this.sigMaxAmt = sigMaxAmt;
	}
	/**
	 * @return the servDisp
	 */
	public java.lang.String getServDisp() {
		return servDisp;
	}
	/**
	 * @param servDisp the servDisp to set
	 */
	public void setServDisp(java.lang.String servDisp) {
		this.servDisp = servDisp;
	}
	/**
	 * @return the applyReason
	 */
	public java.lang.String getApplyReason() {
		return applyReason;
	}
	/**
	 * @param applyReason the applyReason to set
	 */
	public void setApplyReason(java.lang.String applyReason) {
		this.applyReason = applyReason;
	}
	/**
	 * @return the checkStatus
	 */
	public java.lang.String getCheckStatus() {
		return checkStatus;
	}
	/**
	 * @param checkStatus the checkStatus to set
	 */
	public void setCheckStatus(java.lang.String checkStatus) {
		this.checkStatus = checkStatus;
	}
	/**
	 * @return the mchtCaseDesp
	 */
	public java.lang.String getMchtCaseDesp() {
		return mchtCaseDesp;
	}
	/**
	 * @param mchtCaseDesp the mchtCaseDesp to set
	 */
	public void setMchtCaseDesp(java.lang.String mchtCaseDesp) {
		this.mchtCaseDesp = mchtCaseDesp;
	}
	/**
	 * @return the applyOpr
	 */
	public java.lang.String getApplyOpr() {
		return applyOpr;
	}
	/**
	 * @param applyOpr the applyOpr to set
	 */
	public void setApplyOpr(java.lang.String applyOpr) {
		this.applyOpr = applyOpr;
	}
	/**
	 * @return the applyTime
	 */
	public java.lang.String getApplyTime() {
		return applyTime;
	}
	/**
	 * @param applyTime the applyTime to set
	 */
	public void setApplyTime(java.lang.String applyTime) {
		this.applyTime = applyTime;
	}
	/**
	 * @return the misc
	 */
	public java.lang.String getMisc() {
		return misc;
	}
	/**
	 * @param misc the misc to set
	 */
	public void setMisc(java.lang.String misc) {
		this.misc = misc;
	}
	
}