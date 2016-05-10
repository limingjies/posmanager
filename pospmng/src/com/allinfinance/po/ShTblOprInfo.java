package com.allinfinance.po;

import java.io.Serializable;



@SuppressWarnings("serial")
public class ShTblOprInfo implements Serializable {





	// primary key
	private ShTblOprInfoPk id;

	// fields
	private java.lang.String oprName;
//	private java.lang.String brhId;
	private java.lang.String roleId;
	private java.lang.String oprStatus;
	private java.lang.String oprPwd;
	private java.lang.String oprSex;
	private java.lang.String cerType;
	private java.lang.String cerNo;
	private java.lang.String contactPhone;
	private java.lang.String email;
	private java.lang.String pwdWrTm;
	private java.lang.String pwdWrTmTotal;
	private java.lang.String pwdWrLastDt;
	private java.lang.String pwdOutDate;
	private java.lang.String resv1;
	private java.lang.String resv2;
	private java.lang.String lstUpdOpr;
	private java.lang.String lstUpdTime;
	private java.lang.String createTime;
	private String mchtBrhFlag;
	private java.lang.String resvInfo;
	private java.lang.String lastLoginTime;
	private java.lang.String lastLoginIp;
	private java.lang.String lastLoginStatus;
	private java.lang.String currentLoginTime;
	private java.lang.String currentLoginIp;
	private java.lang.String currentLoginStatus;
	private java.lang.String pwdWrTmContinue;







	/**
	 * Return the value associated with the column: OPR_NAME
	 */
	public java.lang.String getOprName () {
		return oprName;
	}

	/**
	 * @return the id
	 */
	public ShTblOprInfoPk getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(ShTblOprInfoPk id) {
		this.id = id;
	}

	/**
	 * Set the value related to the column: OPR_NAME
	 * @param oprName the OPR_NAME value
	 */
	public void setOprName (java.lang.String oprName) {
		this.oprName = oprName;
	}







	/**
	 * Return the value associated with the column: ROLE_ID
	 */
	public java.lang.String getRoleId () {
		return roleId;
	}

	/**
	 * Set the value related to the column: ROLE_ID
	 * @param roleId the ROLE_ID value
	 */
	public void setRoleId (java.lang.String roleId) {
		this.roleId = roleId;
	}



	/**
	 * Return the value associated with the column: OPR_STATUS
	 */
	public java.lang.String getOprStatus () {
		return oprStatus;
	}

	/**
	 * Set the value related to the column: OPR_STATUS
	 * @param oprStatus the OPR_STATUS value
	 */
	public void setOprStatus (java.lang.String oprStatus) {
		this.oprStatus = oprStatus;
	}



	/**
	 * Return the value associated with the column: OPR_PWD
	 */
	public java.lang.String getOprPwd () {
		return oprPwd;
	}

	/**
	 * Set the value related to the column: OPR_PWD
	 * @param oprPwd the OPR_PWD value
	 */
	public void setOprPwd (java.lang.String oprPwd) {
		this.oprPwd = oprPwd;
	}



	/**
	 * Return the value associated with the column: OPR_SEX
	 */
	public java.lang.String getOprSex () {
		return oprSex;
	}

	/**
	 * Set the value related to the column: OPR_SEX
	 * @param oprSex the OPR_SEX value
	 */
	public void setOprSex (java.lang.String oprSex) {
		this.oprSex = oprSex;
	}



	/**
	 * Return the value associated with the column: CER_TYPE
	 */
	public java.lang.String getCerType () {
		return cerType;
	}

	/**
	 * Set the value related to the column: CER_TYPE
	 * @param cerType the CER_TYPE value
	 */
	public void setCerType (java.lang.String cerType) {
		this.cerType = cerType;
	}



	/**
	 * Return the value associated with the column: CER_NO
	 */
	public java.lang.String getCerNo () {
		return cerNo;
	}

