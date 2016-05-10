package com.allinfinance.struts.risk.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.SysParamUtil;
import com.allinfinance.system.util.excelReport;

@SuppressWarnings("serial")
public class T40204_1Action extends BaseAction {

	private String kind;
	private String date1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		String sql = getSql();
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql);
		
		if(dataList==null||dataList.size()<=1){
			return "没有找到符合条件的记录";
		}
		String riskInfo=dataList.get(0)[3].toString().trim();
		String[] riskInfos=riskInfo.split("\\|");
		
		
		List<Object[]> data=new ArrayList<Object[]>();
		Object[] obj=null;
		for (int i = 1; i < dataList.size(); i++) {
			obj=new Object[riskInfos.length+3];
			for (int j = 0; j < obj.length; j++) {
				if(j<3){
					obj[j]=dataList.get(i)[j];
				}else{
					obj[j]=dataList.get(i)[3].toString().trim().split("\\|")[j-3].toString().trim();
				}
			}
			data.add(obj);
		}
		
		for (int i = 0; i < data.size(); i++) {
			if(data.get(i)[0]!=null){
				data.get(i)[0]=CommonFunction.dateFormat(data.get(i)[0].toString());
			}
		}
		String[] title=new String[riskInfos.length+3];
		title[0]="统计日期";
		title[1]="风控编号";
		title[2]="风控名称";
		for (int i = 3; i < title.length; i++) {
			title[i]=riskInfos[i-3];
		}
		
        String head="风险统计报表";
        String fileName=SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN40204RN_" + 
					operator.getOprId() + ".xls";

       
        
        HashMap<String, Object> map=new HashMap<String, Object>();
        
        map.put("dataList", data);
        map.put("title", title);
        map.put("fileName", fileName);
        map.put("head", head);
        map.put("isCount", false);
//        map.put("mapParm", mapParm);
        
//        excelReport.reportDownloadTxn(map);
//		excelReport.reportDownloadTxn(dataList, title, os, head, false,null);
		return Constants.SUCCESS_CODE_CUSTOMIZE+fileName;
	}

	/**
	 * @return 2014-2-12 下午05:06:54
	 * @author cuihailong
	 */
	private String getSql() {
		// TODO Auto-generated method stub
		

		

		String sql = "SELECT a.RISK_DATE,a.RISK_ID,b.SA_MODEL_DESC,a.RISK_INFO "
				+ " from TBL_RISK_TOTAL a left join TBL_RISK_INF b on a.RISK_ID=b.SA_MODEL_KIND" +
						" where a.RISK_ID='"+kind+"' and a.RISK_DATE='"+date1+"'  " +
								" order by a.RISK_ID,a.RISK_SEQ,a.RISK_DATE desc ";
		return sql;
	}

	/**
	 * @return the kind
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * @param kind the kind to set
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * @return the date1
	 */
	public String getDate1() {
		return date1;
	}

	/**
	 * @param date1 the date1 to set
	 */
	public void setDate1(String date1) {
		this.date1 = date1;
	}

	

	

}
