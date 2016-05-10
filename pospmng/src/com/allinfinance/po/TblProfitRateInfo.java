package com.allinfinance.po;

/**
 * TblProfitRateInfo entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class TblProfitRateInfo implements java.io.Serializable {

	// Fields

	private String rateId;
	private String feeName;
	private String feeType;
	private Double feeRate;
	private Double amt1;
	private Double amt2;
	private String misc;
	private String crtTime;
	private String crtOpr;
	private String uptTime;
	private String uptOpr;

	// Constructors

	/** default constructor */
	public TblProfitRateInfo() {
	}

	/** minimal constructor */
	public TblProfitRateInfo(String rateId) {
		this.rateId = rateId;
	}

	/** full constructor */
	public TblProfitRateInfo(String rateId, String feeName, String feeType,
			Double feeRate, Double amt1, Double amt2, String misc,
			String crtTime, String crtOpr, String uptTime, String uptOpr) {
		this.rateId = rateId;
		this.feeName = feeName;
		this.feeType = feeType;
		this.feeRate = feeRate;
		this.amt1 = amt1;
		this.amt2 = amt2;
		this.misc = misc;
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

	public String getFeeName() {
		return this.feeName;
	}

	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}

	public String getFeeType() {
		return this.feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public Double getFeeRate() {
		return this.feeRate;
	}

	public void setFeeRate(Double feeRate) {
		this.feeRate = feeRate;
	}

	public Double getAmt1() {
		return this.amt1;
	}

	public void setAmt1(Double amt1) {
		this.amt1 = amt1;
	}

	public Double getAmt2() {
		return this.amt2;
	}

	public void setAmt2(Double amt2) {
		this.amt2 = amt2;
	}

	public String getMisc() {
		return this.misc;
	}

	public void setMisc(String misc) {
		this.misc = misc;
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