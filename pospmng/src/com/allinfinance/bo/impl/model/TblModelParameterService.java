package com.allinfinance.bo.impl.model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.StringUtil;
import com.allinfinance.dao.impl.TblModelParameterDao;
import com.allinfinance.po.TblModelParameter;

/**
 * Title:签购单模板打印管理
 * 
 * @version 1.0
 */

public class TblModelParameterService {

	public TblModelParameterDao modelParameterDao;

	/**
	 * 新增模板数据
	 * 
	 * @param modelParameter
	 * @return 响应码
	 */
	public void save(TblModelParameter modelParameter) {
		HttpServletRequest request = ServletActionContext.getRequest();
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");       
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间       
		String time = formatter.format(curDate);  
		modelParameter.setCreateTime(time);
		modelParameter.setUpdateTime(time);
		modelParameter.setOperateId(operator.getOprId());
		modelParameterDao.add(modelParameter);
	}

	/**
	 * 更新模板数据
	 * 
	 * @param modelParameter
	 * @return 响应码
	 */
	public void update(TblModelParameter modelParameter) {
		HttpServletRequest request = ServletActionContext.getRequest();
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");       
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间       
		String time = formatter.format(curDate);  
		//更新修改时间 和 操作人ID
		modelParameter.setUpdateTime(time);
		modelParameter.setOperateId(operator.getOprId());
		modelParameterDao.update(modelParameter);
	}

	/**
	 * 依据模板ID查询对应的模板数据
	 * 
	 * @param modelId
	 *            模板ID
	 * @return 模板Info
	 */
	public TblModelParameter loadModelById(String modelId) {
		if (StringUtil.isNull(modelId)) {
			return null;
		}
		TblModelParameter info = modelParameterDao.load(modelId);
		if (null == info) {
			return null;
		} else {
			return info;
		}
	}
	
	
	/**
	 * 删除
	 */
	public void delete(int modelId) {
		String sql = "DELETE FROM TBL_MODEL_PARAMETER WHERE MODEL_ID = "+modelId;
		modelParameterDao.delete(sql);
	}
	

	/**
	 * 查询所有模板数据
	 * 
	 * @param modelId
	 *            模板ID
	 * @return 模板list
	 */
	public List<TblModelParameter> loadAllModel(String modelId) {
		if (StringUtil.isNull(modelId)) {
			List<TblModelParameter> list = modelParameterDao.loadAll();
			return list;
		} else {
			List<TblModelParameter> list = modelParameterDao
					.LoadAllBykey(modelId);
			return list;
		}
	}

	public TblModelParameterDao getmodelParameterDao() {
		return modelParameterDao;
	}

	public void setmodelParameterDao(TblModelParameterDao modelParameterDao) {
		this.modelParameterDao = modelParameterDao;
	}

}
