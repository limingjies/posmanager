package com.allinfinance.po.mchnt;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblMchtNamePK implements Serializable{

	private java.lang.String accpId;
	private java.lang.String cardTp;
	private java.lang.String txnNum;
	
	public TblMchtNamePK() {
	}

	public java.lang.String getAccpId() {
		return accpId;
	}

	public void setAccpId(java.lang.String accpId) {
		this.accpId = accpId;
	}

	public java.lang.String getCardTp() {
		return cardTp;
	}

	public void setCardTp(java.lang.String cardTp) {
		this.cardTp = cardTp;
	}

	public java.lang.String getTxnNum() {
		return txnNum;
	}

	public void setTxnNum(java.lang.String txnNum) {
		this.txnNum = txnNum;
	}
	
	
	
}
