package com.allinfinance.dao.impl.risk;


import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.risk.TblFreezeTxn;
import com.allinfinance.po.risk.TblFreezeTxnPK;

public class TblFreezeTxnDAO extends _RootDAO<TblFreezeTxn> implements com.allinfinance.dao.iface.risk.TblFreezeTxnDAO{

	@Override
	protected Class<TblFreezeTxn> getReferenceClass() {
		// TODO Auto-generated method stub
		return TblFreezeTxn.class;
	}
	public TblFreezeTxn cast (Object object) {
		return (TblFreezeTxn) object;
	}
	@Override
	public void delete(TblFreezeTxnPK id) {
		// TODO Auto-generated method stub
		super.delete((Object) load(id));
	}

	@Override
	public TblFreezeTxn get(TblFreezeTxnPK key) {
		// TODO Auto-generated method stub
		return (TblFreezeTxn) get(getReferenceClass(), key);
	}

	public TblFreezeTxn load(TblFreezeTxnPK key) {
		// TODO Auto-generated method stub
		return (TblFreezeTxn) load(getReferenceClass(), key);
	}

	@Override
	public void update(TblFreezeTxn tblFreezeTxn) {
		super.update(tblFreezeTxn);
	}
	@Override
	public void save(TblFreezeTxn tblFreezeTxn) {
		super.save(tblFreezeTxn);
	}
	@Override
	public void saveOrUpdate(TblFreezeTxn tblFreezeTxn) {
		// TODO Auto-generated method stub
		super.saveOrUpdate(tblFreezeTxn);
	}

}
