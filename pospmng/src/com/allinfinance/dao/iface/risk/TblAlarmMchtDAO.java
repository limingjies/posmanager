package com.allinfinance.dao.iface.risk;


import com.allinfinance.po.risk.TblAlarmMcht;
import com.allinfinance.po.risk.TblAlarmMchtPK;

public interface TblAlarmMchtDAO {

	
	public TblAlarmMcht get(TblAlarmMchtPK key);
	public void delete(TblAlarmMchtPK key);
	public void update(TblAlarmMcht tblAlarmMcht);
	public void save(TblAlarmMcht tblAlarmMcht);
}
