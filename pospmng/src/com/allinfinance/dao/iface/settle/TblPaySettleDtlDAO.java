package com.allinfinance.dao.iface.settle;

import com.allinfinance.po.settle.TblPaySettleDtl;
import com.allinfinance.po.settle.TblPaySettleDtlPK;

public interface TblPaySettleDtlDAO {
	
	public TblPaySettleDtl get(TblPaySettleDtlPK tblPaySettleDtlPK);

	public void update(TblPaySettleDtl tblPaySettleDtl);




}