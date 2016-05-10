package com.allinfinance.dao.iface.mchnt;

import com.allinfinance.po.mchnt.TblMchtCupInf;

public interface TblMchtCupInfDAO {

	public TblMchtCupInf get(String mchntNo);
	
	public void save(TblMchtCupInf tblMchtCupInf);
	
	public void update(TblMchtCupInf tblMchtCupInf);
	
	public void delete(String mchntNo);
}
