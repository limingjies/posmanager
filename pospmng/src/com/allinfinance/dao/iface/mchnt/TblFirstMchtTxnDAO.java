package com.allinfinance.dao.iface.mchnt;

import com.allinfinance.po.mchnt.TblFirstMchtTxn;

public interface TblFirstMchtTxnDAO {
	
	public TblFirstMchtTxn load(java.lang.String key) throws org.hibernate.HibernateException;

	public TblFirstMchtTxn get(java.lang.String key) throws org.hibernate.HibernateException;

	public void save(TblFirstMchtTxn tblFirstMchtTxn) throws org.hibernate.HibernateException;

	public void saveOrUpdate(TblFirstMchtTxn tblFirstMchtTxn) throws org.hibernate.HibernateException;

	public void update(TblFirstMchtTxn tblFirstMchtTxn) throws org.hibernate.HibernateException;

	public void delete(TblFirstMchtTxn tblFirstMchtTxn) throws org.hibernate.HibernateException;
	
	public void delete(java.lang.String id) throws org.hibernate.HibernateException;
	
}
