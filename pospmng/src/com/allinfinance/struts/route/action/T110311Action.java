package com.allinfinance.struts.route.action;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts2.ServletActionContext;

import com.allinfinance.bo.route.TblRouteRuleInfoBO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.dao.iface.route.TblRouteRuleInfoDAO;
import com.allinfinance.dao.iface.route.TblRouteRuleMapDAO;
import com.allinfinance.po.route.TblRouteRuleInfo;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T110311Action extends BaseAction{
	private Operator operator = (Operator) ServletActionContext.getRequest()
			.getSession().getAttribute(Constants.OPERATOR_INFO);
	private TblRouteRuleInfoBO tblRouteRuleInfoBO=(TblRouteRuleInfoBO) ContextUtil.getBean("TblRouteRuleInfoBO");
	private TblRouteRuleInfoDAO tblRouteRuleInfoDAO = (TblRouteRuleInfoDAO) ContextUtil.getBean("TblRouteRuleInfoDAO");
	private TblRouteRuleMapDAO tblRouteRuleMapDAO=(TblRouteRuleMapDAO) ContextUtil.getBean("TblRouteRuleMapDAO");
	private TblRouteRuleInfo routeRuleInfo;
	private Integer ruleId;
	
	@Override
	protected String subExecute() throws Exception {
		return null;
	}
	
	private String editTxng(String txng) {
		String ret = txng;
		if (txng == null && "".equals(txng)){
			return "";
		}

		String[] txngList = txng.split(",");
		for (int i = 0; i < txngList.length; i++){
			if ("1101".equals(txngList[i])){
				// 选择消费，将id:'3101', name:'消费撤销'也加进去
				ret += ",3101";
			} else if ("1011".equals(txngList[i])){
				// 选择预授权，将{id:'1091', name:'预授权完成'},
	            //{id:'3011', name:'预授权撤销'},
	            //{id:'3091', name:'预授权完成撤销'},也加进去
				ret += ",1091,3011,3091";
			}
		}
		
		return ret;
	}
	/**
	 * 增加路由则
	 * @return
	 * @throws IOException 
	 */
	public String add() throws IOException{
		//检查组内是有有商户未配置映射
		String countSql = "select count(*) "
				+ "  from Tbl_Route_Mchtg_detail mg "
				+ " inner join tbl_mcht_base_inf mb "
				+ "    on mg.mcht_id = mb.mcht_no "
				+ " where mg.mcht_gid = '&groupId' "
				+ "   and mg.mcht_id not in "
				+ "      (select rm.mcht_id from Tbl_Route_Rule_Map_Hist rm where rm.mchtg_id = mg.mcht_gid and rm.brh_id3 = '&propertyId' and rm.status = '0') ";
		countSql = countSql.replaceAll("&propertyId", routeRuleInfo.getBrhId3());	
		countSql = countSql.replaceAll("&groupId", routeRuleInfo.getMchtGid().toString());
		int count = Integer.parseInt(CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql));
		if(count > 0){
			writeSuccessMsg("规则未添加：组内有商户缺少映射关系！");
			return null;
		}
		try {
			//生成主键，8位number
			int id = 10000000;
			int max = tblRouteRuleInfoBO.getMax();
			
			if (tblRouteRuleInfoDAO.isOrderExist(0, routeRuleInfo.getMchtGid(),routeRuleInfo.getOrders())) {
				if (tblRouteRuleInfoDAO.isOrderExist(0, routeRuleInfo.getMchtGid(),999999)) {
					writeErrorMsg("已经存在最低优先级为‘999999’的规则，无法自动修改优先级。请手动修改优先级为‘999999’的规则的优先级，或者联系系统管理员。");
					return null;
				}
				if (!tblRouteRuleInfoDAO.updateOrders(routeRuleInfo.getMchtGid(), routeRuleInfo.getOrders())) {
					writeErrorMsg("优先顺序自动修改失败，请重试！");
					return null;
				}
			}
			
			if(max != 0){
				id = max+1;
			}
			routeRuleInfo.setRuleId(id);
			Date date=new Date();
			DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String crtTime=format.format(date);
			if (!"".equals(routeRuleInfo.getValidTime())){
				Date validDate = timeFormat.parse(routeRuleInfo.getValidTime());
				routeRuleInfo.setValidTime(format.format(validDate));
			}
			if (!"".equals(routeRuleInfo.getInvalidTime())){
				Date invalidDate = timeFormat.parse(routeRuleInfo.getInvalidTime());	
				routeRuleInfo.setInvalidTime(format.format(invalidDate));
			}			
			routeRuleInfo.setTxng(editTxng(routeRuleInfo.getTxng()));
			routeRuleInfo.setCrtTime(crtTime);
			routeRuleInfo.setUptTime(crtTime);
			routeRuleInfo.setCrtOpr(operator.getOprId());
			routeRuleInfo.setUptOpr(operator.getOprId());
			routeRuleInfo.setStatus("0");
			tblRouteRuleInfoBO.add(routeRuleInfo,operator.getOprId());
		} catch (Exception e) {
			writeErrorMsg("路由规则添加失败！");
		}
		return null;
	}
	public String update() throws IOException{
		try {
			Date date=new Date();
			DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String crtTime=format.format(date);
			
			
			if (tblRouteRuleInfoDAO.isOrderExist(routeRuleInfo.getRuleId(), routeRuleInfo.getMchtGid(), routeRuleInfo.getOrders())) {
				if (tblRouteRuleInfoDAO.isOrderExist(routeRuleInfo.getRuleId(), routeRuleInfo.getMchtGid(),999999)) {
					writeErrorMsg("已经存在最低优先级为‘999999’的规则，无法自动修改优先级。请手动修改优先级为‘999999’的规则的优先级，或者联系系统管理员。");
					return null;
				}
				if (!tblRouteRuleInfoDAO.updateOrders(routeRuleInfo.getMchtGid(), routeRuleInfo.getOrders())) {
					writeErrorMsg("优先顺序自动修改失败，请重试！");
					return null;
				}
			}
			
			if (!"".equals(routeRuleInfo.getValidTime())){
				Date validDate = timeFormat.parse(routeRuleInfo.getValidTime());
				routeRuleInfo.setValidTime(format.format(validDate));
			}
			if (!"".equals(routeRuleInfo.getInvalidTime())){
				Date invalidDate = timeFormat.parse(routeRuleInfo.getInvalidTime());	
				routeRuleInfo.setInvalidTime(format.format(invalidDate));
			}
			routeRuleInfo.setTxng(editTxng(routeRuleInfo.getTxng()));
			routeRuleInfo.setUptTime(crtTime);
			routeRuleInfo.setUptOpr(operator.getOprId());
			tblRouteRuleInfoBO.update(routeRuleInfo);
			writeSuccessMsg("路由规则更新成功！");
		} catch (Exception e) {
			writeErrorMsg("路由规则更新失败！");;
		}
		return null;
	}
	public String delete() throws IOException{
		try {
			routeRuleInfo = this.tblRouteRuleInfoDAO.get(ruleId);
			tblRouteRuleInfoBO.delete(routeRuleInfo);
			writeSuccessMsg("路由规则删除成功！");
		} catch (Exception e) {
			writeErrorMsg("路由规则删除失败！");;
		}
		return null;
	}
	
	public String start() throws IOException{
		try {
	
			routeRuleInfo = this.tblRouteRuleInfoDAO.get(ruleId);
			this.routeRuleInfo.setRuleId(this.ruleId);
			this.routeRuleInfo.setStatus("0");
			this.tblRouteRuleInfoDAO.update(routeRuleInfo);
			writeSuccessMsg("路由规则启用成功！");;
		} catch (Exception e) {
			writeErrorMsg("路由规则启用失败！");
		}
		return null;
	}

	public String stop() throws IOException{
		try {
			
			routeRuleInfo = this.tblRouteRuleInfoDAO.get(ruleId);
			
//			if (tblRouteRuleMapDAO.getMappedBrhid3(routeRuleInfo.getBrhId3()) > 0) {
//				writeErrorMsg("该规则已经有映射关系,不能停用!");
//				return null;
//			}
			
			this.routeRuleInfo.setRuleId(this.ruleId);
			this.routeRuleInfo.setStatus("1");
			this.tblRouteRuleInfoDAO.update(routeRuleInfo);
			writeSuccessMsg("路由规则停用成功！");
		} catch (Exception e) {
			writeErrorMsg("路由规则停用失败！");
		}
		return null;
	}
	
	public TblRouteRuleInfo getRouteRuleInfo() {
		return routeRuleInfo;
	}
	public void setRouteRuleInfo(TblRouteRuleInfo routeRuleInfo) {
		this.routeRuleInfo = routeRuleInfo;
	}
	public Integer getRuleId() {
		return ruleId;
	}
	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}
}
