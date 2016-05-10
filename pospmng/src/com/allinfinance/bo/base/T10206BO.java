package com.allinfinance.bo.base;

import java.util.List;

import com.allinfinance.po.base.TblEmvPara;
import com.allinfinance.po.base.TblEmvParaPK;

/**
 * Title:角色信息BO
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
public interface T10206BO {
	public TblEmvPara get(TblEmvParaPK id); // 获取卡BIN信息

	public void createTblEmvPara(TblEmvPara tblEmvPara);// 创建卡BIN信息

	public void update(TblEmvPara tblEmvPara); // 更新卡BIN信息

	public void delete(TblEmvParaPK id);// 删除卡BIN信息
	
	public void update(List<TblEmvPara> tblEmvParaList); // 更新卡BIN信息
}
