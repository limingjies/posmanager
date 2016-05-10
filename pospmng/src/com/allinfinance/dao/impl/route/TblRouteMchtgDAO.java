package com.allinfinance.dao.impl.route;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.allinfinance.common.Constants;
import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.route.TblRouteMchtg;
import com.allinfinance.po.route.TblRouteMchtgDetail;

/**
 * A data access object (DAO) providing persistence and search support for
 * TblRouteMchtg entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see h.TblRouteMchtg
 * @author MyEclipse Persistence Tools
 */
public class TblRouteMchtgDAO extends _RootDAO<TblRouteMchtg> implements
		com.allinfinance.dao.iface.route.TblRouteMchtgDAO {

	public Class<TblRouteMchtg> getReferenceClass() {
		return TblRouteMchtg.class;
	}

	/**
	 * Cast the object as a func.TblRouteMchtg
	 */
	public TblRouteMchtg cast(Object object) {
		return (TblRouteMchtg) object;
	}

	public TblRouteMchtg load(java.lang.Integer key)
			throws org.hibernate.HibernateException {
		return (TblRouteMchtg) load(getReferenceClass(), key);
	}

	public TblRouteMchtg get(java.lang.Integer key)
			throws org.hibernate.HibernateException {
		return (TblRouteMchtg) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated
	 * identifier. (Or using the current value of the identifier property if the
	 * assigned generator is used.)
	 * 
	 * @param TblRouteMchtg
	 *            a transient instance of a persistent class
	 * @return the class identifier
	 */
	public Integer save(TblRouteMchtg TblRouteMchtg)
			throws org.hibernate.HibernateException {
		return (java.lang.Integer) super.save(TblRouteMchtg);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of
	 * its identifier property. By default the instance is always saved. This
	 * behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * 
	 * @param TblRouteMchtg
	 *            a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblRouteMchtg TblRouteMchtg)
			throws org.hibernate.HibernateException {
		super.saveOrUpdate(TblRouteMchtg);
	}

	/**
	 * Update the persistent state associated with the given identifier. An
	 * exception is thrown if there is a persistent instance with the same
	 * identifier in the current session.
	 * 
	 * @param TblRouteMchtg
	 *            a transient instance containing updated state
	 */
	public void update(TblRouteMchtg TblRouteMchtg)
			throws org.hibernate.HibernateException {
		super.update(TblRouteMchtg);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param TblRouteMchtg
	 *            the instance to be removed
	 */
	public void delete(TblRouteMchtg TblRouteMchtg)
			throws org.hibernate.HibernateException {
		TblRouteMchtg object = (com.allinfinance.po.route.TblRouteMchtg) super.get(getReferenceClass(), TblRouteMchtg.getMchtGid());
		super.delete((Object) object);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param id
	 *            the instance ID to be removed
	 */
	public void delete(java.lang.Integer id)
			throws org.hibernate.HibernateException {
		super.delete((Object) load(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.allinfinance.dao.iface.base.TblRouteMchtgDAO#findAll()
	 */
	public List<TblRouteMchtg> findAll() {
		return null;
	}

	@Override
	public List<TblRouteMchtg> getAll() {
		DetachedCriteria criteria=DetachedCriteria.forClass(getReferenceClass());
		List<TblRouteMchtg> list = this.getHibernateTemplate().findByCriteria(criteria);
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}

	@Override
	public int getMax() {
		String sql="select max(a.MCHT_GID) from TBL_ROUTE_MCHTG a";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		if(list!=null&&list.size()!=0&&list.get(0)!=null){
			int id= Integer.parseInt( list.get(0).toString());
			return id;
		}
		return 0;
	}

	@Override
	public String getMcht(Integer mchtGid) {
		String sql="select  A.MCHT_ID from TBL_ROUTE_MCHTG_DETAIL A WHERE A.MCHT_GID='"+mchtGid+"'";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		if(list!=null&&list.size()!=0&&list.get(0)!=null){
			return Constants.DATA_OPR_FAIL;
		}
		return null;
	}
}