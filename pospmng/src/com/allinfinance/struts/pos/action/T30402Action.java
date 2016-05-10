package com.allinfinance.struts.pos.action;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;

import oracle.net.ns.Communication;







import com.allinfinance.bo.term.T30401BO;
import com.allinfinance.bo.term.T30402BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.StringUtil;
import com.allinfinance.po.TblTermCupInfo;
import com.allinfinance.po.TblTermCupInfoTmp;
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
public class T30402Action extends BaseAction {
	

	T30401BO t30401BO = (T30401BO) ContextUtil.getBean("T30401BO");
	T30402BO t30402BO = (T30402BO) ContextUtil.getBean("T30402BO");

	
	private String termId;
	
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
		
		String sql = "SELECT upd_opr FROM tbl_term_cup_info_tmp WHERE term_id = '" + termId + "'";
		List<String> list = commQueryDAO.findBySQLQuery(sql);
		if (null != list && !list.isEmpty()) {
			if (!StringUtil.isNull(list.get(0))) {
				if(list.get(0).equals(operator.getOprId())){
					return "同一操作员不能审核！";
				}
			}
		}

		log("审核通过，终端编号：" + termId);
		
		TblTermCupInfoTmp tmp =new TblTermCupInfoTmp();
		
		tmp = t30401BO.get(termId);
		TblTermCupInfo info = t30402BO.get(termId);
		
		if(tmp == null){
			return "找不到该商户信息，请刷新后重试！";
		}
		
		String stateTmp = tmp.getTerm_state();
		String state = null;
		if(info != null){
			state = info.getTerm_state();
		}else {
			info = new TblTermCupInfo();
		}
		
		if("9".equals(stateTmp)){//新增待审核
			tmp.setTerm_state("1");//正常
			tmp.setCrt_date(CommonFunction.getCurrentDateTime());
		}else if("7".equals(stateTmp)){//修改待审核
			tmp.setTerm_state("1");//修改之前的状态
		}else if("5".equals(stateTmp)){//冻结待审核
			tmp.setTerm_state("2");//冻结
		}else if("3".equals(stateTmp)){//注销待审核
			tmp.setTerm_state("0");//注销
		}else if("H".equals(stateTmp)){//恢复待审核
			tmp.setTerm_state("1");//正常
		}else {
			return "不能操作的商户！请刷新页面后重试。";
		}
		
		tmp.setUpd_opr(operator.getOprId());
		tmp.setUpd_date(CommonFunction.getCurrentDateTime());
		
		BeanUtils.copyProperties(tmp, info);
		t30401BO.saveOrUpdate(tmp);
		t30402BO.saveOrUpdate(info);
		
		
		return Constants.SUCCESS_CODE;
	}
	

	/**
	 * 拒绝
	 * @return
	 */
	private String refuse() {		
		
		String sql = "SELECT upd_opr FROM tbl_term_cup_info_tmp WHERE term_id = '" + termId + "'";
		List<String> list = commQueryDAO.findBySQLQuery(sql);
		if (null != list && !list.isEmpty()) {
			if (!StringUtil.isNull(list.get(0))) {
				if(list.get(0).equals(operator.getOprId())){
					return "同一操作员不能审核！";
				}
			}
		}

		log("审核拒绝，终端编号：" + termId);
		
		TblTermCupInfoTmp tmp =new TblTermCupInfoTmp();
		tmp = t30401BO.get(termId);
		
		if(tmp == null){
			return "找不到该商户信息，请刷新后重试！";
		}
		
		String state = tmp.getTerm_state();
		if("9".equals(state)){//新增待审核
			tmp.setTerm_state("8");//新增拒绝
		}
		if("7".equals(state)){//修改待审核
			tmp.setTerm_state("6");//修改拒绝
		}
		if("5".equals(state)){//冻结待审核
			tmp.setTerm_state("1");//正常
		}
		if("3".equals(state)){//注销待审核
			tmp.setTerm_state("1");//正常
		}
		if("H".equals(state)){//恢复待审核
			tmp.setTerm_state("2");//冻结
		}
		
		tmp.setUpd_opr(operator.getOprId());
		tmp.setUpd_opr(CommonFunction.getCurrentDateTime());
		
		rspCode = t30401BO.saveOrUpdate(tmp);
		
		return Constants.SUCCESS_CODE;
	}


	public String getTermId() {
		return termId;
	}


	public void setTermId(String termId) {
		this.termId = termId;
	}


}