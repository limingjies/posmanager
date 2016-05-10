package com.allinfinance.struts.settle.action;

import com.allinfinance.bo.settle.T80901BO;
import com.allinfinance.common.CommonsConstants;
import com.allinfinance.po.settle.TblPayChannelInfo;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T80901Action extends BaseAction{

	private T80901BO t80901BO = (T80901BO) ContextUtil.getBean("T80901BO");
	
	private TblPayChannelInfo tblPayChannelInfoAdd;
	private TblPayChannelInfo tblPayChannelInfoUpd;
	private String channelId;
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
		if("add".equals(getMethod())) {			
			rspCode = add();			
		}else if("update".equals(getMethod())) {
			rspCode = update();
		}else if("delete".equals(getMethod())) {
			rspCode = delete();
		}else if("switch".equals(getMethod())) {
			rspCode = change();
		}
		return rspCode;
	}

	private String add() {
		// TODO Auto-generated method stub
		TblPayChannelInfo tblPayChannelInfo=t80901BO.get(tblPayChannelInfoAdd.getChannelId());
		if(tblPayChannelInfo!=null){
			return "该通道编号已经存在,请重新设定！";
		}
		tblPayChannelInfoAdd.setChannelSta(CommonsConstants.PAY_CHANNEL_STOP);
		return t80901BO.add(tblPayChannelInfoAdd);
	}

	private String update() {
		// TODO Auto-generated method stub
		TblPayChannelInfo tblPayChannelInfo=t80901BO.get(channelId);
		if(tblPayChannelInfo==null){
			return "该结算通道不存在，请刷新重新选择！";
		}
		tblPayChannelInfo.setChannelName(tblPayChannelInfoUpd.getChannelName());
		tblPayChannelInfo.setAcctNo(tblPayChannelInfoUpd.getAcctNo());
		tblPayChannelInfo.setAcctNm(tblPayChannelInfoUpd.getAcctNm());
		tblPayChannelInfo.setMchtNo(tblPayChannelInfoUpd.getMchtNo());
		tblPayChannelInfo.setMchtNm(tblPayChannelInfoUpd.getMchtNm());
		return t80901BO.update(tblPayChannelInfo);
	}

	private String delete() {
		// TODO Auto-generated method stub
		TblPayChannelInfo tblPayChannelInfo=t80901BO.get(channelId);
		if(tblPayChannelInfo==null){
			return "该结算通道不存在，请刷新重新选择！";
		}
		if(CommonsConstants.PAY_CHANNEL_START.equals(tblPayChannelInfo.getChannelSta())){
			return "请先停用该结算通道";
		}
		return t80901BO.delete(channelId);
	}

	private String change() {
		// TODO Auto-generated method stub
		TblPayChannelInfo tblPayChannelInfo=t80901BO.get(channelId);
		if(tblPayChannelInfo==null){
			return "该结算通道不存在，请刷新重新选择！";
		}
		if(CommonsConstants.PAY_CHANNEL_STOP.equals(tblPayChannelInfo.getChannelSta())){
			tblPayChannelInfo.setChannelSta(CommonsConstants.PAY_CHANNEL_START);
			rspCode = t80901BO.update(tblPayChannelInfo);
		}else{
			tblPayChannelInfo.setChannelSta(CommonsConstants.PAY_CHANNEL_STOP);
			rspCode = t80901BO.update(tblPayChannelInfo);
		}
		return rspCode;
	}

	/**
	 * @return the tblPayChannelInfoAdd
	 */
	public TblPayChannelInfo getTblPayChannelInfoAdd() {
		return tblPayChannelInfoAdd;
	}

	/**
	 * @param tblPayChannelInfoAdd the tblPayChannelInfoAdd to set
	 */
	public void setTblPayChannelInfoAdd(TblPayChannelInfo tblPayChannelInfoAdd) {
		this.tblPayChannelInfoAdd = tblPayChannelInfoAdd;
	}

	/**
	 * @return the tblPayChannelInfoUpd
	 */
	public TblPayChannelInfo getTblPayChannelInfoUpd() {
		return tblPayChannelInfoUpd;
	}

	/**
	 * @param tblPayChannelInfoUpd the tblPayChannelInfoUpd to set
	 */
	public void setTblPayChannelInfoUpd(TblPayChannelInfo tblPayChannelInfoUpd) {
		this.tblPayChannelInfoUpd = tblPayChannelInfoUpd;
	}

	/**
	 * @return the channelId
	 */
	public String getChannelId() {
		return channelId;
	}

	/**
	 * @param channelId the channelId to set
	 */
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
}