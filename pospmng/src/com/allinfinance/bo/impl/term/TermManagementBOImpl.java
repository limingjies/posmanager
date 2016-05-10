package com.allinfinance.bo.impl.term;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.allinfinance.bo.term.TermManagementBO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.term.TblTermInfDAO;
import com.allinfinance.dao.iface.term.TblTermInfTmpDAO;
import com.allinfinance.dao.iface.term.TblTermManagementDAO;
import com.allinfinance.po.TblTermInf;
import com.allinfinance.po.TblTermInfTmp;
import com.allinfinance.po.TblTermManagement;
import com.allinfinance.struts.pos.TblTermManagementConstants;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;

public class TermManagementBOImpl implements TermManagementBO {

	private TblTermManagementDAO tblTermManagementDAO;
	private ICommQueryDAO commQueryDAO;
	private TblTermInfDAO tblTermInfDAO;
	private TblTermInfTmpDAO tblTermInfTmpDAO;

	public void setTblTermInfTmpDAO(TblTermInfTmpDAO tblTermInfTmpDAO) {
		this.tblTermInfTmpDAO = tblTermInfTmpDAO;
	}

	public void setTblTermInfDAO(TblTermInfDAO tblTermInfDAO) {
		this.tblTermInfDAO = tblTermInfDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	public void setTblTermManagementDAO(TblTermManagementDAO tblTermManagementDAO) {
		this.tblTermManagementDAO = tblTermManagementDAO;
	}

	public String storeTerminal(HashMap map) {
		if(map == null)
			return Constants.FAILURE_CODE;
		Operator operator = (Operator)map.get("operator");
//		String batchNo = getNextBatchNo();
		
		String batchNo = map.get("batchNo").toString();
		String manufacturer =map.get("manufacturer").toString();
		String terminalType = map.get("terminalType").toString();
		String termType = map.get("termType").toString();
		String productCd = map.get("productCd").toString();
		
		String sqlAcc = "select count(*) from tbl_term_management_check " +
				"where manufaturer = '"+ manufacturer +"' and  terminal_type = '"+ terminalType +"' " +
			    "and term_type = '"+ termType +"' and app_id ='"+ batchNo +"'";
		String acc = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sqlAcc);
		
		if("0".equals(acc)){
			
			return "只能录入与所选申请单中机具类型匹配的机具！";
			
		}else{
			
			String sqlC = "select (SELECT count(*) from tbl_term_management c where a.app_id = c.batch_no and a.manufaturer=c.manufaturer and a.terminal_type= c.terminal_type and a.term_type = c.term_type)||'' as count," +
					"acc_amount from tbl_term_management_check a where a.manufaturer = '"+ manufacturer +"' and  a.terminal_type = '"+ terminalType +"' " +
				    "and a.term_type = '"+ termType +"' and a.app_id ='"+ batchNo +"'";
			List<Object[]> dataList = CommonFunction.getCommQueryDAO().findBySQLQuery(sqlC);
			
			for(int i=0;i<dataList.size();i++){
				String inAmount = (String) dataList.get(i)[0];
				String accAmount = (String) dataList.get(i)[1];
				int errAmount = Integer.valueOf(accAmount) - Integer.valueOf(inAmount);
				
				if(errAmount <=0){
					return "所选申请单中对应类型的机具入库数量已超限！";
				}
			}
			
			String sqlExist = "select count(*) from tbl_term_management " +
					"where manufaturer = '"+ manufacturer +"' and  terminal_type = '"+ terminalType +"' " +
			        "and term_type = '"+ termType +"' and batch_no ='"+ batchNo +"' and product_cd = '"+ productCd +"'";
			String exist = CommonFunction.getCommQueryDAO().findCountBySQLQuery(sqlExist);
			
			if("1".equals(exist)){
				return "同机具类型的机具序列号已存在！";
			}
		}
		
		
		TblTermManagement tblTermManagement = new TblTermManagement();
		tblTermManagement.setId(getNextId(operator.getOprBrhId(),termType));
		tblTermManagement.setBatchNo(batchNo);
		tblTermManagement.setState(TblTermManagementConstants.STAT_NEW);
		tblTermManagement.setManufacturer(manufacturer);
		tblTermManagement.setProductCd(productCd);
		tblTermManagement.setPinPad(map.get("pinPad").toString());
		tblTermManagement.setTermType(termType);
		tblTermManagement.setTerminalType(terminalType);
		if(map.get("misc") != null && !map.get("misc").toString().trim().equals(""))
			tblTermManagement.setMisc(map.get("misc").toString());
		tblTermManagement.setSignOprId("00");
		tblTermManagement.setStorOprId(operator.getOprId());
		tblTermManagement.setStorDate(CommonFunction.getCurrentDate());
		tblTermManagement.setLastUpdOprId(operator.getOprId());
		tblTermManagement.setLastUpdTs(CommonFunction.getCurrentDateTime());
//		tblTermManagement.setBrhId(operator.getOprBrhId());
		tblTermManagement.setBackOprId("-");
		tblTermManagementDAO.save(tblTermManagement);
		return Constants.SUCCESS_CODE_CUSTOMIZE+"申请单号："+batchNo;
	}
	
