package com.allinfinance.dao.impl.mchnt;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.mchnt.TblHoldMchtParticipat;
import com.allinfinance.po.mchnt.TblHoldMchtParticipatPK;

public class TblHoldMchtParticipatDAO extends _RootDAO<com.allinfinance.po.mchnt.TblHoldMchtParticipat>
	implements com.allinfinance.dao.iface.mchnt.TblHoldMchtParticipatDAO{

	public TblHoldMchtParticipatDAO(){
		
	}
	
	public TblHoldMchtParticipat load(TblHoldMchtParticipatPK id)
		throws org.hibernate.HibernateException {
			return (com.allinfinance.po.mchnt.TblHoldMchtParticipat) load(getReferenceClass(),
					id);
	}
	@Override
	protected Class<com.allinfinance.po.mchnt.TblHoldMchtParticipat> getReferenceClass(){
		return com.allinfinance.po.mchnt.TblHoldMchtParticipat.class;
	}
	
	public com.allinfinance.po.mchnt.TblHoldMchtParticipat cast(Object object) {
		return (com.allinfinance.po.mchnt.TblHoldMchtParticipat) object;
	}
	
	public void delete(TblHoldMchtParticipatPK id) throws HibernateException {
		super.delete(load(id));
	}
	public void delete(List<TblHoldMchtParticipatPK> idList)
			throws HibernateException {
		Session session = getHibernateTemplate().getSessionFactory()
		.openSession();
		Transaction tx = session.beginTransaction();
		for (int i = 0; i < idList.size(); i++) {
			session.delete((TblHoldMchtParticipat)load(idList.get(i)));
		}
		session.flush();
		session.clear();
		tx.commit();
		session.close();
	}
	public TblHoldMchtParticipat get(TblHoldMchtParticipatPK id)
			throws HibernateException {
		return (com.allinfinance.po.mchnt.TblHoldMchtParticipat) get(getReferenceClass(),
				id);
	}
	public void save(TblHoldMchtParticipat tblHoldMchtParticipat)
			throws HibernateException {
		super.save(tblHoldMchtParticipat);
	}

	public void saveOrUpdate(TblHoldMchtParticipat tblHoldMchtParticipat) {
		super.saveOrUpdate(tblHoldMchtParticipat);
	}

	public void update(TblHoldMchtParticipat tblHoldMchtParticipat) {
		super.update(tblHoldMchtParticipat);
	}

	


}
