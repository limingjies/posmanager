
package com.allinfinance.struts.query.action;

import java.util.ArrayList;
import java.util.List;

import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ExcelCreator;
import com.allinfinance.system.util.SysParamUtil;




public class T50412Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void reportAction() throws Exception {
		
		String rnName=null;
		String titleName=null;
		ArrayList<String[]> coulmList=new ArrayList<String[]>() ;
		ArrayList<int[]> leftList=new ArrayList<int[]>() ;
		
		if("0".equals(repType)){
			sql=getSql_0();
			rnName="RN504120RN_";
			titleName= SysParamUtil.getParam("RN504120RN");
			String coulmHeader[]={"冻结日期","商户号","商户名","交易日期","交易时间","交易类型","交易流水号",
		    		"交易金额","交易手续费","清算金额","交易卡号","交易通道"};
			coulmList.add(coulmHeader);
			int leftFormat[]={2};
			leftList.add(leftFormat);
			
		}else if("1".equals(repType)){
			sql=getSql_1();
			rnName="RN504121RN_";
			titleName= SysParamUtil.getParam("RN504121RN");
			String coulmHeader[]={"解冻清算日期","冻结日期","商户号","商户名","交易日期","交易时间","交易类型","交易流水号",
		    		"交易金额","交易手续费","清算金额","交易卡号","交易通道"};
			coulmList.add(coulmHeader);
			int leftFormat[]={3};
			leftList.add(leftFormat);
			
		}else if("2".equals(repType)){
			sql=getSql_2();
			rnName="RN504122RN_";
			titleName= SysParamUtil.getParam("RN504122RN");
			String coulmHeader[]={"冻结清算日期","商户号","商户名","结算通道","冻结金额",
		    		"批次冻结流水","冻结录入日期","冻结总金额","已冻金额","批次冻结状态"};
			coulmList.add(coulmHeader);
			int leftFormat[]={2};
			leftList.add(leftFormat);
			
		}else if("3".equals(repType)){
			sql=getSql_3();
			rnName="RN504123RN_";
			titleName= SysParamUtil.getParam("RN504123RN");
			String coulmHeader[]={"解冻清算日期","冻结日期","商户号","商户名",
		    		"批次冻结流水","解冻金额"};
			coulmList.add(coulmHeader);
			int leftFormat[]={3};
			leftList.add(leftFormat);
			
		/*}else if("4".equals(repType)){
			sql=genSql();
			rnName="RN504124RN_";
			titleName= SysParamUtil.getParam("RN504123RN");
			String coulmHeader[]={"解冻清算日期","冻结日期","商户号","商户名",
		    		"批次冻结流水","解冻金额"};
			coulmList.add(coulmHeader);
			int leftFormat[]={3};
			leftList.add(leftFormat);*/
			
		}else{
			writeNoDataMsg("系统错误！");
			return;
		}
		
		List<Object[]> dataList= CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		
		if(dataList.size() == 0) {
			writeNoDataMsg("没有找符合条件的记录，无法生成报表");
			return;
		}
		
		
		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rnName + 
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		
		
		ExcelCreator excelCreator=new ExcelCreator(titleName, coulmList.get(0), dataList);
		 if("4".equals(repType)){
//			 Object[] sumData= (Object[]) CommonFunction.getCommQueryDAO().findBySQLQuery(genSumSql()).get(0);
//			 excelCreator.setSumData(sumData);
		 }
		excelCreator.setLeftFormat(leftList.get(0));
		excelCreator.exportReport(fileName);
		
