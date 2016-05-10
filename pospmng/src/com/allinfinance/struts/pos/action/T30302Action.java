package com.allinfinance.struts.pos.action;


import com.allinfinance.bo.term.T30302BO;
import com.allinfinance.po.TblTermManagementAppMain;
import com.allinfinance.po.TblTermManagementCheck;
import com.allinfinance.po.TblTermManagementCheckPK;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.JSONBean;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-15
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @version 1.0
 */
public class T30302Action extends BaseAction {


	private static final long serialVersionUID = -1673317644406998528L;
	private T30302BO t30302BO;
	private String appId;
	
	private String data;
	

	@Override
	protected String subExecute() throws Exception {
		
	
		if("update".equals(method)) {
				rspCode = update();
		}
		if("add".equals(method)) {
			rspCode = add();
		}
		return rspCode;

	}

	private String update(){
		TblTermManagementAppMain tblTermManagementAppMain=t30302BO.get(appId);
		if(tblTermManagementAppMain==null){
			return "该记录不存在，请重新刷新！";
		}
		if(!"0".equals(tblTermManagementAppMain.getStat())){
			return "只有未审核的申请单才能取消！";
		}
		
		tblTermManagementAppMain.setStat("2");
		return t30302BO.update(tblTermManagementAppMain);
	}
	
	private String add(){
		
		JSONBean jsonBean = new JSONBean();
		jsonBean.parseJSONArrayData(data);
		int len = jsonBean.getArray().size();
		
		String date=CommonFunction.getCurrentDate();
		String appIdAdd=operator.getOprBrhId().substring(0,2)+getAppNum(date);
		TblTermManagementAppMain tblTermManagementAppMain=new TblTermManagementAppMain();
		tblTermManagementAppMain.setAppId(appIdAdd);
		tblTermManagementAppMain.setAppDate(date);
		tblTermManagementAppMain.setAppOprId(operator.getOprId());
		tblTermManagementAppMain.setBrhId(operator.getOprBrhId());
		tblTermManagementAppMain.setStat("0");
		
		
		for(int i = 0; i < len; i++) {
			TblTermManagementCheck tblTermManagementCheck= new TblTermManagementCheck();
			TblTermManagementCheckPK tblTermManagementCheckPK=new TblTermManagementCheckPK();
			tblTermManagementCheckPK.setAppId(appIdAdd);
			tblTermManagementCheckPK.setSonAppId(getSonAppNum(date));
			tblTermManagementCheck.setId(tblTermManagementCheckPK);
			tblTermManagementCheck.setManufaturer(formatTypes(jsonBean.getJSONDataAt(i).getString("manufaturerS")));
			tblTermManagementCheck.setTerminalType(formatTypes(jsonBean.getJSONDataAt(i).getString("terminalTypeS")));
			tblTermManagementCheck.setTermType(jsonBean.getJSONDataAt(i).getString("termTypeS"));
			tblTermManagementCheck.setAmount(jsonBean.getJSONDataAt(i).getString("amountS"));
			tblTermManagementCheck.setMic(jsonBean.getJSONDataAt(i).getString("micS"));
			tblTermManagementCheck.setStat("0");
//			tblTermManagementCheck.setMisc2(operator.getOprBrhId());
		    t30302BO.save(tblTermManagementCheck);
			
		}
		
		
		return t30302BO.save(tblTermManagementAppMain);
	}

	public T30302BO getT30302BO() {
		return t30302BO;
	}

	public void setT30302BO(T30302BO t30302bo) {
		t30302BO = t30302bo;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}


	public  String getAppNum(String date){
		String sql = "select nvl(max(substr(app_id,9)),0) from tbl_term_management_app_main";
		
		int max=Integer.parseInt(CommonFunction.getCommQueryDAO().findBySQLQuery(sql.toString()).get(0).toString()) + 1;
		if(max > 999){
			max = 1;
		}
		return date.substring(2)+(CommonFunction.fillString(String.valueOf(max), '0', 3, false)).toString();
	}
	
	public  String getSonAppNum(String date){
		String sql = "select nvl(max(substr(son_app_id,9,12)),0) from tbl_term_management_check";
		int max=Integer.parseInt(CommonFunction.getCommQueryDAO().findBySQLQuery(sql.toString()).get(0).toString()) + 1;
		
		if(max > 999){
			max = 1;
		}
		
		return date+(CommonFunction.fillString(String.valueOf(max), '0', 4, false)).toString();
	}

	

	public String  formatTypes(String val) {
		if(val!=null && val.indexOf("-")!=-1){
			return 	val.substring(val.indexOf("-")+1).trim();
		}
		return val;
		
	};
}
