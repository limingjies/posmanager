package com.allinfinance.dao.impl.term;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblTermInfTmp;
import com.allinfinance.po.TblTermInfTmpPK;


public class TblTermInfTmpDAO extends _RootDAO<com.allinfinance.po.TblTermInfTmp> implements com.allinfinance.dao.iface.term.TblTermInfTmpDAO{

		public TblTermInfTmpDAO () {}
		
		public List<TblTermInfTmp> findAll() {
			// TODO Auto-generated method stub
			return null;
		}
		
		public Class<com.allinfinance.po.TblTermInfTmp> getReferenceClass () {
			return com.allinfinance.po.TblTermInfTmp.class;
		}
		
		
		/**
		 * Cast the object as a com.allinfinance.po.TblTermInfTmp
		 */
		public com.allinfinance.po.TblTermInfTmp cast (Object object) {
			return (com.allinfinance.po.TblTermInfTmp) object;
		}
		
		
		@SuppressWarnings("unchecked")
		public java.util.List<com.allinfinance.po.TblTermInfTmp> loadAll()
		{
			return loadAll(getReferenceClass());
		}
		
		
		
		
		
		/**
		 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
		 * of the identifier property if the assigned generator is used.)
		 * @param tblTermInfTmp a transient instance of a persistent class
		 * @return the class identifier
		 */
		public TblTermInfTmpPK save(com.allinfinance.po.TblTermInfTmp tblTermInfTmp)
		{
			return (TblTermInfTmpPK) super.save(tblTermInfTmp);
		}
		
		/**
		 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
		 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
		 * identifier property mapping.
		 * @param tblTermInfTmp a transient instance containing new or updated state
		 */
		public void saveOrUpdate(com.allinfinance.po.TblTermInfTmp tblTermInfTmp)
		{
			super.saveOrUpdate(tblTermInfTmp);
		}
		
		
		/**
		 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
		 * instance with the same identifier in the current session.
		 * @param tblTermInfTmp a transient instance containing updated state
		 */
		public void update(com.allinfinance.po.TblTermInfTmp tblTermInfTmp)
		{
			super.update(tblTermInfTmp);
		}
		
		/**
		 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
		 * Session or a transient instance with an identifier associated with existing persistent state.
		 * @param tblTermInfTmp the instance to be removed
		 */
		public void delete(com.allinfinance.po.TblTermInfTmp tblTermInfTmp)
		{
			super.delete((Object) tblTermInfTmp);
		}
		
		/**
		 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
		 * Session or a transient instance with an identifier associated with existing persistent state.
		 * @param id the instance ID to be removed
		 */
		public void delete(TblTermInfTmpPK pk)
		{
			super.delete((Object) load(pk));
		}
		
		public TblTermInfTmp get(TblTermInfTmpPK key) {
			return (TblTermInfTmp) super.get(getReferenceClass(), key);
		}
		
		
		public TblTermInfTmp load(TblTermInfTmpPK key) {
			return (com.allinfinance.po.TblTermInfTmp) load(getReferenceClass(), key);
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

		@Override
		public List<TblTermInfTmp> getByMchnt(String mchntId) {
			DetachedCriteria criteria=DetachedCriteria.forClass(TblTermInfTmp.class);
			criteria.add(Restrictions.eq("MchtCd", mchntId));
			criteria.add(Restrictions.ne("TermSta", "7"));
			List<TblTermInfTmp> list = this.getHibernateTemplate().findByCriteria(criteria);
			return list;
			
		}
		@Override
		public List<TblTermInfTmp> getByMchntAll(String mchntId) {
			DetachedCriteria criteria=DetachedCriteria.forClass(TblTermInfTmp.class);
			criteria.add(Restrictions.eq("MchtCd", mchntId));
			//criteria.add(Restrictions.ne("TermSta", "7"));
			List<TblTermInfTmp> list = this.getHibernateTemplate().findByCriteria(criteria);
			return list;
			
		}
}