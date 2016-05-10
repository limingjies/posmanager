package com.allinfinance.dao.impl.route;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.route.TblRouteUpbrh2;

/**
 * A data access object (DAO) providing persistence and search support for
 * TblRouteUpbrh2 entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see h.TblRouteUpbrh2
 * @author MyEclipse Persistence Tools
 */
public class TblRouteUpbrh2DAO extends _RootDAO<TblRouteUpbrh2> implements
		com.allinfinance.dao.iface.route.TblRouteUpbrh2DAO {

	public Class<TblRouteUpbrh2> getReferenceClass() {
		return TblRouteUpbrh2.class;
	}

	/**
	 * Cast the object as a func.TblRouteUpbrh2
	 */
	public TblRouteUpbrh2 cast(Object object) {
		return (TblRouteUpbrh2) object;
	}

	public TblRouteUpbrh2 load(String key)
			throws org.hibernate.HibernateException {
		return (TblRouteUpbrh2) load(getReferenceClass(), key);
	}

	public TblRouteUpbrh2 get(String key)
			throws org.hibernate.HibernateException {
		return (TblRouteUpbrh2) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated
	 * identifier. (Or using the current value of the identifier property if the
	 * assigned generator is used.)
	 * 
	 * @param TblRouteUpbrh2
	 *            a transient instance of a persistent class
	 * @return the class identifier
	 */
	public Integer save(TblRouteUpbrh2 TblRouteUpbrh2)
			throws org.hibernate.HibernateException {
		return (java.lang.Integer) super.save(TblRouteUpbrh2);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of
	 * its identifier property. By default the instance is always saved. This
	 * behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * 
	 * @param TblRouteUpbrh2
	 *            a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblRouteUpbrh2 TblRouteUpbrh2)
			throws org.hibernate.HibernateException {
		super.saveOrUpdate(TblRouteUpbrh2);
	}

	/**
	 * Update the persistent state associated with the given identifier. An
	 * exception is thrown if there is a persistent instance with the same
	 * identifier in the current session.
	 * 
	 * @param TblRouteUpbrh2
	 *            a transient instance containing updated state
	 */
	public void update(TblRouteUpbrh2 TblRouteUpbrh2)
			throws org.hibernate.HibernateException {
		super.update(TblRouteUpbrh2);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param TblRouteUpbrh2
	 *            the instance to be removed
	 */
	public void delete(TblRouteUpbrh2 TblRouteUpbrh2)
			throws org.hibernate.HibernateException {
		super.delete((Object) TblRouteUpbrh2);
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
	 * @see com.allinfinance.dao.iface.base.TblRouteUpbrh2DAO#findAll()
	 */
	public List<TblRouteUpbrh2> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}