package com.allinfinance.struts.base.action;

import java.text.DecimalFormat;

import com.allinfinance.bo.base.T10600BO;
import com.allinfinance.common.CommonsConstants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.po.TblRouteRule;
import com.allinfinance.po.TblRouteRulePK;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.GenerateNextId;

@SuppressWarnings("serial")
public class T10602Action extends BaseAction{

	private T10600BO t10600BO = (T10600BO) ContextUtil.getBean("T10600BO");

	private TblRouteRule tblRouteRule;
	private TblRouteRule tblRouteRuleUpd;
	private String ruleId;
	private String accpId;
	private String brhId;
	private String dateBeg;
	private String dateEnd;
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
		TblRouteRulePK tblRouteRulePK=new TblRouteRulePK();
		tblRouteRulePK.setAccpId(accpId);
		tblRouteRulePK.setBrhId(brhId);
		tblRouteRulePK.setRuleId(ruleId);
		TblRouteRule tblRouteRule=t10600BO.get(tblRouteRulePK);
		if(tblRouteRule==null){
			return "该路由规则不存在，请刷新重新选择！";
		}
		if(CommonsConstants.ROUTE_RULE_START.equals(tblRouteRule.getOnFlag())){
			return "请先停用该路由规则";
		}
		
		return t10600BO.delete(tblRouteRulePK);
	}


	private String update() {
		// TODO Auto-generated method stub
		TblRouteRulePK tblRouteRulePK=new TblRouteRulePK();
		tblRouteRulePK.setAccpId(accpId);
		tblRouteRulePK.setBrhId(brhId);
		tblRouteRulePK.setRuleId(ruleId);
		TblRouteRule tblRouteRuleBf=t10600BO.get(tblRouteRulePK);
		if(tblRouteRuleBf==null){
			return "该路由规则不存在，请刷新重新选择！";
		}
		if(CommonsConstants.ROUTE_RULE_START.equals(tblRouteRuleBf.getOnFlag())){
			return "请先停用该路由规则";
		}
		if(StringUtil.isEmpty(tblRouteRuleUpd.getCardBin())||"*-无限制".equals(tblRouteRuleUpd.getCardBin())){
			tblRouteRuleUpd.setCardBin("*");
		}
		tblRouteRuleUpd.setOnFlag(CommonsConstants.ROUTE_RULE_STOP);
		tblRouteRuleUpd.setTblRouteRulePK(tblRouteRulePK);
		tblRouteRuleUpd.setDateEnd(dateEnd);
		tblRouteRuleUpd.setDateBeg(dateBeg);
		if(CommonsConstants.ROUTE_RULE_DATE_CTL.equals(tblRouteRuleUpd.getDateCtl())){
			tblRouteRuleUpd.setDateBeg(tblRouteRuleUpd.getDateBeg().replace("-", ""));
			tblRouteRuleUpd.setDateEnd(tblRouteRuleUpd.getDateEnd().replace("-", ""));
		}
		if(CommonsConstants.ROUTE_RULE_TIME_CTL.equals(tblRouteRuleUpd.getTimeCtl())){
			tblRouteRuleUpd.setTimeBeg(tblRouteRuleUpd.getTimeBeg().replace(":", "")+"00");
			tblRouteRuleUpd.setTimeEnd(tblRouteRuleUpd.getTimeEnd().replace(":", "")+"59");
		}
		if(CommonsConstants.ROUTE_RULE_AMT_CTL.equals(tblRouteRuleUpd.getAmtCtl())){
			DecimalFormat df=new DecimalFormat("0.00");
			if(Double.parseDouble(tblRouteRuleUpd.getAmtBeg().toString())>Double.parseDouble(tblRouteRuleUpd.getAmtEnd().toString())){
				return "起始金额不得大于结束金额！";
			}
			tblRouteRuleUpd.setAmtBeg(CommonFunction.fillString(df.format(Double.parseDouble(tblRouteRuleUpd.getAmtBeg().toString())).replace(".", ""), '0', 12, false));
			tblRouteRuleUpd.setAmtEnd(CommonFunction.fillString(df.format(Double.parseDouble(tblRouteRuleUpd.getAmtEnd().toString())).replace(".", ""), '0', 12, false));
		}
		
		return t10600BO.update(tblRouteRuleUpd);
	}


	private String add() {
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(tblRouteRule.getCardBin())||"*-无限制".equals(tblRouteRule.getCardBin())){
			tblRouteRule.setCardBin("*");
		}
		tblRouteRule.setOnFlag(CommonsConstants.ROUTE_RULE_STOP);
		tblRouteRule.getTblRouteRulePK().setRuleId(GenerateNextId.getRuleId());
		if(CommonsConstants.ROUTE_RULE_DATE_CTL.equals(tblRouteRule.getDateCtl())){
			tblRouteRule.setDateBeg(tblRouteRule.getDateBeg().replace("-", ""));
			tblRouteRule.setDateEnd(tblRouteRule.getDateEnd().replace("-", ""));
		}
		if(CommonsConstants.ROUTE_RULE_TIME_CTL.equals(tblRouteRule.getTimeCtl())){
			tblRouteRule.setTimeBeg(tblRouteRule.getTimeBeg().replace(":", "")+"00");
			tblRouteRule.setTimeEnd(tblRouteRule.getTimeEnd().replace(":", "")+"59");
		}
		if(CommonsConstants.ROUTE_RULE_AMT_CTL.equals(tblRouteRule.getAmtCtl())){
			DecimalFormat df=new DecimalFormat("0.00");
			if(Double.parseDouble(tblRouteRule.getAmtBeg().toString())>Double.parseDouble(tblRouteRule.getAmtEnd().toString())){
				return "起始金额不得大于结束金额！";
			}
			tblRouteRule.setAmtBeg(CommonFunction.fillString(df.format(Double.parseDouble(tblRouteRule.getAmtBeg().toString())).replace(".", ""), '0', 12, false));
			tblRouteRule.setAmtEnd(CommonFunction.fillString(df.format(Double.parseDouble(tblRouteRule.getAmtEnd().toString())).replace(".", ""), '0', 12, false));
		}
		
		return t10600BO.add(tblRouteRule);
	}


	/**
	 * @return the tblRouteRule
	 */
	public TblRouteRule getTblRouteRule() {
		return tblRouteRule;
	}


	/**
	 * @param tblRouteRule the tblRouteRule to set
	 */
	public void setTblRouteRule(TblRouteRule tblRouteRule) {
		this.tblRouteRule = tblRouteRule;
	}


	/**
	 * @return the tblRouteRuleUpd
	 */
	public TblRouteRule getTblRouteRuleUpd() {
		return tblRouteRuleUpd;
	}


	/**
	 * @param tblRouteRuleUpd the tblRouteRuleUpd to set
	 */
	public void setTblRouteRuleUpd(TblRouteRule tblRouteRuleUpd) {
		this.tblRouteRuleUpd = tblRouteRuleUpd;
	}


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


	/**
	 * @return the dateBeg
	 */
	public String getDateBeg() {
		return dateBeg;
	}


	/**
	 * @param dateBeg the dateBeg to set
	 */
	public void setDateBeg(String dateBeg) {
		this.dateBeg = dateBeg;
	}


	/**
	 * @return the dateEnd
	 */
	public String getDateEnd() {
		return dateEnd;
	}


	/**
	 * @param dateEnd the dateEnd to set
	 */
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	
}
