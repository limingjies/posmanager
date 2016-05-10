package com.allinfinance.po;

import java.io.Serializable;

public class TblRiskInf implements Serializable {
	private static final long serialVersionUID = 1L;

	protected void initialize() {
	}

	/**
	 * 
	 */
	public TblRiskInf() {
		super();
	}


	// primary key
	private java.lang.String id;

	// fields
	private java.lang.String saBeUse;
	private java.lang.String saModelGroup;
	private java.lang.String saModelDesc;
	private java.lang.String misc;

	/**
	 * @return the id
	 */
	public java.lang.String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * @return the saBeUse
	 */
	public java.lang.String getSaBeUse() {
		return saBeUse;
	}

	/**
	 * @param saBeUse the saBeUse to set
	 */
	public void setSaBeUse(java.lang.String saBeUse) {
		this.saBeUse = saBeUse;
	}

	/**
	 * @return the saModelGroup
	 */
	public java.lang.String getSaModelGroup() {
		return saModelGroup;
	}

	/**
	 * @param saModelGroup the saModelGroup to set
	 */
	public void setSaModelGroup(java.lang.String saModelGroup) {
		this.saModelGroup = saModelGroup;
	}

	/**
	 * @return the saModelDesc
	 */
	public java.lang.String getSaModelDesc() {
		return saModelDesc;
	}

	/**
	 * @param saModelDesc the saModelDesc to set
	 */
	public void setSaModelDesc(java.lang.String saModelDesc) {
		this.saModelDesc = saModelDesc;
	}

	/**
	 * @return the misc
	 */
	public java.lang.String getMisc() {
		return misc;
	}

	/**
	 * @param misc the misc to set
	 */
	public void setMisc(java.lang.String misc) {
		this.misc = misc;
	}

	
}