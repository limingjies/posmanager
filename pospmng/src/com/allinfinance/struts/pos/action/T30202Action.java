package com.allinfinance.struts.pos.action;

import java.util.ArrayList;
import java.util.List;

import com.allinfinance.bo.term.T3010BO;
import com.allinfinance.common.Constants;
import com.allinfinance.po.TblTermInfTmp;
import com.allinfinance.po.TblTermTmkLog;
import com.allinfinance.po.TblTermTmkLogPK;
import com.allinfinance.struts.pos.TblTermInfConstants;
import com.allinfinance.struts.pos.TblTermTmkLogConstants;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.SysCodeUtil;

public class T30202Action extends BaseAction {

	private static final long serialVersionUID = 4822110756831919797L;
	private String termId;
	private String recCrtTs;
	private String termNo;
	private String startDate;
	private String endDate;
	private String mchtCd;
	private String state;
	private String termBranch;
	private T3010BO t3010BO;
	
	/**
	 * @return the termNo
	 */
	public String getTermNo() {
		return termNo;
	}

	/**
	 * @param termNo the termNo to set
	 */
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the mchtCd
	 */
	public String getMchtCd() {
		return mchtCd;
	}

	/**
	 * @param mchtCd the mchtCd to set
	 */
	public void setMchtCd(String mchtCd) {
		this.mchtCd = mchtCd;
	}

	/**
	 * @return the termBranch
	 */
	public String getTermBranch() {
		return termBranch;
	}

	/**
	 * @param termBranch the termBranch to set
	 */
	public void setTermBranch(String termBranch) {
		this.termBranch = termBranch;
	}

	/**
	 * @return the termId
	 */
	public String getTermId() {
		return termId;
	}

	/**
	 * @param termId the termId to set
	 */
	public void setTermId(String termId) {
		this.termId = termId;
	}

	/**
	 * @return the recCrtTs
	 */
	public String getRecCrtTs() {
		return recCrtTs;
	}

	/**
	 * @param recCrtTs the recCrtTs to set
	 */
	public void setRecCrtTs(String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	/**
	 * @param t3010bo the t3010BO to set
	 */
	public void setT3010BO(T3010BO t3010bo) {
		t3010BO = t3010bo;
	}

	@Override
	protected String subExecute() throws Exception {
		String subTxnId = getSubTxnId();
		String batchNo = t3010BO.getBatchNo();
		if(subTxnId.equals("02"))
		{
			List list = t3010BO.qryTermInfo(termNo,state,operator, mchtCd, termBranch, startDate, endDate);
			if(list == null || list.isEmpty())
				return TblTermTmkLogConstants.T30202_03;
			List<TblTermTmkLog> result = new ArrayList<TblTermTmkLog>();
			String tmpTermId = null;
			int count = 0;
			for(int i=0;i<list.size();i++)
			{
				Object[] object = (Object[])list.get(i);
				if(!object[0].toString().trim().equals(tmpTermId)){
					TblTermTmkLog tmp = new TblTermTmkLog();
					tmp.setId(new TblTermTmkLogPK(object[0].toString().trim(),batchNo));
					tmp.setMchnNo(object[1].toString());
					tmp.setTermBranch(object[2].toString());
					tmp.setState(TblTermTmkLogConstants.STATE_INIT);
					tmp.setReqOpr(operator.getOprId());
					tmp.setReqDate(CommonFunction.getCurrentDate());
					tmp.setRecUpdOpr(operator.getOprId());
					tmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
					result.add(tmp);
					count++;
					tmpTermId = object[0].toString().trim();
				}
				if(count > 200)
					break;
			}
			if(t3010BO.initTmkLog(result))
			{
				if(count <= 200)
					return Constants.SUCCESS_CODE_CUSTOMIZE+"申请批次号："+batchNo;
				else
					return Constants.SUCCESS_CODE_CUSTOMIZE+"申请批次号："+batchNo+"  "+SysCodeUtil.getErrCode(TblTermTmkLogConstants.T30202_04);
			}
		}	
		if(subTxnId.equals("01"))
		{
			TblTermInfTmp tblTermInfTmp = t3010BO.get(CommonFunction.fillString(termId, ' ', 12,true ), recCrtTs);
			if(tblTermInfTmp == null)
				return TblTermTmkLogConstants.T30202_01;
			if(tblTermInfTmp.getTermSta().equals(TblTermInfConstants.TERM_STA_RUN))
			{
				TblTermTmkLog tblTermTmkLog = new TblTermTmkLog();
				tblTermTmkLog.setId(new TblTermTmkLogPK(tblTermInfTmp.getId().getTermId().trim(),batchNo));
				tblTermTmkLog.setMchnNo(tblTermInfTmp.getMchtCd());
				tblTermTmkLog.setTermBranch(tblTermInfTmp.getTermBranch());
				tblTermTmkLog.setState(TblTermTmkLogConstants.STATE_INIT);
				tblTermTmkLog.setReqOpr(operator.getOprId());
				tblTermTmkLog.setReqDate(CommonFunction.getCurrentDate());
				tblTermTmkLog.setRecUpdOpr(operator.getOprId());
				tblTermTmkLog.setRecUpdTs(CommonFunction.getCurrentDateTime());
				t3010BO.initTmkLog(tblTermTmkLog);
				return Constants.SUCCESS_CODE_CUSTOMIZE+"申请批次号："+batchNo;
			}
			return TblTermTmkLogConstants.T30202_02;
		}	
		return Constants.FAILURE_CODE;
	}

}
