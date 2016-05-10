package com.allinfinance.dao.impl.profit;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblProfitRateInfo;

public class TblProfitRateInfoDAO extends _RootDAO<TblProfitRateInfo> implements com.allinfinance.dao.iface.profit.TblProfitRateInfoDAO{

	@Override
	public TblProfitRateInfo get(String key) {
		return (TblProfitRateInfo) get(getReferenceClass(), key);
	}

	@Override
	public void delete(String key) {
		super.delete(load(getReferenceClass(), key));
	}

	@Override
	public void update(TblProfitRateInfo tblProfitRateInfo) {
		super.update(tblProfitRateInfo);
	}

	@Override
	public void save(TblProfitRateInfo tblProfitRateInfo) {
		super.save(tblProfitRateInfo);
	}

	@Override
	protected Class<TblProfitRateInfo> getReferenceClass() {
		return TblProfitRateInfo.class;
	} 
}