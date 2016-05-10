
package com.allinfinance.struts.settle.action;

import java.io.FileOutputStream;
import java.util.List;

import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.InformationUtil;
import com.allinfinance.system.util.SysParamUtil;

/**
 * Title: 
 * 
 * File: T80403Action.java
 * 
 * Description:
 * 
 * Company: Shanghai allinfinance Software Systems Co., Ltd.
 * 
 * @author caotz
 * 
 * @version 1.0
 */
public class T8040301Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void reportAction() throws Exception {
		
		reportType = "EXCEL";
		
		String jasName="";
		String rnName="";
		String resName="";
		
//		if("0".equals(repType)){
			title = InformationUtil.createTitles("V_", 14);
			jasName="T80403.jasper";
			rnName="RN80403RN_";
			resName= SysParamUtil.getParam("RN80403RN");
			data = reportCreator.process(genSql(), title);
			List<Object[]> dataList=CommonFunction.getCommQueryDAO().findBySQLQuery(genSumSql());
			parameters.put("P_1",StringUtil.isEmpty(startDate)?"":startDate);
			parameters.put("P_2",StringUtil.isEmpty(endDate)?"":endDate);
			parameters.put("P_3",brhId);
			
			parameters.put("P_4",dataList.get(0)[0]!=null?CommonFunction.moneyFormat(dataList.get(0)[0].toString()):"");
			parameters.put("P_5",dataList.get(0)[1]!=null?CommonFunction.moneyFormat(dataList.get(0)[1].toString()):"");
			parameters.put("P_6",dataList.get(0)[2]!=null?CommonFunction.moneyFormat(dataList.get(0)[2].toString()):"");
			
			/*for (int i = 0; i < data.length; i++) {
				if(StringUtil.isNotEmpty(data[i][2])){
					data[i][2]=CommonFunction.dateFormat(data[i][2].toString());
				}
				if(StringUtil.isNotEmpty(data[i][4])){
					data[i][4]=formatMccFlag(data[i][4].toString());
				}
				if(StringUtil.isNotEmpty(data[i][10])){
					data[i][10]=CommonFunction.moneyFormat(data[i][10].toString());
				}
				if(StringUtil.isNotEmpty(data[i][11])){
					data[i][11]=CommonFunction.moneyFormat(data[i][11].toString());
				}
				if(StringUtil.isNotEmpty(data[i][13])){
					data[i][13]=CommonFunction.moneyFormat(data[i][13].toString());
				}
			}*/
		
		if(data.length == 0) {
			writeNoDataMsg("没有找符合条件的记录，无法生成报表");
			return;
		}
		
		
		reportModel.setData(data);
		reportModel.setTitle(title);
		
//		parameters.put("P_1", oriSettleDate);
		
		reportCreator.initReportData(getJasperInputSteam(jasName), parameters, 
				reportModel.wrapReportDataSource(), getReportType());
		
		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rnName + 
					operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		
		outputStream = new FileOutputStream(fileName);
		
		reportCreator.exportReport(outputStream, resName);
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(startDate)) {
			whereSql.append(" and a.DATE_SETTLMT >='").append(startDate.replace("-", "")+"'");
		}
		if (StringUtil.isNotEmpty(endDate)) {
			whereSql.append(" and a.DATE_SETTLMT <='").append(endDate.replace("-", "")+"'");
		}
		if (StringUtil.isNotEmpty(brhId)) {
			whereSql.append(" and a.mcht_cd in (select mcht_no from tbl_mcht_base_inf  where bank_no ='"+ brhId.trim()+ "' )");
		}
		String sql=" select "
				+ "a.mcht_cd,"
				+ "(select mcht_nm from tbl_mcht_base_inf where mcht_no=a.mcht_cd) as mcht_name,"
				+ "(select to_char(to_date(rec_crt_ts,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd') from tbl_mcht_base_inf where mcht_no=a.mcht_cd) as mcht_crt_date,"
				+ "(select brh_id||'-'||brh_name from  tbl_brh_info  where brh_id in (select bank_no from tbl_mcht_base_inf where mcht_no=a.mcht_cd)) as bank_name,"
				+ "(select decode(tcc,'0','标准MCC','1','非标准MCC',tcc) from tbl_mcht_base_inf where mcht_no=a.mcht_cd) as mcc_falg,"
				+ "(select MCHNT_TP_GRP||'-'||DESCR from TBL_INF_MCHNT_TP_GRP where MCHNT_TP_GRP in (select MCHNT_TP_GRP from TBL_INF_MCHNT_TP where MCHNT_TP in (select MCC from TBL_MCHT_BASE_INF where mcht_no=a.mcht_cd ))) as mcc_grp,"
				+ "(select MCHNT_TP||'-'||DESCR from TBL_INF_MCHNT_TP where MCHNT_TP in (select MCC from TBL_MCHT_BASE_INF where mcht_no=a.mcht_cd )) as mcht_mcc,"
				+ "'费率:'||to_char(a.mcht_fee_pct_min,'90.99')||' - '||'封顶:'||to_Char(a.mcht_fee_pct_max,'90.99') as mcht_fee_pct,"
				+ "'费率:'||to_char(a.cups_allot_d,'90.99')||' - '||'封顶:'||to_Char(a.cups_allot_c,'90.99') as cups_allot,"
				+ "to_char(a.mcht_fee_pct_min-a.cups_allot_d,'90.99') as settle_brh_fee_pct,"
				+ "to_char(abs(nvl(sum(a.MCHT_AT_C-a.MCHT_AT_D),0)),'99,999,999,990.99') as amt,"
				+ "to_char(abs(nvl(sum(a.mcht_fee_d-a.mcht_fee_c),0)),'99,999,999,990.99') as fee,"
				+ "count(1) as txn_count,"
				+ "to_char(abs(nvl(sum(a.acq_ins_allot_c-a.acq_ins_allot_d),0)),'99,999,999,990.99') as brh_fee "
//				+ " (select c.disc_nm from  tbl_inf_disc_cd c where disc_cd = (select fee_rate from tbl_mcht_settle_inf where a.mcht_cd =mcht_no)) as disc_nm"
				+ " from tbl_algo_dtl a"
				+ whereSql
				+ " group by a.mcht_cd,mcht_fee_pct_min,mcht_fee_pct_max,cups_allot_d,cups_allot_c "
				+ " order by a.mcht_cd ";
		return sql.toString();
	}
	
	private String genSumSql() {
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(startDate)) {
			whereSql.append(" and a.DATE_SETTLMT >='").append(startDate.replace("-", "")+"'");
		}
		if (StringUtil.isNotEmpty(endDate)) {
			whereSql.append(" and a.DATE_SETTLMT <='").append(endDate.replace("-", "")+"'");
		}
		if (StringUtil.isNotEmpty(brhId)) {
			whereSql.append(" and a.mcht_cd in (select mcht_no from tbl_mcht_base_inf  where bank_no ='"+ brhId.trim()+ "' )");
		}
		String sql=" select "
				+ " abs(nvl(sum(a.MCHT_AT_C-a.MCHT_AT_D),0)) as amt,"
				+ " abs(nvl(sum(a.mcht_fee_d-a.mcht_fee_c),0)) as fee, "
				+ "abs(nvl(sum(a.acq_ins_allot_c-a.acq_ins_allot_d),0)) as brh_fee "
				+ " from tbl_algo_dtl a "
				+ whereSql;
		return sql.toString();
	}
	
	/*private String formatMccFlag(String tcc){
		switch (Integer.parseInt(tcc)) {
			case 0:return "标准MCC";
			case 1:return "非标准MCC";
			default:return tcc;
		}
	}*/
	
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
