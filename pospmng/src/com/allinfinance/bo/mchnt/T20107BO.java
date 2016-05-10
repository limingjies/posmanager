package com.allinfinance.bo.mchnt;

import java.util.List;
import java.util.Map;

import com.allinfinance.common.Operator;
import com.allinfinance.po.TblTermKey;
import com.allinfinance.po.mchnt.TblHisDiscAlgo;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmp;

public interface T20107BO {
	
	public List<TblTermKey> importFile(List<TblMchtBaseInfTmp> mchtBaseInfList,
			List<TblMchtSettleInfTmp> mchtSettleInfList,
			Map<String, TblHisDiscAlgo> mchtdiskCdMap,
			Map<String, String> mccdiskCdMap,
			Map<String, String[]> termTelMap,Operator operator,boolean check) throws Exception;
	
	public void batchGetTmk(List<TblTermKey> tblTermKeyList) throws Exception;
	
	public String createRetFile(List<TblTermKey> tblTermKeyList,Operator operator) throws Exception;
	
}
