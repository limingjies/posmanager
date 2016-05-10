package com.allinfinance.po;

import java.io.Serializable;

public class TblCardRoutePK implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.String usageKey;
	private java.lang.String cardId;
	

	/**
	 * @param usageKey
	 * @param cardId
	 */
	public TblCardRoutePK(String usageKey, String cardId) {
		this.usageKey = usageKey;
		this.cardId = cardId;
	}

	/**
	 * 
	 */
	public TblCardRoutePK() {}

	public java.lang.String getUsageKey() {
		return usageKey;
	}

	public void setUsageKey(java.lang.String usageKey) {
		this.usageKey = usageKey;
	}

	public java.lang.String getCardId() {
		return cardId;
	}

	public void setCardId(java.lang.String cardId) {
		this.cardId = cardId;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.allinfinance.po.TblCardRoutePK)) return false;
		else {
			com.allinfinance.po.TblCardRoutePK mObj = (com.allinfinance.po.TblCardRoutePK) obj;
			if (null != this.getUsageKey() && null != mObj.getUsageKey()) {
				if (!this.getUsageKey().equals(mObj.getUsageKey())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getCardId() && null != mObj.getCardId()) {
				if (!this.getCardId().equals(mObj.getCardId())) {
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
			if (null != this.getUsageKey()) {
				sb.append(this.getUsageKey().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getCardId()) {
				sb.append(this.getCardId().hashCode());
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