package com.allinfinance.dao.iface.term;


public interface TblTermManagementAppMainDAO {

	
	public com.allinfinance.po.TblTermManagementAppMain get(String key);

	
	public void save(com.allinfinance.po.TblTermManagementAppMain tblTermManagementAppMain);

	
	public void update(com.allinfinance.po.TblTermManagementAppMain tblTermManagementAppMain);

	
	public void delete(String id);
}
