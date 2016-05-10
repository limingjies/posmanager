package com.allinfinance.po.mchnt;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblHoldMchtParticipatPK implements Serializable{

	private String MCHT_NO;
	private String ACT_NO;
	
	public TblHoldMchtParticipatPK(String mcht_no, String act_no) {
		MCHT_NO = mcht_no;
		ACT_NO = act_no;
	}

	public TblHoldMchtParticipatPK() {
	}

	public String getMCHT_NO() {
		return MCHT_NO;
	}

	public void setMCHT_NO(String mcht_no) {
		MCHT_NO = mcht_no;
	}

	public String getACT_NO() {
		return ACT_NO;
	}

	public void setACT_NO(String act_no) {
		ACT_NO = act_no;
	}


	
	
}
