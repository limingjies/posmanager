package com.allinfinance.bo.impl.term;

import java.util.List;

import com.allinfinance.bo.term.TblTermZmkInfBO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.term.TblTermZmkInfDAO;
import com.allinfinance.po.TblTermZmkInf;
import com.allinfinance.po.TblTermZmkInfPK;

/**
 * Title:终端密钥BO
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-9
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class TblTermZmkInfBOTarget implements TblTermZmkInfBO {
	
	private TblTermZmkInfDAO tblTermZmkInfDAO;
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#add(com.allinfinance.po.TblTermZmkInf)
	 */
	public String add(TblTermZmkInf tblTermZmkInf) {
		tblTermZmkInfDAO.save(tblTermZmkInf);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#delete(com.allinfinance.po.TblTermZmkInf)
	 */
	public String delete(TblTermZmkInf tblTermZmkInf) {
		tblTermZmkInfDAO.delete(tblTermZmkInf);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#delete(com.allinfinance.po.TblTermZmkInfPK)
	 */
	public String delete(TblTermZmkInfPK id) {
		tblTermZmkInfDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#get(com.allinfinance.po.TblTermZmkInfPK)
	 */
	public TblTermZmkInf get(TblTermZmkInfPK id) {
		return tblTermZmkInfDAO.get(id);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#update(java.util.List)
	 */
	public String update(List<TblTermZmkInf> tblTermZmkInfList) {
		for(TblTermZmkInf tblTermZmkInf : tblTermZmkInfList) {
			tblTermZmkInfDAO.update(tblTermZmkInf);
		}
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblTermZmkInfDAO
	 */
	public TblTermZmkInfDAO getTblTermZmkInfDAO() {
		return tblTermZmkInfDAO;
	}

	/**
	 * @param tblTermZmkInfDAO the tblTermZmkInfDAO to set
	 */
	public void setTblTermZmkInfDAO(TblTermZmkInfDAO tblTermZmkInfDAO) {
		this.tblTermZmkInfDAO = tblTermZmkInfDAO;
	}

	public void saveOrUpdate(TblTermZmkInf tblTermZmkInf) {
		tblTermZmkInfDAO.saveOrUpdate(tblTermZmkInf);
	}
	
}