package com.allinfinance.dao.iface.mchnt;


import com.allinfinance.po.mchnt.TblHolderCardId;
import com.allinfinance.po.mchnt.TblHolderCardIdPK;

public interface TblHolderCardIdDAO {
	
	public TblHolderCardId get(TblHolderCardIdPK id) throws org.hibernate.HibernateException;
	
	public  void save(TblHolderCardId tblHolderCardId ) throws org.hibernate.HibernateException;
	
	public void update(com.allinfinance.po.mchnt.TblHolderCardId tblHolderCardId);

	public void delete(TblHolderCardIdPK id) throws org.hibernate.HibernateException;
	

}
