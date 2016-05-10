package com.allinfinance.dao.impl.mchnt;

import org.hibernate.HibernateException;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.dao.iface.mchnt.ITblCashRateInfDAO;
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
public class TblCashRateInfDAO extends _RootDAO<TblCashRateInf> implements ITblCashRateInfDAO{

	/* (non-Javadoc)
	 * @see com.allinfinance.dao._RootDAO#getReferenceClass()
	 */
	@Override
	public Class getReferenceClass() {
		return TblCashRateInf.class;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblCashRateInfDAO#cast(java.lang.Object)
	 */
	@Override
	public TblCashRateInf cast(Object object) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblCashRateInfDAO#load(java.lang.String)
	 */
	@Override
	public TblCashRateInf load(String key) throws HibernateException {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblCashRateInfDAO#get(java.lang.String)
	 */
	@Override
	public TblCashRateInf get(String key) throws HibernateException {
		return (TblCashRateInf) this.getHibernateTemplate().get(getReferenceClass(), key);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblCashRateInfDAO#save(com.allinfinance.po.mchnt.TblCashRateInf)
	 */
	@Override
	public String save(TblCashRateInf tblCashRateInf)
			throws HibernateException {
		return (String) this.getHibernateTemplate().save(tblCashRateInf);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblCashRateInfDAO#saveOrUpdate(com.allinfinance.po.mchnt.TblCashRateInf)
	 */
	@Override
	public void saveOrUpdate(TblCashRateInf tblCashRateInf)
			throws HibernateException {
		this.getHibernateTemplate().saveOrUpdate(tblCashRateInf);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblCashRateInfDAO#update(com.allinfinance.po.mchnt.TblCashRateInf)
	 */
	@Override
	public void update(TblCashRateInf tblCashRateInf)
			throws HibernateException {
		this.getHibernateTemplate().update(tblCashRateInf);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblCashRateInfDAO#delete(com.allinfinance.po.mchnt.TblCashRateInf)
	 */
	@Override
	public void delete(TblCashRateInf tblCashRateInf)
			throws HibernateException {
		this.getHibernateTemplate().delete(tblCashRateInf);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblCashRateInfDAO#delete(java.lang.String)
	 */
	@Override
	public void delete(String id) throws HibernateException {
	}}