package com.allinfinance.dao.impl.profit;

import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblAgentFeeCfg;

public class TblAgentFeeCfgDAO  extends _RootDAO<TblAgentFeeCfg> implements com.allinfinance.dao.iface.profit.TblAgentFeeCfgDAO{

	@Override
	protected Class<TblAgentFeeCfg> getReferenceClass() {
		return TblAgentFeeCfg.class;
	}

	@Override
	public TblAgentFeeCfg get(String key) {
		return (TblAgentFeeCfg) get(getReferenceClass(), key);
	}

	@Override
	public void delete(String key) {
		super.delete(load(getReferenceClass(), key));
	}

	@Override
	public void update(TblAgentFeeCfg tblAgentFeeCfg) {
		super.update(tblAgentFeeCfg);
	}

	@Override
	public String save(TblAgentFeeCfg tblAgentFeeCfg) {
		return (String) super.save(tblAgentFeeCfg);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TblAgentFeeCfg findByBrhid(String brhId) {
		TblAgentFeeCfg tafc = new TblAgentFeeCfg();
		tafc.setAgentNo(brhId);
		List<TblAgentFeeCfg> list = getHibernateTemplate().findByExample(tafc);
		return (list!=null&&list.size()>0)?list.get(0):tafc;
	}

}