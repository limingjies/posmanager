package com.allinfinance.bo.risk;

import com.allinfinance.po.TblRiskBlackMcht;

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
public interface T402021BO {
	/**
	 * 添加商户黑名单信息
	 * @param tblCtlMchtInf
	 * @return
	 * @throws Exception
	 * 2010-8-26下午11:33:52
	 */
	public String add(TblRiskBlackMcht tblRiskBlackMcht) throws Exception;
	/**
	 * 更新商户黑名单信息
	 * @param tblCtlMchtInf
	 * @return
	 * @throws Exception
	 * 2010-8-26下午11:34:46
	 */
	public String update(TblRiskBlackMcht tblRiskBlackMcht) throws Exception;
	/**
	 * 查询商户黑名单信息
	 * @param key
	 * @return
	 * 2010-8-26下午11:35:16
	 */
	public TblRiskBlackMcht get(String key);
	
	
	public String delete(String key) throws Exception;
}
