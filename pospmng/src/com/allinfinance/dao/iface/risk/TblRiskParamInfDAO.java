package com.allinfinance.dao.iface.risk;

import java.util.List;

import com.allinfinance.po.TblRiskParamInf;
import com.allinfinance.po.TblRiskParamInfPK;

public interface TblRiskParamInfDAO {

	public com.allinfinance.po.TblRiskParamInf get(TblRiskParamInfPK key);

	public com.allinfinance.po.TblRiskParamInf load(TblRiskParamInfPK key);
	public void update(com.allinfinance.po.TblRiskParamInf tblRiskParamInf);
	public void save(List<TblRiskParamInf> dataList);
	public void delete(TblRiskParamInfPK id);
}
