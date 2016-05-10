package com.allinfinance.po.mchnt;

import java.io.Serializable;

/**
 * This is an object that contains data related to the tbl_first_mcht_inf table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="tbl_first_mcht_inf"
 */

@SuppressWarnings("serial")
public class TblFirstMchtInf implements Serializable {
	
	public static String REF = "TblFirstMchtInf";
	public static String PROP_FIRST_MCHT_CD = "FirstMchtCd";
	public static String PROP_FIRST_MCHT_NM = "FirstMchtNm";
	public static String PROP_FIRST_TERM_ID = "FirstTermId";
	public static String PROP_ACQ_INST_ID = "AcqInstId";
	public static String PROP_MCHT_TP = "MchtTp";
	public static String PROP_FEE_VALUE = "FeeValue";
	public static String PROP_RESERVED = "Reserved";
	public static String PROP_RESERVED1 = "Reserved1";
	public static String PROP_RESERVED2 = "Reserved2";
	public static String PROP_UPDT_TIME = "UpdtTime";
	public static String PROP_CRT_TIME = "CrtTime";
	
	// constructors
	public TblFirstMchtInf () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public TblFirstMchtInf (java.lang.String firstMchtCd) {
		this.setFirstMchtCd(firstMchtCd);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public TblFirstMchtInf (
		java.lang.String firstMchtCd,
		java.lang.String firstMchtNm,
		java.lang.String firstTermId,
		java.lang.String acqInstId,
		java.lang.String mchtTp,
		java.lang.Double feeValue,
		java.lang.String reserved) {

		this.setFirstMchtCd(firstMchtCd);
		this.setFirstMchtNm(firstMchtNm);
		this.setFirstTermId(firstTermId);
		this.setAcqInstId(acqInstId);
		this.setMchtTp(mchtTp);
		this.setFeeValue(feeValue);
		this.setReserved(reserved);
		initialize();
	}

	protected void initialize () {}
	
	private int hashCode = Integer.MIN_VALUE;
	
	
	// primary key
	private java.lang.String firstMchtCd;	
	
	// fields
	private java.lang.String firstMchtNm;
	private java.lang.String firstTermId;
	private java.lang.String acqInstId;
	private java.lang.String mchtTp;
	private java.lang.Double feeValue;
	private java.lang.String reserved;
	private java.lang.String reserved1;
	private java.lang.String reserved2;
	private java.lang.String updtTime;
	private java.lang.String crtTime;
	
	public java.lang.String getReserved1() {
		return reserved1;
	}

	public void setReserved1(java.lang.String reserved1) {
		this.reserved1 = reserved1;
	}

	public java.lang.String getReserved2() {
		return reserved2;
	}

	public void setReserved2(java.lang.String reserved2) {
		this.reserved2 = reserved2;
	}

	public java.lang.String getUpdtTime() {
		return updtTime;
	}

	public void setUpdtTime(java.lang.String updtTime) {
		this.updtTime = updtTime;
	}

	public java.lang.String getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(java.lang.String crtTime) {
		this.crtTime = crtTime;
	}

	public java.lang.String getFirstMchtCd() {
		return firstMchtCd;
	}

	public void setFirstMchtCd(java.lang.String firstMchtCd) {
		this.firstMchtCd = firstMchtCd;
	}

	public java.lang.String getFirstMchtNm() {
		return firstMchtNm;
	}

	public void setFirstMchtNm(java.lang.String firstMchtNm) {
		this.firstMchtNm = firstMchtNm;
	}

	public java.lang.String getFirstTermId() {
		return firstTermId;
	}

	public void setFirstTermId(java.lang.String firstTermId) {
		this.firstTermId = firstTermId;
	}

	public java.lang.String getAcqInstId() {
		return acqInstId;
	}

	public void setAcqInstId(java.lang.String acqInstId) {
		this.acqInstId = acqInstId;
	}

	public java.lang.String getMchtTp() {
		return mchtTp;
	}

	public void setMchtTp(java.lang.String mchtTp) {
		this.mchtTp = mchtTp;
	}

	public java.lang.String getReserved() {
		return reserved;
	}

	public void setReserved(java.lang.String reserved) {
		this.reserved = reserved;
	}
	
	public java.lang.Double getFeeValue() {
		return feeValue;
	}

	public void setFeeValue(java.lang.Double feeValue) {
		this.feeValue = feeValue;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof TblFirstMchtInf)) return false;
		else {
			TblFirstMchtInf tblFirstMchtInf = (TblFirstMchtInf) obj;
			if (null == this.getFirstMchtCd() || null == tblFirstMchtInf.getFirstMchtCd()) return false;
			else return (this.getFirstMchtCd().equals(tblFirstMchtInf.getFirstMchtCd()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getFirstMchtCd()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getFirstMchtCd().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public String toString () {
		return super.toString();
	}

}