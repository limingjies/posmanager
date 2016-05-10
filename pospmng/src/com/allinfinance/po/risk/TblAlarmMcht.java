package com.allinfinance.po.risk;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblAlarmMcht implements Serializable{

	
	private TblAlarmMchtPK tblAlarmMchtPk;
	private java.lang.Integer alarmNum;
	private String cautionFlag;
	private String blockSettleFlag;
	private String blockMchtFlag;
	private String misc;
	private String misc1;
	
	public TblAlarmMcht() {

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

	

	public TblAlarmMchtPK getTblAlarmMchtPk() {
		return tblAlarmMchtPk;
	}

	public void setTblAlarmMchtPk(TblAlarmMchtPK tblAlarmMchtPk) {
		this.tblAlarmMchtPk = tblAlarmMchtPk;
	}

	public String getCautionFlag() {
		return cautionFlag;
	}

	public void setCautionFlag(String cautionFlag) {
		this.cautionFlag = cautionFlag;
	}

	public String getBlockSettleFlag() {
		return blockSettleFlag;
	}

	public void setBlockSettleFlag(String blockSettleFlag) {
		this.blockSettleFlag = blockSettleFlag;
	}

	public String getBlockMchtFlag() {
		return blockMchtFlag;
	}

	public void setBlockMchtFlag(String blockMchtFlag) {
		this.blockMchtFlag = blockMchtFlag;
	}
	
	
	
	
	
	
	
}
