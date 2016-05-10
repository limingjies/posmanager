package com.allinfinance.dao.impl.base;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblBrhTlrInfo;
import com.allinfinance.po.TblBrhTlrInfoPK;


public class TblBrhTlrInfoDao extends _RootDAO<com.allinfinance.po.TblBrhTlrInfo> implements com.allinfinance.dao.iface.base.TblBrhTlrInfoDao{

public TblBrhTlrInfoDao () {}

/* (non-Javadoc)
 * @see com.allinfinance.dao._RootDAO#getReferenceClass()
 */

public Class<TblBrhTlrInfo> getReferenceClass () {
	return TblBrhTlrInfo.class;
}


/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.base.TblBrhTlrInfoDao#delete(java.lang.Integer)
 */
public void delete(TblBrhTlrInfoPK id) {
	super.delete((Object) load(id));
}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.base.TblBrhTlrInfoDao#delete(com.allinfinance.po.TblBrhTlrInfo)
 */
public void delete(TblBrhTlrInfo tblBrhTlrInfo) {
	super.delete((Object) tblBrhTlrInfo);
}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.base.TblBrhTlrInfoDao#get(com.allinfinance.po.TblBrhTlrInfoPK)
 */
public TblBrhTlrInfo get(TblBrhTlrInfoPK key) {
	return (TblBrhTlrInfo) super.get(getReferenceClass(), key);
}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.base.TblBrhTlrInfoDao#load(com.allinfinance.po.TblBrhTlrInfoPK)
 */
public TblBrhTlrInfo load(TblBrhTlrInfoPK key) {
	// TODO Auto-generated method stub
	return (TblBrhTlrInfo) super.load(getReferenceClass(),key);
}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.base.TblBrhTlrInfoDao#refresh(com.allinfinance.po.TblBrhTlrInfo)
 */
public void refresh(TblBrhTlrInfo tblBrhTlrInfo) {
	super.refresh(tblBrhTlrInfo);
}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.base.TblBrhTlrInfoDao#save(com.allinfinance.po.TblBrhTlrInfo)
 */
public TblBrhTlrInfoPK save(TblBrhTlrInfo tblBrhTlrInfo) {
	return (TblBrhTlrInfoPK) super.save(tblBrhTlrInfo);
}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.base.TblBrhTlrInfoDao#saveOrUpdate(com.allinfinance.po.TblBrhTlrInfo)
 */
public void saveOrUpdate(TblBrhTlrInfo tblBrhTlrInfo) {
	super.saveOrUpdate(tblBrhTlrInfo);
}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.base.TblBrhTlrInfoDao#update(com.allinfinance.po.TblBrhTlrInfo)
 */
public void update(TblBrhTlrInfo tblBrhTlrInfo) {
	super.update(tblBrhTlrInfo);
	
}


}