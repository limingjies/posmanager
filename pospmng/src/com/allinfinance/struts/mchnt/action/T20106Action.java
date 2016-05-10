package com.allinfinance.struts.mchnt.action;

import com.allinfinance.bo.mchnt.T20106BO;
import com.allinfinance.common.CommonsConstants;
import com.allinfinance.po.mchnt.TblMchtName;
import com.allinfinance.po.mchnt.TblMchtNamePK;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T20106Action extends BaseAction{

	private T20106BO t20106BO = (T20106BO) ContextUtil.getBean("T20106BO");
	
	
	private TblMchtName tblMchtNameAdd;
	private TblMchtName tblMchtNameUpd;
	private String cardTp;
	private String txnNum;
	private String accpId;
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		if("add".equals(getMethod())) {			
			rspCode = add();			
		} else if("update".equals(getMethod())) {
			rspCode = update();
		}else if("delete".equals(getMethod())) {
			rspCode = delete();
		}
		return rspCode;
	}

	private String delete() {
		// TODO Auto-generated method stub
		TblMchtNamePK tblMchtNamePK=new TblMchtNamePK();
		tblMchtNamePK.setAccpId(accpId);
		tblMchtNamePK.setCardTp(cardTp);
		tblMchtNamePK.setTxnNum(txnNum);
		TblMchtName tblMchtName=t20106BO.get(tblMchtNamePK);
		if(tblMchtName==null){
			return "该商户别名信息不存在，请刷新重新选择！";
		}
		return t20106BO.delete(tblMchtNamePK);
	}

	private String update() {
		// TODO Auto-generated method stub
		TblMchtNamePK tblMchtNamePK=new TblMchtNamePK();
		tblMchtNamePK.setAccpId(accpId);
		tblMchtNamePK.setCardTp(cardTp);
		tblMchtNamePK.setTxnNum(txnNum);
		TblMchtName tblMchtName=t20106BO.get(tblMchtNamePK);
		if(tblMchtName==null){
			return "该商户别名信息不存在，请刷新重新选择！";
		}
		tblMchtName.setMchtNm(tblMchtNameUpd.getMchtNm());
		tblMchtName.setMsc1(CommonFunction.getCurrentDate());
		return t20106BO.update(tblMchtName);
	}

	private String add() {
		// TODO Auto-generated method stub
		TblMchtName tblMchtName=t20106BO.get(tblMchtNameAdd.getId());
		if(tblMchtName!=null){
			return "该商户别名信息已经存在，请刷新重新选择！";
		}
		tblMchtNameAdd.setOnFlag(CommonsConstants.MCHT_NAME_FLAG_START);
		tblMchtNameAdd.setMsc1(CommonFunction.getCurrentDate());
		return t20106BO.save(tblMchtNameAdd);
	}

	public T20106BO getT20106BO() {
		return t20106BO;
	}

	public void setT20106BO(T20106BO t20106bo) {
		t20106BO = t20106bo;
	}

	public TblMchtName getTblMchtNameAdd() {
		return tblMchtNameAdd;
	}

	public void setTblMchtNameAdd(TblMchtName tblMchtNameAdd) {
		this.tblMchtNameAdd = tblMchtNameAdd;
	}

	public TblMchtName getTblMchtNameUpd() {
		return tblMchtNameUpd;
	}

	public void setTblMchtNameUpd(TblMchtName tblMchtNameUpd) {
		this.tblMchtNameUpd = tblMchtNameUpd;
	}

	public String getCardTp() {
		return cardTp;
	}

	public void setCardTp(String cardTp) {
		this.cardTp = cardTp;
	}

	public String getTxnNum() {
		return txnNum;
	}

	public void setTxnNum(String txnNum) {
		this.txnNum = txnNum;
	}

	public String getAccpId() {
		return accpId;
	}

	public void setAccpId(String accpId) {
		this.accpId = accpId;
	}


	
	

	
	
	

	
}
