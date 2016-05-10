package com.allinfinance.po.risk;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblRiskAlarm implements Serializable{

	
//	private String alarmId;
	private TblRiskAlarmPK tblRiskAlarmPk;
	private String riskDate;
	private String riskId;
	private String riskLvl;
	private String alarmLvl;
	private String alarmSta;
	private String cheatFlag;
	private String cheatTp;
	private String misc;
	private String misc1;
	
	public TblRiskAlarm() {

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


	


	public TblRiskAlarmPK getTblRiskAlarmPk() {
		return tblRiskAlarmPk;
	}


	public void setTblRiskAlarmPk(TblRiskAlarmPK tblRiskAlarmPk) {
		this.tblRiskAlarmPk = tblRiskAlarmPk;
	}


	public String getRiskDate() {
		return riskDate;
	}


	public void setRiskDate(String riskDate) {
		this.riskDate = riskDate;
	}


	public String getRiskId() {
		return riskId;
	}


	public void setRiskId(String riskId) {
		this.riskId = riskId;
	}


	public String getRiskLvl() {
		return riskLvl;
	}


	public void setRiskLvl(String riskLvl) {
		this.riskLvl = riskLvl;
	}


	public String getAlarmLvl() {
		return alarmLvl;
	}


	public void setAlarmLvl(String alarmLvl) {
		this.alarmLvl = alarmLvl;
	}


	public String getAlarmSta() {
		return alarmSta;
	}


	public void setAlarmSta(String alarmSta) {
		this.alarmSta = alarmSta;
	}


	public String getCheatFlag() {
		return cheatFlag;
	}


	public void setCheatFlag(String cheatFlag) {
		this.cheatFlag = cheatFlag;
	}


	public String getCheatTp() {
		return cheatTp;
	}


	public void setCheatTp(String cheatTp) {
		this.cheatTp = cheatTp;
	}

	
	
	
	
	
	
	
	
	
}
