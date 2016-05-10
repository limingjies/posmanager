package com.allinfinance.dao.impl.risk;

import com.allinfinance.dao._RootDAO;

public class TblRiskInfUpdLogDAO extends
		_RootDAO<com.allinfinance.po.TblRiskInfUpdLog> implements
		com.allinfinance.dao.iface.risk.TblRiskInfUpdLogDAO {

	public TblRiskInfUpdLogDAO() {
	}

	public java.lang.String save(com.allinfinance.po.TblRiskInfUpdLog tblRiskInfUpdLog) {
		return (java.lang.String) super.save(tblRiskInfUpdLog);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.allinfinance.dao._RootDAO#getReferenceClass()
	 */
	@Override
	public Class<com.allinfinance.po.TblRiskInfUpdLog> getReferenceClass() {
		return com.allinfinance.po.TblRiskInfUpdLog.class;
	}

}