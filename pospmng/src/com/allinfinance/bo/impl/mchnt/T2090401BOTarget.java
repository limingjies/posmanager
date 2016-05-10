package com.allinfinance.bo.impl.mchnt;

import java.math.BigDecimal;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;

import com.allinfinance.bo.mchnt.T2090401BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.mchnt.ITblCashRateInfDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfTmpDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfTmpHistDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfTmpTmpDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtCashInfTmpTmpDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtSettleInfTmpDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtSettleInfTmpHistDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtSettleInfTmpTmpDAO;
import com.allinfinance.po.TblDivMchntTmp;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmpHist;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmpHistPK;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp;
import com.allinfinance.po.mchnt.TblMchtCashInfTmp;
import com.allinfinance.po.mchnt.TblMchtCashInfTmpTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmpHist;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmpHistPK;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmpTmp;
import com.allinfinance.system.util.CommonFunction;

/**
 * Title:删除临时商户信息
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-10
 * 
 * Company: Shanghai Tonglian
 * 
 * @author
 * 
 * @version 1.0
 */
public class T2090401BOTarget implements T2090401BO {
	
	
	private ITblMchtBaseInfTmpTmpDAO tblMchtBaseInfTmpTmpDAO;
	
	private ITblMchtSettleInfTmpTmpDAO tblMchtSettleInfTmpTmpDAO;
	
	private ITblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO;

	private ITblMchtSettleInfTmpDAO tblMchtSettleInfTmpDAO;
	
	private ITblMchtBaseInfTmpHistDAO tblMchtBaseInfTmpHistDAO;

	private ITblMchtSettleInfTmpHistDAO tblMchtSettleInfTmpHistDAO;
	
	private ITblMchtCashInfTmpTmpDAO cashInfTmpTmpDAO;
	private ITblMchtCashInfTmpDAO cashInfTmpDAO;
	
	/**
	 * @return the cashInfTmpTmpDAO
	 */
	public ITblMchtCashInfTmpTmpDAO getCashInfTmpTmpDAO() {
		return cashInfTmpTmpDAO;
	}




	/**
	 * @param cashInfTmpTmpDAO the cashInfTmpTmpDAO to set
	 */
	public void setCashInfTmpTmpDAO(ITblMchtCashInfTmpTmpDAO cashInfTmpTmpDAO) {
		this.cashInfTmpTmpDAO = cashInfTmpTmpDAO;
	}




	/**
	 * @return the cashInfTmpDAO
	 */
	public ITblMchtCashInfTmpDAO getCashInfTmpDAO() {
		return cashInfTmpDAO;
	}




	/**
	 * @param cashInfTmpDAO the cashInfTmpDAO to set
	 */
	public void setCashInfTmpDAO(ITblMchtCashInfTmpDAO cashInfTmpDAO) {
		this.cashInfTmpDAO = cashInfTmpDAO;
	}

