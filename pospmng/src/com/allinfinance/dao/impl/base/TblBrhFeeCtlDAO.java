package com.allinfinance.dao.impl.base;


import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.base.TblBrhFeeCtl;

public class TblBrhFeeCtlDAO extends _RootDAO<TblBrhFeeCtl> implements
		com.allinfinance.dao.iface.base.TblBrhFeeCtlDAO {

	public TblBrhFeeCtlDAO() {
	}

	public Class<TblBrhFeeCtl> getReferenceClass() {
		return TblBrhFeeCtl.class;
	}

	/**
	 * Cast the object as a com.allinfinance.po.brh.TblBrhInfo
	 */
	public TblBrhFeeCtl cast(Object object) {
		return (TblBrhFeeCtl) object;
	}

	public TblBrhFeeCtl load(java.lang.String key) {
		return (TblBrhFeeCtl) load(getReferenceClass(), key);
	}

	public TblBrhFeeCtl get(java.lang.String key) {
		return (TblBrhFeeCtl) get(getReferenceClass(), key);
	}

	public void save(TblBrhFeeCtl tblBrhFeeCtl) {
		 super.save(tblBrhFeeCtl);
	}

	public void update(TblBrhFeeCtl tblBrhFeeCtl) {
		super.update(tblBrhFeeCtl);
	}

	public void delete(String id) {
		super.delete((Object) load(id));
	}
}