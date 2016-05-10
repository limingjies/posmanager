/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-7-17       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 allinfinance, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai allinfinance Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with allinfinance.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.allinfinance.struts.base.action;

import java.lang.reflect.InvocationTargetException;

import com.allinfinance.bo.base.T10203BO;
import com.allinfinance.common.Constants;
import com.allinfinance.po.TblTxnName;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.BeanUtils;
import com.allinfinance.system.util.ContextUtil;

/**
 * Title:地区码维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-17
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T10203Action extends BaseAction {
	
	
	T10203BO t10203BO = (T10203BO) ContextUtil.getBean("T10203BO");
	
	/* (non-Javadoc)
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		
		if("add".equals(method)) {
			log("新增交易类型信息。操作员编号：" + operator.getOprId());
			rspCode = add();
		} else if("delete".equals(method)) {
			log("删除交易类型信息。操作员编号：" + operator.getOprId());
			rspCode = delete();
		} else if("update".equals(method)) {
			log("同步交易类型信息。操作员编号：" + operator.getOprId());
			rspCode = update();
		}
		
		return rspCode;
	}
	
	/**
	 * add city code
	 * @return
	 */
	private String add() {
		TblTxnName tblTxnName = new TblTxnName();
		tblTxnName.setId(id);
		tblTxnName.setTxnName(txnName);
		tblTxnName.setTxnDsp(txnDsp);
		tblTxnName.setDcFlag(dcFlag);
		t10203BO.add(tblTxnName);		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * delete city code
	 * @return
	 */
	private String delete() {
		
		
		for(int i = id.length(); i<4; i++) {
			
			id = id + " ";
		}
		
		if(t10203BO.get(id) == null) {
			return "没有找到要删除的交易类型信息";
		}		
		t10203BO.delete(id);		
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
		
		jsonBean.parseJSONArrayData(this.getTxnNameList());
		
		int len = jsonBean.getArray().size();
		
//		TblTxnName tblTxnName = null;
		
//		List<TblTxnName> tblTxnNameList = new ArrayList<TblTxnName>(len);
		for(int i = 0; i < len; i++) {
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			TblTxnName tblTxnName = new TblTxnName();
			BeanUtils.setObjectWithPropertiesValue(tblTxnName,jsonBean,true);
//			bthErrRegistTxn.setErrStatus("1");
			t10203BO.update(tblTxnName);
		}
		
//		for(int i = 0; i < len; i++) {			
//			tblTxnName = t10203BO.get(jsonBean.getJSONDataAt(i).getString("id"));
//			if(tblTxnName != null){
//				tblTxnName.setTxnName(jsonBean.getJSONDataAt(i).getString("txnName"));
//				tblTxnName.setTxnDsp(jsonBean.getJSONDataAt(i).getString("txnDsp"));
//				tblTxnName.setDcFlag(jsonBean.getJSONDataAt(i).getString("dcFlag"));
//				
//			}
//			tblTxnNameList.add(tblTxnName);
//		}
//		t10203BO.update(tblTxnName);
		
		return Constants.SUCCESS_CODE;
	}
	
	private String id;
	
	private String txnName;
	
	private String txnDsp;
	
	private String dcFlag;
	
	private String txnNameList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTxnName() {
		return txnName;
	}

	public void setTxnName(String txnName) {
		this.txnName = txnName;
	}

	public String getTxnDsp() {
		return txnDsp;
	}

	public void setTxnDsp(String txnDsp) {
		this.txnDsp = txnDsp;
	}

	public String getDcFlag() {
		return dcFlag;
	}

	public void setDcFlag(String dcFlag) {
		this.dcFlag = dcFlag;
	}

	public String getTxnNameList() {
		return txnNameList;
	}

	public void setTxnNameList(String txnNameList) {
		this.txnNameList = txnNameList;
	}		
}