		writeUsefullMsg(fileName);
		return;
	}
	
	
	
	/**
	 * 商户资金解冻明细报表
	 * @author caotz
	 * 2015年8月27日
	 * @return sql
	 */
	private String getSql_3() {
		StringBuffer whereSql = new StringBuffer(" where  1=1 ");
		whereSql.append("and a.unfreeze_flag='2' ");
		
	    if (isNotEmpty(startDate)) {
			whereSql.append(" AND a.unfreeze_date ").append(" >= ").append("'")
					.append(startDate.replace("-", "")).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND a.unfreeze_date").append(" <= ").append("'")
					.append(endDate.replace("-", "")).append("'");
		}
	    
	    String sql = "select "
	    		+ "to_char(to_date(unfreeze_date,'yyyy-mm-dd'),'yyyy-mm-dd') as unfreeze_dates,"
	    		+ "to_char(to_date(freeze_date,'yyyy-mm-dd'),'yyyy-mm-dd') as freeze_dates,"
	    		+ "a.mcht_id,"
	    		+ "(select b.mcht_nm from  tbl_mcht_base_inf b where a.mcht_id=b.mcht_no) as mcht_nm,"
	    		+ "a.batch_no,"
	    		+ "a.do_freeze_amt "
				+ "from tbl_mcht_freeze a  ";
	    
	    sql = sql + whereSql.toString();
	    sql=sql+" order by a.unfreeze_date,a.batch_no  ";
		return sql.toString();
	}
	
	
	/**
	 * 商户资金冻结明细报表
	 * @author caotz
	 * 2015年8月27日
	 * @return sql
	 */
	private String getSql_2() {
		StringBuffer whereSql = new StringBuffer(" where  1=1 ");
		whereSql.append("and t.freeze_flag in ('2','1') " );
		
	    if (isNotEmpty(startDate)) {
			whereSql.append(" AND a.date_stlm ").append(" >= ").append("'")
					.append(startDate.replace("-", "")).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND a.date_stlm").append(" <= ").append("'")
					.append(endDate.replace("-", "")).append("'");
		}
	    
	    String sql = "select "
	    		+ "to_char(to_date(date_stlm,'yyyy-mm-dd'),'yyyy-mm-dd') as date_stlms,"
	    		+ "a.mcht_id,"
	    		+ "(select b.mcht_nm from  tbl_mcht_base_inf b where a.mcht_id=b.mcht_no) as mcht_nm,"
	    		+ "decode(a.channle_id,'0','畅捷渠道','1','非畅捷渠道',a.channle_id) as channle_ids,"
	    		+ "a.freeze_amt as this_amt,"
	    		+ "a.batch_no,"
	    		+ "to_char(to_date(freeze_date,'yyyy-mm-dd'),'yyyy-mm-dd') as freeze_dates,"
	    		+ "t.freeze_amt as total_amt,"
	    		+ "t.do_freeze_amt,"
	    		+ "decode(t.freeze_flag,'0','待冻结','1','部分冻结','2','已冻结',t.freeze_flag) as freeze_flags "
				+ "from tbl_mcht_freeze_dtl a left join tbl_mcht_freeze t on a.batch_no=t.batch_no ";
	    
	    sql = sql + whereSql.toString();
	    sql=sql+" order by a.date_stlm,a.batch_no  ";
		return sql.toString();
	}
	
	
	/**
	 * 风控解冻明细报表
	 * @author caotz
	 * 2015年8月27日
	 * @return sql
	 */
	private String getSql_1() {
		StringBuffer whereSql = new StringBuffer(" where  1=1 ");
		whereSql.append("and a.extra_flag='2' ");
		
	    if (isNotEmpty(startDate)) {
			whereSql.append(" AND a.date_stlm_true ").append(" >= ").append("'")
					.append(startDate.replace("-", "")).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND a.date_stlm_true").append(" <= ").append("'")
					.append(endDate.replace("-", "")).append("'");
		}
	    
	    String sql = "select "
	    		+ "to_char(to_date(date_stlm_true,'yyyy-mm-dd'),'yyyy-mm-dd') as date_stlm_trues,"
	    		+ "to_char(to_date(date_stlm,'yyyy-mm-dd'),'yyyy-mm-dd') as date_stlms,"
	    		+ "a.mcht_id,"
	    		+ "(select b.mcht_nm from  tbl_mcht_base_inf b where a.mcht_id=b.mcht_no) as mcht_nm,"
	    		+ "to_char(to_date(txn_date,'yyyy-mm-dd'),'yyyy-mm-dd') as txn_dates,"
	    		+ "to_char(to_date(txn_time,'hh24:mi:ss'),'hh24:mi:ss') as txn_times,"
	    		+ "(select  b.txn_name from tbl_txn_name b where a.txn_num=b.txn_num) as txn_nm,"
	    		+ "a.txn_sn,"
	    		+ "(a.mcht_amt_cr-a.mcht_amt_db) as mcht_amt,"
	    		+ "(a.mcht_fee_db-a.mcht_fee_cr) as mcht_fee,"
	    		+ "(a.mcht_amt_cr-a.mcht_amt_db-a.mcht_fee_db+a.mcht_fee_cr) as mcht_stlm,"
	    		+ "a.pan,"
	    		+ "(select first_brh_name from tbl_first_brh_dest_id where first_brh_id=a.inst_code) as first_brh_nm "
				+ "from tbl_clear_dtl a ";
	    
	    sql = sql + whereSql.toString();
	    sql=sql+" order by a.date_stlm_true,a.date_stlm,txn_date  ";
		return sql.toString();
	}
	
	
	/**
	 * 风控冻结明细报表
	 * @author caotz
	 * 2015年8月27日
	 * @return sql
	 */
	private String getSql_0() {
		StringBuffer whereSql = new StringBuffer(" where  1=1 ");
		whereSql.append("and a.extra_flag='1' ");
		
	    if (isNotEmpty(startDate)) {
			whereSql.append(" AND a.date_stlm ").append(" >= ").append("'")
					.append(startDate.replace("-", "")).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND a.date_stlm").append(" <= ").append("'")
					.append(endDate.replace("-", "")).append("'");
		}
	    
	    String sql = "select "
	    		+ "to_char(to_date(date_stlm,'yyyy-mm-dd'),'yyyy-mm-dd') as date_stlms,"
	    		+ "a.mcht_id,"
	    		+ "(select b.mcht_nm from  tbl_mcht_base_inf b where a.mcht_id=b.mcht_no) as mcht_nm,"
	    		+ "to_char(to_date(txn_date,'yyyy-mm-dd'),'yyyy-mm-dd') as txn_dates,"
	    		+ "to_char(to_date(txn_time,'hh24:mi:ss'),'hh24:mi:ss') as txn_times,"
	    		+ "(select  b.txn_name from tbl_txn_name b where a.txn_num=b.txn_num) as txn_nm,"
	    		+ "a.txn_sn,"
	    		+ "(a.mcht_amt_cr-a.mcht_amt_db) as mcht_amt,"
	    		+ "(a.mcht_fee_db-a.mcht_fee_cr) as mcht_fee,"
	    		+ "(a.mcht_amt_cr-a.mcht_amt_db-a.mcht_fee_db+a.mcht_fee_cr) as mcht_stlm,"
	    		+ "a.pan,"
	    		+ "(select first_brh_name from tbl_first_brh_dest_id where first_brh_id=a.inst_code) as first_brh_nm "
				+ "from tbl_clear_dtl a ";
	    
	    sql = sql + whereSql.toString();
	    sql=sql+" order by a.date_stlm,txn_date,txn_time  ";
		return sql.toString();
	}
	
	
	
	
	@Override
	protected String genSql() {
		
		StringBuffer whereSql = new StringBuffer(" where  1=1 ");
		whereSql.append("and a.mcht_no in () ");
		
	    if (isNotEmpty(startDate)) {
			whereSql.append(" AND a.date_stlm ").append(" >= ").append("'")
					.append(startDate.replace("-", "")).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND a.date_stlm").append(" <= ").append("'")
					.append(endDate.replace("-", "")).append("'");
		}
	    
	    String sql = "select "
	    		+ "to_char(to_date(date_stlm,'yyyy-mm-dd'),'yyyy-mm-dd') as date_stlms,"
	    		+ "a.mcht_id,"
	    		+ "(select b.mcht_nm from  tbl_mcht_base_inf b where a.mcht_id=b.mcht_no) as mcht_nm,"
	    		+ "(select  b.txn_name from tbl_txn_name b where a.txn_num=b.txn_num) as txn_nm,"
	    		+ "to_char(to_date(txn_date,'yyyy-mm-dd'),'yyyy-mm-dd') as txn_dates,"
	    		+ "to_char(to_date(txn_time,'hh24:mi:ss'),'hh24:mi:ss') as txn_times,"
	    		+ "a.txn_sn,"
	    		+ "(a.mcht_amt_cr-a.mcht_amt_db) as mcht_amt,"
	    		+ "(a.mcht_fee_db-a.mcht_fee_cr) as mcht_fee,"
	    		+ "(a.mcht_amt_cr-a.mcht_amt_db-a.mcht_fee_db+a.mcht_fee_cr) as mcht_stlm,"
	    		+ "a.pan,"
	    		+ "a.resp_code,"
	    		+ "(select first_brh_name from tbl_first_brh_dest_id where first_brh_id=a.inst_code) as first_brh_nm "
//	    		+ "a.inst_mcht_id,"
//	    		+ "a.inst_term_id,"
//	    		+ "a.inst_retrivl_ref,"
//	    		+ "decode(a.stlm_flag,'0','待结算','1','不可结算','2','已结算',a.stlm_flag) as stlm_nm,"
//	    		+ "to_char(to_date(substr(a.inst_date,1,8),'yyyy-mm-dd'),'yyyy-mm-dd') as inst_dt "
				+ "from tbl_mcht_base_inf a ";
	    
	   
	    sql = sql + whereSql.toString();
	    sql=sql+" order by a.date_stlm,txn_date,txn_time  ";
		return sql.toString();
	}
	
	
	
	
	
	private String startDate;
	private String endDate;
	private String repType;

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

	public String getRepType() {
		return repType;
	}

	public void setRepType(String repType) {
		this.repType = repType;
	}
	
	


	

}
