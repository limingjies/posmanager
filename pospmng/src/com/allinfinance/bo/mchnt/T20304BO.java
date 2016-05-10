package com.allinfinance.bo.mchnt;

import java.util.List;

import com.allinfinance.po.mchnt.CstMchtFeeInf;
import com.allinfinance.po.mchnt.CstMchtFeeInfPK;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-15
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author liuxianxian
 * 
 * @version 1.0
 */
public interface T20304BO {
	public String addMchtLimit(CstMchtFeeInf cstMchtFeeInf);
	
	public String updateMchtLimit(CstMchtFeeInf cstMchtFeeInf);
	
	public CstMchtFeeInf getMchtLimit(CstMchtFeeInfPK cstMchtFeeInfPK);
	public void delete(CstMchtFeeInfPK id);
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10401BO#update(java.util.List)
	 */
	public String update(List<CstMchtFeeInf> cstMchtFeeInfList) ;

}
