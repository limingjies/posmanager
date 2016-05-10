package com.allinfinance.po.base;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblBrhFeeCfgPK implements Serializable{

	
	
	private String discId;
	private int seq;
	
	
	public TblBrhFeeCfgPK() {
		
	}


	public String getDiscId() {
		return discId;
	}


	public void setDiscId(String discId) {
		this.discId = discId;
	}


	public int getSeq() {
		return seq;
	}


	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	
}
