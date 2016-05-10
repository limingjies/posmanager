package com.allinfinance.struts.pos.action;

import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.term.TblTermManagementDAO;
import com.allinfinance.po.TblTermManagement;
import com.allinfinance.struts.system.action.BaseAction;

@SuppressWarnings("serial")
public class T30308Action extends BaseAction {

	private String termNo;
	private TblTermManagementDAO tblTermManagementDAO;
	
	protected String subExecute() throws Exception {
		
		if("repair".equals(method)) {
			rspCode = repair();
		}
		if("broken".equals(method)) {
			rspCode = broken();
		}
		if("update".equals(method)) {
			rspCode = update();
		}
		return rspCode;
	}

	private String update() {
		try{
			TblTermManagement tblTermManagement=tblTermManagementDAO.get(termNo);
			if(tblTermManagement==null){
				return "记录不存在，请重新刷新！";
			}
//			if(!"0".equals(tblTermManagement.getState())){
//				return "该状态异常，请重新刷新！";
//			}
			
//			if("95599".equals(operator.getOprBrhId())){
//				tblTermManagement.setState("3");
//				tblTermManagement.setSignOprId("");
//				tblTermManagement.setSignDate("");
//			}else{
//				tblTermManagement.setSignOprId("02");//02:申请报废  01：申请维修
//				tblTermManagement.setSignDate(operator.getOprBrhId());
//			}
			
			tblTermManagement.setSignOprId("00");
			tblTermManagement.setSignDate("");
			tblTermManagementDAO.update(tblTermManagement);
			return Constants.SUCCESS_CODE;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return Constants.DATA_OPR_FAIL;
		}
	}

	private String broken() {
		try{
			TblTermManagement tblTermManagement=tblTermManagementDAO.get(termNo);
			if(tblTermManagement==null){
				return "记录不存在，请重新刷新！";
			}
//			if(!"0".equals(tblTermManagement.getState())){
//				return "该状态异常，请重新刷新！";
//			}
			
			if("95599".equals(operator.getOprBrhId())){
//				tblTermManagement.setState("06");
				tblTermManagement.setSignOprId("06");
				tblTermManagement.setSignDate("");
			}else{
				tblTermManagement.setSignOprId("02");//02:申请报废  01：申请维修
				tblTermManagement.setSignDate(operator.getOprBrhId());
			}
			tblTermManagementDAO.update(tblTermManagement);
			return Constants.SUCCESS_CODE;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return Constants.DATA_OPR_FAIL;
		}
	}

	
	private String repair() {
		try{
			TblTermManagement tblTermManagement=tblTermManagementDAO.get(termNo);
			if(tblTermManagement==null){
				return "记录不存在，请重新刷新！";
			}
//			if(!"0".equals(tblTermManagement.getState())){
//				return "该状态异常，请重新刷新！";
//			}
			
			if("95599".equals(operator.getOprBrhId())){
//				tblTermManagement.setState("2");
				tblTermManagement.setSignOprId("05");
				tblTermManagement.setSignDate("");
			}else{
				tblTermManagement.setSignOprId("01");//02:申请报废  01：申请维修
				tblTermManagement.setSignDate(operator.getOprBrhId());
			}
			tblTermManagementDAO.update(tblTermManagement);
			return Constants.SUCCESS_CODE;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return Constants.DATA_OPR_FAIL;
		}
	}

	public String getTermNo() {
		return termNo;
	}

	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	public TblTermManagementDAO getTblTermManagementDAO() {
		return tblTermManagementDAO;
	}

	public void setTblTermManagementDAO(TblTermManagementDAO tblTermManagementDAO) {
		this.tblTermManagementDAO = tblTermManagementDAO;
	}
	
	
}
