package com.allinfinance.bo.impl.mchnt;

import java.util.List;

import com.allinfinance.bo.impl.daytrade.FrontMcht;
import com.allinfinance.bo.mchnt.T20108BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.FrontConstants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtBaseInfDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtBaseInfTmpDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtSettleInfDAO;
import com.allinfinance.frontend.dto.acctmanager.AcctDtlJsonDto;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInf;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.GenerateNextId;

public class T20108BOTarget implements T20108BO {

	private TblMchtSettleInfDAO tblMchtSettleInfDAO;
	private TblMchtBaseInfDAO tblMchtBaseInfDAO;
	private TblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO;
	private ICommQueryDAO commQueryDAO;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TblMchtBaseInf> getAll() {
		String hql="from TblMchtBaseInf a where a.MchtFunction is null and a.MchtStatus!='8' ";
		List<TblMchtBaseInf> mchtList=commQueryDAO.findByHQLQuery(hql, 0, 500);
		return mchtList;
	}
	
	@Override
	public String openAcct(TblMchtBaseInf inf) {
		TblMchtSettleInf infSettle=tblMchtSettleInfDAO.get(inf.getMchtNo());
		AcctDtlJsonDto acctDtl = new AcctDtlJsonDto();
		acctDtl.setWebTime(CommonFunction.getCurrentDateTime());
		acctDtl.setWebSeqNum(GenerateNextId.getSeqSysNum());
		acctDtl.setAccountBizType(FrontMcht.ACCT_TYPE_1);
		acctDtl.setAcctType(FrontMcht.ACCT_TYPE_PERSON);
		acctDtl.setAddress(inf.getAddr());
		acctDtl.setBrhId(inf.getBankNo());
		acctDtl.setSysBrhId(FrontConstants.ACCT_BRH_ID);
		String brhName = "SELECT BRH_NAME FROM TBL_BRH_INFO WHERE BRH_ID = '" +inf.getBankNo()+ "'";
		acctDtl.setBrhName(commQueryDAO.findCountBySQLQuery(brhName));
		acctDtl.setContactPhone(inf.getCommTel());
		acctDtl.setCurrency(FrontMcht.CURRENCY_TYPE_CNY);
		acctDtl.setMerchantId(inf.getMchtNo());
		acctDtl.setMerchantName(inf.getMchtNm());
		
		acctDtl.setSettleBankNo(infSettle.getOpenStlno());
		acctDtl.setSettleBankName(infSettle.getSettleBankNm());
		acctDtl.setSettleCardNo(infSettle.getSettleAcct().trim().substring(1));
		acctDtl.setSettleName(infSettle.getSettleAcctNm());
		acctDtl.setSettleType(infSettle.getSettleAcct().trim().substring(0,1));
		
		FrontMcht frontMcht = new FrontMcht();
		Object[] ret = frontMcht.doTxn(FrontMcht.TXN_CODE_ADDACCT,acctDtl);
		
		if(Constants.SUCCESS_CODE.equals(ret[0])){
//			TblMchtBaseInfTmp tblMchtBaseInfTmp= tblMchtBaseInfTmpDAO.get(inf.getMchtNo());
//			System.out.println(tblMchtBaseInfTmp.getMchtNm());
//			tblMchtBaseInfTmp.setMchtFunction(FrontMcht.ACCT_TYPE_1);
//			inf.setMchtFunction(FrontMcht.ACCT_TYPE_1);
//			tblMchtBaseInfDAO.update(inf);
//			tblMchtBaseInfTmpDAO.update(tblMchtBaseInfTmp);
			String sqlInf="update tbl_mcht_base_inf set mcht_function='"+FrontMcht.ACCT_TYPE_1+"' where mcht_no='"+inf.getMchtNo()+"' ";
			String sqlInfTmp="update tbl_mcht_base_inf_tmp set mcht_function='"+FrontMcht.ACCT_TYPE_1+"' where mcht_no='"+inf.getMchtNo()+"' ";
			commQueryDAO.excute(sqlInf);
			commQueryDAO.excute(sqlInfTmp);
			// 返回成功信息
			return Constants.SUCCESS_CODE;
		}else{
			return (String) ret[1];
		}
	}


	

	public TblMchtSettleInfDAO getTblMchtSettleInfDAO() {
		return tblMchtSettleInfDAO;
	}

	public void setTblMchtSettleInfDAO(TblMchtSettleInfDAO tblMchtSettleInfDAO) {
		this.tblMchtSettleInfDAO = tblMchtSettleInfDAO;
	}

	public TblMchtBaseInfDAO getTblMchtBaseInfDAO() {
		return tblMchtBaseInfDAO;
	}

	public void setTblMchtBaseInfDAO(TblMchtBaseInfDAO tblMchtBaseInfDAO) {
		this.tblMchtBaseInfDAO = tblMchtBaseInfDAO;
	}

	public TblMchtBaseInfTmpDAO getTblMchtBaseInfTmpDAO() {
		return tblMchtBaseInfTmpDAO;
	}

	public void setTblMchtBaseInfTmpDAO(TblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO) {
		this.tblMchtBaseInfTmpDAO = tblMchtBaseInfTmpDAO;
	}

	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}
	
	
	
	
	
	
	
	
}
