package com.allinfinance.struts.agentpay.action;

import java.io.FileOutputStream;

import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.InformationUtil;
import com.allinfinance.system.util.SysParamUtil;

public class T90601Action extends ReportBaseAction {

	@Override
	protected String genSql() {
		StringBuffer whereSql = new StringBuffer(" where 1=1 and t.RSP_CODE !='0000' and t.RSP_CODE !='4000'");
		if(isNotEmpty(batchId)){
			whereSql.append(" and t.BATCH_ID='"+batchId+"' ");
		}
		if(isNotEmpty(startEntDate)){
			whereSql.append(" and t.ENT_DATE ='"+startEntDate.split("T")[0].replace("-", "")+"' ");
		}
//		if(isNotEmpty(stopEntDate)){
//			whereSql.append(" and t.ENT_DATE <='"+stopEntDate.split("T")[0].replace("-", "")+"' ");
//		}
		if(isNotEmpty(mchtNo)){
			whereSql.append(" and t.MCHT_NO='"+mchtNo+"' ");
		}
		String sql = "SELECT t.BATCH_ID,t.SEQ,t.MCHT_NO,t.ACCT_NO,t.NAME,m.acct_no,t.AMT,t.RSP_CODE,t.RSP_DESC from TBL_RCV_PACK_DTL t left join tbl_mcht_info m on t.mcht_no = m.mcht_no "
				+whereSql.toString()+" order by t.BATCH_ID";
		return sql.toString();
	}

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "EXCEL";
		
		title = InformationUtil.createTitles("V_", 9);
		
		data = report_DSF_Creator.process(genSql(), title);
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		if (StringUtil.isNull(startEntDate)) {
			startEntDate = "全部";
		}
		parameters.put("P_1", startEntDate);
		report_DSF_Creator.initReportData(getJasperInputSteam("T90601.jasper"), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN90601RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN90601RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		report_DSF_Creator.exportReport(outputStream, SysParamUtil.getParam("RN90601RN"));
		
		writeUsefullMsg(fileName);
		return;

	}
	
	private String batchId;
	private String mchtNo;
	private String startEntDate;
//	private String stopEntDate;
	
	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getStartEntDate() {
		return startEntDate;
	}

	public void setStartEntDate(String startEntDate) {
		this.startEntDate = startEntDate;
	}

//	public String getStopEntDate() {
//		return stopEntDate;
//	}

//	public void setStopEntDate(String stopEntDate) {
//		this.stopEntDate = stopEntDate;
//	}

}
