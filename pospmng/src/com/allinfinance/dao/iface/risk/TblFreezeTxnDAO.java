package com.allinfinance.dao.iface.risk;


import com.allinfinance.po.risk.TblFreezeTxn;
import com.allinfinance.po.risk.TblFreezeTxnPK;

public interface TblFreezeTxnDAO {

	
	public TblFreezeTxn get(TblFreezeTxnPK key);
	public void delete(TblFreezeTxnPK key);
	public void update(TblFreezeTxn tblFreezeTxn);
	public void save(TblFreezeTxn tblFreezeTxn);
	public void saveOrUpdate(TblFreezeTxn tblFreezeTxn);
}