	private ICommQueryDAO commQueryDAO;
	/*
	 * 更新商户信息
	 * 
	 */
	public String updateTmp(TblMchtBaseInfTmpTmp tblMchtBaseInfTmpTmp,
			TblMchtSettleInfTmpTmp tblMchtSettleInfTmpTmp,
			TblMchtBaseInfTmp tblMchtBaseInfTmp, 
			TblMchtSettleInfTmp tblMchtSettleInfTmp) {
		// 旧商户号
		String oldMchtNo = tblMchtBaseInfTmpTmp.getMchtNo();
		// 新商户号
		String newMchtNo = tblMchtBaseInfTmp.getMchtNo();
		newMchtNo=oldMchtNo;
		String cashFlag = tblMchtBaseInfTmpTmp.getCashFlag();
		if("1".equals(cashFlag)){//开通提现
			TblMchtCashInfTmpTmp tblMchtCashInfTmpTmp = cashInfTmpTmpDAO.get(newMchtNo);
			if(tblMchtCashInfTmpTmp != null){
				TblMchtCashInfTmp cashInfTmp = new TblMchtCashInfTmp();
				BeanUtils.copyProperties(tblMchtCashInfTmpTmp, cashInfTmp);
				cashInfTmp.setUpdOpr(tblMchtBaseInfTmp.getUpdOprId());
				cashInfTmp.setUpdTime(CommonFunction.getCurrentDateTime());
				cashInfTmpDAO.saveOrUpdate(cashInfTmp);
			}
		}
//		if(tblMchtBaseInfTmpDAO.get(oldMchtNo) != null||tblMchtSettleInfTmpDAO.get(oldMchtNo) != null) {
//			if(!tblMchtBaseInfTmpDAO.get(oldMchtNo).getMchtStatus().trim().equals("2")&&!tblMchtBaseInfTmpDAO.get(oldMchtNo).getMchtStatus().trim().equals("A")){
//				
//				return "自定义的商户号已经存在";
//			}else{
//				tblMchtBaseInfTmp.setMchtNo(oldMchtNo);
//				tblMchtSettleInfTmp.setMchtNo(oldMchtNo);
//				tblMchtBaseInfTmpDAO.update(tblMchtBaseInfTmp);
//				tblMchtSettleInfTmpDAO.update(tblMchtSettleInfTmp);
//				return Constants.SUCCESS_CODE;
//			}
//		}
		tblMchtBaseInfTmp.setMchtNo(oldMchtNo);
		tblMchtSettleInfTmp.setMchtNo(oldMchtNo);
		tblMchtBaseInfTmpDAO.saveOrUpdate(tblMchtBaseInfTmp);
		tblMchtSettleInfTmpDAO.saveOrUpdate(tblMchtSettleInfTmp);
		
		//存入商户基本信息临时历史表
		TblMchtBaseInfTmpHist tblMchtBaseInfTmpHist= new TblMchtBaseInfTmpHist();
		BeanUtils.copyProperties(tblMchtBaseInfTmp, tblMchtBaseInfTmpHist);
		//保存和更新商户基本信息临时历史表
		saveMchtBaseInfTmpHist(tblMchtBaseInfTmpHist, oldMchtNo, newMchtNo);
		//存入商户清算基本信息临时历史表
		TblMchtSettleInfTmpHist tblMchtSettleInfTmpHist= new TblMchtSettleInfTmpHist();
		BeanUtils.copyProperties(tblMchtSettleInfTmp, tblMchtSettleInfTmpHist);
		//保存和更新商户清算基本信息临时历史表
		saveMchtSettleInfTmpHist(tblMchtSettleInfTmpHist, oldMchtNo, newMchtNo);				
		
		if(tblMchtBaseInfTmpTmpDAO.get(oldMchtNo) == null||tblMchtSettleInfTmpTmpDAO.get(oldMchtNo) == null) {
			return "您想删除的商户号不存在";
		}
		tblMchtBaseInfTmpTmpDAO.delete(oldMchtNo);
		tblMchtSettleInfTmpTmpDAO.delete(oldMchtNo);
		
		// 将旧的商户号统一为新的商户号
		tblMchtBaseInfTmpTmp.setMchtNo(oldMchtNo);
		tblMchtSettleInfTmpTmp.setMchtNo(oldMchtNo);
		tblMchtBaseInfTmpTmpDAO.save(tblMchtBaseInfTmpTmp);
		tblMchtSettleInfTmpTmpDAO.save(tblMchtSettleInfTmpTmp);

		return Constants.SUCCESS_CODE;
	}
	
	


	/**
	 * @return the tblMchtBaseInfTmpTmpDAO
	 */
	public ITblMchtBaseInfTmpTmpDAO getTblMchtBaseInfTmpTmpDAO() {
		return tblMchtBaseInfTmpTmpDAO;
	}

	/**
	 * @param tblMchtBaseInfTmpTmpDAO the tblMchtBaseInfTmpTmpDAO to set
	 */
	public void setTblMchtBaseInfTmpTmpDAO(
			ITblMchtBaseInfTmpTmpDAO tblMchtBaseInfTmpTmpDAO) {
		this.tblMchtBaseInfTmpTmpDAO = tblMchtBaseInfTmpTmpDAO;
	}

