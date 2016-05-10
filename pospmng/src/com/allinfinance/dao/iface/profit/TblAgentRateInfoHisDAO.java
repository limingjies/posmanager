package com.allinfinance.dao.iface.profit;

import java.util.List;

import com.allinfinance.po.TblAgentRateInfoHis;
import com.allinfinance.po.TblAgentRateInfoId;

public interface TblAgentRateInfoHisDAO  {

	public TblAgentRateInfoHis get(TblAgentRateInfoId key);
	public void delete(TblAgentRateInfoId key);
	public void update(TblAgentRateInfoHis tblAgentRateInfoHis);
	public void save(TblAgentRateInfoHis tblAgentRateInfoHis);
	public List<TblAgentRateInfoHis> findByDiscid(String discId);
	void deleteByDiscId(String discId);
	public String getNextSeqId(String discId, String rateId);
}