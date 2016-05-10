package com.allinfinance.po.route;

/**
 * TblRouteRuleMapHist entity. @author MyEclipse Persistence Tools
 */

public class TblRouteRuleMapHist implements java.io.Serializable {

	// Fields

	private TblRouteRuleMapHistPk pk;
	private String status;
	private String mchtId;
	private String mchtName;
	private Integer mchtgId;
	private String brhId3;
	private String mchtIdUp;
	private String misc1;
	private String misc2;
	private String runTime;
	private String crtTime;
	private String crtOpr;
	private String uptTime;
	private String uptOpr;

	// Constructors

	/** default constructor */
	public TblRouteRuleMapHist() {
	}

	/** minimal constructor */
	public TblRouteRuleMapHist(TblRouteRuleMapHistPk pk) {
		this.pk = pk;
	}

	/** full constructor */
	public TblRouteRuleMapHist(TblRouteRuleMapHistPk pk, String status,
			String mchtId, String mchtName, Integer mchtgId, String brhId3,
			String mchtIdUp, String misc1, String misc2, String runTime,
			String crtTime, String crtOpr, String uptTime, String uptOpr) {
		this.pk = pk;
		this.status = status;
		this.mchtId = mchtId;
		this.mchtName = mchtName;
		this.mchtgId = mchtgId;
		this.brhId3 = brhId3;
		this.mchtIdUp = mchtIdUp;
		this.misc1 = misc1;
		this.misc2 = misc2;
		this.runTime = runTime;
		this.crtTime = crtTime;
		this.crtOpr = crtOpr;
		this.uptTime = uptTime;
		this.uptOpr = uptOpr;
	}

	// Property accessors

	public String getStatus() {
		return this.status;
	}

	public TblRouteRuleMapHistPk getPk() {
		return pk;
	}

	public void setPk(TblRouteRuleMapHistPk pk) {
		this.pk = pk;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMchtId() {
		return this.mchtId;
	}

	public void setMchtId(String mchtId) {
		this.mchtId = mchtId;
	}

	public String getMchtName() {
		return this.mchtName;
	}

	public void setMchtName(String mchtName) {
		this.mchtName = mchtName;
	}

	public Integer getMchtgId() {
		return this.mchtgId;
	}

	public void setMchtgId(Integer mchtgId) {
		this.mchtgId = mchtgId;
	}

	public String getBrhId3() {
		return this.brhId3;
	}

	public void setBrhId3(String brhId3) {
		this.brhId3 = brhId3;
	}

	public String getMchtIdUp() {
		return this.mchtIdUp;
	}

	public void setMchtIdUp(String mchtIdUp) {
		this.mchtIdUp = mchtIdUp;
	}

	public String getMisc1() {
		return this.misc1;
	}

	public void setMisc1(String misc1) {
		this.misc1 = misc1;
	}

	public String getMisc2() {
		return this.misc2;
	}

	public void setMisc2(String misc2) {
		this.misc2 = misc2;
	}

	public String getRunTime() {
		return this.runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

	public String getCrtTime() {
		return this.crtTime;
	}

	public void setCrtTime(String crtTime) {
		this.crtTime = crtTime;
	}

	public String getCrtOpr() {
		return this.crtOpr;
	}

	public void setCrtOpr(String crtOpr) {
		this.crtOpr = crtOpr;
	}

	public String getUptTime() {
		return this.uptTime;
	}

	public void setUptTime(String uptTime) {
		this.uptTime = uptTime;
	}

	public String getUptOpr() {
		return this.uptOpr;
	}

	public void setUptOpr(String uptOpr) {
		this.uptOpr = uptOpr;
	}

}