package com.allinfinance.bo.impl.route;

import java.util.List;

import com.allinfinance.bo.route.TblRouteMchtgBO;
import com.allinfinance.dao.iface.route.TblRouteMchtgDAO;
import com.allinfinance.po.route.TblRouteMchtg;

public class TblRouteMchtgBORoute implements TblRouteMchtgBO {
	private TblRouteMchtgDAO tblRouteMchtgDAO;
	public TblRouteMchtgDAO getTblRouteMchtgDAO() {
		return tblRouteMchtgDAO;
	}
	public void setTblRouteMchtgDAO(TblRouteMchtgDAO tblRouteMchtgDAO) {
		this.tblRouteMchtgDAO = tblRouteMchtgDAO;
	}

	@Override
	public void add(TblRouteMchtg routeMchtg) {
		tblRouteMchtgDAO.save(routeMchtg);
	}
	@Override
	public void update(TblRouteMchtg routeMchtg) {
		tblRouteMchtgDAO.update(routeMchtg);
	}
	@Override
	public void delete(TblRouteMchtg routeMchtg) {
		tblRouteMchtgDAO.delete(routeMchtg);
	}
	@Override
	public List<TblRouteMchtg> getAll() {
		return tblRouteMchtgDAO.getAll();
	}
	@Override
	public int getMax() {
		return tblRouteMchtgDAO.getMax();
	}
	@Override
	public String getMcht(Integer mchtGid) {
		return tblRouteMchtgDAO.getMcht(mchtGid);
	}
}
