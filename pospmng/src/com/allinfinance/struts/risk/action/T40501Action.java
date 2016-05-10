package com.allinfinance.struts.risk.action;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.InformationUtil;
import com.allinfinance.system.util.SysParamUtil;

public class T40501Action extends ReportBaseAction {

	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		reportType = "EXCEL";

		title = InformationUtil.createTitles("V_", 13);
		String jasName = "T40501.jasper";
		String rnName = "RN40501RN_";
		String resName = SysParamUtil.getParam("RN40501RN");
		data = reportCreator.process(genSql(), title);

		if (data.length == 0) {
			writeNoDataMsg("没有找符合条件的记录，无法生成报表");
			return;
		}

		for (int i = 0; i < data.length; i++) {
			if (StringUtil.isNotEmpty(data[i][3]) && StringUtil.isNotEmpty(data[i][9])) {
				data[i][11] = ((BigDecimal) data[i][9]).divide((BigDecimal) data[i][3], 4, RoundingMode.HALF_UP);
			}
			if (StringUtil.isNotEmpty(data[i][3])) {
				data[i][3] = CommonFunction.moneyFormat(data[i][3].toString());
			}
			if (StringUtil.isNotEmpty(data[i][9])) {
				data[i][9] = CommonFunction.moneyFormat(data[i][9].toString());
			}

			if (StringUtil.isNotEmpty(data[i][4]) && StringUtil.isNotEmpty(data[i][10])) {
				data[i][12] = ((BigDecimal) data[i][10]).divide((BigDecimal) data[i][4], 4, RoundingMode.HALF_UP);
			}

			if (StringUtil.isNull(data[i][5])) {
				data[i][5] = 0;
			}
			if (StringUtil.isNull(data[i][6])) {
				data[i][6] = 0;
			}
			if (StringUtil.isNull(data[i][7])) {
				data[i][7] = 0;
			}
			if (StringUtil.isNull(data[i][8])) {
				data[i][8] = 0;
			}
			if (StringUtil.isNull(data[i][9])) {
				data[i][9] = 0;
			}
			if (StringUtil.isNull(data[i][10])) {
				data[i][10] = 0;
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
		StringBuffer sqlBuffer = new StringBuffer()
				.append("SELECT MBI.MCHT_NO, MBI.MCHT_NM, (SELECT DISTINCT RL.RESVED FROM TBL_RISK_LVL RL WHERE RL.RISK_LVL=MBI.RISL_LVL) RISK_LVL,")
				.append(" (SELECT SUM(NVL(TO_NUMBER(TRIM(t.AMT_TRANS))/100, 0)) FROM TBL_N_TXN_HIS t WHERE t.CARD_ACCP_ID = MBI.MCHT_NO AND t.TXN_NUM IN ('1101', '1091') $STARTDATE$ $ENDDATE$ GROUP BY t.CARD_ACCP_ID) TXN_AMT,")
				.append(" (SELECT NVL(COUNT(*), 0) FROM TBL_N_TXN_HIS t WHERE t.CARD_ACCP_ID = MBI.MCHT_NO AND t.TXN_NUM IN ('1101', '1091') $STARTDATE$ $ENDDATE$ GROUP BY t.CARD_ACCP_ID) TXN_CNT,")
				.append(" (SELECT NVL(COUNT(AM.CARD_ACCP_ID),0) FROM TBL_ALARM_MCHT AM, TBL_RISK_ALARM t WHERE AM.CARD_ACCP_ID = MBI.MCHT_NO AND t.ALARM_ID = AM.ALARM_ID AND t.ALARM_SEQ = AM.ALARM_SEQ AND SUBSTR(AM.ALARM_ID,9,3) IN (SELECT DISTINCT RI.SA_MODEL_KIND FROM TBL_RISK_INF RI WHERE misc='3') $STARTRISKDATE$ $ENDRISKDATE$ GROUP BY AM.CARD_ACCP_ID) FRT_ALM_CNT,")
				.append(" (SELECT NVL(COUNT(AM.CARD_ACCP_ID),0) FROM TBL_ALARM_MCHT AM, TBL_RISK_ALARM t WHERE AM.CARD_ACCP_ID = MBI.MCHT_NO AND t.ALARM_ID = AM.ALARM_ID AND t.ALARM_SEQ = AM.ALARM_SEQ AND SUBSTR(AM.ALARM_ID,9,3) IN (SELECT DISTINCT RI.SA_MODEL_KIND FROM TBL_RISK_INF RI WHERE misc='2') $STARTRISKDATE$ $ENDRISKDATE$ GROUP BY AM.CARD_ACCP_ID) SED_ALM_CNT,")
				.append(" (SELECT NVL(COUNT(AM.CARD_ACCP_ID),0) FROM TBL_ALARM_MCHT AM, TBL_RISK_ALARM t WHERE AM.CARD_ACCP_ID = MBI.MCHT_NO AND t.ALARM_ID = AM.ALARM_ID AND t.ALARM_SEQ = AM.ALARM_SEQ AND SUBSTR(AM.ALARM_ID,9,3) IN (SELECT DISTINCT RI.SA_MODEL_KIND FROM TBL_RISK_INF RI WHERE misc='1') $STARTRISKDATE$ $ENDRISKDATE$ GROUP BY AM.CARD_ACCP_ID) THD_ALM_CNT,")
				.append(" (SELECT NVL(COUNT(AM.CARD_ACCP_ID),0) FROM TBL_ALARM_MCHT AM, TBL_RISK_ALARM t WHERE AM.CARD_ACCP_ID = MBI.MCHT_NO AND t.ALARM_ID = AM.ALARM_ID AND t.ALARM_SEQ = AM.ALARM_SEQ $STARTRISKDATE$ $ENDRISKDATE$ GROUP BY AM.CARD_ACCP_ID) ALL_ALM_CNT,")
				.append(" (SELECT SUM(NVL(TO_NUMBER(TRIM(t.AMT_TRANS))/100, 0)) FROM TBL_ALARM_TXN t, TBL_RISK_ALARM RA WHERE t.CARD_ACCP_ID = MBI.MCHT_NO AND t.TXN_NUM IN ('1101', '1091') AND t.ALARM_ID = RA.ALARM_ID AND t.ALARM_SEQ = RA.ALARM_SEQ AND RA.CHEAT_FLAG = 1 $STARTDATE$ $ENDDATE$ GROUP BY t.CARD_ACCP_ID) CHEAT_AMT,")
				.append(" (SELECT NVL(COUNT(t.CARD_ACCP_ID),0) FROM TBL_ALARM_TXN t, TBL_RISK_ALARM RA WHERE t.CARD_ACCP_ID = MBI.MCHT_NO AND t.TXN_NUM IN ('1101', '1091') AND t.ALARM_ID=RA.ALARM_ID AND t.ALARM_SEQ=RA.ALARM_SEQ AND RA.CHEAT_FLAG=1 $STARTDATE$ $ENDDATE$ GROUP BY t.CARD_ACCP_ID) CHEAT_CNT,")
				.append(" 0, 0");

		String from = " FROM TBL_MCHT_BASE_INF MBI";
		String orderBy = " ORDER BY MBI.MCHT_NO";

		StringBuffer whereBuffer = new StringBuffer(
				" WHERE MBI.MCHT_NO in (SELECT DISTINCT t.CARD_ACCP_ID FROM TBL_N_TXN_HIS t WHERE t.TXN_NUM IN ('1101', '1091') $STARTDATE$ $ENDDATE$) ");
		if (isNotEmpty(queryMchtNoNm)) {
			whereBuffer.append(" AND MBI.MCHT_NO='").append(queryMchtNoNm).append("'");
		}

		if (isNotEmpty(queryRiskLvl)) {
			whereBuffer.append(" AND MBI.RISL_LVL='").append(queryRiskLvl).append("'");
		}
		String sql = sqlBuffer.append(from).append(whereBuffer).append(orderBy).toString();

		if (isNotEmpty(startDate)) {
			String andStartDate = " AND t.INST_DATE>='" + startDate + "000000'";
			String andStartRiskDate = " AND t.RISK_DATE >='" + startDate + "'";

			sql = sql.replace("$STARTDATE$", andStartDate);
			sql = sql.replace("$STARTRISKDATE$", andStartRiskDate);
		} else {
			sql = sql.replace("$STARTDATE$", "");
			sql = sql.replace("$STARTRISKDATE$", "");
		}

		if (isNotEmpty(endDate)) {
			String andEndDate = " AND t.INST_DATE<='" + endDate + "2359559'";
			String andEndRiskDate = " AND t.RISK_DATE <= '" + endDate + "'";

			sql = sql.replace("$ENDDATE$", andEndDate);
			sql = sql.replace("$ENDRISKDATE$", andEndRiskDate);
		} else {
			sql = sql.replace("$ENDDATE$", "");
			sql = sql.replace("$ENDRISKDATE$", "");
		}
		return sql.toString();
	}

	private String startDate;
	private String endDate;
	private String queryMchtNoNm;
	private String queryRiskLvl;

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

}
