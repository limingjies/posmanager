package com.allinfinance.bo.impl.risk;



import java.util.List;


import com.allinfinance.bo.risk.T40213BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.TblMchntInfoConstants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.risk.TblRemindRiskMchtDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtBaseInfDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtBaseInfTmpDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtSettleInfDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtSettleInfTmpDAO;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInf;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmp;
import com.allinfinance.po.risk.TblRemindRiskMcht;
import com.allinfinance.po.risk.TblRemindRiskMchtPK;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.GenerateNextId;

public class T40213BOTarget implements T40213BO {
	
	private ICommQueryDAO commQueryDAO;
	private TblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO;
	private TblMchtBaseInfDAO tblMchtBaseInfDAO;
	private TblMchtSettleInfTmpDAO tblMchtSettleInfTmpDAO;
	private TblMchtSettleInfDAO tblMchtSettleInfDAO;
	
	//商服风险提醒
	private TblRemindRiskMchtDAO tblRemindRiskMchtDAO;
	


	


	

	

	@SuppressWarnings("unchecked")
	public String remindMcht(String mchtNo,Operator operator) {
		// TODO Auto-generated method stub
//		Operator operator = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		
		String querySql="select count(1) from tbl_remind_risk_mcht "
				+ " where risk_date='"+CommonFunction.getCurrentDate()+"' "
				+ " and mcht_no='"+mchtNo+"' ";
		String count=commQueryDAO.findCountBySQLQuery(querySql);
		if(!"0".equals(count)){
			return "当天的该商户已经被风险提醒，请重新刷新选择！";
		}
		TblMchtBaseInf inf=tblMchtBaseInfDAO.get(mchtNo);
			
		String sql="select "
					+ "(select nvl(sum(f.alarm_num),0) from TBL_ALARM_MCHT f where f.card_accp_id='"+mchtNo+"' "
						+ " ) as alarm_totay,"
					+ "(select nvl(sum(j.alarm_num),0) from TBL_ALARM_MCHT j "
						+ "where j.card_accp_id='"+mchtNo+"' ) as alarm_total,"
					+ " (select count(1) from TBL_RISK_ALARM g where g.cheat_flag='1' "
						+ "and EXISTS (select * from TBL_ALARM_MCHT h "
						+ "where h.card_accp_id='"+mchtNo+"'  "
						+ "and g.alarm_id=h.alarm_id and g.alarm_seq=h.alarm_seq) ) as cheat_num "
				+ " from dual ";
		List<Object[]> dataList = commQueryDAO.findBySQLQuery(sql);
//		String todayTimes=StringUtil.isNotEmpty(dataList.get(0)[0])?"0":dataList.get(0)[0].toString();
		String totalTimes=dataList.get(0)[1].toString();
		String cheatTimes=dataList.get(0)[2].toString();
		
		TblRemindRiskMchtPK tblRemindRiskMchtPK=new TblRemindRiskMchtPK();
		tblRemindRiskMchtPK.setRemindId(GenerateNextId.getRemindMchtId());
		tblRemindRiskMchtPK.setRiskDate(CommonFunction.getCurrentDate());
		
		TblRemindRiskMcht tblRemindRiskMcht=new TblRemindRiskMcht();
		tblRemindRiskMcht.setTblRemindRiskMchtPK(tblRemindRiskMchtPK);
		tblRemindRiskMcht.setCrtOpr(operator.getOprId());
		tblRemindRiskMcht.setCrtTime(CommonFunction.getCurrentDateTime());
		tblRemindRiskMcht.setMchtNo(mchtNo);
		tblRemindRiskMcht.setRiskLvl(inf.getRislLvl());
//		tblRemindRiskMcht.setTodayTimes(Integer.parseInt(todayTimes));
		tblRemindRiskMcht.setTotalTimes(Integer.parseInt(totalTimes));
		tblRemindRiskMcht.setCheatTimes(Integer.parseInt(cheatTimes));
		tblRemindRiskMcht.setMisc("0");
		tblRemindRiskMchtDAO.save(tblRemindRiskMcht);
		
		return Constants.SUCCESS_CODE;
	}


