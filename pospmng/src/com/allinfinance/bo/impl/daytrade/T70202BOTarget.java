package com.allinfinance.bo.impl.daytrade;

import java.util.List;

import org.hibernate.HibernateException;

import com.allinfinance.bo.daytrade.T70202BO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.daytrade.TblWithdrawInfDAO;
import com.allinfinance.dao.iface.daytrade.WebFrontTxnLogDAO;
import com.allinfinance.dwr.daytrade.Front044001;
import com.allinfinance.frontend.dto.withdraw.WithDrawJsonDto;
import com.allinfinance.po.daytrade.TblWithdrawInf;
import com.allinfinance.po.daytrade.TblWithdrawInfDtl;
import com.allinfinance.po.daytrade.WebFrontTxnLog;
import com.allinfinance.po.daytrade.WebFrontTxnLogPK;
import com.allinfinance.system.util.CommonFunction;

public class T70202BOTarget implements T70202BO {

	private TblWithdrawInfDAO tblWithdrawInfDAO;
	private WebFrontTxnLogDAO webFrontTxnLogDAO;

	@Override
	public TblWithdrawInf get(String key) throws HibernateException {
		// TODO Auto-generated method stub
		return tblWithdrawInfDAO.get(key);
	}

	@Override
	public String saveOrUpdate(TblWithdrawInf tblWithdrawInf)
			throws HibernateException {
		// TODO Auto-generated method stub
		tblWithdrawInfDAO.saveOrUpdate(tblWithdrawInf);
		return Constants.SUCCESS_CODE;
	}

	public void setTblWithdrawInfDAO(TblWithdrawInfDAO tblWithdrawInfDAO) {
		this.tblWithdrawInfDAO = tblWithdrawInfDAO;
	}

	@Override
	public List<TblWithdrawInfDtl> getDtlList(String batchNo) throws Exception {
		// TODO Auto-generated method stub
		String hql = " from TblWithdrawInfDtl t " + "where t.batchNo = '"
				+ batchNo + "'";
		@SuppressWarnings("unchecked")
		List<TblWithdrawInfDtl> dataList = CommonFunction.getCommQueryDAO()
				.findByHQLQuery(hql);
		return dataList;
	}

	@Override
	public String wdProcess(TblWithdrawInf tblWithdrawInf,
			WithDrawJsonDto withDrawJson) throws Exception {
		Front044001 front044001 = new Front044001();
		Object[] ret = front044001.doTxn(withDrawJson);

		if (Constants.SUCCESS_CODE.equals(ret[0])) {
			WebFrontTxnLog webFrontTxnLog = (WebFrontTxnLog) ret[1];
			// 处理 tblWithdrawInf
			tblWithdrawInf.setWdTime(withDrawJson.getWebTime());
			tblWithdrawInf.setWdSeqNum(withDrawJson.getWebSeqNum());
			tblWithdrawInf.setWdStatus('2'); // 审核通过，处理中
			tblWithdrawInf.setCheckTime(CommonFunction.getCurrentDateTime());
			webFrontTxnLogDAO.save(webFrontTxnLog);
			tblWithdrawInfDAO.saveOrUpdate(tblWithdrawInf);
			// 返回成功信息
			return Constants.SUCCESS_CODE;
		} else {
			return (String) ret[1];
		}
	}

	public void setWebFrontTxnLogDAO(WebFrontTxnLogDAO webFrontTxnLogDAO) {
		this.webFrontTxnLogDAO = webFrontTxnLogDAO;
	}

	@Override
	public void delete(TblWithdrawInf tblWithdrawInf) {
		tblWithdrawInfDAO.delete(tblWithdrawInf);// TODO Auto-generated method stub
		
	}

}
