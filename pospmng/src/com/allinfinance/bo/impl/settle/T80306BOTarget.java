package com.allinfinance.bo.impl.settle;


import com.allinfinance.bo.settle.T80306BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.settle.TblAmtbackApplymisDAO;
import com.allinfinance.dao.iface.settle.TblAmtbackApplyresDAO;
import com.allinfinance.po.TblAmtbackApplyPKmis;
import com.allinfinance.po.TblAmtbackApplyPKres;
import com.allinfinance.po.TblAmtbackApplymis;
import com.allinfinance.po.TblAmtbackApplyres;

public class T80306BOTarget implements T80306BO {
	
	private TblAmtbackApplyresDAO tblAmtbackApplyresDAO;
	private TblAmtbackApplymisDAO tblAmtbackApplymisDAO;
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#add(com.allinfinance.po.TblCtlMchtSettleInf)
	 */
	public String save(TblAmtbackApplyres tblAmtbackApplyres) {
		tblAmtbackApplyresDAO.save(tblAmtbackApplyres);
		return Constants.SUCCESS_CODE;
	}


	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#get(com.allinfinance.po.TblCtlMchtSettleInfPK)
	 */
	public TblAmtbackApplyres get(TblAmtbackApplyPKres tblAmtbackApplyPKres) {
		return tblAmtbackApplyresDAO.get(tblAmtbackApplyPKres);
	}
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#get(com.allinfinance.po.TblCtlMchtSettleInfPK)
	 */
	public TblAmtbackApplymis get(TblAmtbackApplyPKmis tblAmtbackApplyPKmis) {
		return tblAmtbackApplymisDAO.get(tblAmtbackApplyPKmis);
	}

	public String update(TblAmtbackApplyres tblAmtbackApplyres) {
		// TODO Auto-generated method stub
		tblAmtbackApplyresDAO.update(tblAmtbackApplyres);
		return Constants.SUCCESS_CODE;
	}


	public TblAmtbackApplyresDAO getTblAmtbackApplyresDAO() {
		return tblAmtbackApplyresDAO;
	}


	public void setTblAmtbackApplyresDAO(TblAmtbackApplyresDAO tblAmtbackApplyresDAO) {
		this.tblAmtbackApplyresDAO = tblAmtbackApplyresDAO;
	}


	public TblAmtbackApplymisDAO getTblAmtbackApplymisDAO() {
		return tblAmtbackApplymisDAO;
	}


	public void setTblAmtbackApplymisDAO(TblAmtbackApplymisDAO tblAmtbackApplymisDAO) {
		this.tblAmtbackApplymisDAO = tblAmtbackApplymisDAO;
	}



	






	
	
	
	
}