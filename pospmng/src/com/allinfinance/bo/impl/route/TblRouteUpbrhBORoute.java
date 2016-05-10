package com.allinfinance.bo.impl.route;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.allinfinance.bo.route.TblRouteUpbrhBO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.route.TblRouteUpbrhDAO;
import com.allinfinance.po.route.TblRouteUpbrh;

public class TblRouteUpbrhBORoute implements TblRouteUpbrhBO {
	private TblRouteUpbrhDAO tblRouteUpbrhDAO;
	private ICommQueryDAO commGWQueryDAO;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public TblRouteUpbrhDAO getTblRouteUpbrhDAO() {
		return tblRouteUpbrhDAO;
	}
	public void setTblRouteUpbrhDAO(TblRouteUpbrhDAO tblRouteUpbrhDAO) {
		this.tblRouteUpbrhDAO = tblRouteUpbrhDAO;
	}
	public ICommQueryDAO getCommGWQueryDAO() {
		return commGWQueryDAO;
	}
	public void setCommGWQueryDAO(ICommQueryDAO commGWQueryDAO) {
		this.commGWQueryDAO = commGWQueryDAO;
	}
	
	
	@Override
	public String addCharacter(TblRouteUpbrh tbRtu) {
		String retCode = "00";
		Operator operator = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		try{
			//判断填写的性质名称是否唯一，并获取当期可用的下一个性质号
			String strSql ="select "
						+" (select count(*) cnt from TBL_ROUTE_UPBRH t where t.brh_level = '3' and substr(t.brh_id,1,8) = '&busiId' and t.name = '&charName') cnt"
						+"	 ,"
						+" (select nvl(to_char((to_number(max(t.brh_id)) + 1),'000000000000'),'&busiId'||'0001') nxtId from TBL_ROUTE_UPBRH t where t.brh_level = '3' and substr(t.brh_id,1,8) = '&busiId') nextId"
						+"	 from dual";
			strSql = strSql.replace("&charName", tbRtu.getName()).replace("&busiId", tbRtu.getBrhId());
			List<Object[]> dataList = commGWQueryDAO.findBySQLQuery(strSql);
			Iterator<Object[]> iterator = dataList.iterator();
			Object[] obj = null;
			while(iterator.hasNext()) {
				obj = iterator.next();
			}
			if(obj != null){
				if(new Integer(obj[0].toString()).intValue()==0){ //是否唯一
					//this.tblRouteUpbrh = new TblRouteUpbrh();
					tbRtu.setBrhId(obj[1].toString().trim());
					tbRtu.setBrhLevel("3");
					tbRtu.setStatus("0");
					
					tbRtu.setCrtOpr(operator.getOprId());
					tbRtu.setCrtTime(sdf.format(new Date()));
					tbRtu.setUptOpr(operator.getOprId());
					tbRtu.setUptTime(sdf.format(new Date()));
					tbRtu.setUseTime(sdf.format(new Date()));
					this.tblRouteUpbrhDAO.save(tbRtu);
				}else{
					retCode = "01";
				}
			}else{
				retCode = "02";
			}
		}catch(Exception e){
			e.printStackTrace();
			retCode = "03";
		}
		return retCode;
	}
	@Override
	public String updateCharacter(TblRouteUpbrh tbRtu) {
		Operator operator = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		String retCode = "00";
		try{
			String strSql =" select count(*) cnt from TBL_ROUTE_UPBRH t where t.brh_level = '3' and substr(t.brh_id,1,8) = substr('&charId',1,8) and t.brh_id != '&charId' and t.name = '&charName' ";
			strSql = strSql.replace("&charName", tbRtu.getName()).replace("&charId", tbRtu.getBrhId());
			List<Object[]> dataList = commGWQueryDAO.findBySQLQuery(strSql);
			Iterator<Object[]> iterator = dataList.iterator();
			Object obj = null;
			while(iterator.hasNext()) {
				obj = iterator.next();
			}
			if(obj != null){
				if(new Integer(obj.toString()).intValue()==0){ //是否唯一
					TblRouteUpbrh t = tblRouteUpbrhDAO.get(tbRtu.getBrhId());
					t.setName(tbRtu.getName());
					t.setBrhDsp(tbRtu.getBrhDsp());
					t.setUptOpr(operator.getOprId());
					t.setUptTime(sdf.format(new Date()));
					tblRouteUpbrhDAO.update(t);
				}else{
					retCode = "01";
				}
			}else{
				retCode = "02";
			}
		}catch(Exception e){
			e.printStackTrace();
			retCode = "03";
		}
		return retCode;
	}
	@Override
	public String deleteCharacter(TblRouteUpbrh tbRtu) {
		String retCode = "00";
		try{
			TblRouteUpbrh t = tblRouteUpbrhDAO.get(tbRtu.getBrhId());
			tblRouteUpbrhDAO.delete(t);
		}catch(Exception e){
			e.printStackTrace();
			retCode = "01";
		}
		return retCode;
	}
	/*
	 * (non-Javadoc)
	 * @see com.allinfinance.bo.route.TblRouteUpbrhBO#updateStatus(java.lang.String, java.lang.String)
	 */
	@Override
	public String updateStatus(String status,String brhId) {
		Operator operator = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		String retCode = "00";
		TblRouteUpbrh tblRouteUpbrh = this.tblRouteUpbrhDAO.get(brhId);
		tblRouteUpbrh.setStatus(status);
		tblRouteUpbrh.setUptTime(sdf.format(new Date()));
		if("0".equals(status)){
			tblRouteUpbrh.setUseTime(sdf.format(new Date()));
		}
		tblRouteUpbrh.setUptOpr(operator.getOprId());
		
		this.tblRouteUpbrhDAO.update(tblRouteUpbrh);
		return retCode;
	}
}
