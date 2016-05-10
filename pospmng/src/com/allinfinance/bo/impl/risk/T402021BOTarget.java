/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-26       first release
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
package com.allinfinance.bo.impl.risk;

import com.allinfinance.bo.risk.T402021BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.TblMchntInfoConstants;
import com.allinfinance.dao.iface.risk.TblRiskBlackMchtDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtBaseInfDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtBaseInfTmpDAO;
import com.allinfinance.po.TblRiskBlackMcht;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;

/**
 * Title: 商户黑名单管理
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-26
 * 
 * Company: Shanghai Allinfinance Software Systems Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class T402021BOTarget implements T402021BO {

	private TblRiskBlackMchtDAO tblRiskBlackMchtDAO;
	private TblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO;
	private TblMchtBaseInfDAO tblMchtBaseInfDAO;

	public String add(TblRiskBlackMcht tblRiskBlackMcht) throws Exception {
		tblRiskBlackMchtDAO.save(tblRiskBlackMcht);
		
		TblMchtBaseInfTmp tblMchtBaseInfTmp =tblMchtBaseInfTmpDAO.get(tblRiskBlackMcht.getMchtNo());
		TblMchtBaseInf tblMchtBaseInf =tblMchtBaseInfDAO.get(tblRiskBlackMcht.getMchtNo());
		tblMchtBaseInfTmp.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_DEL);
		tblMchtBaseInf.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_DEL);
		tblMchtBaseInfTmpDAO.save(tblMchtBaseInfTmp);
		tblMchtBaseInfDAO.save(tblMchtBaseInf);
		return Constants.SUCCESS_CODE;
	}

	public TblRiskBlackMcht get(String key) {
		return tblRiskBlackMchtDAO.get(key);
	}

	
	public String update(TblRiskBlackMcht tblRiskBlackMcht) throws Exception {
		tblRiskBlackMchtDAO.update(tblRiskBlackMcht);
		return Constants.SUCCESS_CODE;
	}
	
	
	public String delete(String key) {
		tblRiskBlackMchtDAO.delete(key);
		TblMchtBaseInfTmp tblMchtBaseInfTmp =tblMchtBaseInfTmpDAO.get(key);
		TblMchtBaseInf tblMchtBaseInf =tblMchtBaseInfDAO.get(key);
		tblMchtBaseInfTmp.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_OK);
		tblMchtBaseInf.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_OK);
		tblMchtBaseInfTmpDAO.save(tblMchtBaseInfTmp);
		tblMchtBaseInfDAO.save(tblMchtBaseInf);
		return Constants.SUCCESS_CODE;
	}

	public TblRiskBlackMchtDAO getTblRiskBlackMchtDAO() {
		return tblRiskBlackMchtDAO;
	}

	public void setTblRiskBlackMchtDAO(TblRiskBlackMchtDAO tblRiskBlackMchtDAO) {
		this.tblRiskBlackMchtDAO = tblRiskBlackMchtDAO;
	}

	public TblMchtBaseInfTmpDAO getTblMchtBaseInfTmpDAO() {
		return tblMchtBaseInfTmpDAO;
	}

	public void setTblMchtBaseInfTmpDAO(TblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO) {
		this.tblMchtBaseInfTmpDAO = tblMchtBaseInfTmpDAO;
	}

	public TblMchtBaseInfDAO getTblMchtBaseInfDAO() {
		return tblMchtBaseInfDAO;
	}

	public void setTblMchtBaseInfDAO(TblMchtBaseInfDAO tblMchtBaseInfDAO) {
		this.tblMchtBaseInfDAO = tblMchtBaseInfDAO;
	}
	
	
}
