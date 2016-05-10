package com.allinfinance.dao.impl.route;

import java.io.Serializable;
import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.route.TblRouteUpbrh;

/**
 * A data access object (DAO) providing persistence and search support for
 * TblRouteUpbrh entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see h.TblRouteUpbrh
 * @author MyEclipse Persistence Tools
 */
public class TblRouteUpbrhDAO extends _RootDAO<TblRouteUpbrh> implements
		com.allinfinance.dao.iface.route.TblRouteUpbrhDAO {

	public Class<TblRouteUpbrh> getReferenceClass() {
		return TblRouteUpbrh.class;
	}

	/**
	 * Cast the object as a func.TblRouteUpbrh
	 */
	public TblRouteUpbrh cast(Object object) {
		return (TblRouteUpbrh) object;
	}

	public TblRouteUpbrh load(String key)
			throws org.hibernate.HibernateException {
		return (TblRouteUpbrh) load(getReferenceClass(), key);
	}

	public TblRouteUpbrh get(String key)
			throws org.hibernate.HibernateException {
		return (TblRouteUpbrh) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated
	 * identifier. (Or using the current value of the identifier property if the
	 * assigned generator is used.)
	 * 
	 * @param TblRouteUpbrh
	 *            a transient instance of a persistent class
	 * @return the class identifier
	 */
	public String save(TblRouteUpbrh TblRouteUpbrh)
			throws org.hibernate.HibernateException {
		
		return  (String)super.save(TblRouteUpbrh);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of
	 * its identifier property. By default the instance is always saved. This
	 * behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * 
	 * @param TblRouteUpbrh
	 *            a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblRouteUpbrh TblRouteUpbrh)
			throws org.hibernate.HibernateException {
		super.saveOrUpdate(TblRouteUpbrh);
	}

	/**
	 * Update the persistent state associated with the given identifier. An
	 * exception is thrown if there is a persistent instance with the same
	 * identifier in the current session.
	 * 
	 * @param TblRouteUpbrh
	 *            a transient instance containing updated state
	 */
	public void update(TblRouteUpbrh TblRouteUpbrh)
			throws org.hibernate.HibernateException {
		super.update(TblRouteUpbrh);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param TblRouteUpbrh
	 *            the instance to be removed
	 */
	public void delete(TblRouteUpbrh TblRouteUpbrh)
			throws org.hibernate.HibernateException {
		super.delete((Object) TblRouteUpbrh);
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
	 * @see com.allinfinance.dao.iface.base.TblRouteUpbrhDAO#findAll()
	 */
	public List<TblRouteUpbrh> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}