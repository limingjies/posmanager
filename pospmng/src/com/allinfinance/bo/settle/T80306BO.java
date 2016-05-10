package com.allinfinance.bo.settle;

import com.allinfinance.po.TblAmtbackApplyPKmis;
import com.allinfinance.po.TblAmtbackApplyPKres;
import com.allinfinance.po.TblAmtbackApplymis;
import com.allinfinance.po.TblAmtbackApplyres;



public interface T80306BO {

	public TblAmtbackApplyres get(TblAmtbackApplyPKres tblAmtbackApplyPKres);
	
	public TblAmtbackApplymis get(TblAmtbackApplyPKmis tblAmtbackApplyPKmis);
	
	public String save(TblAmtbackApplyres tblAmtbackApplyres);
	
	public String update(TblAmtbackApplyres tblAmtbackApplyres);


}