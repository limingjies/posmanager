package com.allinfinance.bo.impl.base;

import java.util.List;

import com.allinfinance.bo.base.T10103BO;
import com.allinfinance.common.CommonsConstants;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.commquery.dao.ICommQueryDAO;
//import com.allinfinance.dao.iface.base.TblBrhFeeCfgDAO;
import com.allinfinance.dao.iface.base.TblBrhFeeCfgZlfDAO;
import com.allinfinance.dao.iface.base.TblBrhFeeCtlDAO;
//import com.allinfinance.po.base.TblBrhFeeCfg;
import com.allinfinance.po.base.TblBrhFeeCfgPK;
import com.allinfinance.po.base.TblBrhFeeCfgZlf;
import com.allinfinance.po.base.TblBrhFeeCtl;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.GenerateNextId;

public class T10103BOTarget implements T10103BO{

//	private TblBrhFeeCfgDAO tblBrhFeeCfgDAO;
	private TblBrhFeeCfgZlfDAO tblBrhFeeCfgZlfDAO;
	private TblBrhFeeCtlDAO tblBrhFeeCtlDAO;
	public ICommQueryDAO commQueryDAO;
	
//	@Override
//	public TblBrhFeeCfg get(TblBrhFeeCfgPK tblBrhFeeCfgPK) {
//		// TODO Auto-generated method stub
//		return tblBrhFeeCfgDAO.get(tblBrhFeeCfgPK);
//	}
//	@Override
	public TblBrhFeeCfgZlf get(TblBrhFeeCfgPK tblBrhFeeCfgPK) {
		// TODO Auto-generated method stub
		return tblBrhFeeCfgZlfDAO.get(tblBrhFeeCfgPK);
	}
//	@Override
//	public String stop(TblBrhFeeCfg tblBrhFeeCfg,Operator operator) {
//		// TODO Auto-generated method stub
//		tblBrhFeeCfg.setEnableFlag(CommonsConstants.ENABLE_FLAG_STOP);
//		tblBrhFeeCfg.setLstUpdTime(CommonFunction.getCurrentDateTime());
//		tblBrhFeeCfg.setLstUpdTlr(operator.getOprId());
//		tblBrhFeeCfgDAO.update(tblBrhFeeCfg);
//		return Constants.SUCCESS_CODE;
//	}
//	@Override
//	public String start(TblBrhFeeCfg tblBrhFeeCfg,Operator operator) {
//		// TODO Auto-generated method stub
//		tblBrhFeeCfg.setEnableFlag(CommonsConstants.ENABLE_FLAG_START);
//		tblBrhFeeCfg.setLstUpdTime(CommonFunction.getCurrentDateTime());
//		tblBrhFeeCfg.setLstUpdTlr(operator.getOprId());
//		tblBrhFeeCfgDAO.update(tblBrhFeeCfg);
//		return Constants.SUCCESS_CODE;
//	}
	@Override
	public String stop(TblBrhFeeCfgZlf tblBrhFeeCfgZlf,Operator operator) {
		// TODO Auto-generated method stub
		tblBrhFeeCfgZlf.setEnableFlag(CommonsConstants.ENABLE_FLAG_STOP);
		tblBrhFeeCfgZlf.setLstUpdTime(CommonFunction.getCurrentDateTime());
		tblBrhFeeCfgZlf.setLstUpdTlr(operator.getOprId());
		tblBrhFeeCfgZlfDAO.update(tblBrhFeeCfgZlf);
		return Constants.SUCCESS_CODE;
	}
	@Override
	public String start(TblBrhFeeCfgZlf tblBrhFeeCfgZlf,Operator operator) {
		// TODO Auto-generated method stub
		tblBrhFeeCfgZlf.setEnableFlag(CommonsConstants.ENABLE_FLAG_START);
		tblBrhFeeCfgZlf.setLstUpdTime(CommonFunction.getCurrentDateTime());
		tblBrhFeeCfgZlf.setLstUpdTlr(operator.getOprId());
		tblBrhFeeCfgZlfDAO.update(tblBrhFeeCfgZlf);
		return Constants.SUCCESS_CODE;
	}
//	@Override
//	public String stop(TblBrhFeeCfgZlf tblBrhFeeCfgZlf,Operator operator) {
//		// TODO Auto-generated method stub
//		tblBrhFeeCfgZlf.setEnableFlag(CommonsConstants.ENABLE_FLAG_STOP);
//		tblBrhFeeCfgZlf.setLstUpdTime(CommonFunction.getCurrentDateTime());
//		tblBrhFeeCfgZlf.setLstUpdTlr(operator.getOprId());
//		tblBrhFeeCfgZlfDAO.update(tblBrhFeeCfgZlf);
//		return Constants.SUCCESS_CODE;
//	}
//	@Override
//	public String start(TblBrhFeeCfgZlf tblBrhFeeCfgZlf,Operator operator) {
//		// TODO Auto-generated method stub
//		tblBrhFeeCfgZlf.setEnableFlag(CommonsConstants.ENABLE_FLAG_START);
//		tblBrhFeeCfgZlf.setLstUpdTime(CommonFunction.getCurrentDateTime());
//		tblBrhFeeCfgZlf.setLstUpdTlr(operator.getOprId());
//		tblBrhFeeCfgZlfDAO.update(tblBrhFeeCfgZlf);
//		return Constants.SUCCESS_CODE;
//	}
	
