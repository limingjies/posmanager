package com.allinfinance.struts.daytrade.action;

import java.util.Date;

import com.allinfinance.bo.daytrade.T70101BO;
import com.allinfinance.po.daytrade.MbWithdrawFee;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.GenerateNextId;

/**
 *  T70101Action.java
 *     
 *  Project: T+0  
 *
 *  Description:
 *  =========================================================================
 *
 * 如果有任何对代码的修改,请按下面的格式注明修改的内容.
 *    序号          时间          作者                 修改内容 
 * ==========  =============  =============  =============================
 *    1.       2015年5月21日	  徐鹏飞          created this class.
 *    
 *  
 *  Copyright Notice:
 *   Copyright (c) 2009-2015   Allinpay Financial Services Co., Ltd. 
 *    All rights reserved.
 *
 *   This software is the confidential and proprietary information of
 *   allinfinance.com  Inc. ('Confidential Information').  You shall not
 *   disclose such Confidential Information and shall use it only in
 *   accordance with the terms of the license agreement you entered
 *   into with Allinpay Financial.
 *
 *  Warning:
 *  =========================================================================
 *  
 */
public class T70101Action extends BaseAction{
	
	private static final long serialVersionUID = 1L;

	private T70101BO t70101BO = (T70101BO) ContextUtil.getBean("T70101BO");

	private MbWithdrawFee mbWithdrawFeeAdd;
	private MbWithdrawFee mbWithdrawFeeUpd;
	private String rowId;
	
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		if("add".equals(getMethod())) {			
			rspCode = add();			
		}else if("update".equals(getMethod())) {
			rspCode = update();
		}else if("delete".equals(getMethod())) {
			rspCode = delete();
		}else if("switch".equals(getMethod())) {
			rspCode = change();
		}
		return rspCode;
	}

	private String add() {
		// TODO Auto-generated method stub
		mbWithdrawFeeAdd.setRowId(Long.parseLong(GenerateNextId.getFeeRowId()));
		mbWithdrawFeeAdd.setStatus("1");
		mbWithdrawFeeAdd.setCreateDate(new Date());
		mbWithdrawFeeAdd.setCreateBy(operator.getOprId());
		mbWithdrawFeeAdd.setUpdateDate(new Date());
		mbWithdrawFeeAdd.setUpdateBy(operator.getOprId());
		return t70101BO.add(mbWithdrawFeeAdd);
	}
	
	private String update() {
		// TODO Auto-generated method stub
		MbWithdrawFee mbWithdrawFee = t70101BO.get(Long.parseLong(rowId));
		if(mbWithdrawFee != null){
			mbWithdrawFee.setFeeType(mbWithdrawFeeUpd.getFeeType());
			mbWithdrawFee.setRate(mbWithdrawFeeUpd.getRate());
			mbWithdrawFee.setMaxFee(mbWithdrawFeeUpd.getMaxFee());
			mbWithdrawFee.setMinFee(mbWithdrawFeeUpd.getMinFee());
			mbWithdrawFee.setUpdateDate(new Date());
			mbWithdrawFee.setUpdateBy(operator.getOprId());
			return t70101BO.update(mbWithdrawFee);
		}
		return "该条提现计费信息不存在，请刷新！";
	}

	private String delete() {
		// TODO Auto-generated method stub
		MbWithdrawFee mbWithdrawFee = t70101BO.get(Long.parseLong(rowId));
		if(mbWithdrawFee != null){
			return t70101BO.delete(Long.parseLong(rowId));
		}
		return "该条提现计费信息不存在，请刷新！";
	}

	private String change() {
		// TODO Auto-generated method stub
		MbWithdrawFee mbWithdrawFee = t70101BO.get(Long.parseLong(rowId));
		if(mbWithdrawFee == null){
			return "该条提现计费信息不存在，请刷新！";
		}
		if("0".equals(mbWithdrawFee.getStatus())){
			mbWithdrawFee.setStatus("1");
			return t70101BO.update(mbWithdrawFee);
		}else{
			
			if(t70101BO.getDataList(mbWithdrawFee.getMerchantId()).isEmpty()){
				mbWithdrawFee.setStatus("0");
				return t70101BO.update(mbWithdrawFee);
			}
			return "该T+0商户存在已启用的提现计费信息！";
		}
	}

	/**
	 * @return the mbWithdrawFeeAdd
	 */
	public MbWithdrawFee getMbWithdrawFeeAdd() {
		return mbWithdrawFeeAdd;
	}

	/**
	 * @param mbWithdrawFeeAdd the mbWithdrawFeeAdd to set
	 */
	public void setMbWithdrawFeeAdd(MbWithdrawFee mbWithdrawFeeAdd) {
		this.mbWithdrawFeeAdd = mbWithdrawFeeAdd;
	}

	/**
	 * @return the mbWithdrawFeeUpd
	 */
	public MbWithdrawFee getMbWithdrawFeeUpd() {
		return mbWithdrawFeeUpd;
	}

	/**
	 * @param mbWithdrawFeeUpd the mbWithdrawFeeUpd to set
	 */
	public void setMbWithdrawFeeUpd(MbWithdrawFee mbWithdrawFeeUpd) {
		this.mbWithdrawFeeUpd = mbWithdrawFeeUpd;
	}

	/**
	 * @return the rowId
	 */
	public String getRowId() {
		return rowId;
	}

	/**
	 * @param rowId the rowId to set
	 */
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
}
