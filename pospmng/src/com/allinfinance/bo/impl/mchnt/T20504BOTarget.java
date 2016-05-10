package com.allinfinance.bo.impl.mchnt;

import com.allinfinance.bo.mchnt.T20504BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.mchnt.ITblInsMchtInfDAO;
import com.allinfinance.po.mchnt.TblInsMchtInf;


public class T20504BOTarget implements T20504BO {
	
	private ITblInsMchtInfDAO iTblInsMchtInfDAO;

	public String add(TblInsMchtInf tblInsMchtInf) {
		iTblInsMchtInfDAO.save(tblInsMchtInf);
		return Constants.SUCCESS_CODE;
	}
	
	public String delete(TblInsMchtInf tblInsMchtInf) {
		iTblInsMchtInfDAO.delete(tblInsMchtInf);
		return Constants.SUCCESS_CODE;
	}
	
	public String delete(String id) {
		iTblInsMchtInfDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}
	
	public TblInsMchtInf get(String id) {
		return iTblInsMchtInfDAO.get(id);
	}
	
	public String update(TblInsMchtInf tblInsMchtInf) {
		iTblInsMchtInfDAO.update(tblInsMchtInf);
		return Constants.SUCCESS_CODE;
	}

	public ITblInsMchtInfDAO getITblInsMchtInfDAO() {
		return iTblInsMchtInfDAO;
	}

	public void setITblInsMchtInfDAO(ITblInsMchtInfDAO iTblInsMchtInfDAO) {
		this.iTblInsMchtInfDAO = iTblInsMchtInfDAO;
	}
	
}
