package com.allinfinance.struts.agentpay.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.allinfinance.bo.agentpay.T90202BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.ErrorCode;
import com.allinfinance.po.agentpay.TblBankInfo;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.BeanUtils;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;


@SuppressWarnings("serial")
public class T90202Action extends BaseAction {
	
	
	T90202BO t90202BO = (T90202BO) ContextUtil.getBean("T90202BO");
	/* (non-Javadoc)
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		
		if("add".equals(method)) {
			log("新增代收付银行代码信息。操作员编号：" + operator.getOprId());
			rspCode = add();
		} else if("delete".equals(method)) {
			log("删除代收付银行代码信息。操作员编号：" + operator.getOprId());
			rspCode = delete();
		} else if("update".equals(method)) {
			log("同步代收付银行代码信息。操作员编号：" + operator.getOprId());
			rspCode = update();
		}
		
		return rspCode;
	}
	
	/**
	 * add city code
	 * @return
	 */
	private String add() {
		
		if(t90202BO.getBankInfo(CommonFunction.fillString(bankNo, ' ', 14, true)) !=null){
			return ErrorCode.T90202_01;
		}
		TblBankInfo tblBankInfo = new TblBankInfo();
		tblBankInfo.setBankNo(bankNo);
		tblBankInfo.setBankName(bankName);
		t90202BO.add(tblBankInfo);
		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * delete city code
	 * @return
	 */
	private String delete() {
		
		if(t90202BO.getBankInfo(CommonFunction.fillString(bankNo, ' ', 14, true)) == null) {
			return "没有找到要删除的银行代码信息";
		}
		
		t90202BO.delete(CommonFunction.fillString(bankNo, ' ', 14, true));
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * update city code
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		jsonBean.parseJSONArrayData(getBankCodeList());
		
		int len = jsonBean.getArray().size();
		
		TblBankInfo tblBankInfo = null;
		
		List<TblBankInfo> bankInfoList = new ArrayList<TblBankInfo>(len);
		
		for(int i = 0; i < len; i++) {			
			tblBankInfo = new TblBankInfo();			
			jsonBean.setObject(jsonBean.getJSONDataAt(i));			
			BeanUtils.setObjectWithPropertiesValue(tblBankInfo, jsonBean, false);
			tblBankInfo.setBankNo(CommonFunction.fillString(tblBankInfo.getBankNo(), ' ', 14, true));
			bankInfoList.add(tblBankInfo);
		}
		
		t90202BO.update(bankInfoList);
		
		return Constants.SUCCESS_CODE;
	}
	
	private String bankNo;
	private String bankName;
    private String bankCodeList;
	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCodeList() {
		return bankCodeList;
	}

	public void setBankCodeList(String bankCodeList) {
		this.bankCodeList = bankCodeList;
	}

}
