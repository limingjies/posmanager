package com.allinfinance.po.settle;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class TblZthDtl implements Serializable {

	private TblZthDtlPK tblZthDtlPK;
	
	private String mchtNo;
	private BigDecimal amt;
	private String misc;
	private String misc1;
	
	
	public TblZthDtl() {
//		super();
	}


	public TblZthDtlPK getTblZthDtlPK() {
		return tblZthDtlPK;
	}


	public void setTblZthDtlPK(TblZthDtlPK tblZthDtlPK) {
		this.tblZthDtlPK = tblZthDtlPK;
	}



	public String getMchtNo() {
		return mchtNo;
	}


	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}


	public BigDecimal getAmt() {
		return amt;
	}


	public void setAmt(BigDecimal amt) {
		this.amt = amt;
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
	
	
}