package com.allinfinance.dao.impl.risk;


import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.risk.TblRiskAlarm;
import com.allinfinance.po.risk.TblRiskAlarmPK;

public class TblRiskAlarmDAO extends _RootDAO<TblRiskAlarm> implements com.allinfinance.dao.iface.risk.TblRiskAlarmDAO{

	@Override
	protected Class<TblRiskAlarm> getReferenceClass() {
		// TODO Auto-generated method stub
		return TblRiskAlarm.class;
	}
	public TblRiskAlarm cast (Object object) {
		return (TblRiskAlarm) object;
	}
	@Override
	public void delete(TblRiskAlarmPK id) {
		// TODO Auto-generated method stub
		super.delete((Object) load(id));
	}

	@Override
	public TblRiskAlarm get(TblRiskAlarmPK key) {
		// TODO Auto-generated method stub
		return (TblRiskAlarm) get(getReferenceClass(), key);
	}

	public TblRiskAlarm load(TblRiskAlarmPK key) {
		// TODO Auto-generated method stub
		return (TblRiskAlarm) load(getReferenceClass(), key);
	}

	@Override
	public void update(TblRiskAlarm tblRiskAlarm) {
		super.update(tblRiskAlarm);
	}
	@Override
	public void save(TblRiskAlarm tblRiskAlarm) {
		super.save(tblRiskAlarm);
	}
	

}
