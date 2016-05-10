package com.allinfinance.bo.route;

import java.util.List;
import com.allinfinance.po.route.TblRouteRuleMap;

public interface TblRouteRuleMapBO {
	public TblRouteRuleMap get(Integer key);
	
	/**
	 * 查询是否存在映射关系对应该渠道商户
	 * @param mchtIdUp 渠道商户号
	 */
	@SuppressWarnings("rawtypes")
	public List routeRuleMapByMchtIdUp(String mchtIdUp);

	/*public TblRouteRuleMap load(Integer key);

	public java.util.List<TblRouteRuleMap> findAll();

	public Integer save(TblRouteRuleMap TblRouteRuleMap);

	public void saveOrUpdate(TblRouteRuleMap TblRouteRuleMap);

	public void update(TblRouteRuleMap TblRouteRuleMap);

	public void delete(Integer id);

	public void delete(TblRouteRuleMap tblCityCode);

	public Integer getRuleId();

	public void deleteByConditions(String mchntId, String mchtUpbrhNo,String propertyId);

	public TblRouteRuleMap getByConditions(String mchtId, String mchtUpbrhId,String propertyId);

	public void deleteByMchtId(String mchtId);*/

}
