package com.allinfinance.bo.impl.mchnt;

import java.util.UUID;

import com.allinfinance.bo.mchnt.T20202BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.TblMchntInfoConstants;
import com.allinfinance.dao.iface.mchnt.ITblMchtLimitDateDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtBaseInfDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtBaseInfTmpDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtBaseInfTmpLogDAO;
import com.allinfinance.po.TblMchtBaseInfTmpLog;
import com.allinfinance.po.TblMchtLimitDate;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.system.util.CommonFunction;

public class T20202BOTarget implements T20202BO {

	private ITblMchtLimitDateDAO iTblMchtLimitDateDAO;

	private TblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO;

	private TblMchtBaseInfDAO tblMchtBaseInfDAO;

	private TblMchtBaseInfTmpLogDAO iTblMchtBaseInfTmpLogDAO;

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.mchnt.T20202BO#get(java.lang.String)
	 */
	@Override
	public TblMchtLimitDate get(String mchntNo) {
		// TODO Auto-generated method stub
		return iTblMchtLimitDateDAO.get(mchntNo);
	}

	@Override
	public String complete(TblMchtLimitDate tblMchtLimitDate) {
		iTblMchtLimitDateDAO.saveOrUpdate(tblMchtLimitDate);
		return Constants.SUCCESS_CODE;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.mchnt.T20202BO#limit(com.allinfinance.po.TblMchtLimitDate)
	 */
	@Override
	public String limit(TblMchtLimitDate tblMchtLimitDate) {
		// TODO Auto-generated method stub
		String misc = tblMchtLimitDate.getMisc();
		if (null == misc || "".equals(misc)) {
			tblMchtLimitDate.setMisc("1");
		} else if ("1".equals(misc)) {
			tblMchtLimitDate.setMisc("2");
		} else if ("2".equals(misc)) {
			return "最多只能延期2次";
		}
		iTblMchtLimitDateDAO.saveOrUpdate(tblMchtLimitDate);
		return Constants.SUCCESS_CODE;
	}

	// 冻结商户
	public String freeze(String mchntNo, Operator operator) {

		TblMchtBaseInfTmp tmp = tblMchtBaseInfTmpDAO.get(mchntNo);
		TblMchtBaseInf inf = tblMchtBaseInfDAO.get(mchntNo);
		if (null == tmp || null == inf) {
			return "该商户不存在！";
		} else if (!(TblMchntInfoConstants.MCHNT_ST_OK).equals(tmp.getMchtStatus())) {
			return "正常状态商户才可冻结！";
		}
		inf.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_STOP);
		tmp.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_STOP);
		tblMchtBaseInfTmpDAO.saveOrUpdate(tmp);
		tblMchtBaseInfDAO.saveOrUpdate(inf);
		// 终端的冻结：
		String update0 = "update tbl_term_inf set TERM_STA = '4' where MCHT_CD = '"
				+ mchntNo + "'";
		String update1 = "update tbl_term_inf_tmp set TERM_STA = '4' where MCHT_CD = '"
				+ mchntNo + "'";
		String update2 = "update TBL_ALARM_MCHT set BLOCK_MCHT_FLAG='1' where CARD_ACCP_ID='"
				+ mchntNo + "'";
		CommonFunction.getCommQueryDAO().excute(update0);
		CommonFunction.getCommQueryDAO().excute(update1);
		CommonFunction.getCommQueryDAO().excute(update2);
		// 插入日志
		TblMchtBaseInfTmpLog tblMchtBaseInfTmpLog = new TblMchtBaseInfTmpLog();
		tblMchtBaseInfTmpLog.setLId(UUID.randomUUID().toString());
		tblMchtBaseInfTmpLog.setLMchtNo(tmp.getMchtNo());
		tblMchtBaseInfTmpLog.setLCreatedate(CommonFunction.getCurrentDateTime());
		tblMchtBaseInfTmpLog.setLManuAuthFlag(tmp.getManuAuthFlag());
		tblMchtBaseInfTmpLog.setLCreatepeople(tmp.getUpdOprId());
		tblMchtBaseInfTmpLog.setLUpoprid(operator.getOprId());
		tblMchtBaseInfTmpLog.setLUpts(tmp.getRecUpdTs());
		tblMchtBaseInfTmpLog.setBeMchtStatus("w");
		iTblMchtBaseInfTmpLogDAO.saveOrUpdate(tblMchtBaseInfTmpLog);

		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the iTblMchtLimitDateDAO
	 */
	public ITblMchtLimitDateDAO getiTblMchtLimitDateDAO() {
		return iTblMchtLimitDateDAO;
	}

	/**
	 * @param iTblMchtLimitDateDAO the iTblMchtLimitDateDAO to set
	 */
	public void setiTblMchtLimitDateDAO(ITblMchtLimitDateDAO iTblMchtLimitDateDAO) {
		this.iTblMchtLimitDateDAO = iTblMchtLimitDateDAO;
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

	public TblMchtBaseInfTmpLogDAO getiTblMchtBaseInfTmpLogDAO() {
		return iTblMchtBaseInfTmpLogDAO;
	}

	public void setiTblMchtBaseInfTmpLogDAO(
			TblMchtBaseInfTmpLogDAO iTblMchtBaseInfTmpLogDAO) {
		this.iTblMchtBaseInfTmpLogDAO = iTblMchtBaseInfTmpLogDAO;
	}



}
