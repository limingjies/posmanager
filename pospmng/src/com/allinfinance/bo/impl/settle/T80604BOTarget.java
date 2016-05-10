package com.allinfinance.bo.impl.settle;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.bo.settle.T80604BO;
import com.allinfinance.common.Constants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.settle.TblZthDtlDAO;
import com.allinfinance.po.settle.TblZth;
import com.allinfinance.po.settle.TblZthDtl;
import com.allinfinance.po.settle.TblZthDtlPK;

public class T80604BOTarget implements T80604BO {
	


	private ICommQueryDAO commQueryDAO;
	private TblZthDtlDAO tblZthDtlDAO;
	private static Logger log = Logger.getLogger(T80604BOTarget.class);
	
	
	public synchronized String start(String settleDate) throws Exception {
		// TODO Auto-generated method stub
		
		//判断清算日期
		String resCode=isDate(settleDate);
		if(!Constants.SUCCESS_CODE.equals(resCode)){
			return resCode;
		}
		
		List<TblZth> zthList=new ArrayList<TblZth>();
		List<TblZthDtl> zthDtlList=new ArrayList<TblZthDtl>();
		TblZth tblZth=null;
		TblZthDtl tblZthDtl=null;
		TblZthDtlPK tblZthDtlPK=null;
		List<Object[]> dtlList=null;
		DecimalFormat df=new DecimalFormat("0.00");
		
		//够扣的商户(商户清算)
		List<Object[]> mchtDataList=getMchtList(settleDate);
		if(mchtDataList!=null&&mchtDataList.size()>0){
			for (Object[] objects : mchtDataList) {
				dtlList=getDtlList(settleDate,objects[0].toString());
				for (Object[] objDtl : dtlList) {
					tblZth=new TblZth();
					tblZth.setInstDate(objDtl[0].toString());
					tblZth.setSysSeqNum(objDtl[1].toString());
					tblZth.setAmt1(objDtl[2].toString());
					tblZth.setEflag("1");
					tblZth.setDdate(settleDate);
					tblZth.setF42(objDtl[5].toString());
					
					tblZthDtlPK=new TblZthDtlPK();
					tblZthDtlPK.setInstDate(objDtl[0].toString());
					tblZthDtlPK.setSysSeqNum(objDtl[1].toString());
					tblZthDtlPK.setSettlmtDate(settleDate);
					tblZthDtl=new TblZthDtl(); 
					tblZthDtl.setTblZthDtlPK(tblZthDtlPK);
					tblZthDtl.setAmt(new BigDecimal(objDtl[4].toString()));
					tblZthDtl.setMchtNo(objDtl[5].toString());
					
					zthList.add(tblZth);
					zthDtlList.add(tblZthDtl);
				}
			}
		}
		
		//够扣的商户(机构清算)
		List<Object[]> brhDataList=getBrhList(settleDate);
		if(brhDataList!=null&&brhDataList.size()>0){
			for (Object[] objects : brhDataList) {
				dtlList=getDtlBrhList(settleDate,objects[0].toString());
				for (Object[] objDtl : dtlList) {
					tblZth=new TblZth();
					tblZth.setInstDate(objDtl[0].toString());
					tblZth.setSysSeqNum(objDtl[1].toString());
					tblZth.setAmt1(objDtl[2].toString());
					tblZth.setEflag("1");
					tblZth.setDdate(settleDate);
					tblZth.setF42(objDtl[5].toString());
					
					tblZthDtlPK=new TblZthDtlPK();
					tblZthDtlPK.setInstDate(objDtl[0].toString());
					tblZthDtlPK.setSysSeqNum(objDtl[1].toString());
					tblZthDtlPK.setSettlmtDate(settleDate);
					tblZthDtl=new TblZthDtl(); 
					tblZthDtl.setTblZthDtlPK(tblZthDtlPK);
					tblZthDtl.setAmt(new BigDecimal(objDtl[4].toString()));
					tblZthDtl.setMchtNo(objDtl[5].toString());
					
					zthList.add(tblZth);
					zthDtlList.add(tblZthDtl);
				}
			}
		}
		
		
		//不够扣的商户(商户清算)
		List<Object[]> mchtErrDataList=getMchtErrList(settleDate);
		if(mchtErrDataList!=null&&mchtErrDataList.size()>0){
			for (Object[] objects : mchtErrDataList) {
				dtlList=getDtlList(settleDate,objects[0].toString());
				double mchtAmt=Double.parseDouble(objects[3].toString());
				List<Integer> tmpList=new ArrayList<Integer>();
				for (int i = 0; i < dtlList.size(); i++) {
//				for (Object[] objDtl : dtlList) {
					if(mchtAmt>=Double.parseDouble(dtlList.get(i)[4].toString())){
						mchtAmt=mchtAmt-Double.parseDouble(dtlList.get(i)[4].toString());
						tblZthDtlPK=new TblZthDtlPK();
						tblZthDtlPK.setInstDate(dtlList.get(i)[0].toString());
						tblZthDtlPK.setSysSeqNum(dtlList.get(i)[1].toString());
						tblZthDtlPK.setSettlmtDate(settleDate);
						tblZthDtl=new TblZthDtl(); 
						tblZthDtl.setTblZthDtlPK(tblZthDtlPK);
						tblZthDtl.setAmt(new BigDecimal(dtlList.get(i)[4].toString()));
						tblZthDtl.setMchtNo(dtlList.get(i)[5].toString());
						
						tblZth=new TblZth();
						tblZth.setInstDate(dtlList.get(i)[0].toString());
						tblZth.setSysSeqNum(dtlList.get(i)[1].toString());
						tblZth.setF42(dtlList.get(i)[5].toString());
						tblZth.setEflag("1");
						tblZth.setAmt1(dtlList.get(i)[2].toString());
						tblZth.setDdate(settleDate);

						zthDtlList.add(tblZthDtl);
						zthList.add(tblZth);
					}else{
						tmpList.add(i);
					}
					
					if((i+1)==dtlList.size()){
						tblZthDtlPK=new TblZthDtlPK();
						tblZthDtlPK.setInstDate(dtlList.get(tmpList.get(0))[0].toString());
						tblZthDtlPK.setSysSeqNum(dtlList.get(tmpList.get(0))[1].toString());
						tblZthDtlPK.setSettlmtDate(settleDate);
						tblZthDtl=new TblZthDtl(); 
						tblZthDtl.setTblZthDtlPK(tblZthDtlPK);
						tblZthDtl.setAmt(new BigDecimal(mchtAmt));
						tblZthDtl.setMchtNo(dtlList.get(tmpList.get(0))[5].toString());
						
						tblZth=new TblZth();
						tblZth.setInstDate(dtlList.get(tmpList.get(0))[0].toString());
						tblZth.setSysSeqNum(dtlList.get(tmpList.get(0))[1].toString());
						tblZth.setF42(dtlList.get(tmpList.get(0))[5].toString());
						tblZth.setEflag("2");
						tblZth.setAmt1(df.format(Double.parseDouble(dtlList.get(tmpList.get(0))[3].toString())+mchtAmt));
						tblZth.setDdate("");
						
						zthDtlList.add(tblZthDtl);
						zthList.add(tblZth);
					}
				}
			}
		}
		
		
		//不够扣的商户(机构清算)
		List<Object[]> brhErrDataList=getBrhErrList(settleDate);
		if(brhErrDataList!=null&&brhErrDataList.size()>0){
			for (Object[] objects : brhErrDataList) {
				dtlList=getDtlBrhList(settleDate,objects[0].toString());
				double brhAmt=Double.parseDouble(objects[2].toString());
				List<Integer> tmpList=new ArrayList<Integer>();
				for (int i = 0; i < dtlList.size(); i++) {
//						for (Object[] objDtl : dtlList) {
					if(brhAmt>=Double.parseDouble(dtlList.get(i)[4].toString())){
						brhAmt=brhAmt-Double.parseDouble(dtlList.get(i)[4].toString());
						tblZthDtlPK=new TblZthDtlPK();
						tblZthDtlPK.setInstDate(dtlList.get(i)[0].toString());
						tblZthDtlPK.setSysSeqNum(dtlList.get(i)[1].toString());
						tblZthDtlPK.setSettlmtDate(settleDate);
						tblZthDtl=new TblZthDtl(); 
						tblZthDtl.setTblZthDtlPK(tblZthDtlPK);
						tblZthDtl.setAmt(new BigDecimal(dtlList.get(i)[4].toString()));
						tblZthDtl.setMchtNo(dtlList.get(i)[5].toString());
						
						tblZth=new TblZth();
						tblZth.setInstDate(dtlList.get(i)[0].toString());
						tblZth.setSysSeqNum(dtlList.get(i)[1].toString());
						tblZth.setF42(dtlList.get(i)[5].toString());
						tblZth.setEflag("1");
						tblZth.setAmt1(dtlList.get(i)[2].toString());
						tblZth.setDdate(settleDate);

						zthDtlList.add(tblZthDtl);
						zthList.add(tblZth);
					}else{
						tmpList.add(i);
					}
					
					if((i+1)==dtlList.size()){
						tblZthDtlPK=new TblZthDtlPK();
						tblZthDtlPK.setInstDate(dtlList.get(tmpList.get(0))[0].toString());
						tblZthDtlPK.setSysSeqNum(dtlList.get(tmpList.get(0))[1].toString());
						tblZthDtlPK.setSettlmtDate(settleDate);
						tblZthDtl=new TblZthDtl(); 
						tblZthDtl.setTblZthDtlPK(tblZthDtlPK);
						tblZthDtl.setAmt(new BigDecimal(brhAmt));
						tblZthDtl.setMchtNo(dtlList.get(tmpList.get(0))[5].toString());
						
						tblZth=new TblZth();
						tblZth.setInstDate(dtlList.get(tmpList.get(0))[0].toString());
						tblZth.setSysSeqNum(dtlList.get(tmpList.get(0))[1].toString());
						tblZth.setF42(dtlList.get(tmpList.get(0))[5].toString());
						tblZth.setEflag("2");
						tblZth.setAmt1(df.format(Double.parseDouble(dtlList.get(tmpList.get(0))[3].toString())+brhAmt));
						tblZth.setDdate("");
						
						zthDtlList.add(tblZthDtl);
						zthList.add(tblZth);
					}
				}
			}
		}
		
		String updZthSql;
		for (TblZth tblZthTmp : zthList) {
			updZthSql="update tbl_zth "
						+ "set amt1='"+tblZthTmp.getAmt1()+"', "
						+ "eflag='"+tblZthTmp.getEflag()+"',"
						+ "ddate='"+tblZthTmp.getDdate()+"' "
					+ " where inst_date='"+tblZthTmp.getInstDate()+"' "
					+ " and sys_seq_num='"+tblZthTmp.getSysSeqNum()+"' "
					+ " and f42='"+tblZthTmp.getF42()+"' ";
			commQueryDAO.excute(updZthSql);
		}
		
		tblZthDtlDAO.saveList(zthDtlList);
		String sql="update TBL_ZTH_DATE set SETTLE_DATE='"+settleDate+"' ,SYS_DATE=(select to_char(sysdate,'yyyymmdd') from dual) ";
		commQueryDAO.excute(sql);
		return Constants.SUCCESS_CODE;
	}
	

