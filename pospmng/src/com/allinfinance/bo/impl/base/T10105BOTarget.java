package com.allinfinance.bo.impl.base;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.allinfinance.bo.base.T10105BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.base.TblBrhInfoDAO;
import com.allinfinance.dao.iface.base.TblBrhInfoHisDAO;
import com.allinfinance.dao.iface.base.TblBrhSettleInfDAO;
import com.allinfinance.dao.iface.base.TblBrhSettleInfHisDAO;
import com.allinfinance.dao.iface.mchnt.ShTblOprInfoDAO;
import com.allinfinance.dao.iface.profit.TblAgentFeeCfgDAO;
import com.allinfinance.dao.iface.profit.TblAgentFeeCfgHisDAO;
import com.allinfinance.dao.iface.profit.TblAgentRateInfoDAO;
import com.allinfinance.dao.iface.profit.TblAgentRateInfoHisDAO;
import com.allinfinance.dao.iface.profit.TblBrhCashInfoDAO;
import com.allinfinance.dao.iface.profit.TblBrhCashInfoHisDAO;
import com.allinfinance.dao.iface.profit.TblProfitRateInfoDAO;
import com.allinfinance.po.ShTblOprInfo;
import com.allinfinance.po.TblAgentFeeCfg;
import com.allinfinance.po.TblAgentFeeCfgHis;
import com.allinfinance.po.TblAgentRateInfo;
import com.allinfinance.po.TblAgentRateInfoHis;
import com.allinfinance.po.TblAgentRateInfoId;
import com.allinfinance.po.TblBrhInfo;
import com.allinfinance.po.TblBrhInfoHis;
import com.allinfinance.po.TblBrhInfoHisPK;
import com.allinfinance.po.base.TblBrhCashInf;
import com.allinfinance.po.base.TblBrhCashInfHis;
import com.allinfinance.po.base.TblBrhCashInfHisId;
import com.allinfinance.po.base.TblBrhSettleInf;
import com.allinfinance.po.base.TblBrhSettleInfHis;
import com.allinfinance.po.base.TblBrhSettleInfHisPK;
import com.allinfinance.system.util.BeanUtils;
import com.allinfinance.system.util.CommonFunction;

public class T10105BOTarget implements T10105BO{

	private TblAgentFeeCfgDAO tblAgentFeeCfgDAO;
	private TblAgentFeeCfgHisDAO tblAgentFeeCfgHisDAO;
	private TblAgentRateInfoDAO tblAgentRateInfoDAO;
	private TblBrhCashInfoDAO tblBrhCashInfoDAO;
	private TblBrhCashInfoHisDAO tblBrhCashInfoHisDAO;
	private TblAgentRateInfoHisDAO tblAgentRateInfoHisDAO;
	private TblProfitRateInfoDAO tblProfitRateInfoDAO;
	private TblBrhInfoDAO tblBrhInfoDAO;
	private TblBrhSettleInfDAO tblBrhSettleInfDAO;
	private TblBrhInfoHisDAO tblBrhInfoHisDAO;
	private TblBrhSettleInfHisDAO tblBrhSettleInfHisDAO;
	private ShTblOprInfoDAO shTblOprInfoDAO;
	public ICommQueryDAO commQueryDAO;
	
	@Override
	public String add(TblBrhInfo tblBrhInfo, TblBrhSettleInf tblBrhSettleInf, TblAgentFeeCfg tblAgentFeeCfgZlfAdd,
			List<TblAgentRateInfo> rateList, ShTblOprInfo shTblOprInfo, List<TblBrhCashInf> brhCashRateList) {
		String seqId = null;
		tblBrhInfoDAO.save(tblBrhInfo);
//		shTblOprInfoDAO.save(shTblOprInfo);

		// 记录修改历史-基本信息
		seqId = historyRecord(tblBrhInfo);	
		if(tblBrhSettleInf!=null){
			tblBrhSettleInfDAO.save(tblBrhSettleInf);
			// 记录修改历史-结算信息
			historyRecord(tblBrhSettleInf, seqId);
		}				
		
		if(tblAgentFeeCfgZlfAdd!=null){
			 tblAgentFeeCfgDAO.save(tblAgentFeeCfgZlfAdd);
			// 记录修改历史-费率信息
			 historyRecord(tblAgentFeeCfgZlfAdd, seqId);
		}
		for (TblAgentRateInfo tblAgentRateInfo : rateList) {
			tblAgentRateInfoDAO.save(tblAgentRateInfo);
			// 记录修改历史-分润信息
			historyRecord(tblAgentRateInfo, seqId);
		}
		for (TblBrhCashInf tblBrhCashInf : brhCashRateList) {
			tblBrhCashInfoDAO.save(tblBrhCashInf);
			// 记录修改历史-提现费率信息
			historyRecord(tblBrhCashInf, seqId);
		}
		return Constants.SUCCESS_CODE;
		
	}

