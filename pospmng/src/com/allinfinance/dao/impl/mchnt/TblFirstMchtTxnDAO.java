package com.allinfinance.dao.impl.mchnt;

import org.hibernate.HibernateException;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.mchnt.TblFirstMchtTxn;


public class TblFirstMchtTxnDAO extends _RootDAO<TblFirstMchtTxn> implements com.allinfinance.dao.iface.mchnt.TblFirstMchtTxnDAO {

	public TblFirstMchtTxnDAO () {}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.TblFirstMchtTxnDAO#load(java.lang.String)
	 */
	@Override
	public TblFirstMchtTxn load(String key) throws HibernateException {
		// TODO Auto-generated method stub
		return (TblFirstMchtTxn) load(getReferenceClass(), key);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.TblFirstMchtTxnDAO#get(java.lang.String)
	 */
	@Override
	public TblFirstMchtTxn get(String key) throws HibernateException {
		// TODO Auto-generated method stub
		return (TblFirstMchtTxn) get(getReferenceClass(), key);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.TblFirstMchtTxnDAO#save(com.allinfinance.po.mchnt.TblFirstMchtTxn)
	 */
	@Override
	public void save(TblFirstMchtTxn tblFirstMchtTxn) throws HibernateException {
		// TODO Auto-generated method stub
		 super.save(tblFirstMchtTxn);
		
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.TblFirstMchtTxnDAO#saveOrUpdate(com.allinfinance.po.mchnt.TblFirstMchtTxn)
	 */
	@Override
	public void saveOrUpdate(TblFirstMchtTxn tblFirstMchtTxn)
			throws HibernateException {
		// TODO Auto-generated method stub
		super.saveOrUpdate(tblFirstMchtTxn);
		
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.TblFirstMchtTxnDAO#update(com.allinfinance.po.mchnt.TblFirstMchtTxn)
	 */
	@Override
	public void update(TblFirstMchtTxn tblFirstMchtTxn)
			throws HibernateException {
		// TODO Auto-generated method stub
		super.update(tblFirstMchtTxn);
		
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.TblFirstMchtTxnDAO#delete(com.allinfinance.po.mchnt.TblFirstMchtTxn)
	 */
	@Override
	public void delete(TblFirstMchtTxn tblFirstMchtTxn)
			throws HibernateException {
		// TODO Auto-generated method stub
		super.delete((Object) tblFirstMchtTxn);
		
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.mchnt.TblFirstMchtTxnDAO#delete(java.lang.String)
	 */
	@Override
	public void delete(String id) throws HibernateException {
		// TODO Auto-generated method stub
		super.delete((Object) load(id));
		
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao._RootDAO#getReferenceClass()
	 */
	@Override
	protected Class<TblFirstMchtTxn> getReferenceClass() {
		// TODO Auto-generated method stub
		return TblFirstMchtTxn.class;
	}

}