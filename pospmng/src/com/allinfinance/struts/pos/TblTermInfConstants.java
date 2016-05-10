package com.allinfinance.struts.pos;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-20
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @version 1.0
 */
public class TblTermInfConstants {
	/**
	 * 	新增未审核:0;
	 *	启用:1;
	 *	修改未审核:2;
	 *	冻结未审核:3;
	 *	冻结:4;
	 *	恢复未审核:5;
	 *	注销未审核:6;
	 *	注销:7;
	 *	新增审核拒绝:8;
	 *	修改审核拒绝:9;
	 *	冻结审核拒绝:A;
	 *	恢复审核拒绝:B;
	 *	注销审核拒绝:C;
	 *	复制未修改:D;
	 */
	public static final String TERM_STA_INIT = "0";
	public static final String TERM_STA_RUN = "1";
	public static final String TERM_STA_MOD_UNCHK = "2";
	public static final String TERM_STA_STOP_UNCHK = "3";
	public static final String TERM_STA_STOP = "4";
	public static final String TERM_STA_REC_UNCHK = "5"; 
	public static final String TERM_STA_CANCEL_UNCHK = "6";
	public static final String TERM_STA_CANCEL = "7";
	public static final String TERM_STA_ADD_REFUSE = "8";
	public static final String TERM_STA_MOD_REFUSE = "9";
	public static final String TERM_STA_STOP_REFUSE = "A";
	public static final String TERM_STA_REC_REFUSE = "B";
	public static final String TERM_STA_CANCEL_REFUSE = "C";
	public static final String TERM_STA_COPY = "D";
	public static final String TERM_STA_COPY_REFUSE="D1";
	
	public static final String DEFUALT_CHECKBOX = "1";
	/**
	 * 	启用:0
	 *	不可用:1
	 */
	public static final String TERM_SIGN_STA_OK = "0";
	public static final String TERM_SIGN_STA_ERR = "1";
	/**
	 * 签到默认状态
	 */
	public static final String TERM_SIGN_DEFAULT = "0";
	/**
	 * data: [['0','钱宝所有'],['1','合作伙伴所有'],['2','商户自有']]
	 */
	public static final String PROP_TP_OWN = "0";
	public static final String PROP_TP_RENT = "1";
	public static final String PROP_TP_OTHERS = "2";
	
	
	/**
	 *  0-普通POS
	 *	1-财务POS
	 *	2-签约POS
	 *	3-电话POS
	 *	4-MISPOS
	 *	5-移动POS
	 *	6-网络POS
	 */
	public static final String TERM_TP_0 = "0";
	public static final String TERM_TP_1 = "1";
	public static final String TERM_TP_2 = "2";
	public static final String TERM_TP_3 = "3";
	public static final String TERM_TP_4 = "4";
	public static final String TERM_TP_5 = "5"; 
	public static final String TERM_TP_6 = "6"; 
	/**
	 * 1-选择
	 * 0-未选择
	 */
	public static final String CHECKED = "1";
	public static final String UNCHECKED = "0";
	
	public static final String OK = "0";
	public static final String REFUSE = "1";
	public static final String T30101_01 = "30101.01";
	public static final String T30101_02 = "30101.02";
	public static final String T30101_03 = "30101.03";
	public static final String T30101_04 = "30101.04";
	public static final String T30101_05 = "30101.05";
	public static final String T30101_06 = "30101.06";
	public static final String T30101_09 = "30101.09";
	public static final String T30201_01 = "30201.01";
	public static final String T30201_02 = "30201.02";
	public static final String T30201_03 = "30201.03";
	public static final String T30103_01 = "30103.01";
}
