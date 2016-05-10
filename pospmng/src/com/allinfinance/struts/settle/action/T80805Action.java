package com.allinfinance.struts.settle.action;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import com.allinfinance.common.Constants;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.SysParamUtil;
import com.allinfinance.system.util.excelReport;

public class T80805Action extends BaseAction{

	private static final long serialVersionUID = 1L;
	private String mchtNo;
	private String brhId;
	private String startDate;
	private String endDate;
	private String pan;
	private String misc2T;

	@SuppressWarnings("unchecked")
	@Override
	protected String subExecute() throws Exception {
		String sql = getSql();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		List<Object[]> sumList = CommonFunction.getCommQueryDAO().findBySQLQuery(getSumSQL());
		dataList.addAll(sumList);

		DecimalFormat df = new DecimalFormat("0.00");
		if (dataList.size() > 0) {
			for (Object[] objects : dataList) {
				if (objects[2] != null && !"".equals(objects[2])) {
					objects[2] = df.format(Double.parseDouble(objects[2].toString())/100);//交易金额是以分为单位，所以要除100
					objects[2] = CommonFunction.moneyFormat(objects[2].toString());
				}
				if (objects[3] != null && !"".equals(objects[3])) {
					objects[3] = df.format(Double.parseDouble(objects[3].toString()));
					objects[3] = CommonFunction.moneyFormat(objects[3].toString());
				}
				if (objects[4] != null && !"".equals(objects[4])) {
					objects[4] = df.format(Double.parseDouble(objects[4].toString()));
					objects[4] = CommonFunction.moneyFormat(objects[4].toString());
				}
				/*20160329修改，任务285-去掉合作伙伴分润
				  if (objects[7] != null && !"".equals(objects[7])) {
					objects[7] = df.format(Double.parseDouble(objects[7].toString()));
					objects[7] = CommonFunction.moneyFormat(objects[7].toString());
				}*/
			}
		}

		//String[] title={"清算日期","清算商户","交易金额","手续费","清算金额","商户结算账号","合作伙伴","合作伙伴分润"};
		String[] title={"清算日期","清算商户","交易金额","手续费","清算金额","商户结算账号","合作伙伴"};//20160329修改，任务285-去掉合作伙伴分润
        String head="商户结算单汇总报表";
        String fileName=SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + head+ ".xls";
       
        HashMap<String, Object> map=new HashMap<String, Object>();
        
        map.put("dataList", dataList);
        map.put("title", title);
        map.put("fileName", fileName);
        map.put("head", head);
        map.put("isCount", false);
        //map.put("mapParm", mapParm);
        
        excelReport.reportDownloadTxnNew(map);
//		excelReport.reportDownloadTxn(dataList, title, os, head, false,null);
		return Constants.SUCCESS_CODE_CUSTOMIZE+fileName;
	}


