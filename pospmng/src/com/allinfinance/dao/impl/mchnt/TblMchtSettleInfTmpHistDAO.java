package com.allinfinance.dao.impl.mchnt;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtSettleInfTmpHistDAO;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmpHist;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmpHistPK;
import com.allinfinance.system.util.CommonFunction;

public class TblMchtSettleInfTmpHistDAO extends _RootDAO<TblMchtSettleInfTmpHist> implements ITblMchtSettleInfTmpHistDAO{

	// query name references

	public Class<TblMchtSettleInfTmpHist> getReferenceClass () {
		return TblMchtSettleInfTmpHist.class;
	}


	/**
	 * Cast the object as a TblMchtSettleInfTmpHist
	 */
	public TblMchtSettleInfTmpHist cast (Object object) {
		return (TblMchtSettleInfTmpHist) object;
	}


	public TblMchtSettleInfTmpHist load(java.lang.String key)
		throws org.hibernate.HibernateException {
		return (TblMchtSettleInfTmpHist) load(getReferenceClass(), key);
	}

	public TblMchtSettleInfTmpHist get(TblMchtSettleInfTmpHistPK key)
		throws org.hibernate.HibernateException {
		return (TblMchtSettleInfTmpHist) get(getReferenceClass(), key);
	}


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.)
	 * @param TblMchtSettleInfTmpHist a transient instance of a persistent class
	 * @return the class identifier
	 */
	public TblMchtSettleInfTmpHistPK save(TblMchtSettleInfTmpHist TblMchtSettleInfTmpHist)
		throws org.hibernate.HibernateException {
		return (TblMchtSettleInfTmpHistPK) super.save(TblMchtSettleInfTmpHist);
	}

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping.
	 * @param TblMchtSettleInfTmpHist a transient instance containing new or updated state
	 */
	public void saveOrUpdate(TblMchtSettleInfTmpHist TblMchtSettleInfTmpHist)
		throws org.hibernate.HibernateException {
		super.saveOrUpdate(TblMchtSettleInfTmpHist);
	}


	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param TblMchtSettleInfTmpHist a transient instance containing updated state
	 */
	public void update(TblMchtSettleInfTmpHist TblMchtSettleInfTmpHist)
		throws org.hibernate.HibernateException {
		super.update(TblMchtSettleInfTmpHist);
	}

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state.
	 * @param TblMchtSettleInfTmpHist the instance to be removed
	 */
	public void delete(TblMchtSettleInfTmpHist TblMchtSettleInfTmpHist)
		throws org.hibernate.HibernateException {
		super.delete((Object) TblMchtSettleInfTmpHist);
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


	@Override
	public void updateByMchtNo(String newMchtNo, String mchntId) {
		String sql="update TBL_MCHT_SETTLE_INF_TMP_HIST set MCHT_NO = '"+newMchtNo+"' WHERE MCHT_NO='"+mchntId+"' ";
		CommonFunction.getCommQueryDAO().excute(sql);
		
	}
}