package com.allinfinance.dao.iface.mchnt;

import java.io.Serializable;

import com.allinfinance.po.mchnt.TblMchtCashInf;
import com.allinfinance.po.mchnt.TblMchtCashInfTmpHis;
import com.allinfinance.po.mchnt.TblMchtCashInfTmpHisId;

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
public interface ITblMchtCashInfTmpHisDAO  {



	
	public Class<TblMchtCashInfTmpHis> getReferenceClass ();

	public TblMchtCashInfTmpHis cast (Object object);


	public TblMchtCashInfTmpHis load(TblMchtCashInfTmpHisId key)
		throws org.hibernate.HibernateException;

	public TblMchtCashInfTmpHis get(TblMchtCashInfTmpHisId key)
		throws org.hibernate.HibernateException;


	public Serializable save(TblMchtCashInfTmpHis TblMchtCashInfTmpHis)
		throws org.hibernate.HibernateException;

	public void saveOrUpdate(TblMchtCashInfTmpHis TblMchtCashInfTmpHis)
		throws org.hibernate.HibernateException;

	public void update(TblMchtCashInfTmpHis TblMchtCashInfTmpHis)
		throws org.hibernate.HibernateException;

	public void delete(TblMchtCashInfTmpHis TblMchtCashInfTmpHis)
		throws org.hibernate.HibernateException;


}