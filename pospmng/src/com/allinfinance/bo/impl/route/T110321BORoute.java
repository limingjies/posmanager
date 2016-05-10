package com.allinfinance.bo.impl.route;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;

import com.allinfinance.bo.route.T110321BO;
import com.allinfinance.bo.route.TblRouteMchtgBO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfDAO;
import com.allinfinance.dao.iface.route.TblRouteMchtgDetailDAO;
import com.allinfinance.dao.iface.route.TblRouteRuleMapDAO;
import com.allinfinance.dao.iface.route.TblRouteRuleMapHistDAO;
import com.allinfinance.log.Log;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.route.TblRouteMchtgDetail;
import com.allinfinance.po.route.TblRouteMchtgDetailPk;
import com.allinfinance.po.route.TblRouteRuleMap;
import com.allinfinance.po.route.TblRouteRuleMapHist;
import com.allinfinance.po.route.TblRouteRuleMapHistPk;
import com.allinfinance.system.util.ContextUtil;

public class T110321BORoute implements T110321BO {
	private TblRouteRuleMapHistDAO tblRouteRuleMapHistDAO;
	private TblRouteMchtgBO tblRouteMchtgBO;//=(TblRouteMchtgBO) ContextUtil.getBean("TblRouteMchtgBO");
	private TblRouteMchtgDetailDAO tblRouteMchtgDetailDAO;//=(TblRouteMchtgDetailDAO) ContextUtil.getBean("TblRouteMchtgDetailDAO");
	private ITblMchtBaseInfDAO tblMchtBaseInfDAO;//=(ITblMchtBaseInfDAO) ContextUtil.getBean("tblMchtBaseInfDAO");
	private TblRouteRuleMapDAO tblRouteRuleMapDAO;// =(TblRouteRuleMapDAO)
	
	/**
	 * @return the tblRouteRuleMapHistDAO
	 */
	public TblRouteRuleMapHistDAO getTblRouteRuleMapHistDAO() {
		return tblRouteRuleMapHistDAO;
	}

	/**
	 * @param tblRouteRuleMapHistDAO the tblRouteRuleMapHistDAO to set
	 */
	public void setTblRouteRuleMapHistDAO(
			TblRouteRuleMapHistDAO tblRouteRuleMapHistDAO) {
		this.tblRouteRuleMapHistDAO = tblRouteRuleMapHistDAO;
	}

													// ContextUtil.getBean("TblRouteRuleMapDAO");
	
	/**
	 * @return the tblRouteMchtgBO
	 */
	public TblRouteMchtgBO getTblRouteMchtgBO() {
		return tblRouteMchtgBO;
	}

	/**
	 * @param tblRouteMchtgBO the tblRouteMchtgBO to set
	 */
	public void setTblRouteMchtgBO(TblRouteMchtgBO tblRouteMchtgBO) {
		this.tblRouteMchtgBO = tblRouteMchtgBO;
	}

	/**
	 * @return the tblRouteMchtgDetailDAO
	 */
	public TblRouteMchtgDetailDAO getTblRouteMchtgDetailDAO() {
		return tblRouteMchtgDetailDAO;
	}

	/**
	 * @param tblRouteMchtgDetailDAO the tblRouteMchtgDetailDAO to set
	 */
	public void setTblRouteMchtgDetailDAO(
			TblRouteMchtgDetailDAO tblRouteMchtgDetailDAO) {
		this.tblRouteMchtgDetailDAO = tblRouteMchtgDetailDAO;
	}

	/**
	 * @return the tblMchtBaseInfDAO
	 */
	public ITblMchtBaseInfDAO getTblMchtBaseInfDAO() {
		return tblMchtBaseInfDAO;
	}

	/**
	 * @param tblMchtBaseInfDAO the tblMchtBaseInfDAO to set
	 */
	public void setTblMchtBaseInfDAO(ITblMchtBaseInfDAO tblMchtBaseInfDAO) {
		this.tblMchtBaseInfDAO = tblMchtBaseInfDAO;
	}

	/**
	 * @return the tblRouteRuleMapDAO
	 */
	public TblRouteRuleMapDAO getTblRouteRuleMapDAO() {
		return tblRouteRuleMapDAO;
	}

	/**
	 * @param tblRouteRuleMapDAO the tblRouteRuleMapDAO to set
	 */
	public void setTblRouteRuleMapDAO(TblRouteRuleMapDAO tblRouteRuleMapDAO) {
		this.tblRouteRuleMapDAO = tblRouteRuleMapDAO;
	}

