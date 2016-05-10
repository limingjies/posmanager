package com.allinfinance.dao.impl.risk;


import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.risk.TblAlarmMcht;
import com.allinfinance.po.risk.TblAlarmMchtPK;

public class TblAlarmMchtDAO extends _RootDAO<TblAlarmMcht> implements com.allinfinance.dao.iface.risk.TblAlarmMchtDAO{

	@Override
	protected Class<TblAlarmMcht> getReferenceClass() {
		// TODO Auto-generated method stub
		return TblAlarmMcht.class;
	}
	public TblAlarmMcht cast (Object object) {
		return (TblAlarmMcht) object;
	}
	@Override
	public void delete(TblAlarmMchtPK id) {
		// TODO Auto-generated method stub
		super.delete((Object) load(id));
	}

	@Override
	public TblAlarmMcht get(TblAlarmMchtPK key) {
		// TODO Auto-generated method stub
		return (TblAlarmMcht) get(getReferenceClass(), key);
	}

	public TblAlarmMcht load(TblAlarmMchtPK key) {
		// TODO Auto-generated method stub
		return (TblAlarmMcht) load(getReferenceClass(), key);
	}

	@Override
	public void update(TblAlarmMcht tblAlarmMcht) {
		super.update(tblAlarmMcht);
	}
	@Override
	public void save(TblAlarmMcht tblAlarmMcht) {
		super.save(tblAlarmMcht);
	}
	

}
