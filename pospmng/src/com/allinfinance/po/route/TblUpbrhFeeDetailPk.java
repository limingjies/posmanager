package com.allinfinance.po.route;

import java.math.BigDecimal;

/**
 * TblUpbrhFeeDetailId entity. @author MyEclipse Persistence Tools
 */

public class TblUpbrhFeeDetailPk implements java.io.Serializable {

	// Fields

	private String discId;
	private BigDecimal indexNum;

	// Constructors

	/** default constructor */
	public TblUpbrhFeeDetailPk() {
	}

	/** full constructor */
	public TblUpbrhFeeDetailPk(String discId, BigDecimal indexNum) {
		this.discId = discId;
		this.indexNum = indexNum;
	}

	// Property accessors

	public String getDiscId() {
		return this.discId;
	}

	public void setDiscId(String discId) {
		this.discId = discId;
	}

	public BigDecimal getIndexNum() {
		return this.indexNum;
	}

	public void setIndexNum(BigDecimal indexNum) {
		this.indexNum = indexNum;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TblUpbrhFeeDetailPk))
			return false;
		TblUpbrhFeeDetailPk castOther = (TblUpbrhFeeDetailPk) other;

		return ((this.getDiscId() == castOther.getDiscId()) || (this
				.getDiscId() != null && castOther.getDiscId() != null && this
				.getDiscId().equals(castOther.getDiscId())))
				&& ((this.getIndexNum() == castOther.getIndexNum()) || (this
						.getIndexNum() != null
						&& castOther.getIndexNum() != null && this
						.getIndexNum().equals(castOther.getIndexNum())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getDiscId() == null ? 0 : this.getDiscId().hashCode());
		result = 37 * result
				+ (getIndexNum() == null ? 0 : this.getIndexNum().hashCode());
		return result;
	}

}