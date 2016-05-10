/**
 *  T70100BOTarget.java
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

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.allinfinance.bo.daytrade.T73001BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.daytrade.WebFrontTxnLogDAO;
import com.allinfinance.frontend.dto.acctmanager.TransConsumptionDto;
import com.allinfinance.po.daytrade.TblAcctErr;
import com.allinfinance.po.daytrade.WebFrontTxnLog;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.GenerateNextId;

public class T73001BOTarget implements T73001BO {
	public WebFrontTxnLogDAO webFrontTxnLogDAO;

	@Override
	public String update(TblAcctErr tblAcctErr) {
		// TODO Auto-generated method stub
		String i = "1";
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("update TBL_ACCT_ERR t set t.acct_change_stat='"+tblAcctErr.getAcct_change_stat()+"',");
			sql.append( "  t.acct_change_resp='"+tblAcctErr.getAcct_change_resp()+"',");
			sql.append( "  t.acct_change_dt='"+tblAcctErr.getAcct_change_dt() +"'");
			sql.append( " where  t.txn_date ='"+ tblAcctErr.getTxn_date()+"' and ");
			sql.append( " t.txn_time ='"+ tblAcctErr.getTxn_time()+"'");
			CommonFunction.getCommQueryDAO().excute(sql.toString());
			System.out.println("消费调账更新数据成功！");
		} catch (Exception e) {
			i = "-1";
			System.out.println("消费调账更新数据失败！");
			e.printStackTrace();
		}
		return i;
	}

	public String buildFrontMsg(String transCode, TblAcctErr tblAcctErr) {
		// 组装调账前台系统数据
		TransConsumptionDto trc = new TransConsumptionDto();
		trc.setWebTime(CommonFunction.getCurrentDateTime());
		trc.setWebSeqNum(GenerateNextId.getSeqSysNum());
		trc.setPospInstDate(tblAcctErr.getTxn_date()+tblAcctErr.getTxn_time());
		trc.setPospSysSeqNum(tblAcctErr.getSys_seq_num());
		trc.setMerchantId(tblAcctErr.getCard_accp_id());
		trc.setTransFee(new BigDecimal(tblAcctErr.getAmt_trans_fee() == null || tblAcctErr.getAmt_trans_fee().equals("") ? "0" : tblAcctErr
				.getAmt_trans_fee().replace(",", "")));
		trc.setTransAmount(new BigDecimal(tblAcctErr.getAmt_trans() == null || tblAcctErr.getAmt_trans().equals("") ? "0" : tblAcctErr
				.getAmt_trans().replace(",", "")));
		trc.setSettleAmount(new BigDecimal(tblAcctErr.getAmt_stlm() == null || tblAcctErr.getAmt_stlm().equals("") ? "0" : tblAcctErr
				.getAmt_stlm().replace(",", "")));
		trc.setCardNo(tblAcctErr.getPan());
		trc.setCardType(tblAcctErr.getCard_type());
		AdjustAcct adjustAcct = new AdjustAcct();
		Object[] ret = adjustAcct.doTxn(transCode, trc);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
		String cunrrDate=sdf.format(new Date());
		if (Constants.SUCCESS_CODE.equals(ret[0])) {
			WebFrontTxnLog webFrontTxnLog = (WebFrontTxnLog) ret[1];
			webFrontTxnLogDAO.save(webFrontTxnLog);
			if(transCode.equals("033001")){
			tblAcctErr.setAcct_change_stat("1");
			}else{
			tblAcctErr.setAcct_change_stat("2");	
			}
			tblAcctErr.setAcct_change_resp("00");
			tblAcctErr.setAcct_change_dt(cunrrDate);
			update(tblAcctErr);
			// 返回成功信息
			return Constants.SUCCESS_CODE;
		} else {
			tblAcctErr.setAcct_change_stat("0");		
			tblAcctErr.setAcct_change_resp("99");
			tblAcctErr.setAcct_change_dt(cunrrDate);
			update(tblAcctErr);
			return (String) ret[1];
		}
	}

	public WebFrontTxnLogDAO getWebFrontTxnLogDAO() {
		return webFrontTxnLogDAO;
	}

	public void setWebFrontTxnLogDAO(WebFrontTxnLogDAO webFrontTxnLogDAO) {
		this.webFrontTxnLogDAO = webFrontTxnLogDAO;
	}

}
