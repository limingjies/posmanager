package com.allinfinance.dao.iface.risk;

import com.allinfinance.po.risk.TblRiskParamMng;
import com.allinfinance.po.risk.TblRiskParamMngPK;

public interface TblRiskParamMngDAO {
	
	public TblRiskParamMng get(TblRiskParamMngPK key);
	public TblRiskParamMng get(String mchtNo,String termNo,String riskType);
	public TblRiskParamMngPK save(TblRiskParamMng tblRiskParamMng);
	public void update(TblRiskParamMng tblRiskParamMng);
	public void saveOrUpdate(TblRiskParamMng tblRiskParamMng);
	public void delete(TblRiskParamMng tblRiskParamMng);
	public void delete(TblRiskParamMngPK key);

}