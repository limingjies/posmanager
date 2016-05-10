package com.allinfinance.po;

import java.io.Serializable;



public class TblBrhApproveProcess implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String REF = "TblBrhApproveProcess";
	public static String PROP_OPR_ID = "OprId";
	public static String PROP_OPR_INFO = "OprInfo";
	public static String PROP_OPR_TYPE = "OprType";
	public static String PROP_ID = "Id";

	protected void initialize () {}
	
	
	/**
	 * 
	 */
	public TblBrhApproveProcess() {
		super();
		// TODO Auto-generated constructor stub
	}


	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private com.allinfinance.po.TblBrhApproveProcessPK id;

	// fields
	private java.lang.String oprId;
	private java.lang.String oprType;
	private java.lang.String oprInfo;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public com.allinfinance.po.TblBrhApproveProcessPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (com.allinfinance.po.TblBrhApproveProcessPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}


	/**
	 * Return the value associated with the column: opr_id
	 */
	public java.lang.String getOprId () {
		return oprId;
	}

	/**
	 * Set the value related to the column: opr_id
	 * @param oprId the opr_id value
	 */
	public void setOprId (java.lang.String oprId) {
		this.oprId = oprId;
	}


	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.allinfinance.po.TblBrhApproveProcess)) return false;
		else {
			com.allinfinance.po.TblBrhApproveProcess tblMchntRefuse = (com.allinfinance.po.TblBrhApproveProcess) obj;
			if (null == this.getId() || null == tblMchntRefuse.getId()) return false;
			else return (this.getId().equals(tblMchntRefuse.getId()));
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


	public java.lang.String getOprType() {
		return oprType;
	}


	public void setOprType(java.lang.String oprType) {
		this.oprType = oprType;
	}


	public java.lang.String getOprInfo() {
		return oprInfo;
	}


	public void setOprInfo(java.lang.String oprInfo) {
		this.oprInfo = oprInfo;
	}
}