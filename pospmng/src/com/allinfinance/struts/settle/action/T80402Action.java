package com.allinfinance.struts.settle.action;

import java.util.HashMap;
import java.util.List;

import com.allinfinance.common.Constants;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.SysParamUtil;
import com.allinfinance.system.util.excelReport;

@SuppressWarnings("serial")
public class T80402Action extends BaseAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	// private TblMchntInfoDAO tblMchntInfoDAO = (TblMchntInfoDAO)
	// ContextUtil.getBean("MchntInfoDAO");
	private String brhId;
	private String mchntId;
	private String repType;
	private String startDate;
	private String endDate;

	@SuppressWarnings("unchecked")
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		String sql = getSql();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql);

		String[] title;
		if("1".equals(repType)){
			String[] title1={ "商户号", "商户名", "交易总金额", "成功笔数", "失败笔数"};
			title=title1;
			for (Object[] objects : dataList) {
				if (objects[2] != null && !"".equals(objects[2])) {
					objects[2] = CommonFunction.moneyFormat(objects[2].toString());
				}
			}
		}else if("2".equals(repType)){
			String[] title2={ "商户号", "商户名", "终端号",  "交易总金额", "成功笔数", "失败笔数"};
			title=title2;
			for (Object[] objects : dataList) {
				if (objects[3] != null && !"".equals(objects[3])) {
					objects[3] = CommonFunction.moneyFormat(objects[3].toString());
				}
			}
		}else{
			title=new String[6];
		}
		
		String head = "txnReport";
		String fileName = SysParamUtil
				.getParam(SysParamConstants.TEMP_FILE_DISK)
				+ head
				+ "-"
				+ operator.getOprId()
				+ "-"
				+ CommonFunction.getCurrentDateTime() + ".xls";

		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, String> mapParm = new HashMap<String, String>();
		mapParm.put("1", "起始日期_" + startDate);
		mapParm.put("2", "终止日期_" + endDate);
		mapParm.put("count", "2");

		map.put("dataList", dataList);
		map.put("title", title);
		map.put("fileName", fileName);
		map.put("head", "交易审计报表");
		map.put("isCount", false);
		map.put("mapParm", mapParm);

		excelReport.reportDownloadTxnNew(map);
		// excelReport.reportDownloadTxn(dataList, title, os, head,true,
		// df.format(amt));
		return Constants.SUCCESS_CODE_CUSTOMIZE + fileName;
	}

	/**
	 * @return 2014-1-24 上午09:12:41
	 */
	private String getSql() {
		// TODO Auto-generated method stub
		StringBuffer whereSql = new StringBuffer(
				" where  f.txn_num in ('1011','1091','1101','2011','2091','2101','2111','3011','3091','3101','4011','4091','4101','5151','5161') ");
		if (isNotEmpty(brhId)) {
			whereSql.append(" and f.card_accp_id in (select mcht_no from tbl_mcht_base_inf  where bank_no in (SELECT brh_id FROM TBL_BRH_INFO  start with brh_id ='"
					+ brhId.trim()
					+ "' connect by prior  BRH_ID = UP_BRH_ID  ) ) ");
		}
		if (isNotEmpty(mchntId)) {
			whereSql.append(" AND f.card_accp_id='").append(mchntId).append("' ");
		}

		if (isNotEmpty(startDate)) {
			whereSql.append(" AND substr(f.inst_date,1,8)").append(" >= ").append("'").append(startDate.replace("-", "")).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND substr(f.inst_date,1,8)").append(" <= ").append("'").append(endDate.replace("-", "")).append("'");
		}
		
		StringBuffer groupSql = new StringBuffer("  ");
		StringBuffer countWhereSql1 = new StringBuffer("  ");
		StringBuffer countWhereSql2 = new StringBuffer("  ");
		StringBuffer sumWhereSql = new StringBuffer("  ");
		StringBuffer termIdSql = new StringBuffer("  ");
		if("1".equals(repType)){
			groupSql.append(" group by f.card_accp_id ");
			countWhereSql1.append(" and e.card_accp_id=f.card_accp_id ");
			countWhereSql2.append(" and t.card_accp_id=f.card_accp_id ");
			sumWhereSql.append(" and u.card_accp_id=f.card_accp_id ");
			termIdSql.append("f.card_accp_id,").
				append("(select b.MCHT_NM from TBL_MCHT_BASE_INF b where b.MCHT_NO=f.card_accp_id ) as mcht_no_name,");
		}else if("2".equals(repType)){
			groupSql.append(" group by f.card_accp_term_id ");
			countWhereSql1.append(" and e.card_accp_term_id=f.card_accp_term_id ");
			countWhereSql2.append(" and t.card_accp_term_id=f.card_accp_term_id ");
			sumWhereSql.append(" and u.card_accp_term_id=f.card_accp_term_id ");
			termIdSql.append("(select mcht_cd from tbl_term_inf where term_id=f.card_accp_term_id) as mcht_cd, ")
				.append("(select b.MCHT_NM from TBL_MCHT_BASE_INF b where b.MCHT_NO in(select mcht_cd from tbl_term_inf where term_id=f.card_accp_term_id)) as mcht_name, ")
				.append("f.card_accp_term_id,");
		}
		
		String sql = "SELECT "
//				+ "f.card_accp_id,"
//				+ "(select b.MCHT_NM from TBL_MCHT_BASE_INF b where b.MCHT_NO=f.card_accp_id ) as mcht_no_name,"
				+ termIdSql.toString()
				+" (select nvl(sum( case when u.txn_num ='5151' then -TO_NUMBER(NVL(trim(u.amt_trans),0))/100 "
				+ "	else TO_NUMBER(NVL(trim(u.amt_trans),0))/100 end),0) as amt from TBL_N_TXN_HIS u "
				+ "	where u.txn_num in ('1101','1091','5151') and u.trans_state in ( '1','R') and u.REVSAL_FLAG!='1' and u.CANCEL_FLAG!='1'"+sumWhereSql.toString()+" )as amt_sum, "
				+ " (select count(1) from TBL_N_TXN_HIS e where e.resp_code='00' "+countWhereSql1.toString()+" )as succ_times,"
				+ " (select count(1) from TBL_N_TXN_HIS t where t.resp_code!='00' "+countWhereSql2.toString()+" )as fial_times "
				+ "  from TBL_N_TXN_HIS f   ";
		sql = sql + whereSql.toString()+groupSql;
		return sql;
	}

	private String getWhereSql() {
		// TODO Auto-generated method stub
		StringBuffer whereSql = new StringBuffer(
				" where  1=1 ");
		if (isNotEmpty(brhId)) {
			whereSql.append(" and f.card_accp_id in (select mcht_no from tbl_mcht_base_inf  where bank_no in (SELECT brh_id FROM TBL_BRH_INFO  start with brh_id ='"
					+ brhId.trim()
					+ "' connect by prior  BRH_ID = UP_BRH_ID  ) ) ");
		}
		if (isNotEmpty(mchntId)) {
			whereSql.append(" AND f.card_accp_id='").append(mchntId).append("' ");
		}

		if (isNotEmpty(startDate)) {
			whereSql.append(" AND substr(f.inst_date,1,8)").append(" >= ").append("'").append(startDate.replace("-", "")).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND substr(f.inst_date,1,8)").append(" <= ").append("'").append(endDate.replace("-", "")).append("'");
		}

		return whereSql.toString();
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

	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	public String getMchntId() {
		return mchntId;
	}

	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}

	public String getRepType() {
		return repType;
	}

	public void setRepType(String repType) {
		this.repType = repType;
	}


}
