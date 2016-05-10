package com.allinfinance.struts.risk.action;

import com.allinfinance.bo.risk.T40200BO;
import com.allinfinance.common.Constants;
import com.allinfinance.po.risk.TblRiskParamDef;
import com.allinfinance.po.risk.TblRiskParamDefPK;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.BeanUtils;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T40200Action extends BaseAction {
	
	T40200BO t40200BO = (T40200BO) ContextUtil.getBean("T40200BO");
	
	/* (non-Javadoc)
	 * @see com.allinfinance.struts.system.action.BaseAction#subExecute()
	 */
	@Override
	protected String subExecute() throws Exception {
		
		if("add".equals(method)) {
			log("新增风控模型参数");
			rspCode = add();
		} else if("delete".equals(method)) {
			log("删除风控模型参数");
			rspCode = delete();
		} else if("update".equals(method)) {
			log("重定义风控模型参数");
			rspCode = update();
		} else {
			return "未知的交易类型";
		}
		return rspCode;
	}
	
	private String update() throws Exception {
		// TODO Auto-generated method stub
		jsonBean.parseJSONArrayData(getModelDataList());
		int len = jsonBean.getArray().size();
		TblRiskParamDef tblRiskParamDef=null;
		TblRiskParamDefPK tblRiskParamDefPK =null;
		TblRiskParamDef oldTblRiskParamDef=null;
		
		for(int i = 0; i < len; i++) {
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			tblRiskParamDef = new TblRiskParamDef();
			tblRiskParamDefPK = new TblRiskParamDefPK();
			BeanUtils.setObjectWithPropertiesValue(tblRiskParamDefPK,jsonBean,false);
			BeanUtils.setObjectWithPropertiesValue(tblRiskParamDef,jsonBean,false);
			tblRiskParamDef.setId(tblRiskParamDefPK);
			oldTblRiskParamDef = new TblRiskParamDef();
			oldTblRiskParamDef = t40200BO.get(tblRiskParamDefPK);
			if(oldTblRiskParamDef==null)
				return "参数信息不存在，请重新刷新选择！";
			if(tblRiskParamDef.getDefaultValue().length()>Integer.parseInt(tblRiskParamDef.getParamLen().toString())){
				if(len>1){
					return "第"+(i+1)+"条参数值的长度大于此参数的上限长度！";
				}else{
					return "此参数值的长度大于此参数的上限长度！";
				}
			}
			String riskId=tblRiskParamDef.getId().getRiskId();
			String paramSeq=tblRiskParamDef.getId().getParamSeq();
			if(("A00".equals(riskId)&&"1".equals(paramSeq))||
					("A01".equals(riskId)&&"1".equals(paramSeq))||
					"A02".equals(riskId)||
					("A05".equals(riskId)&&"2".equals(paramSeq))||
					("A06".equals(riskId)&&"2".equals(paramSeq))||
					("A07".equals(riskId)&&"2".equals(paramSeq))||
					("A08".equals(riskId)&&"1".equals(paramSeq))||
					("A09".equals(riskId)&&"2".equals(paramSeq))||
					("A10".equals(riskId)&&"1".equals(paramSeq))||
					("A15".equals(riskId)&&"1".equals(paramSeq))||
					("A16".equals(riskId)&&"1".equals(paramSeq))||
					("A18".equals(riskId)&&"1".equals(paramSeq))
					){
				if(tblRiskParamDef.getDefaultValue().split("\\.").length>1){
					if(len>1){
						return "第"+(i+1)+"条参数值请输入整数！";
					}else{
						return "此参数值请输入整数！";
					}
				}
			}
			if(("A08".equals(riskId)&&"2".equals(paramSeq))||("A13".equals(riskId)&&"1".equals(paramSeq))){
				if(Double.parseDouble(tblRiskParamDef.getDefaultValue().toString())>1){
					if(len>1){
						return "第"+(i+1)+"条参数值比率不能大于1！";
					}else{
						return "此参数值比率不能大于1！";
					}
				}
			}
			if(("A10".equals(riskId)&&"2".equals(paramSeq))||("A10".equals(riskId)&&"3".equals(paramSeq))){
				if(tblRiskParamDef.getDefaultValue().split("\\.").length>1||Integer.parseInt(tblRiskParamDef.getDefaultValue().toString())>24){
					if(len>1){
						return "第"+(i+1)+"条参数值请输入00-24之间！";
					}else{
						return "此参数值请输入00-24之间！";
					}
				}
			}
			t40200BO.update(tblRiskParamDef, oldTblRiskParamDef, operator);
		}
		return Constants.SUCCESS_CODE;
	}

	private String add() throws Exception {
		return Constants.SUCCESS_CODE;
	}

	private String delete() throws Exception {
		return Constants.SUCCESS_CODE;
	}
	
	private String modelDataList;

	/**
	 * @return the modelDataList
	 */
	public String getModelDataList() {
		return modelDataList;
	}

	/**
	 * @param modelDataList the modelDataList to set
	 */
	public void setModelDataList(String modelDataList) {
		this.modelDataList = modelDataList;
	}
	
	
}
