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
public class TblInsMchtInf implements Serializable {
	
	public static String REF = "TblInsMchtInf";
//	public static String PROP_ID = "Id";
	public static String PROP_MCHT_NO = "MchtNo";
	public static String PROP_FIRST_MCHT_CD = "FirstMchtCd";
	public static String PROP_INS_ID_CD = "InsIdCd";
	public static String PROP_FIRST_TERM_ID = "FirstTermId";
	public static String PROP_ACQ_INST_ID = "AcqInstId";
	public static String PROP_MISC = "Misc";
	public static String PROP_INSERT_TIME = "InsertTime";
	public static String PROP_UPDATE_TIME = "UpdateTime";
	
	// constructors
	public TblInsMchtInf () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public TblInsMchtInf (java.lang.String mchtNo) {
		this.setMchtNo(mchtNo);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public TblInsMchtInf (
		java.lang.String mchtNo,
		java.lang.String firstTermId,
		java.lang.String insIdCd,
		java.lang.String acqInstId,
		java.lang.String misc,
		java.lang.String insertTime,
		java.lang.String updateTime) {

		this.setMchtNo(mchtNo);
		this.setFirstTermId(firstTermId);
		this.setInsIdCd(insIdCd);
		this.setAcqInstId(acqInstId);
		this.setMisc(misc);
		this.setInsertTime(insertTime);
		this.setUpdateTime(updateTime);
		initialize();
	}

	protected void initialize () {}
	
	private int hashCode = Integer.MIN_VALUE;
	// primary key
//	private TblInsMchtInfPK id;
	private java.lang.String mchtNo;
	
	private java.lang.String firstMchtCd;
	private java.lang.String insIdCd;
	private java.lang.String firstTermId;
	private java.lang.String misc;
	private java.lang.String acqInstId;
	private java.lang.String insertTime;
	private java.lang.String updateTime;
	
	public java.lang.String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(java.lang.String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public java.lang.String getFirstMchtCd() {
		return firstMchtCd;
	}

	public void setFirstMchtCd(java.lang.String firstMchtCd) {
		this.firstMchtCd = firstMchtCd;
	}

	public java.lang.String getInsIdCd() {
		return insIdCd;
	}

	public void setInsIdCd(java.lang.String insIdCd) {
		this.insIdCd = insIdCd;
	}

	public java.lang.String getFirstTermId() {
		return firstTermId;
	}

	public void setFirstTermId(java.lang.String firstTermId) {
		this.firstTermId = firstTermId;
	}

	public java.lang.String getMisc() {
		return misc;
	}

	public void setMisc(java.lang.String misc) {
		this.misc = misc;
	}

	public java.lang.String getAcqInstId() {
		return acqInstId;
	}

	public void setAcqInstId(java.lang.String acqInstId) {
		this.acqInstId = acqInstId;
	}

	public java.lang.String getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(java.lang.String insertTime) {
		this.insertTime = insertTime;
	}

	public java.lang.String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.lang.String updateTime) {
		this.updateTime = updateTime;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof TblInsMchtInf)) return false;
		else {
			TblInsMchtInf tblInsMchtInf = (TblInsMchtInf) obj;
			if (null == this.getMchtNo() || null == tblInsMchtInf.getMchtNo()) return false;
			else return (this.getMchtNo().equals(tblInsMchtInf.getMchtNo()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getMchtNo()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getMchtNo().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public String toString () {
		return super.toString();
	}

}