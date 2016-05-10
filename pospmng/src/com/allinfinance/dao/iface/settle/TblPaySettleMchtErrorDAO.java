package com.allinfinance.dao.iface.settle;


import com.allinfinance.po.settle.TblPaySettleMchtError;

public interface TblPaySettleMchtErrorDAO {
	
	public TblPaySettleMchtError get(String mchtNo);

	public void update(TblPaySettleMchtError tblPaySettleDtl);
	
	public void save(TblPaySettleMchtError tblPaySettleDtl);
	
	




}