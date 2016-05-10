package com.allinfinance.bo.mchnt;

import java.util.List;

import com.allinfinance.po.mchnt.TblHisDiscAlgo;
import com.allinfinance.po.mchnt.TblInfDiscAlgo;
import com.allinfinance.po.mchnt.TblInfDiscCd;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-20
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author liuxianxian
 * 
 * @version 1.0
 */
public interface T20701BO {

	// 3月10号修改，原函数全部删除掉
	public String createArith(List<TblInfDiscAlgo> list,
			TblInfDiscCd tblInfDiscCd, List<TblHisDiscAlgo> descList);

	public String updateArith(List<TblInfDiscAlgo> list,
			TblInfDiscCd tblInfDiscCd, List<TblHisDiscAlgo> descList);

	public String deleteArith(String discCd);

	public TblInfDiscCd getTblInfDiscCd(String discCd) throws Exception;

}
