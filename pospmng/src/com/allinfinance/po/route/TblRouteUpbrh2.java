package com.allinfinance.po.route;

/**
 * TblRouteUpbrh2 entity. @author MyEclipse Persistence Tools
 */

public class TblRouteUpbrh2 implements java.io.Serializable {

	// Fields

	private String brhId2;
	private String suptTxn;
	private String suptArea;
	private String encType;
	private String brhKey;
	private String pinKey;
	private String macKey;
	private String destId;
	private String isfee;
	private String misc1;
	private String misc2;
	private String crtTime;
	private String crtOpr;
	private String uptTime;
	private String uptOpr;

	// Constructors

	/** default constructor */
	public TblRouteUpbrh2() {
	}

	/** minimal constructor */
	public TblRouteUpbrh2(String brhId2, String destId, String crtTime,
			String crtOpr, String uptTime, String uptOpr) {
		this.brhId2 = brhId2;
		this.destId = destId;
		this.crtTime = crtTime;
		this.crtOpr = crtOpr;
		this.uptTime = uptTime;
		this.uptOpr = uptOpr;
	}

	/** full constructor */
	public TblRouteUpbrh2(String brhId2, String suptTxn, String suptArea,
			String encType, String brhKey, String pinKey, String macKey,
			String destId, String isfee, String misc1, String misc2,
			String crtTime, String crtOpr, String uptTime, String uptOpr) {
		this.brhId2 = brhId2;
		this.suptTxn = suptTxn;
		this.suptArea = suptArea;
		this.encType = encType;
		this.brhKey = brhKey;
		this.pinKey = pinKey;
		this.macKey = macKey;
		this.destId = destId;
		this.isfee = isfee;
		this.misc1 = misc1;
		this.misc2 = misc2;
		this.crtTime = crtTime;
		this.crtOpr = crtOpr;
		this.uptTime = uptTime;
		this.uptOpr = uptOpr;
	}

	// Property accessors

	public String getBrhId2() {
		return this.brhId2;
	}

	public void setBrhId2(String brhId2) {
		this.brhId2 = brhId2;
	}

	public String getSuptTxn() {
		return this.suptTxn;
	}

	public void setSuptTxn(String suptTxn) {
		this.suptTxn = suptTxn;
	}

	public String getSuptArea() {
		return this.suptArea;
	}

	public void setSuptArea(String suptArea) {
		this.suptArea = suptArea;
	}

	public String getEncType() {
		return this.encType;
	}

	public void setEncType(String encType) {
		this.encType = encType;
	}

	public String getBrhKey() {
		return this.brhKey;
	}

	public void setBrhKey(String brhKey) {
		this.brhKey = brhKey;
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

	public String getDestId() {
		return this.destId;
	}

	public void setDestId(String destId) {
		this.destId = destId;
	}

	public String getIsfee() {
		return this.isfee;
	}

	public void setIsfee(String isfee) {
		this.isfee = isfee;
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