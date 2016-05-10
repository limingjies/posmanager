
package com.allinfinance.struts.settle.action;

import java.io.FileOutputStream;
import java.text.DecimalFormat;

import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.InformationUtil;
import com.allinfinance.system.util.SysParamUtil;

public class T80804Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 11);
		String jasName="T80804.jasper";
		String rnName="RN80804RN_";
		String resName= SysParamUtil.getParam("RN80804RN");
		data = reportCreator.process(genSql(), title);

		if(data.length == 0) {
			writeNoDataMsg("没有找符合条件的记录，无法生成报表");
			return;
		}
		
		DecimalFormat df=new DecimalFormat("0.00");
     	Double amt8=0.00;
     	Double amt9=0.00;
     	Double amt10=0.00;
		
		for (int i = 0; i < data.length; i++) {
			if(StringUtil.isNotEmpty(data[i][0])){
				data[i][0]=CommonFunction.dateFormat(data[i][0].toString());
			}
			if(StringUtil.isNotEmpty(data[i][1])){
				data[i][1]=CommonFunction.dateFormat(data[i][1].toString());
			}
			if(StringUtil.isNotEmpty(data[i][8])){
				amt8+=Double.parseDouble(data[i][8].toString());
				data[i][8]=CommonFunction.moneyFormat(data[i][8].toString());
			}	
			if(StringUtil.isNotEmpty(data[i][9])){
				amt9+=Double.parseDouble(data[i][9].toString());
				data[i][9]=CommonFunction.moneyFormat(data[i][9].toString());
			}
			if(StringUtil.isNotEmpty(data[i][10])){
				amt10+=Double.parseDouble(data[i][10].toString());
				data[i][10]=CommonFunction.moneyFormat(data[i][10].toString());
			}
		}

		parameters.put("P_1",StringUtil.isEmpty(startDate)?"":CommonFunction.dateFormat(startDate));
		parameters.put("P_2",StringUtil.isEmpty(endDate)?"":CommonFunction.dateFormat(endDate));		
		parameters.put("P_9",CommonFunction.moneyFormat(df.format(amt8)));
		parameters.put("P_10",CommonFunction.moneyFormat(df.format(amt9)));
		parameters.put("P_11",CommonFunction.moneyFormat(df.format(amt10)));
		
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
	    
	    StringBuffer whereSql = new StringBuffer(" where MCHT_NO in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "+operator.getBrhBelowId()+" ) ");
	    StringBuffer whereSqlAmt = new StringBuffer(" where 1=1 ");
	
	   
	    if(isNotEmpty(startDate)) {
	    	whereSql.append(" AND DATE_SETTLMT ").append(" >= ").append("'").append(startDate).append("'");
	    }
	    if(isNotEmpty(endDate)) {
	    	whereSql.append(" AND DATE_SETTLMT").append(" <= ").append("'").append(endDate).append("'");
	    }
	    if(isNotEmpty(queryAmtTransLow)) {
	    	whereSqlAmt.append(" AND a.SUM_TXN_AMT ").append(" >= ").append("'").append(queryAmtTransLow).append("'");
	    }
	    if(isNotEmpty(queryAmtTransUp)) {
	    	whereSqlAmt.append(" AND a.SUM_TXN_AMT ").append(" <= ").append("'").append(queryAmtTransUp).append("'");
	    }
	    if(isNotEmpty(queryMchtNoNm)) {
	//    	whereSql.append(" AND MCHT_NO").append(" = ").append("'").append(mchtNo).append("'");
	//    	whereSql.append(" AND MCHT_NO").append(" in ").append(CommonFunction.getBelowMchtByMchtNo(mchtNo));
	    	whereSql.append(" AND MCHT_NO  in (SELECT MCHT_NO FROM TBL_MCHT_BASE_INF  start with MCHT_NO =").append("'").append(queryMchtNoNm).append("' connect by prior  trim(MCHT_NO) = MCHT_GROUP_ID ) ");
	    }
		if (isNotEmpty(queryBrhId)) {
	//		whereSql.append(" and f.card_accp_id in (select mcht_no from tbl_mcht_base_inf a where a.bank_no = '" + queryBrhId.trim() + "')");
			whereSql.append(" and MCHT_NO in (select mcht_no from tbl_mcht_base_inf  where bank_no in (SELECT brh_id FROM TBL_BRH_INFO  start with brh_id ='" + queryBrhId.trim() + "' connect by prior  BRH_ID = UP_BRH_ID  ) ) ");
		}
	    
	    String sql = "SELECT '"+startDate+"','"+endDate+"',(b.MCHT_NO||' - '||b.MCHT_NM) as MCHT_NO_NM,"
	    		+ "(select d.brh_id||' - '||d.brh_name from TBL_BRH_INFO d where d.brh_id = trim(b.BANK_NO)) as brh_id,"
	    		+ "substr(c.SETTLE_ACCT,2),c.SETTLE_ACCT_NM,c.SETTLE_BANK_NM,substr(c.OPEN_STLNO,1,12),"
	    		+ "a.SUM_TXN_AMT,a.SUM_SETTLE_FEE1,a.SUM_SETTLE_AMT "
	    		+ "FROM (SELECT MCHT_NO,SUM(TXN_AMT) AS SUM_TXN_AMT,SUM(SETTLE_FEE1) AS SUM_SETTLE_FEE1,SUM(SETTLE_AMT) AS SUM_SETTLE_AMT FROM TBL_MCHNT_INFILE_DTL "+whereSql.toString()+" GROUP BY MCHT_NO) a "
	    		+ "left join TBL_MCHT_BASE_INF b on a.MCHT_NO = b.MCHT_NO left join TBL_MCHT_SETTLE_INF c on a.MCHT_NO = c.MCHT_NO "+whereSqlAmt.toString();
	    sql=sql+" order by a.MCHT_NO desc";
		return sql.toString();
	}
	
	
	
	private String startDate;
	private String endDate;
	private String queryAmtTransLow;
	private String queryAmtTransUp;
	private String queryMchtNoNm;
	private String queryBrhId;

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
	 * @return the queryBrhId
	 */
	public String getQueryBrhId() {
		return queryBrhId;
	}

	/**
	 * @param queryBrhId the queryBrhId to set
	 */
	public void setQueryBrhId(String queryBrhId) {
		this.queryBrhId = queryBrhId;
	}
	

}
