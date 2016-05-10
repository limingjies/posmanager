/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   Gavin      2011-6-17       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2011 allinfinance Software, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai allinfinance Software Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with allinfinance.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.allinfinance.bo.mchnt;

import java.lang.reflect.InvocationTargetException;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;

import com.allinfinance.bo.mchnt.T20901BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.TblMchntInfoConstants;
import com.allinfinance.dao.iface.mchnt.ITblGroupMchtInfDAO;
import com.allinfinance.dao.iface.mchnt.ITblGroupMchtSettleInfDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfTmpDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtSettleInfDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtSettleInfTmpDAO;
import com.allinfinance.dao.iface.mchnt.TblMchntRefuseDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtBaseInfPosfDAO;
import com.allinfinance.po.TblMchntRefuse;
import com.allinfinance.po.TblMchntRefusePK;
import com.allinfinance.po.mchnt.TblGroupMchtInf;
import com.allinfinance.po.mchnt.TblGroupMchtSettleInf;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInf;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmp;
/*import com.allinfinance.po.TblMchntRefuse;
import com.allinfinance.po.TblMchntRefusePK;
import com.allinfinance.po.mchnt.TblGroupMchtInf;
import com.allinfinance.po.mchnt.TblGroupMchtSettleInf;
import com.allinfinance.po.TblMchtBaseInf;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInf;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmp;*/
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.StatusUtil;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-17
 * 
 * Company: Shanghai allinfinance Software Systems Co., Ltd.
 * 
 * @author Gavin
 * 
 * @version 1.0
 */
public class TblMchntPosfServiceImpl {
	
	public TblMchtBaseInfPosfDAO tblMchtBaseInfPosfDAO;
	
	/**
	 * @return the tblMchtBaseInfPosfDAO
	 */
	public TblMchtBaseInfPosfDAO getTblMchtBaseInfPosfDAO() {
		return tblMchtBaseInfPosfDAO;
	}

	/**
	 * @param tblMchtBaseInfPosfDAO the tblMchtBaseInfPosfDAO to set
	 */
	public void setTblMchtBaseInfPosfDAO(TblMchtBaseInfPosfDAO tblMchtBaseInfPosfDAO) {
		this.tblMchtBaseInfPosfDAO = tblMchtBaseInfPosfDAO;
	}




	public TblMchtBaseInf getMccByMchntId(String mchntId)
			throws IllegalAccessException, InvocationTargetException {
		if(StringUtil.isNull(mchntId)){
			return null;
		}
		TblMchtBaseInf inf = tblMchtBaseInfPosfDAO.get(mchntId);
		if (null == inf) {
			return null;
		} else {
			return inf;
		}
	}

	
	public TblMchtBaseInf getBaseInf(String mchntId)
			throws IllegalAccessException, InvocationTargetException {
		tblMchtBaseInfPosfDAO.get(mchntId);
		return tblMchtBaseInfPosfDAO.get(mchntId);
	}
	public void deleteBaseInf(String mchntId){
		tblMchtBaseInfPosfDAO.delete(mchntId);
	}

	/**
	 * @param baseInfTmp
	 * @param mchntId
	 * 2015年9月23日 下午3:24:35
	 * @author cuihailong
	 * @param baseInf 
	 */
	public void saveAndDelete(TblMchtBaseInfTmp baseInfTmp, String mchntId,TblMchtBaseInfTmpTmp baseInfTmpTmp, TblMchtBaseInf baseInf) {
		T20901BO t20901BO = (T20901BO) ContextUtil.getBean("T20901BO");
		t20901BO.update(baseInfTmp,baseInfTmpTmp);
		tblMchtBaseInfPosfDAO.update(baseInf);
	};
	/*public String updateBaseInfTmp(TblMchtBaseInfTmp inf)
			throws IllegalAccessException, InvocationTargetException {
		
		tblMchtBaseInfTmpDAO.update(inf);
		
		return Constants.SUCCESS_CODE;
	}


	public boolean getMchntStatus(String mchntId) throws IllegalAccessException,
			InvocationTargetException {
		TblMchtBaseInf tblMchtBaseInf = tblMchtBaseInfDAO.get(mchntId);
		if(tblMchtBaseInf == null)
			return false;
		if(!tblMchtBaseInf.getMchtStatus().equals(TblMchntInfoConstants.MCHNT_ST_OK))
			return false;
		return true;
	}*/


}
