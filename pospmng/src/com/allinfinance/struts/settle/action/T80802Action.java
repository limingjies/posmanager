
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

public class T80802Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 11);
		String jasName="T80802.jasper";
		String rnName="RN80802RN_";
		String resName= SysParamUtil.getParam("RN80802RN");
		data = reportCreator.process(genSql(), title);

		if(data.length == 0) {
			writeNoDataMsg("没有找符合条件的记录，无法生成报表");
			return;
		}
		
		DecimalFormat df=new DecimalFormat("0.00");
    	Double amt7=0.00;
     	Double amt8=0.00;
     	Double amt9=0.00;
		
		for (int i = 0; i < data.length; i++) {
			if(StringUtil.isNotEmpty(data[i][0])){
				data[i][0]=CommonFunction.dateFormat(data[i][0].toString());
			}
			if(StringUtil.isNotEmpty(data[i][7])){
				amt7+=Double.parseDouble(data[i][7].toString());
				data[i][7]=CommonFunction.moneyFormat(data[i][7].toString());
			}
			if(StringUtil.isNotEmpty(data[i][8])){
				amt8+=Double.parseDouble(data[i][8].toString());
				data[i][8]=CommonFunction.moneyFormat(data[i][8].toString());
			}	
			if(StringUtil.isNotEmpty(data[i][9])){
				amt9+=Double.parseDouble(data[i][9].toString());
				data[i][9]=CommonFunction.moneyFormat(data[i][9].toString());
			}
		}

		parameters.put("P_1",StringUtil.isEmpty(startDate)?"":CommonFunction.dateFormat(startDate));
		parameters.put("P_2",StringUtil.isEmpty(endDate)?"":CommonFunction.dateFormat(endDate));		
		parameters.put("P_8",CommonFunction.moneyFormat(df.format(amt7)));
		parameters.put("P_9",CommonFunction.moneyFormat(df.format(amt8)));
		parameters.put("P_10",CommonFunction.moneyFormat(df.format(amt9)));
		
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
		
		StringBuffer whereSql = new StringBuffer(" where a.MCHT_NO in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "+operator.getBrhBelowId()+" ) ");

		   
	    if(isNotEmpty(startDate)) {
	    	whereSql.append(" AND DATE_SETTLMT ").append(" >= ").append("'").append(startDate).append("'");
	    }
	    if(isNotEmpty(endDate)) {
	    	whereSql.append(" AND DATE_SETTLMT").append(" <= ").append("'").append(endDate).append("'");
	    }
	    if(isNotEmpty(queryAmtTransLow)) {
	    	whereSql.append(" AND NVL(a.TXN_AMT,0) ").append(" >= ").append("'").append(queryAmtTransLow).append("'");
	    }
	    if(isNotEmpty(queryAmtTransUp)) {
	    	whereSql.append(" AND NVL(a.TXN_AMT,0) ").append(" <= ").append("'").append(queryAmtTransUp).append("'");
	    }
	    if(isNotEmpty(queryMchtNoNm)) {
//	    	whereSql.append(" AND MCHT_NO").append(" = ").append("'").append(mchtNo).append("'");
//	    	whereSql.append(" AND MCHT_NO").append(" in ").append(CommonFunction.getBelowMchtByMchtNo(mchtNo));
	    	whereSql.append(" AND a.MCHT_NO  in (SELECT MCHT_NO FROM TBL_MCHT_BASE_INF  start with MCHT_NO =").append("'").append(queryMchtNoNm).append("' connect by prior  trim(MCHT_NO) = MCHT_GROUP_ID ) ");
	    }
		if (isNotEmpty(queryBrhId)) {
//			whereSql.append(" and f.card_accp_id in (select mcht_no from tbl_mcht_base_inf a where a.bank_no = '" + queryBrhId.trim() + "')");
			whereSql.append(" and a.MCHT_NO in (select mcht_no from tbl_mcht_base_inf  where bank_no in (SELECT brh_id FROM TBL_BRH_INFO  start with brh_id ='" + queryBrhId.trim() + "' connect by prior  BRH_ID = UP_BRH_ID  ) ) ");
		}
	    
	    String sql = "SELECT DATE_SETTLMT,(a.MCHT_NO||' - '||a.MCHT_NM) as MCHT_NO_NM,"
	    		+ "(select b.brh_id||' - '||b.brh_name from TBL_BRH_INFO b where b.brh_id = trim(BRH_CODE)) as brh_id,"
	    		+ "MCHT_ACCT,ACCT_NM,c.SETTLE_BANK_NM,substr(c.OPEN_STLNO,1,12),TXN_AMT,SETTLE_FEE1,SETTLE_AMT,"
	    		+ "a.CHANNEL_CD||(select '-'||d.FIRST_BRH_NAME from  TBL_FIRST_BRH_DEST_ID d where a.CHANNEL_CD=substr(d.FIRST_BRH_ID,3,2)) as channel_name "
	    		+ "FROM TBL_MCHNT_INFILE_DTL a left join TBL_MCHT_SETTLE_INF c on a.MCHT_NO = c.MCHT_NO ";
	    sql = sql + whereSql.toString();
	    sql=sql+" order by date_settlmt desc";
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
