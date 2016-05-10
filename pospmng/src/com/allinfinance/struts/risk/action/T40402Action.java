package com.allinfinance.struts.risk.action;

import com.allinfinance.bo.risk.T40401BO;
import com.allinfinance.common.RiskConstants;
import com.allinfinance.common.TblMchntInfoConstants;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.risk.TblWhiteMchtApply;
import com.allinfinance.po.risk.TblWhiteMchtCheck;
import com.allinfinance.po.risk.TblWhiteMchtCheckPK;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T40402Action extends BaseAction{

	private T40401BO t40401BO = (T40401BO) ContextUtil.getBean("T40401BO");
	
	TblWhiteMchtApply tblWhiteMchtApply;
	TblWhiteMchtCheck tblWhiteMchtCheck = new TblWhiteMchtCheck();
	TblWhiteMchtCheckPK tblWhiteMchtCheckPK = new TblWhiteMchtCheckPK();
	
	@Override
	protected String subExecute() throws Exception {
		TblMchtBaseInf tblMchtBaseInf = t40401BO.load(mchtNo);
		tblWhiteMchtApply = t40401BO.get(mchtNo);
		// 判断此白名单商户的状态是否正常
		if(!(TblMchntInfoConstants.MCHNT_ST_OK).equals(tblMchtBaseInf.getMchtStatus())){
			return "此商户状态不正常，不能通过审核！";
		}
		// 判断当前操作员是否是初审操作员
		if((operator.getOprId()).equals(tblWhiteMchtApply.getCheckOpr())){
			return "操作员不能同时初审和终审！";
		}

		tblWhiteMchtApply.setCheckOpr(operator.getOprId());
		tblWhiteMchtApply.setCheckTime(CommonFunction.getCurrentDateTime());
		tblWhiteMchtCheckPK.setMchtNo(mchtNo);
		tblWhiteMchtCheckPK.setCheckTime(CommonFunction.getCurrentDateTime());
		tblWhiteMchtCheck.setId(tblWhiteMchtCheckPK);
		tblWhiteMchtCheck.setCheckOpr(operator.getOprId());
		tblWhiteMchtCheck.setDayAveAmt(tblWhiteMchtApply.getDayAveAmt());
		tblWhiteMchtCheck.setMonAveAmt(tblWhiteMchtApply.getMonAveAmt());
		tblWhiteMchtCheck.setSigMinAmt(tblWhiteMchtApply.getSigMinAmt());
		tblWhiteMchtCheck.setSigMaxAmt(tblWhiteMchtApply.getSigMaxAmt());
		tblWhiteMchtCheck.setServDisp(tblWhiteMchtApply.getServDisp());
		tblWhiteMchtCheck.setApplyReason(tblWhiteMchtApply.getApplyReason());
		tblWhiteMchtCheck.setApplyOpr(tblWhiteMchtApply.getApplyOpr());
		tblWhiteMchtCheck.setApplyTime(tblWhiteMchtApply.getApplyTime());
		// TODO Auto-generated method stub
		if("accept".equals(getMethod())) {
			rspCode = accept();			
		} else if("refuse".equals(getMethod())) {
			rspCode = refuse();
		}
		return rspCode;
	}

	private String accept() {
		tblWhiteMchtApply.setCheckStatus(RiskConstants.LAST_CHECK_T);
		tblWhiteMchtCheck.setCheckStatus(RiskConstants.LAST_CHECK_T);
		tblWhiteMchtCheck.setMchtCaseDesp(mchtCaseDesp);
		tblWhiteMchtCheck.setRefuseReason(refuseReason);
		return t40401BO.check(tblWhiteMchtCheck, tblWhiteMchtApply);
	}
	
	private String refuse() {
		tblWhiteMchtApply.setCheckStatus(RiskConstants.LAST_CHECK_F);
		tblWhiteMchtCheck.setCheckStatus(RiskConstants.LAST_CHECK_F);
		tblWhiteMchtCheck.setMchtCaseDesp(mchtCaseDesp);
		tblWhiteMchtCheck.setRefuseReason(refuseReason);
		return t40401BO.check(tblWhiteMchtCheck, tblWhiteMchtApply);
	}
	
	private String mchtNo;
	private String mchtCaseDesp;
	private String refuseReason;
	/**
	 * @return the mchtNo
	 */
	public String getMchtNo() {
		return mchtNo;
	}

	/**
	 * @param mchtNo the mchtNo to set
	 */
	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	/**
	 * @return the mchtCaseDesp
	 */
	public String getMchtCaseDesp() {
		return mchtCaseDesp;
	}

	/**
	 * @param mchtCaseDesp the mchtCaseDesp to set
	 */
	public void setMchtCaseDesp(String mchtCaseDesp) {
		this.mchtCaseDesp = mchtCaseDesp;
	}

	/**
	 * @return the refuseReason
	 */
	public String getRefuseReason() {
		return refuseReason;
	}

	/**
	 * @param refuseReason the refuseReason to set
	 */
	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}
}