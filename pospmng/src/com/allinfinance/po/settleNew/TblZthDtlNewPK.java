package com.allinfinance.po.settleNew;

import java.io.Serializable;

public class TblZthDtlNewPK implements Serializable {

	private static final long serialVersionUID = 1L;

    private String instDate;
    private String sysSeqNum;
    private String settlmtDate;
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
	
	public String getSettlmtDate() {
		return settlmtDate;
	}
	public void setSettlmtDate(String settlmtDate) {
		this.settlmtDate = settlmtDate;
	}
	public TblZthDtlNewPK() {
//		super();
	}

    
}