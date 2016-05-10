package com.allinfinance.po.mchnt;

/**
 * TblCashRateInf entity. @author MyEclipse Persistence Tools
 */

public class TblCashRateInf implements java.io.Serializable {

	// Fields

	private String rateId;
	private String name;
	private String type;
	private Double rate;
	private String crtTime;
	private String crtOpr;
	private String uptTime;
	private String uptOpr;

	// Constructors

	/** default constructor */
	public TblCashRateInf() {
	}

	/** minimal constructor */
	public TblCashRateInf(String rateId) {
		this.rateId = rateId;
	}

	/** full constructor */
	public TblCashRateInf(String rateId, String name, String type, Double rate,
			String crtTime, String crtOpr, String uptTime, String uptOpr) {
		this.rateId = rateId;
		this.name = name;
		this.type = type;
		this.rate = rate;
		this.crtTime = crtTime;
		this.crtOpr = crtOpr;
		this.uptTime = uptTime;
		this.uptOpr = uptOpr;
	}

	// Property accessors

	public String getRateId() {
		return this.rateId;
	}

	public void setRateId(String rateId) {
		this.rateId = rateId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
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