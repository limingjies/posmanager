package com.allinfinance.po.mchnt;

/**
 * TblMchtBaseInfTmpHist entity. @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class TblMchtBaseInfTmpHist implements java.io.Serializable {

	// Fields

	private TblMchtBaseInfTmpHistPK id;
	private String mchtNm;
	private String rislLvl;
	private String mchtLvl;
	private String mchtStatus;
	private String manuAuthFlag;
	private String partNum;
	private String discConsFlg;
	private String discConsRebate;
	private String passFlag;
	private String openDays;
	private String sleepDays;
	private String mchtCnAbbr;
	private String spellName;
	private String engName;
	private String mchtEnAbbr;
	private String areaNo;
	private String settleAreaNo;
	private String addr;
	private String homePage;
	private String mcc;
	private String tcc;
	private String etpsAttr;
	private String mngMchtId;
	private String mchtGrp;
	private String mchtAttr;
	private String mchtGroupFlag;
	private String mchtGroupId;
	private String mchtEngNm;
	private String mchtEngAddr;
	private String mchtEngCityName;
	private String saLimitAmt;
	private String saAction;
	private String psamNum;
	private String cdMacNum;
	private String posNum;
	private String connType;
	private String mchtMngMode;
	private String mchtFunction;
	private String licenceNo;
	private String licenceEndDate;
	private String bankLicenceNo;
	private String busType;
	private String faxNo;
	private String busAmt;
	private String mchtCreLvl;
	private String contact;
	private String postCode;
	private String commEmail;
	private String commMobil;
	private String commTel;
	private String manager;
	private String artifCertifTp;
	private String identityNo;
	private String managerTel;
	private String fax;
	private String electrofax;
	private String regAddr;
	private String applyDate;
	private String enableDate;
	private String preAudNm;
	private String confirmNm;
	private String protocalId;
	private String signInstId;
	private String netNm;
	private String agrBr;
	private String netTel;
	private String prolDate;
	private String prolTlr;
	private String closeDate;
	private String closeTlr;
	private String mainTlr;
	private String checkTlr;
	private String operNo;
	private String operNm;
	private String procFlag;
	private String setCur;
	private String printInstId;
	private String acqInstId;
	private String acqBkName;
	private String bankNo;
	private String orgnNo;
	private String subbrhNo;
	private String subbrhNm;
	private String openTime;
	private String closeTime;
	private String visActFlg;
	private String visMchtId;
	private String mstActFlg;
	private String mstMchtId;
	private String amxActFlg;
	private String amxMchtId;
	private String dnrActFlg;
	private String dnrMchtId;
	private String jcbActFlg;
	private String jcbMchtId;
	private String cupMchtFlg;
	private String debMchtFlg;
	private String creMchtFlg;
	private String cdcMchtFlg;
	private String reserved;
	private String updOprId;
	private String crtOprId;
	private String recUpdTs;
	private String recCrtTs;

	public String compNm;
	public String compaddr;
	public String finacontact;
	public String finacommTel;
	public String finacommEmail;
	public String busInfo;
	public String busArea;
	public String monaveTrans;
	public String sigaveTrans;
	public String contstartDate;
	public String contendDate;
	public java.lang.String addrProvince;//装机地址省mcht_bak1
	public java.lang.String poundage;//手续费方式mcht_bak2
	public java.lang.String cashFlag;//提现开通mcht_bak4

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
	// Constructors

	/**
	 * @return the addrProvince
	 */
	public java.lang.String getAddrProvince() {
		return addrProvince;
	}

	/**
	 * @param addrProvince the addrProvince to set
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
	 * @param poundage the poundage to set
	 */
	public void setPoundage(java.lang.String poundage) {
		this.poundage = poundage;
	}

	/** default constructor */
	public TblMchtBaseInfTmpHist() {
	}

	/** minimal constructor */
	public TblMchtBaseInfTmpHist(TblMchtBaseInfTmpHistPK id, String mchtNm,
			String rislLvl, String mchtLvl, String mchtStatus,
			String manuAuthFlag, String discConsFlg, String passFlag,
			String addr, String mcc, String mchtGroupFlag, String saAction,
			String licenceNo, String faxNo, String contact, String manager,
			String identityNo, String managerTel, String cupMchtFlg,
			String debMchtFlg, String creMchtFlg, String cdcMchtFlg,
			String recUpdTs, String recCrtTs) {
		this.id = id;
		this.mchtNm = mchtNm;
		this.rislLvl = rislLvl;
		this.mchtLvl = mchtLvl;
		this.mchtStatus = mchtStatus;
		this.manuAuthFlag = manuAuthFlag;
		this.discConsFlg = discConsFlg;
		this.passFlag = passFlag;
		this.addr = addr;
		this.mcc = mcc;
		this.mchtGroupFlag = mchtGroupFlag;
		this.saAction = saAction;
		this.licenceNo = licenceNo;
		this.faxNo = faxNo;
		this.contact = contact;
		this.manager = manager;
		this.identityNo = identityNo;
		this.managerTel = managerTel;
		this.cupMchtFlg = cupMchtFlg;
		this.debMchtFlg = debMchtFlg;
		this.creMchtFlg = creMchtFlg;
		this.cdcMchtFlg = cdcMchtFlg;
		this.recUpdTs = recUpdTs;
		this.recCrtTs = recCrtTs;
	}

	/** full constructor */
	public TblMchtBaseInfTmpHist(TblMchtBaseInfTmpHistPK id, String mchtNm,
			String rislLvl, String mchtLvl, String mchtStatus,
			String manuAuthFlag, String partNum, String discConsFlg,
			String discConsRebate, String passFlag, String openDays,
			String sleepDays, String mchtCnAbbr, String spellName,
			String engName, String mchtEnAbbr, String areaNo,
			String settleAreaNo, String addr, String homePage, String mcc,
			String tcc, String etpsAttr, String mngMchtId, String mchtGrp,
			String mchtAttr, String mchtGroupFlag, String mchtGroupId,
			String mchtEngNm, String mchtEngAddr, String mchtEngCityName,
			String saLimitAmt, String saAction, String psamNum,
			String cdMacNum, String posNum, String connType,
			String mchtMngMode, String mchtFunction, String licenceNo,
			String licenceEndDate, String bankLicenceNo, String busType,
			String faxNo, String busAmt, String mchtCreLvl, String contact,
			String postCode, String commEmail, String commMobil,
			String commTel, String manager, String artifCertifTp,
			String identityNo, String managerTel, String fax,
			String electrofax, String regAddr, String applyDate,
			String enableDate, String preAudNm, String confirmNm,
			String protocalId, String signInstId, String netNm, String agrBr,
			String netTel, String prolDate, String prolTlr, String closeDate,
			String closeTlr, String mainTlr, String checkTlr, String operNo,
			String operNm, String procFlag, String setCur, String printInstId,
			String acqInstId, String acqBkName, String bankNo, String orgnNo,
			String subbrhNo, String subbrhNm, String openTime,
			String closeTime, String visActFlg, String visMchtId,
			String mstActFlg, String mstMchtId, String amxActFlg,
			String amxMchtId, String dnrActFlg, String dnrMchtId,
			String jcbActFlg, String jcbMchtId, String cupMchtFlg,
			String debMchtFlg, String creMchtFlg, String cdcMchtFlg,
			String reserved, String updOprId, String crtOprId, String recUpdTs,
			String recCrtTs, String compNm, String compaddr,
			String finacontact, String finacommTel, String finacommEmail,
			String busInfo, String busArea, String monaveTrans,
			String sigaveTrans, String contstartDate, String contendDate) {
		this.id = id;
		this.mchtNm = mchtNm;
		this.rislLvl = rislLvl;
		this.mchtLvl = mchtLvl;
		this.mchtStatus = mchtStatus;
		this.manuAuthFlag = manuAuthFlag;
		this.partNum = partNum;
		this.discConsFlg = discConsFlg;
		this.discConsRebate = discConsRebate;
		this.passFlag = passFlag;
		this.openDays = openDays;
		this.sleepDays = sleepDays;
		this.mchtCnAbbr = mchtCnAbbr;
		this.spellName = spellName;
		this.engName = engName;
		this.mchtEnAbbr = mchtEnAbbr;
		this.areaNo = areaNo;
		this.settleAreaNo = settleAreaNo;
		this.addr = addr;
		this.homePage = homePage;
		this.mcc = mcc;
		this.tcc = tcc;
		this.etpsAttr = etpsAttr;
		this.mngMchtId = mngMchtId;
		this.mchtGrp = mchtGrp;
		this.mchtAttr = mchtAttr;
		this.mchtGroupFlag = mchtGroupFlag;
		this.mchtGroupId = mchtGroupId;
		this.mchtEngNm = mchtEngNm;
		this.mchtEngAddr = mchtEngAddr;
		this.mchtEngCityName = mchtEngCityName;
		this.saLimitAmt = saLimitAmt;
		this.saAction = saAction;
		this.psamNum = psamNum;
		this.cdMacNum = cdMacNum;
		this.posNum = posNum;
		this.connType = connType;
		this.mchtMngMode = mchtMngMode;
		this.mchtFunction = mchtFunction;
		this.licenceNo = licenceNo;
		this.licenceEndDate = licenceEndDate;
		this.bankLicenceNo = bankLicenceNo;
		this.busType = busType;
		this.faxNo = faxNo;
		this.busAmt = busAmt;
		this.mchtCreLvl = mchtCreLvl;
		this.contact = contact;
		this.postCode = postCode;
		this.commEmail = commEmail;
		this.commMobil = commMobil;
		this.commTel = commTel;
		this.manager = manager;
		this.artifCertifTp = artifCertifTp;
		this.identityNo = identityNo;
		this.managerTel = managerTel;
		this.fax = fax;
		this.electrofax = electrofax;
		this.regAddr = regAddr;
		this.applyDate = applyDate;
		this.enableDate = enableDate;
		this.preAudNm = preAudNm;
		this.confirmNm = confirmNm;
		this.protocalId = protocalId;
		this.signInstId = signInstId;
		this.netNm = netNm;
		this.agrBr = agrBr;
		this.netTel = netTel;
		this.prolDate = prolDate;
		this.prolTlr = prolTlr;
		this.closeDate = closeDate;
		this.closeTlr = closeTlr;
		this.mainTlr = mainTlr;
		this.checkTlr = checkTlr;
		this.operNo = operNo;
		this.operNm = operNm;
		this.procFlag = procFlag;
		this.setCur = setCur;
		this.printInstId = printInstId;
		this.acqInstId = acqInstId;
		this.acqBkName = acqBkName;
		this.bankNo = bankNo;
		this.orgnNo = orgnNo;
		this.subbrhNo = subbrhNo;
		this.subbrhNm = subbrhNm;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.visActFlg = visActFlg;
		this.visMchtId = visMchtId;
		this.mstActFlg = mstActFlg;
		this.mstMchtId = mstMchtId;
		this.amxActFlg = amxActFlg;
		this.amxMchtId = amxMchtId;
		this.dnrActFlg = dnrActFlg;
		this.dnrMchtId = dnrMchtId;
		this.jcbActFlg = jcbActFlg;
		this.jcbMchtId = jcbMchtId;
		this.cupMchtFlg = cupMchtFlg;
		this.debMchtFlg = debMchtFlg;
		this.creMchtFlg = creMchtFlg;
		this.cdcMchtFlg = cdcMchtFlg;
		this.reserved = reserved;
		this.updOprId = updOprId;
		this.crtOprId = crtOprId;
		this.recUpdTs = recUpdTs;
		this.recCrtTs = recCrtTs;

		this.compNm = compNm;
		this.compaddr = compaddr;
		this.finacontact = finacontact;
		this.finacommTel = finacommTel;
		this.finacommEmail = finacommEmail;
		this.busInfo = busInfo;
		this.busArea = busArea;
		this.monaveTrans = monaveTrans;
		this.sigaveTrans = sigaveTrans;
		this.contstartDate = contstartDate;
		this.contendDate = contendDate;

	}

	// Property accessors

	public TblMchtBaseInfTmpHistPK getId() {
		return this.id;
	}

	public void setId(TblMchtBaseInfTmpHistPK id) {
		this.id = id;
	}

	public String getMchtNm() {
		return this.mchtNm;
	}

	public void setMchtNm(String mchtNm) {
		this.mchtNm = mchtNm;
	}

	public String getRislLvl() {
		return this.rislLvl;
	}

	public void setRislLvl(String rislLvl) {
		this.rislLvl = rislLvl;
	}

	public String getMchtLvl() {
		return this.mchtLvl;
	}

	public void setMchtLvl(String mchtLvl) {
		this.mchtLvl = mchtLvl;
	}

	public String getMchtStatus() {
		return this.mchtStatus;
	}

	public void setMchtStatus(String mchtStatus) {
		this.mchtStatus = mchtStatus;
	}

	public String getManuAuthFlag() {
		return this.manuAuthFlag;
	}

	public void setManuAuthFlag(String manuAuthFlag) {
		this.manuAuthFlag = manuAuthFlag;
	}

	public String getPartNum() {
		return this.partNum;
	}

	public void setPartNum(String partNum) {
		this.partNum = partNum;
	}

	public String getDiscConsFlg() {
		return this.discConsFlg;
	}

	public void setDiscConsFlg(String discConsFlg) {
		this.discConsFlg = discConsFlg;
	}

	public String getDiscConsRebate() {
		return this.discConsRebate;
	}

	public void setDiscConsRebate(String discConsRebate) {
		this.discConsRebate = discConsRebate;
	}

	public String getPassFlag() {
		return this.passFlag;
	}

	public void setPassFlag(String passFlag) {
		this.passFlag = passFlag;
	}

	public String getOpenDays() {
		return this.openDays;
	}

	public void setOpenDays(String openDays) {
		this.openDays = openDays;
	}

	public String getSleepDays() {
		return this.sleepDays;
	}

	public void setSleepDays(String sleepDays) {
		this.sleepDays = sleepDays;
	}

	public String getMchtCnAbbr() {
		return this.mchtCnAbbr;
	}

	public void setMchtCnAbbr(String mchtCnAbbr) {
		this.mchtCnAbbr = mchtCnAbbr;
	}

	public String getSpellName() {
		return this.spellName;
	}

	public void setSpellName(String spellName) {
		this.spellName = spellName;
	}

	public String getEngName() {
		return this.engName;
	}

	public void setEngName(String engName) {
		this.engName = engName;
	}

	public String getMchtEnAbbr() {
		return this.mchtEnAbbr;
	}

	public void setMchtEnAbbr(String mchtEnAbbr) {
		this.mchtEnAbbr = mchtEnAbbr;
	}

	public String getAreaNo() {
		return this.areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	public String getSettleAreaNo() {
		return this.settleAreaNo;
	}

	public void setSettleAreaNo(String settleAreaNo) {
		this.settleAreaNo = settleAreaNo;
	}

	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getHomePage() {
		return this.homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public String getMcc() {
		return this.mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public String getTcc() {
		return this.tcc;
	}

	public void setTcc(String tcc) {
		this.tcc = tcc;
	}

	public String getEtpsAttr() {
		return this.etpsAttr;
	}

	public void setEtpsAttr(String etpsAttr) {
		this.etpsAttr = etpsAttr;
	}

	public String getMngMchtId() {
		return this.mngMchtId;
	}

	public void setMngMchtId(String mngMchtId) {
		this.mngMchtId = mngMchtId;
	}

	public String getMchtGrp() {
		return this.mchtGrp;
	}

	public void setMchtGrp(String mchtGrp) {
		this.mchtGrp = mchtGrp;
	}

	public String getMchtAttr() {
		return this.mchtAttr;
	}

	public void setMchtAttr(String mchtAttr) {
		this.mchtAttr = mchtAttr;
	}

	public String getMchtGroupFlag() {
		return this.mchtGroupFlag;
	}

	public void setMchtGroupFlag(String mchtGroupFlag) {
		this.mchtGroupFlag = mchtGroupFlag;
	}

	public String getMchtGroupId() {
		return this.mchtGroupId;
	}

	public void setMchtGroupId(String mchtGroupId) {
		this.mchtGroupId = mchtGroupId;
	}

	public String getMchtEngNm() {
		return this.mchtEngNm;
	}

	public void setMchtEngNm(String mchtEngNm) {
		this.mchtEngNm = mchtEngNm;
	}

	public String getMchtEngAddr() {
		return this.mchtEngAddr;
	}

	public void setMchtEngAddr(String mchtEngAddr) {
		this.mchtEngAddr = mchtEngAddr;
	}

	public String getMchtEngCityName() {
		return this.mchtEngCityName;
	}

	public void setMchtEngCityName(String mchtEngCityName) {
		this.mchtEngCityName = mchtEngCityName;
	}

	public String getSaLimitAmt() {
		return this.saLimitAmt;
	}

	public void setSaLimitAmt(String saLimitAmt) {
		this.saLimitAmt = saLimitAmt;
	}

	public String getSaAction() {
		return this.saAction;
	}

	public void setSaAction(String saAction) {
		this.saAction = saAction;
	}

	public String getPsamNum() {
		return this.psamNum;
	}

	public void setPsamNum(String psamNum) {
		this.psamNum = psamNum;
	}

	public String getCdMacNum() {
		return this.cdMacNum;
	}

	public void setCdMacNum(String cdMacNum) {
		this.cdMacNum = cdMacNum;
	}

	public String getPosNum() {
		return this.posNum;
	}

	public void setPosNum(String posNum) {
		this.posNum = posNum;
	}

	public String getConnType() {
		return this.connType;
	}

	public void setConnType(String connType) {
		this.connType = connType;
	}

	public String getMchtMngMode() {
		return this.mchtMngMode;
	}

	public void setMchtMngMode(String mchtMngMode) {
		this.mchtMngMode = mchtMngMode;
	}

	public String getMchtFunction() {
		return this.mchtFunction;
	}

	public void setMchtFunction(String mchtFunction) {
		this.mchtFunction = mchtFunction;
	}

	public String getLicenceNo() {
		return this.licenceNo;
	}

	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}

	public String getLicenceEndDate() {
		return this.licenceEndDate;
	}

	public void setLicenceEndDate(String licenceEndDate) {
		this.licenceEndDate = licenceEndDate;
	}

	public String getBankLicenceNo() {
		return this.bankLicenceNo;
	}

	public void setBankLicenceNo(String bankLicenceNo) {
		this.bankLicenceNo = bankLicenceNo;
	}

	public String getBusType() {
		return this.busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public String getFaxNo() {
		return this.faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getBusAmt() {
		return this.busAmt;
	}

	public void setBusAmt(String busAmt) {
		this.busAmt = busAmt;
	}

	public String getMchtCreLvl() {
		return this.mchtCreLvl;
	}

	public void setMchtCreLvl(String mchtCreLvl) {
		this.mchtCreLvl = mchtCreLvl;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCommEmail() {
		return this.commEmail;
	}

	public void setCommEmail(String commEmail) {
		this.commEmail = commEmail;
	}

	public String getCommMobil() {
		return this.commMobil;
	}

	public void setCommMobil(String commMobil) {
		this.commMobil = commMobil;
	}

	public String getCommTel() {
		return this.commTel;
	}

	public void setCommTel(String commTel) {
		this.commTel = commTel;
	}

	public String getManager() {
		return this.manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getArtifCertifTp() {
		return this.artifCertifTp;
	}

	public void setArtifCertifTp(String artifCertifTp) {
		this.artifCertifTp = artifCertifTp;
	}

	public String getIdentityNo() {
		return this.identityNo;
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}

	public String getManagerTel() {
		return this.managerTel;
	}

	public void setManagerTel(String managerTel) {
		this.managerTel = managerTel;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getElectrofax() {
		return this.electrofax;
	}

	public void setElectrofax(String electrofax) {
		this.electrofax = electrofax;
	}

	public String getRegAddr() {
		return this.regAddr;
	}

	public void setRegAddr(String regAddr) {
		this.regAddr = regAddr;
	}

	public String getApplyDate() {
		return this.applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getEnableDate() {
		return this.enableDate;
	}

	public void setEnableDate(String enableDate) {
		this.enableDate = enableDate;
	}

	public String getPreAudNm() {
		return this.preAudNm;
	}

	public void setPreAudNm(String preAudNm) {
		this.preAudNm = preAudNm;
	}

	public String getConfirmNm() {
		return this.confirmNm;
	}

	public void setConfirmNm(String confirmNm) {
		this.confirmNm = confirmNm;
	}

	public String getProtocalId() {
		return this.protocalId;
	}

	public void setProtocalId(String protocalId) {
		this.protocalId = protocalId;
	}

	public String getSignInstId() {
		return this.signInstId;
	}

	public void setSignInstId(String signInstId) {
		this.signInstId = signInstId;
	}

	public String getNetNm() {
		return this.netNm;
	}

	public void setNetNm(String netNm) {
		this.netNm = netNm;
	}

	public String getAgrBr() {
		return this.agrBr;
	}

	public void setAgrBr(String agrBr) {
		this.agrBr = agrBr;
	}

	public String getNetTel() {
		return this.netTel;
	}

	public void setNetTel(String netTel) {
		this.netTel = netTel;
	}

	public String getProlDate() {
		return this.prolDate;
	}

	public void setProlDate(String prolDate) {
		this.prolDate = prolDate;
	}

	public String getProlTlr() {
		return this.prolTlr;
	}

	public void setProlTlr(String prolTlr) {
		this.prolTlr = prolTlr;
	}

	public String getCloseDate() {
		return this.closeDate;
	}

	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}

	public String getCloseTlr() {
		return this.closeTlr;
	}

	public void setCloseTlr(String closeTlr) {
		this.closeTlr = closeTlr;
	}

	public String getMainTlr() {
		return this.mainTlr;
	}

	public void setMainTlr(String mainTlr) {
		this.mainTlr = mainTlr;
	}

	public String getCheckTlr() {
		return this.checkTlr;
	}

	public void setCheckTlr(String checkTlr) {
		this.checkTlr = checkTlr;
	}

	public String getOperNo() {
		return this.operNo;
	}

	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}

	public String getOperNm() {
		return this.operNm;
	}

	public void setOperNm(String operNm) {
		this.operNm = operNm;
	}

	public String getProcFlag() {
		return this.procFlag;
	}

	public void setProcFlag(String procFlag) {
		this.procFlag = procFlag;
	}

	public String getSetCur() {
		return this.setCur;
	}

	public void setSetCur(String setCur) {
		this.setCur = setCur;
	}

	public String getPrintInstId() {
		return this.printInstId;
	}

	public void setPrintInstId(String printInstId) {
		this.printInstId = printInstId;
	}

	public String getAcqInstId() {
		return this.acqInstId;
	}

	public void setAcqInstId(String acqInstId) {
		this.acqInstId = acqInstId;
	}

	public String getAcqBkName() {
		return this.acqBkName;
	}

	public void setAcqBkName(String acqBkName) {
		this.acqBkName = acqBkName;
	}

	public String getBankNo() {
		return this.bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getOrgnNo() {
		return this.orgnNo;
	}

	public void setOrgnNo(String orgnNo) {
		this.orgnNo = orgnNo;
	}

	public String getSubbrhNo() {
		return this.subbrhNo;
	}

	public void setSubbrhNo(String subbrhNo) {
		this.subbrhNo = subbrhNo;
	}

	public String getSubbrhNm() {
		return this.subbrhNm;
	}

	public void setSubbrhNm(String subbrhNm) {
		this.subbrhNm = subbrhNm;
	}

	public String getOpenTime() {
		return this.openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getCloseTime() {
		return this.closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	public String getVisActFlg() {
		return this.visActFlg;
	}

	public void setVisActFlg(String visActFlg) {
		this.visActFlg = visActFlg;
	}

	public String getVisMchtId() {
		return this.visMchtId;
	}

	public void setVisMchtId(String visMchtId) {
		this.visMchtId = visMchtId;
	}

	public String getMstActFlg() {
		return this.mstActFlg;
	}

	public void setMstActFlg(String mstActFlg) {
		this.mstActFlg = mstActFlg;
	}

	public String getMstMchtId() {
		return this.mstMchtId;
	}

	public void setMstMchtId(String mstMchtId) {
		this.mstMchtId = mstMchtId;
	}

	public String getAmxActFlg() {
		return this.amxActFlg;
	}

	public void setAmxActFlg(String amxActFlg) {
		this.amxActFlg = amxActFlg;
	}

	public String getAmxMchtId() {
		return this.amxMchtId;
	}

	public void setAmxMchtId(String amxMchtId) {
		this.amxMchtId = amxMchtId;
	}

	public String getDnrActFlg() {
		return this.dnrActFlg;
	}

	public void setDnrActFlg(String dnrActFlg) {
		this.dnrActFlg = dnrActFlg;
	}

	public String getDnrMchtId() {
		return this.dnrMchtId;
	}

	public void setDnrMchtId(String dnrMchtId) {
		this.dnrMchtId = dnrMchtId;
	}

	public String getJcbActFlg() {
		return this.jcbActFlg;
	}

	public void setJcbActFlg(String jcbActFlg) {
		this.jcbActFlg = jcbActFlg;
	}

	public String getJcbMchtId() {
		return this.jcbMchtId;
	}

	public void setJcbMchtId(String jcbMchtId) {
		this.jcbMchtId = jcbMchtId;
	}

	public String getCupMchtFlg() {
		return this.cupMchtFlg;
	}

	public void setCupMchtFlg(String cupMchtFlg) {
		this.cupMchtFlg = cupMchtFlg;
	}

	public String getDebMchtFlg() {
		return this.debMchtFlg;
	}

	public void setDebMchtFlg(String debMchtFlg) {
		this.debMchtFlg = debMchtFlg;
	}

	public String getCreMchtFlg() {
		return this.creMchtFlg;
	}

	public void setCreMchtFlg(String creMchtFlg) {
		this.creMchtFlg = creMchtFlg;
	}

	public String getCdcMchtFlg() {
		return this.cdcMchtFlg;
	}

	public void setCdcMchtFlg(String cdcMchtFlg) {
		this.cdcMchtFlg = cdcMchtFlg;
	}

	public String getReserved() {
		return this.reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	public String getUpdOprId() {
		return this.updOprId;
	}

	public void setUpdOprId(String updOprId) {
		this.updOprId = updOprId;
	}

	public String getCrtOprId() {
		return this.crtOprId;
	}

	public void setCrtOprId(String crtOprId) {
		this.crtOprId = crtOprId;
	}

	public String getRecUpdTs() {
		return this.recUpdTs;
	}

	public void setRecUpdTs(String recUpdTs) {
		this.recUpdTs = recUpdTs;
	}

	public String getRecCrtTs() {
		return this.recCrtTs;
	}

	public void setRecCrtTs(String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	public String getCompNm() {
		return compNm;
	}

	public void setCompNm(String compNm) {
		this.compNm = compNm;
	}

	public String getCompaddr() {
		return compaddr;
	}

	public void setCompaddr(String compaddr) {
		this.compaddr = compaddr;
	}

	public String getFinacontact() {
		return finacontact;
	}

	public void setFinacontact(String finacontact) {
		this.finacontact = finacontact;
	}

	public String getFinacommTel() {
		return finacommTel;
	}

	public void setFinacommTel(String finacommTel) {
		this.finacommTel = finacommTel;
	}

	public String getFinacommEmail() {
		return finacommEmail;
	}

	public void setFinacommEmail(String finacommEmail) {
		this.finacommEmail = finacommEmail;
	}

	public String getBusInfo() {
		return busInfo;
	}

	public void setBusInfo(String busInfo) {
		this.busInfo = busInfo;
	}

	public String getBusArea() {
		return busArea;
	}

	public void setBusArea(String busArea) {
		this.busArea = busArea;
	}

	public String getMonaveTrans() {
		return monaveTrans;
	}

	public void setMonaveTrans(String monaveTrans) {
		this.monaveTrans = monaveTrans;
	}

	public String getSigaveTrans() {
		return sigaveTrans;
	}

	public void setSigaveTrans(String sigaveTrans) {
		this.sigaveTrans = sigaveTrans;
	}

	public String getContstartDate() {
		return contstartDate;
	}

	public void setContstartDate(String contstartDate) {
		this.contstartDate = contstartDate;
	}

	public String getContendDate() {
		return contendDate;
	}

	public void setContendDate(String contendDate) {
		this.contendDate = contendDate;
	}

}