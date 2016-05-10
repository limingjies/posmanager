package com.allinfinance.dao.iface.mchnt;

import java.io.Serializable;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.mchnt.TblMchtCashInfTmp;

/**
 * A data access object (DAO) providing persistence and search support for
 * TblMchtCashInfTmp entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.allinfinance.po.mchnt.TblMchtCashInfTmp
 * @author MyEclipse Persistence Tools
 */
public interface ITblMchtCashInfTmpDAO  {



	
	public Class<TblMchtCashInfTmp> getReferenceClass ();

	public TblMchtCashInfTmp cast (Object object);


	public TblMchtCashInfTmp load(String key)
		throws org.hibernate.HibernateException;

	public TblMchtCashInfTmp get(String key)
		throws org.hibernate.HibernateException;


	public Serializable save(TblMchtCashInfTmp TblMchtCashInfTmp)
		throws org.hibernate.HibernateException;

	public void saveOrUpdate(TblMchtCashInfTmp TblMchtCashInfTmp)
		throws org.hibernate.HibernateException;

	public void update(TblMchtCashInfTmp TblMchtCashInfTmp)
		throws org.hibernate.HibernateException;

	public void delete(TblMchtCashInfTmp TblMchtCashInfTmp)
		throws org.hibernate.HibernateException;


}