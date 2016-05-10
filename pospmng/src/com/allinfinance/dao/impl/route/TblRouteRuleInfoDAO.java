package com.allinfinance.dao.impl.route;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.route.TblRouteMchtg;
import com.allinfinance.po.route.TblRouteRuleInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * TblRouteRuleInfo entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see h.TblRouteRuleInfo
 * @author MyEclipse Persistence Tools
 */
public class TblRouteRuleInfoDAO extends _RootDAO<TblRouteRuleInfo> implements
		com.allinfinance.dao.iface.route.TblRouteRuleInfoDAO {

	public Class<TblRouteRuleInfo> getReferenceClass() {
		return TblRouteRuleInfo.class;
	}

	/**
	 * Cast the object as a func.TblRouteRuleInfo
	 */
	public TblRouteRuleInfo cast(Object object) {
		return (TblRouteRuleInfo) object;
	}

	public TblRouteRuleInfo load(java.lang.Integer key)
			throws org.hibernate.HibernateException {
		return (TblRouteRuleInfo) load(getReferenceClass(), key);
	}

	public TblRouteRuleInfo get(java.lang.Integer key)
			throws org.hibernate.HibernateException {
		return (TblRouteRuleInfo) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated
	 * identifier. (Or using the current value of the identifier property if the
	 * assigned generator is used.)
	 * 
	 * @param TblRouteRuleInfo
	 *            a transient instance of a persistent class
	 * @return the class identifier
	 */
	public Integer save(TblRouteRuleInfo TblRouteRuleInfo)
			throws org.hibernate.HibernateException {
		return (java.lang.Integer) super.save(TblRouteRuleInfo);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of
	 * its identifier property. By default the instance is always saved. This
	 * behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * 
	 * @param TblRouteRuleInfo
	 *            a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblRouteRuleInfo TblRouteRuleInfo)
			throws org.hibernate.HibernateException {
		super.saveOrUpdate(TblRouteRuleInfo);
	}

	/**
	 * Update the persistent state associated with the given identifier. An
	 * exception is thrown if there is a persistent instance with the same
	 * identifier in the current session.
	 * 
	 * @param TblRouteRuleInfo
	 *            a transient instance containing updated state
	 */
	public void update(TblRouteRuleInfo TblRouteRuleInfo)
			throws org.hibernate.HibernateException {
		super.update(TblRouteRuleInfo);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param TblRouteRuleInfo
	 *            the instance to be removed
	 */
	public void delete(TblRouteRuleInfo TblRouteRuleInfo)
			throws org.hibernate.HibernateException {
		super.delete((Object) TblRouteRuleInfo);
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

	@Override
	public List<TblRouteRuleInfo> findAll() {
		
		return null;
	}

	@Override
	public List<TblRouteRuleInfo> getAll() {
		DetachedCriteria criteria=DetachedCriteria.forClass(getReferenceClass());
		List<TblRouteRuleInfo> list = this.getHibernateTemplate().findByCriteria(criteria);
		if(list!=null&&list.size()>0){
			return list;
		}
		return null;
	}

	@Override
	public int getMax() {
		String sql="select max(a.RULE_ID) from TBL_ROUTE_RULE_INFO a";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		if(list!=null&&list.size()!=0&&list.get(0)!=null){
			int id= Integer.parseInt( list.get(0).toString());
			return id;
		}
		return 0;
	}

	@Override
	public boolean isOrderExist(Integer ruleId, Integer mchtGid, Integer order) {
		String sql="select a.orders from TBL_ROUTE_RULE_INFO a where a.orders=" + order;
		sql += " and a.MCHT_GID=" + mchtGid;
		
		if (ruleId != 0) {
			sql += " and a.rule_id != " + ruleId;
		}
		
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		if(list!=null&&list.size()!=0&&list.get(0)!=null){
			return true;
		}
		return false;
	}
	
	@Override
	public boolean updateOrders(Integer mchtGid, Integer order){
		String sql="select a.rule_id from TBL_ROUTE_RULE_INFO a where a.orders>=" + order;
		sql += " and a.MCHT_GID=" + mchtGid;
		
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		
		if(list!=null&&list.size()!=0&&list.get(0)!=null){
			for (int i = 0; i < list.size(); i++) {
				int id = Integer.parseInt( list.get(i).toString());
				TblRouteRuleInfo tblRouteRuleInfo = this.get(id);
				if (tblRouteRuleInfo != null) {
					tblRouteRuleInfo.setOrders(tblRouteRuleInfo.getOrders() + 1);
					this.update(tblRouteRuleInfo);
				}
			}
		} else {
			return false;
		}
		
		return true;
	}
}