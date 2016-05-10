package com.allinfinance.struts.settle.action;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.SysParamUtil;


/**
 * 
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-12-01
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * author: 徐鹏飞
 *  
 * time: 2015年4月7日下午2:12:00
 * 
 * version: 1.0
 */
public class T80903Action extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3228933606785021486L;
	
	private String startDate;
	private String endDate;
	private String fileType;
	//文件名称
	private String fileName;

	@Override
	protected String subExecute() throws Exception {
		@SuppressWarnings("unchecked")
		// 方案1开始：先取出关键数据，再通过程序拼合内容
//		List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(genSql());
		// 方案1结束

		// 方案2开始：直接通过SQL语句拼合内容
		List<String> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(genSql());
		// 方案2结束
		if(dataList.size() == 0){
			return "未查到数据，请重新选择！";
		}
		
		//临时存储
		StringBuffer tmpData;
		//文件"中信收款单位"的内容
		StringBuffer retData = new StringBuffer();
		String filePath = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + fileName +".txt";
		//头文件内容
		retData.append("文件类型:FTReceiverMaintenance\r\n");
		retData.append("标题:收款单位维护\r\n");
		retData.append("\r\n");
		retData.append("导入模板的字段内容为：\r\n");
		retData.append("\r\n");
		retData.append("说明:1.前后加'*'号的字段为必填项\r\n");
		retData.append("2.各字段前后不允许有空格\r\n");
		retData.append("-----------------------------------\r\n");
		
		// 方案1开始：先取出关键数据，再通过程序拼合内容
//		Iterator<Object[]> iter = dataList.iterator();
		// 方案1结束

		// 方案2开始：直接通过SQL语句拼合内容
		Iterator<String> iter = dataList.iterator();
		// 方案2结束
		// 迭代SQL查询内容
		while(iter.hasNext()) {
			tmpData = new StringBuffer();
/*			
			// 方案1开始：先取出关键数据，再通过程序拼合内容
			Object[] objects = iter.next();
			tmpData.append("其他银行账户|");
			tmpData.append(objects[0].toString().trim()).append("|");
			tmpData.append(objects[1].toString().trim().substring(1)).append("||人民币|");
			if("0".equals(objects[1].toString().trim().substring(0,1))){
				tmpData.append("对公账户").append("|");
			}else{
				tmpData.append("个人账户").append("|");
			}
			if(objects[2] == null||"".equals(objects[2])){
				tmpData.append("|");
			}else{
				tmpData.append(objects[2].toString().trim()).append("|");
			}
			if(iter.hasNext()) {
				tmpData.append(objects[3].toString().trim()).append("||\r\n");
			}else{
				tmpData.append(objects[3].toString().trim()).append("||");
			}
			// 方案1结束
*/
			
			// 方案2开始：直接通过SQL语句拼合内容
			tmpData.append(iter.next().trim());
			if(iter.hasNext()) {
				tmpData.append("\r\n");
			}
			// 方案2结束
			
			retData.append(tmpData.toString());
		}

		//生成"中信收款单位文件内容"
		FileOutputStream Payee_fos = null;
		DataOutputStream Payee_dos = null;
		File PayeeFile = new File(filePath);

		try{
			//建立文件的读入流
			if (PayeeFile.exists()){
				PayeeFile.delete();
			}
			Payee_fos = new FileOutputStream(PayeeFile);
			Payee_dos = new DataOutputStream(Payee_fos);
			Payee_dos.write(retData.toString().getBytes("UTF-8"));
			//关闭流
			Payee_dos.close();
			Payee_fos.close();           
		}catch(Exception ex){              
			ex.printStackTrace();
		}
		
		return Constants.SUCCESS_CODE_CUSTOMIZE+filePath;
	}
	

	private String genSql() {
		StringBuffer whereSql = new StringBuffer();
		if("A".equals(fileType)){
			whereSql.append("SELECT L_MCHT_NO FROM TBL_MCHT_BASE_INF_TMP_LOG WHERE BE_MCHT_STATUS IN ('1','B')");
			fileName = startDate.replace("-", "")+"-"+endDate.replace("-", "")+"_新增收款单位";
		}else if("U".equals(fileType)){
			whereSql.append("SELECT DISTINCT L_MCHT_NO FROM TBL_MCHT_BASE_INF_TMP_LOG WHERE BE_MCHT_STATUS = '3' AND (AF_SETTLEACCT IS NOT NULL OR AF_SETTLEACCTNM IS NOT NULL OR AF_SETTLEBANKNM IS NOT NULL OR AF_OPENSTLNO IS NOT NULL OR AF_CLEARTYPENM IS NOT NULL)");
			fileName = startDate.replace("-", "")+"-"+endDate.replace("-", "")+"_修改收款单位";
		}
		if (StringUtil.isNotEmpty(startDate)) {
			whereSql.append(" AND SUBSTR(L_CREATEDATE,1,8) >= '").append(startDate.replace("-", "")).append("'");
		}	
		if (StringUtil.isNotEmpty(endDate)) {
			whereSql.append(" AND SUBSTR(L_CREATEDATE,1,8) <= '").append(endDate.replace("-", "")).append("'");
		}
		// 方案1开始：先取出关键数据，再通过程序拼合内容
//		String sql = "SELECT DISTINCT SETTLE_ACCT_NM,SETTLE_ACCT,NVL(TRIM(OPEN_STLNO),''),SETTLE_BANK_NM FROM TBL_MCHT_SETTLE_INF WHERE MCHT_NO IN (" + whereSql.toString() + ")";
		// 方案1结束
		
		// 方案2开始：直接通过SQL语句拼合内容
		String sql = "SELECT DISTINCT '其他银行账户|'||SETTLE_ACCT_NM||'|'||SUBSTR(SETTLE_ACCT,2)||'||人民币|'||(CASE WHEN SUBSTR(SETTLE_ACCT,1,1) = '0' THEN '对公账户' ELSE '个人账户' END)||'|'||NVL(TRIM(OPEN_STLNO),'')||'|'||SETTLE_BANK_NM||'||' FROM TBL_MCHT_SETTLE_INF WHERE MCHT_NO IN (" + whereSql.toString() + ")";
		// 方案2结束
		
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
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
}