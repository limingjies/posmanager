package com.allinfinance.po.route;

/**
 * TblUpbrhMcht entity. @author MyEclipse Persistence Tools
 */

public class TblUpbrhMcht implements java.io.Serializable {

	// Fields

	private TblUpbrhMchtPk pk;
	private String mchtNameUp;
	private String status;
	private String area;
	private String mchtId;
	private String runTime;
	private String stopTime;
	private String stopType;
	private String stopReason;
	private String zmk;
	private String pinKey;
	private String macKey;
	private String discId;
	private String mchtDsp;
	private String misc1;
	private String misc2;
	private String crtTime;
	private String crtOpr;
	private String uptTime;
	private String uptOpr;

	// Constructors

	/** default constructor */
	public TblUpbrhMcht() {
	}

	/** minimal constructor */
	public TblUpbrhMcht(TblUpbrhMchtPk pk, String mchtNameUp, String status,
			String crtTime, String crtOpr, String uptTime, String uptOpr) {
		this.pk = pk;
		this.mchtNameUp = mchtNameUp;
		this.status = status;
		this.crtTime = crtTime;
		this.crtOpr = crtOpr;
		this.uptTime = uptTime;
		this.uptOpr = uptOpr;
	}

	/** full constructor */
	public TblUpbrhMcht(TblUpbrhMchtPk pk, String mchtNameUp, String status,
			String area, String mchtId, String runTime, String stopTime,
			String stopType, String stopReason, String zmk, String pinKey,
			String macKey, String discId, String mchtDsp, String misc1, String misc2,
			String crtTime, String crtOpr, String uptTime, String uptOpr) {
		this.pk = pk;
		this.mchtNameUp = mchtNameUp;
		this.status = status;
		this.area = area;
		this.mchtId = mchtId;
		this.runTime = runTime;
		this.stopTime = stopTime;
		this.stopType = stopType;
		this.stopReason = stopReason;
		this.zmk = zmk;
		this.pinKey = pinKey;
		this.macKey = macKey;
		this.discId = discId;
		this.mchtDsp = mchtDsp;
		this.misc1 = misc1;
		this.misc2 = misc2;
		this.crtTime = crtTime;
		this.crtOpr = crtOpr;
		this.uptTime = uptTime;
		this.uptOpr = uptOpr;
		this.crtOpr = crtOpr;
		this.uptTime = uptTime;
		this.uptOpr = uptOpr;
	}

	// Property accessors


	public String getMchtNameUp() {
		return this.mchtNameUp;
	}

	public TblUpbrhMchtPk getPk() {
		return pk;
	}

	public void setPk(TblUpbrhMchtPk pk) {
		this.pk = pk;
	}

	public void setMchtNameUp(String mchtNameUp) {
		this.mchtNameUp = mchtNameUp;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getMchtId() {
		return this.mchtId;
	}

	public void setMchtId(String mchtId) {
		this.mchtId = mchtId;
	}

	public String getRunTime() {
		return this.runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

	public String getStopTime() {
		return this.stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

	public String getStopType() {
		return this.stopType;
	}

	public void setStopType(String stopType) {
		this.stopType = stopType;
	}

	public String getStopReason() {
		return this.stopReason;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}

	public String getZmk() {
		return this.zmk;
	}

	public void setZmk(String zmk) {
		this.zmk = zmk;
	}

	public String getPinKey() {
		return this.pinKey;
	}

	public void setPinKey(String pinKey) {
		this.pinKey = pinKey;
	}

	public String getMacKey() {
		return this.macKey;
	}

	public void setMacKey(String macKey) {
		this.macKey = macKey;
	}

	public String getDiscId() {
		return this.discId;
	}

	public void setDiscId(String discId) {
		this.discId = discId;
	}

	public String getMchtDsp() {
		return mchtDsp;
	}

	public void setMchtDsp(String mchtDsp) {
		this.mchtDsp = mchtDsp;
	}

	public String getMisc1() {
		return misc1;
	}

	public void setMisc1(String misc1) {
		this.misc1 = misc1;
	}

	public String getMisc2() {
		return misc2;
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