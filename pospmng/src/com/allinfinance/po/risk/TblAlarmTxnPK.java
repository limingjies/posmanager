package com.allinfinance.po.risk;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblAlarmTxnPK implements Serializable{

	private String alarmId;
	private Integer alarmSeq;
	private String instDate;
	private String sysSeqNum;
	
	public TblAlarmTxnPK() {
	}

	public String getInstDate() {
		return instDate;
	}

	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}

	public String getSysSeqNum() {
		return sysSeqNum;
	}

	public void setSysSeqNum(String sysSeqNum) {
		this.sysSeqNum = sysSeqNum;
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
