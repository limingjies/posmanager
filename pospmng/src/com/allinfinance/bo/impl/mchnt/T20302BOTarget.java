package com.allinfinance.bo.impl.mchnt;

import java.util.List;

import com.allinfinance.bo.mchnt.T20302BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.mchnt.TblMchntTpGrpDAO;
import com.allinfinance.po.mchnt.TblInfMchntTpGrp;
import com.allinfinance.po.mchnt.TblInfMchntTpGrpPK;


/**
 * Title:商户组别维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-10
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class T20302BOTarget implements T20302BO {
	
	private TblMchntTpGrpDAO tblMchntTpGrpDAO;
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T20302BO#add(com.allinfinance.po.TblMchntTpGrp)
	 */
	public String add(TblInfMchntTpGrp TblInfMchntTpGrp) {
		tblMchntTpGrpDAO.save(TblInfMchntTpGrp);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T20302BO#delete(java.lang.String)
	 */
	public String delete(TblInfMchntTpGrpPK id) {
		tblMchntTpGrpDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T20302BO#delete(com.allinfinance.po.TblInfMchntTpGrp)
	 */
	public String delete(TblInfMchntTpGrp TblInfMchntTpGrp) {
		tblMchntTpGrpDAO.delete(TblInfMchntTpGrp);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T20302BO#get(java.lang.String)
	 */
	public TblInfMchntTpGrp get(TblInfMchntTpGrpPK id) {
		return tblMchntTpGrpDAO.get(id);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T20302BO#update(com.allinfinance.po.TblInfMchntTpGrp)
	 */
	public String update(TblInfMchntTpGrp TblInfMchntTpGrp) {
		tblMchntTpGrpDAO.update(TblInfMchntTpGrp);
		return Constants.SUCCESS_CODE;
	}
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T20302BO#update(java.util.List)
	 */
	public String update(List<TblInfMchntTpGrp> tblMchntTpGrpList) {
		for(TblInfMchntTpGrp TblInfMchntTpGrp : tblMchntTpGrpList) {
			update(TblInfMchntTpGrp);
		}
		return Constants.SUCCESS_CODE;
	}
	
	/**
	 * @return the tblMchntTpGrpDAO
	 */
	public TblMchntTpGrpDAO getTblMchntTpGrpDAO() {
		return tblMchntTpGrpDAO;
	}

	/**
	 * @param tblMchntTpGrpDAO the tblMchntTpGrpDAO to set
	 */
	public void setTblMchntTpGrpDAO(TblMchntTpGrpDAO tblMchntTpGrpDAO) {
		this.tblMchntTpGrpDAO = tblMchntTpGrpDAO;
	}
}
