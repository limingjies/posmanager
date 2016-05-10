package com.allinfinance.dao.iface.profit;

import com.allinfinance.po.TblProfitRateInfo;

public interface TblProfitRateInfoDAO {
	
	public TblProfitRateInfo get(String key);
	public void delete(String key);
	public void update(TblProfitRateInfo tblProfitRateInfo);
	public void save(TblProfitRateInfo tblProfitRateInfo);
}