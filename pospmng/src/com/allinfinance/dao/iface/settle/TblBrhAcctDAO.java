package com.allinfinance.dao.iface.settle;

public interface TblBrhAcctDAO {
	
	public com.allinfinance.po.TblBrhAcct get(java.lang.String key);

	public com.allinfinance.po.TblBrhAcct load(java.lang.String key);

	public java.util.List<com.allinfinance.po.TblBrhAcct> findAll ();

	public java.lang.String save(com.allinfinance.po.TblBrhAcct tblBrhAcct);

	public void saveOrUpdate(com.allinfinance.po.TblBrhAcct tblBrhAcct);

	public void update(com.allinfinance.po.TblBrhAcct tblBrhAcct);

	public void delete(java.lang.String id);

	public void delete(com.allinfinance.po.TblBrhAcct tblBrhAcct);


}