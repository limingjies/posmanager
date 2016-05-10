package com.allinfinance.dao.iface.profit;

import java.util.List;

import com.allinfinance.po.TblAgentRateInfo;
import com.allinfinance.po.TblAgentRateInfoId;

public interface TblAgentRateInfoDAO  {

	public TblAgentRateInfo get(TblAgentRateInfoId key);
	public void delete(TblAgentRateInfoId key);
	public void update(TblAgentRateInfo tblAgentRateInfo);
	public void save(TblAgentRateInfo tblAgentRateInfo);
	public List<TblAgentRateInfo> findByDiscid(String discId);
	void deleteByDiscId(String discId);
}