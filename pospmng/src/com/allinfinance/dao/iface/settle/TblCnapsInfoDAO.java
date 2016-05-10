package com.allinfinance.dao.iface.settle;

import com.allinfinance.po.settle.TblCnapsInfo;


public interface TblCnapsInfoDAO {
	public TblCnapsInfo get(java.lang.String key) throws org.hibernate.HibernateException;

	public TblCnapsInfo load(java.lang.String key) throws org.hibernate.HibernateException;


	public void save(TblCnapsInfo tblCnapsInfo) throws org.hibernate.HibernateException;

	public void saveOrUpdate(TblCnapsInfo tblCnapsInfo) throws org.hibernate.HibernateException;

	public void update(TblCnapsInfo tblCnapsInfo) throws org.hibernate.HibernateException;

	public void delete(java.lang.String key) throws org.hibernate.HibernateException;

	public void delete(TblCnapsInfo tblCnapsInfo) throws org.hibernate.HibernateException;


}