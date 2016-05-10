package com.allinfinance.po.agentpay;

import java.io.Serializable;

public class TblStlmFileTransInfPK implements Serializable {

	/**
	 * Tbl_Rcv_Pack表映射对象
	 */
	private static final long serialVersionUID = 1L;
	protected int hashCode = Integer.MIN_VALUE;
	private String stlmDate;
	private String mchtNo;
	
	public TblStlmFileTransInfPK() {
		
	}
	public String getStlmDate() {
		return stlmDate;
	}
	public void setStlmDate(String stlmDate) {
		this.stlmDate = stlmDate;
	}
	public String getMchtNo() {
		return mchtNo;
	}
	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.allinfinance.po.agentpay.TblStlmFileTransInfPK)) return false;
		else {
			com.allinfinance.po.agentpay.TblStlmFileTransInfPK mObj = (com.allinfinance.po.agentpay.TblStlmFileTransInfPK) obj;
			if (null != this.getStlmDate() && null != mObj.getStlmDate()) {
				if (!this.getStlmDate().equals(mObj.getStlmDate())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getMchtNo() && null != mObj.getMchtNo()) {
				if (!this.getMchtNo().equals(mObj.getMchtNo())) {
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
			if (null != this.getStlmDate()) {
				sb.append(this.getStlmDate().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getMchtNo()) {
				sb.append(this.getMchtNo().hashCode());
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
