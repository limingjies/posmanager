
package com.allinfinance.struts.settle.action;

import java.io.FileOutputStream;
import java.util.List;

import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.InformationUtil;
import com.allinfinance.system.util.SysParamUtil;

/**
 * Title: 
 * 
 * File: T4020501Action.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-8-10
 * 
 * Company: Shanghai allinfinance Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T80305Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "EXCEL";
		
		String jasName="";
		String rnName="";
		String resName="";
		
//		if("0".equals(repType)){
			title = InformationUtil.createTitles("V_", 9);
			jasName="T8030505.jasper";
			rnName="RN8030505RN_";
			resName= SysParamUtil.getParam("RN8030505RN");
			data = reportCreator.process(genSql(), title);
			List<Object[]> dataList=CommonFunction.getCommQueryDAO().findBySQLQuery(genSumSql());
			parameters.put("P_1",StringUtil.isEmpty(startDate)?"":startDate);
			parameters.put("P_2",StringUtil.isEmpty(endDate)?"":endDate);
			parameters.put("P_3",startTransDate);
			parameters.put("P_4",endTransDate);
			
			parameters.put("P_5",dataList.get(0)[0]!=null?CommonFunction.moneyFormat(dataList.get(0)[0].toString()):"");
			parameters.put("P_6",dataList.get(0)[1]!=null?CommonFunction.moneyFormat(dataList.get(0)[1].toString()):"");
			
			for (int i = 0; i < data.length; i++) {
				if(StringUtil.isNotEmpty(data[i][5])){
					data[i][5]=CommonFunction.dateFormat(data[i][5].toString());
				}
				if(StringUtil.isNotEmpty(data[i][6])){
					data[i][6]=CommonFunction.moneyFormat(data[i][6].toString());
				}
				if(StringUtil.isNotEmpty(data[i][7])){
					data[i][7]=CommonFunction.moneyFormat(data[i][7].toString());
				}
				
				
			}
			
//		}
		
		if(data.length == 0) {
			writeNoDataMsg("没有找符合条件的记录，无法生成报表");
			return;
		}
		
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
//		parameters.put("P_1", oriSettleDate);
		
		reportCreator.initReportData(getJasperInputSteam(jasName), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rnName + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rnName + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, resName);
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		StringBuffer whereSql = new StringBuffer();
		StringBuffer whereSql2 = new StringBuffer();
//		StringBuffer sql = new StringBuffer();
		if (StringUtil.isNotEmpty(startDate)) {
			whereSql.append(" and rec_crt_ts >='").append(startDate.replace("-", "")+"'");
		}
		if (StringUtil.isNotEmpty(endDate)) {
			whereSql.append(" and rec_crt_ts <='").append(endDate.replace("-", "")+"'");
		}
		if (StringUtil.isNotEmpty(startTransDate)) {
			whereSql2.append(" and a.DATE_SETTLMT >='").append(startTransDate.replace("-", "")+"'");
		}
		if (StringUtil.isNotEmpty(endTransDate)) {
			whereSql2.append(" and a.DATE_SETTLMT <='").append(endTransDate.replace("-", "")+"'");
		}
		// sql
		/*sql.append("SELECT distinct "
				+ "p.mcht_cd,"
				+ "o.mcht_nm,"
				+ "O.bank_no,"
				+ "(select brh_name from tbl_brh_info where brh_id = O.bank_no),"
				+ "p.trans_date,"
				
				+ "nvl((select sum(mcht_at_c) from tbl_algo_dtl where c_d_flg =1 and mcht_cd =p.mcht_cd and trans_date=p.trans_date),0)"
				+ "- nvl((select sum(mcht_at_d) from tbl_algo_dtl where c_d_flg =0 and mcht_cd =p.mcht_cd and trans_date=p.trans_date),0),"
				
				+ "nvl((select sum(mcht_fee_c) from tbl_algo_dtl where c_d_flg =1 and mcht_cd =p.mcht_cd and trans_date=p.trans_date),0)"
				+ "-nvl((select sum(mcht_fee_d) from tbl_algo_dtl where c_d_flg =0 and mcht_cd =p.mcht_cd and trans_date=p.trans_date),0) "
				
				+ " from tbl_algo_dtl p left join tbl_mcht_base_inf o on p.mcht_cd =o.mcht_no ")
		   .append(" where exists (SELECT 1 from  tbl_mcht_base_inf where mcht_no=p.mcht_cd ")
		   .append(whereSql)
		   .append(")")
		   .append(whereSql2);*/
		String sql=" select a.mcht_cd,(select mcht_nm from tbl_mcht_base_inf where a.mcht_cd =mcht_no) as mcht_name,"
				+ " (select mcht_cn_abbr from tbl_mcht_base_inf where a.mcht_cd =mcht_no) as mcht_cn_abbr,"
				+ " (select bank_no from tbl_mcht_base_inf where a.mcht_cd =mcht_no) as bank_no,"
				+ " (select b.brh_name from  tbl_brh_info b where brh_id = (select bank_no from tbl_mcht_base_inf where a.mcht_cd =mcht_no)) as bank_name,"
