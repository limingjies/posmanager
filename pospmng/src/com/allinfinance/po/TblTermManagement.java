package com.allinfinance.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TblTermManagement implements Serializable {
	
	
	// primary key
	private java.lang.String id;
	
	// fields
//	private java.lang.String brhId;
	private java.lang.String mechNo;
	private java.lang.String termType;
	private java.lang.String state;
	private java.lang.String manufacturer;
	private java.lang.String productCd;
	private java.lang.String terminalType;
	private java.lang.String batchNo;
	private java.lang.String storOprId;
	private java.lang.String storDate;
	private java.lang.String reciOprId;
	private java.lang.String reciDate;
	private java.lang.String backOprId;
	private java.lang.String bankDate;
	private java.lang.String invalidOprId;
	private java.lang.String invalidDate;
	private java.lang.String signOprId;
	private java.lang.String signDate;
	private java.lang.String misc;
	private java.lang.String lastUpdOprId;
	private java.lang.String lastUpdTs;
	private java.lang.String pinPad;
	
	
	
	/**机具终端绑定标志 0-未绑定   1-已绑定*/
	private java.lang.String bandFlag;
	/**预装机标志（纯机具下发密钥标志） 0-普通机  1-预装机*/
	private java.lang.String keyFlag;
	/**纯机具TMK(预装机TMK)*/
	private java.lang.String key;
	/**出库目的标志（下发机具目的标志）0-商户   1-渠道 */
	private java.lang.String mchtBrhFlag;
	/**商户号-商户名   或者  机构号-机构名*/
	private java.lang.String mchtBrhName;
	
	private java.lang.String reserved;
	private java.lang.String reserved1;

	/**
	 * @return the id
	 */
	public java.lang.String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}
	/**
	 * @return the mechNo
	 */
	public java.lang.String getMechNo() {
		return mechNo;
	}
	/**
	 * @param mechNo the mechNo to set
	 */
	public void setMechNo(java.lang.String mechNo) {
		this.mechNo = mechNo;
	}
	/**
	 * @return the termType
	 */
	public java.lang.String getTermType() {
		return termType;
	}
	/**
	 * @param termType the termType to set
	 */
	public void setTermType(java.lang.String termType) {
		this.termType = termType;
	}
	/**
	 * @return the state
	 */
	public java.lang.String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(java.lang.String state) {
		this.state = state;
	}
	/**
	 * @return the manufacturer
	 */
	public java.lang.String getManufacturer() {
		return manufacturer;
	}
	/**
	 * @param manufacturer the manufacturer to set
	 */
	public void setManufacturer(java.lang.String manufacturer) {
		this.manufacturer = manufacturer;
	}
	/**
	 * @return the productCd
	 */
	public java.lang.String getProductCd() {
		return productCd;
	}
	/**
	 * @param productCd the productCd to set
	 */
	public void setProductCd(java.lang.String productCd) {
		this.productCd = productCd;
	}
	/**
	 * @return the terminalType
	 */
	public java.lang.String getTerminalType() {
		return terminalType;
	}
	/**
	 * @param terminalType the terminalType to set
	 */
	public void setTerminalType(java.lang.String terminalType) {
		this.terminalType = terminalType;
	}
	/**
	 * @return the batchNo
	 */
	public java.lang.String getBatchNo() {
		return batchNo;
	}
	/**
	 * @param batchNo the batchNo to set
	 */
	public void setBatchNo(java.lang.String batchNo) {
		this.batchNo = batchNo;
	}
	/**
	 * @return the storOprId
	 */
	public java.lang.String getStorOprId() {
		return storOprId;
	}
	/**
	 * @param storOprId the storOprId to set
	 */
	public void setStorOprId(java.lang.String storOprId) {
		this.storOprId = storOprId;
	}
	/**
	 * @return the storDate
	 */
	public java.lang.String getStorDate() {
		return storDate;
	}
	/**
	 * @param storDate the storDate to set
	 */
	public void setStorDate(java.lang.String storDate) {
		this.storDate = storDate;
	}
	/**
	 * @return the reciOprId
	 */
	public java.lang.String getReciOprId() {
		return reciOprId;
	}
	/**
	 * @param reciOprId the reciOprId to set
	 */
	public void setReciOprId(java.lang.String reciOprId) {
		this.reciOprId = reciOprId;
	}
	/**
	 * @return the reciDate
	 */
	public java.lang.String getReciDate() {
		return reciDate;
	}
	/**
	 * @param reciDate the reciDate to set
	 */
	public void setReciDate(java.lang.String reciDate) {
		this.reciDate = reciDate;
	}
	/**
	 * @return the backOprId
	 */
	public java.lang.String getBackOprId() {
		return backOprId;
	}
	/**
	 * @param backOprId the backOprId to set
	 */
	public void setBackOprId(java.lang.String backOprId) {
		this.backOprId = backOprId;
	}
	/**
	 * @return the bankDate
	 */
	public java.lang.String getBankDate() {
		return bankDate;
	}
	/**
	 * @param bankDate the bankDate to set
	 */
	public void setBankDate(java.lang.String bankDate) {
		this.bankDate = bankDate;
	}
	/**
	 * @return the invalidOprId
	 */
	public java.lang.String getInvalidOprId() {
		return invalidOprId;
	}
	/**
	 * @param invalidOprId the invalidOprId to set
	 */
	public void setInvalidOprId(java.lang.String invalidOprId) {
		this.invalidOprId = invalidOprId;
	}
	/**
	 * @return the invalidDate
	 */
	public java.lang.String getInvalidDate() {
		return invalidDate;
	}
	/**
	 * @param invalidDate the invalidDate to set
	 */
	public void setInvalidDate(java.lang.String invalidDate) {
		this.invalidDate = invalidDate;
	}
	/**
	 * @return the signOprId
	 */
	public java.lang.String getSignOprId() {
		return signOprId;
	}
	/**
	 * @param signOprId the signOprId to set
	 */
	public void setSignOprId(java.lang.String signOprId) {
		this.signOprId = signOprId;
	}
	/**
	 * @return the signDate
	 */
	public java.lang.String getSignDate() {
		return signDate;
	}
	/**
	 * @param signDate the signDate to set
	 */
	public void setSignDate(java.lang.String signDate) {
		this.signDate = signDate;
	}
	/**
	 * @return the misc
	 */
	public java.lang.String getMisc() {
		return misc;
	}
	/**
	 * @param misc the misc to set
	 */
	public void setMisc(java.lang.String misc) {
		this.misc = misc;
	}
	/**
	 * @return the lastUpdOprId
	 */
	public java.lang.String getLastUpdOprId() {
		return lastUpdOprId;
	}
	/**
	 * @param lastUpdOprId the lastUpdOprId to set
	 */
	public void setLastUpdOprId(java.lang.String lastUpdOprId) {
		this.lastUpdOprId = lastUpdOprId;
	}

	public java.lang.String getLastUpdTs() {
		return lastUpdTs;
	}
	public void setLastUpdTs(java.lang.String lastUpdTs) {
		this.lastUpdTs = lastUpdTs;
	}
	/**
	 * @return the pinPad
	 */
	public java.lang.String getPinPad() {
		return pinPad;
	}
	/**
	 * @param pinPad the pinPad to set
	 */
	public void setPinPad(java.lang.String pinPad) {
		this.pinPad = pinPad;
	}
	public java.lang.String getBandFlag() {
		return bandFlag;
	}
	public void setBandFlag(java.lang.String bandFlag) {
		this.bandFlag = bandFlag;
	}
	public java.lang.String getKeyFlag() {
		return keyFlag;
	}
	public void setKeyFlag(java.lang.String keyFlag) {
		this.keyFlag = keyFlag;
	}
	public java.lang.String getKey() {
		return key;
	}
	public void setKey(java.lang.String key) {
		this.key = key;
	}
	public java.lang.String getMchtBrhFlag() {
		return mchtBrhFlag;
	}
	public void setMchtBrhFlag(java.lang.String mchtBrhFlag) {
		this.mchtBrhFlag = mchtBrhFlag;
	}
	public java.lang.String getMchtBrhName() {
		return mchtBrhName;
	}
	public void setMchtBrhName(java.lang.String mchtBrhName) {
		this.mchtBrhName = mchtBrhName;
	}
	public java.lang.String getReserved() {
		return reserved;
	}
	public void setReserved(java.lang.String reserved) {
		this.reserved = reserved;
	}
	public java.lang.String getReserved1() {
		return reserved1;
	}
	public void setReserved1(java.lang.String reserved1) {
		this.reserved1 = reserved1;
	}
	
}