package com.allinfinance.dao.impl.profit;

import java.math.BigDecimal;
import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblAgentFeeCfgHis;
import com.allinfinance.system.util.CommonFunction;

public class TblAgentFeeCfgHisDAO  extends _RootDAO<TblAgentFeeCfgHis> implements com.allinfinance.dao.iface.profit.TblAgentFeeCfgHisDAO{

	@Override
	protected Class<TblAgentFeeCfgHis> getReferenceClass() {
		return TblAgentFeeCfgHis.class;
	}

	@Override
	public TblAgentFeeCfgHis get(String key) {
		return (TblAgentFeeCfgHis) get(getReferenceClass(), key);
	}

	@Override
	public void delete(String brhId) {
//		super.delete(load(getReferenceClass(), key));
		String sql="DELETE FROM TBL_AGENT_FEE_CFG_HIS WHERE AGENT_NO='"+brhId+"' ";
		CommonFunction.getCommQueryDAO().excute(sql);		
	}

	@Override
	public void update(TblAgentFeeCfgHis tblAgentFeeCfgHis) {
		super.update(tblAgentFeeCfgHis);
	}

	@Override
	public String save(TblAgentFeeCfgHis tblAgentFeeCfgHis) {
		return (String) super.save(tblAgentFeeCfgHis);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TblAgentFeeCfgHis findByBrhid(String brhId) {
		TblAgentFeeCfgHis tafc = new TblAgentFeeCfgHis();
		tafc.setAgentNo(brhId);
		List<TblAgentFeeCfgHis> list = getHibernateTemplate().findByExample(tafc);
		return (list!=null&&list.size()>0)?list.get(0):tafc;
	}
	
	public TblAgentFeeCfgHis findByBrhidSeqId(String brhId, String seqId) {
		TblAgentFeeCfgHis tafc = new TblAgentFeeCfgHis();
		tafc.setAgentNo(brhId);
		tafc.setSeqId(seqId);
		List<TblAgentFeeCfgHis> list = getHibernateTemplate().findByExample(tafc);
		return (list!=null&&list.size()>0)?list.get(0):tafc;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getNextSeqId(String brhId) {
		String sql = "select max(to_number(SEQ_ID))+1 from TBL_AGENT_FEE_CFG_HIS WHERE AGENT_NO='"+brhId+"' ";
		List<BigDecimal> resultSet= CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if(resultSet.size()>0 && resultSet.get(0) != null){
			return resultSet.get(0).toString();
		}else{
			return "1";
		}
	}

}