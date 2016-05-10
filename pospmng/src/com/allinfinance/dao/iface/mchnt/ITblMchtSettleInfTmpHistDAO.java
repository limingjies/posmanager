package com.allinfinance.dao.iface.mchnt;

import com.allinfinance.po.mchnt.TblMchtSettleInfTmpHist;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmpHistPK;

public interface ITblMchtSettleInfTmpHistDAO {



	// query name references




	public Class<TblMchtSettleInfTmpHist> getReferenceClass ();


	/**
	 * Cast the object as a TblMchtSettleInfTmpHist
	 */
	public TblMchtSettleInfTmpHist cast (Object object);


	public TblMchtSettleInfTmpHist load(java.lang.String key)
		throws org.hibernate.HibernateException;

	public TblMchtSettleInfTmpHist get(TblMchtSettleInfTmpHistPK key)
		throws org.hibernate.HibernateException;


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblMchtBaseInfTmp a transient instance of a persistent class
	 * @return the class identifier
	 */
	public TblMchtSettleInfTmpHistPK save(TblMchtSettleInfTmpHist tblMchtSettleInfTmpHist)
		throws org.hibernate.HibernateException;

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblMchtBaseInfTmp a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblMchtSettleInfTmpHist tblMchtSettleInfTmpHist)
		throws org.hibernate.HibernateException;


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblMchtBaseInfTmp a transient instance containing updated state
	 */
	public void update(TblMchtSettleInfTmpHist tblMchtSettleInfTmpHist)
		throws org.hibernate.HibernateException;

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblMchtBaseInfTmp the instance to be removed
	 */
	public void delete(TblMchtSettleInfTmpHist tblMchtSettleInfTmpHist)
		throws org.hibernate.HibernateException;

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id)
		throws org.hibernate.HibernateException;


	public void updateByMchtNo(String newMchtNo, String mchntId);


}