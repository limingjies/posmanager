/**
 * 
 */
package com.allinfinance.dao.impl.profit;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblAgentRateInfo;
import com.allinfinance.po.base.TblBrhCashInf;
import com.allinfinance.po.base.TblBrhCashInfId;

/**
 * @author 陈巍
 *
 */
public class TblBrhCashInfoDAO extends _RootDAO<TblBrhCashInf> implements com.allinfinance.dao.iface.profit.TblBrhCashInfoDAO {

	@Override
	public TblBrhCashInf get(TblBrhCashInfId key) {
		return (TblBrhCashInf)get(getReferenceClass(),key);
	}

	@Override
	public void delete(TblBrhCashInfId key) {
		super.delete(load(getReferenceClass(),key));
		
	}

	@Override
	public void update(TblBrhCashInf tblAgentRateInfo) {
		super.update(tblAgentRateInfo);
	}

	@Override
	public void save(TblBrhCashInf tblAgentRateInfo) {
		super.save(tblAgentRateInfo);
	}

	@Override
	protected Class<TblBrhCashInf> getReferenceClass() {
		return TblBrhCashInf.class;
	}
	
	@Override
	public void deleteByBrhId(String brhId) {
		getHibernateTemplate().deleteAll(findByDiscid(brhId));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblBrhCashInf> findByDiscid(String brhId) {
		return getHibernateTemplate().findByNamedParam("from TblBrhCashInf t where t.id.brhId = :brhId", "brhId", brhId);
	}
}
