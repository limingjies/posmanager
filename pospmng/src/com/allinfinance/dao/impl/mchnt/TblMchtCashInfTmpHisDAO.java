package com.allinfinance.dao.impl.mchnt;

import java.io.Serializable;

import org.hibernate.HibernateException;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpHisDAO;
import com.allinfinance.po.mchnt.TblMchtCashInfTmpHis;
import com.allinfinance.po.mchnt.TblMchtCashInfTmpHisId;

/**
 * A data access object (DAO) providing persistence and search support for
 * TblMchtCashInfTmpHisTmp entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.allinfinance.po.mchnt.TblMchtCashInfTmpHisTmp
 * @author MyEclipse Persistence Tools
 */
public class TblMchtCashInfTmpHisDAO extends _RootDAO<TblMchtCashInfTmpHis> implements ITblMchtCashInfTmpHisDAO{

	/* (non-Javadoc)
	 * @see com.allinfinance.dao._RootDAO#getReferenceClass()
	 */
	@Override
	public Class getReferenceClass() {
		// TODO Auto-generated method stub
		return TblMchtCashInfTmpHis.class;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpHisHisDAO#cast(java.lang.Object)
	 */
	@Override
	public TblMchtCashInfTmpHis cast(Object object) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpHisHisDAO#load(java.lang.String)
	 */
	@Override
	public TblMchtCashInfTmpHis load(TblMchtCashInfTmpHisId key) throws HibernateException {
		return (TblMchtCashInfTmpHis) this.getHibernateTemplate().load(getReferenceClass(), key);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpHisHisDAO#get(java.lang.String)
	 */
	@Override
	public TblMchtCashInfTmpHis get(TblMchtCashInfTmpHisId key) throws HibernateException {
		return (TblMchtCashInfTmpHis) this.getHibernateTemplate().get(getReferenceClass(), key);
	}

	

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpHisHisDAO#saveOrUpdate(com.allinfinance.po.mchnt.TblMchtCashInfTmpHis)
	 */
	@Override
	public void saveOrUpdate(TblMchtCashInfTmpHis TblMchtCashInfTmpHis)
			throws HibernateException {
		this.getHibernateTemplate().saveOrUpdate(TblMchtCashInfTmpHis);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpHisDAO#update(com.allinfinance.po.mchnt.TblMchtCashInfTmpHis)
	 */
	@Override
	public void update(TblMchtCashInfTmpHis TblMchtCashInfTmpHis) throws HibernateException {
		this.getHibernateTemplate().update(TblMchtCashInfTmpHis);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpHisDAO#delete(com.allinfinance.po.mchnt.TblMchtCashInfTmpHis)
	 */
	@Override
	public void delete(TblMchtCashInfTmpHis TblMchtCashInfTmpHis) throws HibernateException {
		this.getHibernateTemplate().delete(TblMchtCashInfTmpHis);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpHisDAO#save(com.allinfinance.po.mchnt.TblMchtCashInfTmpHis)
	 */
	@Override
	public Serializable save(TblMchtCashInfTmpHis TblMchtCashInfTmpHis)
			throws HibernateException {
		// TODO Auto-generated method stub
		return this.getHibernateTemplate().save(TblMchtCashInfTmpHis);
	}}