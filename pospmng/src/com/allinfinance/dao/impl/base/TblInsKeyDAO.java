package com.allinfinance.dao.impl.base;

import com.allinfinance.dao._RootDAO;

public class TblInsKeyDAO extends _RootDAO<com.allinfinance.po.base.TblInsKey> implements com.allinfinance.dao.iface.base.TblInsKeyDAO{

	@Override
	protected Class<com.allinfinance.po.base.TblInsKey> getReferenceClass() {
		// TODO Auto-generated method stub
		return com.allinfinance.po.base.TblInsKey.class;
	}

	public com.allinfinance.po.base.TblInsKey load(com.allinfinance.po.base.TblInsKeyPK key)
	{
		return (com.allinfinance.po.base.TblInsKey) load(getReferenceClass(), key);
	}
	@Override
	public void delete(com.allinfinance.po.base.TblInsKeyPK id) {
		// TODO Auto-generated method stub
		super.delete((Object) load(id));
	}

	@Override
	public void delete(com.allinfinance.po.base.TblInsKey tblInsKey) {
		// TODO Auto-generated method stub
		super.delete((Object) tblInsKey);
	}

	@Override
	public com.allinfinance.po.base.TblInsKey get(com.allinfinance.po.base.TblInsKeyPK key) {
		// TODO Auto-generated method stub
		return (com.allinfinance.po.base.TblInsKey) get(getReferenceClass(), key);
	}

	@Override
	public void save(com.allinfinance.po.base.TblInsKey tblInsKey) {
		// TODO Auto-generated method stub
		super.save(tblInsKey);
	}

	@Override
	public void update(com.allinfinance.po.base.TblInsKey tblInsKey) {
		// TODO Auto-generated method stub
		super.update(tblInsKey);
	}

}
