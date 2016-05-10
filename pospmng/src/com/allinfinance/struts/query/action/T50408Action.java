
package com.allinfinance.struts.query.action;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.InformationUtil;
import com.allinfinance.system.util.SysParamUtil;
import com.allinfinance.system.util.excelReport;

/**
 * Title: 
 * 
 * File: T4020501Action.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-8-10
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class T50408Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {

			String[] title={"MCC码","借记交易本金","贷记交易本金","借记手续费","贷记手续费"};
			String[] sheets={"ST0","ST1"};
	        String fileName=SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN8040102RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
	        List<String> sheetList = java.util.Arrays.asList(sheets);
	        
	        Map<String,String> titleNameMap=new HashMap<String,String>();
			titleNameMap.put("ST0", "中信一级MCC交易金额报表-退货");
			titleNameMap.put("ST1", "中信一级MCC交易金额报表-非退货");
			
			Map<String,String[]> titleListMap=new HashMap<String,String[]>();
			titleListMap.put("ST0",title);
			titleListMap.put("ST1",title);
			
			Map<String,List<Object[]>> dataListMap=new HashMap<String,List<Object[]>>();
			
			for (int i = 0; i < sheetList.size(); i++) {
			
				@SuppressWarnings("unchecked")				
				List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(genSqls()[i]);
			
				for (Object[] objects : dataList) {
					if(objects[1]!=null&&!"".equals(objects[1])){
		    			String str1=objects[1].toString();
		    			objects[1]=CommonFunction.moneyFormat(str1);
		    		}if(objects[2]!=null&&!"".equals(objects[2])){
		    			String str2=objects[2].toString();
		    			objects[2]=CommonFunction.moneyFormat(str2);
		    		}if(objects[3]!=null&&!"".equals(objects[3])){
		    			String str3=objects[3].toString();
		    			objects[3]=CommonFunction.moneyFormat(str3);
		    		}if(objects[4]!=null&&!"".equals(objects[4])){
		    			String str4=objects[4].toString();
		    			objects[4]=CommonFunction.moneyFormat(str4);
		    		}
				}
				dataListMap.put("ST"+String.valueOf(i), dataList);
			}
			
			HashMap<String, Object> map=new HashMap<String, Object>();
	        map.put("fileName", fileName);
	        map.put("list", sheetList);
	        excelReport.reportDownloadTxn(map,titleNameMap,titleListMap,dataListMap);
	        writeUsefullMsg(fileName);
			return ;


	}
	
	@Override
	protected String genSql() {
		
		
		StringBuffer whereSql = new StringBuffer(" where x.MCHT_ID=y.mcht_no and y.bank_no=z.brh_id and m.MCHT_ID=x.MCHT_ID and m.fee_rate=n.disc_cd");
		if (StringUtil.isNotEmpty(startDate)) {
			whereSql.append(" and x.DATE_STLM >='").append(startDate.replace("-", "")).append("'");
		}	
		if (StringUtil.isNotEmpty(endDate)) {
			whereSql.append(" and x.DATE_STLM <='").append(endDate.replace("-", "")).append("'");
		}
		
	
		String sql = "select x.DATE_STLM, x.MCHT_ID amchtno, x.STLM_AMT, y.mcht_nm, y.mcht_cn_abbr, z.brh_name, n.disc_nm " + 
					"from TBL_MCHT_SETTLE_DTL x, tbl_mcht_base_inf y, tbl_brh_info z, tbl_mcht_settle_inf m, tbl_inf_disc_cd n "
					+ whereSql.toString() + " order by x.DATE_STLM desc ";
	
		return sql.toString();
	}
	
	private String[] genSqls() {
		
		StringBuffer whereSql = new StringBuffer();
		if (StringUtil.isNotEmpty(startDate)) {
			whereSql.append(" and x.TXN_DATE >='").append(startDate.replace("-", "")).append("'");
		}	
		if (StringUtil.isNotEmpty(endDate)) {
			whereSql.append(" and x.TXN_DATE <='").append(endDate.replace("-", "")).append("'");
		}
		
		
		String sql0 = "select (select MCC from tbl_mcht_base_inf where  MCHT_NO=x.MCHT_ID)MCC, sum(x.MCHT_AMT_DB), sum(x.MCHT_AMT_CR), sum(x.MCHT_FEE_DB), sum(x.MCHT_FEE_CR) " + 
					"from TBL_CLEAR_DTL x where txn_num = '5151'" + whereSql.toString() +
				" group by x.MCHT_ID order by MCC ";
		
		String sql1 = "select (select MCC from tbl_mcht_base_inf where  MCHT_NO=x.MCHT_ID)MCC, sum(x.MCHT_AMT_DB), sum(x.MCHT_AMT_CR), sum(x.MCHT_FEE_DB), sum(x.MCHT_FEE_CR) " + 
					"from TBL_CLEAR_DTL x where txn_num <> '5151' " + whereSql.toString() +
			"  group by x.MCHT_ID order by MCC ";
		
		String[] str = {sql0,sql1};
		return str;
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
