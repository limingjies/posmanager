package com.allinfinance.struts.pos;

public class TblTermManagementConstants {
	
	public final static String NAME = "TblTermManagement";
	
	public final static String TERM = "TERM";
	public final static String BATCH = "BATC";
	public final static String BATCH_NEXT_ID = "T";
	public final static String EPOS = "EP";
	public final static String POS = "P";
	public final static String TERM_TYPE_EPOS = "0";
	public final static String TERM_TYPE_POS = "1";

	
	public final static String DEFAULT_BRANCH_NO = "0000";
	

	public final static String TERM_STATE_SIGNED = "4";
	public final static String TERM_STATE_INVALID = "1";
	public final static String TERM_STATE_RECI = "3";
	public final static String TERM_STATE_RECI_UNCHECK = "2";	
	public final static String TERM_STATE_NORMAL = "0";
	public final static String TERM_STATE_END = "5";
	
	public final static String T30301_01 = "30301.01";
	public final static String T30301_02 = "30301.02";
	public final static String T30301_03 = "30301.03";
	public final static String T30301_04 = "30301.04";
	public final static String T30301_05 = "30301.05";
	public final static String T30301_06 = "30301.06";
	public final static String T30301_07 = "30301.07";
	public final static String T30301_08 = "30301.08";
	public final static String T30302_01 = "30302.01";
	public final static String T30302_02 = "30302.02";
	public final static String T30305_01 = "30305.01";
	public final static String T30305_02 = "30305.02";
	public final static String T30305_22 = "30305.22";
	public final static String T30305_03 = "30305.03";
	public final static String T30305_32 = "30305.32";
	public final static String T30305_04 = "30305.04";
	public final static String T30305_05 = "30305.05";
	public final static String T30305_52 = "30305.52";
	
	
	public final static String T30307_01="30307.01";
	
	public final static String T30307_02="30307.02";
	
	public final static String T30307_03="30307.03";

	/*
	 * 机具状态：入库--待使用
	 */
	public final static String STAT_NEW = "0";
	/*
	 * 机具状态：绑定--使用中
	 */
	public final static String STAT_USE = "1";
	/*
	 * 机具状态：维修--维修中
	 */
	public final static String STAT_MAINTAIN = "2";
	/*
	 * 机具状态：作废--已作废
	 */
	public final static String STAT_OUT = "3";
	/*
	 * 机具状态：丢失--已丢失
	 */
	public final static String STAT_LOST = "4";
	/*
	 * 机具状态：上缴--机具上缴待接收
	 */
	public final static String STAT_UP = "5";
	/*
	 * 机具状态：下发--机具下发待接收
	 */
	public final static String STAT_DOWN = "6";
}