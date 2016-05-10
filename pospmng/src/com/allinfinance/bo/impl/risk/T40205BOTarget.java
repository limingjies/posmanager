package com.allinfinance.bo.impl.risk;

import java.util.List;

import com.allinfinance.bo.risk.T40205BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.risk.TblCtlMchtSettleInfDAO;
import com.allinfinance.po.TblCtlMchtSettleInf;
import com.allinfinance.po.TblCtlMchtSettleInfPK;

public class T40205BOTarget implements T40205BO {
	
	private TblCtlMchtSettleInfDAO tblCtlMchtSettleInfDAO;
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#add(com.allinfinance.po.TblCtlMchtSettleInf)
	 */
	public String add(TblCtlMchtSettleInf tblCtlMchtSettleInf) {
		tblCtlMchtSettleInfDAO.save(tblCtlMchtSettleInf);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#delete(com.allinfinance.po.TblCtlMchtSettleInf)
	 */
	public String delete(TblCtlMchtSettleInf tblCtlMchtSettleInf) {
		tblCtlMchtSettleInfDAO.delete(tblCtlMchtSettleInf);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#delete(com.allinfinance.po.TblCtlMchtSettleInfPK)
	 */
	public String delete(TblCtlMchtSettleInfPK id) {
		tblCtlMchtSettleInfDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#get(com.allinfinance.po.TblCtlMchtSettleInfPK)
	 */
	public TblCtlMchtSettleInf get(TblCtlMchtSettleInfPK id) {
		return tblCtlMchtSettleInfDAO.get(id);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#update(java.util.List)
	 */
	public String update(List<TblCtlMchtSettleInf> tblCtlMchtSettleInfList) {
		for(TblCtlMchtSettleInf tblCtlMchtSettleInf : tblCtlMchtSettleInfList) {
			tblCtlMchtSettleInfDAO.update(tblCtlMchtSettleInf);
		}
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblCtlMchtSettleInfDAO
	 */
	public TblCtlMchtSettleInfDAO getTblCtlMchtSettleInfDAO() {
		return tblCtlMchtSettleInfDAO;
	}

	/**
	 * @param tblCtlMchtSettleInfDAO the tblCtlMchtSettleInfDAO to set
	 */
	public void setTblCtlMchtSettleInfDAO(TblCtlMchtSettleInfDAO tblCtlMchtSettleInfDAO) {
		this.tblCtlMchtSettleInfDAO = tblCtlMchtSettleInfDAO;
	}

	public String cancel(TblCtlMchtSettleInf tblCtlMchtSettleInf) {
		tblCtlMchtSettleInfDAO.update(tblCtlMchtSettleInf);
		return Constants.SUCCESS_CODE;
	}
	
}