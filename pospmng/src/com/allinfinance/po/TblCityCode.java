package com.allinfinance.po;

import java.io.Serializable;



public class TblCityCode implements Serializable {
	private static final long serialVersionUID = 1L;


	public static String REF = "CstCityCode";
	public static String PROP_INIT_OPR_ID = "InitOprId";
	public static String PROP_MCHT_ADDR = "MchtAddr";
	public static String PROP_CITY_NAME = "CityName";
	public static String PROP_INIT_TIME = "InitTime";
	public static String PROP_INT_CITY_CODE = "intCityCode";
	public static String PROP_CUP_CITY_CODE = "cupCityCode";
	public static String PROP_MODI_OPR_ID = "ModiOprId";
	public static String PROP_CITY_FLAG = "CityFlag";
	public static String PROP_MODI_TIME = "ModiTime";


	// constructors
	public TblCityCode () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public TblCityCode (java.lang.String intCityCode) {
		this.setIntCityCode(intCityCode);
		initialize();
	}
	
	/**
	 * Constructor for primary key
	 */
	public TblCityCode (String intCityCode,String cityName) {
		this.setIntCityCode(intCityCode);
		this.setCityName(cityName);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public TblCityCode (
		java.lang.String intCityCode,
		java.lang.String cupCityCode,
		java.lang.String mchtCityCode,
		java.lang.String cityFlag,
		java.lang.String mchtAddr,
		java.lang.String cityName,
		java.lang.String initOprId,
		java.lang.String modiOprId,
		java.lang.String initTime,
		java.lang.String modiTime) {

		this.setIntCityCode(intCityCode);
		this.setCupCityCode(cupCityCode);
		this.setMchtCityCode(mchtCityCode);
		this.setCityFlag(cityFlag);
		this.setMchtAddr(mchtAddr);
		this.setCityName(cityName);
		this.setInitOprId(initOprId);
		this.setModiOprId(modiOprId);
		this.setInitTime(initTime);
		this.setModiTime(modiTime);
		initialize();
	}

	protected void initialize () {}



	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String cupCityCode;

	// fields
	private java.lang.String intCityCode;
	private java.lang.String mchtCityCode;
	private java.lang.String cityFlag;
	private java.lang.String mchtAddr;
	private java.lang.String cityName;
	private java.lang.String initOprId;
	private java.lang.String modiOprId;
	private java.lang.String initTime;
	private java.lang.String modiTime;



	/**
	 * Return the unique identifier of this class
     * @hibernate.id
     *  generator-class="assigned"
     *  column="INT_CITY_CODE"
     */
	public java.lang.String getIntCityCode () {
		return intCityCode;
	}

	/**
	 * Set the unique identifier of this class
	 * @param intCityCode the new ID
	 */
	public void setIntCityCode (java.lang.String intCityCode) {
		this.intCityCode = intCityCode;
		this.hashCode = Integer.MIN_VALUE;
	}




	public java.lang.String getMchtCityCode() {
		return mchtCityCode;
	}

	public void setMchtCityCode(java.lang.String mchtCityCode) {
		this.mchtCityCode = mchtCityCode;
	}

	/**
	 * Return the value associated with the column: CUP_CITY_CODE
	 */
	public java.lang.String getCupCityCode () {
		return cupCityCode;
	}

	/**
	 * Set the value related to the column: CUP_CITY_CODE
	 * @param cupCityCode the CUP_CITY_CODE value
	 */
	public void setCupCityCode (java.lang.String cupCityCode) {
		this.cupCityCode = cupCityCode;
	}



	/**
	 * Return the value associated with the column: CITY_FLAG
	 */
	public java.lang.String getCityFlag () {
		return cityFlag;
	}

	/**
	 * Set the value related to the column: CITY_FLAG
	 * @param cityFlag the CITY_FLAG value
	 */
	public void setCityFlag (java.lang.String cityFlag) {
		this.cityFlag = cityFlag;
	}



	/**
	 * Return the value associated with the column: MCHT_ADDR
	 */
	public java.lang.String getMchtAddr () {
		return mchtAddr;
	}

	/**
	 * Set the value related to the column: MCHT_ADDR
	 * @param mchtAddr the MCHT_ADDR value
	 */
	public void setMchtAddr (java.lang.String mchtAddr) {
		this.mchtAddr = mchtAddr;
	}



	/**
	 * Return the value associated with the column: CITY_NAME
	 */
	public java.lang.String getCityName () {
		return cityName;
	}

	/**
	 * Set the value related to the column: CITY_NAME
	 * @param cityName the CITY_NAME value
	 */
	public void setCityName (java.lang.String cityName) {
		this.cityName = cityName;
	}



	/**
	 * Return the value associated with the column: INIT_OPR_ID
	 */
	public java.lang.String getInitOprId () {
		return initOprId;
	}

	/**
	 * Set the value related to the column: INIT_OPR_ID
	 * @param initOprId the INIT_OPR_ID value
	 */
	public void setInitOprId (java.lang.String initOprId) {
		this.initOprId = initOprId;
	}



	/**
	 * Return the value associated with the column: MODI_OPR_ID
	 */
	public java.lang.String getModiOprId () {
		return modiOprId;
	}

	/**
	 * Set the value related to the column: MODI_OPR_ID
	 * @param modiOprId the MODI_OPR_ID value
	 */
	public void setModiOprId (java.lang.String modiOprId) {
		this.modiOprId = modiOprId;
	}



	/**
	 * Return the value associated with the column: INIT_TIME
	 */
	public java.lang.String getInitTime () {
		return initTime;
	}

	/**
	 * Set the value related to the column: INIT_TIME
	 * @param initTime the INIT_TIME value
	 */
	public void setInitTime (java.lang.String initTime) {
		this.initTime = initTime;
	}



	/**
	 * Return the value associated with the column: MODI_TIME
	 */
	public java.lang.String getModiTime () {
		return modiTime;
	}

	/**
	 * Set the value related to the column: MODI_TIME
	 * @param modiTime the MODI_TIME value
	 */
	public void setModiTime (java.lang.String modiTime) {
		this.modiTime = modiTime;
	}




	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof TblCityCode)) return false;
		else {
			TblCityCode cstCityCode = (TblCityCode) obj;
			if (null == this.getIntCityCode() || null == cstCityCode.getIntCityCode()) return false;
			else return (this.getIntCityCode().equals(cstCityCode.getIntCityCode()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getIntCityCode()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getIntCityCode().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}


	public String toString () {
		return super.toString();
	}


}