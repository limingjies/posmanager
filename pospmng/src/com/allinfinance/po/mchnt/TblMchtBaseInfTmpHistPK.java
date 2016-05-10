package com.allinfinance.po.mchnt;

/**
 * TblMchtBaseInfTmpHistId entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class TblMchtBaseInfTmpHistPK implements java.io.Serializable {

	// Fields

	private String mchtNo;
	private String mchtNoNew;

	// Constructors

	/** default constructor */
	public TblMchtBaseInfTmpHistPK() {
	}

	/** full constructor */
	public TblMchtBaseInfTmpHistPK(String mchtNo, String mchtNoNew) {
		this.mchtNo = mchtNo;
		this.mchtNoNew = mchtNoNew;
	}

	// Property accessors

	public String getMchtNo() {
		return this.mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getMchtNoNew() {
		return this.mchtNoNew;
	}

	public void setMchtNoNew(String mchtNoNew) {
		this.mchtNoNew = mchtNoNew;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TblMchtBaseInfTmpHistPK))
			return false;
		TblMchtBaseInfTmpHistPK castOther = (TblMchtBaseInfTmpHistPK) other;

		return ((this.getMchtNo() == castOther.getMchtNo()) || (this
				.getMchtNo() != null && castOther.getMchtNo() != null && this
				.getMchtNo().equals(castOther.getMchtNo())))
				&& ((this.getMchtNoNew() == castOther.getMchtNoNew()) || (this
						.getMchtNoNew() != null
						&& castOther.getMchtNoNew() != null && this
						.getMchtNoNew().equals(castOther.getMchtNoNew())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getMchtNo() == null ? 0 : this.getMchtNo().hashCode());
		result = 37 * result
				+ (getMchtNoNew() == null ? 0 : this.getMchtNoNew().hashCode());
		return result;
	}

}