	private String getSql() {
		//2016.02.25 郭宇 修改 检索条件放在sql最里层，否则统计数据不准确
		StringBuffer sql = new StringBuffer("select a.DATE_SETTLMT , a.MCHT_CD, ");
		sql.append("SUM (CASE WHEN A.TXN_NUM = '1101' THEN A.TRANS_AMT WHEN A.TXN_NUM = '1091' THEN A.TRANS_AMT WHEN A.TXN_NUM = '3101' THEN '-' || A.TRANS_AMT WHEN A.TXN_NUM = '3091' THEN '-' || A.TRANS_AMT WHEN A.TXN_NUM = '5151' THEN '-' || A.TRANS_AMT ELSE A.TRANS_AMT END) AS sum_amt,");
		sql.append("sum(a.MCHT_FEE_D) as MCHT_FEE_D, sum(a.MCHT_FEE_C) as MCHT_FEE_C ,a.BRH_INS_ID_CD from TBL_ALGO_CONFIRM_HIS h, TBL_ALGO_DTL a ");
		//20160429 郭宇 修改 提现类交易不显示
		StringBuffer whereSql = new StringBuffer(" where A.TXN_NUM <> '1211' AND A.TXN_NUM <> '1221' AND a.DATE_SETTLMT in h.DATE_SETTLMT and h.STATUS='0' ");
		if (isNotEmpty(startDate)) {
//			whereSql.append(" AND s.DATE_SETTLMT ").append(" >= ").append("'").append(startDate).append("'");
			whereSql.append(" AND a.DATE_SETTLMT ").append(" >= ").append("'").append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
//			whereSql.append(" AND s.DATE_SETTLMT").append(" <= ").append("'").append(endDate).append("'");
			whereSql.append(" AND a.DATE_SETTLMT").append(" <= ").append("'").append(endDate).append("'");
		}
		if (isNotEmpty(mchtNo)) {
//			whereSql.append(" AND s.MCHT_CD ='"+mchtNo+"' ");
			whereSql.append(" AND a.MCHT_CD ='"+mchtNo+"' ");
		}
		if (isNotEmpty(brhId)) {
//			whereSql.append(" and s.BRH_INS_ID_CD ='"+ brhId.trim()+ "' ");
			whereSql.append(" and a.BRH_INS_ID_CD ='"+ brhId.trim()+ "' ");
		}
		if (isNotEmpty(pan)) {
//			whereSql.append(" AND SETTLE_ACCT like '%"+ pan.trim()+ "%' ");
			sql.append("LEFT JOIN TBL_MCHT_SETTLE_INF si ON a.MCHT_CD = si.MCHT_NO ");
			whereSql.append(" AND si.SETTLE_ACCT like '%"+ pan.trim()+ "%' ");
		}
//		if (isNotEmpty(misc2T)) {
//			sql = "select s.DATE_SETTLMT,s.MCHT_CD||'-'||b.MCHT_NM,s.sum_amt,(s.MCHT_FEE_D-s.MCHT_FEE_C) as sum_fee,nvl(s.SETTL_AMT,0),trim(substr(si.SETTLE_ACCT,2,length(si.SETTLE_ACCT))) as SETTLE_ACCT,s.BRH_INS_ID_CD||'-'||c.BRH_NAME,s.acqInsAllot from (select a.DATE_SETTLMT , a.MCHT_CD, sum(a.TRANS_AMT) as sum_amt , sum(a.MCHT_FEE_D) as MCHT_FEE_D, sum(a.MCHT_FEE_C) as MCHT_FEE_C ,a.BRH_INS_ID_CD,sum(a.ACQ_INS_ALLOT_C-a.ACQ_INS_ALLOT_D) as acqInsAllot ,sum((a.TRANS_AMT/100)-(a.MCHT_FEE_D-a.MCHT_FEE_C)) as SETTL_AMT from TBL_ALGO_CONFIRM_HIS h, TBL_ALGO_DTL a "
//					+ "left join TBL_N_TXN b on substr(b.MISC_2,23,14) =a.TRANS_DATE||a.TRANS_DATE_TIME and a.txn_SSN=b.SYS_SEQ_NUM and b.retrivl_ref=a.retrivl_ref "
//					+ "where a.DATE_SETTLMT in h.DATE_SETTLMT and h.STATUS='0' and substr(b.MISC_2,117,1)='0' and substr(b.MISC_2,46,3)='00"+misc2T+"' "
//					+ " GROUP BY a.DATE_SETTLMT,a.MCHT_CD,a.BRH_INS_ID_CD ) s left join tbl_mcht_base_inf b on s.MCHT_CD=b.MCHT_NO left join TBL_MCHT_SETTLE_INF si on s.MCHT_CD=si.MCHT_NO left join TBL_BRH_INFO c on trim(s.BRH_INS_ID_CD)=c.BRH_ID ";
//		}else{
//			sql = "select s.DATE_SETTLMT,s.MCHT_CD||'-'||b.MCHT_NM,s.sum_amt,(s.MCHT_FEE_D-s.MCHT_FEE_C) as sum_fee,nvl(s.SETTL_AMT,0),trim(substr(si.SETTLE_ACCT,2,length(si.SETTLE_ACCT))) as SETTLE_ACCT,s.BRH_INS_ID_CD||'-'||c.BRH_NAME,s.acqInsAllot from (select a.DATE_SETTLMT , a.MCHT_CD, sum(a.TRANS_AMT) as sum_amt , sum(a.MCHT_FEE_D) as MCHT_FEE_D, sum(a.MCHT_FEE_C) as MCHT_FEE_C ,a.BRH_INS_ID_CD,sum(a.ACQ_INS_ALLOT_C-a.ACQ_INS_ALLOT_D) as acqInsAllot ,sum((a.TRANS_AMT/100)-(a.MCHT_FEE_D-a.MCHT_FEE_C)) as SETTL_AMT from TBL_ALGO_DTL a,TBL_ALGO_CONFIRM_HIS h where a.DATE_SETTLMT in h.DATE_SETTLMT and h.STATUS='0' "
//					+ " GROUP BY a.DATE_SETTLMT,a.MCHT_CD,a.BRH_INS_ID_CD ) s left join tbl_mcht_base_inf b on s.MCHT_CD=b.MCHT_NO left join TBL_MCHT_SETTLE_INF si on s.MCHT_CD=si.MCHT_NO left join TBL_BRH_INFO c on trim(s.BRH_INS_ID_CD)=c.BRH_ID ";
//		}
		if (isNotEmpty(misc2T)) {
			sql.append("left join TBL_N_TXN b on substr(b.MISC_2,23,14) =a.TRANS_DATE||a.TRANS_DATE_TIME and a.txn_SSN=b.SYS_SEQ_NUM and b.retrivl_ref=a.retrivl_ref ");
			whereSql.append(" and substr(b.MISC_2,117,1)='0' and substr(b.MISC_2,46,3)='00"+misc2T+"' ");
		}
		
//		sql = sql + whereSql.toString() + " order by s.DATE_SETTLMT desc,s.MCHT_CD ";
		String sqlSum = "select s.DATE_SETTLMT,s.MCHT_CD||'-'||b.MCHT_NM,s.sum_amt,(s.MCHT_FEE_D-s.MCHT_FEE_C) as sum_fee,NVL((s.sum_amt / 100) - (s.MCHT_FEE_D - s.MCHT_FEE_C), 0) as SETTL_AMT,trim(substr(si.SETTLE_ACCT,2,length(si.SETTLE_ACCT))) as SETTLE_ACCT,trim(c.CREATE_NEW_NO)||'-'||c.BRH_NAME from (";
		sqlSum = sqlSum + sql.toString() + whereSql.toString() + " GROUP BY a.DATE_SETTLMT,a.MCHT_CD,a.BRH_INS_ID_CD ) s left join tbl_mcht_base_inf b on s.MCHT_CD=b.MCHT_NO left join TBL_MCHT_SETTLE_INF si on s.MCHT_CD=si.MCHT_NO left join TBL_BRH_INFO c on trim(s.BRH_INS_ID_CD)=c.BRH_ID ";
		sqlSum = sqlSum + " order by s.DATE_SETTLMT desc,s.MCHT_CD ";
		
		return sqlSum;
	}

	private String getSumSQL(){
		StringBuffer sb = new StringBuffer();
		//sb.append("select '','合计',sum(sum_amt),sum(sum_fee),sum(settl_amt),'','',sum(acqInsAllot) from (");
		sb.append("select '','合计',sum(sum_amt),sum(sum_fee),sum(settl_amt),'','','' from (");//20160329修改，任务285-去掉合作伙伴分润
		sb.append(getSql());
		sb.append(" )");
		return sb.toString();
	}
	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

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
	
	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getMisc2T() {
		return misc2T;
	}

	public void setMisc2T(String misc2t) {
		misc2T = misc2t;
	}


}
