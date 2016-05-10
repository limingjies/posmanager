
package com.allinfinance.struts.settleNew.action;

import java.util.List;

import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ExcelCreator;
import com.allinfinance.system.util.SysParamUtil;

public class T100302Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void reportAction() throws Exception {
		
		List<Object[]> dataList= CommonFunction.getCommQueryDAO().findBySQLQuery(genSql());
		
		
		if(dataList.size() == 0) {
			writeNoDataMsg("没有找符合条件的记录，无法生成报表");
			return;
		}
		
		String rnName="RN100302RN_";
		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rnName + 
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		
		String titleName= SysParamUtil.getParam("RN100302RN");
		String coulmHeader[]={"清算日期","商户","所属机构","挂账金额"};
		ExcelCreator excelCreator=new ExcelCreator(titleName, coulmHeader, dataList);
		excelCreator.exportReport(fileName);
		
		
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		
		StringBuffer whereSql = new StringBuffer(" where    a.hanging_flag= '0' ");

	  
	    if (isNotEmpty(startDate)) {
			whereSql.append(" AND a.date_stlm ").append(" >= ").append("'")
					.append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND a.date_stlm").append(" <= ").append("'")
					.append(endDate).append("'");
		}
		if (isNotEmpty(queryMchtNoNm)) {
			 whereSql.append(" AND a.mcht_id").append(" = ").append("'").append(queryMchtNoNm).append("'");
		}
	    
	    String sql = "SELECT "
	    		+ "to_char(to_date(a.date_stlm,'yyyy-mm-dd'),'yyyy-mm-dd') as settle_date,"
	    		+ "(a.mcht_id||' - '||b.mcht_nm) as mcht_no_nm,"
				+ "(select c.brh_id||'-'||c.brh_name from tbl_brh_info c where trim(c.brh_id)=trim(a.brh_id)) as bank_no_name,"
				+ "a.amt_settle "
				+ "from TBL_SETTLE_HANGING a left join tbl_mcht_base_inf b on a.mcht_id=b.mcht_no ";
	    
	    sql = sql + whereSql.toString();
	    sql=sql+" order by date_stlm desc";
		return sql.toString();
	}
	
	
	
	private String startDate;
	private String endDate;
	private String queryMchtNoNm;

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


	/**
	 * @return the queryMchtNoNm
	 */
	public String getQueryMchtNoNm() {
		return queryMchtNoNm;
	}

	/**
	 * @param queryMchtNoNm the queryMchtNoNm to set
	 */
	public void setQueryMchtNoNm(String queryMchtNoNm) {
		this.queryMchtNoNm = queryMchtNoNm;
	}
	
	/*//结算标志
	private String settleFlag(String val) {
		if("0".equals(val))
			return "未结算";
		else if("2".equals(val))
			return "已结算";
		else if("4".equals(val))
			return "暂缓结算";
		return val;
	}*/

}
