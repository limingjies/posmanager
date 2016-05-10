package com.allinfinance.po.base;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblBrhFeeCtl implements Serializable{

	
	
	private String discId;
	private String discName;
	private String lstUpdTime;
	private String lstUpdTlr;
	private String createTime;
	
	
	public TblBrhFeeCtl() {
		
	}


	public String getDiscId() {
		return discId;
	}


	public void setDiscId(String discId) {
		this.discId = discId;
	}


	public String getDiscName() {
		return discName;
	}


	public void setDiscName(String discName) {
		this.discName = discName;
	}


	public String getLstUpdTime() {
		return lstUpdTime;
	}


	public void setLstUpdTime(String lstUpdTime) {
		this.lstUpdTime = lstUpdTime;
	}


	public String getLstUpdTlr() {
		return lstUpdTlr;
	}


	public void setLstUpdTlr(String lstUpdTlr) {
		this.lstUpdTlr = lstUpdTlr;
	}


	public String getCreateTime() {
		return createTime;
	}


	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	
}
