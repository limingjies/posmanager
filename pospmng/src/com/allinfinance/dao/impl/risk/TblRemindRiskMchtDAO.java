package com.allinfinance.dao.impl.risk;


import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.risk.TblRemindRiskMcht;
import com.allinfinance.po.risk.TblRemindRiskMchtPK;

public class TblRemindRiskMchtDAO extends _RootDAO<TblRemindRiskMcht> implements com.allinfinance.dao.iface.risk.TblRemindRiskMchtDAO{

	protected Class<TblRemindRiskMcht> getReferenceClass() {
		// TODO Auto-generated method stub
		return TblRemindRiskMcht.class;
	}
	public TblRemindRiskMcht cast (Object object) {
		return (TblRemindRiskMcht) object;
	}
	
	public void delete(TblRemindRiskMchtPK id) {
		// TODO Auto-generated method stub
		super.delete((Object) load(id));
	}

	public TblRemindRiskMcht get(TblRemindRiskMchtPK key) {
		// TODO Auto-generated method stub
		return (TblRemindRiskMcht) get(getReferenceClass(), key);
	}

	public TblRemindRiskMcht load(TblRemindRiskMchtPK key) {
		// TODO Auto-generated method stub
		return (TblRemindRiskMcht) load(getReferenceClass(), key);
	}

	public void update(TblRemindRiskMcht tblRemindRiskMcht) {
		super.update(tblRemindRiskMcht);
	}
	
	public void save(TblRemindRiskMcht tblRemindRiskMcht) {
		super.save(tblRemindRiskMcht);
	}
	

}
