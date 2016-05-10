package com.allinfinance.po;

import java.io.Serializable;

public class TblModelParameter implements Serializable {

	private static final long serialVersionUID = -8058312592077127758L;
	
	public static String STATUS = "status";
	public static String FIELD_NAME ="fieldName";
	public static String FONT_SIZE = "fontSize";
	public static String FIELD_ORDER = "fieldOrder";
	public static String CONT_FORMAT_TYPE = "contFormatType";
	public static String CONT_FORMAT ="contFormat";
	public static String CREATE_TIME ="createTime";
	public static String UPDATE_TIME = "updateTime";
	public static String OPERATE_ID = "operateId";
	
	/**
	 * 主键类 -- 联合主键(modelId & parameterId)
	 */
    private PrimaryKey primaryKey;  
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 字段名称
	 */
	private String fieldName;
	
	/**
	 * 字体大小
	 */
	private Integer fontSize;
	
	/**
	 * 字段顺序
	 */
	private Integer fieldOrder;
	
	/**
	 * 内容格式类型
	 */
	private String contFormatType;
	
	/**
	 * 内容格式
	 */
	private String contFormat;
	
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

	public PrimaryKey getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(PrimaryKey primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Integer getFontSize() {
		return fontSize;
	}

	public void setFontSize(Integer fontSize) {
		this.fontSize = fontSize;
	}

	public Integer getFieldOrder() {
		return fieldOrder;
	}

	public void setFieldOrder(Integer fieldOrder) {
		this.fieldOrder = fieldOrder;
	}

	public String getContFormatType() {
		return contFormatType;
	}

	public void setContFormatType(String contFormatType) {
		this.contFormatType = contFormatType;
	}

	public String getContFormat() {
		return contFormat;
	}

	public void setContFormat(String contFormat) {
		this.contFormat = contFormat;
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


}