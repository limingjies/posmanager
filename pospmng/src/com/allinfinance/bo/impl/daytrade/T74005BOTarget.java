/**
 *  T74005BOTarget.java
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
package com.allinfinance.bo.impl.daytrade;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.allinfinance.bo.daytrade.T74005BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.daytrade.WebFrontTxnLogDAO;
import com.allinfinance.frontend.dto.acctmanager.WithdrawTransAccDto;
import com.allinfinance.po.daytrade.TblWithdrawErr;
import com.allinfinance.po.daytrade.WebFrontTxnLog;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.GenerateNextId;

public class T74005BOTarget implements T74005BO {
	public WebFrontTxnLogDAO webFrontTxnLogDAO;

	public String buildFrontMsg(String transCode, TblWithdrawErr tw) {
		// 组装调账前台系统数据
		WithdrawTransAccDto wd = new WithdrawTransAccDto();
		wd.setWebTime(CommonFunction.getCurrentDateTime());
		wd.setWebSeqNum(GenerateNextId.getSeqSysNum());
		wd.setOrig_pospSysSeqNum(tw.getMcht_withdraw_sn());
		wd.setOrig_webTime(tw.getMcht_withdraw_dt());
		TxAdjustAcct txAdjustAcct = new TxAdjustAcct();
		Object[] ret = txAdjustAcct.doTxn(transCode, wd);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		String cunrrDate=sdf.format(new Date());
		if (Constants.SUCCESS_CODE.equals(ret[0])) {
			WebFrontTxnLog webFrontTxnLog = (WebFrontTxnLog) ret[1];
			webFrontTxnLogDAO.save(webFrontTxnLog);
			tw.setAcct_change("1");		
			tw.setAcct_change_resp("00");
			tw.setAcct_change_dt(cunrrDate);
			update(tw);
			// 返回成功信息
			return Constants.SUCCESS_CODE;
		} else {
			tw.setAcct_change("0");		
			tw.setAcct_change_resp("99");
			tw.setAcct_change_dt(cunrrDate);
			update(tw);
			return (String) ret[1];
		}
	}

	@Override
	public String update(TblWithdrawErr tblWithdrawErr) {
		// TODO Auto-generated method stub
		String i = "1";
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("update TBL_WITHDRAW_ERR t set t.acct_change='"+tblWithdrawErr.getAcct_change() +"',");
			sql.append( "  t.acct_change_resp='"+tblWithdrawErr.getAcct_change_resp()+"',");
			sql.append( "  t.acct_change_dt='"+tblWithdrawErr.getAcct_change_dt() +"'");
			sql.append( " where  t.mcht_withdraw_sn ='"+ tblWithdrawErr.getMcht_withdraw_sn()+"' and ");
			sql.append( " t.mcht_withdraw_dt ='"+ tblWithdrawErr.getMcht_withdraw_dt()+"'");
			CommonFunction.getCommQueryDAO().excute(sql.toString());
			System.out.println("提现调账更新数据成功！");
		} catch (Exception e) {
			i = "-1";
			System.out.println("提现调账更新数据失败！");
			e.printStackTrace();
		}
		return i;
	}

	public WebFrontTxnLogDAO getWebFrontTxnLogDAO() {
		return webFrontTxnLogDAO;
	}

	public void setWebFrontTxnLogDAO(WebFrontTxnLogDAO webFrontTxnLogDAO) {
		this.webFrontTxnLogDAO = webFrontTxnLogDAO;
	}

}
