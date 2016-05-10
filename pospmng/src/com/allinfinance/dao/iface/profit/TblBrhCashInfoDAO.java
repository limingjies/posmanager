package com.allinfinance.dao.iface.profit;

import java.util.List;

import com.allinfinance.po.TblAgentRateInfo;
import com.allinfinance.po.base.TblBrhCashInf;
import com.allinfinance.po.base.TblBrhCashInfId;

public interface TblBrhCashInfoDAO  {

	public TblBrhCashInf get(TblBrhCashInfId key);
	public void delete(TblBrhCashInfId key);
	public void update(TblBrhCashInf tblAgentRateInfo);
	public void save(TblBrhCashInf tblAgentRateInfo);
	void deleteByBrhId(String brhId);
	public List<TblBrhCashInf> findByDiscid(String brhId);
}