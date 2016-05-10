package com.allinfinance.po.settle;

import java.io.Serializable;

public class TblCnapsInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String cnapsId;
	private String cnapsFlag;	
	private String cnapsName;	
	private String parentCnapsId;	
	private String addr;	
	private String misc;	
	private String misc1;
	/**
	 * @return the cnapsId
	 */
	public String getCnapsId() {
		return cnapsId;
	}
	/**
	 * @param cnapsId the cnapsId to set
	 */
	public void setCnapsId(String cnapsId) {
		this.cnapsId = cnapsId;
	}
	/**
	 * @return the cnapsFlag
	 */
	public String getCnapsFlag() {
		return cnapsFlag;
	}
	/**
	 * @param cnapsFlag the cnapsFlag to set
	 */
	public void setCnapsFlag(String cnapsFlag) {
		this.cnapsFlag = cnapsFlag;
	}
	/**
	 * @return the cnapsName
	 */
	public String getCnapsName() {
		return cnapsName;
	}
	/**
	 * @param cnapsName the cnapsName to set
	 */
	public void setCnapsName(String cnapsName) {
		this.cnapsName = cnapsName;
	}
	/**
	 * @return the parentCnapsId
	 */
	public String getParentCnapsId() {
		return parentCnapsId;
	}
	/**
	 * @param parentCnapsId the parentCnapsId to set
	 */
	public void setParentCnapsId(String parentCnapsId) {
		this.parentCnapsId = parentCnapsId;
	}
	/**
	 * @return the addr
	 */
	public String getAddr() {
		return addr;
	}
	/**
	 * @param addr the addr to set
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}
	/**
	 * @return the misc
	 */
	public String getMisc() {
		return misc;
	}
	/**
	 * @param misc the misc to set
	 */
	public void setMisc(String misc) {
		this.misc = misc;
	}
	/**
	 * @return the misc1
	 */
	public String getMisc1() {
		return misc1;
	}
	/**
	 * @param misc1 the misc1 to set
	 */
	public void setMisc1(String misc1) {
		this.misc1 = misc1;
	}
}