package com.allinfinance.struts.risk.action;

import com.allinfinance.bo.risk.T40102BO;
import com.allinfinance.common.CommonsConstants;
import com.allinfinance.po.TblRunRisk;
import com.allinfinance.po.TblRunRiskPK;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T40102Action extends BaseAction{

	private T40102BO t40102BO = (T40102BO) ContextUtil.getBean("T40102BO");
	
	private String riskLvl;
	private String cardAccpId;
	private String ruleId;
	private TblRunRisk tblRunRiskAdd;
	private TblRunRisk tblRunRiskUpd;
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		if("add".equals(getMethod())) {			
			rspCode = add();			
		} else if("update".equals(getMethod())) {
			rspCode = update();
		}else if("delete".equals(getMethod())) {
			rspCode = delete();
		}
		return rspCode;
	}

	private String delete() {
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
			return "请先停用该风控规则";
		}
		
		return t40102BO.delete(tblRunRiskPK);
	}


	private String update() {
		// TODO Auto-generated method stub
		TblRunRiskPK tblRunRiskPK=new TblRunRiskPK();
		tblRunRiskPK.setCardAccpId(CommonFunction.fillString(cardAccpId, ' ', 15, true));
		tblRunRiskPK.setRiskLvl(riskLvl);
		tblRunRiskPK.setRuleId(Integer.parseInt(ruleId));
		TblRunRisk tblRunRiskBf=t40102BO.get(tblRunRiskPK);
		if(tblRunRiskBf==null){
			return "该风控信息不存在，请刷新重新选择！";
		}
		if(CommonsConstants.ROUTE_RULE_START.equals(tblRunRiskBf.getOnFlag())){
			return "请先停用该风控信息";
		}
		tblRunRiskUpd.setOnFlag(CommonsConstants.ROUTE_RULE_STOP);
		tblRunRiskUpd.setId(tblRunRiskPK);
//		tblRunRiskUpd.setRegTime(CommonFunction.getCurrentDateTime());
		tblRunRiskUpd.setUpdTime(CommonFunction.getCurrentDateTime());
		
		
		return t40102BO.update(tblRunRiskUpd);
	}


	private String add() {
		// TODO Auto-generated method stub
		if(!"*".equals(tblRunRiskAdd.getId().getCardAccpId())){
			tblRunRiskAdd.getId().setRiskLvl("*");
		}
		tblRunRiskAdd.getId().setRuleId(Integer.parseInt(t40102BO.getRuleId()));
		tblRunRiskAdd.setOnFlag(CommonsConstants.ROUTE_RULE_STOP);
		tblRunRiskAdd.setRegTime(CommonFunction.getCurrentDateTime());
		
		return t40102BO.add(tblRunRiskAdd);
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

	public TblRunRisk getTblRunRiskAdd() {
		return tblRunRiskAdd;
	}

	public void setTblRunRiskAdd(TblRunRisk tblRunRiskAdd) {
		this.tblRunRiskAdd = tblRunRiskAdd;
	}

	public TblRunRisk getTblRunRiskUpd() {
		return tblRunRiskUpd;
	}

	public void setTblRunRiskUpd(TblRunRisk tblRunRiskUpd) {
		this.tblRunRiskUpd = tblRunRiskUpd;
	}
	
	
	
	

	
}
