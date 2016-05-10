package com.allinfinance.bo.settle;

import com.allinfinance.po.settle.TblPayTypeSmall;

/**
 * 
 * Title: 中信小额支付维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2013-12-01
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * author: 徐鹏飞
 *  
 * time: 2015年4月13日上午10:44:23
 * 
 * version: 1.0
 */
public interface T80904BO {
	/**
	 * 获取中信小额支付卡信息
	 * @param key
	 * @return
	 */
	public TblPayTypeSmall get(String key);
	/**
	 * 添加中信小额支付卡信息
	 * @param tblPayTypeSmall
	 * @return
	 * @throws Exception
	 */
	public String add(TblPayTypeSmall tblPayTypeSmall) throws Exception;
	/**
	 * 删除中信小额支付卡信息
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String delete(String key) throws Exception;
}
