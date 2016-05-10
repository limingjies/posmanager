package com.allinfinance.struts.risk.action;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.allinfinance.bo.risk.T40209BO;
import com.allinfinance.po.risk.TblRiskParamMcc;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

/**
 * MCC风控参数管理Action
 * @author yww
 * @version 1.0
 * 2016年4月7日  下午1:09:01
 */
@SuppressWarnings("serial")
public class T40209Action extends BaseAction {
	
	private T40209BO t40209BO = (T40209BO) ContextUtil.getBean("T40209BO");
	private TblRiskParamMcc riskParamMccInfo;

	@Override
	protected String subExecute() throws Exception {
		return null;
	}
	

	public String addMcc() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String mcc = request.getParameter("mcc");// 
			String mchtCoAmt = request.getParameter("mchtCoAmt");// 公司商户限额
			String mchtSeAmt = request.getParameter("mchtSeAmt");// 个体商户限额
			String mchtNlAmt = request.getParameter("mchtNlAmt");// 无营业执照商户限额		
			String remark = request.getParameter("remark");// 备注

			TblRiskParamMcc mccInfo = t40209BO.get(mcc);
			if (null != mccInfo) {
				writeErrorMsg("MCC信息已存在,请修改!");
			}else {
				TblRiskParamMcc riskParamMcc = new TblRiskParamMcc();
				riskParamMcc.setMcc(mcc);
				riskParamMcc.setMchtCoAmt(mchtCoAmt);
				riskParamMcc.setMchtSeAmt(mchtSeAmt);
				riskParamMcc.setMchtNlAmt(mchtNlAmt);
				riskParamMcc.setRemark(remark);
				t40209BO.save(riskParamMcc);
				writeSuccessMsg("新增成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			writeErrorMsg("新增失败！");
			log("操作员编号：" + operator.getOprId()+ "MCC风控参数管理新增" + getMethod() + "失败原因："+e.getMessage());
		}
		return null;
	}


	public String updMcc() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String mcc = request.getParameter("mcc");// 
			String mchtCoAmt = request.getParameter("mchtCoAmt");// 公司商户限额
			String mchtSeAmt = request.getParameter("mchtSeAmt");// 个体商户限额
			String mchtNlAmt = request.getParameter("mchtNlAmt");// 无营业执照商户限额		
			String remark = request.getParameter("remark");// 备注

			TblRiskParamMcc mccInfo = t40209BO.get(mcc);
			if (null == mccInfo) {
				writeErrorMsg("修改失败，请重试!");
			}else {
				mccInfo.setMcc(mcc);
				mccInfo.setMchtCoAmt(mchtCoAmt);
				mccInfo.setMchtSeAmt(mchtSeAmt);
				mccInfo.setMchtNlAmt(mchtNlAmt);
				mccInfo.setRemark(remark);
				t40209BO.update(mccInfo);
				writeSuccessMsg("修改成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			writeErrorMsg("修改失败");
			log("操作员编号：" + operator.getOprId()+ "MCC风控参数管修改" + getMethod() + "失败 原因："+e.getMessage());
		}
		return null;
	}


	public void delMcc() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String mccString = request.getParameter("str").trim();
			if (mccString.trim().length()>0) {
				t40209BO.deleteByKey(mccString);
			}
			writeSuccessMsg("删除成功！");
			//return Constants.SUCCESS_CODE;
		} catch (Exception e) {
			e.printStackTrace();
			writeErrorMsg("删除失败");
			log("操作员编号：" + operator.getOprId()+ "MCC风控参数管删除" + getMethod() + "失败 原因："+e.getMessage());
		}
		//return Constants.FAILURE_CODE;
	}


	public TblRiskParamMcc getRiskParamMcc() {
		return riskParamMccInfo;
	}

	public void setRiskParamMcc(TblRiskParamMcc riskParamMcc) {
		this.riskParamMccInfo = riskParamMcc;
	}

}
