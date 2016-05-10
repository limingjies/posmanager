package com.allinfinance.dao.iface.agentpay;

import com.allinfinance.po.agentpay.TblStlmFileTransInfPK;

public interface TblStlmFileTransInfDAO {
	public com.allinfinance.po.agentpay.TblStlmFileTransInf get(com.allinfinance.po.agentpay.TblStlmFileTransInfPK key);

	public com.allinfinance.po.agentpay.TblStlmFileTransInf load(com.allinfinance.po.agentpay.TblStlmFileTransInfPK key);

	public java.util.List<com.allinfinance.po.agentpay.TblStlmFileTransInf> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param TblStlmFileTransInf a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public com.allinfinance.po.agentpay.TblStlmFileTransInfPK save(com.allinfinance.po.agentpay.TblStlmFileTransInf tblStlmFileTransInf);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param TblStlmFileTransInf a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(com.allinfinance.po.agentpay.TblStlmFileTransInf tblStlmFileTransInf);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param TblStlmFileTransInf a transient instance containing updated state
	 */
	public void update(com.allinfinance.po.agentpay.TblStlmFileTransInf tblStlmFileTransInf);
	
	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(TblStlmFileTransInfPK id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param TblStlmFileTransInf the instance to be removed
	 */
	public void delete(com.allinfinance.po.agentpay.TblStlmFileTransInf tblStlmFileTransInf);
}
