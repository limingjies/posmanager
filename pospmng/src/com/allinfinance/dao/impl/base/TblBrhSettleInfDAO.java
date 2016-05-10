package com.allinfinance.dao.impl.base;


import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.base.TblBrhSettleInf;

public class TblBrhSettleInfDAO extends _RootDAO<TblBrhSettleInf> implements
		com.allinfinance.dao.iface.base.TblBrhSettleInfDAO {

	public TblBrhSettleInfDAO() {
	}

	public Class<TblBrhSettleInf> getReferenceClass() {
		return TblBrhSettleInf.class;
	}

	/**
	 * Cast the object as a com.allinfinance.po.brh.TblBrhInfo
	 */
	public TblBrhSettleInf cast(Object object) {
		return (TblBrhSettleInf) object;
	}

	public TblBrhSettleInf load(java.lang.String key) {
		return (TblBrhSettleInf) load(getReferenceClass(), key);
	}

	public TblBrhSettleInf get(java.lang.String key) {
		return (TblBrhSettleInf) get(getReferenceClass(), key);
	}

	public void save(TblBrhSettleInf tblBrhSettleInf) {
		 super.save(tblBrhSettleInf);
	}

	public void update(TblBrhSettleInf tblBrhSettleInf) {
		super.update(tblBrhSettleInf);
	}

	public void delete(java.lang.String id) {
		super.delete((Object) load(id));
	}
}