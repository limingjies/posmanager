package com.allinfinance.po.settleNew;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class TblSettleDtl implements Serializable {

	
	private TblSettleDtlPK tblSettleDtlPK;
	private String begDate;
	private String endDate;
	private BigDecimal amtNormal;
	private BigDecimal amtFreeze;
	private BigDecimal amtErrMinus;
	private BigDecimal amtErrAdd;
	private BigDecimal amtHanging;
	private BigDecimal amtSettle;
	private String payType;
	private String mchtNm;
	private String mchtAcctNo;
	private String mchtAcctNm;
	private String cnapsId;
	private String cnapsName;
	private String areaNm;
	private String reserved1;
	private String reserved2;
	private String lstUpdTm;
	
	public TblSettleDtl() {
//		super();
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

	

	public String getCnapsName() {
		return cnapsName;
	}

	public void setCnapsName(String cnapsName) {
		this.cnapsName = cnapsName;
	}



	public TblSettleDtlPK getTblSettleDtlPK() {
		return tblSettleDtlPK;
	}



	public void setTblSettleDtlPK(TblSettleDtlPK tblSettleDtlPK) {
		this.tblSettleDtlPK = tblSettleDtlPK;
	}



	public String getBegDate() {
		return begDate;
	}



	public void setBegDate(String begDate) {
		this.begDate = begDate;
	}



	public String getEndDate() {
		return endDate;
	}



	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}



	public BigDecimal getAmtNormal() {
		return amtNormal;
	}



	public void setAmtNormal(BigDecimal amtNormal) {
		this.amtNormal = amtNormal;
	}



	public BigDecimal getAmtFreeze() {
		return amtFreeze;
	}



	public void setAmtFreeze(BigDecimal amtFreeze) {
		this.amtFreeze = amtFreeze;
	}



	public BigDecimal getAmtErrMinus() {
		return amtErrMinus;
	}



	public void setAmtErrMinus(BigDecimal amtErrMinus) {
		this.amtErrMinus = amtErrMinus;
	}



	public BigDecimal getAmtErrAdd() {
		return amtErrAdd;
	}



	public void setAmtErrAdd(BigDecimal amtErrAdd) {
		this.amtErrAdd = amtErrAdd;
	}



	public BigDecimal getAmtHanging() {
		return amtHanging;
	}



	public void setAmtHanging(BigDecimal amtHanging) {
		this.amtHanging = amtHanging;
	}



	public BigDecimal getAmtSettle() {
		return amtSettle;
	}



	public void setAmtSettle(BigDecimal amtSettle) {
		this.amtSettle = amtSettle;
	}



	public String getAreaNm() {
		return areaNm;
	}



	public void setAreaNm(String areaNm) {
		this.areaNm = areaNm;
	}



	public String getReserved1() {
		return reserved1;
	}



	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}



	public String getReserved2() {
		return reserved2;
	}



	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}



	public String getLstUpdTm() {
		return lstUpdTm;
	}



	public void setLstUpdTm(String lstUpdTm) {
		this.lstUpdTm = lstUpdTm;
	}



	public String getMchtNm() {
		return mchtNm;
	}



	public void setMchtNm(String mchtNm) {
		this.mchtNm = mchtNm;
	}
	
	
	
	
	
}