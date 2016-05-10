package com.allinfinance.struts.base.action;

import com.allinfinance.bo.base.T10211BO;
import com.allinfinance.common.CommonsConstants;
import com.allinfinance.po.TblRuleMchtRel;
import com.allinfinance.po.TblRuleMchtRelPK;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T10212Action extends BaseAction{

private T10211BO t10211BO = (T10211BO) ContextUtil.getBean("T10211BO");
	
	
	private String ruleCode;
	private String firstMchtCd;
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		if("start".equals(getMethod())) {			
			rspCode = start();			
		} else if("stop".equals(getMethod())) {
			rspCode = stop();
		}
		return rspCode;
	}

	private String stop() {
		// TODO Auto-generated method stub
		TblRuleMchtRelPK tblRuleMchtRelPK=new TblRuleMchtRelPK();
		tblRuleMchtRelPK.setRuleCode(CommonFunction.fillString(ruleCode, ' ',10, true));
		tblRuleMchtRelPK.setFirstMchtCd(CommonFunction.fillString(firstMchtCd, ' ',15, true));
		TblRuleMchtRel tblRuleMchtRel=t10211BO.get(tblRuleMchtRelPK);
		if(tblRuleMchtRel==null){
			return "该规则商户映射信息不存在，请刷新重新选择！";
		}
		if(CommonsConstants.ROUTE_RULE_STOP.equals(tblRuleMchtRel.getMsc2())){
			return "该规则商户映射状态已为停用，请刷新重新选择！";
		}
		tblRuleMchtRel.setMsc2(CommonsConstants.ROUTE_RULE_STOP);
		return t10211BO.update(tblRuleMchtRel);
	}

	private String start() {
		// TODO Auto-generated method stub
		TblRuleMchtRelPK tblRuleMchtRelPK=new TblRuleMchtRelPK();
		tblRuleMchtRelPK.setRuleCode(CommonFunction.fillString(ruleCode, ' ',10, true));
		tblRuleMchtRelPK.setFirstMchtCd(CommonFunction.fillString(firstMchtCd, ' ',15, true));
		TblRuleMchtRel tblRuleMchtRel=t10211BO.get(tblRuleMchtRelPK);
		if(tblRuleMchtRel==null){
			return "该规则商户映射信息不存在，请刷新重新选择！";
		}
		if(CommonsConstants.ROUTE_RULE_START.equals(tblRuleMchtRel.getMsc2())){
			return "该规则商户映射状态已为启用，请刷新重新选择！";
		}
		tblRuleMchtRel.setMsc2(CommonsConstants.ROUTE_RULE_START);
		return t10211BO.update(tblRuleMchtRel);
	}

	/**
	 * @return the ruleCode
	 */
	public String getRuleCode() {
		return ruleCode;
	}

	/**
	 * @param ruleCode the ruleCode to set
	 */
	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	public String getFirstMchtCd() {
		return firstMchtCd;
	}

	public void setFirstMchtCd(String firstMchtCd) {
		this.firstMchtCd = firstMchtCd;
	}

	
	
}
