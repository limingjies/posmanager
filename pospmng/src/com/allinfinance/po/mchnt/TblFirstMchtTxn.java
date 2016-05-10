package com.allinfinance.po.mchnt;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblFirstMchtTxn implements Serializable {
	// primary key
	private java.lang.String firstMchtCd;
	// fields
	private java.lang.String txnDate;
	private java.math.BigDecimal txnNum;
	private java.math.BigDecimal txnAmt;
	private java.math.BigDecimal amtLimit;
	private java.lang.String reserved;
	/**
	 * @return the firstMchtCd
	 */
	public java.lang.String getFirstMchtCd() {
		return firstMchtCd;
	}
	/**
	 * @param firstMchtCd the firstMchtCd to set
	 */
	public void setFirstMchtCd(java.lang.String firstMchtCd) {
		this.firstMchtCd = firstMchtCd;
	}
	/**
	 * @return the txnDate
	 */
	public java.lang.String getTxnDate() {
		return txnDate;
	}
	/**
	 * @param txnDate the txnDate to set
	 */
	public void setTxnDate(java.lang.String txnDate) {
		this.txnDate = txnDate;
	}
	/**
	 * @return the txnNum
	 */
	public java.math.BigDecimal getTxnNum() {
		return txnNum;
	}
	/**
	 * @param txnNum the txnNum to set
	 */
	public void setTxnNum(java.math.BigDecimal txnNum) {
		this.txnNum = txnNum;
	}
	/**
	 * @return the txnAmt
	 */
	public java.math.BigDecimal getTxnAmt() {
		return txnAmt;
	}
	/**
	 * @param txnAmt the txnAmt to set
	 */
	public void setTxnAmt(java.math.BigDecimal txnAmt) {
		this.txnAmt = txnAmt;
	}
	/**
	 * @return the amtLimit
	 */
	public java.math.BigDecimal getAmtLimit() {
		return amtLimit;
	}
	/**
	 * @param amtLimit the amtLimit to set
	 */
	public void setAmtLimit(java.math.BigDecimal amtLimit) {
		this.amtLimit = amtLimit;
	}
	/**
	 * @return the reserved
	 */
	public java.lang.String getReserved() {
		return reserved;
	}
	/**
	 * @param reserved the reserved to set
	 */
	public void setReserved(java.lang.String reserved) {
		this.reserved = reserved;
	}

}