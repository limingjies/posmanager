
package com.allinfinance.struts.settle.action;

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
 * 
 * 描述：:准退货退款明细报表_新
 * @author tangly
 * 2015年8月19日下午5:01:40
 */
public class T81003Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		ExcelReportCreator excelReportCreator=new ExcelReportCreator(); 		
		List<String> sheetList=new ArrayList<String>();
		List<String> titleList=new ArrayList<String>();
		List<String[]> coulmHeaderList=new ArrayList<String[]>();		
		List<String> dataSqlList=new ArrayList<String>();

		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(getCountSql());
		if("0".equals(count)){
			writeNoDataMsg("没有找符合条件的记录，无法生成报表");
			return;
		}
		String rnName="RN810011RN_";
		String resName= SysParamUtil.getParam("RN810011RN");
		String[] coulmHeader={"准退货日期","准退货时间","准退货流水号",
				"应答码","应答码描述信息","终端流水号","卡号","退货本金",
				"退货手续费","退还金额","已退金额","退货状态","准退货完成日期","终端号"
				,"商户名称","原交易金额","原交易终端流水号","原交易日期","原交易时间"};
		
		sheetList.add(resName);
		titleList.add(resName);
		coulmHeaderList.add(coulmHeader);
		dataSqlList.add(genSql());
			
		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rnName + 
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		outputStream = new FileOutputStream(fileName);
		
		int leftFormat[]={6,14};
		excelReportCreator.setLeftFormat(leftFormat);
		excelReportCreator.setSheetList(sheetList);
		excelReportCreator.setTitleList(titleList);
		excelReportCreator.setCoulmHeaderList(coulmHeaderList);
		excelReportCreator.setDataList(excelReportCreator.processData(dataSqlList, coulmHeaderList));		
		excelReportCreator.exportReport(outputStream);
		outputStream.close();		
		log(CommonFunction.getCurrentTime());
		writeUsefullMsg(fileName);
		return;
	}

	private String getCountSql(){
		StringBuffer whereSql = new StringBuffer(" where 1=1 and rspcode='00' ");
		if (StringUtil.isNotEmpty(startDate) && !StringUtil.isNotEmpty(endDate)) {
			whereSql.append(" AND INST_DATE ='").append(startDate.replace("-", "")+"'");
		}
		if (!StringUtil.isNotEmpty(startDate) && StringUtil.isNotEmpty(endDate)) {
			whereSql.append(" AND INST_DATE <='").append(endDate.replace("-", "")+"'");
		}
		if (StringUtil.isNotEmpty(startDate) && StringUtil.isNotEmpty(endDate)) {
			whereSql.append(" AND INST_DATE >='").append(startDate.replace("-", "")+"'");
			whereSql.append(" AND INST_DATE <='").append(endDate.replace("-", "")+"'");
		}
		String sql="SELECT COUNT(*) FROM TBL_ZTH " + whereSql.toString();
		return sql;
	}
	protected String genSql(){
		StringBuffer whereSql = new StringBuffer(" where 1=1 and rspcode='00' ");

		if (StringUtil.isNotEmpty(startDate) && !StringUtil.isNotEmpty(endDate)) {
			whereSql.append(" AND INST_DATE ='").append(startDate.replace("-", "")+"'");
		}
		if (!StringUtil.isNotEmpty(startDate) && StringUtil.isNotEmpty(endDate)) {
			whereSql.append(" AND INST_DATE <='").append(endDate.replace("-", "")+"'");
		}
		if (StringUtil.isNotEmpty(startDate) && StringUtil.isNotEmpty(endDate)) {
			whereSql.append(" AND INST_DATE >='").append(startDate.replace("-", "")+"'");
			whereSql.append(" AND INST_DATE <='").append(endDate.replace("-", "")+"'");
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("select to_char(to_date(inst_date,'yyyymmdd'),'yyyy-mm-dd') as instDate,");
		sql.append(" to_char(to_date(inst_time,'hh24miss'),'hh24:mi:ss') as instTime,"); 
		sql.append(" sys_seq_num as sysSeqNum,"); 
		sql.append(" rspcode,"); 
		sql.append(" rspdsp,"); 
		sql.append(" f11_pos as f11Pos,"); 
		sql.append(" f2,"); 
		sql.append(" to_char(nvl(f4,0),'99,999,999,990.99') as f4,"); 
		sql.append(" to_char(nvl(nfee,0),'99,999,999,990.99') as nfee,"); 
		sql.append(" to_char(nvl(samt,0),'99,999,999,990.99') as samt,"); 
		sql.append(" to_char(nvl(amt1,0),'99,999,999,990.99') as amt1,"); 
		sql.append(" decode(eflag,'0','未退货','1','完成退货','2','部分退货',eflag) as eflag,"); 
		sql.append(" to_char(to_date(ddate,'yyyymmdd'),'yyyy-mm-dd')  as ddate,"); 
		sql.append(" f41,"); 
		sql.append(" f42||'-'||(select mcht_nm from tbl_mcht_base_inf where mcht_no=f42) as f42,"); 
		sql.append(" to_char(nvl(o_amt,0),'99,999,999,990.99') as oAmt,"); 
		sql.append(" o_f11 as oF11,"); 
		sql.append(" to_char(to_date(o_f13,'mmdd'),'mm-dd')  as oF13,"); 
		sql.append(" to_char(to_date(o_f12,'hh24miss'),'hh24:mi:ss')  as oF12"); 
		sql.append(" from TBL_ZTH"); 
		sql.append(whereSql.toString());
		sql.append(" ORDER BY INST_DATE ,INST_TIME "); 
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
