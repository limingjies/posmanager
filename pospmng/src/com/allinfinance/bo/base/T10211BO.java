package com.allinfinance.bo.base;

import com.allinfinance.po.TblRuleMchtRel;
import com.allinfinance.po.TblRuleMchtRelPK;

public interface T10211BO {

	public TblRuleMchtRel get(TblRuleMchtRelPK key);
	 
	public String save(TblRuleMchtRel tblRuleMchtRel);

	
	public String update(TblRuleMchtRel tblRuleMchtRel);
	
	
	public String delete(TblRuleMchtRelPK key);

	
	public String delete(TblRuleMchtRel tblRuleMchtRel);
	
	
	public String deleteMain(String ruleCode);
	public String updateMain(TblRuleMchtRel tblRuleMchtRel);
	public String addMain(TblRuleMchtRel tblRuleMchtRel);
	
	
	public String deleteDtl(TblRuleMchtRelPK tblRuleMchtRelPK);

}
