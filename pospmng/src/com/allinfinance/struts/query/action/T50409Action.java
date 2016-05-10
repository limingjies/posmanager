
package com.allinfinance.struts.query.action;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ExcelReportCreator;
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
public class T50409Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void reportAction() throws Exception {
		log(CommonFunction.getCurrentTime());
		ExcelReportCreator excelReportCreator=new ExcelReportCreator(); 
		
		List<String> sheetList=new ArrayList<String>();
		List<String> titleList=new ArrayList<String>();
		List<String[]> coulmHeaderList=new ArrayList<String[]>();
		List<String[]> paramList=new ArrayList<String[]>();
		
		List<String> dataSqlList=new ArrayList<String>();
		List<String> sumDataSqlList=new ArrayList<String>();
		/*String dataSql=null;
		String sumDataSql=null;*/
		
		List<Object[]> brhList=CommonFunction.getCommQueryDAO().findBySQLQuery(getBrhSql());
		if(brhList.size()==0){
			writeNoDataMsg("没有找符合条件的记录，无法生成报表");
			return;
		}
		String rnName="RN80403RN_";
		String resName= "新"+SysParamUtil.getParam("RN80403RN");
		String[] coulmHeader={"商户号","商户名称","所属机构","入网日期","是否标准入网",
				"商户组别","商户MCC","商户费率","代理商基础费率","通道费率",
				"交易金额","交易手续费","交易笔数","分润"};
		String[] param=new String[2];
		param[0]="起始日期："+(StringUtil.isEmpty(startDate)?"":startDate);
		param[1]="结束日期："+(StringUtil.isEmpty(endDate)?"":endDate);
		
		for (Object[] objects : brhList) {
			sheetList.add(objects[1].toString());
			titleList.add(resName+"_"+(StringUtil.isNotEmpty(objects[1])?objects[1].toString():" "));
//			titleList.add(StringUtil.isNotEmpty(objects[1])?objects[1].toString():" ");
			coulmHeaderList.add(coulmHeader);
			dataSqlList.add(getDataSql(objects[0].toString()));
			sumDataSqlList.add(getSumSql(objects[0].toString()));
//			param.put("所属机构",StringUtil.isNotEmpty(objects[1])?objects[1].toString():" ");
			paramList.add(param);
		}
		

			
		fileName =SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rnName + 
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		outputStream = new FileOutputStream(fileName);

		
		excelReportCreator.setSheetList(sheetList);
		excelReportCreator.setTitleList(titleList);
		excelReportCreator.setParamList(paramList);
		excelReportCreator.setCoulmHeaderList(coulmHeaderList);
		excelReportCreator.setDataList(excelReportCreator.processData(dataSqlList, coulmHeaderList));
		excelReportCreator.setSumList(excelReportCreator.processData(sumDataSqlList, coulmHeaderList));
		
		
		excelReportCreator.exportReport(outputStream);
		outputStream.close();
		
