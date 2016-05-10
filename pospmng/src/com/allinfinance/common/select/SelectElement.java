package com.allinfinance.common.select;
/**
 * 
 * Title:SelectOptions Bean
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-3-12
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author PanShuang
 * 
 * @version 1.0
 */
public class SelectElement {
	
	private String txnId;
	
	private SelectOptionExtractMethod extractMethod;

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public SelectOptionExtractMethod getExtractMethod() {
		return extractMethod;
	}

	public void setExtractMethod(SelectOptionExtractMethod extractMethod) {
		this.extractMethod = extractMethod;
	}
	
	
}
