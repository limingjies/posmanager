package com.allinfinance.bo.impl.route;

import java.util.List;

import com.allinfinance.bo.route.T110331BO;
import com.allinfinance.bo.route.TblRouteRuleInfoBO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.route.TblRouteRuleInfoDAO;
import com.allinfinance.po.route.TblRouteRuleInfo;
import com.allinfinance.system.util.JSONBean;

import net.sf.json.JSONObject;

public class TblRouteRuleInfoBORoute implements TblRouteRuleInfoBO {
	private TblRouteRuleInfoDAO tblRouteRuleInfoDAO;

	@Override
	//添加路由规则
	public String add(TblRouteRuleInfo routeMchtg,String oprId) {
		
		//保存路由规则
		tblRouteRuleInfoDAO.save(routeMchtg);
		
		return Constants.SUCCESS_CODE;
	}
	/**
	 * 更新路由规则
	 */
	@Override
	public void update(TblRouteRuleInfo routeMchtg) {
		tblRouteRuleInfoDAO.update(routeMchtg);
	}
	/**
	 * 删除路由规则
	 */
	@Override
	public void delete(TblRouteRuleInfo routeMchtg) {
		tblRouteRuleInfoDAO.delete(routeMchtg);
	}
	
	public TblRouteRuleInfo get(Integer key) {
		return tblRouteRuleInfoDAO.get(key);
	}
	
	@Override
	public List<TblRouteRuleInfo> getAll() {
		return tblRouteRuleInfoDAO.getAll();
	}
	@Override
	public int getMax() {
		return tblRouteRuleInfoDAO.getMax();
	}
	
	public TblRouteRuleInfoDAO getTblRouteRuleInfoDAO() {
		return tblRouteRuleInfoDAO;
	}
	public void setTblRouteRuleInfoDAO(TblRouteRuleInfoDAO tblRouteRuleInfoDAO) {
		this.tblRouteRuleInfoDAO = tblRouteRuleInfoDAO;
	}
	
}
