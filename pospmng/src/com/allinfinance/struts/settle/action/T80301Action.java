package com.allinfinance.struts.settle.action;

import org.springframework.beans.BeanUtils;

import com.allinfinance.bo.settle.T80301BO;
import com.allinfinance.common.CommonsConstants;
import com.allinfinance.po.BthGcTxnSucc;
import com.allinfinance.po.BthGcTxnSuccPK;
import com.allinfinance.po.TblAmtbackApply;
import com.allinfinance.po.TblAmtbackApplyPK;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T80301Action extends BaseAction{

	private String dateSettlmt8;
	private String keyCup;
	T80301BO t80301BO = (T80301BO) ContextUtil.getBean("T80301BO"); 
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		if ("apply".equals(method)) {
			rspCode= apply();
		}else {
			return "未知的交易类型";
		}
		return rspCode;
	}

	private String apply() {
		// TODO Auto-generated method stub
		BthGcTxnSuccPK bthGcTxnSuccPK=new BthGcTxnSuccPK();
		bthGcTxnSuccPK.setDateSettlmt8(dateSettlmt8);
		bthGcTxnSuccPK.setKeyCup(CommonFunction.fillString(keyCup,' ', 48, true));
		
		
		BthGcTxnSucc bthGcTxnSucc=t80301BO.getBthGcTxnSucc(bthGcTxnSuccPK);
		if(bthGcTxnSucc==null){
			return "此差错信息不存在，请重新刷新选择！";
		}
		TblAmtbackApply tblAmtbackApply=new TblAmtbackApply();
		TblAmtbackApplyPK tblAmtbackApplyPK=new TblAmtbackApplyPK();
		BeanUtils.copyProperties(bthGcTxnSuccPK, tblAmtbackApplyPK);
		tblAmtbackApplyPK.setApplyDate(CommonFunction.getCurrentDate());
		tblAmtbackApplyPK.setApplyOpr(operator.getOprId());
		tblAmtbackApplyPK.setApplyTime(CommonFunction.getCurrentTime());
		
		BeanUtils.copyProperties(bthGcTxnSucc, tblAmtbackApply);
		tblAmtbackApply.setApplyState(CommonsConstants.APPLY_STATE_UNCHECK);
		tblAmtbackApply.setTblAmtbackApplyPK(tblAmtbackApplyPK);
		tblAmtbackApply.setMisc2(CommonsConstants.AMT_BACK_FALG_C);
//		tblAmtbackApply.setResState(CommonsConstants);
		return t80301BO.save(tblAmtbackApply);
	}

	public String getDateSettlmt8() {
		return dateSettlmt8;
	}

	public void setDateSettlmt8(String dateSettlmt8) {
		this.dateSettlmt8 = dateSettlmt8;
	}

	public String getKeyCup() {
		return keyCup;
	}

	public void setKeyCup(String keyCup) {
		this.keyCup = keyCup;
	}


	
}
