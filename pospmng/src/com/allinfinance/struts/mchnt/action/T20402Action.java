package com.allinfinance.struts.mchnt.action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;

import com.allinfinance.bo.mchnt.T20401BO;
import com.allinfinance.bo.mchnt.T20402BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.po.TblMchtCupInfo;
import com.allinfinance.po.TblMchtCupInfoTmp;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.struts.system.action.BaseSupport;
import com.allinfinance.system.util.BeanUtils;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.LogUtil;
import com.allinfinance.system.util.SysParamUtil;

/**
 * Title:入网商户信息维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-3-17
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author GaoHao
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T20402Action extends BaseAction {
	

	T20401BO t20401BO = (T20401BO) ContextUtil.getBean("T20401BO");
	T20402BO t20402BO = (T20402BO) ContextUtil.getBean("T20402BO");

	private String mchtNo;
	private String mcht_no;
	private String inner_acq_inst_id;
	private String acq_inst_id_code;
	private String inst_id;
	private String mcht_nm;
	private String mcht_short_cn ;
	private String area_no;
	private String inner_stlm_ins_id;
	private String mcht_type;
	private String mcht_status;
	private String acq_area_cd;
	private String settle_flg;
	private String conn_inst_cd;
	private String mchnt_tp_grp;
	private String mchnt_srv_tp;
	private String real_mcht_tp;
	private String settle_area_cd;
	private String mcht_acq_curr;
	private String conn_type;
	private String currcy_cd;
	private String deposit_flag;
	private String res_pan_flag;
	private String res_track_flag;
	private String process_flag;
	private String cntry_code;
	private String ch_store_flag;
	private String mcht_tp_reason;
	private String mcc_md;
	private String rebate_flag;
	private String mcht_acq_rebate;
	private String rebate_stlm_cd;
	private String reason_code;
	private String rate_type;
	private String fee_cycle;
	private String fee_type;
	private String limit_flag;
	private String fee_rebate;
	private String settle_amt;
	private String amt_top;
	private String amt_bottom;
	private String disc_cd;
	private String fee_type_m;
	private String limit_flag_m;
	private String settle_amt_m;
	private String amt_top_m;
	private String amt_bottom_m;
	private String disc_cd_m;
	private String fee_rate_type;
	private String settle_bank_no;
	private String settle_bank_type;
	private String advanced_flag;
	private String prior_flag;
	private String open_stlno;
	private String feerate_index;
	private String rate_role;
	private String rate_disc;
	private String cycle_mcht;
	private String mac_chk_flag;
	private String fee_div_mode;
	private String settle_mode;
	private String attr_bmp;
	private String cycle_settle_type;
	private String report_bmp;
	private String day_stlm_flag;
	private String cup_stlm_flag;
	private String adv_ret_flag;
	private String mcht_file_flag;
	private String fee_act;
	private String licence_no;
	private String licence_add;
	private String  principal;
	private String comm_tel;
	private String manager;
	private String mana_cred_tp;
	private String mana_cred_no;
	private String capital_sett_cycle;
	private String card_in_start_date;
	private String settle_acct;
	private String mcht_e_nm;
	private String settle_bank;
	private String rate_no;
	private String card_in_settle_bank;
	private String fee_spe_type;
	private String fee_spe_gra;
	private String reserved;
	private String reserved1;
	private String reserved2;
	private String upd_opr_id;
	private String upd_ts;
	private String crt_opr_id;
	private String crt_ts;
	private String mchtCup40;
	private String mchtCup401;
	private String mchtCup54;
	private String mchtCup541;
	private String mchtCup542;
	private String mchtCup543;
	private String mchtCup781;
	private String mchtCup782;
	private String mchtCup783;
	private String mchtCup784;
	private String mchtCup785;
	private String mchtCup786;
	private String mchtCup787;
	private String mchtCup82;
	private String mchtCup821;
	private String mchtCup822;
	
	@Override
	protected String subExecute() throws Exception {
		if("update".equals(method)) {
			rspCode = update();
		} else if("stop".equals(method)) {
			rspCode = stop();
		} else if("recover".equals(method)) {
			rspCode = recover();
		} else if("del".equals(method)) {
			rspCode = del();
		}
		return rspCode;
	}

	
	private String update() {
		try {
			TblMchtCupInfoTmp tmp =new TblMchtCupInfoTmp();
			
			tmp = t20401BO.get(mcht_no);
			if(tmp == null){
				return "找不到该商户信息，请刷新后重试！";
			}
			
			log("修改直联商户：" + mcht_no);
			
			String state = tmp.getMcht_status();
			String crt_id = tmp.getCrt_opr_id();
			String crt_ts = tmp.getCrt_ts();
			
			BeanUtils.copyProperties(tmp, this);
			
			if("9".equals(state) || "8".equals(state)){//9-新增待审核，8-新增拒绝
				tmp.setMcht_status("9");//新增待审核
			}else{
				tmp.setMcht_status("7");//修改待审核
			}
			
			tmp.setSettle_flg("1");//参与
			tmp.setCrt_opr_id(crt_id);
			tmp.setCrt_ts(crt_ts);
			tmp.setUpd_opr_id(operator.getOprId());
			tmp.setUpd_ts(CommonFunction.getCurrentDateTime());
			
			if (!StringUtil.isNull(deposit_flag)
					&& "on".equalsIgnoreCase(deposit_flag)) {
				tmp.setDeposit_flag("1");
			} else {
				tmp.setDeposit_flag("0");
			}
			
			if (!StringUtil.isNull(res_pan_flag)
					&& "on".equalsIgnoreCase(res_pan_flag)) {
				tmp.setRes_pan_flag("1");
			} else {
				tmp.setRes_pan_flag("0");
			}
			
			if (!StringUtil.isNull(res_track_flag)
					&& "on".equalsIgnoreCase(res_track_flag)) {
				tmp.setRes_track_flag("1");
			} else {
				tmp.setRes_track_flag("0");
			}
			
			if (!StringUtil.isNull(process_flag)
					&& "on".equalsIgnoreCase(process_flag)) {
				tmp.setProcess_flag("1");
			} else {
				tmp.setProcess_flag("0");
			}
			
			if (!StringUtil.isNull(cycle_mcht)
					&& "on".equalsIgnoreCase(cycle_mcht)) {
				tmp.setCycle_mcht("1");
			} else {
				tmp.setCycle_mcht("0");
			}
			
			if(isNotEmpty(mchtCup40)){
				tmp.setMcht_acq_rebate(mchtCup40);
			}else if(isNotEmpty(mchtCup401)){
				tmp.setMcht_acq_rebate(mchtCup401);
			}
			tmp.setRebate_stlm_cd(rebate_stlm_cd);
			
			//分段计费
			if (!isNotEmpty(mchtCup54)) {
				mchtCup54 = "0";
			} 
			if (!isNotEmpty(mchtCup541)) {
				mchtCup541 = "0";
			} 
			if (!isNotEmpty(mchtCup542)) {
				mchtCup542 = "0";
			} 
			if (!isNotEmpty(mchtCup543)) {
				mchtCup543 = "0";
			} 
			String feeDivMode = mchtCup54 + mchtCup541 + mchtCup542 + mchtCup543;
			tmp.setFee_div_mode(feeDivMode);
			
			//分润角色
			if (!isNotEmpty(mchtCup781)) {
				mchtCup781 = "0";
			}else if("on".equalsIgnoreCase(mchtCup781)){
				mchtCup781 = "1";
			}
			if (!isNotEmpty(mchtCup782)) {
				mchtCup782 = "0";
			}else if("on".equalsIgnoreCase(mchtCup782)){
				mchtCup782 = "1";
			}
			if (!isNotEmpty(mchtCup783)) {
				mchtCup783 = "0";
			}else if("on".equalsIgnoreCase(mchtCup783)){
				mchtCup783 = "1";
			}
			if (!isNotEmpty(mchtCup784)) {
				mchtCup784 = "0";
			}else if("on".equalsIgnoreCase(mchtCup784)){
				mchtCup784 = "1";
			}
			if (!isNotEmpty(mchtCup785)) {
				mchtCup785 = "0";
			}else if("on".equalsIgnoreCase(mchtCup785)){
				mchtCup785 = "1";
			}
			if (!isNotEmpty(mchtCup786)) {
				mchtCup786 = "0";
			}else if("on".equalsIgnoreCase(mchtCup786)){
				mchtCup786 = "1";
			}
			if (!isNotEmpty(mchtCup787)) {
				mchtCup787 = "0";
			}else if("on".equalsIgnoreCase(mchtCup787)){
				mchtCup787 = "1";
			}
			String rateRole = mchtCup781 + mchtCup782 + mchtCup783 + mchtCup784 + mchtCup785 + mchtCup786 + mchtCup787 + "000";
			tmp.setRate_role(rateRole);
			
			//商户属性位图，填充到10位
			if (!isNotEmpty(attr_bmp)) {
				attr_bmp = "0";
			}else if("on".equalsIgnoreCase(attr_bmp)){
				attr_bmp = "1";
			}
			String attrBmp = attr_bmp + "000000000";
			tmp.setAttr_bmp(attrBmp);
			
			//报表生成位图，3位组成一个字段，填充到1位
			if (!isNotEmpty(mchtCup82)) {
				mchtCup82 = "0";
			}else if("on".equalsIgnoreCase(mchtCup82)){
				mchtCup82 = "1";
			}
			if (!isNotEmpty(mchtCup821)) {
				mchtCup821 = "0";
			}else if("on".equalsIgnoreCase(mchtCup821)){
				mchtCup821 = "1";
			}
			if (!isNotEmpty(mchtCup822)) {
				mchtCup822 = "0";
			}else if("on".equalsIgnoreCase(mchtCup822)){
				mchtCup822 = "1";
			}
			String reprotBmp = mchtCup82 + mchtCup821 + mchtCup822 + "0000000";
			tmp.setReport_bmp(reprotBmp);
			
			rspCode = t20401BO.saveOrUpdate(tmp);
					
			
			if (Constants.SUCCESS_CODE.equals(rspCode)) {
				return Constants.SUCCESS_CODE_CUSTOMIZE + "修改商户信息成功，商户编号[" + mcht_no + "]";
			} else {
				return rspCode;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "存储失败！";
		}
	}
	
	//冻结
	private String stop() {
		TblMchtCupInfoTmp tmp =new TblMchtCupInfoTmp();
		
		tmp = t20401BO.get(mchtNo);
		String state = null;
		if(tmp == null){
			return "找不到该商户信息，请刷新后重试！";
		}else {
			state = tmp.getMcht_status();
			if(!"1".equals(state)){
				return "不能操作的状态";
			}
		}
		
		log("冻结直联商户：" + mchtNo);
		
		tmp.setMcht_status("5");//冻结待审核
		tmp.setUpd_opr_id(operator.getOprId());
		tmp.setUpd_ts(CommonFunction.getCurrentDateTime());
		
		rspCode = t20401BO.saveOrUpdate(tmp);
		
		return Constants.SUCCESS_CODE;
	}

	//恢复
	private String recover() {
		TblMchtCupInfoTmp tmp =new TblMchtCupInfoTmp();
		
		tmp = t20401BO.get(mchtNo);
		String state = null;
		if(tmp == null){
			return "找不到该商户信息，请刷新后重试！";
		}else {
			state = tmp.getMcht_status();
			if(!"2".equals(state)){
				return "不能操作的状态";
			}
		}
		
		log("恢复直联商户：" + mchtNo);
		
		tmp.setMcht_status("H");//恢复待审核
		tmp.setUpd_opr_id(operator.getOprId());
		tmp.setUpd_ts(CommonFunction.getCurrentDateTime());
		
		rspCode = t20401BO.saveOrUpdate(tmp);
		
		return Constants.SUCCESS_CODE;
	}
	
	//注销
	private String del() {
		TblMchtCupInfoTmp tmp =new TblMchtCupInfoTmp();
		
		tmp = t20401BO.get(mchtNo);
		String state = null;
		if(tmp == null){
			return "找不到该商户信息，请刷新后重试！";
		}else {
			state = tmp.getMcht_status();
			if(!"1".equals(state)){
				return "不能操作的状态";
			}
		}
		
		log("注销直联商户：" + mchtNo);
		
		tmp.setMcht_status("3");//注销待审核
		tmp.setUpd_opr_id(operator.getOprId());
		tmp.setUpd_ts(CommonFunction.getCurrentDateTime());
		
		rspCode = t20401BO.saveOrUpdate(tmp);
		
		return Constants.SUCCESS_CODE;
	}




	public java.lang.String getMcht_no() {
		return mcht_no;
	}


	public void setMcht_no(java.lang.String mcht_no) {
		this.mcht_no = mcht_no;
	}


	public java.lang.String getInner_acq_inst_id() {
		return inner_acq_inst_id;
	}


	public void setInner_acq_inst_id(java.lang.String inner_acq_inst_id) {
		this.inner_acq_inst_id = inner_acq_inst_id;
	}


	public java.lang.String getAcq_inst_id_code() {
		return acq_inst_id_code;
	}


	public void setAcq_inst_id_code(java.lang.String acq_inst_id_code) {
		this.acq_inst_id_code = acq_inst_id_code;
	}


	public java.lang.String getInst_id() {
		return inst_id;
	}


	public void setInst_id(java.lang.String inst_id) {
		this.inst_id = inst_id;
	}


	public java.lang.String getMcht_nm() {
		return mcht_nm;
	}


	public void setMcht_nm(java.lang.String mcht_nm) {
		this.mcht_nm = mcht_nm;
	}


	public java.lang.String getMcht_short_cn() {
		return mcht_short_cn;
	}


	public void setMcht_short_cn(java.lang.String mcht_short_cn) {
		this.mcht_short_cn = mcht_short_cn;
	}


	public java.lang.String getArea_no() {
		return area_no;
	}


	public void setArea_no(java.lang.String area_no) {
		this.area_no = area_no;
	}


	public java.lang.String getInner_stlm_ins_id() {
		return inner_stlm_ins_id;
	}


	public void setInner_stlm_ins_id(java.lang.String inner_stlm_ins_id) {
		this.inner_stlm_ins_id = inner_stlm_ins_id;
	}


	public java.lang.String getMcht_type() {
		return mcht_type;
	}


	public void setMcht_type(java.lang.String mcht_type) {
		this.mcht_type = mcht_type;
	}


	public java.lang.String getMcht_status() {
		return mcht_status;
	}


	public void setMcht_status(java.lang.String mcht_status) {
		this.mcht_status = mcht_status;
	}


	public java.lang.String getAcq_area_cd() {
		return acq_area_cd;
	}


	public void setAcq_area_cd(java.lang.String acq_area_cd) {
		this.acq_area_cd = acq_area_cd;
	}


	public java.lang.String getSettle_flg() {
		return settle_flg;
	}


	public void setSettle_flg(java.lang.String settle_flg) {
		this.settle_flg = settle_flg;
	}


	public java.lang.String getConn_inst_cd() {
		return conn_inst_cd;
	}


	public void setConn_inst_cd(java.lang.String conn_inst_cd) {
		this.conn_inst_cd = conn_inst_cd;
	}


	public java.lang.String getMchnt_tp_grp() {
		return mchnt_tp_grp;
	}


	public void setMchnt_tp_grp(java.lang.String mchnt_tp_grp) {
		this.mchnt_tp_grp = mchnt_tp_grp;
	}


	public java.lang.String getMchnt_srv_tp() {
		return mchnt_srv_tp;
	}


	public void setMchnt_srv_tp(java.lang.String mchnt_srv_tp) {
		this.mchnt_srv_tp = mchnt_srv_tp;
	}


	public java.lang.String getReal_mcht_tp() {
		return real_mcht_tp;
	}


	public void setReal_mcht_tp(java.lang.String real_mcht_tp) {
		this.real_mcht_tp = real_mcht_tp;
	}


	public java.lang.String getSettle_area_cd() {
		return settle_area_cd;
	}


	public void setSettle_area_cd(java.lang.String settle_area_cd) {
		this.settle_area_cd = settle_area_cd;
	}


	public java.lang.String getMcht_acq_curr() {
		return mcht_acq_curr;
	}


	public void setMcht_acq_curr(java.lang.String mcht_acq_curr) {
		this.mcht_acq_curr = mcht_acq_curr;
	}


	public java.lang.String getConn_type() {
		return conn_type;
	}


	public void setConn_type(java.lang.String conn_type) {
		this.conn_type = conn_type;
	}


	public java.lang.String getCurrcy_cd() {
		return currcy_cd;
	}


	public void setCurrcy_cd(java.lang.String currcy_cd) {
		this.currcy_cd = currcy_cd;
	}


	public java.lang.String getDeposit_flag() {
		return deposit_flag;
	}


	public void setDeposit_flag(java.lang.String deposit_flag) {
		this.deposit_flag = deposit_flag;
	}


	public java.lang.String getRes_pan_flag() {
		return res_pan_flag;
	}


	public void setRes_pan_flag(java.lang.String res_pan_flag) {
		this.res_pan_flag = res_pan_flag;
	}


	public java.lang.String getRes_track_flag() {
		return res_track_flag;
	}


	public void setRes_track_flag(java.lang.String res_track_flag) {
		this.res_track_flag = res_track_flag;
	}


	public java.lang.String getProcess_flag() {
		return process_flag;
	}


	public void setProcess_flag(java.lang.String process_flag) {
		this.process_flag = process_flag;
	}


	public java.lang.String getCntry_code() {
		return cntry_code;
	}


	public void setCntry_code(java.lang.String cntry_code) {
		this.cntry_code = cntry_code;
	}


	public java.lang.String getCh_store_flag() {
		return ch_store_flag;
	}


	public void setCh_store_flag(java.lang.String ch_store_flag) {
		this.ch_store_flag = ch_store_flag;
	}


	public java.lang.String getMcht_tp_reason() {
		return mcht_tp_reason;
	}


	public void setMcht_tp_reason(java.lang.String mcht_tp_reason) {
		this.mcht_tp_reason = mcht_tp_reason;
	}


	public java.lang.String getMcc_md() {
		return mcc_md;
	}


	public void setMcc_md(java.lang.String mcc_md) {
		this.mcc_md = mcc_md;
	}


	public java.lang.String getRebate_flag() {
		return rebate_flag;
	}


	public void setRebate_flag(java.lang.String rebate_flag) {
		this.rebate_flag = rebate_flag;
	}


	public java.lang.String getMcht_acq_rebate() {
		return mcht_acq_rebate;
	}


	public void setMcht_acq_rebate(java.lang.String mcht_acq_rebate) {
		this.mcht_acq_rebate = mcht_acq_rebate;
	}


	public java.lang.String getRebate_stlm_cd() {
		return rebate_stlm_cd;
	}


	public void setRebate_stlm_cd(java.lang.String rebate_stlm_cd) {
		this.rebate_stlm_cd = rebate_stlm_cd;
	}


	public java.lang.String getReason_code() {
		return reason_code;
	}


	public void setReason_code(java.lang.String reason_code) {
		this.reason_code = reason_code;
	}


	public java.lang.String getRate_type() {
		return rate_type;
	}


	public void setRate_type(java.lang.String rate_type) {
		this.rate_type = rate_type;
	}


	public java.lang.String getFee_cycle() {
		return fee_cycle;
	}


	public void setFee_cycle(java.lang.String fee_cycle) {
		this.fee_cycle = fee_cycle;
	}


	public java.lang.String getFee_type() {
		return fee_type;
	}


	public void setFee_type(java.lang.String fee_type) {
		this.fee_type = fee_type;
	}


	public java.lang.String getLimit_flag() {
		return limit_flag;
	}


	public void setLimit_flag(java.lang.String limit_flag) {
		this.limit_flag = limit_flag;
	}


	public java.lang.String getFee_rebate() {
		return fee_rebate;
	}


	public void setFee_rebate(java.lang.String fee_rebate) {
		this.fee_rebate = fee_rebate;
	}


	public java.lang.String getSettle_amt() {
		return settle_amt;
	}


	public void setSettle_amt(java.lang.String settle_amt) {
		this.settle_amt = settle_amt;
	}


	public java.lang.String getAmt_top() {
		return amt_top;
	}


	public void setAmt_top(java.lang.String amt_top) {
		this.amt_top = amt_top;
	}


	public java.lang.String getAmt_bottom() {
		return amt_bottom;
	}


	public void setAmt_bottom(java.lang.String amt_bottom) {
		this.amt_bottom = amt_bottom;
	}


	public java.lang.String getDisc_cd() {
		return disc_cd;
	}


	public void setDisc_cd(java.lang.String disc_cd) {
		this.disc_cd = disc_cd;
	}


	public java.lang.String getFee_type_m() {
		return fee_type_m;
	}


	public void setFee_type_m(java.lang.String fee_type_m) {
		this.fee_type_m = fee_type_m;
	}


	public java.lang.String getLimit_flag_m() {
		return limit_flag_m;
	}


	public void setLimit_flag_m(java.lang.String limit_flag_m) {
		this.limit_flag_m = limit_flag_m;
	}


	public java.lang.String getSettle_amt_m() {
		return settle_amt_m;
	}


	public void setSettle_amt_m(java.lang.String settle_amt_m) {
		this.settle_amt_m = settle_amt_m;
	}


	public java.lang.String getAmt_top_m() {
		return amt_top_m;
	}


	public void setAmt_top_m(java.lang.String amt_top_m) {
		this.amt_top_m = amt_top_m;
	}


	public java.lang.String getAmt_bottom_m() {
		return amt_bottom_m;
	}


	public void setAmt_bottom_m(java.lang.String amt_bottom_m) {
		this.amt_bottom_m = amt_bottom_m;
	}


	public java.lang.String getDisc_cd_m() {
		return disc_cd_m;
	}


	public void setDisc_cd_m(java.lang.String disc_cd_m) {
		this.disc_cd_m = disc_cd_m;
	}


	public java.lang.String getFee_rate_type() {
		return fee_rate_type;
	}


	public void setFee_rate_type(java.lang.String fee_rate_type) {
		this.fee_rate_type = fee_rate_type;
	}


	public java.lang.String getSettle_bank_no() {
		return settle_bank_no;
	}


	public void setSettle_bank_no(java.lang.String settle_bank_no) {
		this.settle_bank_no = settle_bank_no;
	}


	public java.lang.String getSettle_bank_type() {
		return settle_bank_type;
	}


	public void setSettle_bank_type(java.lang.String settle_bank_type) {
		this.settle_bank_type = settle_bank_type;
	}


	public java.lang.String getAdvanced_flag() {
		return advanced_flag;
	}


	public void setAdvanced_flag(java.lang.String advanced_flag) {
		this.advanced_flag = advanced_flag;
	}


	public java.lang.String getPrior_flag() {
		return prior_flag;
	}


	public void setPrior_flag(java.lang.String prior_flag) {
		this.prior_flag = prior_flag;
	}


	public java.lang.String getOpen_stlno() {
		return open_stlno;
	}


	public void setOpen_stlno(java.lang.String open_stlno) {
		this.open_stlno = open_stlno;
	}


	public java.lang.String getFeerate_index() {
		return feerate_index;
	}


	public void setFeerate_index(java.lang.String feerate_index) {
		this.feerate_index = feerate_index;
	}


	public java.lang.String getRate_role() {
		return rate_role;
	}


	public void setRate_role(java.lang.String rate_role) {
		this.rate_role = rate_role;
	}


	public java.lang.String getRate_disc() {
		return rate_disc;
	}


	public void setRate_disc(java.lang.String rate_disc) {
		this.rate_disc = rate_disc;
	}


	public java.lang.String getCycle_mcht() {
		return cycle_mcht;
	}


	public void setCycle_mcht(java.lang.String cycle_mcht) {
		this.cycle_mcht = cycle_mcht;
	}


	public java.lang.String getMac_chk_flag() {
		return mac_chk_flag;
	}


	public void setMac_chk_flag(java.lang.String mac_chk_flag) {
		this.mac_chk_flag = mac_chk_flag;
	}


	public java.lang.String getFee_div_mode() {
		return fee_div_mode;
	}


	public void setFee_div_mode(java.lang.String fee_div_mode) {
		this.fee_div_mode = fee_div_mode;
	}


	public java.lang.String getSettle_mode() {
		return settle_mode;
	}


	public void setSettle_mode(java.lang.String settle_mode) {
		this.settle_mode = settle_mode;
	}


	public java.lang.String getAttr_bmp() {
		return attr_bmp;
	}


	public void setAttr_bmp(java.lang.String attr_bmp) {
		this.attr_bmp = attr_bmp;
	}


	public java.lang.String getCycle_settle_type() {
		return cycle_settle_type;
	}


	public void setCycle_settle_type(java.lang.String cycle_settle_type) {
		this.cycle_settle_type = cycle_settle_type;
	}


	public java.lang.String getReport_bmp() {
		return report_bmp;
	}


	public void setReport_bmp(java.lang.String report_bmp) {
		this.report_bmp = report_bmp;
	}


	public java.lang.String getDay_stlm_flag() {
		return day_stlm_flag;
	}


	public void setDay_stlm_flag(java.lang.String day_stlm_flag) {
		this.day_stlm_flag = day_stlm_flag;
	}


	public java.lang.String getCup_stlm_flag() {
		return cup_stlm_flag;
	}


	public void setCup_stlm_flag(java.lang.String cup_stlm_flag) {
		this.cup_stlm_flag = cup_stlm_flag;
	}


	public java.lang.String getAdv_ret_flag() {
		return adv_ret_flag;
	}


	public void setAdv_ret_flag(java.lang.String adv_ret_flag) {
		this.adv_ret_flag = adv_ret_flag;
	}


	public java.lang.String getMcht_file_flag() {
		return mcht_file_flag;
	}


	public void setMcht_file_flag(java.lang.String mcht_file_flag) {
		this.mcht_file_flag = mcht_file_flag;
	}


	public java.lang.String getFee_act() {
		return fee_act;
	}


	public void setFee_act(java.lang.String fee_act) {
		this.fee_act = fee_act;
	}


	public java.lang.String getLicence_no() {
		return licence_no;
	}


	public void setLicence_no(java.lang.String licence_no) {
		this.licence_no = licence_no;
	}


	public java.lang.String getLicence_add() {
		return licence_add;
	}


	public void setLicence_add(java.lang.String licence_add) {
		this.licence_add = licence_add;
	}


	public java.lang.String getPrincipal() {
		return principal;
	}


	public void setPrincipal(java.lang.String principal) {
		this.principal = principal;
	}


	public java.lang.String getComm_tel() {
		return comm_tel;
	}


	public void setComm_tel(java.lang.String comm_tel) {
		this.comm_tel = comm_tel;
	}


	public java.lang.String getManager() {
		return manager;
	}


	public void setManager(java.lang.String manager) {
		this.manager = manager;
	}


	public java.lang.String getMana_cred_tp() {
		return mana_cred_tp;
	}


	public void setMana_cred_tp(java.lang.String mana_cred_tp) {
		this.mana_cred_tp = mana_cred_tp;
	}


	public java.lang.String getMana_cred_no() {
		return mana_cred_no;
	}


	public void setMana_cred_no(java.lang.String mana_cred_no) {
		this.mana_cred_no = mana_cred_no;
	}


	public java.lang.String getCapital_sett_cycle() {
		return capital_sett_cycle;
	}


	public void setCapital_sett_cycle(java.lang.String capital_sett_cycle) {
		this.capital_sett_cycle = capital_sett_cycle;
	}


	public java.lang.String getCard_in_start_date() {
		return card_in_start_date;
	}


	public void setCard_in_start_date(java.lang.String card_in_start_date) {
		this.card_in_start_date = card_in_start_date;
	}


	public java.lang.String getSettle_acct() {
		return settle_acct;
	}


	public void setSettle_acct(java.lang.String settle_acct) {
		this.settle_acct = settle_acct;
	}


	public java.lang.String getMcht_e_nm() {
		return mcht_e_nm;
	}


	public void setMcht_e_nm(java.lang.String mcht_e_nm) {
		this.mcht_e_nm = mcht_e_nm;
	}


	public java.lang.String getSettle_bank() {
		return settle_bank;
	}


	public void setSettle_bank(java.lang.String settle_bank) {
		this.settle_bank = settle_bank;
	}


	public java.lang.String getRate_no() {
		return rate_no;
	}


	public void setRate_no(java.lang.String rate_no) {
		this.rate_no = rate_no;
	}


	public java.lang.String getCard_in_settle_bank() {
		return card_in_settle_bank;
	}


	public void setCard_in_settle_bank(java.lang.String card_in_settle_bank) {
		this.card_in_settle_bank = card_in_settle_bank;
	}


	public java.lang.String getFee_spe_type() {
		return fee_spe_type;
	}


	public void setFee_spe_type(java.lang.String fee_spe_type) {
		this.fee_spe_type = fee_spe_type;
	}


	public java.lang.String getFee_spe_gra() {
		return fee_spe_gra;
	}


	public void setFee_spe_gra(java.lang.String fee_spe_gra) {
		this.fee_spe_gra = fee_spe_gra;
	}


	public java.lang.String getReserved() {
		return reserved;
	}


	public void setReserved(java.lang.String reserved) {
		this.reserved = reserved;
	}


	public java.lang.String getReserved1() {
		return reserved1;
	}


	public void setReserved1(java.lang.String reserved1) {
		this.reserved1 = reserved1;
	}


	public java.lang.String getReserved2() {
		return reserved2;
	}


	public void setReserved2(java.lang.String reserved2) {
		this.reserved2 = reserved2;
	}


	public java.lang.String getUpd_opr_id() {
		return upd_opr_id;
	}


	public void setUpd_opr_id(java.lang.String upd_opr_id) {
		this.upd_opr_id = upd_opr_id;
	}


	public java.lang.String getUpd_ts() {
		return upd_ts;
	}


	public void setUpd_ts(java.lang.String upd_ts) {
		this.upd_ts = upd_ts;
	}


	public java.lang.String getCrt_opr_id() {
		return crt_opr_id;
	}


	public void setCrt_opr_id(java.lang.String crt_opr_id) {
		this.crt_opr_id = crt_opr_id;
	}


	public java.lang.String getCrt_ts() {
		return crt_ts;
	}


	public void setCrt_ts(java.lang.String crt_ts) {
		this.crt_ts = crt_ts;
	}


	public String getMchtCup40() {
		return mchtCup40;
	}


	public void setMchtCup40(String mchtCup40) {
		this.mchtCup40 = mchtCup40;
	}


	public String getMchtCup401() {
		return mchtCup401;
	}


	public void setMchtCup401(String mchtCup401) {
		this.mchtCup401 = mchtCup401;
	}


	public String getMchtCup54() {
		return mchtCup54;
	}


	public void setMchtCup54(String mchtCup54) {
		this.mchtCup54 = mchtCup54;
	}


	public String getMchtCup541() {
		return mchtCup541;
	}


	public void setMchtCup541(String mchtCup541) {
		this.mchtCup541 = mchtCup541;
	}


	public String getMchtCup542() {
		return mchtCup542;
	}


	public void setMchtCup542(String mchtCup542) {
		this.mchtCup542 = mchtCup542;
	}


	public String getMchtCup543() {
		return mchtCup543;
	}


	public void setMchtCup543(String mchtCup543) {
		this.mchtCup543 = mchtCup543;
	}


	public String getMchtCup781() {
		return mchtCup781;
	}


	public void setMchtCup781(String mchtCup781) {
		this.mchtCup781 = mchtCup781;
	}


	public String getMchtCup782() {
		return mchtCup782;
	}


	public void setMchtCup782(String mchtCup782) {
		this.mchtCup782 = mchtCup782;
	}


	public String getMchtCup783() {
		return mchtCup783;
	}


	public void setMchtCup783(String mchtCup783) {
		this.mchtCup783 = mchtCup783;
	}


	public String getMchtCup784() {
		return mchtCup784;
	}


	public void setMchtCup784(String mchtCup784) {
		this.mchtCup784 = mchtCup784;
	}


	public String getMchtCup785() {
		return mchtCup785;
	}


	public void setMchtCup785(String mchtCup785) {
		this.mchtCup785 = mchtCup785;
	}


	public String getMchtCup786() {
		return mchtCup786;
	}


	public void setMchtCup786(String mchtCup786) {
		this.mchtCup786 = mchtCup786;
	}


	public String getMchtCup787() {
		return mchtCup787;
	}


	public void setMchtCup787(String mchtCup787) {
		this.mchtCup787 = mchtCup787;
	}


	public String getMchtCup82() {
		return mchtCup82;
	}


	public void setMchtCup82(String mchtCup82) {
		this.mchtCup82 = mchtCup82;
	}


	public String getMchtCup821() {
		return mchtCup821;
	}


	public void setMchtCup821(String mchtCup821) {
		this.mchtCup821 = mchtCup821;
	}


	public String getMchtCup822() {
		return mchtCup822;
	}


	public void setMchtCup822(String mchtCup822) {
		this.mchtCup822 = mchtCup822;
	}


	public String getMchtNo() {
		return mchtNo;
	}


	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}


}
