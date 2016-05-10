package com.allinfinance.po.settle;

import java.io.Serializable;

public class TblPaySettleDtlPK implements Serializable {

	private static final long serialVersionUID = 1L;

    private String instDate;
    private String mchtNo;
    private String channelId;
    
    public TblPaySettleDtlPK() {
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

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	
    
}