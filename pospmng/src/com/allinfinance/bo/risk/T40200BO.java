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
import com.allinfinance.po.risk.TblRiskParamDef;
import com.allinfinance.po.risk.TblRiskParamDefPK;

/**
 * Title:风险模型参数定义
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
public interface T40200BO {
	public String update(TblRiskParamDef tblRiskParamDefNew, TblRiskParamDef tblRiskParamDefOld, Operator operator) throws Exception;
	
	public TblRiskParamDef get(TblRiskParamDefPK key) throws Exception;
	
}
