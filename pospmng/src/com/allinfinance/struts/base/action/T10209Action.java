package com.allinfinance.struts.base.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.allinfinance.bo.base.T10209BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.po.TblCardRoute;
import com.allinfinance.po.TblCardRoutePK;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.BeanUtils;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

/**
 * Title:本行卡表维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-5
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T10209Action extends BaseAction {
	
	T10209BO t10209BO = (T10209BO) ContextUtil.getBean("T10209BO");
	
	/* (non-Javadoc)
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		
		// add method
		if("add".equals(method)) {
			rspCode = add();
		} else if("delete".equals(method)) {
			rspCode = delete();
		} else if("update".equals(method)) {
			rspCode = update();
		}
		return rspCode;
	}
	
	/**
	 * add system parameter information
	 * @return
	 */
	private String add() {
		
		//initialize system parameter object
		TblCardRoute tblCardRoute = new TblCardRoute();
		//initialize id of system parameter object
		TblCardRoutePK tblCardRoutePK = new TblCardRoutePK();
		//set property
		tblCardRoutePK.setUsageKey("0");

		tblCardRoutePK.setCardId(cardId);
		
		// if there is a same id in the system
		if(t10209BO.get(tblCardRoutePK) != null) {
			return "该卡信息已存在。";
		}
		
		tblCardRoute.setId(tblCardRoutePK);

		if(StringUtil.isNull(branchNo)){
			tblCardRoute.setBranchNo("0");
		}else{
			tblCardRoute.setBranchNo(branchNo);
		}
		if(StringUtil.isNull(branchNoLen)){
			tblCardRoute.setBranchNoLen("0");
		}else{
			tblCardRoute.setBranchNoLen(branchNoLen);
		}
		if(StringUtil.isNull(branchNoOffset)){
			tblCardRoute.setBranchNoOffset("0");
		}else{
			tblCardRoute.setBranchNoOffset(branchNoOffset);
		}
		if(StringUtil.isNull(cardIdLen)){
			tblCardRoute.setCardIdLen("0");
		}else{
			tblCardRoute.setCardIdLen(cardIdLen);
		}
		if(StringUtil.isNull(cardIdOffset)){
			tblCardRoute.setCardIdOffset("0");
		}else{
			tblCardRoute.setCardIdOffset(cardIdOffset);
		}
		if(StringUtil.isNull(cardName)){
			tblCardRoute.setCardName("0");
		}else{
			tblCardRoute.setCardName(cardName);
		}
		if(StringUtil.isNull(cardType)){
			tblCardRoute.setCardType("0");
		}else{
			tblCardRoute.setCardType(cardType);
		}
		if(StringUtil.isNull(destSrvId)){
			tblCardRoute.setDestSrvId("0");
		}else{
			tblCardRoute.setDestSrvId(destSrvId);
		}
		if(StringUtil.isNull(instCode)){
			tblCardRoute.setInstCode("0");
		}else{
			tblCardRoute.setInstCode(instCode);
		}
		
			tblCardRoute.setTxnNum("####");
		
		tblCardRoute.setModiOprId("-");
		tblCardRoute.setModiTime("-");
		tblCardRoute.setInitOprId(operator.getOprId());
		tblCardRoute.setInitTime(CommonFunction.getCurrentDateTime());
		
		
		
		//add to database
		t10209BO.add(tblCardRoute);
		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * delete system parameter information
	 * @return
	 */
	private String delete() {
		
		TblCardRoutePK tblCardRoutePK = new TblCardRoutePK(usageKey,cardId);
		
		if(t10209BO.get(tblCardRoutePK) == null) {
			return "您所要删除的卡信息已经不存在";
		}
		
		t10209BO.delete(tblCardRoutePK);
		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * update the parameter information list
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		jsonBean.parseJSONArrayData(getParameterList());
		
		int len = jsonBean.getArray().size();
		
		
		TblCardRoute tblCardRoute = null;
		
		TblCardRoutePK tblCardRoutePK = null;
		
		List<TblCardRoute> tblCardRouteList = new ArrayList<TblCardRoute>(len);
		
		for(int i = 0; i < len; i++) {
			
			tblCardRoute = new TblCardRoute();
			
			tblCardRoutePK = new TblCardRoutePK();
			
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			
			BeanUtils.setObjectWithPropertiesValue(tblCardRoutePK, jsonBean,false);
			
			tblCardRoute.setId(tblCardRoutePK);
			
			tblCardRoute.setTxnNum("####");
			
			BeanUtils.setObjectWithPropertiesValue(tblCardRoute, jsonBean,false);
			
			BeanUtils.setNullValueWithValue(tblCardRoute, "-", 0);
			
			tblCardRoute.setModiOprId(operator.getOprId());
			tblCardRoute.setModiTime(CommonFunction.getCurrentDateTime());
			
			tblCardRouteList.add(tblCardRoute);
			
		}
		
		t10209BO.update(tblCardRouteList);
		
		return Constants.SUCCESS_CODE;
	}
	/**the parameter owner*/
	private String usageKey;
	private String cardId;
	private String cardIdOffset;
	private String cardIdLen;
	private String branchNo;
	private String branchNoOffset;
	private String branchNoLen;
	private String instCode;
	private String cardName;
	private String destSrvId;
	private String txnNum;
	private String cardType;
	private String modiOprId;
	private String modiTime;
	private String initOprId;
	private String initTime;
	
	/**the list of parameter to update*/
	private String parameterList;
	
	public String getModiOprId() {
		return modiOprId;
	}

	public void setModiOprId(String modiOprId) {
		this.modiOprId = modiOprId;
	}

	public String getModiTime() {
		return modiTime;
	}

	public void setModiTime(String modiTime) {
		this.modiTime = modiTime;
	}

	public String getInitOprId() {
		return initOprId;
	}

	public void setInitOprId(String initOprId) {
		this.initOprId = initOprId;
	}

	public String getInitTime() {
		return initTime;
	}

	public void setInitTime(String initTime) {
		this.initTime = initTime;
	}

	public String getUsageKey() {
		return usageKey;
	}

	public void setUsageKey(String usageKey) {
		this.usageKey = usageKey;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardIdOffset() {
		return cardIdOffset;
	}

	public void setCardIdOffset(String cardIdOffset) {
		this.cardIdOffset = cardIdOffset;
	}

	public String getCardIdLen() {
		return cardIdLen;
	}

	public void setCardIdLen(String cardIdLen) {
		this.cardIdLen = cardIdLen;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public String getBranchNoOffset() {
		return branchNoOffset;
	}

	public void setBranchNoOffset(String branchNoOffset) {
		this.branchNoOffset = branchNoOffset;
	}

	public String getBranchNoLen() {
		return branchNoLen;
	}

	public void setBranchNoLen(String branchNoLen) {
		this.branchNoLen = branchNoLen;
	}

	public String getInstCode() {
		return instCode;
	}

	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getDestSrvId() {
		return destSrvId;
	}

	public void setDestSrvId(String destSrvId) {
		this.destSrvId = destSrvId;
	}

	public String getTxnNum() {
		return txnNum;
	}

	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	/**
	 * @return the parameterList
	 */
	public String getParameterList() {
		return parameterList;
	}

	/**
	 * @param parameterList the parameterList to set
	 */
	public void setParameterList(String parameterList) {
		this.parameterList = parameterList;
	}
}
