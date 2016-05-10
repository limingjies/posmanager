/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-8-20       first release
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
package com.allinfinance.bo.risk;

import com.allinfinance.common.Operator;
import com.allinfinance.po.TblRiskInf;
import com.allinfinance.po.TblRiskParamInf;
import com.allinfinance.po.TblRiskParamInfPK;

/**
 * Title:风险模型设置
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-20
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public interface T40101BO {
	/**
	 * 更新风险模型信息
	 * @param tblRiskInf
	 * @return
	 * @throws Exception
	 * 2010-8-20上午11:40:01
	 */
	public String update(TblRiskParamInf tblRiskParamInfNew, TblRiskParamInf tblRiskParamInfOld, Operator operator) throws Exception;
	
	/**
	 * 查找风险模型
	 * @param key
	 * @return
	 * @throws Exception
	 * 2011-6-17上午10:28:30
	 */
	public TblRiskInf get(String key) throws Exception;
	public TblRiskParamInf get(TblRiskParamInfPK key) throws Exception;
	public String  update(TblRiskInf tblRiskInf, Operator operator,String riskStatus) throws Exception;
	
	public String  updateLvl(TblRiskInf tblRiskInf, Operator operator,String misc) throws Exception;
}
