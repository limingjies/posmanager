package com.allinfinance.dao.iface.risk;

import com.allinfinance.po.risk.TblMchtFreeze;

public interface TblMchtFreezeDAO {
	
	public TblMchtFreeze get(java.lang.String key);

	public TblMchtFreeze load(java.lang.String key);

	public java.util.List<TblMchtFreeze> findAll ();

	public void save(TblMchtFreeze tblMchtFreeze);


	public void saveOrUpdate(TblMchtFreeze tblMchtFreeze);

	
	public void update(TblMchtFreeze tblMchtFreeze);


	public void delete(java.lang.String id);

	
	public void delete(TblMchtFreeze tblMchtFreeze);
}
