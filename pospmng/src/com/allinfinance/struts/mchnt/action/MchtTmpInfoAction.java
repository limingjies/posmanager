package com.allinfinance.struts.mchnt.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.allinfinance.bo.term.T3010BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.StringUtil;
import com.allinfinance.po.TblTermInf;
import com.allinfinance.po.TblTermInfTmp;
import com.allinfinance.po.risk.TblRiskParamMng;
import com.allinfinance.struts.pos.TblTermInfConstants;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class MchtTmpInfoAction  extends BaseAction {

	private T3010BO t3010BO = (T3010BO) ContextUtil.getBean("t3010BO");
	
		private Operator operator = (Operator) ServletActionContext.getRequest()
			.getSession().getAttribute(Constants.OPERATOR_INFO);
		//private T3010BO t3010BO;
		private String termIdUpd;
		private String mchnNoUpd;
		private String recCrtTs;
		private String updFormal;
		private String param1Upd;
		private String param2Upd;
		private String param6Upd;
		private String param8Upd;
		private String param1Upd1;
		private String param1Upd2;
		private String param1Upd3;
		private String param1Upd4;
		private String propTpUpd; // 产权属性
		private String connectModeUpd;// 连接类型
		private String misc1;	// 签购单模板
		private String termTpUpd; // 终端类型
		private String termTpUpdForInit; // 终端类型(tab未点过的话,termTpUpd是null)
		private String reserveFlag1Upd; // 绑定电话
		private String misc3;	// 预留字段3 第一位:是否强制下载主密钥(1-未下载主密钥，需要下载主密钥;2-已下载主密钥，不能再下载主密钥。) 第二位:是否强制联机报道(0-不需要更新;1-建议更新（查询流水，当天是否拒绝过，如果没拒绝过，本次拒绝，如果已经拒绝过，允许签到）;2-必须更新。)
		private TblRiskParamMng tblRiskParamMng;
		private String mchntNo;

		@Override
		protected String subExecute() throws Exception {
			return null;
		}
		
		public String termUpdate() throws IOException{
			try{
				TblTermInfTmp tblTermInfTmp = t3010BO.get(CommonFunction.fillString(termIdUpd, ' ', 12, true), recCrtTs);
				TblTermInf tblTermInf = t3010BO.getTermInfo(termIdUpd);

				HttpServletRequest request = ServletActionContext.getRequest();
				String misc3_1 = request.getParameter("misc3_1");// 预留字段3 第一位:是否强制下载主密钥(1-未下载主密钥，需要下载主密钥;2-已下载主密钥，不能再下载主密钥。)
				String misc3_2 = request.getParameter("misc3_2");// 预留字段3 第二位:是否强制联机报道(0-不需要更新;1-建议更新（查询流水，当天是否拒绝过，如果没拒绝过，本次拒绝，如果已经拒绝过，允许签到）;2-必须更新。)
				String miscThree ="";
				if (null != misc3 && misc3.length()>2) {
					miscThree = misc3_1+misc3_2+misc3.substring(2, misc3.length());
				}else {
					miscThree = misc3_1+misc3_2;
				}
				
				if(tblTermInfTmp == null)
					return TblTermInfConstants.T30101_03;
				// 更新临时表
//				tblTermInfTmp.setPropTp(propTpUpd);
//				tblTermInfTmp.setConnectMode(connectModeUpd);
				tblTermInfTmp.setMisc1(misc1);
				tblTermInfTmp.setMisc3(miscThree);
				//终端类型(tab未点过的话,termTpUpd是null)
//				if (StringUtil.isEmpty(termTpUpd)){
//					tblTermInfTmp.setTermTp(termTpUpdForInit);
//				}else{
//					tblTermInfTmp.setTermTp(termTpUpd);
//				}
//				tblTermInfTmp.setReserveFlag1(reserveFlag1Upd);
				
				tblTermInfTmp.setRecUpdOpr(operator.getOprId());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				tblTermInfTmp.setRecUpdTs(sdf.format(new Date()));
				t3010BO.update(tblTermInfTmp, tblTermInfTmp.getTermSta());
				
				// 更新正式表
				if (tblTermInf != null) {
//					tblTermInf.setPropTp(propTpUpd);
//					tblTermInf.setConnectMode(connectModeUpd);
					tblTermInf.setMisc1(misc1);
					tblTermInf.setMisc3(miscThree);
					//终端类型(tab未点过的话,termTpUpd是null)
//					if (StringUtil.isEmpty(termTpUpd)){
//						tblTermInf.setTermTp(termTpUpdForInit);
//					}else{
//						tblTermInf.setTermTp(termTpUpd);
//					}
//					tblTermInf.setReserveFlag1(reserveFlag1Upd);
					tblTermInf.setRecUpdOpr(operator.getOprId());
					tblTermInf.setRecUpdTs(sdf.format(new Date()));
					t3010BO.update(tblTermInf);
				}
				writeSuccessMsg("终端信息修改成功！");
			} catch (Exception e) {
				writeErrorMsg("终端信息修改失败！");
			}
			return null;
		}
		
		
		/**
		 * 终端模板批量修改
		 */
		public String termModelUpd() throws IOException {
			try {
				HttpServletRequest request = ServletActionContext.getRequest();
				String str = request.getParameter("str");
				String modelId = request.getParameter("modelId");
				String[] data = str.split(";");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				for (int i = 0; i < data.length; i++) {
					String string = data[i];
					String[] b = string.split(",");
					String termId = b[0];
					String recCrtTs = b[1];
					//获取终端临时对象，赋值
					TblTermInfTmp tblTermInfTmp = t3010BO.get(CommonFunction.fillString(termId, ' ', 12, true), recCrtTs);
					if (null != tblTermInfTmp) {
						tblTermInfTmp.setMisc1(modelId);
						tblTermInfTmp.setRecUpdOpr(operator.getOprId());
						tblTermInfTmp.setRecUpdTs(sdf.format(new Date()));
						t3010BO.update(tblTermInfTmp, tblTermInfTmp.getTermSta());
					}else {
						writeErrorMsg("不存在该终端号!");
						return null;
					}

					TblTermInf tblTermInf = t3010BO.getTermInfo(termId);
					if (null != tblTermInf) {
						tblTermInf.setMisc1(modelId);
						tblTermInf.setRecUpdOpr(operator.getOprId());
						tblTermInf.setRecUpdTs(sdf.format(new Date()));
						t3010BO.update(tblTermInf);
					}
					
				}
				writeSuccessMsg("更新成功！");
			} catch (Exception e) {
				e.printStackTrace();
				log("操作员编号：" + operator.getOprId()+ "终端模板修改" + getMethod() + "失败。 原因："+e.getMessage());
				writeErrorMsg("更新失败！");
			}
			return null;
		}

		
		public String updateDealInfo() throws IOException{
			try{
				if("".equals(mchntNo)){
					tblRiskParamMng = null;
				}
				//t3010BO = (T3010BO)ContextUtil.getBean("t3010BO");
				TblTermInfTmp tblTermInfTmp = t3010BO.get(CommonFunction.fillString(termIdUpd, ' ', 12, true), recCrtTs);
				if(tblTermInfTmp == null){
					writeErrorMsg("不存在该终端号!");
//					return TblTermInfConstants.T30101_03;
					return null;
				}
				//判断是否勾选交易
				int value = checkTxn(param1Upd)+checkTxn(param2Upd)+checkTxn(param6Upd)+checkTxn(param8Upd);		
				if(value == 0){
					writeErrorMsg("请选择交易权限!");
//					return TblTermInfConstants.T30101_02;
					return null;
				}
				int param1Value=checkTxn(param1Upd1)+checkTxn(param1Upd2)+checkTxn(param1Upd3)+checkTxn(param1Upd4);
				if(param1Value==0){
//					return "请选择卡类型权限！";
					writeErrorMsg("请选择卡类型权限!");
//					return TblTermInfConstants.T30101_02;
					return null;
				}
				tblTermInfTmp.setTermPara1(getParseTermPara1());
				tblTermInfTmp.setRecUpdOpr(operator.getOprId());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				tblTermInfTmp.setRecUpdTs(sdf.format(new Date()));
		//		tblTermInf.setTermPara(getTermPara());
				if (updFormal != null && "1".equals(updFormal)) {	
					// 状态不改变
					t3010BO.update(tblTermInfTmp, tblTermInfTmp.getTermSta());
				} else {
					if(tblTermInfTmp.getTermSta().equals(TblTermInfConstants.TERM_STA_INIT)||tblTermInfTmp.getTermSta().equals(TblTermInfConstants.TERM_STA_COPY))
						t3010BO.update(tblTermInfTmp, TblTermInfConstants.TERM_STA_INIT,tblRiskParamMng);
					else
						t3010BO.update(tblTermInfTmp, TblTermInfConstants.TERM_STA_MOD_UNCHK,tblRiskParamMng);
				}
				
				// 更新正式表,并不改变状态
				if (updFormal != null && "1".equals(updFormal)) {
					TblTermInf tblTermInf = t3010BO.getTermInfo(termIdUpd);
					
					if (tblTermInf != null) {
						tblTermInf.setTermPara1(tblTermInfTmp.getTermPara1());
						tblTermInf.setRecUpdOpr(operator.getOprId());
						tblTermInf.setRecUpdTs(sdf.format(new Date()));
						
						t3010BO.update(tblTermInf);
					}
				}
				writeSuccessMsg("终端信息修改成功！");
			} catch (Exception e) {
				writeErrorMsg("终端信息修改失败！");
			}
			return null;
		}
		/**
		 * 终端批量修改
		 * @return
		 * @throws IOException
		 */
		public String termBatchhUpdate()throws IOException{
			if(StringUtils.isBlank(termIdUpd)||StringUtils.isBlank(mchnNoUpd)||StringUtils.isBlank(recCrtTs))
			{
				writeErrorMsg("更新失败！");
				return null;
			}
			String parseTermPara1 = getParseTermPara1();
			List<TblTermInfTmp> list = new ArrayList<TblTermInfTmp>();
			String[] termIds = termIdUpd.split("\\|");
			String[] recs = recCrtTs.split("\\|");
			for (int i = 0; i < termIds.length; i++) {
				String termId = termIds[i];
				String recCrtTime = recs[i];
				TblTermInfTmp tblTermInfTmp = t3010BO.get(CommonFunction.fillString(termId, ' ', 12, true), recCrtTime);
				if(tblTermInfTmp == null ){
					writeErrorMsg("查询终端信息失败！");
					return null;
				}else {
					tblTermInfTmp.setTermPara1(parseTermPara1);
					list.add(tblTermInfTmp);
				}
			}
			if(list.size() == 0){
				writeErrorMsg("未查到终端信息！");
				return null;
			}else {
				try {
					String result = t3010BO.batchUpd(list);
					if(Constants.SUCCESS_CODE.equals(result)){
						writeSuccessMsg("更新终端信息成功！");
						return null;
					}
				} catch (Exception e) {
					e.printStackTrace();
					log(e.getMessage());
					writeErrorMsg("更新终端信息失败！");
					return null;
				}
				}
			return null;
		}

		private String getParseTermPara1() {
			StringBuffer resultTermPara1 = new StringBuffer();
			resultTermPara1.append(formatTermPara1(param1Upd1)).append(formatTermPara1(param1Upd2))
			.append(formatTermPara1(param1Upd3)).append(formatTermPara1(param1Upd4));
			String cardTp=CommonFunction.fillString(resultTermPara1.toString(), ' ', 10, true);	
			StringBuffer termPara1 = new StringBuffer();
			termPara1.append(cardTp).append(formatTermPara1(param1Upd)).append(formatTermPara1(param6Upd))
			.append(formatTermPara1(param6Upd)).append(formatTermPara1(param2Upd)).append(formatTermPara1(param2Upd))
			.append(formatTermPara1(param2Upd)).append(formatTermPara1(param2Upd)).append(formatTermPara1(param8Upd)).append(formatTermPara1(param8Upd));
			return termPara1.toString();
		}
		
		
		private String formatTermPara1(String checked){
			if(StringUtil.isNotEmpty(checked)){
				return checked;
			}else{
				return "0";
			}
		}
		
		
		public String isChecked(String param)
		{
			return param==null?TblTermInfConstants.UNCHECKED:TblTermInfConstants.CHECKED;
		}
		
		public int checkTxn(String param)
		{
			return param==null?0:1;
		}
		
		
//		private String getTermPara() throws Exception {
//			TblMchntService service = (TblMchntService) ContextUtil.getBean("tblMchntService");
//			TblMchtBaseInfTmp tblMchtBaseInfTmp =service.getBaseInfTmp(mchnNoUpd);
//			String phone=SysParamUtil.getParam(SysParamConstants.TERM_PARA_PHONE);
//			String key=SysParamUtil.getParam(SysParamConstants.TERM_PARA_22_KEY);
//			StringBuffer termPara=new StringBuffer();
//			termPara.append("14").append(CommonFunction.fillString(phone, ' ', 14, true))
//			.append("15").append(CommonFunction.fillString(phone, ' ', 14, true))
//			.append("16").append(CommonFunction.fillString(phone, ' ', 14, true))
//			.append("22").append(CommonFunction.fillString(tblMchtBaseInfTmp.getMchtNm().trim(), ' ', 40, true))
//			.append("26").append(key);
//			return termPara.toString();
//		}


		public String getPropTpUpd() {
			return propTpUpd;
		}


		public void setPropTpUpd(String propTpUpd) {
			this.propTpUpd = propTpUpd;
		}


		public String getConnectModeUpd() {
			return connectModeUpd;
		}


		public void setConnectModeUpd(String connectModeUpd) {
			this.connectModeUpd = connectModeUpd;
		}


		public String getMisc1() {
			return misc1;
		}


		public void setMisc1(String misc1) {
			this.misc1 = misc1;
		}


		public String getTermTpUpd() {
			return termTpUpd;
		}


		public void setTermTpUpd(String termTpUpd) {
			this.termTpUpd = termTpUpd;
		}
		
		public String getTermTpUpdForInit() {
			return termTpUpdForInit;
		}

		public void setTermTpUpdForInit(String termTpUpdForInit) {
			this.termTpUpdForInit = termTpUpdForInit;
		}

		public String getReserveFlag1Upd() {
			return reserveFlag1Upd;
		}


		public void setReserveFlag1Upd(String bindTel1Upd) {
			this.reserveFlag1Upd = bindTel1Upd;
		}


		public String getUpdFormal() {
			return updFormal;
		}


		public void setUpdFormal(String updFormal) {
			this.updFormal = updFormal;
		}
		
		public String getTermIdUpd() {
			return termIdUpd;
		}

		public void setTermIdUpd(String termIdUpd) {
			this.termIdUpd = termIdUpd;
		}

		public String getMchnNoUpd() {
			return mchnNoUpd;
		}

		public void setMchnNoUpd(String mchnNoUpd) {
			this.mchnNoUpd = mchnNoUpd;
		}

		public String getRecCrtTs() {
			return recCrtTs;
		}

		public void setRecCrtTs(String recCrtTs) {
			this.recCrtTs = recCrtTs;
		}


		public String getParam1Upd() {
			return param1Upd;
		}


		public void setParam1Upd(String param1Upd) {
			this.param1Upd = param1Upd;
		}


		public String getParam2Upd() {
			return param2Upd;
		}

		public void setParam2Upd(String param2Upd) {
			this.param2Upd = param2Upd;
		}

		public String getParam6Upd() {
			return param6Upd;
		}

		public void setParam6Upd(String param6Upd) {
			this.param6Upd = param6Upd;
		}

		public String getParam8Upd() {
			return param8Upd;
		}

		public void setParam8Upd(String param8Upd) {
			this.param8Upd = param8Upd;
		}

		public String getParam1Upd1() {
			return param1Upd1;
		}

		public void setParam1Upd1(String param1Upd1) {
			this.param1Upd1 = param1Upd1;
		}

		public String getParam1Upd2() {
			return param1Upd2;
		}

		public void setParam1Upd2(String param1Upd2) {
			this.param1Upd2 = param1Upd2;
		}


		public String getParam1Upd3() {
			return param1Upd3;
		}

		public void setParam1Upd3(String param1Upd3) {
			this.param1Upd3 = param1Upd3;
		}

		public String getParam1Upd4() {
			return param1Upd4;
		}

		public void setParam1Upd4(String param1Upd4) {
			this.param1Upd4 = param1Upd4;
		}

		public T3010BO getT3010BO() {
			return t3010BO;
		}

		public void setT3010BO(T3010BO t3010bo) {
			t3010BO = t3010bo;
		}

		public String getMisc3() {
			return misc3;
		}

		public void setMisc3(String misc3) {
			this.misc3 = misc3;
		}

		public TblRiskParamMng getTblRiskParamMng() {
			return tblRiskParamMng;
		}

		public void setTblRiskParamMng(TblRiskParamMng tblRiskParamMng) {
			this.tblRiskParamMng = tblRiskParamMng;
		}

		public String getMchntNo() {
			return mchntNo;
		}

		public void setMchntNo(String mchntNo) {
			this.mchntNo = mchntNo;
		}

}