package com.allinfinance.dao.iface.settle;

import com.allinfinance.po.settle.TblPayChannelInfo;


public interface TblPayChannelInfoDAO {
	public TblPayChannelInfo get(java.lang.String key) throws org.hibernate.HibernateException;

	public TblPayChannelInfo load(java.lang.String key) throws org.hibernate.HibernateException;


	public void save(TblPayChannelInfo tblPayChannelInfo) throws org.hibernate.HibernateException;

	public void saveOrUpdate(TblPayChannelInfo tblPayChannelInfo) throws org.hibernate.HibernateException;

	public void update(TblPayChannelInfo tblPayChannelInfo) throws org.hibernate.HibernateException;

	public void delete(java.lang.String key) throws org.hibernate.HibernateException;

	public void delete(TblPayChannelInfo tblPayChannelInfo) throws org.hibernate.HibernateException;


}