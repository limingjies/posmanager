package com.allinfinance.po;

/**
 * TblAgentRateInfoId entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
public class TblAgentRateInfoId implements java.io.Serializable {

	// Fields

	private String discId;
	private String rateId;

	// Constructors

	/** default constructor */
	public TblAgentRateInfoId() {
	}

	/** full constructor */
	public TblAgentRateInfoId(String discId, String rateId) {
		this.discId = discId;
		this.rateId = rateId;
	}

	// Property accessors

	public String getDiscId() {
		return this.discId;
	}

	public void setDiscId(String discId) {
		this.discId = discId;
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
		if (!(other instanceof TblAgentRateInfoId))
			return false;
		TblAgentRateInfoId castOther = (TblAgentRateInfoId) other;

		return ((this.getDiscId() == castOther.getDiscId()) || (this
				.getDiscId() != null && castOther.getDiscId() != null && this
				.getDiscId().equals(castOther.getDiscId())))
				&& ((this.getRateId() == castOther.getRateId()) || (this
						.getRateId() != null && castOther.getRateId() != null && this
						.getRateId().equals(castOther.getRateId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getDiscId() == null ? 0 : this.getDiscId().hashCode());
		result = 37 * result
				+ (getRateId() == null ? 0 : this.getRateId().hashCode());
		return result;
	}

}