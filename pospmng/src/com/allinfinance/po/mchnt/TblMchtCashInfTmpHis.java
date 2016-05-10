package com.allinfinance.po.mchnt;

/**
 * TblMchtCashInfTmpHis entity. @author MyEclipse Persistence Tools
 */

public class TblMchtCashInfTmpHis implements java.io.Serializable {

	// Fields

	private TblMchtCashInfTmpHisId id;
	private String cashTp;
	private String feeTp;
	private Double feeInvstIntrst;
	private String feeInvstTy;
	private Double feeAmt;
	private String shrPrftInvstCd;
	private String shrPrftInvstTy;
	private String blncHndlTy;
	private String crtTime;
	private String crtOpr;
	private String updTime;
	private String updOpr;
	private String resved;
	private String resved1;

	// Constructors

	/** default constructor */
	public TblMchtCashInfTmpHis() {
	}

	/** minimal constructor */
	public TblMchtCashInfTmpHis(TblMchtCashInfTmpHisId id) {
		this.id = id;
	}

	/** full constructor */
	public TblMchtCashInfTmpHis(TblMchtCashInfTmpHisId id, String cashTp,
			String feeTp, Double feeInvstIntrst, String feeInvstTy,
			Double feeAmt, String shrPrftInvstCd, String shrPrftInvstTy,
			String blncHndlTy, String crtTime, String crtOpr, String updTime,
			String updOpr, String resved, String resved1) {
		this.id = id;
		this.cashTp = cashTp;
		this.feeTp = feeTp;
		this.feeInvstIntrst = feeInvstIntrst;
		this.feeInvstTy = feeInvstTy;
		this.feeAmt = feeAmt;
		this.shrPrftInvstCd = shrPrftInvstCd;
		this.shrPrftInvstTy = shrPrftInvstTy;
		this.blncHndlTy = blncHndlTy;
		this.crtTime = crtTime;
		this.crtOpr = crtOpr;
		this.updTime = updTime;
		this.updOpr = updOpr;
		this.resved = resved;
		this.resved1 = resved1;
	}

	// Property accessors

	public TblMchtCashInfTmpHisId getId() {
		return this.id;
	}

	public void setId(TblMchtCashInfTmpHisId id) {
		this.id = id;
	}

	public String getCashTp() {
		return this.cashTp;
	}

	public void setCashTp(String cashTp) {
		this.cashTp = cashTp;
	}

	public String getFeeTp() {
		return this.feeTp;
	}

	public void setFeeTp(String feeTp) {
		this.feeTp = feeTp;
	}

	public Double getFeeInvstIntrst() {
		return this.feeInvstIntrst;
	}

	public void setFeeInvstIntrst(Double feeInvstIntrst) {
		this.feeInvstIntrst = feeInvstIntrst;
	}

	public String getFeeInvstTy() {
		return this.feeInvstTy;
	}

	public void setFeeInvstTy(String feeInvstTy) {
		this.feeInvstTy = feeInvstTy;
	}

	public Double getFeeAmt() {
		return this.feeAmt;
	}

	public void setFeeAmt(Double feeAmt) {
		this.feeAmt = feeAmt;
	}

	public String getShrPrftInvstCd() {
		return this.shrPrftInvstCd;
	}

	public void setShrPrftInvstCd(String shrPrftInvstCd) {
		this.shrPrftInvstCd = shrPrftInvstCd;
	}

	public String getShrPrftInvstTy() {
		return this.shrPrftInvstTy;
	}

	public void setShrPrftInvstTy(String shrPrftInvstTy) {
		this.shrPrftInvstTy = shrPrftInvstTy;
	}

	public String getBlncHndlTy() {
		return this.blncHndlTy;
	}

	public void setBlncHndlTy(String blncHndlTy) {
		this.blncHndlTy = blncHndlTy;
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

	public String getUpdTime() {
		return this.updTime;
	}

	public void setUpdTime(String updTime) {
		this.updTime = updTime;
	}

	public String getUpdOpr() {
		return this.updOpr;
	}

	public void setUpdOpr(String updOpr) {
		this.updOpr = updOpr;
	}

	public String getResved() {
		return this.resved;
	}

	public void setResved(String resved) {
		this.resved = resved;
	}

	public String getResved1() {
		return this.resved1;
	}

	public void setResved1(String resved1) {
		this.resved1 = resved1;
	}

}