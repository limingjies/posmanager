package com.allinfinance.dao.impl.risk;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.risk.TblRiskParamMng;
import com.allinfinance.po.risk.TblRiskParamMngPK;

/**
 	* A data access object (DAO) providing persistence and search support for TblRiskParamMng entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .TblRiskParamMng
  * @author MyEclipse Persistence Tools 
 */
public class TblRiskParamMngDAO extends _RootDAO<TblRiskParamMng> implements
	com.allinfinance.dao.iface.risk.TblRiskParamMngDAO {
	

	public Class<TblRiskParamMng> getReferenceClass() {
		return TblRiskParamMng.class;
	}

	/**
	 * Cast the object as a func.TblRiskParamMng
	 */
	public TblRiskParamMng cast(Object object) {
		return (TblRiskParamMng) object;
	}

	public TblRiskParamMng load(TblRiskParamMngPK key)
			throws org.hibernate.HibernateException {
		return (TblRiskParamMng) load(getReferenceClass(), key);
	}

	@Override
	public TblRiskParamMng get(TblRiskParamMngPK key)
			throws org.hibernate.HibernateException {
		return (TblRiskParamMng) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated
	 * identifier. (Or using the current value of the identifier property if the
	 * assigned generator is used.)
	 * 
	 * @param TblRiskParamMng
	 *            a transient instance of a persistent class
	 * @return the class identifier
	 */
	@Override
	public TblRiskParamMngPK save(TblRiskParamMng TblRiskParamMng)
			throws org.hibernate.HibernateException {
		 return (TblRiskParamMngPK) super.save(TblRiskParamMng);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of
	 * its identifier property. By default the instance is always saved. This
	 * behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * 
	 * @param TblRiskParamMng
	 *            a transient instance containing new or updated state
	 */
	@Override
	public void saveOrUpdate(TblRiskParamMng TblRiskParamMng)
			throws org.hibernate.HibernateException {
		super.saveOrUpdate(TblRiskParamMng);
	}

	/**
	 * Update the persistent state associated with the given identifier. An
	 * exception is thrown if there is a persistent instance with the same
	 * identifier in the current session.
	 * 
	 * @param TblRiskParamMng
	 *            a transient instance containing updated state
	 */
	@Override
	public void update(TblRiskParamMng TblRiskParamMng)
			throws org.hibernate.HibernateException {
		super.update(TblRiskParamMng);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param TblRiskParamMng
	 *            the instance to be removed
	 */
	@Override
	public void delete(TblRiskParamMng tblRiskParamMng)
			throws org.hibernate.HibernateException {
		TblRiskParamMng object = (TblRiskParamMng) super.get(getReferenceClass(), tblRiskParamMng.getId());
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
	@Override
	public void delete(TblRiskParamMngPK id)
			throws org.hibernate.HibernateException {
		super.delete((Object) load(id));
	}
	
	@Override
	public TblRiskParamMng get(String mchtId,String termId,String riskType){
		if(StringUtils.isEmpty(mchtId) || StringUtils.isEmpty(termId) || StringUtils.isEmpty(riskType)){
			return null;
		}else{
			TblRiskParamMngPK tblRiskParamMngPK = new TblRiskParamMngPK( mchtId, termId, riskType);
			return this.get(tblRiskParamMngPK);
		}
		
	}
}
