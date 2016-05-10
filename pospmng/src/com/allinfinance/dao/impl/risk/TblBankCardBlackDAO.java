package com.allinfinance.dao.impl.risk;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.risk.TblBankCardBlack;



public class TblBankCardBlackDAO extends _RootDAO<TblBankCardBlack> implements com.allinfinance.dao.iface.risk.TblBankCardBlackDAO {

	public TblBankCardBlackDAO() {
	}

	
	public Class<TblBankCardBlack> getReferenceClass() {
		return TblBankCardBlack.class;
	}

	public TblBankCardBlack cast(Object object) {
		return (TblBankCardBlack) object;
	}

	
	public TblBankCardBlack load(java.lang.String key)
			throws org.hibernate.HibernateException {
		return (TblBankCardBlack) load(getReferenceClass(), key);
	}

	
	public TblBankCardBlack get(java.lang.String key)
			throws org.hibernate.HibernateException {
		return (TblBankCardBlack) get(getReferenceClass(), key);
	}

	
	public void save(TblBankCardBlack tblBankCardBlack)
			throws org.hibernate.HibernateException {
		 super.save(tblBankCardBlack);
	}

	
	public void saveOrUpdate(TblBankCardBlack tblBankCardBlack)
			throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblBankCardBlack);
	}

	public void update(TblBankCardBlack tblBankCardBlack)
			throws org.hibernate.HibernateException {
		super.update(tblBankCardBlack);
	}

	
	public void delete(TblBankCardBlack tblBankCardBlack)
			throws org.hibernate.HibernateException {
		super.delete(tblBankCardBlack);
	}

	public void delete(java.lang.String id)
			throws org.hibernate.HibernateException {
		super.delete(load(id));
	}
}