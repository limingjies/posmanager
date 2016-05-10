package com.allinfinance.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BthGcTxnSucc implements Serializable {

	// primary key
	private BthGcTxnSuccPK bthGcTxnSuccPK;

	// fields
	
	private java.lang.String instDate;
	private java.lang.String instTime;
	private java.lang.String pan;
	private java.lang.String cardAccpTermId;
	private java.lang.String cardAccpId;
	private java.lang.String txnNum;
	private java.lang.String amtTrans;
	private java.lang.String termSsn;
	private java.lang.String sysSeqNum;
	private java.lang.String revsalSsn;
	private java.lang.String retrivlRef;
	
	
	public BthGcTxnSucc() {
	}
	public java.lang.String getInstDate() {
		return instDate;
	}
	public void setInstDate(java.lang.String instDate) {
		this.instDate = instDate;
	}
	public java.lang.String getInstTime() {
		return instTime;
	}
	public void setInstTime(java.lang.String instTime) {
		this.instTime = instTime;
	}
	public java.lang.String getPan() {
		return pan;
	}
	public void setPan(java.lang.String pan) {
		this.pan = pan;
	}
	public java.lang.String getCardAccpTermId() {
		return cardAccpTermId;
	}
	public void setCardAccpTermId(java.lang.String cardAccpTermId) {
		this.cardAccpTermId = cardAccpTermId;
	}
	public java.lang.String getCardAccpId() {
		return cardAccpId;
	}
	public void setCardAccpId(java.lang.String cardAccpId) {
		this.cardAccpId = cardAccpId;
	}
	public java.lang.String getTxnNum() {
		return txnNum;
	}
	public void setTxnNum(java.lang.String txnNum) {
		this.txnNum = txnNum;
	}
	public java.lang.String getAmtTrans() {
		return amtTrans;
	}
	public void setAmtTrans(java.lang.String amtTrans) {
		this.amtTrans = amtTrans;
	}
	public java.lang.String getTermSsn() {
		return termSsn;
	}
	public void setTermSsn(java.lang.String termSsn) {
		this.termSsn = termSsn;
	}
	public java.lang.String getSysSeqNum() {
		return sysSeqNum;
	}
	public void setSysSeqNum(java.lang.String sysSeqNum) {
		this.sysSeqNum = sysSeqNum;
	}
	public java.lang.String getRevsalSsn() {
		return revsalSsn;
	}
	public void setRevsalSsn(java.lang.String revsalSsn) {
		this.revsalSsn = revsalSsn;
	}
	public java.lang.String getRetrivlRef() {
		return retrivlRef;
	}
	public void setRetrivlRef(java.lang.String retrivlRef) {
		this.retrivlRef = retrivlRef;
	}
	public BthGcTxnSuccPK getBthGcTxnSuccPK() {
		return bthGcTxnSuccPK;
	}
	public void setBthGcTxnSuccPK(BthGcTxnSuccPK bthGcTxnSuccPK) {
		this.bthGcTxnSuccPK = bthGcTxnSuccPK;
	}
	
	
	
	
	
	
}
