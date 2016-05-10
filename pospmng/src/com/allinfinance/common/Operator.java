/* @(#)
 *
 * Project:NEBMis
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2010-3-10       first release
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

import java.util.Map;

/**
 * Title:系统操作员
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-3-10
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class Operator {
	
	/**操作员编号*/
	private String oprId;
	/**操作员名称*/
	private String oprName;
	/**操作员所属机构编号*/
	private String oprBrhId;
	/**操作员所属机构名称*/
	private String oprBrhName;
	/**操作员级别*/
	private String oprDegree;
	/**操作员所属机构级别*/
	private String oprBrhLvl;
	/**本行及一下机构MAP*/
	private Map<String, String> brhBelowMap;
	/**本行及一下机构编号*/
	private String brhBelowId;
	/**本商户及一下商户编号*/
	private String mchtBelowId;
	

	/**操作员预留信息*/
	private String resvInfo;
	/**上次登录日期时间*/
	private String lastLoginTime;
	/**上次登陆IP*/
	private String lastLoginIp;
	/**上次登陆状态*/
	private String lastLoginStatus;


	/**
	 * @return the mchtBelowId
	 */
	public String getMchtBelowId() {
		return mchtBelowId;
	}

	/**
	 * @param mchtBelowId the mchtBelowId to set
	 */
	public void setMchtBelowId(String mchtBelowId) {
		this.mchtBelowId = mchtBelowId;
	}

	public String getOprId() {
		return oprId;
	}

	public void setOprId(String oprId) {
		this.oprId = oprId;
	}

	public String getOprName() {
		return oprName;
	}

	public void setOprName(String oprName) {
		this.oprName = oprName;
	}

	public String getOprBrhId() {
		return oprBrhId;
	}

	public void setOprBrhId(String oprBrhId) {
		this.oprBrhId = oprBrhId;
	}

	public String getOprBrhName() {
		return oprBrhName;
	}

	public void setOprBrhName(String oprBrhName) {
		this.oprBrhName = oprBrhName;
	}

	public String getOprDegree() {
		return oprDegree;
	}

	public void setOprDegree(String oprDegree) {
		this.oprDegree = oprDegree;
	}

	public String getOprBrhLvl() {
		return oprBrhLvl;
	}

	public void setOprBrhLvl(String oprBrhLvl) {
		this.oprBrhLvl = oprBrhLvl;
	}

	public Map<String, String> getBrhBelowMap() {
		return brhBelowMap;
	}

	public void setBrhBelowMap(Map<String, String> brhBelowMap) {
		this.brhBelowMap = brhBelowMap;
	}
	
	public String getBrhBelowId() {
		return brhBelowId;
	}

	public void setBrhBelowId(String brhBelowId) {
		this.brhBelowId = brhBelowId;
	}

	/**
	 * @return the resvInfo
	 */
	public String getResvInfo() {
		return resvInfo;
	}

	/**
	 * @param resvInfo the resvInfo to set
	 */
	public void setResvInfo(String resvInfo) {
		this.resvInfo = resvInfo;
	}

	/**
	 * @return the lastLoginTime
	 */
	public String getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * @param lastLoginTime the lastLoginTime to set
	 */
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * @return the lastLoginIp
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	/**
	 * @param lastLoginIp the lastLoginIp to set
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	/**
	 * @return the lastLoginStatus
	 */
	public String getLastLoginStatus() {
		return lastLoginStatus;
	}

	/**
	 * @param lastLoginStatus the lastLoginStatus to set
	 */
	public void setLastLoginStatus(String lastLoginStatus) {
		this.lastLoginStatus = lastLoginStatus;
	}
}
