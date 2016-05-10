package com.allinfinance.po.settle;

import java.io.Serializable;
import java.math.BigDecimal;

public class TblPaySettleMchtError implements Serializable {

	private static final long serialVersionUID = 1L;

    private String mchtNo;
    private String mchtNm;
    private BigDecimal amt;
    private BigDecimal bal;
	
    public TblPaySettleMchtError() {
//		super();
	}

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getMchtNm() {
		return mchtNm;
	}

	public void setMchtNm(String mchtNm) {
		this.mchtNm = mchtNm;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public BigDecimal getBal() {
		return bal;
	}

	public void setBal(BigDecimal bal) {
		this.bal = bal;
	}

	
	

    
}