package com.allinfinance.po;

import java.io.Serializable;



public class TblTermZmkInf implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String REF = "TblTermZmkInf";
	public static String PROP_MISC2 = "Misc2";
	public static String PROP_MISC1 = "Misc1";
	public static String PROP_KEY_INDEX = "KeyIndex";
	public static String PROP_ID = "Id";
	public static String PROP_RANDOM = "Random";
	
	
	/**
	 * 
	 */
	public TblTermZmkInf() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param tblTermZmkInfPK
	 */
	public TblTermZmkInf(TblTermZmkInfPK tblTermZmkInfPK) {
		this.id = tblTermZmkInfPK;
	}

	protected void initialize () {}

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.allinfinance.po.TblTermZmkInfPK id;

	// fields
	private java.lang.String keyIndex;
	private java.lang.String random;
	private java.lang.String misc1;
	private java.lang.String misc2;
	
	private java.lang.String recOprId;
	private java.lang.String recUpdOpr;
	private java.lang.String recCrtTs;
	private java.lang.String RecUpdTs;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.allinfinance.po.TblTermZmkInfPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.allinfinance.po.TblTermZmkInfPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}


	public java.lang.String getKeyIndex() {
		return keyIndex;
	}

	public void setKeyIndex(java.lang.String keyIndex) {
		this.keyIndex = keyIndex;
	}

	/**
	 * Return the value associated with the column: random
	 */
	public java.lang.String getRandom () {
		return random;
	}

	/**
	 * Set the value related to the column: random
	 * @param random the random value
	 */
	public void setRandom (java.lang.String random) {
		this.random = random;
	}



	/**
	 * Return the value associated with the column: misc_1
	 */
	public java.lang.String getMisc1 () {
		return misc1;
	}

	/**
	 * Set the value related to the column: misc_1
	 * @param misc1 the misc_1 value
	 */
	public void setMisc1 (java.lang.String misc1) {
		this.misc1 = misc1;
	}



	/**
	 * Return the value associated with the column: misc_2
	 */
	public java.lang.String getMisc2 () {
		return misc2;
	}

	/**
	 * Set the value related to the column: misc_2
	 * @param misc2 the misc_2 value
	 */
	public void setMisc2 (java.lang.String misc2) {
		this.misc2 = misc2;
	}

	public java.lang.String getRecOprId() {
		return recOprId;
	}

	public void setRecOprId(java.lang.String recOprId) {
		this.recOprId = recOprId;
	}

	public java.lang.String getRecUpdOpr() {
		return recUpdOpr;
	}

	public void setRecUpdOpr(java.lang.String recUpdOpr) {
		this.recUpdOpr = recUpdOpr;
	}

	public java.lang.String getRecCrtTs() {
		return recCrtTs;
	}

	public void setRecCrtTs(java.lang.String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	public java.lang.String getRecUpdTs() {
		return RecUpdTs;
	}

	public void setRecUpdTs(java.lang.String recUpdTs) {
		RecUpdTs = recUpdTs;
	}

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.allinfinance.po.TblTermZmkInf)) return false;
		else {
			com.allinfinance.po.TblTermZmkInf tblTermZmkInf = (com.allinfinance.po.TblTermZmkInf) obj;
			if (null == this.getId() || null == tblTermZmkInf.getId()) return false;
			else return (this.getId().equals(tblTermZmkInf.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}
}