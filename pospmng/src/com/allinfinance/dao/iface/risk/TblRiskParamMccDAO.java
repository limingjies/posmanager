package com.allinfinance.dao.iface.risk;

import com.allinfinance.po.risk.TblRiskParamMcc;

/**
 * MCC风控参数管理--TblRiskParamMccDAO类
 * @author yww
 * @version 1.0
 * 2016年4月7日  下午1:56:44
 */
public interface TblRiskParamMccDAO {

	public void save(TblRiskParamMcc tblRiskParamMcc);
	public void delete(TblRiskParamMcc tblRiskParamMcc);
	public void update(TblRiskParamMcc tblRiskParamMcc);
	public TblRiskParamMcc get(String mcc);
	public TblRiskParamMcc load(String key);
	
}
