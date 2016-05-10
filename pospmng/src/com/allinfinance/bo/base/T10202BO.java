
package com.allinfinance.bo.base;

import java.util.List;

import com.allinfinance.po.CstSysParam;
import com.allinfinance.po.CstSysParamPK;

/**
 * Title: 系统参数BO
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
public interface T10202BO {
	/**
	 * 查询系统参数信息
	 * @param id    系统参数编号
	 * @return
	 */
	public CstSysParam get(CstSysParamPK id);
	/**
	 * 添加系统参数信息
	 * @param cstSysParam    系统参数信息
	 * @return
	 */
	public String add(CstSysParam cstSysParam);
	/**
	 * 批量更新系统参数信息
	 * @param cstSysParamList    系统参数信息集合
	 * @return
	 */
	public String update(List<CstSysParam> cstSysParamList);
	/**
	 * 删除系统参数信息
	 * @param cstSysParam    系统参数
	 * @return
	 */
	public String delete(CstSysParam cstSysParam);
	/**
	 * 删除系统参数信息
	 * @param id    系统参数编号
	 * @return
	 */
	public String delete(CstSysParamPK id);
	
}
