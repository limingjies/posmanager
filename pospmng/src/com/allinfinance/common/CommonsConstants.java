package com.allinfinance.common;

public class CommonsConstants {


	
	/**路由规则状态：0-停用*/
	public static final String ROUTE_RULE_STOP = "0";
	/**路由规则状态：1-启用*/
	public static final String ROUTE_RULE_START = "1";
	
	
	/**路由规则日期控制：1-指定日期段*/
	public static final String ROUTE_RULE_DATE_CTL = "1";
	
	/**路由规则时间控制：1-指定日期段*/
	public static final String ROUTE_RULE_TIME_CTL = "1";
	
	/**路由规则金额控制：1-指定金额段*/
	public static final String ROUTE_RULE_AMT_CTL = "1";
	
	
	
	/**差错退货审核状态：0-未审核*/
	public static String APPLY_STATE_UNCHECK = "0";
	/**差错退货审核状态：1-已审核*/
	public static String APPLY_STATE_PASS = "1";
	/**差错退货审核状态：2-拒绝*/
	public static String APPLY_STATE_REFUSE = "2";
	
	
	/**差错退货交易结果：0-失败*/
	public static String BACK_STATE_FAL = "0";
	/**差错退货交易结果：1-成功*/
	public static String BACK_STATE_SUC = "1";
	
	
	/**差错退货交易标志：C-差错退货*/
	public static String AMT_BACK_FALG_C = "C";
	/**差错退货交易标志：S-手工退货*/
	public static String AMT_BACK_FALG_S = "S";
	/**业务退货交易标志：Y-手工退货*/
	public static String AMT_BACK_FALG_Y = "Y";
	
	/**差错退货交易码：5151-退货交易*/
	public static String TXN_NUM_5151 = "5151";
	
	
	/**商户别名维护-启用标志：0-停用*/
	public static String MCHT_NAME_FLAG_STOP = "0";
	/**商户别名维护-启用标志：1-启用*/
	public static String MCHT_NAME_FLAG_START = "1";
	
	/**业务退货状态：0-待受理*/
	public static String AMT_APPLY_WAIT="0";
	/**业务退货状态：1-受理成功*/
	public static String AMT_APPLY_PASS="1";
	/**业务退货状态：2-拒绝受理*/
	public static String AMT_APPLY_REFUCE="2";
	
	
	/**是否新增一级商户号密钥：0-不新增*/
	public static String FIRST_BRH_KEY="0";
	/**是否新增一级商户号密钥：1-新增*/
	public static String FIRST_MCHT_KEY="1"; 
	
	/**新增一级商户号密钥状态：0-未生成*/
	public static String FIRST_MCHT_KEY_STA="0";
	
	/**单用户登陆标志：1-单用户*/
	public static String SINGAL_USER="1";
	
	
	/**是否选择：0-否*/
	public static String UNCHECKED="0";
	/**是否选择：1-是*/
	public static String CHECKED="1"; 
	
	
	

	/**用户所属标志：0-商户*/
    public static String OPR_MCHT_FLAG = "0";
    /**用户所属标志：1-机构*/
	public static String OPR_BRH_FLAG = "1";
	/**用户机构号或商户号为空标志：- */
	public static String OPR_BRH_MCHT_FLAG = "-";
	
	/**角色Id：2-普通商户用户*/
    public static String ROLE_ID_MCHT = "2";
    /**角色Id：3-普通机构用户*/
	public static String ROLE_ID_BRH = "3";
	
	
	/**商户种类：1-普通商户*/
    public static String COM_MCHT_FLAG = "1";
    /**商户种类：2-集团商户*/
	public static String GRP_MCHT_FLAG = "2";
	/**商户种类：3-集团子商户*/
	public static String SON_MCHT_FLAG = "3";
	
	

	
	/**欺诈标志：2-属于欺诈*/
	public static String CHEAT_FLAG="2";
	
	/**风控提示标志：1-已提醒*/
	public static String CAUTION_FLAG="1";
	
	/**风控调单标志：1-已调单*/
	public static String RECEIPT_FLAG="1";
	
	/**清算通道状态：0-停用*/
	public static final String PAY_CHANNEL_STOP = "0";
	/**清算通道状态：1-启用*/
	public static final String PAY_CHANNEL_START = "1";
	
	/**风控交易冻结状态：1-已冻结*/
	public static final String RISK_TXN_FREEZE = "1";
	/**风控交易冻结状态：2-已解冻*/
	public static final String RISK_TXN_UNFREEZE = "2";
	
	
	/**机构手续费配置启用标志：0-停用*/
	public static final String ENABLE_FLAG_STOP = "0";
	/**机构手续费配置启用标志：1-启用*/
	public static final String ENABLE_FLAG_START = "1";
	
	
	/**清分明细标志：0-待结算*/
	public static final String STLM_FLAG_INIT = "0";
}
