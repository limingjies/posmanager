package com.allinfinance.bo.risk;


import com.allinfinance.po.risk.TblRiskParamMcc;

/**
 * MCC风控参数管理--T40209BO
 * @author yww
 * @version 1.0
 * 2016年4月7日  下午1:56:44
 */
public interface T40209BO {

	public void save(TblRiskParamMcc tblRiskParamMcc);
	public void delete(TblRiskParamMcc tblRiskParamMcc);
	public void deleteByKey(String key);
	public void update(TblRiskParamMcc tblRiskParamMcc);
	public TblRiskParamMcc get(String mcc);
	public TblRiskParamMcc load(String key);

}
