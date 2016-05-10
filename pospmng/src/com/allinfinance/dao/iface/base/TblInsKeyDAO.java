package com.allinfinance.dao.iface.base;

public interface TblInsKeyDAO {

	public com.allinfinance.po.base.TblInsKey get(com.allinfinance.po.base.TblInsKeyPK key);


	
 
	public void save(com.allinfinance.po.base.TblInsKey tblInsKey);

	
	public void update(com.allinfinance.po.base.TblInsKey tblInsKey);
	
	
	public void delete(com.allinfinance.po.base.TblInsKeyPK id);

	
	public void delete(com.allinfinance.po.base.TblInsKey tblInsKey);

}
