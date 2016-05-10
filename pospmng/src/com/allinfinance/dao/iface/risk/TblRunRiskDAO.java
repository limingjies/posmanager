package com.allinfinance.dao.iface.risk;

import java.util.List;

import com.allinfinance.po.TblRunRisk;
import com.allinfinance.po.TblRunRiskPK;

public interface TblRunRiskDAO {

	public com.allinfinance.po.TblRunRisk get(TblRunRiskPK key);

	public com.allinfinance.po.TblRunRisk load(TblRunRiskPK key);
	public void delete(TblRunRiskPK key);
	public void update(com.allinfinance.po.TblRunRisk tblRunRisk);
	public void saveList(List<TblRunRisk> dataList);
	public void save(com.allinfinance.po.TblRunRisk tblRunRisk);
}
