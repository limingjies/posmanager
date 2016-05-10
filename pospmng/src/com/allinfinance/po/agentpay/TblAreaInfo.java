package com.allinfinance.po.agentpay;

import java.io.Serializable;

public class TblAreaInfo implements Serializable {

	/**
	 * Tbl_Rcv_Pack表映射对象
	 */
	private static final long serialVersionUID = 1L;
	private TblAreaInfoPK id;
	private String province;
	private String areaName;
	
	public TblAreaInfo() {
		
	}
	
	public TblAreaInfo(TblAreaInfoPK id, String province, String areaName) {
		this.id = id;
		this.province = province;
		this.areaName = areaName;
	}

	public TblAreaInfoPK getId() {
		return id;
	}

	public void setId(TblAreaInfoPK id) {
		this.id = id;
	}

	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}
