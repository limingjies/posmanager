package com.allinfinance.dao.iface.mchnt;

import java.io.Serializable;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.mchnt.TblMchtCashInf;
import com.allinfinance.po.mchnt.TblMchtCashInfTmpTmp;

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
public interface ITblMchtCashInfTmpTmpDAO  {



	
	public Class<TblMchtCashInfTmpTmp> getReferenceClass ();

	public TblMchtCashInfTmpTmp cast (Object object);


	public TblMchtCashInfTmpTmp load(String key)
		throws org.hibernate.HibernateException;

	public TblMchtCashInfTmpTmp get(String key)
		throws org.hibernate.HibernateException;


	public Serializable save(TblMchtCashInfTmpTmp TblMchtCashInfTmpTmp)
		throws org.hibernate.HibernateException;

	public void saveOrUpdate(TblMchtCashInfTmpTmp TblMchtCashInfTmpTmp)
		throws org.hibernate.HibernateException;

	public void update(TblMchtCashInfTmpTmp TblMchtCashInfTmpTmp)
		throws org.hibernate.HibernateException;

	public void delete(TblMchtCashInfTmpTmp TblMchtCashInfTmpTmp)
		throws org.hibernate.HibernateException;


}