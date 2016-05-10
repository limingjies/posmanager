package com.allinfinance.struts.route.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.allinfinance.dao.iface.route.TblRouteUpbrhDAO;
import com.allinfinance.po.route.TblRouteUpbrh;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T110101Action extends BaseAction{

	TblRouteUpbrhDAO tblRouteUpbrhDAO = (TblRouteUpbrhDAO)ContextUtil.getBean("TblRouteUpbrhDAO");
	private TblRouteUpbrh tblRouteUpbrh;
	private String busiId;
	public void setTblRouteUpbrh(TblRouteUpbrh tblRouteUpbrh){
		this.tblRouteUpbrh = tblRouteUpbrh;
	}
	public TblRouteUpbrh getTblRouteUpbrh(){
		return this.tblRouteUpbrh;
	}
	public void setBusiId(String busiId){
		this.busiId = busiId;
	}
	public String getBusiId(){
		return this.busiId;
	}
	@Override
	protected String subExecute() throws Exception {
		// TODO Auto-generated method stub
			if("start".equals(getMethod())) {			
				rspCode = updateStatus("0");			
			} else if("stop".equals(getMethod())) {
				rspCode = updateStatus("1");
			}
			writeSuccessMsg("支付渠道状态更新成功！");
		return rspCode;
	}

	private String updateStatus(String status){
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		tblRouteUpbrh = this.tblRouteUpbrhDAO.get(busiId);
		this.tblRouteUpbrh.setBrhId(this.busiId);
		this.tblRouteUpbrh.setBrhLevel("2");
		this.tblRouteUpbrh.setStatus(status);
		this.tblRouteUpbrh.setUptTime(sdf.format(now));
		if("0".equals(status)){
			this.tblRouteUpbrh.setUseTime(sdf.format(now));
		}
		this.tblRouteUpbrh.setUptOpr(this.operator.getOprId());
		this.tblRouteUpbrhDAO.update(tblRouteUpbrh);
		return "00";
	}

}



