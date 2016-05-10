package com.allinfinance.bo.impl.term;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeanUtils;

import com.allinfinance.bo.term.T3010BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.risk.TblRiskParamMngDAO;
import com.allinfinance.dao.iface.term.TblTermInfDAO;
import com.allinfinance.dao.iface.term.TblTermInfTmpDAO;
import com.allinfinance.dao.iface.term.TblTermKeyDAO;
import com.allinfinance.dao.iface.term.TblTermRefuseDAO;
import com.allinfinance.dao.iface.term.TblTermTmkLogDAO;
import com.allinfinance.dao.iface.term.TblTermZmkInfDAO;
import com.allinfinance.po.TblTermInf;
import com.allinfinance.po.TblTermInfTmp;
import com.allinfinance.po.TblTermInfTmpPK;
import com.allinfinance.po.TblTermKey;
import com.allinfinance.po.TblTermKeyPK;
import com.allinfinance.po.TblTermRefuse;
import com.allinfinance.po.TblTermRefusePK;
import com.allinfinance.po.TblTermTmkLog;
import com.allinfinance.po.TblTermTmkLogPK;
import com.allinfinance.po.TblTermZmkInf;
import com.allinfinance.po.TblTermZmkInfPK;
import com.allinfinance.po.risk.TblRiskParamMng;
import com.allinfinance.po.risk.TblRiskParamMngPK;
import com.allinfinance.struts.pos.EposMisc;
import com.allinfinance.struts.pos.TblTermInfConstants;
import com.allinfinance.struts.pos.TblTermTmkLogConstants;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.StatusUtil;

/**
 * Title:终端信息管理
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-16
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @version 1.0
 */
public class T3010BOTarget implements T3010BO {
	
	private TblTermInfTmpDAO tblTermInfTmpDAO;
	private TblTermKeyDAO tblTermKeyDAO;
	private TblTermZmkInfDAO tblTermZmkInfDAO;
	private ICommQueryDAO commQueryDAO;
	private TblTermInfDAO tblTermInfDAO;
	private TblTermTmkLogDAO tblTermTmkLogDAO;
	private TblTermRefuseDAO tblTermRefuseDAO;
	private TblRiskParamMngDAO tblRiskParamMngDAO;
	
	public String add(TblTermInfTmp tblTermInfTmp) {
		return tblTermInfTmpDAO.save(tblTermInfTmp).getTermId();
	}

	public TblTermInfTmp get(String termId,String recCrtTs) {
		TblTermInfTmpPK pk= new TblTermInfTmpPK(termId,CommonFunction.fillString(recCrtTs, ' ', 14, true));
		return tblTermInfTmpDAO.get(pk);
	}

	public boolean update(TblTermInfTmp tblTermInfTmp,String termSta) {
		if(termSta != null)
			tblTermInfTmp.setTermSta(termSta);
		tblTermInfTmpDAO.update(tblTermInfTmp);
		return true;
	}

	public boolean updateForRisk(TblTermInfTmp tblTermInfTmp,String termSta) {
		if(termSta != null)
			tblTermInfTmp.setTermSta(termSta);
		tblTermInfTmpDAO.update(tblTermInfTmp);
		TblTermInf tblTermInf = (TblTermInf) tblTermInfTmp.clone();
		tblTermInfDAO.update(tblTermInf);
		return true;
	}
	
