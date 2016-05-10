package com.allinfinance.dao.iface.term;

import java.io.Serializable;

public interface TblTermCupInfoTmpDAO {
	public com.allinfinance.po.TblTermCupInfoTmp get(java.lang.String key);

	public com.allinfinance.po.TblTermCupInfoTmp load(java.lang.String key);

	public java.util.List<com.allinfinance.po.TblTermCupInfoTmp> findAll ();

	public java.lang.String save(com.allinfinance.po.TblTermCupInfoTmp tblTermCupInfoTmp);

	public void saveOrUpdate(com.allinfinance.po.TblTermCupInfoTmp tblTermCupInfoTmp);

	public void update(com.allinfinance.po.TblTermCupInfoTmp tblTermCupInfoTmp);

	public void delete(java.lang.String id);

	public void delete(com.allinfinance.po.TblTermCupInfoTmp tblTermCupInfoTmp);
}

