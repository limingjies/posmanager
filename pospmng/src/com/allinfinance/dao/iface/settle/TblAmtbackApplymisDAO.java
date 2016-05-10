package com.allinfinance.dao.iface.settle;

import com.allinfinance.po.TblAmtbackApplyPKmis;



public interface TblAmtbackApplymisDAO {
	public  com.allinfinance.po.TblAmtbackApplymis get(TblAmtbackApplyPKmis tblAmtbackApplyPKmis);
	
	
	public void save(com.allinfinance.po.TblAmtbackApplymis tblAmtbackApplymis);
	

	public void update(com.allinfinance.po.TblAmtbackApplymis tblAmtbackApplymis);

	public void delete(TblAmtbackApplyPKmis tblAmtbackApplyPKmis);



}