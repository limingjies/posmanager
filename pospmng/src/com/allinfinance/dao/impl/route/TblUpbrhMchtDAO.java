package com.allinfinance.dao.impl.route;

import java.util.List;
import oracle.net.aso.h;
import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.route.TblUpbrhMcht;
import com.allinfinance.po.route.TblUpbrhMchtPk;

/**
 * A data access object (DAO) providing persistence and search support for
 * TblUpbrhMcht entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see h.TblUpbrhMcht
 * @author MyEclipse Persistence Tools
 */
public class TblUpbrhMchtDAO extends _RootDAO<TblUpbrhMcht> implements
		com.allinfinance.dao.iface.route.TblUpbrhMchtDAO {
	public Class<TblUpbrhMcht> getReferenceClass() {
		return TblUpbrhMcht.class;
	}

	/**
	 * Cast the object as a func.TblUpbrhMcht
	 */
	public TblUpbrhMcht cast(Object object) {
		return (TblUpbrhMcht) object;
	}

	public TblUpbrhMcht load(TblUpbrhMchtPk key)
			throws org.hibernate.HibernateException {
		return (TblUpbrhMcht) load(getReferenceClass(), key);
	}

	public TblUpbrhMcht get(TblUpbrhMchtPk key)
			throws org.hibernate.HibernateException {
		return (TblUpbrhMcht) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated
	 * identifier. (Or using the current value of the identifier property if the
	 * assigned generator is used.)
	 * 
	 * @param TblUpbrhMcht
	 *            a transient instance of a persistent class
	 * @return the class identifier
	 */
	public TblUpbrhMchtPk save(TblUpbrhMcht TblUpbrhMcht)
			throws org.hibernate.HibernateException {
		return (TblUpbrhMchtPk) super.save(TblUpbrhMcht);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of
	 * its identifier property. By default the instance is always saved. This
	 * behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * 
	 * @param TblUpbrhMcht
	 *            a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblUpbrhMcht TblUpbrhMcht)
			throws org.hibernate.HibernateException {
		super.saveOrUpdate(TblUpbrhMcht);
	}

	/**
	 * Update the persistent state associated with the given identifier. An
	 * exception is thrown if there is a persistent instance with the same
	 * identifier in the current session.
	 * 
	 * @param TblUpbrhMcht
	 *            a transient instance containing updated state
	 */
	public void update(TblUpbrhMcht TblUpbrhMcht)
			throws org.hibernate.HibernateException {
		super.update(TblUpbrhMcht);
	}

	public void execute(String sql) {
		this.getSession().createSQLQuery(sql).executeUpdate();
	}
	
	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param TblUpbrhMcht
	 *            the instance to be removed
	 */
	public void delete(TblUpbrhMcht TblUpbrhMcht)
			throws org.hibernate.HibernateException {
		super.delete((Object) TblUpbrhMcht);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param id
	 *            the instance ID to be removed
	 */
	public void delete(TblUpbrhMchtPk key)
			throws org.hibernate.HibernateException {
		super.delete((Object) load(key));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.allinfinance.dao.iface.base.TblUpbrhMchtDAO#findAll()
	 */
	public List<TblUpbrhMcht> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}