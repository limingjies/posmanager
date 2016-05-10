package com.allinfinance.dao.iface.base;

import com.allinfinance.po.TblRuleMchtRelPK;

public interface TblRuleMchtRelDAO {

	public com.allinfinance.po.TblRuleMchtRel get(TblRuleMchtRelPK tblRuleMchtRelPK);
	 
	public void save(com.allinfinance.po.TblRuleMchtRel tblRuleMchtRel);

	
	public void update(com.allinfinance.po.TblRuleMchtRel tblRuleMchtRel);
	
	
	public void delete(TblRuleMchtRelPK tblRuleMchtRelPK);

	
	public void delete(com.allinfinance.po.TblRuleMchtRel tblRuleMchtRel);
}
