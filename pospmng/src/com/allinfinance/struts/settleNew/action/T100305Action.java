package com.allinfinance.struts.settleNew.action;

import java.util.List;

import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ExcelCreator;
import com.allinfinance.system.util.SysParamUtil;

public class T100305Action extends ReportBaseAction {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void reportAction() throws Exception {

		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(genSql());

		if (dataList.size() == 0) {
			writeNoDataMsg("没有找符合条件的记录，无法生成报表");
			return;
		}

		String rnName = "RN100305RN_";
		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK)
				+ rnName + operator.getOprId() + "_"
				+ CommonFunction.getCurrentDateTime() + ".xls";

		String titleName = SysParamUtil.getParam("RN100305RN");
		String coulmHeader[] = { "清算日期", "商户", "结算通道号", "结算开始日期", "结算截止日期",
				"正常结算金额", "差错扣减金额", "差错到账金额", "挂账金额", "结算金额","支付类型", "商户账户号", "商户账户名",
				"开户行号", "开户行名称", "地区名称" };
		ExcelCreator excelCreator = new ExcelCreator(titleName, coulmHeader,
				dataList);
		excelCreator.exportReport(fileName);

		writeUsefullMsg(fileName);
		return;
	}

	@Override
	protected String genSql() {

		StringBuffer whereSql = new StringBuffer(" where 1=1 ");

		if (isNotEmpty(startDate)) {
			whereSql.append(" AND DATE_STLM").append(" >= ").append("'")
					.append(startDate).append("'");
		}

		if (isNotEmpty(endDate)) {
			whereSql.append(" AND DATE_STLM").append(" <= ").append("'")
					.append(endDate).append("'");
		}
		if (isNotEmpty(queryMchtNoNm)) {
			whereSql.append(" AND MCHT_ID").append(" = ").append("'")
					.append(queryMchtNoNm.trim()).append("'");
		}
		String sql = "select DATE_STLM,MCHT_ID||'-'||MCHT_NM,(select b.channel_id||'-'||b.channel_name from TBL_PAY_CHANNEL_INFO b where b.channel_id=a.channel_id) as channel_id_nm,BEG_DATE,END_DATE,AMT_NORMAL,AMT_ERR_MINUS,AMT_ERR_ADD,AMT_HANGING,AMT_SETTLE, (case PAY_TYPE when '0' then '无需支付' when '1' then '大额支付' when '2' then '小额支付' end )PAY_TYPE,MCHT_ACCT_NO,MCHT_ACCT_NM,CNAPS_ID,CNAPS_NAME,AREA_NM  from TBL_SETTLE_DTL a "
				+ whereSql + " order by MCHT_ID ";

		String countSql = "select count(*) from TBL_SETTLE_DTL a " + whereSql;
		return sql;
	}

	private String startDate;
	private String endDate;
	private String queryMchtNoNm;

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

}
