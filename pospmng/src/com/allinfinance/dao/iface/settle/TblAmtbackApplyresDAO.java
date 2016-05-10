package com.allinfinance.dao.iface.settle;

import com.allinfinance.po.TblAmtbackApplyPKres;
import com.allinfinance.po.TblAmtbackApplyres;


public interface TblAmtbackApplyresDAO {
	public com.allinfinance.po.TblAmtbackApplyres get(TblAmtbackApplyPKres tblAmtbackApplyPKres);

	public void save(TblAmtbackApplyres tblAmtbackApplyres);
	

	public void update(com.allinfinance.po.TblAmtbackApplyres tblAmtbackApplyres);





}