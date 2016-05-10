
package com.allinfinance.struts.settle.action;


import java.io.UnsupportedEncodingException;

import org.apache.struts2.ServletActionContext;

import com.allinfinance.bo.settle.T80603BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.socket.BatchSocketClient;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.SysParamUtil;

/**
 * Title: 
 * 
 * File: T4020501Action.java
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-8-10
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author caotz
 * 
 * @version 1.0
 */
public class T80603Action extends BaseAction {
	
	private static final long serialVersionUID = 1L;
//	private static final String BAT_BATCH_RUN_CMD = "5";
//	private static final String BAT_ONLINE_TASK_CMD = "10";
//	private static final String BAT_RUN_CMD = "2";
//	private static final String BAT_LOAD_NODE_CMD = "8";
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		if ("one".equals(method)) {
			return start();
		} else if ("run".equals(method)) {
			return run();
		} else if ("onl".equals(method)) {
			return onl();
		} else if ("rest".equals(getMethod())) {
			return rest();
		} else if ("resetAsn".equals(method)) {
			return resetAsn();
		} else if ("resetBat".equals(method)) {
			return resetBat();
		} else if ("shouQuan".equals(method)) {
			return isPclFuhe(ApproveTlr, ApproveTlrPass);
		} else if ("shouQuanOne".equals(method)) {
			return isPclFuhe(ApproveTlrOne, ApproveTlrPassOne);
		} else if ("shouQuanOnl".equals(method)) {
			return isPclFuhe(ApproveTlrOnl, ApproveTlrPassOnl);
		} else if ("shouQuanRun".equals(method)) {
			return isPclFuhe(ApproveTlrRun, ApproveTlrPassRun);
		} else if ("shouQuanRest".equals(method)) {
			return isPclFuhe(ApproveTlrRest, ApproveTlrPassRest);
		}
		return rspCode;
	}

	/**
	 * 启动单个任务
	 * @throws UnsupportedEncodingException 
	 * @date 2011-03-07
	 */
	public String start() throws UnsupportedEncodingException {
		String ip = getBatchMainIp();
		String port = getBatchMainPort();
		if("0".equals(ip)) {
			rspCode = "未配置环境变量BATCH_MAIN_IP！";
		} else if("0".equals(port)) {
			rspCode = "未配置环境变量BATCH_MAIN_PORT！";
		} else {
			BatchSocketClient socketClient = new BatchSocketClient();
			String connectState = socketClient.sendMsg(ip, Integer.parseInt(port), socketClient.start(startBatIdOne, startBatDateOne.replace("-", "")));
			if (connectState == null) {
				rspCode = "连接主控节点IP["+ip+"]端口["+port+"]失败！";
			} else if ("".equals(connectState.trim())){
				rspCode = "启动失败，无状态返回！";
			} else if (Constants.SUCCESS_CODE.equals(CommonFunction.mySubstr(connectState,1,4-1))){
				rspCode = Constants.SUCCESS_CODE;
			} else {
				rspCode = CommonFunction.mySubstr(connectState,4,256+4-1).trim();
			}
		}
		return rspCode;
	}

	/**
	 * 启动全部任务
	 * @throws UnsupportedEncodingException 
	 * @date 2011-03-07
	 */
	public String run() throws UnsupportedEncodingException {
		String ip = getBatchMainIp();
		String port = getBatchMainPort();
		if("0".equals(ip)) {
			rspCode = "未配置环境变量BATCH_MAIN_IP！";
		} else if("0".equals(port)) {
			rspCode = "未配置环境变量BATCH_MAIN_PORT！";
		} else {
			BatchSocketClient socketClient = new BatchSocketClient();
			String connectState = socketClient.sendMsg(ip, Integer.parseInt(port), socketClient.run(startBatId3,grpIdRun, startBatDateTime));
			if (connectState == null) {
				rspCode = "连接主控节点IP["+ip+"]端口["+port+"]失败！";
			} else if ("".equals(connectState.trim())){
				rspCode = "启动失败，无状态返回！";
			} else if (Constants.SUCCESS_CODE.equals(CommonFunction.mySubstr(connectState,1,4-1))){
				rspCode = Constants.SUCCESS_CODE;
			} else {
				rspCode = CommonFunction.mySubstr(connectState, 4, 256+4-1).trim();	//第三字节为应答码的结束符
			}
		}
		return rspCode;
	}

	/**
	 * 启动批量任务
	 * 
	 * @return
	 * @throws Exception
	 * @date 2011-03-07
	 * @author Laoboo
	 */
	public String onl() {
		String ip = getBatchMainIp();
		String port = getBatchMainPort();
		if("0".equals(ip)) {
			rspCode = "未配置环境变量BATCH_MAIN_IP！";
		} else if("0".equals(port)) {
			rspCode = "未配置环境变量BATCH_MAIN_PORT！";
		} else {
			BatchSocketClient socketClient = new BatchSocketClient();
			String connectState = socketClient.sendMsg(ip, Integer.parseInt(port), socketClient.onl());
			if (connectState == null) {
				rspCode = "连接主控节点IP["+ip+"]端口["+port+"]失败！";
			} else if ("21".equals(connectState.substring(0,2))){
				rspCode = Constants.SUCCESS_CODE;
			} else {
				rspCode = connectState.substring(3, connectState.trim().length());
			}
		}
		return rspCode;
	}
	
	/**
	 * 重置批量
	 * @date 2011-03-07
	 */
	public String rest() {
		T80603BO t0601003bo = (T80603BO) ContextUtil.getBean("T80603BO");
		try {
			rspCode = t0601003bo.update(restFlag,restBatId,restGrpId);
		} catch (Exception e) {
			e.printStackTrace();
			rspCode = "重置批量失败！";
		}
		return rspCode;
	}
	
	/**
	 * 获取服务IP
	 * 
	 * @return
	 * @throws Exception
	 * @date 2011-03-07
	 * @author Laoboo
	 */
	public String getBatchMainIp() {
//		String sql = "SELECT PARAM_VALUE FROM TBL_PARAM_INFO WHERE 1=1 AND PARAM_MARK ='BATCH_MAIN_IP'";
//		return CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql);
		return SysParamUtil.getParam(SysParamConstants.SOCKET_IP);
		
	}

	/**
	 * 获取服务端口
	 * 
	 * @return
	 * @throws Exception
	 * @date 2011-03-07
	 * @author Laoboo
	 */
	public String getBatchMainPort() {
//		String sql = "SELECT PARAM_VALUE FROM TBL_PARAM_INFO WHERE 1=1 AND PARAM_MARK='BATCH_MAIN_PORT'";
//		return CommonFunction.getCommQueryDAO().findCountBySQLQuery(sql);
		return SysParamUtil.getParam(SysParamConstants.SOCKET_BATCH_PORT);
	}
	
	/**
	 * 重置
	 * 
	 * @return
	 * @throws Exception
	 */
	private String resetBat() throws Exception {
		T80603BO t0601003bo = (T80603BO) ContextUtil.getBean("T80603BO");
		String id = ServletActionContext.getRequest().getParameter("id");

		t0601003bo.resetBat(id);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 重置
	 * 
	 * @return
	 * @throws Exception
	 */
	private String resetAsn() throws Exception {
		T80603BO t0601003bo = (T80603BO) ContextUtil.getBean("T80603BO");
		String id2 = ServletActionContext.getRequest().getParameter("id2");
		t0601003bo.resetAsn(id2);
		return Constants.SUCCESS_CODE;
	}

	private String startBatId2;
	private String startBatDate2;
	private String startBatId3;
	private String startBatDateTime = CommonFunction.getCurrentDateTime().substring(0,12);
	private String ApproveTlr;
	private String ApproveTlrPass;
	private String ApproveTlrOnl;
	private String ApproveTlrPassOnl;
	private String ApproveTlrRun;
	private String ApproveTlrPassRun;
	private String ApproveTlrRest;
	private String ApproveTlrPassRest;
	private String restFlag;
	private String restBatId;
	private String ApproveTlrOne;
	private String ApproveTlrPassOne;
	private String startBatId4;
	private String startBatDate4;
	private String startBatIdOne;
	private String startBatDateOne;
	private String id;
	private String id2;
	private String grpIdRun;
	
	private String restGrpId;
	
	/**
	 * @return the approveTlrOne
	 */
	public String getApproveTlrOne() {
		return ApproveTlrOne;
	}

	/**
	 * @param approveTlrOne the approveTlrOne to set
	 */
	public void setApproveTlrOne(String approveTlrOne) {
		ApproveTlrOne = approveTlrOne;
	}

	/**
	 * @return the approveTlrPassOne
	 */
	public String getApproveTlrPassOne() {
		return ApproveTlrPassOne;
	}

	/**
	 * @param approveTlrPassOne the approveTlrPassOne to set
	 */
	public void setApproveTlrPassOne(String approveTlrPassOne) {
		ApproveTlrPassOne = approveTlrPassOne;
	}

	/**
	 * @return the startBatId4
	 */
	public String getStartBatId4() {
		return startBatId4;
	}

	/**
	 * @param startBatId4 the startBatId4 to set
	 */
	public void setStartBatId4(String startBatId4) {
		this.startBatId4 = startBatId4;
	}

	/**
	 * @return the startBatDate4
	 */
	public String getStartBatDate4() {
		return startBatDate4;
	}

	/**
	 * @param startBatDate4 the startBatDate4 to set
	 */
	public void setStartBatDate4(String startBatDate4) {
		this.startBatDate4 = startBatDate4;
	}

	/**
	 * @return the restFlag
	 */
	public String getRestFlag() {
		return restFlag;
	}

	/**
	 * @param restFlag the restFlag to set
	 */
	public void setRestFlag(String restFlag) {
		this.restFlag = restFlag;
	}

	/**
	 * @return the restBatId
	 */
	public String getRestBatId() {
		return restBatId;
	}

	/**
	 * @param restBatId the restBatId to set
	 */
	public void setRestBatId(String restBatId) {
		this.restBatId = restBatId;
	}

	/**
	 * @return the approveTlrRest
	 */
	public String getApproveTlrRest() {
		return ApproveTlrRest;
	}

	/**
	 * @param approveTlrRest the approveTlrRest to set
	 */
	public void setApproveTlrRest(String approveTlrRest) {
		ApproveTlrRest = approveTlrRest;
	}

	/**
	 * @return the approveTlrPassRest
	 */
	public String getApproveTlrPassRest() {
		return ApproveTlrPassRest;
	}

	/**
	 * @param approveTlrPassRest the approveTlrPassRest to set
	 */
	public void setApproveTlrPassRest(String approveTlrPassRest) {
		ApproveTlrPassRest = approveTlrPassRest;
	}

	/**
	 * @return the approveTlrOnl
	 */
	public String getApproveTlrOnl() {
		return ApproveTlrOnl;
	}

	/**
	 * @param approveTlrOnl the approveTlrOnl to set
	 */
	public void setApproveTlrOnl(String approveTlrOnl) {
		ApproveTlrOnl = approveTlrOnl;
	}

	/**
	 * @return the approveTlrPassOnl
	 */
	public String getApproveTlrPassOnl() {
		return ApproveTlrPassOnl;
	}

	/**
	 * @param approveTlrPassOnl the approveTlrPassOnl to set
	 */
	public void setApproveTlrPassOnl(String approveTlrPassOnl) {
		ApproveTlrPassOnl = approveTlrPassOnl;
	}

	/**
	 * @return the approveTlrRun
	 */
	public String getApproveTlrRun() {
		return ApproveTlrRun;
	}

	/**
	 * @param approveTlrRun the approveTlrRun to set
	 */
	public void setApproveTlrRun(String approveTlrRun) {
		ApproveTlrRun = approveTlrRun;
	}

	/**
	 * @return the approveTlrPassRun
	 */
	public String getApproveTlrPassRun() {
		return ApproveTlrPassRun;
	}

	/**
	 * @param approveTlrPassRun the approveTlrPassRun to set
	 */
	public void setApproveTlrPassRun(String approveTlrPassRun) {
		ApproveTlrPassRun = approveTlrPassRun;
	}

	/**
	 * @return the startBatId3
	 */
	public String getStartBatId3() {
		return startBatId3;
	}

	/**
	 * @param startBatId3 the startBatId3 to set
	 */
	public void setStartBatId3(String startBatId3) {
		this.startBatId3 = startBatId3;
	}

	/**
	 * @return the startBatDateTime
	 */
	public String getStartBatDateTime() {
		return startBatDateTime;
	}

	/**
	 * @param startBatDateTime the startBatDateTime to set
	 */
	public void setStartBatDateTime(String startBatDateTime) {
		this.startBatDateTime = startBatDateTime;
	}

	/**
	 * @return the startBatId2
	 */
	public String getStartBatId2() {
		return startBatId2;
	}

	/**
	 * @param startBatId2
	 *            the startBatId2 to set
	 */
	public void setStartBatId2(String startBatId2) {
		this.startBatId2 = startBatId2;
	}

	/**
	 * @return the startBatDate2
	 */
	public String getStartBatDate2() {
		return startBatDate2;
	}

	/**
	 * @param startBatDate2
	 *            the startBatDate2 to set
	 */
	public void setStartBatDate2(String startBatDate2) {
		this.startBatDate2 = startBatDate2;
	}

	/**
	 * @return the approveTlr
	 */
	public String getApproveTlr() {
		return ApproveTlr;
	}

	/**
	 * @param approveTlr
	 *            the approveTlr to set
	 */
	public void setApproveTlr(String approveTlr) {
		ApproveTlr = approveTlr;
	}

	/**
	 * @return the approveTlrPass
	 */
	public String getApproveTlrPass() {
		return ApproveTlrPass;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId2() {
		return id2;
	}

	public void setId2(String id2) {
		this.id2 = id2;
	}
	

	public String getStartBatIdOne() {
		return startBatIdOne;
	}

	public void setStartBatIdOne(String startBatIdOne) {
		this.startBatIdOne = startBatIdOne;
	}

	public String getStartBatDateOne() {
		return startBatDateOne;
	}

	public void setStartBatDateOne(String startBatDateOne) {
		this.startBatDateOne = startBatDateOne;
	}
	

	public String getGrpIdRun() {
		return grpIdRun;
	}

	public void setGrpIdRun(String grpIdRun) {
		this.grpIdRun = grpIdRun;
	}

	/**
	 * @param approveTlrPass
	 *            the approveTlrPass to set
	 */
	public void setApproveTlrPass(String approveTlrPass) {
		ApproveTlrPass = approveTlrPass;
	}
	
	public String getRestGrpId() {
		return restGrpId;
	}

	public void setRestGrpId(String restGrpId) {
		this.restGrpId = restGrpId;
	}

		// 查询是否是批处理复核员
		protected String isPclFuhe(String approveTlr,String approveTlrPass) {
			return  Constants.SUCCESS_CODE;
		}
}
