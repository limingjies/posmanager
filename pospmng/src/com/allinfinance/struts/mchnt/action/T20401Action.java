package com.allinfinance.struts.mchnt.action;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import oracle.net.ns.Communication;









import com.allinfinance.bo.mchnt.T20401BO;
import com.allinfinance.bo.mchnt.T20402BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.TblMchntInfoConstants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfTmpDAO;
import com.allinfinance.po.TblMchtCupInfoTmp;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

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
public class T20401Action extends BaseAction {
	
	public ITblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO;
	T20401BO t20401BO = (T20401BO) ContextUtil.getBean("T20401BO");
	T20402BO t20402BO = (T20402BO) ContextUtil.getBean("T20402BO");

	Operator opr = (Operator) ServletActionContext.getRequest()
	.getSession().getAttribute(Constants.OPERATOR_INFO);
	
	@Override
	protected String subExecute() throws Exception {
		if("add".equals(method)) {
			rspCode = add();
		} 
		
		return rspCode;
	}
	
	private String mchtCup1;
	private String mchtCup2;
	private String mchtCup3;
	private String mchtCup4;
	private String mchtCup5;
	private String mchtCup6;
	private String mchtCup7;
	private String mchtCup8;
	private String mchtCup9;
	private String mchtCup10;
	private String mchtCup11;
	private String mchtCup12;
	private String mchtCup13;
	private String mchtCup14;
	private String mchtCup15;
	private String mchtCup16;
	private String mchtCup17;
	private String mchtCup18;
	private String mchtCup19;
	private String mchtCup20;
	private String mchtCup21;
	private String mchtCup22;
	private String mchtCup23;
	private String mchtCup24;
	private String mchtCup25;
	private String mchtCup26;
	private String mchtCup27;
	private String mchtCup28;
	private String mchtCup29;
	private String mchtCup30;
	private String mchtCup31;
	private String mchtCup32;
	private String mchtCup33;
	private String mchtCup34;
	private String mchtCup35;
	private String mchtCup36;
	private String mchtCup37;
	private String mchtCup38;
	private String mchtCup39;
	private String mchtCup40;
	private String mchtCup401;
	private String mchtCup41;
	private String mchtCup42;
	private String mchtCup43;
	private String mchtCup44;
	private String mchtCup45;
	private String mchtCup46;
	private String mchtCup47;
	private String mchtCup48;
	private String mchtCup49;
	private String mchtCup50;
	private String mchtCup51;
	private String mchtCup52;
	private String mchtCup53;
	private String mchtCup54;
	private String mchtCup541;
	private String mchtCup542;
	private String mchtCup543;
	private String mchtCup55;
	private String mchtCup56;
	private String mchtCup57;
	private String mchtCup58;
	private String mchtCup59;
	private String mchtCup60;
	private String mchtCup61;
	private String mchtCup62;
	private String mchtCup63;
	private String mchtCup64;
	private String mchtCup65;
	private String mchtCup66;
	private String mchtCup67;
	private String mchtCup68;
	private String mchtCup69;
	private String mchtCup70;
	private String mchtCup71;
	private String mchtCup72;
	private String mchtCup73;
	private String mchtCup74;
	private String mchtCup75;
	private String mchtCup76;
	private String mchtCup77;
	private String mchtCup78;
	private String mchtCup781;
	private String mchtCup782;
	private String mchtCup783;
	private String mchtCup784;
	private String mchtCup785;
	private String mchtCup786;
	private String mchtCup787;
	private String mchtCup79;
	private String mchtCup80;
	private String mchtCup81;
	private String mchtCup82;
	private String mchtCup83;
	private String mchtCup821;
	private String mchtCup822;
	
	
	
	
	/**
	 * 添加商户审核信息
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private String add() throws IllegalAccessException, InvocationTargetException {
		TblMchtCupInfoTmp tblMchtCupInfoTmp =new TblMchtCupInfoTmp();	
		
		//构造商户号
		String spe = "440" + mchtCup13.substring(0,4) + mchtCup16;
		String mchtNo;
		
		if(isNotEmpty(mchtCup2)){
			if(t20401BO.get(mchtCup2) != null){
				return "该商户号在直联表已存在！";
			}
			if(tblMchtBaseInfTmpDAO.get(mchtCup2) != null){
				return "该商户号与间联商户重复！";
			}
			if (mchtCup2.length() != 15) {
				return "自定义商户编号应为15位的数字";
			}
			if (!mchtCup2.startsWith(spe)){
				return "自定义商户号录入错误，编号规则应为["+spe+"xxxx]";
			}
			mchtNo = mchtCup2;
		}else {
			mchtNo = getMchntId(spe);
		}
		
		tblMchtCupInfoTmp.setMcht_no(mchtNo);
		tblMchtCupInfoTmp.setMchnt_srv_tp(mchtCup1);
		tblMchtCupInfoTmp.setConn_type(mchtCup3);
		tblMchtCupInfoTmp.setInner_acq_inst_id(mchtCup4.substring(0, 10));
		tblMchtCupInfoTmp.setAcq_inst_id_code(mchtCup5.substring(0, 10));
		tblMchtCupInfoTmp.setInst_id(mchtCup6.substring(0, 10));
		tblMchtCupInfoTmp.setInner_stlm_ins_id(mchtCup7.substring(0, 10));
		tblMchtCupInfoTmp.setConn_inst_cd(mchtCup8.substring(0, 10));
		tblMchtCupInfoTmp.setCntry_code(mchtCup9);
		tblMchtCupInfoTmp.setMcht_nm(mchtCup10);
		tblMchtCupInfoTmp.setMcht_short_cn(mchtCup11);
		tblMchtCupInfoTmp.setSettle_flg("1");//参加
		tblMchtCupInfoTmp.setMcht_e_nm(mchtCup12);
		tblMchtCupInfoTmp.setArea_no(mchtCup13);
		tblMchtCupInfoTmp.setAcq_area_cd(mchtCup14);
		tblMchtCupInfoTmp.setSettle_area_cd(mchtCup15);
		tblMchtCupInfoTmp.setMcht_type(mchtCup16);
		tblMchtCupInfoTmp.setReal_mcht_tp(mchtCup17);
		tblMchtCupInfoTmp.setMchnt_tp_grp(mchtCup18);
		tblMchtCupInfoTmp.setMcht_status("9");
		tblMchtCupInfoTmp.setMcht_tp_reason(mchtCup20);
		tblMchtCupInfoTmp.setCh_store_flag(mchtCup21);
		tblMchtCupInfoTmp.setMcc_md(mchtCup22);
		tblMchtCupInfoTmp.setMcht_acq_curr(mchtCup23);
		tblMchtCupInfoTmp.setCurrcy_cd(mchtCup24);
		
		if (!StringUtil.isNull(mchtCup25)
				&& "on".equalsIgnoreCase(mchtCup25)) {
			tblMchtCupInfoTmp.setDeposit_flag("1");
		} else {
			tblMchtCupInfoTmp.setDeposit_flag("0");
		}
		
		if (!StringUtil.isNull(mchtCup26)
				&& "on".equalsIgnoreCase(mchtCup26)) {
			tblMchtCupInfoTmp.setRes_pan_flag("1");
		} else {
			tblMchtCupInfoTmp.setRes_pan_flag("0");
		}
		
		if (!StringUtil.isNull(mchtCup27)
				&& "on".equalsIgnoreCase(mchtCup27)) {
			tblMchtCupInfoTmp.setRes_track_flag("1");
		} else {
			tblMchtCupInfoTmp.setRes_track_flag("0");
		}
		
		if (!StringUtil.isNull(mchtCup28)
				&& "on".equalsIgnoreCase(mchtCup28)) {
			tblMchtCupInfoTmp.setProcess_flag("1");
		} else {
			tblMchtCupInfoTmp.setProcess_flag("0");
		}
		
		if (!StringUtil.isNull(mchtCup29)
				&& "on".equalsIgnoreCase(mchtCup29)) {
			tblMchtCupInfoTmp.setCycle_mcht("1");
		} else {
			tblMchtCupInfoTmp.setCycle_mcht("0");
		} 
		
		tblMchtCupInfoTmp.setMac_chk_flag(mchtCup30);
		tblMchtCupInfoTmp.setLicence_no(mchtCup31);
		tblMchtCupInfoTmp.setLicence_add(mchtCup32);
		tblMchtCupInfoTmp.setPrincipal(mchtCup33);
		tblMchtCupInfoTmp.setComm_tel(mchtCup34);
		tblMchtCupInfoTmp.setManager(mchtCup35);
		
		
		tblMchtCupInfoTmp.setMana_cred_tp(mchtCup36);
		tblMchtCupInfoTmp.setMana_cred_no(mchtCup37);
		tblMchtCupInfoTmp.setSettle_bank(mchtCup38);
		tblMchtCupInfoTmp.setRebate_flag(mchtCup39);
		
		//费率
		if(isNotEmpty(mchtCup40)){
			tblMchtCupInfoTmp.setMcht_acq_rebate(mchtCup40);
		}else if(isNotEmpty(mchtCup401)){
			tblMchtCupInfoTmp.setMcht_acq_rebate(mchtCup401);
		}
		
		tblMchtCupInfoTmp.setRebate_stlm_cd(mchtCup41);
		tblMchtCupInfoTmp.setSettle_bank_type(mchtCup42);
		tblMchtCupInfoTmp.setAdvanced_flag(mchtCup43);
		tblMchtCupInfoTmp.setPrior_flag(mchtCup44);
		tblMchtCupInfoTmp.setOpen_stlno(mchtCup45);
		tblMchtCupInfoTmp.setCard_in_start_date(mchtCup46);
		tblMchtCupInfoTmp.setSettle_mode(mchtCup47);
		tblMchtCupInfoTmp.setCycle_settle_type(mchtCup48);
		tblMchtCupInfoTmp.setDay_stlm_flag(mchtCup49);
		tblMchtCupInfoTmp.setCup_stlm_flag(mchtCup50);
		tblMchtCupInfoTmp.setAdv_ret_flag(mchtCup51);
		tblMchtCupInfoTmp.setFee_act(mchtCup52);
		tblMchtCupInfoTmp.setCapital_sett_cycle(mchtCup53);
		
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
		tblMchtCupInfoTmp.setFee_div_mode(feeDivMode);
		
		tblMchtCupInfoTmp.setSettle_acct(mchtCup55);
		tblMchtCupInfoTmp.setCard_in_settle_bank(mchtCup56);
		tblMchtCupInfoTmp.setFee_spe_type(mchtCup57);
		tblMchtCupInfoTmp.setFee_spe_gra(mchtCup58);
		tblMchtCupInfoTmp.setReason_code(mchtCup59);
		tblMchtCupInfoTmp.setRate_type(mchtCup60);
		tblMchtCupInfoTmp.setFee_cycle(mchtCup61);
		tblMchtCupInfoTmp.setFee_type(mchtCup62);
		tblMchtCupInfoTmp.setLimit_flag(mchtCup63);
		tblMchtCupInfoTmp.setFee_rebate(mchtCup64);
		tblMchtCupInfoTmp.setSettle_amt(mchtCup65);
		tblMchtCupInfoTmp.setAmt_top(mchtCup66);
		tblMchtCupInfoTmp.setAmt_bottom(mchtCup67);
		tblMchtCupInfoTmp.setDisc_cd(mchtCup68);
		tblMchtCupInfoTmp.setFee_type_m(mchtCup69);
		tblMchtCupInfoTmp.setLimit_flag_m(mchtCup70);
		tblMchtCupInfoTmp.setSettle_amt_m(mchtCup71);
		tblMchtCupInfoTmp.setAmt_top_m(mchtCup72);
		tblMchtCupInfoTmp.setAmt_bottom_m(mchtCup73);
		tblMchtCupInfoTmp.setDisc_cd_m(mchtCup74);
		tblMchtCupInfoTmp.setFee_rate_type(mchtCup75);
		tblMchtCupInfoTmp.setSettle_bank_no(mchtCup76);
		tblMchtCupInfoTmp.setFeerate_index(mchtCup77);
		
		//分润行
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
		tblMchtCupInfoTmp.setRate_role(rateRole);
		
		tblMchtCupInfoTmp.setRate_disc(mchtCup79);
		tblMchtCupInfoTmp.setRate_no(mchtCup80);
		
		//商户属性位图，填充到10位
		if (!isNotEmpty(mchtCup81)) {
			mchtCup81 = "0";
		}else if("on".equalsIgnoreCase(mchtCup81)){
			mchtCup81 = "1";
		}
		String attrBmp = mchtCup81 + "000000000";
		tblMchtCupInfoTmp.setAttr_bmp(attrBmp);
		
		
		//报表生成位图，3位选项，填充到10位
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
		tblMchtCupInfoTmp.setReport_bmp(reprotBmp);
		
		tblMchtCupInfoTmp.setMcht_file_flag(mchtCup83);


		tblMchtCupInfoTmp.setCrt_opr_id(opr.getOprId());
		tblMchtCupInfoTmp.setCrt_ts(CommonFunction.getCurrentDateTime());
		tblMchtCupInfoTmp.setUpd_opr_id(opr.getOprId());
		
		rspCode = t20401BO.add(tblMchtCupInfoTmp);
		
		return Constants.SUCCESS_CODE_CUSTOMIZE + "新增商户信息成功，商户编号[" + mchtNo + "]";
	}
	

	public static synchronized String getMchntId(String spe) {
		ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
		//判断是否存在序号为0001的ID
		String bgJ = "select count(*) from TBL_MCHT_BASE_INF_TMP where trim(mcht_no) = '" + spe + "0001" + "'" ;
		BigDecimal cJ = (BigDecimal) commQueryDAO.findBySQLQuery(bgJ).get(0);
		String bgZ = "select count(*) from tbl_mcht_cup_info_tmp where trim(mcht_no) = '" + spe + "0001" + "'" ;
		BigDecimal cZ = (BigDecimal) commQueryDAO.findBySQLQuery(bgZ).get(0);
		if (cJ.intValue() == 0 && cZ.intValue() == 0) {
			return spe + "0001";
		}
		//间联表最小编号
		String sqlJ = "select min(substr(mcht_no,12,4) + 1) from TBL_MCHT_BASE_INF_TMP where (substr(mcht_no,12,4) + 1) not in " +
					  "(select substr(mcht_no,12,4) + 0 from TBL_MCHT_BASE_INF_TMP where substr(mcht_no,1,11) = '" + spe + "') " +
					  "and substr(mcht_no,1,11) = '" + spe + "' and (substr(mcht_no,12,4) + 1) not in " +
					  "(select substr(b.mcht_no,12,4) + 0 as cupNo from tbl_mcht_cup_info_tmp b where substr(b.mcht_no,1,11) = '" + spe + "')";
		List<BigDecimal> resultSetJ = commQueryDAO.findBySQLQuery(sqlJ);
		
		
		//直联表最小编号
		String sqlZ = "select min(substr(mcht_no,12,4) + 1) as min from tbl_mcht_cup_info_tmp where (substr(mcht_no,12,4) + 1) not in " +
			  		  "(select substr(mcht_no,12,4) + 0 as cupNo from tbl_mcht_cup_info_tmp where substr(mcht_no,1,11) = '" + spe + "') " +
			  		  "and substr(mcht_no,1,11) = '" + spe + "' and (substr(mcht_no,12,4) + 1) not in " +
			  		  "(select substr(b.mcht_no,12,4) + 0 as conNo from tbl_mcht_base_inf_tmp b where substr(b.mcht_no,1,11) = '" + spe + "')";
		List<BigDecimal> resultSetZ = commQueryDAO.findBySQLQuery(sqlZ);
		
		int id = 0001;
		if(resultSetJ.get(0) == null && resultSetZ.get(0) == null) {
			return spe + "0001";
		}else if(resultSetJ.get(0) == null && resultSetZ.get(0) != null){
			id = resultSetZ.get(0).intValue();
		}else if(resultSetJ.get(0) != null && resultSetZ.get(0) == null){
			id = resultSetJ.get(0).intValue();
		}else if(resultSetJ.get(0) != null && resultSetZ.get(0) != null){
			if(resultSetJ.get(0).intValue() >= resultSetZ.get(0).intValue()){
				id = resultSetZ.get(0).intValue();
			}else{
				id = resultSetJ.get(0).intValue();
			}
		}
		
		if(id == 10000) {
			return "";
		}
		return spe + CommonFunction.fillString(String.valueOf(id), '0', 4, false);
	}

	public String getMchtCup1() {
		return mchtCup1;
	}

	public void setMchtCup1(String mchtCup1) {
		this.mchtCup1 = mchtCup1;
	}

	public String getMchtCup2() {
		return mchtCup2;
	}

	public void setMchtCup2(String mchtCup2) {
		this.mchtCup2 = mchtCup2;
	}

	public String getMchtCup3() {
		return mchtCup3;
	}

	public void setMchtCup3(String mchtCup3) {
		this.mchtCup3 = mchtCup3;
	}

	public String getMchtCup4() {
		return mchtCup4;
	}

	public void setMchtCup4(String mchtCup4) {
		this.mchtCup4 = mchtCup4;
	}

	public String getMchtCup5() {
		return mchtCup5;
	}

	public void setMchtCup5(String mchtCup5) {
		this.mchtCup5 = mchtCup5;
	}

	public String getMchtCup6() {
		return mchtCup6;
	}

	public void setMchtCup6(String mchtCup6) {
		this.mchtCup6 = mchtCup6;
	}

	public String getMchtCup7() {
		return mchtCup7;
	}

	public void setMchtCup7(String mchtCup7) {
		this.mchtCup7 = mchtCup7;
	}

	public String getMchtCup8() {
		return mchtCup8;
	}

	public void setMchtCup8(String mchtCup8) {
		this.mchtCup8 = mchtCup8;
	}

	public String getMchtCup9() {
		return mchtCup9;
	}

	public void setMchtCup9(String mchtCup9) {
		this.mchtCup9 = mchtCup9;
	}

	public String getMchtCup10() {
		return mchtCup10;
	}

	public void setMchtCup10(String mchtCup10) {
		this.mchtCup10 = mchtCup10;
	}

	public String getMchtCup11() {
		return mchtCup11;
	}

	public void setMchtCup11(String mchtCup11) {
		this.mchtCup11 = mchtCup11;
	}

	public String getMchtCup12() {
		return mchtCup12;
	}

	public void setMchtCup12(String mchtCup12) {
		this.mchtCup12 = mchtCup12;
	}

	public String getMchtCup13() {
		return mchtCup13;
	}

	public void setMchtCup13(String mchtCup13) {
		this.mchtCup13 = mchtCup13;
	}

	public String getMchtCup14() {
		return mchtCup14;
	}

	public void setMchtCup14(String mchtCup14) {
		this.mchtCup14 = mchtCup14;
	}

	public String getMchtCup15() {
		return mchtCup15;
	}

	public void setMchtCup15(String mchtCup15) {
		this.mchtCup15 = mchtCup15;
	}

	public String getMchtCup16() {
		return mchtCup16;
	}

	public void setMchtCup16(String mchtCup16) {
		this.mchtCup16 = mchtCup16;
	}

	public String getMchtCup17() {
		return mchtCup17;
	}

	public void setMchtCup17(String mchtCup17) {
		this.mchtCup17 = mchtCup17;
	}

	public String getMchtCup18() {
		return mchtCup18;
	}

	public void setMchtCup18(String mchtCup18) {
		this.mchtCup18 = mchtCup18;
	}

	public String getMchtCup19() {
		return mchtCup19;
	}

	public void setMchtCup19(String mchtCup19) {
		this.mchtCup19 = mchtCup19;
	}

	public String getMchtCup20() {
		return mchtCup20;
	}

	public void setMchtCup20(String mchtCup20) {
		this.mchtCup20 = mchtCup20;
	}

	public String getMchtCup21() {
		return mchtCup21;
	}

	public void setMchtCup21(String mchtCup21) {
		this.mchtCup21 = mchtCup21;
	}

	public String getMchtCup22() {
		return mchtCup22;
	}

	public void setMchtCup22(String mchtCup22) {
		this.mchtCup22 = mchtCup22;
	}

	public String getMchtCup23() {
		return mchtCup23;
	}

	public void setMchtCup23(String mchtCup23) {
		this.mchtCup23 = mchtCup23;
	}

	public String getMchtCup24() {
		return mchtCup24;
	}

	public void setMchtCup24(String mchtCup24) {
		this.mchtCup24 = mchtCup24;
	}

	public String getMchtCup25() {
		return mchtCup25;
	}

	public void setMchtCup25(String mchtCup25) {
		this.mchtCup25 = mchtCup25;
	}

	public String getMchtCup26() {
		return mchtCup26;
	}

	public void setMchtCup26(String mchtCup26) {
		this.mchtCup26 = mchtCup26;
	}

	public String getMchtCup27() {
		return mchtCup27;
	}

	public void setMchtCup27(String mchtCup27) {
		this.mchtCup27 = mchtCup27;
	}

	public String getMchtCup28() {
		return mchtCup28;
	}

	public void setMchtCup28(String mchtCup28) {
		this.mchtCup28 = mchtCup28;
	}

	public String getMchtCup29() {
		return mchtCup29;
	}

	public void setMchtCup29(String mchtCup29) {
		this.mchtCup29 = mchtCup29;
	}

	public String getMchtCup30() {
		return mchtCup30;
	}

	public void setMchtCup30(String mchtCup30) {
		this.mchtCup30 = mchtCup30;
	}

	public String getMchtCup31() {
		return mchtCup31;
	}

	public void setMchtCup31(String mchtCup31) {
		this.mchtCup31 = mchtCup31;
	}

	public String getMchtCup32() {
		return mchtCup32;
	}

	public void setMchtCup32(String mchtCup32) {
		this.mchtCup32 = mchtCup32;
	}

	public String getMchtCup33() {
		return mchtCup33;
	}

	public void setMchtCup33(String mchtCup33) {
		this.mchtCup33 = mchtCup33;
	}

	public String getMchtCup34() {
		return mchtCup34;
	}

	public void setMchtCup34(String mchtCup34) {
		this.mchtCup34 = mchtCup34;
	}

	public String getMchtCup35() {
		return mchtCup35;
	}

	public void setMchtCup35(String mchtCup35) {
		this.mchtCup35 = mchtCup35;
	}

	public String getMchtCup36() {
		return mchtCup36;
	}

	public void setMchtCup36(String mchtCup36) {
		this.mchtCup36 = mchtCup36;
	}

	public String getMchtCup37() {
		return mchtCup37;
	}

	public void setMchtCup37(String mchtCup37) {
		this.mchtCup37 = mchtCup37;
	}

	public String getMchtCup38() {
		return mchtCup38;
	}

	public void setMchtCup38(String mchtCup38) {
		this.mchtCup38 = mchtCup38;
	}

	public String getMchtCup39() {
		return mchtCup39;
	}

	public void setMchtCup39(String mchtCup39) {
		this.mchtCup39 = mchtCup39;
	}

	public String getMchtCup40() {
		return mchtCup40;
	}

	public void setMchtCup40(String mchtCup40) {
		this.mchtCup40 = mchtCup40;
	}

	public String getMchtCup41() {
		return mchtCup41;
	}

	public void setMchtCup41(String mchtCup41) {
		this.mchtCup41 = mchtCup41;
	}

	public String getMchtCup42() {
		return mchtCup42;
	}

	public void setMchtCup42(String mchtCup42) {
		this.mchtCup42 = mchtCup42;
	}

	public String getMchtCup43() {
		return mchtCup43;
	}

	public void setMchtCup43(String mchtCup43) {
		this.mchtCup43 = mchtCup43;
	}

	public String getMchtCup44() {
		return mchtCup44;
	}

	public void setMchtCup44(String mchtCup44) {
		this.mchtCup44 = mchtCup44;
	}

	public String getMchtCup45() {
		return mchtCup45;
	}

	public void setMchtCup45(String mchtCup45) {
		this.mchtCup45 = mchtCup45;
	}

	public String getMchtCup46() {
		return mchtCup46;
	}

	public void setMchtCup46(String mchtCup46) {
		this.mchtCup46 = mchtCup46;
	}

	public String getMchtCup47() {
		return mchtCup47;
	}

	public void setMchtCup47(String mchtCup47) {
		this.mchtCup47 = mchtCup47;
	}

	public String getMchtCup48() {
		return mchtCup48;
	}

	public void setMchtCup48(String mchtCup48) {
		this.mchtCup48 = mchtCup48;
	}

	public String getMchtCup49() {
		return mchtCup49;
	}

	public void setMchtCup49(String mchtCup49) {
		this.mchtCup49 = mchtCup49;
	}

	public String getMchtCup50() {
		return mchtCup50;
	}

	public void setMchtCup50(String mchtCup50) {
		this.mchtCup50 = mchtCup50;
	}

	public String getMchtCup51() {
		return mchtCup51;
	}

	public void setMchtCup51(String mchtCup51) {
		this.mchtCup51 = mchtCup51;
	}

	public String getMchtCup52() {
		return mchtCup52;
	}

	public void setMchtCup52(String mchtCup52) {
		this.mchtCup52 = mchtCup52;
	}

	public String getMchtCup53() {
		return mchtCup53;
	}

	public void setMchtCup53(String mchtCup53) {
		this.mchtCup53 = mchtCup53;
	}

	public String getMchtCup54() {
		return mchtCup54;
	}

	public void setMchtCup54(String mchtCup54) {
		this.mchtCup54 = mchtCup54;
	}

	public String getMchtCup55() {
		return mchtCup55;
	}

	public void setMchtCup55(String mchtCup55) {
		this.mchtCup55 = mchtCup55;
	}

	public String getMchtCup56() {
		return mchtCup56;
	}

	public void setMchtCup56(String mchtCup56) {
		this.mchtCup56 = mchtCup56;
	}

	public String getMchtCup57() {
		return mchtCup57;
	}

	public void setMchtCup57(String mchtCup57) {
		this.mchtCup57 = mchtCup57;
	}

	public String getMchtCup58() {
		return mchtCup58;
	}

	public void setMchtCup58(String mchtCup58) {
		this.mchtCup58 = mchtCup58;
	}

	public String getMchtCup59() {
		return mchtCup59;
	}

	public void setMchtCup59(String mchtCup59) {
		this.mchtCup59 = mchtCup59;
	}

	public String getMchtCup60() {
		return mchtCup60;
	}

	public void setMchtCup60(String mchtCup60) {
		this.mchtCup60 = mchtCup60;
	}

	public String getMchtCup61() {
		return mchtCup61;
	}

	public void setMchtCup61(String mchtCup61) {
		this.mchtCup61 = mchtCup61;
	}

	public String getMchtCup62() {
		return mchtCup62;
	}

	public void setMchtCup62(String mchtCup62) {
		this.mchtCup62 = mchtCup62;
	}

	public String getMchtCup63() {
		return mchtCup63;
	}

	public void setMchtCup63(String mchtCup63) {
		this.mchtCup63 = mchtCup63;
	}

	public String getMchtCup64() {
		return mchtCup64;
	}

	public void setMchtCup64(String mchtCup64) {
		this.mchtCup64 = mchtCup64;
	}

	public String getMchtCup65() {
		return mchtCup65;
	}

	public void setMchtCup65(String mchtCup65) {
		this.mchtCup65 = mchtCup65;
	}

	public String getMchtCup66() {
		return mchtCup66;
	}

	public void setMchtCup66(String mchtCup66) {
		this.mchtCup66 = mchtCup66;
	}

	public String getMchtCup67() {
		return mchtCup67;
	}

	public void setMchtCup67(String mchtCup67) {
		this.mchtCup67 = mchtCup67;
	}

	public String getMchtCup68() {
		return mchtCup68;
	}

	public void setMchtCup68(String mchtCup68) {
		this.mchtCup68 = mchtCup68;
	}

	public String getMchtCup69() {
		return mchtCup69;
	}

	public void setMchtCup69(String mchtCup69) {
		this.mchtCup69 = mchtCup69;
	}

	public String getMchtCup70() {
		return mchtCup70;
	}

	public void setMchtCup70(String mchtCup70) {
		this.mchtCup70 = mchtCup70;
	}

	public String getMchtCup71() {
		return mchtCup71;
	}

	public void setMchtCup71(String mchtCup71) {
		this.mchtCup71 = mchtCup71;
	}

	public String getMchtCup72() {
		return mchtCup72;
	}

	public void setMchtCup72(String mchtCup72) {
		this.mchtCup72 = mchtCup72;
	}

	public String getMchtCup73() {
		return mchtCup73;
	}

	public void setMchtCup73(String mchtCup73) {
		this.mchtCup73 = mchtCup73;
	}

	public String getMchtCup74() {
		return mchtCup74;
	}

	public void setMchtCup74(String mchtCup74) {
		this.mchtCup74 = mchtCup74;
	}

	public String getMchtCup75() {
		return mchtCup75;
	}

	public void setMchtCup75(String mchtCup75) {
		this.mchtCup75 = mchtCup75;
	}

	public String getMchtCup76() {
		return mchtCup76;
	}

	public void setMchtCup76(String mchtCup76) {
		this.mchtCup76 = mchtCup76;
	}

	public String getMchtCup77() {
		return mchtCup77;
	}

	public void setMchtCup77(String mchtCup77) {
		this.mchtCup77 = mchtCup77;
	}

	public String getMchtCup78() {
		return mchtCup78;
	}

	public void setMchtCup78(String mchtCup78) {
		this.mchtCup78 = mchtCup78;
	}

	public String getMchtCup79() {
		return mchtCup79;
	}

	public void setMchtCup79(String mchtCup79) {
		this.mchtCup79 = mchtCup79;
	}

	public String getMchtCup80() {
		return mchtCup80;
	}

	public void setMchtCup80(String mchtCup80) {
		this.mchtCup80 = mchtCup80;
	}

	public String getMchtCup81() {
		return mchtCup81;
	}

	public void setMchtCup81(String mchtCup81) {
		this.mchtCup81 = mchtCup81;
	}

	public String getMchtCup82() {
		return mchtCup82;
	}

	public void setMchtCup82(String mchtCup82) {
		this.mchtCup82 = mchtCup82;
	}

	public String getMchtCup83() {
		return mchtCup83;
	}

	public void setMchtCup83(String mchtCup83) {
		this.mchtCup83 = mchtCup83;
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


	public String getMchtCup401() {
		return mchtCup401;
	}


	public void setMchtCup401(String mchtCup401) {
		this.mchtCup401 = mchtCup401;
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

	public ITblMchtBaseInfTmpDAO getTblMchtBaseInfTmpDAO() {
		return tblMchtBaseInfTmpDAO;
	}

	public void setTblMchtBaseInfTmpDAO(ITblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO) {
		this.tblMchtBaseInfTmpDAO = tblMchtBaseInfTmpDAO;
	}

}
