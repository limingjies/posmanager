package com.allinfinance.struts.model.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.allinfinance.bo.impl.model.TblModelInfoService;
import com.allinfinance.bo.impl.model.TblModelParameterService;
import com.allinfinance.bo.term.T3010BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.po.PrimaryKey;
import com.allinfinance.po.TblModelInfo;
import com.allinfinance.po.TblModelParameter;
import com.allinfinance.po.TblTermInfTmp;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.JSONBean;

public class ModelParameterAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	private TblModelParameterService modelParameterService = (TblModelParameterService) ContextUtil.getBean("modelParameterService");
	private TblModelInfoService modelInfoService = (TblModelInfoService) ContextUtil.getBean("modelInfoService");
	private Object[] objs;
	private T3010BO t3010BO = (T3010BO) ContextUtil.getBean("t3010BO");
	
	@Override
	protected String subExecute() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String flag = request.getParameter("flag");
		//新增模板
		if ("add".equals(flag)) {
			String modelName = request.getParameter("modelName");
			String printNumber = request.getParameter("printNumber");
			TblModelInfo modelInfo = new TblModelInfo();
			modelInfo.setModelName(modelName);
			modelInfo.setPrintNumber(Integer.parseInt(printNumber));
			try {
				//模板信息保存
				modelInfoService.save(modelInfo);
				String[] fieldName =request.getParameterValues("fieldName");
				String[] fontSize =request.getParameterValues("fontSize");
				String[] fieldOrder =request.getParameterValues("fieldOrder");
				//String[] contFormatType =request.getParameterValues("contFormatType");
				String[] contFormat =request.getParameterValues("contFormat");
				String[] status =request.getParameterValues("status");
				int y=0;
				for (int i = 0; i < status.length; i++) {
					TblModelParameter modelmeterParaInfo = new TblModelParameter();
					PrimaryKey primarykey = new PrimaryKey();
					primarykey.setModelId(modelInfo.getModelId());
					//禅道97任务要求第2联参数的序号从+50开始：目前页面每联参数固定20个，所以当从0开始，参数在大于等于20时加31就代表第二联参数从51开始了
					if (i>=20) {
						primarykey.setParameterId(i+31);
					}else {
						primarykey.setParameterId(i+1);
					}
					modelmeterParaInfo.setPrimaryKey(primarykey);
					if ("1".equals(status[i])) {
						for (int j = 0; j < fontSize.length; j++) {
							modelmeterParaInfo.setFieldName((fieldName[y]==null||"".equals(fieldName[y])) ? null:fieldName[y]);
							//modelmeterParaInfo.setContFormatType((contFormatType[y]==null||"".equals(contFormatType[y])) ? null:contFormatType[y]);
							modelmeterParaInfo.setContFormat((contFormat[y]==null||"".equals(contFormat[y])) ? null:contFormat[y]);
							modelmeterParaInfo.setStatus("1");
							modelmeterParaInfo.setFontSize((fontSize[y]==null||"".equals(fontSize[y])) ? null:Integer.parseInt(fontSize[y]));
							modelmeterParaInfo.setFieldOrder((fieldOrder[y]==null||"".equals(fieldOrder[y])) ? null:Integer.parseInt(fieldOrder[y]));
						}
						y++;
					}else {
						modelmeterParaInfo.setStatus("0");
						modelmeterParaInfo.setFontSize(1);
					}
					modelParameterService.save(modelmeterParaInfo);
				}
			} catch (Exception e) {
				//若上面代码出现异常，则新增的数据
				modelParameterService.delete(modelInfo.getModelId());
				modelInfoService.delete(modelInfo);
				return "操作失败";
			}
		}else if ("load".equals(flag)) {
			String hql = "select a.MODEL_Id, a.MODEL_NAME, a.PRINT_NUMBER, b.FIELD_NAME, b.FONT_SIZE, b.FIELD_ORDER, b.CONT_FORMAT_TYPE, b.CONT_FORMAT,b.STATUS from TBL_MODEL_INFO a, TBL_MODEL_PARAMETER b where a.MODEL_ID = b.MODEL_ID and a.MODEL_ID = "+modelId+" order by b.PARAMETER_ID ";
			@SuppressWarnings("unchecked")
			List<Object[]> data = commQueryDAO.findBySQLQuery(hql);
			
			List<Object> jsonDataList = new ArrayList<Object>(); 
			String str ="modelId,modelName,printNumber,fieldName,fontSize,fieldOrder,contFormatType,contFormat,status";
			String[] columns = str.split(",");
			for(Object[] list1 : data) {
				int columnLen = list1.length;
				Map<String, String> jsonMap = new HashMap<String, String>();
				for(int i = 0; i < columnLen; i++) {
					jsonMap.put(columns[i], transNull(list1[i]));
				}
				jsonDataList.add(jsonMap);
			}
			JSONBean bean = new JSONBean();
			bean.addJSONElement(Constants.JSON_HEANDER_TOTALCOUNT, data.size());
			bean.addChild(Constants.JSON_HEANDER_DATA, jsonDataList);
			if (!StringUtil.isNull(bean.toString())) {
				writeMessage(bean.toString());
			}
			return bean.toString();
			
		}else if ("upd".equals(flag)) {

			String modelName = request.getParameter("modelName");
			String modelId = request.getParameter("modelId");
			String printNumber = request.getParameter("printNumber");
			String hql ="";
			TblModelInfo modelInfo = modelInfoService.loadModelById(modelId);
			modelInfo.setModelName(modelName);
			modelInfo.setStatus("0");
			modelInfo.setPrintNumber(Integer.parseInt(printNumber));
			try {
				//模板信息保存
				modelInfoService.update(modelInfo);
				String[] fieldName =request.getParameterValues("fieldName");
				String[] fontSize =request.getParameterValues("fontSize");
				String[] fieldOrder =request.getParameterValues("fieldOrder");
				//String[] contFormatType =request.getParameterValues("contFormatType");
				String[] contFormat =request.getParameterValues("contFormat");
				String[] status =request.getParameterValues("status");

				int y=0;
				for (int i = 0; i < status.length; i++) {
					if (i>=20) {
						hql = "from TblModelParameter where MODEL_ID = "+modelId +" and PARAMETER_ID = '" +(i+31)+"'";
					}else {
						hql = "from TblModelParameter where MODEL_ID = "+modelId +" and PARAMETER_ID = '" +(i+1)+"'";
					}
					
					@SuppressWarnings("unchecked")
					List<TblModelParameter> list = commQueryDAO.findByHQLQuery(hql);
					TblModelParameter modelmeterParaInfo = new TblModelParameter();	
					if (list.size()>0) {
						modelmeterParaInfo = list.get(0);
					}

					if ("1".equals(status[i])) {
						for (int j = 0; j < fontSize.length; j++) {
							modelmeterParaInfo.setFieldName((fieldName[y]==null||"".equals(fieldName[y])) ? null:fieldName[y]);
							modelmeterParaInfo.setContFormat((contFormat[y]==null||"".equals(contFormat[y])) ? null:contFormat[y]);
							modelmeterParaInfo.setStatus("1");
							modelmeterParaInfo.setFontSize((fontSize[y]==null||"".equals(fontSize[y])) ? null:Integer.parseInt(fontSize[y]));
							modelmeterParaInfo.setFieldOrder((fieldOrder[y]==null||"".equals(fieldOrder[y])) ? null:Integer.parseInt(fieldOrder[y]));
						}
						y++;
					}else {
						modelmeterParaInfo.setStatus("0");
						modelmeterParaInfo.setFontSize(1);
					}
					//对应160223版本做的变更。改版本上模板参数为40个，比之前多了20个。所以在修改时少的部分应该调用新增方法。--yww 20160113
					if (null==modelmeterParaInfo.getCreateTime() || "".equals(modelmeterParaInfo.getCreateTime())) {
						PrimaryKey primarykey = new PrimaryKey();
						primarykey.setModelId(modelInfo.getModelId());
						//禅道97任务要求第2联参数的序号从+50开始：目前页面每联参数固定20个，所以当从0开始，参数在大于等于20时加31就代表第二联参数从51开始了
						if (i>=20) {
							primarykey.setParameterId(i+31);
						}else {
							primarykey.setParameterId(i+1);
						}
						modelmeterParaInfo.setPrimaryKey(primarykey);
						modelParameterService.save(modelmeterParaInfo);
					}else {
						modelParameterService.update(modelmeterParaInfo);
					}
				}
			} catch (Exception e) {
				return "操作失败";
			}
		
		}else if ("publish".equals(flag)) {
			String modelId =request.getParameter("modelId");
			TblModelInfo info = modelInfoService.loadModelById(modelId);
			info.setStatus("1");
			//每重新发布一次，模板的版本号加一
			int version = info.getVersion()+1;
			info.setVersion(version);
			modelInfoService.update(info);
		}else if ("stop".equals(flag)) {
			String modelId =request.getParameter("modelId");
			List<TblTermInfTmp> list = t3010BO.loadByMisc1(modelId);
			if (list.size()>0) {
				writeMessage("{msg:'failure'}");
				return "";
			}else {
				TblModelInfo info = modelInfoService.loadModelById(modelId);
				info.setStatus("0");
				modelInfoService.update(info);
			}
		}else if ("checkName".equals(flag)) {
			String name =request.getParameter("name");
			boolean bo = modelInfoService.checkModelName(name);
			if (!bo) {
				return "failure";
			}
		}else if ("delete".equals(flag)) {
			String modelId =request.getParameter("modelId");
			List<TblTermInfTmp> list = t3010BO.loadByMisc1(modelId);
			if (list.size()>0) {
				writeErrorMsg("failure");
				return null;
			}else {
				TblModelInfo info = modelInfoService.loadModelById(modelId);
				if (info!=null&&"0".equals(info.getStatus())){
					modelInfoService.delete(info);
					modelParameterService.delete(Integer.parseInt(modelId));
					writeSuccessMsg(" ");
				}else {
					writeErrorMsg("failure");
					return null;
				}
			}
		}
		return Constants.SUCCESS_CODE;
	}

	private String modelId;

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public TblModelParameterService getModelParameterService() {
		return modelParameterService;
	}

	public void setModelParameterService(
			TblModelParameterService modelParameterService) {
		this.modelParameterService = modelParameterService;
	}

	public TblModelInfoService getModelInfoService() {
		return modelInfoService;
	}

	public void setModelInfoService(TblModelInfoService modelInfoService) {
		this.modelInfoService = modelInfoService;
	}

	public Object[] getObjs() {
		return objs;
	}

	public void setObjs(Object[] objs) {
		this.objs = objs;
	}

	/**
	 * 转化空指针
	 * @param object
	 * @return
	 */
	private static String transNull(Object object) {
		if(object == null) {
			return "";
		} else {
			return object.toString().trim();
		}
	}

	public T3010BO getT3010BO() {
		return t3010BO;
	}

	public void setT3010BO(T3010BO t3010bo) {
		t3010BO = t3010bo;
	}
}
