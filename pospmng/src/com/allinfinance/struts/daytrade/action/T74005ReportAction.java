/**
 *  T74005ReportAction.java
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

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ExcelReportCreator;
import com.allinfinance.system.util.SysParamUtil;

@SuppressWarnings("serial")
public class T74005ReportAction extends ReportBaseAction {
	private String txn_date;
	private String txn_date2;
	private String queryEflag;

	@Override
	protected void reportAction() throws Exception {
		ExcelReportCreator excelReportCreator=new ExcelReportCreator(); 
		List<String> sheetList=new ArrayList<String>();
		List<String> titleList=new ArrayList<String>();
		List<String[]> coulmHeaderList=new ArrayList<String[]>();	
		List<String> dataSqlList=new ArrayList<String>();
		
	    StringBuffer countSql = new StringBuffer("SELECT COUNT(*) FROM TBL_WITHDRAW_ERR t where 1=1 ");
	    if(isNotEmpty(txn_date)&& !isNotEmpty(txn_date2)) {
	    	countSql.append(" and t.txn_date =").append("'").append(txn_date).append("' ");
	    }
	    if(!isNotEmpty(txn_date)&& isNotEmpty(txn_date2)) {
	    	countSql.append(" and t.txn_date <=").append("'").append(txn_date2).append("' ");
	    }
	    if(isNotEmpty(txn_date)&& isNotEmpty(txn_date2)) {
	    	countSql.append(" and t.txn_date >=").append("'").append(txn_date).append("' ");
	    	countSql.append(" and t.txn_date <=").append("'").append(txn_date2).append("' ");
	    }
	    if (isNotEmpty(queryEflag)) {			
	    	countSql.append(" and t.acct_change =").append("'").append(queryEflag).append("' ");
		}else{
			countSql.append(" and  t.acct_change  is null  ");
			}
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql.toString());
		if("0".equals(count)){
			writeNoDataMsg("没有找符合条件的记录，无法生成报表");
			return;
		}

		String rnName="RN74005RN_";
		String resName= SysParamUtil.getParam("RN74005RN");
		
		String[] coulmHeader={"前置时间","前置流水号","原商服提现时间","原商服提现流水","交易日期","差异类型","前置提现状态","前置提现金额","前置提现手续费","前置提现结算金额","账户提现状态","账户提现金额","账户提现手续费","账户提现结算金额","调账状态","调账应答码","调账时间","插入时间","更新时间"};
        
		StringBuffer sql = new StringBuffer();	   
	    sql.append("SELECT to_char(to_date(t.front_dt,'yyyymmddhh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss') as front_dt,t.front_sn,"
	    		+ " to_char(to_date(t.mcht_withdraw_dt,'yyyymmddhh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss') as mcht_withdraw_dt,t.mcht_withdraw_sn,"
	    		+ " to_char(to_date(t.txn_date,'yyyymmdd'),'yyyy-mm-dd') as txn_date, ");
	    sql.append("(case when t.err_type = 'D001' then '前置和账户系统数据差异' WHEN  t.err_type = 'D002' THEN '前置存在，账户不存在'  WHEN  t.err_type = 'D003' THEN '前置不存在，账户存在' END) AS err_type, ");
	    sql.append(" (case when t.acct_change='0' then '调账失败' when t.acct_change='1' then '已冲账' when t.acct_change='' then '未调账' end ) AS acct_change , ");
	    sql.append(" (case when t.acct_change_resp='00' then '成功' when t.acct_change_resp='1' then '失败' end) AS acct_change_resp, ");
	    sql.append(" t.acct_change_dt,");
	    sql.append(" (case when t.front_stat = '0' then '成功' WHEN  t.front_stat = '1' THEN '失败'  WHEN  t.front_stat = '2' THEN '处理中' END) AS front_stat, ");
	    sql.append(" to_char(nvl(TO_NUMBER(NVL(trim(t.front_withdraw_amt),0.00))/100,0),'99,999,999,990.99') as front_withdraw_amt, ");
	    sql.append(" to_char(nvl(TO_NUMBER(NVL(trim(t.front_withdraw_fee),0.00))/100,0),'99,999,999,990.99') as front_withdraw_fee, ");
	    sql.append(" to_char(nvl(TO_NUMBER(NVL(trim(t.front_withdraw_stlm_amt),0.00))/100,0),'99,999,999,990.99') as front_withdraw_stlm_amt, ");
	    sql.append("t.acct_withdraw_stat, ");
	    sql.append("to_char(nvl(TO_NUMBER(NVL(trim(t.acct_withdraw_amt),0.00))/100,0),'99,999,999,990.99') as acct_withdraw_amt, ");
	    sql.append("to_char(nvl(TO_NUMBER(NVL(trim(t.acct_withdraw_fee),0.00))/100,0),'99,999,999,990.99') as acct_withdraw_fee, ");
	    sql.append("to_char(nvl(TO_NUMBER(NVL(trim(t.acct_withdraw_stlm_amt),0.00))/100,0),'99,999,999,990.99') as acct_withdraw_stlm_amt, ");
	   
	    sql.append(" to_char(to_date(t.inst_dt,'yyyymmddhh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss') as inst_dt,"
	    		+ " to_char(to_date(t.update_dt,'yyyymmddhh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss') as update_dt ");
	    sql.append("from TBL_WITHDRAW_ERR t  where 1=1");
	    if(isNotEmpty(txn_date)&& !isNotEmpty(txn_date2)) {
	    	sql.append(" and t.txn_date =").append("'").append(txn_date).append("' ");
	    }
	    if(!isNotEmpty(txn_date)&& isNotEmpty(txn_date2)) {
	    	sql.append(" and t.txn_date <=").append("'").append(txn_date2).append("' ");
	    }
	    if(isNotEmpty(txn_date)&& isNotEmpty(txn_date2)) {
	    	sql.append(" and t.txn_date >=").append("'").append(txn_date).append("' ");
	    	sql.append(" and t.txn_date <=").append("'").append(txn_date2).append("' ");
	    }
	    if (isNotEmpty(queryEflag)) {			
			sql.append(" and t.acct_change =").append("'").append(queryEflag).append("' ");
		}else{
			sql.append(" and t.acct_change is null ");
			}
	    
	    sql.append("order by t.txn_date,t.txn_date ");
        sheetList.add(resName);
		titleList.add(resName);
		coulmHeaderList.add(coulmHeader);
		dataSqlList.add(sql.toString());
		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rnName + 
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		outputStream = new FileOutputStream(fileName);
		
		int leftFormat[]={5};
		excelReportCreator.setLeftFormat(leftFormat);
		excelReportCreator.setSheetList(sheetList);
		excelReportCreator.setTitleList(titleList);
		excelReportCreator.setCoulmHeaderList(coulmHeaderList);
		excelReportCreator.setDataList(excelReportCreator.processData(dataSqlList, coulmHeaderList));	
		excelReportCreator.exportReport(outputStream);
		outputStream.close();
		writeUsefullMsg(fileName);
		return;
	}

	@Override
	protected String genSql() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTxn_date() {
		return txn_date;
	}

	public void setTxn_date(String txn_date) {
		this.txn_date = txn_date;
	}

	public String getTxn_date2() {
		return txn_date2;
	}

	public void setTxn_date2(String txn_date2) {
		this.txn_date2 = txn_date2;
	}

	public String getQueryEflag() {
		return queryEflag;
	}

	public void setQueryEflag(String queryEflag) {
		this.queryEflag = queryEflag;
	}
	
	
	

}
