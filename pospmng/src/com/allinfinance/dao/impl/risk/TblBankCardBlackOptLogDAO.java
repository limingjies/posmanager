package com.allinfinance.dao.impl.risk;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.risk.TblBankCardBlackOptLog;
import com.allinfinance.po.risk.TblBankCardBlackOptLogPK;

public class TblBankCardBlackOptLogDAO extends _RootDAO<TblBankCardBlackOptLog> implements com.allinfinance.dao.iface.risk.TblBankCardBlackOptLogDAO{

public TblBankCardBlackOptLogDAO () {}

public Class<TblBankCardBlackOptLog> getReferenceClass () {
	return TblBankCardBlackOptLog.class;
}

public TblBankCardBlackOptLog cast (Object object) {
	return (TblBankCardBlackOptLog) object;
}

public TblBankCardBlackOptLog load(TblBankCardBlackOptLogPK key)
	throws org.hibernate.HibernateException {
	return (TblBankCardBlackOptLog) load(getReferenceClass(), key);
}

public TblBankCardBlackOptLog get(TblBankCardBlackOptLogPK key)
	throws org.hibernate.HibernateException{
	return (TblBankCardBlackOptLog) get(getReferenceClass(), key);
}

public void save(TblBankCardBlackOptLog tblBankCardBlackOptLog)
	throws org.hibernate.HibernateException{
	super.save(tblBankCardBlackOptLog);
}

public void update(TblBankCardBlackOptLog tblBankCardBlackOptLog)
	throws org.hibernate.HibernateException{
	super.update(tblBankCardBlackOptLog);
}


public void delete(TblBankCardBlackOptLog tblBankCardBlackOptLog)
	throws org.hibernate.HibernateException {
	super.delete((Object) tblBankCardBlackOptLog);
}

}