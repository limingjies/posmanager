/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-7-30       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2010 allinfinance, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai allinfinance Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with allinfinance.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.allinfinance.common;

/**
 * Title:系统参数宏定义
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-7-30
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class SysParamConstants {
	/**产品模式标识*/
	public static final String PRODUCTION_MODE = "PRODUCTION_MODE";
	/**操作员密码过期期限*/
	public static final String OPR_PWD_OUT_DAY = "OPR_PWD_OUT_DAY";
	/**终端主密钥密钥索引*/
	public static final String ZMK_KEY_INDEX = "ZMK_KEY_INDEX";
	/**终端密钥密钥索引*/
	public static final String KEY_INDEX = "KEY_INDEX";
	/**报表模版信息路径*/
	public static final String REPORT_MODEL_DIR = "REPORT_MODEL_DIR";
	/**临时文件存放磁盘*/
	public static final String TEMP_FILE_DISK = "TEMP_FILE_DISK";
	/**上传文件服务器目录*/
	public static final String FILE_UPLOAD_DISK = "FILE_UPLOAD_DISK";
	/**清算代发文件下载*/
	public static final String FILE_PATH_SETTLE_DOWNLOAD = "FILE_PATH_SETTLE_DOWNLOAD";
	
	/**商户营销活动代发文件下载*/
	public static final String FILE_PATH_MARKET_DOWNLOAD = "FILE_PATH_MARKET_DOWNLOAD";
	/**系统强制密码**/
	public static final String RESET_PWD = "RESET_PWD";
	/**终端密钥下发文件地址**/
	public static final String FILE_UPLOAD_TMK = "FILE_UPLOAD_TMK";
	/** 代收付源文件临时文件下载 **/
	public static final String TMP_FILE_DSF = "TMP_FILE_DSF";
	/** 代收付初审未审核 **/
	public static final String PRE_NO_ADUIT ="PRE_NO_ADUIT";
	/** 代收付初审通过**/
	public static final String PREADUIT_PASS ="PREADUIT_PASS";
	/** 代收付初审未通过**/
	public static final String PREADUIT_REFUSE ="PREADUIT_REFUSE";
	/** 代收付初审重审**/
	public static final String PREADUIT_REDO ="PREADUIT_REDO";
	/** 代收付复审未审核**/
	public static final String CHECK_NO_ADUIT ="CHECK_NO_ADUIT";
	/** 代收付复审通过**/
	public static final String CHECKADUIT_PASS ="CHECKADUIT_PASS";
	/** 代收付复审未通过**/
	public static final String CHECKADUIT_REFUSE ="CHECKADUIT_REFUSE";
	/** 代收付复审重审**/
	public static final String CHECKADUIT_REDO ="CHECKADUIT_REDO";
	public static final String OPR_DEFAULT_PWD = "OPR_DEFAULT_PWD";
	/**待下载文件存放磁盘*/
	public static final String FILE_DOWNLOAD_DISK = "FILE_DOWNLOAD_DISK";
	/**批量录入商户反馈文件存放磁盘*/
	public static final String MCHNT_BLUK_IMP_RET="MCHNT_BLUK_IMP_RET";
	
	/**批量录入商户反馈文件存放磁盘*/
	public static final String ROUTE_BLUK_IMP_RET="ROUTE_BLUK_IMP_RET";
	
	/**单用户登陆限制标志*/
	public static final String SINGAL_USER = "SINGAL_USER";
	/**单用户登陆--用户集*/
	public static final String USER_LIST="USER_LIST";
	
	/**密码连续错误次数*/
	public static final String PWD_WR_TM_CONTINUE = "PWD_WR_TM_CONTINUE";
	/**操作员锁定时间*/
	public static final String LOCK_TIME = "LOCK_TIME";
	
	/**终端参数term_para电话号码*/
	public static final String TERM_PARA_PHONE="TERM_PARA_PHONE";
	/**终端参数term_para 26key*/
	public static final String  TERM_PARA_22_KEY="TERM_PARA_22_KEY";
	
	/**socket_ip*/
	public static final String SOCKET_IP="IP";
	/**socket_port*/
	public static final String  SOCKET_PORT="PORT";
	/**socket_batch_port批量端口*/
	public static final String  SOCKET_BATCH_PORT="BATCH_PORT";
	/**socket_batch_port批量端口_T0*/
	public static final String  SOCKET_BATCH_PORT_NEW="BATCH_PORT_NEW";
	
	
	/**划付文件存放磁盘*/
	public static final String PAY_FILE_DISK = "PAY_FILE_DISK";
	/**中信划付文件名称*/
	public static final String DF_FILE_NAME_ZXFK="ZXFK_";
	/**中信划网银付文件名称*/
	public static final String DF_FILE_NAME_ZXWYFK="ZXWYFK_";
	/**工行划付文件名称*/
	public static final String  DF_FILE_NAME_GSFK="GSFK_";
	/**邮储划付文件名称*/
	public static final String  DF_FILE_NAME_YCFK="DF00000000";
	/**畅捷划付文件名称*/
	public static final String  DF_FILE_NAME_CJFK="CJFK_";
	/**畅捷退货名称*/
	public static final String  RETURN_FILE_NAME_CJ="CJ_REF_";
	
	/**中信结算渠道代码*/
	public static final String  CHANNEL_CODE_ZX= "CHANNEL_CODE_ZX";
	/**中信结算网银渠道代码*/
	public static final String  CHANNEL_CODE_ZXWY= "CHANNEL_CODE_ZXWY";
	/**工行结算渠道代码*/
	public static final String 	CHANNEL_CODE_GH= "CHANNEL_CODE_GH";
	/**邮储结算渠道代码*/
	public static final String  CHANNEL_CODE_YC= "CHANNEL_CODE_YC";
	/**畅捷支付渠道代码*/
	public static final String  TXN_CODE_CJ= "TXN_CODE_CJ";
	/**畅捷支付渠道代码*/
	public static final String  TXN_CODE_CJ_NEW= "TXN_CODE_CJ_NEW";
	
	
	/**模板路径*/
	public static final String  TEMPLET_PATH= "TEMPLET_PATH";
	/**银联卡表模板名称*/
	public static final String  CARD_BIN_TEMPLET_NAME="cardBinTemplet.txt";
	/**银联卡表模板下载名称*/
	public static final String  CARD_BIN_TEMPLET="CARD_BIN_TEMPLET";
	/**路由批量模板名称*/
	public static final String  ROUTE_TEMPLET_NAME="routeTemplet.zip";
	/**路由批量模板下载名称*/
	public static final String  ROUTE_TEMPLET="ROUTE_TEMPLET";
	/**商户批量模板名称*/
	public static final String  MCHT_TEMPLET_NAME="mchtTemplet.zip";
	/**商户批量模板下载名称*/
	public static final String  MCHT_TEMPLET="MCHT_TEMPLET";
	
	/**划付明细打包文件*/
	public static final String  PAY_SETTLE_DTL_NAME="PAY_SETTLE_DTL_NAME";
	
	
	
	/** ftp参数*/
	/**SFTP服务器地址*/
	public static final String SFTP_IP = "ftpIP";
	/**SFTP服务器端口*/
	public static final int SFTP_PORT = 22;
	
	/**SFTP服务器登录用户名*/
	public static final String SFTP_Name = "ftpName";
	/**SFTP服务器登录密码*/
	public static final String SFTP_Pwd = "ftpPwd";
	/**清算对账报表路径*/
	public static final String FILE_PATH_SETTLE_REPORT = "FILE_PATH_SETTLE_REPORT";
	
	/**SFTP服务器登录用户名_新清结算*/
	public static final String SFTP_NAME_NEW = "ftpNameNew";
	/**SFTP服务器登录密码_新清结算*/
	public static final String SFTP_PWD_NEW = "ftpPwdNew";
	/**清算对账报表路径_新清结算*/
	public static final String FILE_PATH_SETTLE_REPORT_NEW = "FILE_PATH_SETTLE_REPORT_NEW";
	
	/**机构秘钥*/
	public static final String BRHINFO_KEY = "BRHINFO_KEY";
	
	/**
	 * 图片映射路径
	 */
	public static final String UPLOAD_CHILD_FOLDER = "UPLOAD_CHILD_FOLDER";
	/**
	 * 图片路径映射名
	 */
	public static final String UPLOAD_CHILD_FOLDER_NAME = "UPLOAD_CHILD_FOLDER_NAME";
	
	/**
	 * TMS服务器IP
	 */
	public static final String TMS_SERVER = "TMS_SERVER";
	/**
	 * TMS服务器端口
	 */
	public static final String TMS_PORT = "TMS_PORT";
	/**
	 * TMS服务Action
	 */
	public static final String TMS_IMPORT_ACTION = "TMS_IMPORT_ACTION";
	/**
	 * TMS联机策略
	 */
	public static final String TMS_ONLINE_POLICY = "TMS_ONLINE_POLICY";
	/**
	 * 差错交易人工调账上传文件目录
	 */
	public static final String ADJUST_OFFLINE_FILE_DISK = "AJUST_OFFLINE_FILE_DISK";
	/**
	 * 终端是否强制下载主密钥（默认1）
	 * 1-未下载主密钥，需要下载主密钥； 2-已下载主密钥， 不能再下载主密钥
	 */
	public static final String TERM_DOWNLOAD_KEY_MANDATORY = "TERM_DOWNLOAD_KEY_MANDATORY";
	/**
	 * 终端是否强制联机报道（默认2）
	 * 0-不需要更新。 1-建议更新（查询流水， 当天是否拒绝过， 如果没拒绝过， 本次拒绝， 如果已经拒绝过，允许签到）。 2-必须更新。
	 */
	public static final String TERM_ONLINEREPORT_MANDATORY = "TERM_ONLINEREPORT_MANDATORY";
}
