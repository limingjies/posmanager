package com.allinfinance.po.base;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblBrhSettleInf implements Serializable{

	private String brhId;
	private String settleFlag;
	private String settleBankNo;
	private String settleBankNm;
	private String settleAcctNm;
	private String settleAcct;
	private String settleAcctMid;
	private String settleAcctMidNm;
	private String crtOpr;
	private String crtTs;
	private String updOpr;
	private String updTs;
	private String misc;
	private String misc1;
	
	public TblBrhSettleInf() {
//		super();
	}

	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	public String getSettleFlag() {
		return settleFlag;
	}

	public void setSettleFlag(String settleFlag) {
		this.settleFlag = settleFlag;
	}

	public String getSettleBankNo() {
		return settleBankNo;
	}

	public void setSettleBankNo(String settleBankNo) {
		this.settleBankNo = settleBankNo;
	}

	public String getSettleBankNm() {
		return settleBankNm;
	}

	public void setSettleBankNm(String settleBankNm) {
		this.settleBankNm = settleBankNm;
	}

	public String getSettleAcctNm() {
		return settleAcctNm;
	}

	public void setSettleAcctNm(String settleAcctNm) {
		this.settleAcctNm = settleAcctNm;
	}

	public String getSettleAcct() {
		return settleAcct;
	}

	public void setSettleAcct(String settleAcct) {
		this.settleAcct = settleAcct;
	}

	public String getSettleAcctMid() {
		return settleAcctMid;
	}

	public void setSettleAcctMid(String settleAcctMid) {
		this.settleAcctMid = settleAcctMid;
	}

	public String getSettleAcctMidNm() {
		return settleAcctMidNm;
	}

	public void setSettleAcctMidNm(String settleAcctMidNm) {
		this.settleAcctMidNm = settleAcctMidNm;
	}

	public String getCrtOpr() {
		return crtOpr;
	}

	public void setCrtOpr(String crtOpr) {
		this.crtOpr = crtOpr;
	}

	public String getCrtTs() {
		return crtTs;
	}

	public void setCrtTs(String crtTs) {
		this.crtTs = crtTs;
	}

	public String getUpdOpr() {
		return updOpr;
	}

	public void setUpdOpr(String updOpr) {
		this.updOpr = updOpr;
	}

	public String getUpdTs() {
		return updTs;
	}

	public void setUpdTs(String updTs) {
		this.updTs = updTs;
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
	
	
	
	
}
