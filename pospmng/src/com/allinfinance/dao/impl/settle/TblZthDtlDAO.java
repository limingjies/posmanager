package com.allinfinance.dao.impl.settle;


import java.util.Iterator;
import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.settle.TblZthDtl;
import com.allinfinance.po.settle.TblZthDtlPK;


public class TblZthDtlDAO extends _RootDAO<TblZthDtl> implements com.allinfinance.dao.iface.settle.TblZthDtlDAO {



	@Override
	protected Class<TblZthDtl> getReferenceClass() {
		// TODO Auto-generated method stub
		return com.allinfinance.po.settle.TblZthDtl.class;
	}
	public com.allinfinance.po.settle.TblZthDtl cast (Object object) {
		return (com.allinfinance.po.settle.TblZthDtl) object;
	}
	@Override
	public void delete(TblZthDtlPK id) {
		// TODO Auto-generated method stub
		super.delete((Object) load(id));
	}

	@Override
	public TblZthDtl get(TblZthDtlPK key) {
		// TODO Auto-generated method stub
		return (com.allinfinance.po.settle.TblZthDtl) get(getReferenceClass(), key);
	}

	@Override
	public TblZthDtl load(TblZthDtlPK key) {
		// TODO Auto-generated method stub
		return (com.allinfinance.po.settle.TblZthDtl) load(getReferenceClass(), key);
	}

	@Override
	public void update(TblZthDtl tblZthDtl) {
		super.update(tblZthDtl);
	}
	@Override
	public void save(TblZthDtl tblZthDtl) {
		super.save(tblZthDtl);
	}
	@Override
	public void saveList(List<TblZthDtl> dataList) {
		// TODO Auto-generated method stub
		Iterator<TblZthDtl> it = dataList.iterator();
		while(it.hasNext())
		{
			this.getHibernateTemplate().save(it.next());
		}
	}
	
	

}