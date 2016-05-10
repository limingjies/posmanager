package com.allinfinance.po.risk;

import java.io.Serializable;

public class TblBankCardBlackOptLogPK implements Serializable {

	private static final long serialVersionUID = 1L;
	private java.lang.String cardNo;
	private java.lang.String optTime;
	/**
	 * @return the cardNo
	 */
	public java.lang.String getCardNo() {
		return cardNo;
	}
	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(java.lang.String cardNo) {
		this.cardNo = cardNo;
	}
	/**
	 * @return the optTime
	 */
	public java.lang.String getOptTime() {
		return optTime;
	}
	/**
	 * @param optTime the optTime to set
	 */
	public void setOptTime(java.lang.String optTime) {
		this.optTime = optTime;
	}
	
}