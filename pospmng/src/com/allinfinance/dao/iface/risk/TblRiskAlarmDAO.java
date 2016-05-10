package com.allinfinance.dao.iface.risk;


import com.allinfinance.po.risk.TblRiskAlarm;
import com.allinfinance.po.risk.TblRiskAlarmPK;

public interface TblRiskAlarmDAO {

	
	public TblRiskAlarm get(TblRiskAlarmPK key);
	public void delete(TblRiskAlarmPK key);
	public void update(TblRiskAlarm tblRiskAlarm);
	public void save(TblRiskAlarm tblRiskAlarm);
}
