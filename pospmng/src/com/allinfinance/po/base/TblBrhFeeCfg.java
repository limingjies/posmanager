package com.allinfinance.po.base;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class TblBrhFeeCfg implements Serializable{

	
	
	private TblBrhFeeCfgPK tblBrhFeeCfgPK;
	
	private String name;
	private String enableFlag;
	private String mchtNo;
	private String mchtGrp;
	private String joinType;
	private String joinBegDate;
	private String joinEndDate;
	private BigDecimal mchtFeeLow;
	private BigDecimal mchtFeeUp;
	private BigDecimal mchtCapFeeLow;
	private BigDecimal mchtCapFeeUp;
	private BigDecimal feeRate;
	private BigDecimal feeValue;
	private BigDecimal allotRate;
	private String flag1;
	private String flag2;
	private String misc1;
	private String misc2;
	private BigDecimal amt1;
	private BigDecimal amt2;
	private String lstUpdTime;
	private String lstUpdTlr;
	private String createTime;
	
	
	
	public TblBrhFeeCfg() {
		
	}



	public TblBrhFeeCfgPK getTblBrhFeeCfgPK() {
		return tblBrhFeeCfgPK;
	}



	public void setTblBrhFeeCfgPK(TblBrhFeeCfgPK tblBrhFeeCfgPK) {
		this.tblBrhFeeCfgPK = tblBrhFeeCfgPK;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEnableFlag() {
		return enableFlag;
	}



	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}



	public String getMchtNo() {
		return mchtNo;
	}



	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}



	public String getJoinType() {
		return joinType;
	}



	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}



	public String getJoinBegDate() {
		return joinBegDate;
	}



	public void setJoinBegDate(String joinBegDate) {
		this.joinBegDate = joinBegDate;
	}



	public String getJoinEndDate() {
		return joinEndDate;
	}



	public void setJoinEndDate(String joinEndDate) {
		this.joinEndDate = joinEndDate;
	}



	public BigDecimal getMchtFeeLow() {
		return mchtFeeLow;
	}



	public void setMchtFeeLow(BigDecimal mchtFeeLow) {
		this.mchtFeeLow = mchtFeeLow;
	}



	public BigDecimal getMchtFeeUp() {
		return mchtFeeUp;
	}



	public void setMchtFeeUp(BigDecimal mchtFeeUp) {
		this.mchtFeeUp = mchtFeeUp;
	}



	public BigDecimal getMchtCapFeeLow() {
		return mchtCapFeeLow;
	}



	public void setMchtCapFeeLow(BigDecimal mchtCapFeeLow) {
		this.mchtCapFeeLow = mchtCapFeeLow;
	}



	public BigDecimal getMchtCapFeeUp() {
		return mchtCapFeeUp;
	}



	public void setMchtCapFeeUp(BigDecimal mchtCapFeeUp) {
		this.mchtCapFeeUp = mchtCapFeeUp;
	}



	public BigDecimal getFeeRate() {
		return feeRate;
	}



	public void setFeeRate(BigDecimal feeRate) {
		this.feeRate = feeRate;
	}



	public BigDecimal getFeeValue() {
		return feeValue;
	}



	public void setFeeValue(BigDecimal feeValue) {
		this.feeValue = feeValue;
	}



	public BigDecimal getAllotRate() {
		return allotRate;
	}



	public void setAllotRate(BigDecimal allotRate) {
		this.allotRate = allotRate;
	}



	public String getFlag1() {
		return flag1;
	}



	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}



	public String getFlag2() {
		return flag2;
	}



	public void setFlag2(String flag2) {
		this.flag2 = flag2;
	}



	public String getMisc1() {
		return misc1;
	}



	public void setMisc1(String misc1) {
		this.misc1 = misc1;
	}



	public String getMisc2() {
		return misc2;
	}



	public void setMisc2(String misc2) {
		this.misc2 = misc2;
	}



	public BigDecimal getAmt1() {
		return amt1;
	}



	public void setAmt1(BigDecimal amt1) {
		this.amt1 = amt1;
	}



	public BigDecimal getAmt2() {
		return amt2;
	}



	public void setAmt2(BigDecimal amt2) {
		this.amt2 = amt2;
	}



	public String getLstUpdTime() {
		return lstUpdTime;
	}



	public void setLstUpdTime(String lstUpdTime) {
		this.lstUpdTime = lstUpdTime;
	}



	public String getLstUpdTlr() {
		return lstUpdTlr;
	}



	public void setLstUpdTlr(String lstUpdTlr) {
		this.lstUpdTlr = lstUpdTlr;
	}



	public String getCreateTime() {
		return createTime;
	}



	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}



	public String getMchtGrp() {
		return mchtGrp;
	}



	public void setMchtGrp(String mchtGrp) {
		this.mchtGrp = mchtGrp;
	}

	

	
	
	
	
}
