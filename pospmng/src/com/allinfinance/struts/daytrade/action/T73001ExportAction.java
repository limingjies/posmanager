package com.allinfinance.struts.daytrade.action;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ExcelReportCreator;
import com.allinfinance.system.util.SysParamUtil;

public class T73001ExportAction extends ReportBaseAction {
	private static final long serialVersionUID = 1L;

	@Override
	public void reportAction() throws Exception {
		ExcelReportCreator excelReportCreator=new ExcelReportCreator(); 
		List<String> sheetList=new ArrayList<String>();
		List<String> titleList=new ArrayList<String>();
		List<String[]> coulmHeaderList=new ArrayList<String[]>();	
		List<String> dataSqlList=new ArrayList<String>();
		
	StringBuffer countSql = new StringBuffer("SELECT COUNT(*) FROM TBL_ACCT_ERR t where 1=1 ");
	if (StringUtil.isNotEmpty(txn_dateStart)&& !StringUtil.isNotEmpty(txn_dateEnd) ) {
		countSql.append("and t.date_stlm ='").append(txn_dateStart)
				.append("'");
	}
	if (!StringUtil.isNotEmpty(txn_dateStart) && StringUtil.isNotEmpty(txn_dateEnd)) {
		countSql.append("and t.date_stlm <='").append(txn_dateEnd)
				.append("'");
	}
	if (StringUtil.isNotEmpty(txn_dateStart) && StringUtil.isNotEmpty(txn_dateEnd) ) {
		countSql.append("and t.date_stlm >='").append(txn_dateStart).append("'");
		countSql.append("and t.date_stlm <='").append(txn_dateEnd).append("'");
	}
	if (StringUtil.isNotEmpty(brhNo)) {
		countSql.append(" and t.brh_id ='").append(brhNo.trim())
				.append("'");
	}
	if (StringUtil.isNotEmpty(machNo)) {
		countSql.append(" and t.card_accp_id ='").append(machNo.trim())
				.append("'");
	}
	if (StringUtil.isNotEmpty(queryEflag)) {			
		countSql.append(" and t.ACCT_CHANGE_STAT =").append("'").append(queryEflag).append("' ");
	}else{
		countSql.append(" and  t.ACCT_CHANGE_STAT  is null ");
		}
	
	String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(countSql.toString());
	if("0".equals(count)){
		writeNoDataMsg("没有找符合条件的记录，无法生成报表");
		return;
	}
	
	String rnName="RN73001RN_";
	String resName= SysParamUtil.getParam("RN73001RN");
	
	String[] coulmHeader={"通道匹配值","清算日期","账户差错类型","交易名称","调账状态","调账应答码","调账日期",
			"机构号","商户号","交易日期","交易时间","系统跟踪号","检索参考号","通道名称","通道商户号",
			"通道终端号","通道检索参考号","终端号","终端流水号",
			"卡号","卡类型","交易金额","交易手续费","结算金额"};
    
	 sheetList.add(resName);
		titleList.add(resName);
		coulmHeaderList.add(coulmHeader);
		dataSqlList.add(genSql());
		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rnName + 
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		outputStream = new FileOutputStream(fileName);
		
		int leftFormat[]={8,9,20};
		excelReportCreator.setLeftFormat(leftFormat);
		excelReportCreator.setSheetList(sheetList);
		excelReportCreator.setTitleList(titleList);
		excelReportCreator.setCoulmHeaderList(coulmHeaderList);
		excelReportCreator.setDataList(excelReportCreator.processData(dataSqlList, coulmHeaderList));	
		excelReportCreator.exportReport(outputStream);
		outputStream.close();
		writeUsefullMsg(fileName);
		return;
	}

