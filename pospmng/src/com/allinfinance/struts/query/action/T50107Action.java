package com.allinfinance.struts.query.action;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ExcelReportCreator;
import com.allinfinance.system.util.SysParamUtil;


@SuppressWarnings("serial")
public class T50107Action extends   ReportBaseAction{

	/* (non-Javadoc)
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
//	private TblMchntInfoDAO tblMchntInfoDAO = (TblMchntInfoDAO) ContextUtil.getBean("MchntInfoDAO");
	private String queryCardAccptId;
	private String queryBrhId;
	private String queryCardAccpTermId;
	private String queryPan;
	//private String queryTermSsn;
	private String queryAmtTransLow;
	private String queryAmtTransUp;
	private String respName;//交易应答
	private String acctType;//账户类型
	private String termType;//终端类型
	private String queryTxnName;
	private String queryTransState;
	private String startDate;
	private String endDate;
/*	private String querySettleBrh;
	private String queryRevsalSsn;*/
	private String queryRetrivlRef;
	private String queryRevsalFlag;
	
//	private String queryCancelSsn;
	private String queryCancelFlag;
	/*private String queryRespCode;
	private String querySysSeqNum;*/
	private String queryBankName;
	private String queryCardOrg;
	private String queryMcntGroup;
	private String queryCardAccpTermName;
	private String queryAuthNo;
	private String queryMchtUp;
	private String queryTermSsn;
	private String querySysSeqNum;
	
	private String querySettleType;
	
	private String whereSql1="";
	@Override
	protected void reportAction() throws Exception {
		StringBuffer whereSql=new StringBuffer("");
		if (isNotEmpty(queryTermSsn)) {
		whereSql.append("  and f.term_ssn").append(" = ").append("'").append(queryTermSsn).append("' ");
	}
	if (isNotEmpty(querySysSeqNum)) {
		whereSql.append(" AND f.sys_seq_num").append(" = ").append("'").append(querySysSeqNum).append("' ");
	}
	whereSql1="where 1=1"+whereSql.toString();
		ExcelReportCreator excelReportCreator=new ExcelReportCreator(); 
		
		List<String> sheetList=new ArrayList<String>();
		List<String> titleList=new ArrayList<String>();
		List<String[]> coulmHeaderList=new ArrayList<String[]>();
		List<String[]> paramList=new ArrayList<String[]>();
		
		List<String> dataSqlList=new ArrayList<String>();
		List<String> sumDataSqlList=new ArrayList<String>();
		
		
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(getCountSql());
		if("0".equals(count)){
			writeNoDataMsg("没有找符合条件的记录，无法生成报表");
			return;
		}

		String rnName="RN50107RN_";
		String resName= SysParamUtil.getParam("RN50107RN");
		
//        String[] coulmHeader={"交易日期","交易时间","卡号","账户类型","商户编号","商户名称","终端号","终端类型","系统流水号",
//        		"所属机构","交易通道","交易类型","交易结果","交易应答","交易金额","冲正结果","撤销结果"};
        String[] coulmHeader={"卡号","发卡行","卡组织","账户类型","交易日期","交易时间","交易类型","交易金额","交易结果","交易应答",
        		"冲正结果","终端号","终端名称","终端类型","商户号","商户名称","合作伙伴","结算类型","参考号","系统流水号","终端流水号","渠道商户号",
        		"渠道商户名称","交易通道","通道检索参考号","账户系统应答信息"};

        String[] param=new String[2];
		param[0]="起始日期："+(StringUtil.isEmpty(startDate)?"":CommonFunction.dateFormat(startDate));
		param[1]="结束日期："+(StringUtil.isEmpty(endDate)?"":CommonFunction.dateFormat(endDate));
        
        sheetList.add(resName);
		titleList.add(resName);
		coulmHeaderList.add(coulmHeader);
		dataSqlList.add(getDataSql());
		sumDataSqlList.add(getSumSql(count));
		paramList.add(param);
		
		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rnName + 
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		outputStream = new FileOutputStream(fileName);
		
		int leftFormat[]={0,1,12,15,16};
		excelReportCreator.setLeftFormat(leftFormat);
		excelReportCreator.setSheetList(sheetList);
		excelReportCreator.setTitleList(titleList);
		excelReportCreator.setParamList(paramList);
		excelReportCreator.setCoulmHeaderList(coulmHeaderList);
		excelReportCreator.setDataList(excelReportCreator.processData(dataSqlList, coulmHeaderList));
		excelReportCreator.setSumList(excelReportCreator.processData(sumDataSqlList, coulmHeaderList));
		
		excelReportCreator.exportReport(outputStream);
		outputStream.close();
		writeUsefullMsg(fileName);
		return;
	}
	
