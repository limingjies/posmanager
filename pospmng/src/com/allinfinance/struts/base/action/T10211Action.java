package com.allinfinance.struts.base.action;

import com.allinfinance.bo.base.T10211BO;
import com.allinfinance.common.CommonsConstants;
import com.allinfinance.po.TblRuleMchtRel;
import com.allinfinance.po.TblRuleMchtRelPK;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T10211Action extends BaseAction{

	private T10211BO t10211BO = (T10211BO) ContextUtil.getBean("T10211BO");
	
	
	private TblRuleMchtRel tblRuleMchtRelAdd;
	private TblRuleMchtRel tblRuleMchtRelUpd;
	private String ruleCode;
	private String msc1;
	private String srvId;
	private String firstMchtCdDtl;
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		if("add".equals(getMethod())) {			
			rspCode = add();			
		} else if("update".equals(getMethod())) {
			rspCode = update();
		}else if("delete".equals(getMethod())) {
			rspCode = delete();
		}if("addDtl".equals(getMethod())) {			
			rspCode = addDtl();			
		}if("deleteDtl".equals(getMethod())) {			
			rspCode = deleteDtl();			
		}
		return rspCode;
	}

	private String addDtl() {
		// TODO Auto-generated method stub
		TblRuleMchtRelPK tblRuleMchtRelPK=new TblRuleMchtRelPK();
		tblRuleMchtRelPK.setRuleCode(CommonFunction.fillString(ruleCode, ' ', 10, true));
		tblRuleMchtRelPK.setFirstMchtCd(CommonFunction.fillString(firstMchtCdDtl, ' ', 15, true));
		if(t10211BO.get(tblRuleMchtRelPK)!=null){
			return "该规则下的一级商户已经存在，请重新刷新查看！";
		}
		
		TblRuleMchtRel tblRuleMchtRel=new TblRuleMchtRel();
		tblRuleMchtRel.setTblRuleMchtRelPK(tblRuleMchtRelPK);
		tblRuleMchtRel.setMsc1(msc1);
		tblRuleMchtRel.setSrvId(srvId);
		tblRuleMchtRel.setMsc2(CommonsConstants.ROUTE_RULE_STOP);
		
		return t10211BO.save(tblRuleMchtRel);
	}
	
	private String deleteDtl() {
		// TODO Auto-generated method stub
		TblRuleMchtRelPK tblRuleMchtRelPK=new TblRuleMchtRelPK();
		tblRuleMchtRelPK.setRuleCode(CommonFunction.fillString(ruleCode, ' ', 10, true));
		tblRuleMchtRelPK.setFirstMchtCd(CommonFunction.fillString(firstMchtCdDtl, ' ', 15, true));
		if(t10211BO.get(tblRuleMchtRelPK)==null){
			return "该规则下的一级商户不存在，请重新刷新选择！";
		}
		return t10211BO.deleteDtl(tblRuleMchtRelPK);
	}
	
	
	private String delete() {
		// TODO Auto-generated method stub
		return t10211BO.deleteMain(ruleCode);
	}

	private String update() {
		// TODO Auto-generated method stub
		TblRuleMchtRelPK tblRuleMchtRelPK=new TblRuleMchtRelPK();
		tblRuleMchtRelPK.setRuleCode(ruleCode);
		tblRuleMchtRelUpd.setTblRuleMchtRelPK(tblRuleMchtRelPK);
		return t10211BO.updateMain(tblRuleMchtRelUpd);
	}

	private String add() {
		// TODO Auto-generated method stub
		TblRuleMchtRelPK tblRuleMchtRelPK=new TblRuleMchtRelPK();
		tblRuleMchtRelPK.setRuleCode(CommonFunction.fillString(tblRuleMchtRelAdd.getTblRuleMchtRelPK().getRuleCode(), ' ', 10, true));
		tblRuleMchtRelPK.setFirstMchtCd(CommonFunction.fillString(tblRuleMchtRelAdd.getTblRuleMchtRelPK().getFirstMchtCd(), ' ', 15, true));
		TblRuleMchtRel tblRuleMchtRel=t10211BO.get(tblRuleMchtRelPK);
		if(tblRuleMchtRel!=null){
			return "该规则商户映射信息已经存在，请刷新重新选择！";
		}
		tblRuleMchtRelAdd.setMsc2(CommonsConstants.ROUTE_RULE_STOP);
		tblRuleMchtRelAdd.setTblRuleMchtRelPK(tblRuleMchtRelPK);
		return t10211BO.addMain(tblRuleMchtRelAdd);
	}

	/**
	 * @return the tblRuleMchtRelAdd
	 */
	public TblRuleMchtRel getTblRuleMchtRelAdd() {
		return tblRuleMchtRelAdd;
	}

	/**
	 * @param tblRuleMchtRelAdd the tblRuleMchtRelAdd to set
	 */
	public void setTblRuleMchtRelAdd(TblRuleMchtRel tblRuleMchtRelAdd) {
		this.tblRuleMchtRelAdd = tblRuleMchtRelAdd;
	}

	/**
	 * @return the tblRuleMchtRelUpd
	 */
	public TblRuleMchtRel getTblRuleMchtRelUpd() {
		return tblRuleMchtRelUpd;
	}

	/**
	 * @param tblRuleMchtRelUpd the tblRuleMchtRelUpd to set
	 */
	public void setTblRuleMchtRelUpd(TblRuleMchtRel tblRuleMchtRelUpd) {
		this.tblRuleMchtRelUpd = tblRuleMchtRelUpd;
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

	public String getMsc1() {
		return msc1;
	}

	public void setMsc1(String msc1) {
		this.msc1 = msc1;
	}

	public String getSrvId() {
		return srvId;
	}

	public void setSrvId(String srvId) {
		this.srvId = srvId;
	}

	public String getFirstMchtCdDtl() {
		return firstMchtCdDtl;
	}

	public void setFirstMchtCdDtl(String firstMchtCdDtl) {
		this.firstMchtCdDtl = firstMchtCdDtl;
	}

	
}