	public boolean update(TblTermInfTmp tblTermInfTmp,String termSta, TblRiskParamMng tblRiskParamMng) {
		if(termSta != null)
			tblTermInfTmp.setTermSta(termSta);
		if(tblRiskParamMng != null){
			TblRiskParamMngPK paramMngPK = new TblRiskParamMngPK();
			paramMngPK.setMchtId(tblTermInfTmp.getMchtCd());
			paramMngPK.setRiskType("1");
			paramMngPK.setTermId(tblTermInfTmp.getId().getTermId());
			TblRiskParamMng tblRiskParamMng2 = tblRiskParamMngDAO.get(paramMngPK);
			if(tblRiskParamMng2 == null){
				tblRiskParamMng.setId(paramMngPK);
				tblRiskParamMng.setRegTime(CommonFunction.getCurrentDateTime());
				tblRiskParamMng.setRegOpr(tblTermInfTmp.getRecUpdOpr());
				tblRiskParamMng.setUpdOpr(tblTermInfTmp.getRecUpdOpr());
				tblRiskParamMng.setUpdTime(CommonFunction.getCurrentDateTime());
				tblRiskParamMngDAO.saveOrUpdate(tblRiskParamMng);
			}else {
				BeanUtils.copyProperties(tblRiskParamMng, tblRiskParamMng2, new String[]{"id","regTime","regOpr"});
				tblRiskParamMng2.setUpdOpr(tblTermInfTmp.getRecUpdOpr());
				tblRiskParamMng2.setUpdTime(CommonFunction.getCurrentDateTime());
				tblRiskParamMngDAO.update(tblRiskParamMng2);
			}
		}
		tblTermInfTmpDAO.update(tblTermInfTmp);
		return true;
	}

	public boolean update(TblTermInf tblTermInf,String termSta) {
		if(termSta != null)
			tblTermInf.setTermSta(termSta);
		tblTermInfDAO.update(tblTermInf);
		return true;
	}
	
	public boolean update(TblTermInf tblTermInf) {
		
		tblTermInfDAO.update(tblTermInf);
		return true;
	}
	
	public String getTermId(String brhId) {
		if(brhId == null || brhId.trim().equals(""))
			return Constants.FAILURE_CODE;
		if(brhId.length()>2)
			brhId = brhId.substring(0, 2);
		String sql = "select max(substr(term_Id,4,6))+1 from TBL_TERM_INF_TMP where substr(term_id,2,2)='"+brhId+"'";
		List list = commQueryDAO.findBySQLQuery(sql);
		if(list == null || list.isEmpty() || list.get(0) == null)
			return 0+brhId+CommonFunction.fillString("1", '0', 5, false);
		BigDecimal maxB = (BigDecimal)list.get(0);
		int max = maxB.intValue();
		if(max > 99999)
			max = 1;
		return 0+brhId + CommonFunction.fillString(String.valueOf(max), '0', 5, false);
	}

