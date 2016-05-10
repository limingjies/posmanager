package com.allinfinance.dao.iface.profit;

import java.util.List;

import com.allinfinance.po.base.TblBrhCashInfHis;
import com.allinfinance.po.base.TblBrhCashInfHisId;

public interface TblBrhCashInfoHisDAO  {

	public TblBrhCashInfHis get(TblBrhCashInfHisId key);
	public void delete(TblBrhCashInfHisId key);
	public void update(TblBrhCashInfHis tblAgentRateInfo);
	public void save(TblBrhCashInfHis tblAgentRateInfo);
	public Double getNextSeqId(String brhId, String rateId);
}