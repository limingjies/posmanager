package com.allinfinance.dao.base;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblBrhInfo;


public class TblBrhInfoDAO extends _RootDAO<com.allinfinance.po.TblBrhInfo> implements com.allinfinance.dao.iface.base.TblBrhInfoDAO {

	public TblBrhInfoDAO () {}
	
	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.TblBrhInfoDAO#findAll()
	 */
	public List<TblBrhInfo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Class<com.allinfinance.po.TblBrhInfo> getReferenceClass () {
		return com.allinfinance.po.TblBrhInfo.class;
	}


	/**
	 * Cast the object as a com.allinfinance.po.brh.TblBrhInfo
	 */
	public com.allinfinance.po.TblBrhInfo cast (Object object) {
		return (com.allinfinance.po.TblBrhInfo) object;
	}


	public com.allinfinance.po.TblBrhInfo load(java.lang.String key)
	{
		return (com.allinfinance.po.TblBrhInfo) load(getReferenceClass(), key);
	}

	public com.allinfinance.po.TblBrhInfo get(java.lang.String key)
	{
		return (com.allinfinance.po.TblBrhInfo) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblBrhInfo a transient instance of a persistent class
	 * @return the class identifier
	 */
	public java.lang.String save(com.allinfinance.po.TblBrhInfo tblBrhInfo)
	{
		return (java.lang.String) super.save(tblBrhInfo);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblBrhInfo a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.allinfinance.po.TblBrhInfo tblBrhInfo)
	{
		super.saveOrUpdate(tblBrhInfo);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblBrhInfo a transient instance containing updated state
	 */
	public void update(com.allinfinance.po.TblBrhInfo tblBrhInfo)
	{
		super.update(tblBrhInfo);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblBrhInfo the instance to be removed
	 */
	public void delete(com.allinfinance.po.TblBrhInfo tblBrhInfo)
	{
		super.delete((Object) tblBrhInfo);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id)
	{
		super.delete((Object) load(id));
	}

	@Override
	public List<TblBrhInfo> getUpbrhid(String brh_id) {
		DetachedCriteria criteria=DetachedCriteria.forClass(TblBrhInfo.class);
		criteria.add(Restrictions.eq("UpBrhId", brh_id));
		return this.getHibernateTemplate().findByCriteria(criteria);
	}
	
	@Override
	public TblBrhInfo getByCreateNewNo(String createNewNo) {
		DetachedCriteria criteria=DetachedCriteria.forClass(TblBrhInfo.class);
		criteria.add(Restrictions.eq("createNewNo", createNewNo));
		List<TblBrhInfo> list = this.getHibernateTemplate().findByCriteria(criteria);
		if (list != null) {
			return list.get(0);
		}
		return null;
	}
}