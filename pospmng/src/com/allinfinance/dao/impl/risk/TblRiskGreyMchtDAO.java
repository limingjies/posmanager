package com.allinfinance.dao.impl.risk;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.risk.TblRiskGreyMcht;
import com.allinfinance.po.risk.TblRiskGreyMchtPK;

public class TblRiskGreyMchtDAO extends _RootDAO<TblRiskGreyMcht> implements com.allinfinance.dao.iface.risk.TblRiskGreyMchtDAO{

public TblRiskGreyMchtDAO () {}

public Class<TblRiskGreyMcht> getReferenceClass () {
	return TblRiskGreyMcht.class;
}

public TblRiskGreyMcht cast (Object object) {
	return (TblRiskGreyMcht) object;
}

public TblRiskGreyMcht load(TblRiskGreyMchtPK key)
	throws org.hibernate.HibernateException {
	return (TblRiskGreyMcht) load(getReferenceClass(), key);
}

public TblRiskGreyMcht get(TblRiskGreyMchtPK key)
	throws org.hibernate.HibernateException{
	return (TblRiskGreyMcht) get(getReferenceClass(), key);
}

public void save(TblRiskGreyMcht tblRiskGreyMcht)
	throws org.hibernate.HibernateException{
	super.save(tblRiskGreyMcht);
}

public void update(TblRiskGreyMcht tblRiskGreyMcht)
	throws org.hibernate.HibernateException{
	super.update(tblRiskGreyMcht);
}


public void delete(TblRiskGreyMcht tblRiskGreyMcht)
	throws org.hibernate.HibernateException {
	super.delete((Object) tblRiskGreyMcht);
}


public void delete(TblRiskGreyMchtPK key)
	throws org.hibernate.HibernateException {
	super.delete(load(key));
}

}