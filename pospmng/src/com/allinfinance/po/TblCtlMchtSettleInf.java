package com.allinfinance.po;

import java.io.Serializable;

public class TblCtlMchtSettleInf implements Serializable{
	
	private static final long serrialVersionUID = 1L;
	
	private int hashCode = Integer.MIN_VALUE;

	// primary key
	public com.allinfinance.po.TblCtlMchtSettleInfPK id;
	
	// fields
	public java.lang.String saMchtNo;
	public java.lang.String saActionDate;
	public java.lang.String saActionAmt;
	public java.lang.String actionDoneAmt;
	public java.lang.String oriSettleDate;
	public java.lang.String actionFlag;
	public java.lang.String reserved1;
	public java.lang.String reserved2;
	public java.lang.String saInitOprId;
	public java.lang.String saPriviOprId;
	public java.lang.String saInitTime;	
	



	public com.allinfinance.po.TblCtlMchtSettleInfPK getId() {
		return id;
	}

	public void setId(com.allinfinance.po.TblCtlMchtSettleInfPK id) {
		this.id = id;
	}

	public java.lang.String getSaMchtNo() {
		return saMchtNo;
	}

	public void setSaMchtNo(java.lang.String saMchtNo) {
		this.saMchtNo = saMchtNo;
	}

	public java.lang.String getSaActionDate() {
		return saActionDate;
	}

	public void setSaActionDate(java.lang.String saActionDate) {
		this.saActionDate = saActionDate;
	}

	public java.lang.String getSaActionAmt() {
		return saActionAmt;
	}

	public void setSaActionAmt(java.lang.String saActionAmt) {
		this.saActionAmt = saActionAmt;
	}

	public java.lang.String getActionDoneAmt() {
		return actionDoneAmt;
	}

	public void setActionDoneAmt(java.lang.String actionDoneAmt) {
		this.actionDoneAmt = actionDoneAmt;
	}

	public java.lang.String getOriSettleDate() {
		return oriSettleDate;
	}

	public void setOriSettleDate(java.lang.String oriSettleDate) {
		this.oriSettleDate = oriSettleDate;
	}

	public java.lang.String getActionFlag() {
		return actionFlag;
	}

	public void setActionFlag(java.lang.String actionFlag) {
		this.actionFlag = actionFlag;
	}

	public java.lang.String getReserved1() {
		return reserved1;
	}

	public void setReserved1(java.lang.String reserved1) {
		this.reserved1 = reserved1;
	}

	public java.lang.String getReserved2() {
		return reserved2;
	}

	public void setReserved2(java.lang.String reserved2) {
		this.reserved2 = reserved2;
	}

	public java.lang.String getSaInitOprId() {
		return saInitOprId;
	}

	public void setSaInitOprId(java.lang.String saInitOprId) {
		this.saInitOprId = saInitOprId;
	}

	public java.lang.String getSaPriviOprId() {
		return saPriviOprId;
	}

	public void setSaPriviOprId(java.lang.String saPriviOprId) {
		this.saPriviOprId = saPriviOprId;
	}

	public java.lang.String getSaInitTime() {
		return saInitTime;
	}

	public void setSaInitTime(java.lang.String saInitTime) {
		this.saInitTime = saInitTime;
	}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.allinfinance.po.TblCtlMchtSettleInf)) return false;
		else {
			com.allinfinance.po.TblCtlMchtSettleInf tblCtlMchtSettleInf = (com.allinfinance.po.TblCtlMchtSettleInf) obj;
			if (null == this.getId() || null == tblCtlMchtSettleInf.getId()) return false;
			else return (this.getId().equals(tblCtlMchtSettleInf.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}
}