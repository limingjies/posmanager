package com.allinfinance.struts.risk.action;

import com.allinfinance.bo.risk.T40207BO;
import com.allinfinance.po.TblRiskWhite;
import com.allinfinance.po.TblRiskWhitePK;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T40207Action extends BaseAction{

	private T40207BO t40207BO = (T40207BO) ContextUtil.getBean("T40207BO");
	
	
	private TblRiskWhite tblRiskWhiteAdd;
	private TblRiskWhite tblRiskWhiteUpd;
	private String riskId;
	private String cardAccpId;
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
		TblRiskWhitePK tblRiskWhitePK=new TblRiskWhitePK();
		tblRiskWhitePK.setRiskId(riskId);
		tblRiskWhitePK.setCardAccpId(cardAccpId);
		TblRiskWhite tblRiskWhite=t40207BO.get(tblRiskWhitePK);
		if(tblRiskWhite==null){
			return "该商户白名单不存在，请刷新重新选择！";
		}
		return t40207BO.delete(tblRiskWhitePK);
	}

	private String update() {
		// TODO Auto-generated method stub
		TblRiskWhitePK tblRiskWhitePK=new TblRiskWhitePK();
		tblRiskWhitePK.setRiskId(riskId);
		tblRiskWhitePK.setCardAccpId(cardAccpId);
		TblRiskWhite tblRiskWhite=t40207BO.get(tblRiskWhitePK);
		if(tblRiskWhite==null){
			return "该商户白名单不存在，请刷新重新选择！";
		}
		tblRiskWhite.setResved(tblRiskWhiteUpd.getResved());
		return t40207BO.update(tblRiskWhite);
	}

	private String add() {
		// TODO Auto-generated method stub
		TblRiskWhite tblRiskWhite=t40207BO.get(tblRiskWhiteAdd.getId());
		if(tblRiskWhite!=null){
			return "该商户白名单信息已经存在，请刷新重新选择！";
		}
		return t40207BO.save(tblRiskWhiteAdd);
	}


	
	public String getRiskId() {
		return riskId;
	}

	public void setRiskId(String riskId) {
		this.riskId = riskId;
	}

	public T40207BO getT40207BO() {
		return t40207BO;
	}

	public void setT40207BO(T40207BO t40207bo) {
		t40207BO = t40207bo;
	}

	public TblRiskWhite getTblRiskWhiteAdd() {
		return tblRiskWhiteAdd;
	}

	public void setTblRiskWhiteAdd(TblRiskWhite tblRiskWhiteAdd) {
		this.tblRiskWhiteAdd = tblRiskWhiteAdd;
	}

	public TblRiskWhite getTblRiskWhiteUpd() {
		return tblRiskWhiteUpd;
	}

	public void setTblRiskWhiteUpd(TblRiskWhite tblRiskWhiteUpd) {
		this.tblRiskWhiteUpd = tblRiskWhiteUpd;
	}

	public String getCardAccpId() {
		return cardAccpId;
	}

	public void setCardAccpId(String cardAccpId) {
		this.cardAccpId = cardAccpId;
	}

	

	
	
	

	
}
