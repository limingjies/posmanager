package com.allinfinance.struts.agentpay.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.allinfinance.bo.agentpay.T90204BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.ErrorCode;
import com.allinfinance.po.agentpay.TblBusCodeInfo;
import com.allinfinance.po.agentpay.TblParamInfo;
import com.allinfinance.po.agentpay.TblParamInfoPK;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.BeanUtils;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;


@SuppressWarnings("serial")
public class T90204Action extends BaseAction {
	
	
	T90204BO t90204BO = (T90204BO) ContextUtil.getBean("T90204BO");
	/* (non-Javadoc)
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		
		if("add".equals(method)) {
			log("新增代收付参数信息。操作员编号：" + operator.getOprId());
			rspCode = add();
		} else if("delete".equals(method)) {
			log("删除代收付参数信息。操作员编号：" + operator.getOprId());
			rspCode = delete();
		} else if("update".equals(method)) {
			log("同步代收付参数信息。操作员编号：" + operator.getOprId());
			rspCode = update();
		}
		
		return rspCode;
	}
	
	/**
	 * add city code
	 * @return
	 */
	private String add() {
		TblParamInfoPK id = new TblParamInfoPK();
		id.setParamCode(paramCode);
		id.setParamMark(paramMark);
		if(t90204BO.get(id) !=null){
			return ErrorCode.T90204_01;
		}
		TblParamInfo tblParamInfo = new TblParamInfo();
		tblParamInfo.setId(id);
		tblParamInfo.setParamType(paramType);
		tblParamInfo.setParamPro(paramPro);
		tblParamInfo.setParamValue(paramValue);
		tblParamInfo.setDescr(descr);
		tblParamInfo.setCreateTime(CommonFunction.getCurrentDateTime());
		tblParamInfo.setLstUpdTime(CommonFunction.getCurrentDateTime());
		tblParamInfo.setLstUpdTlr(operator.getOprId());
		t90204BO.add(tblParamInfo);
		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * delete city code
	 * @return
	 */
	private String delete() {
		TblParamInfoPK id = new TblParamInfoPK();
		id.setParamCode(paramCode);
		id.setParamMark(paramMark);
		if(t90204BO.get(id) == null) {
			return "没有找到要删除的参数信息";
		}
		
		t90204BO.delete(id);
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * update city code
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private String update() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		jsonBean.parseJSONArrayData(getParamList());
		
		int len = jsonBean.getArray().size();
		
		TblParamInfo tblParamInfo = null;
		TblParamInfoPK tblParamInfoPK = null;		
		List<TblParamInfo> paramInfoList = new ArrayList<TblParamInfo>(len);
		
		for(int i = 0; i < len; i++) {			
			tblParamInfo = new TblParamInfo();	
			tblParamInfoPK =  new TblParamInfoPK();
			jsonBean.setObject(jsonBean.getJSONDataAt(i));			
			BeanUtils.setObjectWithPropertiesValue(tblParamInfoPK, jsonBean, false);
			BeanUtils.setObjectWithPropertiesValue(tblParamInfo, jsonBean, false);
			tblParamInfo.setId(tblParamInfoPK);
			tblParamInfo.setLstUpdTime(CommonFunction.getCurrentDateTime());
			tblParamInfo.setLstUpdTlr(operator.getOprId());
			paramInfoList.add(tblParamInfo);
		}
		
		t90204BO.update(paramInfoList);
		
		return Constants.SUCCESS_CODE;
	}
	
	private String paramMark;
	private String paramCode;
	private String paramType;
	private String paramPro;
	private String paramValue;
	private String descr;
	private String paramList;
	public T90204BO getT90204BO() {
		return t90204BO;
	}

	public void setT90204BO(T90204BO t90204bo) {
		t90204BO = t90204bo;
	}

	public String getParamMark() {
		return paramMark;
	}

	public void setParamMark(String paramMark) {
		this.paramMark = paramMark;
	}

	public String getParamCode() {
		return paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getParamPro() {
		return paramPro;
	}

	public void setParamPro(String paramPro) {
		this.paramPro = paramPro;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getParamList() {
		return paramList;
	}

	public void setParamList(String paramList) {
		this.paramList = paramList;
	}

}
