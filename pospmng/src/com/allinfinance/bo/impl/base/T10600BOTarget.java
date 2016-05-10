package com.allinfinance.bo.impl.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.allinfinance.bo.base.T10600BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.TblMchntInfoConstants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.base.TblRouteRuleDAO;
import com.allinfinance.dao.iface.mchnt.TblBlukImpRetInfDAO;
import com.allinfinance.po.TblRouteRule;
import com.allinfinance.po.TblRouteRulePK;
import com.allinfinance.po.mchnt.TblBlukImpRetInf;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.GenerateNextId;
import com.allinfinance.system.util.SysParamUtil;
import com.allinfinance.system.util.excelReport;

public class T10600BOTarget implements T10600BO{

	private TblRouteRuleDAO tblRouteRuleDAO;
	public ICommQueryDAO commQueryDAO;
	private TblBlukImpRetInfDAO tblBlukImpRetInfDAO;
	@Override
	public String add(TblRouteRule tblRouteRule) {
		// TODO Auto-generated method stub
		tblRouteRuleDAO.save(tblRouteRule);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(TblRouteRule tblRouteRule) {
		// TODO Auto-generated method stub
		tblRouteRuleDAO.delete(tblRouteRule);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String delete(TblRouteRulePK tblRouteRulePK) {
		// TODO Auto-generated method stub
		tblRouteRuleDAO.delete(tblRouteRulePK);
		return Constants.SUCCESS_CODE;
	}

	@Override
	public TblRouteRule get(TblRouteRulePK tblRouteRulePK) {
		// TODO Auto-generated method stub
		return tblRouteRuleDAO.get(tblRouteRulePK);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.base.T10600BO#addList(java.util.List)
	 */
	@Override
	public String addList(List<TblRouteRule> tblRouteRuleList,String routeRuleInfo,Operator operator) throws Exception {
		// TODO Auto-generated method stub
		//初始化商户路由ID
		String ruleIdIndex = GenerateNextId.getRuleId();
		// 存放待反馈文件商户路由数据
		List<String[]> dataList = new ArrayList<String[]>();
		String[] data ;
		// 获取本次操作的批次号(批量导入标识位+"P_"+CommonFunction.getCurrentDate()+4位递增的序号)
		String batchHead = TblMchntInfoConstants.BLUK_IMP_ROUTE + "P_";
		String batchId = GenerateNextId.getBatchNo(batchHead + CommonFunction.getCurrentDate() + "_");
		
		for(TblRouteRule tblRouteRule : tblRouteRuleList){
			tblRouteRule.getTblRouteRulePK().setRuleId(ruleIdIndex);
			data = new String[2];
			data[0] = batchId;
			data[1] = tblRouteRule.getTblRouteRulePK().getAccpId();
			dataList.add(data);
			tblRouteRuleDAO.save(tblRouteRule);
			ruleIdIndex = String.valueOf(Integer.parseInt(ruleIdIndex)+1);
		}
		
		// 统一文件名称
		String dateTime = CommonFunction.getCurrentDateTime();
		String fileName = batchHead + dateTime ;

		// 记录批量导入商户回执信息
		TblBlukImpRetInf tblBlukImpRetInf = new TblBlukImpRetInf();
		tblBlukImpRetInf.setBatchNo(batchId); //批次号
		tblBlukImpRetInf.setBlukDate(dateTime);  // 时间
		tblBlukImpRetInf.setBrhId(operator.getOprBrhId());  // 执行机构
		tblBlukImpRetInf.setOprId(operator.getOprId());  // 执行人员
		tblBlukImpRetInf.setBlukFileName(fileName);  // 回执文件名称
		tblBlukImpRetInf.setBlukMchnNum(String.valueOf(dataList.size()));  // 批导商户数量
		tblBlukImpRetInf.setMisc1(TblMchntInfoConstants.BLUK_IMP_ROUTE);  // 批量导入标识位：["M","商户批量导入"],["R","路由批量导入"]
		tblBlukImpRetInf.setMisc2("");  // 默认
		tblBlukImpRetInf.setMisc3(routeRuleInfo);  // 默认
		tblBlukImpRetInfDAO.save(tblBlukImpRetInf);
		
        String[] title={"批次号","批导商户号"};
        String head="路由批量导入回执文件";
        String downUrl=SysParamUtil.getParam(SysParamConstants.FILE_DOWNLOAD_DISK) + SysParamUtil.getParam(SysParamConstants.ROUTE_BLUK_IMP_RET);
        fileName=downUrl + fileName + ".xls";
        
        HashMap<String, Object> map=new HashMap<String, Object>();
        
        map.put("dataList", dataList);
        map.put("title", title);
        map.put("fileName", fileName);
        map.put("head", head);
        
        excelReport.bulkIptDownload(map,downUrl);
		// 便于回收内存
        map = null;
		
		return Constants.SUCCESS_CODE;
	}

	@Override
	public String update(TblRouteRule tblRouteRule) {
		// TODO Auto-generated method stub
		tblRouteRuleDAO.update(tblRouteRule);
		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblRouteRuleDAO
	 */
	public TblRouteRuleDAO getTblRouteRuleDAO() {
		return tblRouteRuleDAO;
	}

	/**
	 * @param tblRouteRuleDAO the tblRouteRuleDAO to set
	 */
	public void setTblRouteRuleDAO(TblRouteRuleDAO tblRouteRuleDAO) {
		this.tblRouteRuleDAO = tblRouteRuleDAO;
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
	 * @return the tblBlukImpRetInfDAO
	 */
	public TblBlukImpRetInfDAO getTblBlukImpRetInfDAO() {
		return tblBlukImpRetInfDAO;
	}

	/**
	 * @param tblBlukImpRetInfDAO the tblBlukImpRetInfDAO to set
	 */
	public void setTblBlukImpRetInfDAO(TblBlukImpRetInfDAO tblBlukImpRetInfDAO) {
		this.tblBlukImpRetInfDAO = tblBlukImpRetInfDAO;
	}
	
	

	
}
