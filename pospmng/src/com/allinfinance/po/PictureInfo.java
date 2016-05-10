package com.allinfinance.po;

import java.io.Serializable;

/**
 * 
 * Title: 图片信息
 * 
 * Description: 图片信息
 * 
 * Copyright: Copyright (c) 2015年12月16日
 * 
 * Company: Shenzhen Qianbao Software Systems Co., Ltd.
 * 
 * @author luhq
 * 
 * @version 1.0
 */
@SuppressWarnings("serial")
public class PictureInfo implements Serializable {

	public PictureInfo() {
		super();
	}

	/**
	 * 图片ID
	 */
	private String id;

	/**
	 * 图片路径
	 */
	private String picUrl;

	/**
	 * 图片业务名
	 */
	private String picBizName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getPicBizName() {
		return picBizName;
	}

	public void setPicBizName(String picBizName) {
		this.picBizName = picBizName;
	}

}