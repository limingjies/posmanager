
package com.allinfinance.bo.risk;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.allinfinance.common.Operator;
import com.allinfinance.po.TblTermInfTmp;
import com.allinfinance.po.risk.TblRiskParamMng;

/**
 * 风控参数管理
 * @author Jee Khan
 *
 */
public interface TblRiskParamMngBo {

	public String save(TblRiskParamMng tblRiskParamMng, Operator operator);
			
	public String delete(TblRiskParamMng tblRiskParamMng, Operator operator) throws Exception;
	
	public String batchSaveTermInfo(TblRiskParamMng tblRiskParamMng,String termInfo,String tradeInfo, Operator operator);

	public String batchAddTerm(List<TblTermInfTmp> list,
			TblRiskParamMng tblRiskParamMng, Operator operator) throws IllegalAccessException, InvocationTargetException;
	
}
