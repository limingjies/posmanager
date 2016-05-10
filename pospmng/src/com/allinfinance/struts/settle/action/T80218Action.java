package com.allinfinance.struts.settle.action;

import java.io.FileOutputStream;

import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.InformationUtil;
import com.allinfinance.system.util.SysParamUtil;

public class T80218Action extends ReportBaseAction{
	private static final long serialVersionUID = 1L;
	
	@Override
	public void reportAction() throws Exception {
		
		reportType = "EXCEL";
		
		String rpName = "";
		title = InformationUtil.createTitles("V_", 2);
		rpName = "RN80218RN";
		data = reportCreator.process(genSql(), title);

		reportModel.setData(data);
		reportModel.setTitle(title);
		reportCreator.initReportData(getJasperInputSteam("T80218.jasper"), parameters, 
					reportModel.wrapReportDataSource(), getReportType());
		
        if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rpName + "_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rpName + "_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		System.out.println(SysParamUtil.getParam(rpName));
		
		reportCreator.exportReport(outputStream, SysParamUtil.getParam(rpName));
		
		writeUsefullMsg(fileName);
		return;
	}

	@Override
	protected String genSql() {
		
		StringBuffer sb = new StringBuffer();
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(queryMcc)) {
			whereSql.append(" AND mcc ='").append(queryMcc).append("' ");
		}		
			sb.append("select count(*),(select mchnt_tp ||' - '||descr from tbl_inf_mchnt_tp where mchnt_tp=mcc ) mcc from tbl_mcht_base_inf ");
			sb.append(whereSql.toString());
			sb.append(" group by mcc order by count(*) desc");
				
		return sb.toString();
	}
	
	private String queryMcc;
	
	

	
	public String getQueryMcc() {
		return queryMcc;
	}

	public void setQueryMcc(String queryMcc) {
		this.queryMcc = queryMcc;
	}

	private String reportName;


	
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	

}
