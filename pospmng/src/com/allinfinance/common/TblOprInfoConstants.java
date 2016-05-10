package com.allinfinance.common;

/**
 * Title:用户管理的常量
 *
 * Description:
 *
 * Copyright: Copyright (c) 2010-11-11
 *
 *
 * @version 1.0
 */
public class TblOprInfoConstants {
	/**用户正常*/
	public static final String STATUS_OK = "0";
	/**用户注销*/
	public static final String STATUS_OUT = "1";
	/**用户初始化*/
	public static final String STATUS_INIT = "2";
	/**当日密码错误次数**/
	public static final String PWD_WR_TM="0";
	/**密码错误总次数**/
	public static final String PWD_WR_TM_TOTAL="0";
	/**密码错误最后日期**/
	public static final String PWD_WR_LAST_DT="00000000";
	/**新用户修改密码*/
	public static final String LOGIN_CODE_INIT = "00";
	/**用户密码过期*/
	public static final String LOGIN_CODE_PWD_OUT = "01";
	/**用户登录事件代码*/
	public static final String LOGIN_EVENT_CODE = "code";
	/**通联管理用户*/
	public static final String TONGLIAN_ROLE_LEVEL = "9";
	
	/**初始化当前登录信息*/
	public static final String CURRENT_LOGIN_INFO = "-";
	/**初始化密码连续错误次数*/
	public static final String PWD_WR_TM_CONTINUE = "0";
	
	
	/**商户管理员*/
	public static final String SUP_MCHT_ROLE = "1";
	/**机构管理员*/
	public static final String SUP_BRH_ROLE = "4";
	/**默认添加商户操作员编号*/
	public static final String DEFAULT_OPR_NO = "admin";
	/**默认添加商户操作员名称*/
	public static final String DEFAULT_OPR_NAME = "商户管理员";
}
