package com.allinfinance.bo.base;

import java.util.List;

import com.allinfinance.po.TblOprInfo;

/**
 * Title:操作员BO
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
public interface T10401BO {
	/**
	 * 查询操作员信息
	 * @param oprId    操作员编号
	 * @return
	 */
	public TblOprInfo get(String oprId);
	/**
	 * 新增操作员信息
	 * @param tblOprInfo    操作员信息
	 * @return
	 */
	public String add(TblOprInfo tblOprInfo);
	/**
	 * 批量更新操作员信息
	 * @param tblOprInfoList    操作员信息集合
	 * @return
	 */
	public String update(List<TblOprInfo> tblOprInfoList);
	/**
	 * 更新操作员信息
	 * @param tblOprInfo     操作员信息
	 * @return
	 */
	public String update(TblOprInfo tblOprInfo);
	/**
	 * 删除操作员信息
	 * @param tblOprInfo    操作员信息
	 * @return
	 */
	public String delete(TblOprInfo tblOprInfo);
	/**
	 * 删除操作员信息
	 * @param oprId    操作员编号
	 * @return
	 */
	public String delete(String oprId);
}
