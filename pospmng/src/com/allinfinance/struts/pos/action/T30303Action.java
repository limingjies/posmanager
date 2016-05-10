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
@SuppressWarnings("serial")
public class T30303Action extends BaseAction {

	
	private T30302BO t30302BO;
	private String data;
	
	protected String subExecute() throws Exception {
		
		if("pass".equals(method)) {
			rspCode = pass();
		}
		return rspCode;
	}

	private String pass(){
		
		String date=CommonFunction.getCurrentDate();
		JSONBean jsonBean = new JSONBean();
		jsonBean.parseJSONArrayData(data);
		int len = jsonBean.getArray().size();
		
		
		String appId=jsonBean.getJSONDataAt(0).getString("appIdS");
		TblTermManagementAppMain tblTermManagementAppMain=t30302BO.get(appId);
		if(tblTermManagementAppMain==null){
			return "该记录不存在，请重新刷新！";
		}
		if(!"0".equals(tblTermManagementAppMain.getStat())){
			return "该记录状态异常，请重新刷新！";
		}
		tblTermManagementAppMain.setBackDate(date);
		tblTermManagementAppMain.setBackOprId(operator.getOprId());
		tblTermManagementAppMain.setStat("1");
		
		
		for(int i = 0; i < len; i++) {
			TblTermManagementCheckPK tblTermManagementCheckPK=new TblTermManagementCheckPK();
			tblTermManagementCheckPK.setAppId(appId);
			tblTermManagementCheckPK.setSonAppId(jsonBean.getJSONDataAt(i).getString("sonAppIdS"));
			TblTermManagementCheck tblTermManagementCheck= t30302BO.get(tblTermManagementCheckPK);
			
			String state=jsonBean.getJSONDataAt(i).getString("statS");
			if("1".equals(state)){
				tblTermManagementCheck.setAccAmount(tblTermManagementCheck.getAmount());
			}else if("2".equals(state)){
				tblTermManagementCheck.setAccAmount(jsonBean.getJSONDataAt(i).getString("accAmountS"));
			}
			tblTermManagementCheck.setStat(state);
			tblTermManagementCheck.setMic(jsonBean.getJSONDataAt(i).getString("micS"));
			
//			tblTermManagementCheck.setMisc2(operator.getOprBrhId());
		    t30302BO.update(tblTermManagementCheck);
			
		}
		
		return t30302BO.update(tblTermManagementAppMain);
		
		
	}
	
	public T30302BO getT30302BO() {
		return t30302BO;
	}

	public void setT30302BO(T30302BO t30302bo) {
		t30302BO = t30302bo;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	
	
	
}
