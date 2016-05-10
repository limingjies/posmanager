
package com.allinfinance.struts.query.action;

import java.util.List;

import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ExcelCreator;
import com.allinfinance.system.util.SysParamUtil;

public class T50410Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void reportAction() throws Exception {
		
		
		
		List<Object[]> dataList= CommonFunction.getCommQueryDAO().findBySQLQuery(genSql());
		
		
		if(dataList.size() == 0) {
			writeNoDataMsg("没有找符合条件的记录，无法生成报表");
			return;
		}
		
//		Object[] sumData= (Object[]) CommonFunction.getCommQueryDAO().findBySQLQuery(genSumSql()).get(0);
		
		String rnName="RN50410RN_";
		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rnName + 
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		
		String titleName= SysParamUtil.getParam("RN50410RN");
		String coulmHeader[]={"清算日期","商户号","商户名","交易金额","交易手续费","清算金额","提现交易金额","提现交易手续费","提现金额","提现笔数","提现手续费","实际到账金额"};
		int leftFormat[]={2};
		ExcelCreator excelCreator=new ExcelCreator(titleName, coulmHeader, dataList);
//		excelCreator.setSumData(sumData);
		excelCreator.setLeftFormat(leftFormat);
		excelCreator.exportReport(fileName);
		
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		
		StringBuffer whereSql = new StringBuffer(" where  1=1 ");
		
	  
	    if (isNotEmpty(startDate)) {
			whereSql.append(" AND a.withdraw_date ").append(" >= ").append("'")
					.append(startDate.replace("-", "")).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND a.withdraw_date").append(" <= ").append("'")
					.append(endDate.replace("-", "")).append("'");
		}
		if (isNotEmpty(brhId)) {
			 whereSql.append(" AND a.mcht_id").append(" in "
			 		+ "(select mcht_no from tbl_mcht_base_inf where bank_no in "
			 			+ "(select brh_id from tbl_brh_info where brh_id='"+brhId+"' or up_brh_id='"+brhId+"' )"
			 		+ ") ");
		}
	    
	    String sql = "select "
	    		+ "to_char(to_date(a.withdraw_date,'yyyy-mm-dd'),'yyyy-mm-dd') as draw_date,"
	    		+ "a.mcht_id,"
	    		+ "(select b.mcht_nm from  tbl_mcht_base_inf b where a.mcht_id=b.mcht_no) as mcht_nm,"
	    		+ "nvl((select sum(c.mcht_amt_cr-c.mcht_amt_db) from tbl_clear_dtl c where c.date_stlm=a.withdraw_date and c.mcht_id=a.mcht_id),0)as sum_trans_amt, "
	    		+ "nvl((select sum(c.mcht_fee_db-c.mcht_fee_cr) from tbl_clear_dtl c where c.date_stlm=a.withdraw_date and c.mcht_id=a.mcht_id),0)as sum_fee_amt, "
	    		+ "nvl((select sum(c.mcht_amt_cr-c.mcht_amt_db-c.mcht_fee_db+c.mcht_fee_cr) from tbl_clear_dtl c where c.date_stlm=a.withdraw_date and c.mcht_id=a.mcht_id),0)as sum_stlm_amt, "
	    		+ "nvl((select sum(c.mcht_amt_cr-c.mcht_amt_db) from tbl_clear_dtl c where c.date_stlm=a.withdraw_date and c.mcht_id=a.mcht_id and c.extra_flag='3'),0)as tx_trans_amt, "
	    		+ "nvl((select sum(c.mcht_fee_db-c.mcht_fee_cr) from tbl_clear_dtl c where c.date_stlm=a.withdraw_date and c.mcht_id=a.mcht_id and c.extra_flag='3'),0)as tx_fee_amt, "
	    		+ "nvl((select sum(c.mcht_amt_cr-c.mcht_amt_db-c.mcht_fee_db+c.mcht_fee_cr) from tbl_clear_dtl c where c.date_stlm=withdraw_date and c.mcht_id=a.mcht_id and c.extra_flag='3'),0)as tx_stlm_amt, "
				+ "nvl(sum(a.withdraw_num),0) as tx_count, "
				+ "nvl(sum(a.withdraw_amt_fee),0) as tx_fee, "
				+ "nvl(((select sum(c.mcht_amt_cr-c.mcht_amt_db-c.mcht_fee_db+c.mcht_fee_cr) from tbl_clear_dtl c where c.date_stlm=withdraw_date and c.mcht_id=a.mcht_id and c.extra_flag='3')-sum(a.withdraw_amt_fee)),0)as fact_amt "
				+ "from tbl_withdraw_fee_dtl a ";
	    
