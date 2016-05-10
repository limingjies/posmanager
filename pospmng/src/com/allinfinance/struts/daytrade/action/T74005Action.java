/**
 *  T74005Action.java
 *     
 *  Project: T+0  
 *
 *  Description:
 *  =========================================================================
 *
 * 如果有任何对代码的修改,请按下面的格式注明修改的内容.
 *    序号          时间          作者                 修改内容 
 * ==========  =============  =============  =============================
 *    1.       2015年7月14日	  唐柳玉          created this class.
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
package com.allinfinance.struts.daytrade.action;

import com.allinfinance.bo.daytrade.T74005BO;
import com.allinfinance.po.daytrade.TblWithdrawErr;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

public class T74005Action extends BaseAction {

	private static final long serialVersionUID = 1L;
	private T74005BO t74005BO = (T74005BO) ContextUtil.getBean("T74005BO");
	private TblWithdrawErr tblWithdrawErr;
	private String transCode;

	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		if ("update".equals(getMethod())) {//
			rspCode = update();
		}		
		return rspCode;
	}

	private String update() {
		if(!isNotEmpty(tblWithdrawErr.getMcht_withdraw_dt())){
			return "原商服提现时间为空,不能冲账!";
		}
		if(!isNotEmpty(tblWithdrawErr.getMcht_withdraw_dt())){
			return "原商服提现流水为空,不能冲账!";
		}
	  String rq=t74005BO.buildFrontMsg("044005",tblWithdrawErr);
		return rq;
	}

	public TblWithdrawErr getTblWithdrawErr() {
		return tblWithdrawErr;
	}

	public void setTblWithdrawErr(TblWithdrawErr tblWithdrawErr) {
		this.tblWithdrawErr = tblWithdrawErr;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	
	

}
