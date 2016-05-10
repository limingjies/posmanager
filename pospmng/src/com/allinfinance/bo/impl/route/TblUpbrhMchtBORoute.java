package com.allinfinance.bo.impl.route;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.allinfinance.bo.route.TblUpbrhMchtBO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.route.TblUpbrhMchtDAO;
import com.allinfinance.po.route.TblUpbrhMcht;
import com.allinfinance.po.route.TblUpbrhMchtPk;

public class TblUpbrhMchtBORoute implements TblUpbrhMchtBO {
	private TblUpbrhMchtDAO tblUpbrhMchtDAO;
	private ICommQueryDAO commGWQueryDAO;
	
	public ICommQueryDAO getCommGWQueryDAO() {
		return commGWQueryDAO;
	}
	public void setCommGWQueryDAO(ICommQueryDAO commGWQueryDAO) {
		this.commGWQueryDAO = commGWQueryDAO;
	}
	public TblUpbrhMchtDAO getTblUpbrhMchtDAO() {
		return tblUpbrhMchtDAO;
	}
	public void setTblUpbrhMchtDAO(TblUpbrhMchtDAO tblUpbrhMchtDAO) {
		this.tblUpbrhMchtDAO = tblUpbrhMchtDAO;
	}

	@Override
	public void add(TblUpbrhMcht upbrhMcht) {
		HttpServletRequest request = ServletActionContext.getRequest();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");  
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间       
		String time = formatter.format(curDate);  
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		upbrhMcht.setCrtOpr(operator.getOprId());
		upbrhMcht.setCrtTime(time);
		upbrhMcht.setUptTime(time);
		upbrhMcht.setUptOpr(operator.getOprId());
		upbrhMcht.setStatus("0");//新增的状态为0--启用
		upbrhMcht.setRunTime(time);//设置启用时间
		/*upbrhMcht.setStatus("1");//新增的状态为1--停用
		//新增初始为停用状态，所以默认初始也加上停用类型、原因和时间字段值
		upbrhMcht.setStopType("5");//停用类型5 -- 其他
		upbrhMcht.setStopReason("");//停用原因 -- 初始默认停用
		upbrhMcht.setStopTime(time);//停用时间  */
		tblUpbrhMchtDAO.save(upbrhMcht);
	}
	
	@Override
	public void update(TblUpbrhMcht upbrhMcht) {
		HttpServletRequest request = ServletActionContext.getRequest();
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");       
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间       
		String time = formatter.format(curDate);  
		if ("1".equals(upbrhMcht.getStatus())) {
			//清空启用时间设停用时间
			//upbrhMcht.setRunTime("");//--20151103修改，依据业务提的需求，不清空这些值
			upbrhMcht.setStopTime(time);
		}else if ("0".equals(upbrhMcht.getStatus())) {
			//清空停用时间和停用类型、原因 ； 设置启用时间
			/*upbrhMcht.setStopType("");//--20151103修改，依据业务提的需求，不清空这些值
			upbrhMcht.setStopReason("");
			upbrhMcht.setStopTime("");*/
			upbrhMcht.setRunTime(time);
		}
		//更新修改时间 和 操作人ID
		upbrhMcht.setUptOpr(operator.getOprId());
		upbrhMcht.setUptTime(time);
		tblUpbrhMchtDAO.update(upbrhMcht);
	}

	public void updateInfo(TblUpbrhMcht upbrhMcht) {
		HttpServletRequest request = ServletActionContext.getRequest();
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");       
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间       
		String time = formatter.format(curDate);  
		//更新修改时间 和 操作人ID
		upbrhMcht.setUptOpr(operator.getOprId());
		upbrhMcht.setUptTime(time);
		tblUpbrhMchtDAO.update(upbrhMcht);
	}

	public void updatePk(String brhId3,TblUpbrhMchtPk pk) {
		if (brhId3.length()>12) {
			brhId3 = brhId3.substring(0,12);
		}
		String sql = " UPDATE TBL_UPBRH_MCHT SET BRH_ID3 = '"+brhId3+"' WHERE MCHT_ID_UP = '"+pk.getMchtIdUp()+"' and TERM_ID_UP = '"+pk.getTermIdUp()+"' and BRH_ID3 = '"+pk.getBrhId3()+"' ";
		tblUpbrhMchtDAO.execute(sql);
	}
	
	/**
	 * 唯一性验证：渠道商户号 +渠道终端号在 同一个支付渠道下唯一（包含停用的）
	 */
	@SuppressWarnings("unchecked")
	public List<String> checkMchtIdUpUnique(TblUpbrhMcht upbrhMcht) {
		String sql = " select * from TBL_UPBRH_MCHT where MCHT_ID_UP= '"+upbrhMcht.getPk().getMchtIdUp()+"' and substr(brh_id3, 1, 4)= '"+upbrhMcht.getPk().getBrhId3().substring(0,4)+"' and TERM_ID_UP= '"+upbrhMcht.getPk().getTermIdUp()+"'";
		return commGWQueryDAO.findBySQLQuery(sql);
	}
	
	@Override
	public void delete(TblUpbrhMcht upbrhMcht) {
		tblUpbrhMchtDAO.delete(upbrhMcht);
	}

	public TblUpbrhMcht get(TblUpbrhMchtPk key){
		return tblUpbrhMchtDAO.get(key);
	}
	
}
