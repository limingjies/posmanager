package com.allinfinance.po.risk;

import java.io.Serializable;
import java.math.BigDecimal;

public class TblMchtFreeze implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mchtId;
	private BigDecimal freezeAmt;
	private BigDecimal doFreezeAmt;
	private String batchNo;
	private String freezeDate;
	private String freezeFlag;
	private String unFreezeFlag;
	private String unFreezeDate;
	private String instDate;
	private String updateDt;

	public TblMchtFreeze() {
	}

	public String getMchtId() {
		return mchtId;
	}

	public void setMchtId(String mchtId) {
		this.mchtId = mchtId;
	}

	public BigDecimal getFreezeAmt() {
		return freezeAmt;
	}

	public void setFreezeAmt(BigDecimal freezeAmt) {
		this.freezeAmt = freezeAmt;
	}

	public BigDecimal getDoFreezeAmt() {
		return doFreezeAmt;
	}

	public void setDoFreezeAmt(BigDecimal doFreezeAmt) {
		this.doFreezeAmt = doFreezeAmt;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getFreezeDate() {
		return freezeDate;
	}

	public void setFreezeDate(String freezeDate) {
		this.freezeDate = freezeDate;
	}

	public String getFreezeFlag() {
		return freezeFlag;
	}

	public void setFreezeFlag(String freezeFlag) {
		this.freezeFlag = freezeFlag;
	}

	public String getUnFreezeFlag() {
		return unFreezeFlag;
	}

	public void setUnFreezeFlag(String unFreezeFlag) {
		this.unFreezeFlag = unFreezeFlag;
	}

	public String getUnFreezeDate() {
		return unFreezeDate;
	}

	public void setUnFreezeDate(String unFreezeDate) {
		this.unFreezeDate = unFreezeDate;
	}

	public String getInstDate() {
		return instDate;
	}

	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}

	public String getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(String updateDt) {
		this.updateDt = updateDt;
	}

}
