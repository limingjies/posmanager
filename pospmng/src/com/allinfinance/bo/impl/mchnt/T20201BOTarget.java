/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-10       first release
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
package com.allinfinance.bo.impl.mchnt;

import java.lang.reflect.InvocationTargetException;

import com.allinfinance.bo.mchnt.T20201BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.mchnt.TblDivMchntDAO;
import com.allinfinance.dao.iface.mchnt.TblDivMchntTmpDAO;
import com.allinfinance.dao.iface.mchnt.TblMchntInfoDAO;
import com.allinfinance.dao.iface.mchnt.TblMchntInfoTmpDAO;
import com.allinfinance.dao.iface.mchnt.TblMchntRefuseDAO;
import com.allinfinance.po.mchnt.TblMchtBaseInf;

/**
 * Title: 商户审核BO实现
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-10
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class T20201BOTarget implements T20201BO {
	
	private TblMchntInfoDAO tblMchntInfoDAO;
	
	private TblDivMchntDAO tblDivMchntDAO;
	
	private TblMchntInfoTmpDAO tblMchntInfoTmpDAO;
	
	private TblDivMchntTmpDAO tblDivMchntTmpDAO;
	
	private TblMchntRefuseDAO tblMchntRefuseDAO;
	
	private ICommQueryDAO commQueryDAO;
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T20201BO#accept(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public String accept(String mchntId) throws IllegalAccessException, InvocationTargetException {

//		TblMchntInfoTmp tblMchntInfoTmp = tblMchntInfoTmpDAO.get(mchntId);
//		if(tblMchntInfoTmp == null) {
//			return "没有找到指定的临时商户信息";
//		}
//		
//		TblMchntInfo tblMchntInfo = new TblMchntInfo();
//		BeanUtils.copyProperties(tblMchntInfo, tblMchntInfoTmp);
//		
//		// 添加待审核通过
//		if(TblMchntInfoConstants.MCHNT_ST_NEW_UNCK.equals(tblMchntInfoTmp.getMchntSt()) || 
//				TblMchntInfoConstants.MCHNT_ST_MODI_UNCK.equals(tblMchntInfoTmp.getMchntSt())) { // 修改审核通过
//
//			// 获得临时商户下的分期信息
//			String hql = "from com.allinfinance.po.TblDivMchntTmp t where t.Id.MchtId = '" + mchntId + "'";
//			
//			List<TblDivMchntTmp> tblDivMchntTmpList = commQueryDAO.findByHQLQuery(hql);
//			
//			List<TblDivMchnt> tblDivMchntList = new ArrayList<TblDivMchnt>(tblDivMchntTmpList.size());
//			TblDivMchnt tblDivMchnt = null;
//			
//			// 遍历商户临时分期信息，添加到正式分期信息集合中
//			for(TblDivMchntTmp tblDivMchntTmp : tblDivMchntTmpList) {
//				tblDivMchnt = new TblDivMchnt();
//				tblDivMchnt.setId(new TblDivMchntPK(tblDivMchntTmp.getId().getMchtId(),
//													tblDivMchntTmp.getId().getDivNo()));
//				tblDivMchnt.setProductCode(tblDivMchntTmp.getProductCode());
//				tblDivMchntList.add(tblDivMchnt);
//			}
//			
//			// 添加审核通过
//			if(TblMchntInfoConstants.MCHNT_ST_NEW_UNCK.equals(tblMchntInfoTmp.getMchntSt())) {
//				Log.log("商户添加审核通过");
//				tblMchntInfoTmp.setMchntSt(TblMchntInfoConstants.MCHNT_ST_OK);
//				tblMchntInfo.setMchntSt(TblMchntInfoConstants.MCHNT_ST_OK);
//				// 保存商户信息
//				tblMchntInfoDAO.save(tblMchntInfo);
//				// 更新商户临时信息
//				tblMchntInfoTmpDAO.update(tblMchntInfoTmp);
//				// 保存商户分期信息
//				for(TblDivMchnt tmpDiv : tblDivMchntList) {
//					tblDivMchntDAO.save(tmpDiv);
//				}
//				
//			} else if(TblMchntInfoConstants.MCHNT_ST_MODI_UNCK.equals(tblMchntInfoTmp.getMchntSt())) { // 修改审核通过
//				Log.log("商户修改审核通过");
//				tblMchntInfoTmp.setMchntSt(TblMchntInfoConstants.MCHNT_ST_OK);
//				tblMchntInfo.setMchntSt(TblMchntInfoConstants.MCHNT_ST_OK);
//				
//				// 获得商户下的分期信息
//				hql = "from com.allinfinance.po.TblDivMchnt t where t.Id.MchtId = '" + mchntId + "'";
//				
//				List<TblDivMchnt> tblDivMchntOldList = commQueryDAO.findByHQLQuery(hql);
//				// 更新商户信息
//				tblMchntInfoDAO.update(tblMchntInfo);
//				// 更新商户临时信息
//				tblMchntInfoTmpDAO.update(tblMchntInfoTmp);
//				// 删除商户旧分期信息
//				for(TblDivMchnt tmpDiv : tblDivMchntOldList) {
//					tblDivMchntDAO.delete(tmpDiv);
//				}
//				// 新增商户分期信息
//				for(TblDivMchnt tmpDiv : tblDivMchntList) {
//					tblDivMchntDAO.save(tmpDiv);
//				}
//			}
//			
//			return Constants.SUCCESS_CODE;
//			
//		} else if(TblMchntInfoConstants.MCHNT_ST_STOP_UNCK.equals(tblMchntInfoTmp.getMchntSt())) { // 停用审核通过
//			Log.log("商户停用审核通过");
//			tblMchntInfoTmp.setMchntSt(TblMchntInfoConstants.MCHNT_ST_STOP);
//		} else if(TblMchntInfoConstants.MCHNT_ST_RCV_UNCK.equals(tblMchntInfoTmp.getMchntSt())) { // 恢复审核通过
//			Log.log("商户恢复审核通过");
//			tblMchntInfoTmp.setMchntSt(TblMchntInfoConstants.MCHNT_ST_OK);
//		}
//		
//		BeanUtils.copyProperties(tblMchntInfo, tblMchntInfoTmp);
//		
//		tblMchntInfoDAO.update(tblMchntInfo);
//		tblMchntInfoTmpDAO.update(tblMchntInfoTmp);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T20201BO#back(java.lang.String, java.lang.String)
	 */
	public String back(String mchntId, String refuseInfo,Operator operator) {

//		TblMchntInfoTmp tblMchntInfoTmp = tblMchntInfoTmpDAO.get(mchntId);
//		
//		if(tblMchntInfoTmp == null) {
//			return "没有找到指定的临时商户信息";
//		}
//		
//		// 商户审核退回原因
//		TblMchntRefusePK tblMchntRefusePK = new TblMchntRefusePK(mchntId,CommonFunction.getCurrentDateTime());
//		TblMchntRefuse tblMchntRefuse = new TblMchntRefuse();
//		tblMchntRefuse.setId(tblMchntRefusePK);
//		
//		if(TblMchntInfoConstants.MCHNT_ST_NEW_UNCK.equals(tblMchntInfoTmp.getMchntSt())) { // 添加待审核
//			Log.log("商户添加待审核退回");
//			tblMchntInfoTmp.setMchntSt(TblMchntInfoConstants.MCHNT_ST_NEW_UNCK_BACK);
//			tblMchntRefuse.setRefuseType("添加审核退回");
//		} else if(TblMchntInfoConstants.MCHNT_ST_MODI_UNCK.equals(tblMchntInfoTmp.getMchntSt())) { // 修改待审核
//			Log.log("商户修改待审核退回");
//			tblMchntInfoTmp.setMchntSt(TblMchntInfoConstants.MCHNT_ST_MODI_UNCK_BACK);
//			tblMchntRefuse.setRefuseType("修改审核退回");
//		} else if(TblMchntInfoConstants.MCHNT_ST_STOP_UNCK.equals(tblMchntInfoTmp.getMchntSt())) { // 停用待审核
//			Log.log("商户停用待审核退回");
//			tblMchntInfoTmp.setMchntSt(TblMchntInfoConstants.MCHNT_ST_OK);
//			tblMchntRefuse.setRefuseType("停用审核退回");
//		} else if(TblMchntInfoConstants.MCHNT_ST_RCV_UNCK.equals(tblMchntInfoTmp.getMchntSt())) { // 恢复待审核
//			Log.log("商户恢复待审核退回");
//			tblMchntInfoTmp.setMchntSt(TblMchntInfoConstants.MCHNT_ST_STOP);
//			tblMchntRefuse.setRefuseType("恢复审核退回");
//		}
//		
//		tblMchntRefuse.setRefuseInfo(refuseInfo);
//		tblMchntRefuse.setBrhId(operator.getOprBrhId());
//		tblMchntRefuse.setOprId(operator.getOprId());
//		tblMchntRefuseDAO.save(tblMchntRefuse);
//		tblMchntInfoTmpDAO.update(tblMchntInfoTmp);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T20201BO#refuse(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public String refuse(String mchntId, String refuseInfo,Operator operator) throws Exception {
		
//		TblMchntInfoTmp tblMchntInfoTmp = tblMchntInfoTmpDAO.get(mchntId);
//		
//		if(tblMchntInfoTmp == null) {
//			return "没有找到指定的临时商户信息";
//		}
//		
//		// 商户审核拒绝原因
//		TblMchntRefusePK tblMchntRefusePK = new TblMchntRefusePK(mchntId,CommonFunction.getCurrentDateTime());
//		TblMchntRefuse tblMchntRefuse = new TblMchntRefuse();
//		tblMchntRefuse.setId(tblMchntRefusePK);
//		
//		if(TblMchntInfoConstants.MCHNT_ST_NEW_UNCK.equals(tblMchntInfoTmp.getMchntSt())) { // 添加待审核
//			Log.log("商户添加审核拒绝");
//			tblMchntRefuse.setRefuseType("添加审核拒绝");
//			tblMchntRefuse.setRefuseInfo(refuseInfo);
//			tblMchntRefuse.setBrhId(operator.getOprBrhId());
//			tblMchntRefuse.setOprId(operator.getOprId());
//			tblMchntRefuseDAO.save(tblMchntRefuse);
//			tblMchntInfoTmpDAO.delete(tblMchntInfoTmp);
//		} else if(TblMchntInfoConstants.MCHNT_ST_MODI_UNCK.equals(tblMchntInfoTmp.getMchntSt())) { // 修改待审核
//			Log.log("商户修改审核拒绝");
//			tblMchntRefuse.setRefuseType("修改审核拒绝");
//			tblMchntRefuse.setRefuseInfo(refuseInfo);
//			tblMchntRefuse.setBrhId(operator.getOprBrhId());
//			tblMchntRefuse.setOprId(operator.getOprId());
//			
//			/**
//			 * 修改审核拒绝，需要恢复商户所有的正式信息到商户临时信息中，
//			 * 这里包括商户的基本信息和商户的分期信息
//			 */
//			
//			TblMchntInfo tblMchntInfo = tblMchntInfoDAO.get(mchntId);
//			
//			BeanUtils.copyProperties(tblMchntInfoTmp, tblMchntInfo);
//			
//			String hql = "from com.allinfinance.po.TblDivMchntTmp t where t.Id.MchtId = '" + mchntId + "'";
//			
//			List<TblDivMchntTmp> tblDivMchntTmpDeleteList = commQueryDAO.findByHQLQuery(hql);
//			
//			hql = "from com.allinfinance.po.TblDivMchnt t where t.Id.MchtId = '" + mchntId + "'";
//			
//			List<TblDivMchnt> tblDivMchntList = commQueryDAO.findByHQLQuery(hql);
//			
//			// 删除商户临时分期信息
//			for(TblDivMchntTmp tblDivMchntTmp : tblDivMchntTmpDeleteList) {
//				tblDivMchntTmpDAO.delete(tblDivMchntTmp);
//			}
//			
//			TblDivMchntTmp tblDivMchntTmp = null;
//			
//			// 商户正式分期信息拷贝到临时商户分期信息中
//			for(TblDivMchnt tblDivMchnt : tblDivMchntList) {
//				tblDivMchntTmp = new TblDivMchntTmp(new TblDivMchntTmpPK(tblDivMchnt.getId().getMchtId(),
//																		 tblDivMchnt.getId().getDivNo()),
//																		 tblDivMchnt.getProductCode());
//				tblDivMchntTmpDAO.save(tblDivMchntTmp);
//			}
//			
//			tblMchntInfoTmpDAO.update(tblMchntInfoTmp);
//			
//			tblMchntRefuseDAO.save(tblMchntRefuse);
//		}
		
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblMchntInfoDAO
	 */
	public TblMchntInfoDAO getTblMchntInfoDAO() {
		return tblMchntInfoDAO;
	}

	/**
	 * @param tblMchntInfoDAO the tblMchntInfoDAO to set
	 */
	public void setTblMchntInfoDAO(TblMchntInfoDAO tblMchntInfoDAO) {
		this.tblMchntInfoDAO = tblMchntInfoDAO;
	}

	/**
	 * @return the tblDivMchntDAO
	 */
	public TblDivMchntDAO getTblDivMchntDAO() {
		return tblDivMchntDAO;
	}

	/**
	 * @param tblDivMchntDAO the tblDivMchntDAO to set
	 */
	public void setTblDivMchntDAO(TblDivMchntDAO tblDivMchntDAO) {
		this.tblDivMchntDAO = tblDivMchntDAO;
	}

	/**
	 * @return the tblMchntInfoTmpDAO
	 */
	public TblMchntInfoTmpDAO getTblMchntInfoTmpDAO() {
		return tblMchntInfoTmpDAO;
	}

	/**
	 * @param tblMchntInfoTmpDAO the tblMchntInfoTmpDAO to set
	 */
	public void setTblMchntInfoTmpDAO(TblMchntInfoTmpDAO tblMchntInfoTmpDAO) {
		this.tblMchntInfoTmpDAO = tblMchntInfoTmpDAO;
	}

	/**
	 * @return the tblDivMchntTmpDAO
	 */
	public TblDivMchntTmpDAO getTblDivMchntTmpDAO() {
		return tblDivMchntTmpDAO;
	}

	/**
	 * @param tblDivMchntTmpDAO the tblDivMchntTmpDAO to set
	 */
	public void setTblDivMchntTmpDAO(TblDivMchntTmpDAO tblDivMchntTmpDAO) {
		this.tblDivMchntTmpDAO = tblDivMchntTmpDAO;
	}

	/**
	 * @return the tblMchntRefuseDAO
	 */
	public TblMchntRefuseDAO getTblMchntRefuseDAO() {
		return tblMchntRefuseDAO;
	}

	/**
	 * @param tblMchntRefuseDAO the tblMchntRefuseDAO to set
	 */
	public void setTblMchntRefuseDAO(TblMchntRefuseDAO tblMchntRefuseDAO) {
		this.tblMchntRefuseDAO = tblMchntRefuseDAO;
	}

	/**
	 * @return the commQueryDAO
	 */
	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	/**
	 * @param commQueryDAO the commQueryDAO to set
	 */
	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.allinfinance.bo.mchnt.T20201BO#get(java.lang.String)
	 */
	public TblMchtBaseInf get(String key) {
		return tblMchntInfoDAO.get(key);
	}
}
