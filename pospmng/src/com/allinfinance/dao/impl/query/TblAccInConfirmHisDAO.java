package com.allinfinance.dao.impl.query;

import org.hibernate.HibernateException;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.query.TblAccInConfirmHis;


public class TblAccInConfirmHisDAO extends _RootDAO<TblAccInConfirmHis> implements com.allinfinance.dao.iface.query.TblAccInConfirmHisDAO {

	@Override
	public TblAccInConfirmHis get(String key) throws HibernateException {
		return (TblAccInConfirmHis) get(getReferenceClass(), key);
	}

	@Override
	public TblAccInConfirmHis load(String key) throws HibernateException {
		return (TblAccInConfirmHis) load(getReferenceClass(), key);
	}

	@Override
	public void save(TblAccInConfirmHis tblAccInConfirmHis)
			throws HibernateException {
		 super.save(tblAccInConfirmHis);
	}

	@Override
	public void saveOrUpdate(TblAccInConfirmHis tblAccInConfirmHis)
			throws HibernateException {
		 super.saveOrUpdate(tblAccInConfirmHis);
	}

	@Override
	public void update(TblAccInConfirmHis tblAccInConfirmHis)
			throws HibernateException {
		 super.update(tblAccInConfirmHis);
	}

	@Override
	public void delete(String key) throws HibernateException {
		 super.delete(key);
	}

	@Override
	public void delete(TblAccInConfirmHis tblAccInConfirmHis)
			throws HibernateException {
		 super.delete(tblAccInConfirmHis);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getReferenceClass() {
		return TblAccInConfirmHis.class;
	}

}
