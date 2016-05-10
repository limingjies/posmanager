package com.allinfinance.dao.impl.mchnt;

import java.io.Serializable;

import org.hibernate.HibernateException;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtCashInfDAO;
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
public class TblMchtCashInfDAO extends _RootDAO<TblMchtCashInf> implements ITblMchtCashInfDAO{

	/* (non-Javadoc)
	 * @see com.allinfinance.dao._RootDAO#getReferenceClass()
	 */
	@Override
	public Class getReferenceClass() {
		// TODO Auto-generated method stub
		return TblMchtCashInf.class;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfDAO#cast(java.lang.Object)
	 */
	@Override
	public TblMchtCashInf cast(Object object) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfDAO#load(java.lang.String)
	 */
	@Override
	public TblMchtCashInf load(String key) throws HibernateException {
		return (TblMchtCashInf) this.getHibernateTemplate().load(getReferenceClass(), key);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfDAO#get(java.lang.String)
	 */
	@Override
	public TblMchtCashInf get(String key) throws HibernateException {
		return (TblMchtCashInf) this.getHibernateTemplate().get(getReferenceClass(), key);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfDAO#save(com.allinfinance.po.mchnt.TblMchtCashInf)
	 */
	@Override
	public Serializable save(TblMchtCashInf TblMchtCashInf)
			throws HibernateException {
		return this.getHibernateTemplate().save(TblMchtCashInf);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfDAO#saveOrUpdate(com.allinfinance.po.mchnt.TblMchtCashInf)
	 */
	@Override
	public void saveOrUpdate(TblMchtCashInf TblMchtCashInf)
			throws HibernateException {
		this.getHibernateTemplate().saveOrUpdate(TblMchtCashInf);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfDAO#update(com.allinfinance.po.mchnt.TblMchtCashInf)
	 */
	@Override
	public void update(TblMchtCashInf TblMchtCashInf) throws HibernateException {
		this.getHibernateTemplate().update(TblMchtCashInf);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfDAO#delete(com.allinfinance.po.mchnt.TblMchtCashInf)
	 */
	@Override
	public void delete(TblMchtCashInf TblMchtCashInf) throws HibernateException {
		this.getHibernateTemplate().delete(TblMchtCashInf);
	}}