	public String storeTerminals(HashMap map) {
		if(map == null)
			return Constants.FAILURE_CODE;
		List<TblTermManagement> list = new ArrayList<TblTermManagement>();
		int num = Integer.parseInt(map.get("number").toString());
		Operator operator = (Operator)map.get("operator");
		Long startProductCd = Long.valueOf(map.get("startProductCd").toString());
		String batchNo = getNextBatchNo();
		for (int i=0;i<num;i++)
		{	
			String termType = map.get("termType").toString();
			TblTermManagement tblTermManagement = new TblTermManagement();
			tblTermManagement.setId(getNextId(operator.getOprBrhId(),termType));
			tblTermManagement.setBatchNo(batchNo);
			tblTermManagement.setState(TblTermManagementConstants.STAT_NEW);
			tblTermManagement.setManufacturer(map.get("manufacturer").toString());
			tblTermManagement.setProductCd(startProductCd.toString());
			tblTermManagement.setPinPad(map.get("pinPad").toString());
			tblTermManagement.setTermType(map.get("termType").toString());
			tblTermManagement.setTerminalType(map.get("terminalType").toString());
			if(map.get("misc") != null && !map.get("misc").toString().trim().equals(""))
				tblTermManagement.setMisc(map.get("misc").toString());
			tblTermManagement.setStorOprId(operator.getOprId());
			tblTermManagement.setStorDate(CommonFunction.getCurrentDate());
			tblTermManagement.setLastUpdOprId(operator.getOprId());
			tblTermManagement.setLastUpdTs(CommonFunction.getCurrentDateTime());
//			tblTermManagement.setBrhId(operator.getOprBrhId());
			tblTermManagement.setBackOprId("-");
			startProductCd++;
			list.add(tblTermManagement);
		}
		tblTermManagementDAO.saveBatch(list);
		return Constants.SUCCESS_CODE_CUSTOMIZE+"申请单号："+batchNo;
	}
	
	public String getNextId(String brhId,String termType) {
		if(brhId == null || brhId.trim().equals(""))
			return Constants.FAILURE_CODE;
		if(brhId.length()>2)
			brhId = brhId.substring(0, 2);
		String sql = "select SEQ_TERM_MANAGEMENT.nextval from dual";
		List list = commQueryDAO.findBySQLQuery(sql);
		String nextId = "0";
		if(list != null && !list.isEmpty())
		{
			String num = list.get(0).toString();
			nextId = CommonFunction.fillString(num, '0', 5, false);
		}
		if("K".equals(termType)){
			return "P"+ brhId + nextId;
		}else {
			return "T"+ brhId + nextId;
		}
		
	}
	
