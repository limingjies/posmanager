package com.allinfinance.po.mchnt;

import java.math.BigDecimal;

/**
 * TblMchtCashInfTmpHisId entity. @author MyEclipse Persistence Tools
 */

public class TblMchtCashInfTmpHisId implements java.io.Serializable {

	// Fields

	private String mchtId;
	private BigDecimal seqId;

	// Constructors

	/** default constructor */
	public TblMchtCashInfTmpHisId() {
	}

	/** full constructor */
	public TblMchtCashInfTmpHisId(String mchtId, BigDecimal seqId) {
		this.mchtId = mchtId;
		this.seqId = seqId;
	}

	// Property accessors

	public String getMchtId() {
		return this.mchtId;
	}

	public void setMchtId(String mchtId) {
		this.mchtId = mchtId;
	}

	public BigDecimal getSeqId() {
		return this.seqId;
	}

	public void setSeqId(BigDecimal seqId) {
		this.seqId = seqId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TblMchtCashInfTmpHisId))
			return false;
		TblMchtCashInfTmpHisId castOther = (TblMchtCashInfTmpHisId) other;

		return ((this.getMchtId() == castOther.getMchtId()) || (this
				.getMchtId() != null && castOther.getMchtId() != null && this
				.getMchtId().equals(castOther.getMchtId())))
				&& ((this.getSeqId() == castOther.getSeqId()) || (this
						.getSeqId() != null && castOther.getSeqId() != null && this
						.getSeqId().equals(castOther.getSeqId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getMchtId() == null ? 0 : this.getMchtId().hashCode());
		result = 37 * result
				+ (getSeqId() == null ? 0 : this.getSeqId().hashCode());
		return result;
	}

}