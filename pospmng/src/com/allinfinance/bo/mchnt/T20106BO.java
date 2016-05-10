package com.allinfinance.bo.mchnt;

import com.allinfinance.po.mchnt.TblMchtName;
import com.allinfinance.po.mchnt.TblMchtNamePK;



public interface T20106BO {

	public TblMchtName get(TblMchtNamePK tblMchtNamePK);

	public String save(TblMchtName tblMchtName);
	
	public String update(TblMchtName tblMchtName);


	public String delete(TblMchtNamePK tblMchtNamePK);
}
