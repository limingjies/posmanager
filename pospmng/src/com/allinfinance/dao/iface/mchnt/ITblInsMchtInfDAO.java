package com.allinfinance.dao.iface.mchnt;

import com.allinfinance.po.mchnt.TblInsMchtInf;

public interface ITblInsMchtInfDAO {
	
	public Class<TblInsMchtInf> getReferenceClass () ;
	
	public TblInsMchtInf cast (Object object);
	
	public TblInsMchtInf load(java.lang.String key)
			throws org.hibernate.HibernateException;
	
	public TblInsMchtInf get(java.lang.String key)
			throws org.hibernate.HibernateException;

	public java.lang.String save(TblInsMchtInf tblInsMchtInfTmp)
			throws org.hibernate.HibernateException;

	public void saveOrUpdate(TblInsMchtInf tblInsMchtInfTmp)
			throws org.hibernate.HibernateException;
	
	public void update(TblInsMchtInf tblInsMchtInfTmp)
			throws org.hibernate.HibernateException;

	public void delete(TblInsMchtInf tblInsMchtInfTmp)
			throws org.hibernate.HibernateException;
	
	public void delete(java.lang.String id)
		throws org.hibernate.HibernateException;

}