	public boolean save(TblTermInfTmp tblTermInfTmp, String oprId,String termBatchNm,String termSta ) {
		TblTermInf tblTermInf = new TblTermInf();
		if(termBatchNm != null)
		{
			tblTermInfTmp.setTermBatchNm(termBatchNm);
			/*String param = tblTermInfTmp.getTermPara();
			String[] array = param.split("\\|");
			array[8] = "31"+termBatchNm;
			param = "";
			for(String tmp : array)
			{
				param += tmp+"|";
			}
			tblTermInfTmp.setTermPara(param);*/
		}
		tblTermInf = (TblTermInf) tblTermInfTmp.clone();//复制的是数据库的数据，在传过来之前已将状态置为正常
		//如果是新增，更新tblTermInf中crt时间作为入网时间（给商户及终端信息明细表用）
		if(TblTermInfConstants.TERM_STA_INIT.equals(termSta)){
			//由8字节改为14字节数据的格式有日期格式YYYYMMDD改为 YYYYMMDDHHMMSS
			//tblTermInf.setRecCrtTs(CommonFunction.getCurrentDate());
			tblTermInf.setRecCrtTs(CommonFunction.getCurrentDateTime());
			//20160324 guoyu delete 原公司特殊处理，钱宝不适用 
//			String sqls="update tbl_term_key set mac_key_len='08', mac_key='3C29ACCF176D0A8A',"
//					+ " mac_key_chk='56A8749898923FE6', pin_key_len='16', pin_key='13045ABE18C56398FE82A997709EE952',"
//					+ " pin_key_chk='D2A97C01C18C29DB', trk_key_len='16', trk_key='A673819C0371257161380012DF194839',"
//					+ " trk_key_chk='832099BC44F89E80' , pos_bmk='6A4140FA94DE39F85B83EDD69B484BF3',"
//					+ " pos_tmk='A0E4AD1F4C3C3C628A166940E863F029', tmk_st='1' where term_id in ('"+tblTermInf.getId()+"') "
//					+ "and MCHT_CD in (select mcht_no from tbl_mcht_base_inf  where bank_no in (SELECT brh_id FROM TBL_BRH_INFO  start with brh_id in('00005','00006','00015') connect by prior  BRH_ID = UP_BRH_ID ) ) ";
//			CommonFunction.getCommQueryDAO().excute(sqls);
//			
//			
//			
//			String sqlss="update tbl_term_key set KEY_INDEX='0023', "
//					+ "MAC_KEY_LEN='08', MAC_KEY='76EE9987FDC3F55E', MAC_KEY_CHK='208AAD33E7BA3813', "
//					+ "PIN_KEY_LEN='16', PIN_KEY='F4528FCC2FDC8DDFACk53F25665ED45FB', PIN_KEY_CHK='99742F8F1F8AE05D', "
//					+ "TRK_KEY_LEN='16', TRK_KEY='4296068701B48511D50F29A7D46982EB', TRK_KEY_CHK='1322B743B47A011A', "
//					+ "POS_BMK='E4878DE075E7594A66C062646D5FDB7F', POS_TMK='70F24A531D9FCCFBB37C47E6A51365A6', "
//					+ "TMK_ST='1', REC_OPR_ID='U', REC_UPD_OPR='-', REC_CRT_TS='-', REC_UPD_TS='-' "
//					+ "where term_id in ('"+tblTermInf.getId()+"') "
//					+ "and MCHT_CD in (select mcht_no from tbl_mcht_base_inf  where bank_no in (SELECT brh_id FROM TBL_BRH_INFO  start with brh_id in('00057') connect by prior  BRH_ID = UP_BRH_ID ) ) ";
//			CommonFunction.getCommQueryDAO().excute(sqlss);
			
		}
		
		//如果不是新增，crt时间不变
		if(!TblTermInfConstants.TERM_STA_INIT.equals(termSta)){
			tblTermInf.setRecCrtTs(tblTermInfTmp.getRecCrtTs());
		}
		//20160506 杨文武 修改
//		tblTermInf.setTermPara(tblTermInfTmp.getTermPara().replaceAll("\\|", ""));
		if (!StringUtils.isBlank(tblTermInfTmp.getTermPara())) {
			String TermPara = tblTermInfTmp.getTermPara().replaceAll("\\|", "");
			tblTermInf.setTermPara(TermPara);
		}
		if(tblTermInfTmp.getMisc2() != null && !tblTermInfTmp.getMisc2().equals(""))
		{
			EposMisc epos = new EposMisc(tblTermInf.getMisc2());
			epos.setVersion(tblTermInfTmp.getMisc2());
			tblTermInf.setMisc2(epos.toString());
		}
		tblTermInf.setRecUpdOpr(oprId);
		//由8字节改为14字节数据的格式有日期格式YYYYMMDD改为 YYYYMMDDHHMMSS
		//tblTermInf.setRecUpdTs(CommonFunction.getCurrentDate());
		tblTermInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
		
		tblTermInfDAO.saveOrUpdate(tblTermInf);
		return true;
	}

