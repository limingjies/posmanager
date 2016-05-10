/**
 *  T73001Action.java
 *     
 *  Project: T+0  
 *
 *  Description:
 *  =========================================================================
 *
 * 如果有任何对代码的修改,请按下面的格式注明修改的内容.
 *    序号          时间          作者                 修改内容 
 * ==========  =============  =============  =============================
 *    1.       2015年7月16日	  唐柳玉          created this class.
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

import com.allinfinance.bo.daytrade.T73001BO;
import com.allinfinance.po.daytrade.TblAcctErr;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

/**
 * 消费调账 描述：
 * 
 * @author tangly 2015年7月16日下午11:22:26
 */
public class T73001Action extends BaseAction {

	private static final long serialVersionUID = 1L;
	private T73001BO t73001BO = (T73001BO) ContextUtil.getBean("T73001BO");
	private TblAcctErr tblAcctErr;

	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		if (isNotEmpty(tblAcctErr.getCard_accp_id())) {
			tblAcctErr.setCard_accp_id(tblAcctErr.getCard_accp_id().substring(0, 15));
		}		
		if ("add".equals(getMethod())) {
			rspCode = add();
		} else if ("update".equals(getMethod())) {
			rspCode = update();
		}
		return rspCode;
	}

	/**
	 * 冲账
	 * 
	 * @return
	 */
	private String update() {
		
		if(!dateAnaly().equals("")){
			return dateAnaly()+"不能冲账!";
		}
		tblAcctErr.setCard_accp_id(tblAcctErr.getCard_accp_id().substring(0, 15));
		String e = t73001BO.buildFrontMsg("033001",tblAcctErr);		
		return e;

	}

	/**
	 * 补账
	 * 
	 * @return
	 */
	private String add() {
		if(!dateAnaly().equals("")){
			return dateAnaly()+"不能补账!";
		}
		String e = t73001BO.buildFrontMsg("033011", tblAcctErr);
		return e;
	}

	public TblAcctErr getTblAcctErr() {
		return tblAcctErr;
	}

	public void setTblAcctErr(TblAcctErr tblAcctErr) {
		this.tblAcctErr = tblAcctErr;
	}

	public String dateAnaly() {
		if (!isNotEmpty(tblAcctErr.getTxn_date())) {
			return "交易日期为空,";

		}
		if (!isNotEmpty(tblAcctErr.getTxn_time())) {
			return "交易时间为空,";
		}
		if (!isNotEmpty(tblAcctErr.getSys_seq_num())) {
			return "系统跟踪号为空,";

		}
		if (!isNotEmpty(tblAcctErr.getCard_accp_id())) {
			return "商户号为空,";
		}
		if (!isNotEmpty(tblAcctErr.getPan())) {
			return "卡号为空,";

		}
		if (!isNotEmpty(tblAcctErr.getCard_type())) {
			return "卡类型为空,";
		}
		if (!isNotEmpty(tblAcctErr.getAmt_trans())) {
			return "交易金额为空,";

		}
		if (!isNotEmpty(tblAcctErr.getAmt_trans_fee())) {
			return "交易手续费为空,";

		}
		if (!isNotEmpty(tblAcctErr.getAmt_stlm())) {
			return "结算金额为空,";

		}
		return "";
	}
}
