package com.allinfinance.po.agentpay;

import java.io.Serializable;

public class TblBankInfo implements Serializable {

	/**
	 * Tbl_Rcv_Pack表映射对象
	 */
	private static final long serialVersionUID = 1L;
	private String bankNo;
	private String bankName;
	
	public TblBankInfo() {
		
	}
	public TblBankInfo(String bankNo, String bankName) {
		this.bankNo = bankNo;
		this.bankName = bankName;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
}
