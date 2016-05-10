package com.allinfinance.po.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_TXN_NAME table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_TXN_NAME"
 */

public abstract class BaseTblTxnName  implements Serializable {

	public static String REF = "TblTxnName";
	public static String PROP_ID = "Id";
	public static String PROP_TXN_NAME = "TxnName";
	public static String PROP_DC_FLAG = "DcFlag";
	public static String PROP_TXN_DSP = "TxnDsp";


	// constructors
	public BaseTblTxnName () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblTxnName (java.lang.String id) {
		this.setId(id);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String txnName;
	private java.lang.String txnDsp;
	private java.lang.String dcFlag;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="sequence"
     *  column="TXN_NUM"
     */
	public java.lang.String getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: TXN_NAME
	 */
	public java.lang.String getTxnName () {
		return txnName;
	}

	/**
	 * Set the value related to the column: TXN_NAME
	 * @param txnName the TXN_NAME value
	 */
	public void setTxnName (java.lang.String txnName) {
		this.txnName = txnName;
	}



	/**
	 * Return the value associated with the column: TXN_DSP
	 */
	public java.lang.String getTxnDsp () {
		return txnDsp;
	}

	/**
	 * Set the value related to the column: TXN_DSP
	 * @param txnDsp the TXN_DSP value
	 */
	public void setTxnDsp (java.lang.String txnDsp) {
		this.txnDsp = txnDsp;
	}



	/**
	 * Return the value associated with the column: DC_FLAG
	 */
	public java.lang.String getDcFlag () {
		return dcFlag;
	}

	/**
	 * Set the value related to the column: DC_FLAG
	 * @param dcFlag the DC_FLAG value
	 */
	public void setDcFlag (java.lang.String dcFlag) {
		this.dcFlag = dcFlag;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.allinfinance.po.TblTxnName)) return false;
		else {
			com.allinfinance.po.TblTxnName tblTxnName = (com.allinfinance.po.TblTxnName) obj;
			if (null == this.getId() || null == tblTxnName.getId()) return false;
			else return (this.getId().equals(tblTxnName.getId()));
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