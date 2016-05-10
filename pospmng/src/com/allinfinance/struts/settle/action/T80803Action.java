package com.allinfinance.struts.settle.action;

import java.util.List;

import com.allinfinance.bo.settle.T80803BO;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;





@SuppressWarnings("serial")
public class T80803Action extends BaseAction {
	
	T80803BO t80803BO = (T80803BO) ContextUtil.getBean("T80803BO");
	
	private String instDate;
	private String channelId;
	private String aimChnlId;
	
	@Override
	protected String subExecute() throws Exception {
		
		if("downloadAll".equals(method)) {
			log("下载全部划付文件："+operator.getOprId());
			rspCode = downloadAll();
		} else if("download".equals(method)) {
			log("下载指定划付文件："+operator.getOprId());
			rspCode = download();
		} else {
			return "未知的交易类型";
		}
		return rspCode;
	}
	
	
	
	@SuppressWarnings("unchecked")
	private String download() throws Exception {
		// TODO Auto-generated method stub
		
		String zthSql="select SETTLE_DATE,SYS_DATE  from TBL_ZTH_DATE ";
		List<Object[]> zthDate =commQueryDAO.findBySQLQuery(zthSql);
		if(zthDate==null||zthDate.size()==0){
			return "请先进行准退货批量跑批！";
		}
		if(Integer.parseInt(instDate)>Integer.parseInt(zthDate.get(0)[0].toString())){
			return "该日期目前未进行准退货跑批，请先进行准退货批量跑批！";
		}
		
		return t80803BO.makeFile(instDate,channelId,aimChnlId);
	}



	@SuppressWarnings("unchecked")
	private String downloadAll() throws Exception {
		// TODO Auto-generated method stub
		
		String zthSql="select SETTLE_DATE,SYS_DATE  from TBL_ZTH_DATE ";
		List<Object[]> zthDate =commQueryDAO.findBySQLQuery(zthSql);
		if(zthDate==null||zthDate.size()==0){
			return "请先进行准退货批量跑批！";
		}
		if(Integer.parseInt(instDate)>Integer.parseInt(zthDate.get(0)[0].toString())){
			return "该日期目前未进行准退货跑批，请先进行准退货批量跑批！";
		}
		
		return t80803BO.allFile(instDate);
	}



	public String getInstDate() {
		return instDate;
	}



	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}



	public String getChannelId() {
		return channelId;
	}



	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}



	public String getAimChnlId() {
		return aimChnlId;
	}



	public void setAimChnlId(String aimChnlId) {
		this.aimChnlId = aimChnlId;
	}



	

	
	
	
}
