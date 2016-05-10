package com.allinfinance.bo.impl.risk;



import java.math.BigDecimal;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.allinfinance.bo.impl.daytrade.AdjustAcct;
import com.allinfinance.bo.risk.T40211BO;
import com.allinfinance.common.CommonsConstants;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.TblMchntInfoConstants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.daytrade.WebFrontTxnLogDAO;
import com.allinfinance.dao.iface.risk.TblAlarmCardDAO;
import com.allinfinance.dao.iface.risk.TblAlarmMchtDAO;
import com.allinfinance.dao.iface.risk.TblAlarmTxnDAO;
import com.allinfinance.dao.iface.risk.TblBankCardBlackDAO;
import com.allinfinance.dao.iface.risk.TblBillReceiptDAO;
import com.allinfinance.dao.iface.risk.TblChkFreezeDAO;
import com.allinfinance.dao.iface.risk.TblRemindRiskMchtDAO;
import com.allinfinance.dao.iface.risk.TblRemindRiskTxnDAO;
import com.allinfinance.dao.iface.risk.TblRiskAlarmDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtBaseInfDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtBaseInfTmpDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtSettleInfDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtSettleInfTmpDAO;
import com.allinfinance.frontend.dto.acctmanager.TransConsumptionDto;
import com.allinfinance.po.daytrade.WebFrontTxnLog;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInf;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmp;
import com.allinfinance.po.risk.TblAlarmCard;
import com.allinfinance.po.risk.TblAlarmCardPK;
import com.allinfinance.po.risk.TblAlarmMcht;
import com.allinfinance.po.risk.TblAlarmMchtPK;
import com.allinfinance.po.risk.TblAlarmTxn;
import com.allinfinance.po.risk.TblAlarmTxnPK;
import com.allinfinance.po.risk.TblBankCardBlack;
import com.allinfinance.po.risk.TblBillReceipt;
import com.allinfinance.po.risk.TblChkFreeze;
import com.allinfinance.po.risk.TblChkFreezePK;
import com.allinfinance.po.risk.TblRemindRiskMcht;
import com.allinfinance.po.risk.TblRemindRiskMchtPK;
import com.allinfinance.po.risk.TblRemindRiskTxn;
import com.allinfinance.po.risk.TblRemindRiskTxnPK;
import com.allinfinance.po.risk.TblRiskAlarm;
import com.allinfinance.po.risk.TblRiskAlarmPK;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.GenerateNextId;

public class T40211BOTarget implements T40211BO {
	
	private ICommQueryDAO commQueryDAO;
	
	//风控警报
	private TblRiskAlarmDAO tblRiskAlarmDAO;
	private TblAlarmCardDAO tblAlarmCardDAO;
	private TblAlarmMchtDAO tblAlarmMchtDAO;
	private TblAlarmTxnDAO tblAlarmTxnDAO;
	
	//冻结交易
//	private TblFreezeTxnDAO tblFreezeTxnDAO;
	private TblChkFreezeDAO tblChkFreezeDAO;
	
	//前置交易流水
	public WebFrontTxnLogDAO webFrontTxnLogDAO;
	
	//商服风险提醒、调单回执
	private TblRemindRiskMchtDAO tblRemindRiskMchtDAO;
	private TblRemindRiskTxnDAO tblRemindRiskTxnDAO;
	private TblBillReceiptDAO tblBillReceiptDAO;
	

	private TblBankCardBlackDAO tblBankCardBlackDAO;

	@Override
	public String updateAlarm(TblRiskAlarm tblRiskAlarm) {
		// TODO Auto-generated method stub
		tblRiskAlarmDAO.update(tblRiskAlarm);
		return Constants.SUCCESS_CODE;
	}


	@Override
	public TblAlarmTxn getTxn(TblAlarmTxnPK tblAlarmTxnPK) {
		// TODO Auto-generated method stub
		return tblAlarmTxnDAO.get(tblAlarmTxnPK);
	}


	@Override
	public TblAlarmMcht getMcht(TblAlarmMchtPK tblAlarmMchtPK) {
		// TODO Auto-generated method stub
		return tblAlarmMchtDAO.get(tblAlarmMchtPK);
	}


	@Override
	public TblAlarmCard getCard(TblAlarmCardPK tblAlarmCardPK) {
		// TODO Auto-generated method stub
		return tblAlarmCardDAO.get(tblAlarmCardPK);
	}


