package com.allinfinance.dao.impl.risk;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.risk.TblRiskParamMcc;

/**
 * MCC风控参数管理--TblRiskParamMccDAO--实现类
 * @author yww
 * @version 1.0
 * 2016年4月7日  下午1:56:44
 */
public class TblRiskParamMccDAO extends _RootDAO<TblRiskParamMcc> implements com.allinfinance.dao.iface.risk.TblRiskParamMccDAO{

	public void save(TblRiskParamMcc tblRiskParamMcc){
		//getHibernateTemplate().save(tblRiskParamMcc);
		//super.save(tblRiskParamMcc);
		this.getHibernateTemplate().save(tblRiskParamMcc);
	}
	
	public void delete(TblRiskParamMcc tblRiskParamMcc){
		getHibernateTemplate().delete(tblRiskParamMcc);
	}
	
	public void update(TblRiskParamMcc tblRiskParamMcc){
		super.update(tblRiskParamMcc);
	}
	
	public TblRiskParamMcc get(String key){
		return (TblRiskParamMcc) get(getReferenceClass(), key);
	}
	
	public TblRiskParamMcc load(String key){
		return (TblRiskParamMcc) load(getReferenceClass(), key);
	}
	
	protected Class<TblRiskParamMcc> getReferenceClass() {
		return TblRiskParamMcc.class;
	}

	public TblRiskParamMcc cast (Object object) {
		return (TblRiskParamMcc) object;
	}
}
