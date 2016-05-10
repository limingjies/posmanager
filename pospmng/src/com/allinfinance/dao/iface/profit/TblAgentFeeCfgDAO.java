package com.allinfinance.dao.iface.profit;

import com.allinfinance.po.TblAgentFeeCfg;

public interface TblAgentFeeCfgDAO  {

	public TblAgentFeeCfg get(String key);
	public void delete(String key);
	public void update(TblAgentFeeCfg tblAgentFeeCfg);
	public String save(TblAgentFeeCfg tblAgentFeeCfg);
	public TblAgentFeeCfg findByBrhid(String brhId);
}