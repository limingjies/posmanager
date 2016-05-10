package com.allinfinance.po.settle;

import java.io.Serializable;

public class TblPaySettleDtlTmpPK implements Serializable {

	private static final long serialVersionUID = 1L;

    private String instDate;
    private String mchtNo;
	
    public TblPaySettleDtlTmpPK() {
//		super();
	}

	public String getInstDate() {
		return instDate;
	}

	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

    
}