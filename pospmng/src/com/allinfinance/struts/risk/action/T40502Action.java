package com.allinfinance.struts.risk.action;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;

import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.select.SelectMethod;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.InformationUtil;
import com.allinfinance.system.util.SysParamUtil;

public class T40502Action extends ReportBaseAction {

	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		reportType = "EXCEL";

		title = InformationUtil.createTitles("V_", 12);
		String jasName = "T40502.jasper";
		String rnName = "RN40502RN_";
		String resName = SysParamUtil.getParam("RN40502RN");
		data = reportCreator.process(genSql(), title);

		if (data.length == 0) {
			writeNoDataMsg("没有找符合条件的记录，无法生成报表");
			return;
		}

		for (int i = 0; i < data.length; i++) {
			if (StringUtil.isNotEmpty(data[i][3])) {
				data[i][3] = SelectMethod.getKind(null).get(data[i][3].toString());
			}

			if (StringUtil.isNotEmpty(data[i][4])) {
				if (data[i][4].toString().equals("1")) {
					data[i][4] = "是";
				} else if (data[i][4].toString().equals("0")) {
					data[i][4] = "否";
				} else {
					data[i][4] = "未知";
				}
			} else {
				data[i][4] = "未知";
			}

			if (StringUtil.isNotEmpty(data[i][6])) {
				data[i][6] = CommonFunction.moneyFormat(data[i][6].toString());
			}
			if (StringUtil.isNotEmpty(data[i][7])) {
				data[i][7] = CommonFunction.dateFormat(data[i][7].toString());
			}
			if (StringUtil.isNotEmpty(data[i][8])) {
				data[i][8] = CommonFunction.dateFormat(data[i][8].toString());
			}
			if (StringUtil.isNotEmpty(data[i][10])) {
				data[i][10] = getTxnStatus().get(data[i][10].toString());
			}
		}

		parameters.put("P_1", StringUtil.isEmpty(startDate) ? "" : CommonFunction.dateFormat(startDate.substring(0, 8)));
		parameters.put("P_2", StringUtil.isEmpty(endDate) ? "" : CommonFunction.dateFormat(endDate.substring(0, 8)));
		parameters.put("P_3", new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));

		reportModel.setData(data);
		reportModel.setTitle(title);

		reportCreator.initReportData(getJasperInputSteam(jasName), parameters, reportModel.wrapReportDataSource(), getReportType());

		if (Constants.REPORT_EXCELMODE.equals(reportType)) {
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rnName + operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		} else if (Constants.REPORT_PDFMODE.equals(reportType)) {
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rnName + operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		}

		outputStream = new FileOutputStream(fileName);
		reportCreator.exportReport(outputStream, resName);
		writeUsefullMsg(fileName);
		return;
	}

	@Override
	protected String genSql() {
		StringBuffer whereBuffer = new StringBuffer(" WHERE ATXN.CARD_ACCP_ID = MBI.MCHT_NO AND ATXN.TXN_NUM IN ('1101', '1091')");
		if (isNotEmpty(queryMchtNoNm)) {
			whereBuffer.append(" AND ATXN.CARD_ACCP_ID='").append(queryMchtNoNm).append("'");
		}
		if (isNotEmpty(queryRiskLvl)) {
			whereBuffer.append(" AND MBI.RISL_LVL='").append(queryRiskLvl).append("'");
		}
		if (isNotEmpty(startDate)) {
			startDate = startDate + "000000";
			whereBuffer.append(" AND ATXN.INST_DATE>='").append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			endDate = endDate + "2359559";
			whereBuffer.append(" AND ATXN.INST_DATE<='").append(endDate).append("'");
		}
		if (isNotEmpty(joinDate)) {
			whereBuffer.append(" AND SUBSTR(MBI.REC_CRT_TS, 0, 8) ='").append(joinDate).append("'");
		}
		if (isNotEmpty(queryPan)) {
			whereBuffer.append(" AND ATXN.PAN ='").append(queryPan).append("'");
		}
		if (isNotEmpty(queryIsCheat)) {
			whereBuffer.append(" AND ATXN.CHEAT_FLAG ='").append(queryIsCheat).append("'");
		}
		if (isNotEmpty(queryRule)) {
			whereBuffer.append(" AND SUBSTR(ATXN.ALARM_ID,9,3) ='").append(queryRule).append("'");
		}

		StringBuffer sqlBuffer = new StringBuffer()
				.append("SELECT MBI.MCHT_NO, MBI.MCHT_NM, (SELECT DISTINCT RL.RESVED FROM TBL_RISK_LVL RL WHERE RL.RISK_LVL=MBI.RISL_LVL) RISK_LVL, SUBSTR(ATXN.ALARM_ID,9,3) ALARM_ID, ATXN.CHEAT_FLAG, ")
				.append(" ATXN.PAN, TO_NUMBER(NVL(trim(ATXN.AMT_TRANS),0))/100, SUBSTR(ATXN.INST_DATE, 0, 8)  TXN_DATE, SUBSTR(ATXN.INST_DATE, 9, 14) TXN_TIME, ")
				.append(" (select TN.TXN_NAME from TBL_TXN_NAME TN where TN.TXN_NUM=ATXN.txn_num) TXN_NAME, ATXN.TRANS_STATE, ATXN.CARD_ACCP_TERM_ID ");
		String from = " FROM TBL_ALARM_TXN ATXN, TBL_MCHT_BASE_INF MBI";
		String orderBy = " ORDER BY ATXN.INST_DATE";
		return sqlBuffer.append(from).append(whereBuffer).append(orderBy).toString();
	}

	private String startDate;
	private String endDate;
	private String joinDate;
	private String queryMchtNoNm;
	private String queryRiskLvl;
	private String queryPan;
	private String queryRule;
	private String queryIsCheat;

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

	public String getQueryMchtNoNm() {
		return queryMchtNoNm;
	}

	public void setQueryMchtNoNm(String queryMchtNoNm) {
		this.queryMchtNoNm = queryMchtNoNm;
	}

	public String getQueryRiskLvl() {
		return queryRiskLvl;
	}

	public void setQueryRiskLvl(String queryRiskLvl) {
		this.queryRiskLvl = queryRiskLvl;
	}

	public String getQueryPan() {
		return queryPan;
	}

	public void setQueryPan(String queryPan) {
		this.queryPan = queryPan;
	}

	public String getQueryRule() {
		return queryRule;
	}

	public void setQueryRule(String queryRule) {
		this.queryRule = queryRule;
	}

	public String getQueryIsCheat() {
		return queryIsCheat;
	}

	public void setQueryIsCheat(String queryIsCheat) {
		this.queryIsCheat = queryIsCheat;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	private LinkedHashMap<String, String> getTxnStatus() {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		dataMap.put("0", "请求中");
		dataMap.put("1", "成功");
		dataMap.put("2", "卡拒绝");
		dataMap.put("3", "超时");
		dataMap.put("4", "主机拒绝");
		dataMap.put("5", "pin/mac错");
		dataMap.put("6", "前置拒绝");
		dataMap.put("7", "记账中");
		dataMap.put("8", "记账超时");
		dataMap.put("9", "交易确认 ");
		dataMap.put("R", "已退货");
		return dataMap;
	}

}
