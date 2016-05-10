package com.allinfinance.bo.risk;

import com.allinfinance.po.TblRunRisk;
import com.allinfinance.po.TblRunRiskPK;


public interface T40102BO {
	
	public TblRunRisk get(TblRunRiskPK tblRunRiskPK);

	public String add(TblRunRisk tblRunRisk);
	
	public String update(TblRunRisk tblRunRisk);

	public String getRuleId();

	public String delete(TblRunRiskPK tblRunRiskPK);
}
