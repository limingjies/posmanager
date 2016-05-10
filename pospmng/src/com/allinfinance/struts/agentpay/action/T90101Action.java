package com.allinfinance.struts.agentpay.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.allinfinance.bo.agentpay.T90101BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.ErrorCode;
import com.allinfinance.po.agentpay.TblAreaInfo;
import com.allinfinance.po.agentpay.TblMchtFileTransInfo;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.BeanUtils;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;


@SuppressWarnings("serial")
public class T90101Action extends BaseAction {
	
	
	T90101BO t90101BO = (T90101BO) ContextUtil.getBean("T90101BO");
	/* (non-Javadoc)
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		
		if("add".equals(method)) {
			log("新增代收付商户信息。操作员编号：" + operator.getOprId());
			rspCode = add();
		} else if("delete".equals(method)) {
			log("删除代收付商户信息。操作员编号：" + operator.getOprId());
			rspCode = delete();
		} else if("update".equals(method)) {
			log("同步代收付商户信息。操作员编号：" + operator.getOprId());
			rspCode = update();
		}
		
		return rspCode;
	}
	
	/**
	 * add MchtFileTransInfo
	 * @return
	 */
	private String add() {
		
		if(t90101BO.getMchtFileTransInfo(mchtNo) !=null){
			return ErrorCode.T90101_01;
		}
		TblMchtFileTransInfo tblMchtFileTransInfo = new TblMchtFileTransInfo();
		tblMchtFileTransInfo.setMchtNo(mchtNo);
		tblMchtFileTransInfo.setGetFileWay(getFileWay);
		tblMchtFileTransInfo.setCommWay(commWay);
		tblMchtFileTransInfo.setCheckType(checkType);
		tblMchtFileTransInfo.setUserName(userName);
		tblMchtFileTransInfo.setPasswd(passwd);
		tblMchtFileTransInfo.setIp(ip);
		tblMchtFileTransInfo.setPort(port);
		tblMchtFileTransInfo.setFilePath(filePath);
		tblMchtFileTransInfo.setKeyPath(keyPath);
		tblMchtFileTransInfo.setKeyIdx(keyIdx);
		tblMchtFileTransInfo.setKeyVal(keyVal);
		tblMchtFileTransInfo.setCheckVal(checkVal);
		tblMchtFileTransInfo.setRspSendFlag(rspSendFlag);
		tblMchtFileTransInfo.setRspSendTime(rspSendTime);
		tblMchtFileTransInfo.setDzSendTime(dzSendTime);
		tblMchtFileTransInfo.setLstUpdTlr(operator.getOprId());
		tblMchtFileTransInfo.setLstUpdTime(CommonFunction.getCurrentDateTime());
		tblMchtFileTransInfo.setCreateTime(CommonFunction.getCurrentDateTime());
		t90101BO.add(tblMchtFileTransInfo);
		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * delete MchtFileTransInfo
	 * @return
	 */
	private String delete() {
		
		if(t90101BO.getMchtFileTransInfo(mchtNo) ==null) {
			return "没有找到要删除的商户信息";
		}
		
		t90101BO.delete(mchtNo);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * update MchtFileTransInfo
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		jsonBean.parseJSONArrayData(getMchtFileTransInfoList());
		
		int len = jsonBean.getArray().size();
		
		TblMchtFileTransInfo tblMchtFileTransInfo = null;
		
		List<TblMchtFileTransInfo> mchtFileTransInfoList = new ArrayList<TblMchtFileTransInfo>(len);
		
		for(int i = 0; i < len; i++) {			
			tblMchtFileTransInfo = new TblMchtFileTransInfo();
			jsonBean.setObject(jsonBean.getJSONDataAt(i));			
			BeanUtils.setObjectWithPropertiesValue(tblMchtFileTransInfo, jsonBean, false);
			tblMchtFileTransInfo.setLstUpdTlr(operator.getOprId());
			tblMchtFileTransInfo.setLstUpdTime(CommonFunction.getCurrentDateTime());
			mchtFileTransInfoList.add(tblMchtFileTransInfo);
		}
		
		t90101BO.update(mchtFileTransInfoList);
		
		return Constants.SUCCESS_CODE;
	}
	
	private String mchtNo;
	private String getFileWay;
	private String commWay;
	private String checkType;
	private String userName;
	private String passwd;
	private String ip;
	private String port;
	private String filePath;
	private String keyPath;
	private String keyIdx;
	private String keyVal;
	private String checkVal;
	private String rspSendFlag;
	private Integer rspSendTime;
	private String dzSendTime;
    private String mchtFileTransInfoList;
	public T90101BO getT90101BO() {
		return t90101BO;
	}

	public void setT90101BO(T90101BO t90101bo) {
		t90101BO = t90101bo;
	}

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getGetFileWay() {
		return getFileWay;
	}

	public void setGetFileWay(String getFileWay) {
		this.getFileWay = getFileWay;
	}

	public String getCommWay() {
		return commWay;
	}

	public void setCommWay(String commWay) {
		this.commWay = commWay;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getKeyPath() {
		return keyPath;
	}

	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}

	public String getKeyIdx() {
		return keyIdx;
	}

	public void setKeyIdx(String keyIdx) {
		this.keyIdx = keyIdx;
	}

	public String getKeyVal() {
		return keyVal;
	}

	public void setKeyVal(String keyVal) {
		this.keyVal = keyVal;
	}

	public String getCheckVal() {
		return checkVal;
	}

	public void setCheckVal(String checkVal) {
		this.checkVal = checkVal;
	}

	public String getRspSendFlag() {
		return rspSendFlag;
	}

	public void setRspSendFlag(String rspSendFlag) {
		this.rspSendFlag = rspSendFlag;
	}

	public Integer getRspSendTime() {
		return rspSendTime;
	}

	public void setRspSendTime(Integer rspSendTime) {
		this.rspSendTime = rspSendTime;
	}

	public String getDzSendTime() {
		return dzSendTime;
	}

	public void setDzSendTime(String dzSendTime) {
		this.dzSendTime = dzSendTime;
	}

	public String getMchtFileTransInfoList() {
		return mchtFileTransInfoList;
	}

	public void setMchtFileTransInfoList(String mchtFileTransInfoList) {
		this.mchtFileTransInfoList = mchtFileTransInfoList;
	}

}
