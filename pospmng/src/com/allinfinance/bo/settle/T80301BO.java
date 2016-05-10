package com.allinfinance.bo.settle;

import com.allinfinance.po.BthGcTxnSucc;
import com.allinfinance.po.BthGcTxnSuccPK;
import com.allinfinance.po.TblAmtbackApply;
import com.allinfinance.po.TblAmtbackApplyPK;



public interface T80301BO {

	public TblAmtbackApply get(TblAmtbackApplyPK tblAmtbackApplyPK);

	public String save(TblAmtbackApply tblAmtbackApply);
	
	public String update(TblAmtbackApply tblAmtbackApply);


	public String delete(TblAmtbackApplyPK tblAmtbackApplyPK);
	
	public BthGcTxnSucc getBthGcTxnSucc(BthGcTxnSuccPK bthGcTxnSuccPK);
}
