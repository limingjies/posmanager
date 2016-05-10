package com.allinfinance.po.mchnt;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblHolderCardIdPK implements Serializable{

	private String ACT_NO;
	private String CARD_ID;
	
	public TblHolderCardIdPK() {
		
	}

	public TblHolderCardIdPK(String act_no, String card_id) {
		ACT_NO = act_no;
		CARD_ID = card_id;
	}

	public String getACT_NO() {
		return ACT_NO;
	}

	public void setACT_NO(String act_no) {
		ACT_NO = act_no;
	}

	public String getCARD_ID() {
		return CARD_ID;
	}

	public void setCARD_ID(String card_id) {
		CARD_ID = card_id;
	}

	
	
	
}
