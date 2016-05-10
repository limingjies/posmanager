package com.allinfinance.dao.impl.risk;


import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.risk.TblAlarmTxn;
import com.allinfinance.po.risk.TblAlarmTxnPK;

public class TblAlarmTxnDAO extends _RootDAO<TblAlarmTxn> implements com.allinfinance.dao.iface.risk.TblAlarmTxnDAO{

	@Override
	protected Class<TblAlarmTxn> getReferenceClass() {
		// TODO Auto-generated method stub
		return TblAlarmTxn.class;
	}
	public TblAlarmTxn cast (Object object) {
		return (TblAlarmTxn) object;
	}
	@Override
	public void delete(TblAlarmTxnPK id) {
		// TODO Auto-generated method stub
		super.delete((Object) load(id));
	}

	@Override
	public TblAlarmTxn get(TblAlarmTxnPK key) {
		// TODO Auto-generated method stub
		return (TblAlarmTxn) get(getReferenceClass(), key);
	}

	public TblAlarmTxn load(TblAlarmTxnPK key) {
		// TODO Auto-generated method stub
		return (TblAlarmTxn) load(getReferenceClass(), key);
	}

	@Override
	public void update(TblAlarmTxn tblAlarmTxn) {
		super.update(tblAlarmTxn);
	}
	@Override
	public void save(TblAlarmTxn tblAlarmTxn) {
		super.save(tblAlarmTxn);
	}
	

}
