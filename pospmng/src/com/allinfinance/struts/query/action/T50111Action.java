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

@SuppressWarnings("serial")
public class T50111Action extends BaseAction {

	private String mchtNo;
	private String dateSettlmt;
	private String instCode;

	/*
	 * (non-Javadoc)
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		String sql = getSql();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql);
		DecimalFormat df=new DecimalFormat("0.00");
        if(dataList.size()>0){
        	for (Object[] objects : dataList) {
        		
        		if(objects[0]!=null&&!"".equals(objects[0])){
        			objects[0]=CommonFunction.dateFormat(objects[0].toString());
        		}
        		if(objects[1]!=null&&!"".equals(objects[1])){
        			objects[1]=CommonFunction.dateFormat(objects[1].toString());
        		}
        		if(objects[2]!=null&&!"".equals(objects[2])){
        			String str=objects[2].toString();
        			objects[2]=str.substring(0, 6)+str.substring(6, 10)+str.substring(10);
        		}
        		if(objects[6]!=null&&!"".equals(objects[6])){
        			objects[6]=df.format(Double.parseDouble(objects[6].toString()));
        			objects[6]=CommonFunction.moneyFormat(objects[6].toString());
        		}
        		if(objects[7]!=null&&!"".equals(objects[7])){
        			objects[7]=df.format(Double.parseDouble(objects[7].toString()));
        			objects[7]=CommonFunction.moneyFormat(objects[7].toString());
        		}
        		if(objects[8]!=null&&!"".equals(objects[8])){
        			objects[8]=df.format(Double.parseDouble(objects[8].toString()));
        			objects[8]=CommonFunction.moneyFormat(objects[8].toString());
        		}
			}
        }
		
		String[] title={"交易日期","交易时间","卡号","交易类型","终端号","终端流水号","交易金额","手续费","清算金额"};
        String head=CommonFunction.dateFormat(dateSettlmt)+"_新结算单明细报表";
        String fileName=SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + head+"_" + 
					operator.getOprId() + ".xls";

        
        HashMap<String, Object> map=new HashMap<String, Object>();
        
        map.put("dataList", dataList);
        map.put("title", title);
        map.put("fileName", fileName);
        map.put("head", head);
        map.put("isCount", false);
//        map.put("mapParm", mapParm);
        
        excelReport.reportDownloadTxnNew(map);
//		excelReport.reportDownloadTxn(dataList, title, os, head, false,null);
		return Constants.SUCCESS_CODE_CUSTOMIZE+fileName;
	}

	/**
	 * @return 2014-2-12 下午05:06:54
	 * @author cuihailong
	 */
	private String getSql() {
		// TODO Auto-generated method stub
		StringBuffer whereSql = new StringBuffer(" where   "
				+ "a.MCHT_ID in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "+operator.getBrhBelowId()+" ) ");

		if (isNotEmpty(dateSettlmt)) {
			whereSql.append(" AND a.DATE_STLM ").append(" = ").append("'")
					.append(dateSettlmt).append("'");
		}
		if (isNotEmpty(mchtNo)) {
			whereSql.append(" AND a.MCHT_ID ").append(" = ").append("'")
					.append(mchtNo).append("'");
		}
		if (isNotEmpty(instCode)) {
			whereSql.append(" AND a.INST_CODE ").append(" = ").append("'")
					.append(instCode).append("'");
		}
		String sql = "SELECT a.DATE_STLM,a.TXN_TIME,a.pan,"
				+ " (select c.TXN_NAME from TBL_TXN_NAME c where c.TXN_NUM =a.txn_num ) as txn_name,"
				+ "a.TERM_ID,a.TERM_SN,"
				+ "abs(a.MCHT_AMT_CR-a.MCHT_AMT_DB) as txn_amt,"
				+ "abs(a.MCHT_FEE_CR-a.MCHT_FEE_DB) as settle_fee, "
				+ "(a.MCHT_AMT_CR-a.MCHT_AMT_DB+a.MCHT_FEE_CR-a.MCHT_FEE_DB) as settle_amt "
				+ " from TBL_CLEAR_DTL a ";
		sql = sql + whereSql.toString();
		return sql;
	}

	/**
	 * @return the dateSettlmt
	 */
	public String getDateSettlmt() {
		return dateSettlmt;
	}

	/**
	 * @param dateSettlmt
	 *            the dateSettlmt to set
	 */
	public void setDateSettlmt(String dateSettlmt) {
		this.dateSettlmt = dateSettlmt;
	}

	/**
	 * @return the mchtNo
	 */
	public String getMchtNo() {
		return mchtNo;
	}

	/**
	 * @param mchtNo
	 *            the mchtNo to set
	 */
	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getInstCode() {
		return instCode;
	}

	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}

}
