package com.allinfinance.dao.iface.mchnt;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.mchnt.TblCashRateInf;
import com.allinfinance.po.mchnt.TblCashRateInf;

/**
 * A data access object (DAO) providing persistence and search support for
 * TblCashRateInf entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.allinfinance.po.mchnt.TblCashRateInf
 * @author MyEclipse Persistence Tools
 */
public interface ITblCashRateInfDAO  {
	
	public Class<TblCashRateInf> getReferenceClass ();

	public TblCashRateInf cast (Object object);


	public TblCashRateInf load(java.lang.String key)
		throws org.hibernate.HibernateException;

	public TblCashRateInf get(java.lang.String key)
		throws org.hibernate.HibernateException;


	public java.lang.String save(TblCashRateInf tblMchtBaseInfTmp)
		throws org.hibernate.HibernateException;

	public void saveOrUpdate(TblCashRateInf tblMchtBaseInfTmp)
		throws org.hibernate.HibernateException;

	public void update(TblCashRateInf tblMchtBaseInfTmp)
		throws org.hibernate.HibernateException;

	public void delete(TblCashRateInf tblMchtBaseInfTmp)
		throws org.hibernate.HibernateException;

	public void delete(java.lang.String id)
		throws org.hibernate.HibernateException;

}