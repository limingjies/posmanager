package com.allinfinance.dao.iface.mchnt;

import java.io.Serializable;

public interface TblMchtCupInfoDAO {
	public com.allinfinance.po.TblMchtCupInfo get(java.lang.String key);

	public com.allinfinance.po.TblMchtCupInfo load(java.lang.String key);

	public java.util.List<com.allinfinance.po.TblMchtCupInfo> findAll ();

	public java.lang.String save(com.allinfinance.po.TblMchtCupInfo tblMchtCupInfo);

	public void saveOrUpdate(com.allinfinance.po.TblMchtCupInfo tblMchtCupInfo);

	public void update(com.allinfinance.po.TblMchtCupInfo tblMchtCupInfo);

	public void delete(java.lang.String id);

	public void delete(com.allinfinance.po.TblMchtCupInfo tblMchtCupInfo);
}