	@Override
	public String setNewMchtConfig(HttpServletRequest request){
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			String crtTime=format.format(date);
			String mchtId = request.getParameter("mchtId");
			String mchtgId = request.getParameter("mchtgId");
			int mchtGid = Integer.parseInt(mchtgId);
			String[] mchtUpbrhIds = request.getParameter("mchtUpbrhIds").split(",");
			String[] propertyIds = request.getParameter("propertyIds").split(",");
			String[] termIds = request.getParameter("termIds").split(",");
			TblMchtBaseInf mchtBaseInf = tblMchtBaseInfDAO.get(mchtId);
			//商户组明细
			TblRouteMchtgDetail routeMchtgDetail=new TblRouteMchtgDetail();
			TblRouteMchtgDetailPk pk=new TblRouteMchtgDetailPk(mchtId, mchtGid);
			routeMchtgDetail.setPk(pk);
			routeMchtgDetail.setCrtTime(crtTime);
			routeMchtgDetail.setCrtOpr(operator.getOprId());
			routeMchtgDetail.setUptOpr(operator.getOprId());
			routeMchtgDetail.setUptTime(crtTime);
			
			if(mchtBaseInf!=null){
				routeMchtgDetail.setRunTime(mchtBaseInf.getRecUpdTs());
			}
			
			Integer ruleId;
			//规则编号生成
			ruleId=tblRouteRuleMapDAO.getRuleId();
			if(ruleId==null){
				ruleId=1;
			}else if(ruleId==99999999){
				return "映射关系已满！";
			}else {
				ruleId++;
			}
			//Lishi规则编号生成
			Integer hruleId=tblRouteRuleMapHistDAO.getRuleId();
			if(hruleId==null){
				hruleId=1;
			}else if(hruleId==99999999){
				return "映射关系已满！";
			}else {
				hruleId++;
			}
			List<TblRouteRuleMap> list=new ArrayList<TblRouteRuleMap>();
			List<TblRouteRuleMapHist> hlist=new ArrayList<TblRouteRuleMapHist>();
			
			
			for (int i = 0; i < propertyIds.length; i++) {
				String propertyId=propertyIds[i];
				String mchtUpbrhId=mchtUpbrhIds[i];
				String termId=termIds[i];
				if(propertyId==null||propertyId.equals("")||mchtUpbrhId==null||mchtUpbrhId.equals("")){
					continue;
				}
				
				//映射关系
				TblRouteRuleMap routeRuleMap=new TblRouteRuleMap();
				routeRuleMap.setBrhId3(propertyId);
				routeRuleMap.setCrtOpr(operator.getOprId());
				routeRuleMap.setCrtTime(crtTime);
				routeRuleMap.setMchtId(mchtId);
				routeRuleMap.setMchtIdUp(mchtUpbrhId);
				routeRuleMap.setMisc1(termId);
			
				routeRuleMap.setRuleId(ruleId);
				routeRuleMap.setUptOpr(crtTime);
				routeRuleMap.setUptOpr(operator.getOprId());
				routeRuleMap.setUptTime(crtTime);
				
				//映射历史
				TblRouteRuleMapHist routeRuleMapHist=new TblRouteRuleMapHist();
				if(routeMchtgDetail!=null){
					routeRuleMapHist.setMchtgId(routeMchtgDetail.getPk().getMchtGid());
				}
				routeRuleMapHist.setBrhId3(propertyId);
				routeRuleMapHist.setCrtOpr(operator.getOprId());
				routeRuleMapHist.setCrtTime(crtTime);
				routeRuleMapHist.setMchtId(mchtId);
				routeRuleMapHist.setMchtIdUp(mchtUpbrhId);
				routeRuleMapHist.setMisc1(termId);
				if(mchtBaseInf!=null){
					routeRuleMapHist.setMchtName(mchtBaseInf.getMchtNm());
				}
				routeRuleMapHist.setRunTime(crtTime);
				routeRuleMapHist.setStatus("0");
				routeRuleMapHist.setUptOpr(operator.getOprId());
				routeRuleMapHist.setUptTime(crtTime);
				TblRouteRuleMapHistPk hpk=new TblRouteRuleMapHistPk();
				
				hpk.setRuleId(hruleId);
				hpk.setStopTime(crtTime);
				routeRuleMapHist.setPk(hpk);
				
				hlist.add(routeRuleMapHist);
				list.add(routeRuleMap);
				ruleId++;
				hruleId++;
			}
			for (TblRouteRuleMap tblRouteRuleMap : list) {
				tblRouteRuleMapDAO.save(tblRouteRuleMap);
			}
			for (TblRouteRuleMapHist tblRouteRuleMapHist : hlist) {
				tblRouteRuleMapHistDAO.save(tblRouteRuleMapHist);
			}
			tblRouteMchtgDetailDAO.save(routeMchtgDetail);
			return "新商户配置成功！";
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "新商户配置失败！";
	}

