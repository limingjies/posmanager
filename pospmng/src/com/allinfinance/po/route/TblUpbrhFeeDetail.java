package com.allinfinance.po.route;

import java.math.BigDecimal;

/**
 * TblUpbrhFeeDetail entity. @author MyEclipse Persistence Tools
 */

public class TblUpbrhFeeDetail implements java.io.Serializable {

	// Fields

	private TblUpbrhFeeDetailPk pk;
	private Double minFee;
	private Double maxFee;
	private Double floorAmount;
	private Double upperAmount;
	private BigDecimal flag;
	private Double feeValue;
	private String txnNum;
	private String cardType;
	private String misc1;
	private String misc2;
	private String crtTime;
	private String crtOpr;
	private String uptTime;
	private String uptOpr;

	// Constructors

	/** default constructor */
	public TblUpbrhFeeDetail() {
	}

	/** minimal constructor */
	public TblUpbrhFeeDetail(TblUpbrhFeeDetailPk pk, String crtTime,
			String crtOpr, String uptTime, String uptOpr) {
		this.pk = pk;
		this.crtTime = crtTime;
		this.crtOpr = crtOpr;
		this.uptTime = uptTime;
		this.uptOpr = uptOpr;
	}

	/** full constructor */
	public TblUpbrhFeeDetail(TblUpbrhFeeDetailPk pk, Double minFee,
			Double maxFee, Double floorAmount, Double upperAmount,
			BigDecimal flag, Double feeValue, String txnNum, String cardType,
			String misc1, String misc2, String crtTime, String crtOpr,
			String uptTime, String uptOpr) {
		this.pk = pk;
		this.minFee = minFee;
		this.maxFee = maxFee;
		this.floorAmount = floorAmount;
		this.upperAmount = upperAmount;
		this.flag = flag;
		this.feeValue = feeValue;
		this.txnNum = txnNum;
		this.cardType = cardType;
		this.misc1 = misc1;
		this.misc2 = misc2;
		this.crtTime = crtTime;
		this.crtOpr = crtOpr;
		this.uptTime = uptTime;
		this.uptOpr = uptOpr;
	}

	// Property accessors

	public TblUpbrhFeeDetailPk getPk() {
		return this.pk;
	}

	public void setPk(TblUpbrhFeeDetailPk pk) {
		this.pk = pk;
	}

	public Double getMinFee() {
		return this.minFee;
	}

	public void setMinFee(Double minFee) {
		this.minFee = minFee;
	}

	public Double getMaxFee() {
		return this.maxFee;
	}

	public void setMaxFee(Double maxFee) {
		this.maxFee = maxFee;
	}

	public Double getFloorAmount() {
		return this.floorAmount;
	}

	public void setFloorAmount(Double floorAmount) {
		this.floorAmount = floorAmount;
	}

	public Double getUpperAmount() {
		return this.upperAmount;
	}

	public void setUpperAmount(Double upperAmount) {
		this.upperAmount = upperAmount;
	}

	public BigDecimal getFlag() {
		return this.flag;
	}

	public void setFlag(BigDecimal flag) {
		this.flag = flag;
	}

	public Double getFeeValue() {
		return this.feeValue;
	}

	public void setFeeValue(Double feeValue) {
		this.feeValue = feeValue;
	}

	public String getTxnNum() {
		return this.txnNum;
	}

	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
	}

	public String getCardType() {
		return this.cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
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