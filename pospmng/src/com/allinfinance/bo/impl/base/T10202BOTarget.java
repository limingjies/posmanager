package com.allinfinance.bo.impl.base;

import java.util.List;

import com.allinfinance.bo.base.T10202BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.base.CstSysParamDAO;
import com.allinfinance.po.CstSysParam;
import com.allinfinance.po.CstSysParamPK;

/**
 * Title:系统参数BO
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
public class T10202BOTarget implements T10202BO {
	
	private CstSysParamDAO cstSysParamDAO;
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#add(com.allinfinance.po.CstSysParam)
	 */
	public String add(CstSysParam cstSysParam) {
		cstSysParamDAO.save(cstSysParam);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#delete(com.allinfinance.po.CstSysParam)
	 */
	public String delete(CstSysParam cstSysParam) {
		cstSysParamDAO.delete(cstSysParam);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#delete(com.allinfinance.po.CstSysParamPK)
	 */
	public String delete(CstSysParamPK id) {
		cstSysParamDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#get(com.allinfinance.po.CstSysParamPK)
	 */
	public CstSysParam get(CstSysParamPK id) {
		return cstSysParamDAO.get(id);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#update(java.util.List)
	 */
	public String update(List<CstSysParam> cstSysParamList) {
		for(CstSysParam cstSysParam : cstSysParamList) {
			cstSysParamDAO.update(cstSysParam);
		}
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the cstSysParamDAO
	 */
	public CstSysParamDAO getCstSysParamDAO() {
		return cstSysParamDAO;
	}

	/**
	 * @param cstSysParamDAO the cstSysParamDAO to set
	 */
	public void setCstSysParamDAO(CstSysParamDAO cstSysParamDAO) {
		this.cstSysParamDAO = cstSysParamDAO;
	}
	
}
