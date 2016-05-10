package com.allinfinance.po.risk;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblRemindRiskTxn implements Serializable{

	
	private TblRemindRiskTxnPK tblRemindRiskTxnPK;
	private String mchtNo;
	private String instDate;
	private String sysSeqNum;
	private String pan;
	private String crtOpr;
	private String crtTime;
	private String misc;
	private String misc1;
	
	public TblRemindRiskTxn() {

	}
	
	
	public String getMisc() {
		return misc;
	}
	public void setMisc(String misc) {
		this.misc = misc;
	}
	public String getMisc1() {
		return misc1;
	}
	public void setMisc1(String misc1) {
		this.misc1 = misc1;
	}


	public String getMchtNo() {
		return mchtNo;
	}


	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}


	public String getCrtOpr() {
		return crtOpr;
	}


	public void setCrtOpr(String crtOpr) {
		this.crtOpr = crtOpr;
	}


	public String getCrtTime() {
		return crtTime;
	}


	public void setCrtTime(String crtTime) {
		this.crtTime = crtTime;
	}



	public String getInstDate() {
		return instDate;
	}


	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}


	public String getSysSeqNum() {
		return sysSeqNum;
	}


	public void setSysSeqNum(String sysSeqNum) {
		this.sysSeqNum = sysSeqNum;
	}


	public String getPan() {
		return pan;
	}


	public void setPan(String pan) {
		this.pan = pan;
	}


	public TblRemindRiskTxnPK getTblRemindRiskTxnPK() {
		return tblRemindRiskTxnPK;
	}


	public void setTblRemindRiskTxnPK(TblRemindRiskTxnPK tblRemindRiskTxnPK) {
		this.tblRemindRiskTxnPK = tblRemindRiskTxnPK;
	}


	
	

	
	
}
