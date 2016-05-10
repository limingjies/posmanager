package com.allinfinance.po;

import java.io.Serializable;



public class TblCardRoute implements Serializable{
	private static final long serialVersionUID = 1L;


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.allinfinance.po.TblCardRoutePK id;

	// fields
	private java.lang.String cardIdOffset;
	private java.lang.String cardIdLen;
	private java.lang.String branchNo;
	private java.lang.String branchNoOffset;
	private java.lang.String branchNoLen;
	private java.lang.String instCode;
	private java.lang.String cardName;
	private java.lang.String destSrvId;
	private java.lang.String txnNum;
	private java.lang.String cardType;
	private java.lang.String modiOprId;
	private java.lang.String modiTime;
	private java.lang.String initOprId;
	private java.lang.String initTime;

	

	/**
	 * 
	 */
	public TblCardRoute() {
		super();
		// TODO Auto-generated constructor stub
	}


	public int getHashCode() {
		return hashCode;
	}

	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}


	public com.allinfinance.po.TblCardRoutePK getId() {
		return id;
	}


	public void setId(com.allinfinance.po.TblCardRoutePK id) {
		this.id = id;
	}

	public java.lang.String getCardIdOffset() {
		return cardIdOffset;
	}


	public void setCardIdOffset(java.lang.String cardIdOffset) {
		this.cardIdOffset = cardIdOffset;
	}


	public java.lang.String getCardIdLen() {
		return cardIdLen;
	}


	public void setCardIdLen(java.lang.String cardIdLen) {
		this.cardIdLen = cardIdLen;
	}


	public java.lang.String getBranchNo() {
		return branchNo;
	}


	public void setBranchNo(java.lang.String branchNo) {
		this.branchNo = branchNo;
	}


	public java.lang.String getBranchNoOffset() {
		return branchNoOffset;
	}


	public void setBranchNoOffset(java.lang.String branchNoOffset) {
		this.branchNoOffset = branchNoOffset;
	}


	public java.lang.String getBranchNoLen() {
		return branchNoLen;
	}


	public void setBranchNoLen(java.lang.String branchNoLen) {
		this.branchNoLen = branchNoLen;
	}


	public java.lang.String getInstCode() {
		return instCode;
	}


	public void setInstCode(java.lang.String instCode) {
		this.instCode = instCode;
	}


	public java.lang.String getCardName() {
		return cardName;
	}


	public void setCardName(java.lang.String cardName) {
		this.cardName = cardName;
	}


	public java.lang.String getDestSrvId() {
		return destSrvId;
	}


	public void setDestSrvId(java.lang.String destSrvId) {
		this.destSrvId = destSrvId;
	}


	public java.lang.String getTxnNum() {
		return txnNum;
	}


	public void setTxnNum(java.lang.String txnNum) {
		this.txnNum = txnNum;
	}


	public java.lang.String getCardType() {
		return cardType;
	}


	public void setCardType(java.lang.String cardType) {
		this.cardType = cardType;
	}

	public java.lang.String getModiOprId() {
		return modiOprId;
	}


	public void setModiOprId(java.lang.String modiOprId) {
		this.modiOprId = modiOprId;
	}


	public java.lang.String getModiTime() {
		return modiTime;
	}


	public void setModiTime(java.lang.String modiTime) {
		this.modiTime = modiTime;
	}


	public java.lang.String getInitOprId() {
		return initOprId;
	}


	public void setInitOprId(java.lang.String initOprId) {
		this.initOprId = initOprId;
	}


	public java.lang.String getInitTime() {
		return initTime;
	}


	public void setInitTime(java.lang.String initTime) {
		this.initTime = initTime;
	}


	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.allinfinance.po.TblCardRoute)) return false;
		else {
			com.allinfinance.po.TblCardRoute tblCardRoute = (com.allinfinance.po.TblCardRoute) obj;
			if (null == this.getId() || null == tblCardRoute.getId()) return false;
			else return (this.getId().equals(tblCardRoute.getId()));
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