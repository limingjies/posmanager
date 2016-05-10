package com.allinfinance.bo.impl.term;

import java.util.List;

import com.allinfinance.bo.term.T3030101BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.term.TblTermManagementCheckDAO;
import com.allinfinance.po.TblTermManagementCheck;
import com.allinfinance.po.TblTermManagementCheckPK;

public class T3030101BOTarget implements T3030101BO {
	
	private TblTermManagementCheckDAO tblTermManagementCheckDAO;
	
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#add(com.allinfinance.po.TblTermManagementCheck)
	 */
	public String add(TblTermManagementCheck tblTermManagementCheck) {
		tblTermManagementCheckDAO.save(tblTermManagementCheck);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#delete(com.allinfinance.po.TblTermManagementCheck)
	 */
	public String delete(TblTermManagementCheck tblTermManagementCheck) {
		tblTermManagementCheckDAO.delete(tblTermManagementCheck);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#delete(com.allinfinance.po.TblTermManagementCheck)
	 */
	public String delete(TblTermManagementCheckPK id) {
		tblTermManagementCheckDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#get(com.allinfinance.po.TblTermManagementCheckPK)
	 */
	public TblTermManagementCheck get(TblTermManagementCheckPK id) {
		return tblTermManagementCheckDAO.get(id);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#update(java.util.List)
	 */
	public String update(List<TblTermManagementCheck> tblTermManagementCheckList) {
		for(TblTermManagementCheck tblTermManagementCheck : tblTermManagementCheckList) {
			tblTermManagementCheckDAO.update(tblTermManagementCheck);
		}
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblTermManagementCheckDAO
	 */
	public TblTermManagementCheckDAO getTblTermManagementCheckDAO() {
		return tblTermManagementCheckDAO;
	}

	/**
	 * @param tblTermManagementCheckDAO the tblTermManagementCheckDAO to set
	 */
	public void setTblTermManagementCheckDAO(TblTermManagementCheckDAO tblTermManagementCheckDAO) {
		this.tblTermManagementCheckDAO = tblTermManagementCheckDAO;
	}

	public String cancel(TblTermManagementCheck tblTermManagementCheck) {
		tblTermManagementCheckDAO.update(tblTermManagementCheck);
		return Constants.SUCCESS_CODE;
	}

	public String update(TblTermManagementCheck tblTermManagementCheck) {
		tblTermManagementCheckDAO.update(tblTermManagementCheck);
		return null;
	}

	
}