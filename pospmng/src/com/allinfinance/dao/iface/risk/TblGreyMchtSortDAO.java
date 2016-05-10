package com.allinfinance.dao.iface.risk;

import com.allinfinance.po.risk.TblGreyMchtSort;


public interface TblGreyMchtSortDAO {
	public TblGreyMchtSort get(java.lang.String key) throws org.hibernate.HibernateException;

	public TblGreyMchtSort load(java.lang.String key) throws org.hibernate.HibernateException;


	public void save(TblGreyMchtSort tblGreyMchtSort) throws org.hibernate.HibernateException;

	public void saveOrUpdate(TblGreyMchtSort tblGreyMchtSort) throws org.hibernate.HibernateException;

	public void update(TblGreyMchtSort tblGreyMchtSort) throws org.hibernate.HibernateException;

	public void delete(java.lang.String key) throws org.hibernate.HibernateException;

	public void delete(TblGreyMchtSort tblGreyMchtSort) throws org.hibernate.HibernateException;


}