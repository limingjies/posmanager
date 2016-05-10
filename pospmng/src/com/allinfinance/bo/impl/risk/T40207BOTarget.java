package com.allinfinance.bo.impl.risk;


import com.allinfinance.bo.risk.T40207BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.risk.TblRiskWhiteDAO;
import com.allinfinance.po.TblRiskWhite;
import com.allinfinance.po.TblRiskWhitePK;

public class T40207BOTarget implements T40207BO {
	
	private TblRiskWhiteDAO tblRiskWhiteDAO;
	
	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#add(com.allinfinance.po.TblCtlMchtSettleInf)
	 */
	public String save(TblRiskWhite tblRiskWhite) {
		tblRiskWhiteDAO.save(tblRiskWhite);
		return Constants.SUCCESS_CODE;
	}


	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#delete(com.allinfinance.po.TblCtlMchtSettleInfPK)
	 */
	public String delete(TblRiskWhitePK tblRiskWhitePK) {
		tblRiskWhiteDAO.delete(tblRiskWhitePK);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T10202BO#get(com.allinfinance.po.TblCtlMchtSettleInfPK)
	 */
	public TblRiskWhite get(TblRiskWhitePK tblRiskWhitePK) {
		return tblRiskWhiteDAO.get(tblRiskWhitePK);
	}


	public String update(TblRiskWhite tblRiskWhite) {
		// TODO Auto-generated method stub
		tblRiskWhiteDAO.update(tblRiskWhite);
		return Constants.SUCCESS_CODE;
	}


	public TblRiskWhiteDAO getTblRiskWhiteDAO() {
		return tblRiskWhiteDAO;
	}


	public void setTblRiskWhiteDAO(TblRiskWhiteDAO tblRiskWhiteDAO) {
		this.tblRiskWhiteDAO = tblRiskWhiteDAO;
	}




	
	
	
	
}