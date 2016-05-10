package com.allinfinance.dao.iface.risk;


import com.allinfinance.po.risk.TblAlarmCard;
import com.allinfinance.po.risk.TblAlarmCardPK;

public interface TblAlarmCardDAO {

	
	public TblAlarmCard get(TblAlarmCardPK key);
	public void delete(TblAlarmCardPK key);
	public void update(TblAlarmCard tblAlarmCard);
	public void save(TblAlarmCard tblAlarmCard);
}
