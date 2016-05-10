package com.allinfinance.bo.impl.term;

import java.util.List;

import com.allinfinance.bo.term.TblTermKeyBO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.term.TblTermKeyDAO;
import com.allinfinance.po.TblTermKey;
import com.allinfinance.po.TblTermKeyPK;

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
public class TblTermKeyBOTarget implements TblTermKeyBO {
	
	private TblTermKeyDAO tblTermKeyDAO;
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#add(com.allinfinance.po.TblTermKey)
	 */
	public String add(TblTermKey tblTermKey) {
		tblTermKeyDAO.save(tblTermKey);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#delete(com.allinfinance.po.TblTermKey)
	 */
	public String delete(TblTermKey tblTermKey) {
		tblTermKeyDAO.delete(tblTermKey);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#delete(com.allinfinance.po.TblTermKeyPK)
	 */
	public String delete(TblTermKeyPK id) {
		tblTermKeyDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#get(com.allinfinance.po.TblTermKeyPK)
	 */
	public TblTermKey get(TblTermKeyPK id) {
		return tblTermKeyDAO.get(id);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#update(java.util.List)
	 */
	public String update(List<TblTermKey> tblTermKeyList) {
		for(TblTermKey tblTermKey : tblTermKeyList) {
			tblTermKeyDAO.update(tblTermKey);
		}
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblTermKeyDAO
	 */
	public TblTermKeyDAO getTblTermKeyDAO() {
		return tblTermKeyDAO;
	}

	/**
	 * @param tblTermKeyDAO the tblTermKeyDAO to set
	 */
	public void setTblTermKeyDAO(TblTermKeyDAO tblTermKeyDAO) {
		this.tblTermKeyDAO = tblTermKeyDAO;
	}

	public void saveOrUpdate(TblTermKey tblTermKey) {
		tblTermKeyDAO.saveOrUpdate(tblTermKey);
	}
	
}
