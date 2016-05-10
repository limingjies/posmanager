package com.allinfinance.po.mchnt;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblHolderCardId implements Serializable{

	private TblHolderCardIdPK id;
	private String CARD_TYPE;
	
	public TblHolderCardId() {
	}

	public TblHolderCardId(TblHolderCardIdPK id, String card_type) {
		this.id = id;
		CARD_TYPE = card_type;
	}

	public TblHolderCardIdPK getId() {
		return id;
	}

	public void setId(TblHolderCardIdPK id) {
		this.id = id;
	}

	public String getCARD_TYPE() {
		return CARD_TYPE;
	}

	public void setCARD_TYPE(String card_type) {
		CARD_TYPE = card_type;
	}

	
	
	


	
}
