package com.allinfinance.struts.base.action;

import com.allinfinance.bo.base.T10600BO;
import com.allinfinance.common.CommonsConstants;
import com.allinfinance.po.TblRouteRule;
import com.allinfinance.po.TblRouteRulePK;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T10603Action extends BaseAction{

	private T10600BO t10600BO = (T10600BO) ContextUtil.getBean("T10600BO");
//	private T10211BO t10211BO = (T10211BO) ContextUtil.getBean("T10211BO");
	
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		try {
			if("start".equals(getMethod())) {			
					rspCode = start();			
			} else if("stop".equals(getMethod())) {
				rspCode = stop();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，对路由控制操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}

	private String stop() {
		// TODO Auto-generated method stub
		TblRouteRulePK tblRouteRulePK=new TblRouteRulePK();
		tblRouteRulePK.setAccpId(accpId);
		tblRouteRulePK.setBrhId(brhId);
		tblRouteRulePK.setRuleId(ruleId);
		TblRouteRule tblRouteRule=t10600BO.get(tblRouteRulePK);
		if(CommonsConstants.ROUTE_RULE_STOP.equals(tblRouteRule.getOnFlag())){
			return "该路由规则状态已为停用,请重新刷新操作！";
		}
		tblRouteRule.setOnFlag(CommonsConstants.ROUTE_RULE_STOP);
		
		return t10600BO.update(tblRouteRule);
	}

	private String start() {
		// TODO Auto-generated method stub
		TblRouteRulePK tblRouteRulePK=new TblRouteRulePK();
		tblRouteRulePK.setAccpId(accpId);
		tblRouteRulePK.setBrhId(brhId);
		tblRouteRulePK.setRuleId(ruleId);
		TblRouteRule tblRouteRule=t10600BO.get(tblRouteRulePK);
		if(CommonsConstants.ROUTE_RULE_START.equals(tblRouteRule.getOnFlag())){
			return "该路由规则状态已为启用,请重新刷新操作！";
		}
		/*if(CommonsConstants.ROUTE_RULE_STOP.equals(t10211BO.get(CommonFunction.fillString(tblRouteRule.getRuleCode().trim(), ' ',10, true)))){
			return "该路由规则商户映射已停用,请先启用该规则商户映射！";
		}*/
		tblRouteRule.setOnFlag(CommonsConstants.ROUTE_RULE_START);
		
		return t10600BO.update(tblRouteRule);
	}

	private String ruleId;
	private String accpId;
	private String brhId;

	/**
	 * @return the ruleId
	 */
	public String getRuleId() {
		return ruleId;
	}

	/**
	 * @param ruleId the ruleId to set
	 */
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	/**
	 * @return the accpId
	 */
	public String getAccpId() {
		return accpId;
	}

	/**
	 * @param accpId the accpId to set
	 */
	public void setAccpId(String accpId) {
		this.accpId = accpId;
	}

	/**
	 * @return the brhId
	 */
	public String getBrhId() {
		return brhId;
	}

	/**
	 * @param brhId the brhId to set
	 */
	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}
	
}
