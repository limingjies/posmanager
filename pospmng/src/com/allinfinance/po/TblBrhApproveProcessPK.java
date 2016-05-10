package com.allinfinance.po;

import java.io.Serializable;

public class TblBrhApproveProcessPK implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String brhId;
	private java.lang.String txnTime;
	
	
	/**
	 * 
	 */
	public TblBrhApproveProcessPK() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param mchntId2
	 * @param currentDateTime
	 */
	public TblBrhApproveProcessPK(String brhId, String txnTime) {
		this.brhId = brhId;
		this.txnTime = txnTime;
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
	public java.lang.String getTxnTime () {
		return txnTime;
	}

	/**
	 * Set the value related to the column: txn_time
	 * @param txnTime the txn_time value
	 */
	public void setTxnTime (java.lang.String txnTime) {
		this.txnTime = txnTime;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.allinfinance.po.TblBrhApproveProcessPK)) return false;
		else {
			com.allinfinance.po.TblBrhApproveProcessPK mObj = (com.allinfinance.po.TblBrhApproveProcessPK) obj;
			if (null != this.getBrhId() && null != mObj.getBrhId()) {
				if (!this.getBrhId().equals(mObj.getBrhId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getTxnTime() && null != mObj.getTxnTime()) {
				if (!this.getTxnTime().equals(mObj.getTxnTime())) {
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
			if (null != this.getTxnTime()) {
				sb.append(this.getTxnTime().hashCode());
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