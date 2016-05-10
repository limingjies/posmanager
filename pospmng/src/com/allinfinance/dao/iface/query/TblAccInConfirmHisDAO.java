package com.allinfinance.dao.iface.query;

import com.allinfinance.po.query.TblAccInConfirmHis;


public interface TblAccInConfirmHisDAO {
	public TblAccInConfirmHis get(java.lang.String key) throws org.hibernate.HibernateException;

	public TblAccInConfirmHis load(java.lang.String key) throws org.hibernate.HibernateException;


	public void save(TblAccInConfirmHis tblAccInConfirmHis) throws org.hibernate.HibernateException;

	public void saveOrUpdate(TblAccInConfirmHis tblAccInConfirmHis) throws org.hibernate.HibernateException;

	public void update(TblAccInConfirmHis tblAccInConfirmHis) throws org.hibernate.HibernateException;

	public void delete(java.lang.String key) throws org.hibernate.HibernateException;

	public void delete(TblAccInConfirmHis tblAccInConfirmHis) throws org.hibernate.HibernateException;


}