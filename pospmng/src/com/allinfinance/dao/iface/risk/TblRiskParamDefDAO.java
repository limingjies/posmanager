package com.allinfinance.dao.iface.risk;

import java.util.List;

import com.allinfinance.po.risk.TblRiskParamDef;
import com.allinfinance.po.risk.TblRiskParamDefPK;

public interface TblRiskParamDefDAO {

	public TblRiskParamDef get(TblRiskParamDefPK key);

	public TblRiskParamDef load(TblRiskParamDefPK key);
	public void update(TblRiskParamDef tblRiskParamDef);
	public void save(List<TblRiskParamDef> dataList);
	public void delete(TblRiskParamDefPK id);
}
