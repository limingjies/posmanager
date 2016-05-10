package com.allinfinance.struts.risk.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.allinfinance.common.Constants;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.select.SelectMethod;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.SysParamUtil;
import com.allinfinance.system.util.excelReport;

@SuppressWarnings("serial")
public class T40204Action extends BaseAction {

	private String date1;
	private String date2;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		List<String> riskIdList = CommonFunction.getCommQueryDAO().findBySQLQuery(getRiskIdList());
		LinkedHashMap<String, String> mchtMap=getMchntNo();
		
		Map<String,String[]> titleListMap=new HashMap<String,String[]>();
		Map<String,List<Object[]>> dataListMap=new HashMap<String,List<Object[]>>();
		Map<String,String> titleNameMap=SelectMethod.getKind(null);
		titleNameMap.put("B01", "B01-移动终端疑似移机");
		
		List<Object[]> dataListOld=null;
		List<Object[]> dataListNew=null;
		Object[] obj=null;
		
		for (int i = 0; i < riskIdList.size(); i++) {//n个sheet
			titleListMap.put(riskIdList.get(i),SysParamUtil.getParam( com.allinfinance.common.RiskConstants.RISK_TOTAL_TITEL+"_"+riskIdList.get(i)).split("\\|"));
			dataListOld=CommonFunction.getCommQueryDAO().findBySQLQuery(getSql(riskIdList.get(i)));
			dataListNew=new ArrayList<Object[]>();
			for (int j = 0; j < dataListOld.size(); j++) {//每张数据集
				obj=new Object[titleListMap.get(riskIdList.get(i)).length];
				if(dataListOld!=null&&dataListOld.size()>0){
					
					for (int k = 0; k < obj.length; k++) {//每条数据
						
						if(!"B01".equals(riskIdList.get(i).trim())){
							if(k<1){
								obj[k]=CommonFunction.dateFormat(dataListOld.get(j)[0].toString());
							}else if(k==1){
								obj[k]=dataListOld.get(j)[2].toString();
							}else if(k==2&&!
									("A03".equals(riskIdList.get(i).trim())||
											"A11".equals(riskIdList.get(i).trim())||
											"A12".equals(riskIdList.get(i).trim())||
											"A16".equals(riskIdList.get(i).trim()))){
								if(mchtMap.get(dataListOld.get(j)[1].toString().trim().split("\\|")[k-2].toString().trim())!=null)
									obj[k]=mchtMap.get(dataListOld.get(j)[1].toString().trim().split("\\|")[k-2].toString().trim());
								else
									obj[k]=dataListOld.get(j)[1].toString().trim().split("\\|")[k-2].toString().trim();
							}else if((k==4||k==5)&&"A17".equals(riskIdList.get(i).trim())){
								obj[k]=CommonFunction.dateFormat(dataListOld.get(j)[1].toString().trim().split("\\|")[k-2].toString().trim());
							}else{
								obj[k]=dataListOld.get(j)[1].toString().trim().split("\\|")[k-2].toString().trim();
							}
						}else{
							if(k<1){
								obj[k]=CommonFunction.dateFormat(dataListOld.get(j)[0].toString());
							
							}else if(k==1){
								if(mchtMap.get(dataListOld.get(j)[1].toString().trim().split("\\|")[k-1].toString().trim())!=null)
									obj[k]=mchtMap.get(dataListOld.get(j)[1].toString().trim().split("\\|")[k-1].toString().trim());
								else
									obj[k]=dataListOld.get(j)[1].toString().trim().split("\\|")[k-1].toString().trim();
							}else if(k==4){
									StringBuffer countStr = new StringBuffer(" ");
//									for (int k2 = 0; k2 < Integer.valueOf(obj[3].toString().trim()); k2++) {
									for (int k2 = 0; k2 < dataListOld.get(j)[1].toString().trim().split("\\|").length-3; k2++) {
										String str=dataListOld.get(j)[1].toString().trim().split("\\|")[k-1+k2].toString();
										
										countStr.append("移机信息").append(k2+1).append(":").append(getMoveTerm(str)).append(" ");
									}
									obj[k]=countStr;
							}else{
								obj[k]=dataListOld.get(j)[1].toString().trim().split("\\|")[k-1].toString().trim();
							}
						}
						
					}
				}
				
				
				dataListNew.add(obj);
			}
			dataListMap.put(riskIdList.get(i), dataListNew);
		}
		
		
		
//        String head=CommonFunction.dateFormat(date1);//+"_风险统计报表";
//		String head=;
        String fileName=SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40204RN_" + 
					operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";

       
        
        HashMap<String, Object> map=new HashMap<String, Object>();
        
        map.put("fileName", fileName);
//        map.put("head", head);
        map.put("list", riskIdList);
        excelReport.reportDownloadTxn(map,titleNameMap,titleListMap,dataListMap);
//		excelReport.reportDownloadTxn(dataList, title, os, head, false,null);
		return Constants.SUCCESS_CODE_CUSTOMIZE+fileName;
	}

	private String getRiskIdList() {
		// TODO Auto-generated method stub
		String sql = "SELECT a.sa_model_kind from TBL_RISK_INF a order by a.sa_model_kind ";
			
		return sql;
	}
	
	private String getMoveTerm(String termInfo) {
		// TODO Auto-generated method stub
		StringBuffer resStr = new StringBuffer("[");
		resStr.append(SelectMethod.getMoveTermMcc(null).get(termInfo.substring(0,3))).append(",");
		resStr.append(SelectMethod.getMoveTermMnc(null).get(termInfo.substring(3,6).trim())).append(",");
		resStr.append("(").append(termInfo.substring(6,16).trim()).append(",").append(termInfo.substring(16,26).trim()).append("),");
		resStr.append("交易数量").append(termInfo.substring(26).trim());
		resStr.append("]");
		return resStr.toString();
	}
	
	private String getSql(String kind) {
		// TODO Auto-generated method stub
		

		

		String sql = "SELECT a.RISK_DATE,a.RISK_INFO,"
				+ "(select a.risk_lvl||'-'||c.RESVED from TBL_RISK_LVL c where c.RISK_ID =a.RISK_ID and c.RISK_LVL=a.risk_lvl) as riskLevlName "
				+ " from TBL_RISK_TOTAL a " +
						" where a.RISK_ID='"+kind+"' and a.RISK_DATE>='"+date1+"' and a.RISK_DATE<='"+date2+"'" +
								"and a.risk_seq!=1 " +
								" order by a.RISK_ID,a.RISK_DATE desc,a.RISK_SEQ  ";
		return sql;
	}

	

	/**
	 * @return the date1
	 */
	public String getDate1() {
		return date1.replace("-", "");
	}

	/**
	 * @param date1 the date1 to set
	 */
	public void setDate1(String date1) {
		this.date1 = date1.replace("-", "");
	}

	/**
	 * @return the date2
	 */
	public String getDate2() {
		return date2.replace("-", "");
	}

	/**
	 * @param date2 the date2 to set
	 */
	public void setDate2(String date2) {
		this.date2 = date2.replace("-", "");
	}

	

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getMchntNo() {
		LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();
		String sql = "select MCHT_NO, MCHT_NO ||' - '|| MCHT_NM as MCHT_NM from TBL_MCHT_BASE_INF  ";
		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		Iterator<Object[]> iterator = dataList.iterator();
		while(iterator.hasNext()) {
			Object[] obj = iterator.next();
			dataMap.put(obj[0].toString().trim(), obj[1].toString().trim());
		}
		return dataMap;
	}

}
