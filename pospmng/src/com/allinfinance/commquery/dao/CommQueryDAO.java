/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-3-8       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 allinfinance, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai allinfinance Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with allinfinance.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.allinfinance.commquery.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.allinfinance.po.mchnt.TblMchtSettleInf;

/**
 * Title:通用查询
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-3-8
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class CommQueryDAO extends HibernateDaoSupport implements ICommQueryDAO {

	public void excute(final String sql) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				session.getTransaction().begin();
				Query query = session.createSQLQuery(sql);
				int executeColum = query.executeUpdate();
				session.getTransaction().commit();
				return executeColum;
			}
		});
	}

	public int excuteWithoutTransaction(final String sql) {
		int i = (Integer) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Query query = session.createSQLQuery(sql);
						int executeColum = query.executeUpdate();
						return executeColum;
					}
				}, true);
		return i;
	}

	/**
	 * 分页查询
	 * 
	 * @param currentPage
	 * @param clazz
	 * @return
	 */
	public List<Object> getListByPage(int currentPage,
			DetachedCriteria detachedCriteria) {
		int pageSize = 100;
		// 查询当前页展示的数据,发出select * from xxx...
		detachedCriteria.setProjection(null);
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		int firstResult = currentPage * pageSize;
		int maxResults = pageSize;
		List<Object> rows = this.getHibernateTemplate().findByCriteria(
				detachedCriteria, firstResult, maxResults);
		return rows;
	}

	/**
	 * 查询总记录数
	 * 
	 * @param clazz
	 * @return
	 */
	public int getTotalNum(DetachedCriteria detachedCriteria) {
		// 查询总记录数,由hibernate框架发出select count(id) from xxx....
		detachedCriteria.setProjection(Projections.rowCount());
		@SuppressWarnings("unchecked")
		List<Object> countList = this.getHibernateTemplate().findByCriteria(
				detachedCriteria);
		if(countList!=null&&countList.size()>0){
			int total = (Integer) countList.get(0);
			return total;
		}else return 0;
	}

	public List find(String query) {
		return getHibernateTemplate().find(query);
	}

	public List findByHQLQuery(final String hql, final int begin,
			final int count) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				if (begin >= 0) {
					query.setFirstResult(begin);
					query.setMaxResults(count);
				}
				return query.list();
			}
		});
	}

	public List findByHQLQuery(final String hql) {
		List data = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				return query.list();
			}
		});
		return data;
	}

	public List findByNamedQuery(String name) {
		return getHibernateTemplate().findByNamedQuery(name);
	}

	public List findByNamedQuery(final String name, final int begin,
			final int count) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.getNamedQuery(name);
				if (begin >= 0) {
					query.setFirstResult(begin);
					query.setMaxResults(count);
				}
				return query.list();
			}
		});
	}

	public List findByNamedQuery(String name, Map params) {
		return findByNamedQuery(name, params, -1, -1);
	}

	public List findByNamedQuery(final String name, final Map params,
			final int begin, final int count) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.getNamedQuery(name);
				if (null != params) {
					for (Iterator i = params.entrySet().iterator(); i.hasNext();) {
						Map.Entry entry = (Map.Entry) i.next();
						query.setParameter((String) entry.getKey(),
								entry.getValue());
					}
				}
				if (begin >= 0) {
					query.setFirstResult(begin);
					query.setMaxResults(count);
				}
				return query.list();
			}
		});
	}

	public List findByNamedQuery(String name, Serializable[] params) {
		return findByNamedQuery(name, params, -1, -1);
	}

	public List findByNamedQuery(final String name,
			final Serializable[] params, final int begin, final int count) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.getNamedQuery(name);
				if (null != params) {
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
				}
				if (begin >= 0) {
					query.setFirstResult(begin);
					query.setMaxResults(count);
				}
				return query.list();
			}
		});
	}

	public List findBySQLQuery(final String sql, final int begin,
			final int count) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				if (begin >= 0) {
					query.setFirstResult(begin);
					query.setMaxResults(count);
				}
				return query.list();
			}
		});
	}

	public List findBySQLQuery(final String sql) {
		List data = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				return query.list();
			}
		});
		return data;
	}

	public List findBySQLQuery(final String sql, final Map map) {
		List data = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				Iterator iter = map.keySet().iterator();
				while (iter.hasNext()) {
					String key = iter.next().toString();
					Object obj = map.get(key);
					String[] keys = query.getNamedParameters();
					for (int i = 0; i < keys.length; i++) {
						if (key != null && key.equals(keys[i])) {
							if (obj instanceof String) {
								query.setString(key, obj.toString());
							}
							if (obj instanceof Number) {
								query.setInteger(key,
										Integer.parseInt(obj.toString()));
							}
							if (obj instanceof BigDecimal) {
								query.setBigDecimal(key, (BigDecimal) obj);
							}
							if (obj instanceof List) {
								query.setParameterList(key, (List) obj);
							}
						}
					}
				}
				return query.list();
			}
		});
		return data;
	}

	public List findBySQLQuery(final String sql, final int begin,
			final int count, final Map map) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				Iterator iter = map.keySet().iterator();
				while (iter.hasNext()) {
					String key = iter.next().toString();
					Object obj = map.get(key);
					String[] keys = query.getNamedParameters();
					for (int i = 0; i < keys.length; i++) {
						if (key != null && key.equals(keys[i])) {
							if (obj instanceof String) {
								query.setString(key, obj.toString());
							}
							if (obj instanceof Number) {
								query.setInteger(key,
										Integer.parseInt(obj.toString()));
							}
							if (obj instanceof BigDecimal) {
								query.setBigDecimal(key, (BigDecimal) obj);
							}
							if (obj instanceof List) {
								query.setParameterList(key, (List) obj);
							}
						}
					}
				}
				if (begin >= 0) {
					query.setFirstResult(begin);
					query.setMaxResults(count);
				}
				return query.list();
			}
		});
	}

	public String findCountBySQLQuery(final String countSql) {
		List data = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(countSql);
				return query.list();
			}
		});
		if (data == null || data.get(0) != null)
			return data.get(0).toString();
		return null;
	}

	public String findCountBySQLQuery(final String countSql, final Map map) {
		List data = getHibernateTemplate().executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(countSql);
				Iterator iter = map.keySet().iterator();
				while (iter.hasNext()) {
					String key = iter.next().toString();
					Object obj = map.get(key);
					String[] keys = query.getNamedParameters();
					for (int i = 0; i < keys.length; i++) {
						if (key != null && key.equals(keys[i])) {
							if (obj instanceof String) {
								query.setString(key, obj.toString());
							}
							if (obj instanceof Number) {
								query.setInteger(key,
										Integer.parseInt(obj.toString()));
							}
							if (obj instanceof BigDecimal) {
								query.setBigDecimal(key, (BigDecimal) obj);
							}
							if (obj instanceof List) {
								query.setParameterList(key, (List) obj);
							}
						}
					}
				}
				return query.list();
			}
		});
		return data.get(0).toString();
	}

	public void flush() {
		getHibernateTemplate().flush();
	}
	
	public Connection getConnection(){
		return getHibernateTemplate().getSessionFactory().openSession().connection();
	}
}
