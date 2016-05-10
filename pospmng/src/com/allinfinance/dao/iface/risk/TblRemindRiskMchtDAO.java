package com.allinfinance.dao.iface.risk;


import com.allinfinance.po.risk.TblRemindRiskMcht;
import com.allinfinance.po.risk.TblRemindRiskMchtPK;

public interface TblRemindRiskMchtDAO {

	
	public TblRemindRiskMcht get(TblRemindRiskMchtPK key);
	public void delete(TblRemindRiskMchtPK key);
	public void update(TblRemindRiskMcht tblRemindRiskMcht);
	public void save(TblRemindRiskMcht tblRemindRiskMcht);
}
