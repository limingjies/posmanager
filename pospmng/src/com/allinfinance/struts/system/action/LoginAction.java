/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-5-21       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2008 allinfinance, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai allinfinance Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with allinfinance.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.allinfinance.struts.system.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.allinfinance.common.CommonsConstants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.TblOprInfoConstants;
import com.allinfinance.dao.iface.base.TblOprInfoDAO;
import com.allinfinance.po.TblOprInfo;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.Encryption;
import com.allinfinance.system.util.SysParamUtil;

/**
 * Title:系统登录
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-5-21
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = -915297506148396706L;

	/**操作员编号*/
	private String oprid;
	/**操作员密码*/
	private String password;
	
	private String validateCode;
	
	protected HttpSession session = ServletActionContext.getRequest().getSession();
	
	/* (non-Javadoc)
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected String subExecute() throws Exception {
		
		TblOprInfoDAO tblOprInfoDAO = (TblOprInfoDAO) ContextUtil.getBean("OprInfoDAO");
		
		
		String code =  (String) getSessionAttribute("vC");
		if(code==null||!validateCode.equalsIgnoreCase(code)){
			writeErrorMsg("验证码输入错误！");
			return SUCCESS;
		}
		
		
		//判断ID是否为有效ID
		boolean authUser = false;
		try {
			String ids = SysParamUtil.getParam("AUTH_USER");
			if (StringUtil.isNull(ids)) {
				writeErrorMsg("登录失败，没有找到可登录的柜员ID。");
				return SUCCESS;
			} else {
				String[] id = ids.split(",");
				for (String tmp : id) {
					if (!StringUtil.isNull(tmp) && tmp.equals(oprid)) {
						authUser = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			log("登录失败，解析可登录柜员失败。");
			e.printStackTrace();
		}
//		if (!authUser) {
//			log("登录失败，柜员[" + oprid + "]不是可登录的柜员ID。");
//			writeErrorMsg("登录失败，柜员[" + oprid + "]不是可登录的柜员ID，请至统一认证平台登录。");
//			return SUCCESS;
//		}

		
		
		TblOprInfo tblOprInfo = tblOprInfoDAO.get(oprid);
		
		//判断操作员是否存在
		if(tblOprInfo == null) {
			log("登录失败，操作员不存在。编号[ " + oprid + " ]");
			writeErrorMsg("操作员号或密码错误，请重新输入!"); // 不能用数据库字段标志登录失败次数，因为无此操作员记录
			return SUCCESS;
		}
		
		// 将配置上次和本次登录状态
		tblOprInfo.setLastLoginTime(tblOprInfo.getCurrentLoginTime());
		tblOprInfo.setLastLoginIp(tblOprInfo.getCurrentLoginIp());
		tblOprInfo.setLastLoginStatus(tblOprInfo.getCurrentLoginStatus());
		tblOprInfo.setCurrentLoginTime(CommonFunction.getCurrentDateTime());
		tblOprInfo.setCurrentLoginIp(getRequest().getRemoteHost());
		
		// 判断操作员合法性
		if("1".equals(tblOprInfo.getOprSta().trim())) {
			log("登录失败，操作员被冻结。编号[ " + oprid + " ]");
			writeErrorMsg("该操作员已经被冻结，请与管理员联系。");
			return SUCCESS;
		}
		
		// 判断密码连续输入错误次数是否达到限定数次
		if(SysParamUtil.getParam(SysParamConstants.PWD_WR_TM_CONTINUE).equals(tblOprInfo.getPwdWrTmContinue().trim())) {
			String lockTime = CommonFunction.lockFlag(tblOprInfo.getLastLoginTime(), SysParamUtil.getParam(SysParamConstants.LOCK_TIME));
			// 判断用户是否正处于锁定时间内（由于密码连续错误次数大于限定数次被锁定一段时间）
			if("0".equals(lockTime.split("#")[0])){
				log("登录失败，密码连续错误次数大于限定数次被锁定，锁定剩余时间：" + lockTime.split("#")[1] +"，编号[ " + oprid + " ]");
				writeErrorMsg("登录失败，操作员被锁定，剩余时间：" + lockTime.split("#")[1]);
				return SUCCESS;
			}
		}
		
		//System.out.println("11:" + tblOprInfo.getPwdOutDate());
		// 判断密码是否过期
		if(Integer.parseInt(tblOprInfo.getPwdOutDate().trim()) <= Integer.parseInt(CommonFunction.getCurrentDate())) {
			log("登录失败，操作员密码过期。编号[ " + oprid + " ]");
			writeAlertMsg("您的密码已经过期，请进行修改。", TblOprInfoConstants.LOGIN_CODE_PWD_OUT);
			return SUCCESS;
		}
		
		// 判断操作员是否是新注册的
		if(TblOprInfoConstants.STATUS_INIT.equals(tblOprInfo.getOprSta().trim())) {
			log("登录失败，第一次登录修改密码!编号[ " + oprid + " ]，CODE:[ " + TblOprInfoConstants.LOGIN_CODE_INIT + " ]");
			writeAlertMsg("你是第一次登录，请修改密码!", TblOprInfoConstants.LOGIN_CODE_INIT);
			return SUCCESS;
		}
		
		//判断密码输入是否正确
		String oprPassword = StringUtils.trim(tblOprInfo.getOprPwd());
		password = StringUtils.trim(Encryption.encrypt(password));
		if(!oprPassword.equals(password)) {
			
//			if(!CommonFunction.getCurrentDate().equals(tblOprInfo.getPwdWrLastDt())) {
//				tblOprInfo.setPwdWrLastDt(CommonFunction.getCurrentDate());
//				tblOprInfo.setPwdWrTm("1");
//			} else {
//				tblOprInfo.setPwdWrTm(String.valueOf(Integer.parseInt(tblOprInfo.getPwdWrTm().trim()) + 1));
//			}
//			tblOprInfo.setPwdWrTmTotal(String.valueOf(Integer.parseInt(tblOprInfo.getPwdWrTmTotal().trim()) + 1));
//			
//			// 如果输入错误总次数大于5次或当天输入错误次数大于3次则冻结操作员
//			if(Integer.parseInt(tblOprInfo.getPwdWrTmTotal().trim()) >= 5 || 
//					(Integer.parseInt(tblOprInfo.getPwdWrTm().trim()) >= 3 && 
//							CommonFunction.getCurrentDate().equals(tblOprInfo.getPwdWrLastDt().trim()))) {
//				tblOprInfo.setOprSta("1");
//			}
//			tblOprInfoDAO.update(tblOprInfo);
			
			// 判断密码连续输入错误次数是否达到限定数次
			if(SysParamUtil.getParam(SysParamConstants.PWD_WR_TM_CONTINUE).equals(tblOprInfo.getPwdWrTmContinue().trim())) {
				tblOprInfo.setPwdWrTmContinue("1");
			} else {
				tblOprInfo.setPwdWrTmContinue(String.valueOf(Integer.parseInt(tblOprInfo.getPwdWrTmContinue().trim()) + 1));
			}
			// 登录状态[0-成功，1-失败]
			tblOprInfo.setCurrentLoginStatus("1");
			tblOprInfoDAO.update(tblOprInfo);
			
			if(SysParamUtil.getParam(SysParamConstants.PWD_WR_TM_CONTINUE).equals(tblOprInfo.getPwdWrTmContinue().trim())) {
				log("登录失败，密码错误!编号[ " + oprid + " ]");
				writeErrorMsg("此操作员登录失败次数超限，将被锁定" + SysParamUtil.getParam(SysParamConstants.LOCK_TIME) + "小时");
				return SUCCESS;
			}
			
			log("登录失败，密码错误。编号[ " + oprid + " ]");
			writeErrorMsg("操作员号或密码错误，请重新输入!");
			return SUCCESS;
		}
		
		// 每次登陆成功，初始化连续密码错误次数
		tblOprInfo.setPwdWrTmContinue("0");
		// 登录状态[0-成功，1-失败]
		tblOprInfo.setCurrentLoginStatus("0");
		tblOprInfoDAO.update(tblOprInfo);
		
		setSessionAttribute("oprId", oprid);
		
		
		//获取环境变量,是否单用户登录
		String userSingalFlag = SysParamUtil.getParam(SysParamConstants.SINGAL_USER);
		if(userSingalFlag.equals(CommonsConstants.SINGAL_USER)) {
			log("单用户登录模式");

			Map<String, String> userlistMap = (Map<String, String>) ServletActionContext.getServletContext().getAttribute(SysParamConstants.USER_LIST);
			if (userlistMap ==  null ) {
				userlistMap = new  HashMap<String, String>();
			}
			
			if(StringUtil.isNotEmpty(userlistMap.get(oprid))){
				userlistMap.remove(oprid);
			}
			
			userlistMap.put(oprid, session.getId());
			log(session.getId());
			
			ServletActionContext.getServletContext().setAttribute(SysParamConstants.USER_LIST, userlistMap);
			
		}else {
			log("多用户登录模式");
		}
		
		
		writeSuccessMsg("登录成功");
		return SUCCESS;
	}

	public String getOprid() {
		return oprid;
	}

	public void setOprid(String oprid) {
		this.oprid = oprid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the validateCode
	 */
	public String getValidateCode() {
		return validateCode;
	}

	/**
	 * @param validateCode the validateCode to set
	 */
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	
}
