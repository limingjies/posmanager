package com.allinfinance.struts.agentpay.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.allinfinance.bo.agentpay.T90301BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.ErrorCode;
import com.allinfinance.po.agentpay.TblMchtInfo;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.BeanUtils;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T90301Action extends BaseAction {

	T90301BO t90301BO = (T90301BO) ContextUtil.getBean("T90301BO");

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {

		if ("add".equals(method)) {
			log("新增代收付商户信息信息。操作员编号：" + operator.getOprId());
			rspCode = add();
		} else if ("delete".equals(method)) {
			log("删除代收付商户信息信息。操作员编号：" + operator.getOprId());
			rspCode = delete();
		} else if ("update".equals(method)) {
			log("同步代收付商户信息信息。操作员编号：" + operator.getOprId());
			rspCode = update();
		}

		return rspCode;
	}

	/**
	 * add city code
	 * 
	 * @return
	 */
	private String add() {

		if (t90301BO.getMchtInfo(mchtNo) != null) {
			return ErrorCode.T90301_01;
		}
		TblMchtInfo tblMchtInfo = new TblMchtInfo();
		//页面输入的值
		tblMchtInfo.setMchtNo(mchtNo);
		tblMchtInfo.setMchtName(mchtName);
		tblMchtInfo.setStat(stat);
		tblMchtInfo.setMchtComCode(mchtComCode);
		tblMchtInfo.setOpnBankNo(opnBankNo);
		tblMchtInfo.setAcctNo(acctNo);
		tblMchtInfo.setName(name);
		tblMchtInfo.setFlag(flag);
		tblMchtInfo.setRislLvl(rislLvl);
		tblMchtInfo.setMchtLvl(mchtLvl);
		tblMchtInfo.setMchtGrp(mchtGrp);
		tblMchtInfo.setAreaNo(areaNo);
		tblMchtInfo.setAddr(addr);
		tblMchtInfo.setZipCode(zipCode);
		tblMchtInfo.setTel(tel);
		tblMchtInfo.setMail(mail);
		//自动添加的值
		tblMchtInfo.setEntryTlr(operator.getOprId());
		tblMchtInfo.setLstUpdTlr(operator.getOprId());
		tblMchtInfo.setLstUpdTime(CommonFunction.getCurrentDateTime());
		tblMchtInfo.setCreateTime(CommonFunction.getCurrentDateTime());
		
		t90301BO.add(tblMchtInfo);

		return Constants.SUCCESS_CODE;
	}

	/**
	 * delete city code
	 * 
	 * @return
	 */
	private String delete() {

		if (t90301BO.getMchtInfo(mchtNo) == null) {
			return "没有找到要删除的商户信息信息";
		}

		t90301BO.delete(mchtNo);
		return Constants.SUCCESS_CODE;
	}

	/**
	 * update city code
	 * 
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private String update() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {

		jsonBean.parseJSONArrayData(getMchtList());

		int len = jsonBean.getArray().size();

		TblMchtInfo tblMchtInfo = null;

		List<TblMchtInfo> MchtInfoList = new ArrayList<TblMchtInfo>(len);

		for (int i = 0; i < len; i++) {
			tblMchtInfo = new TblMchtInfo();
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			BeanUtils.setObjectWithPropertiesValue(tblMchtInfo, jsonBean, false);
			MchtInfoList.add(tblMchtInfo);
		}

		t90301BO.update(MchtInfoList);

		return Constants.SUCCESS_CODE;
	}

	private String mchtNo;
	private String mchtName;
	private String stat;
	private String mchtComCode;
	private String opnBankNo;
	private String acctNo;
	private String name;
	private String flag;
	private String rislLvl;
	private String mchtLvl;
	private String mchtGrp;
	private String areaNo;
	private String addr;
	private String zipCode;
	private String tel;
	private String mail;
	
	private String mchtList;

	public T90301BO getT90301BO() {
		return t90301BO;
	}

	public void setT90301BO(T90301BO t90301bo) {
		t90301BO = t90301bo;
	}

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getMchtName() {
		return mchtName;
	}

	public void setMchtName(String mchtName) {
		this.mchtName = mchtName;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getMchtComCode() {
		return mchtComCode;
	}

	public void setMchtComCode(String mchtComCode) {
		this.mchtComCode = mchtComCode;
	}

	public String getOpnBankNo() {
		return opnBankNo;
	}

	public void setOpnBankNo(String opnBankNo) {
		this.opnBankNo = opnBankNo;
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

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getRislLvl() {
		return rislLvl;
	}

	public void setRislLvl(String rislLvl) {
		this.rislLvl = rislLvl;
	}

	public String getMchtLvl() {
		return mchtLvl;
	}

	public void setMchtLvl(String mchtLvl) {
		this.mchtLvl = mchtLvl;
	}

	public String getMchtGrp() {
		return mchtGrp;
	}

	public void setMchtGrp(String mchtGrp) {
		this.mchtGrp = mchtGrp;
	}

	public String getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMchtList() {
		return mchtList;
	}

	public void setMchtList(String mchtList) {
		this.mchtList = mchtList;
	}

}
