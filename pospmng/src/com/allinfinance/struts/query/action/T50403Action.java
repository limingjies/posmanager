package com.allinfinance.struts.query.action;

import java.io.FileOutputStream;

import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.InformationUtil;
import com.allinfinance.system.util.SysParamUtil;

public class T50403Action extends ReportBaseAction {
	private static final long serialVersionUID = 1L;
	@Override
	public void reportAction() throws Exception {

		reportType = "EXCEL";

		String rpName = "";
		title = InformationUtil.createTitles("V_", 4);
		rpName = "RN80216RN";
		data = reportCreator.process(genSql(), title);

		reportModel.setData(data);
		reportModel.setTitle(title);
		reportCreator
				.initReportData(getJasperInputSteam("T802166.jasper"),
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
		if (StringUtil.isNotEmpty(queryTxnKey) && !"请选择".equals(whereSql)) {
			whereSql.append("and substr(a.txn_key, 26, 12) in ('")
					.append(queryTxnKey).append("')");
		}
		sb.append("select (select substr(FIRST_MCHT_CD,4, 12)||' - '||trim(FIRST_MCHT_NM)  from TBL_FIRST_MCHT_INF where p.txnKey =  substr(FIRST_MCHT_CD,4, 12))txnKey　,"
				+ "settlmt ,BISHU,JIAOYIJINE from(select * from (select substr(a.txn_key, 26, 12) txnKey, substr(a.DATE_STLM,1,8) settlmt, count(*) as bishu, (sum(a.MCHT_AMT_CR) - sum(a.MCHT_AMT_DB)) as jiaoyijine from tbl_clear_dtl a ");
		sb.append(whereSql.toString());
		sb.append(" group by substr(a.txn_key, 26, 12), substr(a.DATE_STLM,1,8) order by substr(a.DATE_STLM,1,8)) o ) p");
		return sb.toString();
	}

	private String queryTxnKey;

	public String getQueryTxnKey() {
		return queryTxnKey;
	}

	public void setQueryTxnKey(String queryTxnKey) {
		this.queryTxnKey = queryTxnKey;
	}

}
