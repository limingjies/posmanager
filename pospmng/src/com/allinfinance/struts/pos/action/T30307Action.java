package com.allinfinance.struts.pos.action;

import java.util.List;

import com.allinfinance.bo.term.T3010BO;
import com.allinfinance.bo.term.TermManagementBO;
import com.allinfinance.common.Constants;
import com.allinfinance.po.TblTermInf;
import com.allinfinance.po.TblTermInfTmp;
import com.allinfinance.po.TblTermManagement;
import com.allinfinance.struts.pos.TblTermManagementConstants;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;

public class T30307Action extends BaseAction {
	
	private static final long serialVersionUID = -7458083560605416590L;
	private TermManagementBO t3030BO;
	private T3010BO t3010BO;
	private String termId;
	private String termIdId;
	private String pinId;
	
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

	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		if("unbindAll".equals(method)) {
			log("解绑全部");
			rspCode = unbindAll();
		}else if("unbindMana".equals(method)) {
			log("解绑机具");
			rspCode = unbindMana();
		} else if("unbindPin".equals(method)) {
			log("解绑密码键盘");
			rspCode = unbindPin();
		} 
		return rspCode;
	}

	protected String unbindAll() throws Exception {
		TblTermManagement management = t3030BO.getTerminal(termIdId);
		TblTermManagement pinPad = t3030BO.getTerminal(pinId);
		
		String sql="select term_id from tbl_term_inf where term_id_id = '"+ termIdId +"'";
		List<String> list=commQueryDAO.findBySQLQuery(sql);
		if(list.isEmpty()){
			return TblTermManagementConstants.T30307_02;
		}
		
		String sqlPin="select term_id from tbl_term_inf where equip_inv_nm = '"+ pinId +"'";
		List<String> listPin=commQueryDAO.findBySQLQuery(sqlPin);
		if(listPin.isEmpty()){
			return TblTermManagementConstants.T30307_03;
		}
		
	
		String termId=list.get(0);
		TblTermInf tblTermInf = t3010BO.getTermInfo(CommonFunction.fillString(termId, ' ', 12, true));
		TblTermInfTmp tblTermInfTmp = t3010BO.get(tblTermInf.getId(),tblTermInf.getRecCrtTs());
		
		if(tblTermInf == null)
			return TblTermManagementConstants.T30305_01;
		
		if(management == null)
			return TblTermManagementConstants.T30305_02;
		
		if(pinPad == null)
			return TblTermManagementConstants.T30305_22;
		
		if(tblTermInf.getTermIdId().trim().equals(Constants.DEFAULT))
			return TblTermManagementConstants.T30307_01;
		
		if(management.getState().equals(TblTermManagementConstants.TERM_STATE_RECI))//作废提示
			return TblTermManagementConstants.T30305_05;
		
		if(pinPad.getState().equals(TblTermManagementConstants.TERM_STATE_RECI))//作废提示
			return TblTermManagementConstants.T30305_52;

		if(!management.getState().equals(TblTermManagementConstants.TERM_STATE_END)
				&&management.getState().equalsIgnoreCase("1"))
			t3030BO.unbindTermInfo(management,operator.getOprId(),tblTermInf,tblTermInfTmp);
		else
			return TblTermManagementConstants.T30305_01;
		
		if(!pinPad.getState().equals(TblTermManagementConstants.TERM_STATE_END)
				&&pinPad.getState().equalsIgnoreCase("1"))
			t3030BO.unbindTermInfo(pinPad,operator.getOprId(),tblTermInf,tblTermInfTmp);
		else
			return TblTermManagementConstants.T30305_01;
		
		return Constants.SUCCESS_CODE;
	}
	
	protected String unbindMana() throws Exception {
		TblTermManagement management = t3030BO.getTerminal(termIdId);
		
		String sql="select term_id from tbl_term_inf where term_id_id = '"+ termIdId +"'";
		List<String> list=commQueryDAO.findBySQLQuery(sql);
		if(list.isEmpty()){
			return TblTermManagementConstants.T30307_02;
		}		
	
		String termId=list.get(0);
		
		TblTermInf tblTermInf = t3010BO.getTermInfo(CommonFunction.fillString(termId, ' ', 12, true));
		TblTermInfTmp tblTermInfTmp = t3010BO.get(tblTermInf.getId(),tblTermInf.getRecCrtTs());
		
		if(tblTermInf == null)
			return TblTermManagementConstants.T30305_01;
		
		if(management == null)
			return TblTermManagementConstants.T30305_02;
		
		if(tblTermInf.getTermIdId().trim().equals(Constants.DEFAULT))
			return TblTermManagementConstants.T30307_01;
		
		if(management.getState().equals(TblTermManagementConstants.TERM_STATE_RECI))//作废提示
			return TblTermManagementConstants.T30305_05;

		if(!management.getState().equals(TblTermManagementConstants.TERM_STATE_END)
				&&management.getState().equalsIgnoreCase("1"))
			t3030BO.unbindTermInfo(management,operator.getOprId(),tblTermInf,tblTermInfTmp);
		else
			return TblTermManagementConstants.T30305_01;
		
		return Constants.SUCCESS_CODE;
	}

	protected String unbindPin() throws Exception {
		TblTermManagement pinPad = t3030BO.getTerminal(pinId);
		
		String sqlPin="select term_id from tbl_term_inf where equip_inv_nm = '"+ pinId +"'";
		List<String> listPin=commQueryDAO.findBySQLQuery(sqlPin);
		if(listPin.isEmpty()){
			return TblTermManagementConstants.T30307_03;
		}
		
		String termId=listPin.get(0);
		
		TblTermInf tblTermInf = t3010BO.getTermInfo(CommonFunction.fillString(termId, ' ', 12, true));
		TblTermInfTmp tblTermInfTmp = t3010BO.get(tblTermInf.getId(),tblTermInf.getRecCrtTs());
		
		if(tblTermInf == null)
			return TblTermManagementConstants.T30305_01;
		
		if(pinPad == null)
			return TblTermManagementConstants.T30305_22;
		
		if(pinPad.getState().equals(TblTermManagementConstants.TERM_STATE_RECI))//作废提示
			return TblTermManagementConstants.T30305_52;
		
		if(!pinPad.getState().equals(TblTermManagementConstants.TERM_STATE_END)
				&&pinPad.getState().equalsIgnoreCase("1"))
			t3030BO.unbindTermInfo(pinPad,operator.getOprId(),tblTermInf,tblTermInfTmp);
		else
			return TblTermManagementConstants.T30305_01;
		
		return Constants.SUCCESS_CODE;
	}
}
