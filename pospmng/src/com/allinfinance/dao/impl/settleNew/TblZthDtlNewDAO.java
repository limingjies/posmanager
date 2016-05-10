package com.allinfinance.dao.impl.settleNew;


import java.util.Iterator;
import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.settleNew.TblZthDtlNew;
import com.allinfinance.po.settleNew.TblZthDtlNewPK;


public class TblZthDtlNewDAO extends _RootDAO<TblZthDtlNew> implements com.allinfinance.dao.iface.settleNew.TblZthDtlNewDAO {



	@Override
	protected Class<TblZthDtlNew> getReferenceClass() {
		return TblZthDtlNew.class;
	}
	public com.allinfinance.po.settle.TblZthDtl cast (Object object) {
		return (com.allinfinance.po.settle.TblZthDtl) object;
	}
	@Override
	public void delete(TblZthDtlNewPK id) {
		super.delete((Object) load(id));
	}

	@Override
	public TblZthDtlNew get(TblZthDtlNewPK key) {
		return (TblZthDtlNew) get(getReferenceClass(), key);
	}

	@Override
	public TblZthDtlNew load(TblZthDtlNewPK key) {
		return (TblZthDtlNew) load(getReferenceClass(), key);
	}

	@Override
	public void update(TblZthDtlNew tblZthDtl) {
		super.update(tblZthDtl);
	}
	@Override
	public void save(TblZthDtlNew tblZthDtl) {
		super.save(tblZthDtl);
	}
	@Override
	public void saveList(List<TblZthDtlNew> dataList) {
		Iterator<TblZthDtlNew> it = dataList.iterator();
		while(it.hasNext())
		{
			this.getHibernateTemplate().save(it.next());
		}
	}
	
	

}