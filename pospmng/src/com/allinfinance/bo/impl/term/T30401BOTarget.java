package com.allinfinance.bo.impl.term;

import java.util.List;

import com.allinfinance.bo.term.T30401BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.term.TblTermCupInfoTmpDAO;
import com.allinfinance.po.TblTermCupInfoTmp;

public class T30401BOTarget implements T30401BO {
	
	private TblTermCupInfoTmpDAO tblTermCupInfoTmpDAO;
	
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#add(com.allinfinance.po.TblTermCupInfoTmp)
	 */
	public String add(TblTermCupInfoTmp tblTermCupInfoTmp) {
		tblTermCupInfoTmpDAO.save(tblTermCupInfoTmp);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#delete(com.allinfinance.po.TblTermCupInfoTmp)
	 */
	public String delete(TblTermCupInfoTmp tblTermCupInfoTmp) {
		tblTermCupInfoTmpDAO.delete(tblTermCupInfoTmp);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#delete(com.allinfinance.po.TblTermCupInfoTmp)
	 */
	public String delete(String id) {
		tblTermCupInfoTmpDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#get(com.allinfinance.po.TblTermCupInfoTmpPK)
	 */
	public TblTermCupInfoTmp get(String id) {
		return tblTermCupInfoTmpDAO.get(id);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#update(java.util.List)
	 */
	public String update(List<TblTermCupInfoTmp> tblTermCupInfoTmpList) {
		for(TblTermCupInfoTmp tblTermCupInfoTmp : tblTermCupInfoTmpList) {
			tblTermCupInfoTmpDAO.update(tblTermCupInfoTmp);
		}
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblTermCupInfoTmpDAO
	 */
	public TblTermCupInfoTmpDAO getTblTermCupInfoTmpDAO() {
		return tblTermCupInfoTmpDAO;
	}

	/**
	 * @param tblTermCupInfoTmpDAO the tblTermCupInfoTmpDAO to set
	 */
	public void setTblTermCupInfoTmpDAO(TblTermCupInfoTmpDAO tblTermCupInfoTmpDAO) {
		this.tblTermCupInfoTmpDAO = tblTermCupInfoTmpDAO;
	}

	public String cancel(TblTermCupInfoTmp tblTermCupInfoTmp) {
		tblTermCupInfoTmpDAO.update(tblTermCupInfoTmp);
		return Constants.SUCCESS_CODE;
	}

	public String saveOrUpdate(TblTermCupInfoTmp tblTermCupInfoTmp) {
		tblTermCupInfoTmpDAO.saveOrUpdate(tblTermCupInfoTmp);
		return Constants.SUCCESS_CODE;
	}

	
}