package com.allinfinance.po;

/**
 * TblAgentRateInfo entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
public class TblAgentRateInfoHis implements java.io.Serializable {

	// Fields

	private TblAgentRateInfoId id;
	private String seqId;
	private String feeType;
	private Double feeRate;

	// Constructors

	/** default constructor */
	public TblAgentRateInfoHis() {
	}

	/** minimal constructor */
	public TblAgentRateInfoHis(TblAgentRateInfoId id) {
		this.id = id;
	}

	/** full constructor */
	public TblAgentRateInfoHis(TblAgentRateInfoId id, String feeType,
			Double feeRate) {
		this.id = id;
		this.feeType = feeType;
		this.feeRate = feeRate;
	}

	// Property accessors

	public TblAgentRateInfoId getId() {
		return this.id;
	}

	public void setId(TblAgentRateInfoId id) {
		this.id = id;
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

	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}
}