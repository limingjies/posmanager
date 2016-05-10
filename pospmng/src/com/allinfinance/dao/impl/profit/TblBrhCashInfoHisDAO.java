/**
 * 
 */
package com.allinfinance.dao.impl.profit;

import java.math.BigDecimal;
import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.base.TblBrhCashInfHis;
import com.allinfinance.po.base.TblBrhCashInfHisId;
import com.allinfinance.system.util.CommonFunction;

/**
 * @author 陈巍
 *
 */
public class TblBrhCashInfoHisDAO extends _RootDAO<TblBrhCashInfHis> implements com.allinfinance.dao.iface.profit.TblBrhCashInfoHisDAO {

	@Override
	public TblBrhCashInfHis get(TblBrhCashInfHisId key) {
		return (TblBrhCashInfHis)get(getReferenceClass(),key);
	}

	@Override
	public void delete(TblBrhCashInfHisId key) {
		super.delete(load(getReferenceClass(),key));
		
	}

	@Override
	public void update(TblBrhCashInfHis tblAgentRateInfo) {
		super.update(tblAgentRateInfo);
	}

	@Override
	public void save(TblBrhCashInfHis tblAgentRateInfo) {
		super.save(tblAgentRateInfo);
	}

	@Override
	protected Class<TblBrhCashInfHis> getReferenceClass() {
		return TblBrhCashInfHis.class;
	}

	@Override
	public Double getNextSeqId(String brhId, String rateId) {
		String sql = "select max(SEQ_ID)+1 from TBL_BRH_CASH_INF_HIS WHERE BRH_ID='"+brhId+"' AND RATE_ID='" + rateId +"'";
		List<Double> resultSet= CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if(resultSet.size()>0 && resultSet.get(0) != null){
			return resultSet.get(0);
		}else{
			return Double.parseDouble("1");
		}
	}

}
