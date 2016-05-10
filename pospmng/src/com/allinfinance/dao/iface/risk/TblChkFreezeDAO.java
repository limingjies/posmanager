package com.allinfinance.dao.iface.risk;

import com.allinfinance.po.risk.TblChkFreeze;
import com.allinfinance.po.risk.TblChkFreezePK;



public interface TblChkFreezeDAO {

	
	public TblChkFreeze get(TblChkFreezePK key);
	public void delete(TblChkFreezePK key);
	public void update(TblChkFreeze tblChkFreeze);
	public void save(TblChkFreeze tblChkFreeze);
	public void saveOrUpdate(TblChkFreeze tblChkFreeze);
}
