package com.allinfinance.struts.risk.action;

import java.math.BigDecimal;

import com.allinfinance.bo.impl.daytrade.AdjustAcct;
import com.allinfinance.bo.impl.risk.FrontMchtFreeze;
import com.allinfinance.bo.risk.T40215BO;
import com.allinfinance.common.Constants;
import com.allinfinance.frontend.dto.acctmanager.MerchantDrozenDto;
import com.allinfinance.po.risk.TblMchtFreeze;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.GenerateNextId;

public class T40215Action extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	T40215BO t40215BO = (T40215BO) ContextUtil.getBean("T40215BO");

	private String mchtNo;
	private BigDecimal freezeAmt;

	private String method;

	private String batchNo;

	@Override
	protected String subExecute() throws Exception {
		if ("add".equals(method)) {
			return addFreeze();
		} else if ("recoverFreeze".equals(method)) {
			return recoverFreeze();
		}

		return Constants.SUCCESS_CODE;
	}

	public static void main(String[] args) {
		System.out.println("20150912000001".substring(8, 14));
	}

	// 商户资金冻结
	public String addFreeze() {
		TblMchtFreeze tblMchtFreeze = new TblMchtFreeze();
		tblMchtFreeze.setBatchNo(GenerateNextId.getSeqFreezeNo());
		tblMchtFreeze.setMchtId(mchtNo);
		tblMchtFreeze.setFreezeAmt(freezeAmt);
		tblMchtFreeze.setDoFreezeAmt(BigDecimal.valueOf(0));
		tblMchtFreeze.setFreezeFlag("0");
		tblMchtFreeze.setUnFreezeFlag("1");
		tblMchtFreeze.setFreezeDate(CommonFunction.getCurrentDate());
		tblMchtFreeze.setInstDate(CommonFunction.getCurrentDateTime());
		tblMchtFreeze.setUpdateDt(CommonFunction.getCurrentDateTime());
		try {
			String retResp = buildFrontMsg(FrontMchtFreeze.MACHT_CODE_FREEZE,
					tblMchtFreeze);
			if (!Constants.SUCCESS_CODE.equals(retResp)) {
				return retResp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		t40215BO.save(tblMchtFreeze);
		return Constants.SUCCESS_CODE;

	}

	// 商户资金解冻

	public String recoverFreeze() {
		TblMchtFreeze tblMchtFreeze = t40215BO.get(batchNo);
		if(null==tblMchtFreeze){
			
			return "此交易不存在！";
		}
		if ("0".equals(tblMchtFreeze.getUnFreezeFlag())) {

			return "此交易已解冻！";

		}
		tblMchtFreeze.setUnFreezeDate(CommonFunction.getCurrentDate());
		tblMchtFreeze.setUnFreezeFlag("0");
		tblMchtFreeze.setUpdateDt(CommonFunction.getCurrentDateTime());
		try {
			String retResp = recoverFrontMsg(FrontMchtFreeze.MACHT_CODE_UNFREEZE,
					tblMchtFreeze);
			if (!Constants.SUCCESS_CODE.equals(retResp)) {
				return retResp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		t40215BO.update(tblMchtFreeze);
		return Constants.SUCCESS_CODE;
	}

	// 前置冻结
	private String buildFrontMsg(String transCode, TblMchtFreeze tblMchtFreeze) {
		// 组装调账前台系统数据
		MerchantDrozenDto trc = new MerchantDrozenDto();
		trc.setWebTime(CommonFunction.getCurrentDateTime());
		trc.setWebSeqNum(tblMchtFreeze.getBatchNo().substring(8, 14));
		trc.setMerchantId(tblMchtFreeze.getMchtId());
		trc.setSettleAmount(tblMchtFreeze.getFreezeAmt());
		FrontMchtFreeze frontMchtFreeze = new FrontMchtFreeze();
		Object[] ret = frontMchtFreeze.doTxn(transCode, trc);
		if (Constants.SUCCESS_CODE.equals(ret[0])) {
			// WebFrontTxnLog webFrontTxnLog = (WebFrontTxnLog) ret[1];
			// webFrontTxnLogDAO.save(webFrontTxnLog);
			return Constants.SUCCESS_CODE;
		} else {
			return (String) ret[1];
		}
	}

	// 前置解冻

	private String recoverFrontMsg(String transCode, TblMchtFreeze tblMchtFreeze) {	
		// 组装调账前台系统数据
		MerchantDrozenDto trc = new MerchantDrozenDto();
		trc.setWebTime(CommonFunction.getCurrentDateTime());
		trc.setWebSeqNum(GenerateNextId.getSeqFreezeNo().substring(8, 14));
		trc.setMerchantId(tblMchtFreeze.getMchtId());
		trc.setSettleAmount(tblMchtFreeze.getFreezeAmt());
		trc.setOrig_webSeqNum(tblMchtFreeze.getBatchNo().substring(8, 14));
		trc.setOrig_webTime(tblMchtFreeze.getInstDate());
		FrontMchtFreeze frontMchtFreeze = new FrontMchtFreeze();
		Object[] ret = frontMchtFreeze.doTxn(transCode, trc);
		if (Constants.SUCCESS_CODE.equals(ret[0])) {
			// WebFrontTxnLog webFrontTxnLog = (WebFrontTxnLog) ret[1];
			// webFrontTxnLogDAO.save(webFrontTxnLog);
			return Constants.SUCCESS_CODE;
		} else {
			return (String) ret[1];
		}
	}

	public String getMchtNo() {
		return mchtNo;
	}

	public void setMchtNo(String mchtNo) {
		this.mchtNo = mchtNo;
	}

	public BigDecimal getFreezeAmt() {
		return freezeAmt;
	}

	public void setFreezeAmt(BigDecimal freezeAmt) {
		this.freezeAmt = freezeAmt;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

}
