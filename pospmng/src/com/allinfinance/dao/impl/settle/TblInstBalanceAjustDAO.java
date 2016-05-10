package com.allinfinance.dao.impl.settle;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.settle.TblInstBalanceAjust;
import com.allinfinance.po.settle.TblInstBalanceAjustPK;

public class TblInstBalanceAjustDAO extends
		_RootDAO<com.allinfinance.po.settle.TblInstBalanceAjust> implements
		com.allinfinance.dao.iface.settle.TblInstBalanceAjustDAO {

	public TblInstBalanceAjustDAO() {
	}

	public Class<com.allinfinance.po.settle.TblInstBalanceAjust> getReferenceClass() {
		return com.allinfinance.po.settle.TblInstBalanceAjust.class;
	}

	/**
	 * Cast the object as a com.allinfinance.po.TblInstBalanceAjust
	 */
	public com.allinfinance.po.settle.TblInstBalanceAjust cast(Object object) {
		return (com.allinfinance.po.settle.TblInstBalanceAjust) object;
	}

	public com.allinfinance.po.settle.TblInstBalanceAjust load(TblInstBalanceAjustPK pk) {
		return (com.allinfinance.po.settle.TblInstBalanceAjust) load(
				getReferenceClass(), pk);
	}

	public com.allinfinance.po.settle.TblInstBalanceAjust get(TblInstBalanceAjustPK pk) {
		return (com.allinfinance.po.settle.TblInstBalanceAjust) get(
				getReferenceClass(), pk);
	}

	/**
	 * Persist the given transient instance, first assigning a generated
	 * identifier. (Or using the current value of the identifier property if the
	 * assigned generator is used.)
	 * 
	 * @param tblInstBalanceAjust
	 *            a transient instance of a persistent class
	 * @return the class identifier
	 */
	public void save(
			com.allinfinance.po.settle.TblInstBalanceAjust tblInstBalanceAjust) {
		super.save(tblInstBalanceAjust);
	}

	/**
	 * Update the persistent state associated with the given identifier. An
	 * exception is thrown if there is a persistent instance with the same
	 * identifier in the current session.
	 * 
	 * @param tblInstBalanceAjust
	 *            a transient instance containing updated state
	 */
	public void update(
			com.allinfinance.po.settle.TblInstBalanceAjust tblInstBalanceAjust) {
		super.update(tblInstBalanceAjust);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an
	 * instance associated with the receiving Session or a transient instance
	 * with an identifier associated with existing persistent state.
	 * 
	 * @param id
	 *            the instance ID to be removed
	 */
	public void delete(TblInstBalanceAjustPK pk) {
		super.delete((Object) load(pk));
	}

	@Override
	public void delete(TblInstBalanceAjust tblInstBalanceAjust) {
		super.delete(tblInstBalanceAjust);
	}

}