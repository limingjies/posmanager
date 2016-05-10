package com.allinfinance.struts.settle.action;

import java.io.FileOutputStream;
import java.util.Date;

import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.InformationUtil;
import com.allinfinance.system.util.SysParamUtil;

public class T80217Action extends ReportBaseAction {
	private static final long serialVersionUID = 1L;

	@Override
	public void reportAction() throws Exception {

		reportType = "EXCEL";
		System.out.println(SysParamUtil.getParam("RN80217RN"));
		String rpName = "";
		title = InformationUtil.createTitles("V_", 3);
		rpName = "RN80217RN";
		data = reportCreator.process(genSql(), title);

		reportModel.setData(data);
		reportModel.setTitle(title);
		reportCreator
				.initReportData(getJasperInputSteam("T80217.jasper"),
						parameters, reportModel.wrapReportDataSource(),
						getReportType());

		if (Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK)
					+ rpName + "_" + operator.getOprId() + "_"
					+ CommonFunction.getCurrentDateTime() + ".xls";
		else if (Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK)
					+ rpName + "_" + operator.getOprId() + "_"
					+ CommonFunction.getCurrentDateTime() + ".pdf";

		outputStream = new FileOutputStream(fileName);

		reportCreator.exportReport(outputStream, SysParamUtil.getParam(rpName));

		writeUsefullMsg(fileName);
		return;
	}

	@Override
	protected String genSql() {

		StringBuffer sb = new StringBuffer();

		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(queryStartTm)) {
			whereSql.append(" AND a.DATE_SETTLMT >='").append(queryStartTm)
					.append("' ");
		}
		if (StringUtil.isNotEmpty(queryStartTm)) {
			whereSql.append(" AND a.DATE_SETTLMT <='").append(queryEndTm)
					.append("' ");
		}
		sb.append("select count(*),sum(abs(a.MCHT_AT_C-a.MCHT_AT_D)) as txn_amt,(select mchnt_tp ||' - '||descr from tbl_inf_mchnt_tp where mchnt_tp=mcc )mcc from TBL_ALGO_DTL a "
				+ "left join tbl_mcht_base_inf b on a.mcht_cd=b.mcht_no ");
		sb.append(whereSql);
		sb.append("group by mcc order by txn_amt desc");

		return sb.toString();
	}

	private String queryStartTm;

	private String queryEndTm;

	public String getQueryStartTm() {
		return queryStartTm;
	}

	public void setQueryStartTm(String queryStartTm) {
		this.queryStartTm = queryStartTm;
	}

	public String getQueryEndTm() {
		return queryEndTm;
	}

	public void setQueryEndTm(String queryEndTm) {
		this.queryEndTm = queryEndTm;
	}



}
