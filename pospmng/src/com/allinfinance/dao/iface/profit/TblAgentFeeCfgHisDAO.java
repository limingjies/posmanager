package com.allinfinance.dao.iface.profit;

import com.allinfinance.po.TblAgentFeeCfgHis;

public interface TblAgentFeeCfgHisDAO  {

	public TblAgentFeeCfgHis get(String key);
	public void delete(String brhId);
	public void update(TblAgentFeeCfgHis tblAgentFeeCfgHis);
	public String save(TblAgentFeeCfgHis tblAgentFeeCfgHis);
	public TblAgentFeeCfgHis findByBrhid(String brhId);
	public TblAgentFeeCfgHis findByBrhidSeqId(String brhId, String seqId);
	public String getNextSeqId(String brhId);
}