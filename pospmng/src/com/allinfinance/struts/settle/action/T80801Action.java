
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

public class T80801Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 9);
		String jasName="T80801.jasper";
		String rnName="RN80801RN_";
		String resName= SysParamUtil.getParam("RN80801RN");
		data = reportCreator.process(genSql(), title);

		if(data.length == 0) {
			writeNoDataMsg("没有找符合条件的记录，无法生成报表");
			return;
		}
		
		DecimalFormat df=new DecimalFormat("0.00");
    	Double amt4=0.00;
     	Double amt5=0.00;
     	Double amt6=0.00;
		
		for (int i = 0; i < data.length; i++) {
			if(StringUtil.isNotEmpty(data[i][0])){
				data[i][0]=CommonFunction.dateFormat(data[i][0].toString());
			}
			if(StringUtil.isNotEmpty(data[i][1])){
				data[i][1]=CommonFunction.dateFormat(data[i][1].toString());
			}
			if(StringUtil.isNotEmpty(data[i][4])){
				amt4+=Double.parseDouble(data[i][4].toString());
				data[i][4]=CommonFunction.moneyFormat(data[i][4].toString());
			}
			if(StringUtil.isNotEmpty(data[i][5])){
				amt5+=Double.parseDouble(data[i][5].toString());
				data[i][5]=CommonFunction.moneyFormat(data[i][5].toString());
			}	
			if(StringUtil.isNotEmpty(data[i][6])){
				amt6+=Double.parseDouble(data[i][6].toString());
				data[i][6]=CommonFunction.moneyFormat(data[i][6].toString());
			}	
			if(StringUtil.isNotEmpty(data[i][7])){
				data[i][7]=settleFlag(data[i][7].toString());
			}
		}

		parameters.put("P_1",StringUtil.isEmpty(startDate)?"":CommonFunction.dateFormat(startDate));
		parameters.put("P_2",StringUtil.isEmpty(endDate)?"":CommonFunction.dateFormat(endDate));
		parameters.put("P_3",StringUtil.isEmpty(actStartDate)?"":CommonFunction.dateFormat(actStartDate));
		parameters.put("P_4",StringUtil.isEmpty(actEndDate)?"":CommonFunction.dateFormat(actEndDate));
		parameters.put("P_5",CommonFunction.moneyFormat(df.format(amt4)));
		parameters.put("P_6",CommonFunction.moneyFormat(df.format(amt5)));
		parameters.put("P_7",CommonFunction.moneyFormat(df.format(amt6)));
		
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
		
		StringBuffer whereSql = new StringBuffer(" where  SETTLE_TYPE = '1' AND "
	    		+ "MCHT_NO in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "+operator.getBrhBelowId()+" ) ");

	   
	    if(isNotEmpty(startDate)) {
	    	whereSql.append(" AND DATE_SETTLMT ").append(" >= ").append("'").append(startDate).append("'");
	    }
	    if(isNotEmpty(endDate)) {
	    	whereSql.append(" AND DATE_SETTLMT").append(" <= ").append("'").append(endDate).append("'");
	    }
	    if(isNotEmpty(actStartDate)) {
	    	whereSql.append(" AND ACT_SETTLMT_DATE ").append(" >= ").append("'").append(actStartDate).append("'");
	    }
	    if(isNotEmpty(actEndDate)) {
	    	whereSql.append(" AND ACT_SETTLMT_DATE").append(" <= ").append("'").append(actEndDate).append("'");
	    }
	    if(isNotEmpty(queryMchtNoNm)) {
//	    	whereSql.append(" AND MCHT_NO").append(" = ").append("'").append(mchtNo).append("'");
//	    	whereSql.append(" AND MCHT_NO").append(" in ").append(CommonFunction.getBelowMchtByMchtNo(mchtNo));
	    	whereSql.append(" AND MCHT_NO  in (SELECT MCHT_NO FROM TBL_MCHT_BASE_INF  start with MCHT_NO =").append("'").append(queryMchtNoNm).append("' connect by prior  trim(MCHT_NO) = MCHT_GROUP_ID ) ");
	    }
	    if(isNotEmpty(querySettleFlag)) {
	    	whereSql.append(" AND a.SETTLE_FLAG").append(" = ").append("'").append(querySettleFlag).append("'");
	    }
	    
	    String sql = "SELECT DATE_SETTLMT,ACT_SETTLMT_DATE,(MCHT_NO||' - '||MCHT_NM) as mchtNoNm,"
	    		+ "(select b.BRH_ID||'-'||b.BRH_NAME from TBL_BRH_INFO b where trim(b.BRH_ID)=trim(a.BRH_CODE)) as bankNoName,"
	    		+ "TXN_AMT,SETTLE_FEE1,SETTLE_AMT,SETTLE_FLAG,"
	    		+ "a.CHANNEL_CD||(select '-'||d.FIRST_BRH_NAME from  TBL_FIRST_BRH_DEST_ID d where a.CHANNEL_CD=substr(d.FIRST_BRH_ID,3,2)) as channel_name "
	    		+ "from TBL_MCHNT_INFILE_DTL a ";
	    sql = sql + whereSql.toString();
	    sql=sql+" order by date_settlmt desc";
		return sql.toString();
	}
	
	
	
	private String startDate;
	private String endDate;
	private String actStartDate;
	private String actEndDate;
	private String queryMchtNoNm;
	private String querySettleFlag;

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

	/**
	 * @return the actStartDate
	 */
	public String getActStartDate() {
		return actStartDate;
	}

	/**
	 * @param actStartDate the actStartDate to set
	 */
	public void setActStartDate(String actStartDate) {
		this.actStartDate = actStartDate;
	}

	/**
	 * @return the actEndDate
	 */
	public String getActEndDate() {
		return actEndDate;
	}

	/**
	 * @param actEndDate the actEndDate to set
	 */
	public void setActEndDate(String actEndDate) {
		this.actEndDate = actEndDate;
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
	 * @return the querySettleFlag
	 */
	public String getQuerySettleFlag() {
		return querySettleFlag;
	}

	/**
	 * @param querySettleFlag the querySettleFlag to set
	 */
	public void setQuerySettleFlag(String querySettleFlag) {
		this.querySettleFlag = querySettleFlag;
	}
	
	//结算标志
	private String settleFlag(String val) {
		if("0".equals(val))
			return "未结算";
		else if("2".equals(val))
			return "已结算";
		else if("4".equals(val))
			return "暂缓结算";
		return val;
	}

}
