package com.allinfinance.po.mchnt.base;

import java.io.Serializable;


public abstract class BaseTblInfDiscAlgoPK implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String discId;
	private java.lang.Integer indexNum;


	public BaseTblInfDiscAlgoPK () {}
	
	public BaseTblInfDiscAlgoPK (
		java.lang.String discId,
		java.lang.Integer indexNum) {
        this.setDiscId(discId);
        this.setIndexNum(indexNum);
	}






	public java.lang.String getDiscId() {
		return discId;
	}

	public void setDiscId(java.lang.String discId) {
		this.discId = discId;
	}

	public java.lang.Integer getIndexNum() {
		return indexNum;
	}

	public void setIndexNum(java.lang.Integer indexNum) {
		this.indexNum = indexNum;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.allinfinance.po.mchnt.TblInfDiscAlgoPK)) return false;
		else {
			com.allinfinance.po.mchnt.TblInfDiscAlgoPK mObj = (com.allinfinance.po.mchnt.TblInfDiscAlgoPK) obj;
			if (null != this.getDiscId() && null != mObj.getDiscId()) {
				if (!this.getDiscId().equals(mObj.getDiscId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getIndexNum() && null != mObj.getIndexNum()) {
				if (!this.getIndexNum().equals(mObj.getIndexNum())) {
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
			StringBuffer sb = new StringBuffer();
			if (null != this.getDiscId()) {
				sb.append(this.getDiscId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getIndexNum()) {
				sb.append(this.getIndexNum().hashCode());
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