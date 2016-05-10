package com.allinfinance.dao.impl.base;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblBrhInfoHis;
import com.allinfinance.po.TblBrhInfoHisPK;
import com.allinfinance.system.util.CommonFunction;


public class TblBrhInfoHisDAO extends _RootDAO<com.allinfinance.po.TblBrhInfoHis> implements com.allinfinance.dao.iface.base.TblBrhInfoHisDAO {

	public TblBrhInfoHisDAO () {}
	
	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.TblBrhInfoDAO#findAll()
	 */
	public List<TblBrhInfoHis> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Class<com.allinfinance.po.TblBrhInfoHis> getReferenceClass () {
		return com.allinfinance.po.TblBrhInfoHis.class;
	}


	/**
	 * Cast the object as a com.allinfinance.po.brh.TblBrhInfoHis
	 */
	public com.allinfinance.po.TblBrhInfoHis cast (Object object) {
		return (com.allinfinance.po.TblBrhInfoHis) object;
	}


	public com.allinfinance.po.TblBrhInfoHis load(TblBrhInfoHisPK key)
	{
		return (com.allinfinance.po.TblBrhInfoHis) load(getReferenceClass(), key);
	}

	public com.allinfinance.po.TblBrhInfoHis get(TblBrhInfoHisPK key)
	{
		return (com.allinfinance.po.TblBrhInfoHis) get(getReferenceClass(), key);
	}

	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param tblBrhInfoHis a transient instance of a persistent class
	 * @return the class identifier
	 */
	public com.allinfinance.po.TblBrhInfoHisPK save(com.allinfinance.po.TblBrhInfoHis tblBrhInfoHis)
	{
		return (com.allinfinance.po.TblBrhInfoHisPK) super.save(tblBrhInfoHis);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param tblBrhInfoHis a transient instance containing new or updated state
	 */
	public void saveOrUpdate(com.allinfinance.po.TblBrhInfoHis tblBrhInfoHis)
	{
		super.saveOrUpdate(tblBrhInfoHis);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param tblBrhInfoHis a transient instance containing updated state
	 */
	public void update(com.allinfinance.po.TblBrhInfoHis tblBrhInfoHis)
	{
		super.update(tblBrhInfoHis);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param tblBrhInfoHis the instance to be removed
	 */
	public void delete(com.allinfinance.po.TblBrhInfoHis tblBrhInfoHis)
	{
		super.delete((Object) tblBrhInfoHis);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String brhId)
	{
		String sql="DELETE FROM TBL_BRH_INFO_HIS WHERE BRH_ID='"+brhId+"' ";
		CommonFunction.getCommQueryDAO().excute(sql);
	}


	@Override
	public void delete(TblBrhInfoHisPK id) {
		super.delete((Object) load(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getNextSeqId(String brhId) {
		String sql = "select max(to_number(SEQ_ID))+1 from TBL_BRH_INFO_HIS WHERE BRH_ID='"+brhId+"' ";
		List<BigDecimal> resultSet= CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if(resultSet.size()>0 && resultSet.get(0) != null){
			return resultSet.get(0).toString();
		}else{
			return "1";
		}
	}
}