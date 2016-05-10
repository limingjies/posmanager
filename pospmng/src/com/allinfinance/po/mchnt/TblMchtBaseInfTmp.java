package com.allinfinance.po.mchnt;

import com.allinfinance.po.mchnt.base.BaseTblMchtBaseInfTmp;

public class TblMchtBaseInfTmp extends BaseTblMchtBaseInfTmp {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public TblMchtBaseInfTmp () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public TblMchtBaseInfTmp (java.lang.String mchtNo) {
		super(mchtNo);
	}

	/**
	 * Constructor for required fields
	 */
	public TblMchtBaseInfTmp (
		java.lang.String mchtNo,
		java.lang.String mchtNm,
		java.lang.String addr,
		java.lang.String mcc,
		java.lang.String licenceNo,
		java.lang.String licenceEndDate,
		java.lang.String bankLicenceNo,
		java.lang.String faxNo,
		java.lang.String contact,
		java.lang.String commEmail,
		java.lang.String manager,
		java.lang.String identityNo,
		java.lang.String managerTel,
		java.lang.String openTime,
		java.lang.String closeTime,
		java.lang.String recUpdTs,
		java.lang.String recCrtTs,

		java.lang.String compNm,
		java.lang.String compaddr,
		java.lang.String finacontact,
		java.lang.String finacommTel,
		java.lang.String finacommEmail,
		java.lang.String busInfo,
		java.lang.String busArea,
		java.lang.String monaveTrans,
		java.lang.String sigaveTrans,
		java.lang.String contstartDate,
		java.lang.String contendDate) {

		super (
			mchtNo,
			mchtNm,
			addr,
			mcc,
			licenceNo,
			licenceEndDate,
			bankLicenceNo,
			faxNo,
			contact,
			commEmail,
			manager,
			identityNo,
			managerTel,
			openTime,
			closeTime,
			recUpdTs,
			recCrtTs,
			
			compNm,
			compaddr,
			finacontact,
			finacommTel,
			finacommEmail,
			busInfo,
			busArea,
			monaveTrans,
			sigaveTrans,
			contstartDate,
			contendDate);
	}

/*[CONSTRUCTOR MARKER END]*/


}