package com.allinfinance.dao.impl.profit;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblAgentRateInfo;
import com.allinfinance.po.TblAgentRateInfoId;

public class TblAgentRateInfoDAO extends _RootDAO<TblAgentRateInfo> implements com.allinfinance.dao.iface.profit.TblAgentRateInfoDAO {

	@Override
	public TblAgentRateInfo get(TblAgentRateInfoId key) {
		return (TblAgentRateInfo) get(getReferenceClass(),key);
	}

	@Override
	public void delete(TblAgentRateInfoId key) {
		super.delete(load(getReferenceClass(),key));
	}
	
	@Override
	public void deleteByDiscId(String discId) {
		getHibernateTemplate().deleteAll(findByDiscid(discId));
	}

	@Override
	public void update(TblAgentRateInfo tblAgentRateInfo) {
		super.update(tblAgentRateInfo);
	}

	@Override
	public void save(TblAgentRateInfo tblAgentRateInfo) {
		super.save(tblAgentRateInfo);
	}

	@Override
	protected Class<TblAgentRateInfo> getReferenceClass() {
		return TblAgentRateInfo.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblAgentRateInfo> findByDiscid(String discId) {
		return getHibernateTemplate().findByNamedParam("from TblAgentRateInfo t where t.id.discId = :discId", "discId", discId);
	}
	
}