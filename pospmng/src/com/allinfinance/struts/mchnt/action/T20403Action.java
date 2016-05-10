package com.allinfinance.struts.mchnt.action;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;

import oracle.net.ns.Communication;







import com.allinfinance.bo.mchnt.T20401BO;
import com.allinfinance.bo.mchnt.T20402BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.TblMchntInfoConstants;
import com.allinfinance.po.TblMchtCupInfo;
import com.allinfinance.po.TblMchtCupInfoTmp;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

/**
 * Title:直联商户审核
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
public class T20403Action extends BaseAction {
	

	T20401BO t20401BO = (T20401BO) ContextUtil.getBean("T20401BO");
	T20402BO t20402BO = (T20402BO) ContextUtil.getBean("T20402BO");

	Operator opr = (Operator) ServletActionContext.getRequest()
	.getSession().getAttribute(Constants.OPERATOR_INFO);
	
	private String mchntId;
	
	@Override
	protected String subExecute() throws Exception {
		if("accept".equals(method)) {
			rspCode = accept();
		} else if("refuse".equals(method)) {
			rspCode = refuse();
		}
		
		return rspCode;
	}

	
	/**
	 * 通过
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private String accept() throws IllegalAccessException, InvocationTargetException {
		
		String sql = "SELECT upd_opr_id FROM tbl_mcht_cup_info_tmp WHERE MCHT_NO = '" + mchntId + "'";
		List<String> list = commQueryDAO.findBySQLQuery(sql);
		if (null != list && !list.isEmpty()) {
			if (!StringUtil.isNull(list.get(0))) {
				if(list.get(0).equals(operator.getOprId())){
					return "同一操作员不能审核！";
				}
			}
		}

		log("审核通过，商户编号：" + mchntId);
		
		TblMchtCupInfoTmp tmp =new TblMchtCupInfoTmp();
		
		tmp = t20401BO.get(mchntId);
		TblMchtCupInfo info = t20402BO.get(mchntId);
		
		if(tmp == null){
			return "找不到该商户信息，请刷新后重试！";
		}
		
		String stateTmp = tmp.getMcht_status();
		String state = null;
		if(info == null){
			info = new TblMchtCupInfo();
		}
		
		if("9".equals(stateTmp)){//新增待审核
			tmp.setMcht_status("1");//正常
			tmp.setCrt_ts(CommonFunction.getCurrentDateTime());
		}else if("7".equals(stateTmp)){//修改待审核
			tmp.setMcht_status("1");//修改之前的状态
		}else if("5".equals(stateTmp)){//冻结待审核
			tmp.setMcht_status("2");//冻结
		}else if("3".equals(stateTmp)){//注销待审核
			tmp.setMcht_status("0");//注销
		}else if("H".equals(stateTmp)){//恢复待审核
			tmp.setMcht_status("1");//正常
		}else {
			return "不能操作的商户！请刷新页面后重试。";
		}
		
		tmp.setUpd_opr_id(operator.getOprId());
		tmp.setUpd_ts(CommonFunction.getCurrentDateTime());
		
		BeanUtils.copyProperties(tmp, info);
		t20401BO.saveOrUpdate(tmp);
		t20402BO.saveOrUpdate(info);
		
		
		return Constants.SUCCESS_CODE;
	}
	

	/**
	 * 拒绝
	 * @return
	 */
	private String refuse() {		
		String sql = "SELECT upd_opr_id FROM tbl_mcht_cup_info_tmp WHERE MCHT_NO = '" + mchntId + "'";
		List<String> list = commQueryDAO.findBySQLQuery(sql);
		if (null != list && !list.isEmpty()) {
			if (!StringUtil.isNull(list.get(0))) {
				if(list.get(0).equals(operator.getOprId())){
					return "同一操作员不能审核！";
				}
			}
		}
		
		log("审核拒绝，商户编号：" + mchntId);
		
		TblMchtCupInfoTmp tmp =new TblMchtCupInfoTmp();
		tmp = t20401BO.get(mchntId);
		
		if(tmp == null){
			return "找不到该商户信息，请刷新后重试！";
		}
		
		String state = tmp.getMcht_status();
		if("9".equals(state)){//新增待审核
			tmp.setMcht_status("8");//新增拒绝
		}
		if("7".equals(state)){//修改待审核
			tmp.setMcht_status("6");//修改拒绝
		}
		if("5".equals(state)){//冻结待审核
			tmp.setMcht_status("1");//正常
		}
		if("3".equals(state)){//注销待审核
			tmp.setMcht_status("1");//正常
		}
		if("H".equals(state)){//恢复待审核
			tmp.setMcht_status("2");//冻结
		}
		
		tmp.setUpd_opr_id(operator.getOprId());
		tmp.setUpd_ts(CommonFunction.getCurrentDateTime());
		
		rspCode = t20401BO.saveOrUpdate(tmp);
		
		return Constants.SUCCESS_CODE;
	}


	public String getMchntId() {
		return mchntId;
	}

	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}
	

}
