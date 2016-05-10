package com.allinfinance.dao.impl.route;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.route.TblRouteRuleMap;
import com.allinfinance.po.route.TblRouteRuleMapHist;
import com.allinfinance.po.route.TblRouteRuleMapHistPk;

/**
 * A data access object (DAO) providing persistence and search support for
 * TblRouteRuleMapHist entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see h.TblRouteRuleMapHist
 * @author MyEclipse Persistence Tools
 */
public class TblRouteRuleMapHistDAO extends _RootDAO<TblRouteRuleMapHist>
		implements com.allinfinance.dao.iface.route.TblRouteRuleMapHistDAO {
	public Class<TblRouteRuleMapHist> getReferenceClass() {
		return TblRouteRuleMapHist.class;
	}

	/**
	 * Cast the object as a func.TblRouteRuleMapHist
	 */
	public TblRouteRuleMapHist cast(Object object) {
		return (TblRouteRuleMapHist) object;
	}

	public TblRouteRuleMapHist load(TblRouteRuleMapHistPk key)
			throws org.hibernate.HibernateException {
		return (TblRouteRuleMapHist) load(getReferenceClass(), key);
	}

	public TblRouteRuleMapHist get(TblRouteRuleMapHistPk key)
			throws org.hibernate.HibernateException {
		return (TblRouteRuleMapHist) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated
	 * identifier. (Or using the current value of the identifier property if the
	 * assigned generator is used.)
	 * 
	 * @param TblRouteRuleMapHist
	 *            a transient instance of a persistent class
	 * @return the class identifier
	 */
	public TblRouteRuleMapHistPk save(TblRouteRuleMapHist TblRouteRuleMapHist)
			throws org.hibernate.HibernateException {
		return (TblRouteRuleMapHistPk) super.save(TblRouteRuleMapHist);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of
	 * its identifier property. By default the instance is always saved. This
	 * behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * 
	 * @param TblRouteRuleMapHist
	 *            a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblRouteRuleMapHist TblRouteRuleMapHist)
			throws org.hibernate.HibernateException {
		super.saveOrUpdate(TblRouteRuleMapHist);
	}

	/**
	 * Update the persistent state associated with the given identifier. An
	 * exception is thrown if there is a persistent instance with the same
	 * identifier in the current session.
	 * 
	 * @param TblRouteRuleMapHist
	 *            a transient instance containing updated state
	 */
	public void update(TblRouteRuleMapHist TblRouteRuleMapHist)
			throws org.hibernate.HibernateException {
		super.update(TblRouteRuleMapHist);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param TblRouteRuleMapHist
	 *            the instance to be removed
	 */
	public void delete(TblRouteRuleMapHist TblRouteRuleMapHist)
			throws org.hibernate.HibernateException {
		super.delete((Object) TblRouteRuleMapHist);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param id
	 *            the instance ID to be removed
	 */
	public void delete(TblRouteRuleMapHistPk key)
			throws org.hibernate.HibernateException {
		super.delete((Object) load(key));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.allinfinance.dao.iface.base.TblRouteRuleMapHistDAO#findAll()
	 */
	public List<TblRouteRuleMapHist> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getRuleId() {
		String sql="select max(RULE_ID) maxId from TBL_ROUTE_RULE_MAP_HIST";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		List list = query.list();
		if(list!=null&&list.size()!=0&&list.get(0)!=null){
			return Integer.parseInt(list.get(0).toString());
		}
		return null;
	}

	@Override
	public void setStatusByMchtId(String mchtId,String oprId) {
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		String stopTime=format.format(date);
		String sql="update TBL_ROUTE_RULE_MAP_HIST set status='1',STOP_TIME='"+stopTime+"',UPT_TIME='"+stopTime+"',UPT_OPR='"+oprId+"'  where MCHT_ID='"+mchtId+"' AND STATUS='0'";
		int executeUpdate = this.getSession().createSQLQuery(sql).executeUpdate();
	}

	@Override
	public void updateByConditions(String mchtId, String mchtUpbrhNo,
			String propertyId, String oprId,String termId) {
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		String stopTime=format.format(date);
		String sql="update TBL_ROUTE_RULE_MAP_HIST set status='1',STOP_TIME='"+stopTime+"',UPT_TIME='"+stopTime+"',UPT_OPR='"+oprId+"'  where MCHT_ID='"+mchtId+
					"' and MCHT_ID_UP='"+mchtUpbrhNo+"' and BRH_ID3='"+propertyId+"' AND MISC1= '"+termId+"' AND STATUS='0'";
		int executeUpdate = this.getSession().createSQLQuery(sql).executeUpdate();
		
	}

	@Override
	public void updateByConditions(String mchtId, String propertyId,
			String oprId,String termId) {
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		String stopTime=format.format(date);
		String sql="update TBL_ROUTE_RULE_MAP_HIST set status='1',STOP_TIME='"+stopTime+"',UPT_TIME='"+stopTime+"',UPT_OPR='"+oprId+"'  where MCHT_ID='"+mchtId+
					"'  and BRH_ID3='"+propertyId+"' and misc1= '"+termId+"' AND STATUS='0'";
		int executeUpdate = this.getSession().createSQLQuery(sql).executeUpdate();
	}

	@Override
	public TblRouteRuleMapHist getByConditions(String mchtId, String mchtUpbrhId,
			String propertyId, String termId) {
		String sql="MCHT_ID='"+mchtId+"'"+
				"  AND BRH_ID3='"+propertyId+"' AND TRIM(MISC1) ='"+termId+"' AND MCHT_ID_UP='"+mchtUpbrhId+"' AND STATUS='0'";
		DetachedCriteria criteria=DetachedCriteria.forClass(getReferenceClass());
		criteria.add(Restrictions.sqlRestriction(sql));
		List list = this.getHibernateTemplate().findByCriteria(criteria);
		if(list!=null&&list.size()>0&&list.get(0)!=null){
			return (TblRouteRuleMapHist) list.get(0);
		}
		return null;
	}

	@Override
	public void updateByMcht(String mchtId, String oprId) {
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		String stopTime=format.format(date);
		String sql="update TBL_ROUTE_RULE_MAP_HIST set status='1',STOP_TIME='"+stopTime+"',UPT_TIME='"+stopTime+"',UPT_OPR='"+oprId+"'  where MCHT_ID='"+mchtId+
					"' AND STATUS='0'";
		int executeUpdate = this.getSession().createSQLQuery(sql).executeUpdate();
	}

}