package com.allinfinance.dao.iface.settle;

import com.allinfinance.po.settle.TblPayTypeSmall;


public interface TblPayTypeSmallDAO {
	public TblPayTypeSmall get(java.lang.String key) throws org.hibernate.HibernateException;

	public TblPayTypeSmall load(java.lang.String key) throws org.hibernate.HibernateException;


	public void save(TblPayTypeSmall tblPayTypeSmall) throws org.hibernate.HibernateException;

	public void saveOrUpdate(TblPayTypeSmall tblPayTypeSmall) throws org.hibernate.HibernateException;

	public void update(TblPayTypeSmall tblPayTypeSmall) throws org.hibernate.HibernateException;

	public void delete(java.lang.String key) throws org.hibernate.HibernateException;

	public void delete(TblPayTypeSmall tblPayTypeSmall) throws org.hibernate.HibernateException;


}