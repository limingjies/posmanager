package com.allinfinance.dao.impl.risk;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.risk.TblFreezeTxn;
import com.allinfinance.po.risk.TblMchtFreeze;

public class TblMchtFreezeDAO extends _RootDAO<TblFreezeTxn> implements
		com.allinfinance.dao.iface.risk.TblMchtFreezeDAO {

	@Override
	public TblMchtFreeze get(String key) {
		// TODO Auto-generated method stub
		return (TblMchtFreeze) get(getReferenceClass(), key);
	}

	@Override
	public TblMchtFreeze load(String key) {
		// TODO Auto-generated method stub
		return (TblMchtFreeze) load(getReferenceClass(), key);
		
	}

	@Override
	public List<TblMchtFreeze> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(TblMchtFreeze tblMchtFreeze) {
		// TODO Auto-generated method stub
		 super.save(tblMchtFreeze);
	}

	@Override
	public void saveOrUpdate(TblMchtFreeze tblMchtFreeze) {
		// TODO Auto-generated method stub
		super.saveOrUpdate(tblMchtFreeze);
	}

	@Override
	public void update(TblMchtFreeze tblMchtFreeze) {
		// TODO Auto-generated method stub
		super.update(tblMchtFreeze);
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		super.delete(id);
	}

	@Override
	public void delete(TblMchtFreeze tblMchtFreeze) {
		// TODO Auto-generated method stub
		super.delete(tblMchtFreeze);
	}

	@Override
	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return TblMchtFreeze.class;
	}

}
