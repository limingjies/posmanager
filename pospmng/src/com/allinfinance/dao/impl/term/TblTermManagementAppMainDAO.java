package com.allinfinance.dao.impl.term;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblTermManagementAppMain;

public class TblTermManagementAppMainDAO extends _RootDAO<com.allinfinance.po.TblTermManagementAppMain> implements com.allinfinance.dao.iface.term.TblTermManagementAppMainDAO{

	
	
	
	
	@Override
	protected Class<com.allinfinance.po.TblTermManagementAppMain> getReferenceClass() {
		// TODO Auto-generated method stub
		return com.allinfinance.po.TblTermManagementAppMain.class;
	}


	public TblTermManagementAppMain load(String key) {
		return (com.allinfinance.po.TblTermManagementAppMain) load(getReferenceClass(),
				key);
	}
	
	public void delete(String id) {
		// TODO Auto-generated method stub
		super.delete(load(id));
	}

	public TblTermManagementAppMain get(String key) {
		// TODO Auto-generated method stub
		return (com.allinfinance.po.TblTermManagementAppMain) get(getReferenceClass(),
				key);
	}

	public void save(TblTermManagementAppMain tblTermManagementAppMain) {
		// TODO Auto-generated method stub
		super.save(tblTermManagementAppMain);
	}

	public void update(TblTermManagementAppMain tblTermManagementAppMain) {
		// TODO Auto-generated method stub
		super.update(tblTermManagementAppMain);
	}

}
