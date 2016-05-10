package com.allinfinance.bo.impl.route;

import com.allinfinance.bo.route.TblRouteUpbrh2BO;
import com.allinfinance.dao.iface.route.TblRouteUpbrh2DAO;
import com.allinfinance.po.route.TblRouteUpbrh2;

public class TblRouteUpbrh2BORoute implements TblRouteUpbrh2BO {
	private TblRouteUpbrh2DAO tblRouteUpbrh2DAO;

	@Override
	public TblRouteUpbrh2 get(String key) {
		return tblRouteUpbrh2DAO.get(key);
	}

	public void update(TblRouteUpbrh2 tblRouteUpbrh2) {
		tblRouteUpbrh2DAO.update(tblRouteUpbrh2);
	}

	public TblRouteUpbrh2DAO getTblRouteUpbrh2DAO() {
		return tblRouteUpbrh2DAO;
	}

	public void setTblRouteUpbrh2DAO(TblRouteUpbrh2DAO tblRouteUpbrh2DAO) {
		this.tblRouteUpbrh2DAO = tblRouteUpbrh2DAO;
	}
	
	
}
