package com.allinfinance.bo.risk;

import com.allinfinance.po.TblRiskWhite;
import com.allinfinance.po.TblRiskWhitePK;



public interface T40207BO {

	public TblRiskWhite get(TblRiskWhitePK tblRiskWhitePK);

	public String save(TblRiskWhite tblRiskWhite);
	
	public String update(TblRiskWhite tblRiskWhite);


	public String delete(TblRiskWhitePK tblRiskWhitePK);
}
