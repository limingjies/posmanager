package com.allinfinance.struts.risk.action;

import com.allinfinance.bo.risk.T40102BO;
import com.allinfinance.common.CommonsConstants;
import com.allinfinance.po.TblRunRisk;
import com.allinfinance.po.TblRunRiskPK;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T40103Action extends BaseAction{

	private T40102BO t40102BO = (T40102BO) ContextUtil.getBean("T40102BO");
	
	private String riskLvl;
	private String cardAccpId;
	private String ruleId;
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		if("stop".equals(getMethod())) {			
			rspCode = stop();			
		} else if("start".equals(getMethod())) {
			rspCode = start();
		}
		return rspCode;
	}

	private String stop() {
		// TODO Auto-generated method stub
		TblRunRiskPK tblRunRiskPK=new TblRunRiskPK();
		tblRunRiskPK.setCardAccpId(CommonFunction.fillString(cardAccpId, ' ', 15, true));
		tblRunRiskPK.setRiskLvl(riskLvl);
		tblRunRiskPK.setRuleId(Integer.parseInt(ruleId));
		TblRunRisk tblRunRisk=t40102BO.get(tblRunRiskPK);
		if(tblRunRisk==null){
			return "该风控信息不存在，请重新刷新选择！";
		}
		if(CommonsConstants.ROUTE_RULE_STOP.equals(tblRunRisk.getOnFlag())){
			return "该风控规则状态已为停用,请重新刷新操作！";
		}
		tblRunRisk.setOnFlag(CommonsConstants.ROUTE_RULE_STOP);
		
		return t40102BO.update(tblRunRisk);
	}

	private String start() {
		// TODO Auto-generated method stub
		TblRunRiskPK tblRunRiskPK=new TblRunRiskPK();
		tblRunRiskPK.setCardAccpId(CommonFunction.fillString(cardAccpId, ' ', 15, true));
		tblRunRiskPK.setRiskLvl(riskLvl);
		tblRunRiskPK.setRuleId(Integer.parseInt(ruleId));
		TblRunRisk tblRunRisk=t40102BO.get(tblRunRiskPK);
		if(tblRunRisk==null){
			return "该风控信息不存在，请重新刷新选择！";
		}
		if(CommonsConstants.ROUTE_RULE_START.equals(tblRunRisk.getOnFlag())){
			return "该路由规则状态已为启用,请重新刷新操作！";
		}
		tblRunRisk.setOnFlag(CommonsConstants.ROUTE_RULE_START);
		
		return t40102BO.update(tblRunRisk);
	}

	public String getRiskLvl() {
		return riskLvl;
	}

	public void setRiskLvl(String riskLvl) {
		this.riskLvl = riskLvl;
	}

	public String getCardAccpId() {
		return cardAccpId;
	}

	public void setCardAccpId(String cardAccpId) {
		this.cardAccpId = cardAccpId;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	
	
	
	

	
}