	/**
	 * @return the tblMchtSettleInfTmpTmpDAO
	 */
	public ITblMchtSettleInfTmpTmpDAO getTblMchtSettleInfTmpTmpDAO() {
		return tblMchtSettleInfTmpTmpDAO;
	}

	/**
	 * @param tblMchtSettleInfTmpTmpDAO the tblMchtSettleInfTmpTmpDAO to set
	 */
	public void setTblMchtSettleInfTmpTmpDAO(
			ITblMchtSettleInfTmpTmpDAO tblMchtSettleInfTmpTmpDAO) {
		this.tblMchtSettleInfTmpTmpDAO = tblMchtSettleInfTmpTmpDAO;
	}

	

	/**
	 * @return the tblMchtBaseInfTmpDAO
	 */
	public ITblMchtBaseInfTmpDAO getTblMchtBaseInfTmpDAO() {
		return tblMchtBaseInfTmpDAO;
	}

	/**
	 * @param tblMchtBaseInfTmpDAO the tblMchtBaseInfTmpDAO to set
	 */
	public void setTblMchtBaseInfTmpDAO(ITblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO) {
		this.tblMchtBaseInfTmpDAO = tblMchtBaseInfTmpDAO;
	}


	/**
	 * @return the tblMchtBaseInfTmpDAO
	 */
	public ITblMchtBaseInfTmpHistDAO getTblMchtBaseInfTmpHistDAO() {
		return tblMchtBaseInfTmpHistDAO;
	}

	/**
	 * @param tblMchtBaseInfTmpDAO the tblMchtBaseInfTmpDAO to set
	 */
	public void setTblMchtBaseInfTmpHistDAO(ITblMchtBaseInfTmpHistDAO tblMchtBaseInfTmpHistDAO) {
		this.tblMchtBaseInfTmpHistDAO = tblMchtBaseInfTmpHistDAO;
	}
	/**
	 * @return the tblMchtSettleInfTmpDAO
	 */
	public ITblMchtSettleInfTmpDAO getTblMchtSettleInfTmpDAO() {
		return tblMchtSettleInfTmpDAO;
	}

	/**
	 * @param tblMchtSettleInfTmpDAO the tblMchtSettleInfTmpDAO to set
	 */
	public void setTblMchtSettleInfTmpDAO(
			ITblMchtSettleInfTmpDAO tblMchtSettleInfTmpDAO) {
		this.tblMchtSettleInfTmpDAO = tblMchtSettleInfTmpDAO;
	}
	
	/**
	 * @return the tblMchtSettleInfTmpDAO
	 */
	public ITblMchtSettleInfTmpHistDAO getTblMchtSettleInfTmpHistDAO() {
		return tblMchtSettleInfTmpHistDAO;
	}

	/**
	 * @param tblMchtSettleInfTmpDAO the tblMchtSettleInfTmpDAO to set
	 */
	public void setTblMchtSettleInfTmpHistDAO(
			ITblMchtSettleInfTmpHistDAO tblMchtSettleInfTmpHistDAO) {
		this.tblMchtSettleInfTmpHistDAO = tblMchtSettleInfTmpHistDAO;
	}
	