		/*long init;
		long max;
		long used;
		init = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getInit(); 
		max = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax(); 
		used = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed();
		log("初始："+init/1024/1024+"M=====最大："+max/1024+"M======使用："+used/2014/1024+"M");*/
		log(CommonFunction.getCurrentTime());
		writeUsefullMsg(fileName);
		return;
	}
	
	
	private  String getDataSql(String brhIdQuerry) {
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(startDate)) {
			whereSql.append(" and a.DATE_STLM >='").append(startDate.replace("-", "")+"'");
		}
		if (StringUtil.isNotEmpty(endDate)) {
			whereSql.append(" and a.DATE_STLM <='").append(endDate.replace("-", "")+"'");
		}
		whereSql.append(" and a.MCHT_ID in (select mcht_no from tbl_mcht_base_inf  where bank_no in"+
				"(SELECT brh_id FROM tbl_brh_info  start with brh_id = '"+brhIdQuerry+"' connect by prior  brh_id = UP_BRH_ID ))");
		String sql=" select "
				+ "a.MCHT_ID,"
				+ "(select trim(mcht_nm) from tbl_mcht_base_inf where mcht_no=a.MCHT_ID) as mcht_name,"
				+ "(select brh_id||'-'||brh_name from  tbl_brh_info  where brh_id in (select bank_no from tbl_mcht_base_inf where mcht_no=a.MCHT_ID)) as bank_name,"
				+ "(select to_char(to_date(rec_crt_ts,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd') from tbl_mcht_base_inf where mcht_no=a.MCHT_ID) as mcht_crt_date,"
				+ "(select decode(tcc,'0','标准入网','1','非标准入网',tcc) from tbl_mcht_base_inf where mcht_no=a.MCHT_ID) as mcc_falg,"
				+ "(select MCHNT_TP_GRP||'-'||DESCR from TBL_INF_MCHNT_TP_GRP where MCHNT_TP_GRP in (select MCHNT_TP_GRP from TBL_INF_MCHNT_TP where MCHNT_TP in (select MCC from TBL_MCHT_BASE_INF where mcht_no=a.MCHT_ID ))) as mcc_grp,"
				+ "(select MCHNT_TP||'-'||DESCR from TBL_INF_MCHNT_TP where MCHNT_TP in (select MCC from TBL_MCHT_BASE_INF where mcht_no=a.MCHT_ID )) as mcht_mcc,"
				+ "'费率:'||to_char(a.MCHT_FEE_MIN,'90.99')||' - '||'封顶:'||to_Char(decode(a.MCHT_FEE_MAX,'999999999','0',a.MCHT_FEE_MAX),'999,990.99') as mcht_fee_pct,"
				+ "'费率:'||to_char(a.MCHT_ALLOT_DB,'90.999')||' - '||'封顶:'||to_Char(decode(a.MCHT_ALLOT_CR,'999999999','0',a.MCHT_ALLOT_CR),'999,990.999') as cups_allot,"
				+ "to_char((case when a.MCHT_ALLOT_DB>a.MCHT_FEE_MIN then 0 else a.MCHT_FEE_MIN-a.MCHT_ALLOT_DB end),'90.999') as settle_brh_fee_pct,"
				+ "to_char(nvl(sum(a.MCHT_AMT_CR-a.MCHT_AMT_DB),0),'99,999,999,990.999') as amt,"
				+ "to_char(nvl(sum(a.MCHT_FEE_DB-MCHT_FEE_CR),0),'99,999,999,990.999') as fee,"
				+ "count(1) as txn_count,"
				+ "to_char(nvl(sum(a.BRH_ALLOT_CR-a.BRH_ALLOT_DB),0),'99,999,999,990.999') as brh_fee "
//				+ " (select c.disc_nm from  tbl_inf_disc_cd c where disc_cd = (select fee_rate from tbl_mcht_settle_inf where a.MCHT_ID =mcht_no)) as disc_nm"
				+ " from TBL_CLEAR_DTL a"
				+ whereSql
				+ " group by a.MCHT_ID,MCHT_FEE_MIN,MCHT_FEE_MAX,MCHT_ALLOT_DB,MCHT_ALLOT_CR "
				+ " order by a.MCHT_ID ";
		return sql.toString();
	}
	
	private String getSumSql(String brhIdQuerry) {
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(startDate)) {
			whereSql.append(" and a.DATE_STLM >='").append(startDate.replace("-", "")+"'");
		}
		if (StringUtil.isNotEmpty(endDate)) {
			whereSql.append(" and a.DATE_STLM <='").append(endDate.replace("-", "")+"'");
		}
		whereSql.append(" and a.MCHT_ID in (select mcht_no from tbl_mcht_base_inf  where bank_no in"+
				"(SELECT brh_id FROM tbl_brh_info  start with brh_id = '"+brhIdQuerry+"' connect by prior  brh_id = UP_BRH_ID ))");
		String sql=" select '总计','','','','','','','','','',"
				+ " to_char(nvl(sum(a.MCHT_AMT_CR-a.MCHT_AMT_DB),0),'99,999,999,990.999') as amt,"
				+ " to_char(nvl(sum(a.MCHT_FEE_DB-MCHT_FEE_CR),0),'99,999,999,990.999') as fee, "
				+ " to_char(count(1),'99,999,999,999') as txn_count,"
				+ "	to_char(nvl(sum(a.BRH_ALLOT_CR-a.BRH_ALLOT_DB),0),'99,999,999,990.999') as brh_fee "
				+ " from TBL_CLEAR_DTL a "
				+ whereSql;
		return sql.toString();
	}
	
	private String getBrhSql(){
		StringBuffer whereSqlBrh = new StringBuffer();
		if (StringUtil.isNotEmpty(brhId)) {
			whereSqlBrh.append(" and brh_id ='").append(brhId+"'");
		}
		String sqlBrh="select BRH_ID,BRH_ID||'-'||BRH_NAME from TBL_BRH_INFO where UP_BRH_ID='00001' "+
				whereSqlBrh+" order by BRH_ID ";
		return sqlBrh;
	}
	
	
	private String startDate;
	private String endDate;
	private String brhId;

	
	
	protected String genSql(){
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

	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	

}