	public String historyRecord(TblBrhCashInf tblBrhCashInf, String seqId) {
		TblBrhCashInfHis tblBrhCashInfHis = new TblBrhCashInfHis();
		TblBrhCashInfHisId tblBrhCashInfHisId = new TblBrhCashInfHisId();
		tblBrhCashInfHisId.setBrhId(tblBrhCashInf.getId().getBrhId());
		tblBrhCashInfHisId.setRateId(tblBrhCashInf.getId().getRateId());
		if (!"".equals(seqId)) {
			tblBrhCashInfHisId.setSeqId(Double.parseDouble(seqId));
		} else {
			tblBrhCashInfHisId.setSeqId(tblBrhCashInfoHisDAO.getNextSeqId(tblBrhCashInf.getId().getBrhId(), tblBrhCashInf.getId().getRateId()));
		}
		tblBrhCashInfHis.setId(tblBrhCashInfHisId);
		tblBrhCashInfHis.setName(tblBrhCashInf.getName());
		tblBrhCashInfHis.setRate(tblBrhCashInf.getRate());
		tblBrhCashInfHis.setCrtTime(tblBrhCashInf.getCrtTime());
		tblBrhCashInfHis.setCrtOpr(tblBrhCashInf.getCrtOpr());
		tblBrhCashInfHis.setUptTime(tblBrhCashInf.getUptTime());
		tblBrhCashInfHis.setUptOpr(tblBrhCashInf.getUptOpr());
		
		tblBrhCashInfoHisDAO.save(tblBrhCashInfHis);
		return null;
	}
	
