package com.allinfinance.bo.impl.mchnt;

import java.util.List;

import com.allinfinance.bo.mchnt.T20503BO;
import com.allinfinance.common.Constants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.base.TblInsKeyDAO;
import com.allinfinance.dao.iface.mchnt.ITblFirstMchtInfDAO;
import com.allinfinance.po.mchnt.TblFirstMchtInf;

public class T20503BOTarget_old implements T20503BO {
	
	private ITblFirstMchtInfDAO iTblFirstMchtInfDAO;
	private TblInsKeyDAO tblInsKeyDAO;
	private ICommQueryDAO commQueryDAO;
	
	public String add(TblFirstMchtInf tblFirstMchtInf) {
		iTblFirstMchtInfDAO.save(tblFirstMchtInf);
		return Constants.SUCCESS_CODE;
	}

	public String delete(TblFirstMchtInf tblFirstMchtInf) {
		iTblFirstMchtInfDAO.delete(tblFirstMchtInf);
		return Constants.SUCCESS_CODE;
	}

	public String delete(String id) {
		iTblFirstMchtInfDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	public TblFirstMchtInf get(String id) {
		return iTblFirstMchtInfDAO.get(id);
	}

	public String update(TblFirstMchtInf tblFirstMchtInf) {
		iTblFirstMchtInfDAO.update(tblFirstMchtInf);
		return Constants.SUCCESS_CODE;
	}

	public ITblFirstMchtInfDAO getITblFirstMchtInfDAO() {
		return iTblFirstMchtInfDAO;
	}

	public void setITblFirstMchtInfDAO(ITblFirstMchtInfDAO iTblFirstMchtInfDAO) {
		this.iTblFirstMchtInfDAO = iTblFirstMchtInfDAO;
	}

	public ITblFirstMchtInfDAO getiTblFirstMchtInfDAO() {
		return iTblFirstMchtInfDAO;
	}

	public void setiTblFirstMchtInfDAO(ITblFirstMchtInfDAO iTblFirstMchtInfDAO) {
		this.iTblFirstMchtInfDAO = iTblFirstMchtInfDAO;
	}

	public TblInsKeyDAO getTblInsKeyDAO() {
		return tblInsKeyDAO;
	}

	public void setTblInsKeyDAO(TblInsKeyDAO tblInsKeyDAO) {
		this.tblInsKeyDAO = tblInsKeyDAO;
	}

	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	
}
