package com.allinfinance.bo.impl.route;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.allinfinance.bo.route.TblRouteRuleMapBO;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.route.TblRouteRuleMapDAO;
import com.allinfinance.po.route.TblRouteRuleMap;

public class TblRouteRuleMapBORoute implements TblRouteRuleMapBO {
	private TblRouteRuleMapDAO tblRouteRuleMapDAO;
	private ICommQueryDAO commGWQueryDAO;
	

	@Override
	public TblRouteRuleMap get(Integer key) {
		return tblRouteRuleMapDAO.get(key);
	}

	/**
	 * 查询是否存在映射关系对应该渠道商户
	 */
	@SuppressWarnings("rawtypes")
	public List routeRuleMapByMchtIdUp(String mchtIdUp) {
		if (!StringUtils.isBlank(mchtIdUp)) {
			//查询是否存在映射关系对应该渠道商户
			String sql = "SELECT a.RULE_ID, a.MCHT_ID, a.BRH_ID3, a.MCHT_ID_UP FROM TBL_ROUTE_RULE_MAP a ,Tbl_Route_Rule_Map_Hist b where a.RULE_ID=b.RULE_ID and b.status=0 and a.MCHT_ID_UP ='"+mchtIdUp+"'";
			List list = commGWQueryDAO.findBySQLQuery(sql);
			return list;
		}else {
			return null;
		}
	}

	public TblRouteRuleMapDAO getTblRouteRuleMapDAO() {
		return tblRouteRuleMapDAO;
	}

	public void setTblRouteRuleMapDAO(TblRouteRuleMapDAO tblRouteRuleMapDAO) {
		this.tblRouteRuleMapDAO = tblRouteRuleMapDAO;
	}

	public ICommQueryDAO getCommGWQueryDAO() {
		return commGWQueryDAO;
	}

	public void setCommGWQueryDAO(ICommQueryDAO commGWQueryDAO) {
		this.commGWQueryDAO = commGWQueryDAO;
	}
	
}
