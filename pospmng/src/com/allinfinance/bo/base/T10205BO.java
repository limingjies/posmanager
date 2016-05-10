package com.allinfinance.bo.base;

import java.util.List;

import com.allinfinance.po.TblBankBinInf;




/**
 * Title:卡bin参数维护表更改后的数据库操作
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
public interface T10205BO {
	public TblBankBinInf get(Integer id); // 获取卡BIN信息

	public void createTblBankBinInf(TblBankBinInf tblBankBinInf);// 创建卡BIN信息

	public void update(TblBankBinInf tblBankBinInf); // 更新卡BIN信息

	public void delete(Integer id);// 删除卡BIN信息
	
	public void update(List<TblBankBinInf> tblBankBinInfList); // 更新卡BIN信息
	
	public String upload(List<TblBankBinInf> tblBankBinInfList);
	
}
