package com.allinfinance.struts.mchnt.action;


import com.allinfinance.bo.mchnt.T20503BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.ErrorCode;
import com.allinfinance.po.mchnt.TblFirstMchtInf;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

/**
 * Title:一级商户信息维护
 */
@SuppressWarnings("serial")
public class T20503Action_old extends BaseAction {
	
	T20503BO t20503BO = (T20503BO) ContextUtil.getBean("T20503BO");
	
	@Override
	protected String subExecute() throws Exception {
		if("add".equals(method)) {
			log("新增一级商户信息。操作员编号：" + operator.getOprId());
			rspCode = add();
		} else if("delete".equals(method)) {
			log("删除一级商户信息。操作员编号：" + operator.getOprId());
			rspCode = delete();
		}
//		else if("update".equals(method)) {
//			log("同步一级商户信息。操作员编号：" + operator.getOprId());
//			rspCode = update();
//		}
		return rspCode;
	}
	
	private String add() throws Exception {
		TblFirstMchtInf tblFirstMchtInf = new TblFirstMchtInf();
		if(t20503BO.get(firstMchtCd) != null){
			return ErrorCode.T20503_01;
		}
		tblFirstMchtInf.setFirstMchtCd(getFirstMchtCd());
		tblFirstMchtInf.setFirstMchtNm(getFirstMchtNm());
		tblFirstMchtInf.setFirstTermId(getFirstTermId());
		tblFirstMchtInf.setAcqInstId(getAcqInstId());
		tblFirstMchtInf.setMchtTp(getMchtTp());
//		tblFirstMchtInf.setFeeValue(Double.parseDouble(getFeeValue()));
		tblFirstMchtInf.setFeeValue(getFeeValue());
		tblFirstMchtInf.setReserved(getReserved());
		tblFirstMchtInf.setReserved1(getReserved1());
		tblFirstMchtInf.setReserved2(getReserved2());
		t20503BO.add(tblFirstMchtInf);
		log("添加一级商户信息成功。操作员编号：" + operator.getOprId()
				+ "，一級商戶编号：" + getFirstMchtCd());
		return Constants.SUCCESS_CODE;
	}

	private String delete() {
		if(t20503BO.get(firstMchtCd) == null) {
			return "没有找到要删除的一级商户信息";
		}
		t20503BO.delete(firstMchtCd);
		return Constants.SUCCESS_CODE;
	}

	
	private String firstMchtCd;
	private String firstMchtNm;
	private String firstTermId;
	private String acqInstId;
	private String mchtTp;
	private Double feeValue;	
	private String reserved;
	private String reserved1;
	private String reserved2;
	
	public String getFirstMchtCd() {
		return firstMchtCd;
	}

	public void setFirstMchtCd(String firstMchtCd) {
		this.firstMchtCd = firstMchtCd;
	}

	public String getFirstMchtNm() {
		return firstMchtNm;
	}

	public void setFirstMchtNm(String firstMchtNm) {
		this.firstMchtNm = firstMchtNm;
	}

	public String getFirstTermId() {
		return firstTermId;
	}

	public void setFirstTermId(String firstTermId) {
		this.firstTermId = firstTermId;
	}
	
	public String getAcqInstId() {
		return acqInstId;
	}

	public void setAcqInstId(String acqInstId) {
		this.acqInstId = acqInstId;
	}

	public T20503BO getT20503BO() {
		return t20503BO;
	}

	public void setT20503BO(T20503BO t20503bo) {
		t20503BO = t20503bo;
	}

	public String getMchtTp() {
		return mchtTp;
	}

	public void setMchtTp(String mchtTp) {
		this.mchtTp = mchtTp;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	public Double getFeeValue() {
		return feeValue;
	}

	public void setFeeValue(Double feeValue) {
		this.feeValue = feeValue;
	}

	/**
	 * @return the reserved2
	 */
	public String getReserved2() {
		return reserved2;
	}

	/**
	 * @param reserved2 the reserved2 to set
	 */
	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	/**
	 * @return the reserved1
	 */
	public String getReserved1() {
		return reserved1;
	}

	/**
	 * @param reserved1 the reserved1 to set
	 */
	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	
	
}