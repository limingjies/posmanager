package com.allinfinance.struts.pos.action;

import java.util.List;

import com.allinfinance.bo.term.T30302BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.term.TblTermManagementDAO;
import com.allinfinance.po.TblTermManagement;
import com.allinfinance.po.TblTermManagementAppMain;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;



@SuppressWarnings("serial")
public class T3030101Action extends BaseAction {

	T30302BO t30302BO = (T30302BO) ContextUtil.getBean("T30302BO");
	
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		if("complete".equals(method)) {
			log("请求完成");
			rspCode = complete();
		}
		
		if("repair".equals(method)) {
			rspCode = repair();
		}
		return rspCode;
	}






	private String appId;
	private String stat;
	
	
	private String termId;
	private String result;
	private TblTermManagementDAO tblTermManagementDAO;
	
	
	
	private String repair() {
		try{
			TblTermManagement tblTermManagement=tblTermManagementDAO.get(termId);
			if(tblTermManagement==null){
				return "记录不存在，请重新刷新！";
			}
			if(!"05".equals(tblTermManagement.getSignOprId())){
				return "该状态异常，请重新刷新！";
			}
			
			if("0".equals(result)){
//				tblTermManagement.setState("0");
				tblTermManagement.setSignOprId("00");
			}else if("1".equals(result)){
				tblTermManagement.setSignOprId("06");
//				tblTermManagement.setState("3");
			}
			
//			tblTermManagement.setSignOprId("");//02:申请报废  01：申请维修
			tblTermManagement.setSignDate("");
			
			
			tblTermManagementDAO.update(tblTermManagement);
			return Constants.SUCCESS_CODE;
		}catch (Exception e) {
			e.printStackTrace();
			return Constants.DATA_OPR_FAIL;
		}
	}

	//请求完成
	private String complete() {
		
		TblTermManagementAppMain app = new TblTermManagementAppMain();
			
		app = t30302BO.get(appId);
		
		String appId = app.getAppId();
		String brhId = app.getBrhId();
		
		String sql = "select (SELECT count(*) from tbl_term_management c where a.app_id = c.batch_no and a.manufaturer=c.manufaturer and a.terminal_type= c.terminal_type and a.term_type = c.term_type)||'' as count," +
				"nvl(acc_amount,0) as acc,son_app_id from tbl_term_management_app_main b left outer join tbl_term_management_check a on(a.app_id = b.app_id) where a.app_id = '"+ appId +"'";
		
		List<Object[]> listC = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		
		for(int i = 0 ;i < listC.size(); i++){
			String inAmount =(String) listC.get(i)[0];
			String accAmount =(String) listC.get(i)[1];
			String sonId =(String) listC.get(i)[2];
			int errAmount = Integer.valueOf(accAmount) - Integer.valueOf(inAmount);
			
			if(errAmount > 0){
				return "子单号:"+ sonId +"中机具录入数量不够，请录完后再完成该条申请单！";
			}
			if(errAmount < 0){
				return "子单号:"+ sonId +"中机具录入数量超过通过数量，无法完成该条申请单！";
			}
		}
		
		app.setStat("3");
		
		t30302BO.update(app);
		
		return Constants.SUCCESS_CODE;
	}



	public String getAppId() {
		return appId;
	}



	public void setAppId(String appId) {
		this.appId = appId;
	}



	public String getStat() {
		return stat;
	}



	public void setStat(String stat) {
		this.stat = stat;
	}



	public String getResult() {
		return result;
	}



	public void setResult(String result) {
		this.result = result;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public TblTermManagementDAO getTblTermManagementDAO() {
		return tblTermManagementDAO;
	}

	public void setTblTermManagementDAO(TblTermManagementDAO tblTermManagementDAO) {
		this.tblTermManagementDAO = tblTermManagementDAO;
	}
	
}