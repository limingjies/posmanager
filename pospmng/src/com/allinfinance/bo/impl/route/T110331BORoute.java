package com.allinfinance.bo.impl.route;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.allinfinance.bo.route.T110331BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfDAO;
import com.allinfinance.dao.iface.route.TblRouteMchtgDetailDAO;
import com.allinfinance.dao.iface.route.TblRouteRuleMapDAO;
import com.allinfinance.dao.iface.route.TblRouteRuleMapHistDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtSettleInfDAO;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.mchnt.TblMchtSettleInf;
import com.allinfinance.po.route.TblRouteMchtgDetail;
import com.allinfinance.po.route.TblRouteMchtgDetailPk;
import com.allinfinance.po.route.TblRouteRuleMap;
import com.allinfinance.po.route.TblRouteRuleMapHist;
import com.allinfinance.po.route.TblRouteRuleMapHistPk;
import com.allinfinance.system.util.ContextUtil;

public class T110331BORoute implements T110331BO {
	private TblRouteRuleMapHistDAO tblRouteRuleMapHistDAO;//=(TblRouteRuleMapHistDAO) ContextUtil.getBean("TblRouteRuleMapHistDAO");
	private ITblMchtBaseInfDAO tblMchtBaseInfDAO;//=(ITblMchtBaseInfDAO) ContextUtil.getBean("tblMchtBaseInfDAO");
	private TblRouteMchtgDetailDAO tblRouteMchtgDetailDAO;//=(TblRouteMchtgDetailDAO) ContextUtil.getBean("TblRouteMchtgDetailDAO");
	private TblRouteRuleMapDAO tblRouteRuleMapDAO;//=(TblRouteRuleMapDAO) ContextUtil.getBean("TblRouteRuleMapDAO");
	private TblMchtSettleInfDAO tblMchtSettleInfDao;
	/**
	 * 修改商户映射关系
	 */
	@Override
	public String editMchtUpbrh(Operator operator,String ruleId,String mchtId,String mchtUpbrhId,String propertyId,String termId){
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		String crtTime=format.format(date);
		int rule = Integer.parseInt(ruleId);
		
		TblRouteRuleMap routeRuleMap = tblRouteRuleMapDAO.get(rule);
		String oldTermId = routeRuleMap.getMisc1();
		String oldPropertyId = routeRuleMap.getBrhId3();
		routeRuleMap.setBrhId3(propertyId);
		routeRuleMap.setMchtIdUp(mchtUpbrhId);
		routeRuleMap.setMisc1(termId);
		routeRuleMap.setUptOpr(operator.getOprId());
		routeRuleMap.setUptTime(crtTime);
		tblRouteRuleMapDAO.update(routeRuleMap);
		//商户以及商户组查询
		TblMchtBaseInf mchtBaseInf = tblMchtBaseInfDAO.get(mchtId);
		TblRouteMchtgDetail routeMchtgDetail = tblRouteMchtgDetailDAO.getByMchtId(mchtId);
		//更新映射历史
		tblRouteRuleMapHistDAO.updateByConditions(mchtId,oldPropertyId, operator.getOprId(),oldTermId);
		
		//映射历史
		TblRouteRuleMapHist routeRuleMapHist=new TblRouteRuleMapHist();
		if(routeMchtgDetail!=null){
			routeRuleMapHist.setMchtgId(routeMchtgDetail.getPk().getMchtGid());
		}
		routeRuleMapHist.setBrhId3(propertyId);
		routeRuleMapHist.setCrtOpr(operator.getOprId());
		routeRuleMapHist.setCrtTime(crtTime);
		routeRuleMapHist.setMchtId(mchtId);
		routeRuleMapHist.setMisc1(termId);
		routeRuleMapHist.setMchtIdUp(mchtUpbrhId);
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
			return "映射关系已满！";
		}else {
			hruleId++;
		}
		hpk.setRuleId(hruleId);
		hpk.setStopTime(crtTime);
		routeRuleMapHist.setPk(hpk);
		tblRouteRuleMapHistDAO.save(routeRuleMapHist);
		return Constants.SUCCESS_CODE;
		
	}
	/**
	 * 为商户配置渠道商户
	 */
	@Override
	public String addMchtUpbrh(String oprId, String mchtId, String propertyId, String mchtUpbrhId, String termId,int index){
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		String crtTime=format.format(date);
		
		//商户信息
		TblMchtBaseInf mchtBaseInf = tblMchtBaseInfDAO.get(mchtId);
		//所属商户组查询
		TblRouteMchtgDetail routeMchtgDetail = tblRouteMchtgDetailDAO.getByMchtId(mchtId);
		tblRouteRuleMapHistDAO.updateByConditions(mchtId, mchtUpbrhId, propertyId, oprId,termId);
		//映射历史
		TblRouteRuleMapHist routeRuleMapHist=new TblRouteRuleMapHist();
		if(routeMchtgDetail!=null){
			routeRuleMapHist.setMchtgId(routeMchtgDetail.getPk().getMchtGid());
		}
		routeRuleMapHist.setBrhId3(propertyId);
		routeRuleMapHist.setCrtOpr(oprId);
		routeRuleMapHist.setCrtTime(crtTime);
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
		//主键编号生成
		Integer hruleId=tblRouteRuleMapHistDAO.getRuleId();
		if(hruleId==null){
			hruleId = 1 + index;
		}else if(hruleId==99999999){
			return Constants.FAILURE_CODE;
		}else {
			hruleId += 1 + index ;
		}
		hpk.setRuleId(hruleId);
		hpk.setStopTime(crtTime);
		routeRuleMapHist.setPk(hpk);
		tblRouteRuleMapHistDAO.save(routeRuleMapHist);
		
		//映射关系
		TblRouteRuleMap routeRuleMap=new TblRouteRuleMap();
		routeRuleMap.setBrhId3(propertyId);
		routeRuleMap.setCrtOpr(oprId);
		routeRuleMap.setCrtTime(crtTime);
		routeRuleMap.setMchtId(mchtId);
		routeRuleMap.setMisc1(termId);
		routeRuleMap.setMchtIdUp(mchtUpbrhId);
		//主键编号生成
		Integer ruleId=tblRouteRuleMapDAO.getRuleId();
		if(ruleId==null){
			ruleId = 1 + index;
		}else if(ruleId==99999999){
			return Constants.FAILURE_CODE;
		}else {
			ruleId += 1 + index;
		}
		routeRuleMap.setRuleId(ruleId);
		routeRuleMap.setUptOpr(oprId);
		routeRuleMap.setUptTime(crtTime);
		tblRouteRuleMapDAO.save(routeRuleMap);
		return Constants.SUCCESS_CODE;
		
	}
	@Override
	public String bacthAddMchtUpbrh(Operator operator,String mchtId, String mchtUpbrhId, 
			String propertyId,String termId,String groupId){
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		String crtTime=format.format(date);
		TblRouteRuleMap routeRuleMap;
		//商户以及商户组查询
		Integer mchtGid=Integer.parseInt(groupId);
		TblRouteMchtgDetailPk pk=new TblRouteMchtgDetailPk(mchtId, mchtGid);
		
		TblMchtBaseInf mchtBaseInf = tblMchtBaseInfDAO.get(mchtId);
		TblMchtSettleInf mchtSettleInf = tblMchtSettleInfDao.get(mchtId);
		TblRouteMchtgDetail tblRouteMchtgDetail = tblRouteMchtgDetailDAO.getByMchtId(mchtId);
		TblRouteMchtgDetail routeMchtgDetail;
		if(tblRouteMchtgDetail!=null){
			
			tblRouteMchtgDetailDAO.delete(tblRouteMchtgDetail);
		
			routeMchtgDetail=new TblRouteMchtgDetail(pk);
			routeMchtgDetail.setCrtOpr(operator.getOprId());
			routeMchtgDetail.setCrtTime(crtTime);
			routeMchtgDetail.setRunTime(mchtBaseInf.getRecUpdTs());
			routeMchtgDetail.setFlag1(mchtSettleInf.getBankStatement());
			routeMchtgDetail.setFlag2(mchtSettleInf.getIntegral());
			tblRouteMchtgDetailDAO.save(routeMchtgDetail);
		}
		
//		TblRouteMchtgDetail mchtgDetail=new TblRouteMchtgDetail(pk);
		//更新所有该商户下的应设历史记录
		tblRouteRuleMapHistDAO.updateByMcht(mchtId, operator.getOprId());
		
		//映射历史
		TblRouteRuleMapHist routeRuleMapHist=new TblRouteRuleMapHist();
		routeRuleMapHist.setMchtgId(mchtGid);
		routeRuleMapHist.setBrhId3(propertyId);
		routeRuleMapHist.setCrtOpr(operator.getOprId());
		routeRuleMapHist.setCrtTime(crtTime);
		routeRuleMapHist.setMchtId(mchtId);
		routeRuleMapHist.setMisc1(termId);
		routeRuleMapHist.setMchtIdUp(mchtUpbrhId);
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
		//routeRuleMap =tblRouteRuleMapDAO.getByConditions(mchtId,mchtUpbrhId,propertyId,termId);
		/*if(routeRuleMap!=null){
			routeRuleMap.setBrhId3(propertyId);
			routeRuleMap.setCrtOpr(operator.getOprId());
			routeRuleMap.setCrtTime(crtTime);
			routeRuleMap.setMchtIdUp(mchtUpbrhId);
			tblRouteRuleMapDAO.update(routeRuleMap);
			
		}*/
			routeRuleMap=new TblRouteRuleMap();
			
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
			routeRuleMap.setUptOpr(operator.getOprId());
			routeRuleMap.setUptTime(crtTime);
			tblRouteRuleMapDAO.save(routeRuleMap);
		//成功
		return Constants.SUCCESS_CODE;
		
	}
	/**
	 * 验证商户路由映射数据是否合规有效
	 */
	private String validMapData(String mchtId,String propertyId,String upMchtId,String upTermID){
		ICommQueryDAO commGWQueryDAO = (ICommQueryDAO) ContextUtil.getBean("commGWQueryDAO");
		String sql1 = "select count(1)  from tbl_mcht_base_inf mb where mb.mcht_status <> '8' and  mb.mcht_no = '" + mchtId + "'";
		String sql2 = "select count(1) from tbl_route_upbrh t where t.brh_level = '3' and t.status = '0' and t.brh_id = '" + propertyId + "'";
		String sql3 = "select count(1) "
			+ "	  from tbl_upbrh_mcht um "
			+ "	 where um.mcht_id_up = '" + upMchtId + "' "
			+ "	   and um.brh_id3 = '" + propertyId + "' "
			+ "	   and um.term_id_up = '" + upTermID + "' "
			+ "	   and um.status = '0' "
			+ "	   and exists "
			+ "	 (select 'X' from tbl_mcht_base_inf t where t.mcht_no = um.mcht_id and t.mcht_status <> '8')";
		String sql4 = "select count(1) from tbl_route_rule_map t "
			+ "	 where t.mcht_id = '"+ mchtId +"' and t.brh_id3='"+ propertyId +"' and t.mcht_id_up='"+ upMchtId +"' and t.misc1='"+ upTermID +"'";
		int cnt1 = Integer.parseInt(commGWQueryDAO.findCountBySQLQuery(sql1));
		if(cnt1 <= 0 ){
			return "该商户不存在或已注销";
		}
		int cnt2 = Integer.parseInt(commGWQueryDAO.findCountBySQLQuery(sql2));
		if(cnt2 <= 0 ){
			return "该性质不存在或已停用";
		}
		int cnt3 = Integer.parseInt(commGWQueryDAO.findCountBySQLQuery(sql3));
		if(cnt3 <= 0 ){
			return "该渠道商户不存在或已停用";
		}
		int cnt4 = Integer.parseInt(commGWQueryDAO.findCountBySQLQuery(sql4));
		if(cnt4 > 0 ){
			return "系统中已存在该映射关系";
		}
		return Constants.SUCCESS_CODE;
	}
	
	public void importMapData(Map<String,String> data,Operator operator){
		String mchtId = data.get("商户号");
		String propertyId = data.get("性质编码");
		String mchtUpbrhId = data.get("渠道商户号");
		String termId = data.get("渠道终端号");
		String recId = data.get("映射关系ID");		
		String valid = validMapData(mchtId, propertyId, mchtUpbrhId, termId);		
		if(Constants.SUCCESS_CODE.equals(valid)){	//数据有效
			String result = "";
			TblRouteRuleMap tblRouteRuleMap = tblRouteRuleMapDAO.getByConditions(mchtId, propertyId);	//取商户针对该性质的已有映射
			if(tblRouteRuleMap != null){
				recId = tblRouteRuleMap.getRuleId().toString();
			}
			if(StringUtils.isNotEmpty(recId)){	//更新映射关系
				result = editMchtUpbrh(operator,recId,mchtId,mchtUpbrhId,propertyId,termId);
			} else {	//添加映射关系
				result = addMchtUpbrh(operator.getOprId(),mchtId,propertyId,mchtUpbrhId,termId,0);
			}
			if(Constants.SUCCESS_CODE.equals(result)){
				data.put("执行结果", "00");
			}else{
				data.put("执行结果", "导入失败");
			}
		}else{
			data.put("执行结果", valid);
		}	
	}
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
	public TblMchtSettleInfDAO getTblMchtSettleInfDao() {
		return tblMchtSettleInfDao;
	}
	public void setTblMchtSettleInfDao(TblMchtSettleInfDAO tblMchtSettleInfDao) {
		this.tblMchtSettleInfDao = tblMchtSettleInfDao;
	}
}
