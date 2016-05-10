package com.allinfinance.bo.risk;

import com.allinfinance.common.Operator;
import com.allinfinance.po.risk.TblAlarmCard;
import com.allinfinance.po.risk.TblAlarmCardPK;
import com.allinfinance.po.risk.TblAlarmMcht;
import com.allinfinance.po.risk.TblAlarmMchtPK;
import com.allinfinance.po.risk.TblAlarmTxn;
import com.allinfinance.po.risk.TblAlarmTxnPK;
import com.allinfinance.po.risk.TblRiskAlarm;
import com.allinfinance.po.risk.TblRiskAlarmPK;


public interface T40211BO {
	
	public TblRiskAlarm getAlarm(TblRiskAlarmPK tblRiskAlarmPK);
	public String updateAlarm(TblRiskAlarm tblRiskAlarm);
	
	
	public TblAlarmTxn getTxn(TblAlarmTxnPK tblAlarmTxnPK);
	public TblAlarmMcht getMcht(TblAlarmMchtPK tblAlarmMchtPK);
	public TblAlarmCard getCard(TblAlarmCardPK tblAlarmCardPK);
	
	/**风险交易提醒*/
	public String remindTxn(TblAlarmTxn tblAlarmTxn);
	
	/**风险交易冻结*/
	public String freezeTxn(TblAlarmTxn tblAlarmTxn);
	
	/**风险交易解冻*/
	public String recoverTxn(TblAlarmTxn tblAlarmTxn);
	
	/**风险调单*/
	public String receiptTxn(TblAlarmTxn tblAlarmTxn);
	
	/**风险商户提醒*/
	public String remindMcht(TblAlarmMcht tblAlarmMcht);
	
	/**商户冻结*/
	public String blockMcht(TblAlarmMcht tblAlarmMcht,Operator operator);
	
	/**商户解冻*/
	public String recoverMcht(TblAlarmMcht tblAlarmMcht,Operator operator);
	
	/**商户结算冻结*/
	public String blockSettle(TblAlarmMcht tblAlarmMcht);
	
	/**商户结算解冻*/
	public String recoverSettle(TblAlarmMcht tblAlarmMcht);
	
	/**加入卡黑名*/
	public String blockCard(TblAlarmCard tblAlarmCard);
	
	/**解除卡黑名单*/
	public String recoverCard(TblAlarmCard tblAlarmCard);
	
	
}
