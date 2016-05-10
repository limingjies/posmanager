package com.allinfinance.bo.impl.base;

import java.util.List;

import com.allinfinance.bo.base.T10201BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.base.TblCityCodeDAO;
import com.allinfinance.po.TblCityCode;

/**
 * Title:地区码BO实现
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
public class T10201BOTarget implements T10201BO {
	
	private TblCityCodeDAO tblCityCodeDAO;
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10203BO#add(com.allinfinance.po.TblCityCode)
	 */
	public String add(TblCityCode tblCityCode) {
		tblCityCodeDAO.save(tblCityCode);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10203BO#delete(com.allinfinance.po.TblCityCode)
	 */
	public String delete(TblCityCode tblCityCode) {
		tblCityCodeDAO.delete(tblCityCode);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10203BO#delete(java.lang.String)
	 */
	public String delete(String id) {
		tblCityCodeDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10203BO#get(com.allinfinance.po.TblCityCode)
	 */
	public TblCityCode get(String id) {
		return tblCityCodeDAO.get(id);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10203BO#update(java.util.List)
	 */
	public String update(List<TblCityCode> tblCityCodeList) {
		for(TblCityCode tblCityCode : tblCityCodeList) {
			tblCityCodeDAO.update(tblCityCode);
		}
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblCityCodeDAO
	 */
	public TblCityCodeDAO getTblCityCodeDAO() {
		return tblCityCodeDAO;
	}

	/**
	 * @param tblCityCodeDAO the tblCityCodeDAO to set
	 */
	public void setTblCityCodeDAO(TblCityCodeDAO tblCityCodeDAO) {
		this.tblCityCodeDAO = tblCityCodeDAO;
	}
	
}
