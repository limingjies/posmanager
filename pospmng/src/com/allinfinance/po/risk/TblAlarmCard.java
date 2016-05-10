package com.allinfinance.po.risk;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblAlarmCard implements Serializable{

	
	private TblAlarmCardPK tblAlarmCardPk;
	private java.lang.Integer alarmNum;
	private String misc;
	private String misc1;
	public TblAlarmCard() {

	}
	public TblAlarmCardPK getTblAlarmCardPk() {
		return tblAlarmCardPk;
	}
	public void setTblAlarmCardPk(TblAlarmCardPK tblAlarmCardPk) {
		this.tblAlarmCardPk = tblAlarmCardPk;
	}
	
	
	public java.lang.Integer getAlarmNum() {
		return alarmNum;
	}
	public void setAlarmNum(java.lang.Integer alarmNum) {
		this.alarmNum = alarmNum;
	}
	public String getMisc() {
		return misc;
	}
	public void setMisc(String misc) {
		this.misc = misc;
	}
	public String getMisc1() {
		return misc1;
	}
	public void setMisc1(String misc1) {
		this.misc1 = misc1;
	}
	
	
	
	
	
	
}