	@Override
	public String bacthAddMchtUpbrh(String oprId, int newMchtgId,
			String mchtId, String mchtUpbrhId, String propertyId,String termId) {

		TblRouteRuleMap routeRuleMap;
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		String crtTime=format.format(date);
		
		//添加到新商户组
		TblRouteMchtgDetailPk pk=new TblRouteMchtgDetailPk(mchtId, newMchtgId);
		TblRouteMchtgDetail routeMchtgDetail=new TblRouteMchtgDetail(pk);
		routeMchtgDetail.setCrtOpr(oprId);
		routeMchtgDetail.setCrtTime(crtTime);
		routeMchtgDetail.setUptOpr(oprId);
		routeMchtgDetail.setUptTime(crtTime);
		TblMchtBaseInf mchtBaseInf = tblMchtBaseInfDAO.get(mchtId);
		if(mchtBaseInf!=null){
			routeMchtgDetail.setRunTime(mchtBaseInf.getRecUpdTs());
		}
		tblRouteMchtgDetailDAO.saveOrUpdate(routeMchtgDetail);
		Log.log("添加到新商户组");
		routeRuleMap =tblRouteRuleMapDAO.getByConditions(mchtId,mchtUpbrhId,propertyId,termId);
		if(routeRuleMap!=null){
			routeRuleMap.setBrhId3(propertyId);
			routeRuleMap.setCrtOpr(oprId);
			routeRuleMap.setCrtTime(crtTime);
			routeRuleMap.setMchtIdUp(mchtUpbrhId);
			routeRuleMap.setMisc1(termId);
			tblRouteRuleMapDAO.update(routeRuleMap);
			Log.log("如果映射关系存在就更新");
		}
		if(routeRuleMap==null){
			routeRuleMap=new TblRouteRuleMap();
			//映射历史
			TblRouteRuleMapHist routeRuleMapHist=new TblRouteRuleMapHist();
			routeRuleMapHist.setBrhId3(propertyId);
			routeRuleMapHist.setCrtOpr(oprId);
			routeRuleMapHist.setCrtTime(crtTime);
			routeRuleMapHist.setMchtgId(newMchtgId);
			routeRuleMapHist.setMchtId(mchtId);
			routeRuleMapHist.setMisc1(termId);
			routeRuleMapHist.setMchtIdUp(mchtUpbrhId);
			if(mchtBaseInf!=null){
				routeRuleMapHist.setMchtName(mchtBaseInf.getMchtNm());
			}
			routeRuleMapHist.setRunTime(crtTime);
			routeRuleMapHist.setStatus("0");
			routeRuleMapHist.setUptOpr(oprId);
			routeRuleMapHist.setUptTime(crtTime);
			TblRouteRuleMapHistPk hpk=new TblRouteRuleMapHistPk();
			//规则编号生成
			Integer hruleId=tblRouteRuleMapHistDAO.getRuleId();
			if(hruleId==null){
				hruleId=1;
			}else if(hruleId==99999999){
				return Constants.FAILURE_CODE;
			}else {
				hruleId++;
			}
			hpk.setRuleId(hruleId);
			hpk.setStopTime(crtTime);
			routeRuleMapHist.setPk(hpk);
			
			tblRouteRuleMapHistDAO.save(routeRuleMapHist);
			Log.log("添加映射历史记录");
			//映射关系
			routeRuleMap.setBrhId3(propertyId);
			routeRuleMap.setCrtOpr(oprId);
			routeRuleMap.setCrtTime(crtTime);
			routeRuleMap.setMchtId(mchtId);
			routeRuleMap.setMisc1(termId);
			routeRuleMap.setMchtIdUp(mchtUpbrhId);
			//规则编号生成
			Integer ruleId=tblRouteRuleMapDAO.getRuleId();
			if(ruleId==null){
				ruleId=1;
			}else if(ruleId==99999999){
				return Constants.FAILURE_CODE;
			}else {
				ruleId++;
			}
			routeRuleMap.setRuleId(ruleId);
			routeRuleMap.setUptOpr(crtTime);
			routeRuleMap.setUptOpr(oprId);
			routeRuleMap.setUptTime(crtTime);
			tblRouteRuleMapDAO.save(routeRuleMap);
			Log.log("添加映射关系");
		}
		return Constants.SUCCESS_CODE;
	
	}
}
