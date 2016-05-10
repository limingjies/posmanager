package com.allinfinance.struts.pos.action;



import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.allinfinance.bo.mchnt.T20402BO;
import com.allinfinance.bo.term.T30401BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.po.TblMchtCupInfo;
import com.allinfinance.po.TblTermCupInfoTmp;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.BeanUtils;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.SysParamUtil;

/**
 * Title:终端维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-17
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @version 1.0
 */
public class T30401Action extends BaseAction {
	
	T30401BO t30401BO = (T30401BO) ContextUtil.getBean("T30401BO");
	
	T20402BO t20402BO = (T20402BO) ContextUtil.getBean("T20402BO");

	@Override
	protected String subExecute() throws Exception {
		if("add".equals(method)) {
			rspCode = add();
		} else if("update".equals(method)) {
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
	
	//新增
	private String add() throws IllegalAccessException, InvocationTargetException {
				
		TblTermCupInfoTmp tmp = new TblTermCupInfoTmp();
		TblMchtCupInfo mcht = new TblMchtCupInfo();
		
		mcht = t20402BO.get(mcht_no);
		if(mcht == null){
			return "正式表找不到该商户信息！";
		}
		
		String idStart = "44" + mcht.getArea_no().substring(2, 4);
		String termId = this.getTermId(idStart);
		
		if(t30401BO.get(termId) != null){
			return "该终端号已存在";
		}
		log("新增直联终端：" + termId);
		tmp.setTerm_id(termId);
		tmp.setAddress(address);
		tmp.setCall_type(call_type);
		tmp.setClose_time(close_time);
		tmp.setConnect_mode(connect_mode);
		tmp.setCountry_code(country_code);
		tmp.setCurrcy_code_trans(currcy_code_trans);
		tmp.setEmv_flag(emv_flag);
		tmp.setEnc_key_mode(enc_key_mode);
		tmp.setIC_flag(IC_flag);
		tmp.setIC_para_flag(IC_para_flag);
		tmp.setIn_inst_id(in_inst_id);
		tmp.setInst_id(in_inst_id);//同in_inst_id
		tmp.setKey_index(key_index);
		tmp.setKey_length(key_length);
		tmp.setMac_alg(mac_alg);
		tmp.setMak_length(mak_length);
		tmp.setMcht_no(mcht_no);
		tmp.setName(name);
		tmp.setOpen_time(open_time);
		tmp.setOut_flag(out_flag);
		tmp.setPara_down_flag(para_down_flag);
		tmp.setPik_length(pik_length);
		tmp.setPub_key_flag(pub_key_flag);
		tmp.setTel_phone(tel_phone);
		tmp.setTerm_call_num(term_call_num);
		tmp.setTerm_enc_key_1(term_enc_key_1);
		tmp.setTerm_enc_type(term_enc_type);
		tmp.setTerm_share(term_share);
		tmp.setTerm_state("9");//新增待审核
		tmp.setTerm_trans_key_1(term_trans_key_1);
		tmp.setTerm_type(term_type);
		tmp.setTms_down_flag(tms_down_flag);
		tmp.setTrack_key_len(track_key_len);
		tmp.setTrans_key_mode(trans_key_mode);
		
		
		tmp.setCrt_date(CommonFunction.getCurrentDateTime());
		tmp.setCrt_opr(operator.getOprId());
		tmp.setUpd_opr(operator.getOprId());
		
		rspCode = t30401BO.add(tmp);
		return Constants.SUCCESS_CODE_CUSTOMIZE + "新增终端信息成功，终端编号[" + termId + "]";
	}

	
	//修改
	private String update() throws IllegalAccessException, InvocationTargetException {
		try{
			TblTermCupInfoTmp tmp = new TblTermCupInfoTmp();
			
			tmp = t30401BO.get(term_id);
			if(tmp == null){
				return "找不到该终端信息，请刷新后再试！";
			}
			
			log("修改直联终端：" + term_id);
			
			String state = tmp.getTerm_state();
			String crt_id = tmp.getCrt_opr();
			String crt_ts = tmp.getCrt_date();
			
			tmp.setTerm_id(term_id);
			tmp.setAddress(addressE);
			tmp.setCall_type(call_typeE);
			tmp.setClose_time(close_timeE);
			tmp.setConnect_mode(connect_modeE);
			tmp.setCountry_code(country_codeE);
			tmp.setCurrcy_code_trans(currcy_code_transE);
			tmp.setEmv_flag(emv_flagE);
			tmp.setEnc_key_mode(enc_key_modeE);
			tmp.setIC_flag(IC_flagE);
			tmp.setIC_para_flag(IC_para_flagE);
			tmp.setIn_inst_id(in_inst_idE);
			tmp.setInst_id(in_inst_idE);//同in_inst_id
			tmp.setKey_index(key_indexE);
			tmp.setKey_length(key_lengthE);
			tmp.setMac_alg(mac_algE);
			tmp.setMak_length(mak_lengthE);
			tmp.setMcht_no(mcht_noE);
			tmp.setName(nameE);
			tmp.setOpen_time(open_timeE);
			tmp.setOut_flag(out_flagE);
			tmp.setPara_down_flag(para_down_flagE);
			tmp.setPik_length(pik_lengthE);
			tmp.setPub_key_flag(pub_key_flagE);
			tmp.setTel_phone(tel_phoneE);
			tmp.setTerm_call_num(term_call_numE);
			tmp.setTerm_enc_key_1(term_enc_key_1E);
			tmp.setTerm_enc_type(term_enc_typeE);
			tmp.setTerm_share(term_shareE);
			tmp.setTerm_trans_key_1(term_trans_key_1E);
			tmp.setTerm_type(term_typeE);
			tmp.setTms_down_flag(tms_down_flagE);
			tmp.setTrack_key_len(track_key_lenE);
			tmp.setTrans_key_mode(trans_key_modeE);
			
			if("9".equals(state) || "8".equals(state)){//9-新增待审核，8-新增拒绝
				tmp.setTerm_state("9");//新增待审核
			}else{
				tmp.setTerm_state("7");//修改待审核
			}
			
			
			tmp.setCrt_opr(crt_id);
			tmp.setCrt_date(crt_ts);
			tmp.setUpd_opr(operator.getOprId());
			tmp.setUpd_date(CommonFunction.getCurrentDateTime());
			
			rspCode = t30401BO.saveOrUpdate(tmp);
			
			
			if (Constants.SUCCESS_CODE.equals(rspCode)) {
				return Constants.SUCCESS_CODE_CUSTOMIZE + "修改终端信息成功，终端编号[" + term_id + "]";
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
		TblTermCupInfoTmp tmp =new TblTermCupInfoTmp();
		
		tmp = t30401BO.get(term_id);
		String state = null;
		if(tmp == null){
			return "找不到该商户信息，请刷新后重试！";
		}else {
			state = tmp.getTerm_state();
			if(!"1".equals(state)){
				return "不能操作的状态";
			}
		}
		
		log("冻结直联终端：" + term_id);
		
		tmp.setTerm_state("5");//冻结待审核
		tmp.setUpd_opr(operator.getOprId());
		tmp.setUpd_date(CommonFunction.getCurrentDateTime());
		
		rspCode = t30401BO.saveOrUpdate(tmp);
		
		return Constants.SUCCESS_CODE;
	}

	//恢复
	private String recover() {
		TblTermCupInfoTmp tmp =new TblTermCupInfoTmp();
		
		tmp = t30401BO.get(term_id);
		String state = null;
		if(tmp == null){
			return "找不到该商户信息，请刷新后重试！";
		}else {
			state = tmp.getTerm_state();
			if(!"2".equals(state)){
				return "不能操作的状态";
			}
		}
		
		log("恢复直联终端：" + term_id);
		
		tmp.setTerm_state("H");//恢复待审核
		tmp.setUpd_opr(operator.getOprId());
		tmp.setUpd_date(CommonFunction.getCurrentDateTime());
		
		rspCode = t30401BO.saveOrUpdate(tmp);
		
		return Constants.SUCCESS_CODE;
	}
	
	//注销
	private String del() {
		TblTermCupInfoTmp tmp =new TblTermCupInfoTmp();
		
		tmp = t30401BO.get(term_id);
		String state = null;
		if(tmp == null){
			return "找不到该商户信息，请刷新后重试！";
		}else {
			state = tmp.getTerm_state();
			if(!"1".equals(state)){
				return "不能操作的状态";
			}
		}
		
		log("注销直联终端：" + term_id);
		
		tmp.setTerm_state("3");//注销待审核
		tmp.setUpd_opr(operator.getOprId());
		tmp.setUpd_date(CommonFunction.getCurrentDateTime());
		
		rspCode = t30401BO.saveOrUpdate(tmp);
		
		return Constants.SUCCESS_CODE;
	}
	
	
	
	//构造终端号
	public String getTermId(String idStart) {
		if(idStart == null || idStart.trim().equals("")){
			return Constants.FAILURE_CODE;
		}
		 
		//初始赋值0001
		String sql = "select count(*) from tbl_term_cup_info_tmp where trim(term_id) = '" + idStart + "0001" + "'" ;
		BigDecimal init = (BigDecimal) commQueryDAO.findBySQLQuery(sql).get(0);
		if (init.intValue() == 0) {
			return idStart + "0001";
		}
		
		String min = "select min(substr(term_id,5,8) + 1) from tbl_term_cup_info_tmp where (substr(term_id,5,8) + 1) not in " +
		  "(select substr(term_id,5,8) + 0 from tbl_term_cup_info_tmp where substr(term_id,1,4) = '" + idStart + "') ";
		List<BigDecimal> resultSet = commQueryDAO.findBySQLQuery(min);
		
		int idEnd = 1;
		if(resultSet.get(0) == null) {
			return idStart + "0001";
		}else{
			idEnd = resultSet.get(0).intValue();
		}
			
		if(idEnd > 9999){
			return "终端数量已超过9999！";
		}
			
		return idStart + CommonFunction.fillString(idEnd + "", '0', 4, false);
	}

	private String termId;
	private String term_id;
	private String mcht_no;
	private String in_inst_id;
	private String inst_id;
	private String term_type ;
	private String term_state;
	private String out_flag;
	private String IC_flag;
	private String call_type;
	private String currcy_code_trans;
	private String key_index;
	private String enc_key_mode;
	private String trans_key_mode;
	private String term_enc_type;
	private String term_enc_key_1;
	private String term_trans_key_1;
	private String open_time;
	private String close_time;
	private String para_down_flag;
	private String tms_down_flag;
	private String pub_key_flag;
	private String IC_para_flag;
	private String emv_flag;
	private String key_length;
	private String pik_length;
	private String mak_length;
	private String mac_alg;
	private String country_code;
	private String connect_mode;
	private String track_key_len;
	private String term_share;
	private String term_call_num;
	private String address;
	private String name;
	private String tel_phone;
	
	private String mcht_noE;
	private String in_inst_idE;
	private String inst_idE;
	private String term_typeE;
	private String term_stateE;
	private String out_flagE;
	private String IC_flagE;
	private String call_typeE;
	private String currcy_code_transE;
	private String key_indexE;
	private String enc_key_modeE;
	private String trans_key_modeE;
	private String term_enc_typeE;
	private String term_enc_key_1E;
	private String term_trans_key_1E;
	private String open_timeE;
	private String close_timeE;
	private String para_down_flagE;
	private String tms_down_flagE;
	private String pub_key_flagE;
	private String IC_para_flagE;
	private String emv_flagE;
	private String key_lengthE;
	private String pik_lengthE;
	private String mak_lengthE;
	private String mac_algE;
	private String country_codeE;
	private String connect_modeE;
	private String track_key_lenE;
	private String term_shareE;
	private String term_call_numE;
	private String addressE;
	private String nameE;
	private String tel_phoneE;
	
	public String getTerm_id() {
		return term_id;
	}

	public void setTerm_id(String term_id) {
		this.term_id = term_id;
	}

	public String getMcht_no() {
		return mcht_no;
	}

	public void setMcht_no(String mcht_no) {
		this.mcht_no = mcht_no;
	}

	public String getIn_inst_id() {
		return in_inst_id;
	}

	public void setIn_inst_id(String in_inst_id) {
		this.in_inst_id = in_inst_id;
	}

	public String getInst_id() {
		return inst_id;
	}

	public void setInst_id(String inst_id) {
		this.inst_id = inst_id;
	}

	public String getTerm_type() {
		return term_type;
	}

	public void setTerm_type(String term_type) {
		this.term_type = term_type;
	}

	public String getTerm_state() {
		return term_state;
	}

	public void setTerm_state(String term_state) {
		this.term_state = term_state;
	}

	public String getOut_flag() {
		return out_flag;
	}

	public void setOut_flag(String out_flag) {
		this.out_flag = out_flag;
	}

	public String getIC_flag() {
		return IC_flag;
	}

	public void setIC_flag(String ic_flag) {
		IC_flag = ic_flag;
	}

	public String getCall_type() {
		return call_type;
	}

	public void setCall_type(String call_type) {
		this.call_type = call_type;
	}

	public String getCurrcy_code_trans() {
		return currcy_code_trans;
	}

	public void setCurrcy_code_trans(String currcy_code_trans) {
		this.currcy_code_trans = currcy_code_trans;
	}

	public String getKey_index() {
		return key_index;
	}

	public void setKey_index(String key_index) {
		this.key_index = key_index;
	}

	public String getEnc_key_mode() {
		return enc_key_mode;
	}

	public void setEnc_key_mode(String enc_key_mode) {
		this.enc_key_mode = enc_key_mode;
	}

	public String getTrans_key_mode() {
		return trans_key_mode;
	}

	public void setTrans_key_mode(String trans_key_mode) {
		this.trans_key_mode = trans_key_mode;
	}

	public String getTerm_enc_type() {
		return term_enc_type;
	}

	public void setTerm_enc_type(String term_enc_type) {
		this.term_enc_type = term_enc_type;
	}

	public String getTerm_enc_key_1() {
		return term_enc_key_1;
	}

	public void setTerm_enc_key_1(String term_enc_key_1) {
		this.term_enc_key_1 = term_enc_key_1;
	}

	public String getTerm_trans_key_1() {
		return term_trans_key_1;
	}

	public void setTerm_trans_key_1(String term_trans_key_1) {
		this.term_trans_key_1 = term_trans_key_1;
	}

	public String getOpen_time() {
		return open_time;
	}

	public void setOpen_time(String open_time) {
		this.open_time = open_time;
	}

	public String getClose_time() {
		return close_time;
	}

	public void setClose_time(String close_time) {
		this.close_time = close_time;
	}

	public String getPara_down_flag() {
		return para_down_flag;
	}

	public void setPara_down_flag(String para_down_flag) {
		this.para_down_flag = para_down_flag;
	}

	public String getTms_down_flag() {
		return tms_down_flag;
	}

	public void setTms_down_flag(String tms_down_flag) {
		this.tms_down_flag = tms_down_flag;
	}

	public String getPub_key_flag() {
		return pub_key_flag;
	}

	public void setPub_key_flag(String pub_key_flag) {
		this.pub_key_flag = pub_key_flag;
	}

	public String getIC_para_flag() {
		return IC_para_flag;
	}

	public void setIC_para_flag(String ic_para_flag) {
		IC_para_flag = ic_para_flag;
	}

	public String getEmv_flag() {
		return emv_flag;
	}

	public void setEmv_flag(String emv_flag) {
		this.emv_flag = emv_flag;
	}

	public String getKey_length() {
		return key_length;
	}

	public void setKey_length(String key_length) {
		this.key_length = key_length;
	}

	public String getPik_length() {
		return pik_length;
	}

	public void setPik_length(String pik_length) {
		this.pik_length = pik_length;
	}

	public String getMak_length() {
		return mak_length;
	}

	public void setMak_length(String mak_length) {
		this.mak_length = mak_length;
	}

	public String getMac_alg() {
		return mac_alg;
	}

	public void setMac_alg(String mac_alg) {
		this.mac_alg = mac_alg;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getConnect_mode() {
		return connect_mode;
	}

	public void setConnect_mode(String connect_mode) {
		this.connect_mode = connect_mode;
	}

	public String getTrack_key_len() {
		return track_key_len;
	}

	public void setTrack_key_len(String track_key_len) {
		this.track_key_len = track_key_len;
	}

	public String getTerm_share() {
		return term_share;
	}

	public void setTerm_share(String term_share) {
		this.term_share = term_share;
	}

	public String getTerm_call_num() {
		return term_call_num;
	}

	public void setTerm_call_num(String term_call_num) {
		this.term_call_num = term_call_num;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel_phone() {
		return tel_phone;
	}

	public void setTel_phone(String tel_phone) {
		this.tel_phone = tel_phone;
	}

	//edit
	public String getMcht_noE() {
		return mcht_noE;
	}

	public void setMcht_noE(String mcht_noE) {
		this.mcht_noE = mcht_noE;
	}

	public String getIn_inst_idE() {
		return in_inst_idE;
	}

	public void setIn_inst_idE(String in_inst_idE) {
		this.in_inst_idE = in_inst_idE;
	}

	public String getInst_idE() {
		return inst_idE;
	}

	public void setInst_idE(String inst_idE) {
		this.inst_idE = inst_idE;
	}

	public String getTerm_typeE() {
		return term_typeE;
	}

	public void setTerm_typeE(String term_typeE) {
		this.term_typeE = term_typeE;
	}

	public String getTerm_stateE() {
		return term_stateE;
	}

	public void setTerm_stateE(String term_stateE) {
		this.term_stateE = term_stateE;
	}

	public String getOut_flagE() {
		return out_flagE;
	}

	public void setOut_flagE(String out_flagE) {
		this.out_flagE = out_flagE;
	}

	public String getIC_flagE() {
		return IC_flagE;
	}

	public void setIC_flagE(String ic_flage) {
		IC_flagE = ic_flage;
	}

	public String getCall_typeE() {
		return call_typeE;
	}

	public void setCall_typeE(String call_typeE) {
		this.call_typeE = call_typeE;
	}

	public String getCurrcy_code_transE() {
		return currcy_code_transE;
	}

	public void setCurrcy_code_transE(String currcy_code_transE) {
		this.currcy_code_transE = currcy_code_transE;
	}

	public String getKey_indexE() {
		return key_indexE;
	}

	public void setKey_indexE(String key_indexE) {
		this.key_indexE = key_indexE;
	}

	public String getEnc_key_modeE() {
		return enc_key_modeE;
	}

	public void setEnc_key_modeE(String enc_key_modeE) {
		this.enc_key_modeE = enc_key_modeE;
	}

	public String getTrans_key_modeE() {
		return trans_key_modeE;
	}

	public void setTrans_key_modeE(String trans_key_modeE) {
		this.trans_key_modeE = trans_key_modeE;
	}

	public String getTerm_enc_typeE() {
		return term_enc_typeE;
	}

	public void setTerm_enc_typeE(String term_enc_typeE) {
		this.term_enc_typeE = term_enc_typeE;
	}

	public String getTerm_enc_key_1E() {
		return term_enc_key_1E;
	}

	public void setTerm_enc_key_1E(String term_enc_key_1E) {
		this.term_enc_key_1E = term_enc_key_1E;
	}

	public String getTerm_trans_key_1E() {
		return term_trans_key_1E;
	}

	public void setTerm_trans_key_1E(String term_trans_key_1E) {
		this.term_trans_key_1E = term_trans_key_1E;
	}

	public String getOpen_timeE() {
		return open_timeE;
	}

	public void setOpen_timeE(String open_timeE) {
		this.open_timeE = open_timeE;
	}

	public String getClose_timeE() {
		return close_timeE;
	}

	public void setClose_timeE(String close_timeE) {
		this.close_timeE = close_timeE;
	}

	public String getPara_down_flagE() {
		return para_down_flagE;
	}

	public void setPara_down_flagE(String para_down_flagE) {
		this.para_down_flagE = para_down_flagE;
	}

	public String getTms_down_flagE() {
		return tms_down_flagE;
	}

	public void setTms_down_flagE(String tms_down_flagE) {
		this.tms_down_flagE = tms_down_flagE;
	}

	public String getPub_key_flagE() {
		return pub_key_flagE;
	}

	public void setPub_key_flagE(String pub_key_flagE) {
		this.pub_key_flagE = pub_key_flagE;
	}

	public String getIC_para_flagE() {
		return IC_para_flagE;
	}

	public void setIC_para_flagE(String ic_para_flage) {
		IC_para_flagE = ic_para_flage;
	}

	public String getEmv_flagE() {
		return emv_flagE;
	}

	public void setEmv_flagE(String emv_flagE) {
		this.emv_flagE = emv_flagE;
	}

	public String getKey_lengthE() {
		return key_lengthE;
	}

	public void setKey_lengthE(String key_lengthE) {
		this.key_lengthE = key_lengthE;
	}

	public String getPik_lengthE() {
		return pik_lengthE;
	}

	public void setPik_lengthE(String pik_lengthE) {
		this.pik_lengthE = pik_lengthE;
	}

	public String getMak_lengthE() {
		return mak_lengthE;
	}

	public void setMak_lengthE(String mak_lengthE) {
		this.mak_lengthE = mak_lengthE;
	}

	public String getMac_algE() {
		return mac_algE;
	}

	public void setMac_algE(String mac_algE) {
		this.mac_algE = mac_algE;
	}

	public String getCountry_codeE() {
		return country_codeE;
	}

	public void setCountry_codeE(String country_codeE) {
		this.country_codeE = country_codeE;
	}

	public String getConnect_modeE() {
		return connect_modeE;
	}

	public void setConnect_modeE(String connect_modeE) {
		this.connect_modeE = connect_modeE;
	}

	public String getTrack_key_lenE() {
		return track_key_lenE;
	}

	public void setTrack_key_lenE(String track_key_lenE) {
		this.track_key_lenE = track_key_lenE;
	}

	public String getTerm_shareE() {
		return term_shareE;
	}

	public void setTerm_shareE(String term_shareE) {
		this.term_shareE = term_shareE;
	}

	public String getTerm_call_numE() {
		return term_call_numE;
	}

	public void setTerm_call_numE(String term_call_numE) {
		this.term_call_numE = term_call_numE;
	}

	public String getAddressE() {
		return addressE;
	}

	public void setAddressE(String addressE) {
		this.addressE = addressE;
	}

	public String getNameE() {
		return nameE;
	}

	public void setNameE(String nameE) {
		this.nameE = nameE;
	}

	public String getTel_phoneE() {
		return tel_phoneE;
	}

	public void setTel_phoneE(String tel_phoneE) {
		this.tel_phoneE = tel_phoneE;
	}
	
	
}