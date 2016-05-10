package com.allinfinance.dao.impl.mchnt;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.dao.iface.mchnt.ITblFirstMchtInfDAO;
import com.allinfinance.po.mchnt.TblFirstMchtInf;


public class TblFirstMchtInfDAO extends _RootDAO<TblFirstMchtInf> implements ITblFirstMchtInfDAO {

	public TblFirstMchtInfDAO () {}
	
	public Class<TblFirstMchtInf> getReferenceClass () {
		return TblFirstMchtInf.class;
	}

	public TblFirstMchtInf cast (Object object) {
		return (TblFirstMchtInf) object;
	}

	public TblFirstMchtInf load(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (TblFirstMchtInf) load(getReferenceClass(), key);
	}

	public TblFirstMchtInf get(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (TblFirstMchtInf) get(getReferenceClass(), key);
	}

	public java.lang.String save(TblFirstMchtInf tblFirstMchtInf)
		throws org.hibernate.HibernateException {
		return (java.lang.String) super.save(tblFirstMchtInf);
	}

	public void saveOrUpdate(TblFirstMchtInf tblFirstMchtInf)
		throws org.hibernate.HibernateException {
		super.saveOrUpdate(tblFirstMchtInf);
	}


	public void update(TblFirstMchtInf tblFirstMchtInf)
		throws org.hibernate.HibernateException {
		super.update(tblFirstMchtInf);
	}

	public void delete(TblFirstMchtInf tblFirstMchtInf)
		throws org.hibernate.HibernateException {
		super.delete((Object) tblFirstMchtInf);
	}

	public void delete(java.lang.String id)
		throws org.hibernate.HibernateException {
		super.delete((Object) load(id));
	}

}