package com.allinfinance.dao.impl.settle;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.settle.TblPayChannelInfo;



public class TblPayChannelInfoDAO extends _RootDAO<TblPayChannelInfo> implements com.allinfinance.dao.iface.settle.TblPayChannelInfoDAO {

	public TblPayChannelInfoDAO() {
	}

	
	public Class<TblPayChannelInfo> getReferenceClass() {
		return TblPayChannelInfo.class;
	}

	public TblPayChannelInfo cast(Object object) {
		return (TblPayChannelInfo) object;
	}

	
	public TblPayChannelInfo load(java.lang.String key)
			throws org.hibernate.HibernateException {
		return (TblPayChannelInfo) load(getReferenceClass(), key);
	}

	
	public TblPayChannelInfo get(java.lang.String key)
			throws org.hibernate.HibernateException {
		return (TblPayChannelInfo) get(getReferenceClass(), key);
	}

	
	public void save(TblPayChannelInfo tblPayChannelInfo)
			throws org.hibernate.HibernateException {
		 super.save(tblPayChannelInfo);
	}

	
	public void saveOrUpdate(TblPayChannelInfo tblPayChannelInfo)
			throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblPayChannelInfo);
	}

	public void update(TblPayChannelInfo tblPayChannelInfo)
			throws org.hibernate.HibernateException {
		super.update(tblPayChannelInfo);
	}

	
	public void delete(TblPayChannelInfo tblPayChannelInfo)
			throws org.hibernate.HibernateException {
		super.delete(tblPayChannelInfo);
	}

	public void delete(java.lang.String id)
			throws org.hibernate.HibernateException {
		super.delete(load(id));
	}
}