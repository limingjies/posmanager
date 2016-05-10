package com.allinfinance.bo.impl.mchnt;

import com.allinfinance.bo.mchnt.T21002BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.mchnt.ShTblOprInfoDAO;
import com.allinfinance.po.ShTblOprInfo;
import com.allinfinance.po.ShTblOprInfoPk;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2014-1-17
 * 
 * 
 * 
 * @version 1.0
 */
public class T21002BOTarget implements T21002BO {

	private ShTblOprInfoDAO shTblOprInfoDAO;
	/* (non-Javadoc)
	 */
	
	public String add(ShTblOprInfo tblOprInfo) throws Exception {
		shTblOprInfoDAO.save(tblOprInfo);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 */
	
	public String update(ShTblOprInfo tblOprInfo) throws Exception {
		shTblOprInfoDAO.update(tblOprInfo);
		return Constants.SUCCESS_CODE;
	}
	/**
	 * 用户注销
	 */
	public String delete(ShTblOprInfo tblOprInfo) throws Exception {
		//tblOprInfo.setOprStatus(TblOprInfoConstants.STATUS_OK);
		shTblOprInfoDAO.update(tblOprInfo);
		return Constants.SUCCESS_CODE;
	}
	
	/* (non-Javadoc)
	 */
	
	public ShTblOprInfo get(ShTblOprInfoPk key) throws Exception {
		return shTblOprInfoDAO.get(key);
	}

	public ShTblOprInfoDAO getShTblOprInfoDAO() {
		return shTblOprInfoDAO;
	}

	public void setShTblOprInfoDAO(ShTblOprInfoDAO shTblOprInfoDAO) {
		this.shTblOprInfoDAO = shTblOprInfoDAO;
	}
	


}
