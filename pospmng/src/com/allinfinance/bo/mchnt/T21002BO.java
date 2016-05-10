package com.allinfinance.bo.mchnt;

import com.allinfinance.po.ShTblOprInfo;
import com.allinfinance.po.ShTblOprInfoPk;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2014-1-17
 * 
 * 
 * 
 * @version 1.0
 */
public interface T21002BO {

	/**添加用户信息*/
	public String add(ShTblOprInfo tblOprInfo) throws Exception;
	/**更新用户信息*/
	public String update(ShTblOprInfo tblOprInfo) throws Exception;
	/**注销用户信息*/
	public String delete(ShTblOprInfo tblOprInfo) throws Exception;
	/**获得用户信息*/
	public ShTblOprInfo get(ShTblOprInfoPk key) throws Exception;
}
