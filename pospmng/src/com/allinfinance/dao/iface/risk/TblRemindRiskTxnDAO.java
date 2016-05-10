package com.allinfinance.dao.iface.risk;


import com.allinfinance.po.risk.TblRemindRiskTxn;
import com.allinfinance.po.risk.TblRemindRiskTxnPK;

public interface TblRemindRiskTxnDAO {

	
	public TblRemindRiskTxn get(TblRemindRiskTxnPK key);
	public void delete(TblRemindRiskTxnPK key);
	public void update(TblRemindRiskTxn tblRemindRiskTxn);
	public void save(TblRemindRiskTxn tblRemindRiskTxn);
}
