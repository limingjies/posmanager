package com.allinfinance.bo.impl.base;

import java.util.List;

import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.base.TblOprInfoDAO;
import com.allinfinance.po.TblOprInfo;

/**
 * Title:操作员BO实现
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-9
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class T10401BOTarget implements com.allinfinance.bo.base.T10401BO {
	
	private TblOprInfoDAO tblOprInfoDAO;
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10401BO#add(com.allinfinance.po.TblOprInfo)
	 */
	public String add(TblOprInfo tblOprInfo) {
		tblOprInfoDAO.save(tblOprInfo);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10401BO#delete(com.allinfinance.po.TblOprInfo)
	 */
	public String delete(TblOprInfo tblOprInfo) {
		tblOprInfoDAO.delete(tblOprInfo);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10401BO#delete(java.lang.String)
	 */
	public String delete(String oprId) {
		tblOprInfoDAO.delete(oprId);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10401BO#get(java.lang.String)
	 */
	public TblOprInfo get(String oprId) {
		return tblOprInfoDAO.get(oprId);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10401BO#update(java.util.List)
	 */
	public String update(List<TblOprInfo> tblOprInfoList) {
		for(TblOprInfo tblOprInfo : tblOprInfoList) {
			update(tblOprInfo);
		}
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblOprInfoDAO
	 */
	public TblOprInfoDAO getTblOprInfoDAO() {
		return tblOprInfoDAO;
	}

	/**
	 * @param tblOprInfoDAO the tblOprInfoDAO to set
	 */
	public void setTblOprInfoDAO(TblOprInfoDAO tblOprInfoDAO) {
		this.tblOprInfoDAO = tblOprInfoDAO;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10401BO#update(com.allinfinance.po.TblOprInfo)
	 */
	public String update(TblOprInfo tblOprInfo) {
		this.tblOprInfoDAO.update(tblOprInfo);
		return Constants.SUCCESS_CODE;
	}
	
}
