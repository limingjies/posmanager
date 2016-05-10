
package com.allinfinance.bo.base;

import java.util.List;

import com.allinfinance.po.TblCardRoute;
import com.allinfinance.po.TblCardRoutePK;

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
public interface T10209BO {
	/**
	 * 查询系统参数信息
	 * @param id    系统参数编号
	 * @return
	 */
	public TblCardRoute get(TblCardRoutePK id);
	/**
	 * 添加系统参数信息
	 * @param tblCardRoute    系统参数信息
	 * @return
	 */
	public String add(TblCardRoute tblCardRoute);
	/**
	 * 批量更新系统参数信息
	 * @param tblCardRouteList    系统参数信息集合
	 * @return
	 */
	public String update(List<TblCardRoute> tblCardRouteList);
	/**
	 * 删除系统参数信息
	 * @param tblCardRoute    系统参数
	 * @return
	 */
	public String delete(TblCardRoute tblCardRoute);
	/**
	 * 删除系统参数信息
	 * @param id    系统参数编号
	 * @return
	 */
	public String delete(TblCardRoutePK id);
	
}
