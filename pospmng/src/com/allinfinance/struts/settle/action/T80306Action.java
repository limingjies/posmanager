package com.allinfinance.struts.settle.action;


import com.allinfinance.bo.settle.T80306BO;
import com.allinfinance.common.CommonsConstants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.po.TblAmtbackApplyPKmis;
import com.allinfinance.po.TblAmtbackApplyPKres;
import com.allinfinance.po.TblAmtbackApplymis;
import com.allinfinance.po.TblAmtbackApplyres;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T80306Action extends BaseAction{

	private String dateSettlmt8;
	private String keyCup;
	private String applyDate;
	private String applyTime;
	private String applyOpr;
	private String refuseInfo;

	T80306BO t80306BO = (T80306BO) ContextUtil.getBean("T80306BO");  
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
		TblAmtbackApplyPKmis tblAmtbackApplyPKmis=new TblAmtbackApplyPKmis();
		tblAmtbackApplyPKmis.setDateSettlmt8(dateSettlmt8);
		tblAmtbackApplyPKmis.setKeyCup(CommonFunction.fillString(keyCup,' ', 48, true));
		tblAmtbackApplyPKmis.setApplyDate(applyDate);
		tblAmtbackApplyPKmis.setApplyOpr(applyOpr);
		tblAmtbackApplyPKmis.setApplyTime(applyTime);	
		
		TblAmtbackApplymis tblAmtbackApplymis=t80306BO.get(tblAmtbackApplyPKmis);
	
		if(tblAmtbackApplymis==null){
			return "此差错申请信息不存在，请重新刷新选择！";
		}
		
		if(tblAmtbackApplymis.getTblAmtbackApplyPKmis().getApplyOpr().equals(operator.getOprId())){
			return "不能同一操作员审核！";
		}
		TblAmtbackApplyPKres tblAmtbackApplyPKres=new TblAmtbackApplyPKres();
		tblAmtbackApplyPKres.setDateSettlmt8(tblAmtbackApplyPKmis.getDateSettlmt8());
		tblAmtbackApplyPKres.setKeyCup(tblAmtbackApplyPKmis.getKeyCup());
		tblAmtbackApplyPKres.setApplyDate(tblAmtbackApplyPKmis.getApplyDate());
		tblAmtbackApplyPKres.setApplyOpr(tblAmtbackApplyPKmis.getApplyOpr());
		tblAmtbackApplyPKres.setApplyTime(tblAmtbackApplyPKmis.getApplyTime());
		TblAmtbackApplyres tblAmtbackApplyres=new TblAmtbackApplyres();
		tblAmtbackApplyres.setTblAmtbackApplyPKres(tblAmtbackApplyPKres);
		tblAmtbackApplyres.setAmtTrans(tblAmtbackApplymis.getAmtTrans());
		tblAmtbackApplyres.setApplyState(tblAmtbackApplymis.getApplyState());
		tblAmtbackApplyres.setBrhId(tblAmtbackApplymis.getBrhId());
		tblAmtbackApplyres.setCardAccpId(tblAmtbackApplymis.getCardAccpId());
		tblAmtbackApplyres.setCardAccpTermId(tblAmtbackApplymis.getCardAccpTermId());
		tblAmtbackApplyres.setCheckDate(tblAmtbackApplymis.getCheckDate());
		tblAmtbackApplyres.setCheckOpr(tblAmtbackApplymis.getCheckOpr());
		tblAmtbackApplyres.setInstDate(tblAmtbackApplymis.getInstDate());
		tblAmtbackApplyres.setInstTime(tblAmtbackApplymis.getInstTime());
		tblAmtbackApplyres.setMisc1(tblAmtbackApplymis.getMisc1());
		tblAmtbackApplyres.setMisc2(tblAmtbackApplymis.getMisc2());
		tblAmtbackApplyres.setMisc3(operator.getOprId());
		tblAmtbackApplyres.setMisc4(tblAmtbackApplymis.getMisc4());
		tblAmtbackApplyres.setPan(tblAmtbackApplymis.getPan());
		tblAmtbackApplyres.setResState(CommonsConstants.AMT_APPLY_REFUCE);
		tblAmtbackApplyres.setRetrivlRef(tblAmtbackApplymis.getRetrivlRef());
		tblAmtbackApplyres.setRevsalSsn(tblAmtbackApplymis.getRevsalSsn());
		tblAmtbackApplyres.setSysSeqNum(tblAmtbackApplymis.getSysSeqNum());
		tblAmtbackApplyres.setTxnName(CommonFunction.getCurrentDate());
		tblAmtbackApplyres.setTermSsn(tblAmtbackApplymis.getTermSsn());
		tblAmtbackApplyres.setTxnNum(tblAmtbackApplymis.getTxnNum());
		if(StringUtil.isNotEmpty(refuseInfo)){
			tblAmtbackApplyres.setMisc1("业务拒绝原因："+refuseInfo);
		}
//		tblAmtbackApply.setResState(CommonsConstants);
		return t80306BO.save(tblAmtbackApplyres);
	}
	private String pass() {
		// TODO Auto-generated method stub
		TblAmtbackApplyPKmis tblAmtbackApplyPKmis=new TblAmtbackApplyPKmis();
		tblAmtbackApplyPKmis.setDateSettlmt8(dateSettlmt8);
		tblAmtbackApplyPKmis.setKeyCup(CommonFunction.fillString(keyCup,' ', 48, true));
		tblAmtbackApplyPKmis.setApplyDate(applyDate);
		tblAmtbackApplyPKmis.setApplyOpr(applyOpr);
		tblAmtbackApplyPKmis.setApplyTime(applyTime);
		
		
		TblAmtbackApplymis tblAmtbackApplymis=t80306BO.get(tblAmtbackApplyPKmis);
	
		
		if(tblAmtbackApplymis==null){
			return "此差错申请信息不存在，请重新刷新选择！";
		}
		
		if(tblAmtbackApplymis.getTblAmtbackApplyPKmis().getApplyOpr().equals(operator.getOprId())){
			return "不能同一操作员审核！";
		}
		TblAmtbackApplyPKres tblAmtbackApplyPKres=new TblAmtbackApplyPKres();
		tblAmtbackApplyPKres.setDateSettlmt8(tblAmtbackApplyPKmis.getDateSettlmt8());
		tblAmtbackApplyPKres.setKeyCup(tblAmtbackApplyPKmis.getKeyCup());
		tblAmtbackApplyPKres.setApplyDate(tblAmtbackApplyPKmis.getApplyDate());
		tblAmtbackApplyPKres.setApplyOpr(tblAmtbackApplyPKmis.getApplyOpr());
		tblAmtbackApplyPKres.setApplyTime(tblAmtbackApplyPKmis.getApplyTime());
		
		TblAmtbackApplyres tblAmtbackApplyres=new TblAmtbackApplyres();
		tblAmtbackApplyres.setTblAmtbackApplyPKres(tblAmtbackApplyPKres);
		tblAmtbackApplyres.setAmtTrans(tblAmtbackApplymis.getAmtTrans());
		tblAmtbackApplyres.setApplyState(tblAmtbackApplymis.getApplyState());
		tblAmtbackApplyres.setBrhId(tblAmtbackApplymis.getBrhId());
		tblAmtbackApplyres.setCardAccpId(tblAmtbackApplymis.getCardAccpId());
		tblAmtbackApplyres.setCardAccpTermId(tblAmtbackApplymis.getCardAccpTermId());
		tblAmtbackApplyres.setCheckDate(tblAmtbackApplymis.getCheckDate());
		tblAmtbackApplyres.setCheckOpr(tblAmtbackApplymis.getCheckOpr());
		tblAmtbackApplyres.setInstDate(tblAmtbackApplymis.getInstDate());
		tblAmtbackApplyres.setInstTime(tblAmtbackApplymis.getInstTime());
		tblAmtbackApplyres.setMisc1(tblAmtbackApplymis.getMisc1());
		tblAmtbackApplyres.setMisc2(tblAmtbackApplymis.getMisc2());
		tblAmtbackApplyres.setMisc3(operator.getOprId());
		tblAmtbackApplyres.setMisc4(tblAmtbackApplymis.getMisc4());
		tblAmtbackApplyres.setPan(tblAmtbackApplymis.getPan());
		tblAmtbackApplyres.setResState(CommonsConstants.AMT_APPLY_PASS);
		tblAmtbackApplyres.setRetrivlRef(tblAmtbackApplymis.getRetrivlRef());
		tblAmtbackApplyres.setRevsalSsn(tblAmtbackApplymis.getRevsalSsn());
		tblAmtbackApplyres.setSysSeqNum(tblAmtbackApplymis.getSysSeqNum());
		tblAmtbackApplyres.setTxnName(CommonFunction.getCurrentDate());
		tblAmtbackApplyres.setTermSsn(tblAmtbackApplymis.getTermSsn());
		tblAmtbackApplyres.setTxnNum(tblAmtbackApplymis.getTxnNum());
		
		return t80306BO.save(tblAmtbackApplyres);
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
