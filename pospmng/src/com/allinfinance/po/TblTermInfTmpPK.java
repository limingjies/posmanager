package com.allinfinance.po;

import java.io.Serializable;

import com.allinfinance.system.util.CommonFunction;

public class TblTermInfTmpPK implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String termId;
	private java.lang.String recCrtTs;


	public TblTermInfTmpPK () 
	{
		this.recCrtTs = CommonFunction.getCurrentDate();
	}
	
	public TblTermInfTmpPK (
		java.lang.String termId,
		java.lang.String recCrtTs) {

		this.setTermId(termId);
		this.setRecCrtTs(recCrtTs);
	}


	/**
	 * Return the value associated with the column: TERM_ID
	 */
	public java.lang.String getTermId () {
		return termId;
	}

	/**
	 * Set the value related to the column: TERM_ID
	 * @param termId the TERM_ID value
	 */
	public void setTermId (java.lang.String termId) {
		this.termId = termId;
	}



	/**
	 * Return the value associated with the column: REC_CRT_TS
	 */
	public java.lang.String getRecCrtTs () {
		return recCrtTs;
	}

	/**
	 * Set the value related to the column: REC_CRT_TS
	 * @param recCrtTs the REC_CRT_TS value
	 */
	public void setRecCrtTs (java.lang.String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.allinfinance.po.TblTermInfTmpPK)) return false;
		else {
			com.allinfinance.po.TblTermInfTmpPK mObj = (com.allinfinance.po.TblTermInfTmpPK) obj;
			if (null != this.getTermId() && null != mObj.getTermId()) {
				if (!this.getTermId().equals(mObj.getTermId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getRecCrtTs() && null != mObj.getRecCrtTs()) {
				if (!this.getRecCrtTs().equals(mObj.getRecCrtTs())) {
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
			if (null != this.getTermId()) {
				sb.append(this.getTermId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getRecCrtTs()) {
				sb.append(this.getRecCrtTs().hashCode());
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