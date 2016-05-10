package com.allinfinance.dao.iface.mchnt;

import java.io.Serializable;

public interface TblMchtCupInfoTmpDAO {
	public com.allinfinance.po.TblMchtCupInfoTmp get(java.lang.String key);

	public com.allinfinance.po.TblMchtCupInfoTmp load(java.lang.String key);

	public java.util.List<com.allinfinance.po.TblMchtCupInfoTmp> findAll ();

	public java.lang.String save(com.allinfinance.po.TblMchtCupInfoTmp tblMchtCupInfoTmp);

	public void saveOrUpdate(com.allinfinance.po.TblMchtCupInfoTmp tblMchtCupInfoTmp);

	public void update(com.allinfinance.po.TblMchtCupInfoTmp tblMchtCupInfoTmp);

	public void delete(java.lang.String id);

	public void delete(com.allinfinance.po.TblMchtCupInfoTmp tblMchtCupInfoTmp);
}