	public String historyRecord(TblAgentFeeCfg tblAgentFeeCfg, String seqId) {
		TblAgentFeeCfgHis tblAgentFeeCfgHis = new TblAgentFeeCfgHis();
		
		if (!"".equals(seqId)) {
			tblAgentFeeCfgHis.setSeqId(seqId);
		} else {
			tblAgentFeeCfgHis.setSeqId(tblAgentFeeCfgHisDAO.getNextSeqId(tblAgentFeeCfg.getAgentNo()));
		}
		tblAgentFeeCfgHis.setDiscId(tblAgentFeeCfg.getDiscId());
		tblAgentFeeCfgHis.setAgentNo(tblAgentFeeCfg.getAgentNo());
		tblAgentFeeCfgHis.setSeq(tblAgentFeeCfg.getSeq());
		tblAgentFeeCfgHis.setName(tblAgentFeeCfg.getName());
		tblAgentFeeCfgHis.setEnableFlag(tblAgentFeeCfg.getEnableFlag());
		tblAgentFeeCfgHis.setMchtNo(tblAgentFeeCfg.getMchtNo());
		tblAgentFeeCfgHis.setMchtGrp(tblAgentFeeCfg.getMchtGrp());
		tblAgentFeeCfgHis.setPromotionBegDate(tblAgentFeeCfg.getPromotionBegDate());
		tblAgentFeeCfgHis.setPromotionEndDate(tblAgentFeeCfg.getPromotionEndDate());
		tblAgentFeeCfgHis.setPromotionRate(tblAgentFeeCfg.getPromotionRate());
		tblAgentFeeCfgHis.setBaseAmtMonth(tblAgentFeeCfg.getBaseAmtMonth());
		tblAgentFeeCfgHis.setGradeAmtMonth(tblAgentFeeCfg.getGradeAmtMonth());
		tblAgentFeeCfgHis.setAllotRate(tblAgentFeeCfg.getAllotRate());
		tblAgentFeeCfgHis.setSpecFeeRate(tblAgentFeeCfg.getSpecFeeRate());
		tblAgentFeeCfgHis.setExtVisaRate(tblAgentFeeCfg.getExtVisaRate());
		tblAgentFeeCfgHis.setExtMasterRate(tblAgentFeeCfg.getExtMasterRate());
		tblAgentFeeCfgHis.setExtJcbRate(tblAgentFeeCfg.getExtJcbRate());
		tblAgentFeeCfgHis.setExtRate1(tblAgentFeeCfg.getExtRate1());
		tblAgentFeeCfgHis.setExtRate2(tblAgentFeeCfg.getExtRate2());
		tblAgentFeeCfgHis.setExtRate3(tblAgentFeeCfg.getExtRate3());
		tblAgentFeeCfgHis.setFlag1(tblAgentFeeCfg.getFlag1());
		tblAgentFeeCfgHis.setFlag2(tblAgentFeeCfg.getFlag2());
		tblAgentFeeCfgHis.setMisc1(tblAgentFeeCfg.getMisc1());
		tblAgentFeeCfgHis.setMisc2(tblAgentFeeCfg.getMisc2());
		tblAgentFeeCfgHis.setAmt1(tblAgentFeeCfg.getAmt1());
		tblAgentFeeCfgHis.setAmt2(tblAgentFeeCfg.getAmt2());
		tblAgentFeeCfgHis.setCrtTime(tblAgentFeeCfg.getCrtTime());
		tblAgentFeeCfgHis.setCrtOpr(tblAgentFeeCfg.getCrtOpr());
		tblAgentFeeCfgHis.setUptTime(tblAgentFeeCfg.getUptTime());
		tblAgentFeeCfgHis.setUptOpr(tblAgentFeeCfg.getUptOpr());
		
		tblAgentFeeCfgHisDAO.save(tblAgentFeeCfgHis);
		return null;
	}

	@Override
	public String historyRecord(TblBrhInfo tblBrhInfo) {
		TblBrhInfoHis tblBrhInfoHis = new TblBrhInfoHis();
		TblBrhInfoHisPK id = new TblBrhInfoHisPK();
		id.setBrhId(tblBrhInfo.getBrhId());
		id.setSeqId(tblBrhInfoHisDAO.getNextSeqId(tblBrhInfo.getBrhId()));
		tblBrhInfoHis.setId(id);
		tblBrhInfoHis.setCupBrhId(tblBrhInfo.getCupBrhId());
		tblBrhInfoHis.setBrhLevel(tblBrhInfo.getBrhLevel());
		tblBrhInfoHis.setBrhSta(tblBrhInfo.getBrhSta());
		tblBrhInfoHis.setUpBrhId(tblBrhInfo.getUpBrhId());
		tblBrhInfoHis.setRegDt(tblBrhInfo.getRegDt());
		tblBrhInfoHis.setPostCd(tblBrhInfo.getPostCd());
		tblBrhInfoHis.setBrhAddr(tblBrhInfo.getBrhAddr());
		tblBrhInfoHis.setBrhName(tblBrhInfo.getBrhName());
		tblBrhInfoHis.setBrhTelNo(tblBrhInfo.getBrhTelNo());
		tblBrhInfoHis.setBrhContName(tblBrhInfo.getBrhContName());
		tblBrhInfoHis.setOpenRateId(tblBrhInfo.getOpenRateId());
		tblBrhInfoHis.setResv1(tblBrhInfo.getResv1());
		tblBrhInfoHis.setResv2(tblBrhInfo.getResv2());
		tblBrhInfoHis.setResv3(tblBrhInfo.getResv3());
		tblBrhInfoHis.setResv4(tblBrhInfo.getResv4());
		tblBrhInfoHis.setResv5(tblBrhInfo.getResv5());
		tblBrhInfoHis.setLastUpdOprId(tblBrhInfo.getLastUpdOprId());
		tblBrhInfoHis.setLastUpdTxnId(tblBrhInfo.getLastUpdTxnId());
		tblBrhInfoHis.setLastUpdTs(tblBrhInfo.getLastUpdTs());
		tblBrhInfoHis.setSettleOrgNo(tblBrhInfo.getSettleOrgNo());
		tblBrhInfoHis.setSettleJobType(tblBrhInfo.getSettleJobType());
		tblBrhInfoHis.setSettleMemProperty(tblBrhInfo.getSettleMemProperty());
		tblBrhInfoHis.setStatus(tblBrhInfo.getStatus());
		tblBrhInfoHis.setCreateNewNo(tblBrhInfo.getCreateNewNo());
		
		tblBrhInfoHisDAO.save(tblBrhInfoHis);
		return id.getSeqId();
	}