	public List qryTermInfo(String termNo, String state, Operator operator,
			String mchtCd, String termBranch, String startDate,String endDate) {
		StringBuffer whereSql = new StringBuffer(" WHERE trim(t1.term_id) not in (select t3.term_id_id from tbl_term_tmk_log t3 where t3.state ='0')")
				.append(" and t1.TERM_BRANCH in " + operator.getBrhBelowId()).append(" and t1.term_sta = '1' ");
		if (mchtCd != null && !mchtCd.trim().equals("")) {
			whereSql.append(" AND t1.MCHT_CD='").append(mchtCd).append("'");
		}
		if (termBranch != null && !termBranch.trim().equals("")) {
			whereSql.append(" AND t1.TERM_BRANCH='").append(termBranch)
			.append("'");
		}
		if (termNo != null && !termNo.trim().equals("")) {
			whereSql.append(" AND t1.TERM_ID='")
			.append(CommonFunction.fillString(termNo, ' ', 12, true))
			.append("'");
		}
		if (state != null && !state.trim().equals("")){
			if(state.equals("1"))
				whereSql.append(" AND t2.STATE='1'");
			else
				whereSql.append(" AND t2.STATE is null");
		}
		if (startDate != null && !startDate.trim().equals("")) {
			whereSql.append(" AND t1.REC_CRT_TS>").append(startDate);
		}
		if (endDate != null && !endDate.trim().equals("")) {
			whereSql.append(" AND t1.REC_CRT_TS<").append(endDate);
		}
		
		String sql = "SELECT t1.term_id,t1.mcht_cd,t1.term_branch FROM tbl_term_inf_tmp t1 left join tbl_term_tmk_log t2 on trim(t1.term_id)=t2.term_id_id and t2.state!='0'"
		+ whereSql.toString() + " ORDER BY t1.term_id";
		
		List list = commQueryDAO.findBySQLQuery(sql);
		return list;
	}

	public boolean initTmkLog(TblTermTmkLog tblTermTmkLog) {
		tblTermTmkLogDAO.save(tblTermTmkLog);
		return true;
	}

	public String getBatchNo() {
		String sql = "select max(substr(BATCH_NO,9,12)) from TBL_TERM_TMK_LOG where substr(BATCH_NO,0,8)='"+CommonFunction.getCurrentDate()+"'";
		List list = commQueryDAO.findBySQLQuery(sql);
		if(list == null || list.isEmpty() || list.get(0) == null)
			return CommonFunction.getCurrentDate()+CommonFunction.fillString("1", '0', 4, false);
		int max = Integer.parseInt(list.get(0).toString())+1;
		if(max > 999999)
			max = 1;
		return CommonFunction.getCurrentDate() + CommonFunction.fillString(String.valueOf(max), '0', 4, false);
	}

	public String chkTmkLog(String termIdId, String batchNo, String oprId) {
		TblTermTmkLogPK pk = new TblTermTmkLogPK(termIdId,batchNo);
		TblTermTmkLog tblTermTmkLog = tblTermTmkLogDAO.get(pk);
		if(tblTermTmkLog == null)
			return TblTermTmkLogConstants.T30203_01;
		if(tblTermTmkLog.getReqOpr().equals(oprId.trim()))
			return TblTermTmkLogConstants.T30203_02;
		if(tblTermTmkLog.getState().equals(TblTermTmkLogConstants.STATE_RUN))
			return TblTermTmkLogConstants.T30203_04;
		tblTermTmkLog.setChkOpr(oprId);
		tblTermTmkLog.setChkDate(CommonFunction.getCurrentDate());
		tblTermTmkLog.setState(TblTermTmkLogConstants.STATE_RUN);
		tblTermTmkLog.setRecUpdOpr(oprId);
		tblTermTmkLog.setRecUpdTs(CommonFunction.getCurrentDateTime());
		tblTermTmkLogDAO.update(tblTermTmkLog);
		return Constants.SUCCESS_CODE;
	}

