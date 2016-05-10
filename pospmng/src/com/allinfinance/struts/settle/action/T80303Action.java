package com.allinfinance.struts.settle.action;


import com.allinfinance.bo.settle.T80301BO;
import com.allinfinance.common.CommonsConstants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.po.TblAmtbackApply;
import com.allinfinance.po.TblAmtbackApplyPK;
import com.allinfinance.socket.AmtbackApply;
import com.allinfinance.socket.TxnSocket;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T80303Action extends BaseAction{

	private String dateSettlmt8;
	private String keyCup;
	private String applyDate;
	private String applyTime;
	private String applyOpr;
	private String refuseInfo;
	T80301BO t80301BO = (T80301BO) ContextUtil.getBean("T80301BO"); 
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		if ("pass".equals(method)) {
			rspCode= pass();
		}else if ("refuse".equals(method)) {
			rspCode= refuse();
		}else{
			return "未知的交易类型";
		}
		return rspCode;
	}

	private String refuse() {
		// TODO Auto-generated method stub
		TblAmtbackApplyPK tblAmtbackApplyPK=new TblAmtbackApplyPK();
		
		tblAmtbackApplyPK.setDateSettlmt8(dateSettlmt8);
		tblAmtbackApplyPK.setKeyCup(CommonFunction.fillString(keyCup,' ', 48, true));
		tblAmtbackApplyPK.setApplyDate(applyDate);
		tblAmtbackApplyPK.setApplyOpr(applyOpr);
		tblAmtbackApplyPK.setApplyTime(applyTime);;
		
		
		TblAmtbackApply tblAmtbackApply=t80301BO.get(tblAmtbackApplyPK);
		if(tblAmtbackApply==null){
			return "此差错申请信息不存在，请重新刷新选择！";
		}
		
		if(tblAmtbackApply.getTblAmtbackApplyPK().getApplyOpr().equals(operator.getOprId())){
			return "不能同一操作员审核！";
		}
		tblAmtbackApply.setApplyState(CommonsConstants.APPLY_STATE_REFUSE);
		tblAmtbackApply.setCheckDate(CommonFunction.getCurrentDate());
		tblAmtbackApply.setCheckOpr(operator.getOprId());
		if(StringUtil.isNotEmpty(refuseInfo)){
			tblAmtbackApply.setMisc1("拒绝原因："+refuseInfo);
		}
//		tblAmtbackApply.setResState(CommonsConstants);
		return t80301BO.update(tblAmtbackApply);
	}
	private String pass() {
		// TODO Auto-generated method stub
		TblAmtbackApplyPK tblAmtbackApplyPK=new TblAmtbackApplyPK();
		
		tblAmtbackApplyPK.setDateSettlmt8(dateSettlmt8);
		tblAmtbackApplyPK.setKeyCup(CommonFunction.fillString(keyCup,' ', 48, true));
		tblAmtbackApplyPK.setApplyDate(applyDate);
		tblAmtbackApplyPK.setApplyOpr(applyOpr);
		tblAmtbackApplyPK.setApplyTime(applyTime);;
		
		
		TblAmtbackApply tblAmtbackApply=t80301BO.get(tblAmtbackApplyPK);
		if(tblAmtbackApply==null){
			return "此差错申请信息不存在，请重新刷新选择！";
		}
		
		if(tblAmtbackApply.getTblAmtbackApplyPK().getApplyOpr().equals(operator.getOprId())){
			return "不能同一操作员审核！";
		}
		tblAmtbackApply.setApplyState(CommonsConstants.APPLY_STATE_PASS);
		tblAmtbackApply.setCheckDate(CommonFunction.getCurrentDate());
		tblAmtbackApply.setCheckOpr(operator.getOprId());
		
		TxnSocket txnSocket=new TxnSocket();
		txnSocket.setAmtTrans(tblAmtbackApply.getAmtTrans());
		txnSocket.setCardAccpId(tblAmtbackApply.getCardAccpId());
		txnSocket.setCardAccpTermId(tblAmtbackApply.getCardAccpTermId());
		txnSocket.setFalg(tblAmtbackApply.getMisc2().trim());
		txnSocket.setInstDate(tblAmtbackApply.getInstDate());
		txnSocket.setPan(tblAmtbackApply.getPan());
		txnSocket.setRevsalSsn(tblAmtbackApply.getRetrivlRef());
		txnSocket.setTxnNum(CommonsConstants.TXN_NUM_5151);
		
		String resStr=AmtbackApply.getResString(AmtbackApply.getSendString(txnSocket));
		
		
		log(resStr);
		
		if(StringUtil.isEmpty(resStr)){
			tblAmtbackApply.setResState(CommonsConstants.BACK_STATE_FAL);
			return "交易异常！";
		}
		
		if(StringUtil.isNotEmpty(resStr)&&("00".equals(resStr.substring(8,10)))) {
			
			tblAmtbackApply.setResState(CommonsConstants.BACK_STATE_SUC);
			
			
		}else{
			tblAmtbackApply.setResState(CommonsConstants.BACK_STATE_FAL);
			t80301BO.update(tblAmtbackApply);
			return "审核成功，但交易失败！";
		}
		return t80301BO.update(tblAmtbackApply);
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

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getApplyOpr() {
		return applyOpr;
	}

	public void setApplyOpr(String applyOpr) {
		this.applyOpr = applyOpr;
	}

	public String getRefuseInfo() {
		return refuseInfo;
	}

	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}


	
}
