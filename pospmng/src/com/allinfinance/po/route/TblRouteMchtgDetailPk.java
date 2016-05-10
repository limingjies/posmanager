package com.allinfinance.po.route;

/**
 * TblRouteMchtgDetailId entity. @author MyEclipse Persistence Tools
 */

public class TblRouteMchtgDetailPk implements java.io.Serializable {

	// Fields

	private String mchtId;
	private Integer mchtGid;

	// Constructors

	/** default constructor */
	public TblRouteMchtgDetailPk() {
	}

	/** full constructor */
	public TblRouteMchtgDetailPk(String mchtId, Integer mchtGid) {
		this.mchtId = mchtId;
		this.mchtGid = mchtGid;
	}

	// Property accessors

	public String getMchtId() {
		return this.mchtId;
	}

	public void setMchtId(String mchtId) {
		this.mchtId = mchtId;
	}

	public Integer getMchtGid() {
		return this.mchtGid;
	}

	public void setMchtGid(Integer mchtGid) {
		this.mchtGid = mchtGid;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TblRouteMchtgDetailPk))
			return false;
		TblRouteMchtgDetailPk castOther = (TblRouteMchtgDetailPk) other;

		return ((this.getMchtId() == castOther.getMchtId()) || (this
				.getMchtId() != null && castOther.getMchtId() != null && this
				.getMchtId().equals(castOther.getMchtId())))
				&& ((this.getMchtGid() == castOther.getMchtGid()) || (this
						.getMchtGid() != null && castOther.getMchtGid() != null && this
						.getMchtGid().equals(castOther.getMchtGid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getMchtId() == null ? 0 : this.getMchtId().hashCode());
		result = 37 * result
				+ (getMchtGid() == null ? 0 : this.getMchtGid().hashCode());
		return result;
	}

}