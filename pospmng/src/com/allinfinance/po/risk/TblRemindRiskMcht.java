package com.allinfinance.po.risk;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblRemindRiskMcht implements Serializable{

	
	private TblRemindRiskMchtPK tblRemindRiskMchtPK;
	private String mchtNo;
	private String riskLvl;
	private java.lang.Integer todayTimes;
	private java.lang.Integer totalTimes;
	private java.lang.Integer cheatTimes;
	private String crtOpr;
	private String crtTime;
	private String misc;
	private String misc1;
	
	public TblRemindRiskMcht() {

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



	
	public String getRiskLvl() {
		return riskLvl;
	}


	public void setRiskLvl(String riskLvl) {
		this.riskLvl = riskLvl;
	}




	public String getMchtNo() {
		return mchtNo;
	}


	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}
	
	
	
	public java.lang.Integer getTodayTimes() {
		return todayTimes;
	}


	public void setTodayTimes(java.lang.Integer todayTimes) {
		this.todayTimes = todayTimes;
	}


	public java.lang.Integer getTotalTimes() {
		return totalTimes;
	}


	public void setTotalTimes(java.lang.Integer totalTimes) {
		this.totalTimes = totalTimes;
	}


	public java.lang.Integer getCheatTimes() {
		return cheatTimes;
	}


	public void setCheatTimes(java.lang.Integer cheatTimes) {
		this.cheatTimes = cheatTimes;
	}


	public String getCrtOpr() {
		return crtOpr;
	}


	public void setCrtOpr(String crtOpr) {
		this.crtOpr = crtOpr;
	}


	public String getCrtTime() {
		return crtTime;
	}


	public void setCrtTime(String crtTime) {
		this.crtTime = crtTime;
	}


	public TblRemindRiskMchtPK getTblRemindRiskMchtPK() {
		return tblRemindRiskMchtPK;
	}


	public void setTblRemindRiskMchtPK(TblRemindRiskMchtPK tblRemindRiskMchtPK) {
		this.tblRemindRiskMchtPK = tblRemindRiskMchtPK;
	}


	

	
	
}
