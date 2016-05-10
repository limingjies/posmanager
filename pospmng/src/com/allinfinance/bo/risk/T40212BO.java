package com.allinfinance.bo.risk;

import com.allinfinance.common.Operator;
import com.allinfinance.po.risk.TblBankCardBlack;

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
public interface T40212BO {
	/**
	 * 查询银行卡黑名单信息
	 * @param key
	 * @return
	 * 2010-8-26下午11:35:16
	 */
	public TblBankCardBlack get(String key);
	/**
	 * 添加银行卡黑名单信息
	 * @param tblCtlMchtInf
	 * @return
	 * @throws Exception
	 * 2010-8-26下午11:33:52
	 */
	public String add(TblBankCardBlack tblBankCardBlack) throws Exception;
	/**
	 * 删除银行卡黑名单信息
	 * @param key
	 * @return
	 * 2010-8-26下午11:35:16
	 */
	public String delete(String key,Operator operator) throws Exception;
}
