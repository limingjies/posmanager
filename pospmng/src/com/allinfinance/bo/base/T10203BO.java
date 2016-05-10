package com.allinfinance.bo.base;


import com.allinfinance.po.TblTxnName;


/**
 * Title:交易码BO
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
public interface T10203BO {
	/**
	 * 查询交易码信息
	 * @param id    交易码编号
	 * @return
	 */
	public TblTxnName get(String id);
	/**
	 * 添加交易码信息
	 * @param TblFuncInf    交易码信息
	 * @return
	 */
	public String add(TblTxnName tblTxnName);
	/**
	 * 批量更新交易码信息
	 * @param TblTxnNameList    交易码信息集合
	 * @return
	 */
	public String update(TblTxnName tblTxnName);
	/**
	 * 删除交易码信息
	 * @param TblTxnName    交易码信息
	 * @return
	 */
	public String delete(TblTxnName tblTxnName);
	/**
	 * 删除交易码信息
	 * @param id    交易码编号
	 * @return
	 */
	public String delete(String id);
}
