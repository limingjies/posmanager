package com.allinfinance.po.agentpay;

import java.io.Serializable;

public class TblRcvPackDtlPK implements Serializable {
	
	
	/**
	 * Tbl_Rcv_Pack_Dtl 表主键
	 */
	private static final long serialVersionUID = 1L;

	protected int hashCode = Integer.MIN_VALUE;
	
	
	private String batchId;
	private Integer seq;
	
	public TblRcvPackDtlPK() {
		// TODO Auto-generated constructor stub
	}
	public TblRcvPackDtlPK(String batchId, Integer seq) {
		this.batchId = batchId;
		this.seq = seq;
	}

	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.allinfinance.po.agentpay.TblRcvPackDtlPK)) return false;
		else {
			com.allinfinance.po.agentpay.TblRcvPackDtlPK mObj = (com.allinfinance.po.agentpay.TblRcvPackDtlPK) obj;
			if (null != this.getBatchId() && null != mObj.getBatchId()) {
				if (!this.getBatchId().equals(mObj.getBatchId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getSeq() && null != mObj.getSeq()) {
				if (!this.getSeq().equals(mObj.getSeq())) {
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
			if (null != this.getBatchId()) {
				sb.append(this.getBatchId().hashCode());
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
