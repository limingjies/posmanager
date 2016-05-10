package com.allinfinance.po;

import java.math.BigDecimal;

/**
 * TblAgentFeeCfg entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
public class TblAgentFeeCfgHis implements java.io.Serializable {

	// Fields

	private String discId;
	private String agentNo;
	private String seqId;
	private BigDecimal seq;
	private String name;
	private String enableFlag;
	private String mchtNo;
	private String mchtGrp;
	private String promotionBegDate;
	private String promotionEndDate;
	private Double promotionRate;
	private Double baseAmtMonth;
	private Double gradeAmtMonth;
	private Double allotRate;
	private Double specFeeRate;
	private Double extVisaRate;
	private Double extMasterRate;
	private Double extJcbRate;
	private Double extRate1;
	private Double extRate2;
	private Double extRate3;
	private String flag1;
	private String flag2;
	private String misc1;
	private String misc2;
	private Double amt1;
	private Double amt2;
	private String crtTime;
	private String crtOpr;
	private String uptTime;
	private String uptOpr;

	// Constructors

	/** default constructor */
	public TblAgentFeeCfgHis() {
	}

	/** minimal constructor */
	public TblAgentFeeCfgHis(String discId, BigDecimal seq) {
		this.discId = discId;
		this.seq = seq;
	}

	/** full constructor */
	public TblAgentFeeCfgHis(String discId, String agentNo, BigDecimal seq,
			String name, String enableFlag, String mchtNo, String mchtGrp,
			String promotionBegDate, String promotionEndDate,
			Double promotionRate, Double baseAmtMonth, Double gradeAmtMonth,
			Double allotRate, Double specFeeRate, Double extVisaRate,
			Double extMasterRate, Double extJcbRate, Double extRate1,
			Double extRate2, Double extRate3, String flag1, String flag2,
			String misc1, String misc2, Double amt1, Double amt2,
			String crtTime, String crtOpr, String uptTime, String uptOpr) {
		this.discId = discId;
		this.agentNo = agentNo;
		this.seq = seq;
		this.name = name;
		this.enableFlag = enableFlag;
		this.mchtNo = mchtNo;
		this.mchtGrp = mchtGrp;
		this.promotionBegDate = promotionBegDate;
		this.promotionEndDate = promotionEndDate;
		this.promotionRate = promotionRate;
		this.baseAmtMonth = baseAmtMonth;
		this.gradeAmtMonth = gradeAmtMonth;
		this.allotRate = allotRate;
		this.specFeeRate = specFeeRate;
		this.extVisaRate = extVisaRate;
		this.extMasterRate = extMasterRate;
		this.extJcbRate = extJcbRate;
		this.extRate1 = extRate1;
		this.extRate2 = extRate2;
		this.extRate3 = extRate3;
		this.flag1 = flag1;
		this.flag2 = flag2;
		this.misc1 = misc1;
		this.misc2 = misc2;
		this.amt1 = amt1;
		this.amt2 = amt2;
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

	public String getAgentNo() {
		return this.agentNo;
	}

	public void setAgentNo(String agentNo) {
		this.agentNo = agentNo;
	}

	public BigDecimal getSeq() {
		return this.seq;
	}

	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnableFlag() {
		return this.enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	public String getMchtNo() {
		return this.mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getMchtGrp() {
		return this.mchtGrp;
	}

	public void setMchtGrp(String mchtGrp) {
		this.mchtGrp = mchtGrp;
	}

	public String getPromotionBegDate() {
		return this.promotionBegDate;
	}

	public void setPromotionBegDate(String promotionBegDate) {
		this.promotionBegDate = promotionBegDate;
	}

	public String getPromotionEndDate() {
		return this.promotionEndDate;
	}

	public void setPromotionEndDate(String promotionEndDate) {
		this.promotionEndDate = promotionEndDate;
	}

	public Double getPromotionRate() {
		return this.promotionRate;
	}

	public void setPromotionRate(Double promotionRate) {
		this.promotionRate = promotionRate;
	}

	public Double getBaseAmtMonth() {
		return this.baseAmtMonth;
	}

	public void setBaseAmtMonth(Double baseAmtMonth) {
		this.baseAmtMonth = baseAmtMonth;
	}

	public Double getGradeAmtMonth() {
		return this.gradeAmtMonth;
	}

	public void setGradeAmtMonth(Double gradeAmtMonth) {
		this.gradeAmtMonth = gradeAmtMonth;
	}

	public Double getAllotRate() {
		return this.allotRate;
	}

	public void setAllotRate(Double allotRate) {
		this.allotRate = allotRate;
	}

	public Double getSpecFeeRate() {
		return this.specFeeRate;
	}

	public void setSpecFeeRate(Double specFeeRate) {
		this.specFeeRate = specFeeRate;
	}

	public Double getExtVisaRate() {
		return this.extVisaRate;
	}

	public void setExtVisaRate(Double extVisaRate) {
		this.extVisaRate = extVisaRate;
	}

	public Double getExtMasterRate() {
		return this.extMasterRate;
	}

	public void setExtMasterRate(Double extMasterRate) {
		this.extMasterRate = extMasterRate;
	}

	public Double getExtJcbRate() {
		return this.extJcbRate;
	}

	public void setExtJcbRate(Double extJcbRate) {
		this.extJcbRate = extJcbRate;
	}

	public Double getExtRate1() {
		return this.extRate1;
	}

	public void setExtRate1(Double extRate1) {
		this.extRate1 = extRate1;
	}

	public Double getExtRate2() {
		return this.extRate2;
	}

	public void setExtRate2(Double extRate2) {
		this.extRate2 = extRate2;
	}

	public Double getExtRate3() {
		return this.extRate3;
	}

	public void setExtRate3(Double extRate3) {
		this.extRate3 = extRate3;
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

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

}