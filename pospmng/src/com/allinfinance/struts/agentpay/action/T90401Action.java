package com.allinfinance.struts.agentpay.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.allinfinance.bo.agentpay.T90401BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.ErrorCode;
import com.allinfinance.common.StringUtil;
import com.allinfinance.po.agentpay.TblMchtFund;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.BeanUtils;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T90401Action extends BaseAction {

	T90401BO t90401BO = (T90401BO) ContextUtil.getBean("T90401BO");

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {

		if ("add".equals(method)) {
			log("新增代收付商户资金信息。操作员编号：" + operator.getOprId());
			rspCode = add();
		} else if ("delete".equals(method)) {
			log("删除代收付商户资金信息。操作员编号：" + operator.getOprId());
			rspCode = delete();
		} else if ("update".equals(method)) {
			log("同步代收付商户资金信息。操作员编号：" + operator.getOprId());
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

		if (t90401BO.getMchtFund(mchtNo) != null) {
			return ErrorCode.T90401_01;
		}
		TblMchtFund tblMchtFund = new TblMchtFund();
		// 页面输入的值
		if(StringUtil.isEmpty(avlBal)){
			avlBal = "0";
		}
		if(StringUtil.isEmpty(frzAmt)){
			frzAmt = "0";
		}
		if(!StringUtil.isNumber(avlBal)){
			return "可用余额输入有误！";
		}
		if(!StringUtil.isNumber(frzAmt)){
			return "冻结金额输入有误！";
		}
		bal = String.valueOf(Float.valueOf(avlBal)+Float.valueOf(frzAmt));
		tblMchtFund.setMchtNo(mchtNo);
		tblMchtFund.setBal(bal);
		tblMchtFund.setAvlBal(avlBal);
		tblMchtFund.setFrzAmt(frzAmt);
		tblMchtFund.setLstUpdTlr(operator.getOprId());
		tblMchtFund.setLstUpdTime(CommonFunction.getCurrentDateTime());
		tblMchtFund.setCreateTime(CommonFunction.getCurrentDateTime());

		t90401BO.add(tblMchtFund);

		return Constants.SUCCESS_CODE;
	}

	/**
	 * delete city code
	 * 
	 * @return
	 */
	private String delete() {

		if (t90401BO.getMchtFund(mchtNo) == null) {
			return "没有找到要删除的商户资金信息";
		}

		t90401BO.delete(mchtNo);
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

		jsonBean.parseJSONArrayData(getMchtFundList());

		int len = jsonBean.getArray().size();

		TblMchtFund tblMchtFund = null;

		List<TblMchtFund> MchtFundList = new ArrayList<TblMchtFund>(len);

		for (int i = 0; i < len; i++) {
			tblMchtFund = new TblMchtFund();
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			BeanUtils.setObjectWithPropertiesValue(tblMchtFund, jsonBean, false);
			if(StringUtil.isEmpty(tblMchtFund.getAvlBal())){
				tblMchtFund.setAvlBal("0");;
			}
			if(StringUtil.isEmpty(tblMchtFund.getFrzAmt())){
				tblMchtFund.setFrzAmt("0");
			}
			if(!StringUtil.isNumber(tblMchtFund.getAvlBal())){
				return "商户号："+tblMchtFund.getMchtNo()+"可用余额输入有误！";
			}
			if(!StringUtil.isNumber(tblMchtFund.getFrzAmt())){
				return "商户号："+tblMchtFund.getMchtNo()+"冻结金额输入有误！";
			}
			if(!StringUtil.isNumber(tblMchtFund.getBal())){
				return "商户号："+tblMchtFund.getMchtNo()+"账户余额有误！";
			}
			tblMchtFund.setLstUpdTlr(operator.getOprId());
			tblMchtFund.setLstUpdTime(CommonFunction.getCurrentDateTime());
			MchtFundList.add(tblMchtFund);
		}

		t90401BO.update(MchtFundList);

		return Constants.SUCCESS_CODE;
	}

	private String mchtNo;
	private String bal;
	private String avlBal;
	private String frzAmt;
	private String flag1;
	private String flag2;
	private String flag3;
	private String misc1;
	private String misc2;
	private String misc3;
	private String lstUpdTlr;
	private String lstUpdTime;
	private String createTime;
	private String mchtFundList;

	public T90401BO getT90401BO() {
		return t90401BO;
	}

	public void setT90401BO(T90401BO t90401bo) {
		t90401BO = t90401bo;
	}

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public String getBal() {
		return bal;
	}

	public void setBal(String bal) {
		this.bal = bal;
	}

	public String getAvlBal() {
		return avlBal;
	}

	public void setAvlBal(String avlBal) {
		this.avlBal = avlBal;
	}

	public String getFrzAmt() {
		return frzAmt;
	}

	public void setFrzAmt(String frzAmt) {
		this.frzAmt = frzAmt;
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

	public String getMchtFundList() {
		return mchtFundList;
	}

	public void setMchtFundList(String mchtFundList) {
		this.mchtFundList = mchtFundList;
	}

}
