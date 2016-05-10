package com.allinfinance.po;

import java.io.Serializable;

public class TblModelInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String MODEL_ID = "modelId";
	public static String MODEL_NAME ="modelName";
	public static String VERSION = "version";
	public static String CREATE_TIME ="createTime";
	public static String UPDATE_TIME = "updateTime";
	public static String OPERATE_ID = "operateId";
	public static String STATUS = "status";
	public static String PRINT_NUMBER = "printNumber";
	
	/**
	 * 模板ID
	 */
	private Integer modelId ;
	
	/**
	 * 模板名称
	 */
	private String modelName;
	
	/**
	 * 版本号
	 */
	private Integer version;
	
	/**
	 * 创建时间
	 */
	private String createTime;
	
	/**
	 * 修改时间
	 */
	private String updateTime;
	
	/**
	 * 操作人
	 */
	private String operateId;
	
	/**
	 * 状态：0停用  1启用
	 */
	private String status;
	
	/**
	 * 打印联数
	 */
	private Integer printNumber;
	
	public Integer getModelId() {
		return modelId;
	}
	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getOperateId() {
		return operateId;
	}
	public void setOperateId(String operateId) {
		this.operateId = operateId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getPrintNumber() {
		return printNumber;
	}
	public void setPrintNumber(Integer printNumber) {
		this.printNumber = printNumber;
	}
	
}