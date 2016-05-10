package com.allinfinance.po.mchnt.base;

import java.io.Serializable;

import com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp;

@SuppressWarnings("serial")
public abstract class BaseTblMchtBaseInfTmpTmp implements Serializable {

	public static String REF = "TblMchtBaseInfTmp";
	public static String PROP_PROTOCAL_ID = "ProtocalId";
	public static String PROP_MCHT_GROUP_ID = "MchtGroupId";
	public static String PROP_PART_NUM = "PartNum";
	public static String PROP_PRINT_INST_ID = "PrintInstId";
	public static String PROP_MANAGER = "Manager";
	public static String PROP_AMX_ACT_FLG = "AmxActFlg";
	public static String PROP_MANAGER_TEL = "ManagerTel";
	public static String PROP_ENG_NAME = "EngName";
	public static String PROP_CONFIRM_NM = "ConfirmNm";
	public static String PROP_SPELL_NAME = "SpellName";
	public static String PROP_SET_CUR = "SetCur";
	public static String PROP_RISL_LVL = "RislLvl";
	public static String PROP_ELECTROFAX = "Electrofax";
	public static String PROP_AREA_NO = "AreaNo";
	public static String PROP_CLOSE_TLR = "CloseTlr";
	public static String PROP_AGR_BR = "AgrBr";
	public static String PROP_ORGN_NO = "OrgnNo";
	public static String PROP_BANK_NO = "BankNo";
	public static String PROP_NET_TEL = "NetTel";
	public static String PROP_COMM_EMAIL = "CommEmail";
	public static String PROP_ADDR = "Addr";
	public static String PROP_NET_NM = "NetNm";
	public static String PROP_PROL_DATE = "ProlDate";
	public static String PROP_MNG_MCHT_ID = "MngMchtId";
	public static String PROP_PRE_AUD_NM = "PreAudNm";
	public static String PROP_OPEN_TIME = "OpenTime";
	public static String PROP_MCHT_LVL = "MchtLvl";
	public static String PROP_COMM_TEL = "CommTel";
	public static String PROP_SA_LIMIT_AMT = "SaLimitAmt";
	public static String PROP_MCHT_GRP = "MchtGrp";
	public static String PROP_HOME_PAGE = "HomePage";
	public static String PROP_VIS_ACT_FLG = "VisActFlg";
	public static String PROP_REG_ADDR = "RegAddr";
	public static String MAIN_TLR = "MainTlr";
	public static String CHECK_TLR = "CheckTlr";
	public static String PROP_CONN_TYPE = "ConnType";
	public static String PROP_DNR_ACT_FLG = "DnrActFlg";
	public static String PROP_REC_UPD_TS = "RecUpdTs";

	// public static String PROP_REJECT = "Reject";
	// public static String PROP_MCHNT_ATTR = "MchntAttr";
	// public static String PROP_LINK_PER = "LinkPer";

	public static String getPROP_REC_UPD_TS() {
		return PROP_REC_UPD_TS;
	}

	public static void setPROP_REC_UPD_TS(String prop_rec_upd_ts) {
		PROP_REC_UPD_TS = prop_rec_upd_ts;
	}

	public static String getPROP_CDC_MCHT_FLG() {
		return PROP_CDC_MCHT_FLG;
	}

	public static void setPROP_CDC_MCHT_FLG(String prop_cdc_mcht_flg) {
		PROP_CDC_MCHT_FLG = prop_cdc_mcht_flg;
	}

	public static String PROP_FAX_NO = "FaxNo";
	public static String PROP_MCHT_MNG_MODE = "MchtMngMode";
	public static String PROP_DNR_MCHT_ID = "DnrMchtId";
	public static String PROP_SIGN_INST_ID = "SignInstId";
	public static String PROP_CLOSE_DATE = "CloseDate";
	public static String PROP_BUS_TYPE = "BusType";
	public static String PROP_TCC = "Tcc";
	public static String PROP_MCHT_ATTR = "MchtAttr";
	public static String PROP_SUBBRH_NM = "SubbrhNm";
	public static String PROP_RESERVED = "Reserved";
	public static String PROP_CUP_MCHT_FLG = "CupMchtFlg";
	public static String PROP_CDC_MCHT_FLG = "CdcMchtFlg";
	public static String PROP_BANK_LICENCE_NO = "BankLicenceNo";
	public static String PROP_PSAM_NUM = "PsamNum";
	public static String PROP_SLEEP_DAYS = "SleepDays";
	public static String PROP_FAX = "Fax";
	public static String PROP_DISC_CONS_REBATE = "DiscConsRebate";
	public static String PROP_CD_MAC_NUM = "CdMacNum";
	public static String PROP_PROL_TLR = "ProlTlr";
	public static String PROP_MCHT_NO = "MchtNo";
	public static String PROP_OPER_NO = "OperNo";
	public static String PROP_JCB_MCHT_ID = "JcbMchtId";
	public static String PROP_DEB_MCHT_FLG = "DebMchtFlg";
	public static String PROP_MCHT_NM = "MchtNm";
	public static String PROP_MCHT_FUNCTION = "MchtFunction";
	public static String PROP_MST_ACT_FLG = "MstActFlg";
	public static String PROP_OPEN_DAYS = "OpenDays";
	public static String PROP_CRE_MCHT_FLG = "CreMchtFlg";
	public static String PROP_CONTACT = "Contact";
	public static String PROP_MCHT_STATUS = "MchtStatus";
	public static String PROP_BUS_AMT = "BusAmt";
	public static String PROP_LICENCE_END_DATE = "LicenceEndDate";
	public static String PROP_PASS_FLAG = "PassFlag";
	public static String PROP_MCC = "Mcc";
	public static String PROP_MST_MCHT_ID = "MstMchtId";
	public static String PROP_OPER_NM = "OperNm";
	public static String PROP_SUBBRH_NO = "SubbrhNo";
	public static String PROP_LICENCE_NO = "LicenceNo";
	public static String PROP_MCHT_ENG_CITY_NAME = "MchtEngCityName";
	public static String PROP_IDENTITY_NO = "IdentityNo";
	public static String PROP_VIS_MCHT_ID = "VisMchtId";
	public static String PROP_CLOSE_TIME = "CloseTime";
	public static String PROP_MCHT_EN_ABBR = "MchtEnAbbr";
	public static String PROP_PROC_FLAG = "ProcFlag";
	public static String PROP_ACQ_INST_ID = "AcqInstId";
	public static String PROP_APPLY_DATE = "ApplyDate";
	public static String PROP_MCHT_CRE_LVL = "MchtCreLvl";
	public static String PROP_ACQ_BK_NAME = "AcqBkName";
	public static String PROP_AMX_MCHT_ID = "AmxMchtId";
	public static String PROP_MCHT_CN_ABBR = "MchtCnAbbr";
	public static String PROP_COMM_MOBIL = "CommMobil";
	public static String PROP_POST_CODE = "PostCode";
	public static String PROP_DISC_CONS_FLG = "DiscConsFlg";
	public static String PROP_MCHT_ENG_NM = "MchtEngNm";
	public static String PROP_MCHT_ENG_ADDR = "MchtEngAddr";
	public static String PROP_MCHT_GROUP_FLAG = "MchtGroupFlag";
	public static String PROP_ARTIF_CERTIF_TP = "ArtifCertifTp";
	public static String PROP_ETPS_ATTR = "EtpsAttr";
	public static String PROP_JCB_ACT_FLG = "JcbActFlg";
	public static String PROP_POS_NUM = "PosNum";
	public static String PROP_SA_ACTION = "SaAction";
	public static String PROP_ENABLE_DATE = "EnableDate";
	public static String PROP_REC_CRT_TS = "RecCrtTs";
	public static String PROP_MANU_AUTH_FLAG = "ManuAuthFlag";
	public static String UPD_OPR_ID = "UpdOprId";
	public static String CRT_OPR_ID = "CrtOprId";
	public static String PROP_INTERNAL_NO = "InternalNo";

	public static String PROP_COMP_NM = "CompNm";
	public static String PROP_COMP_ADDR = "Compaddr";
	public static String PROP_FINA_CONTACT = "Finacontact";
	public static String PROP_FINA_COMM_TEL = "FinacommTel";
	public static String PROP_FINA_COMM_EMAIL = "FinacommEmail";
	public static String PROP_BUS_INFO = "BusInfo";
	public static String PROP_BUS_AREA = "BusArea";
	public static String PROP_MON_AVE_TRANS = "MonaveTrans";
	public static String PROP_SIG_AVE_TRANS = "SigaveTrans";
	public static String PROP_CONT_START_DATE = "ContstartDate";
	public static String PROP_CONT_END_DATE = "ContendDate";

