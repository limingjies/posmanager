package com.allinfinance.bo.dataImport;

import java.util.List;

import com.allinfinance.common.Operator;
import com.allinfinance.po.TblTermInf;
import com.allinfinance.po.TblTermInfTmp;
import com.allinfinance.po.mchnt.TblHisDiscAlgo;
import com.allinfinance.po.mchnt.TblInfDiscAlgo;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInf;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmp;

public interface DataImportBO {

	public List<Object> mchtImportData(String[] records, Operator operator, String importTime) throws Exception;
	
	public String getNewId(String orgId, String tableName);
	
	public String getOldId(String newId, String tableName);
	
	public String getTermId(String areaNo, String termType);
	
	public String importTermInfo(String orgTermId, TblTermInfTmp tblTermInfTmp) throws Exception;

	public String getMchntCd(String orderName);

	public String updatePicFlag(String mchntCd);

	TblTermInf getTermInf(String termId);
	
	public String updateSN(String termId, String recCrtTs, String sn);
	public String sendMessage(String newMchtNo, TblMchtBaseInfTmp tmp,
			TblMchtBaseInf inf, TblMchtSettleInfTmp tmpSettle,
			TblMchtSettleInf settle, String createNewNo) throws Exception;

}
