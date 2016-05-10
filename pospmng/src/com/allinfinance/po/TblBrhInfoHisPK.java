package com.allinfinance.po;

import java.io.Serializable;

public class TblBrhInfoHisPK implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String brhId;
	private java.lang.String seqId;
	
	
	/**
	 * 
	 */
	public TblBrhInfoHisPK() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param mchntId2
	 * @param currentDateTime
	 */
	public TblBrhInfoHisPK(String brhId, String seqId) {
		this.brhId = brhId;
		this.seqId = seqId;
	}

	/**
	 * Return the value associated with the column: mchnt_id
	 */
	public java.lang.String getBrhId () {
		return brhId;
	}

	/**
	 * Set the value related to the column: mchnt_id
	 * @param mchntId the mchnt_id value
	 */
	public void setBrhId (java.lang.String brhId) {
		this.brhId = brhId;
	}



	/**
	 * Return the value associated with the column: txn_time
	 */
	public java.lang.String getSeqId () {
		return seqId;
	}

	/**
	 * Set the value related to the column: txn_time
	 * @param seqId the txn_time value
	 */
	public void setSeqId (java.lang.String seqId) {
		this.seqId = seqId;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.allinfinance.po.TblBrhInfoHisPK)) return false;
		else {
			com.allinfinance.po.TblBrhInfoHisPK mObj = (com.allinfinance.po.TblBrhInfoHisPK) obj;
			if (null != this.getBrhId() && null != mObj.getBrhId()) {
				if (!this.getBrhId().equals(mObj.getBrhId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getSeqId() && null != mObj.getSeqId()) {
				if (!this.getSeqId().equals(mObj.getSeqId())) {
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
			if (null != this.getBrhId()) {
				sb.append(this.getBrhId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getSeqId()) {
				sb.append(this.getSeqId().hashCode());
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