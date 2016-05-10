package com.allinfinance.struts.route.action;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import com.allinfinance.common.Constants;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ExcelReportCreator;
import com.allinfinance.system.util.SysParamUtil;


@SuppressWarnings("serial")
public class T110202Action extends  ReportBaseAction{

	/* (non-Javadoc)
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	private String queryCardBIN ;
	private String queryAmtTransLow ;
	private String queryAmtTransUp ;
	private String queryRouteId ;
	private String queryTransDate ;
	private String queryTransTime ;

	private String queryRetrivlRef ;
	private String queryTxnName;
	private String queryTransState ;
	private String queryRespCode ;
	private String queryBankName ;

	private String queryMchtTermId ;
	private String queryUpbrhTermId ;
	private String queryCardAccptId ;
	private String queryMchtUpBrhId;

	private String queryBrh1Id;
	private String queryBrh2Id ;
	private String queryBrh3Id ;
	private String queryAreaCode;
	
	@Override
	protected void reportAction() throws Exception {
		// TODO Auto-generated method stub
		ExcelReportCreator excelReportCreator=new ExcelReportCreator(); 
		
		List<String> sheetList=new ArrayList<String>();
		List<String> titleList=new ArrayList<String>();
		List<String[]> coulmHeaderList=new ArrayList<String[]>();
//		List<String[]> paramList=new ArrayList<String[]>();
		
		List<String> dataSqlList=new ArrayList<String>();
		List<String> sumDataSqlList=new ArrayList<String>();
		
		
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(getCountSql());
		if("0".equals(count)){
			writeNoDataMsg("没有找符合条件的记录，无法生成报表");
			return;
		}

		String rnName="RN110202RN_";
		String resName= SysParamUtil.getParam("RN110202RN");
		
        String[] coulmHeader={"卡号","发卡行","账户类型","交易状态","交易金额","交易时间","交易类型","本地商户",
        		"本地终端号","渠道商户","渠道终端号","支付渠道","业务","性质","路由规则号","交易参考号","终端返回码","交易通道","冲正流水号","冲正结果","撤销流水号","撤销结果"};
        sheetList.add(resName);
		titleList.add(resName);
		coulmHeaderList.add(coulmHeader);
		dataSqlList.add(getDataSql());
		sumDataSqlList.add(getSumSql(count));
		
		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rnName + operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		outputStream = new FileOutputStream(fileName);
		
		int leftFormat[]={4,7};
		excelReportCreator.setLeftFormat(leftFormat);
		excelReportCreator.setSheetList(sheetList);
		excelReportCreator.setTitleList(titleList);
//		excelReportCreator.setParamList(paramList);
		excelReportCreator.setCoulmHeaderList(coulmHeaderList);
		excelReportCreator.setDataList(excelReportCreator.processData(dataSqlList, coulmHeaderList));
		excelReportCreator.setSumList(excelReportCreator.processData(sumDataSqlList, coulmHeaderList));
		
		excelReportCreator.exportReport(outputStream);
		outputStream.close();
		writeUsefullMsg(fileName);
		return;
	}
	private String getDataSql() {
		String sql = " select pan,bank_name,card_type,trans_ret_sta,amt,inst_datetime,txn_name,mcht_name,mcht_term_id,mcht_brh_name,up_brh_term_id,brh_name_1,brh_name_2,brh_name_3,rule_id,retrivl_ref,resp_name,settle_brh_name,revsal_ssn,revsal_r,cancel_ssn,cancel_r "
				+ "   from (  "
				+ getSubSql()
				+ "    	    ) a "
				+  getWhereSql()
		        + " order by a.inst_datetime desc";
		return sql;
	}
	
	private String getCountSql() {
		String amtSql="select to_char(count(1)) "
				+ "   from (  "
				+ getSubSql()
				+ "    	    ) a "
				+  getWhereSql();
		return amtSql.toString();
	}
	
	private String getSumSql(String count) {
		String amtSql="select '','','','','','','','','','交易总数：','"+count+"','交易总金额：',"
        		+ 	" to_char("
        		+ 		"nvl(sum( case when a.txn_num ='5151' then -TO_NUMBER(NVL(trim(a.amt_trans),0))/100 "
        		+ 			"else TO_NUMBER(NVL(trim(a.amt_trans),0))/100 end ),0)"
        		+ 	",'99,999,999,990.99') as amt_total,'','','','','','','','','' "
				+ "   from (  "
				+ getSubSql()
				+ "    	    ) a "
				+  getWhereSql()
				+ " and a.txn_num in ('1101','1091','5151') and a.trans_state in ( '1','R') and a.REVSAL_FLAG!='1' and a.CANCEL_FLAG!='1' ";
		String amt = CommonFunction.getCommQueryDAO().findCountBySQLQuery(amtSql);
		return amtSql.toString();
	}
	
	private String getSubSql(){
		String subSql = "select  "
				+ "    	to_char(to_date(inst_date,'yyyymmddhh24miss'),'yyyy-mm-dd hh24:mi:ss') as inst_datetime, "
				+ "    	substr(f.inst_date,1,8) as txn_date, "
				+ "    	substr(f.inst_date,9,6) as txn_time, "
				+ "    	f.pan, "
				+ "    	f.card_accp_id, "
				+ "    	mb.mcht_no || '-'|| mb.mcht_nm as mcht_name, "
				+ "    	f.card_accp_term_id as mcht_term_id, "
				+ "    	f.retrivl_ref, "
				+ "    	to_char(case when f.txn_num in('5151','3101','2101','3091','2091') then -TO_NUMBER(NVL(trim(f.amt_trans),0))/100 else TO_NUMBER(NVL(trim(f.amt_trans),0))/100 end ,'99,999,999,990.99') as amt,f.amt_trans,  "
				+ "    	f.txn_num, tn.txn_name, "
				+ "    	f.trans_state,decode(f.trans_state, '0', '请求中', '1', '成功', '2','卡拒绝', '3', '超时', '4','主机拒绝','5', 'pin/mac错', '6', '前置拒绝', '7','记账中', '8', '记账超时','R', '已退货',f.trans_state) as trans_ret_sta,  "
				+ "    	f.resp_code,f.resp_code|| '-'||trim(rm.rsp_code_dsp) as resp_name, "
				+ "    	decode(substr(f.misc_2,104,2),'00','借记卡','01','贷记卡','02','准贷记卡','03','预付费卡','04','公务卡','05','未知卡','未知卡') card_type,  "
				+ "    	(case when substr(f.misc_2,104,2) in ('00','01','02','03','04') then '0' else '1' end) card_org,  "
				+ "    	(case when (substr(f.misc_2,104,2) in ('00','01','02','03','04')) and (nvl(length(trim(substr(f.misc_2,106,8))),0)=8) then substr(f.misc_2,106,8) else '*' end) bank_no, "
				+ "    	(case when (substr(f.misc_2,104,2) in ('00','01','02','03','04')) and (nvl(length(trim(substr(f.misc_2,106,8))),0)=8) then  "
				+ "    	       (select min(bb.CARD_DIS) from  tbl_bank_bin_inf bb where trim(bb.ins_id_cd) = substr(f.misc_2,106,8) )  "
				+ "    	          else  '*'  "
				+ "    	          end) bank_name,  "
				+ "    	ub.mcht_id_up as up_brh_id, ub.mcht_id_up || '-'||ub.mcht_name_up as mcht_brh_name, "
				+ "    	ub.term_id_up as up_brh_term_id, "
				+ "    	ru3.brh_id as brh_id_3,ru3.name as brh_name_3, "
				+ "    	ru2.brh_id as brh_id_2, ru2.name as brh_name_2, "
				+ "    	ru1.brh_id as brh_id_1, ru1.name as brh_name_1, "
				+ "    	rrr.rule_id, "
				+ "    	ub.area as up_brh_area_code, "
				+ "	    f.msg_dest_id||'-'||fb.FIRST_BRH_NAME as settle_brh_name,"
				+ "	    f.revsal_ssn,f.revsal_flag,decode(f.revsal_flag,'0','未冲正','已冲正') revsal_r,f.cancel_ssn,f.cancel_flag,decode(f.cancel_flag,'未撤销','已撤销') cancel_r"
				+ "    	   from tbl_n_txn f  "
				+ "			  left join TBL_FIRST_BRH_DEST_ID fb"
				+ "                on fb.DEST_ID = f.msg_dest_id "
				+ "    	      left join TBL_MCHT_BASE_INF mb  "
				+ "    	           on f.card_accp_id = mb.mcht_no "
				+ "    	      left join TBL_TXN_NAME tn " 
				+ "    	           on f.txn_num = tn.txn_num "
				+ "    	      left join tbl_rsp_code_map rm "
				+ "    	           on f.resp_code = trim(rm.dest_rsp_code) "
				+ "    	      left join gtwyadm.tbl_upbrh_mcht ub  "
				+ "    	           on f.up_mcht_id = ub.mcht_id_up and f.up_term_id = ub.term_id_up and f.up_brh_id3 = ub.brh_id3 "
				+ "    	      left join gtwyadm.Tbl_Route_Upbrh ru3 "
				+ "    	           on ru3.brh_id = f.up_brh_id3 and ru3.brh_level = '3' "
				+ "    	      left join gtwyadm.Tbl_Route_Upbrh ru2 "
				+ "    	           on ru2.brh_id = substr(f.up_brh_id3,1,8) and ru2.brh_level = '2'  "
				+ "    	      left join gtwyadm.Tbl_Route_Upbrh ru1 "
				+ "    	           on ru1.brh_id = substr(f.up_brh_id3,1,4) and ru1.brh_level = '1'  "
				+ "    	      left join gtwyadm.tbl_route_mchtg_detail rmd "
				+ "    	           on f.card_accp_id = rmd.mcht_id      "
				+ "    	      left join gtwyadm.Tbl_Route_Rule_Info rrr "
				+ "    	           on rrr.mcht_gid = rmd.mcht_gid and rrr.brh_id3 = f.up_brh_id3   "
				+ "    	where  rrr.orders is null or rrr.orders is not null and rrr.orders = (select min(rr.orders) minorder from gtwyadm.Tbl_Route_Rule_Info  rr "
				+ "    	           where f.up_brh_id3 = rr.brh_id3 and rr.mcht_gid = rmd.mcht_gid  "
				+ "    	           group by rr.mcht_gid,rr.brh_id3) ";
		return subSql;
	}
	private String getWhereSql() {
		StringBuffer whereSql = new StringBuffer(" where 1=1 "
				+ " and a.txn_num in ('1011','1091','1101','1111','1121','1141',"
				+ "'2011','2091','2101','2111','2121','3011','3091','3101',"
				+ "'3111','3121','4011','4091','4101','4111','4121','5151','5161') ");
		if (isNotEmpty(queryCardBIN)) {
			whereSql.append(" AND instr(trim(a.pan),").append(queryCardBIN).append(",1)=1");
		}
		if (isNotEmpty(queryAmtTransLow)) {
			whereSql.append(" AND TO_NUMBER(NVL(trim(a.amt_trans),0))/100 ").append(" >= ").append(queryAmtTransLow);
		}
		if (isNotEmpty(queryAmtTransUp)) {
			whereSql.append(" AND TO_NUMBER(NVL(trim(a.amt_trans),0))/100 ").append(" <= ").append(queryAmtTransUp);
		}
		if (isNotEmpty(queryRouteId)) {
			whereSql.append(" AND a.rule_id").append(" like '%").append(queryRouteId).append("%'");
		}
		if (isNotEmpty(queryTransDate)) {
			whereSql.append(" AND a.txn_date").append(" = '").append(queryTransDate).append("'");
		}
		if (isNotEmpty(queryTransTime)) {
			whereSql.append(" AND a.txn_time").append(" = '").append(queryTransTime).append("'");
		}
		
		
		if (isNotEmpty(queryRetrivlRef)) {
			whereSql.append(" AND a.retrivl_ref").append(" like ").append("'%").append(queryRetrivlRef).append("%'");
		}
		if (isNotEmpty(queryTxnName)) {
			whereSql.append(" AND a.txn_num").append(" = '").append(queryTxnName).append("'");
		}
		if (isNotEmpty(queryTransState)) {
			if (Constants.TransState.equals(queryTransState)) {
				whereSql.append(" AND a.trans_state  !='1'  and a.trans_state  !='0'  and a.trans_state  !='R' ");
			} else {
				whereSql.append(" AND a.trans_state").append(" = '").append(queryTransState).append("'");
			}
		}
		if (isNotEmpty(queryRespCode)) {
			whereSql.append(" AND a.RESP_CODE").append(" = '").append(queryRespCode).append("' ");
		}
		if (isNotEmpty(queryBankName)) {
			whereSql.append(" AND a.bank_no").append(" = '").append(queryBankName).append("' ");
		}
		
		if (isNotEmpty(queryMchtTermId)) {
			whereSql.append(" AND a.mcht_term_id").append(" like ").append("'%").append(queryMchtTermId).append("%'");
		}
		if (isNotEmpty(queryUpbrhTermId)) {
			whereSql.append(" AND a.up_brh_term_Id").append(" like ").append("'%").append(queryUpbrhTermId).append("%'");
		}
		if (isNotEmpty(queryCardAccptId)) {
			whereSql.append(" AND a.card_accp_id").append(" = '").append(queryCardAccptId).append("'");
		}
		if (isNotEmpty(queryMchtUpBrhId)) {
			whereSql.append(" AND a.up_brh_Id").append(" = '").append(queryMchtUpBrhId).append("'");
		}
		
		
		if (isNotEmpty(queryBrh1Id)) {
			whereSql.append(" AND a.brh_id_1").append(" = '").append(queryBrh1Id).append("'");
		}
		if (isNotEmpty(queryBrh2Id)) {
			whereSql.append(" AND a.brh_id_2").append(" = '").append(queryBrh2Id).append("'");
		}
		if (isNotEmpty(queryBrh3Id)) {
			whereSql.append(" AND a.brh_id_3").append(" = '").append(queryBrh3Id).append("'");
		}
		if (isNotEmpty(queryAreaCode)) {
			whereSql.append(" AND a.up_brh_area_code").append(" = '").append(queryAreaCode).append("'");
		}
		
		return whereSql.toString();
		
	}
	
	@Override
	protected String genSql() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getQueryCardBIN() {
		return queryCardBIN;
	}
	public void setQueryCardBIN(String queryCardBIN) {
		this.queryCardBIN = queryCardBIN;
	}
	public String getQueryAmtTransLow() {
		return queryAmtTransLow;
	}
	public void setQueryAmtTransLow(String queryAmtTransLow) {
		this.queryAmtTransLow = queryAmtTransLow;
	}
	public String getQueryAmtTransUp() {
		return queryAmtTransUp;
	}
	public void setQueryAmtTransUp(String queryAmtTransUp) {
		this.queryAmtTransUp = queryAmtTransUp;
	}
	public String getQueryRouteId() {
		return queryRouteId;
	}
	public void setQueryRouteId(String queryRouteId) {
		this.queryRouteId = queryRouteId;
	}
	public String getQueryTransDate() {
		return queryTransDate;
	}
	public void setQueryTransDate(String queryTransDate) {
		this.queryTransDate = queryTransDate;
	}
	public String getQueryTransTime() {
		return queryTransTime;
	}
	public void setQueryTransTime(String queryTransTime) {
		this.queryTransTime = queryTransTime;
	}
	public String getQueryRetrivlRef() {
		return queryRetrivlRef;
	}
	public void setQueryRetrivlRef(String queryRetrivlRef) {
		this.queryRetrivlRef = queryRetrivlRef;
	}
	public String getQueryTxnName() {
		return queryTxnName;
	}
	public void setQueryTxnName(String queryTxnName) {
		this.queryTxnName = queryTxnName;
	}
	public String getQueryTransState() {
		return queryTransState;
	}
	public void setQueryTransState(String queryTransState) {
		this.queryTransState = queryTransState;
	}
	public String getQueryRespCode() {
		return queryRespCode;
	}
	public void setQueryRespCode(String queryRespCode) {
		this.queryRespCode = queryRespCode;
	}
	public String getQueryBankName() {
		return queryBankName;
	}
	public void setQueryBankName(String queryBankName) {
		this.queryBankName = queryBankName;
	}
	public String getQueryMchtTermId() {
		return queryMchtTermId;
	}
	public void setQueryMchtTermId(String queryMchtTermId) {
		this.queryMchtTermId = queryMchtTermId;
	}
	public String getQueryUpbrhTermId() {
		return queryUpbrhTermId;
	}
	public void setQueryUpbrhTermId(String queryUpbrhTermId) {
		this.queryUpbrhTermId = queryUpbrhTermId;
	}
	public String getQueryCardAccptId() {
		return queryCardAccptId;
	}
	public void setQueryCardAccptId(String queryCardAccptId) {
		this.queryCardAccptId = queryCardAccptId;
	}
	public String getQueryMchtUpBrhId() {
		return queryMchtUpBrhId;
	}
	public void setQueryMchtUpBrhId(String queryMchtUpBrhId) {
		this.queryMchtUpBrhId = queryMchtUpBrhId;
	}
	public String getQueryBrh1Id() {
		return queryBrh1Id;
	}
	public void setQueryBrh1Id(String queryBrh1Id) {
		this.queryBrh1Id = queryBrh1Id;
	}
	public String getQueryBrh2Id() {
		return queryBrh2Id;
	}
	public void setQueryBrh2Id(String queryBrh2Id) {
		this.queryBrh2Id = queryBrh2Id;
	}
	public String getQueryBrh3Id() {
		return queryBrh3Id;
	}
	public void setQueryBrh3Id(String queryBrh3Id) {
		this.queryBrh3Id = queryBrh3Id;
	}
	public String getQueryAreaCode() {
		return queryAreaCode;
	}
	public void setQueryAreaCode(String queryAreaCode) {
		this.queryAreaCode = queryAreaCode;
	}
	

}