	@Override
	public String historyRecord(TblBrhSettleInf tblBrhSettleInf, String seqId) {
		TblBrhSettleInfHis tblBrhSettleInfHis = new TblBrhSettleInfHis();
		TblBrhSettleInfHisPK id = new TblBrhSettleInfHisPK();
		id.setBrhId(tblBrhSettleInf.getBrhId());
		if (!"".equals(seqId)) {
			id.setSeqId(seqId);
		} else {
			id.setSeqId(tblBrhSettleInfHisDAO.getNextSeqId(tblBrhSettleInf.getBrhId()));
		}
		tblBrhSettleInfHis.setId(id);
		tblBrhSettleInfHis.setSettleFlag(tblBrhSettleInf.getSettleFlag());
		tblBrhSettleInfHis.setSettleBankNo(tblBrhSettleInf.getSettleBankNo());
		tblBrhSettleInfHis.setSettleBankNm(tblBrhSettleInf.getSettleBankNm());
		tblBrhSettleInfHis.setSettleAcctNm(tblBrhSettleInf.getSettleAcctNm());
		tblBrhSettleInfHis.setSettleAcct(tblBrhSettleInf.getSettleAcct());
		tblBrhSettleInfHis.setSettleAcctMid(tblBrhSettleInf.getSettleAcctMid());
		tblBrhSettleInfHis.setSettleAcctMidNm(tblBrhSettleInf.getSettleAcctMidNm());
		tblBrhSettleInfHis.setCrtOpr(tblBrhSettleInf.getCrtOpr());
		tblBrhSettleInfHis.setCrtTs(tblBrhSettleInf.getCrtTs());
		tblBrhSettleInfHis.setUpdOpr(tblBrhSettleInf.getUpdOpr());
		tblBrhSettleInfHis.setUpdTs(tblBrhSettleInf.getUpdTs());
		tblBrhSettleInfHis.setMisc(tblBrhSettleInf.getMisc());
		tblBrhSettleInfHis.setMisc1(tblBrhSettleInf.getMisc1());
		
		tblBrhSettleInfHisDAO.save(tblBrhSettleInfHis);
		return null;
	}
	
	public String historyRecord(TblAgentRateInfo tblAgentRateInfo, String seqId) {
		TblAgentRateInfoHis tblAgentRateInfoHis = new TblAgentRateInfoHis();
		TblAgentRateInfoId tblAgentRateInfoId = tblAgentRateInfo.getId();
		if (!"".equals(seqId)) {
			tblAgentRateInfoHis.setSeqId(seqId);
		} else {
			tblAgentRateInfoHis.setSeqId(tblAgentRateInfoHisDAO.getNextSeqId(tblAgentRateInfoId.getDiscId(), tblAgentRateInfoId.getRateId()));
		}
		tblAgentRateInfoHis.setId(tblAgentRateInfo.getId());
		tblAgentRateInfoHis.setFeeRate(tblAgentRateInfo.getFeeRate());
		tblAgentRateInfoHis.setFeeType(tblAgentRateInfo.getFeeType());
		
		tblAgentRateInfoHisDAO.save(tblAgentRateInfoHis);
		
		return null;
	}
	
