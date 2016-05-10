package com.allinfinance.dao.iface.settle;

import com.allinfinance.po.TblAmtbackApplyPK;


public interface TblAmtbackApplyDAO {
	public com.allinfinance.po.TblAmtbackApply get(TblAmtbackApplyPK tblAmtbackApplyPK);

	public void save(com.allinfinance.po.TblAmtbackApply tblAmtbackApply);

	public void update(com.allinfinance.po.TblAmtbackApply tblAmtbackApply);

	public void delete(TblAmtbackApplyPK tblAmtbackApplyPK);



}