package com.allinfinance.bo.mchnt;

import java.util.List;

import com.allinfinance.po.mchnt.TblInfMchntTpGrp;
import com.allinfinance.po.mchnt.TblInfMchntTpGrpPK;



/**
 * Title:商户组别维护BO
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-10
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public interface T20302BO {
	
	/**
	 * 添加商户组别信息
	 * @param tblMchntTpGrp    商户组别
	 * @return
	 */
	public String add(TblInfMchntTpGrp tblMchntTpGrp);
	
	/**
	 * 查询商户组别信息
	 * @param mchntTpGrp    商户组别编号
	 * @return
	 */
	public TblInfMchntTpGrp get(TblInfMchntTpGrpPK id);
	
	/**
	 * 删除商户组别信息
	 * @param mchntTpGrp    商户组别编号
	 * @return
	 */
	public String delete(TblInfMchntTpGrpPK id);
	
	/**
	 * 删除商户组别信息
	 * @param tblMchntTpGrp    商户组别信息
	 * @return
	 */
	public String delete(TblInfMchntTpGrp tblMchntTpGrp);
	
	/**
	 * 更新商户组别信息
	 * @param tblMchntTpGrp    商户组别信息
	 * @return
	 */
	public String update(TblInfMchntTpGrp TblInfMchntTpGrp);
	
	/**
	 * 批量更新商户组别
	 * @param tblMchntTpGrpList    商户组别信息集合
	 * @return
	 */
	public String update(List<TblInfMchntTpGrp> tblMchntTpGrpList);
}