	public String invalidTerminal(HashMap map) {
		if(map == null)
			return Constants.FAILURE_CODE;
		String termNo = map.get("termNo")==null?"":map.get("termNo").toString();
		String misc = map.get("misc")==null?"":map.get("misc").toString();
		if(termNo.equals(""))
			return Constants.FAILURE_CODE;
		Operator operator = (Operator)map.get("operator");
		TblTermManagement tblTermManagement = tblTermManagementDAO.get(termNo);
		List list0 = tblTermInfTmpDAO.findByHQLQuery("from TblTermInfTmp t where t.TermIdId='"+termNo+"'");
		List list1 = tblTermInfDAO.findByHQLQuery("from TblTermInf t where t.TermIdId='"+termNo+"'");
		if(list1!=null&&!list1.isEmpty()&&list0!=null&&!list0.isEmpty())
		{
			TblTermInfTmp tblTermInfTmp = (TblTermInfTmp) list0.get(0);
			TblTermInf tblTermInf = (TblTermInf) list1.get(0);
			tblTermInf.setProductCd(Constants.DEFAULT);
			tblTermInf.setTermIdId(Constants.DEFAULT);
			tblTermInf.setTermFactory(Constants.DEFAULT);
			tblTermInf.setTermMachTp(Constants.DEFAULT);
			tblTermInfTmp.setProductCd(Constants.DEFAULT);
			tblTermInfTmp.setTermIdId(Constants.DEFAULT);
			tblTermInfTmp.setTermFactory(Constants.DEFAULT);
			tblTermInfTmp.setTermMachTp(Constants.DEFAULT);
			tblTermInfDAO.update(tblTermInf);
			tblTermInfTmpDAO.update(tblTermInfTmp);
		}
		tblTermManagement.setState(TblTermManagementConstants.STAT_OUT);
		tblTermManagement.setInvalidDate(CommonFunction.getCurrentDate());
		tblTermManagement.setInvalidOprId(operator.getOprId());
		tblTermManagement.setLastUpdOprId(operator.getOprId());
		tblTermManagement.setMisc(misc);
		tblTermManagement.setLastUpdTs(CommonFunction.getCurrentDateTime());
		tblTermManagementDAO.update(tblTermManagement);
		return Constants.SUCCESS_CODE;
	}

	public String upTerminal(HashMap map) {
		if(map == null)
			return Constants.FAILURE_CODE;
		String termNo = map.get("termNo")==null?"":map.get("termNo").toString();
		String misc = map.get("misc")==null?"":map.get("misc").toString();
		if(termNo.equals(""))
			return Constants.FAILURE_CODE;
		Operator operator = (Operator)map.get("operator");
		TblTermManagement tblTermManagement = tblTermManagementDAO.get(termNo);
//		String brhId = tblTermManagement.getBrhId();
		String vind = tblTermManagement.getSignOprId();
		
		/*if(!brhId.equals(operator.getOprBrhId())) {
			return "只有本行操作员才可以上缴本行机具，机具所属:" + brhId;
		}*/
//		List list = CommonFunction.getCommQueryDAO().findBySQLQuery("select brh_level from tbl_brh_info where brh_id='"+ brhId +"'");
//		List list1 = CommonFunction.getCommQueryDAO().findBySQLQuery("select up_brh_id from tbl_brh_info where brh_id='"+ brhId +"'");
		
		/*if(!list.isEmpty() && !"".equals(list) && !list1.isEmpty() && !"".equals(list1)){
			String brhLevel = (String)list.get(0);
			String upBrh = (String)list1.get(0);
		
			if("0".equals(brhLevel.trim()) ||  upBrh.isEmpty() || "".equals(upBrh.trim())) {
				return TblTermManagementConstants.T30301_04;
			}else {
				tblTermManagement.setBackOprId(upBrh);
			}
		}else{
			return TblTermManagementConstants.T30305_02;
		}*/
		tblTermManagement.setState(TblTermManagementConstants.STAT_UP);
		tblTermManagement.setLastUpdOprId(operator.getOprId());
		tblTermManagement.setMisc(misc);
		tblTermManagement.setLastUpdTs(CommonFunction.getCurrentDateTime());
		tblTermManagement.setBatchNo("");//清空申请单号
		tblTermManagementDAO.update(tblTermManagement);
		return Constants.SUCCESS_CODE;
	}
	
	public String downTerminal(HashMap map) {
		if(map == null)
			return Constants.FAILURE_CODE;
		String termNo = map.get("termNo")==null?"":map.get("termNo").toString();
		String misc = map.get("misc")==null?"":map.get("misc").toString();
		String aimBrh = map.get("brhId")==null?"":map.get("brhId").toString();
		if(termNo.equals(""))
			return Constants.FAILURE_CODE;
		Operator operator = (Operator)map.get("operator");
		TblTermManagement tblTermManagement = tblTermManagementDAO.get(termNo);
//		String brhId = tblTermManagement.getBrhId();
		String vind = tblTermManagement.getSignOprId();
		
		/*if(aimBrh.equals(brhId)){
			return "已经拥有的终端，不能再下发给自己！";
		}*/
		
		if(!operator.getOprBrhLvl().equals("0")) {
			return "只有钱宝金融操作员可以下发POS机具。";
		}
		/*if(!brhId.equals(operator.getOprBrhId())) {
			return "只有本行操作员才可以下发本行机具，机具所属:" + brhId;
		}*/
//		List list = CommonFunction.getCommQueryDAO().findBySQLQuery("select brh_level from tbl_brh_info where brh_id='"+ brhId +"'");
		String brhLevel = null;//(String)list.get(0);
		if("2".equals(brhLevel.trim())) {
			return TblTermManagementConstants.T30301_03;
		}
		tblTermManagement.setState(TblTermManagementConstants.STAT_DOWN);
		tblTermManagement.setLastUpdOprId(operator.getOprId());
		tblTermManagement.setMisc(misc);
		tblTermManagement.setLastUpdTs(CommonFunction.getCurrentDateTime());
		tblTermManagement.setBackOprId((String)map.get("brhId"));//目标机构
		tblTermManagement.setBatchNo("");//清空申请单号
		tblTermManagementDAO.update(tblTermManagement);
		return Constants.SUCCESS_CODE;
	}
	
