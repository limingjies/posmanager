
package com.allinfinance.struts.settleNew.action;

import java.util.List;

import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ExcelCreator;
import com.allinfinance.system.util.SysParamUtil;

public class T100303Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void reportAction() throws Exception {
		
		List<Object[]> dataList= CommonFunction.getCommQueryDAO().findBySQLQuery(genSql());
		
		
		if(dataList.size() == 0) {
			writeNoDataMsg("没有找符合条件的记录，无法生成报表");
			return;
		}
		
		String rnName="RN100303RN_";
		fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + rnName + 
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		
		String titleName= SysParamUtil.getParam("RN100303RN");
		String coulmHeader[]={"清算日期","商户","所属机构","实际结算日期","结算标志","交易笔数","交易金额","手续费","清算金额","清算通道"};
		ExcelCreator excelCreator=new ExcelCreator(titleName, coulmHeader, dataList);
		excelCreator.exportReport(fileName);
		
		
		writeUsefullMsg(fileName);
		return;
	}
	
	
	@Override
	protected String genSql() {
		
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");

		if (isNotEmpty(startDate)) {
			whereSql.append(" and a.date_stlm ").append(" >= ").append("'")
					.append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" and a.date_stlm").append(" <= ").append("'")
					.append(endDate).append("'");
		}
		if (isNotEmpty(queryAmtTransLow)) {
			whereSql.append(" and nvl(a.stlm_amt_trans,0) ").append(" >= ")
					.append("'").append(queryAmtTransLow).append("' ");
		}
		if (isNotEmpty(queryAmtTransUp)) {
			whereSql.append(" and nvl(a.stlm_amt_trans,0) ").append(" <= ")
					.append("'").append(queryAmtTransUp).append("' ");
		}
		if (isNotEmpty(queryMchtNoNm)) {
			 whereSql.append(" and a.mcht_id").append(" = ").append("'").append(queryMchtNoNm).append("' ");
		}
		if (isNotEmpty(queryBrhId)) {
			whereSql.append(" and trim(a.brh_id) in(select brh_id from tbl_brh_info where brh_id= '").append(queryBrhId).append("' "
					+ " or up_brh_id='").append(queryBrhId).append("' )");
		}
		String sql = "select "
				+ "to_char(to_date(a.date_stlm,'yyyy-mm-dd'),'yyyy-mm-dd') as settle_date,"
				+ "a.mcht_id||(select ' - '||mcht_nm from tbl_mcht_base_inf where mcht_no=a.mcht_id) as mcht_no_nm,"
				+ "trim(a.brh_id)||(select ' - '||b.brh_name from tbl_brh_info b where b.brh_id = trim(a.brh_id)) as brh_id_name,"
				+ "to_char(to_date(a.date_stlm_true,'yyyy-mm-dd'),'yyyy-mm-dd') as settle_date_fact,"
				+ "decode(a.stlm_flag,'0','待结算','1','不可结算','2','已结算',a.stlm_flag),"
				+ "a.stlm_num,"
				+ "a.stlm_amt_trans,"
				+ "a.stlm_amt_fee,"
				+ "a.stlm_amt, "
				+ "a.inst_code||(select ' - '||first_brh_name from tbl_first_brh_dest_id where first_brh_id=a.inst_code)as inst_code_name "
				+ "FROM tbl_mcht_settle_dtl a  " + whereSql.toString();
		sql=sql+" order by a.date_stlm desc,a.brh_id,a.mcht_id ";
		return sql;
	}
	
	
	
	private String startDate;
	private String endDate;
	private String queryAmtTransLow;
	private String queryAmtTransUp;
	private String queryMchtNoNm;
	private String queryBrhId;

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
	 * @return the queryAmtTransLow
	 */
	public String getQueryAmtTransLow() {
		return queryAmtTransLow;
	}

	/**
	 * @param queryAmtTransLow the queryAmtTransLow to set
	 */
	public void setQueryAmtTransLow(String queryAmtTransLow) {
		this.queryAmtTransLow = queryAmtTransLow;
	}

	/**
	 * @return the queryAmtTransUp
	 */
	public String getQueryAmtTransUp() {
		return queryAmtTransUp;
	}

	/**
	 * @param queryAmtTransUp the queryAmtTransUp to set
	 */
	public void setQueryAmtTransUp(String queryAmtTransUp) {
		this.queryAmtTransUp = queryAmtTransUp;
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

	/**
	 * @return the queryBrhId
	 */
	public String getQueryBrhId() {
		return queryBrhId;
	}

	/**
	 * @param queryBrhId the queryBrhId to set
	 */
	public void setQueryBrhId(String queryBrhId) {
		this.queryBrhId = queryBrhId;
	}
	

}
