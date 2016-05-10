package com.allinfinance.bo.mchnt;

import com.allinfinance.po.mchnt.TblHolderCardId;
import com.allinfinance.po.mchnt.TblHolderCardIdPK;

public interface T20804BO {

	public TblHolderCardId get(TblHolderCardIdPK id) ;
	
	public  String save(TblHolderCardId tblHolderCardId ) ;
	
	public String update(com.allinfinance.po.mchnt.TblHolderCardId tblHolderCardId);

	public String delete(TblHolderCardIdPK id) ;
}
