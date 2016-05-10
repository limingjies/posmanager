package com.allinfinance.po;

import java.io.Serializable;


public abstract class BaseTblRoleFuncMapId implements Serializable {

	protected int hashCode = Integer.MIN_VALUE;

	private java.lang.Long keyId;
	private java.lang.Long valueId;


	public BaseTblRoleFuncMapId () {}
	
	public BaseTblRoleFuncMapId (
		java.lang.Long keyId,
		java.lang.Long valueId) {

		this.setKeyId(keyId);
		this.setValueId(valueId);
	}


	/**
	 * Return the value associated with the column: KEY_ID
	 */
	public java.lang.Long getKeyId () {
		return keyId;
	}

	/**
	 * Set the value related to the column: KEY_ID
	 * @param keyId the KEY_ID value
	 */
	public void setKeyId (java.lang.Long keyId) {
		this.keyId = keyId;
	}



	/**
	 * Return the value associated with the column: VALUE_ID
	 */
	public java.lang.Long getValueId () {
		return valueId;
	}

	/**
	 * Set the value related to the column: VALUE_ID
	 * @param valueId the VALUE_ID value
	 */
	public void setValueId (java.lang.Long valueId) {
		this.valueId = valueId;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.allinfinance.po.TblRoleFuncMapId)) return false;
		else {
			com.allinfinance.po.TblRoleFuncMapId mObj = (com.allinfinance.po.TblRoleFuncMapId) obj;
			if (null != this.getKeyId() && null != mObj.getKeyId()) {
				if (!this.getKeyId().equals(mObj.getKeyId())) {
					return false;
				}
			}
			else {
				return false;
			}
			if (null != this.getValueId() && null != mObj.getValueId()) {
				if (!this.getValueId().equals(mObj.getValueId())) {
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
			if (null != this.getKeyId()) {
				sb.append(this.getKeyId().hashCode());
				sb.append(":");
			}
			else {
				return super.hashCode();
			}
			if (null != this.getValueId()) {
				sb.append(this.getValueId().hashCode());
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