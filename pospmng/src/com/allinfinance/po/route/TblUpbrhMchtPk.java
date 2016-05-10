package com.allinfinance.po.route;

/**
 * TblUpbrhMchtId entity. @author MyEclipse Persistence Tools
 */

public class TblUpbrhMchtPk implements java.io.Serializable {

	// Fields

	private String mchtIdUp;
	private String termIdUp;
	private String brhId3;

	// Constructors

	/** default constructor */
	public TblUpbrhMchtPk() {
	}

	/** full constructor */
	public TblUpbrhMchtPk(String mchtIdUp, String termIdUp, String brhId3) {
		this.mchtIdUp = mchtIdUp;
		this.termIdUp = termIdUp;
		this.brhId3 = brhId3;
	}

	// Property accessors

	public String getMchtIdUp() {
		return this.mchtIdUp;
	}

	public void setMchtIdUp(String mchtIdUp) {
		this.mchtIdUp = mchtIdUp;
	}

	public String getTermIdUp() {
		return this.termIdUp;
	}

	public void setTermIdUp(String termIdUp) {
		this.termIdUp = termIdUp;
	}

	public String getBrhId3() {
		return this.brhId3;
	}

	public void setBrhId3(String brhId3) {
		this.brhId3 = brhId3;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TblUpbrhMchtPk))
			return false;
		TblUpbrhMchtPk castOther = (TblUpbrhMchtPk) other;

		return ((this.getMchtIdUp() == castOther.getMchtIdUp()) || (this
				.getMchtIdUp() != null && castOther.getMchtIdUp() != null && this
				.getMchtIdUp().equals(castOther.getMchtIdUp())))
				&& ((this.getTermIdUp() == castOther.getTermIdUp()) || (this
						.getTermIdUp() != null
						&& castOther.getTermIdUp() != null && this
						.getTermIdUp().equals(castOther.getTermIdUp())))
				&& ((this.getBrhId3() == castOther.getBrhId3()) || (this
						.getBrhId3() != null && castOther.getBrhId3() != null && this
						.getBrhId3().equals(castOther.getBrhId3())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getMchtIdUp() == null ? 0 : this.getMchtIdUp().hashCode());
		result = 37 * result
				+ (getTermIdUp() == null ? 0 : this.getTermIdUp().hashCode());
		result = 37 * result
				+ (getBrhId3() == null ? 0 : this.getBrhId3().hashCode());
		return result;
	}

}