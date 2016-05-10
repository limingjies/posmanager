package com.allinfinance.po.risk;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblAlarmTxn implements Serializable{

	
	private TblAlarmTxnPK tblAlarmTxnPk;
	private String cardAccpId;
	private String pan;
	private String cardAccpTermId;
	private String txnNum;
	private String amtTrans;
	private String transState;
	private String rspCode;
	private String cheatTp;
	private String cheatFlag;
	private String cautionFlag;
	private String receiptFlag;
	private String blockFlag;
	private String misc;
	private String misc1;
	
	public TblAlarmTxn() {

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

	

	public String getCautionFlag() {
		return cautionFlag;
	}

	public void setCautionFlag(String cautionFlag) {
		this.cautionFlag = cautionFlag;
	}


	public TblAlarmTxnPK getTblAlarmTxnPk() {
		return tblAlarmTxnPk;
	}


	public void setTblAlarmTxnPk(TblAlarmTxnPK tblAlarmTxnPk) {
		this.tblAlarmTxnPk = tblAlarmTxnPk;
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


	public String getCheatTp() {
		return cheatTp;
	}


	public void setCheatTp(String cheatTp) {
		this.cheatTp = cheatTp;
	}


	public String getCheatFlag() {
		return cheatFlag;
	}


	public void setCheatFlag(String cheatFlag) {
		this.cheatFlag = cheatFlag;
	}


	public String getReceiptFlag() {
		return receiptFlag;
	}


	public void setReceiptFlag(String receiptFlag) {
		this.receiptFlag = receiptFlag;
	}


	public String getBlockFlag() {
		return blockFlag;
	}


	public void setBlockFlag(String blockFlag) {
		this.blockFlag = blockFlag;
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


	public String getAmtTrans() {
		return amtTrans;
	}


	public void setAmtTrans(String amtTrans) {
		this.amtTrans = amtTrans;
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
