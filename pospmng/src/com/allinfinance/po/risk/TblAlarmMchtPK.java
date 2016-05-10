package com.allinfinance.po.risk;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblAlarmMchtPK implements Serializable{

	private String alarmId;
	private Integer alarmSeq;
	private String cardAccpId;
	
	public TblAlarmMchtPK() {
	}

	public String getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}

	public String getCardAccpId() {
		return cardAccpId;
	}

	public void setCardAccpId(String cardAccpId) {
		this.cardAccpId = cardAccpId;
	}

	public Integer getAlarmSeq() {
		return alarmSeq;
	}

	public void setAlarmSeq(Integer alarmSeq) {
		this.alarmSeq = alarmSeq;
	}

	

	
	
}