	public String chkTmkLog(String batchNo, String oprId) {
		List<TblTermTmkLog> list = commQueryDAO.findBySQLQuery("select req_opr from TBL_TERM_TMK_LOG t where t.batch_no='"+batchNo+"' and t.req_opr!='"+oprId+"'");
		if(list==null||list.isEmpty())
			return TblTermTmkLogConstants.T30203_02;
		StringBuffer sql = new StringBuffer("update tbl_term_tmk_log set state=").append(TblTermTmkLogConstants.STATE_RUN)
		.append(",chk_opr = '").append(oprId).append("',chk_date = ").append(CommonFunction.getCurrentDate())
		.append(",rec_upd_opr = '").append(oprId).append("',rec_upd_ts = ").append(CommonFunction.getCurrentDateTime())
		.append(" where batch_no='").append(batchNo).append("' and state='").append(TblTermTmkLogConstants.STATE_INIT)
		.append("' and req_opr!='").append(oprId.trim()).append("'");
		commQueryDAO.excute(sql.toString());
		return Constants.SUCCESS_CODE;
	}

	public boolean initTmkLog(List<TblTermTmkLog> list) {
		if(list != null && !list.isEmpty())
		{
			tblTermTmkLogDAO.batchSave(list);
			return true;
		}
		return false;
	}


	public TblTermInf getTermInfo(String termId) {
		if(termId!=null&&!"".equals(termId))
			return tblTermInfDAO.get(CommonFunction.fillString(termId, ' ', 12, true)); 
		return null;
	}

	public String delTmkLog(String termIdId, String batchNo, String oprId) {
		TblTermTmkLog tblTermTmkLog = tblTermTmkLogDAO.get(new TblTermTmkLogPK(termIdId,batchNo));
		if(tblTermTmkLog.getReqOpr().equals(oprId))
		{
			return TblTermTmkLogConstants.T30203_05;
		}
		tblTermTmkLogDAO.delete(tblTermTmkLog);
		return Constants.SUCCESS_CODE;
	}

	
	public String refuseLog( String termId,String refuseInfo,String refuseType) {
		TblTermRefusePK tblTermRefusePK=new TblTermRefusePK(CommonFunction.getCurrentDateTime(),termId);
		TblTermRefuse tblTermRefuse=new TblTermRefuse();
		Operator opr = (Operator) ServletActionContext.getRequest()
		.getSession().getAttribute(Constants.OPERATOR_INFO);
		tblTermRefuse.setId(tblTermRefusePK);
		tblTermRefuse.setBrhId(opr.getOprBrhId());
		tblTermRefuse.setOprId(opr.getOprId());
		tblTermRefuse.setRefuseInfo(refuseInfo);
		tblTermRefuse.setRefuseType(StatusUtil.getNextStatus("TM."+refuseType));
		tblTermRefuseDAO.save(tblTermRefuse);
		return Constants.SUCCESS_CODE;
	}

	public boolean chkTmkLog(String termId) 
	{
		StringBuffer hql = new StringBuffer("from TblTermTmkLog a")
				.append(" where a.State='").append(TblTermTmkLogConstants.STATE_INIT).append("'")
				.append(" and a.Id.TermIdId='").append(termId).append("'");
		List list = tblTermTmkLogDAO.findByHQLQuery(hql.toString());
		if(list!=null&&!list.isEmpty())
			return false;
		return true;
	}

