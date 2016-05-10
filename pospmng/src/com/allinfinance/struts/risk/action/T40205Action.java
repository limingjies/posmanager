
package com.allinfinance.struts.risk.action;

import java.io.FileOutputStream;

import com.allinfinance.common.Constants;
import com.allinfinance.common.RiskConstants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.select.SelectMethod;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.InformationUtil;
import com.allinfinance.system.util.SysParamUtil;

/**
 * Title: 
 * 
 * File: T4020501Action.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-8-10
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T40205Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 14);
		
		data = reportCreator.process(genSql(), title);
		
		for (int i = 0; i < data.length; i++) {
			if(StringUtil.isNotEmpty(data[i][0])){
				data[i][0]=CommonFunction.dateFormat(data[i][0].toString());
			}
			if(StringUtil.isNotEmpty(data[i][1])){
//				data[i][1]=riskIdName(data[i][1].toString());
				data[i][1]=SelectMethod.getKind(null).get(data[i][1].toString());
				
			}
			if(StringUtil.isNotEmpty(data[i][4])){
				data[i][4]=CommonFunction.dateFormat(data[i][4].toString());
			}
			if(StringUtil.isNotEmpty(data[i][5])){
				data[i][5]=CommonFunction.dateFormat(data[i][5].toString());
			}
			if(StringUtil.isNotEmpty(data[i][11])){
				data[i][11]=CommonFunction.moneyFormat(data[i][11].toString());
			}
			if(StringUtil.isNotEmpty(data[i][12])){
				data[i][12]=txnSta(data[i][12].toString());
			}
		}

		if(data.length == 0) {
			writeNoDataMsg("没有找符合条件的记录，无法生成报表");
			return;
		}
		
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
//		parameters.put("P_1", oriSettleDate);
		
		reportCreator.initReportData(getJasperInputSteam("T40205.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40205RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40205RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN4020501RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		
		
		StringBuffer whereSql = new StringBuffer(" where 1=1  and a.risk_id != 'B01' ");
		if (StringUtil.isNotEmpty(startDate)) {
			whereSql.append(" and a.risk_date>='").append(startDate).append("'");
		}	
		if (StringUtil.isNotEmpty(endDate)) {
			whereSql.append(" and a.risk_date<='").append(endDate).append("'");
		}
		
		if(isNotEmpty(queryAmtTransLow)) {
        	whereSql.append(" AND TO_NUMBER(NVL(trim(a.amt_trans),0))/100 ").append(" >= ").append("'").append(queryAmtTransLow).append("'");
        }
        if(isNotEmpty(queryAmtTransUp)) {
        	whereSql.append(" AND TO_NUMBER(NVL(trim(a.amt_trans),0))/100 ").append(" <= ").append("'").append(queryAmtTransUp).append("'");
        }
		
        if (StringUtil.isNotEmpty(queryTxnName)) {
        	whereSql.append(" and a.txn_num = '").append(queryTxnName).append("'");
        }
		if (StringUtil.isNotEmpty(querySysSeqNum)) {
			whereSql.append(" and a.sys_seq_num like '%").append(querySysSeqNum).append("%'");
		}
		if (StringUtil.isNotEmpty(queryRiskId)) {
			whereSql.append(" and a.risk_id = '").append(queryRiskId).append("'");
		}
		if (StringUtil.isNotEmpty(queryTransState)) {
			if(RiskConstants.TXN_FAIL.equals(queryTransState))
				whereSql.append(" and a.trans_state !='0' and a.trans_state !='1' ");
			else
				whereSql.append(" and a.trans_state = '").append(queryTransState).append("'");
		}
		if (StringUtil.isNotEmpty(queryPan)) {
			whereSql.append(" and a.pan like '%").append(queryPan).append("%'");
		}
		if (StringUtil.isNotEmpty(queryCardAccpTermId)) {
			whereSql.append(" and a.card_accp_term_id like '%").append(queryCardAccpTermId).append("%'");
		}
		if (StringUtil.isNotEmpty(queryCardAccpId)) {
			whereSql.append(" and a.card_accp_id = '").append(queryCardAccpId).append("'");
		}
		if (StringUtil.isNotEmpty(queryRiskLvl)) {
			whereSql.append(" and a.risk_lvl = '").append(queryRiskLvl).append("'");
		}
		
		String sql = "select a.risk_date,a.risk_id," +
				"(select a.risk_lvl||'-'||c.RESVED from TBL_RISK_LVL c where c.RISK_ID =a.RISK_ID and c.RISK_LVL=a.risk_lvl) as riskLevlName,"+
				" (select b.TXN_NAME from TBL_TXN_NAME b where a.txn_num=b.txn_num )as txnName," +
				"substr(a.inst_date,1,8),substr(a.inst_date,9),a.card_accp_id||'-'||b.MCHT_NM ," +
				"(select b.bank_no||'-'||e.brh_name from tbl_brh_info e where trim(e.brh_id) =trim(b.bank_no) ) ," +
				"a.card_accp_term_id, a.pan,a.sys_seq_num,TO_NUMBER(NVL(trim(a.amt_trans),0))/100,a.trans_state, " +
				"(select trim(RSP_CODE) || '-' || trim(rsp_code_dsp) from tbl_rsp_code_map where trim(dest_rsp_code) = trim(a.RSP_CODE)) " +
				" from TBL_RISK_DATA a  left join TBL_MCHT_BASE_INF b on a.card_accp_id=b.MCHT_NO " +whereSql.toString()+" order by a.risk_date desc,a.risk_id,a.inst_date desc ";
		
		return sql.toString();
	}
	
	
	protected String riskIdName(String val) {
		if("A00".equals(val)||"A01".equals(val)||"A02".equals(val))
            return val+"-频繁试卡";
    	else if("A04".equals(val)||"A03".equals(val))
    		return val+"-大额交易";
        else if("A05".equals(val)||"A06".equals(val))
    		return val+"-频繁小额交易";
    	else if("A07".equals(val))
    		return val+"-频繁整数交易";
    	else if("A08".equals(val))
    		return val+"-高失败率";
    	else if("A09".equals(val))
    		return val+"-可疑交易";
    	else if("A10".equals(val)||"A11".equals(val)||"A12".equals(val)||"A13".equals(val))
    		return val+"-信用卡高风险交易";
    	else
    		return val;
	}
	
	protected String txnSta(String val) {
		if("0".equals(val))
			return "请求中";
		else if("1".equals(val))
			return  "成功";
		else if("9".equals(val))
			return  "交易确认 ";
		else if("R".equals(val))
			return  "已退货";
		else 
			return "失败";
	}
	private String startDate;
	private String endDate;
	private String queryAmtTransLow;
	private String queryAmtTransUp;
	private String queryTxnName;
	private String queryCardAccpId;
	private String queryCardAccpTermId;
	private String querySysSeqNum;
	private String queryPan;
	private String queryTransState;
	private String queryRiskId;
	private String queryRiskLvl;

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the queryAmtTransLow
	 */
	public String getQueryAmtTransLow() {
		return queryAmtTransLow;
	}

	/**
	 * @param queryAmtTransLow the queryAmtTransLow to set
	 */
	public void setQueryAmtTransLow(String queryAmtTransLow) {
		this.queryAmtTransLow = queryAmtTransLow;
	}

	/**
	 * @return the queryAmtTransUp
	 */
	public String getQueryAmtTransUp() {
		return queryAmtTransUp;
	}

	/**
	 * @param queryAmtTransUp the queryAmtTransUp to set
	 */
	public void setQueryAmtTransUp(String queryAmtTransUp) {
		this.queryAmtTransUp = queryAmtTransUp;
	}

	/**
	 * @return the queryTxnName
	 */
	public String getQueryTxnName() {
		return queryTxnName;
	}

	/**
	 * @param queryTxnName the queryTxnName to set
	 */
	public void setQueryTxnName(String queryTxnName) {
		this.queryTxnName = queryTxnName;
	}

	/**
	 * @return the queryCardAccpId
	 */
	public String getQueryCardAccpId() {
		return queryCardAccpId;
	}

	/**
	 * @param queryCardAccpId the queryCardAccpId to set
	 */
	public void setQueryCardAccpId(String queryCardAccpId) {
		this.queryCardAccpId = queryCardAccpId;
	}

	/**
	 * @return the queryCardAccpTermId
	 */
	public String getQueryCardAccpTermId() {
		return queryCardAccpTermId;
	}

	/**
	 * @param queryCardAccpTermId the queryCardAccpTermId to set
	 */
	public void setQueryCardAccpTermId(String queryCardAccpTermId) {
		this.queryCardAccpTermId = queryCardAccpTermId;
	}

	/**
	 * @return the querySysSeqNum
	 */
	public String getQuerySysSeqNum() {
		return querySysSeqNum;
	}

	/**
	 * @param querySysSeqNum the querySysSeqNum to set
	 */
	public void setQuerySysSeqNum(String querySysSeqNum) {
		this.querySysSeqNum = querySysSeqNum;
	}

	/**
	 * @return the queryPan
	 */
	public String getQueryPan() {
		return queryPan;
	}

	/**
	 * @param queryPan the queryPan to set
	 */
	public void setQueryPan(String queryPan) {
		this.queryPan = queryPan;
	}

	/**
	 * @return the queryTransState
	 */
	public String getQueryTransState() {
		return queryTransState;
	}

	/**
	 * @param queryTransState the queryTransState to set
	 */
	public void setQueryTransState(String queryTransState) {
		this.queryTransState = queryTransState;
	}

	/**
	 * @return the queryRiskId
	 */
	public String getQueryRiskId() {
		return queryRiskId;
	}

	/**
	 * @param queryRiskId the queryRiskId to set
	 */
	public void setQueryRiskId(String queryRiskId) {
		this.queryRiskId = queryRiskId;
	}

	public String getQueryRiskLvl() {
		return queryRiskLvl;
	}

	public void setQueryRiskLvl(String queryRiskLvl) {
		this.queryRiskLvl = queryRiskLvl;
	}
	
	
	
}
