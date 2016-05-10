package com.allinfinance.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblRiskParamInf implements Serializable {

	// primary key
	private TblRiskParamInfPK id;

	// fields

	private java.lang.String paramLen;
	private java.lang.String paramValue;
	private java.lang.String paramName;
	private java.lang.String resved;
	private java.lang.String resved1;
	
	
	private int hashCode = Integer.MIN_VALUE;
	/**
	 * 
	 */
	public TblRiskParamInf() {
		initialize();
	}

		/**
		 * Constructor for primary key
		 */
		public TblRiskParamInf (com.allinfinance.po.TblRiskParamInfPK id) {
			this.setId(id);
			initialize();
		}

		protected void initialize () {}
	
	/**
	 * @return the id
	 */
	public TblRiskParamInfPK getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(TblRiskParamInfPK id) {
		this.id = id;
	}

	/**
	 * @return the paramLen
	 */
	public java.lang.String getParamLen() {
		return paramLen;
	}
	/**
	 * @param paramLen the paramLen to set
	 */
	public void setParamLen(java.lang.String paramLen) {
		this.paramLen = paramLen;
	}
	/**
	 * @return the paramValue
	 */
	public java.lang.String getParamValue() {
		return paramValue;
	}
	/**
	 * @param paramValue the paramValue to set
	 */
	public void setParamValue(java.lang.String paramValue) {
		this.paramValue = paramValue;
	}
	/**
	 * @return the paramName
	 */
	public java.lang.String getParamName() {
		return paramName;
	}
	/**
	 * @param paramName the paramName to set
	 */
	public void setParamName(java.lang.String paramName) {
		this.paramName = paramName;
	}
	/**
	 * @return the resved
	 */
	public java.lang.String getResved() {
		return resved;
	}
	/**
	 * @param resved the resved to set
	 */
	public void setResved(java.lang.String resved) {
		this.resved = resved;
	}
	/**
	 * @return the resved1
	 */
	public java.lang.String getResved1() {
		return resved1;
	}
	/**
	 * @param resved1 the resved1 to set
	 */
	public void setResved1(java.lang.String resved1) {
		this.resved1 = resved1;
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}
	
}
