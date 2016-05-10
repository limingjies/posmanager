package com.allinfinance.dao.iface.mchnt;

import java.util.List;

import com.allinfinance.po.mchnt.TblHoldMchtParticipat;
import com.allinfinance.po.mchnt.TblHoldMchtParticipatPK;

public interface TblHoldMchtParticipatDAO {

	public TblHoldMchtParticipat get(TblHoldMchtParticipatPK id) throws org.hibernate.HibernateException;
	
	public  void save(TblHoldMchtParticipat tblHoldMchtParticipat ) throws org.hibernate.HibernateException;
	
	public void saveOrUpdate(com.allinfinance.po.mchnt.TblHoldMchtParticipat tblHoldMchtParticipat);

	public void update(com.allinfinance.po.mchnt.TblHoldMchtParticipat tblHoldMchtParticipat);

	public void delete(TblHoldMchtParticipatPK id) throws org.hibernate.HibernateException;
	
	public void delete(List<TblHoldMchtParticipatPK> idList) throws org.hibernate.HibernateException;
	
}
