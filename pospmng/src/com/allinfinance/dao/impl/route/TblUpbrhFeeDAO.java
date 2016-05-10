package com.allinfinance.dao.impl.route;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.route.TblUpbrhFee;

/**
 * A data access object (DAO) providing persistence and search support for
 * TblUpbrhFee entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see h.TblUpbrhFee
 * @author MyEclipse Persistence Tools
 */
public class TblUpbrhFeeDAO extends _RootDAO<TblUpbrhFee> implements
		com.allinfinance.dao.iface.route.TblUpbrhFeeDAO {

	public Class<TblUpbrhFee> getReferenceClass() {
		return TblUpbrhFee.class;
	}

	/**
	 * Cast the object as a func.TblUpbrhFee
	 */
	public TblUpbrhFee cast(Object object) {
		return (TblUpbrhFee) object;
	}

	public TblUpbrhFee load(String key) throws org.hibernate.HibernateException {
		return (TblUpbrhFee) load(getReferenceClass(), key);
	}

	public TblUpbrhFee get(String key) throws org.hibernate.HibernateException {
		return (TblUpbrhFee) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated
	 * identifier. (Or using the current value of the identifier property if the
	 * assigned generator is used.)
	 * 
	 * @param TblUpbrhFee
	 *            a transient instance of a persistent class
	 * @return the class identifier
	 */
	public void save(TblUpbrhFee TblUpbrhFee)
			throws org.hibernate.HibernateException {
		super.save(TblUpbrhFee);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of
	 * its identifier property. By default the instance is always saved. This
	 * behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * 
	 * @param TblUpbrhFee
	 *            a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblUpbrhFee TblUpbrhFee)
			throws org.hibernate.HibernateException {
		super.saveOrUpdate(TblUpbrhFee);
	}

	/**
	 * Update the persistent state associated with the given identifier. An
	 * exception is thrown if there is a persistent instance with the same
	 * identifier in the current session.
	 * 
	 * @param TblUpbrhFee
	 *            a transient instance containing updated state
	 */
	public void update(TblUpbrhFee TblUpbrhFee)
			throws org.hibernate.HibernateException {
		super.update(TblUpbrhFee);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param TblUpbrhFee
	 *            the instance to be removed
	 */
	public void delete(TblUpbrhFee TblUpbrhFee)
			throws org.hibernate.HibernateException {
		super.delete((Object) TblUpbrhFee);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param id
	 *            the instance ID to be removed
	 */
	public void delete(String id) throws org.hibernate.HibernateException {
		super.delete((Object) load(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.allinfinance.dao.iface.base.TblUpbrhFeeDAO#findAll()
	 */
	public List<TblUpbrhFee> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}