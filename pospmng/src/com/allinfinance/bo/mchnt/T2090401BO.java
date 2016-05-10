package com.allinfinance.bo.mchnt;

import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmpTmp;


/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-20
 * 
 * Company: Shanghai Tonglian 
 * 
 * @author xupengfei
 * 
 * @version 1.0
 */
public interface T2090401BO {

	public String updateTmp(TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp,
			TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmp,
			TblMchtBaseInfTmp tblMchtBaseInfTmp, 
			TblMchtSettleInfTmp tblMchtSettleInfTmp);
	
}
