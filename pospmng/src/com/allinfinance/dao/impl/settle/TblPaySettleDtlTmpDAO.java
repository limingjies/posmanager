package com.allinfinance.dao.impl.settle;

import java.util.Iterator;
import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.settle.TblPaySettleDtlPK;
import com.allinfinance.po.settle.TblPaySettleDtlTmp;
import com.allinfinance.po.settle.TblPaySettleDtlTmpPK;


public class TblPaySettleDtlTmpDAO extends _RootDAO<TblPaySettleDtlTmp> implements com.allinfinance.dao.iface.settle.TblPaySettleDtlTmpDAO {



	public Class<TblPaySettleDtlTmp> getReferenceClass () {
		return TblPaySettleDtlTmp.class;
	}

	public TblPaySettleDtlTmp cast (Object object) {
		return (TblPaySettleDtlTmp) object;
	}



	public TblPaySettleDtlTmp get(TblPaySettleDtlTmpPK tblPaySettleDtlPK)
	{
		return (TblPaySettleDtlTmp) get(getReferenceClass(), tblPaySettleDtlPK);
	}


	public void update(TblPaySettleDtlTmp tblPaySettleDtl)
	{
		super.update(tblPaySettleDtl);
	}
	
	public void save(TblPaySettleDtlTmp tblPaySettleDtl)
	{
		super.save(tblPaySettleDtl);
	}
	
	public void saveList(List<TblPaySettleDtlTmp> dataList) {
		// TODO Auto-generated method stub
		Iterator<TblPaySettleDtlTmp> it = dataList.iterator();
		while(it.hasNext())
		{
			this.getHibernateTemplate().saveOrUpdate(it.next());
		}
	}

}