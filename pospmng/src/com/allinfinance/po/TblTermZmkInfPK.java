package com.allinfinance.po;

import java.io.Serializable;

public class TblTermZmkInfPK implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String mchtNo;
	private java.lang.String termId;
	
	
	/**
	 * 
	 */
	public TblTermZmkInfPK() {
		super();
		// TODO Auto-generated constructor stub
	}

	public java.lang.String getMchtNo() {
		return mchtNo;
	}




	public void setMchtNo(java.lang.String mchtNo) {
		this.mchtNo = mchtNo;
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
		if (!(obj instanceof com.allinfinance.po.TblTermZmkInfPK)) return false;
		else {
			com.allinfinance.po.TblTermZmkInfPK mObj = (com.allinfinance.po.TblTermZmkInfPK) obj;
			if (null != this.getMchtNo() && null != mObj.getMchtNo()) {
				if (!this.getMchtNo().equals(mObj.getMchtNo())) {
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
			if (null != this.getMchtNo()) {
				sb.append(this.getMchtNo().hashCode());
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