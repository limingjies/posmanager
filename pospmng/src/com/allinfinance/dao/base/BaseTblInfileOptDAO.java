package com.allinfinance.dao.base;

import com.allinfinance.dao.iface.TblInfileOptDAO;



/**
 * This is an automatically generated DAO class which should not be edited.
 */
public abstract class BaseTblInfileOptDAO extends com.allinfinance.dao._RootDAO<com.allinfinance.po.settle.TblInfileOpt> implements TblInfileOptDAO{



	// query name references




	public Class<com.allinfinance.po.settle.TblInfileOpt> getReferenceClass () {
		return com.allinfinance.po.settle.TblInfileOpt.class;
	}


	/**
	 * Cast the object as a com.allinfinance.po.settle.TblInfileOpt
	 */
	public com.allinfinance.po.settle.TblInfileOpt cast (Object object) {
		return (com.allinfinance.po.settle.TblInfileOpt) object;
	}


	public com.allinfinance.po.settle.TblInfileOpt load(com.allinfinance.po.settle.TblInfileOptPK key)
	{
		return (com.allinfinance.po.settle.TblInfileOpt) load(getReferenceClass(), key);
	}

	public com.allinfinance.po.settle.TblInfileOpt get(com.allinfinance.po.settle.TblInfileOptPK key)
	{
		return (com.allinfinance.po.settle.TblInfileOpt) get(getReferenceClass(), key);
	}

	public java.util.List<com.allinfinance.po.settle.TblInfileOpt> loadAll()
	{
		return loadAll(getReferenceClass());
	}





	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblInfileOpt a transient instance of a persistent class
	 * @return the class identifier
	 */
	public com.allinfinance.po.settle.TblInfileOptPK save(com.allinfinance.po.settle.TblInfileOpt tblInfileOpt)
	{
		return (com.allinfinance.po.settle.TblInfileOptPK) super.save(tblInfileOpt);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblInfileOpt a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.allinfinance.po.settle.TblInfileOpt tblInfileOpt)
	{
		super.saveOrUpdate(tblInfileOpt);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblInfileOpt a transient instance containing updated state
	 */
	public void update(com.allinfinance.po.settle.TblInfileOpt tblInfileOpt)
	{
		super.update(tblInfileOpt);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblInfileOpt the instance to be removed
	 */
	public void delete(com.allinfinance.po.settle.TblInfileOpt tblInfileOpt)
	{
		super.delete((Object) tblInfileOpt);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(com.allinfinance.po.settle.TblInfileOptPK id)
	{
		super.delete((Object) load(id));
	}






}