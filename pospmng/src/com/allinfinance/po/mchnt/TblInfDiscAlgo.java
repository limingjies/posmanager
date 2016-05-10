package com.allinfinance.po.mchnt;




public class TblInfDiscAlgo extends BaseTblInfDiscAlgo {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public TblInfDiscAlgo () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public TblInfDiscAlgo (TblInfDiscAlgoPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public TblInfDiscAlgo (
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

		super (
			id,
			operRslt,
			operand1,
			operator1,
			operand2,
			operator2,
			operand3,
			recUpdUsrId,
			recUpdTs,
			recCrtTs,
			stxnNum);
	}

/*[CONSTRUCTOR MARKER END]*/


}