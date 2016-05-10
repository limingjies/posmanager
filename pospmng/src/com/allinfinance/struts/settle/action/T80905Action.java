package com.allinfinance.struts.settle.action;

import com.allinfinance.bo.settle.T80905BO;
import com.allinfinance.po.settle.TblFirstBrhDestId;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T80905Action extends BaseAction{

	private T80905BO t80905BO = (T80905BO) ContextUtil.getBean("T80905BO");
	
	private TblFirstBrhDestId tblFirstBrhDestIdAdd;
	private TblFirstBrhDestId tblFirstBrhDestIdUpd;
	private String destId;
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		if("add".equals(getMethod())) {			
			rspCode = add();			
		}else if("update".equals(getMethod())) {
			rspCode = update();
		}else if("delete".equals(getMethod())) {
			rspCode = delete();
		}
		return rspCode;
	}

	private String add() {
		// TODO Auto-generated method stub
		TblFirstBrhDestId tblFirstBrhDestId=t80905BO.get(tblFirstBrhDestIdAdd.getDestId());
		if(tblFirstBrhDestId!=null){
			return "该通道编号已经存在,请重新设定！";
		}
		return t80905BO.add(tblFirstBrhDestIdAdd);
	}

	private String update() {
		// TODO Auto-generated method stub
		TblFirstBrhDestId tblFirstBrhDestId=t80905BO.get(destId);
		if(tblFirstBrhDestId==null){
			return "该结算通道不存在，请刷新重新选择！";
		}
		tblFirstBrhDestId.setFirstBrhName(tblFirstBrhDestIdUpd.getFirstBrhName());
		tblFirstBrhDestId.setFirstBrhId(tblFirstBrhDestIdUpd.getFirstBrhId());
		tblFirstBrhDestId.setBak1(tblFirstBrhDestIdUpd.getBak1());
		tblFirstBrhDestId.setBak2(tblFirstBrhDestIdUpd.getBak2());
		return t80905BO.update(tblFirstBrhDestId);
	}

	private String delete() {
		// TODO Auto-generated method stub
		TblFirstBrhDestId tblFirstBrhDestId=t80905BO.get(destId);
		if(tblFirstBrhDestId==null){
			return "该结算通道不存在，请刷新重新选择！";
		}
		return t80905BO.delete(destId);
	}

	/**
	 * @return the tblFirstBrhDestIdAdd
	 */
	public TblFirstBrhDestId getTblFirstBrhDestIdAdd() {
		return tblFirstBrhDestIdAdd;
	}

	/**
	 * @param tblFirstBrhDestIdAdd the tblFirstBrhDestIdAdd to set
	 */
	public void setTblFirstBrhDestIdAdd(TblFirstBrhDestId tblFirstBrhDestIdAdd) {
		this.tblFirstBrhDestIdAdd = tblFirstBrhDestIdAdd;
	}

	/**
	 * @return the tblFirstBrhDestIdUpd
	 */
	public TblFirstBrhDestId getTblFirstBrhDestIdUpd() {
		return tblFirstBrhDestIdUpd;
	}

	/**
	 * @param tblFirstBrhDestIdUpd the tblFirstBrhDestIdUpd to set
	 */
	public void setTblFirstBrhDestIdUpd(TblFirstBrhDestId tblFirstBrhDestIdUpd) {
		this.tblFirstBrhDestIdUpd = tblFirstBrhDestIdUpd;
	}

	/**
	 * @return the destId
	 */
	public String getDestId() {
		return destId;
	}

	/**
	 * @param destId the destId to set
	 */
	public void setDestId(String destId) {
		this.destId = destId;
	}
	
}