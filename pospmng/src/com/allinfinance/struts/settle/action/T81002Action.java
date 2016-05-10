
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
 * Title:准退货退款明细报表
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-12-01
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * author: 徐鹏飞
 *  
 * time: 2015年5月14日下午3:18:12
 * 
 * version: 1.0
 */
public class T81002Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		ExcelReportCreator excelReportCreator=new ExcelReportCreator(); 
		
		List<String> sheetList=new ArrayList<String>();
		List<String> titleList=new ArrayList<String>();
		List<String[]> coulmHeaderList=new ArrayList<String[]>();
		List<String[]> paramList=new ArrayList<String[]>();
		
		List<String> dataSqlList=new ArrayList<String>();

		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(getCountSql());
		if("0".equals(count)){
			writeNoDataMsg("没有找符合条件的记录，无法生成报表");
			return;
		}
		String rnName="RN81002RN_";
		String resName= SysParamUtil.getParam("RN81002RN");
		String[] coulmHeader={"退款日期","商户号","机构号","退款金额","原准退货日期","原准退货流水","原准退货金额","退货手续费","应退金额","退货状态"};
		String[] param=new String[2];
		param[0]="退款日期："+(StringUtil.isEmpty(settlmtDate)?"":settlmtDate);
		
		sheetList.add(resName);
		titleList.add(resName);
		coulmHeaderList.add(coulmHeader);
		dataSqlList.add(genSql());
		paramList.add(param);
			
		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rnName + 
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		outputStream = new FileOutputStream(fileName);
		
		int leftFormat[]={1,2};
		excelReportCreator.setLeftFormat(leftFormat);
		excelReportCreator.setSheetList(sheetList);
		excelReportCreator.setTitleList(titleList);
		excelReportCreator.setParamList(paramList);
		excelReportCreator.setCoulmHeaderList(coulmHeaderList);
		excelReportCreator.setDataList(excelReportCreator.processData(dataSqlList, coulmHeaderList));
			
		excelReportCreator.exportReport(outputStream);
		outputStream.close();
		
		log(CommonFunction.getCurrentTime());
		writeUsefullMsg(fileName);
		return;
	}

	private String getCountSql(){
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(settlmtDate)) {
			whereSql.append(" AND a.SETTLMT_DATE ='").append(settlmtDate.replace("-", "")+"'");
		}
		String sql="SELECT COUNT(*) FROM TBL_ZTH_DTL a "
				+ whereSql;
		return sql.toString();
	}

	protected String genSql(){
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(settlmtDate)) {
			whereSql.append(" AND a.SETTLMT_DATE ='").append(settlmtDate.replace("-", "")+"'");
		}
		String sql="SELECT "
				+ "to_char(to_date(a.SETTLMT_DATE,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd') AS SETTLMT_DATE,"
				+ "a.MCHT_NO||' - ' ||b.MCHT_NAME,"
				+ "(SELECT c.brh_id||' - '||c.brh_name FROM TBL_BRH_INFO c WHERE c.brh_id in (select bank_no from TBL_MCHT_BASE_INF f where f.MCHT_NO=a.MCHT_NO))AS brh_id_name,"
				+ "to_char(nvl(a.AMT,0),'99,999,999,990.99') as amt,"
				+ "to_char(to_date(a.INST_DATE,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd') AS INST_DATE,"
				+ "a.SYS_SEQ_NUM,"
				+ "to_char(nvl(b.F4,0),'99,999,999,990.99') as amtOF,"
				+ "to_char(nvl(b.NFEE,0),'99,999,999,990.99') as amtFee,"
				+ "to_char(nvl(b.SAMT,0),'99,999,999,990.99') as amtFA,"
				+ "(case when b.EFLAG = '0' then '未退货' WHEN  b.EFLAG = '1' THEN '完成退货' WHEN  b.EFLAG = '2' THEN '部分退货' END) AS flag "
				+ "FROM TBL_ZTH_DTL a LEFT JOIN TBL_ZTH b ON a.INST_DATE = b.INST_DATE AND a.SYS_SEQ_NUM = b.SYS_SEQ_NUM "
				+ whereSql;
		return sql.toString();
	}

	private String settlmtDate;
	
	/**
	 * @return the settlmtDate
	 */
	public String getSettlmtDate() {
		return settlmtDate;
	}

	/**
	 * @param settlmtDate the settlmtDate to set
	 */
	public void setSettlmtDate(String settlmtDate) {
		this.settlmtDate = settlmtDate;
	}

}
