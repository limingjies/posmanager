package com.allinfinance.dao.iface.term;

import java.io.Serializable;

public interface TblTermCupInfoDAO {
	public com.allinfinance.po.TblTermCupInfo get(java.lang.String key);

	public com.allinfinance.po.TblTermCupInfo load(java.lang.String key);

	public java.util.List<com.allinfinance.po.TblTermCupInfo> findAll ();

	public java.lang.String save(com.allinfinance.po.TblTermCupInfo tblTermCupInfo);

	public void saveOrUpdate(com.allinfinance.po.TblTermCupInfo tblTermCupInfo);

	public void update(com.allinfinance.po.TblTermCupInfo tblTermCupInfo);

	public void delete(java.lang.String id);

	public void delete(com.allinfinance.po.TblTermCupInfo tblTermCupInfo);
}

