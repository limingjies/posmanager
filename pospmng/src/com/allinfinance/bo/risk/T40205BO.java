package com.allinfinance.bo.risk;

import java.util.List;

import com.allinfinance.po.TblCtlMchtSettleInf;
import com.allinfinance.po.TblCtlMchtSettleInfPK;

public interface T40205BO {

	public TblCtlMchtSettleInf get(TblCtlMchtSettleInfPK id);

	public String add(TblCtlMchtSettleInf tblCtlMchtSettleInf);

	public String update(List<TblCtlMchtSettleInf> tblCtlMchtSettleInfList);
	
	public String cancel(TblCtlMchtSettleInf tblCtlMchtSettleInf);

	public String delete(TblCtlMchtSettleInf tblCtlMchtSettleInf);

	public String delete(TblCtlMchtSettleInfPK id);
}
