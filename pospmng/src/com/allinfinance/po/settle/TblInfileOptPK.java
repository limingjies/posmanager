package com.allinfinance.po.settle;

import java.io.Serializable;

public class TblInfileOptPK implements Serializable {

	private static final long serialVersionUID = 1L;

    private String settleDate;
    private String brhId;

    public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getBrhId() {
		return brhId;
	}

	public void setBrhId(String brhId) {
		this.brhId = brhId;
	}
}