	// constructors
	public BaseTblMchtBaseInfTmpTmp() {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public BaseTblMchtBaseInfTmpTmp(java.lang.String mchtNo) {
		this.setMchtNo(mchtNo);
		initialize();
	}

	/**
	 * Constructor for required fields
	 */
	public BaseTblMchtBaseInfTmpTmp(java.lang.String mchtNo,
			java.lang.String mchtNm, java.lang.String addr,
			java.lang.String mcc, java.lang.String mchtGrp,
			java.lang.String mchtCnAbbr, java.lang.String licenceNo,
			java.lang.String licenceEndDate, java.lang.String bankLicenceNo,
			java.lang.String bankNo, java.lang.String faxNo,
			java.lang.String contact, java.lang.String commEmail,
			java.lang.String manager, java.lang.String identityNo,
			java.lang.String managerTel, java.lang.String openTime,
			java.lang.String closeTime, java.lang.String recUpdTs,
			java.lang.String recCrtTs, java.lang.String mchtGroupFlag,

			java.lang.String compNm, java.lang.String compaddr,
			java.lang.String finacontact, java.lang.String finacommTel,
			java.lang.String finacommEmail, java.lang.String busInfo,
			java.lang.String busArea, java.lang.String monaveTrans,
			java.lang.String sigaveTrans, java.lang.String contstartDate,
			java.lang.String contendDate) {

		this.setMchtNo(mchtNo);
		this.setMchtNm(mchtNm);
		this.setAddr(addr);
		this.setMcc(mcc);
		this.setMchtGrp(mchtGrp);
		this.setMchtCnAbbr(mchtCnAbbr);
		this.setLicenceNo(licenceNo);
		this.setLicenceEndDate(licenceEndDate);
		this.setBankLicenceNo(bankLicenceNo);
		this.setBankNo(bankNo);
		this.setFaxNo(faxNo);
		this.setContact(contact);
		this.setCommEmail(commEmail);
		this.setManager(manager);
		this.setIdentityNo(identityNo);
		this.setManagerTel(managerTel);
		this.setOpenTime(openTime);
		this.setCloseTime(closeTime);
		this.setRecUpdTs(recUpdTs);
		this.setRecCrtTs(recCrtTs);
		this.setMchtGroupFlag(mchtGroupFlag);

		this.setRecCrtTs(compNm);
		this.setRecCrtTs(compaddr);
		this.setRecCrtTs(finacontact);
		this.setRecCrtTs(finacommTel);
		this.setRecCrtTs(finacommEmail);
		this.setRecCrtTs(busInfo);
		this.setRecCrtTs(busArea);
		this.setRecCrtTs(monaveTrans);
		this.setRecCrtTs(sigaveTrans);
		this.setRecCrtTs(contstartDate);
		this.setRecCrtTs(contendDate);
		initialize();
	}

	protected void initialize() {
	}

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String mchtNo;

	// fields
	private java.lang.String mchtNm;
	private java.lang.String rislLvl;
	private java.lang.String mchtLvl;
	private java.lang.String mchtStatus;
	private java.lang.String manuAuthFlag;
	private java.lang.String partNum;
	private java.lang.String discConsFlg;
	private java.lang.String discConsRebate;
	private java.lang.String passFlag;
	private java.lang.String openDays;
	private java.lang.String sleepDays;
	private java.lang.String mchtCnAbbr;
	private java.lang.String spellName;
	private java.lang.String engName;
	private java.lang.String mchtEnAbbr;
	private java.lang.String areaNo;
	private java.lang.String settleAreaNo;
	private java.lang.String addr;
	private java.lang.String homePage;
	private java.lang.String mcc;
	private java.lang.String tcc;
	private java.lang.String etpsAttr;
	private java.lang.String mngMchtId;
	private java.lang.String mchtGrp;
	private java.lang.String mchtAttr;
	private java.lang.String mchtGroupFlag;
	private java.lang.String mchtGroupId;
	private java.lang.String mchtEngNm;
	private java.lang.String mchtEngAddr;
	private java.lang.String mchtEngCityName;
	private java.lang.String saLimitAmt;
	private java.lang.String saAction;
	private java.lang.String psamNum;
	private java.lang.String cdMacNum;
	private java.lang.String posNum;
	private java.lang.String connType;
	private java.lang.String mchtMngMode;
	private java.lang.String mchtFunction;
	private java.lang.String licenceNo;
	private java.lang.String licenceEndDate;
	private java.lang.String bankLicenceNo;
	private java.lang.String busType;
	private java.lang.String faxNo;
	private java.lang.String busAmt;
	private java.lang.String mchtCreLvl;
	private java.lang.String contact;
	private java.lang.String postCode;
	private java.lang.String commEmail;
	private java.lang.String commMobil;
	private java.lang.String commTel;
	private java.lang.String manager;
	private java.lang.String artifCertifTp;
	private java.lang.String identityNo;
	private java.lang.String managerTel;
	private java.lang.String fax;
	private java.lang.String electrofax;
	private java.lang.String regAddr;
	private java.lang.String applyDate;
	private java.lang.String enableDate;
	private java.lang.String preAudNm;
	private java.lang.String confirmNm;
	private java.lang.String protocalId;
	private java.lang.String signInstId;
	private java.lang.String netNm;
	private java.lang.String agrBr;
	private java.lang.String netTel;
	private java.lang.String prolDate;
	private java.lang.String prolTlr;
	private java.lang.String closeDate;
	private java.lang.String closeTlr;
	private java.lang.String mainTlr;
	private java.lang.String checkTlr;
	private java.lang.String operNo;
	private java.lang.String operNm;
	private java.lang.String procFlag;
	private java.lang.String setCur;
	private java.lang.String printInstId;
	private java.lang.String acqInstId;
	private java.lang.String acqBkName;
	private java.lang.String bankNo;
	private java.lang.String orgnNo;
	private java.lang.String subbrhNo;
	private java.lang.String subbrhNm;
	private java.lang.String openTime;
	private java.lang.String closeTime;
	private java.lang.String visActFlg;
	private java.lang.String visMchtId;
	private java.lang.String mstActFlg;
	private java.lang.String mstMchtId;
	private java.lang.String amxActFlg;
	private java.lang.String amxMchtId;
	private java.lang.String dnrActFlg;
	private java.lang.String dnrMchtId;
	private java.lang.String jcbActFlg;
	private java.lang.String jcbMchtId;
	private java.lang.String cupMchtFlg;
	private java.lang.String debMchtFlg;
	private java.lang.String creMchtFlg;
	private java.lang.String cdcMchtFlg;
	private String updOprId;
	private String crtOprId;

	public java.lang.String compNm;
	public java.lang.String compaddr;
	public java.lang.String finacontact;
	public java.lang.String finacommTel;
	public java.lang.String finacommEmail;
	public java.lang.String busInfo;
	public java.lang.String busArea;
	public java.lang.String monaveTrans;
	public java.lang.String sigaveTrans;
	public java.lang.String contstartDate;
	public java.lang.String contendDate;

	public java.lang.String addrProvince;// 装机地址省mcht_bak1
	public java.lang.String poundage;// 手续费方式mcht_bak2

	public java.lang.String openVirtualAcctFlag;// 虚拟账户开户标志mcht_bak3
	public java.lang.String cashFlag;//提现开通mcht_bak4
	// Constructors

	/**
	 * @return the cashFlag
	 */
	public java.lang.String getCashFlag() {
		return cashFlag;
	}

	/**
	 * @param cashFlag the cashFlag to set
	 */
	public void setCashFlag(java.lang.String cashFlag) {
		this.cashFlag = cashFlag;
	}

	public java.lang.String getOpenVirtualAcctFlag() {
		return openVirtualAcctFlag;
	}

	public void setOpenVirtualAcctFlag(java.lang.String openVirtualAcctFlag) {
		this.openVirtualAcctFlag = openVirtualAcctFlag;
	}

	/**
	 * @return the addrProvince
	 */
	public java.lang.String getAddrProvince() {
		return addrProvince;
	}

	/**
	 * @param addrProvince
	 *            the addrProvince to set
	 */
	public void setAddrProvince(java.lang.String addrProvince) {
		this.addrProvince = addrProvince;
	}

	/**
	 * @return the poundage
	 */
	public java.lang.String getPoundage() {
		return poundage;
	}

	/**
	 * @param poundage
	 *            the poundage to set
	 */
	public void setPoundage(java.lang.String poundage) {
		this.poundage = poundage;
	}

	public java.lang.String getCdcMchtFlg() {
		return cdcMchtFlg;
	}

	public void setCdcMchtFlg(java.lang.String cdcMchtFlg) {
		this.cdcMchtFlg = cdcMchtFlg;
	}

	private java.lang.String reserved;
	private java.lang.String recUpdTs;
	private java.lang.String recCrtTs;

	/**
	 * Return the unique identifier of this class
	 * 
	 * @hibernate.id generator-class="assigned" column="MCHT_NO"
	 */
	public java.lang.String getMchtNo() {
		return mchtNo;
	}

	/**
	 * Set the unique identifier of this class
	 * 
	 * @param mchtNo
	 *            the new ID
	 */
	public void setMchtNo(java.lang.String mchtNo) {
		this.mchtNo = mchtNo;
		this.hashCode = Integer.MIN_VALUE;
	}

	public String getUpdOprId() {
		return updOprId;
	}

	public void setUpdOprId(String updOprId) {
		this.updOprId = updOprId;
	}

	public String getCrtOprId() {
		return crtOprId;
	}

	public void setCrtOprId(String crtOprId) {
		this.crtOprId = crtOprId;
	}

	/**
	 * Return the value associated with the column: MCHT_NM
	 */
	public java.lang.String getMchtNm() {
		return mchtNm;
	}

	/**
	 * Set the value related to the column: MCHT_NM
	 * 
	 * @param mchtNm
	 *            the MCHT_NM value
	 */
	public void setMchtNm(java.lang.String mchtNm) {
		this.mchtNm = mchtNm;
	}

	/**
	 * Return the value associated with the column: RISL_LVL
	 */
	public java.lang.String getRislLvl() {
		return rislLvl;
	}

	/**
	 * Set the value related to the column: RISL_LVL
	 * 
	 * @param rislLvl
	 *            the RISL_LVL value
	 */
	public void setRislLvl(java.lang.String rislLvl) {
		this.rislLvl = rislLvl;
	}

	/**
	 * Return the value associated with the column: MCHT_LVL
	 */
	public java.lang.String getMchtLvl() {
		return mchtLvl;
	}

	/**
	 * Set the value related to the column: MCHT_LVL
	 * 
	 * @param mchtLvl
	 *            the MCHT_LVL value
	 */
	public void setMchtLvl(java.lang.String mchtLvl) {
		this.mchtLvl = mchtLvl;
	}

	/**
	 * Return the value associated with the column: MCHT_STATUS
	 */
	public java.lang.String getMchtStatus() {
		return mchtStatus;
	}

	/**
	 * Set the value related to the column: MCHT_STATUS
	 * 
	 * @param mchtStatus
	 *            the MCHT_STATUS value
	 */
	public void setMchtStatus(java.lang.String mchtStatus) {
		this.mchtStatus = mchtStatus;
	}

	/**
	 * Return the value associated with the column: MANU_AUTH_FLAG
	 */
	public java.lang.String getManuAuthFlag() {
		return manuAuthFlag;
	}

	/**
	 * Set the value related to the column: MANU_AUTH_FLAG
	 * 
	 * @param manuAuthFlag
	 *            the MANU_AUTH_FLAG value
	 */
	public void setManuAuthFlag(java.lang.String manuAuthFlag) {
		this.manuAuthFlag = manuAuthFlag;
	}

	/**
	 * Return the value associated with the column: PART_NUM
	 */
	public java.lang.String getPartNum() {
		return partNum;
	}

	/**
	 * Set the value related to the column: PART_NUM
	 * 
	 * @param partNum
	 *            the PART_NUM value
	 */
	public void setPartNum(java.lang.String partNum) {
		this.partNum = partNum;
	}

	/**
	 * Return the value associated with the column: DISC_CONS_FLG
	 */
	public java.lang.String getDiscConsFlg() {
		return discConsFlg;
	}

	/**
	 * Set the value related to the column: DISC_CONS_FLG
	 * 
	 * @param discConsFlg
	 *            the DISC_CONS_FLG value
	 */
	public void setDiscConsFlg(java.lang.String discConsFlg) {
		this.discConsFlg = discConsFlg;
	}

	/**
	 * Return the value associated with the column: DISC_CONS_REBATE
	 */
	public java.lang.String getDiscConsRebate() {
		return discConsRebate;
	}

	/**
	 * Set the value related to the column: DISC_CONS_REBATE
	 * 
	 * @param discConsRebate
	 *            the DISC_CONS_REBATE value
	 */
	public void setDiscConsRebate(java.lang.String discConsRebate) {
		this.discConsRebate = discConsRebate;
	}

	/**
	 * Return the value associated with the column: PASS_FLAG
	 */
	public java.lang.String getPassFlag() {
		return passFlag;
	}

	/**
	 * Set the value related to the column: PASS_FLAG
	 * 
	 * @param passFlag
	 *            the PASS_FLAG value
	 */
	public void setPassFlag(java.lang.String passFlag) {
		this.passFlag = passFlag;
	}

	/**
	 * Return the value associated with the column: OPEN_DAYS
	 */
	public java.lang.String getOpenDays() {
		return openDays;
	}

	/**
	 * Set the value related to the column: OPEN_DAYS
	 * 
	 * @param openDays
	 *            the OPEN_DAYS value
	 */
	public void setOpenDays(java.lang.String openDays) {
		this.openDays = openDays;
	}

	/**
	 * Return the value associated with the column: SLEEP_DAYS
	 */
	public java.lang.String getSleepDays() {
		return sleepDays;
	}

	/**
	 * Set the value related to the column: SLEEP_DAYS
	 * 
	 * @param sleepDays
	 *            the SLEEP_DAYS value
	 */
	public void setSleepDays(java.lang.String sleepDays) {
		this.sleepDays = sleepDays;
	}

	/**
	 * Return the value associated with the column: MCHT_CN_ABBR
	 */
	public java.lang.String getMchtCnAbbr() {
		return mchtCnAbbr;
	}

	/**
	 * Set the value related to the column: MCHT_CN_ABBR
	 * 
	 * @param mchtCnAbbr
	 *            the MCHT_CN_ABBR value
	 */
	public void setMchtCnAbbr(java.lang.String mchtCnAbbr) {
		this.mchtCnAbbr = mchtCnAbbr;
	}

	/**
	 * Return the value associated with the column: SPELL_NAME
	 */
	public java.lang.String getSpellName() {
		return spellName;
	}

	/**
	 * Set the value related to the column: SPELL_NAME
	 * 
	 * @param spellName
	 *            the SPELL_NAME value
	 */
	public void setSpellName(java.lang.String spellName) {
		this.spellName = spellName;
	}

	/**
	 * Return the value associated with the column: ENG_NAME
	 */
	public java.lang.String getEngName() {
		return engName;
	}

	/**
	 * Set the value related to the column: ENG_NAME
	 * 
	 * @param engName
	 *            the ENG_NAME value
	 */
	public void setEngName(java.lang.String engName) {
		this.engName = engName;
	}

	/**
	 * Return the value associated with the column: MCHT_EN_ABBR
	 */
	public java.lang.String getMchtEnAbbr() {
		return mchtEnAbbr;
	}

	/**
	 * Set the value related to the column: MCHT_EN_ABBR
	 * 
	 * @param mchtEnAbbr
	 *            the MCHT_EN_ABBR value
	 */
	public void setMchtEnAbbr(java.lang.String mchtEnAbbr) {
		this.mchtEnAbbr = mchtEnAbbr;
	}

	/**
	 * Return the value associated with the column: AREA_NO
	 */
	public java.lang.String getAreaNo() {
		return areaNo;
	}

	/**
	 * Set the value related to the column: AREA_NO
	 * 
	 * @param areaNo
	 *            the AREA_NO value
	 */
	public void setAreaNo(java.lang.String areaNo) {
		this.areaNo = areaNo;
	}

	/**
	 * Return the value associated with the column: ADDR
	 */
	public java.lang.String getAddr() {
		return addr;
	}

	/**
	 * Set the value related to the column: ADDR
	 * 
	 * @param addr
	 *            the ADDR value
	 */
	public void setAddr(java.lang.String addr) {
		this.addr = addr;
	}

	/**
	 * Return the value associated with the column: HOME_PAGE
	 */
	public java.lang.String getHomePage() {
		return homePage;
	}

	/**
	 * Set the value related to the column: HOME_PAGE
	 * 
	 * @param homePage
	 *            the HOME_PAGE value
	 */
	public void setHomePage(java.lang.String homePage) {
		this.homePage = homePage;
	}

	/**
	 * Return the value associated with the column: MCC
	 */
	public java.lang.String getMcc() {
		return mcc;
	}

	/**
	 * Set the value related to the column: MCC
	 * 
	 * @param mcc
	 *            the MCC value
	 */
	public void setMcc(java.lang.String mcc) {
		this.mcc = mcc;
	}

	/**
	 * Return the value associated with the column: TCC
	 */
	public java.lang.String getTcc() {
		return tcc;
	}

	/**
	 * Set the value related to the column: TCC
	 * 
	 * @param tcc
	 *            the TCC value
	 */
	public void setTcc(java.lang.String tcc) {
		this.tcc = tcc;
	}

	/**
	 * Return the value associated with the column: ETPS_ATTR
	 */
	public java.lang.String getEtpsAttr() {
		return etpsAttr;
	}

	/**
	 * Set the value related to the column: ETPS_ATTR
	 * 
	 * @param etpsAttr
	 *            the ETPS_ATTR value
	 */
	public void setEtpsAttr(java.lang.String etpsAttr) {
		this.etpsAttr = etpsAttr;
	}

	/**
	 * Return the value associated with the column: MNG_MCHT_ID
	 */
	public java.lang.String getMngMchtId() {
		return mngMchtId;
	}

	/**
	 * Set the value related to the column: MNG_MCHT_ID
	 * 
	 * @param mngMchtId
	 *            the MNG_MCHT_ID value
	 */
	public void setMngMchtId(java.lang.String mngMchtId) {
		this.mngMchtId = mngMchtId;
	}

	/**
	 * Return the value associated with the column: MCHT_GRP
	 */
	public java.lang.String getMchtGrp() {
		return mchtGrp;
	}

	/**
	 * Set the value related to the column: MCHT_GRP
	 * 
	 * @param mchtGrp
	 *            the MCHT_GRP value
	 */
	public void setMchtGrp(java.lang.String mchtGrp) {
		this.mchtGrp = mchtGrp;
	}

	/**
	 * Return the value associated with the column: MCHT_ATTR
	 */
	public java.lang.String getMchtAttr() {
		return mchtAttr;
	}

	/**
	 * Set the value related to the column: MCHT_ATTR
	 * 
	 * @param mchtAttr
	 *            the MCHT_ATTR value
	 */
	public void setMchtAttr(java.lang.String mchtAttr) {
		this.mchtAttr = mchtAttr;
	}

	/**
	 * Return the value associated with the column: MCHT_GROUP_FLAG
	 */
	public java.lang.String getMchtGroupFlag() {
		return mchtGroupFlag;
	}

	/**
	 * Set the value related to the column: MCHT_GROUP_FLAG
	 * 
	 * @param mchtGroupFlag
	 *            the MCHT_GROUP_FLAG value
	 */
	public void setMchtGroupFlag(java.lang.String mchtGroupFlag) {
		this.mchtGroupFlag = mchtGroupFlag;
	}

	/**
	 * Return the value associated with the column: MCHT_GROUP_ID
	 */
	public java.lang.String getMchtGroupId() {
		return mchtGroupId;
	}

	/**
	 * Set the value related to the column: MCHT_GROUP_ID
	 * 
	 * @param mchtGroupId
	 *            the MCHT_GROUP_ID value
	 */
	public void setMchtGroupId(java.lang.String mchtGroupId) {
		this.mchtGroupId = mchtGroupId;
	}

	/**
	 * Return the value associated with the column: MCHT_ENG_NM
	 */
	public java.lang.String getMchtEngNm() {
		return mchtEngNm;
	}

	/**
	 * Set the value related to the column: MCHT_ENG_NM
	 * 
	 * @param mchtEngNm
	 *            the MCHT_ENG_NM value
	 */
	public void setMchtEngNm(java.lang.String mchtEngNm) {
		this.mchtEngNm = mchtEngNm;
	}

	/**
	 * Return the value associated with the column: MCHT_ENG_ADDR
	 */
	public java.lang.String getMchtEngAddr() {
		return mchtEngAddr;
	}

	/**
	 * Set the value related to the column: MCHT_ENG_ADDR
	 * 
	 * @param mchtEngAddr
	 *            the MCHT_ENG_ADDR value
	 */
	public void setMchtEngAddr(java.lang.String mchtEngAddr) {
		this.mchtEngAddr = mchtEngAddr;
	}

	/**
	 * Return the value associated with the column: MCHT_ENG_CITY_NAME
	 */
	public java.lang.String getMchtEngCityName() {
		return mchtEngCityName;
	}

	/**
	 * Set the value related to the column: MCHT_ENG_CITY_NAME
	 * 
	 * @param mchtEngCityName
	 *            the MCHT_ENG_CITY_NAME value
	 */
	public void setMchtEngCityName(java.lang.String mchtEngCityName) {
		this.mchtEngCityName = mchtEngCityName;
	}

	/**
	 * Return the value associated with the column: SA_LIMIT_AMT
	 */
	public java.lang.String getSaLimitAmt() {
		return saLimitAmt;
	}

	/**
	 * Set the value related to the column: SA_LIMIT_AMT
	 * 
	 * @param saLimitAmt
	 *            the SA_LIMIT_AMT value
	 */
	public void setSaLimitAmt(java.lang.String saLimitAmt) {
		this.saLimitAmt = saLimitAmt;
	}

	/**
	 * Return the value associated with the column: SA_ACTION
	 */
	public java.lang.String getSaAction() {
		return saAction;
	}

	/**
	 * Set the value related to the column: SA_ACTION
	 * 
	 * @param saAction
	 *            the SA_ACTION value
	 */
	public void setSaAction(java.lang.String saAction) {
		this.saAction = saAction;
	}

	/**
	 * Return the value associated with the column: PSAM_NUM
	 */
	public java.lang.String getPsamNum() {
		return psamNum;
	}

	/**
	 * Set the value related to the column: PSAM_NUM
	 * 
	 * @param psamNum
	 *            the PSAM_NUM value
	 */
	public void setPsamNum(java.lang.String psamNum) {
		this.psamNum = psamNum;
	}

	/**
	 * Return the value associated with the column: CD_MAC_NUM
	 */
	public java.lang.String getCdMacNum() {
		return cdMacNum;
	}

	/**
	 * Set the value related to the column: CD_MAC_NUM
	 * 
	 * @param cdMacNum
	 *            the CD_MAC_NUM value
	 */
	public void setCdMacNum(java.lang.String cdMacNum) {
		this.cdMacNum = cdMacNum;
	}

	/**
	 * Return the value associated with the column: POS_NUM
	 */
	public java.lang.String getPosNum() {
		return posNum;
	}

	/**
	 * Set the value related to the column: POS_NUM
	 * 
	 * @param posNum
	 *            the POS_NUM value
	 */
	public void setPosNum(java.lang.String posNum) {
		this.posNum = posNum;
	}

	/**
	 * Return the value associated with the column: CONN_TYPE
	 */
	public java.lang.String getConnType() {
		return connType;
	}

	/**
	 * Set the value related to the column: CONN_TYPE
	 * 
	 * @param connType
	 *            the CONN_TYPE value
	 */
	public void setConnType(java.lang.String connType) {
		this.connType = connType;
	}

	/**
	 * Return the value associated with the column: MCHT_MNG_MODE
	 */
	public java.lang.String getMchtMngMode() {
		return mchtMngMode;
	}

	/**
	 * Set the value related to the column: MCHT_MNG_MODE
	 * 
	 * @param mchtMngMode
	 *            the MCHT_MNG_MODE value
	 */
	public void setMchtMngMode(java.lang.String mchtMngMode) {
		this.mchtMngMode = mchtMngMode;
	}

	/**
	 * Return the value associated with the column: MCHT_FUNCTION
	 */
	public java.lang.String getMchtFunction() {
		return mchtFunction;
	}

	/**
	 * Set the value related to the column: MCHT_FUNCTION
	 * 
	 * @param mchtFunction
	 *            the MCHT_FUNCTION value
	 */
	public void setMchtFunction(java.lang.String mchtFunction) {
		this.mchtFunction = mchtFunction;
	}

	/**
	 * Return the value associated with the column: LICENCE_NO
	 */
	public java.lang.String getLicenceNo() {
		return licenceNo;
	}

	/**
	 * Set the value related to the column: LICENCE_NO
	 * 
	 * @param licenceNo
	 *            the LICENCE_NO value
	 */
	public void setLicenceNo(java.lang.String licenceNo) {
		this.licenceNo = licenceNo;
	}

	/**
	 * Return the value associated with the column: LICENCE_END_DATE
	 */
	public java.lang.String getLicenceEndDate() {
		return licenceEndDate;
	}

	/**
	 * Set the value related to the column: LICENCE_END_DATE
	 * 
	 * @param licenceEndDate
	 *            the LICENCE_END_DATE value
	 */
	public void setLicenceEndDate(java.lang.String licenceEndDate) {
		this.licenceEndDate = licenceEndDate;
	}

	/**
	 * Return the value associated with the column: BANK_LICENCE_NO
	 */
	public java.lang.String getBankLicenceNo() {
		return bankLicenceNo;
	}

	/**
	 * Set the value related to the column: BANK_LICENCE_NO
	 * 
	 * @param bankLicenceNo
	 *            the BANK_LICENCE_NO value
	 */
	public void setBankLicenceNo(java.lang.String bankLicenceNo) {
		this.bankLicenceNo = bankLicenceNo;
	}

	/**
	 * Return the value associated with the column: BUS_TYPE
	 */
	public java.lang.String getBusType() {
		return busType;
	}

	/**
	 * Set the value related to the column: BUS_TYPE
	 * 
	 * @param busType
	 *            the BUS_TYPE value
	 */
	public void setBusType(java.lang.String busType) {
		this.busType = busType;
	}

	/**
	 * Return the value associated with the column: FAX_NO
	 */
	public java.lang.String getFaxNo() {
		return faxNo;
	}

	/**
	 * Set the value related to the column: FAX_NO
	 * 
	 * @param faxNo
	 *            the FAX_NO value
	 */
	public void setFaxNo(java.lang.String faxNo) {
		this.faxNo = faxNo;
	}

	/**
	 * Return the value associated with the column: BUS_AMT
	 */
	public java.lang.String getBusAmt() {
		return busAmt;
	}

	/**
	 * Set the value related to the column: BUS_AMT
	 * 
	 * @param busAmt
	 *            the BUS_AMT value
	 */
	public void setBusAmt(java.lang.String busAmt) {
		this.busAmt = busAmt;
	}

	/**
	 * Return the value associated with the column: MCHT_CRE_LVL
	 */
	public java.lang.String getMchtCreLvl() {
		return mchtCreLvl;
	}

	/**
	 * Set the value related to the column: MCHT_CRE_LVL
	 * 
	 * @param mchtCreLvl
	 *            the MCHT_CRE_LVL value
	 */
	public void setMchtCreLvl(java.lang.String mchtCreLvl) {
		this.mchtCreLvl = mchtCreLvl;
	}

	/**
	 * Return the value associated with the column: CONTACT
	 */
	public java.lang.String getContact() {
		return contact;
	}

	/**
	 * Set the value related to the column: CONTACT
	 * 
	 * @param contact
	 *            the CONTACT value
	 */
	public void setContact(java.lang.String contact) {
		this.contact = contact;
	}

	/**
	 * Return the value associated with the column: POST_CODE
	 */
	public java.lang.String getPostCode() {
		return postCode;
	}

	/**
	 * Set the value related to the column: POST_CODE
	 * 
	 * @param postCode
	 *            the POST_CODE value
	 */
	public void setPostCode(java.lang.String postCode) {
		this.postCode = postCode;
	}

	/**
	 * Return the value associated with the column: COMM_EMAIL
	 */
	public java.lang.String getCommEmail() {
		return commEmail;
	}

	/**
	 * Set the value related to the column: COMM_EMAIL
	 * 
	 * @param commEmail
	 *            the COMM_EMAIL value
	 */
	public void setCommEmail(java.lang.String commEmail) {
		this.commEmail = commEmail;
	}

	/**
	 * Return the value associated with the column: COMM_MOBIL
	 */
	public java.lang.String getCommMobil() {
		return commMobil;
	}

	/**
	 * Set the value related to the column: COMM_MOBIL
	 * 
	 * @param commMobil
	 *            the COMM_MOBIL value
	 */
	public void setCommMobil(java.lang.String commMobil) {
		this.commMobil = commMobil;
	}

	/**
	 * Return the value associated with the column: COMM_TEL
	 */
	public java.lang.String getCommTel() {
		return commTel;
	}

	/**
	 * Set the value related to the column: COMM_TEL
	 * 
	 * @param commTel
	 *            the COMM_TEL value
	 */
	public void setCommTel(java.lang.String commTel) {
		this.commTel = commTel;
	}

	/**
	 * Return the value associated with the column: MANAGER
	 */
	public java.lang.String getManager() {
		return manager;
	}

	/**
	 * Set the value related to the column: MANAGER
	 * 
	 * @param manager
	 *            the MANAGER value
	 */
	public void setManager(java.lang.String manager) {
		this.manager = manager;
	}

	/**
	 * Return the value associated with the column: ARTIF_CERTIF_TP
	 */
	public java.lang.String getArtifCertifTp() {
		return artifCertifTp;
	}

	/**
	 * Set the value related to the column: ARTIF_CERTIF_TP
	 * 
	 * @param artifCertifTp
	 *            the ARTIF_CERTIF_TP value
	 */
	public void setArtifCertifTp(java.lang.String artifCertifTp) {
		this.artifCertifTp = artifCertifTp;
	}

	/**
	 * Return the value associated with the column: IDENTITY_NO
	 */
	public java.lang.String getIdentityNo() {
		return identityNo;
	}

	/**
	 * Set the value related to the column: IDENTITY_NO
	 * 
	 * @param identityNo
	 *            the IDENTITY_NO value
	 */
	public void setIdentityNo(java.lang.String identityNo) {
		this.identityNo = identityNo;
	}

	/**
	 * Return the value associated with the column: MANAGER_TEL
	 */
	public java.lang.String getManagerTel() {
		return managerTel;
	}

	/**
	 * Set the value related to the column: MANAGER_TEL
	 * 
	 * @param managerTel
	 *            the MANAGER_TEL value
	 */
	public void setManagerTel(java.lang.String managerTel) {
		this.managerTel = managerTel;
	}

	/**
	 * Return the value associated with the column: FAX
	 */
	public java.lang.String getFax() {
		return fax;
	}

	/**
	 * Set the value related to the column: FAX
	 * 
	 * @param fax
	 *            the FAX value
	 */
	public void setFax(java.lang.String fax) {
		this.fax = fax;
	}

	/**
	 * Return the value associated with the column: ELECTROFAX
	 */
	public java.lang.String getElectrofax() {
		return electrofax;
	}

	/**
	 * Set the value related to the column: ELECTROFAX
	 * 
	 * @param electrofax
	 *            the ELECTROFAX value
	 */
	public void setElectrofax(java.lang.String electrofax) {
		this.electrofax = electrofax;
	}

	/**
	 * Return the value associated with the column: REG_ADDR
	 */
	public java.lang.String getRegAddr() {
		return regAddr;
	}

	/**
	 * Set the value related to the column: REG_ADDR
	 * 
	 * @param regAddr
	 *            the REG_ADDR value
	 */
	public void setRegAddr(java.lang.String regAddr) {
		this.regAddr = regAddr;
	}

	/**
	 * Return the value associated with the column: APPLY_DATE
	 */
	public java.lang.String getApplyDate() {
		return applyDate;
	}

	/**
	 * Set the value related to the column: APPLY_DATE
	 * 
	 * @param applyDate
	 *            the APPLY_DATE value
	 */
	public void setApplyDate(java.lang.String applyDate) {
		this.applyDate = applyDate;
	}

	/**
	 * Return the value associated with the column: ENABLE_DATE
	 */
	public java.lang.String getEnableDate() {
		return enableDate;
	}

	/**
	 * Set the value related to the column: ENABLE_DATE
	 * 
	 * @param enableDate
	 *            the ENABLE_DATE value
	 */
	public void setEnableDate(java.lang.String enableDate) {
		this.enableDate = enableDate;
	}

	/**
	 * Return the value associated with the column: PRE_AUD_NM
	 */
	public java.lang.String getPreAudNm() {
		return preAudNm;
	}

	/**
	 * Set the value related to the column: PRE_AUD_NM
	 * 
	 * @param preAudNm
	 *            the PRE_AUD_NM value
	 */
	public void setPreAudNm(java.lang.String preAudNm) {
		this.preAudNm = preAudNm;
	}

	/**
	 * Return the value associated with the column: CONFIRM_NM
	 */
	public java.lang.String getConfirmNm() {
		return confirmNm;
	}

	/**
	 * Set the value related to the column: CONFIRM_NM
	 * 
	 * @param confirmNm
	 *            the CONFIRM_NM value
	 */
	public void setConfirmNm(java.lang.String confirmNm) {
		this.confirmNm = confirmNm;
	}

	/**
	 * Return the value associated with the column: PROTOCAL_ID
	 */
	public java.lang.String getProtocalId() {
		return protocalId;
	}

	/**
	 * Set the value related to the column: PROTOCAL_ID
	 * 
	 * @param protocalId
	 *            the PROTOCAL_ID value
	 */
	public void setProtocalId(java.lang.String protocalId) {
		this.protocalId = protocalId;
	}

	/**
	 * Return the value associated with the column: SIGN_INST_ID
	 */
	public java.lang.String getSignInstId() {
		return signInstId;
	}

	/**
	 * Set the value related to the column: SIGN_INST_ID
	 * 
	 * @param signInstId
	 *            the SIGN_INST_ID value
	 */
	public void setSignInstId(java.lang.String signInstId) {
		this.signInstId = signInstId;
	}

	/**
	 * Return the value associated with the column: NET_NM
	 */
	public java.lang.String getNetNm() {
		return netNm;
	}

	/**
	 * Set the value related to the column: NET_NM
	 * 
	 * @param netNm
	 *            the NET_NM value
	 */
	public void setNetNm(java.lang.String netNm) {
		this.netNm = netNm;
	}

	/**
	 * Return the value associated with the column: AGR_BR
	 */
	public java.lang.String getAgrBr() {
		return agrBr;
	}

	/**
	 * Set the value related to the column: AGR_BR
	 * 
	 * @param agrBr
	 *            the AGR_BR value
	 */
	public void setAgrBr(java.lang.String agrBr) {
		this.agrBr = agrBr;
	}

	/**
	 * Return the value associated with the column: NET_TEL
	 */
	public java.lang.String getNetTel() {
		return netTel;
	}

	/**
	 * Set the value related to the column: NET_TEL
	 * 
	 * @param netTel
	 *            the NET_TEL value
	 */
	public void setNetTel(java.lang.String netTel) {
		this.netTel = netTel;
	}

	/**
	 * Return the value associated with the column: PROL_DATE
	 */
	public java.lang.String getProlDate() {
		return prolDate;
	}

	/**
	 * Set the value related to the column: PROL_DATE
	 * 
	 * @param prolDate
	 *            the PROL_DATE value
	 */
	public void setProlDate(java.lang.String prolDate) {
		this.prolDate = prolDate;
	}

	/**
	 * Return the value associated with the column: PROL_TLR
	 */
	public java.lang.String getProlTlr() {
		return prolTlr;
	}

	/**
	 * Set the value related to the column: PROL_TLR
	 * 
	 * @param prolTlr
	 *            the PROL_TLR value
	 */
	public void setProlTlr(java.lang.String prolTlr) {
		this.prolTlr = prolTlr;
	}

	/**
	 * Return the value associated with the column: CLOSE_DATE
	 */
	public java.lang.String getCloseDate() {
		return closeDate;
	}

	/**
	 * Set the value related to the column: CLOSE_DATE
	 * 
	 * @param closeDate
	 *            the CLOSE_DATE value
	 */
	public void setCloseDate(java.lang.String closeDate) {
		this.closeDate = closeDate;
	}

	/**
	 * Return the value associated with the column: CLOSE_TLR
	 */
	public java.lang.String getCloseTlr() {
		return closeTlr;
	}

	/**
	 * Set the value related to the column: CLOSE_TLR
	 * 
	 * @param closeTlr
	 *            the CLOSE_TLR value
	 */
	public void setCloseTlr(java.lang.String closeTlr) {
		this.closeTlr = closeTlr;
	}

	/**
	 * Return the value associated with the column: OPER_NO
	 */
	public java.lang.String getOperNo() {
		return operNo;
	}

	/**
	 * Set the value related to the column: OPER_NO
	 * 
	 * @param operNo
	 *            the OPER_NO value
	 */
	public void setOperNo(java.lang.String operNo) {
		this.operNo = operNo;
	}

	/**
	 * Return the value associated with the column: OPER_NM
	 */
	public java.lang.String getOperNm() {
		return operNm;
	}

	/**
	 * Set the value related to the column: OPER_NM
	 * 
	 * @param operNm
	 *            the OPER_NM value
	 */
	public void setOperNm(java.lang.String operNm) {
		this.operNm = operNm;
	}

	/**
	 * Return the value associated with the column: PROC_FLAG
	 */
	public java.lang.String getProcFlag() {
		return procFlag;
	}

	/**
	 * Set the value related to the column: PROC_FLAG
	 * 
	 * @param procFlag
	 *            the PROC_FLAG value
	 */
	public void setProcFlag(java.lang.String procFlag) {
		this.procFlag = procFlag;
	}

	/**
	 * Return the value associated with the column: SET_CUR
	 */
	public java.lang.String getSetCur() {
		return setCur;
	}

	/**
	 * Set the value related to the column: SET_CUR
	 * 
	 * @param setCur
	 *            the SET_CUR value
	 */
	public void setSetCur(java.lang.String setCur) {
		this.setCur = setCur;
	}

	/**
	 * Return the value associated with the column: PRINT_INST_ID
	 */
	public java.lang.String getPrintInstId() {
		return printInstId;
	}

	/**
	 * Set the value related to the column: PRINT_INST_ID
	 * 
	 * @param printInstId
	 *            the PRINT_INST_ID value
	 */
	public void setPrintInstId(java.lang.String printInstId) {
		this.printInstId = printInstId;
	}

	/**
	 * Return the value associated with the column: ACQ_INST_ID
	 */
	public java.lang.String getAcqInstId() {
		return acqInstId;
	}

	/**
	 * Set the value related to the column: ACQ_INST_ID
	 * 
	 * @param acqInstId
	 *            the ACQ_INST_ID value
	 */
	public void setAcqInstId(java.lang.String acqInstId) {
		this.acqInstId = acqInstId;
	}

	/**
	 * Return the value associated with the column: ACQ_BK_NAME
	 */
	public java.lang.String getAcqBkName() {
		return acqBkName;
	}

	/**
	 * Set the value related to the column: ACQ_BK_NAME
	 * 
	 * @param acqBkName
	 *            the ACQ_BK_NAME value
	 */
	public void setAcqBkName(java.lang.String acqBkName) {
		this.acqBkName = acqBkName;
	}

	/**
	 * Return the value associated with the column: BANK_NO
	 */
	public java.lang.String getBankNo() {
		return bankNo;
	}

	/**
	 * Set the value related to the column: BANK_NO
	 * 
	 * @param bankNo
	 *            the BANK_NO value
	 */
	public void setBankNo(java.lang.String bankNo) {
		this.bankNo = bankNo;
	}

	/**
	 * Return the value associated with the column: ORGN_NO
	 */
	public java.lang.String getOrgnNo() {
		return orgnNo;
	}

	/**
	 * Set the value related to the column: ORGN_NO
	 * 
	 * @param orgnNo
	 *            the ORGN_NO value
	 */
	public void setOrgnNo(java.lang.String orgnNo) {
		this.orgnNo = orgnNo;
	}

	/**
	 * Return the value associated with the column: SUBBRH_NO
	 */
	public java.lang.String getSubbrhNo() {
		return subbrhNo;
	}

	/**
	 * Set the value related to the column: SUBBRH_NO
	 * 
	 * @param subbrhNo
	 *            the SUBBRH_NO value
	 */
	public void setSubbrhNo(java.lang.String subbrhNo) {
		this.subbrhNo = subbrhNo;
	}

	/**
	 * Return the value associated with the column: SUBBRH_NM
	 */
	public java.lang.String getSubbrhNm() {
		return subbrhNm;
	}

	/**
	 * Set the value related to the column: SUBBRH_NM
	 * 
	 * @param subbrhNm
	 *            the SUBBRH_NM value
	 */
	public void setSubbrhNm(java.lang.String subbrhNm) {
		this.subbrhNm = subbrhNm;
	}

	/**
	 * Return the value associated with the column: OPEN_TIME
	 */
	public java.lang.String getOpenTime() {
		return openTime;
	}

	/**
	 * Set the value related to the column: OPEN_TIME
	 * 
	 * @param openTime
	 *            the OPEN_TIME value
	 */
	public void setOpenTime(java.lang.String openTime) {
		this.openTime = openTime;
	}

	/**
	 * Return the value associated with the column: CLOSE_TIME
	 */
	public java.lang.String getCloseTime() {
		return closeTime;
	}

	/**
	 * Set the value related to the column: CLOSE_TIME
	 * 
	 * @param closeTime
	 *            the CLOSE_TIME value
	 */
	public void setCloseTime(java.lang.String closeTime) {
		this.closeTime = closeTime;
	}

	/**
	 * Return the value associated with the column: VIS_ACT_FLG
	 */
	public java.lang.String getVisActFlg() {
		return visActFlg;
	}

	/**
	 * Set the value related to the column: VIS_ACT_FLG
	 * 
	 * @param visActFlg
	 *            the VIS_ACT_FLG value
	 */
	public void setVisActFlg(java.lang.String visActFlg) {
		this.visActFlg = visActFlg;
	}

	/**
	 * Return the value associated with the column: VIS_MCHT_ID
	 */
	public java.lang.String getVisMchtId() {
		return visMchtId;
	}

	/**
	 * Set the value related to the column: VIS_MCHT_ID
	 * 
	 * @param visMchtId
	 *            the VIS_MCHT_ID value
	 */
	public void setVisMchtId(java.lang.String visMchtId) {
		this.visMchtId = visMchtId;
	}

	/**
	 * Return the value associated with the column: MST_ACT_FLG
	 */
	public java.lang.String getMstActFlg() {
		return mstActFlg;
	}

	/**
	 * Set the value related to the column: MST_ACT_FLG
	 * 
	 * @param mstActFlg
	 *            the MST_ACT_FLG value
	 */
	public void setMstActFlg(java.lang.String mstActFlg) {
		this.mstActFlg = mstActFlg;
	}

	/**
	 * Return the value associated with the column: MST_MCHT_ID
	 */
	public java.lang.String getMstMchtId() {
		return mstMchtId;
	}

	/**
	 * Set the value related to the column: MST_MCHT_ID
	 * 
	 * @param mstMchtId
	 *            the MST_MCHT_ID value
	 */
	public void setMstMchtId(java.lang.String mstMchtId) {
		this.mstMchtId = mstMchtId;
	}

	/**
	 * Return the value associated with the column: AMX_ACT_FLG
	 */
	public java.lang.String getAmxActFlg() {
		return amxActFlg;
	}

	/**
	 * Set the value related to the column: AMX_ACT_FLG
	 * 
	 * @param amxActFlg
	 *            the AMX_ACT_FLG value
	 */
	public void setAmxActFlg(java.lang.String amxActFlg) {
		this.amxActFlg = amxActFlg;
	}

	/**
	 * Return the value associated with the column: AMX_MCHT_ID
	 */
	public java.lang.String getAmxMchtId() {
		return amxMchtId;
	}

	/**
	 * Set the value related to the column: AMX_MCHT_ID
	 * 
	 * @param amxMchtId
	 *            the AMX_MCHT_ID value
	 */
	public void setAmxMchtId(java.lang.String amxMchtId) {
		this.amxMchtId = amxMchtId;
	}

	/**
	 * Return the value associated with the column: DNR_ACT_FLG
	 */
	public java.lang.String getDnrActFlg() {
		return dnrActFlg;
	}

	/**
	 * Set the value related to the column: DNR_ACT_FLG
	 * 
	 * @param dnrActFlg
	 *            the DNR_ACT_FLG value
	 */
	public void setDnrActFlg(java.lang.String dnrActFlg) {
		this.dnrActFlg = dnrActFlg;
	}

	/**
	 * Return the value associated with the column: DNR_MCHT_ID
	 */
	public java.lang.String getDnrMchtId() {
		return dnrMchtId;
	}

	/**
	 * Set the value related to the column: DNR_MCHT_ID
	 * 
	 * @param dnrMchtId
	 *            the DNR_MCHT_ID value
	 */
	public void setDnrMchtId(java.lang.String dnrMchtId) {
		this.dnrMchtId = dnrMchtId;
	}

	/**
	 * Return the value associated with the column: JCB_ACT_FLG
	 */
	public java.lang.String getJcbActFlg() {
		return jcbActFlg;
	}

	/**
	 * Set the value related to the column: JCB_ACT_FLG
	 * 
	 * @param jcbActFlg
	 *            the JCB_ACT_FLG value
	 */
	public void setJcbActFlg(java.lang.String jcbActFlg) {
		this.jcbActFlg = jcbActFlg;
	}

	/**
	 * Return the value associated with the column: JCB_MCHT_ID
	 */
	public java.lang.String getJcbMchtId() {
		return jcbMchtId;
	}

	/**
	 * Set the value related to the column: JCB_MCHT_ID
	 * 
	 * @param jcbMchtId
	 *            the JCB_MCHT_ID value
	 */
	public void setJcbMchtId(java.lang.String jcbMchtId) {
		this.jcbMchtId = jcbMchtId;
	}

	/**
	 * Return the value associated with the column: CUP_MCHT_FLG
	 */
	public java.lang.String getCupMchtFlg() {
		return cupMchtFlg;
	}

	/**
	 * Set the value related to the column: CUP_MCHT_FLG
	 * 
	 * @param cupMchtFlg
	 *            the CUP_MCHT_FLG value
	 */
	public void setCupMchtFlg(java.lang.String cupMchtFlg) {
		this.cupMchtFlg = cupMchtFlg;
	}

	/**
	 * Return the value associated with the column: DEB_MCHT_FLG
	 */
	public java.lang.String getDebMchtFlg() {
		return debMchtFlg;
	}

	/**
	 * Set the value related to the column: DEB_MCHT_FLG
	 * 
	 * @param debMchtFlg
	 *            the DEB_MCHT_FLG value
	 */
	public void setDebMchtFlg(java.lang.String debMchtFlg) {
		this.debMchtFlg = debMchtFlg;
	}

	/**
	 * Return the value associated with the column: CRE_MCHT_FLG
	 */
	public java.lang.String getCreMchtFlg() {
		return creMchtFlg;
	}

	/**
	 * Set the value related to the column: CRE_MCHT_FLG
	 * 
	 * @param creMchtFlg
	 *            the CRE_MCHT_FLG value
	 */
	public void setCreMchtFlg(java.lang.String creMchtFlg) {
		this.creMchtFlg = creMchtFlg;
	}

	/**
	 * Return the value associated with the column: RESERVED
	 */
	public java.lang.String getReserved() {
		return reserved;
	}

	/**
	 * Set the value related to the column: RESERVED
	 * 
	 * @param reserved
	 *            the RESERVED value
	 */
	public void setReserved(java.lang.String reserved) {
		this.reserved = reserved;
	}

	/**
	 * Return the value associated with the column: REC_UPD_TS
	 */
	public java.lang.String getRecUpdTs() {
		return recUpdTs;
	}

	/**
	 * Set the value related to the column: REC_UPD_TS
	 * 
	 * @param recUpdTs
	 *            the REC_UPD_TS value
	 */
	public void setRecUpdTs(java.lang.String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}

	/**
	 * Return the value associated with the column: REC_CRT_TS
	 */
	public java.lang.String getRecCrtTs() {
		return recCrtTs;
	}

	/**
	 * Set the value related to the column: REC_CRT_TS
	 * 
	 * @param recCrtTs
	 *            the REC_CRT_TS value
	 */
	public void setRecCrtTs(java.lang.String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	public java.lang.String getSettleAreaNo() {
		return settleAreaNo;
	}

	public void setSettleAreaNo(java.lang.String settleAreaNo) {
		this.settleAreaNo = settleAreaNo;
	}

	public java.lang.String getMainTlr() {
		return mainTlr;
	}

	public void setMainTlr(java.lang.String mainTlr) {
		this.mainTlr = mainTlr;
	}

	public java.lang.String getCheckTlr() {
		return checkTlr;
	}

	public void setCheckTlr(java.lang.String checkTlr) {
		this.checkTlr = checkTlr;
	}

	/**
	 * @return the compNm
	 */
	public java.lang.String getCompNm() {
		return compNm;
	}

	/**
	 * @param compNm
	 *            the compNm to set
	 */
	public void setCompNm(java.lang.String compNm) {
		this.compNm = compNm;
	}

	/**
	 * @return the compaddr
	 */
	public java.lang.String getCompaddr() {
		return compaddr;
	}

	/**
	 * @param compaddr
	 *            the compaddr to set
	 */
	public void setCompaddr(java.lang.String compaddr) {
		this.compaddr = compaddr;
	}

	/**
	 * @return the finacontact
	 */
	public java.lang.String getFinacontact() {
		return finacontact;
	}

	/**
	 * @param finacontact
	 *            the finacontact to set
	 */
	public void setFinacontact(java.lang.String finacontact) {
		this.finacontact = finacontact;
	}

	/**
	 * @return the finacommTel
	 */
	public java.lang.String getFinacommTel() {
		return finacommTel;
	}

	/**
	 * @param finacommTel
	 *            the finacommTel to set
	 */
	public void setFinacommTel(java.lang.String finacommTel) {
		this.finacommTel = finacommTel;
	}

	/**
	 * @return the finacommEmail
	 */
	public java.lang.String getFinacommEmail() {
		return finacommEmail;
	}

	/**
	 * @param finacommEmail
	 *            the finacommEmail to set
	 */
	public void setFinacommEmail(java.lang.String finacommEmail) {
		this.finacommEmail = finacommEmail;
	}

	/**
	 * @return the busInfo
	 */
	public java.lang.String getBusInfo() {
		return busInfo;
	}

	/**
	 * @param busInfo
	 *            the busInfo to set
	 */
	public void setBusInfo(java.lang.String busInfo) {
		this.busInfo = busInfo;
	}

	/**
	 * @return the busArea
	 */
	public java.lang.String getBusArea() {
		return busArea;
	}

	/**
	 * @param busArea
	 *            the busArea to set
	 */
	public void setBusArea(java.lang.String busArea) {
		this.busArea = busArea;
	}

	/**
	 * @return the monaveTrans
	 */
	public java.lang.String getMonaveTrans() {
		return monaveTrans;
	}

	/**
	 * @param monaveTrans
	 *            the monaveTrans to set
	 */
	public void setMonaveTrans(java.lang.String monaveTrans) {
		this.monaveTrans = monaveTrans;
	}

	/**
	 * @return the sigaveTrans
	 */
	public java.lang.String getSigaveTrans() {
		return sigaveTrans;
	}

	/**
	 * @param sigaveTrans
	 *            the sigaveTrans to set
	 */
	public void setSigaveTrans(java.lang.String sigaveTrans) {
		this.sigaveTrans = sigaveTrans;
	}

	/**
	 * @return the contstartDate
	 */
	public java.lang.String getContstartDate() {
		return contstartDate;
	}

	/**
	 * @param contstartDate
	 *            the contstartDate to set
	 */
	public void setContstartDate(java.lang.String contstartDate) {
		this.contstartDate = contstartDate;
	}

	/**
	 * @return the contendDate
	 */
	public java.lang.String getContendDate() {
		return contendDate;
	}

	/**
	 * @param contendDate
	 *            the contendDate to set
	 */
	public void setContendDate(java.lang.String contendDate) {
		this.contendDate = contendDate;
	}

	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof TblMchtBaseInfTmpTmp))
			return false;
		else {
			TblMchtBaseInfTmpTmp tblMchtBaseInfTmp = (TblMchtBaseInfTmpTmp) obj;
			if (null == this.getMchtNo()
					|| null == tblMchtBaseInfTmp.getMchtNo())
				return false;
			else
				return (this.getMchtNo().equals(tblMchtBaseInfTmp.getMchtNo()));
		}
	}

	public int hashCode() {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getMchtNo())
				return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":"
						+ this.getMchtNo().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BaseTblMchtBaseInfTmpTmp [mchtNo=" + mchtNo + ", mchtNm="
				+ mchtNm + ", rislLvl=" + rislLvl + ", mchtLvl=" + mchtLvl
				+ ", mchtStatus=" + mchtStatus + ", manuAuthFlag="
				+ manuAuthFlag + ", partNum=" + partNum + ", discConsFlg="
				+ discConsFlg + ", discConsRebate=" + discConsRebate
				+ ", passFlag=" + passFlag + ", openDays=" + openDays
				+ ", sleepDays=" + sleepDays + ", mchtCnAbbr=" + mchtCnAbbr
				+ ", spellName=" + spellName + ", engName=" + engName
				+ ", mchtEnAbbr=" + mchtEnAbbr + ", areaNo=" + areaNo
				+ ", settleAreaNo=" + settleAreaNo + ", addr=" + addr
				+ ", homePage=" + homePage + ", mcc=" + mcc + ", tcc=" + tcc
				+ ", etpsAttr=" + etpsAttr + ", mngMchtId=" + mngMchtId
				+ ", mchtGrp=" + mchtGrp + ", mchtAttr=" + mchtAttr
				+ ", mchtGroupFlag=" + mchtGroupFlag + ", mchtGroupId="
				+ mchtGroupId + ", mchtEngNm=" + mchtEngNm + ", mchtEngAddr="
				+ mchtEngAddr + ", mchtEngCityName=" + mchtEngCityName
				+ ", saLimitAmt=" + saLimitAmt + ", saAction=" + saAction
				+ ", psamNum=" + psamNum + ", cdMacNum=" + cdMacNum
				+ ", posNum=" + posNum + ", connType=" + connType
				+ ", mchtMngMode=" + mchtMngMode + ", mchtFunction="
				+ mchtFunction + ", licenceNo=" + licenceNo
				+ ", licenceEndDate=" + licenceEndDate + ", bankLicenceNo="
				+ bankLicenceNo + ", busType=" + busType + ", faxNo=" + faxNo
				+ ", busAmt=" + busAmt + ", mchtCreLvl=" + mchtCreLvl
				+ ", contact=" + contact + ", postCode=" + postCode
				+ ", commEmail=" + commEmail + ", commMobil=" + commMobil
				+ ", commTel=" + commTel + ", manager=" + manager
				+ ", artifCertifTp=" + artifCertifTp + ", identityNo="
				+ identityNo + ", managerTel=" + managerTel + ", fax=" + fax
				+ ", electrofax=" + electrofax + ", regAddr=" + regAddr
				+ ", applyDate=" + applyDate + ", enableDate=" + enableDate
				+ ", preAudNm=" + preAudNm + ", confirmNm=" + confirmNm
				+ ", protocalId=" + protocalId + ", signInstId=" + signInstId
				+ ", netNm=" + netNm + ", agrBr=" + agrBr + ", netTel="
				+ netTel + ", prolDate=" + prolDate + ", prolTlr=" + prolTlr
				+ ", closeDate=" + closeDate + ", closeTlr=" + closeTlr
				+ ", mainTlr=" + mainTlr + ", checkTlr=" + checkTlr
				+ ", operNo=" + operNo + ", operNm=" + operNm + ", procFlag="
				+ procFlag + ", setCur=" + setCur + ", printInstId="
				+ printInstId + ", acqInstId=" + acqInstId + ", acqBkName="
				+ acqBkName + ", bankNo=" + bankNo + ", orgnNo=" + orgnNo
				+ ", subbrhNo=" + subbrhNo + ", subbrhNm=" + subbrhNm
				+ ", openTime=" + openTime + ", closeTime=" + closeTime
				+ ", visActFlg=" + visActFlg + ", visMchtId=" + visMchtId
				+ ", mstActFlg=" + mstActFlg + ", mstMchtId=" + mstMchtId
				+ ", amxActFlg=" + amxActFlg + ", amxMchtId=" + amxMchtId
				+ ", dnrActFlg=" + dnrActFlg + ", dnrMchtId=" + dnrMchtId
				+ ", jcbActFlg=" + jcbActFlg + ", jcbMchtId=" + jcbMchtId
				+ ", cupMchtFlg=" + cupMchtFlg + ", debMchtFlg=" + debMchtFlg
				+ ", creMchtFlg=" + creMchtFlg + ", cdcMchtFlg=" + cdcMchtFlg
				+ ", updOprId=" + updOprId + ", crtOprId=" + crtOprId
				+ ", reserved=" + reserved + ", recUpdTs=" + recUpdTs
				+ ", recCrtTs=" + recCrtTs + ", compNm=" + compNm
				+ ", compaddr=" + compaddr + ", finacontact=" + finacontact
				+ ", finacommTel=" + finacommTel + ", finacommEmail="
				+ finacommEmail + ", busInfo=" + busInfo + ", busArea="
				+ busArea + ", monaveTrans=" + monaveTrans + ", sigaveTrans="
				+ sigaveTrans + ", contstartDate=" + contstartDate
				+ ", contendDate=" + contendDate + "]";
	}
}