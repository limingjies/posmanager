package com.allinfinance.struts.settle.action;

import java.util.List;







import com.allinfinance.bo.settle.T80301BO;
import com.allinfinance.common.CommonsConstants;
import com.allinfinance.po.TblAmtbackApply;
import com.allinfinance.po.TblAmtbackApplyPK;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T80300Action extends BaseAction{

	private String instDate;
	private String sysSeqNum;
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
		
		String sql="select SYS_SEQ_NUM,substr(inst_date,1,8),substr(inst_date,9,6),pan,CARD_ACCP_TERM_ID,CARD_ACCP_ID,"
				+ " TXN_NUM,AMT_TRANS,TERM_SSN,RETRIVL_REF from TBL_N_TXN_HIS "
				+ "where SYS_SEQ_NUM='"+sysSeqNum+"' and substr(inst_date,1,8)='"+instDate+"' ";
		
		@SuppressWarnings("unchecked")
		List<Object[]> dataList=commQueryDAO.findBySQLQuery(sql);
		if(dataList==null||dataList.size()==0){
			return "该数据不存在，请重新刷新选择！";
		}
		
		String sysSeqNum=dataList.get(0)[0].toString();
		String instDate=dataList.get(0)[1].toString();
		String instTime=dataList.get(0)[2].toString();
		String pan=dataList.get(0)[3].toString();
		String cardAccpTermId=dataList.get(0)[4].toString();
		String cardAccpId=dataList.get(0)[5].toString();
		String txnNum=dataList.get(0)[6].toString();
		String amtTrans=dataList.get(0)[7].toString();
		String termSsn=dataList.get(0)[8].toString();
		String retrivlRef=dataList.get(0)[9].toString();
		
		
		TblAmtbackApply tblAmtbackApply=new TblAmtbackApply();
		TblAmtbackApplyPK tblAmtbackApplyPK=new TblAmtbackApplyPK();
		tblAmtbackApplyPK.setDateSettlmt8(instDate);
		tblAmtbackApplyPK.setKeyCup(CommonFunction.fillString(sysSeqNum, ' ', 48, true));
		tblAmtbackApplyPK.setApplyDate(CommonFunction.getCurrentDate());
		tblAmtbackApplyPK.setApplyOpr(operator.getOprId());
		tblAmtbackApplyPK.setApplyTime(CommonFunction.getCurrentTime());
		
		tblAmtbackApply.setInstDate(instDate);
		tblAmtbackApply.setInstTime(instTime);
		tblAmtbackApply.setPan(pan);
		tblAmtbackApply.setCardAccpTermId(cardAccpTermId);
		tblAmtbackApply.setCardAccpId(cardAccpId);
		tblAmtbackApply.setTxnNum(txnNum);
		tblAmtbackApply.setAmtTrans(amtTrans);
		tblAmtbackApply.setTermSsn(termSsn);
		tblAmtbackApply.setRetrivlRef(retrivlRef);
		tblAmtbackApply.setSysSeqNum(sysSeqNum);
		tblAmtbackApply.setApplyState(CommonsConstants.APPLY_STATE_UNCHECK);
		tblAmtbackApply.setTblAmtbackApplyPK(tblAmtbackApplyPK);
		tblAmtbackApply.setMisc2(CommonsConstants.AMT_BACK_FALG_Y);
//		tblAmtbackApply.setResState(CommonsConstants);
		return t80301BO.save(tblAmtbackApply);
	}

	

	public String getInstDate() {
		return instDate;
	}

	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}

	public String getSysSeqNum() {
		return sysSeqNum;
	}

	public void setSysSeqNum(String sysSeqNum) {
		this.sysSeqNum = sysSeqNum;
	}




	
}
