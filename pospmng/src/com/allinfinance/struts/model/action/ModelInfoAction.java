package com.allinfinance.struts.model.action;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.grid.GridConfigUtil;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.SysParamUtil;

public class ModelInfoAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	//private TblModelInfoService modelInfoService = (TblModelInfoService) ContextUtil.getBean("TblModelInfoService");

	public String execute() {
		if(!Boolean.valueOf(SysParamUtil.getParam(SysParamConstants.PRODUCTION_MODE))) {
			try {
				GridConfigUtil.initGirdConfig(ServletActionContext.getServletContext());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			if (StringUtil.isNull(start)) {
				start = "0";
			}
			String jsonData = GridConfigUtil.getGridData(modelId, Integer.parseInt(start), request);

			writeMessage(jsonData);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
		}
		return SUCCESS;
	}

	@Override
	protected String subExecute() throws Exception {
		return Constants.SUCCESS_CODE;
	}

	/**数据仓库编号*/
//	private String storeId;
	private String start;
	private String modelId;

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	
}
