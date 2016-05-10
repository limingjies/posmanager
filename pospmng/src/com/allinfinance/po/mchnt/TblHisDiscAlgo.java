package com.allinfinance.po.mchnt;

import com.allinfinance.po.mchnt.base.BaseTblHisDiscAlgo;



public class TblHisDiscAlgo extends BaseTblHisDiscAlgo {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public TblHisDiscAlgo () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public TblHisDiscAlgo (TblHisDiscAlgoPK id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public TblHisDiscAlgo (
		TblHisDiscAlgoPK id,
		java.math.BigDecimal minFee,
		java.math.BigDecimal maxFee,
		java.math.BigDecimal floorMount,
		java.math.BigDecimal upperMount,
		java.lang.Integer flag,
		java.math.BigDecimal feeValue,
		java.lang.String recUpdUsrId,
		java.lang.String recUpdTs,
		java.lang.String recCrtTs,
		java.lang.String stxnNum,
		java.lang.String cardType,
		java.lang.String misc1,
		java.lang.String misc2) {

		super (
			id,
			minFee,
			maxFee,
			floorMount,
			upperMount,
			flag,
			feeValue,
			recUpdUsrId,
			recUpdTs,
			recCrtTs,
			stxnNum,
			cardType,
			misc1,
			misc2);
	}

/*[CONSTRUCTOR MARKER END]*/


}