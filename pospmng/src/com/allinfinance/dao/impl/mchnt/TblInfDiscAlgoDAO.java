package com.allinfinance.dao.impl.mchnt;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.mchnt.TblInfDiscAlgo;
import com.allinfinance.po.mchnt.TblInfDiscAlgoPK;

public class TblInfDiscAlgoDAO  extends _RootDAO<com.allinfinance.po.mchnt.TblHisDiscAlgo> implements com.allinfinance.dao.iface.mchnt.TblInfDiscAlgoDAO {

	// query name references
	public static final String QUERY_GET_AL_BY_CD = "getAlByCd";


	public Class getReferenceClass () {
		return TblInfDiscAlgo.class;
	}


	public TblInfDiscAlgo get(TblInfDiscAlgoPK key)
	{
		return (TblInfDiscAlgo) get(getReferenceClass(), key);
	}

	public TblInfDiscAlgo load(TblInfDiscAlgoPK key)
	{
		return (TblInfDiscAlgo) load(getReferenceClass(), key);
	}


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param tblInfDiscAlgo a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public TblInfDiscAlgoPK save(TblInfDiscAlgo tblInfDiscAlgo)
	{
		return (TblInfDiscAlgoPK) super.save(tblInfDiscAlgo);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param tblInfDiscAlgo a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(TblInfDiscAlgo tblInfDiscAlgo)
	{
		super.saveOrUpdate(tblInfDiscAlgo);
	}

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblInfDiscAlgo a transient instance containing updated state
	 */
	public void update(TblInfDiscAlgo tblInfDiscAlgo) 
	{
		super.update(tblInfDiscAlgo);
	}


	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(TblInfDiscAlgoPK id)
	{
		super.delete(load(id));
	}


	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param tblInfDiscAlgo the instance to be removed
	 */
	public void delete(TblInfDiscAlgo tblInfDiscAlgo)
	{
		super.delete(tblInfDiscAlgo);
	}

	/**
	 * Re-read the state of the given instance from the underlying database. It is inadvisable to use this to implement
	 * long-running sessions that span many business tasks. This method is, however, useful in certain special circumstances.
	 * For example 
	 * <ul> 
	 * <li>where a database trigger alters the object state upon insert or update</li>
	 * <li>after executing direct SQL (eg. a mass update) in the same session</li>
	 * <li>after inserting a Blob or Clob</li>
	 * </ul>
	 */
	public void refresh (TblInfDiscAlgo tblInfDiscAlgo)
	{
		super.refresh(tblInfDiscAlgo);
	}

	
}