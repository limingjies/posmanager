package com.allinfinance.dao.impl.base;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblRuleMchtRel;
import com.allinfinance.po.TblRuleMchtRelPK;

public class TblRuleMchtRelDAO extends _RootDAO<com.allinfinance.po.TblRuleMchtRel>
		implements com.allinfinance.dao.iface.base.TblRuleMchtRelDAO {
	

	@Override
	protected Class<TblRuleMchtRel> getReferenceClass() {
		// TODO Auto-generated method stub
		return com.allinfinance.po.TblRuleMchtRel.class;
	}

	public com.allinfinance.po.TblRuleMchtRel load(TblRuleMchtRelPK tblRuleMchtRelPK)
	{
		return (com.allinfinance.po.TblRuleMchtRel) load(getReferenceClass(), tblRuleMchtRelPK);
	}
	@Override
	public void delete(TblRuleMchtRelPK tblRuleMchtRelPK) {
		// TODO Auto-generated method stub
		super.delete((Object) load(tblRuleMchtRelPK));
	}

	@Override
	public void delete(TblRuleMchtRel tblRuleMchtRel) {
		// TODO Auto-generated method stub
		super.delete((Object) tblRuleMchtRel);
	}

	@Override
	public TblRuleMchtRel get(TblRuleMchtRelPK tblRuleMchtRelPK) {
		// TODO Auto-generated method stub
		return (com.allinfinance.po.TblRuleMchtRel) get(getReferenceClass(), tblRuleMchtRelPK);
	}

	@Override
	public void save(TblRuleMchtRel tblRuleMchtRel) {
		// TODO Auto-generated method stub
		super.save(tblRuleMchtRel);
	}

	@Override
	public void update(TblRuleMchtRel tblRuleMchtRel) {
		// TODO Auto-generated method stub
		super.update(tblRuleMchtRel);
	}
}
