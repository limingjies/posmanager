package com.allinfinance.dao.impl.settle;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.settle.TblPaySettleDtl;
import com.allinfinance.po.settle.TblPaySettleDtlPK;


public class TblPaySettleDtlDAO extends _RootDAO<TblPaySettleDtl> implements com.allinfinance.dao.iface.settle.TblPaySettleDtlDAO {



	public Class<TblPaySettleDtl> getReferenceClass () {
		return TblPaySettleDtl.class;
	}

	public TblPaySettleDtl cast (Object object) {
		return (TblPaySettleDtl) object;
	}



	public TblPaySettleDtl get(TblPaySettleDtlPK tblPaySettleDtlPK)
	{
		return (TblPaySettleDtl) get(getReferenceClass(), tblPaySettleDtlPK);
	}


	public void update(TblPaySettleDtl tblPaySettleDtl)
	{
		super.update(tblPaySettleDtl);
	}

}