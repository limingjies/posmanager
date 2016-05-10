
package com.allinfinance.struts.settle.action;

import java.io.FileOutputStream;

import com.allinfinance.common.Constants;
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
public class T80304Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "EXCEL";
		
		String jasName="";
		String rnName="";
		title = InformationUtil.createTitles("V_", 14);
		if("0".equals(repType)){
			jasName="T80304.jasper";
			rnName="RN80304RN_";
			data = reportCreator.process(genSql(), title);

		}else{
			jasName="T80305.jasper";
			rnName="RN80305RN_";
			data = reportCreator.process(genSqls(), title);
		}
		
		
		
		for (int i = 0; i < data.length; i++) {
			if(StringUtil.isNotEmpty(data[i][0])){
				data[i][0]=CommonFunction.dateFormat(data[i][0].toString());
			}
			if(StringUtil.isNotEmpty(data[i][1])){
				data[i][1]=SelectMethod.getStlmFlag(null).get(data[i][1].toString());
				
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
		
		reportCreator.initReportData(getJasperInputSteam(jasName), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rnName + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rnName + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam("RN4020501RN"));
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		
		
		StringBuffer whereSql = new StringBuffer(" where 1=1  and a.STLM_FLAG not in('0','A','D' ) " 
				+ "and a.txn_num in "
				+ "('1011','1091','1101','1111','1121','1141','2011','2091','2101','2111','2121','3011','3091','3101','3111','3121','4011','4091','4101','4111','4121','5151','5161') ");
//		StringBuffer whereSql1 = new StringBuffer(" where 1=1  and g.STLM_FLAG not in('0','A','D' ) " 
//				+ "and g.txn_num in "
//				+ "('1011','1091','1101','1111','1121','1141','2011','2091','2101','2111','2121','3011','3091','3101','3111','3121','4011','4091','4101','4111','4121','5151','5161') ");
		if (StringUtil.isNotEmpty(startDate)) {
			whereSql.append(" and a.DATE_SETTLMT>='").append(startDate.replace("-", "")).append("'");
//			whereSql1.append(" and g.DATE_SETTLMT>='").append(startDate.replace("-", "")).append("'");
		}	
		if (StringUtil.isNotEmpty(endDate)) {
			whereSql.append(" and a.DATE_SETTLMT<='").append(endDate.replace("-", "")).append("'");
//			whereSql1.append(" and g.DATE_SETTLMT<='").append(endDate.replace("-", "")).append("'");
		}
		
		
		String sql = "select * from (select a.DATE_SETTLMT,a.STLM_FLAG,a.retrivl_ref," +
				" (select b.TXN_NAME from TBL_TXN_NAME b where a.txn_num=b.txn_num )as txnName,"
				+ " a.insert_date,a.insert_time," +
				" a.card_accp_id||'-'||b.MCHT_NM as mcht_name," +
				" (select b.bank_no||'-'||e.brh_name from tbl_brh_info e where trim(e.brh_id) =trim(b.bank_no) ) as brh_name,"+
				" a.card_accp_term_id,a.pan,a.sys_seq_num,TO_NUMBER(NVL(trim(a.amt_trans),0))/100 as amt ,a.trans_state, "+
//				"(select trim(dest_rsp_code) || '-' || trim(rsp_code_dsp) from tbl_rsp_code_map where trim(dest_rsp_code) = trim(a.RESP_CODE)) "+
				"trim(a.RESP_CODE) ||(select  '-' || trim(rsp_code_dsp) from tbl_rsp_code_map where trim(dest_rsp_code) = trim(a.RESP_CODE)) as resp_name "+
				" from BTH_CHK_TXN a  left join TBL_MCHT_BASE_INF b on a.card_accp_id=b.MCHT_NO " +whereSql.toString()+
				" order by a.DATE_SETTLMT desc ,a.insert_date desc,a.insert_time desc )";
		
		
	/*String sql1=" select * from (SELECT g.DATE_SETTLMT,g.STLM_FLAG,g.retrivl_ref,"
			+ " (SELECT h.TXN_NAME FROM TBL_TXN_NAME h WHERE g.txn_num=h.txn_num )AS txnName,"
			+ " substr(l.inst_date,1,8),substr(l.inst_date,9),h.card_accp_id||'-'||j.MCHT_NM AS mcht_name,"
			+ " (SELECT j.bank_no||'-'||k.brh_name FROM tbl_brh_info k WHERE trim(k.brh_id) =trim(j.bank_no) ) AS brh_name,"
			+ " g.card_accp_term_id,g.pan,l.sys_seq_num,TO_NUMBER(NVL(trim(g.amt_trans),0))/100 AS amt ,l.trans_state,"
			+ " (SELECT trim(dest_rsp_code) || '-' || trim(rsp_code_dsp) FROM tbl_rsp_code_map WHERE trim(dest_rsp_code) = trim(l.RESP_CODE))"
			+ " FROM bth_cup_txn_bdt g"
			+ " left join BTH_GC_TXN_SUCC h on g.KEY_CUP=h.KEY_CUP "
			+ " LEFT JOIN TBL_N_TXN_HIS l ON l.sys_seq_num=g.cup_ssn and l.trans_date_time=g.trans_date_time"
			+ " LEFT JOIN TBL_MCHT_BASE_INF j ON h.card_accp_id=j.MCHT_NO "
			+  whereSql1.toString()+
			" order by g.DATE_SETTLMT desc ,l.inst_date desc )";*/
	
		return sql.toString();//+" union all "+sql1.toString();
	}
	
	private String genSqls() {
		
		
		StringBuffer whereSql = new StringBuffer(" where 1=1 and  a.txn_num ='5151' and a.TRANS_CODE='CCT' ");
		if (StringUtil.isNotEmpty(startDate)) {
			whereSql.append(" and a.DATE_SETTLMT>='").append(startDate.replace("-", "")).append("'");
		}	
		if (StringUtil.isNotEmpty(endDate)) {
			whereSql.append(" and a.DATE_SETTLMT<='").append(endDate.replace("-", "")).append("'");
		}
		
		
		String sql = "select a.DATE_SETTLMT,a.STLM_FLAG,a.retrivl_ref," +
				" '差错退货',"
				+ " a.insert_date,a.insert_time," +
				" a.card_accp_id||'-'||b.MCHT_NM as mcht_name," +
				" (select b.bank_no||'-'||e.brh_name from tbl_brh_info e where trim(e.brh_id) =trim(b.bank_no) ) as brh_name,"+
				" a.card_accp_term_id,a.pan,a.sys_seq_num,TO_NUMBER(NVL(trim(a.amt_trans),0))/100 as amt ,a.trans_state, "+
//				"(select trim(dest_rsp_code) || '-' || trim(rsp_code_dsp) from tbl_rsp_code_map where trim(dest_rsp_code) = trim(a.RESP_CODE)) "+
				"trim(a.RESP_CODE) ||(select  '-' || trim(rsp_code_dsp) from tbl_rsp_code_map where trim(dest_rsp_code) = trim(a.RESP_CODE)) as resp_name "+
				" from BTH_CHK_TXN a  left join TBL_MCHT_BASE_INF b on a.card_accp_id=b.MCHT_NO " +whereSql.toString()+
				" order by a.DATE_SETTLMT desc ,a.insert_date desc,a.insert_time desc ";
																	 
		return sql.toString();
	}
	
	
	protected String txnSta(String val) {
		if("0".equals(val))
			return "请求中";
		else if("1".equals(val))
			return  "成功";
		else if("R".equals(val))
			return  "已退货";
		else 
			return "失败";
	}
	
	private String startDate;
	private String endDate;
	private String repType;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRepType() {
		return repType;
	}

	public void setRepType(String repType) {
		this.repType = repType;
	}

	
	
	
	

	
	
	
}
