package com.allinfinance.dao.impl.daytrade;

import org.hibernate.HibernateException;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.daytrade.TblWithdrawInf;

public class TblWithdrawInfDAO extends _RootDAO<TblWithdrawInf> implements
		com.allinfinance.dao.iface.daytrade.TblWithdrawInfDAO {

	@Override
	public TblWithdrawInf get(String key) throws HibernateException {
		// TODO Auto-generated method stub
		return (TblWithdrawInf) get(TblWithdrawInf.class, key);
	}

	@Override
	public void saveOrUpdate(TblWithdrawInf tblWithdrawInf)
			throws HibernateException {
		// TODO Auto-generated method stub
		super.update(tblWithdrawInf);
	}

	@Override
	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return TblWithdrawInf.class;
	}

	@Override
	public void delete(TblWithdrawInf tblWithdrawInf) {
		// TODO Auto-generated method stub
		super.delete(tblWithdrawInf);
	}

}