	public String revTerminal(HashMap map) {
		if(map == null)
			return Constants.FAILURE_CODE;
		String termNo = map.get("termNo")==null?"":map.get("termNo").toString();
		String misc = map.get("misc")==null?"":map.get("misc").toString();
		if(termNo.equals(""))
			return Constants.FAILURE_CODE;
		Operator operator = (Operator)map.get("operator");
		TblTermManagement tblTermManagement = tblTermManagementDAO.get(termNo);
		
		String brhId = null;//tblTermManagement.getBrhId();
		String stat = tblTermManagement.getState();
		
		List list = CommonFunction.getCommQueryDAO().findBySQLQuery("select brh_level from tbl_brh_info where brh_id='"+ brhId +"'");
		String brhLevel = (String)list.get(0);
		String oprBrhLevel = operator.getOprBrhLvl();
		
		if(tblTermManagement.getState().equals(TblTermManagementConstants.STAT_UP)) {//已上缴、待接收
			if("1".equals(brhLevel)) {
				if(!"0".equals(oprBrhLevel)) return TblTermManagementConstants.T30301_05;
			} else if("2".equals(brhLevel)) {
				if(!"1".equals(oprBrhLevel)) return TblTermManagementConstants.T30301_06;
			} else {
				return Constants.FAILURE_CODE;
			}
		} else if(tblTermManagement.getState().equals(TblTermManagementConstants.STAT_DOWN)) {//已下发、待接收
			if("0".equals(brhLevel)) {
				if(!"1".equals(oprBrhLevel)) return TblTermManagementConstants.T30301_07;
			} else if("1".equals(brhLevel)) {
				if(!"2".equals(oprBrhLevel)) return TblTermManagementConstants.T30301_08;
			} else {
				return Constants.FAILURE_CODE;
			}
		} else {
			return Constants.FAILURE_CODE;
		}
		tblTermManagement.setState(TblTermManagementConstants.STAT_NEW);
		tblTermManagement.setLastUpdOprId(operator.getOprId());
		tblTermManagement.setMisc(misc);
		tblTermManagement.setLastUpdTs(CommonFunction.getCurrentDateTime());
//		tblTermManagement.setBrhId(operator.getOprBrhId());
		tblTermManagement.setBackOprId("-");//目标机构清空
		tblTermManagement.setBatchNo((String)map.get("batchNo"));
//		tblTermManagement.setReciOprId(brhId);//来源
//		tblTermManagement.setReciDate(CommonFunction.getCurrentDateTime().substring(0, 8));//接收日期
//
//		tblTermManagement.setStorOprId(operator.getOprId());//入库操作员
//		tblTermManagement.setStorDate(CommonFunction.getCurrentDateTime().substring(0, 8));//入库日期

		tblTermManagementDAO.update(tblTermManagement);
		return Constants.SUCCESS_CODE;
	}
	
	public List<TblTermManagement> queryTerminal(String manufacturer, String terminalType,
			int number, String termType) {
		StringBuffer hql = new StringBuffer("from TblTermManagement t where t.TermType = '");
		hql.append(termType.substring(0, 1));
		hql.append("'");
		hql.append(" and t.State = '");
		hql.append(TblTermManagementConstants.TERM_STATE_NORMAL);
		hql.append("'");
		if(manufacturer != null && !"".equals(manufacturer))
		{
			hql.append(" and t.Manufacturer ='").append(manufacturer).append("'");
		}
		if(terminalType != null && !"".equals(terminalType))
		{
			hql.append(" and t.TerminalType ='").append(terminalType).append("'");
		}
		hql.append(" order by t.Id");
		List <TblTermManagement> list = tblTermManagementDAO.findByHQLQuery(hql.toString(), number);
		return list;
	}
	
