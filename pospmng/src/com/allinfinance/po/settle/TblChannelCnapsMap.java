package com.allinfinance.po.settle;

import java.io.Serializable;

public class TblChannelCnapsMap implements Serializable {
	private static final long serialVersionUID = 1L;

	private String cnapsId;
	private String channelId;
	private String status;
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
	 * @return the channelId
	 */
	public String getChannelId() {
		return channelId;
	}
	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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