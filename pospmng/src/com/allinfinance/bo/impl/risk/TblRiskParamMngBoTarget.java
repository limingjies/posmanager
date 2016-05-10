
package com.allinfinance.bo.impl.risk;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.allinfinance.bo.risk.TblRiskParamMngBo;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.dao.iface.risk.TblRiskParamMngDAO;
import com.allinfinance.dao.iface.term.TblTermInfDAO;
import com.allinfinance.dao.iface.term.TblTermInfTmpDAO;
import com.allinfinance.po.TblTermInf;
import com.allinfinance.po.TblTermInfTmp;
import com.allinfinance.po.TblTermInfTmpPK;
import com.allinfinance.po.risk.TblRiskParamMng;
import com.allinfinance.po.risk.TblRiskParamMngPK;
import com.allinfinance.system.util.CommonFunction;

/**
 * 风控参数管理
 * @author Jee Khan
 *
 */
public class TblRiskParamMngBoTarget implements TblRiskParamMngBo {
	private TblRiskParamMngDAO tblRiskParamMngDAO;
	private TblTermInfDAO tblTermInfDAO;
	private TblTermInfTmpDAO tblTermInfTmpDAO;

	@Override
	public String save(TblRiskParamMng tblRiskParamMng, Operator operator){
		String retCode = "数据不正确！";
		if(tblRiskParamMng != null){
			TblRiskParamMngPK key = tblRiskParamMng.getId();
			key.setTermId(CommonFunction.fillString("00000000",' ',12,true));
			key.setRiskType("0");
			TblRiskParamMng temp = tblRiskParamMngDAO.get(key);
			if(temp == null){
				temp = tblRiskParamMng;
				setDefaultValue(temp);
				temp.setRegOpr(operator.getOprId());
				temp.setRegTime(CommonFunction.getCurrentDateTime());
				temp.setUpdOpr(operator.getOprId());
				temp.setUpdTime(CommonFunction.getCurrentDateTime());
			}else{
				copyValue(tblRiskParamMng,temp);
				temp.setUpdOpr(operator.getOprId());
				temp.setUpdTime(CommonFunction.getCurrentDateTime());
			}
			tblRiskParamMngDAO.saveOrUpdate(temp);
			tblRiskParamMng = temp;
			retCode = "00";
		}
		return retCode;
	}
	
	
	@Override	
	public String delete(TblRiskParamMng tblRiskParamMng, Operator operator){
		String retCode = "-1";
		if(tblRiskParamMng != null){
			tblRiskParamMngDAO.saveOrUpdate(tblRiskParamMng);
		}
		return retCode;
	}
	/**
	 * 批量修改终端风控信息及交易信息
	 * termInfos	终端信息，数据格式：商户号1#终端号1#终端创建时间1,商户号2#终端号2#终端创建时间2,---
	 */
	public String batchSaveTermInfo(TblRiskParamMng tblRiskParamMng,String termInfos,String tradeInfo, Operator operator){
		String retCode = "00";
		StringBuffer sbErr = new StringBuffer();	//错误信息
		String[] arrTerm = termInfos.split(",");	//原始终端信息
		for(String term : arrTerm){
			String[] info = term.split("#");
			TblTermInf tblTermInf = tblTermInfDAO.get(CommonFunction.fillString(info[1],' ',12,true));
			TblTermInfTmpPK pk = new TblTermInfTmpPK(CommonFunction.fillString(info[1],' ',12,true),CommonFunction.fillString(info[2],' ',14,true));
			TblTermInfTmp tblTermInfTmp = tblTermInfTmpDAO.get(pk);
			if(tblTermInf == null || tblTermInfTmp == null){
				sbErr.append( "没有获取到对应的终端信息[" + term + "]<br/>");
			}else{
				TblRiskParamMngPK key = new TblRiskParamMngPK(info[0],CommonFunction.fillString(info[1],' ',12,true),"1");
				tblRiskParamMng.setId(key);
				TblRiskParamMng temp = tblRiskParamMngDAO.get(key);
				if(temp == null){	//新增
					temp = tblRiskParamMng;
					setDefaultValue(temp);
					temp.setRegOpr(operator.getOprId());
					temp.setRegTime(CommonFunction.getCurrentDateTime());
					temp.setUpdOpr(operator.getOprId());
					temp.setUpdTime(CommonFunction.getCurrentDateTime());
				}else{	//修改
					copyValue(tblRiskParamMng,temp);
					temp.setUpdOpr(operator.getOprId());
					temp.setUpdTime(CommonFunction.getCurrentDateTime());
				}
				if(!info[2].equals(tblTermInf.getTermPara1())){	//更新交易信息
					tblTermInf.setRecUpdOpr(operator.getOprId());
					tblTermInf.setRecUpdTs(CommonFunction.getCurrentDateTime());
					tblTermInf.setTermPara1(tradeInfo);
					tblTermInfDAO.update(tblTermInf);
					tblTermInfTmp.setRecUpdOpr(operator.getOprId());
					tblTermInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
					tblTermInfTmp.setTermPara1(tradeInfo);
					tblTermInfTmpDAO.update(tblTermInfTmp);
				}
				tblRiskParamMngDAO.saveOrUpdate(temp);
			}
		}
		if(sbErr.toString().length() > 0){
			retCode = sbErr.toString();
		}
		return retCode;
	}
	
