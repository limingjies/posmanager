package com.allinfinance.dao.impl.settle;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.settle.TblPayTypeSmall;



public class TblPayTypeSmallDAO extends _RootDAO<TblPayTypeSmall> implements com.allinfinance.dao.iface.settle.TblPayTypeSmallDAO {

	public TblPayTypeSmallDAO() {
	}

	
	public Class<TblPayTypeSmall> getReferenceClass() {
		return TblPayTypeSmall.class;
	}

	public TblPayTypeSmall cast(Object object) {
		return (TblPayTypeSmall) object;
	}

	
	public TblPayTypeSmall load(java.lang.String key)
			throws org.hibernate.HibernateException {
		return (TblPayTypeSmall) load(getReferenceClass(), key);
	}

	
	public TblPayTypeSmall get(java.lang.String key)
			throws org.hibernate.HibernateException {
		return (TblPayTypeSmall) get(getReferenceClass(), key);
	}

	
	public void save(TblPayTypeSmall tblPayTypeSmall)
			throws org.hibernate.HibernateException {
		 super.save(tblPayTypeSmall);
	}

	
	public void saveOrUpdate(TblPayTypeSmall tblPayTypeSmall)
			throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblPayTypeSmall);
	}

	public void update(TblPayTypeSmall tblPayTypeSmall)
			throws org.hibernate.HibernateException {
		super.update(tblPayTypeSmall);
	}

	
	public void delete(TblPayTypeSmall tblPayTypeSmall)
			throws org.hibernate.HibernateException {
		super.delete(tblPayTypeSmall);
	}

	public void delete(java.lang.String id)
			throws org.hibernate.HibernateException {
		super.delete(load(id));
	}
}