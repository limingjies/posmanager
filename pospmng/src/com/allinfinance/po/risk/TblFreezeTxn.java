package com.allinfinance.po.risk;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class TblFreezeTxn implements Serializable{

	
	private TblFreezeTxnPK tblFreezeTxnPK;
	private String cardAccpId;
	private String pan;
	private String cardAccpTermId;
	private String txnNum;
	private BigDecimal amtTrans;
	private String transState;
	private String rspCode;
	private String freezeFlag;
	private BigDecimal fee;
	private String crtOpr;
	private String crtTime;
	private String misc;
	private String misc1;
	
	public TblFreezeTxn() {

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

	

	public String getCardAccpId() {
		return cardAccpId;
	}


	public void setCardAccpId(String cardAccpId) {
		this.cardAccpId = cardAccpId;
	}


	public String getPan() {
		return pan;
	}


	public void setPan(String pan) {
		this.pan = pan;
	}




	public String getCardAccpTermId() {
		return cardAccpTermId;
	}


	public void setCardAccpTermId(String cardAccpTermId) {
		this.cardAccpTermId = cardAccpTermId;
	}


	public String getTxnNum() {
		return txnNum;
	}


	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
	}



	

	public TblFreezeTxnPK getTblFreezeTxnPK() {
		return tblFreezeTxnPK;
	}


	public void setTblFreezeTxnPK(TblFreezeTxnPK tblFreezeTxnPK) {
		this.tblFreezeTxnPK = tblFreezeTxnPK;
	}


	public BigDecimal getAmtTrans() {
		return amtTrans;
	}


	public void setAmtTrans(BigDecimal amtTrans) {
		this.amtTrans = amtTrans;
	}


	public String getFreezeFlag() {
		return freezeFlag;
	}


	public void setFreezeFlag(String freezeFlag) {
		this.freezeFlag = freezeFlag;
	}


	public BigDecimal getFee() {
		return fee;
	}


	public void setFee(BigDecimal fee) {
		this.fee = fee;
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


	public String getTransState() {
		return transState;
	}


	public void setTransState(String transState) {
		this.transState = transState;
	}


	public String getRspCode() {
		return rspCode;
	}


	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}

	
	
	
	
	
	
	
}
