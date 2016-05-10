
package com.allinfinance.struts.query.action;

import java.util.List;

import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ExcelCreator;
import com.allinfinance.system.util.SysParamUtil;

public class T50411Action extends ReportBaseAction{

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
		
		String rnName="RN50411RN_";
		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rnName + 
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		
		String titleName= SysParamUtil.getParam("RN50411RN");
		 String coulmHeader[]={"清算日期","商户号","商户名","交易类型","交易日期","交易时间","交易流水号","交易参考号",
		    		"交易金额","交易手续费","清算金额","交易卡号","交易应答码",
		    		"交易通道","通道商户","通道终端","通道参考号",
		    		"结算标志","通道对账标志"};
		int leftFormat[]={2,11,12,18};
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
		whereSql.append("and a.inst_err_type='1' ");
		
	    if (isNotEmpty(startDate)) {
			whereSql.append(" AND a.date_stlm ").append(" >= ").append("'")
					.append(startDate.replace("-", "")).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND a.date_stlm").append(" <= ").append("'")
					.append(endDate.replace("-", "")).append("'");
		}
	    
	    String sql = "select "
	    		+ "to_char(to_date(a.date_stlm,'yyyy-mm-dd'),'yyyy-mm-dd') as date_stlms,"
	    		+ "a.card_accp_id,"
	    		+ "(select b.mcht_nm from  tbl_mcht_base_inf b where a.card_accp_id=b.mcht_no) as mcht_nm,"
	    		+ "(select  b.txn_name from tbl_txn_name b where a.txn_num=b.txn_num) as txn_nm,"
	    		+ "to_char(to_date(a.txn_date,'yyyy-mm-dd'),'yyyy-mm-dd') as txn_dates,"
	    		+ "to_char(to_date(a.txn_time,'hh24:mi:ss'),'hh24:mi:ss') as txn_times,"
	    		+ "a.sys_seq_num,"
	    		+ "a.retrivl_ref,"
	    		+ "a.amt_trans,"
	    		+ "a.amt_trans_fee,"
	    		+ "a.amt_stlm,"
	    		+ "a.pan,"
	    		+ "a.resp_code,"
	    		+ "(select first_brh_name from tbl_first_brh_dest_id where first_brh_id=a.inst_code) as first_brh_nm,"
	    		+ "a.inst_mcht_id,"
	    		+ "a.inst_term_id,"
	    		+ "a.inst_retrivl_ref,"
	    		+ "decode(a.stlm_flag,'0','待结算','1','不可结算','2','已结算',a.stlm_flag) as stlm_nm,"
	    		+ "decode(a.inst_chk_flag,'0','未对账','1','不参与对账','2','对账平',"
	    		+ "'S','可疑','A','可疑对账平',"
	    		+ "'3','收单有，通道无','4','收单无，通道有','5','收单和通道关键信息不一致',a.inst_chk_flag) as acct_err "
				+ "from tbl_chk_err a ";
	    
	   
	    sql = sql + whereSql.toString();
	    sql=sql+" order by a.date_stlm,a.txn_date ,a.txn_time  ";
		return sql.toString();
	}
	
	
	
	
	
	private String startDate;
	private String endDate;

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


	

}
