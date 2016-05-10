package com.allinfinance.dao.iface.settle;

import com.allinfinance.po.settle.TblChannelCnapsMap;


public interface TblChannelCnapsMapDAO {
	public TblChannelCnapsMap get(java.lang.String key) throws org.hibernate.HibernateException;

	public TblChannelCnapsMap load(java.lang.String key) throws org.hibernate.HibernateException;


	public void save(TblChannelCnapsMap tblChannelCnapsMap) throws org.hibernate.HibernateException;

	public void saveOrUpdate(TblChannelCnapsMap tblChannelCnapsMap) throws org.hibernate.HibernateException;

	public void update(TblChannelCnapsMap tblChannelCnapsMap) throws org.hibernate.HibernateException;

	public void delete(java.lang.String key) throws org.hibernate.HibernateException;

	public void delete(TblChannelCnapsMap tblChannelCnapsMap) throws org.hibernate.HibernateException;


}