	public String reset(String settleDate) throws Exception {
		// TODO Auto-generated method stub
		String sql = "UPDATE TBL_BAT_TASK_CTL SET ASN_STATUS = '0' ,beg_time='' ,end_time =''  WHERE BAT_ID = '" + settleDate + "'";
		commQueryDAO.excute(sql);
		log.info(sql);
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @param instDate清算日期
	 * 判断清算日期是否符合
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	private String isDate(String settleDate){
		
		String paySql="select distinct max(inst_date) from tbl_pay_settle_dtl";
		String payDate=commQueryDAO.findCountBySQLQuery(paySql);
		if(Integer.parseInt(settleDate)>Integer.parseInt(payDate)){
			return "该日期下未跑清算对账批量，请先进行清算对账跑批！";
		}
		
		if(Integer.parseInt(settleDate)<Integer.parseInt(payDate)){
			return "该日期已经划付清算，不能进行准退货跑批！";
		}
		
		
		String sql="select settle_date,sys_date from tbl_zth_date ";
		List<Object[]> zthDate =commQueryDAO.findBySQLQuery(sql);
		if(zthDate==null||zthDate.size()==0){
			return Constants.SUCCESS_CODE;
		}
		
		if(settleDate.equals(zthDate.get(0)[0].toString())){
			return "该日期下已经跑过准退货批量！";
		}
		
		
		if(Integer.parseInt(settleDate)<Integer.parseInt(zthDate.get(0)[0].toString())){
			return "该日期下已经跑过准退货批量！";
		}
		
		if(Integer.parseInt(settleDate)>Integer.parseInt(zthDate.get(0)[0].toString())&&
				payDate.equals(settleDate)){
			return Constants.SUCCESS_CODE;
		}else{
			return "选择清算日期有误！";
		}
	}
	
	
	/**
	 * @param date清算日期
	 * 获取准退货够扣的商户
	 * 按商户清算
	 * @return List<Object[]>
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> getMchtList(String date){
		
		String sql="select * from ("
					+ " select a.f42,c.bank_no,a.amt,nvl(b.amt_settlmt,0), nvl(b.amt_settlmt,0)-nvl(a.amt,0) as fact_amt"
					+ " from ("
						+ "select f42,sum(nvl(samt,0)-nvl(amt1,0))as amt "
						+ "from tbl_zth "
						+ "where inst_date<='"+date+"' "
						+ " and rspcode='00' "
						+ " and eflag in ('0','2') "
						+ "group by f42) a "
					+ "left join tbl_pay_settle_dtl b on a.f42=b.mcht_no and b.inst_date='"+date+"' and b.channel_id='07' "
					+ "left join tbl_mcht_base_inf c on a.f42=c.mcht_no "
					+ "where c.bank_no not in(select brh_id from tbl_brh_info where substr(resv1,7,1)='1' and brh_id!='00001')"
				+ ") where fact_amt>=0";
		
		List<Object[]> dataList =commQueryDAO.findBySQLQuery(sql);
		return dataList;
	}
	
	/**
	 * @param date清算日期
	 * 获取准退货不够扣的商户
	 * 按商户清算
	 * @return List<Object[]>
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> getMchtErrList(String date){
		
		String sql="select * from ("
					+ " select a.f42,c.bank_no,a.amt,nvl(b.amt_settlmt,0), nvl(b.amt_settlmt,0)-nvl(a.amt,0) as fact_amt"
					+ " from ("
						+ "select f42,sum(nvl(samt,0)-nvl(amt1,0))as amt "
						+ "from tbl_zth "
						+ "where inst_date<='"+date+"' "
						+ " and rspcode='00' "
						+ " and eflag in ('0','2') "
						+ "group by f42) a "
					+ "left join tbl_pay_settle_dtl b on a.f42=b.mcht_no and b.inst_date='"+date+"' and b.channel_id='07' "
					+ "left join tbl_mcht_base_inf c on a.f42=c.mcht_no "
					+ "where c.bank_no not in(select brh_id from tbl_brh_info where substr(resv1,7,1)='1' and brh_id!='00001')"
				+ ") where fact_amt<0";
		
		List<Object[]> dataList =commQueryDAO.findBySQLQuery(sql);
		return dataList;
	}
	
	/**
	 * @param date清算日期
	 * 获取准退货够扣的机构
	 * 按机构清算
	 * @return List<Object[]>
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> getBrhList(String date){
		
		String sql="select t.brh_id,t.back_amt,t.amt_settlmt,nvl(t.amt_settlmt,0)-nvl(back_amt,0) from "
						+ "(select a.brh_id,"
							+ "nvl("
								+ "(select sum(nvl(samt,0)-nvl(amt1,0)) "
								+ "from tbl_zth "
								+ "where inst_date<='"+date+"' "
								+ "and rspcode='00' "
								+ "and eflag in ('0','2') "
								+ "and f42 in(select mcht_no from tbl_mcht_base_inf where bank_no=a.brh_id)) "
							+ ",0) as back_amt ,"
						+ "nvl(b.amt_settlmt,0) as amt_settlmt "
						+ "from tbl_brh_info a "
						+ "left join tbl_pay_settle_dtl b on a.brh_id=trim(b.mcht_no) and b.inst_date='"+date+"' and b.channel_id='07' "
						+ "where substr(a.resv1,7,1)='1' and a.brh_id!='00001' "
						+ ") t "
				+ "where t.back_amt>0 "
				+ "and nvl(t.amt_settlmt,0)-nvl(back_amt,0)>=0 ";
		
		List<Object[]> dataList =commQueryDAO.findBySQLQuery(sql);
		return dataList;
	}
	
	
	/**
	 * @param date清算日期
	 * 获取准退货不够扣的机构
	 * 按机构清算
	 * @return List<Object[]>
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> getBrhErrList(String date){
		
		String sql="select t.brh_id,t.back_amt,t.amt_settlmt,nvl(t.amt_settlmt,0)-nvl(back_amt,0) from "
						+ "(select a.brh_id,"
							+ "nvl("
								+ "(select sum(nvl(samt,0)-nvl(amt1,0)) "
								+ "from tbl_zth "
								+ "where inst_date<='"+date+"' "
								+ "and rspcode='00' "
								+ "and eflag in ('0','2') "
								+ "and f42 in(select mcht_no from tbl_mcht_base_inf where bank_no=a.brh_id)) "
							+ ",0) as back_amt ,"
						+ "nvl(b.amt_settlmt,0) as amt_settlmt "
						+ "from tbl_brh_info a "
						+ "left join tbl_pay_settle_dtl b on a.brh_id=trim(b.mcht_no) and b.inst_date='"+date+"' and b.channel_id='07' "
						+ "where substr(a.resv1,7,1)='1' and a.brh_id!='00001' "
						+ ") t "
				+ "where t.back_amt>0 "
				+ "and nvl(t.amt_settlmt,0)-nvl(back_amt,0)<0 ";
		
		List<Object[]> dataList =commQueryDAO.findBySQLQuery(sql);
		return dataList;
	}
	
	/**
	 * @param date清算日期
	 * @param mchtNo商户号
	 * 获取准退货明细
	 * 按商户清算
	 * @return List<Object[]>
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> getDtlList(String date,String mchtNo){
		
		String sql="select a.INST_DATE,a.SYS_SEQ_NUM,a.samt,nvl(a.amt1,0),a.samt-nvl(a.amt1,0),a.f42 "
				+ " from tbl_zth a "
				+ "where a.inst_date<='"+date+"' "
				+ " and a.rspcode='00' "
				+ "and a.eflag in ('0','2') "
				+ " and a.f42='"+mchtNo+"' "
				+ " order by a.inst_date,a.inst_time ";
		
		List<Object[]> dataList =commQueryDAO.findBySQLQuery(sql);
		return dataList;
	}
	
	
	/**
	 * @param date清算日期
	 * @param brhId机构号
	 * 获取准退货明细
	 * 按机构清算
	 * @return List<Object[]>
	 */
	@SuppressWarnings("unchecked")
	private List<Object[]> getDtlBrhList(String date,String brhId){
		
		String sql="select a.INST_DATE,a.SYS_SEQ_NUM,a.samt,nvl(a.amt1,0),a.samt-nvl(a.amt1,0),a.f42 "
				+ " from tbl_zth a "
				+ "where a.inst_date<='"+date+"' "
				+ " and a.rspcode='00' "
				+ "and a.eflag in ('0','2') "
				+ " and a.f42 in(select mcht_no from tbl_mcht_base_inf where bank_no='"+brhId+"') "
				+ " order by a.inst_date,a.f42,a.inst_time ";
		
		List<Object[]> dataList =commQueryDAO.findBySQLQuery(sql);
		return dataList;
	}
	
	/**
	 * @return the commQueryDAO
	 */
	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}



	/**
	 * @param commQueryDAO the commQueryDAO to set
	 */
	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}


	public TblZthDtlDAO getTblZthDtlDAO() {
		return tblZthDtlDAO;
	}


	public void setTblZthDtlDAO(TblZthDtlDAO tblZthDtlDAO) {
		this.tblZthDtlDAO = tblZthDtlDAO;
	}
	
	
}