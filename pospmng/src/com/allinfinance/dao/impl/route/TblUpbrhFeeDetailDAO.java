package com.allinfinance.dao.impl.route;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.route.TblUpbrhFeeDetail;
import com.allinfinance.po.route.TblUpbrhFeeDetailPk;

/**
 * A data access object (DAO) providing persistence and search support for
 * TblUpbrhFeeDetailDetail entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see h.TblUpbrhFeeDetailDetail
 * @author MyEclipse Persistence Tools
 */
public class TblUpbrhFeeDetailDAO extends _RootDAO<TblUpbrhFeeDetail> implements
		com.allinfinance.dao.iface.route.TblUpbrhFeeDetailDAO {

	public Class<TblUpbrhFeeDetail> getReferenceClass() {
		return TblUpbrhFeeDetail.class;
	}

	/**
	 * Cast the object as a func.TblUpbrhFeeDetail
	 */
	public TblUpbrhFeeDetail cast(Object object) {
		return (TblUpbrhFeeDetail) object;
	}

	public TblUpbrhFeeDetail load(TblUpbrhFeeDetailPk key)
			throws org.hibernate.HibernateException {
		return (TblUpbrhFeeDetail) load(getReferenceClass(), key);
	}

	public TblUpbrhFeeDetail get(TblUpbrhFeeDetailPk key)
			throws org.hibernate.HibernateException {
		return (TblUpbrhFeeDetail) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated
	 * identifier. (Or using the current value of the identifier property if the
	 * assigned generator is used.)
	 * 
	 * @param TblUpbrhFeeDetail
	 *            a transient instance of a persistent class
	 * @return the class identifier
	 */
	public void save(TblUpbrhFeeDetail TblUpbrhFeeDetail) {
		super.save(TblUpbrhFeeDetail);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of
	 * its identifier property. By default the instance is always saved. This
	 * behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * 
	 * @param TblUpbrhFeeDetail
	 *            a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblUpbrhFeeDetail TblUpbrhFeeDetail)
			throws org.hibernate.HibernateException {
		super.saveOrUpdate(TblUpbrhFeeDetail);
	}

	/**
	 * Update the persistent state associated with the given identifier. An
	 * exception is thrown if there is a persistent instance with the same
	 * identifier in the current session.
	 * 
	 * @param TblUpbrhFeeDetail
	 *            a transient instance containing updated state
	 */
	public void update(TblUpbrhFeeDetail TblUpbrhFeeDetail)
			throws org.hibernate.HibernateException {
		super.update(TblUpbrhFeeDetail);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param TblUpbrhFeeDetail
	 *            the instance to be removed
	 */
	public void delete(TblUpbrhFeeDetail TblUpbrhFeeDetail)
			throws org.hibernate.HibernateException {
		super.delete((Object) TblUpbrhFeeDetail);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param id
	 *            the instance ID to be removed
	 */
	public void delete(TblUpbrhFeeDetailPk id) throws org.hibernate.HibernateException {
		super.delete((Object) load(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.allinfinance.dao.iface.base.TblUpbrhFeeDetailDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	public List<TblUpbrhFeeDetail> findAll() {
		// TODO Auto-generated method stub
//		return (TblUpbrhFeeDetail) load(getReferenceClass(), key);
		return super.loadAll(getReferenceClass());
	}

	@Override
	public TblUpbrhFeeDetail get(String key) {
		// TODO Auto-generated method stub
		return null;
	}
}