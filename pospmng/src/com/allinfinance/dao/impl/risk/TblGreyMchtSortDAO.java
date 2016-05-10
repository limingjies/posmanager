package com.allinfinance.dao.impl.risk;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.risk.TblGreyMchtSort;



public class TblGreyMchtSortDAO extends _RootDAO<TblGreyMchtSort> implements com.allinfinance.dao.iface.risk.TblGreyMchtSortDAO {

	public TblGreyMchtSortDAO() {
	}

	
	public Class<TblGreyMchtSort> getReferenceClass() {
		return TblGreyMchtSort.class;
	}

	public TblGreyMchtSort cast(Object object) {
		return (TblGreyMchtSort) object;
	}

	
	public TblGreyMchtSort load(java.lang.String key)
			throws org.hibernate.HibernateException {
		return (TblGreyMchtSort) load(getReferenceClass(), key);
	}

	
	public TblGreyMchtSort get(java.lang.String key)
			throws org.hibernate.HibernateException {
		return (TblGreyMchtSort) get(getReferenceClass(), key);
	}

	
	public void save(TblGreyMchtSort tblGreyMchtSort)
			throws org.hibernate.HibernateException {
		 super.save(tblGreyMchtSort);
	}

	
	public void saveOrUpdate(TblGreyMchtSort tblGreyMchtSort)
			throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblGreyMchtSort);
	}

	public void update(TblGreyMchtSort tblGreyMchtSort)
			throws org.hibernate.HibernateException {
		super.update(tblGreyMchtSort);
	}

	
	public void delete(TblGreyMchtSort tblGreyMchtSort)
			throws org.hibernate.HibernateException {
		super.delete(tblGreyMchtSort);
	}

	public void delete(java.lang.String id)
			throws org.hibernate.HibernateException {
		super.delete(load(id));
	}
}