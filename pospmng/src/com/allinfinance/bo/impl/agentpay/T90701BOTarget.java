package com.allinfinance.bo.impl.agentpay;

import java.util.List;

import com.allinfinance.bo.agentpay.T90701BO;
import com.allinfinance.common.Constants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.agentpay.TblStlmFileTransInfDAO;
import com.allinfinance.po.agentpay.TblStlmFileTransInf;
import com.allinfinance.po.agentpay.TblStlmFileTransInfPK;

public class T90701BOTarget implements T90701BO {
	
	private TblStlmFileTransInfDAO tblStlmFileTransInfDAO;
	private ICommQueryDAO commDFQueryDAO;
	public TblStlmFileTransInf get(TblStlmFileTransInfPK id) {
		return this.tblStlmFileTransInfDAO.get(id);
	}

	@Override
	public String add(TblStlmFileTransInf tblStlmFileTransInf) {
		tblStlmFileTransInfDAO.save(tblStlmFileTransInf);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String update(List<TblStlmFileTransInf> TblStlmFileTransInfList) {
		for(TblStlmFileTransInf TblStlmFileTransInf: TblStlmFileTransInfList){
			tblStlmFileTransInfDAO.update(TblStlmFileTransInf);
		}
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String update(TblStlmFileTransInf tblStlmFileTransInf) {
		tblStlmFileTransInfDAO.update(tblStlmFileTransInf);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(TblStlmFileTransInf tblStlmFileTransInf) {
		tblStlmFileTransInfDAO.delete(tblStlmFileTransInf);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(TblStlmFileTransInfPK id) {
		tblStlmFileTransInfDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}
	@Override
	public String updateAllTlr(String tlr) {
		String sqlstr = "update TBL_STLM_FILE_TRANS_INF set LST_UPD_TLR ='"+tlr+"' where FILE_STAT =2";
		commDFQueryDAO.excute(sqlstr);
		return Constants.SUCCESS_CODE;
	}
	public TblStlmFileTransInfDAO getTblStlmFileTransInfDAO() {
		return tblStlmFileTransInfDAO;
	}

	public void setTblStlmFileTransInfDAO(TblStlmFileTransInfDAO tblStlmFileTransInfDAO) {
		this.tblStlmFileTransInfDAO = tblStlmFileTransInfDAO;
	}

	public ICommQueryDAO getCommDFQueryDAO() {
		return commDFQueryDAO;
	}
	public void setCommDFQueryDAO(ICommQueryDAO commDFQueryDAO) {
		this.commDFQueryDAO = commDFQueryDAO;
	}
	
}
