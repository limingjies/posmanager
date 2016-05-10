package com.allinfinance.struts.pos.action;

import com.allinfinance.bo.term.T3010BO;
import com.allinfinance.bo.term.TermManagementBO;
import com.allinfinance.common.Constants;
import com.allinfinance.po.TblTermInf;
import com.allinfinance.po.TblTermInfTmp;
import com.allinfinance.po.TblTermManagement;
import com.allinfinance.struts.pos.TblTermManagementConstants;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;


public class T30305Action extends BaseAction {
	
	private static final long serialVersionUID = -7458083560605416590L;
	private TermManagementBO t3030BO;
	private T3010BO t3010BO;
	private String termId;
	private String termIdId;
	private String pinId;
	private String pinFlag;
	
	/**
	 * @param t3030bo the t3030BO to set
	 */
	public void setT3030BO(TermManagementBO t3030bo) {
		t3030BO = t3030bo;
	}

	/**
	 * @param t3010bo the t3010BO to set
	 */
	public void setT3010BO(T3010BO t3010bo) {
		t3010BO = t3010bo;
	}

	/**
	 * @return the termId
	 */
	public String getTermId() {
		return termId;
	}

	/**
	 * @param termId the termId to set
	 */
	public void setTermId(String termId) {
		this.termId = termId;
	}

	/**
	 * @return the termIdId
	 */
	public String getTermIdId() {
		return termIdId;
	}

	/**
	 * @param termIdId the termIdId to set
	 */
	public void setTermIdId(String termIdId) {
		this.termIdId = termIdId;
	}

	public String getPinId() {
		return pinId;
	}

	public void setPinId(String pinId) {
		this.pinId = pinId;
	}

	public String getPinFlag() {
		return pinFlag;
	}

	public void setPinFlag(String pinFlag) {
		this.pinFlag = pinFlag;
	}

	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		if("managementBind".equals(method)) {
			log("绑定机具");
			rspCode = managementBind();
		}
		else if("pinPadBind".equals(method)) {
			log("绑定机具绑定密码键盘");
			rspCode = pinPadBind();
		} 
		return rspCode;
	}
	
	protected String managementBind() throws Exception {
		TblTermInf tblTermInf = t3010BO.getTermInfo(CommonFunction.fillString(termId, ' ', 12, true));
		TblTermInfTmp tblTermInfTmp = t3010BO.get(tblTermInf.getId(),tblTermInf.getRecCrtTs());
		TblTermManagement tblTermManagement = t3030BO.getTerminal(termIdId);
		if(tblTermInf == null)
			return TblTermManagementConstants.T30305_01;
		if(tblTermManagement == null)
			return TblTermManagementConstants.T30305_02;
		if(!tblTermInf.getTermIdId().trim().equals(Constants.DEFAULT))
			return TblTermManagementConstants.T30305_04;
		if(tblTermManagement.getState().equals(TblTermManagementConstants.TERM_STATE_RECI))
			return TblTermManagementConstants.T30305_05;
		
		
		if(!tblTermManagement.getState().equals(TblTermManagementConstants.TERM_STATE_END))
			t3030BO.bindTermInfo(tblTermManagement,operator.getOprId(),tblTermInf,tblTermInfTmp);
		else
			return TblTermManagementConstants.T30305_03;
		return Constants.SUCCESS_CODE;
	}
	
	protected String pinPadBind() throws Exception {
		TblTermInf tblTermInf = t3010BO.getTermInfo(CommonFunction.fillString(termId, ' ', 12, true));
		TblTermInfTmp tblTermInfTmp = t3010BO.get(tblTermInf.getId(),tblTermInf.getRecCrtTs());
		TblTermManagement tblTermManagement = t3030BO.getTerminal(pinId);
		if(tblTermInf == null)
			return TblTermManagementConstants.T30305_01;
		if(tblTermManagement == null)
			return TblTermManagementConstants.T30305_22;
		if(tblTermManagement.getState().equals(TblTermManagementConstants.TERM_STATE_RECI))
			return TblTermManagementConstants.T30305_52;
		
		
		if(!tblTermManagement.getState().equals(TblTermManagementConstants.TERM_STATE_END))
			t3030BO.bindTermInfo(tblTermManagement,operator.getOprId(),tblTermInf,tblTermInfTmp);
		else
			return TblTermManagementConstants.T30305_32;
		return Constants.SUCCESS_CODE;
	}

}
