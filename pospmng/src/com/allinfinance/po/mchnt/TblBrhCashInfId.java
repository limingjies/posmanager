package com.allinfinance.po.mchnt;

/**
 * TblBrhCashInfId entity. @author MyEclipse Persistence Tools
 */

public class TblBrhCashInfId implements java.io.Serializable {

	// Fields

	private String brhId;
	private String rateId;

	// Constructors

	/** default constructor */
	public TblBrhCashInfId() {
	}

	/** full constructor */
	public TblBrhCashInfId(String brhId, String rateId) {
		this.brhId = brhId;
		this.rateId = rateId;
	}

	// Property accessors

	public String getBrhId() {
		return this.brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	public String getRateId() {
		return this.rateId;
	}

	public void setRateId(String rateId) {
		this.rateId = rateId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TblBrhCashInfId))
			return false;
		TblBrhCashInfId castOther = (TblBrhCashInfId) other;

		return ((this.getBrhId() == castOther.getBrhId()) || (this.getBrhId() != null
				&& castOther.getBrhId() != null && this.getBrhId().equals(
				castOther.getBrhId())))
				&& ((this.getRateId() == castOther.getRateId()) || (this
						.getRateId() != null && castOther.getRateId() != null && this
						.getRateId().equals(castOther.getRateId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getBrhId() == null ? 0 : this.getBrhId().hashCode());
		result = 37 * result
				+ (getRateId() == null ? 0 : this.getRateId().hashCode());
		return result;
	}

}