	/**
	 * 默认将风控参数设置为0
	 * @param tblRiskParamMng
	 */
	private void setDefaultValue(TblRiskParamMng tblRiskParamMng){
		java.lang.reflect.Field[] fields = TblRiskParamMng.class.getDeclaredFields();
		for(java.lang.reflect.Field field:fields){
			try{
				field.setAccessible(true);
				Object obj = field.get(tblRiskParamMng);
				if(obj instanceof Double || obj instanceof Integer){
					if(obj == null || (Double)obj <=0){
						field.set(tblRiskParamMng, 0);
					}
				}
			}catch(Exception e){
				
			}
		}
	}
	
	private void copyValue(TblRiskParamMng from,TblRiskParamMng to){
		setDefaultValue(from);
		java.lang.reflect.Field[] fields = TblRiskParamMng.class.getDeclaredFields();
		for(java.lang.reflect.Field field:fields){
			try{
				field.setAccessible(true);
				Object obj = field.get(from);
				if(obj instanceof Double || obj instanceof Integer || obj instanceof String){
					field.set(to, obj);
				}
			}catch(Exception e){
				
			}
		}
	}


	public TblRiskParamMngDAO getTblRiskParamMngDAO() {
		return tblRiskParamMngDAO;
	}


	public void setTblRiskParamMngDAO(TblRiskParamMngDAO tblRiskParamMngDAO) {
		this.tblRiskParamMngDAO = tblRiskParamMngDAO;
	}


	public TblTermInfDAO getTblTermInfDAO() {
		return tblTermInfDAO;
	}


	public void setTblTermInfDAO(TblTermInfDAO tblTermInfDAO) {
		this.tblTermInfDAO = tblTermInfDAO;
	}


	public TblTermInfTmpDAO getTblTermInfTmpDAO() {
		return tblTermInfTmpDAO;
	}


	public void setTblTermInfTmpDAO(TblTermInfTmpDAO tblTermInfTmpDAO) {
		this.tblTermInfTmpDAO = tblTermInfTmpDAO;
	}


	@Override
	public String batchAddTerm(List<TblTermInfTmp> list,
			TblRiskParamMng tblRiskParamMng, Operator operato) throws IllegalAccessException, InvocationTargetException {
		for (TblTermInfTmp tblTermInfTmp : list) {
			TblRiskParamMng riskParamMng = new TblRiskParamMng();
			BeanUtils.copyProperties(riskParamMng, tblRiskParamMng);
			tblTermInfTmp.setRecUpdOpr(operato.getOprId());
			tblTermInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
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
				org.springframework.beans.BeanUtils.copyProperties(tblRiskParamMng, tblRiskParamMng2, new String[]{"id","regTime","regOpr"});
				tblRiskParamMng2.setUpdOpr(tblTermInfTmp.getRecUpdOpr());
				tblRiskParamMng2.setUpdTime(CommonFunction.getCurrentDateTime());
				tblRiskParamMngDAO.update(tblRiskParamMng2);
			}
			tblTermInfTmpDAO.update(tblTermInfTmp);
		}
		return Constants.SUCCESS_CODE;
	}


}
