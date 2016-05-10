package com.allinfinance.struts.base.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.SysParamUtil;
import com.allinfinance.system.util.excelReport;

public class T10404Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		
		String sheetNm = "操作记录统计数据";
		
		@SuppressWarnings("unchecked")
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(genSql());
		if(dataList.size() == 0){
			writeNoDataMsg("没有找符合条件的记录，无法统计数据");
			return;
		}
		
		Map<String, List<Object[]>> dataListMap = new HashMap<String, List<Object[]>>();
		
		List<String> sheetList = new ArrayList<String>();
		
		sheetList.add(sheetNm);
		
		List<Object[]> dataListNew  = new ArrayList<Object[]>();
		
		Object[] objectNew ;
		
		for (Object[] objects : dataList) {
			objectNew = new Object[objects.length + 1];
			for (int i = 0; i < objects.length; i++) {
				objectNew[i] = objects[i];
			}
			objectNew[4] = String.valueOf(Integer.parseInt(objects[2].toString()) + Integer.parseInt(objects[3].toString()));
			dataListNew.add(objectNew);
		}
		dataListMap.put(sheetNm, dataListNew);

		StringBuffer titleName = new StringBuffer();
		if (StringUtil.isNotEmpty(startDate)) {
			titleName.append("从").append(startDate).append("开始");
		}	
		if (StringUtil.isNotEmpty(endDate)) {
			titleName.append("到").append(endDate).append("为止");
		}
		if (StringUtil.isNotEmpty(ipAddr)) {
			titleName.append("在IP为：").append(ipAddr).append("的主机上");
		}
		titleName.append(" 操作记录统计数据");
		
		String[] title={"操作员号","操作名称","操作成功次数","操作失败次数","操作总次数"};
		String[] title1={"操作名称","操作员号","操作成功次数","操作失败次数","操作总次数"};
		
		String fileName=SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN10404RN_" + 
				operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		
        Map<String,String> titleNameMap=new HashMap<String,String>();
		
		Map<String,String[]> titleListMap=new HashMap<String,String[]>();
		
		titleNameMap.put(sheetNm, titleName.toString());
		if ("1".equals(secGroupTyp)){
			titleListMap.put(sheetNm,title1);
		} else {
			titleListMap.put(sheetNm,title);
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
		StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");
		String sql = null;
		if (StringUtil.isNotEmpty(startDate)) {
			whereSql.append(" and TXN_DATE >='").append(startDate.replace("-", "")).append("'");
		}	
		if (StringUtil.isNotEmpty(endDate)) {
			whereSql.append(" and TXN_DATE <='").append(endDate.replace("-", "")).append("'");
		}
		if (StringUtil.isNotEmpty(ipAddr)) {
			whereSql.append(" and IP_ADDR ='").append(ipAddr).append("'");
		}	
		if (StringUtil.isNotEmpty(brhId)) {
			whereSql.append(" and ORG_CODE ='").append(brhId).append("'");
		}	
		if (StringUtil.isNotEmpty(oprNo)) {
			whereSql.append(" and OPR_ID ='").append(oprNo).append("'");
		}	
		if (StringUtil.isNotEmpty(conTxn)) {
			whereSql.append(" and TXN_CODE ='").append(conTxn).append("'");
		}	
		if ("1".equals(secGroupTyp)){
			sql = "SELECT NVL(TXN_NAME, '*') as TXN_NAME,OPR_ID,"
					+ "count(case when txn_sta='0' then '0' else null end) as succ_num,"
					+ "count(case when txn_sta='1' then '1' else null end) as fail_num "
					+ "FROM TBL_TXN_INFO "
					+ whereSql.toString() +
					" GROUP BY TXN_NAME,OPR_ID ORDER BY TXN_NAME,OPR_ID";
		} else {
			sql = "SELECT OPR_ID,NVL(TXN_NAME, '*') as TXN_NAME,"
					+ "count(case when txn_sta='0' then '0' else null end) as succ_num,"
					+ "count(case when txn_sta='1' then '1' else null end) as fail_num "
					+ "FROM TBL_TXN_INFO "
					+ whereSql.toString() +
					" GROUP BY OPR_ID,TXN_NAME ORDER BY OPR_ID,TXN_NAME";
		}
		return sql.toString();
	}
	
	private String startDate;
	private String endDate;
	private String ipAddr;
	private String brhId;
	private String oprNo;
	private String conTxn;
	private String secGroupTyp;

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
	 * @return the ipAddr
	 */
	public String getIpAddr() {
		return ipAddr;
	}

	/**
	 * @param ipAddr the ipAddr to set
	 */
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	/**
	 * @return the brhId
	 */
	public String getBrhId() {
		return brhId;
	}

	/**
	 * @param brhId the brhId to set
	 */
	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}

	/**
	 * @return the oprNo
	 */
	public String getOprNo() {
		return oprNo;
	}

	/**
	 * @param oprNo the oprNo to set
	 */
	public void setOprNo(String oprNo) {
		this.oprNo = oprNo;
	}

	/**
	 * @return the conTxn
	 */
	public String getConTxn() {
		return conTxn;
	}

	/**
	 * @param conTxn the conTxn to set
	 */
	public void setConTxn(String conTxn) {
		this.conTxn = conTxn;
	}

	/**
	 * @return the secGroupTyp
	 */
	public String getSecGroupTyp() {
		return secGroupTyp;
	}

	/**
	 * @param secGroupTyp the secGroupTyp to set
	 */
	public void setSecGroupTyp(String secGroupTyp) {
		this.secGroupTyp = secGroupTyp;
	}
	
}
