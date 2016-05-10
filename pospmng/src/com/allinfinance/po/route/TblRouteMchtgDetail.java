package com.allinfinance.po.route;

/**
 * TblRouteMchtgDetail entity. @author MyEclipse Persistence Tools
 */

public class TblRouteMchtgDetail implements java.io.Serializable {

	// Fields

	private TblRouteMchtgDetailPk pk;
	private String runTime;
	private String flag1;
	private String flag2;
	private String misc1;
	private String misc2;
	private String crtTime;
	private String crtOpr;
	private String uptTime;
	private String uptOpr;

	// Constructors

	/** default constructor */
	public TblRouteMchtgDetail() {
	}

	/** minimal constructor */
	public TblRouteMchtgDetail(TblRouteMchtgDetailPk pk) {
		this.pk = pk;
	}

	/** full constructor */
	public TblRouteMchtgDetail(TblRouteMchtgDetailPk pk, String runTime,
			String flag1, String flag2, String misc1, String misc2,
			String crtTime, String crtOpr, String uptTime, String uptOpr) {
		this.pk = pk;
		this.runTime = runTime;
		this.flag1 = flag1;
		this.flag2 = flag2;
		this.misc1 = misc1;
		this.misc2 = misc2;
		this.crtTime = crtTime;
		this.crtOpr = crtOpr;
		this.uptTime = uptTime;
		this.uptOpr = uptOpr;
	}

	// Property accessors


	public String getRunTime() {
		return this.runTime;
	}

	public TblRouteMchtgDetailPk getPk() {
		return pk;
	}

	public void setPk(TblRouteMchtgDetailPk pk) {
		this.pk = pk;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

	public String getFlag1() {
		return this.flag1;
	}

	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}

	public String getFlag2() {
		return this.flag2;
	}

	public void setFlag2(String flag2) {
		this.flag2 = flag2;
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