	public String refuse(TblTermInfTmpPK pk, String state) {
		if(state.equalsIgnoreCase(TblTermInfConstants.TERM_STA_COPY_REFUSE)){
			TblTermInfTmp tblTermInfTmp = tblTermInfTmpDAO.get(pk);
			tblTermInfTmp.setTermSta(state);
			tblTermInfTmpDAO.update(tblTermInfTmp);
			return Constants.SUCCESS_CODE;
		}
		
		if(state.equals(TblTermInfConstants.TERM_STA_ADD_REFUSE))
		{
			TblTermInfTmp tblTermInfTmp = tblTermInfTmpDAO.get(pk);
			tblTermInfTmp.setTermSta(state);
			tblTermInfTmpDAO.update(tblTermInfTmp);
//			tblTermInfTmpDAO.delete(pk);
//			CommonFunction.getCommQueryDAO().excute("delete tbl_term_key where term_id='" + pk.getTermId() + "'");
//			CommonFunction.getCommQueryDAO().excute("delete tbl_term_zmk_inf where term_id='" + pk.getTermId() + "'");
			return Constants.SUCCESS_CODE;
		}
		if(state.equals(TblTermInfConstants.TERM_STA_MOD_REFUSE))
		{
			TblTermInf tblTermInf = tblTermInfDAO.get(pk.getTermId());
			TblTermInfTmp tblTermInfTmp = tblTermInfTmpDAO.get(pk);
			
			String tmpDate = tblTermInfTmp.getRecCrtTs();
			
//			String param = tblTermInf.getTermPara();
//			String param1 = tblTermInfTmp.getTermPara();
			
			tblTermInfTmp = (TblTermInfTmp)tblTermInf.clone(tblTermInfTmp);
			
			/*String[] array = param1.split("\\|");
			StringBuffer result = new StringBuffer("");
			int current = 0;
			for(int i=0;i<array.length;i++)
			{
				result.append(param.substring(current, current+array[i].length())).append("|");
				current += array[i].length();
			}*/
//			tblTermInfTmp.setTermPara(result.toString());
			tblTermInfTmp.setRecCrtTs(tmpDate);
			tblTermInfTmpDAO.update(tblTermInfTmp);	
			return Constants.SUCCESS_CODE;
		}
		return Constants.FAILURE_CODE;
	}

	/**
	 * 依据模板ID查询是否有终端使用该模板
	 */
	public List<TblTermInfTmp> loadByMisc1(String key) {
		int a = 6-key.length();
		if (a>0) {
			 String b = String.valueOf(Math.pow(10,a));
			 b=b.substring(1, a+1);
			 key = b+key;
		}
		String hql = " select MCHT_CD From TBL_TERM_INF_TMP  where MISC_1 ="+key;
		@SuppressWarnings("unchecked")
		List<TblTermInfTmp> list = commQueryDAO.findBySQLQuery(hql);
		return list;
	}

	public List<String> batchAdd(TblTermInfTmp tblTermInfTmp, TblTermKey tblTermKey, TblTermZmkInf tblTermZmkInf, int termNumNew,String[] termSerialNums) {
		List<String> termIdList = new ArrayList<String>();
		TblTermInfTmp tblTermInfTmpAdd;
		TblTermInfTmpPK pk;
		TblTermKey tblTermKeyAdd;
		TblTermKeyPK keyPK;
		TblTermZmkInf tblTermZmkInfAdd;
		TblTermZmkInfPK zmkPK;
		// 初始termId
		String termId = tblTermInfTmp.getId().getTermId();
		termId = termId.substring(0,3) + CommonFunction.fillString(String.valueOf(Integer.parseInt(termId.substring(3))-1), '0', 5, false);
		for (int i = 0 ; i < termNumNew ; i++){
			// termId进行自增
			termId = termId.substring(0,3) + CommonFunction.fillString(String.valueOf(Integer.parseInt(termId)+1), '0', 5, false);
			// 添加终端
			tblTermInfTmpAdd = new TblTermInfTmp();
			pk = new TblTermInfTmpPK();
			pk.setTermId(termId);
			BeanUtils.copyProperties(tblTermInfTmp, tblTermInfTmpAdd);
			tblTermInfTmpAdd.setTermSerialNum(termSerialNums[i]);
			tblTermInfTmpAdd.setId(pk);
			termIdList.add(tblTermInfTmpDAO.save(tblTermInfTmpAdd).getTermId());
			// 添加终端密钥
			tblTermKeyAdd = new TblTermKey();
			keyPK = new TblTermKeyPK();
			keyPK.setTermId(termId);
			keyPK.setMchtCd(tblTermInfTmp.getMchtCd());
			BeanUtils.copyProperties(tblTermKey, tblTermKeyAdd);
			tblTermKeyAdd.setId(keyPK);
			tblTermKeyDAO.saveOrUpdate(tblTermKeyAdd);
			// 添加终端密钥索引
			tblTermZmkInfAdd = new TblTermZmkInf();
			zmkPK = new TblTermZmkInfPK();
			zmkPK.setTermId(termId);
			zmkPK.setMchtNo(tblTermInfTmp.getMchtCd());
			BeanUtils.copyProperties(tblTermZmkInf, tblTermZmkInfAdd);
			tblTermZmkInfAdd.setId(zmkPK);
			tblTermZmkInfDAO.saveOrUpdate(tblTermZmkInfAdd);
		}
		return termIdList;
	}

