package com.allinfinance.struts.system.action;

import com.allinfinance.dao.iface.base.TblOprInfoDAO;
import com.allinfinance.po.TblOprInfo;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class UpdResvInfoAction extends BaseAction {
	private TblOprInfoDAO oprInfoDAO;
	@Override
	protected String subExecute() throws Exception {
		log("操作员[ " + operator.getOprId() + " ]修改预留信息");
		oprInfoDAO=(TblOprInfoDAO) ContextUtil.getBean("OprInfoDAO");
		TblOprInfo tblOprInfo = oprInfoDAO.get(operator.getOprId());
		
		tblOprInfo.setResvInfo(resvInfoUpd);
		oprInfoDAO.update(tblOprInfo);
		log("操作员[" + operator.getOprId() + "]成功修改预留信息！");
		writeSuccessMsg("预留信息修改成功！");
		return SUCCESS ;
			
	}
	//预留信息
	private String resvInfoUpd;
	/**
	 * @return the resvInfoUpd
	 */
	public String getResvInfoUpd() {
		return resvInfoUpd;
	}
	/**
	 * @param resvInfoUpd the resvInfoUpd to set
	 */
	public void setResvInfoUpd(String resvInfoUpd) {
		this.resvInfoUpd = resvInfoUpd;
	}
	
}