	@Override
	protected String genSql() {
		StringBuffer sb = new StringBuffer();
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(txn_dateStart)&& !StringUtil.isNotEmpty(txn_dateEnd) ) {
			whereSql.append("and t.date_stlm ='").append(txn_dateStart)
					.append("'");
		}
		if (!StringUtil.isNotEmpty(txn_dateStart) && StringUtil.isNotEmpty(txn_dateEnd)) {
			whereSql.append("and t.date_stlm <='").append(txn_dateEnd)
					.append("'");
		}
		if (StringUtil.isNotEmpty(txn_dateStart) && StringUtil.isNotEmpty(txn_dateEnd) ) {
			whereSql.append("and t.date_stlm >='").append(txn_dateStart).append("'");
			whereSql.append("and t.date_stlm <='").append(txn_dateEnd).append("'");
		}
		if (StringUtil.isNotEmpty(brhNo)) {
			whereSql.append(" and t.brh_id ='").append(brhNo.trim())
					.append("'");
		}
		if (StringUtil.isNotEmpty(machNo)) {
			whereSql.append(" and t.card_accp_id ='").append(machNo.trim())
					.append("'");
		}
		if (StringUtil.isNotEmpty(queryEflag)) {			
			whereSql.append(" and t.ACCT_CHANGE_STAT =").append("'").append(queryEflag).append("' ");
		}else{
			whereSql.append(" and t.ACCT_CHANGE_STAT is null ");
			}
		sb.append("select t.key_inst,to_char(to_date(t.DATE_STLM,'yyyymmdd'),'yyyy-mm-dd') as DATE_STLM,"
				+ " (case t.ACCT_ERR_TYPE when '1' then '冲账' when '2' then '补账' end) ACCT_ERR_TYPE, ");
		sb.append( " (select  b.txn_name from tbl_txn_name b where t.txn_num=b.txn_num) as txn_num,");
		sb.append( " (case t.ACCT_CHANGE_STAT when '1' then '已冲账' when '2' then '已补账' when '0' then '调账失败' when '' then '未调账' end) acct_change_stat, "
				+ "(case t.ACCT_CHANGE_RESP when '00' then '成功' when '99' then '失败' end) acct_change_resp,t.acct_change_dt,");
		sb.append(	" t.brh_id,");
		sb.append( " t.card_accp_id||'-'||(select mcht_nm from tbl_mcht_base_inf where mcht_no=t.card_accp_id) as card_accp_id , "  );
		sb.append( " to_char(to_date(t.txn_date,'yyyymmdd'),'yyyy-mm-dd') txn_date,to_char(to_date(t.txn_time,'hh24miss'),'hh24:mi:ss') txn_time,t.sys_seq_num,");
		sb.append( " t.retrivl_ref, (select first_brh_name from tbl_first_brh_dest_id where first_brh_id=t.inst_code) as inst_code,");
		sb.append( " t.inst_mcht_id,t.inst_term_id,t.inst_retrivl_ref,");	
		sb.append( " t.card_accp_term_id,t.term_sn,t.pan,t.card_type,"
				+ " to_char(nvl(TO_NUMBER(NVL(trim(t.amt_trans),0.00))/100,0),'99,999,999,990.99') as amt_trans,"
				+ " to_char(nvl(TO_NUMBER(NVL(trim(t.amt_trans_fee),0.00))/100,0),'99,999,999,990.99') as amt_trans_fee,"
				+ " to_char(nvl(TO_NUMBER(NVL(trim(t.amt_stlm),0.00))/100,0),'99,999,999,990.99') as amt_stlm ");
		sb.append(" from TBL_ACCT_ERR t ");
		sb.append(whereSql.toString());
		return sb.toString();
	}

	private String txn_dateEnd;
	private String txn_dateStart;
	private String brhNo;
	private String machNo;
	private String queryEflag;
	
	public String getQueryEflag() {
		return queryEflag;
	}

	public void setQueryEflag(String queryEflag) {
		this.queryEflag = queryEflag;
	}

	public String getTxn_dateEnd() {
		return txn_dateEnd;
	}

	public void setTxn_dateEnd(String txn_dateEnd) {
		this.txn_dateEnd = txn_dateEnd;
	}

	public String getTxn_dateStart() {
		return txn_dateStart;
	}

	public void setTxn_dateStart(String txn_dateStart) {
		this.txn_dateStart = txn_dateStart;
	}

	public String getBrhNo() {
		return brhNo;
	}

	public void setBrhNo(String brhNo) {
		this.brhNo = brhNo;
	}

	public String getMachNo() {
		return machNo;
	}

	public void setMachNo(String machNo) {
		this.machNo = machNo;
	}

}
