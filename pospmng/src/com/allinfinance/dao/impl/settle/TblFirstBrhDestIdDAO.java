package com.allinfinance.dao.impl.settle;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.settle.TblFirstBrhDestId;



public class TblFirstBrhDestIdDAO extends _RootDAO<TblFirstBrhDestId> implements com.allinfinance.dao.iface.settle.TblFirstBrhDestIdDAO {

	public TblFirstBrhDestIdDAO() {
	}

	
	public Class<TblFirstBrhDestId> getReferenceClass() {
		return TblFirstBrhDestId.class;
	}

	public TblFirstBrhDestId cast(Object object) {
		return (TblFirstBrhDestId) object;
	}

	
	public TblFirstBrhDestId load(java.lang.String key)
			throws org.hibernate.HibernateException {
		return (TblFirstBrhDestId) load(getReferenceClass(), key);
	}

	
	public TblFirstBrhDestId get(java.lang.String key)
			throws org.hibernate.HibernateException {
		return (TblFirstBrhDestId) get(getReferenceClass(), key);
	}

	
	public void save(TblFirstBrhDestId tblFirstBrhDestId)
			throws org.hibernate.HibernateException {
		 super.save(tblFirstBrhDestId);
	}

	
	public void saveOrUpdate(TblFirstBrhDestId tblFirstBrhDestId)
			throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblFirstBrhDestId);
	}

	public void update(TblFirstBrhDestId tblFirstBrhDestId)
			throws org.hibernate.HibernateException {
		super.update(tblFirstBrhDestId);
	}

	
	public void delete(TblFirstBrhDestId tblFirstBrhDestId)
			throws org.hibernate.HibernateException {
		super.delete(tblFirstBrhDestId);
	}

	public void delete(java.lang.String id)
			throws org.hibernate.HibernateException {
		super.delete(load(id));
	}
}