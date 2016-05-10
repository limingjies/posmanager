
package com.allinfinance.struts.settle.action;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ExcelReportCreator;
import com.allinfinance.system.util.SysParamUtil;

public class T80807Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;
	
	private String startDate;
	private String endDate;
	private String queryInstCode;

	@Override
	protected void reportAction() throws Exception {
		// TODO Auto-generated method stub
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

		String rnName="RN80807RN_";
		String resName= SysParamUtil.getParam("RN80807RN");
		
        String[] coulmHeader = {"清算日期","通道名称","对平交易金额","差错交易金额","对平通道手续费","差错通道手续费","应到金额"};
        String[] params1 = {"制表日期" ,CommonFunction.getCurrDate("yyyy-MM-dd")};
        

        
        sheetList.add(resName);
		titleList.add(resName);
		coulmHeaderList.add(coulmHeader);
		dataSqlList.add(getDataSql());
		paramList.add(params1);
		//sumDataSqlList.add(getSumSql(count));
		
		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rnName + 
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		outputStream = new FileOutputStream(fileName);
		
		int leftFormat[]={1};
		excelReportCreator.setLeftFormat(leftFormat);
		excelReportCreator.setSheetList(sheetList);
		excelReportCreator.setTitleList(titleList);
		excelReportCreator.setParamList(paramList);
		excelReportCreator.setCoulmHeaderList(coulmHeaderList);
		excelReportCreator.setDataList(excelReportCreator.processData(dataSqlList, coulmHeaderList));
	//	excelReportCreator.setSumList(excelReportCreator.processData(sumDataSqlList, coulmHeaderList));
		
		excelReportCreator.exportReport(outputStream);
		outputStream.close();
		writeUsefullMsg(fileName);
		
	}
	
	private String getDataSql(){
		StringBuffer whereSql = new StringBuffer("where 1=1 ");
		if (isNotEmpty(queryInstCode)) {
			whereSql.append(" AND r.chl_id ").append(" = ").append("'").append(queryInstCode).append("'");
		}
		if (isNotEmpty(startDate)) {
			whereSql.append(" AND r.settl_date ").append(" >= ").append("'").append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND r.settl_date ").append(" <= ").append("'").append(endDate).append("'");
		}
	
		String subsql = " select r.chl_id, "
				+ "	       r.settl_date, "
				+ "	       to_char(sum(amt_trans_bal), '99999999999999990D00') amt_trans_bal, "
				+ "	       to_char(sum(amt_trans_err), '99999999999999990D00') amt_trans_err, "
				+ "	       to_char(sum(poundage_bal), '99999999999999990D00') poundage_bal, "
				+ "	       to_char(sum(poundage_err), '99999999999999990D00') poundage_err, "
				+ "	       to_char(sum(amt_recv), '99999999999999990D00') amt_recv "
				+ "	  from (select a.chnl_id chl_id, "
				+ "	               a.settl_date, "
				+ "	               sum(nvl(to_number(trim(a.trans_amt)), 0)/100) amt_trans_bal, "
				+ "	               0 amt_trans_err, "
				+ "	               sum(nvl(to_number(trim(a.cups_fee_c)), 0) - nvl(to_number(trim(a.cups_fee_d)), 0)) poundage_bal, "
				+ "	               0 poundage_err, "
				+ "	               (sum(nvl(to_number(trim(a.trans_amt)), 0)/100) - sum(nvl(to_number(trim(a.cups_fee_c)), 0) - nvl(to_number(trim(a.cups_fee_d)), 0))) amt_recv "
				+ "	          from TBL_ALGO_DTL a "
				+ "	         group by a.chnl_id, a.settl_date "
				+ "	        union all "
				+ "	        select b.inst_code chl_id, "
				+ "	               b.date_settlmt settle_date, "
				+ "	               0 amt_trans_bal, "
				+ "	               sum(nvl(to_number(trim(b.inst_amt_trans)), 0)/100) amt_trans_err, "
				+ "	               0 poundage_bal, "
				+ "	               sum(nvl(to_number(trim(b.inst_trans_fee)), 0)/100) poundage_err, "
				+ "	               (sum(nvl(to_number(trim(b.inst_amt_trans)), 0)/100) - sum(nvl(to_number(trim(b.inst_trans_fee)), 0)/100)) amt_recv "
				+ "	          from BTH_ERR_DTL b "
				+ "	         where b.stlm_flag = '2' "
				+ "	         group by b.inst_code, b.date_settlmt) r "
				+ whereSql.toString()
				+ "	 group by r.chl_id, r.settl_date ";
					 
				String sql = "select d.settl_date,d.chl_id||'-'||f.first_brh_name,d.amt_trans_bal,d.amt_trans_err,d.poundage_bal,d.poundage_err,d.amt_recv from ( " + subsql	+ " ) d  "
						+ " left join tbl_first_brh_dest_id f "
						+ " on d.chl_id = f.dest_id "
						+ "	 order by d.settl_date desc ,d.chl_id ";
		
		return sql;
	}
	
	private String getCountSql(){
		StringBuffer whereSql = new StringBuffer("where 1=1 ");
		if (isNotEmpty(queryInstCode)) {
			whereSql.append(" AND r.chl_id ").append(" = ").append("'").append(queryInstCode).append("'");
		}
		if (isNotEmpty(startDate)) {
			whereSql.append(" AND r.settl_date ").append(" >= ").append("'").append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND r.settl_date ").append(" <= ").append("'").append(endDate).append("'");
		}
	
		String countSql = " SELECT COUNT(*)  "
			+ "	 from ( "
			+ "			select a.chnl_id chl_id,a.settl_date "
//			+ "			   sum(nvl(to_number(trim(a.trans_amt)),0)/100) amt_trans, "
//			+ "			   sum(nvl(to_number(trim(a.cups_fee_c)),0)-nvl(to_number(trim(a.cups_fee_d)),0)) poundage, "
//			+ "			   (sum(nvl(to_number(trim(a.trans_amt)),0)/100) -sum(nvl(to_number(trim(a.cups_fee_c)),0)-nvl(to_number(trim(a.cups_fee_d)),0))) amt_recv "
			+ "			   from TBL_ALGO_DTL a "
			+ "			   group by a.chnl_id,a.settl_date "
			+ "			union all "
			+ "			select b.inst_code chl_id,b.date_settlmt settle_date "
//			+ "			   sum(nvl(to_number(trim(b.inst_amt_trans)),0)/100) amt_trans, "
//			+ "			   sum(/*nvl(to_number(trim(b.fee_credit)),0)-nvl(to_number(trim(b.fee_debit)),0)*/0) poundage, "
//			+ "			   (sum(nvl(to_number(trim(b.inst_amt_trans)),0)) - sum(/*nvl(to_number(trim(b.fee_credit)),0)-nvl(to_number(trim(b.fee_debit)),0)*/0)) amt_recv "
			+ "			   from BTH_ERR_DTL b "
			+ "			  where b.stlm_flag = '2' "
			+ "			  group by b.inst_code,b.date_settlmt) r "
			+ whereSql.toString() ;
//			+ "			 group by r.chl_id,r.settl_date ";
		
//		String countSql = "SELECT COUNT(*) from ( " + subsql	+ ")";
		
		return countSql;
	}
	
	
	@Override
	protected String genSql() {
		return null;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
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
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the queryInstCode
	 */
	public String getQueryInstCode() {
		return queryInstCode;
	}

	/**
	 * @param queryInstCode the queryInstCode to set
	 */
	public void setQueryInstCode(String queryInstCode) {
		this.queryInstCode = queryInstCode;
	}
	

}
