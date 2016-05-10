package com.allinfinance.po.mchnt;

import java.lang.Comparable;
import java.io.Serializable;


/**
 * This is an object that contains data related to the TBL_INF_DISC_ALGO table.
 * Do not modify this class because it will be overwritten if the configuration file
 * related to this class is modified.
 *
 * @hibernate.class
 *  table="TBL_INF_DISC_ALGO"
 */

public abstract class BaseTblInfDiscAlgo  implements Comparable, Serializable {

	public static String REF = "TblInfDiscAlgo";
	public static String PROP_OPERAND3 = "Operand3";
	public static String PROP_OPERATOR2 = "Operator2";
	public static String PROP_OPER_RSLT = "OperRslt";
	public static String PROP_OPERAND1 = "Operand1";
	public static String PROP_OPERAND2 = "Operand2";
	public static String PROP_OPERATOR1 = "Operator1";
	public static String PROP_REC_CRT_TS = "RecCrtTs";
	public static String PROP_ID = "Id";
	public static String PROP_REC_UPD_USR_ID = "RecUpdUsrId";
	public static String PROP_REC_UPD_TS = "RecUpdTs";


	// constructors
	public BaseTblInfDiscAlgo () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblInfDiscAlgo (TblInfDiscAlgoPK id) {
		this.setId(id);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseTblInfDiscAlgo (
		TblInfDiscAlgoPK id,
		java.lang.Integer operRslt,
		java.math.BigDecimal operand1,
		java.lang.String operator1,
		java.math.BigDecimal operand2,
		java.lang.String operator2,
		java.math.BigDecimal operand3,
		java.lang.String recUpdUsrId,
		java.lang.String recUpdTs,
		java.lang.String recCrtTs,
		java.lang.String stxnNum) {

		this.setId(id);
		this.setOperRslt(operRslt);
		this.setOperand1(operand1);
		this.setOperator1(operator1);
		this.setOperand2(operand2);
		this.setOperator2(operator2);
		this.setOperand3(operand3);
		this.setRecUpdUsrId(recUpdUsrId);
		this.setRecUpdTs(recUpdTs);
		this.setRecCrtTs(recCrtTs);
		this.setStxnNum(stxnNum);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private TblInfDiscAlgoPK id;

	// fields
	private java.lang.Integer operRslt;
	private java.math.BigDecimal operand1;
	private java.lang.String operator1;
	private java.math.BigDecimal operand2;
	private java.lang.String operator2;
	private java.math.BigDecimal operand3;
	private java.lang.String recUpdUsrId;
	private java.lang.String recUpdTs;
	private java.lang.String recCrtTs;
	private java.lang.String stxnNum;



	public java.lang.String getStxnNum() {
		return stxnNum;
	}

	public void setStxnNum(java.lang.String stxnNum) {
		this.stxnNum = stxnNum;
	}

	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     */
	public TblInfDiscAlgoPK getId () {
		return id;
	}

	/**
	 * Set the unique identifier of this class
	 * @param id the new ID
	 */
	public void setId (TblInfDiscAlgoPK id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}




	/**
	 * Return the value associated with the column: OPER_RSLT
	 */
	public java.lang.Integer getOperRslt () {
		return operRslt;
	}

	/**
	 * Set the value related to the column: OPER_RSLT
	 * @param operRslt the OPER_RSLT value
	 */
	public void setOperRslt (java.lang.Integer operRslt) {
		this.operRslt = operRslt;
	}



	/**
	 * Return the value associated with the column: OPERAND1
	 */
	public java.math.BigDecimal getOperand1 () {
		return operand1;
	}

	/**
	 * Set the value related to the column: OPERAND1
	 * @param operand1 the OPERAND1 value
	 */
	public void setOperand1 (java.math.BigDecimal operand1) {
		this.operand1 = operand1;
	}



	/**
	 * Return the value associated with the column: OPERATOR1
	 */
	public java.lang.String getOperator1 () {
		return operator1;
	}

	/**
	 * Set the value related to the column: OPERATOR1
	 * @param operator1 the OPERATOR1 value
	 */
	public void setOperator1 (java.lang.String operator1) {
		this.operator1 = operator1;
	}



	/**
	 * Return the value associated with the column: OPERAND2
	 */
	public java.math.BigDecimal getOperand2 () {
		return operand2;
	}

	/**
	 * Set the value related to the column: OPERAND2
	 * @param operand2 the OPERAND2 value
	 */
	public void setOperand2 (java.math.BigDecimal operand2) {
		this.operand2 = operand2;
	}



	/**
	 * Return the value associated with the column: OPERATOR2
	 */
	public java.lang.String getOperator2 () {
		return operator2;
	}

	/**
	 * Set the value related to the column: OPERATOR2
	 * @param operator2 the OPERATOR2 value
	 */
	public void setOperator2 (java.lang.String operator2) {
		this.operator2 = operator2;
	}



	/**
	 * Return the value associated with the column: OPERAND3
	 */
	public java.math.BigDecimal getOperand3 () {
		return operand3;
	}

	/**
	 * Set the value related to the column: OPERAND3
	 * @param operand3 the OPERAND3 value
	 */
	public void setOperand3 (java.math.BigDecimal operand3) {
		this.operand3 = operand3;
	}



	/**
	 * Return the value associated with the column: REC_UPD_USR_ID
	 */
	public java.lang.String getRecUpdUsrId () {
		return recUpdUsrId;
	}

	/**
	 * Set the value related to the column: REC_UPD_USR_ID
	 * @param recUpdUsrId the REC_UPD_USR_ID value
	 */
	public void setRecUpdUsrId (java.lang.String recUpdUsrId) {
		this.recUpdUsrId = recUpdUsrId;
	}



	/**
	 * Return the value associated with the column: REC_UPD_TS
	 */
	public java.lang.String getRecUpdTs () {
		return recUpdTs;
	}

	/**
	 * Set the value related to the column: REC_UPD_TS
	 * @param recUpdTs the REC_UPD_TS value
	 */
	public void setRecUpdTs (java.lang.String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}



	/**
	 * Return the value associated with the column: REC_CRT_TS
	 */
	public java.lang.String getRecCrtTs () {
		return recCrtTs;
	}

	/**
	 * Set the value related to the column: REC_CRT_TS
	 * @param recCrtTs the REC_CRT_TS value
	 */
	public void setRecCrtTs (java.lang.String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}





	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof TblInfDiscAlgo)) return false;
		else {
			TblInfDiscAlgo tblInfDiscAlgo = (TblInfDiscAlgo) obj;
			if (null == this.getId() || null == tblInfDiscAlgo.getId()) return false;
			else return (this.getId().equals(tblInfDiscAlgo.getId()));
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

	public int compareTo (Object obj) {
		if (obj.hashCode() > hashCode()) return 1;
		else if (obj.hashCode() < hashCode()) return -1;
		else return 0;
	}

	public String toString () {
		return super.toString();
	}


}