
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
public class T50109Action extends BaseAction {

	private String startDate;
	private String endDate;
	private String mchtNo;
	private String brhId;
	/* (non-Javadoc)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected String subExecute() throws Exception {
		String sql=getSql();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
	    DecimalFormat df=new DecimalFormat("0.00");
		Object[] sumData = new Object[dataList.get(0).length];
    	sumData[2] = "合计";
    	sumData[3] = 0;
    	sumData[4] = 0;
    	sumData[5] = 0;
        if(dataList.size()>0){
        	for (Object[] objects : dataList) {
        		
        		if(objects[0]!=null&&!"".equals(objects[0])){
        			objects[0]=CommonFunction.dateFormat(objects[0].toString());
        		}
        		if(objects[3]!=null&&!"".equals(objects[3])){
        			sumData[3] = Double.parseDouble(sumData[3].toString()) + Double.parseDouble(objects[3].toString());
        			objects[3]=df.format(Double.parseDouble(objects[3].toString()));
        			objects[3]=CommonFunction.moneyFormat(objects[3].toString());
        		}
        		if(objects[4]!=null&&!"".equals(objects[4])){
        			sumData[4] = Double.parseDouble(sumData[4].toString()) + Double.parseDouble(objects[4].toString());
        			objects[4]=df.format(Double.parseDouble(objects[4].toString()));
        			objects[4]=CommonFunction.moneyFormat(objects[4].toString());
        		}
        		if(objects[5]!=null&&!"".equals(objects[5])){
        			sumData[5] = Double.parseDouble(sumData[5].toString()) + Double.parseDouble(objects[5].toString());
        			objects[5]=df.format(Double.parseDouble(objects[5].toString()));
        			objects[5]=CommonFunction.moneyFormat(objects[5].toString());
        		}
        		
        		if(objects[6]!=null&&!"".equals(objects[6])){
        			objects[6]=CommonFunction.dateFormat(objects[6].toString());
        		}
        		if(objects[7]!=null&&!"".equals(objects[7])){
        			objects[7]=CommonFunction.dateFormat(objects[7].toString());
        		}
			}
        	sumData[3]=CommonFunction.moneyFormat(sumData[3].toString());
        	sumData[4]=CommonFunction.moneyFormat(sumData[4].toString());
        	sumData[5]=CommonFunction.moneyFormat(sumData[5].toString());
        	dataList.add(sumData);
        }
        String[] title={"清算日期","批次号","清算商户","交易金额","清算金额","手续费","交易起始日期","交易结束日期","交易通道"};
        String head="结算单报表";
        String fileName=SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + head+"_" + 
					operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";

        
        HashMap<String, Object> map=new HashMap<String, Object>();
        HashMap<String, String> mapParm=new HashMap<String, String>();
        mapParm.put("1", "起始日期_"+CommonFunction.dateFormat(startDate));
        mapParm.put("2", "终止日期_"+CommonFunction.dateFormat(endDate));
        mapParm.put("count", "2");
        
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
	 * @return
	 * 2014-2-8 上午09:38:19
	 * @author cuihailong
	 */
	private String getSql() {
		// TODO Auto-generated method stub
		StringBuffer whereSql = new StringBuffer(" where   "
				+ "MCHT_NO in (select  g.mcht_no from tbl_mcht_base_inf g where g.bank_no in "+operator.getBrhBelowId()+" ) ");
			if(isNotEmpty(startDate)) {
	        	whereSql.append(" AND DATE_SETTLMT ").append(" >= ").append("'").append(startDate).append("'");
	        }
	        if(isNotEmpty(endDate)) {
	        	whereSql.append(" AND DATE_SETTLMT").append(" <= ").append("'").append(endDate).append("'");
	        }
	        if(isNotEmpty(mchtNo)) {
	        	whereSql.append(" AND MCHT_NO  in (SELECT MCHT_NO FROM TBL_MCHT_BASE_INF  start with MCHT_NO =").append("'").append(mchtNo).append("' connect by prior  trim(MCHT_NO) = MCHT_GROUP_ID ) ");
	        }
	        if(isNotEmpty(brhId)) {
	        	whereSql.append(" and trim(BRH_CODE)  in (SELECT brh_id FROM TBL_BRH_INFO  start with brh_id ='" + brhId.trim() + "' connect by prior  BRH_ID = UP_BRH_ID   ) ");
	        }
	        
	        String sql = "SELECT DATE_SETTLMT,1,MCHT_NO||'-'||MCHT_NM,TXN_AMT,SETTLE_AMT,SETTLE_FEE1,START_DATE,END_DATE,"
	        		+ "a.CHANNEL_CD||(select '-'||d.FIRST_BRH_NAME from  TBL_FIRST_BRH_DEST_ID d where a.CHANNEL_CD=substr(d.FIRST_BRH_ID,3,2)) as channel_name "
	        		+ " FROM TBL_MCHNT_INFILE_DTL a ";
	        sql = sql + whereSql.toString();
	        sql=sql+" ORDER BY DATE_SETTLMT DESC";
	        
	       
		return sql;
	}

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
	 * @return the mchtNo
	 */
	public String getMchtNo() {
		return mchtNo;
	}

	/**
	 * @param mchtNo the mchtNo to set
	 */
	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	
}
