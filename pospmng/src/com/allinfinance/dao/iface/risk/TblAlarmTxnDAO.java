package com.allinfinance.dao.iface.risk;


import com.allinfinance.po.risk.TblAlarmTxn;
import com.allinfinance.po.risk.TblAlarmTxnPK;

public interface TblAlarmTxnDAO {

	
	public TblAlarmTxn get(TblAlarmTxnPK key);
	public void delete(TblAlarmTxnPK key);
	public void update(TblAlarmTxn tblAlarmTxn);
	public void save(TblAlarmTxn tblAlarmTxn);
}
