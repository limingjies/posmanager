package com.allinfinance.po.route;

/**
 * TblRouteUpbrh entity. @author MyEclipse Persistence Tools
 */

public class TblRouteUpbrh implements java.io.Serializable {

	// Fields

	private String brhId;
	private String brhLevel;
	private String status;
	private String name;
	private String brhDsp;
	private String misc1;
	private String misc2;
	private String useTime;
	private String crtTime;
	private String crtOpr;
	private String uptTime;
	private String uptOpr;

	// Constructors

	/** default constructor */
	public TblRouteUpbrh() {
	}

	/** minimal constructor */
	public TblRouteUpbrh(String brhId, String brhLevel, String status,
			String name, String useTime, String crtTime, String crtOpr,
			String uptTime, String uptOpr) {
		this.brhId = brhId;
		this.brhLevel = brhLevel;
		this.status = status;
		this.name = name;
		this.useTime = useTime;
		this.crtTime = crtTime;
		this.crtOpr = crtOpr;
		this.uptTime = uptTime;
		this.uptOpr = uptOpr;
	}

	/** full constructor */
	public TblRouteUpbrh(String brhId, String brhLevel, String status,
			String name, String brhDsp, String misc1, String misc2,
			String useTime, String crtTime, String crtOpr, String uptTime,
			String uptOpr) {
		this.brhId = brhId;
		this.brhLevel = brhLevel;
		this.status = status;
		this.name = name;
		this.brhDsp = brhDsp;
		this.misc1 = misc1;
		this.misc2 = misc2;
		this.useTime = useTime;
		this.crtTime = crtTime;
		this.crtOpr = crtOpr;
		this.uptTime = uptTime;
		this.uptOpr = uptOpr;
	}

	// Property accessors

	public String getBrhId() {
		return this.brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	public String getBrhLevel() {
		return this.brhLevel;
	}

	public void setBrhLevel(String brhLevel) {
		this.brhLevel = brhLevel;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrhDsp() {
		return this.brhDsp;
	}

	public void setBrhDsp(String brhDsp) {
		this.brhDsp = brhDsp;
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

	public String getUseTime() {
		return this.useTime;
	}

	public void setUseTime(String useTime) {
		this.useTime = useTime;
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