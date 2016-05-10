package com.allinfinance.dao.iface.route;

import com.allinfinance.po.route.TblRouteRuleMapHist;
import com.allinfinance.po.route.TblRouteRuleMapHistPk;

public interface TblRouteRuleMapHistDAO {
	public Class<TblRouteRuleMapHist> getReferenceClass();

	public TblRouteRuleMapHist cast(Object object);

	public TblRouteRuleMapHist load(TblRouteRuleMapHistPk key);

	public TblRouteRuleMapHist get(TblRouteRuleMapHistPk key);

	public TblRouteRuleMapHistPk save(TblRouteRuleMapHist TblRouteRuleMapHist);

	public void saveOrUpdate(TblRouteRuleMapHist TblRouteRuleMapHist);

	public void update(TblRouteRuleMapHist TblRouteRuleMapHist);

	public void delete(TblRouteRuleMapHist TblRouteRuleMapHist);

	public void delete(TblRouteRuleMapHistPk id);

	public Integer getRuleId();


	public void setStatusByMchtId(String mchtId, String oprId);

	public void updateByConditions(String mchntId, String mchtUpbrhNo,
			String propertyId, String oprId,String termId);

	public void updateByConditions(String mchtId, String propertyId,
			String oprId,String termId);

	public TblRouteRuleMapHist getByConditions(String mchtId, String mchtUpbrhId,
			String propertyId, String termId);

	public void updateByMcht(String mchtId, String oprId);

}