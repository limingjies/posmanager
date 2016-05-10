package com.allinfinance.bo.impl.mchnt;

import java.util.List;

import com.allinfinance.bo.mchnt.T20303BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.mchnt.TblMchntTpDAO;
import com.allinfinance.po.mchnt.TblInfMchntTp;
import com.allinfinance.po.mchnt.TblInfMchntTpPK;


/**
 * Title:商户MCC维护
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
public class T20303BOTarget implements T20303BO {
	
	private TblMchntTpDAO tblMchntTpDAO;
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T20303BO#add(com.allinfinance.po.TblMchntTp)
	 */
	public String add(TblInfMchntTp tblInfMchntTp) {
		tblMchntTpDAO.save(tblInfMchntTp);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T20303BO#delete(java.lang.String)
	 */
	public String delete(TblInfMchntTpPK mchntTp) {
		tblMchntTpDAO.delete(mchntTp);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T20303BO#delete(com.allinfinance.po.TblInfMchntTp)
	 */
	public String delete(TblInfMchntTp tblInfMchntTp) {
		tblMchntTpDAO.delete(tblInfMchntTp);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T20303BO#get(java.lang.String)
	 */
	public TblInfMchntTp get(TblInfMchntTpPK mchntTp) {
		return tblMchntTpDAO.get(mchntTp);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T20303BO#update(com.allinfinance.po.TblInfMchntTp)
	 */
	public String update(TblInfMchntTp tblInfMchntTp) {
		tblMchntTpDAO.update(tblInfMchntTp);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T20303BO#update(java.util.List)
	 */
	public String update(List<TblInfMchntTp> tblMchntTpList) {
		for(TblInfMchntTp TblInfMchntTp : tblMchntTpList) {
			update(TblInfMchntTp);
		}
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblMchntTpDAO
	 */
	public TblMchntTpDAO getTblMchntTpDAO() {
		return tblMchntTpDAO;
	}

	/**
	 * @param tblMchntTpDAO the tblMchntTpDAO to set
	 */
	public void setTblMchntTpDAO(TblMchntTpDAO tblMchntTpDAO) {
		this.tblMchntTpDAO = tblMchntTpDAO;
	}
	
	
}
