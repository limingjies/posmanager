package com.allinfinance.bo.term;

import java.util.List;

import com.allinfinance.po.TblTermZmkInf;
import com.allinfinance.po.TblTermZmkInfPK;

/**
 * Title: ZMKBO
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
public interface TblTermZmkInfBO {
	/**
	 * 查询ZMK信息
	 * @param id    ZMK编号
	 * @return
	 */
	public TblTermZmkInf get(TblTermZmkInfPK id);
	/**
	 * 添加ZMK信息
	 * @param tblTermZmkInf    ZMK信息
	 * @return
	 */
	public String add(TblTermZmkInf tblTermZmkInf);
	/**
	 * 批量更新ZMK信息
	 * @param tblTermZmkInfList    ZMK信息集合
	 * @return
	 */
	public String update(List<TblTermZmkInf> tblTermZmkInfList);
	/**
	 * 删除ZMK信息
	 * @param tblTermZmkInf    ZMK
	 * @return
	 */
	public String delete(TblTermZmkInf tblTermZmkInf);
	/**
	 * 删除ZMK信息
	 * @param id    ZMK编号
	 * @return
	 */
	public String delete(TblTermZmkInfPK id);
	public void saveOrUpdate(TblTermZmkInf tblTermZmkInf);
	
}
