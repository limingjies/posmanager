package com.allinfinance.bo.impl.mchnt;

import com.allinfinance.bo.mchnt.T20505BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.mchnt.TblFirstMchtTxnDAO;
import com.allinfinance.po.mchnt.TblFirstMchtTxn;

public class T20505BOTarget implements T20505BO {
	
	private TblFirstMchtTxnDAO tblFirstMchtTxnDAO;

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.mchnt.T20505BO#get(java.lang.String)
	 */
	@Override
	public TblFirstMchtTxn get(String id) {
		// TODO Auto-generated method stub
		return tblFirstMchtTxnDAO.get(id);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.mchnt.T20505BO#add(com.allinfinance.po.mchnt.TblFirstMchtTxn)
	 */
	@Override
	public String add(TblFirstMchtTxn tblFirstMchtTxn) {
		// TODO Auto-generated method stub
		tblFirstMchtTxnDAO.save(tblFirstMchtTxn);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.mchnt.T20505BO#update(com.allinfinance.po.mchnt.TblFirstMchtTxn)
	 */
	@Override
	public String update(TblFirstMchtTxn tblFirstMchtTxn) {
		// TODO Auto-generated method stub
		tblFirstMchtTxnDAO.update(tblFirstMchtTxn);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.mchnt.T20505BO#delete(com.allinfinance.po.mchnt.TblFirstMchtTxn)
	 */
	@Override
	public String delete(TblFirstMchtTxn tblFirstMchtTxn) {
		// TODO Auto-generated method stub
		tblFirstMchtTxnDAO.delete(tblFirstMchtTxn);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.mchnt.T20505BO#delete(java.lang.String)
	 */
	@Override
	public String delete(String id) {
		// TODO Auto-generated method stub
		tblFirstMchtTxnDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblFirstMchtTxnDAO
	 */
	public TblFirstMchtTxnDAO getTblFirstMchtTxnDAO() {
		return tblFirstMchtTxnDAO;
	}

	/**
	 * @param tblFirstMchtTxnDAO the tblFirstMchtTxnDAO to set
	 */
	public void setTblFirstMchtTxnDAO(TblFirstMchtTxnDAO tblFirstMchtTxnDAO) {
		this.tblFirstMchtTxnDAO = tblFirstMchtTxnDAO;
	}
	
}