	public boolean updTerminal(TblTermManagement terminal, String state,String oprId,String mech) {
		terminal.setState(state);
		terminal.setMechNo(mech);
		terminal.setLastUpdOprId(oprId);
		terminal.setLastUpdTs(CommonFunction.getCurrentDateTime());
		tblTermManagementDAO.update(terminal);
		return true;
	}


	public String getNextBatchNo() {
		String sql = "select SEQ_TERMINAL_BATCH.nextval from dual";
		List list = commQueryDAO.findBySQLQuery(sql);
		String nextId = "0000";
		if(list != null && !list.isEmpty())
		{
			String num = list.get(0).toString();
			nextId = CommonFunction.fillString(num, '0', 4, false);
		}
		return CommonFunction.getCurrentDate()+nextId;
	}

	public String reciTerminals(String batchNo, String termId, String mchnNo,
			String oprId) {
// 		update Tbl_Term_Management set MECH_NO='mchnNo',STATE='3',RECI_OPR_ID='oprId',
//		RECI_DATE='yyyymmdd',LAST_UPD_OPR_ID='oprId',LAST_UPD_TS=new TimeStamp()  
//		where BATCH_NO='batchNo' and STATE='2'
		if(batchNo == null||batchNo.trim().equals(""))
			return Constants.FAILURE_CODE;
		if(mchnNo == null||mchnNo.trim().equals(""))
			return Constants.FAILURE_CODE;
		if(batchNo == null||batchNo.trim().equals(""))
			return Constants.FAILURE_CODE;
		if(oprId == null||oprId.trim().equals(""))
			return Constants.FAILURE_CODE;
		
		StringBuffer sql = new StringBuffer("update Tbl_Term_Management ");
		sql.append("set MECH_NO='").append(mchnNo).append("',");
		sql.append("STATE='").append(TblTermManagementConstants.TERM_STATE_RECI).append("',");
		sql.append("RECI_OPR_ID='").append(oprId).append("',");
		sql.append("RECI_DATE='").append(CommonFunction.getCurrentDate()).append("',");
		sql.append("LAST_UPD_OPR_ID='").append(oprId).append("',");
		sql.append("LAST_UPD_TS=to_timestamp('").append(CommonFunction.getCurrentDateTimeForShow()).append("','yyyy-mm-dd hh24:mi:ss')");
		sql.append(" where BATCH_NO='").append(batchNo).append("' ");
		sql.append("and STATE='").append(TblTermManagementConstants.TERM_STATE_RECI_UNCHECK).append("'");
		commQueryDAO.excute(sql.toString());
		return Constants.SUCCESS_CODE;
	}

	public String reciTerminal(String batchNo, String termId, String mechNo,
			String oprId) {
		TblTermManagement tblTermManagement = tblTermManagementDAO.get(termId);
		if(tblTermManagement == null)
			return Constants.FAILURE_CODE;
		
		tblTermManagement.setState(TblTermManagementConstants.TERM_STATE_RECI);
		tblTermManagement.setMechNo(mechNo);
		tblTermManagement.setReciOprId(oprId);
		tblTermManagement.setReciDate(CommonFunction.getCurrentDate());
		tblTermManagement.setLastUpdOprId(oprId);
		tblTermManagement.setLastUpdTs(CommonFunction.getCurrentDateTime());
		
		tblTermManagementDAO.update(tblTermManagement);
		return Constants.SUCCESS_CODE;
	}

	public String refuseTerminal(String batchNo, String termId, String mechNo,
			String oprId) {
		TblTermManagement tblTermManagement = tblTermManagementDAO.get(termId);
		if(tblTermManagement == null)
			return Constants.FAILURE_CODE;
		
		tblTermManagement.setState(TblTermManagementConstants.TERM_STATE_NORMAL);
		tblTermManagement.setMechNo(mechNo);
		tblTermManagement.setLastUpdOprId(oprId);
		tblTermManagement.setLastUpdTs(CommonFunction.getCurrentDateTime());
		
		tblTermManagementDAO.update(tblTermManagement);
		return Constants.SUCCESS_CODE;
	}

