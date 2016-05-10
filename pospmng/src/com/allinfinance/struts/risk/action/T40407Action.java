package com.allinfinance.struts.risk.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.allinfinance.bo.risk.TblRiskParamMngBo;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.term.TblTermInfTmpDAO;
import com.allinfinance.po.TblTermInfTmp;
import com.allinfinance.po.TblTermInfTmpPK;
import com.allinfinance.po.risk.TblRiskParamMng;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

/**
 * 终端风控参数维护
 * 
 * @author Jee Khan
 *
 */
@SuppressWarnings("serial")
public class T40407Action extends BaseAction {

	private TblRiskParamMngBo tblRiskParamMngBo;
	private TblRiskParamMng tblRiskParamMng;
	private String termInfo;
	private String tradeInfo;
	private String termIds;
	private String mchtIds;
	private String recCrtTs;
	private String termParam1;
	private TblTermInfTmpDAO tblTermInfTmpDAO;
	@Override
	protected String subExecute() throws Exception {
		tblRiskParamMngBo = (TblRiskParamMngBo) ContextUtil
				.getBean("tblRiskParamMngBo");
		tblTermInfTmpDAO = (TblTermInfTmpDAO) ContextUtil
				.getBean("TblTermInfTmpDAO");
		if ("add".equals(getMethod())) {
			rspCode = add();
		} else if ("edit".equals(getMethod())) {
			rspCode = edit();
		}
		return rspCode;
	}

	private String edit() {
		if (this.termInfo == null || "".equals(this.termInfo)) {
			return "终端信息不可为空！";
		}
		String ret = tblRiskParamMngBo.batchSaveTermInfo(tblRiskParamMng,
				termInfo, tradeInfo, operator);
		return ret;
	}

	/**
	 * 终端审核时，终端风控参数修改以及批量修改
	 * 
	 * @return
	 */
	private String add() {
		if (StringUtils.isBlank(mchtIds) || StringUtils.isBlank(termIds)
				|| StringUtils.isBlank(recCrtTs)
				|| StringUtils.isBlank(termParam1)) {
			return "终端信息为空，请刷新页面重试！";
		}
		String[] termNos = termIds.split("\\|");
		//String[] mchtNos = mchtIds.split("\\|");
		String[] recTs = recCrtTs.split("\\|");
		if(termNos.length!=recTs.length){
			return "终端信息错误，请刷新页面重试！";
		}
		List<TblTermInfTmp> list = new ArrayList<TblTermInfTmp>();
		for (int i = 0; i < termNos.length; i++) {
			TblTermInfTmpPK key = new TblTermInfTmpPK(CommonFunction.fillString(termNos[i], ' ', 12, true),CommonFunction.fillString(recTs[i], ' ', 14, true) );
			TblTermInfTmp tblTermInfTmp = new TblTermInfTmp();
			tblTermInfTmp = tblTermInfTmpDAO.get(key );
			if(tblTermInfTmp == null){
				return "查询终端信息失败，请刷新页面重试！";
			}
			tblTermInfTmp.setTermPara1(termParam1);
			list.add(tblTermInfTmp);
		}
		String result;
		try {
			result = tblRiskParamMngBo.batchAddTerm(list,tblRiskParamMng,operator);
			if(Constants.SUCCESS_CODE.equals(result)){
				return result;
			}else 
			return "终端风控参数修改失败！";
		} catch (Exception e) {
			e.printStackTrace();
			log("终端风控参数修改失败:"+e.getMessage());
			return "终端风控参数修改失败！";
		} 
		
	}

	public TblRiskParamMngBo getTblRiskParamMngBo() {
		return tblRiskParamMngBo;
	}

	public void setTblRiskParamMngBo(TblRiskParamMngBo tblRiskParamMngBo) {
		this.tblRiskParamMngBo = tblRiskParamMngBo;
	}

	public TblRiskParamMng getTblRiskParamMng() {
		return tblRiskParamMng;
	}

	public void setTblRiskParamMng(TblRiskParamMng tblRiskParamMng) {
		this.tblRiskParamMng = tblRiskParamMng;
	}

	public String getTermInfo() {
		return termInfo;
	}

	public void setTermInfo(String termInfo) {
		this.termInfo = termInfo;
	}

	public String getTradeInfo() {
		return tradeInfo;
	}

	public void setTradeInfo(String tradeInfo) {
		this.tradeInfo = tradeInfo;
	}

	/**
	 * @return the termIds
	 */
	public String getTermIds() {
		return termIds;
	}

	/**
	 * @param termIds
	 *            the termIds to set
	 */
	public void setTermIds(String termIds) {
		this.termIds = termIds;
	}

	/**
	 * @return the mchtIds
	 */
	public String getMchtIds() {
		return mchtIds;
	}

	/**
	 * @param mchtIds
	 *            the mchtIds to set
	 */
	public void setMchtIds(String mchtIds) {
		this.mchtIds = mchtIds;
	}

	/**
	 * @return the recCrtTs
	 */
	public String getRecCrtTs() {
		return recCrtTs;
	}

	/**
	 * @param recCrtTs
	 *            the recCrtTs to set
	 */
	public void setRecCrtTs(String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	/**
	 * @return the termParam1
	 */
	public String getTermParam1() {
		return termParam1;
	}

	/**
	 * @param termParam1
	 *            the termParam1 to set
	 */
	public void setTermParam1(String termParam1) {
		this.termParam1 = termParam1;
	}

}
