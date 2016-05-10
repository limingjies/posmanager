package com.allinfinance.bo.impl.term;

import java.util.List;

import com.allinfinance.bo.term.T30402BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.term.TblTermCupInfoDAO;
import com.allinfinance.po.TblTermCupInfo;

public class T30402BOTarget implements T30402BO {
	
	private TblTermCupInfoDAO tblTermCupInfoDAO;
	
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#add(com.allinfinance.po.TblTermCupInfo)
	 */
	public String add(TblTermCupInfo tblTermCupInfo) {
		tblTermCupInfoDAO.save(tblTermCupInfo);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#delete(com.allinfinance.po.TblTermCupInfo)
	 */
	public String delete(TblTermCupInfo tblTermCupInfo) {
		tblTermCupInfoDAO.delete(tblTermCupInfo);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#delete(com.allinfinance.po.TblTermCupInfo)
	 */
	public String delete(String id) {
		tblTermCupInfoDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#get(com.allinfinance.po.TblTermCupInfoPK)
	 */
	public TblTermCupInfo get(String id) {
		return tblTermCupInfoDAO.get(id);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#update(java.util.List)
	 */
	public String update(List<TblTermCupInfo> tblTermCupInfoList) {
		for(TblTermCupInfo tblTermCupInfo : tblTermCupInfoList) {
			tblTermCupInfoDAO.update(tblTermCupInfo);
		}
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblTermCupInfoDAO
	 */
	public TblTermCupInfoDAO getTblTermCupInfoDAO() {
		return tblTermCupInfoDAO;
	}

	/**
	 * @param tblTermCupInfoDAO the tblTermCupInfoDAO to set
	 */
	public void setTblTermCupInfoDAO(TblTermCupInfoDAO tblTermCupInfoDAO) {
		this.tblTermCupInfoDAO = tblTermCupInfoDAO;
	}

	public String cancel(TblTermCupInfo tblTermCupInfo) {
		tblTermCupInfoDAO.update(tblTermCupInfo);
		return Constants.SUCCESS_CODE;
	}

	public String saveOrUpdate(TblTermCupInfo tblTermCupInfo) {
		tblTermCupInfoDAO.saveOrUpdate(tblTermCupInfo);
		return Constants.SUCCESS_CODE;
	}

	
}