	public String refuseTerminals(String batchNo, String termId, String mchnNo,
			String oprId) {
		if(batchNo == null||batchNo.trim().equals(""))
			return Constants.FAILURE_CODE;
		if(mchnNo == null||mchnNo.trim().equals(""))
			return Constants.FAILURE_CODE;
		if(batchNo == null||batchNo.trim().equals(""))
			return Constants.FAILURE_CODE;
		if(oprId == null||oprId.trim().equals(""))
			return Constants.FAILURE_CODE;
		
		StringBuffer sql = new StringBuffer("update Tbl_Term_Management ");
		sql.append("set MECH_NO='").append(mchnNo).append("',");
		sql.append("BATCH_NO='',");
		sql.append("STATE='").append(TblTermManagementConstants.TERM_STATE_NORMAL).append("',");
		sql.append("LAST_UPD_OPR_ID='").append(oprId).append("',");
		sql.append("LAST_UPD_TS=to_timestamp('").append(CommonFunction.getCurrentDateTimeForShow()).append("','yyyy-mm-dd hh24:mi:ss')");
		sql.append(" where BATCH_NO='").append(batchNo).append("' ");
		sql.append("and STATE='").append(TblTermManagementConstants.TERM_STATE_RECI_UNCHECK).append("'");
		commQueryDAO.excute(sql.toString());
		return Constants.SUCCESS_CODE;
	}

	public String signTerminal(String batchNo, String termId, String oprId) {
		TblTermManagement tblTermManagement = tblTermManagementDAO.get(termId);
		if(tblTermManagement == null)
			return Constants.FAILURE_CODE;
		
		tblTermManagement.setState(TblTermManagementConstants.TERM_STATE_SIGNED);
		tblTermManagement.setSignOprId(oprId);
		tblTermManagement.setSignDate(CommonFunction.getCurrentDate());
		tblTermManagement.setLastUpdOprId(oprId);
		tblTermManagement.setLastUpdTs(CommonFunction.getCurrentDateTime());
		
		tblTermManagementDAO.update(tblTermManagement);
		return Constants.SUCCESS_CODE;
	}

	public String signTerminals(String batchNo, String termId, String oprId) {
		if(batchNo == null||batchNo.trim().equals(""))
			return Constants.FAILURE_CODE;
		if(batchNo == null||batchNo.trim().equals(""))
			return Constants.FAILURE_CODE;
		if(oprId == null||oprId.trim().equals(""))
			return Constants.FAILURE_CODE;
		
		StringBuffer sql = new StringBuffer("update Tbl_Term_Management ");
		sql.append("set STATE='").append(TblTermManagementConstants.TERM_STATE_SIGNED).append("',");
		sql.append("SIGN_OPR_ID='").append(oprId).append("',");
		sql.append("SIGN_DATE='").append(CommonFunction.getCurrentDate()).append("',");
		sql.append("LAST_UPD_OPR_ID='").append(oprId).append("',");
		sql.append("LAST_UPD_TS=to_timestamp('").append(CommonFunction.getCurrentDateTimeForShow()).append("','yyyy-mm-dd hh24:mi:ss')");
		sql.append(" where BATCH_NO='").append(batchNo).append("' ");
		sql.append("and STATE='").append(TblTermManagementConstants.TERM_STATE_RECI).append("'");
		commQueryDAO.excute(sql.toString());
		return Constants.SUCCESS_CODE;
	}

	public String bankTermianl(int action, String termId,Operator operator) {
		TblTermManagement tblTermManagement = tblTermManagementDAO.get(termId);
		if(tblTermManagement == null)
			return Constants.FAILURE_CODE;
		tblTermManagement.setBackOprId(operator.getOprId());
		tblTermManagement.setBankDate(CommonFunction.getCurrentDate());
		if(action == 0)
		{
			updTerminal(tblTermManagement, TblTermManagementConstants.TERM_STATE_NORMAL,operator.getOprId(),"");
		}
		if(action == 1)
		{
			updTerminal(tblTermManagement, TblTermManagementConstants.TERM_STATE_INVALID,operator.getOprId(),"");
		}
		if(action == 2)
		{
			updTerminal(tblTermManagement, TblTermManagementConstants.TERM_STATE_RECI_UNCHECK,operator.getOprId(),tblTermManagement.getMechNo());
		}
		return Constants.SUCCESS_CODE;
	}

	public TblTermManagement getTerminal(String termId) {
		return tblTermManagementDAO.get(termId);
	}
	
