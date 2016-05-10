package com.allinfinance.bo.base;

import java.util.List;

import com.allinfinance.po.TblCityCode;

/**
 * Title:地区码BO
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
public interface T10201BO {
	/**
	 * 查询地区码信息
	 * @param id    地区码编号
	 * @return
	 */
	public TblCityCode get(String id);
	/**
	 * 添加地区码信息
	 * @param tblCityCode    地区码信息
	 * @return
	 */
	public String add(TblCityCode tblCityCode);
	/**
	 * 批量更新地区码信息
	 * @param tblCityCodeList    地区码信息集合
	 * @return
	 */
	public String update(List<TblCityCode> tblCityCodeList);
	/**
	 * 删除地区码信息
	 * @param tblCityCode    地区码信息
	 * @return
	 */
	public String delete(TblCityCode tblCityCode);
	/**
	 * 删除地区码信息
	 * @param id    地区码编号
	 * @return
	 */
	public String delete(String id);
}
