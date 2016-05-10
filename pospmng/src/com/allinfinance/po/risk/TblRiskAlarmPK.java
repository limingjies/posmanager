package com.allinfinance.po.risk;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblRiskAlarmPK implements Serializable{

	
	private String alarmId;
	private Integer alarmSeq;
	public TblRiskAlarmPK() {
//		super();
	}
	public String getAlarmId() {
		return alarmId;
	}
	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}
	public Integer getAlarmSeq() {
		return alarmSeq;
	}
	public void setAlarmSeq(Integer alarmSeq) {
		this.alarmSeq = alarmSeq;
	}
	
	
	
	
	
	
	
	
	
}