	public boolean refuseTerm(String termId,String oprId) {
		TblTermManagement tblTermManagement = tblTermManagementDAO.get(termId);
		tblTermManagement.setState(TblTermManagementConstants.TERM_STATE_SIGNED);
		tblTermManagement.setLastUpdOprId(oprId);
		tblTermManagement.setLastUpdTs(CommonFunction.getCurrentDateTime());
		return true;
	}

	public boolean bindTermInfo(TblTermManagement tblTermManagement,
			String oprId, TblTermInf tblTermInf, TblTermInfTmp tblTermInfTmp) {
		updTerminal(tblTermManagement, TblTermManagementConstants.STAT_USE,oprId,tblTermInf.getMchtCd().trim());
		
		if(tblTermInf.getEquipInvId() == null){
			tblTermInf.setEquipInvId("NN");
		}
		String bindFlagMana = tblTermInf.getEquipInvId().substring(0,1);
		String bindFlagPin = tblTermInf.getEquipInvId().substring(1,2);
		
		if(!"K".equals(tblTermManagement.getTermType())){
			tblTermInf.setProductCd(tblTermManagement.getProductCd());
			tblTermInf.setTermIdId(tblTermManagement.getId());
			tblTermInf.setTermFactory(tblTermManagement.getManufacturer());
			tblTermInf.setTermMachTp(tblTermManagement.getTerminalType());
			
			
			tblTermInfTmp.setProductCd(tblTermManagement.getProductCd());
			tblTermInfTmp.setTermIdId(tblTermManagement.getId());
			tblTermInfTmp.setTermFactory(tblTermManagement.getManufacturer());
			tblTermInfTmp.setTermMachTp(tblTermManagement.getTerminalType());
			
			
			if("N".equals(tblTermManagement.getPinPad())){
				tblTermInf.setEquipInvId("YY");
				tblTermInfTmp.setEquipInvId("YY");
			}else {
				tblTermInf.setEquipInvId("Y"+bindFlagPin);
				tblTermInfTmp.setEquipInvId("Y"+bindFlagPin);
			}
		}else {
			tblTermInf.setEquipInvId(bindFlagMana+"Y");
			tblTermInf.setEquipInvNm(tblTermManagement.getId());
			tblTermInfTmp.setEquipInvId(bindFlagMana+"Y");
			tblTermInfTmp.setEquipInvNm(tblTermManagement.getId());
		}
			
			
		if(tblTermInf == null)
			return false;
		tblTermInfTmpDAO.update(tblTermInfTmp);
		tblTermInfDAO.update(tblTermInf);
		return true;
	}

	public boolean unbindTermInfo(TblTermManagement tblTermManagement,
			String oprId, TblTermInf tblTermInf, TblTermInfTmp tblTermInfTmp) {
		
		updTerminal(tblTermManagement, TblTermManagementConstants.STAT_NEW, oprId, "");
		
		String bindFlagMana = tblTermInf.getEquipInvId().substring(0,1);
		String bindFlagPin = tblTermInf.getEquipInvId().substring(1,2);
		
		if("E".equals(tblTermManagement.getTermType()) || "P".equals(tblTermManagement.getTermType())){
			tblTermInf.setProductCd("-");
			tblTermInf.setTermIdId("-");
			tblTermInf.setTermFactory("-");
			tblTermInf.setTermMachTp("-");
			
			
			tblTermInfTmp.setProductCd("-");
			tblTermInfTmp.setTermIdId("-");
			tblTermInfTmp.setTermFactory("-");
			tblTermInfTmp.setTermMachTp("-");
			
			
			if("N".equals(tblTermManagement.getPinPad())){
				tblTermInf.setEquipInvId("NN");
				tblTermInfTmp.setEquipInvId("NN");
			}else {
				tblTermInf.setEquipInvId("N"+bindFlagPin);
				tblTermInfTmp.setEquipInvId("N"+bindFlagPin);
			}
		}else if("K".equals(tblTermManagement.getTermType())){
			tblTermInf.setEquipInvId(bindFlagMana+"N");
			tblTermInf.setEquipInvNm("");
			tblTermInfTmp.setEquipInvId(bindFlagMana+"N");
			tblTermInfTmp.setEquipInvNm("");
		}
		
		if(tblTermInf == null)
			return false;
		tblTermInfTmpDAO.update(tblTermInfTmp);
		tblTermInfDAO.update(tblTermInf);
		return true;
	}

}