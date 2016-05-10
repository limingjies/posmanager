
package com.allinfinance.struts.query.action;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.dao.iface.base.TblBrhInfoDAO;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.ExcelReportCreator;
import com.allinfinance.system.util.SysParamUtil;

/**
 * Title: 
 * 
 * File: T50301Action.java
 * 
 * Description:
 * 
 * Company: Shanghai allinfinance Software Systems Co., Ltd.
 * 
 * @author caotz
 * 
 * @version 1.0
 */
public class T50301Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;
	
	private String startDate;
	private String endDate;
	private String brhId;

	private TblBrhInfoDAO tblBrhInfoDAO = (TblBrhInfoDAO) ContextUtil.getBean("BrhInfoDAO");
	
	@Override
	protected void reportAction() throws Exception {
		log(CommonFunction.getCurrentTime());
		ExcelReportCreator excelReportCreator=new ExcelReportCreator(); 
		
		List<String> sheetList=new ArrayList<String>();
		List<String> titleList=new ArrayList<String>();
		List<String[]> coulmHeaderList=new ArrayList<String[]>();
		List<String[]> paramList=new ArrayList<String[]>();
		
		List<String> dataSqlList=new ArrayList<String>();
//		List<String> sumDataSqlList=null;
		/*String dataSql=null;
		String sumDataSql=null;*/
		
		String rnName="RN50301RN_";
		String resName="新"+ SysParamUtil.getParam("RN50301RN");
		String[] coulmHeader={"入网日期","商户号","商户名称","商户简称","所属机构",
				"商户费率","终端数量","终端号"};
		String[] param=new String[3];
		param[0]="起始日期："+(StringUtil.isEmpty(startDate)?"":startDate);
		param[1]="结束日期："+(StringUtil.isEmpty(endDate)?"":endDate);
		param[2]="所属机构："+(StringUtil.isEmpty(brhId)?"":brhId+
				"-"+(StringUtil.isNull(tblBrhInfoDAO.get(brhId))?"":tblBrhInfoDAO.get(brhId).getBrhName()));
		
		sheetList.add(resName);
		titleList.add(resName);
		coulmHeaderList.add(coulmHeader);
		dataSqlList.add(getDataSql());
		paramList.add(param);
		

			
		fileName =SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rnName + 
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		outputStream = new FileOutputStream(fileName);

		int leftFormat[]={2,3,4,7};
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
	
	
	private  String getDataSql() {
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (StringUtil.isNotEmpty(startDate)) {
			whereSql.append(" and substr(a.rec_crt_ts,1,8) >='").append(startDate.replace("-", "")+"'");
		}
		if (StringUtil.isNotEmpty(endDate)) {
			whereSql.append(" and substr(a.rec_crt_ts,1,8) <='").append(endDate.replace("-", "")+"'");
		}
		if (StringUtil.isNotEmpty(brhId)) {
			whereSql.append(" and a.bank_no in(select brh_id from tbl_brh_info  "
					+ "start with brh_id='"+brhId+"' connect by prior  brh_id = UP_BRH_ID) ");
		}
		
		String sql=" SELECT "
				+ "to_char(to_date(rec_crt_ts,'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd') as infile_date,"
				+ "a.mcht_no,trim(a.mcht_nm) as mcht_name,trim(a.mcht_cn_abbr) as mcht_cn_name,"
				+ "(select CREATE_NEW_NO||'-'||trim(brh_name) from tbl_brh_info where brh_id=a.bank_no) as brh_id_name,"
				+ "(select to_char(fee_value,'90.99') from tbl_his_disc_algo where disc_id in"
						+ "(select b.fee_rate from tbl_mcht_settle_inf b where b.mcht_no=a.mcht_no)) as fee_val,"
				+ "(select count(*) from tbl_term_inf where mcht_cd=a.mcht_no) as term_count,"
				+ "(select wm_concat (trim(b.term_id))  from tbl_term_inf b where a.mcht_no=b.mcht_cd ) new_result "
				+ " FROM tbl_mcht_base_inf a "
				+ whereSql
				+ " order by a.rec_crt_ts ";
		return sql.toString();
	}
	
	
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
