package com.allinfinance.bo.impl.mchnt;


import com.allinfinance.bo.mchnt.T20106BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.mchnt.TblMchtNameDAO;
import com.allinfinance.po.mchnt.TblMchtName;
import com.allinfinance.po.mchnt.TblMchtNamePK;

public class T20106BOTarget implements T20106BO {
	
	private TblMchtNameDAO tblMchtNameDAO;
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#add(com.allinfinance.po.TblCtlMchtSettleInf)
	 */
	public String save(TblMchtName tblMchtName) {
		tblMchtNameDAO.save(tblMchtName);
		return Constants.SUCCESS_CODE;
	}


	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#delete(com.allinfinance.po.TblCtlMchtSettleInfPK)
	 */
	public String delete(TblMchtNamePK tblMchtNamePK) {
		tblMchtNameDAO.delete(tblMchtNamePK);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#get(com.allinfinance.po.TblCtlMchtSettleInfPK)
	 */
	public TblMchtName get(TblMchtNamePK tblMchtNamePK) {
		return tblMchtNameDAO.get(tblMchtNamePK);
	}


	public String update(TblMchtName tblMchtName) {
		// TODO Auto-generated method stub
		tblMchtNameDAO.update(tblMchtName);
		return Constants.SUCCESS_CODE;
	}


	public TblMchtNameDAO getTblMchtNameDAO() {
		return tblMchtNameDAO;
	}


	public void setTblMchtNameDAO(TblMchtNameDAO tblMchtNameDAO) {
		this.tblMchtNameDAO = tblMchtNameDAO;
	}


	




	
	
	
	
}