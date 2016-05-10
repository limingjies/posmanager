package com.allinfinance.dao.impl.risk;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.risk.TblWhiteMchtCheck;
import com.allinfinance.po.risk.TblWhiteMchtCheckPK;

public class TblWhiteMchtCheckDAO extends _RootDAO<TblWhiteMchtCheck> implements com.allinfinance.dao.iface.risk.TblWhiteMchtCheckDAO{

public TblWhiteMchtCheckDAO () {}

public Class<TblWhiteMchtCheck> getReferenceClass () {
	return TblWhiteMchtCheck.class;
}

public TblWhiteMchtCheck cast (Object object) {
	return (TblWhiteMchtCheck) object;
}

public TblWhiteMchtCheck load(TblWhiteMchtCheckPK key)
	throws org.hibernate.HibernateException {
	return (TblWhiteMchtCheck) load(getReferenceClass(), key);
}

public TblWhiteMchtCheck get(TblWhiteMchtCheckPK key)
	throws org.hibernate.HibernateException{
	return (TblWhiteMchtCheck) get(getReferenceClass(), key);
}

public void save(TblWhiteMchtCheck tblWhiteMchtCheck)
	throws org.hibernate.HibernateException{
	super.save(tblWhiteMchtCheck);
}

public void update(TblWhiteMchtCheck tblWhiteMchtCheck)
	throws org.hibernate.HibernateException{
	super.update(tblWhiteMchtCheck);
}


public void delete(TblWhiteMchtCheck tblWhiteMchtCheck)
	throws org.hibernate.HibernateException {
	super.delete((Object) tblWhiteMchtCheck);
}

}