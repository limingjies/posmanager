package com.allinfinance.dao.impl.settle;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.settle.TblCnapsInfo;



public class TblCnapsInfoDAO extends _RootDAO<TblCnapsInfo> implements com.allinfinance.dao.iface.settle.TblCnapsInfoDAO {

	public TblCnapsInfoDAO() {
	}

	
	public Class<TblCnapsInfo> getReferenceClass() {
		return TblCnapsInfo.class;
	}

	public TblCnapsInfo cast(Object object) {
		return (TblCnapsInfo) object;
	}

	
	public TblCnapsInfo load(java.lang.String key)
			throws org.hibernate.HibernateException {
		return (TblCnapsInfo) load(getReferenceClass(), key);
	}

	
	public TblCnapsInfo get(java.lang.String key)
			throws org.hibernate.HibernateException {
		return (TblCnapsInfo) get(getReferenceClass(), key);
	}

	
	public void save(TblCnapsInfo tblCnapsInfo)
			throws org.hibernate.HibernateException {
		 super.save(tblCnapsInfo);
	}

	
	public void saveOrUpdate(TblCnapsInfo tblCnapsInfo)
			throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblCnapsInfo);
	}

	public void update(TblCnapsInfo tblCnapsInfo)
			throws org.hibernate.HibernateException {
		super.update(tblCnapsInfo);
	}

	
	public void delete(TblCnapsInfo tblCnapsInfo)
			throws org.hibernate.HibernateException {
		super.delete(tblCnapsInfo);
	}

	public void delete(java.lang.String id)
			throws org.hibernate.HibernateException {
		super.delete(load(id));
	}
}