	@Override
	public TblBrhFeeCtl get(String discId) {
		// TODO Auto-generated method stub
		return tblBrhFeeCtlDAO.get(discId);
	}
	
	@Override
	public String addCtl(TblBrhFeeCtl tblBrhFeeCtl,Operator operator) {
		// TODO Auto-generated method stub
		tblBrhFeeCtl.setCreateTime(CommonFunction.getCurrentDateTime());
		tblBrhFeeCtl.setLstUpdTlr(operator.getOprId());
		tblBrhFeeCtlDAO.save(tblBrhFeeCtl);
		return Constants.SUCCESS_CODE;
	}
	@Override
	public String updateCtl(TblBrhFeeCtl tblBrhFeeCtl,Operator operator) {
		// TODO Auto-generated method stub
		tblBrhFeeCtl.setLstUpdTime(CommonFunction.getCurrentDateTime());
		tblBrhFeeCtl.setLstUpdTlr(operator.getOprId());
		tblBrhFeeCtlDAO.update(tblBrhFeeCtl);
		return Constants.SUCCESS_CODE;
	}
	@SuppressWarnings("unchecked")
	@Override
	public String deleteCtl(String discId) {
		// TODO Auto-generated method stub
		String hql=" from com.allinfinance.po.base.TblBrhFeeCfgZlf t "
				+ "where  t.tblBrhFeeCfgPK.discId='"+discId+"' ";
		List<TblBrhFeeCfgZlf> dataList =commQueryDAO.findByHQLQuery(hql);
		for (TblBrhFeeCfgZlf tblBrhFeeCfgZlf : dataList) {
			if(!tblBrhFeeCfgZlf.getEnableFlag().equals(CommonsConstants.ENABLE_FLAG_STOP)){
				return "该费率代码下有未停用的费率规则！";
			}
		}
		tblBrhFeeCtlDAO.delete(discId);
		for (TblBrhFeeCfgZlf tblBrhFeeCfgZlf : dataList) {
			tblBrhFeeCfgZlfDAO.delete(tblBrhFeeCfgZlf);
		}
		return Constants.SUCCESS_CODE;
	}
//	@Override
//	public String addCfg(TblBrhFeeCfg tblBrhFeeCfg, Operator operator) {
//		// TODO Auto-generated method stub
//		int seq=GenerateNextId.getBrhFeeCfgSeq(tblBrhFeeCfg.getTblBrhFeeCfgPK().getDiscId());
//		tblBrhFeeCfg.getTblBrhFeeCfgPK().setSeq(seq);
//		tblBrhFeeCfg.setCreateTime(CommonFunction.getCurrentDateTime());
//		tblBrhFeeCfg.setLstUpdTlr(operator.getOprId());
//		tblBrhFeeCfg.setEnableFlag(CommonsConstants.ENABLE_FLAG_STOP);
//		tblBrhFeeCfgDAO.save(tblBrhFeeCfg);
//		return Constants.SUCCESS_CODE;
//	}
//	@Override
//	public String updateCfg(TblBrhFeeCfg tblBrhFeeCfg, Operator operator) {
//		// TODO Auto-generated method stub
//		tblBrhFeeCfg.setLstUpdTime(CommonFunction.getCurrentDateTime());
//		tblBrhFeeCfg.setLstUpdTlr(operator.getOprId());
//		tblBrhFeeCfgDAO.update(tblBrhFeeCfg);
//		return Constants.SUCCESS_CODE;
//	}
	@Override
	public String addCfg(TblBrhFeeCfgZlf tblBrhFeeCfgZlf, Operator operator) {
		// TODO Auto-generated method stub
		int seq=GenerateNextId.getBrhFeeCfgZlfSeq(tblBrhFeeCfgZlf.getTblBrhFeeCfgPK().getDiscId());
		tblBrhFeeCfgZlf.getTblBrhFeeCfgPK().setSeq(seq);
		tblBrhFeeCfgZlf.setCreateTime(CommonFunction.getCurrentDateTime());
		tblBrhFeeCfgZlf.setLstUpdTlr(operator.getOprId());
		tblBrhFeeCfgZlf.setEnableFlag(CommonsConstants.ENABLE_FLAG_STOP);
		tblBrhFeeCfgZlfDAO.save(tblBrhFeeCfgZlf);
		return Constants.SUCCESS_CODE;
	}
	@Override
	public String updateCfg(TblBrhFeeCfgZlf tblBrhFeeCfgZlf, Operator operator) {
		// TODO Auto-generated method stub
		tblBrhFeeCfgZlf.setLstUpdTime(CommonFunction.getCurrentDateTime());
		tblBrhFeeCfgZlf.setLstUpdTlr(operator.getOprId());
		tblBrhFeeCfgZlfDAO.update(tblBrhFeeCfgZlf);
		return Constants.SUCCESS_CODE;
	}
//	@Override
//	public String deleteCfg(TblBrhFeeCfgPK tblBrhFeeCfgPK) {
//		// TODO Auto-generated method stub
//		tblBrhFeeCfgDAO.delete(tblBrhFeeCfgPK);
//		return Constants.SUCCESS_CODE;
//	}
	
//	@Override
//	public String addCfg(TblBrhFeeCfgZlf tblBrhFeeCfgZlf, Operator operator) {
//		// TODO Auto-generated method stub
//		int seq=GenerateNextId.getBrhFeeCfgSeq(tblBrhFeeCfgZlf.getTblBrhFeeCfgPK().getDiscId());
//		tblBrhFeeCfgZlf.getTblBrhFeeCfgPK().setSeq(seq);
//		tblBrhFeeCfgZlf.setCreateTime(CommonFunction.getCurrentDateTime());
//		tblBrhFeeCfgZlf.setLstUpdTlr(operator.getOprId());
//		tblBrhFeeCfgZlf.setEnableFlag(CommonsConstants.ENABLE_FLAG_STOP);
//		tblBrhFeeCfgZlfDAO.save(tblBrhFeeCfgZlf);
//		return Constants.SUCCESS_CODE;
//	}
//	@Override
//	public String updateCfg(TblBrhFeeCfgZlf tblBrhFeeCfgZlf, Operator operator) {
//		// TODO Auto-generated method stub
//		tblBrhFeeCfgZlf.setLstUpdTime(CommonFunction.getCurrentDateTime());
//		tblBrhFeeCfgZlf.setLstUpdTlr(operator.getOprId());
//		tblBrhFeeCfgZlfDAO.update(tblBrhFeeCfgZlf);
//		return Constants.SUCCESS_CODE;
//	}
	@Override
	public String deleteCfg(TblBrhFeeCfgPK tblBrhFeeCfgPK) {
		// TODO Auto-generated method stub
		tblBrhFeeCfgZlfDAO.delete(tblBrhFeeCfgPK);
		return Constants.SUCCESS_CODE;
	}
//	public TblBrhFeeCfgDAO getTblBrhFeeCfgDAO() {
//		return tblBrhFeeCfgDAO;
//	}
//	public void setTblBrhFeeCfgDAO(TblBrhFeeCfgDAO tblBrhFeeCfgDAO) {
//		this.tblBrhFeeCfgDAO = tblBrhFeeCfgDAO;
//	}
	public TblBrhFeeCfgZlfDAO getTblBrhFeeCfgZlfDAO() {
		return tblBrhFeeCfgZlfDAO;
	}
	public void setTblBrhFeeCfgZlfDAO(TblBrhFeeCfgZlfDAO tblBrhFeeCfgZlfDAO) {
		this.tblBrhFeeCfgZlfDAO = tblBrhFeeCfgZlfDAO;
	}
	public TblBrhFeeCtlDAO getTblBrhFeeCtlDAO() {
		return tblBrhFeeCtlDAO;
	}
	public void setTblBrhFeeCtlDAO(TblBrhFeeCtlDAO tblBrhFeeCtlDAO) {
		this.tblBrhFeeCtlDAO = tblBrhFeeCtlDAO;
	}
	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}
	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}
	
}
