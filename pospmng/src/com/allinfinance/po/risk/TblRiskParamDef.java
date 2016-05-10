package com.allinfinance.po.risk;

import java.io.Serializable;
import com.allinfinance.po.risk.TblRiskParamDefPK;


public class TblRiskParamDef implements Serializable {
	private static final long serialVersionUID = 1L;

	// primary key
	private TblRiskParamDefPK id;

	// fields
	private java.lang.String paramLen;
	private java.lang.String defaultValue;
	private java.lang.String paramName;
	private java.lang.String resved;
	private java.lang.String resved1;
	/**
	 * @return the id
	 */
	public TblRiskParamDefPK getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(TblRiskParamDefPK id) {
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
	 * @return the defaultValue
	 */
	public java.lang.String getDefaultValue() {
		return defaultValue;
	}
	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(java.lang.String defaultValue) {
		this.defaultValue = defaultValue;
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
	
}