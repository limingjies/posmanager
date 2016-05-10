package com.allinfinance.po.agentpay;

import java.io.Serializable;

public class TblAreaInfoPK implements Serializable {

	/**
	 * Tbl_Rcv_Pack表映射对象
	 */
	private static final long serialVersionUID = 1L;
	protected int hashCode = Integer.MIN_VALUE;
	private String areaNo;
	private String zip;
	
	public TblAreaInfoPK() {
		
	}
	
	public TblAreaInfoPK(String areaNo, String zip, String province,
			String areaName) {
		this.areaNo = areaNo;
		this.zip = zip;
	}

	public String getAreaNo() {
		return areaNo;
	}
	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.allinfinance.po.agentpay.TblAreaInfoPK)) return false;
		else {
			com.allinfinance.po.agentpay.TblAreaInfoPK mObj = (com.allinfinance.po.agentpay.TblAreaInfoPK) obj;
			if (null != this.getAreaNo() && null != mObj.getAreaNo()) {
				if (!this.getAreaNo().equals(mObj.getAreaNo())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getZip() && null != mObj.getZip()) {
				if (!this.getZip().equals(mObj.getZip())) {
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
			if (null != this.getAreaNo()) {
				sb.append(this.getAreaNo().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getZip()) {
				sb.append(this.getZip().hashCode());
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
