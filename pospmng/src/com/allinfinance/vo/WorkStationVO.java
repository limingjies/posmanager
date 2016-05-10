package com.allinfinance.vo;

import java.io.Serializable;

/**
 * Title: 用户工作区任务信息
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-11-20
 * 
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class WorkStationVO implements Serializable{
	// 任务名称
	private String taskName;
	// 任务数量
	private String taskCount;
	// 任务机构
	private String taskBrh;
	// 任务界面
	private String taskPageUrl;
	// 任务界面编号
	private String taskPageId;
	// 任务界面标题
	private String taskPageTitle;
	
	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * @return the taskCount
	 */
	public String getTaskCount() {
		return taskCount;
	}

	/**
	 * @param taskCount the taskCount to set
	 */
	public void setTaskCount(String taskCount) {
		this.taskCount = taskCount;
	}

	/**
	 * @return the taskBrh
	 */
	public String getTaskBrh() {
		return taskBrh;
	}

	/**
	 * @param taskBrh the taskBrh to set
	 */
	public void setTaskBrh(String taskBrh) {
		this.taskBrh = taskBrh;
	}

	/**
	 * @return the taskPageUrl
	 */
	public String getTaskPageUrl() {
		return taskPageUrl;
	}

	/**
	 * @param taskPageUrl the taskPageUrl to set
	 */
	public void setTaskPageUrl(String taskPageUrl) {
		this.taskPageUrl = taskPageUrl;
	}

	/**
	 * @return the taskPageId
	 */
	public String getTaskPageId() {
		return taskPageId;
	}

	/**
	 * @param taskPageId the taskPageId to set
	 */
	public void setTaskPageId(String taskPageId) {
		this.taskPageId = taskPageId;
	}

	/**
	 * @return the taskPageTitle
	 */
	public String getTaskPageTitle() {
		return taskPageTitle;
	}

	/**
	 * @param taskPageTitle the taskPageTitle to set
	 */
	public void setTaskPageTitle(String taskPageTitle) {
		this.taskPageTitle = taskPageTitle;
	}
	
	
}
