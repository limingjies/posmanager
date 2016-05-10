package com.allinfinance.po.mchnt;

/**
 * TblMchtSettleInfTmpHistId entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class TblMchtSettleInfTmpHistPK implements java.io.Serializable {

	// Fields

	private String mchtNo;
	private String mchtNoNew;

	// Constructors

	/** default constructor */
	public TblMchtSettleInfTmpHistPK() {
	}

	/** full constructor */
	public TblMchtSettleInfTmpHistPK(String mchtNo, String mchtNoSeq) {
		this.mchtNo = mchtNo;
		this.mchtNoNew = mchtNoSeq;
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

	public void setMchtNoNew(String mchtNoSeq) {
		this.mchtNoNew = mchtNoSeq;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TblMchtSettleInfTmpHistPK))
			return false;
		TblMchtSettleInfTmpHistPK castOther = (TblMchtSettleInfTmpHistPK) other;

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