package com.allinfinance.dao.impl.mchnt;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpTmpDAO;
import com.allinfinance.po.mchnt.TblMchtCashInfTmpTmp;

/**
 * A data access object (DAO) providing persistence and search support for
 * TblMchtCashInfTmpTmpTmpTmp entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.allinfinance.po.mchnt.TblMchtCashInfTmpTmpTmpTmp
 * @author MyEclipse Persistence Tools
 */
public class TblMchtCashInfTmpTmpDAO extends _RootDAO<TblMchtCashInfTmpTmp> implements ITblMchtCashInfTmpTmpDAO{

	/* (non-Javadoc)
	 * @see com.allinfinance.dao._RootDAO#getReferenceClass()
	 */
	@Override
	public Class getReferenceClass() {
		// TODO Auto-generated method stub
		return TblMchtCashInfTmpTmp.class;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpTmpDAO#cast(java.lang.Object)
	 */
	@Override
	public TblMchtCashInfTmpTmp cast(Object object) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpTmpDAO#load(java.lang.String)
	 */
	@Override
	public TblMchtCashInfTmpTmp load(String key) throws HibernateException {
		return (TblMchtCashInfTmpTmp) this.getHibernateTemplate().load(getReferenceClass(), key);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpTmpDAO#get(java.lang.String)
	 */
	@Override
	public TblMchtCashInfTmpTmp get(String key) throws HibernateException {
		return (TblMchtCashInfTmpTmp) this.getHibernateTemplate().get(getReferenceClass(), key);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpTmpDAO#save(com.allinfinance.po.mchnt.TblMchtCashInfTmpTmp)
	 */
	@Override
	public Serializable save(TblMchtCashInfTmpTmp TblMchtCashInfTmpTmp)
			throws HibernateException {
		return this.getHibernateTemplate().save(TblMchtCashInfTmpTmp);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpTmpDAO#saveOrUpdate(com.allinfinance.po.mchnt.TblMchtCashInfTmpTmp)
	 */
	@Override
	public void saveOrUpdate(TblMchtCashInfTmpTmp TblMchtCashInfTmpTmp)
			throws HibernateException {
		this.getHibernateTemplate().saveOrUpdate(TblMchtCashInfTmpTmp);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpTmpDAO#update(com.allinfinance.po.mchnt.TblMchtCashInfTmpTmp)
	 */
	@Override
	public void update(TblMchtCashInfTmpTmp TblMchtCashInfTmpTmp) throws HibernateException {
		this.getHibernateTemplate().update(TblMchtCashInfTmpTmp);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpTmpDAO#delete(com.allinfinance.po.mchnt.TblMchtCashInfTmpTmp)
	 */
	@Override
	public void delete(TblMchtCashInfTmpTmp TblMchtCashInfTmpTmp) throws HibernateException {
		this.getHibernateTemplate().delete(TblMchtCashInfTmpTmp);
	}}