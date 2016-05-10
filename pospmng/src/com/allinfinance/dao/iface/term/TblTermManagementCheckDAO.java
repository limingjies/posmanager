package com.allinfinance.dao.iface.term;

import com.allinfinance.po.TblTermManagementCheckPK;



public interface TblTermManagementCheckDAO {

	public com.allinfinance.po.TblTermManagementCheck get(TblTermManagementCheckPK key);

	public com.allinfinance.po.TblTermManagementCheck load(TblTermManagementCheckPK key);

	
	public void save(com.allinfinance.po.TblTermManagementCheck tblTermManagementCheck);

	
	public void update(com.allinfinance.po.TblTermManagementCheck tblTermManagementCheck);

	public void delete(com.allinfinance.po.TblTermManagementCheck tblTermManagementCheck);
	
	public void delete(TblTermManagementCheckPK id);

	
}