	@Override
	public TblRiskAlarm getAlarm(TblRiskAlarmPK tblRiskAlarmPK) {
		// TODO Auto-generated method stub
		return tblRiskAlarmDAO.get(tblRiskAlarmPK);
	}


	/**风险提醒*/
	@Override
	public String remindTxn(TblAlarmTxn tblAlarmTxn) {
		// TODO Auto-generated method stub
		Operator operator = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		String querySql="select count(1) from tbl_remind_risk_txn "
				+ " where inst_date='"+tblAlarmTxn.getTblAlarmTxnPk().getInstDate()+"' "
				+ " and sys_seq_num='"+tblAlarmTxn.getTblAlarmTxnPk().getSysSeqNum()+"' ";
		String count=commQueryDAO.findCountBySQLQuery(querySql);
		if(!"0".equals(count)){
			return "该交易已经被风险提醒，请重新刷新选择！";
		}
		
		TblRemindRiskTxnPK tblRemindRiskTxnPK=new TblRemindRiskTxnPK();
		tblRemindRiskTxnPK.setRemindId(GenerateNextId.getRemindTxnId());
		tblRemindRiskTxnPK.setRiskDate(CommonFunction.getCurrentDate());
		
		TblRemindRiskTxn tblRemindRiskTxn=new TblRemindRiskTxn();
		tblRemindRiskTxn.setTblRemindRiskTxnPK(tblRemindRiskTxnPK);
		tblRemindRiskTxn.setCrtOpr(operator.getOprId());
		tblRemindRiskTxn.setCrtTime(CommonFunction.getCurrentDateTime());
		tblRemindRiskTxn.setInstDate(tblAlarmTxn.getTblAlarmTxnPk().getInstDate());
		tblRemindRiskTxn.setMchtNo(tblAlarmTxn.getCardAccpId());
		tblRemindRiskTxn.setPan(StringUtil.isNotEmpty(tblAlarmTxn.getPan())?tblAlarmTxn.getPan().trim():"");
		tblRemindRiskTxn.setSysSeqNum(tblAlarmTxn.getTblAlarmTxnPk().getSysSeqNum());
		tblRemindRiskTxn.setMisc("0");
		tblRemindRiskTxnDAO.save(tblRemindRiskTxn);
		
//		tblAlarmTxn.setCautionFlag(CommonsConstants.CAUTION_FLAG);
//		tblAlarmTxnDAO.update(tblAlarmTxn);
		String sql="update TBL_ALARM_TXN set CAUTION_FLAG='1' "
				+ " where INST_DATE='"+tblAlarmTxn.getTblAlarmTxnPk().getInstDate()+"' "
				+ " and SYS_SEQ_NUM='"+tblAlarmTxn.getTblAlarmTxnPk().getSysSeqNum()+"' ";
		commQueryDAO.excute(sql);
		return Constants.SUCCESS_CODE;
	}
	
	
	/**风险调单*/
	@Override
	public String receiptTxn(TblAlarmTxn tblAlarmTxn) {
		// TODO Auto-generated method stub
		Operator operator = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		
		String querySql="select count(1) from tbl_bill_receipt "
				+ " where inst_date='"+tblAlarmTxn.getTblAlarmTxnPk().getInstDate()+"' "
				+ " and sys_seq_num='"+tblAlarmTxn.getTblAlarmTxnPk().getSysSeqNum()+"' ";
		String count=commQueryDAO.findCountBySQLQuery(querySql);
		if(!"0".equals(count)){
			return "该交易已经被调单处理,请重新刷新选择！";
		}
		
		TblBillReceipt tblBillReceipt=new TblBillReceipt();
		tblBillReceipt.setAmtTrans(tblAlarmTxn.getAmtTrans());
		tblBillReceipt.setBillId(GenerateNextId.getBillId());
		tblBillReceipt.setBillOpr(operator.getOprId());
		tblBillReceipt.setBillTime(CommonFunction.getCurrentDateTime());
//		tblBillReceipt.setBrhId(brhId);
		tblBillReceipt.setInstDate(tblAlarmTxn.getTblAlarmTxnPk().getInstDate());
		tblBillReceipt.setMchtNo(tblAlarmTxn.getCardAccpId());
		tblBillReceipt.setPan(StringUtil.isNotEmpty(tblAlarmTxn.getPan())?tblAlarmTxn.getPan().trim():"");
		tblBillReceipt.setReceiptStatus(CommonsConstants.RECEIPT_FLAG);
		tblBillReceipt.setSysSeqNum(tblAlarmTxn.getTblAlarmTxnPk().getSysSeqNum());
		tblBillReceipt.setMisc("0");
		tblBillReceiptDAO.save(tblBillReceipt);
		
//		tblAlarmTxn.setReceiptFlag(CommonsConstants.RECEIPT_FLAG);
//		tblAlarmTxn.setMisc(tblBillReceipt.getBillId());
//		tblAlarmTxnDAO.update(tblAlarmTxn);
		String sql="update TBL_ALARM_TXN set RECEIPT_FLAG='1',misc='"+tblBillReceipt.getBillId()+"' "
				+ " where INST_DATE='"+tblAlarmTxn.getTblAlarmTxnPk().getInstDate()+"' "
				+ " and SYS_SEQ_NUM='"+tblAlarmTxn.getTblAlarmTxnPk().getSysSeqNum()+"' ";
		commQueryDAO.excute(sql);
		return Constants.SUCCESS_CODE;
	}


	


	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}


	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}


	public TblRiskAlarmDAO getTblRiskAlarmDAO() {
		return tblRiskAlarmDAO;
	}


	public void setTblRiskAlarmDAO(TblRiskAlarmDAO tblRiskAlarmDAO) {
		this.tblRiskAlarmDAO = tblRiskAlarmDAO;
	}


	public TblAlarmCardDAO getTblAlarmCardDAO() {
		return tblAlarmCardDAO;
	}


	public void setTblAlarmCardDAO(TblAlarmCardDAO tblAlarmCardDAO) {
		this.tblAlarmCardDAO = tblAlarmCardDAO;
	}


	public TblAlarmMchtDAO getTblAlarmMchtDAO() {
		return tblAlarmMchtDAO;
	}


	public void setTblAlarmMchtDAO(TblAlarmMchtDAO tblAlarmMchtDAO) {
		this.tblAlarmMchtDAO = tblAlarmMchtDAO;
	}


	public TblAlarmTxnDAO getTblAlarmTxnDAO() {
		return tblAlarmTxnDAO;
	}


	public void setTblAlarmTxnDAO(TblAlarmTxnDAO tblAlarmTxnDAO) {
		this.tblAlarmTxnDAO = tblAlarmTxnDAO;
	}


	public TblRemindRiskMchtDAO getTblRemindRiskMchtDAO() {
		return tblRemindRiskMchtDAO;
	}


	public void setTblRemindRiskMchtDAO(TblRemindRiskMchtDAO tblRemindRiskMchtDAO) {
		this.tblRemindRiskMchtDAO = tblRemindRiskMchtDAO;
	}


	public TblRemindRiskTxnDAO getTblRemindRiskTxnDAO() {
		return tblRemindRiskTxnDAO;
	}


	public void setTblRemindRiskTxnDAO(TblRemindRiskTxnDAO tblRemindRiskTxnDAO) {
		this.tblRemindRiskTxnDAO = tblRemindRiskTxnDAO;
	}


	public TblBillReceiptDAO getTblBillReceiptDAO() {
		return tblBillReceiptDAO;
	}


	public void setTblBillReceiptDAO(TblBillReceiptDAO tblBillReceiptDAO) {
		this.tblBillReceiptDAO = tblBillReceiptDAO;
	}

	

	public TblBankCardBlackDAO getTblBankCardBlackDAO() {
		return tblBankCardBlackDAO;
	}


	public void setTblBankCardBlackDAO(TblBankCardBlackDAO tblBankCardBlackDAO) {
		this.tblBankCardBlackDAO = tblBankCardBlackDAO;
	}
	
	


	public TblChkFreezeDAO getTblChkFreezeDAO() {
		return tblChkFreezeDAO;
	}


	public void setTblChkFreezeDAO(TblChkFreezeDAO tblChkFreezeDAO) {
		this.tblChkFreezeDAO = tblChkFreezeDAO;
	}
	
	


	public WebFrontTxnLogDAO getWebFrontTxnLogDAO() {
		return webFrontTxnLogDAO;
	}


	public void setWebFrontTxnLogDAO(WebFrontTxnLogDAO webFrontTxnLogDAO) {
		this.webFrontTxnLogDAO = webFrontTxnLogDAO;
	}


	@SuppressWarnings("unchecked")
	@Override
	public String remindMcht(TblAlarmMcht tblAlarmMcht) {
		// TODO Auto-generated method stub
		Operator operator = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		
		String querySql="select count(1) from tbl_remind_risk_mcht "
				+ " where risk_date='"+CommonFunction.getCurrentDate()+"' "
				+ " and mcht_no='"+tblAlarmMcht.getTblAlarmMchtPk().getCardAccpId()+"' ";
		String count=commQueryDAO.findCountBySQLQuery(querySql);
		if(!"0".equals(count)){
			return "当天下的该商户已经被风险提醒，请重新刷新选择！";
		}
		
		TblRiskAlarmPK tblRiskAlarmPK=new TblRiskAlarmPK();
		tblRiskAlarmPK.setAlarmId(tblAlarmMcht.getTblAlarmMchtPk().getAlarmId());
		tblRiskAlarmPK.setAlarmSeq(tblAlarmMcht.getTblAlarmMchtPk().getAlarmSeq());
		TblRiskAlarm tblRiskAlarm=tblRiskAlarmDAO.get(tblRiskAlarmPK);
			
		String sql="select "
					+ "(select nvl(sum(f.alarm_num),0) from TBL_ALARM_MCHT f where f.card_accp_id='"+tblAlarmMcht.getTblAlarmMchtPk().getCardAccpId()+"' "
						+ "and substr(f.alarm_id,1,8)='"+tblAlarmMcht.getTblAlarmMchtPk().getAlarmId().substring(0, 8)+"') as alarm_totay,"
					+ "(select nvl(sum(j.alarm_num),0) from TBL_ALARM_MCHT j "
						+ "where j.card_accp_id='"+tblAlarmMcht.getTblAlarmMchtPk().getCardAccpId()+"' ) as alarm_total,"
					+ " (select count(1) from TBL_RISK_ALARM g where g.cheat_flag='1' "
						+ "and EXISTS (select * from TBL_ALARM_MCHT h "
						+ "where h.card_accp_id='"+tblAlarmMcht.getTblAlarmMchtPk().getCardAccpId()+"'  "
						+ "and g.alarm_id=h.alarm_id and g.alarm_seq=h.alarm_seq) ) as cheat_num "
				+ " from dual ";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		String todayTimes=dataList.get(0)[0].toString();
		String totalTimes=dataList.get(0)[1].toString();
		String cheatTimes=dataList.get(0)[2].toString();
		
		TblRemindRiskMchtPK tblRemindRiskMchtPK=new TblRemindRiskMchtPK();
		tblRemindRiskMchtPK.setRemindId(GenerateNextId.getRemindMchtId());
		tblRemindRiskMchtPK.setRiskDate(CommonFunction.getCurrentDate());
		
		TblRemindRiskMcht tblRemindRiskMcht=new TblRemindRiskMcht();
		tblRemindRiskMcht.setTblRemindRiskMchtPK(tblRemindRiskMchtPK);
		tblRemindRiskMcht.setCrtOpr(operator.getOprId());
		tblRemindRiskMcht.setCrtTime(CommonFunction.getCurrentDateTime());
		tblRemindRiskMcht.setMchtNo(tblAlarmMcht.getTblAlarmMchtPk().getCardAccpId());
		tblRemindRiskMcht.setRiskLvl(tblRiskAlarm.getRiskLvl());
		tblRemindRiskMcht.setTodayTimes(Integer.parseInt(todayTimes));
		tblRemindRiskMcht.setTotalTimes(Integer.parseInt(totalTimes));
		tblRemindRiskMcht.setCheatTimes(Integer.parseInt(cheatTimes));
		tblRemindRiskMcht.setMisc("0");
		tblRemindRiskMchtDAO.save(tblRemindRiskMcht);
		
		
		
//		tblAlarmMcht.setCautionFlag(CommonsConstants.CAUTION_FLAG);
//		tblAlarmMchtDAO.update(tblAlarmMcht);
		String sqls="update TBL_ALARM_MCHT set CAUTION_FLAG='1' where CARD_ACCP_ID='"+tblAlarmMcht.getTblAlarmMchtPk().getCardAccpId()+"' ";
		commQueryDAO.excute(sqls);
		
		return Constants.SUCCESS_CODE;
	}


	@Override
	public String blockMcht(TblAlarmMcht tblAlarmMcht,Operator operator) {
		// TODO Auto-generated method stub
//		Operator operator = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		
		TblMchtBaseInfTmpDAO infTmpDAO = (TblMchtBaseInfTmpDAO) ContextUtil.getBean("tblMchtBaseInfTmpDAO");
		TblMchtBaseInfDAO infDAO = (TblMchtBaseInfDAO) ContextUtil.getBean("tblMchtBaseInfDAO");
		TblMchtSettleInfTmpDAO settleTmpDAO = (TblMchtSettleInfTmpDAO) ContextUtil.getBean("tblMchtSettleInfTmpDAO");
		TblMchtSettleInfDAO settleDAO = (TblMchtSettleInfDAO) ContextUtil.getBean("tblMchtSettleInfDAO");
		
		TblMchtSettleInfTmp settleTmp=settleTmpDAO.get(tblAlarmMcht.getTblAlarmMchtPk().getCardAccpId());
		TblMchtSettleInf settle=settleDAO.get(tblAlarmMcht.getTblAlarmMchtPk().getCardAccpId());
		TblMchtBaseInfTmp infTmp=infTmpDAO.get(tblAlarmMcht.getTblAlarmMchtPk().getCardAccpId());
		TblMchtBaseInf inf=infDAO.get(tblAlarmMcht.getTblAlarmMchtPk().getCardAccpId());
		
		infTmp.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_STOP);
		infTmp.setUpdOprId(operator.getOprId());
		infTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		inf.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_STOP);
		inf.setUpdOprId(operator.getOprId());
		inf.setRecUpdTs(CommonFunction.getCurrentDateTime());
		settleTmp.setSettleType(TblMchntInfoConstants.MCHNT_SETTLE_TYPE_NO);
		settleTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		settle.setSettleType(TblMchntInfoConstants.MCHNT_SETTLE_TYPE_NO);
		settle.setRecUpdTs(CommonFunction.getCurrentDateTime());
		
		infTmpDAO.update(infTmp);
		infDAO.update(inf);
		settleTmpDAO.update(settleTmp);
		settleDAO.update(settle);
		
		String sql="update TBL_ALARM_MCHT set BLOCK_MCHT_FLAG='1' where CARD_ACCP_ID='"+tblAlarmMcht.getTblAlarmMchtPk().getCardAccpId()+"' ";
		commQueryDAO.excute(sql);
		
		return Constants.SUCCESS_CODE;
	}


	@Override
	public String recoverMcht(TblAlarmMcht tblAlarmMcht,Operator operator) {
		// TODO Auto-generated method stub
		TblMchtBaseInfTmpDAO infTmpDAO = (TblMchtBaseInfTmpDAO) ContextUtil.getBean("tblMchtBaseInfTmpDAO");
		TblMchtBaseInfDAO infDAO = (TblMchtBaseInfDAO) ContextUtil.getBean("tblMchtBaseInfDAO");
		TblMchtSettleInfTmpDAO settleTmpDAO = (TblMchtSettleInfTmpDAO) ContextUtil.getBean("tblMchtSettleInfTmpDAO");
		TblMchtSettleInfDAO settleDAO = (TblMchtSettleInfDAO) ContextUtil.getBean("tblMchtSettleInfDAO");
		
		TblMchtBaseInfTmp infTmp=infTmpDAO.get(tblAlarmMcht.getTblAlarmMchtPk().getCardAccpId());
		TblMchtBaseInf inf=infDAO.get(tblAlarmMcht.getTblAlarmMchtPk().getCardAccpId());
		TblMchtSettleInfTmp settleTmp=settleTmpDAO.get(tblAlarmMcht.getTblAlarmMchtPk().getCardAccpId());
		TblMchtSettleInf settle=settleDAO.get(tblAlarmMcht.getTblAlarmMchtPk().getCardAccpId());
		
		infTmp.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_OK);
		infTmp.setUpdOprId(operator.getOprId());
		infTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		inf.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_OK);
		inf.setUpdOprId(operator.getOprId());
		inf.setRecUpdTs(CommonFunction.getCurrentDateTime());
		settleTmp.setSettleType(TblMchntInfoConstants.MCHNT_SETTLE_TYPE_DAY);
		settleTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		settle.setSettleType(TblMchntInfoConstants.MCHNT_SETTLE_TYPE_DAY);
		settle.setRecUpdTs(CommonFunction.getCurrentDateTime());
		
		infTmpDAO.update(infTmp);
		infDAO.update(inf);
		settleTmpDAO.update(settleTmp);
		settleDAO.update(settle);
		
		String sql="update TBL_ALARM_MCHT set BLOCK_MCHT_FLAG='0' where CARD_ACCP_ID='"+tblAlarmMcht.getTblAlarmMchtPk().getCardAccpId()+"' ";
		commQueryDAO.excute(sql);
		
		return Constants.SUCCESS_CODE;
	}


	@SuppressWarnings("unchecked")
	@Override
	public String blockCard(TblAlarmCard tblAlarmCard) {
		// TODO Auto-generated method stub
		Operator operator = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		
		if(tblBankCardBlackDAO.get(tblAlarmCard.getTblAlarmCardPk().getPan().trim())!=null){
			return "此卡已经被拉入黑名单，请重新刷新纪录查看！";
		}
		
		TblBankCardBlack tblBankCardBlack=new TblBankCardBlack();
		tblBankCardBlack.setBinNo(tblAlarmCard.getTblAlarmCardPk().getPan().trim().substring(0, 6));
		tblBankCardBlack.setCardNo(tblAlarmCard.getTblAlarmCardPk().getPan().trim());
		tblBankCardBlack.setCrtOpr(operator.getOprId());
		tblBankCardBlack.setCrtTime(CommonFunction.getCurrentDateTime());
		tblBankCardBlack.setRemarkInfo("风控警报处理");
		
		String sql=" select trim(CARD_DIS),trim(INS_ID_CD),CARD_TP from TBL_BANK_BIN_INF "
				+ " where BIN_STA_NO='"+tblBankCardBlack.getBinNo()+"' ";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
		if(dataList==null||dataList.size()==0){
			tblBankCardBlack.setCardDis("");
			tblBankCardBlack.setCardTp("");
			tblBankCardBlack.setInsIdCd("");
		}else{
			tblBankCardBlack.setCardDis(StringUtil.isNotEmpty(dataList.get(0)[0])?dataList.get(0)[0].toString():"");
			tblBankCardBlack.setCardTp(StringUtil.isNotEmpty(dataList.get(0)[2])?dataList.get(0)[2].toString():"");
			tblBankCardBlack.setInsIdCd(StringUtil.isNotEmpty(dataList.get(0)[1])?dataList.get(0)[1].toString():"");
		}
		
		tblBankCardBlackDAO.save(tblBankCardBlack);
		
		return Constants.SUCCESS_CODE;
	}


	@Override
	public String recoverCard(TblAlarmCard tblAlarmCard) {
		// TODO Auto-generated method stub
		
		if(tblBankCardBlackDAO.get(tblAlarmCard.getTblAlarmCardPk().getPan().trim())==null){
			return "此卡已经被解除黑名单，请重新刷新纪录查看！";
		}
		tblBankCardBlackDAO.delete(tblAlarmCard.getTblAlarmCardPk().getPan().trim());
		return Constants.SUCCESS_CODE;
	}


	@Override
	public String blockSettle(TblAlarmMcht tblAlarmMcht) {
		// TODO Auto-generated method stub
		TblMchtSettleInfTmpDAO infTmpDAO = (TblMchtSettleInfTmpDAO) ContextUtil.getBean("tblMchtSettleInfTmpDAO");
		TblMchtSettleInfDAO infDAO = (TblMchtSettleInfDAO) ContextUtil.getBean("tblMchtSettleInfDAO");
		
		TblMchtSettleInfTmp infTmp=infTmpDAO.get(tblAlarmMcht.getTblAlarmMchtPk().getCardAccpId());
		TblMchtSettleInf inf=infDAO.get(tblAlarmMcht.getTblAlarmMchtPk().getCardAccpId());
		infTmp.setSettleType(TblMchntInfoConstants.MCHNT_SETTLE_TYPE_NO);
		infTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		inf.setSettleType(TblMchntInfoConstants.MCHNT_SETTLE_TYPE_NO);
		inf.setRecUpdTs(CommonFunction.getCurrentDateTime());
		
		infTmpDAO.update(infTmp);
		infDAO.update(inf);
		return Constants.SUCCESS_CODE;
	}


	@Override
	public String recoverSettle(TblAlarmMcht tblAlarmMcht) {
		// TODO Auto-generated method stub
		TblMchtSettleInfTmpDAO infTmpDAO = (TblMchtSettleInfTmpDAO) ContextUtil.getBean("tblMchtSettleInfTmpDAO");
		TblMchtSettleInfDAO infDAO = (TblMchtSettleInfDAO) ContextUtil.getBean("tblMchtSettleInfDAO");
		
		TblMchtSettleInfTmp infTmp=infTmpDAO.get(tblAlarmMcht.getTblAlarmMchtPk().getCardAccpId());
		TblMchtSettleInf inf=infDAO.get(tblAlarmMcht.getTblAlarmMchtPk().getCardAccpId());
		infTmp.setSettleType(TblMchntInfoConstants.MCHNT_SETTLE_TYPE_DAY);
		infTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		inf.setSettleType(TblMchntInfoConstants.MCHNT_SETTLE_TYPE_DAY);
		inf.setRecUpdTs(CommonFunction.getCurrentDateTime());
		
		infTmpDAO.update(infTmp);
		infDAO.update(inf);
		
		return Constants.SUCCESS_CODE;
	}


	@Override
	public String freezeTxn(TblAlarmTxn tblAlarmTxn) {
		
		TblChkFreezePK tblChkFreezePK=new TblChkFreezePK();
		tblChkFreezePK.setTxnDt(tblAlarmTxn.getTblAlarmTxnPk().getInstDate());
		tblChkFreezePK.setSysSeqNum(tblAlarmTxn.getTblAlarmTxnPk().getSysSeqNum());
		
		TblChkFreeze tblChkFreeze= tblChkFreezeDAO.get(tblChkFreezePK);
		if(tblChkFreeze==null){
			tblChkFreeze=new TblChkFreeze();
			tblChkFreeze.setTblChkFreezePK(tblChkFreezePK);
			
			Object[] obj= getTxnInfo(tblChkFreezePK);
			tblChkFreeze.setTxnNum(tblAlarmTxn.getTxnNum());
			tblChkFreeze.setTransState(tblAlarmTxn.getTransState());
			tblChkFreeze.setRetrivlRef(obj[0].toString());
			tblChkFreeze.setCardAccpId(tblAlarmTxn.getCardAccpId());
			tblChkFreeze.setCardAccpTermId(tblAlarmTxn.getCardAccpTermId());
			tblChkFreeze.setTermSn(obj[1].toString());
			tblChkFreeze.setRespCode(tblAlarmTxn.getRspCode());
			tblChkFreeze.setAmtTrans(tblAlarmTxn.getAmtTrans());
			tblChkFreeze.setAmtFee(obj[2].toString());
			tblChkFreeze.setAmtStlm(obj[3].toString());
			tblChkFreeze.setPan(StringUtil.isNotEmpty(tblAlarmTxn.getPan())?tblAlarmTxn.getPan().trim():"");
			tblChkFreeze.setFreezeFlag(CommonsConstants.RISK_TXN_FREEZE);
			tblChkFreeze.setInstDate(CommonFunction.getCurrentDateTime());

			
		}else{
			if(CommonsConstants.RISK_TXN_FREEZE.equals(tblChkFreeze.getFreezeFlag())){
				return "该交易已经冻结!";
			}
			tblChkFreeze.setFreezeFlag(CommonsConstants.RISK_TXN_FREEZE);
		}
		if(!isStlm(tblChkFreezePK)){
			return "该交易已结算或者不可结算或已提现!";
		}
		
		tblChkFreeze.setLstUpdTm(CommonFunction.getCurrentDateTime());

		try {
			String retResp=buildFrontMsg(AdjustAcct.TXN_CODE_FREZACCT, tblChkFreeze);
			if (!Constants.SUCCESS_CODE.equals(retResp)) {
				return "前置冻结失败！";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		tblChkFreezeDAO.saveOrUpdate(tblChkFreeze);
		return Constants.SUCCESS_CODE;
	}


	@Override
	public String recoverTxn(TblAlarmTxn tblAlarmTxn) {
		
		TblChkFreezePK tblChkFreezePK=new TblChkFreezePK();
		tblChkFreezePK.setTxnDt(tblAlarmTxn.getTblAlarmTxnPk().getInstDate());
		tblChkFreezePK.setSysSeqNum(tblAlarmTxn.getTblAlarmTxnPk().getSysSeqNum());
		
		TblChkFreeze tblChkFreeze= tblChkFreezeDAO.get(tblChkFreezePK);
		if(tblChkFreeze==null){
			return "该数据不存在，请重新刷新选择!";
		}
		if(CommonsConstants.RISK_TXN_UNFREEZE.equals(tblChkFreeze.getFreezeFlag())){
			return "该交易已经解冻!";
		}
		
//		if(tblChkFreezePK.getTxnDt().substring(0, 8).equals(CommonFunction.getCurrentDate())){
		try {
			String retResp=buildFrontMsg(AdjustAcct.TXN_CODE_THAWACCT, tblChkFreeze);
			if (!Constants.SUCCESS_CODE.equals(retResp)) {
				return "前置解冻失败！";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		}
		tblChkFreeze.setFreezeFlag(CommonsConstants.RISK_TXN_UNFREEZE);
		
		tblChkFreeze.setLstUpdTm(CommonFunction.getCurrentDateTime());
		
		tblChkFreezeDAO.update(tblChkFreeze);
		return Constants.SUCCESS_CODE;
	}





	
	private String formatMoney(String money12){
		String inter=String.valueOf(Integer.parseInt(money12.substring(0, 10)));
		String doub=money12.substring(10);
		String newMoney=inter+"."+doub;
		return newMoney;
	}
	

	@SuppressWarnings("unchecked")
	private Object[] getTxnInfo(TblChkFreezePK tblChkFreezePK){
		String sql="select retrivl_ref,term_ssn,amt_cdhldr_bil,amt_settlmt,misc_2 "
				+ "from tbl_n_txn "
				+ "where inst_date='"+tblChkFreezePK.getTxnDt()+"' "
				+ "and sys_seq_num='"+tblChkFreezePK.getSysSeqNum()+"' ";
		List<Object[]> data=commQueryDAO.findBySQLQuery(sql);
		return data==null?new Object[5]:data.get(0);
	}

	@SuppressWarnings("unchecked")
	private boolean isStlm(TblChkFreezePK tblChkFreezePK){
		String sql="select stlm_flag from tbl_clear_dtl "
				+ "where txn_date='"+tblChkFreezePK.getTxnDt().substring(0, 8)+"' "
				+ "and txn_time='"+tblChkFreezePK.getTxnDt().substring(8)+"' "
				+ " and txn_sn ='"+tblChkFreezePK.getSysSeqNum()+"' ";
		List<String> data=commQueryDAO.findBySQLQuery(sql);
		if(data==null||data.size()==0){
			return true;
		}
		String stlmFlag=data.get(0).toString();
		if(CommonsConstants.STLM_FLAG_INIT.equals(stlmFlag)){
			return true;
		}else{
			return false;
		}
	}
	
	
	private String buildFrontMsg(String transCode, TblChkFreeze tblChkFreeze) {
		
		Object[] obj=getTxnInfo(tblChkFreeze.getTblChkFreezePK());
		// 组装调账前台系统数据
		TransConsumptionDto trc = new TransConsumptionDto();
		trc.setWebTime(CommonFunction.getCurrentDateTime());
		trc.setWebSeqNum(GenerateNextId.getSeqSysNum());
		trc.setPospInstDate(tblChkFreeze.getTblChkFreezePK().getTxnDt());
		trc.setPospSysSeqNum(tblChkFreeze.getTblChkFreezePK().getSysSeqNum());
		trc.setMerchantId(tblChkFreeze.getCardAccpId());
		trc.setTransFee(new BigDecimal(formatMoney(tblChkFreeze.getAmtFee())));
		trc.setTransAmount(new BigDecimal(formatMoney(tblChkFreeze.getAmtTrans())));
		trc.setSettleAmount(new BigDecimal(formatMoney(tblChkFreeze.getAmtStlm())));
		trc.setCardNo(tblChkFreeze.getPan());
		trc.setCardType(obj[4]==null?"":obj[4].toString().substring(103,105));
		AdjustAcct adjustAcct = new AdjustAcct();
		Object[] ret = adjustAcct.doTxn(transCode, trc);
		if (Constants.SUCCESS_CODE.equals(ret[0])) {
			WebFrontTxnLog webFrontTxnLog = (WebFrontTxnLog) ret[1];
			webFrontTxnLogDAO.save(webFrontTxnLog);
			return Constants.SUCCESS_CODE;
		} else {
			return (String) ret[1];
		}
	}
	
}