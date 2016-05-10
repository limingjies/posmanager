package com.allinfinance.po.risk;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblChkFreezePK implements Serializable{

	private String txnDt;
	private String sysSeqNum;
	
	public TblChkFreezePK() {
	}

	

	public String getTxnDt() {
		return txnDt;
	}



	public void setTxnDt(String txnDt) {
		this.txnDt = txnDt;
	}



	public String getSysSeqNum() {
		return sysSeqNum;
	}

	public void setSysSeqNum(String sysSeqNum) {
		this.sysSeqNum = sysSeqNum;
	}


	
	
	

	
	
}