	private String getDataSql() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//获取用户信息
		Operator operator = (Operator)request.getSession().getAttribute(Constants.OPERATOR_INFO);
		//upd by yww 160414 实现禅道316任务--交易查询卡号隐藏--start
		String userSql = " SELECT OPR_ID, CRT_OPR_ID, CRT_TIME FROM TBL_ALL_CARD_NO_USER where opr_id= '"+operator.getOprId()+"' ";
		//查询用户ID在权限表是否存在，存在则可以查看全卡号，否则查看短卡号
		@SuppressWarnings("unchecked")
		List<Object[]> userList = CommonFunction.getCommQueryDAO().findBySQLQuery(userSql);
		String panSql = "";
		if (userList.size()>0) {
			panSql = " f.pan, ";
		}else {
			panSql = " rpad(substr(f.pan,1,6),length(trim(f.pan))-4,'*')||substr(trim(f.pan),length(trim(f.pan))-3,4) pan, ";
		}
		//upd by yww 160414 实现禅道316任务--交易查询卡号隐藏--end
		
		String sql = "select pan, bank_name, card_org, acct_type, txn_date, txn_time, txn_num_name, amt, trans_ret_sta, resp_name, revsal_flag_name, card_accp_term_id, term_name, term_tp, card_accp_id, mcht_name, brh_id_name, settle_type, retrivl_ref, sys_seq_num, term_ssn, mcht_brh_id, mcht_brh_name, settle_brh_name, msg_src_retrivl_id, cancel_flag_name "
		    	+ "   from ( "
		    	+ "			  SELECT f.inst_date,f.txn_num,f.retrivl_ref,f.authr_id_resp, "
		    	+ "			         to_char(to_date(substr(f.inst_date, 1, 8), 'yyyy-mm-dd'), 'yyyy-mm-dd') as txn_date, "
		    	+ "			         to_char(to_date(substr(f.inst_date, 9, 6), 'hh24:mi:ss'), 'hh24:mi:ss') as txn_time, "
		    	+ panSql		   + "f.card_accp_id, trim(m.MCHT_NM) as mcht_name, "
		    	+ "					 f.up_f37 msg_src_retrivl_id,f.term_ssn,"
		    	+ "			         f.card_accp_term_id,f.sys_seq_num, "
		    	+ "			         b.brh_id || ' - ' || b.brh_name AS brh_id_name, "
		    	+ "			         f.tdestid || d.first_brh_name as settle_brh_name, "
		    	+ "			         t.TXN_NAME  as txn_num_name, f.trans_state,"
		    	//20160504 guoyu upd 成功，请求中，交易确认，已退货以外都算失败；选择成功，可检索出交易确认
//		    	+ "			         decode(f.trans_state, '0', '请求中', '1', '成功', '2','卡拒绝', '3', '超时', '4','主机拒绝','5', 'pin/mac错', '6', '前置拒绝', '7','记账中', '8', '记账超时', '9', '交易确认 ','R', '已退货',f.trans_state) as trans_ret_sta, "
				+ "			         decode(f.trans_state, '0', '请求中', '1', '成功', '9', '交易确认 ','R', '已退货','失败') as trans_ret_sta, "
		    	+ "			         f.RESP_CODE ||(select '-' || trim(rsp_code_dsp) from tbl_rsp_code_map where trim(dest_rsp_code) = f.RESP_CODE and SRC_ID='2801') as resp_name, "
		    	+ "			         (case when f.txn_num in ('5151', '3101', '2101', '3091', '2091') then -TO_NUMBER(NVL(trim(f.amt_trans), 0)) / 100 else TO_NUMBER(NVL(trim(f.amt_trans), 0)) / 100 end) as amt, "
		    	+ "			         f.revsal_flag,(case when f.revsal_flag = '0' then '未冲正' WHEN f.revsal_flag = '1' THEN '已冲正' END) AS revsal_flag_name, "
		    	+ "			         f.cancel_flag,(case when f.cancel_flag = '0' then '未撤销' WHEN f.cancel_flag = '1' THEN '已撤销' END) AS cancel_flag_name, "
		    	+ "			         decode((select n.mcht_group_flag  from TBL_MCHT_BASE_INF n where MCHT_NO=f.card_accp_id),'0','0','1') mcht_group, "
		    	+ "			         substr(f.misc_2,104,2) card_type,  "
		    	+ "			         (case when substr(f.misc_2,104,2) in ('00','01','02','03','04') then '银联' else '外卡' end) card_org,  "
		    	+ "			         (case when (substr(f.misc_2,104,2) in ('00','01','02','03','04')) and (nvl(length(trim(substr(f.misc_2,106,8))),0)=8) then substr(f.misc_2,106,8) else '*' end) bank_no, "
		    	+ "			         (case when (substr(f.misc_2,104,2) in ('00','01','02','03','04')) and (nvl(length(trim(substr(f.misc_2,106,8))),0)=8) then  "
		    	+ "			                     (select min(bb.CARD_DIS) from  tbl_bank_bin_inf bb where trim(bb.ins_id_cd) = substr(f.misc_2,106,8) )  "
		    	+ "			                     else  '*' end) bank_name,  "
		    	+ "			          u.mcht_id_up mcht_brh_id,u.mcht_name_up mcht_brh_name,u.term_id_up term_brh_id,u.brh_id3, "
		    	+ "			         (select term.misc_2 from tbl_term_inf term where term.term_id = rpad(f.card_accp_term_id,12,' ')) term_name ,"
		    	+ "                  (case substr(f.misc_2,117,1) when '0' then 'T+'||to_number(substr(f.misc_2,46,3)) when '1' then  decode(substr(f.misc_2,118,1),'0','周结','1','月结','2','季结',null) else null end) settle_type "
		    	+ "               ,f.RESP_CODE"
		    	+ "               ,(select case term.TERM_TP when '0' then '普通POS' when '1' then '财务POS' when '2' then '签约POS' when '3' then '电话POS' when '4' then 'MISPOS' when '5' then '移动POS' when '6' then '网络POS' when '7' then 'MPOS' end from tbl_term_inf term where term.term_id = rpad(f.card_accp_term_id,12,' ')) TERM_TP"
				+ "               ,(select case trim(substr(b.SETTLE_ACCT,1,1)) when '0' then '对公账户' when '1' then '对私账户' end from TBL_MCHT_SETTLE_INF b where b.MCHT_NO = f.card_accp_id) ACCT_TYPE"
		    	+ "			    from TBL_N_TXN_HIS f "
		    	+ "			      left join  TBL_MCHT_BASE_INF m on f.card_accp_id = m.mcht_no "
		    	+ "			      left join  TBL_BRH_INFO b on m.bank_no = b.brh_id  "
		    	+ "			      left join TBL_FIRST_BRH_DEST_ID d on d.dest_id =f.tdestid  "
		    	+ "			      left join TBL_TXN_NAME t on f.txn_num = t.txn_num "
		    	+ "			      left join gtwyadm.tbl_upbrh_mcht u on f.up_mcht_id = u.mcht_id_up and f.up_term_id = u.term_id_up and f.up_brh_id3 = u.brh_id3  "
		    	+whereSql1
		    	+ "			    ) a  ";
		    sql = sql + getWhereSql().toString();
		    sql=sql+" order by a.inst_date desc";
		return sql;
	}
	
	public String getQueryTransState() {
		return queryTransState;
	}

	public void setQueryTransState(String queryTransState) {
		this.queryTransState = queryTransState;
	}

	public String getQueryRevsalFlag() {
		return queryRevsalFlag;
	}

	public void setQueryRevsalFlag(String queryRevsalFlag) {
		this.queryRevsalFlag = queryRevsalFlag;
	}

	public String getQueryCancelFlag() {
		return queryCancelFlag;
	}

	public void setQueryCancelFlag(String queryCancelFlag) {
		this.queryCancelFlag = queryCancelFlag;
	}
	
	private String getCountSql() {
		String amtSql="select to_char(count(1)) "
				+ "	from ( "
				+ "		  SELECT f.*, "
				+ "		         decode((select n.mcht_group_flag  from TBL_MCHT_BASE_INF n where MCHT_NO=f.card_accp_id),'0','0','1') mcht_group, "
				+ "		         substr(f.misc_2,104,2) card_type,  "
				+ "		         (case when substr(f.misc_2,104,2) in ('00','01','02','03','04') then '银联' else '外卡' end) card_org,  "
				+ "		         (case when (substr(f.misc_2,104,2) in ('00','01','02','03','04')) and (nvl(length(trim(substr(f.misc_2,106,8))),0)=8) then substr(f.misc_2,106,8) else '*' end) bank_no, "
				+ "		         (case when (substr(f.misc_2,104,2) in ('00','01','02','03','04')) and (nvl(length(trim(substr(f.misc_2,106,8))),0)=8) then  "
				+ "		                     (select min(bb.CARD_DIS) from  tbl_bank_bin_inf bb where trim(bb.ins_id_cd) = substr(f.misc_2,106,8) )  "
				+ "		                     else  '*' end) bank_name,  "
				+ "		          u.mcht_id_up mcht_brh_id,u.mcht_name_up mcht_brh_name,u.term_id_up term_brh_id,u.brh_id3, "
				+ "		         (select term.misc_2 from tbl_term_inf term where term.term_id = rpad(f.card_accp_term_id,12,' ')) term_name ,"
				+ "			       (case when f.txn_num in ('5151', '3101', '2101', '3091', '2091') then -TO_NUMBER(NVL(trim(f.amt_trans), 0)) / 100 else TO_NUMBER(NVL(trim(f.amt_trans), 0)) / 100 end) as amt, "
				+ "              (case substr(f.misc_2,117,1) when '0' then 'T+'||to_number(substr(f.misc_2,46,3)) when '1' then  decode(substr(f.misc_2,118,1),'0','周结','1','月结','2','季结',null) else null end) settle_type "
				+ "               ,(select case term.TERM_TP when '0' then '普通POS' when '1' then '财务POS' when '2' then '签约POS' when '3' then '电话POS' when '4' then 'MISPOS' when '5' then '移动POS' when '6' then '网络POS' when '7' then 'MPOS' end from tbl_term_inf term where term.term_id = rpad(f.card_accp_term_id,12,' ')) TERM_TP"
				+ "               ,(select case trim(substr(b.SETTLE_ACCT,1,1)) when '0' then '对公账户' when '1' then '对私账户' end from TBL_MCHT_SETTLE_INF b where b.MCHT_NO = f.card_accp_id) ACCT_TYPE"
				+ "		    from TBL_N_TXN_HIS f "
				+ "		      left join  TBL_MCHT_BASE_INF m on f.card_accp_id = m.mcht_no "
				+ "		      left join  TBL_BRH_INFO b on m.bank_no = b.brh_id  "
				+ "		      left join TBL_FIRST_BRH_DEST_ID d on d.dest_id =f.tdestid  "
				+ "		      left join TBL_TXN_NAME t on f.txn_num = t.txn_num "
				+ "		      left join gtwyadm.tbl_upbrh_mcht u on f.up_mcht_id = u.mcht_id_up and f.up_term_id = u.term_id_up and f.up_brh_id3 = u.brh_id3  "
				+whereSql1
				+ "		    ) a  "
        		+ getWhereSql().toString();
		return amtSql.toString();
	}
	
	private String getSumSql(String count) {
		String amtSql="select '','','','','','','','','','','','','','','','','','','','','交易总数：','"+count+"','交易总金额：',"
        		+ 	" to_char("
        		+ 		"nvl(sum( a.amt ),0)"
        		+ 	",'99,999,999,990.99') as amt_total,'','' "
        		+ "	from ( "
				+ "		  SELECT f.*, "
				+ "		         decode((select n.mcht_group_flag  from TBL_MCHT_BASE_INF n where MCHT_NO=f.card_accp_id),'0','0','1') mcht_group, "
				+ "		         substr(f.misc_2,104,2) card_type,  "
				+ "		         (case when substr(f.misc_2,104,2) in ('00','01','02','03','04') then '银联' else '外卡' end) card_org,  "
				+ "		         (case when (substr(f.misc_2,104,2) in ('00','01','02','03','04')) and (nvl(length(trim(substr(f.misc_2,106,8))),0)=8) then substr(f.misc_2,106,8) else '*' end) bank_no, "
				+ "		         (case when (substr(f.misc_2,104,2) in ('00','01','02','03','04')) and (nvl(length(trim(substr(f.misc_2,106,8))),0)=8) then  "
				+ "		                     (select min(bb.CARD_DIS) from  tbl_bank_bin_inf bb where trim(bb.ins_id_cd) = substr(f.misc_2,106,8) )  "
				+ "		                     else  '*' end) bank_name,  "
				+ "	             (case when f.txn_num in('5151','3101','2101','3091','2091') then -TO_NUMBER(NVL(trim(f.amt_trans),0))/100 else TO_NUMBER(NVL(trim(f.amt_trans),0))/100 end ) as amt, "
				+ "		          u.mcht_id_up mcht_brh_id,u.mcht_name_up mcht_brh_name,u.term_id_up term_brh_id,u.brh_id3, "
				+ "		         (select term.misc_2 from tbl_term_inf term where term.term_id = rpad(f.card_accp_term_id,12,' ')) term_name,"
				+ "              (case substr(f.misc_2,117,1) when '0' then 'T+'||to_number(substr(f.misc_2,46,3)) when '1' then  decode(substr(f.misc_2,118,1),'0','周结','1','月结','2','季结',null) else null end) settle_type "
				+ "               ,(select case term.TERM_TP when '0' then '普通POS' when '1' then '财务POS' when '2' then '签约POS' when '3' then '电话POS' when '4' then 'MISPOS' when '5' then '移动POS' when '6' then '网络POS' when '7' then 'MPOS' end from tbl_term_inf term where term.term_id = rpad(f.card_accp_term_id,12,' ')) TERM_TP"
				+ "               ,(select case trim(substr(b.SETTLE_ACCT,1,1)) when '0' then '对公账户' when '1' then '对私账户' end from TBL_MCHT_SETTLE_INF b where b.MCHT_NO = f.card_accp_id) ACCT_TYPE"
				+ "		    from TBL_N_TXN_HIS f "
				+ "		      left join  TBL_MCHT_BASE_INF m on f.card_accp_id = m.mcht_no "
				+ "		      left join  TBL_BRH_INFO b on m.bank_no = b.brh_id  "
				+ "		      left join TBL_FIRST_BRH_DEST_ID d on d.dest_id =f.tdestid  "
				+ "		      left join TBL_TXN_NAME t on f.txn_num = t.txn_num "
				+ "		      left join gtwyadm.tbl_upbrh_mcht u on f.up_mcht_id = u.mcht_id_up and f.up_term_id = u.term_id_up and f.up_brh_id3 = u.brh_id3  "
				+ whereSql1
				+ "		    ) a  "
        		+ getWhereSql().toString()
        		+" and a.txn_num in ('1101','1091','5151') and a.trans_state in ( '1','R') and a.REVSAL_FLAG!='1' and a.CANCEL_FLAG!='1' ";
		return amtSql.toString();
	}
	
	private String getWhereSql() {
		// TODO Auto-generated method stub
		StringBuffer whereSql = new StringBuffer(" where 1=1 "+
//			  		+ "f.card_accp_id in (select mcht_no from tbl_mcht_base_inf a where a.bank_no in " + operator.getBrhBelowId() + ") "+
				 //20160415 郭宇 修改 交易类型1181外全显示
//			  " and a.txn_num in ('1011','1091','1101','1111','1121','1141','1161','2011','2091','2101','2111','2121','3011','3091','3101','3111','3121','4011','4091','4101','4111','4121','5151','5161') ");
			  " and a.txn_num not in ('1181') ");
		if(isNotEmpty(startDate)) {
        	whereSql.append(" AND a.inst_date").append(" >= ").append(startDate).append("000000 ");
        }
        if(isNotEmpty(endDate)) {
        	whereSql.append(" AND a.inst_date").append(" <= ").append(endDate).append("999999 ");
        }
    	if (isNotEmpty(queryBrhId)) {
	  		whereSql.append(" and a.card_accp_id in (select mcht_no from tbl_mcht_base_inf k where k.bank_no in(select brh_id  from TBL_BRH_INFO where brh_id='" + queryBrhId.trim() + "' or UP_BRH_ID='"+queryBrhId.trim()+"' ))");
//			  		whereSql.append(" and f.card_accp_id in (select mcht_no from tbl_mcht_base_inf  where bank_no in (SELECT brh_id FROM TBL_BRH_INFO  start with brh_id ='" + queryBrhId.trim() + "' connect by prior  BRH_ID = UP_BRH_ID  ) ) ");
	  	} 
        if(isNotEmpty(queryCardAccptId)) {
        	whereSql.append(" AND a.card_accp_id").append(" = ").append("'").append(queryCardAccptId).append("'");
//		        	whereSql.append(" AND f.card_accp_id").append(" in (SELECT MCHT_NO FROM TBL_MCHT_BASE_INF  start with MCHT_NO =").append("'").append(queryCardAccpId).append("' connect by prior  trim(MCHT_NO) = MCHT_GROUP_ID ) ");
        }
        if(isNotEmpty(queryCardAccpTermId)) {
        	whereSql.append(" AND a.card_accp_term_id").append(" like ").append("'%").append(queryCardAccpTermId).append("%'");
        }
		if (isNotEmpty(queryPan)) {
			if(queryPan.trim().length() == 6){
				whereSql.append(" AND instr(trim(a.pan),").append(queryPan).append(",1)=1");
			}else if(queryPan.trim().length() == 10){
				whereSql.append(" AND instr(trim(a.pan),'").append(queryPan.substring(0, 6)).append("',1)=1").append(" AND instr(trim(a.pan),'").append(queryPan.substring(6, 10)).append("',-1)=(length(trim(a.pan))-4 +1)");
			}else if(queryPan.trim().length() > 10){
				whereSql.append(" AND trim(a.pan)").append(" ='").append(queryPan).append("'");
			}else{
				whereSql.append(" AND 1=3");
			}
		}
        /*if(isNotEmpty(queryTermSsn)) {
        	whereSql.append(" AND f.term_ssn").append(" like ").append("'%").append(queryTermSsn).append("%'");
        }*/
        
		if (isNotEmpty(queryAmtTransLow)) {
			whereSql.append(" AND a.amt ").append(" >= ").append(Float.parseFloat(queryAmtTransLow.trim()));
		}
		if (isNotEmpty(queryAmtTransUp)) {
			whereSql.append(" AND a.amt ").append(" <= ").append(Float.parseFloat(queryAmtTransUp.trim()));
		}
		if (isNotEmpty(respName)) {
			whereSql.append(" AND a.RESP_CODE").append(" = '").append(respName).append("'");
		}
		if (isNotEmpty(acctType)) {
			whereSql.append(" AND a.ACCT_TYPE").append(" = '").append(getAcctType(acctType)).append("'");
		}
		if (isNotEmpty(termType)) {
			whereSql.append(" AND a.TERM_TP").append(" = '").append(getPosType(termType)).append("'");
		}
		
		
        if(isNotEmpty(queryTxnName)) {
        	whereSql.append(" AND a.txn_num").append(" = ").append("'").append(queryTxnName).append("'");
        }
/*        if(isNotEmpty(querySysSeqNum)) {
        	whereSql.append(" AND f.sys_seq_num").append(" like ").append("'%").append(querySysSeqNum).append("%'");
        }
        */
		//20160504 guoyu upd 成功，请求中，交易确认，已退货以外都算失败；选择成功，可检索出交易确认
		if (isNotEmpty(queryTransState)) {
			//失败
			if (Constants.TransState.equals(queryTransState)) {
//				whereSql.append(" AND a.trans_state  !='1'  and a.trans_state  !='0'  and a.trans_state  !='R' ");
				whereSql.append(" AND a.trans_state !='1' and a.trans_state !='0' and a.trans_state !='R' and a.trans_state !='9'");
			//成功
			} else if (Constants.TransState_Success.equals(queryTransState)) {
				whereSql.append(" AND a.trans_state in ('1','9') ");
        	}else{
        		whereSql.append(" AND a.trans_state").append(" = ").append("'").append(queryTransState).append("'");
        	}
        }
        /*
        if(isNotEmpty(queryRespCode)) {
        	whereSql.append(" AND f.RESP_CODE").append(" = ").append("'").append(queryRespCode).append("' ");
        }
        
        if(isNotEmpty(querySettleBrh)) {
        	whereSql.append(" AND f.tdestid").append(" = ").append("'").append(querySettleBrh).append("' ");
        }
        if(isNotEmpty(queryRevsalSsn)) {
        	whereSql.append(" AND f.revsal_ssn").append(" like ").append("'%").append(queryRevsalSsn).append("%'");
        }*/
        if(isNotEmpty(queryRetrivlRef)) {
        	whereSql.append(" AND a.retrivl_ref").append(" like ").append("'%").append(queryRetrivlRef).append("%'");
        }
        if(isNotEmpty(queryRevsalFlag)) {
        	whereSql.append(" AND a.revsal_flag").append(" = ").append("'").append(queryRevsalFlag).append("' ");
        }
        /*if(isNotEmpty(queryCancelSsn)) {
        	whereSql.append(" AND f.cancel_ssn").append(" like ").append("'%").append(queryCancelSsn).append("%'");
        }*/
        if(isNotEmpty(queryCancelFlag)) {
        	whereSql.append(" AND a.cancel_flag").append(" = ").append("'").append(queryCancelFlag).append("' ");
        }
		if (isNotEmpty(queryBankName)) {
			whereSql.append(" AND a.bank_no").append(" = ").append("'").append(queryBankName).append("' ");
		}

		if (isNotEmpty(queryCardOrg)) {  //卡组织
			whereSql.append(" AND a.card_org = '").append(getCardOrg(queryCardOrg.trim())).append("'");
		}
		if (isNotEmpty(queryMcntGroup)) { //商户种类
			whereSql.append(" AND a.mcht_group = '").append(queryMcntGroup).append("'");
		}
		if (isNotEmpty(queryCardAccpTermName)) {
			whereSql.append(" AND a.term_name").append(" like ").append("'%").append(queryCardAccpTermName).append("%'");
		}
		if (isNotEmpty(queryAuthNo)) {
			whereSql.append(" AND a.authr_id_resp").append(" like ").append("'%").append(queryAuthNo).append("%' ");
		}
		/*if (isNotEmpty(queryCardSettleNo)) {
			whereSql.append(" AND f.revsal_flag").append(" = ").append("'").append(queryRevsalFlag).append("' ");
		}*/	
		if (isNotEmpty(queryMchtUp)) {
			whereSql.append(" AND a.mcht_brh_id").append(" = ").append("'").append(queryMchtUp).append("' ");
		}

		if (isNotEmpty(querySettleType)) {
			whereSql.append(" AND a.settle_type").append(" = ").append("'").append(querySettleType).append("' ");
		}
		
		return whereSql.toString();
		
		
	}
	public String getQuerySettleType() {
		return querySettleType;
	}

	public void setQuerySettleType(String querySettleType) {
		this.querySettleType = querySettleType;
	}

	@Override
	protected String genSql() {
		// TODO Auto-generated method stub
		return null;
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

	public String getQueryCardAccptId() {
		return queryCardAccptId;
	}
	public void setQueryCardAccptId(String queryCardAccptId) {
		this.queryCardAccptId = queryCardAccptId;
	}
	public String getQueryBrhId() {
		return queryBrhId;
	}
	public void setQueryBrhId(String queryBrhId) {
		this.queryBrhId = queryBrhId;
	}
	public String getQueryCardAccpTermId() {
		return queryCardAccpTermId;
	}
	public void setQueryCardAccpTermId(String queryCardAccpTermId) {
		this.queryCardAccpTermId = queryCardAccpTermId;
	}
	public String getQueryPan() {
		return queryPan;
	}
	public void setQueryPan(String queryPan) {
		this.queryPan = queryPan;
	}
	public String getQueryTxnName() {
		return queryTxnName;
	}
	public void setQueryTxnName(String queryTxnName) {
		this.queryTxnName = queryTxnName;
	}
	public String getQueryRetrivlRef() {
		return queryRetrivlRef;
	}
	public void setQueryRetrivlRef(String queryRetrivlRef) {
		this.queryRetrivlRef = queryRetrivlRef;
	}
	public String getQueryBankName() {
		return queryBankName;
	}
	public void setQueryBankName(String queryBankName) {
		this.queryBankName = queryBankName;
	}
	public String getQueryCardOrg() {
		return queryCardOrg;
	}
	public void setQueryCardOrg(String queryCardOrg) {
		this.queryCardOrg = queryCardOrg;
	}
	public String getQueryMcntGroup() {
		return queryMcntGroup;
	}
	public void setQueryMcntGroup(String queryMcntGroup) {
		this.queryMcntGroup = queryMcntGroup;
	}
	public String getQueryCardAccpTermName() {
		return queryCardAccpTermName;
	}
	public void setQueryCardAccpTermName(String queryCardAccpTermName) {
		this.queryCardAccpTermName = queryCardAccpTermName;
	}
	public String getQueryAuthNo() {
		return queryAuthNo;
	}
	public void setQueryAuthNo(String queryAuthNo) {
		this.queryAuthNo = queryAuthNo;
	}
	public String getQueryMchtUp() {
		return queryMchtUp;
	}
	public void setQueryMchtUp(String queryMchtUp) {
		this.queryMchtUp = queryMchtUp;
	}

	public String getQueryTermSsn() {
		return queryTermSsn;
	}

	public void setQueryTermSsn(String queryTermSsn) {
		this.queryTermSsn = queryTermSsn;
	}

	public String getQuerySysSeqNum() {
		return querySysSeqNum;
	}

	public void setQuerySysSeqNum(String querySysSeqNum) {
		this.querySysSeqNum = querySysSeqNum;
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

	public String getRespName() {
		return respName;
	}

	public void setRespName(String respName) {
		this.respName = respName;
	}

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}
	
	//0-普通POS，1-财务POS，2-签约POS，3-电话POS，4-MISPOS，5-移动POS，6-网络POS,7-MPOS
	private String getPosType(String typeCode) {
		if ("0".equals(typeCode)) {
			return "普通POS";
		} else if ("1".equals(typeCode)) {
			return "财务POS";
		} else if ("2".equals(typeCode)) {
			return "签约POS";
		} else if ("3".equals(typeCode)) {
			return "电话POS";
		} else if ("4".equals(typeCode)) {
			return "MISPOS";
		} else if ("5".equals(typeCode)) {
			return "移动POS";
		} else if ("6".equals(typeCode)) {
			return "网络POS";
		} else if ("7".equals(typeCode)) {
			return "MPOS";
		}
		return "";
	}
	
	private String getAcctType(String typeCode) {
		if ("0".equals(typeCode)) {
			return "对公账户";
		} else if ("1".equals(typeCode)) {
			return "对私账户";
		} 
		return "";
	}
	
	private String getCardOrg(String cardOrgCode) {
		if ("0".equals(cardOrgCode)) {
			return "银联";
		} else if ("1".equals(cardOrgCode)) {
			return "外卡";
		} 
		return "";
	}
}
