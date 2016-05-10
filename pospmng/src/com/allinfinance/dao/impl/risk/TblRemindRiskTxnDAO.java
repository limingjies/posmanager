package com.allinfinance.dao.impl.risk;


import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.risk.TblRemindRiskTxn;
import com.allinfinance.po.risk.TblRemindRiskTxnPK;

public class TblRemindRiskTxnDAO extends _RootDAO<TblRemindRiskTxn> implements com.allinfinance.dao.iface.risk.TblRemindRiskTxnDAO{

	protected Class<TblRemindRiskTxn> getReferenceClass() {
		// TODO Auto-generated method stub
		return TblRemindRiskTxn.class;
	}
	public TblRemindRiskTxn cast (Object object) {
		return (TblRemindRiskTxn) object;
	}
	
	public void delete(TblRemindRiskTxnPK id) {
		// TODO Auto-generated method stub
		super.delete((Object) load(id));
	}

	public TblRemindRiskTxn get(TblRemindRiskTxnPK key) {
		// TODO Auto-generated method stub
		return (TblRemindRiskTxn) get(getReferenceClass(), key);
	}

	public TblRemindRiskTxn load(TblRemindRiskTxnPK key) {
		// TODO Auto-generated method stub
		return (TblRemindRiskTxn) load(getReferenceClass(), key);
	}

	public void update(TblRemindRiskTxn tblRemindRiskTxn) {
		super.update(tblRemindRiskTxn);
	}
	
	public void save(TblRemindRiskTxn tblRemindRiskTxn) {
		super.save(tblRemindRiskTxn);
	}
	

}
