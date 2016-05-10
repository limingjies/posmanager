package com.allinfinance.dao.impl.profit;

import java.math.BigDecimal;
import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblAgentRateInfoHis;
import com.allinfinance.po.TblAgentRateInfoId;
import com.allinfinance.system.util.CommonFunction;

public class TblAgentRateInfoHisDAO extends _RootDAO<TblAgentRateInfoHis> implements com.allinfinance.dao.iface.profit.TblAgentRateInfoHisDAO {

	@Override
	public TblAgentRateInfoHis get(TblAgentRateInfoId key) {
		return (TblAgentRateInfoHis) get(getReferenceClass(),key);
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
	public void update(TblAgentRateInfoHis tblAgentRateInfoHis) {
		super.update(tblAgentRateInfoHis);
	}

	@Override
	public void save(TblAgentRateInfoHis tblAgentRateInfoHis) {
		super.save(tblAgentRateInfoHis);
	}

	@Override
	protected Class<TblAgentRateInfoHis> getReferenceClass() {
		return TblAgentRateInfoHis.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TblAgentRateInfoHis> findByDiscid(String discId) {
		return getHibernateTemplate().findByNamedParam("from TblAgentRateInfoHis t where t.id.discId = :discId", "discId", discId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getNextSeqId(String discId, String rateId) {
		String sql = "select max(to_number(SEQ_ID))+1 from TBL_AGENT_RATE_INFO_HIS WHERE DISC_ID='"+discId+"' AND RATE_ID='" + rateId +"'";
		List<BigDecimal> resultSet= CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if(resultSet.size()>0 && resultSet.get(0) != null){
			return resultSet.get(0).toString();
		}else{
			return "1";
		}
	}
	
}