package com.allinfinance.po.route;

/**
 * TblUpbrhFee entity. @author MyEclipse Persistence Tools
 */

public class TblUpbrhFee implements java.io.Serializable {

	// Fields

	private String discId;
	private String discNm;
	private String brhId2;
	private String status;
	private String crtTime;
	private String crtOpr;
	private String uptTime;
	private String uptOpr;

	// Constructors

	/** default constructor */
	public TblUpbrhFee() {
	}

	/** full constructor */
	public TblUpbrhFee(String discId, String discNm, String brhId2,
			String status, String crtTime, String crtOpr, String uptTime,
			String uptOpr) {
		this.discId = discId;
		this.discNm = discNm;
		this.brhId2 = brhId2;
		this.status = status;
		this.crtTime = crtTime;
		this.crtOpr = crtOpr;
		this.uptTime = uptTime;
		this.uptOpr = uptOpr;
	}

	// Property accessors

	public String getDiscId() {
		return this.discId;
	}

	public void setDiscId(String discId) {
		this.discId = discId;
	}

	public String getDiscNm() {
		return this.discNm;
	}

	public void setDiscNm(String discNm) {
		this.discNm = discNm;
	}

	public String getBrhId2() {
		return this.brhId2;
	}

	public void setBrhId2(String brhId2) {
		this.brhId2 = brhId2;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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