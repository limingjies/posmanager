package com.allinfinance.po.risk;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblChkFreeze implements Serializable{

	
	private TblChkFreezePK tblChkFreezePK;
	
	private String keyInst;
	private String instCode;
	private String txnNum;
	private String transState;
	private String retrivlRef;
	private String instMchtId;
	private String instTermId;
	private String instRetrivlRef;
	private String cardAccpId;
	private String cardAccpTermId;
	private String termSn;
	private String respCode;
	private String amtTrans;
	private String amtFee;
	private String amtStlm;
	private String pan;
	private String freezeFlag;
	private String stlmFlag;
	private String reserved1;
	private String reserved2;
	private String instDate;
	private String lstUpdTm;
	
	
	public TblChkFreeze() {

	}


	public TblChkFreezePK getTblChkFreezePK() {
		return tblChkFreezePK;
	}


	public void setTblChkFreezePK(TblChkFreezePK tblChkFreezePK) {
		this.tblChkFreezePK = tblChkFreezePK;
	}


	public String getKeyInst() {
		return keyInst;
	}


	public void setKeyInst(String keyInst) {
		this.keyInst = keyInst;
	}


	public String getInstCode() {
		return instCode;
	}


	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}


	public String getTxnNum() {
		return txnNum;
	}


	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
	}


	public String getTransState() {
		return transState;
	}


	public void setTransState(String transState) {
		this.transState = transState;
	}


	public String getRetrivlRef() {
		return retrivlRef;
	}


	public void setRetrivlRef(String retrivlRef) {
		this.retrivlRef = retrivlRef;
	}


	public String getInstMchtId() {
		return instMchtId;
	}


	public void setInstMchtId(String instMchtId) {
		this.instMchtId = instMchtId;
	}


	public String getInstTermId() {
		return instTermId;
	}


	public void setInstTermId(String instTermId) {
		this.instTermId = instTermId;
	}


	public String getInstRetrivlRef() {
		return instRetrivlRef;
	}


	public void setInstRetrivlRef(String instRetrivlRef) {
		this.instRetrivlRef = instRetrivlRef;
	}


	public String getCardAccpId() {
		return cardAccpId;
	}


	public void setCardAccpId(String cardAccpId) {
		this.cardAccpId = cardAccpId;
	}


	public String getCardAccpTermId() {
		return cardAccpTermId;
	}


	public void setCardAccpTermId(String cardAccpTermId) {
		this.cardAccpTermId = cardAccpTermId;
	}


	public String getTermSn() {
		return termSn;
	}


	public void setTermSn(String termSn) {
		this.termSn = termSn;
	}


	public String getRespCode() {
		return respCode;
	}


	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}


	public String getAmtTrans() {
		return amtTrans;
	}


	public void setAmtTrans(String amtTrans) {
		this.amtTrans = amtTrans;
	}


	public String getAmtFee() {
		return amtFee;
	}


	public void setAmtFee(String amtFee) {
		this.amtFee = amtFee;
	}


	public String getAmtStlm() {
		return amtStlm;
	}


	public void setAmtStlm(String amtStlm) {
		this.amtStlm = amtStlm;
	}


	public String getPan() {
		return pan;
	}


	public void setPan(String pan) {
		this.pan = pan;
	}


	public String getFreezeFlag() {
		return freezeFlag;
	}


	public void setFreezeFlag(String freezeFlag) {
		this.freezeFlag = freezeFlag;
	}


	public String getStlmFlag() {
		return stlmFlag;
	}


	public void setStlmFlag(String stlmFlag) {
		this.stlmFlag = stlmFlag;
	}


	public String getReserved1() {
		return reserved1;
	}


	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}


	public String getReserved2() {
		return reserved2;
	}


	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}


	public String getInstDate() {
		return instDate;
	}


	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}


	public String getLstUpdTm() {
		return lstUpdTm;
	}


	public void setLstUpdTm(String lstUpdTm) {
		this.lstUpdTm = lstUpdTm;
	}
	
	
	
	
	
	
	
}
