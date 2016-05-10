package com.allinfinance.struts.agentpay.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.allinfinance.bo.agentpay.T90201BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.ErrorCode;
import com.allinfinance.po.agentpay.TblAreaInfo;
import com.allinfinance.po.agentpay.TblAreaInfoPK;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.BeanUtils;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;


@SuppressWarnings("serial")
public class T90201Action extends BaseAction {
	
	
	T90201BO t90201BO = (T90201BO) ContextUtil.getBean("T90201BO");
	/* (non-Javadoc)
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		
		if("add".equals(method)) {
			log("新增代收付地区码信息。操作员编号：" + operator.getOprId());
			rspCode = add();
		} else if("delete".equals(method)) {
			log("删除代收付地区码信息。操作员编号：" + operator.getOprId());
			rspCode = delete();
		} else if("update".equals(method)) {
			log("同步代收付地区码信息。操作员编号：" + operator.getOprId());
			rspCode = update();
		}
		
		return rspCode;
	}
	
	/**
	 * add city code
	 * @return
	 */
	private String add() {
		TblAreaInfoPK tblAreaInfoPK = new TblAreaInfoPK();
		tblAreaInfoPK.setAreaNo(CommonFunction.fillString(areaNo, ' ', 5, true));
		tblAreaInfoPK.setZip(CommonFunction.fillString(zip, ' ', 6, true));
		if(t90201BO.getAreaInfo(tblAreaInfoPK) !=null){
			return ErrorCode.T90201_01;
		}
		TblAreaInfo tblAreaInfo = new TblAreaInfo();
		tblAreaInfo.setId(tblAreaInfoPK);
		tblAreaInfo.setProvince(province);
		tblAreaInfo.setAreaName(areaName);
		t90201BO.add(tblAreaInfo);
		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * delete city code
	 * @return
	 */
	private String delete() {
		TblAreaInfoPK id = new TblAreaInfoPK();
		id.setAreaNo(CommonFunction.fillString(areaNo, ' ', 5, true));
		id.setZip(CommonFunction.fillString(zip, ' ', 6, true));
		if(t90201BO.getAreaInfo(id) == null) {
			return "没有找到要删除的地区码信息";
		}
		
		t90201BO.delete(id);
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
		
		jsonBean.parseJSONArrayData(getAreaCodeList());
		
		int len = jsonBean.getArray().size();
		TblAreaInfoPK tblAreaInfoPK = null;
		TblAreaInfo tblAreaInfo = null;
		
		List<TblAreaInfo> areaInfoList = new ArrayList<TblAreaInfo>(len);
		
		for(int i = 0; i < len; i++) {			
			tblAreaInfo = new TblAreaInfo();
			tblAreaInfoPK = new TblAreaInfoPK();
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			BeanUtils.setObjectWithPropertiesValue(tblAreaInfoPK, jsonBean, false);
			BeanUtils.setObjectWithPropertiesValue(tblAreaInfo, jsonBean, false);
			tblAreaInfoPK.setAreaNo(CommonFunction.fillString(tblAreaInfoPK.getAreaNo(), ' ', 5, true));
			tblAreaInfoPK.setZip(CommonFunction.fillString(tblAreaInfoPK.getZip(), ' ', 6, true));
			tblAreaInfo.setId(tblAreaInfoPK);
			areaInfoList.add(tblAreaInfo);
		}
		
		t90201BO.update(areaInfoList);
		
		return Constants.SUCCESS_CODE;
	}
	
	private String areaNo;
	private String zip;
	private String province;
	private String areaName;
    private String areaCodeList;
	public T90201BO getT90201BO() {
		return t90201BO;
	}

	public void setT90201BO(T90201BO t90201bo) {
		t90201BO = t90201bo;
	}

	public String getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaCodeList() {
		return areaCodeList;
	}

	public void setAreaCodeList(String areaCodeList) {
		this.areaCodeList = areaCodeList;
	}

}
