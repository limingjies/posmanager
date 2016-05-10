package com.allinfinance.po.settle;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblZth implements Serializable {

	
	private String instDate;
	private String sysSeqNum;
	private String f42;
	private String amt1;
	private String eflag;
	private String ddate;
	
	
	public TblZth() {
//		super();
	}


	public String getInstDate() {
		return instDate;
	}


	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}


	public String getSysSeqNum() {
		return sysSeqNum;
	}


	public void setSysSeqNum(String sysSeqNum) {
		this.sysSeqNum = sysSeqNum;
	}


	public String getF42() {
		return f42;
	}


	public void setF42(String f42) {
		this.f42 = f42;
	}


	public String getAmt1() {
		return amt1;
	}


	public void setAmt1(String amt1) {
		this.amt1 = amt1;
	}


	public String getEflag() {
		return eflag;
	}


	public void setEflag(String eflag) {
		this.eflag = eflag;
	}


	public String getDdate() {
		return ddate;
	}


	public void setDdate(String ddate) {
		this.ddate = ddate;
	}


	
	
	
}