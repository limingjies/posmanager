package com.allinfinance.dao.impl.agentpay;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.agentpay.TblParamInfo;
import com.allinfinance.po.agentpay.TblParamInfoPK;

public class TblParamInfoDAO extends _RootDAO<com.allinfinance.po.agentpay.TblParamInfo> implements com.allinfinance.dao.iface.agentpay.TblParamInfoDAO {

	@Override
	protected Class getReferenceClass() {
		// TODO Auto-generated method stub
		return com.allinfinance.po.agentpay.TblParamInfo.class;
	}

	@Override
	public TblParamInfo get(TblParamInfoPK key) {
		// TODO Auto-generated method stub
		return (com.allinfinance.po.agentpay.TblParamInfo)get(getReferenceClass(), key);
	}

	@Override
	public TblParamInfo load(TblParamInfoPK key) {
		// TODO Auto-generated method stub
		return (com.allinfinance.po.agentpay.TblParamInfo)load(getReferenceClass(), key);
	}

	@Override
	public List<TblParamInfo> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TblParamInfoPK save(TblParamInfo tblParamInfo) {
		// TODO Auto-generated method stub
		return (TblParamInfoPK)super.save(tblParamInfo);
	}

	@Override
	public void saveOrUpdate(TblParamInfo tblParamInfo) {
		super.saveOrUpdate(tblParamInfo);
	}

	@Override
	public void update(TblParamInfo tblParamInfo) {
		super.update(tblParamInfo);
	}

	@Override
	public void delete(TblParamInfoPK id) {
		super.delete((Object) load(id));
	}

	@Override
	public void delete(TblParamInfo tblParamInfo) {
		// TODO Auto-generated method stub
		super.delete((Object) tblParamInfo);
	}

}
