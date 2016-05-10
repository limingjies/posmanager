package com.allinfinance.struts.route.action;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.allinfinance.bo.route.T110321BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfDAO;
import com.allinfinance.dao.iface.route.TblRouteMchtgDetailDAO;
import com.allinfinance.dao.iface.route.TblRouteRuleMapDAO;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.route.TblRouteMchtgDetail;
import com.allinfinance.po.route.TblRouteMchtgDetailPk;
import com.allinfinance.po.route.TblRouteRuleMap;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T110321Action extends BaseAction{
	
	
	private Operator operator = (Operator) ServletActionContext.getRequest()
			.getSession().getAttribute(Constants.OPERATOR_INFO);
	private T110321BO t110321BO=(T110321BO) ContextUtil.getBean("T110321BO");
	private TblRouteMchtgDetailDAO tblRouteMchtgDetailDAO=(TblRouteMchtgDetailDAO) ContextUtil.getBean("TblRouteMchtgDetailDAO");
	private ITblMchtBaseInfDAO tblMchtBaseInfDAO=(ITblMchtBaseInfDAO) ContextUtil.getBean("tblMchtBaseInfDAO");
	private TblRouteRuleMapDAO tblRouteRuleMapDAO=(TblRouteRuleMapDAO) ContextUtil.getBean("TblRouteRuleMapDAO");
	
	@Override
	protected String subExecute() throws Exception {
		return null;
	}
	/**
	 * 新商户配置
	 * @return
	 * @throws IOException 
	 */
	public String setNewMchtConfig() throws IOException{
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String result = t110321BO.setNewMchtConfig(request);
			writeSuccessMsg(result);
		} catch (Exception e) {
			log(e.getMessage());
			writeErrorMsg("新商户配置失败！");
		} 
		return null;
	} 
	public String bacthConfigMchtUpbrh() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		TblRouteRuleMap routeRuleMap;
		String mchtIds = request.getParameter("mchtIds");
		String mchtUpbrhIds = request.getParameter("mchtUpbrhIds");
		String newGid = request.getParameter("groupId");
		String propertyIds = request.getParameter("propertyIds");
		String[] mchtIdStrings = mchtIds.split(",");
		String[] mchtUpbrhIdStrings = mchtUpbrhIds.split(",");
		String[] propertyIdStrings = propertyIds.split(",");
		String[] termIds = request.getParameter("termIds").split(",");
		String termId="";
		String mchtId="";
		String mchtUpbrhId="";
		String propertyId="";
		String success="配置成功的商户映射关系：<div/>";
		String failure="配置失败的商户映射关系：<div/>";
		int newMchtgId = Integer.parseInt(newGid);
		
		for (int i = 0; i < mchtUpbrhIdStrings.length; i++) {
			mchtUpbrhId=mchtUpbrhIdStrings[i];
			propertyId=propertyIdStrings[i];
			mchtId=mchtIdStrings[i];
			termId=termIds[i];
			try {
				String result = t110321BO.bacthAddMchtUpbrh(operator.getOprBrhId(), newMchtgId,  mchtId, mchtUpbrhId, propertyId,termId);
				if(result.equals(Constants.FAILURE_CODE)){
					writeErrorMsg("映射关系已满！");
					return null;
				}
				//成功
        		success+="<font color=green>商户号"+mchtId+"-渠道商户号："+mchtUpbrhId+"-性质："+propertyId+"</font></div>";
			} catch (IOException e) {
				log(e.getMessage());
				//失败
				failure+="<font color=red>商户号"+mchtId+"-渠道商户号："+mchtUpbrhId+"-性质："+propertyId+"</font></div>";
			}
		}
		writeSuccessMsg(success+failure);
		return null;
	}
}
