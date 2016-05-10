package com.allinfinance.dao.impl.settle;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.settle.TblChannelCnapsMap;



public class TblChannelCnapsMapDAO extends _RootDAO<TblChannelCnapsMap> implements com.allinfinance.dao.iface.settle.TblChannelCnapsMapDAO {

	public TblChannelCnapsMapDAO() {
	}

	
	public Class<TblChannelCnapsMap> getReferenceClass() {
		return TblChannelCnapsMap.class;
	}

	public TblChannelCnapsMap cast(Object object) {
		return (TblChannelCnapsMap) object;
	}

	
	public TblChannelCnapsMap load(java.lang.String key)
			throws org.hibernate.HibernateException {
		return (TblChannelCnapsMap) load(getReferenceClass(), key);
	}

	
	public TblChannelCnapsMap get(java.lang.String key)
			throws org.hibernate.HibernateException {
		return (TblChannelCnapsMap) get(getReferenceClass(), key);
	}

	
	public void save(TblChannelCnapsMap tblChannelCnapsMap)
			throws org.hibernate.HibernateException {
		 super.save(tblChannelCnapsMap);
	}

	
	public void saveOrUpdate(TblChannelCnapsMap tblChannelCnapsMap)
			throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblChannelCnapsMap);
	}

	public void update(TblChannelCnapsMap tblChannelCnapsMap)
			throws org.hibernate.HibernateException {
		super.update(tblChannelCnapsMap);
	}

	
	public void delete(TblChannelCnapsMap tblChannelCnapsMap)
			throws org.hibernate.HibernateException {
		super.delete(tblChannelCnapsMap);
	}

	public void delete(java.lang.String id)
			throws org.hibernate.HibernateException {
		super.delete(load(id));
	}
}