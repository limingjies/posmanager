package com.allinfinance.dao.impl.settle;


import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.settle.TblPaySettleMchtError;


public class TblPaySettleMchtErrorDAO extends _RootDAO<TblPaySettleMchtError> implements com.allinfinance.dao.iface.settle.TblPaySettleMchtErrorDAO {



	public Class<TblPaySettleMchtError> getReferenceClass () {
		return TblPaySettleMchtError.class;
	}

	public TblPaySettleMchtError cast (Object object) {
		return (TblPaySettleMchtError) object;
	}



	public TblPaySettleMchtError get(String tblPaySettleDtlPK)
	{
		return (TblPaySettleMchtError) get(getReferenceClass(), tblPaySettleDtlPK);
	}


	public void update(TblPaySettleMchtError tblPaySettleDtl)
	{
		super.update(tblPaySettleDtl);
	}
	
	public void save(TblPaySettleMchtError tblPaySettleDtl)
	{
		super.save(tblPaySettleDtl);
	}
	
	

}