	@Override
	public String updateSettle(TblBrhInfo tblBrhInfo, TblBrhSettleInf tblBrhSettleInf, Operator operator,
			TblBrhInfo tblBrhInfOld, TblAgentFeeCfg tblAgentFeeCfgUpd, List<TblAgentRateInfo> rateList,List<TblBrhCashInf> brhCashRateList) {
		String seqId = null;
		tblBrhSettleInf.setUpdOpr(operator.getOprId());
		tblBrhSettleInf.setUpdTs(CommonFunction.getCurrentDateTime());
		tblBrhSettleInfDAO.update(tblBrhSettleInf);
		tblBrhInfoDAO.update(tblBrhInfo);
		
		// 记录修改历史-基本信息
		seqId = historyRecord(tblBrhInfo);//新增tblBrhInfoHis记录			
		// 记录修改历史-结算信息
		historyRecord(tblBrhSettleInf, seqId);//新增tblBrhSettleInfHis记录
		if(tblAgentFeeCfgUpd!=null){
			TblAgentFeeCfg tblAgentFeeCfgUpdOld = tblAgentFeeCfgDAO.get(tblAgentFeeCfgUpd.getDiscId());
			BeanUtils.copyPropertiesIgnoreNull(tblAgentFeeCfgUpd, tblAgentFeeCfgUpdOld);//将新对象的值拷贝到旧对象
			tblAgentFeeCfgDAO.update(tblAgentFeeCfgUpdOld);//更新旧对象的值
			// 记录修改历史-费率信息
			historyRecord(tblAgentFeeCfgUpdOld, seqId);//往历史表tblAgentFeeCfgHis中新增费率信息修改记录
		}

		tblAgentRateInfoDAO.deleteByDiscId(tblAgentFeeCfgUpd.getDiscId());//查找所有匹配对象AgentRateInfo，删除
		for (TblAgentRateInfo tblAgentRateInfo : rateList) {
			tblAgentRateInfoDAO.save(tblAgentRateInfo);//新增AgentRateInfo对象
			// 记录修改历史-分润信息
			historyRecord(tblAgentRateInfo, seqId);//新增tblAgentRateInfoHis记录
		}

		tblBrhCashInfoDAO.deleteByBrhId(tblBrhInfo.getBrhId());
		for (TblBrhCashInf tblBrhCashInf : brhCashRateList) {
			tblBrhCashInfoDAO.save(tblBrhCashInf);
			// 记录修改历史-提现费率信息
			historyRecord(tblBrhCashInf, seqId);
		}
		return Constants.SUCCESS_CODE;
	}
	


	@Override
	public Map getAreaInfoByBankNo(String bankNo) {
		List l = commQueryDAO.findBySQLQuery("select BANK_NAME,PROVINCE,CITY,SUBBRANCH_NAME,SUBBRANCH_ID from TBL_SUBBRANCH_INFO where SUBBRANCH_ID='"+bankNo+"'");
		if(l!=null&&l.size()>0){
			Object[] s = (Object[]) l.get(0);
			Map m = new HashMap();
			m.put("bank_name", s[0]);
			m.put("province", s[1]);
			m.put("city", s[2]);
			m.put("subbranch_name", s[3]);
			m.put("subbranch_id", s[4]);
			return m;
		}
		return null;
	}
	
	@Override
	public TblAgentFeeCfg getAgentInfo(String brhId) {
//		//查询分润信息
//		TblAgentFeeCfg tblAgentFeeCfg = tblAgentFeeCfgDAO.findByBrhid(brhId);
//		List<TblAgentRateInfo> rateList = null;
//		if(tblAgentFeeCfg!=null){
//			String discId = tblAgentFeeCfg.getDiscId();
//			//查询分润费率
//			rateList = tblAgentRateInfoDAO.findByDiscid(discId);
//		}
//		Map returnMap = new HashMap();
//		returnMap.put("feeCfg", tblAgentFeeCfg);
//		returnMap.put("rateList", rateList);
		return tblAgentFeeCfgDAO.findByBrhid(brhId);
	}
	
