package com.allinfinance.bo.impl.settle;

import com.allinfinance.bo.settle.T80904BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.settle.TblPayTypeSmallDAO;
import com.allinfinance.po.settle.TblPayTypeSmall;

public class T80904BOTarget implements T80904BO {
	private	 TblPayTypeSmallDAO tblPayTypeSmallDAO;

	@Override
	public TblPayTypeSmall get(String key) {
		// TODO Auto-generated method stub
		return tblPayTypeSmallDAO.get(key);
	}

	@Override
	public String add(TblPayTypeSmall tblPayTypeSmall) throws Exception {
		// TODO Auto-generated method stub
		tblPayTypeSmallDAO.save(tblPayTypeSmall);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(String key) throws Exception {
		// TODO Auto-generated method stub
		tblPayTypeSmallDAO.delete(key);
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblPayTypeSmallDAO
	 */
	public TblPayTypeSmallDAO getTblPayTypeSmallDAO() {
		return tblPayTypeSmallDAO;
	}

	/**
	 * @param tblPayTypeSmallDAO the tblPayTypeSmallDAO to set
	 */
	public void setTblPayTypeSmallDAO(TblPayTypeSmallDAO tblPayTypeSmallDAO) {
		this.tblPayTypeSmallDAO = tblPayTypeSmallDAO;
	}
	
}
