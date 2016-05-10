package com.allinfinance.struts.pos.action;

import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.term.TblTermManagementDAO;
import com.allinfinance.po.TblTermManagement;
import com.allinfinance.struts.system.action.BaseAction;

@SuppressWarnings("serial")
public class T30309Action extends BaseAction{

	private String termNo;
	private TblTermManagementDAO tblTermManagementDAO;
	
	protected String subExecute() throws Exception {
		
		if("pass".equals(method)) {
			rspCode = pass();
		}
		if("refuse".equals(method)) {
			rspCode = refuse();
		}
		return rspCode;
	}


	private String pass() {
		try{
			TblTermManagement tblTermManagement=tblTermManagementDAO.get(termNo);
			if(tblTermManagement==null){
				return "记录不存在，请重新刷新！";
			}
//			if(!"0".equals(tblTermManagement.getState())){
//				return "该状态异常，请重新刷新！";
//			}
			
			if("01".equals(tblTermManagement.getSignOprId())){
//				tblTermManagement.setState("2");
				tblTermManagement.setSignOprId("05");
			}else if("02".equals(tblTermManagement.getSignOprId())){
//				tblTermManagement.setState("3");
				tblTermManagement.setSignOprId("06");
			}else{
				return "该状态异常，请重新刷新！";
			}
			
//			tblTermManagement.setSignOprId("");//02:申请报废  01：申请维修
			tblTermManagement.setSignDate("");
			
			
			tblTermManagementDAO.update(tblTermManagement);
			return Constants.SUCCESS_CODE;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return Constants.DATA_OPR_FAIL;
		}
	}
	
	private String refuse() {
		try{
			TblTermManagement tblTermManagement=tblTermManagementDAO.get(termNo);
			if(tblTermManagement==null){
				return "记录不存在，请重新刷新！";
			}
//			if(!"0".equals(tblTermManagement.getState())){
//				return "该状态异常，请重新刷新！";
//			}
			
			if("01".equals(tblTermManagement.getSignOprId())){
				tblTermManagement.setSignOprId("03");//02:申请报废  01：申请维修
			}else if("02".equals(tblTermManagement.getSignOprId())){
				tblTermManagement.setSignOprId("04");//02:申请报废  01：申请维修

			}else{
				return "该状态异常，请重新刷新！";
			}
			
			tblTermManagement.setSignDate("");
			
			
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
