package com.allinfinance.bo.impl.mchnt;

import java.util.List;

import com.allinfinance.bo.mchnt.T20401BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.mchnt.TblMchtCupInfoTmpDAO;
import com.allinfinance.po.TblMchtCupInfoTmp;

public class T20401BOTarget implements T20401BO {
	
	private TblMchtCupInfoTmpDAO tblMchtCupInfoTmpDAO;
	
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#add(com.allinfinance.po.TblMchtCupInfoTmp)
	 */
	public String add(TblMchtCupInfoTmp tblMchtCupInfoTmp) {
		tblMchtCupInfoTmpDAO.save(tblMchtCupInfoTmp);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#delete(com.allinfinance.po.TblMchtCupInfoTmp)
	 */
	public String delete(TblMchtCupInfoTmp tblMchtCupInfoTmp) {
		tblMchtCupInfoTmpDAO.delete(tblMchtCupInfoTmp);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#delete(com.allinfinance.po.TblMchtCupInfoTmp)
	 */
	public String delete(String id) {
		tblMchtCupInfoTmpDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#get(com.allinfinance.po.TblMchtCupInfoTmpPK)
	 */
	public TblMchtCupInfoTmp get(String id) {
		return tblMchtCupInfoTmpDAO.get(id);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#update(java.util.List)
	 */
	public String update(List<TblMchtCupInfoTmp> tblMchtCupInfoTmpList) {
		for(TblMchtCupInfoTmp tblMchtCupInfoTmp : tblMchtCupInfoTmpList) {
			tblMchtCupInfoTmpDAO.update(tblMchtCupInfoTmp);
		}
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblMchtCupInfoTmpDAO
	 */
	public TblMchtCupInfoTmpDAO getTblMchtCupInfoTmpDAO() {
		return tblMchtCupInfoTmpDAO;
	}

	/**
	 * @param tblMchtCupInfoTmpDAO the tblMchtCupInfoTmpDAO to set
	 */
	public void setTblMchtCupInfoTmpDAO(TblMchtCupInfoTmpDAO tblMchtCupInfoTmpDAO) {
		this.tblMchtCupInfoTmpDAO = tblMchtCupInfoTmpDAO;
	}

	public String cancel(TblMchtCupInfoTmp tblMchtCupInfoTmp) {
		tblMchtCupInfoTmpDAO.update(tblMchtCupInfoTmp);
		return Constants.SUCCESS_CODE;
	}

	public String saveOrUpdate(TblMchtCupInfoTmp tblMchtCupInfoTmp) {
		tblMchtCupInfoTmpDAO.saveOrUpdate(tblMchtCupInfoTmp);
		return Constants.SUCCESS_CODE;
	}

	
}