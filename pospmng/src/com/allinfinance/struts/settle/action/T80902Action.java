package com.allinfinance.struts.settle.action;

import com.allinfinance.bo.settle.T80902BO;
import com.allinfinance.po.settle.TblChannelCnapsMap;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T80902Action extends BaseAction{

	private T80902BO t80902BO = (T80902BO) ContextUtil.getBean("T80902BO");
	
	private TblChannelCnapsMap tblChannelCnapsMapAdd;
	private TblChannelCnapsMap tblChannelCnapsMapUpd;
	private String cnapsId;
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
		}else if("batchDel".equals(getMethod())) {
			rspCode = batchDel();
		}
		return rspCode;
	}

	private String add() {
		// TODO Auto-generated method stub
		TblChannelCnapsMap tblChannelCnapsMap=t80902BO.get(tblChannelCnapsMapAdd.getCnapsId());
		if(tblChannelCnapsMap!=null){
			return "该开户行已进行绑定,请重新设定！";
		}
		return t80902BO.add(tblChannelCnapsMapAdd);
	}

	private String update() {
		// TODO Auto-generated method stub
		TblChannelCnapsMap tblChannelCnapsMap=t80902BO.get(cnapsId);
		if(tblChannelCnapsMap==null){
			return "该开户行已解除关联，请刷新重新选择！";
		}
		tblChannelCnapsMap.setChannelId(tblChannelCnapsMapUpd.getChannelId());
		return t80902BO.update(tblChannelCnapsMap);
	}

	private String delete() {
		// TODO Auto-generated method stub
		TblChannelCnapsMap tblChannelCnapsMap=t80902BO.get(cnapsId);
		if(tblChannelCnapsMap==null){
			return "该开户行已解除关联，请刷新重新选择！";
		}
		return t80902BO.delete(cnapsId);
	}

	private String batchDel() {
		// TODO Auto-generated method stub
		return t80902BO.batchDel(channelId);
	}

	/**
	 * @return the tblChannelCnapsMapAdd
	 */
	public TblChannelCnapsMap getTblChannelCnapsMapAdd() {
		return tblChannelCnapsMapAdd;
	}

	/**
	 * @param tblChannelCnapsMapAdd the tblChannelCnapsMapAdd to set
	 */
	public void setTblChannelCnapsMapAdd(TblChannelCnapsMap tblChannelCnapsMapAdd) {
		this.tblChannelCnapsMapAdd = tblChannelCnapsMapAdd;
	}

	/**
	 * @return the tblChannelCnapsMapUpd
	 */
	public TblChannelCnapsMap getTblChannelCnapsMapUpd() {
		return tblChannelCnapsMapUpd;
	}

	/**
	 * @param tblChannelCnapsMapUpd the tblChannelCnapsMapUpd to set
	 */
	public void setTblChannelCnapsMapUpd(TblChannelCnapsMap tblChannelCnapsMapUpd) {
		this.tblChannelCnapsMapUpd = tblChannelCnapsMapUpd;
	}

	/**
	 * @return the cnapsId
	 */
	public String getCnapsId() {
		return cnapsId;
	}

	/**
	 * @param cnapsId the cnapsId to set
	 */
	public void setCnapsId(String cnapsId) {
		this.cnapsId = cnapsId;
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