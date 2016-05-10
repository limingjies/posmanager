package com.allinfinance.po.agentpay;

import java.io.Serializable;

public class TblParamInfoPK implements Serializable {
	private static final long serialVersionUID = 1L;
	protected int hashCode = Integer.MIN_VALUE;
	private String paramMark;
	private String paramCode;
	
	public TblParamInfoPK() {
		
	}
	
	public TblParamInfoPK(String paramMark, String paramCode) {
		this.paramMark = paramMark;
		this.paramCode = paramCode;
	}



	public String getParamMark() {
		return paramMark;
	}
	public void setParamMark(String paramMark) {
		this.paramMark = paramMark;
	}
	public String getParamCode() {
		return paramCode;
	}
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.allinfinance.po.agentpay.TblParamInfoPK)) return false;
		else {
			com.allinfinance.po.agentpay.TblParamInfoPK mObj = (com.allinfinance.po.agentpay.TblParamInfoPK) obj;
			if (null != this.getParamMark() && null != mObj.getParamMark()) {
				if (!this.getParamMark().equals(mObj.getParamMark())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getParamCode() && null != mObj.getParamCode()) {
				if (!this.getParamCode().equals(mObj.getParamCode())) {
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
			if (null != this.getParamMark()) {
				sb.append(this.getParamMark().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getParamCode()) {
				sb.append(this.getParamCode().hashCode());
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
