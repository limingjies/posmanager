
package com.allinfinance.bo.term;

import java.util.List;

import com.allinfinance.po.TblTermKey;
import com.allinfinance.po.TblTermKeyPK;

/**
 * Title: 终端密钥BO
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-9
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public interface TblTermKeyBO {
	/**
	 * 查询终端密钥信息
	 * @param id    终端密钥编号
	 * @return
	 */
	public TblTermKey get(TblTermKeyPK id);
	/**
	 * 添加终端密钥信息
	 * @param tblTermKey    终端密钥信息
	 * @return
	 */
	public String add(TblTermKey tblTermKey);
	/**
	 * 批量更新终端密钥信息
	 * @param tblTermKeyList    终端密钥信息集合
	 * @return
	 */
	public String update(List<TblTermKey> tblTermKeyList);
	/**
	 * 删除终端密钥信息
	 * @param tblTermKey    终端密钥
	 * @return
	 */
	public String delete(TblTermKey tblTermKey);
	/**
	 * 删除终端密钥信息
	 * @param id    终端密钥编号
	 * @return
	 */
	public String delete(TblTermKeyPK id);
	
	public void saveOrUpdate(TblTermKey tblTermKey);
	
}