	/**
	 * Set the value related to the column: CER_NO
	 * @param cerNo the CER_NO value
	 */
	public void setCerNo (java.lang.String cerNo) {
		this.cerNo = cerNo;
	}



	/**
	 * Return the value associated with the column: CONTACT_PHONE
	 */
	public java.lang.String getContactPhone () {
		return contactPhone;
	}

	/**
	 * Set the value related to the column: CONTACT_PHONE
	 * @param contactPhone the CONTACT_PHONE value
	 */
	public void setContactPhone (java.lang.String contactPhone) {
		this.contactPhone = contactPhone;
	}



	/**
	 * Return the value associated with the column: EMAIL
	 */
	public java.lang.String getEmail () {
		return email;
	}

	/**
	 * Set the value related to the column: EMAIL
	 * @param email the EMAIL value
	 */
	public void setEmail (java.lang.String email) {
		this.email = email;
	}



	/**
	 * Return the value associated with the column: PWD_WR_TM
	 */
	public java.lang.String getPwdWrTm () {
		return pwdWrTm;
	}

	/**
	 * Set the value related to the column: PWD_WR_TM
	 * @param pwdWrTm the PWD_WR_TM value
	 */
	public void setPwdWrTm (java.lang.String pwdWrTm) {
		this.pwdWrTm = pwdWrTm;
	}



	/**
	 * Return the value associated with the column: PWD_WR_TM_TOTAL
	 */
	public java.lang.String getPwdWrTmTotal () {
		return pwdWrTmTotal;
	}

	/**
	 * Set the value related to the column: PWD_WR_TM_TOTAL
	 * @param pwdWrTmTotal the PWD_WR_TM_TOTAL value
	 */
	public void setPwdWrTmTotal (java.lang.String pwdWrTmTotal) {
		this.pwdWrTmTotal = pwdWrTmTotal;
	}



	/**
	 * Return the value associated with the column: PWD_WR_LAST_DT
	 */
	public java.lang.String getPwdWrLastDt () {
		return pwdWrLastDt;
	}

	/**
	 * Set the value related to the column: PWD_WR_LAST_DT
	 * @param pwdWrLastDt the PWD_WR_LAST_DT value
	 */
	public void setPwdWrLastDt (java.lang.String pwdWrLastDt) {
		this.pwdWrLastDt = pwdWrLastDt;
	}



	/**
	 * Return the value associated with the column: PWD_OUT_DATE
	 */
	public java.lang.String getPwdOutDate () {
		return pwdOutDate;
	}

	/**
	 * Set the value related to the column: PWD_OUT_DATE
	 * @param pwdOutDate the PWD_OUT_DATE value
	 */
	public void setPwdOutDate (java.lang.String pwdOutDate) {
		this.pwdOutDate = pwdOutDate;
	}



	/**
	 * Return the value associated with the column: RESV1
	 */
	public java.lang.String getResv1 () {
		return resv1;
	}

	/**
	 * Set the value related to the column: RESV1
	 * @param resv1 the RESV1 value
	 */
	public void setResv1 (java.lang.String resv1) {
		this.resv1 = resv1;
	}



	/**
	 * Return the value associated with the column: RESV2
	 */
	public java.lang.String getResv2 () {
		return resv2;
	}

	/**
	 * Set the value related to the column: RESV2
	 * @param resv2 the RESV2 value
	 */
	public void setResv2 (java.lang.String resv2) {
		this.resv2 = resv2;
	}



	/**
	 * Return the value associated with the column: LST_UPD_OPR
	 */
	public java.lang.String getLstUpdOpr () {
		return lstUpdOpr;
	}

	/**
	 * Set the value related to the column: LST_UPD_OPR
	 * @param lstUpdOpr the LST_UPD_OPR value
	 */
	public void setLstUpdOpr (java.lang.String lstUpdOpr) {
		this.lstUpdOpr = lstUpdOpr;
	}



