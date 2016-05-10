package com.allinfinance.struts.query.action;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import com.allinfinance.common.Constants;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.SysParamUtil;
import com.allinfinance.system.util.excelReport;

@SuppressWarnings("serial")
public class T50110Action extends BaseAction {

	private String startDate;
	private String endDate;
	private String mchtNo;
	private String brhId;

	/*
	 * (non-Javadoc)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		String sql = getSql();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql);

		DecimalFormat df = new DecimalFormat("0.00");
		if (dataList.size() > 0) {
			for (Object[] objects : dataList) {

				if (objects[0] != null && !"".equals(objects[0])) {
					objects[0] = CommonFunction.dateFormat(objects[0]
							.toString());
				}

				if (objects[3] != null && !"".equals(objects[3])) {
					objects[3] = df.format(Double.parseDouble(objects[3]
							.toString()));
				}
				if (objects[4] != null && !"".equals(objects[4])) {
					objects[4] = df.format(Double.parseDouble(objects[4]
							.toString()));
				}

				if (objects[5] != null && !"".equals(objects[5])) {
					objects[5] = df.format(Double.parseDouble(objects[5]
							.toString()));
				}

			}
		}
		String[] title = { "清算日期", "批次号", "清算商户", "交易金额", "清算金额", "手续费", "交易通道" };
		String head = "新结算单报表";
		String fileName = SysParamUtil
				.getParam(SysParamConstants.TEMP_FILE_DISK)
				+ head
				+ "_"
				+ operator.getOprId()
				+ "_"
				+ CommonFunction.getCurrentDateTime() + ".xls";

		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, String> mapParm = new HashMap<String, String>();
		mapParm.put("1", "起始日期_" + CommonFunction.dateFormat(startDate));
		mapParm.put("2", "终止日期_" + CommonFunction.dateFormat(endDate));
		mapParm.put("count", "2");

		map.put("dataList", dataList);
		map.put("title", title);
		map.put("fileName", fileName);
		map.put("head", head);
		map.put("isCount", false);
		// map.put("mapParm", mapParm);

		excelReport.reportDownloadTxnNew(map);
		// excelReport.reportDownloadTxn(dataList, title, os, head, false,null);
		return Constants.SUCCESS_CODE_CUSTOMIZE + fileName;
	}

	/**
	 * @return 2014-2-8 上午09:38:19
	 * @author cuihailong
	 */
	private String getSql() {
		// TODO Auto-generated method stub
		StringBuffer whereSql = new StringBuffer(
				" where   "
						+ "MCHT_ID in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "
						+ operator.getBrhBelowId() + " ) ");
		if (isNotEmpty(startDate)) {
			whereSql.append(" AND DATE_STLM ").append(" >= ").append("'")
					.append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND DATE_STLM").append(" <= ").append("'")
					.append(endDate).append("'");
		}
		if (isNotEmpty(mchtNo)) {
			whereSql.append(
					" AND MCHT_ID  in (SELECT MCHT_NO FROM TBL_MCHT_BASE_INF  start with MCHT_NO =")
					.append("'")
					.append(mchtNo)
					.append("' connect by prior  trim(MCHT_ID) = MCHT_GROUP_ID ) ");
		}
		if (isNotEmpty(brhId)) {
			whereSql.append(" and trim(BRH_ID)  in (SELECT brh_id FROM TBL_BRH_INFO  start with brh_id ='"
					+ brhId.trim()
					+ "' connect by prior  BRH_ID = UP_BRH_ID   ) ");
		}

		String sql = "SELECT DATE_STLM,1,MCHT_ID||'-'||(SELECT MCHT_NM FROM TBL_MCHT_BASE_INF where MCHT_NO=MCHT_ID)MCHT_NM,STLM_AMT_TRANS,STLM_AMT,STLM_AMT_FEE,"
				+ "substr(a.INST_CODE,3,2)||(select '-'||d.FIRST_BRH_NAME from  TBL_FIRST_BRH_DEST_ID d where a.INST_CODE=d.FIRST_BRH_ID) as channel_name "
				+ " FROM TBL_MCHT_SETTLE_DTL a ";
		sql = sql + whereSql.toString();
		sql = sql + " ORDER BY DATE_STLM DESC";

		return sql;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
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
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the mchtNo
	 */
	public String getMchtNo() {
		return mchtNo;
	}

	/**
	 * @param mchtNo
	 *            the mchtNo to set
	 */
	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

}
