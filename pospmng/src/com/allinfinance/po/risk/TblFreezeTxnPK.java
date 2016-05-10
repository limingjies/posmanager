package com.allinfinance.po.risk;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblFreezeTxnPK implements Serializable{

	private String instDate;
	private String sysSeqNum;
	
	public TblFreezeTxnPK() {
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


	
	
	

	
	
}
