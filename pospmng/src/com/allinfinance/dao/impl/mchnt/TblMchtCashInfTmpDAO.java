package com.allinfinance.dao.impl.mchnt;

import java.io.Serializable;

import org.hibernate.HibernateException;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpTmpDAO;
import com.allinfinance.po.mchnt.TblMchtCashInfTmp;
import com.allinfinance.po.mchnt.TblMchtCashInfTmpTmp;

/**
 * A data access object (DAO) providing persistence and search support for
 * TblMchtCashInfTmpTmp entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.allinfinance.po.mchnt.TblMchtCashInfTmpTmp
 * @author MyEclipse Persistence Tools
 */
public class TblMchtCashInfTmpDAO extends _RootDAO<TblMchtCashInfTmp> implements ITblMchtCashInfTmpDAO{

	/* (non-Javadoc)
	 * @see com.allinfinance.dao._RootDAO#getReferenceClass()
	 */
	@Override
	public Class getReferenceClass() {
		// TODO Auto-generated method stub
		return TblMchtCashInfTmp.class;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpDAO#cast(java.lang.Object)
	 */
	@Override
	public TblMchtCashInfTmp cast(Object object) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpDAO#load(java.lang.String)
	 */
	@Override
	public TblMchtCashInfTmp load(String key) throws HibernateException {
		return (TblMchtCashInfTmp) this.getHibernateTemplate().load(getReferenceClass(), key);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpDAO#get(java.lang.String)
	 */
	@Override
	public TblMchtCashInfTmp get(String key) throws HibernateException {
		return (TblMchtCashInfTmp) this.getHibernateTemplate().get(getReferenceClass(), key);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpDAO#save(com.allinfinance.po.mchnt.TblMchtCashInfTmp)
	 */
	@Override
	public Serializable save(TblMchtCashInfTmp TblMchtCashInfTmp)
			throws HibernateException {
		return this.getHibernateTemplate().save(TblMchtCashInfTmp);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpDAO#saveOrUpdate(com.allinfinance.po.mchnt.TblMchtCashInfTmp)
	 */
	@Override
	public void saveOrUpdate(TblMchtCashInfTmp TblMchtCashInfTmp)
			throws HibernateException {
		this.getHibernateTemplate().saveOrUpdate(TblMchtCashInfTmp);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpDAO#update(com.allinfinance.po.mchnt.TblMchtCashInfTmp)
	 */
	@Override
	public void update(TblMchtCashInfTmp TblMchtCashInfTmp) throws HibernateException {
		this.getHibernateTemplate().update(TblMchtCashInfTmp);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpDAO#delete(com.allinfinance.po.mchnt.TblMchtCashInfTmp)
	 */
	@Override
	public void delete(TblMchtCashInfTmp TblMchtCashInfTmp) throws HibernateException {
		this.getHibernateTemplate().delete(TblMchtCashInfTmp);
	}}