	/**
	 * @return the commQueryDAO
	 */
	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	/**
	 * @param commQueryDAO the commQueryDAO to set
	 */
	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.T20101BO#loadDiv(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<TblDivMchntTmp> getMaxSeqByMchtNo(String mchtNo) {
		String hql = "select nvl(max(mchtNoSeq),0) from com.allinfinance.po.TblMchtBaseInfTmpHist t where t.Id.mchtNo = '" + mchtNo + "'";
		return commQueryDAO.findByHQLQuery(hql);
	}

	/**
	 * 保存和更新商户基本信息临时历史表
	 * @param tblMchtBaseInfTmpHist
	 * @param mchtNoOld
	 * @param mchtNoNew
	 */
	private void saveMchtBaseInfTmpHist(TblMchtBaseInfTmpHist tblMchtBaseInfTmpHist,String mchtNoOld, String mchtNoNew) {
		//商户历史原有方法
		/*String sql = "select MCHT_NO from TBL_MCHT_BASE_INF_TMP_HIST where MCHT_NO = '" + mchtNoOld + "'";
		List<String> pks = commQueryDAO.findBySQLQuery(sql);
		boolean ret1 = true;
		if (pks == null || pks.size() ==0 ||pks.get(0) == null){
			ret1 = false;
		}
		
		sql = "select MCHT_NO from TBL_MCHT_BASE_INF_TMP_HIST where MCHT_NO_NEW = '" + mchtNoOld + "'";
		List<String> pks2 = commQueryDAO.findBySQLQuery(sql);
		boolean ret2 = true;
		if (pks2 == null || pks2.size() ==0 ||pks2.get(0) == null){
			ret2 = false;
		}
		
		TblMchtBaseInfTmpHistPK pk = new TblMchtBaseInfTmpHistPK();
		if (!ret1 &&!ret2){
			pk.setMchtNo(mchtNoNew);
			pk.setMchtNoNew("-");
		}else if(!ret1 && ret2) {
			commQueryDAO.excute("update TBL_MCHT_BASE_INF_TMP_HIST set MCHT_NO_NEW = '"+mchtNoNew+"' "
					+ "where MCHT_NO='"+pks2.get(0)+"' and MCHT_NO_NEW='-'");
			pk.setMchtNo(pks2.get(0));
			pk.setMchtNoNew("-");
		}else if (ret1 &&!ret2){
			commQueryDAO.excute("update TBL_MCHT_BASE_INF_TMP_HIST set MCHT_NO_NEW = '"+mchtNoNew+"' "
					+ "where MCHT_NO='"+pks.get(0)+"' and MCHT_NO_NEW='-'");
			pk.setMchtNo(pks.get(0));
			pk.setMchtNoNew("-");
		}else if(ret1 &&ret2) {
			
			return;
		}*/
		Operator opr = (Operator) ServletActionContext.getRequest()
				.getSession().getAttribute(Constants.OPERATOR_INFO);
		String sql = "select MAX(to_number(MCHT_NO_NEW)) from TBL_MCHT_BASE_INF_TMP_HIST where MCHT_NO = '" + mchtNoOld + "' ";
		List<BigDecimal> mchtHistSerail = commQueryDAO.findBySQLQuery(sql);
		TblMchtBaseInfTmpHistPK pk=new TblMchtBaseInfTmpHistPK();
		if(null!=mchtHistSerail &&mchtHistSerail.get(0)!=null&& mchtHistSerail.size()>0){
				String serail =  mchtHistSerail.get(0).toString();
//				int ser = Integer.parseInt(serail)+1;
				String mchtNew = String.valueOf(serail);
				pk.setMchtNoNew(mchtNew);
		}else{
			pk.setMchtNoNew("1");
		}
		pk.setMchtNo(mchtNoOld);
		tblMchtBaseInfTmpHist.setId(pk);
		String[] ignorePropertiesForMcht={"recCrtTs","crtOprId"};
		tblMchtBaseInfTmpHist.setUpdOprId(opr.getOprId());
		tblMchtBaseInfTmpHist.setRecUpdTs(CommonFunction.getCurrentDateTime());
		TblMchtBaseInfTmpHist baseInfTmpHist = tblMchtBaseInfTmpHistDAO.get(pk);
		if(baseInfTmpHist==null){
			tblMchtBaseInfTmpHistDAO.saveOrUpdate(tblMchtBaseInfTmpHist);
		}else {
			BeanUtils.copyProperties(tblMchtBaseInfTmpHist, baseInfTmpHist, ignorePropertiesForMcht);
			tblMchtBaseInfTmpHistDAO.update(baseInfTmpHist);
		}
	}
	
	/**
	 * 保存和更新商户清算基本信息临时历史表
	 * @param tblMchtSettleInfTmpHist
	 * @param mchtNoOld
	 * @param mchtNoNew
	 */
	private void saveMchtSettleInfTmpHist(TblMchtSettleInfTmpHist tblMchtSettleInfTmpHist,String mchtNoOld, String mchtNoNew) {
		

		/*String sql = "select MCHT_NO from TBL_MCHT_SETTLE_INF_TMP_HIST where MCHT_NO = '" + mchtNoOld + "'";
		List<String> pks = commQueryDAO.findBySQLQuery(sql);
		boolean ret1 = true;
		if (pks == null || pks.size() ==0 ||pks.get(0) == null){
			ret1 = false;
		}
		
		sql = "select MCHT_NO from TBL_MCHT_SETTLE_INF_TMP_HIST where MCHT_NO_NEW = '" + mchtNoOld + "'";
		List<String> pks2 = commQueryDAO.findBySQLQuery(sql);
		boolean ret2 = true;
		if (pks2 == null || pks2.size() ==0 ||pks2.get(0) == null){
			ret2 = false;
		}
		
		TblMchtSettleInfTmpHistPK pk = new TblMchtSettleInfTmpHistPK();
		if (!ret1 &&!ret2){
			pk.setMchtNo(mchtNoNew);
			pk.setMchtNoNew("-");
		}else if(!ret1 && ret2) {
			commQueryDAO.excute("update TBL_MCHT_SETTLE_INF_TMP_HIST set MCHT_NO_NEW = '"+mchtNoNew+"' "
					+ "where MCHT_NO='"+pks2.get(0)+"' and MCHT_NO_NEW='-'");
			pk.setMchtNo(pks2.get(0));
			pk.setMchtNoNew("-");
		}else if (ret1 &&!ret2){
			commQueryDAO.excute("update TBL_MCHT_SETTLE_INF_TMP_HIST set MCHT_NO_NEW = '"+mchtNoNew+"' "
					+ "where MCHT_NO='"+pks.get(0)+"' and MCHT_NO_NEW='-'");
			pk.setMchtNo(pks.get(0));
			pk.setMchtNoNew("-");
		}else if(ret1 &&ret2){
			return;
		}*/
		TblMchtSettleInfTmpHistPK pk = new TblMchtSettleInfTmpHistPK();
		String sql = "select MAX(to_number(MCHT_NO_NEW)) from TBL_MCHT_SETTLE_INF_TMP_HIST where MCHT_NO = '" + mchtNoOld + "' ";
		List<BigDecimal> mchtHistSerail = commQueryDAO.findBySQLQuery(sql);
		if(null!=mchtHistSerail &&mchtHistSerail.get(0)!=null&& mchtHistSerail.size()>0){
			String serail = mchtHistSerail.get(0).toString();
//			int ser = Integer.parseInt(serail)+1;
			String mchtNew = String.valueOf(serail);
			pk.setMchtNoNew(mchtNew);
		}else{
			pk.setMchtNoNew("1");
		}
		pk.setMchtNo(mchtNoOld);
		tblMchtSettleInfTmpHist.setId(pk);
		String[] ignorePropertiesForSettle={"recCrtTs"};
		TblMchtSettleInfTmpHist settleInfTmpHist = tblMchtSettleInfTmpHistDAO.get(pk);
		tblMchtSettleInfTmpHist.setRecUpdTs(CommonFunction.getCurrentDateTime());
		if(settleInfTmpHist==null){
			tblMchtSettleInfTmpHistDAO.saveOrUpdate(tblMchtSettleInfTmpHist);
		}else {
			BeanUtils.copyProperties(tblMchtSettleInfTmpHist, settleInfTmpHist, ignorePropertiesForSettle);
			tblMchtSettleInfTmpHistDAO.update(settleInfTmpHist);
		}
		//保存
		//String currentTime = CommonFunction.getCurrentTime();
//		tblMchtSettleInfTmpHist.setRecCrtTs(currentTime);
//		tblMchtSettleInfTmpHist.setRecUpdTs(currentTime);
	}
}
