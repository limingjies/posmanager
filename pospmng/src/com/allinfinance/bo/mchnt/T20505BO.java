package com.allinfinance.bo.mchnt;

import com.allinfinance.po.mchnt.TblFirstMchtTxn;

public interface T20505BO {

	public TblFirstMchtTxn get(String id);

	public String add(TblFirstMchtTxn tblFirstMchtTxn);

	public String update(TblFirstMchtTxn tblFirstMchtTxn);
	
	public String delete(TblFirstMchtTxn tblFirstMchtTxn);
	
	public String delete(String id);

}
