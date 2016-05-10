package com.allinfinance.po;

import java.io.Serializable;

public class TblCtlMchtSettleInfPK implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String ctlSettleNo;
	private java.lang.String ctlSettleType;

	/**
	 * 
	 */
	public TblCtlMchtSettleInfPK() {
		super();
	}

	public TblCtlMchtSettleInfPK(String ctlSettleNo,String ctlSettleType){
		this.ctlSettleNo = ctlSettleNo;
		this.ctlSettleType = ctlSettleType;
	}
	
	public java.lang.String getCtlSettleNo() {
		return ctlSettleNo;
	}

	public void setCtlSettleNo(java.lang.String ctlSettleNo) {
		this.ctlSettleNo = ctlSettleNo;
	}

	public java.lang.String getCtlSettleType() {
		return ctlSettleType;
	}

	public void setCtlSettleType(java.lang.String ctlSettleType) {
		this.ctlSettleType = ctlSettleType;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.allinfinance.po.TblCtlMchtSettleInfPK)) return false;
		else {
			com.allinfinance.po.TblCtlMchtSettleInfPK mObj = (com.allinfinance.po.TblCtlMchtSettleInfPK) obj;
			if (null != this.getCtlSettleNo() && null != mObj.getCtlSettleNo()) {
				if (!this.getCtlSettleNo().equals(mObj.getCtlSettleNo())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getCtlSettleType() && null != mObj.getCtlSettleType()) {
				if (!this.getCtlSettleType().equals(mObj.getCtlSettleType())) {
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
			if (null != this.getCtlSettleNo()) {
				sb.append(this.getCtlSettleNo().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getCtlSettleType()) {
				sb.append(this.getCtlSettleType().hashCode());
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