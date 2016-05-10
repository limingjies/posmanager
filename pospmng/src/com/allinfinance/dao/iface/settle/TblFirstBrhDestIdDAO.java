package com.allinfinance.dao.iface.settle;

import com.allinfinance.po.settle.TblFirstBrhDestId;


public interface TblFirstBrhDestIdDAO {
	public TblFirstBrhDestId get(java.lang.String key) throws org.hibernate.HibernateException;

	public TblFirstBrhDestId load(java.lang.String key) throws org.hibernate.HibernateException;


	public void save(TblFirstBrhDestId tblFirstBrhDestId) throws org.hibernate.HibernateException;

	public void saveOrUpdate(TblFirstBrhDestId tblFirstBrhDestId) throws org.hibernate.HibernateException;

	public void update(TblFirstBrhDestId tblFirstBrhDestId) throws org.hibernate.HibernateException;

	public void delete(java.lang.String key) throws org.hibernate.HibernateException;

	public void delete(TblFirstBrhDestId tblFirstBrhDestId) throws org.hibernate.HibernateException;


}