//				+ " a.trans_date,"
				+ " a.DATE_SETTLMT,"
				+ " abs(nvl(sum(a.MCHT_AT_C-a.MCHT_AT_D),0)) as amt,"
				+ " abs(nvl(sum(a.mcht_fee_d-a.mcht_fee_c),0)) as fee,"
				+ " (select c.disc_nm from  tbl_inf_disc_cd c where disc_cd = (select fee_rate from tbl_mcht_settle_inf where a.mcht_cd =mcht_no)) as disc_nm"
				+ " from tbl_algo_dtl a"
				+ " where EXISTS( select 1 FROM tbl_mcht_base_inf  WHERE mcht_no=a.mcht_cd "+whereSql+" )" +whereSql2
				+ " group by a.DATE_SETTLMT,a.mcht_cd "
				+ " order by a.DATE_SETTLMT,a.mcht_cd ";
		return sql.toString();
	}
	
	private String genSumSql() {
		StringBuffer whereSql = new StringBuffer();
		StringBuffer whereSql2 = new StringBuffer();
//		StringBuffer sql = new StringBuffer();
		if (StringUtil.isNotEmpty(startDate)) {
			whereSql.append(" and rec_crt_ts >='").append(startDate.replace("-", "")+"'");
		}
		if (StringUtil.isNotEmpty(endDate)) {
			whereSql.append(" and rec_crt_ts <='").append(endDate.replace("-", "")+"'");
		}
		if (StringUtil.isNotEmpty(startTransDate)) {
			whereSql2.append(" and a.DATE_SETTLMT >='").append(startTransDate.replace("-", "")+"'");
		}
		if (StringUtil.isNotEmpty(endTransDate)) {
			whereSql2.append(" and a.DATE_SETTLMT <='").append(endTransDate.replace("-", "")+"'");
		}
		String sql=" select "
				+ " abs(nvl(sum(a.MCHT_AT_C-a.MCHT_AT_D),0)) as amt,"
				+ " abs(nvl(sum(a.mcht_fee_d-a.mcht_fee_c),0)) as fee "
				+ " from tbl_algo_dtl a "
				+ " where EXISTS(select  1 FROM tbl_mcht_base_inf  WHERE mcht_no=a.mcht_cd "+whereSql+" )" +whereSql2;
		return sql.toString();
	}
	
	
	private String startDate;
	private String endDate;
	private String startTransDate;
	private String endTransDate;
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

	public String getStartTransDate() {
		return startTransDate;
	}

	public void setStartTransDate(String startTransDate) {
		this.startTransDate = startTransDate;
	}

	public String getEndTransDate() {
		return endTransDate;
	}

	public void setEndTransDate(String endTransDate) {
		this.endTransDate = endTransDate;
	}

}
