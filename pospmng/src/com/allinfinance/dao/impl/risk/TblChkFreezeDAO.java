package com.allinfinance.dao.impl.risk;


import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.risk.TblChkFreeze;
import com.allinfinance.po.risk.TblChkFreezePK;

public class TblChkFreezeDAO extends _RootDAO<TblChkFreeze> implements com.allinfinance.dao.iface.risk.TblChkFreezeDAO{

	@Override
	protected Class<TblChkFreeze> getReferenceClass() {
		return TblChkFreeze.class;
	}
	public TblChkFreeze cast (Object object) {
		return (TblChkFreeze) object;
	}
	@Override
	public void delete(TblChkFreezePK id) {
		super.delete((Object) load(id));
	}

	@Override
	public TblChkFreeze get(TblChkFreezePK key) {
		return (TblChkFreeze) get(getReferenceClass(), key);
	}

	public TblChkFreeze load(TblChkFreezePK key) {
		return (TblChkFreeze) load(getReferenceClass(), key);
	}

	@Override
	public void update(TblChkFreeze tblChkFreeze) {
		super.update(tblChkFreeze);
	}
	@Override
	public void save(TblChkFreeze tblChkFreeze) {
		super.save(tblChkFreeze);
	}
	@Override
	public void saveOrUpdate(TblChkFreeze tblChkFreeze) {
		super.saveOrUpdate(tblChkFreeze);
	}

}
