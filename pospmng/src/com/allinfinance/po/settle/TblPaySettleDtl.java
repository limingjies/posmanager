package com.allinfinance.po.settle;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class TblPaySettleDtl implements Serializable {

	private TblPaySettleDtlPK tblPaySettleDtlPK;
	private String setDtBeg;
	private String setDtEnd;
	private String batchNo;
//	private String channelId;
	private BigDecimal amtSettlmt;
	private String mchtAcctNo;
	private String mchtAcctNm;
	private String cnapsId;
	private String cnapsName;
	private String payType;
	private String misc;
	private String misc1;
	
	public TblPaySettleDtl() {
//		super();
	}

	public String getSetDtBeg() {
		return setDtBeg;
	}

	public void setSetDtBeg(String setDtBeg) {
		this.setDtBeg = setDtBeg;
	}

	public String getSetDtEnd() {
		return setDtEnd;
	}

	public void setSetDtEnd(String setDtEnd) {
		this.setDtEnd = setDtEnd;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
	public BigDecimal getAmtSettlmt() {
		return amtSettlmt;
	}

	public void setAmtSettlmt(BigDecimal amtSettlmt) {
		this.amtSettlmt = amtSettlmt;
	}

	public String getMchtAcctNo() {
		return mchtAcctNo;
	}

	public void setMchtAcctNo(String mchtAcctNo) {
		this.mchtAcctNo = mchtAcctNo;
	}

	public String getMchtAcctNm() {
		return mchtAcctNm;
	}

	public void setMchtAcctNm(String mchtAcctNm) {
		this.mchtAcctNm = mchtAcctNm;
	}

	public String getCnapsId() {
		return cnapsId;
	}

	public void setCnapsId(String cnapsId) {
		this.cnapsId = cnapsId;
	}

	

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getMisc() {
		return misc;
	}

	public void setMisc(String misc) {
		this.misc = misc;
	}

	public String getMisc1() {
		return misc1;
	}

	public void setMisc1(String misc1) {
		this.misc1 = misc1;
	}

	public TblPaySettleDtlPK getTblPaySettleDtlPK() {
		return tblPaySettleDtlPK;
	}

	public void setTblPaySettleDtlPK(TblPaySettleDtlPK tblPaySettleDtlPK) {
		this.tblPaySettleDtlPK = tblPaySettleDtlPK;
	}

	public String getCnapsName() {
		return cnapsName;
	}

	public void setCnapsName(String cnapsName) {
		this.cnapsName = cnapsName;
	}
	
	
	
	
}