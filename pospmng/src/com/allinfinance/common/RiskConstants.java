package com.allinfinance.common;

public class RiskConstants {

	/**启用风控模型*/
	public static final String START_RISK = "1";
	/**停用风控模型*/
	public static final String STOP_RISK = "0";
	
	/**风控模型状态变更*/
	public static final String RISK_STATUS = "风控模型状态";
	/**风控警报级别变更*/
	public static final String RISK_WORING_LVL = "风控警报级别";
	
	/**风控等级*/
	public static final String RISK_LVL = "风控等级";
	
	/**风控模型启用状态*/
	public static final String RISK_STATUS_START = "启用";
	/**风控模型停用状态*/
	public static final String RISK_STATUS_STOP = "停用";
	
	
	/**风控统计报表头*/
	public static final String RISK_TOTAL_TITEL = "RISK_TOTAL_TITEL";
	
	
	
	/**交易状态：失败*/
	public static final String TXN_FAIL = "A";
	
	/**风险提示函阅读标志：1-已阅读*/
	public static String READ_STATUS = "1";
	/**风险调查函回执标志：1-已回执*/
	public static String RECEIPT_STATUS = "2";
	/**白名单商户审核状态：1-未审核*/
	public static String UNCHECK = "1";
	/**白名单商户审核状态：2-初审通过*/
	public static String  FST_CHECK_T= "2";
	/**白名单商户审核状态：3-初审不通过*/
	public static String FST_CHECK_F = "3";
	/**白名单商户审核状态：4-终审通过*/
	public static String LAST_CHECK_T = "4";
	/**白名单商户审核状态：5-终审不通过*/
	public static String LAST_CHECK_F = "5";
	
	/**风险商户等级——白名单*/
	public static final String RISK_LVL_WHITE_MCHT = "4";
	/**风控模型修改关联项——无限制*/
	public static final String RISK_CONN_ALL = "A";
	/**风控模型修改关联项——停用或启用*/
	public static final String RISK_CONN_SWITCH = "S";
	/**风控模型修改关联项——警报级别*/
	public static final String RISK_CONN_WARN_LVL = "W";
	/**风控模型修改关联项——风控模型参数值重定义*/
	public static final String RISK_CONN_PARAM_UPD = "P";
	
	/**新增银行卡黑名单信息*/
	public static final String ADD_BANK_CARD_BLACK = "1";
	/**删除银行卡黑名单信息*/
	public static final String DEL_BANK_CARD_BLACK = "0";
}
