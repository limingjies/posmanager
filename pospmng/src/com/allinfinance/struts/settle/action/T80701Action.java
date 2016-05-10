
package com.allinfinance.struts.settle.action;

import java.io.FileOutputStream;

import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.InformationUtil;
import com.allinfinance.system.util.SysParamUtil;

public class T80701Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 14);
		String jasName="T80701.jasper";
		String rnName="RN80701RN_";
		String resName= SysParamUtil.getParam("RN80701RN");
		data = reportCreator.process(genSql(), title);

		if(data.length == 0) {
			writeNoDataMsg("没有找符合条件的记录，无法生成报表");
			return;
		}
		
		for (int i = 0; i < data.length; i++) {
			if(StringUtil.isNotEmpty(data[i][0])){
				data[i][0]=CommonFunction.dateFormat(data[i][0].toString());
			}	
			/*if(StringUtil.isNotEmpty(data[i][1])){
				data[i][1]=setInstCodeMap(data[i][1].toString());
			}*/
			if(StringUtil.isNotEmpty(data[i][2])){
				data[i][2]=CommonFunction.dateFormat(data[i][2].toString());
			}
			if(StringUtil.isNotEmpty(data[i][10])){
				data[i][10]=CommonFunction.moneyFormat(data[i][10].toString());
			}
			if(StringUtil.isNotEmpty(data[i][11])){
				data[i][11]=CommonFunction.moneyFormat(data[i][11].toString());
			}	
			if(StringUtil.isNotEmpty(data[i][12])){
				data[i][12]=CommonFunction.moneyFormat(data[i][12].toString());
			}	
			if(StringUtil.isNotEmpty(data[i][13])){
				data[i][13]=setStlmMap(data[i][13].toString());
			}
		}

		parameters.put("P_1",StringUtil.isEmpty(startDate)?"":CommonFunction.dateFormat(startDate));
		parameters.put("P_2",StringUtil.isEmpty(endDate)?"":CommonFunction.dateFormat(endDate));
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
		reportCreator.initReportData(getJasperInputSteam(jasName), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rnName + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rnName + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, resName);
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
	    
	    StringBuffer whereSql = new StringBuffer(" where (CARD_ACCP_ID is null or CARD_ACCP_ID in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "+operator.getBrhBelowId()+" )) ");

	    if(isNotEmpty(startDate)) {
	    	whereSql.append(" AND DATE_SETTLMT ").append(" >= ").append("'").append(startDate).append("'");
	    }
	    if(isNotEmpty(endDate)) {
	    	whereSql.append(" AND DATE_SETTLMT").append(" <= ").append("'").append(endDate).append("'");
	    }
	    if(isNotEmpty(queryMchtNoNm)) {
	    	whereSql.append(" AND CARD_ACCP_ID  in (SELECT MCHT_NO FROM TBL_MCHT_BASE_INF  start with MCHT_NO =").append("'").append(queryMchtNoNm).append("' connect by prior  trim(MCHT_NO) = MCHT_GROUP_ID ) ");
	    }
	    if(isNotEmpty(queryInstCode)) {
	    	whereSql.append(" AND a.INST_CODE").append(" = ").append("'").append(queryInstCode).append("'");
	    }
	    
	    String sql = "SELECT DATE_SETTLMT,a.INST_CODE||' - '||e.FIRST_BRH_NAME as fir_brh_name,TRANS_DATE_TIME,"
	    		+ "(CASE WHEN b.MCHT_NO IS NULL THEN '' ELSE b.MCHT_NO||' - '||b.MCHT_NM END) AS mchtNoNm,"
	    		+ "(CASE WHEN c.BRH_ID IS NULL THEN '' ELSE c.BRH_ID||' - '||c.BRH_NAME END) AS bankNoName,"
	    		+ "CARD_ACCP_TERM_ID,d.TXN_NAME,SYS_SEQ_NUM,POSP_PAN,INST_PAN,"
	    		+ "TO_NUMBER(NVL(trim(POSP_AMT_TRANS),0))/100,TO_NUMBER(NVL(trim(INST_AMT_TRANS),0))/100,"
	    		+ "TO_NUMBER(NVL(trim(FEE_DEBIT),0))/100-TO_NUMBER(NVL(trim(FEE_CREDIT),0))/100,STLM_FLAG "
	    		+ "FROM BTH_ERR_DTL a "
	    		+ "LEFT JOIN TBL_MCHT_BASE_INF b ON b.MCHT_NO = a.CARD_ACCP_ID "
	    		+ "LEFT JOIN TBL_BRH_INFO c ON c.BRH_ID = b.BANK_NO "
	    		+ "LEFT JOIN TBL_TXN_NAME d ON d.TXN_NUM = a.TXN_NUM "
	    		+ "LEFT JOIN TBL_FIRST_BRH_DEST_ID e ON e.DEST_ID = a.INST_CODE ";
	    
	    sql = sql + whereSql.toString();
	    sql=sql+" order by DATE_SETTLMT desc";
		return sql.toString();
	}
	
	
	
	private String startDate;
	private String endDate;
	private String queryMchtNoNm;
	private String queryInstCode;

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
	 * @return the queryMchtNoNm
	 */
	public String getQueryMchtNoNm() {
		return queryMchtNoNm;
	}

	/**
	 * @param queryMchtNoNm the queryMchtNoNm to set
	 */
	public void setQueryMchtNoNm(String queryMchtNoNm) {
		this.queryMchtNoNm = queryMchtNoNm;
	}

	/**
	 * @return the queryInstCode
	 */
	public String getQueryInstCode() {
		return queryInstCode;
	}

	/**
	 * @param queryInstCode the queryInstCode to set
	 */
	public void setQueryInstCode(String queryInstCode) {
		this.queryInstCode = queryInstCode;
	}
	
	/*//通道机构匹配
	private String setInstCodeMap(String val) {
		if("0001".equals(val))
			return val+"-中信";
		else if("0002".equals(val))
			return val+"-银生宝";
		else if("0003".equals(val))
			return val+"-轩宸";
		else if("0004".equals(val))
			return val+"-聚财通";
		return val;
	}*/
	
	//差错描述匹配
	private String setStlmMap(String val) {
		if("1".equals(val))
			return "posp成功，通道机构失败";
		else if("2".equals(val))
			return "posp失败，通道机构成功";
		else if("3".equals(val))
			return "posp与通道机构金额不一致";
		return val;
	}
	

}
