package com.allinfinance.dao.iface.risk;

import com.allinfinance.po.TblRiskWhitePK;


public interface TblRiskWhiteDAO {
	public com.allinfinance.po.TblRiskWhite get(TblRiskWhitePK tblRiskWhitePK);

	public com.allinfinance.po.TblRiskWhite load(TblRiskWhitePK tblRiskWhitePK);



	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblRiskInf a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public void save(com.allinfinance.po.TblRiskWhite tblRiskWhite);


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblRiskInf a transient instance containing updated state
	 */
	public void update(com.allinfinance.po.TblRiskWhite tblRiskWhite);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(TblRiskWhitePK tblRiskWhitePK);



}