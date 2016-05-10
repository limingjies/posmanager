package com.allinfinance.dao.impl.risk;


import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.risk.TblAlarmCard;
import com.allinfinance.po.risk.TblAlarmCardPK;

public class TblAlarmCardDAO extends _RootDAO<TblAlarmCard> implements com.allinfinance.dao.iface.risk.TblAlarmCardDAO{

	@Override
	protected Class<TblAlarmCard> getReferenceClass() {
		// TODO Auto-generated method stub
		return TblAlarmCard.class;
	}
	public TblAlarmCard cast (Object object) {
		return (TblAlarmCard) object;
	}
	@Override
	public void delete(TblAlarmCardPK id) {
		// TODO Auto-generated method stub
		super.delete((Object) load(id));
	}

	@Override
	public TblAlarmCard get(TblAlarmCardPK key) {
		// TODO Auto-generated method stub
		return (TblAlarmCard) get(getReferenceClass(), key);
	}

	public TblAlarmCard load(TblAlarmCardPK key) {
		// TODO Auto-generated method stub
		return (TblAlarmCard) load(getReferenceClass(), key);
	}

	@Override
	public void update(TblAlarmCard tblAlarmCard) {
		super.update(tblAlarmCard);
	}
	@Override
	public void save(TblAlarmCard tblAlarmCard) {
		super.save(tblAlarmCard);
	}
	

}
