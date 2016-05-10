package com.allinfinance.dao.iface.mchnt;

import com.allinfinance.po.mchnt.TblBlukImpRetInf;


public interface TblBlukImpRetInfDAO {
	
	public TblBlukImpRetInf get(java.lang.String id);

	public TblBlukImpRetInf load(java.lang.String key);

	public java.lang.String save(TblBlukImpRetInf tblBlukImpRetInf);

	public void saveOrUpdate(TblBlukImpRetInf tblBlukImpRetInf);

	public void update(TblBlukImpRetInf tblBlukImpRetInf);

	public void delete(java.lang.String id);
	
	public void delete(TblBlukImpRetInf tblBlukImpRetInf);
}