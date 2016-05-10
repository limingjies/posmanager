package com.allinfinance.po.route;

/**
 * TblRouteRuleMap entity. @author MyEclipse Persistence Tools
 */

public class TblRouteRuleMap implements java.io.Serializable {

	// Fields

	private Integer ruleId;
	private String mchtId;
	private String brhId3;
	private String mchtIdUp;
	private String misc1;
	private String misc2;
	private String crtTime;
	private String crtOpr;
	private String uptTime;
	private String uptOpr;

	// Constructors

	/** default constructor */
	public TblRouteRuleMap() {
	}

	/** minimal constructor */
	public TblRouteRuleMap(Integer ruleId, String mchtId, String brhId3,
			String mchtIdUp, String crtTime, String crtOpr, String uptTime,
			String uptOpr) {
		this.ruleId = ruleId;
		this.mchtId = mchtId;
		this.brhId3 = brhId3;
		this.mchtIdUp = mchtIdUp;
		this.crtTime = crtTime;
		this.crtOpr = crtOpr;
		this.uptTime = uptTime;
		this.uptOpr = uptOpr;
	}

	/** full constructor */
	public TblRouteRuleMap(Integer ruleId, String mchtId, String brhId3,
			String mchtIdUp, String misc1, String misc2, String crtTime,
			String crtOpr, String uptTime, String uptOpr) {
		this.ruleId = ruleId;
		this.mchtId = mchtId;
		this.brhId3 = brhId3;
		this.mchtIdUp = mchtIdUp;
		this.misc1 = misc1;
		this.misc2 = misc2;
		this.crtTime = crtTime;
		this.crtOpr = crtOpr;
		this.uptTime = uptTime;
		this.uptOpr = uptOpr;
	}

	// Property accessors

	public Integer getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public String getMchtId() {
		return this.mchtId;
	}

	public void setMchtId(String mchtId) {
		this.mchtId = mchtId;
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