package com.allinfinance.struts.agentpay.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.allinfinance.bo.agentpay.T90203BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.ErrorCode;
import com.allinfinance.po.agentpay.TblBankInfo;
import com.allinfinance.po.agentpay.TblBusCodeInfo;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.BeanUtils;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;


@SuppressWarnings("serial")
public class T90203Action extends BaseAction {
	
	
	T90203BO t90203BO = (T90203BO) ContextUtil.getBean("T90203BO");
	/* (non-Javadoc)
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		
		if("add".equals(method)) {
			log("新增代收付业务代码信息。操作员编号：" + operator.getOprId());
			rspCode = add();
		} else if("delete".equals(method)) {
			log("删除代收付业务代码信息。操作员编号：" + operator.getOprId());
			rspCode = delete();
		} else if("update".equals(method)) {
			log("同步代收付业务代码信息。操作员编号：" + operator.getOprId());
			rspCode = update();
		}
		
		return rspCode;
	}
	
	/**
	 * add city code
	 * @return
	 */
	private String add() {
		
		if(t90203BO.getBusCodeInfo(busCode) !=null){
			return ErrorCode.T90203_01;
		}
		TblBusCodeInfo tblBusCodeInfo = new TblBusCodeInfo();
		tblBusCodeInfo.setBusCode(busCode);
		tblBusCodeInfo.setUsage(usage);
		tblBusCodeInfo.setTypeNo(typeNo);
		tblBusCodeInfo.setName(name);
		tblBusCodeInfo.setSeq(seq);
		t90203BO.add(tblBusCodeInfo);
		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * delete city code
	 * @return
	 */
	private String delete() {
		
		if(t90203BO.getBusCodeInfo(CommonFunction.fillString(busCode, ' ', 5, true)) == null) {
			return "没有找到要删除的业务代码信息";
		}
		
		t90203BO.delete(CommonFunction.fillString(busCode, ' ', 5, true));
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
		
		jsonBean.parseJSONArrayData(getBusCodeCodeList());
		
		int len = jsonBean.getArray().size();
		
		TblBusCodeInfo tblBusCodeInfo = null;
		
		List<TblBusCodeInfo> busCodeInfoList = new ArrayList<TblBusCodeInfo>(len);
		
		for(int i = 0; i < len; i++) {			
			tblBusCodeInfo = new TblBusCodeInfo();			
			jsonBean.setObject(jsonBean.getJSONDataAt(i));			
			BeanUtils.setObjectWithPropertiesValue(tblBusCodeInfo, jsonBean, false);
			tblBusCodeInfo.setBusCode(CommonFunction.fillString(tblBusCodeInfo.getBusCode(), ' ', 5, true));
			busCodeInfoList.add(tblBusCodeInfo);
		}
		
		t90203BO.update(busCodeInfoList);
		
		return Constants.SUCCESS_CODE;
	}
	
	private String busCode;
	private String usage;
    private String typeNo;
    private String name;
    private String seq;
    private String busCodeCodeList;
	public T90203BO getT90203BO() {
		return t90203BO;
	}

	public void setT90203BO(T90203BO t90203bo) {
		t90203BO = t90203bo;
	}

	public String getBusCode() {
		return busCode;
	}

	public void setBusCode(String busCode) {
		this.busCode = busCode;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getTypeNo() {
		return typeNo;
	}

	public void setTypeNo(String typeNo) {
		this.typeNo = typeNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getBusCodeCodeList() {
		return busCodeCodeList;
	}

	public void setBusCodeCodeList(String busCodeCodeList) {
		this.busCodeCodeList = busCodeCodeList;
	}

}
