package com.allinfinance.struts.mchnt.action;

import java.util.List;

import com.allinfinance.bo.mchnt.T20108BO;
import com.allinfinance.common.Constants;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T20108Action extends BaseAction{

	private T20108BO t20108BO = (T20108BO) ContextUtil.getBean("T20108BO");

	@Override
	protected String subExecute() throws Exception {
			if("openAcct".equals(getMethod())) {			
					rspCode = openAcct();			
			}
		return rspCode;
	}


	private String openAcct() {
		 List<TblMchtBaseInf> mchtList=t20108BO.getAll();
		 if(mchtList==null||mchtList.size()==0){
			 return "暂无存量商户！";
		 }
		 int succ=0;
		 int fail=0;
		 for (TblMchtBaseInf tblMchtBaseInf : mchtList) {
			 rspCode=t20108BO.openAcct(tblMchtBaseInf);
			 if(!Constants.SUCCESS_CODE.equals(rspCode)){
				 fail++;
			 }else{
				 succ++;
			 }
		}
		 return Constants.SUCCESS_CODE_CUSTOMIZE+"总开户["+mchtList.size()+"],成功["+succ+"],失败["+fail+"]";
		
	}


	
}
