package com.allinfinance.bo.impl.model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.StringUtil;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.impl.TblModelInfoDao;
import com.allinfinance.po.TblModelInfo;


/**
 * Title:签购单模板打印管理
 * 
 * @version 1.0
 */

public class TblModelInfoService {

	public TblModelInfoDao modelInfoDao;

	private ICommQueryDAO commQueryDAO;
	
	/**
	 * 新增模板数据
	 * @param modelInfo
	 * @return 响应码
	 */
	@SuppressWarnings("unchecked")
	public String save(TblModelInfo modelInfo) {
		HttpServletRequest request = ServletActionContext.getRequest();
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		/*String hql = "From TblModelInfo t where t.modelName = '"+modelInfo.getModelName()+"' order by t.modelId asc ";
		List<TblModelInfo> data = commQueryDAO.findByHQLQuery(hql);
		if(data != null && data.size() > 0) {
			return "模板名称已经存在";
			//存在后应给出提示，并不继续下一步操作
		}*/
		String hql2 = "From TblModelInfo t order by t.modelId ";
		List<TblModelInfo> list = commQueryDAO.findByHQLQuery(hql2);
		if (list.size()>0) {
			TblModelInfo info = list.get(list.size()-1);
			int x = info.getModelId();
			modelInfo.setModelId(x+1);
		}else {
			modelInfo.setModelId(1);
		}
		modelInfo.setVersion(0);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");       
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间       
		String time = formatter.format(curDate);  
		modelInfo.setCreateTime(time);
		modelInfo.setUpdateTime(time);
		//操作人ID
		modelInfo.setOperateId(operator.getOprId());
		modelInfo.setStatus("0");
		modelInfoDao.add(modelInfo);
		return Constants.SUCCESS_CODE;
	}

	/**
	 * 更新模板数据
	 * @param modelInfo
	 * @return 响应码
	 */
	public void update(TblModelInfo modelInfo) {
		HttpServletRequest request = ServletActionContext.getRequest();
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");       
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间       
		String time = formatter.format(curDate);  
		//更新修改时间 和 操作人ID
		modelInfo.setUpdateTime(time);
		modelInfo.setOperateId(operator.getOprId());
		modelInfoDao.update(modelInfo);
	}

	/**
	 * 依据模板ID查询对应的模板数据
	 * @param modelId	模板ID
	 * @return 模板Info
	 */
	public TblModelInfo loadModelById(String modelId){
		if(StringUtil.isNull(modelId)){
			return null;
		}
		TblModelInfo info = modelInfoDao.get(Integer.parseInt(modelId));
		if (null == info) {
			return null;
		} else {
			return info;
		}
	}

	/**
	 * 依据模板名称获取对象，验证模板名称唯一性
	 */
	public boolean checkModelName(String key){
		String hql = "From TblModelInfo t where t.modelName = '"+key+"' order by t.modelId asc ";
		@SuppressWarnings("unchecked")
		List<TblModelInfo> data = commQueryDAO.findByHQLQuery(hql);
		if(data != null && data.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 查询所有模板数据
	 * @param modelId 模板ID
	 * @return 模板list
	 */
	public List<TblModelInfo> loadAllModel(String modelId){
		if(StringUtil.isNull(modelId)){
			List<TblModelInfo> list = modelInfoDao.loadAll();
			return list;
		}else {
			List<TblModelInfo> list = modelInfoDao.LoadAllBykey(modelId);
			return list;
		}
	}
	
	/**
	 * 删除对象
	 * @param modelInfo
	 */
	public void delete(TblModelInfo modelInfo) {
		modelInfoDao.delete(modelInfo);
	}
	
	public TblModelInfoDao getModelInfoDao() {
		return modelInfoDao;
	}

	public void setModelInfoDao(TblModelInfoDao modelInfoDao) {
		this.modelInfoDao = modelInfoDao;
	}

	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}
	
}
