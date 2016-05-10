package com.allinfinance.dao.iface.mchnt;

import java.io.Serializable;

import com.allinfinance.po.mchnt.TblMchtCashInf;

/**
 * A data access object (DAO) providing persistence and search support for
 * TblMchtCashInf entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.allinfinance.po.mchnt.TblMchtCashInf
 * @author MyEclipse Persistence Tools
 */
public interface ITblMchtCashInfDAO  {



	
	public Class<TblMchtCashInf> getReferenceClass ();

	public TblMchtCashInf cast (Object object);


	public TblMchtCashInf load(String key)
		throws org.hibernate.HibernateException;

	public TblMchtCashInf get(String key)
		throws org.hibernate.HibernateException;


	public Serializable save(TblMchtCashInf TblMchtCashInf)
		throws org.hibernate.HibernateException;

	public void saveOrUpdate(TblMchtCashInf TblMchtCashInf)
		throws org.hibernate.HibernateException;

	public void update(TblMchtCashInf TblMchtCashInf)
		throws org.hibernate.HibernateException;

	public void delete(TblMchtCashInf TblMchtCashInf)
		throws org.hibernate.HibernateException;


}