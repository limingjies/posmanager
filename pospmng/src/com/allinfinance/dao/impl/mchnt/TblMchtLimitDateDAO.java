package com.allinfinance.dao.impl.mchnt;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.TblMchtLimitDate;

public class TblMchtLimitDateDAO extends
		_RootDAO<com.allinfinance.po.TblMchtLimitDate> implements
		com.allinfinance.dao.iface.mchnt.ITblMchtLimitDateDAO {

	public TblMchtLimitDateDAO () {}
	
	@Override
	public void saveOrUpdate(TblMchtLimitDate tblMchtLimitDate)
	{
		super.saveOrUpdate(tblMchtLimitDate);

	}

	@Override
	public void save(TblMchtLimitDate tblMchtLimitDate)
	{
		super.save(tblMchtLimitDate);

	}

	@Override
	protected Class<com.allinfinance.po.TblMchtLimitDate> getReferenceClass() {
		return com.allinfinance.po.TblMchtLimitDate.class;
	}

	@Override
	public com.allinfinance.po.TblMchtLimitDate get(String id) {
		
		return (com.allinfinance.po.TblMchtLimitDate) super.get(getReferenceClass(), id);
	}

}
