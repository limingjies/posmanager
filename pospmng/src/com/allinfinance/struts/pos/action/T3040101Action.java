package com.allinfinance.struts.pos.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Collection;
import java.util.Collections;

import net.sf.jasperreports.engine.export.ExporterFilter;

import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.struts.system.action.ReportBaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.InformationUtil;
import com.allinfinance.system.util.SysParamUtil;

/**
 * Title: 
 * 
 * File: T3040101Action.java
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
public class T3040101Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "TXT";
		
		title = InformationUtil.createTitles("V_", 37);
		
		data = reportCreator.process(genSql(), title);
		
		if(data.length == 0) {
			writeNoDataMsg("没有找符合条件的终端记录,无法生成文件");
			return;
		}
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN3040101RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN3040101RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		else if(Constants.REPORT_TXTMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN3040101RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".txt";
		
		File file = new File(fileName);
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		int len = data.length;
		
		for(int i = 0;i < len; i++){
			if(i == 0){
				writer.write("插入标志,商户代码,终端号,内卡收单机构代码,32域机构代码,终端类型,终端状态,是否开启外卡机构收单,是否支持IC卡,拨入类型," +
						"默认交易货币,当前密钥信息索引,终端加密密钥方式,终端传输密钥方式,终端加密类型,终端加密密钥1,终端传输密钥1,开通时间,注销时间," +
						"参数下载标志,TMS参数下载标志,公钥下载标志,IC参数下载标志,EMV参数下载标志,终端主密钥长度,PIK长度,MAK长度,MAC算法,国家代码," +
						"联接方式,磁道加密密钥长度,终端分润方式,终端正常拔入号码,地址,联系人,联系电话,安徽预留");
				writer.newLine();
				for(int j = 0;j < 37;j++ ){
					if(j!=36){
						if(data[i][j] != null){
							writer.write(data[i][j].toString()+",");
						}else{
							writer.write(""+",");
						}
					}else{
						if(data[i][j] != null){
							writer.write(data[i][j].toString());
						}else{
							writer.write("");
						}
					}
				}
				writer.newLine();
			}else {
				for(int j = 0;j < 37;j++ ){
					if(j!=36){
						if(data[i][j] != null){
							writer.write(data[i][j].toString()+",");
						}else{
							writer.write(""+",");
						}
					}else{
						if(data[i][j] != null){
							writer.write(data[i][j].toString());
						}else{
							writer.write("");
						}
					}
				}
				writer.newLine();
			}
		}
		writer.flush();
		writer.close();
				
		writeUsefullMsg(fileName);
		return;
	}
	
	@Override
	protected String genSql() {
		
		
		String whereSql = " WHERE crt_opr in (select opr_id from tbl_opr_info where brh_id in " + operator.getBrhBelowId() + ") ";
		if (!StringUtil.isNull(termId)) {
			whereSql += "AND term_id = '" + termId + "' ";
		}
		if (!StringUtil.isNull(mchnNo)) {
			whereSql += "AND mcht_no = '" + mchnNo + "' ";
		}
		if (!StringUtil.isNull(termTp)) {
			whereSql += "AND term_type = '" + termTp + "' ";
		}
		if (!StringUtil.isNull(termSta)) {
			whereSql += "AND term_state >= '" + termSta + "' ";
		}
		if (!StringUtil.isNull(startTime)) {
			whereSql += "AND substr(crt_date,0,8) <= '" + startTime + "' ";
		}
		if (!StringUtil.isNull(endTime)) {
			whereSql += "AND substr(crt_date,0,8) <= '" + endTime + "' ";
		}
		if (!StringUtil.isNull(startTimeU)) {
			whereSql += "AND substr(upd_date,0,8) >= '" + startTimeU + "' ";
		}
		if (!StringUtil.isNull(endTimeU)) {
			whereSql += "AND substr(upd_date,0,8) <= '" + endTimeU + "' ";
		}

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT (case when term_state != 0 AND crt_date = upd_date then 'I' when term_state != 0 AND crt_date < upd_date then 'U' WHEN  term_state = 0 THEN 'D' END) AS INSET_FLAG," +
				  "mcht_no,term_id,in_inst_id,inst_id,term_type,term_state,out_flag,ic_flag,call_type,currcy_code_trans,key_index,enc_key_mode,trans_key_mode,term_enc_type,term_enc_key_1," +
				  "term_trans_key_1,open_time,close_time,para_down_flag,tms_down_flag,pub_key_flag,ic_para_flag,emv_flag,key_length,pik_length,mak_length,mac_alg,country_code,connect_mode," +
				  "track_key_len,term_share,term_call_num,address,name,tel_phone,anhui_reserved " +
				  "FROM tbl_term_cup_info a ");
		
		sb.append(whereSql);
		sb.append(" ORDER BY mcht_no,term_id,term_type,term_state ");
		
		return sb.toString();
	}
	
	private String termId;
	private String mchnNo;
	private String termTp;
	private String termSta;
	private String startTime;
	private String endTime;
	private String startTimeU;
	private String endTimeU;

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getMchnNo() {
		return mchnNo;
	}

	public void setMchnNo(String mchnNo) {
		this.mchnNo = mchnNo;
	}

	public String getTermTp() {
		return termTp;
	}

	public void setTermTp(String termTp) {
		this.termTp = termTp;
	}

	public String getTermSta() {
		return termSta;
	}

	public void setTermSta(String termSta) {
		this.termSta = termSta;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartTimeU() {
		return startTimeU;
	}

	public void setStartTimeU(String startTimeU) {
		this.startTimeU = startTimeU;
	}

	public String getEndTimeU() {
		return endTimeU;
	}

	public void setEndTimeU(String endTimeU) {
		this.endTimeU = endTimeU;
	}

}