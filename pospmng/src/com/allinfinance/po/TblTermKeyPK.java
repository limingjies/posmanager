package com.allinfinance.po;

import java.io.Serializable;

public class TblTermKeyPK implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String mchtCd;
	private java.lang.String termId;


	
	/**
	 * 
	 */
	public TblTermKeyPK() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param mchtCd2
	 * @param termId2
	 */
	public TblTermKeyPK(String mchtCd, String termId) {
		this.mchtCd = mchtCd;
		this.termId = termId;
	}

	/**
	 * Return the value associated with the column: mchnt_id
	 */
	public java.lang.String getMchtCd () {
		return mchtCd;
	}

	/**
	 * Set the value related to the column: mchnt_id
	 * @param mchtCd the mchnt_id value
	 */
	public void setMchtCd (java.lang.String mchtCd) {
		this.mchtCd = mchtCd;
	}



	/**
	 * Return the value associated with the column: term_id
	 */
	public java.lang.String getTermId () {
		return termId;
	}

	/**
	 * Set the value related to the column: term_id
	 * @param termId the term_id value
	 */
	public void setTermId (java.lang.String termId) {
		this.termId = termId;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.allinfinance.po.TblTermKeyPK)) return false;
		else {
			com.allinfinance.po.TblTermKeyPK mObj = (com.allinfinance.po.TblTermKeyPK) obj;
			if (null != this.getMchtCd() && null != mObj.getMchtCd()) {
				if (!this.getMchtCd().equals(mObj.getMchtCd())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getTermId() && null != mObj.getTermId()) {
				if (!this.getTermId().equals(mObj.getTermId())) {
					return false;
				}
			}
			else {
				return false;
			}
			return true;
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			StringBuilder sb = new StringBuilder();
			if (null != this.getMchtCd()) {
				sb.append(this.getMchtCd().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getTermId()) {
				sb.append(this.getTermId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			this.hashCode = sb.toString().hashCode();
		}
		return this.hashCode;
	}
}