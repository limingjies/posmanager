package com.allinfinance.po.settleNew;

import java.io.Serializable;

public class TblSettleDtlPK implements Serializable {

	private static final long serialVersionUID = 1L;

	   
    private String dateStlm;
    private String mchtId;
    private String channelId;
    
    public TblSettleDtlPK() {
//		super();
	}

	
	public String getDateStlm() {
		return dateStlm;
	}
	public void setDateStlm(String dateStlm) {
		this.dateStlm = dateStlm;
	}
	public String getMchtId() {
		return mchtId;
	}
	public void setMchtId(String mchtId) {
		this.mchtId = mchtId;
	}
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	
    
}