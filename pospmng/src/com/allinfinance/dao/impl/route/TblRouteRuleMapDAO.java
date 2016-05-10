package com.allinfinance.dao.impl.route;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.route.TblRouteRuleMap;

/**
 * A data access object (DAO) providing persistence and search support for
 * TblRouteRuleMap entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see h.TblRouteRuleMap
 * @author MyEclipse Persistence Tools
 */
public class TblRouteRuleMapDAO extends _RootDAO<TblRouteRuleMap> implements
		com.allinfinance.dao.iface.route.TblRouteRuleMapDAO {

	public Class<TblRouteRuleMap> getReferenceClass() {
		return TblRouteRuleMap.class;
	}

	/**
	 * Cast the object as a func.TblRouteRuleMap
	 */
	public TblRouteRuleMap cast(Object object) {
		return (TblRouteRuleMap) object;
	}

	public TblRouteRuleMap load(java.lang.Integer key)
			throws org.hibernate.HibernateException {
		return (TblRouteRuleMap) load(getReferenceClass(), key);
	}

	public TblRouteRuleMap get(java.lang.Integer key)
			throws org.hibernate.HibernateException {
		return (TblRouteRuleMap) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated
	 * identifier. (Or using the current value of the identifier property if the
	 * assigned generator is used.)
	 * 
	 * @param TblRouteRuleMap
	 *            a transient instance of a persistent class
	 * @return the class identifier
	 */
	public Integer save(TblRouteRuleMap TblRouteRuleMap)
			throws org.hibernate.HibernateException {
		return (java.lang.Integer) super.save(TblRouteRuleMap);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of
	 * its identifier property. By default the instance is always saved. This
	 * behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * 
	 * @param TblRouteRuleMap
	 *            a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblRouteRuleMap TblRouteRuleMap)
			throws org.hibernate.HibernateException {
		super.saveOrUpdate(TblRouteRuleMap);
	}

	/**
	 * Update the persistent state associated with the given identifier. An
	 * exception is thrown if there is a persistent instance with the same
	 * identifier in the current session.
	 * 
	 * @param TblRouteRuleMap
	 *            a transient instance containing updated state
	 */
	public void update(TblRouteRuleMap TblRouteRuleMap)
			throws org.hibernate.HibernateException {
		super.update(TblRouteRuleMap);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param TblRouteRuleMap
	 *            the instance to be removed
	 */
	public void delete(TblRouteRuleMap TblRouteRuleMap)
			throws org.hibernate.HibernateException {
		super.delete((Object) TblRouteRuleMap);
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
	 * @see com.allinfinance.dao.iface.base.TblRouteRuleMapDAO#findAll()
	 */
	public List<TblRouteRuleMap> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getRuleId() {
		String sql="select max(RULE_ID) maxId FROM TBL_ROUTE_RULE_MAP";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		if(list!=null&&list.size()!=0&&list.get(0)!=null){
			return Integer.parseInt(list.get(0).toString());
		}
		return null;
	}

	@Override
	public void deleteByConditions(String mchntId, String mchtUpbrhNo,
			String propertyId,String termId) {
		String whereSql="";
		if(termId==null||termId.equals("")){
			whereSql=" AND MISC1 is null ";
		}else whereSql=" AND MISC1='"+termId+"' ";
		String sql="MCHT_ID='"+mchntId+"'"+
				"  AND BRH_ID3='"+propertyId+"' AND MCHT_ID_UP='"+mchtUpbrhNo+"' ";
		DetachedCriteria criteria=DetachedCriteria.forClass(getReferenceClass());
		criteria.add(Restrictions.sqlRestriction(sql));
		List list = this.getHibernateTemplate().findByCriteria(criteria);
			this.delete(list.get(0));
	}

	@Override
	public TblRouteRuleMap getByConditions(String mchtId, String mchtUpbrhId,
			String propertyId,String termId) {
		String sql="MCHT_ID='"+mchtId+"'"+
				"  AND BRH_ID3='"+propertyId+"' AND TRIM(MISC1) ='"+termId+"' AND MCHT_ID_UP='"+mchtUpbrhId+"' ";
		DetachedCriteria criteria=DetachedCriteria.forClass(getReferenceClass());
		criteria.add(Restrictions.sqlRestriction(sql));
		List list = this.getHibernateTemplate().findByCriteria(criteria);
		if(list!=null&&list.size()>0&&list.get(0)!=null){
			return (TblRouteRuleMap) list.get(0);
		}
		return null;
	}
	
	@Override
	public TblRouteRuleMap getByConditions(String mchtId,String propertyId) {
		String sql="MCHT_ID='"+ mchtId +"'"+"  AND BRH_ID3='"+ propertyId +"'";
		DetachedCriteria criteria=DetachedCriteria.forClass(getReferenceClass());
		criteria.add(Restrictions.sqlRestriction(sql));
		List list = this.getHibernateTemplate().findByCriteria(criteria);
		if(list!=null&&list.size()>0&&list.get(0)!=null){
			return (TblRouteRuleMap) list.get(0);
		}
		return null;
	}
	

	@Override
	public void deleteByMchtId(String mchtId) {
		String sql="MCHT_ID='"+mchtId+"'";
		DetachedCriteria criteria=DetachedCriteria.forClass(getReferenceClass());
		criteria.add(Restrictions.sqlRestriction(sql));
		List<TblRouteRuleMap> list = this.getHibernateTemplate().findByCriteria(criteria);
		for (TblRouteRuleMap tblRouteRuleMap : list) {
			this.delete(tblRouteRuleMap);
			
		}
	}

	@Override
	public int getMappedBrhid3(String brhId3) {
		String sql=" BRH_ID3='" + brhId3 + "' ";
		DetachedCriteria criteria=DetachedCriteria.forClass(getReferenceClass());
		criteria.add(Restrictions.sqlRestriction(sql));
		List list = this.getHibernateTemplate().findByCriteria(criteria);
		if(list!=null && list.size() > 0 && list.get(0) != null){
			return list.size();
		}
		return 0;
	}
}