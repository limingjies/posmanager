package com.allinfinance.po.route;

/**
 * TblRouteRuleMapHistId entity. @author MyEclipse Persistence Tools
 */

public class TblRouteRuleMapHistPk implements java.io.Serializable {

	// Fields

	private Integer ruleId;
	private String stopTime;

	// Constructors

	/** default constructor */
	public TblRouteRuleMapHistPk() {
	}

	/** full constructor */
	public TblRouteRuleMapHistPk(Integer ruleId, String stopTime) {
		this.ruleId = ruleId;
		this.stopTime = stopTime;
	}

	// Property accessors

	public Integer getRuleId() {
		return this.ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public String getStopTime() {
		return this.stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TblRouteRuleMapHistPk))
			return false;
		TblRouteRuleMapHistPk castOther = (TblRouteRuleMapHistPk) other;

		return ((this.getRuleId() == castOther.getRuleId()) || (this
				.getRuleId() != null && castOther.getRuleId() != null && this
				.getRuleId().equals(castOther.getRuleId())))
				&& ((this.getStopTime() == castOther.getStopTime()) || (this
						.getStopTime() != null
						&& castOther.getStopTime() != null && this
						.getStopTime().equals(castOther.getStopTime())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getRuleId() == null ? 0 : this.getRuleId().hashCode());
		result = 37 * result
				+ (getStopTime() == null ? 0 : this.getStopTime().hashCode());
		return result;
	}

}