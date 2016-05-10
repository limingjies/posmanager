package com.allinfinance.struts.mchnt.action;

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
 * File: T2040202Action.java
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
public class T2040202Action extends ReportBaseAction{

	private static final long serialVersionUID = 1L;

	@Override
	protected void reportAction() throws Exception {
		
		reportType = "TXT";
		
		title = InformationUtil.createTitles("V_", 87);
		
		data = reportCreator.process(genSql(), title);
		
		if(data.length == 0) {
			writeNoDataMsg("没有找符合条件的商户记录,无法生成文件");
			return;
		}
		
		if(Constants.REPORT_EXCELMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN2040202RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".xls";
		else if(Constants.REPORT_PDFMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN2040202RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".pdf";
		else if(Constants.REPORT_TXTMODE.equals(reportType))
			fileName = SysParamUtil.getParam(SysParamConstants.TEMP_FILE_DISK) + "RN2040202RN_" + 
						operator.getOprId() + "_" + CommonFunction.getCurrentDateTime() + ".txt";
		
		File file = new File(fileName);
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		int lenD = data.length;
		int lenT = title.length;
		
		for(int i = 0;i < lenD; i++){
			if(i == 0){//第一行插入字段描述
				writer.write("插入标志,商户编号,内卡收单机构代码,32域机构代码,所属平台机构代码,商户中文名称,商户中文简称,行政区划代码,内卡结算机构代码," +
						"交易商户类型,商户状态,受理地区代码,直联收单清算标志,收单接入机构代码,商户组别,商户服务类型,真实商户类型,清算地区代码,商户受理币种," +
						"联接方式,商户默认交易币种,是否向商户收取押金,商户是否保存卡号,商户是否保存磁道信息,是否向商户收取服务费,国家代码,连锁店标识," +
						"套用商户类型原因,MCC套用依据,扣率算法标识,签约商户内卡收单扣率,扣率算法代码,登记原因码,结算类型标识,手续费清算周期,收费类型-单笔," +
						"封顶、保底信息-单笔,手续费扣率-单笔,固定金额-单笔,封顶值-单笔,保底值-单笔,算法代码-单笔,收费类型-月结类,封顶、保底信息-月结类," +
						"固定金额-月结类,封顶值-月结类,保底值-月结类,算法代码-月结类,分润方式,内卡清算资金开户行名称,清算机构类型,异常时收单垫付标志," +
						"商户优先标志,开户行清算号,商户分润手续费索引,分润角色,分润算法,是否周期商户,分段计费模式,商户清算模式,商户属性位图," +
						"周期结算类型,报表生成标志位图,是否参加日间清算,银联代理清算标识,垫付回补类型,商户文件生成标志,商户手续费决定索引," +
						"营业执照号码,营业地址,负责人,固定电话,法人代表,法人代表证件号码,法人代表证件类型,本金清算周期," +
						"预留1,预留2,内卡开始收单日期,商户名称,清算账户账号,商户英文名称,开户行代码,分润代码," +
						"内卡清算资金开户行清算号,特殊计费类型,特殊计费档次");
				
				writer.newLine();
				
				for(int j = 0;j < lenT;j++ ){
					if(j != lenT -1){
						if(data[i][j] != null){
							writer.write(data[i][j].toString().trim()+",");
						}else{
							writer.write(""+",");
						}
					}else{
						if(data[i][j] != null){
							writer.write(data[i][j].toString().trim());
						}else{
							writer.write("");
						}
					}
				}
				
				writer.newLine();
			}else {
				for(int j = 0;j < lenT;j++ ){
					if(j != lenT -1){
						if(data[i][j] != null){
							writer.write(data[i][j].toString().trim()+",");
						}else{
							writer.write(""+",");
						}
					}else{
						if(data[i][j] != null){
							writer.write(data[i][j].toString().trim());
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
		
		
		String whereSql = " WHERE crt_opr_id in (select opr_id from tbl_opr_info where brh_id in " + operator.getBrhBelowId() + ") ";
		if (!StringUtil.isNull(mchtNo)) {
			whereSql += "AND mcht_no = '" + mchtNo + "' ";
		}
		if (!StringUtil.isNull(mchtStatus)) {
			whereSql += "AND mcht_status = '" + mchtStatus + "' ";
		}
		if (!StringUtil.isNull(mchtType)) {
			whereSql += "AND mchnt_srv_tp = '" + mchtType + "' ";
		}
		if (!StringUtil.isNull(startDate)) {
			whereSql += "AND substr(crt_ts,0,8) >= '" + startDate + "' ";
		}
		if (!StringUtil.isNull(endDate)) {
			whereSql += "AND substr(crt_ts,0,8) <= '" + endDate + "' ";
		}
		if (!StringUtil.isNull(startDateU)) {
			whereSql += "AND substr(upd_ts,0,8) >= '" + startDateU + "' ";
		}
		if (!StringUtil.isNull(endDateU)) {
			whereSql += "AND substr(upd_ts,0,8) <= '" + endDateU + "' ";
		}
		

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT (case when mcht_status != 0 AND crt_ts = upd_ts then 'I' when mcht_status != 0 AND crt_ts < upd_ts then 'U' WHEN  mcht_status = 0 THEN 'D' END) AS INSET_FLAG," +
				  "mcht_no,inner_acq_inst_id,acq_inst_id_code,inst_id,mcht_nm,mcht_short_cn,area_no,inner_stlm_ins_id,mcht_type," +
				  "mcht_status,acq_area_cd,1 AS SETTLE_FLAG,conn_inst_cd,mchnt_tp_grp,mchnt_srv_tp,real_mcht_tp,settle_area_cd,mcht_acq_curr,conn_type," +
				  "currcy_cd,deposit_flag,res_pan_flag,res_track_flag,process_flag,cntry_code,ch_store_flag,mcht_tp_reason,mcc_md,rebate_flag," +
				  "mcht_acq_rebate,rebate_stlm_cd,reason_code,rate_type,fee_cycle,fee_type,limit_flag,fee_rebate,settle_amt,amt_top," +
				  "amt_bottom,disc_cd,fee_type_m,limit_flag_m,settle_amt_m,amt_top_m,amt_bottom_m,disc_cd_m,fee_rate_type,settle_bank_no," +
				  "settle_bank_type,advanced_flag,prior_flag,open_stlno,feerate_index,rate_role,rate_disc AS XX,cycle_mcht,fee_div_mode," +
				  "settle_mode,attr_bmp,cycle_settle_type,report_bmp,day_stlm_flag,cup_stlm_flag,adv_ret_flag,mcht_file_flag AS XX2,fee_act,licence_no," +
				  "licence_add,principal,comm_tel,manager,mana_cred_no,mana_cred_tp,capital_sett_cycle,reserved,reserved1,card_in_start_date,mcht_short_cn,settle_acct,mcht_e_nm," +
				  "settle_bank,rate_no,card_in_settle_bank,fee_spe_type," +
				  "(case when fee_spe_gra in('0','2','4','7') then '0' when fee_spe_gra in('1','3','5') then '1' when fee_spe_gra ='6' then '2' end) " +
				  "FROM tbl_mcht_cup_info a ");
		
		sb.append(whereSql);
		sb.append(" ORDER BY mcht_no,area_no,mchnt_srv_tp,mcht_type ");
		
		return sb.toString();
	}
	
	private String mchtNo;
	private String mchtStatus;
	private String mchtType;
	private String startDate;
	private String endDate;
	private String startDateU;
	private String endDateU;

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getMchtStatus() {
		return mchtStatus;
	}

	public void setMchtStatus(String mchtStatus) {
		this.mchtStatus = mchtStatus;
	}

	public String getMchtType() {
		return mchtType;
	}

	public void setMchtType(String mchtType) {
		this.mchtType = mchtType;
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

	public String getStartDateU() {
		return startDateU;
	}

	public void setStartDateU(String startDateU) {
		this.startDateU = startDateU;
	}

	public String getEndDateU() {
		return endDateU;
	}

	public void setEndDateU(String endDateU) {
		this.endDateU = endDateU;
	}

}