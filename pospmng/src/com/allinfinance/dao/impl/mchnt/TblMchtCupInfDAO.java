package com.allinfinance.dao.impl.mchnt;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.mchnt.TblMchtCupInf;

public class TblMchtCupInfDAO extends _RootDAO<TblMchtCupInf> implements com.allinfinance.dao.iface.mchnt.TblMchtCupInfDAO{


	public Class<TblMchtCupInf> getReferenceClass () {
		return TblMchtCupInf.class;
	}
	
	public TblMchtCupInf load(java.lang.String key)
	{
		return (TblMchtCupInf) load(getReferenceClass(), key);
	}
	
	

	public void delete(String mchntNo) {
		// TODO Auto-generated method stub
		super.delete((Object) load(mchntNo));
	}

	public TblMchtCupInf get(String mchntNo) {
		// TODO Auto-generated method stub
		return (TblMchtCupInf) get(getReferenceClass(), mchntNo);
	}

	public void save(TblMchtCupInf tblMchtCupInf) {
		// TODO Auto-generated method stub
		super.save(tblMchtCupInf);
	}

	public void update(TblMchtCupInf tblMchtCupInf) {
		// TODO Auto-generated method stub
		super.update(tblMchtCupInf);
	}

}