	/**
	 * Return the value associated with the column: LST_UPD_TIME
	 */
	public java.lang.String getLstUpdTime () {
		return lstUpdTime;
	}

	/**
	 * Set the value related to the column: LST_UPD_TIME
	 * @param lstUpdTime the LST_UPD_TIME value
	 */
	public void setLstUpdTime (java.lang.String lstUpdTime) {
		this.lstUpdTime = lstUpdTime;
	}



	/**
	 * Return the value associated with the column: CREATE_TIME
	 */
	public java.lang.String getCreateTime () {
		return createTime;
	}

	/**
	 * Set the value related to the column: CREATE_TIME
	 * @param createTime the CREATE_TIME value
	 */
	public void setCreateTime (java.lang.String createTime) {
		this.createTime = createTime;
	}




	


	
	public String toString () {
		return super.toString();
	}

	/**
	 * 
	 */
	public ShTblOprInfo() {
	}

	/**
	 * @return the mchtBrhFlag
	 */
	public String getMchtBrhFlag() {
		return mchtBrhFlag;
	}

	/**
	 * @param mchtBrhFlag the mchtBrhFlag to set
	 */
	public void setMchtBrhFlag(String mchtBrhFlag) {
		this.mchtBrhFlag = mchtBrhFlag;
	}

	/**
	 * @return the resvInfo
	 */
	public java.lang.String getResvInfo() {
		return resvInfo;
	}

	/**
	 * @param resvInfo the resvInfo to set
	 */
	public void setResvInfo(java.lang.String resvInfo) {
		this.resvInfo = resvInfo;
	}

	/**
	 * @return the lastLoginTime
	 */
	public java.lang.String getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * @param lastLoginTime the lastLoginTime to set
	 */
	public void setLastLoginTime(java.lang.String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * @return the lastLoginIp
	 */
	public java.lang.String getLastLoginIp() {
		return lastLoginIp;
	}

	/**
	 * @param lastLoginIp the lastLoginIp to set
	 */
	public void setLastLoginIp(java.lang.String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	/**
	 * @return the lastLoginStatus
	 */
	public java.lang.String getLastLoginStatus() {
		return lastLoginStatus;
	}

	/**
	 * @param lastLoginStatus the lastLoginStatus to set
	 */
	public void setLastLoginStatus(java.lang.String lastLoginStatus) {
		this.lastLoginStatus = lastLoginStatus;
	}

	/**
	 * @return the currentLoginTime
	 */
	public java.lang.String getCurrentLoginTime() {
		return currentLoginTime;
	}

	/**
	 * @param currentLoginTime the currentLoginTime to set
	 */
	public void setCurrentLoginTime(java.lang.String currentLoginTime) {
		this.currentLoginTime = currentLoginTime;
	}

	/**
	 * @return the currentLoginIp
	 */
	public java.lang.String getCurrentLoginIp() {
		return currentLoginIp;
	}

	/**
	 * @param currentLoginIp the currentLoginIp to set
	 */
	public void setCurrentLoginIp(java.lang.String currentLoginIp) {
		this.currentLoginIp = currentLoginIp;
	}

	/**
	 * @return the currentLoginStatus
	 */
	public java.lang.String getCurrentLoginStatus() {
		return currentLoginStatus;
	}

	/**
	 * @param currentLoginStatus the currentLoginStatus to set
	 */
	public void setCurrentLoginStatus(java.lang.String currentLoginStatus) {
		this.currentLoginStatus = currentLoginStatus;
	}

	/**
	 * @return the pwdWrTmContinue
	 */
	public java.lang.String getPwdWrTmContinue() {
		return pwdWrTmContinue;
	}

	/**
	 * @param pwdWrTmContinue the pwdWrTmContinue to set
	 */
	public void setPwdWrTmContinue(java.lang.String pwdWrTmContinue) {
		this.pwdWrTmContinue = pwdWrTmContinue;
	}
	
	
}