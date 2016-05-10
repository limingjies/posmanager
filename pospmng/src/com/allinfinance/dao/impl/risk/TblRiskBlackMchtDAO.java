package com.allinfinance.dao.impl.risk;


import com.allinfinance.dao._RootDAO;

public class TblRiskBlackMchtDAO extends _RootDAO<com.allinfinance.po.TblRiskBlackMcht> implements com.allinfinance.dao.iface.risk.TblRiskBlackMchtDAO {

	public TblRiskBlackMchtDAO() {
	}


	public Class<com.allinfinance.po.TblRiskBlackMcht> getReferenceClass() {
		return com.allinfinance.po.TblRiskBlackMcht.class;
	}

	/**
	 * Cast the object as a com.allinfinance.po.TblCtlMchtInf
	 */
	public com.allinfinance.po.TblRiskBlackMcht cast(Object object) {
		return (com.allinfinance.po.TblRiskBlackMcht) object;
	}

	public com.allinfinance.po.TblRiskBlackMcht load(java.lang.String key) {
		return (com.allinfinance.po.TblRiskBlackMcht) load(getReferenceClass(), key);
	}

	public com.allinfinance.po.TblRiskBlackMcht get(java.lang.String key) {
		return (com.allinfinance.po.TblRiskBlackMcht) get(getReferenceClass(), key);
	}

	@SuppressWarnings("unchecked")
	public java.util.List<com.allinfinance.po.TblRiskBlackMcht> loadAll() {
		return loadAll(getReferenceClass());
	}

	public java.lang.String save(com.allinfinance.po.TblRiskBlackMcht tblRiskBlackMcht) {
		return (java.lang.String) super.save(tblRiskBlackMcht);
	}

	public void update(com.allinfinance.po.TblRiskBlackMcht tblRiskBlackMcht) {
		super.update(tblRiskBlackMcht);
	}

	public void delete(java.lang.String id) {
		super.delete((Object) load(id));
	}

}