	@Override
	public TblAgentFeeCfgHis getAgentInfoHis(String brhId, String seqId) {
//		//查询分润信息
//		TblAgentFeeCfg tblAgentFeeCfg = tblAgentFeeCfgDAO.findByBrhid(brhId);
//		List<TblAgentRateInfo> rateList = null;
//		if(tblAgentFeeCfg!=null){
//			String discId = tblAgentFeeCfg.getDiscId();
//			//查询分润费率
//			rateList = tblAgentRateInfoDAO.findByDiscid(discId);
//		}
//		Map returnMap = new HashMap();
//		returnMap.put("feeCfg", tblAgentFeeCfg);
//		returnMap.put("rateList", rateList);
		return tblAgentFeeCfgHisDAO.findByBrhidSeqId(brhId, seqId);
	}
	
	public void setTblAgentFeeCfgDAO(TblAgentFeeCfgDAO tblAgentFeeCfgDAO) {
		this.tblAgentFeeCfgDAO = tblAgentFeeCfgDAO;
	}
	public void setTblAgentRateInfoDAO(TblAgentRateInfoDAO tblAgentRateInfoDAO) {
		this.tblAgentRateInfoDAO = tblAgentRateInfoDAO;
	}
	public void setTblProfitRateInfoDAO(TblProfitRateInfoDAO tblProfitRateInfoDAO) {
		this.tblProfitRateInfoDAO = tblProfitRateInfoDAO;
	}
	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}
	public void setTblBrhInfoDAO(TblBrhInfoDAO tblBrhInfoDAO) {
		this.tblBrhInfoDAO = tblBrhInfoDAO;
	}
	public void setTblBrhSettleInfDAO(TblBrhSettleInfDAO tblBrhSettleInfDAO) {
		this.tblBrhSettleInfDAO = tblBrhSettleInfDAO;
	}



	public ShTblOprInfoDAO getShTblOprInfoDAO() {
		return shTblOprInfoDAO;
	}



	public void setShTblOprInfoDAO(ShTblOprInfoDAO shTblOprInfoDAO) {
		this.shTblOprInfoDAO = shTblOprInfoDAO;
	}

	public TblAgentFeeCfgHisDAO getTblAgentFeeCfgHisDAO() {
		return tblAgentFeeCfgHisDAO;
	}

	public void setTblAgentFeeCfgHisDAO(TblAgentFeeCfgHisDAO tblAgentFeeCfgHisDAO) {
		this.tblAgentFeeCfgHisDAO = tblAgentFeeCfgHisDAO;
	}

	public TblAgentRateInfoHisDAO getTblAgentRateInfoHisDAO() {
		return tblAgentRateInfoHisDAO;
	}

	public void setTblAgentRateInfoHisDAO(TblAgentRateInfoHisDAO tblAgentRateInfoHisDAO) {
		this.tblAgentRateInfoHisDAO = tblAgentRateInfoHisDAO;
	}



	public TblBrhInfoHisDAO getTblBrhInfoHisDAO() {
		return tblBrhInfoHisDAO;
	}

	public void setTblBrhInfoHisDAO(TblBrhInfoHisDAO tblBrhInfoHisDAO) {
		this.tblBrhInfoHisDAO = tblBrhInfoHisDAO;
	}

	public TblBrhSettleInfHisDAO getTblBrhSettleInfHisDAO() {
		return tblBrhSettleInfHisDAO;
	}

	public void setTblBrhSettleInfHisDAO(TblBrhSettleInfHisDAO tblBrhSettleInfHisDAO) {
		this.tblBrhSettleInfHisDAO = tblBrhSettleInfHisDAO;
	}

	public TblBrhCashInfoDAO getTblBrhCashInfoDAO() {
		return tblBrhCashInfoDAO;
	}

	public void setTblBrhCashInfoDAO(TblBrhCashInfoDAO tblBrhCashInfoDAO) {
		this.tblBrhCashInfoDAO = tblBrhCashInfoDAO;
	}

	public TblBrhCashInfoHisDAO getTblBrhCashInfoHisDAO() {
		return tblBrhCashInfoHisDAO;
	}

	public void setTblBrhCashInfoHisDAO(TblBrhCashInfoHisDAO tblBrhCashInfoHisDAO) {
		this.tblBrhCashInfoHisDAO = tblBrhCashInfoHisDAO;
	}
}
