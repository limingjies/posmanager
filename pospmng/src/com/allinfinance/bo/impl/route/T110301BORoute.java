package com.allinfinance.bo.impl.route;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.allinfinance.log.Log;
import javax.servlet.http.HttpServletRequest;






import com.allinfinance.bo.route.T110301BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfDAO;
import com.allinfinance.dao.iface.route.TblRouteMchtgDetailDAO;
import com.allinfinance.dao.iface.route.TblRouteRuleMapDAO;
import com.allinfinance.dao.iface.route.TblRouteRuleMapHistDAO;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.route.TblRouteMchtgDetail;
import com.allinfinance.po.route.TblRouteMchtgDetailPk;
import com.allinfinance.po.route.TblRouteRuleMap;
import com.allinfinance.po.route.TblRouteRuleMapHist;
import com.allinfinance.po.route.TblRouteRuleMapHistPk;

public class T110301BORoute implements T110301BO {
	
	private TblRouteRuleMapHistDAO tblRouteRuleMapHistDAO;//=(TblRouteRuleMapHistDAO) ContextUtil.getBean("TblRouteRuleMapHistDAO");
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
	private TblRouteMchtgDetailDAO tblRouteMchtgDetailDAO;//=(TblRouteMchtgDetailDAO) ContextUtil.getBean("TblRouteMchtgDetailDAO");
	private ITblMchtBaseInfDAO tblMchtBaseInfDAO;//=(ITblMchtBaseInfDAO) ContextUtil.getBean("tblMchtBaseInfDAO");
	private TblRouteRuleMapDAO tblRouteRuleMapDAO;//=(TblRouteRuleMapDAO) ContextUtil.getBean("TblRouteRuleMapDAO");
	
	@Override
	public String addMchtToGroup(HttpServletRequest request) {
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		String ids=request.getParameter("mchtIds");
		String gId=request.getParameter("mchtGid");
		int mchtGid=Integer.parseInt(gId);
		String[] mchtIds = ids.split(",");
		for (String mchtId : mchtIds) {
			Date date=new Date();
			DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
			String crtTime=format.format(date);
			TblRouteMchtgDetail routeMchtgDetail=new TblRouteMchtgDetail();
			TblRouteMchtgDetailPk pk=new TblRouteMchtgDetailPk(mchtId, mchtGid);
			routeMchtgDetail.setPk(pk);
			routeMchtgDetail.setCrtTime(crtTime);
			routeMchtgDetail.setCrtOpr(operator.getOprId());
			routeMchtgDetail.setUptOpr(operator.getOprId());
			routeMchtgDetail.setUptTime(crtTime);
			TblMchtBaseInf mchtBaseInf = tblMchtBaseInfDAO.get(mchtId);
			if(mchtBaseInf!=null){
				routeMchtgDetail.setRunTime(mchtBaseInf.getRecUpdTs());
			}
			tblRouteMchtgDetailDAO.save(routeMchtgDetail);
		}
		return null;
	}
	@Override
	public String bacthAddMchtUpbrh(int i,HttpServletRequest request,Integer newMchtgId,
			String oldGid,Integer oldMchtgId,String mchtId,String mchtUpbrhId,String propertyId,String termId){
		TblRouteRuleMap routeRuleMap;
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		String crtTime=format.format(date);
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		//是否有原商户组
		if(oldGid==null||oldGid==""){
			//根据商户查询商户组
			TblRouteMchtgDetail routeMchtgDetail1 = tblRouteMchtgDetailDAO.getByMchtId(mchtId);
			if(routeMchtgDetail1!=null&&!newMchtgId.equals(routeMchtgDetail1.getPk().getMchtGid())){//该商户有商户组
				oldMchtgId=routeMchtgDetail1.getPk().getMchtGid();
			}
		}
		if(oldMchtgId!=null&&oldMchtgId>0){
			//从原商户组中删除
			TblRouteMchtgDetailPk oid=new TblRouteMchtgDetailPk(mchtId, oldMchtgId);
			TblRouteMchtgDetail detail = tblRouteMchtgDetailDAO.get(oid);
			if(detail!=null){
				tblRouteMchtgDetailDAO.delete(oid);
				Log.log("从原商户组中删除");
			}
		}
		TblMchtBaseInf mchtBaseInf = tblMchtBaseInfDAO.get(mchtId);
		if(i==0){
			//删除原映射关系
			tblRouteRuleMapDAO.deleteByMchtId(mchtId);
			Log.log("删除原映射关系");
			//设置所有改商户的映射历史为停用
			tblRouteRuleMapHistDAO.setStatusByMchtId(mchtId,operator.getOprId());
			Log.log("设置所有改商户的映射历史为停用");
			//添加到新商户组
			TblRouteMchtgDetailPk pk=new TblRouteMchtgDetailPk(mchtId, newMchtgId);
			TblRouteMchtgDetail routeMchtgDetail=tblRouteMchtgDetailDAO.get(pk);
			if(routeMchtgDetail==null){
				routeMchtgDetail=new TblRouteMchtgDetail(pk);
			}
			routeMchtgDetail.setCrtOpr(operator.getOprId());
			routeMchtgDetail.setCrtTime(crtTime);
			routeMchtgDetail.setUptOpr(operator.getOprId());
			routeMchtgDetail.setUptTime(crtTime);
			
			if(mchtBaseInf!=null){
				routeMchtgDetail.setRunTime(mchtBaseInf.getRecUpdTs());
			}
			tblRouteMchtgDetailDAO.saveOrUpdate(routeMchtgDetail);
			Log.log("添加到新商户组");
		}
		
		
		routeRuleMap =tblRouteRuleMapDAO.getByConditions(mchtId,mchtUpbrhId,propertyId,termId);
		if(routeRuleMap!=null){
			TblRouteRuleMapHist ruleMapHist = tblRouteRuleMapHistDAO.getByConditions(mchtId,mchtUpbrhId,propertyId,termId);
			if(ruleMapHist!=null){
				ruleMapHist.setBrhId3(propertyId);
				ruleMapHist.setMchtIdUp(mchtUpbrhId);
				ruleMapHist.setMisc1(termId);
				ruleMapHist.setUptTime(crtTime);
				tblRouteRuleMapHistDAO.update(ruleMapHist);
				Log.log("如果历史记录存在，将其更新为停用状态");
			}
			routeRuleMap.setBrhId3(propertyId);
			routeRuleMap.setCrtOpr(operator.getOprId());
			routeRuleMap.setCrtTime(crtTime);
			routeRuleMap.setMisc1(termId);
			routeRuleMap.setMchtIdUp(mchtUpbrhId);
			tblRouteRuleMapDAO.update(routeRuleMap);
			Log.log("更新路由映射关系");
		}
		if(routeRuleMap==null){
			routeRuleMap=new TblRouteRuleMap();
			//映射历史
			TblRouteRuleMapHist routeRuleMapHist=new TblRouteRuleMapHist();
			routeRuleMapHist.setBrhId3(propertyId);
			routeRuleMapHist.setCrtOpr(operator.getOprId());
			routeRuleMapHist.setCrtTime(crtTime);
			routeRuleMapHist.setMchtgId(newMchtgId);
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
			Log.log("添加映射历史数据");
			//映射关系
			routeRuleMap.setBrhId3(propertyId);
			routeRuleMap.setCrtOpr(operator.getOprId());
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
			routeRuleMap.setUptOpr(operator.getOprId());
			routeRuleMap.setUptTime(crtTime);
			tblRouteRuleMapDAO.save(routeRuleMap);
			Log.log("添加映射关系");
		}
		return Constants.SUCCESS_CODE;
	}
}
