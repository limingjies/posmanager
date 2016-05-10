package com.allinfinance.dao.iface.mchnt;

import com.allinfinance.po.mchnt.TblFirstMchtInf;

public interface ITblFirstMchtInfDAO {
	
	public Class<TblFirstMchtInf> getReferenceClass ();

	public TblFirstMchtInf cast (Object object);
	
	public TblFirstMchtInf load(java.lang.String key)
			throws org.hibernate.HibernateException;

	public TblFirstMchtInf get(java.lang.String key)
			throws org.hibernate.HibernateException;

	public java.lang.String save(TblFirstMchtInf tblFirstMchtInfTmp)
			throws org.hibernate.HibernateException;

	public void saveOrUpdate(TblFirstMchtInf tblFirstMchtInfTmp)
			throws org.hibernate.HibernateException;

	public void update(TblFirstMchtInf tblFirstMchtInfTmp)
			throws org.hibernate.HibernateException;

	public void delete(TblFirstMchtInf tblFirstMchtInfTmp)
			throws org.hibernate.HibernateException;
	
	public void delete(java.lang.String id)
		throws org.hibernate.HibernateException;

	
}
