/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-1       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 allinfinance, Inc. All rights reserved.
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
package com.allinfinance.struts.mchnt.action;

import java.util.ArrayList;
import java.util.List;

import com.allinfinance.bo.mchnt.T20303BO;
import com.allinfinance.common.Constants;
import com.allinfinance.po.mchnt.TblInfMchntTp;
import com.allinfinance.po.mchnt.TblInfMchntTpPK;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

/**
 * Title:商户MCC维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-1
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class T20303Action extends BaseAction {
	
	private T20303BO t20303BO = (T20303BO) ContextUtil.getBean("T20303BO");
	
	/* (non-Javadoc)
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute(){
		try {
			if("add".equals(method)) {
				rspCode = add();
			} else if("delete".equals(method)) {
				rspCode = delete();
			} else if("update".equals(method)) {
				rspCode = update();
			} else if("stop".equals(method)) {
				rspCode = stop();
			} else if("start".equals(method)) {
				rspCode = start();
			}
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，商户MCC维护操作" + getMethod() + "失败，失败原因为："+e.getMessage());
		}
		return rspCode;
	}
	
	/**
	 * 添加商户MCC信息
	 * @return
	 */
	private String add() {
		
		TblInfMchntTpPK tblInfMchntTpPK = new TblInfMchntTpPK();
		tblInfMchntTpPK.setMchntTp(mchntTp);
		tblInfMchntTpPK.setFrnIn("0");
		if(t20303BO.get(tblInfMchntTpPK) != null) {
			return "该商户MCC编号已经被使用";
		}
		TblInfMchntTp tblInfMchntTp = new TblInfMchntTp();
		tblInfMchntTp.setId(tblInfMchntTpPK);
		tblInfMchntTp.setDescr(mccDescr);
		tblInfMchntTp.setMchntTpGrp(mchntTpGrp);
		tblInfMchntTp.setRecUpdUsrId(operator.getOprId());
		tblInfMchntTp.setLastOperIn(Constants.ADD);
		tblInfMchntTp.setRecCrtTs(CommonFunction.getCurrentDateTime());
		tblInfMchntTp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		tblInfMchntTp.setRecSt(Constants.VALID);
		t20303BO.add(tblInfMchntTp);
		log("添加商户组别信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 删除商户MCC信息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String delete() {
		
		String sql = "select * from tbl_mcht_base_inf where mcc = '" + mchntTp + "'";
		
		List<Object[]> result = commQueryDAO.findBySQLQuery(sql);
		
		if(result.size() > 0) {
			return "该商户MCC已经被商户使用，无法删除！";
		}
		TblInfMchntTpPK tblInfMchntTpPK = new TblInfMchntTpPK();
		tblInfMchntTpPK.setMchntTp(mchntTp);
		tblInfMchntTpPK.setFrnIn("0");
		t20303BO.delete(tblInfMchntTpPK);
		log("删除商户组别信息成功。操作员编号：" + operator.getOprId());
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 同步商户MCC信息
	 * @return
	 */
	private String update() {

		jsonBean.parseJSONArrayData(mchntTpList);
		
		int len = jsonBean.getArray().size();
		
		TblInfMchntTp tblMchntTp = null;
		
		List<TblInfMchntTp> tblMchntTpList = new ArrayList<TblInfMchntTp>(len);
		for(int i = 0; i < len; i++) {
			TblInfMchntTpPK pk = new TblInfMchntTpPK();
			pk.setMchntTp(jsonBean.getJSONDataAt(i).getString("mchntTp"));
			pk.setFrnIn("0");
			tblMchntTp = t20303BO.get(pk);
//			tblMchntTp.setMchntTpGrp(jsonBean.getJSONDataAt(i).getString("mchntTpGrp"));
			tblMchntTp.setDescr(jsonBean.getJSONDataAt(i).getString("descr"));
			tblMchntTpList.add(tblMchntTp);
		}
		return t20303BO.update(tblMchntTpList);
//		log("同步商户组别信息成功。操作员编号：" + operator.getOprId());
//		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 停用MCC
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String stop() {
		String sql = "select * from tbl_mcht_base_inf where mcc = '" + mchntTp + "'";
				
		List<Object[]> result = commQueryDAO.findBySQLQuery(sql);
		
		if(result.size() > 0) {
			return "该商户MCC已经被商户使用，无法停用！";
		}
		
		TblInfMchntTpPK tblInfMchntTpPK = new TblInfMchntTpPK();
		tblInfMchntTpPK.setMchntTp(mchntTp);
		tblInfMchntTpPK.setFrnIn("0");
		TblInfMchntTp mcc = t20303BO.get(tblInfMchntTpPK);
		
		if("0".equals(mcc.getRecSt())){
			return "已经停用的MCC！";
		}
		mcc.setRecSt("0");
		t20303BO.update(mcc);
		
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * 启用MCC
	 * @return
	 */
	private String start() {
		TblInfMchntTpPK tblInfMchntTpPK = new TblInfMchntTpPK();
		tblInfMchntTpPK.setMchntTp(mchntTp);
		tblInfMchntTpPK.setFrnIn("0");
		TblInfMchntTp mcc = t20303BO.get(tblInfMchntTpPK);
		
		if("1".equals(mcc.getRecSt())){
			return "已经启用的MCC！";
		}
		mcc.setRecSt("1");
		t20303BO.update(mcc);
		
		return Constants.SUCCESS_CODE;
	}
	
	// 商户MCC编号
	private String mchntTp;
	// 商户组别编号
	private String mchntTpGrp;
	// 商户MCC描述
	private String mccDescr;
	// 商户组别集合
	private String mchntTpList;

	/**
	 * @return the mchntTp
	 */
	public String getMchntTp() {
		return mchntTp;
	}

	/**
	 * @param mchntTp the mchntTp to set
	 */
	public void setMchntTp(String mchntTp) {
		this.mchntTp = mchntTp;
	}

	/**
	 * @return the mchntTpGrp
	 */
	public String getMchntTpGrp() {
		return mchntTpGrp;
	}

	/**
	 * @param mchntTpGrp the mchntTpGrp to set
	 */
	public void setMchntTpGrp(String mchntTpGrp) {
		this.mchntTpGrp = mchntTpGrp;
	}

	/**
	 * @return the mccDescr
	 */
	public String getMccDescr() {
		return mccDescr;
	}

	/**
	 * @param mccDescr the mccDescr to set
	 */
	public void setMccDescr(String mccDescr) {
		this.mccDescr = mccDescr;
	}

	/**
	 * @return the mchntTpList
	 */
	public String getMchntTpList() {
		return mchntTpList;
	}

	/**
	 * @param mchntTpList the mchntTpList to set
	 */
	public void setMchntTpList(String mchntTpList) {
		this.mchntTpList = mchntTpList;
	}
	
	
}
