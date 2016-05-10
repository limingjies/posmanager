package com.allinfinance.po.risk;

import java.io.Serializable;

public class TblRiskParamDefPK implements Serializable {

	private static final long serialVersionUID = 1L;
	private java.lang.String riskId;
	private java.lang.String paramSeq;
	/**
	 * @return the riskId
	 */
	public java.lang.String getRiskId() {
		return riskId;
	}
	/**
	 * @param riskId the riskId to set
	 */
	public void setRiskId(java.lang.String riskId) {
		this.riskId = riskId;
	}
	/**
	 * @return the paramSeq
	 */
	public java.lang.String getParamSeq() {
		return paramSeq;
	}
	/**
	 * @param paramSeq the paramSeq to set
	 */
	public void setParamSeq(java.lang.String paramSeq) {
		this.paramSeq = paramSeq;
	}
	
}