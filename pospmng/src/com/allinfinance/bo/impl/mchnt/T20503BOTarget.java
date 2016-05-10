package com.allinfinance.bo.impl.mchnt;


import java.math.BigDecimal;
import java.util.List;

import com.allinfinance.bo.mchnt.T20503BO;
import com.allinfinance.common.CommonsConstants;
import com.allinfinance.common.Constants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.base.TblInsKeyDAO;
import com.allinfinance.dao.iface.mchnt.ITblFirstMchtInfDAO;
import com.allinfinance.po.base.TblInsKey;
import com.allinfinance.po.base.TblInsKeyPK;
import com.allinfinance.po.mchnt.TblFirstMchtInf;
import com.allinfinance.system.util.CommonFunction;

public class T20503BOTarget implements T20503BO {
	
	private ITblFirstMchtInfDAO iTblFirstMchtInfDAO;
	private TblInsKeyDAO tblInsKeyDAO;
	private ICommQueryDAO commQueryDAO;
	
	public String add(TblFirstMchtInf tblFirstMchtInf) {
		
		String mchtSeq=getNo();
		
		//机构密钥
		if(CommonsConstants.FIRST_MCHT_KEY.equals(tblFirstMchtInf.getCrtTime().substring(1))){
			TblInsKeyPK tblInsKeyPK=new TblInsKeyPK();
			tblInsKeyPK.setFirstMchtNo(mchtSeq);
			tblInsKeyPK.setInsIdCd(tblFirstMchtInf.getReserved1());
			TblInsKey tblInsKey=new TblInsKey();
			tblInsKey.setTblInsKeyPK(tblInsKeyPK);
			tblInsKey.setInsKeyIdx(tblFirstMchtInf.getUpdtTime());
			tblInsKey.setRecOprId(CommonsConstants.FIRST_MCHT_KEY_STA);
			tblInsKeyDAO.save(tblInsKey);
			tblFirstMchtInf.setUpdtTime(mchtSeq);
		}else{
			tblFirstMchtInf.setUpdtTime("");
		}
		iTblFirstMchtInfDAO.save(tblFirstMchtInf);
		return Constants.SUCCESS_CODE;
	}

	public String delete(TblFirstMchtInf tblFirstMchtInf) {
		iTblFirstMchtInfDAO.delete(tblFirstMchtInf);
		return Constants.SUCCESS_CODE;
	}

	public String delete(String id) {
		
		
		//机构密钥
		TblFirstMchtInf tblFirstMchtInf=iTblFirstMchtInfDAO.get(id);
		if(CommonsConstants.FIRST_MCHT_KEY.equals(tblFirstMchtInf.getCrtTime().substring(1))){
			TblInsKeyPK tblInsKeyPK=new TblInsKeyPK();
			tblInsKeyPK.setFirstMchtNo(tblFirstMchtInf.getUpdtTime());
			tblInsKeyPK.setInsIdCd(CommonFunction.fillString(tblFirstMchtInf.getReserved1(), ' ', 11, true));
			if(tblInsKeyDAO.get(tblInsKeyPK)!=null){
				tblInsKeyDAO.delete(tblInsKeyPK);
			}
		}
		iTblFirstMchtInfDAO.delete(id);
		return Constants.SUCCESS_CODE;
	}

	public TblFirstMchtInf get(String id) {
		return iTblFirstMchtInfDAO.get(id);
	}

	public String update(TblFirstMchtInf tblFirstMchtInf) {
		iTblFirstMchtInfDAO.update(tblFirstMchtInf);
		return Constants.SUCCESS_CODE;
	}

	public ITblFirstMchtInfDAO getITblFirstMchtInfDAO() {
		return iTblFirstMchtInfDAO;
	}

	public void setITblFirstMchtInfDAO(ITblFirstMchtInfDAO iTblFirstMchtInfDAO) {
		this.iTblFirstMchtInfDAO = iTblFirstMchtInfDAO;
	}

	public TblInsKeyDAO getTblInsKeyDAO() {
		return tblInsKeyDAO;
	}

	public void setTblInsKeyDAO(TblInsKeyDAO tblInsKeyDAO) {
		this.tblInsKeyDAO = tblInsKeyDAO;
	}

	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	public ITblFirstMchtInfDAO getiTblFirstMchtInfDAO() {
		return iTblFirstMchtInfDAO;
	}

	public void setiTblFirstMchtInfDAO(ITblFirstMchtInfDAO iTblFirstMchtInfDAO) {
		this.iTblFirstMchtInfDAO = iTblFirstMchtInfDAO;
	}
	
	@SuppressWarnings("unchecked")
	private String getNo(){
		String sql = "select max(to_number(UPDT_TIME))+1 from TBL_FIRST_MCHT_INF";
		List<BigDecimal> resultSet= commQueryDAO.findBySQLQuery(sql);
		if(resultSet.size()>0 && resultSet.get(0) != null){
			return resultSet.get(0).toString();
		}else{
			return "1";
		}
	}

	
}
