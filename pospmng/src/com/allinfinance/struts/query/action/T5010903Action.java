package com.allinfinance.struts.query.action;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import com.allinfinance.common.Constants;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.SysParamUtil;
import com.allinfinance.system.util.excelReport;

/**
 * 所有明细下载
 * @author Jee Khan
 *
 */
@SuppressWarnings("serial")
public class T5010903Action extends BaseAction {

	private String startDate;
	private String endDate;

	@SuppressWarnings("unchecked")
	@Override
	protected String subExecute() throws Exception {
		String sql = getSql();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		DecimalFormat df=new DecimalFormat("0.00");
        if(dataList.size()>0){
        	Object[] sumData = new Object[dataList.get(0).length];
        	sumData[7] = "合计";
        	sumData[8] = 0;
        	sumData[9] = 0;
        	sumData[10] = 0;
        	for (Object[] objects : dataList) {       		
        		if(objects[2]!=null&&!"".equals(objects[2])){
        			objects[2]=CommonFunction.dateFormat(objects[2].toString());
        		}
        		if(objects[3]!=null&&!"".equals(objects[3])){
        			objects[3]=CommonFunction.dateFormat(objects[3].toString());
        		}
        		if(objects[4]!=null&&!"".equals(objects[4])){
        			String str=objects[4].toString();
        			objects[4]=str.substring(0, 6)+str.substring(6, 10)+str.substring(10);
        		}
        		if(objects[8]!=null&&!"".equals(objects[8])){
        			sumData[8] = Double.parseDouble(sumData[8].toString()) + Double.parseDouble(objects[8].toString());
        			objects[8] = df.format(Double.parseDouble(objects[8].toString()));
        			objects[8] = CommonFunction.moneyFormat(objects[8].toString());
        		}
        		if(objects[9]!=null&&!"".equals(objects[9])){
        			sumData[9] = Double.parseDouble(sumData[9].toString()) + Double.parseDouble(objects[9].toString());
        			objects[9]=df.format(Double.parseDouble(objects[9].toString()));
        			objects[9]=CommonFunction.moneyFormat(objects[9].toString());
        		}
        		if(objects[10]!=null&&!"".equals(objects[10])){
        			sumData[10] = Double.parseDouble(sumData[10].toString()) + Double.parseDouble(objects[10].toString());
        			objects[10]=df.format(Double.parseDouble(objects[10].toString()));
        			objects[10]=CommonFunction.moneyFormat(objects[10].toString());
        		}
			}
        	sumData[8]=CommonFunction.moneyFormat(sumData[8].toString());
        	sumData[9]=CommonFunction.moneyFormat(sumData[9].toString());
        	sumData[10]=CommonFunction.moneyFormat(sumData[10].toString());
        	dataList.add(sumData);
        }
		
		String[] title={"商户号","商户名称","交易日期","交易时间","卡号","交易类型","终端号","终端流水号","交易金额","手续费","清算金额"};
        String head = "结算单明细报表" ;
        String fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + head +"_" 
        		+ operator.getOprId() + "_" + CommonFunction.getCurrentDateTime()+ ".xls";

        HashMap<String, Object> map=new HashMap<String, Object>();
        map.put("dataList", dataList);
        map.put("title", title);
        map.put("fileName", fileName);
        map.put("head", head);
        map.put("isCount", false);
        
        excelReport.reportDownloadTxnNew(map);
		return Constants.SUCCESS_CODE_CUSTOMIZE + fileName;
	}


	private String getSql() {
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		if (isNotEmpty(startDate)) {
			whereSql.append(" AND a.DATE_SETTLMT ").append(" >= ").append("'").append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND a.DATE_SETTLMT ").append(" <= ").append("'").append(endDate).append("'");
		}

		String sql = "SELECT a.mcht_cd, m.mcht_nm, a.trans_date, a.trans_date_time, a.pan,"
				+ "   (select c.TXN_NAME from TBL_TXN_NAME c where c.TXN_NUM = a.txn_num) as txn_name,"
				+ "   a.term_id, a.term_ssn,"
				+ "   abs(a.MCHT_AT_C - a.MCHT_AT_D) as txn_amt,"
				+ "   abs(a.MCHT_FEE_C - a.MCHT_FEE_D) as settle_fee,"
				+ "   (a.MCHT_AT_C - a.MCHT_AT_D + MCHT_FEE_C - MCHT_FEE_D) as settle_amt"
				+ " from TBL_ALGO_DTL a"
				+ "  left join tbl_mcht_base_inf m"
				+ " 	on a.mcht_cd = m.mcht_no";
		sql = sql + whereSql.toString();
		return sql;
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

}
