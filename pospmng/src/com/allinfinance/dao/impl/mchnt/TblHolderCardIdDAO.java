package com.allinfinance.dao.impl.mchnt;

import org.hibernate.HibernateException;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.mchnt.TblHolderCardId;
import com.allinfinance.po.mchnt.TblHolderCardIdPK;

public class TblHolderCardIdDAO extends _RootDAO<com.allinfinance.po.mchnt.TblHolderCardId>
		implements com.allinfinance.dao.iface.mchnt.TblHolderCardIdDAO{

	public TblHolderCardId load(TblHolderCardIdPK id)
		throws org.hibernate.HibernateException {
			return (com.allinfinance.po.mchnt.TblHolderCardId) load(getReferenceClass(),id);
	}
	@Override
	protected Class<com.allinfinance.po.mchnt.TblHolderCardId> getReferenceClass() {
		// TODO Auto-generated method stub
		return com.allinfinance.po.mchnt.TblHolderCardId.class;
	}

	public void delete(TblHolderCardIdPK id) throws HibernateException {
		// TODO Auto-generated method stub
		super.delete(load(id));
	}

	public TblHolderCardId get(TblHolderCardIdPK id) throws HibernateException {
		// TODO Auto-generated method stub
		return (com.allinfinance.po.mchnt.TblHolderCardId) get(getReferenceClass(),
				id);
	}

	public void save(TblHolderCardId tblHolderCardId) throws HibernateException {
		// TODO Auto-generated method stub
		super.save(tblHolderCardId);
	}

	public void update(TblHolderCardId tblHolderCardId) {
		// TODO Auto-generated method stub
		super.update(tblHolderCardId);
	}

}
