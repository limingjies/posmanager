package com.allinfinance.po.agentpay;

import java.io.Serializable;
import java.math.BigDecimal;

public class TblRcvPackDtl implements Serializable {

	/**
	 * Tbl_Rcv_Pack_Dtl表映射对象
	 */
	private static final long serialVersionUID = 1L;
	private int hashCode = Integer.MIN_VALUE;

	private TblRcvPackDtlPK id;
	private String entDate;
	private String mchtNo;
	private String rcvDate;
	private String procStat;
	private String rcvBatch_id;
	private String custId;
	private String bankCode;
	private String acctNo;
	private String name;
	private String province;
	private String city;
	private String opnOrgName;
	private String acctType;
	private String custType;
	private BigDecimal amt;
	private String curCd;
	private String agtNo;
	private String agtCustNo;
	private String idType;
	private String idNo;
	private String mobile;
	private String custSelfNo;
	private String remark;
	private String rspCode;
	private String rspDesc;
	private String flag1;
	private String flag2;
	private String flag3;
	private String misc1;
	private String misc2;
	private String misc3;
	private String lstUpdTlr;
	private String lstUpdTime;
	private String createTime;

	public TblRcvPackDtl() {
		super();
	}

	public TblRcvPackDtl(TblRcvPackDtlPK id, String entDate, String mchtNo,
			String rcvDate, String procStat, String rcvBatch_id, String custId,
			String bankCode, String acctNo, String name, String province,
			String city, String opnOrgName, String acctType, String custType,
			BigDecimal amt, String curCd, String agtNo, String agtCustNo,
			String idType, String idNo, String mobile, String custSelfNo,
			String remark, String rspCode, String rspDesc, String flag1,
			String flag2, String flag3, String misc1, String misc2,
			String misc3, String lstUpdTlr, String lstUpdTime, String createTime) {
		this.id = id;
		this.entDate = entDate;
		this.mchtNo = mchtNo;
		this.rcvDate = rcvDate;
		this.procStat = procStat;
		this.rcvBatch_id = rcvBatch_id;
		this.custId = custId;
		this.bankCode = bankCode;
		this.acctNo = acctNo;
		this.name = name;
		this.province = province;
		this.city = city;
		this.opnOrgName = opnOrgName;
		this.acctType = acctType;
		this.custType = custType;
		this.amt = amt;
		this.curCd = curCd;
		this.agtNo = agtNo;
		this.agtCustNo = agtCustNo;
		this.idType = idType;
		this.idNo = idNo;
		this.mobile = mobile;
		this.custSelfNo = custSelfNo;
		this.remark = remark;
		this.rspCode = rspCode;
		this.rspDesc = rspDesc;
		this.flag1 = flag1;
		this.flag2 = flag2;
		this.flag3 = flag3;
		this.misc1 = misc1;
		this.misc2 = misc2;
		this.misc3 = misc3;
		this.lstUpdTlr = lstUpdTlr;
		this.lstUpdTime = lstUpdTime;
		this.createTime = createTime;
	}


	public TblRcvPackDtlPK getId() {
		return id;
	}

	public void setId(TblRcvPackDtlPK id) {
		this.id = id;
	}

	public String getEntDate() {
		return entDate;
	}

	public void setEntDate(String entDate) {
		this.entDate = entDate;
	}

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getRcvDate() {
		return rcvDate;
	}

	public void setRcvDate(String rcvDate) {
		this.rcvDate = rcvDate;
	}

	public String getProcStat() {
		return procStat;
	}

	public void setProcStat(String procStat) {
		this.procStat = procStat;
	}

	public String getRcvBatch_id() {
		return rcvBatch_id;
	}

	public void setRcvBatch_id(String rcvBatch_id) {
		this.rcvBatch_id = rcvBatch_id;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getOpnOrgName() {
		return opnOrgName;
	}

	public void setOpnOrgName(String opnOrgName) {
		this.opnOrgName = opnOrgName;
	}

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public String getCurCd() {
		return curCd;
	}

	public void setCurCd(String curCd) {
		this.curCd = curCd;
	}

	public String getAgtNo() {
		return agtNo;
	}

	public void setAgtNo(String agtNo) {
		this.agtNo = agtNo;
	}

	public String getAgtCustNo() {
		return agtCustNo;
	}

	public void setAgtCustNo(String agtCustNo) {
		this.agtCustNo = agtCustNo;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCustSelfNo() {
		return custSelfNo;
	}

	public void setCustSelfNo(String custSelfNo) {
		this.custSelfNo = custSelfNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRspCode() {
		return rspCode;
	}

	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}

	public String getRspDesc() {
		return rspDesc;
	}

	public void setRspDesc(String rspDesc) {
		this.rspDesc = rspDesc;
	}

	public String getFlag1() {
		return flag1;
	}

	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}

	public String getFlag2() {
		return flag2;
	}

	public void setFlag2(String flag2) {
		this.flag2 = flag2;
	}

	public String getFlag3() {
		return flag3;
	}

	public void setFlag3(String flag3) {
		this.flag3 = flag3;
	}

	public String getMisc1() {
		return misc1;
	}

	public void setMisc1(String misc1) {
		this.misc1 = misc1;
	}

	public String getMisc2() {
		return misc2;
	}

	public void setMisc2(String misc2) {
		this.misc2 = misc2;
	}

	public String getMisc3() {
		return misc3;
	}

	public void setMisc3(String misc3) {
		this.misc3 = misc3;
	}

	public String getLstUpdTlr() {
		return lstUpdTlr;
	}

	public void setLstUpdTlr(String lstUpdTlr) {
		this.lstUpdTlr = lstUpdTlr;
	}

	public String getLstUpdTime() {
		return lstUpdTime;
	}

	public void setLstUpdTime(String lstUpdTime) {
		this.lstUpdTime = lstUpdTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}