package com.allinfinance.bo.impl.base;

import java.util.List;

import com.allinfinance.bo.base.T10209BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.base.TblCardRouteDAO;
import com.allinfinance.po.TblCardRoute;
import com.allinfinance.po.TblCardRoutePK;

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
public class T10209BOTarget implements T10209BO {
	
	private TblCardRouteDAO tblCardRouteDAO;
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#add(com.allinfinance.po.TblCardRoute)
	 */
	public String add(TblCardRoute tblCardRoute) {
		tblCardRouteDAO.save(tblCardRoute);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#delete(com.allinfinance.po.TblCardRoute)
	 */
	public String delete(TblCardRoute tblCardRoute) {
		tblCardRouteDAO.delete(tblCardRoute);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#delete(com.allinfinance.po.TblCardRoutePK)
	 */
	public String delete(TblCardRoutePK id) {
		tblCardRouteDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#get(com.allinfinance.po.TblCardRoutePK)
	 */
	public TblCardRoute get(TblCardRoutePK id) {
		return tblCardRouteDAO.get(id);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#update(java.util.List)
	 */
	public String update(List<TblCardRoute> tblCardRouteList) {
		for(TblCardRoute tblCardRoute : tblCardRouteList) {
			tblCardRouteDAO.update(tblCardRoute);
		}
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblCardRouteDAO
	 */
	public TblCardRouteDAO getTblCardRouteDAO() {
		return tblCardRouteDAO;
	}

	/**
	 * @param tblCardRouteDAO the tblCardRouteDAO to set
	 */
	public void setTblCardRouteDAO(TblCardRouteDAO tblCardRouteDAO) {
		this.tblCardRouteDAO = tblCardRouteDAO;
	}
	
}
