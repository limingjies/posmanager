package com.allinfinance.bo.impl.settle;


import com.allinfinance.bo.settle.T80301BO;
import com.allinfinance.common.Constants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.settle.BthGcTxnSuccDAO;
import com.allinfinance.dao.iface.settle.TblAmtbackApplyDAO;
import com.allinfinance.po.BthGcTxnSucc;
import com.allinfinance.po.BthGcTxnSuccPK;
import com.allinfinance.po.TblAmtbackApply;
import com.allinfinance.po.TblAmtbackApplyPK;

public class T80301BOTarget implements T80301BO {
	
	private TblAmtbackApplyDAO tblAmtbackApplyDAO;
	private BthGcTxnSuccDAO bthGcTxnSuccDAO;
	private ICommQueryDAO commQueryDAO;
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#add(com.allinfinance.po.TblCtlMchtSettleInf)
	 */
	public String save(TblAmtbackApply tblAmtbackApply) {
		tblAmtbackApplyDAO.save(tblAmtbackApply);
		return Constants.SUCCESS_CODE;
	}


	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#delete(com.allinfinance.po.TblCtlMchtSettleInfPK)
	 */
	public String delete(TblAmtbackApplyPK tblAmtbackApplyPK) {
		tblAmtbackApplyDAO.delete(tblAmtbackApplyPK);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#get(com.allinfinance.po.TblCtlMchtSettleInfPK)
	 */
	public TblAmtbackApply get(TblAmtbackApplyPK tblAmtbackApplyPK) {
		return tblAmtbackApplyDAO.get(tblAmtbackApplyPK);
	}


	public String update(TblAmtbackApply tblAmtbackApply) {
		// TODO Auto-generated method stub
		tblAmtbackApplyDAO.update(tblAmtbackApply);
		return Constants.SUCCESS_CODE;
	}


	public TblAmtbackApplyDAO getTblAmtbackApplyDAO() {
		return tblAmtbackApplyDAO;
	}


	public void setTblAmtbackApplyDAO(TblAmtbackApplyDAO tblAmtbackApplyDAO) {
		this.tblAmtbackApplyDAO = tblAmtbackApplyDAO;
	}


	public BthGcTxnSuccDAO getBthGcTxnSuccDAO() {
		return bthGcTxnSuccDAO;
	}


	public void setBthGcTxnSuccDAO(BthGcTxnSuccDAO bthGcTxnSuccDAO) {
		this.bthGcTxnSuccDAO = bthGcTxnSuccDAO;
	}


	public BthGcTxnSucc getBthGcTxnSucc(BthGcTxnSuccPK bthGcTxnSuccPK) {
		// TODO Auto-generated method stub
		return bthGcTxnSuccDAO.get(bthGcTxnSuccPK);
	}


	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}


	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}








	
	
	
	
}