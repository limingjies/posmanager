package com.allinfinance.po.mchnt;

import java.io.Serializable;

public class TblHisDiscAlgoHis implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @return the id
	 */
	public TblHisDiscAlgoHisPK getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(TblHisDiscAlgoHisPK id) {
		this.id = id;
	}

	/**
	 * @return the minFee
	 */
	public java.math.BigDecimal getMinFee() {
		return minFee;
	}

	/**
	 * @param minFee the minFee to set
	 */
	public void setMinFee(java.math.BigDecimal minFee) {
		this.minFee = minFee;
	}

	/**
	 * @return the maxFee
	 */
	public java.math.BigDecimal getMaxFee() {
		return maxFee;
	}

	/**
	 * @param maxFee the maxFee to set
	 */
	public void setMaxFee(java.math.BigDecimal maxFee) {
		this.maxFee = maxFee;
	}

	/**
	 * @return the floorMount
	 */
	public java.math.BigDecimal getFloorMount() {
		return floorMount;
	}

	/**
	 * @param floorMount the floorMount to set
	 */
	public void setFloorMount(java.math.BigDecimal floorMount) {
		this.floorMount = floorMount;
	}

	/**
	 * @return the upperMount
	 */
	public java.math.BigDecimal getUpperMount() {
		return upperMount;
	}

	/**
	 * @param upperMount the upperMount to set
	 */
	public void setUpperMount(java.math.BigDecimal upperMount) {
		this.upperMount = upperMount;
	}

	/**
	 * @return the flag
	 */
	public java.lang.Integer getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public void setFlag(java.lang.Integer flag) {
		this.flag = flag;
	}

	/**
	 * @return the feeValue
	 */
	public java.math.BigDecimal getFeeValue() {
		return feeValue;
	}

	/**
	 * @param feeValue the feeValue to set
	 */
	public void setFeeValue(java.math.BigDecimal feeValue) {
		this.feeValue = feeValue;
	}

	/**
	 * @return the recUpdUsrId
	 */
	public java.lang.String getRecUpdUsrId() {
		return recUpdUsrId;
	}

	/**
	 * @param recUpdUsrId the recUpdUsrId to set
	 */
	public void setRecUpdUsrId(java.lang.String recUpdUsrId) {
		this.recUpdUsrId = recUpdUsrId;
	}

	/**
	 * @return the recUpdTs
	 */
	public java.lang.String getRecUpdTs() {
		return recUpdTs;
	}

	/**
	 * @param recUpdTs the recUpdTs to set
	 */
	public void setRecUpdTs(java.lang.String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}

	/**
	 * @return the recCrtTs
	 */
	public java.lang.String getRecCrtTs() {
		return recCrtTs;
	}

	/**
	 * @param recCrtTs the recCrtTs to set
	 */
	public void setRecCrtTs(java.lang.String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	/**
	 * @return the txnNum
	 */
	public java.lang.String getTxnNum() {
		return txnNum;
	}

	/**
	 * @param txnNum the txnNum to set
	 */
	public void setTxnNum(java.lang.String txnNum) {
		this.txnNum = txnNum;
	}

	/**
	 * @return the cardType
	 */
	public java.lang.String getCardType() {
		return cardType;
	}

	/**
	 * @param cardType the cardType to set
	 */
	public void setCardType(java.lang.String cardType) {
		this.cardType = cardType;
	}

	/**
	 * @return the misc1
	 */
	public java.lang.String getMisc1() {
		return misc1;
	}

	/**
	 * @param misc1 the misc1 to set
	 */
	public void setMisc1(java.lang.String misc1) {
		this.misc1 = misc1;
	}

	/**
	 * @return the misc2
	 */
	public java.lang.String getMisc2() {
		return misc2;
	}

	/**
	 * @param misc2 the misc2 to set
	 */
	public void setMisc2(java.lang.String misc2) {
		this.misc2 = misc2;
	}
	private TblHisDiscAlgoHisPK id;
	private java.math.BigDecimal minFee;
	private java.math.BigDecimal maxFee;
	private java.math.BigDecimal floorMount;
	private java.math.BigDecimal upperMount;
	private java.lang.Integer flag;
	private java.math.BigDecimal feeValue;
	private java.lang.String recUpdUsrId;
	private java.lang.String recUpdTs;
	private java.lang.String recCrtTs;
	private java.lang.String txnNum;
	private java.lang.String cardType;
	private java.lang.String misc1;
	private java.lang.String misc2;
}
