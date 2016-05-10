package com.allinfinance.dao.impl.route;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.route.TblRouteMchtgDetail;
import com.allinfinance.po.route.TblRouteMchtgDetailPk;

/**
 * A data access object (DAO) providing persistence and search support for
 * TblRouteMchtgDetailDetail entities. Transaction control of the save(),
 * update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle
 * user-managed Spring transactions. Each of these methods provides additional
 * information for how to configure it for the desired type of transaction
 * control.
 * 
 * @see h.TblRouteMchtgDetailDetail
 * @author MyEclipse Persistence Tools
 */
public class TblRouteMchtgDetailDAO extends _RootDAO<TblRouteMchtgDetail>
		implements com.allinfinance.dao.iface.route.TblRouteMchtgDetailDAO {
	public Class<TblRouteMchtgDetail> getReferenceClass() {
		return TblRouteMchtgDetail.class;
	}

	/**
	 * Cast the object as a func.TblRouteMchtgDetail
	 */
	public TblRouteMchtgDetail cast(Object object) {
		return (TblRouteMchtgDetail) object;
	}

	public TblRouteMchtgDetail load(TblRouteMchtgDetailPk key)
			throws org.hibernate.HibernateException {
		return (TblRouteMchtgDetail) load(getReferenceClass(), key);
	}

	public TblRouteMchtgDetail get(TblRouteMchtgDetailPk key)
			throws org.hibernate.HibernateException {
		return (TblRouteMchtgDetail) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated
	 * identifier. (Or using the current value of the identifier property if the
	 * assigned generator is used.)
	 * 
	 * @param TblRouteMchtgDetail
	 *            a transient instance of a persistent class
	 * @return the class identifier
	 */
	public TblRouteMchtgDetailPk save(TblRouteMchtgDetail TblRouteMchtgDetail)
			throws org.hibernate.HibernateException {
		return (TblRouteMchtgDetailPk) super.save(TblRouteMchtgDetail);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of
	 * its identifier property. By default the instance is always saved. This
	 * behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * 
	 * @param TblRouteMchtgDetail
	 *            a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblRouteMchtgDetail TblRouteMchtgDetail)
			throws org.hibernate.HibernateException {
		super.saveOrUpdate(TblRouteMchtgDetail);
	}

	/**
	 * Update the persistent state associated with the given identifier. An
	 * exception is thrown if there is a persistent instance with the same
	 * identifier in the current session.
	 * 
	 * @param TblRouteMchtgDetail
	 *            a transient instance containing updated state
	 */
	public void update(TblRouteMchtgDetail TblRouteMchtgDetail)
			throws org.hibernate.HibernateException {
		super.update(TblRouteMchtgDetail);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param TblRouteMchtgDetail
	 *            the instance to be removed
	 */
	public void delete(TblRouteMchtgDetail TblRouteMchtgDetail)
			throws org.hibernate.HibernateException {
		super.delete((Object) TblRouteMchtgDetail);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param id
	 *            the instance ID to be removed
	 */
	public void delete(TblRouteMchtgDetailPk key)
			throws org.hibernate.HibernateException {
		super.delete((Object) load(key));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.allinfinance.dao.iface.base.TblRouteMchtgDetailDAO#findAll()
	 */
	public List<TblRouteMchtgDetail> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TblRouteMchtgDetail getByMchtId(String mchtId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(getReferenceClass());
		criteria.add(Restrictions.sqlRestriction("MCHT_ID='"+mchtId+"'"));
		List list = this.getHibernateTemplate().findByCriteria(criteria);
		if(list!=null&&list.size()>0&&list.get(0)!=null){
			return (TblRouteMchtgDetail) list.get(0);
		}
		return null;
	}
}