	public String blockMcht(String mchtNo,Operator operator) {
		// TODO Auto-generated method stub
//		Operator operator = (Operator) ServletActionContext.getRequest().getSession().getAttribute(Constants.OPERATOR_INFO);
		
		
		TblMchtSettleInfTmp settleTmp=tblMchtSettleInfTmpDAO.get(mchtNo);
		TblMchtSettleInf settle=tblMchtSettleInfDAO.get(mchtNo);
		TblMchtBaseInfTmp infTmp=tblMchtBaseInfTmpDAO.get(mchtNo);
		TblMchtBaseInf inf=tblMchtBaseInfDAO.get(mchtNo);
		
		infTmp.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_STOP);
		infTmp.setUpdOprId(operator.getOprId());
		infTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		inf.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_STOP);
		inf.setUpdOprId(operator.getOprId());
		inf.setRecUpdTs(CommonFunction.getCurrentDateTime());
		
		settleTmp.setSettleType(TblMchntInfoConstants.MCHNT_SETTLE_TYPE_NO);
		settleTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		settle.setSettleType(TblMchntInfoConstants.MCHNT_SETTLE_TYPE_NO);
		settle.setRecUpdTs(CommonFunction.getCurrentDateTime());
		
		tblMchtBaseInfTmpDAO.update(infTmp);
		tblMchtBaseInfDAO.update(inf);
		tblMchtSettleInfTmpDAO.update(settleTmp);
		tblMchtSettleInfDAO.update(settle);
		
		
		return Constants.SUCCESS_CODE;
	}


	public String recoverMcht(String  mchtNo,Operator operator) {
		// TODO Auto-generated method stub
		
		TblMchtBaseInfTmp infTmp=tblMchtBaseInfTmpDAO.get(mchtNo);
		TblMchtBaseInf inf=tblMchtBaseInfDAO.get(mchtNo);
		TblMchtSettleInfTmp settleTmp=tblMchtSettleInfTmpDAO.get(mchtNo);
		TblMchtSettleInf settle=tblMchtSettleInfDAO.get(mchtNo);
		
		infTmp.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_OK);
		infTmp.setUpdOprId(operator.getOprId());
		infTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		inf.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_OK);
		inf.setUpdOprId(operator.getOprId());
		inf.setRecUpdTs(CommonFunction.getCurrentDateTime());
		settleTmp.setSettleType(TblMchntInfoConstants.MCHNT_SETTLE_TYPE_DAY);
		settleTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		settle.setSettleType(TblMchntInfoConstants.MCHNT_SETTLE_TYPE_DAY);
		settle.setRecUpdTs(CommonFunction.getCurrentDateTime());
		
		tblMchtBaseInfTmpDAO.update(infTmp);
		tblMchtBaseInfDAO.update(inf);
		tblMchtSettleInfTmpDAO.update(settleTmp);
		tblMchtSettleInfDAO.update(settle);
		
		
		return Constants.SUCCESS_CODE;
	}


	
	public String blockSettle(String mchtNo) {
		// TODO Auto-generated method stub
		
		TblMchtSettleInfTmp infTmp=tblMchtSettleInfTmpDAO.get(mchtNo);
		TblMchtSettleInf inf=tblMchtSettleInfDAO.get(mchtNo);
		infTmp.setSettleType(TblMchntInfoConstants.MCHNT_SETTLE_TYPE_NO);
		infTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		inf.setSettleType(TblMchntInfoConstants.MCHNT_SETTLE_TYPE_NO);
		inf.setRecUpdTs(CommonFunction.getCurrentDateTime());
		
		tblMchtSettleInfTmpDAO.update(infTmp);
		tblMchtSettleInfDAO.update(inf);
		
		return Constants.SUCCESS_CODE;
	}


	public String recoverSettle(String mchtNo) {
		// TODO Auto-generated method stub
		
		TblMchtSettleInfTmp infTmp=tblMchtSettleInfTmpDAO.get(mchtNo);
		TblMchtSettleInf inf=tblMchtSettleInfDAO.get(mchtNo);
		infTmp.setSettleType(TblMchntInfoConstants.MCHNT_SETTLE_TYPE_DAY);
		infTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		inf.setSettleType(TblMchntInfoConstants.MCHNT_SETTLE_TYPE_DAY);
		inf.setRecUpdTs(CommonFunction.getCurrentDateTime());
		
		tblMchtSettleInfTmpDAO.update(infTmp);
		tblMchtSettleInfDAO.update(inf);
		
		return Constants.SUCCESS_CODE;
	}



	public TblMchtBaseInfTmpDAO getTblMchtBaseInfTmpDAO() {
		return tblMchtBaseInfTmpDAO;
	}



	public void setTblMchtBaseInfTmpDAO(TblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO) {
		this.tblMchtBaseInfTmpDAO = tblMchtBaseInfTmpDAO;
	}



	public TblMchtBaseInfDAO getTblMchtBaseInfDAO() {
		return tblMchtBaseInfDAO;
	}



	public void setTblMchtBaseInfDAO(TblMchtBaseInfDAO tblMchtBaseInfDAO) {
		this.tblMchtBaseInfDAO = tblMchtBaseInfDAO;
	}



	public TblMchtSettleInfTmpDAO getTblMchtSettleInfTmpDAO() {
		return tblMchtSettleInfTmpDAO;
	}



	public void setTblMchtSettleInfTmpDAO(
			TblMchtSettleInfTmpDAO tblMchtSettleInfTmpDAO) {
		this.tblMchtSettleInfTmpDAO = tblMchtSettleInfTmpDAO;
	}



	public TblMchtSettleInfDAO getTblMchtSettleInfDAO() {
		return tblMchtSettleInfDAO;
	}



	public void setTblMchtSettleInfDAO(TblMchtSettleInfDAO tblMchtSettleInfDAO) {
		this.tblMchtSettleInfDAO = tblMchtSettleInfDAO;
	}




	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}


	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}


	
	public TblRemindRiskMchtDAO getTblRemindRiskMchtDAO() {
		return tblRemindRiskMchtDAO;
	}


	public void setTblRemindRiskMchtDAO(TblRemindRiskMchtDAO tblRemindRiskMchtDAO) {
		this.tblRemindRiskMchtDAO = tblRemindRiskMchtDAO;
	}


	

	
	
}