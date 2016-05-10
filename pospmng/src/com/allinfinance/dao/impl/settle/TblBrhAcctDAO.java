package com.allinfinance.dao.impl.settle;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblBrhAcct;

import java.util.List;

public class TblBrhAcctDAO extends _RootDAO<com.allinfinance.po.TblBrhAcct> implements com.allinfinance.dao.iface.settle.TblBrhAcctDAO {

	public TblBrhAcctDAO () {}

	public List<TblBrhAcct> findAll() {
		return null;
	}

	public Class<com.allinfinance.po.TblBrhAcct> getReferenceClass () {
		return com.allinfinance.po.TblBrhAcct.class;
	}

	public com.allinfinance.po.TblBrhAcct cast (Object object) {
		return (com.allinfinance.po.TblBrhAcct) object;
	}


	public com.allinfinance.po.TblBrhAcct load(java.lang.String key)
	{
		return (com.allinfinance.po.TblBrhAcct) load(getReferenceClass(), key);
	}

	public com.allinfinance.po.TblBrhAcct get(java.lang.String key)
	{
		return (com.allinfinance.po.TblBrhAcct) get(getReferenceClass(), key);
	}

	public java.lang.String save(com.allinfinance.po.TblBrhAcct tblBrhAcct)
	{
		return (java.lang.String) super.save(tblBrhAcct);
	}

	public void saveOrUpdate(com.allinfinance.po.TblBrhAcct tblBrhAcct)
	{
		super.saveOrUpdate(tblBrhAcct);
	}

	public void update(com.allinfinance.po.TblBrhAcct tblBrhAcct)
	{
		super.update(tblBrhAcct);
	}

	public void delete(com.allinfinance.po.TblBrhAcct tblBrhAcct)
	{
		super.delete((Object) tblBrhAcct);
	}

	public void delete(java.lang.String id)
	{
		super.delete((Object) load(id));
	}
}