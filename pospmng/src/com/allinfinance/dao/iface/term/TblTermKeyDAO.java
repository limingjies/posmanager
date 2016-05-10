package com.allinfinance.dao.iface.term;

import java.util.List;

import com.allinfinance.po.TblTermKey;

public interface TblTermKeyDAO {
	
	public com.allinfinance.po.TblTermKey get(com.allinfinance.po.TblTermKeyPK key);
	public com.allinfinance.po.TblTermKey load(com.allinfinance.po.TblTermKeyPK key);
	public java.util.List<com.allinfinance.po.TblTermKey> findAll ();
	public com.allinfinance.po.TblTermKeyPK save(com.allinfinance.po.TblTermKey tblTermKey);
	public void saveOrUpdate(com.allinfinance.po.TblTermKey tblTermKey);
	public void update(com.allinfinance.po.TblTermKey tblTermKey);
	public void delete(com.allinfinance.po.TblTermKeyPK id);
	public void delete(com.allinfinance.po.TblTermKey tblTermKey);
	public List<TblTermKey> getByMchnt(String mchntId);
	public void updateByTremId(String termId, String newMchtNo,
			String currentDateTime);
}