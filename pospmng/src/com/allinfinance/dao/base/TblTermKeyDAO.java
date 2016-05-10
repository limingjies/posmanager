package com.allinfinance.dao.base;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblTermInfTmp;
import com.allinfinance.po.TblTermKey;


public class TblTermKeyDAO extends _RootDAO<com.allinfinance.po.TblTermKey> implements com.allinfinance.dao.iface.term.TblTermKeyDAO{

public TblTermKeyDAO () {}

/* (non-Javadoc)
 * @see com.allinfinance.dao.iface.TblTermKeyDAO#findAll()
 */
public List<TblTermKey> findAll() {
	// TODO Auto-generated method stub
	return null;
}

public Class<com.allinfinance.po.TblTermKey> getReferenceClass () {
	return com.allinfinance.po.TblTermKey.class;
}


/**
 * Cast the object as a com.allinfinance.po.TblTermKey
 */
public com.allinfinance.po.TblTermKey cast (Object object) {
	return (com.allinfinance.po.TblTermKey) object;
}


public com.allinfinance.po.TblTermKey load(com.allinfinance.po.TblTermKeyPK key)
{
	return (com.allinfinance.po.TblTermKey) load(getReferenceClass(), key);
}

public com.allinfinance.po.TblTermKey get(com.allinfinance.po.TblTermKeyPK key)
{
	return (com.allinfinance.po.TblTermKey) get(getReferenceClass(), key);
}

@SuppressWarnings("unchecked")
public java.util.List<com.allinfinance.po.TblTermKey> loadAll()
{
	return loadAll(getReferenceClass());
}





/**
 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
 * of the identifier property if the assigned generator is used.)
 * @param tblTermKey a transient instance of a persistent class
 * @return the class identifier
 */
public com.allinfinance.po.TblTermKeyPK save(com.allinfinance.po.TblTermKey tblTermKey)
{
	return (com.allinfinance.po.TblTermKeyPK) super.save(tblTermKey);
}

/**
 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
 * identifier property mapping.
 * @param tblTermKey a transient instance containing new or updated state
 */
public void saveOrUpdate(com.allinfinance.po.TblTermKey tblTermKey)
{
	super.saveOrUpdate(tblTermKey);
}


/**
 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
 * instance with the same identifier in the current session.
 * @param TblTermKey a transient instance containing updated state
 */
public void update(com.allinfinance.po.TblTermKey tblTermKey)
{
	super.update(tblTermKey);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param tblTermKey the instance to be removed
 */
public void delete(com.allinfinance.po.TblTermKey tblTermKey)
{
	super.delete((Object) tblTermKey);
}

/**
 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
 * Session or a transient instance with an identifier associated with existing persistent state.
 * @param id the instance ID to be removed
 */
public void delete(com.allinfinance.po.TblTermKeyPK id)
{
	super.delete((Object) load(id));
}

@Override
public List<TblTermKey> getByMchnt(String mchntId) {
	DetachedCriteria criteria=DetachedCriteria.forClass(TblTermKey.class);
	criteria.add(Restrictions.eq("MchtCd", mchntId));
	List<TblTermKey> list = this.getHibernateTemplate().findByCriteria(criteria);
	return list;
}

@Override
public void updateByTremId(String termId, String newMchtNo,
		String currentDateTime) {
	String sql="update tbl_term_key set mcht_cd='"+newMchtNo+"',rec_upd_ts='"+currentDateTime+"' where term_id='"+termId+"'";
}

}