	/**
	 * @return the tblTermInfTmpDAO
	 */
	public TblTermInfTmpDAO getTblTermInfTmpDAO() {
		return tblTermInfTmpDAO;
	}

	/**
	 * @param tblTermInfTmpDAO the tblTermInfTmpDAO to set
	 */
	public void setTblTermInfTmpDAO(TblTermInfTmpDAO tblTermInfTmpDAO) {
		this.tblTermInfTmpDAO = tblTermInfTmpDAO;
	}

	/**
	 * @return the tblTermKeyDAO
	 */
	public TblTermKeyDAO getTblTermKeyDAO() {
		return tblTermKeyDAO;
	}

	/**
	 * @param tblTermKeyDAO the tblTermKeyDAO to set
	 */
	public void setTblTermKeyDAO(TblTermKeyDAO tblTermKeyDAO) {
		this.tblTermKeyDAO = tblTermKeyDAO;
	}

	/**
	 * @return the tblTermZmkInfDAO
	 */
	public TblTermZmkInfDAO getTblTermZmkInfDAO() {
		return tblTermZmkInfDAO;
	}

	/**
	 * @param tblTermZmkInfDAO the tblTermZmkInfDAO to set
	 */
	public void setTblTermZmkInfDAO(TblTermZmkInfDAO tblTermZmkInfDAO) {
		this.tblTermZmkInfDAO = tblTermZmkInfDAO;
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

	/**
	 * @return the tblTermInfDAO
	 */
	public TblTermInfDAO getTblTermInfDAO() {
		return tblTermInfDAO;
	}

	/**
	 * @param tblTermInfDAO the tblTermInfDAO to set
	 */
	public void setTblTermInfDAO(TblTermInfDAO tblTermInfDAO) {
		this.tblTermInfDAO = tblTermInfDAO;
	}

	/**
	 * @return the tblTermTmkLogDAO
	 */
	public TblTermTmkLogDAO getTblTermTmkLogDAO() {
		return tblTermTmkLogDAO;
	}

	/**
	 * @param tblTermTmkLogDAO the tblTermTmkLogDAO to set
	 */
	public void setTblTermTmkLogDAO(TblTermTmkLogDAO tblTermTmkLogDAO) {
		this.tblTermTmkLogDAO = tblTermTmkLogDAO;
	}

	/**
	 * @return the tblTermRefuseDAO
	 */
	public TblTermRefuseDAO getTblTermRefuseDAO() {
		return tblTermRefuseDAO;
	}

	/**
	 * @param tblTermRefuseDAO the tblTermRefuseDAO to set
	 */
	public void setTblTermRefuseDAO(TblTermRefuseDAO tblTermRefuseDAO) {
		this.tblTermRefuseDAO = tblTermRefuseDAO;
	}

	@Override
	public List<TblTermInfTmp> getByMchnt(String mchntId) {
		 return tblTermInfTmpDAO.getByMchnt(mchntId);
	}

	public TblRiskParamMngDAO getTblRiskParamMngDAO() {
		return tblRiskParamMngDAO;
	}

	public void setTblRiskParamMngDAO(TblRiskParamMngDAO tblRiskParamMngDAO) {
		this.tblRiskParamMngDAO = tblRiskParamMngDAO;
	}

	@Override
	public String batchUpd(List<TblTermInfTmp> list) {
		for (TblTermInfTmp tblTermInfTmp : list) {
			tblTermInfTmpDAO.update(tblTermInfTmp);
		}
		return Constants.SUCCESS_CODE;
	}
}
