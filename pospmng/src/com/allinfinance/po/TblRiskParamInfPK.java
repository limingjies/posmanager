package com.allinfinance.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblRiskParamInfPK implements Serializable{

	private java.lang.String modelKind;
	private java.lang.String modelSeq;
	private java.lang.String riskLvl;
	
	
	/**
	 * 
	 */
	public TblRiskParamInfPK() {
	}
	/**
	 * @return the modelKind
	 */
	public java.lang.String getModelKind() {
		return modelKind;
	}
	/**
	 * @param modelKind the modelKind to set
	 */
	public void setModelKind(java.lang.String modelKind) {
		this.modelKind = modelKind;
	}
	/**
	 * @return the modelSeq
	 */
	public java.lang.String getModelSeq() {
		return modelSeq;
	}
	/**
	 * @param modelSeq the modelSeq to set
	 */
	public void setModelSeq(java.lang.String modelSeq) {
		this.modelSeq = modelSeq;
	}
	public java.lang.String getRiskLvl() {
		return riskLvl;
	}
	public void setRiskLvl(java.lang.String riskLvl) {
		this.riskLvl = riskLvl;
	}
	
	
	
}
