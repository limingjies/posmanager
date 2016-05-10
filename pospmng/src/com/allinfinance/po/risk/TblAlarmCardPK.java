package com.allinfinance.po.risk;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblAlarmCardPK implements Serializable{

	private String alarmId;
	private Integer alarmSeq;
	private String pan;
	
	public TblAlarmCardPK() {
	}

	public String getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public Integer getAlarmSeq() {
		return alarmSeq;
	}

	public void setAlarmSeq(Integer alarmSeq) {
		this.alarmSeq = alarmSeq;
	}

	
	
	
}
