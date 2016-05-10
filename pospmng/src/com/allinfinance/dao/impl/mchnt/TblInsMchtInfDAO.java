package com.allinfinance.dao.impl.mchnt;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.dao.iface.mchnt.ITblInsMchtInfDAO;
import com.allinfinance.po.mchnt.TblInsMchtInf;

public class TblInsMchtInfDAO extends _RootDAO<TblInsMchtInf> implements ITblInsMchtInfDAO {
	
	public TblInsMchtInfDAO () {}

	public Class<TblInsMchtInf> getReferenceClass () {
		return TblInsMchtInf.class;
	}

	public TblInsMchtInf cast (Object object) {
		return (TblInsMchtInf) object;
	}

	public TblInsMchtInf load(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (TblInsMchtInf) load(getReferenceClass(), key);
	}

	public TblInsMchtInf get(java.lang.String key)
			throws org.hibernate.HibernateException {
			return (TblInsMchtInf) get(getReferenceClass(), key);
	}

	public java.lang.String save(TblInsMchtInf tblInsMchtInf)
			throws org.hibernate.HibernateException {
			return (java.lang.String) super.save(tblInsMchtInf);
	}

	
	public void saveOrUpdate(TblInsMchtInf tblInsMchtInfTmp)
			throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblInsMchtInfTmp);
	}

	
	
	public void update(TblInsMchtInf tblInsMchtInfTmp)
		throws org.hibernate.HibernateException {
		super.update(tblInsMchtInfTmp);
	}

	public void delete(TblInsMchtInf tblInsMchtInfTmp)
		throws org.hibernate.HibernateException {
		super.delete((Object) tblInsMchtInfTmp);
	}

	public void delete(java.lang.String id)
		throws org.hibernate.HibernateException {
		super.delete((Object) load(id));
	}

}