	    sql = sql + whereSql.toString();
	    sql=sql+" group by a.withdraw_date,a.mcht_id order by a.withdraw_date,a.mcht_id ";
		return sql.toString();
	}
	
	
	private String genSumSql() {
		
		StringBuffer whereSql = new StringBuffer(" where  1=1 ");
		StringBuffer whereClearSql = new StringBuffer(" where  1=1 ");

	  
	    if (isNotEmpty(startDate)) {
			whereSql.append(" AND a.withdraw_date ").append(" >= ").append("'").append(startDate.replace("-", "")).append("'");
			whereClearSql.append(" AND c.date_stlm ").append(" >= ").append("'").append(startDate.replace("-", "")).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND a.withdraw_date").append(" <= ").append("'").append(endDate.replace("-", "")).append("'");
			whereClearSql.append(" AND c.date_stlm ").append(" <= ").append("'").append(endDate.replace("-", "")).append("'");
		}
		if (isNotEmpty(brhId)) {
			 whereSql.append(" AND a.mcht_id").append(" in "
			 		+ "(select mcht_no from tbl_mcht_base_inf where bank_no in "
			 			+ "(select brh_id from tbl_brh_info where brh_id='"+brhId+"' or up_brh_id='"+brhId+"' )"
			 		+ ") ");
			 whereClearSql.append(" AND c.mcht_id").append(" in "
				 		+ "(select mcht_no from tbl_mcht_base_inf where bank_no in "
				 			+ "(select brh_id from tbl_brh_info where brh_id='"+brhId+"' or up_brh_id='"+brhId+"' )"
				 		+ ") ");
		}
	    
		whereClearSql.append(" c.mcht");
	    String sql = "select "
	    		+ " '总计',"
	    		+ "'',"
	    		+ "'',"
	    		+ "t.sum_trans_amt,t.sum_fee_amt,t.sum_stlm_amt,t.tx_trans_amt,t.tx_fee_amt,t.tx_stlm_amt,t.tx_count,t.tx_fee,(t.tx_stlm_amt-t.tx_fee) as fact_amt "
	    		+ " from ( "
	    			+ "select "
		    		+ "nvl((select sum(c.mcht_amt_cr-c.mcht_amt_db) from tbl_clear_dtl c "+whereClearSql+"),0)as sum_trans_amt, "
		    		+ "nvl((select sum(c.mcht_fee_db-c.mcht_fee_cr) from tbl_clear_dtl c "+whereClearSql+"),0)as sum_fee_amt, "
		    		+ "nvl((select sum(c.mcht_amt_cr-c.mcht_amt_db-c.mcht_fee_db+c.mcht_fee_cr) from tbl_clear_dtl c "+whereClearSql+"),0)as sum_stlm_amt, "
		    		+ "nvl((select sum(c.mcht_amt_cr-c.mcht_amt_db) from tbl_clear_dtl c "+whereClearSql+" and c.extra_flag='3'),0)as tx_trans_amt, "
		    		+ "nvl((select sum(c.mcht_fee_db-c.mcht_fee_cr) from tbl_clear_dtl c "+whereClearSql+" and c.extra_flag='3'),0)as tx_fee_amt, "
		    		+ "nvl((select sum(c.mcht_amt_cr-c.mcht_amt_db-c.mcht_fee_db+c.mcht_fee_cr) from tbl_clear_dtl c "+whereClearSql+" and c.extra_flag='3'),0)as tx_stlm_amt, "
	//				+ "nvl(sum(a.withdraw_num),0) as tx_count, "
		    		+ "nvl((select sum(a.withdraw_num) from tbl_withdraw_fee_dtl a "+whereSql+" ),0) as tx_count,"  
	//				+ "nvl(sum(a.withdraw_amt_fee),0) as tx_fee, "
		    		+ "nvl((select sum(a.withdraw_amt_fee) from tbl_withdraw_fee_dtl a "+whereSql+" ),0) as tx_fee "
	//				+ "nvl(((select sum(decode(db_cr,'0',-c.amt_stlm,'1',c.amt_stlm,c.amt_stlm)) from tbl_clear_dtl c "+whereClearSql+" and c.extra_flag='3')-sum(a.withdraw_amt_fee)),0)as fact_amt "
	//				+ "from tbl_withdraw_fee_dtl a ";
					+ "from dual "
				+ ") t";
	    
//	    sql = sql + whereSql.toString();
		return sql.toString();
	}
	
	
	
	private String startDate;
	private String endDate;
	private String brhId;

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

	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	

	

}
