package com.allinfinance.struts.risk.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.bo.impl.daytrade.AdjustAcct;
import com.allinfinance.bo.risk.T40211BO;
import com.allinfinance.common.CommonsConstants;
import com.allinfinance.common.Constants;
import com.allinfinance.po.risk.TblAlarmCard;
import com.allinfinance.po.risk.TblAlarmCardPK;
import com.allinfinance.po.risk.TblAlarmMcht;
import com.allinfinance.po.risk.TblAlarmMchtPK;
import com.allinfinance.po.risk.TblAlarmTxn;
import com.allinfinance.po.risk.TblAlarmTxnPK;
import com.allinfinance.po.risk.TblChkFreeze;
import com.allinfinance.po.risk.TblChkFreezePK;
import com.allinfinance.po.risk.TblRiskAlarm;
import com.allinfinance.po.risk.TblRiskAlarmPK;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T40211Action extends BaseAction {

	T40211BO t40211BO = (T40211BO) ContextUtil.getBean("T40211BO");

	private String alarmId;
	private String alarmSeq;
	private String instDate;
	private String sysSeqNum;
	private String cardAccpId;
	private String pan;

	private String alarmSta;
	private String cheatFlag;
	private String cheatTp;
	private String misc;

	private long serialVersionUID = 1L;
	public String MCHT_NO;
	public String MCHT_NAME;
	public String AMOUNT;

	public String TERMINAL_NO;
	public String OPERATOR_NO;
	public String CARD_NO;
	public String TRANS_TYPE;
	public String EXP_DATE;
	public String BATCH_NO;
	public String VOUCHE_NO;
	public String AUTH_NO;
	public String DATE_TIME;
	public String REF_NO;
	public String REFERENCE;
	public String NO;
	private static Logger log = Logger.getLogger(T40211Action.class);
	public String CHECK_IMAGE = "";

	@Override
	protected String subExecute() throws Exception {
		log("操作员：" + operator.getOprId());
		if ("update".equals(method)) {
			log("风控警报处理");
			rspCode = update();
		} else if ("remindTxn".equals(method)) {
			log("交易风险提醒");
			rspCode = remindTxn();
		} else if ("receiptTxn".equals(method)) {
			log("风险调单");
			rspCode = receiptTxn();
		} else if ("freezeTxn".equals(method)) {
			log("交易冻结");
			rspCode = freezeTxn();
		} else if ("recoverTxn".equals(method)) {
			log("交易解冻");
			rspCode = recoverTxn();
		}else if ("recoverTxnDtl".equals(method)) {
			log("交易解冻（明细）");
			rspCode = recoverTxnDtl();
		} else if ("remindMcht".equals(method)) {
			log("风险商户提醒");
			rspCode = remindMcht();
		} else if ("blockMcht".equals(method)) {
			log("商户冻结");
			rspCode = blockMcht();
		} else if ("recoverMcht".equals(method)) {
			log("商户解冻");
			rspCode = recoverMcht();
		} else if ("blockSettle".equals(method)) {
			log("商户结算冻结");
			rspCode = blockSettle();
		} else if ("recoverSettle".equals(method)) {
			log("商户结算解冻");
			rspCode = recoverSettle();
		} else if ("blockCard".equals(method)) {
			log("加入卡黑名单");
			rspCode = blockCard();
		} else if ("recoverCard".equals(method)) {
			log("解除卡黑名单");
			rspCode = recoverCard();
		} else if ("show".equals(method)) {

			String sql = "select x.card_accp_name, x.card_accp_id, x.card_accp_term_id, '01', x.pan, y.txn_name, substr(x.fld_reserved, 3, 6), x.term_ssn, x.authr_id_resp, x.inst_date, x.retrivl_ref ,to_number(trim(x.amt_trans))/100,z.f62 from tbl_n_txn x, tbl_txn_name y , tbl_qgd z where x.txn_num=y.txn_num and (x.key_revsal=z.key_orgtxn and x.sys_seq_num=z.o_sys_seq_num and substr(x.inst_date, 1, 8) = z.inst_date) ";
			String where = "and x.inst_date='" + instDate
					+ "' and x.SYS_SEQ_NUM='" + sysSeqNum + "'";
			@SuppressWarnings("unchecked")
			List<Object[]> list = CommonFunction.getCommQueryDAO()
					.findBySQLQuery(sql + where);
			if (null == list || 0 == list.size()) {
				writeErrorMsg("此笔交易签购单不存在！");

			}else {
				
				writeSuccessMsg("");
			}
			return SUCCESS;
		}
		return rspCode;
	}

	/**
	 * 解除银行卡黑名单单
	 * 
	 * @return
	 */
	private String recoverCard() {
		// TODO Auto-generated method stub
		TblAlarmCardPK tblAlarmCardPK = new TblAlarmCardPK();
		tblAlarmCardPK.setAlarmId(alarmId);
		tblAlarmCardPK.setAlarmSeq(Integer.parseInt(alarmSeq));
		tblAlarmCardPK.setPan(CommonFunction.fillString(pan, ' ', 19, true));
		TblAlarmCard tblAlarmCard = t40211BO.getCard(tblAlarmCardPK);
		if (tblAlarmCard == null) {
			return "该数据不存在，请重新刷新选择！";
		}
		return t40211BO.recoverCard(tblAlarmCard);
	}

	/**
	 * 加入银行卡黑名单
	 * 
	 * @return
	 */
	private String blockCard() {
		// TODO Auto-generated method stub
		TblAlarmCardPK tblAlarmCardPK = new TblAlarmCardPK();
		tblAlarmCardPK.setAlarmId(alarmId);
		tblAlarmCardPK.setAlarmSeq(Integer.parseInt(alarmSeq));
		tblAlarmCardPK.setPan(CommonFunction.fillString(pan, ' ', 19, true));
		TblAlarmCard tblAlarmCard = t40211BO.getCard(tblAlarmCardPK);
		if (tblAlarmCard == null) {
			return "该数据不存在，请重新刷新选择！";
		}
		return t40211BO.blockCard(tblAlarmCard);
	}

	/**
	 * 商户结算解冻
	 * 
	 * @return
	 */
	private String recoverSettle() {
		// TODO Auto-generated method stub
		TblAlarmMchtPK tblAlarmMchtPK = new TblAlarmMchtPK();
		tblAlarmMchtPK.setAlarmId(alarmId);
		tblAlarmMchtPK.setAlarmSeq(Integer.parseInt(alarmSeq));
		tblAlarmMchtPK.setCardAccpId(cardAccpId);
		TblAlarmMcht tblAlarmMcht = t40211BO.getMcht(tblAlarmMchtPK);
		if (tblAlarmMcht == null) {
			return "该数据不存在，请重新刷新选择！";
		}
		return t40211BO.recoverSettle(tblAlarmMcht);
	}

	/**
	 * 商户结算冻结
	 * 
	 * @return
	 */
	private String blockSettle() {
		// TODO Auto-generated method stub
		TblAlarmMchtPK tblAlarmMchtPK = new TblAlarmMchtPK();
		tblAlarmMchtPK.setAlarmId(alarmId);
		tblAlarmMchtPK.setAlarmSeq(Integer.parseInt(alarmSeq));
		tblAlarmMchtPK.setCardAccpId(cardAccpId);
		TblAlarmMcht tblAlarmMcht = t40211BO.getMcht(tblAlarmMchtPK);
		if (tblAlarmMcht == null) {
			return "该数据不存在，请重新刷新选择！";
		}
		return t40211BO.blockSettle(tblAlarmMcht);
	}

	/**
	 * 商户解冻
	 * 
	 * @return
	 */
	private String recoverMcht() {
		// TODO Auto-generated method stub
		TblAlarmMchtPK tblAlarmMchtPK = new TblAlarmMchtPK();
		tblAlarmMchtPK.setAlarmId(alarmId);
		tblAlarmMchtPK.setAlarmSeq(Integer.parseInt(alarmSeq));
		tblAlarmMchtPK.setCardAccpId(cardAccpId);
		TblAlarmMcht tblAlarmMcht = t40211BO.getMcht(tblAlarmMchtPK);
		if (tblAlarmMcht == null) {
			return "该数据不存在，请重新刷新选择！";
		}
		return t40211BO.recoverMcht(tblAlarmMcht, operator);
	}

	/**
	 * 商户冻结
	 * 
	 * @return
	 */
	private String blockMcht() {
		// TODO Auto-generated method stub
		TblAlarmMchtPK tblAlarmMchtPK = new TblAlarmMchtPK();
		tblAlarmMchtPK.setAlarmId(alarmId);
		tblAlarmMchtPK.setAlarmSeq(Integer.parseInt(alarmSeq));
		tblAlarmMchtPK.setCardAccpId(cardAccpId);
		TblAlarmMcht tblAlarmMcht = t40211BO.getMcht(tblAlarmMchtPK);
		if (tblAlarmMcht == null) {
			return "该数据不存在，请重新刷新选择！";
		}
		return t40211BO.blockMcht(tblAlarmMcht, operator);
	}

	/**
	 * 风险商户提醒
	 * 
	 * @return
	 */
	private String remindMcht() {
		// TODO Auto-generated method stub
		TblAlarmMchtPK tblAlarmMchtPK = new TblAlarmMchtPK();
		tblAlarmMchtPK.setAlarmId(alarmId);
		tblAlarmMchtPK.setAlarmSeq(Integer.parseInt(alarmSeq));
		tblAlarmMchtPK.setCardAccpId(cardAccpId);
		TblAlarmMcht tblAlarmMcht = t40211BO.getMcht(tblAlarmMchtPK);
		if (tblAlarmMcht == null) {
			return "该数据不存在，请重新刷新选择！";
		}
		return t40211BO.remindMcht(tblAlarmMcht);
	}

	/**
	 * 风险调单
	 * 
	 * @return
	 * @throws Exception
	 */
	private String receiptTxn() {
		// TODO Auto-generated method stub
		TblAlarmTxnPK tblAlarmTxnPK = new TblAlarmTxnPK();
		tblAlarmTxnPK.setAlarmId(alarmId);
		tblAlarmTxnPK.setAlarmSeq(Integer.parseInt(alarmSeq));
		tblAlarmTxnPK.setInstDate(instDate);
		tblAlarmTxnPK.setSysSeqNum(sysSeqNum);
		TblAlarmTxn tblAlarmTxn = t40211BO.getTxn(tblAlarmTxnPK);
		if (tblAlarmTxn == null) {
			return "该数据不存在，请重新刷新选择！";
		}
		return t40211BO.receiptTxn(tblAlarmTxn);
	}

	/**
	 * 风险警报处理
	 * 
	 * @return
	 * @throws Exception
	 */
	private String update() {
		// TODO Auto-generated method stub
		TblRiskAlarmPK tblRiskAlarmPK = new TblRiskAlarmPK();
		tblRiskAlarmPK.setAlarmId(alarmId);
		tblRiskAlarmPK.setAlarmSeq(Integer.parseInt(alarmSeq));

		TblRiskAlarm tblRiskAlarm = t40211BO.getAlarm(tblRiskAlarmPK);
		if (tblRiskAlarm == null) {
			return "该数据不存在，请重新刷新选择！";
		}
		tblRiskAlarm.setAlarmSta(alarmSta);
		tblRiskAlarm.setMisc(misc);
		tblRiskAlarm.setCheatFlag(cheatFlag);
		if (CommonsConstants.CHEAT_FLAG.equals(cheatFlag)) {
			tblRiskAlarm.setCheatTp(cheatTp == null ? "" : cheatTp);
		} else {
			tblRiskAlarm.setCheatTp("");
		}
		return t40211BO.updateAlarm(tblRiskAlarm);
	}

	/**
	 * 风险交易提醒
	 * 
	 * @return
	 * @throws Exception
	 */
	private String remindTxn() throws Exception {

		TblAlarmTxnPK tblAlarmTxnPK = new TblAlarmTxnPK();
		tblAlarmTxnPK.setAlarmId(alarmId);
		tblAlarmTxnPK.setAlarmSeq(Integer.parseInt(alarmSeq));
		tblAlarmTxnPK.setInstDate(instDate);
		tblAlarmTxnPK.setSysSeqNum(sysSeqNum);
		TblAlarmTxn tblAlarmTxn = t40211BO.getTxn(tblAlarmTxnPK);
		if (tblAlarmTxn == null) {
			return "该数据不存在，请重新刷新选择！";
		}
		return t40211BO.remindTxn(tblAlarmTxn);
	}

	/**
	 * 风险交易冻结
	 * 
	 * @return
	 * @throws Exception
	 */
	private String freezeTxn() throws Exception {

		TblAlarmTxnPK tblAlarmTxnPK = new TblAlarmTxnPK();
		tblAlarmTxnPK.setAlarmId(alarmId);
		tblAlarmTxnPK.setAlarmSeq(Integer.parseInt(alarmSeq));
		tblAlarmTxnPK.setInstDate(instDate);
		tblAlarmTxnPK.setSysSeqNum(sysSeqNum);
		TblAlarmTxn tblAlarmTxn = t40211BO.getTxn(tblAlarmTxnPK);
		if (tblAlarmTxn == null) {
			return "该数据不存在，请重新刷新选择！";
		}
		return t40211BO.freezeTxn(tblAlarmTxn);
	}

	/**
	 * 风险交易解冻
	 * 
	 * @return
	 * @throws Exception
	 */
	private String recoverTxn() throws Exception {

		TblAlarmTxnPK tblAlarmTxnPK = new TblAlarmTxnPK();
		tblAlarmTxnPK.setAlarmId(alarmId);
		tblAlarmTxnPK.setAlarmSeq(Integer.parseInt(alarmSeq));
		tblAlarmTxnPK.setInstDate(instDate);
		tblAlarmTxnPK.setSysSeqNum(sysSeqNum);
		TblAlarmTxn tblAlarmTxn = t40211BO.getTxn(tblAlarmTxnPK);
		if (tblAlarmTxn == null) {
			return "该数据不存在，请重新刷新选择！";
		}
		return t40211BO.recoverTxn(tblAlarmTxn);
	}
	/**
	 * 风险交易解冻（明细解冻）
	 * 
	 * @return
	 * @throws Exception
	 */
	private String recoverTxnDtl() throws Exception {	
		TblAlarmTxnPK tblAlarmTxnPK = new TblAlarmTxnPK();
		tblAlarmTxnPK.setInstDate(instDate);
		tblAlarmTxnPK.setSysSeqNum(sysSeqNum);
		TblAlarmTxn tblAlarmTxn = new TblAlarmTxn();
		tblAlarmTxn.setTblAlarmTxnPk(tblAlarmTxnPK);
		return t40211BO.recoverTxn(tblAlarmTxn);
	}
	public String getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}

	public String getAlarmSeq() {
		return alarmSeq;
	}

	public void setAlarmSeq(String alarmSeq) {
		this.alarmSeq = alarmSeq;
	}

	public String getInstDate() {
		return instDate;
	}

	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}

	public String getSysSeqNum() {
		return sysSeqNum;
	}

	public void setSysSeqNum(String sysSeqNum) {
		this.sysSeqNum = sysSeqNum;
	}

	public String getAlarmSta() {
		return alarmSta;
	}

	public void setAlarmSta(String alarmSta) {
		this.alarmSta = alarmSta;
	}

	public String getCheatFlag() {
		return cheatFlag;
	}

	public void setCheatFlag(String cheatFlag) {
		this.cheatFlag = cheatFlag;
	}

	public String getCheatTp() {
		return cheatTp;
	}

	public void setCheatTp(String cheatTp) {
		this.cheatTp = cheatTp;
	}

	public String getMisc() {
		return misc;
	}

	public void setMisc(String misc) {
		this.misc = misc;
	}

	public String getCardAccpId() {
		return cardAccpId;
	}

	public void setCardAccpId(String cardAccpId) {
		this.cardAccpId = cardAccpId;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

}
