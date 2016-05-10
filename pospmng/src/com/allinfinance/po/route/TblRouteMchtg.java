package com.allinfinance.po.route;

/**
 * TblRouteMchtg entity. @author MyEclipse Persistence Tools
 */

public class TblRouteMchtg implements java.io.Serializable {

	// Fields

	private Integer mchtGid;
	private String mchtGname;
	private String mchtGdsp;
	private String misc1;
	private String misc2;
	private String crtTime;
	private String crtOpr;
	private String uptTime;
	private String uptOpr;

	// Constructors

	/** default constructor */
	public TblRouteMchtg() {
	}

	/** minimal constructor */
	public TblRouteMchtg(Integer mchtGid, String mchtGname) {
		this.mchtGid = mchtGid;
		this.mchtGname = mchtGname;
	}

	/** full constructor */
	public TblRouteMchtg(Integer mchtGid, String mchtGname, String mchtGdsp,
			String misc1, String misc2, String crtTime, String crtOpr,
			String uptTime, String uptOpr) {
		this.mchtGid = mchtGid;
		this.mchtGname = mchtGname;
		this.mchtGdsp = mchtGdsp;
		this.misc1 = misc1;
		this.misc2 = misc2;
		this.crtTime = crtTime;
		this.crtOpr = crtOpr;
		this.uptTime = uptTime;
		this.uptOpr = uptOpr;
	}

	// Property accessors

	public Integer getMchtGid() {
		return this.mchtGid;
	}

	public void setMchtGid(Integer mchtGid) {
		this.mchtGid = mchtGid;
	}

	public String getMchtGname() {
		return this.mchtGname;
	}

	public void setMchtGname(String mchtGname) {
		this.mchtGname = mchtGname;
	}

	public String getMchtGdsp() {
		return this.mchtGdsp;
	}

	public void setMchtGdsp(String mchtGdsp) {
		this.mchtGdsp = mchtGdsp;
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