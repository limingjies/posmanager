package com.allinfinance.bo.impl.mchnt;

import java.util.List;

import com.allinfinance.bo.mchnt.T20802BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.mchnt.TblHoldMchtParticipatDAO;
import com.allinfinance.po.mchnt.TblHoldMchtParticipat;
import com.allinfinance.po.mchnt.TblHoldMchtParticipatPK;

public class T20802BOTarget implements T20802BO{
	
	private TblHoldMchtParticipatDAO tblHoldMchtParticipatDAO;

	public String delete(List<TblHoldMchtParticipatPK> idList) {
		 tblHoldMchtParticipatDAO.delete(idList);
		 return Constants.SUCCESS_CODE;
	}

	public TblHoldMchtParticipat get(TblHoldMchtParticipatPK id) {
		return tblHoldMchtParticipatDAO.get(id);
	}

	public TblHoldMchtParticipatDAO getTblHoldMchtParticipatDAO() {
		return tblHoldMchtParticipatDAO;
	}

	public void setTblHoldMchtParticipatDAO(
			TblHoldMchtParticipatDAO tblHoldMchtParticipatDAO) {
		this.tblHoldMchtParticipatDAO = tblHoldMchtParticipatDAO;
	}

	public String add(TblHoldMchtParticipat tblHoldMchtParticipat) {
		tblHoldMchtParticipatDAO.save(tblHoldMchtParticipat);
		return Constants.SUCCESS_CODE;
	}

	public String cancel(TblHoldMchtParticipat tblHoldMchtParticipat) {
		return null;
	}

	public String update(List<TblHoldMchtParticipat> tblHoldMchtParticipatList) {
		for(TblHoldMchtParticipat tblHoldMchtParticipat : tblHoldMchtParticipatList) {
			tblHoldMchtParticipatDAO.saveOrUpdate(tblHoldMchtParticipat);
		}
		return Constants.SUCCESS_CODE;
	}

	
}
