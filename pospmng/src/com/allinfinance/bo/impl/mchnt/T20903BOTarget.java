package com.allinfinance.bo.impl.mchnt;

import com.allinfinance.bo.mchnt.T20903BO;
import com.allinfinance.common.Constants;
import com.allinfinance.commquery.dao.ICommQueryDAO;

/**
 * Title:商户组别维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-10
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author
 * 
 * @version 1.0
 */
public class T20903BOTarget implements T20903BO {

	
	private ICommQueryDAO commQueryDAO;


	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	
	/*
	 * 删除计费信息
	 * 
	 * @see com.allinfinance.bo.mchnt.T20701BO#deleteArith(java.lang.String)
	 */
	public String deleteArith(String mchtNo) {
	
		
		
		String sql = "delete from TBL_MCHT_BASE_INF_TMP_TMP where trim(MCHT_NO) = '"
				+ mchtNo.trim() + "'";
		String sqlsettle = "delete from TBL_MCHT_SETTLE_INF_TMP_TMP where trim(MCHT_NO) = '"
				+ mchtNo.trim() + "'";
		commQueryDAO.excute(sql);
		commQueryDAO.excute(sqlsettle);
		
		

		return Constants.SUCCESS_CODE;
	}

}
