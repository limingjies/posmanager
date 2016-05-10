package com.allinfinance.dao.impl.mchnt;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfTmpHistDAO;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmpHist;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmpHistPK;
import com.allinfinance.system.util.CommonFunction;

public class TblMchtBaseInfTmpHistDAO extends _RootDAO<TblMchtBaseInfTmpHist> implements ITblMchtBaseInfTmpHistDAO{

	// query name references

	public Class<TblMchtBaseInfTmpHist> getReferenceClass () {
		return TblMchtBaseInfTmpHist.class;
	}


	/**
	 * Cast the object as a TblMchtBaseInfTmpHist
	 */
	public TblMchtBaseInfTmpHist cast (Object object) {
		return (TblMchtBaseInfTmpHist) object;
	}


	public TblMchtBaseInfTmpHist load(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (TblMchtBaseInfTmpHist) load(getReferenceClass(), key);
	}

	public TblMchtBaseInfTmpHist get(TblMchtBaseInfTmpHistPK key)
		throws org.hibernate.HibernateException {
		return (TblMchtBaseInfTmpHist) get(getReferenceClass(), key);
	}


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param TblMchtBaseInfTmpHist a transient instance of a persistent class
	 * @return the class identifier
	 */
	public TblMchtBaseInfTmpHistPK save(TblMchtBaseInfTmpHist TblMchtBaseInfTmpHist)
		throws org.hibernate.HibernateException {
		return (TblMchtBaseInfTmpHistPK) super.save(TblMchtBaseInfTmpHist);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param TblMchtBaseInfTmpHist a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblMchtBaseInfTmpHist TblMchtBaseInfTmpHist)
		throws org.hibernate.HibernateException {
		super.saveOrUpdate(TblMchtBaseInfTmpHist);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param TblMchtBaseInfTmpHist a transient instance containing updated state
	 */
	public void update(TblMchtBaseInfTmpHist TblMchtBaseInfTmpHist)
		throws org.hibernate.HibernateException {
		super.update(TblMchtBaseInfTmpHist);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param TblMchtBaseInfTmpHist the instance to be removed
	 */
	public void delete(TblMchtBaseInfTmpHist TblMchtBaseInfTmpHist)
		throws org.hibernate.HibernateException {
		super.delete((Object) TblMchtBaseInfTmpHist);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id)
		throws org.hibernate.HibernateException {
		super.delete((Object) load(id));
	}


	public List<TblMchtBaseInfTmpHist> getByMchtNo(String mchntId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(getReferenceClass());
		criteria.add(Restrictions.sqlRestriction("where mcht_no = '"+mchntId+"' "));
		List<TblMchtBaseInfTmpHist> list = this.getHibernateTemplate().findByCriteria(criteria);
		return list;
	}


	@Override
	public void updateByMchtNo(String newMchtNo, String mchntId) {
		String sql="update TBL_MCHT_BASE_INF_TMP_HIST set MCHT_NO = '"+newMchtNo+"' WHERE MCHT_NO='"+mchntId+"' ";
		CommonFunction.getCommQueryDAO().excute(sql);
	}
	
}