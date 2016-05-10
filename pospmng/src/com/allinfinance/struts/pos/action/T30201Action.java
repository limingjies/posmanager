package com.allinfinance.struts.pos.action;


import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.bo.impl.mchnt.TblMchntService;
import com.allinfinance.bo.term.T3010BO;
import com.allinfinance.common.Constants;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.po.TblTermInf;
import com.allinfinance.po.TblTermInfTmp;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.risk.TblRiskParamMng;
import com.allinfinance.struts.pos.TblTermInfConstants;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.SysParamUtil;
import com.allinfinance.system.util.TMSService;

/**
 * Title:终端信息审核
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-18
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @version 1.0
 */
public class T30201Action extends BaseAction {

	private static final long serialVersionUID = 991596026562130540L;
	private static Logger log = Logger.getLogger(T30201Action.class);
	private T3010BO t3010BO;
	private String termId;
	private String recCrtTs;
	private String termSta;
	private String refuseInfo;
	private String termParam1;
	private TblRiskParamMng tblRiskParamMng;
	private static final HashMap<String,String> map = new HashMap<String,String>();
	static{
		map.put("00", TblTermInfConstants.TERM_STA_RUN);
		map.put("01", TblTermInfConstants.TERM_STA_ADD_REFUSE);
		map.put("20", TblTermInfConstants.TERM_STA_RUN);
		map.put("21", TblTermInfConstants.TERM_STA_MOD_REFUSE);
		map.put("30", TblTermInfConstants.TERM_STA_STOP);
		map.put("31", TblTermInfConstants.TERM_STA_RUN);
		map.put("50", TblTermInfConstants.TERM_STA_RUN);
		map.put("51", TblTermInfConstants.TERM_STA_STOP);
		map.put("60", TblTermInfConstants.TERM_STA_CANCEL);
		map.put("61", TblTermInfConstants.TERM_STA_RUN);
		map.put("D1", TblTermInfConstants.TERM_STA_COPY_REFUSE);
		
	}
	/**
	 * @param t3010bo the t3010BO to set
	 */
	public void setT3010BO(T3010BO t3010bo) {
		t3010BO = t3010bo;
	}

	/**
	 * @return the termId
	 */
	public String getTermId() {
		return termId;
	}

	/**
	 * @param termId the termId to set
	 */
	public void setTermId(String termId) {
		this.termId = termId;
	}


	/**
	 * @return the recCrtTs
	 */
	public String getRecCrtTs() {
		return recCrtTs;
	}

	/**
	 * @param recCrtTs the recCrtTs to set
	 */
	public void setRecCrtTs(String recCrtTs) {
		this.recCrtTs = recCrtTs;
	}

	/**
	 * @return the termSta
	 */
	public String getTermSta() {
		return termSta;
	}

	/**
	 * @param termSta the termSta to set
	 */
	public void setTermSta(String termSta) {
		this.termSta = termSta;
	}

	
	/**
	 * 
	 * @return the refuseInfo
	 * 2011-8-23上午10:23:26
	 */
	
	public String getRefuseInfo() {
		return refuseInfo;
	}
	/**
	 * 
	 * @param refuseInfo to set
	 * 2011-8-23上午10:24:12
	 */
	public void setRefuseInfo(String refuseInfo) {
		this.refuseInfo = refuseInfo;
	}
	
	@Override
	protected String subExecute() throws Exception {
		String subTxnId = getSubTxnId();
		TblTermInfTmp tblTermInfTmp = t3010BO.get(CommonFunction.fillString(termId, ' ', 12, true), recCrtTs);
		TblTermInf tblTermInf = t3010BO.getTermInfo(CommonFunction.fillString(termId, ' ', 12, true));
		/*if("2".equals(subTxnId)) {
			//synTermInfo("1", tblTermInfTmp);
			return Constants.SUCCESS_CODE;
		}*/
		//这个状态传给同步的方法
		String termStaForSyn = "0";
		if(tblTermInfTmp.getTermSta().equals(TblTermInfConstants.TERM_STA_INIT)) {
			termStaForSyn = "1";
		} else if(tblTermInfTmp.getTermSta().equals(TblTermInfConstants.TERM_STA_MOD_UNCHK)) {
			termStaForSyn = "2";
		}
		
		if(tblTermInfTmp.getTermSta().equals(TblTermInfConstants.TERM_STA_INIT)) {
			String checkMchtSql = "select count(*) from tbl_mcht_base_inf where mcht_no='" + tblTermInfTmp.getMchtCd() + "'";
			List data= CommonFunction.getCommQueryDAO().findBySQLQuery(checkMchtSql);
			if(data.get(0).toString().equals("0")) {
				return "终端所属商户未通过审核或所属商户不存在";
			}
			//	终端拒绝时，修改终端要和账户绑定，通过审核的条件
			/*if(!data.get(0).toString().equals("1")) {
				return  TblTermInfConstants.T30101_03;
			}*/
		}
		
		if(tblTermInfTmp == null)
			return TblTermInfConstants.T30201_01;
		
		if(null == tblTermInfTmp.getRecUpdOpr()) {
			
		} else if(tblTermInfTmp.getRecUpdOpr().trim().equals(operator.getOprId())) {
			return TblTermInfConstants.T30201_02;
		}
		
		String state = map.get(termSta+subTxnId);
		tblTermInfTmp.setRecUpdOpr(operator.getOprId());
		if(subTxnId.equals("0")){
			tblTermInfTmp.setTermPara1(termParam1);
		}
		//由8字节改为14字节数据的格式有日期格式YYYYMMDD改为 YYYYMMDDHHMMSS
		//tblTermInfTmp.setRecUpdTs(CommonFunction.getCurrentDate());
		tblTermInfTmp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		//20160506 杨文武 修改
//		tblTermInf.setRecUpdTs(CommonFunction.getCurrentDateTime());//更新修改时间
		
		boolean flag = true;
		//若termSta等于6-做注销待审核的审核操作且审核通过时不掉用update，其他情况此处调用
		if (TblTermInfConstants.TERM_STA_CANCEL_UNCHK.equals(termSta) && !"1".equals(subTxnId)) {
		}else {
			if (tblRiskParamMng == null&& "0".equals(subTxnId)) {
				rspCode="操作失败";
				return rspCode;
			}
			flag = t3010BO.update(tblTermInfTmp,state,tblRiskParamMng);
		}
		
		if(subTxnId.equals("1")) {//拒绝
			rspCode = t3010BO.refuseLog(termId, refuseInfo,termSta);
			if(state.equals(TblTermInfConstants.TERM_STA_ADD_REFUSE) || state.equals(TblTermInfConstants.TERM_STA_MOD_REFUSE) || state.equals(TblTermInfConstants.TERM_STA_CANCEL_REFUSE)|| state.equalsIgnoreCase(TblTermInfConstants.TERM_STA_COPY_REFUSE)){
					t3010BO.refuse(tblTermInfTmp.getId(), state);
			}
		} else {//通过
			if(tblTermInf != null){
				tblTermInfTmp.setRecCrtTs(tblTermInf.getRecCrtTs());
			}
			String result= "";
			//若termSta等于6--做注销待审核状态的审核操作，则调用TMS撤机接口,若接口返回错误，flag状态也改为false
			if (TblTermInfConstants.TERM_STA_CANCEL_UNCHK.equals(termSta)) {
				TMSService df = new TMSService();
				result= df.deleteTerm(tblTermInfTmp.getProductCd(), tblTermInfTmp.getMisc3().substring(2));
				if (!Constants.SUCCESS_CODE.equals(result)) {
					flag = false;
				}else {
					//撤机接口返回成功值时将sn号清空,终端状态改为7-注销
					tblTermInfTmp.setProductCd("");
					tblTermInf.setProductCd("");
					tblTermInf.setTermSta(TblTermInfConstants.TERM_STA_CANCEL);
					//20160506 杨文武 修改
					tblTermInf.setRecUpdTs(CommonFunction.getCurrentDateTime());//更新修改时间
					t3010BO.update(tblTermInfTmp, TblTermInfConstants.TERM_STA_CANCEL);//修改终端，改终端状态为注销--7
					t3010BO.update(tblTermInf);
				}
			}
			//如果是做注销待审核状态的审核操作，且调用TMS撤机失败，则打印日志，不调用save方法
			if(TblTermInfConstants.TERM_STA_CANCEL_UNCHK.equals(termSta) && flag == false){
				log.info("错误日志: TMS撤机接口deleteTerm处理失败，返回信息="+result+"");
			}else {
				flag = flag&t3010BO.save(tblTermInfTmp, operator.getOprId(),tblTermInf==null?null:tblTermInf.getTermBatchNm(),termSta);
			}
			/*if(flag && !"0".equals(termStaForSyn)) {
				synTermInfo(termStaForSyn, tblTermInfTmp);
			}*/
		}
		
		if(flag) {
			return Constants.SUCCESS_CODE;
		}
		if ("运行时错误".equals(rspCode)) {
			rspCode="操作失败";//改提示信息"运行时错误"为"操作失败"
		}
		return rspCode;
	}

	/**
	 * 同步终端信息给前置
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public void synTermInfo(String termStaForSyn, TblTermInfTmp tblTermInfTmp) throws IllegalAccessException, InvocationTargetException {
		
		TblMchntService tblMchntService = (TblMchntService) ContextUtil.getBean("tblMchntService");
		TblMchtBaseInf tblMchtBaseInf = tblMchntService.getBaseInf(tblTermInfTmp.getMchtCd());
		
//		 "报文内容" = 6005 + 19个0+09999999 + 041+操作位(1||2) + 维护设备号(Ans 11)+内部地区码(Ans 2) 
//		                  +银联地区码(Ans 4)+内部机构号(Ans 9)+柜员号(Ans 6)+操作员机构号（Ans 8）
		
		ICommQueryDAO commQueryDAO = (ICommQueryDAO) ContextUtil.getBean("CommQueryDAO");
		List dataList = CommonFunction.getCommQueryDAO().findBySQLQuery("select resv2 from tbl_brh_info where brh_id='" + tblMchtBaseInf.getBankNo().trim() + "'");
		String ltrId = (String)dataList.get(0);
		if(ltrId == null) {
			ltrId = " ";
		}
		
		String areaSql = "select cup_city_code from cst_city_code where mcht_city_code='" + tblMchtBaseInf.getAreaNo().trim()  +"'";
		List areaList = CommonFunction.getCommQueryDAO().findBySQLQuery(areaSql);
		String area = (String)areaList.get(0);
		
		String sendMsgInfo = "6005" + CommonFunction.fillString("0", '0', 19, false) + "09999999" + "042" 
							+ termStaForSyn 
							+ CommonFunction.fillString(tblTermInfTmp.getId().getTermId().trim(), ' ', 11, true)
							+ tblTermInfTmp.getTermBranch().substring(0, 2) 
							+ area.trim() 
							+ CommonFunction.fillString(tblMchtBaseInf.getBankNo().trim(), ' ', 9, true) 
							+ CommonFunction.fillString(ltrId, ' ', 6, true)
							+ CommonFunction.fillString(tblMchtBaseInf.getBankNo().trim(), ' ', 8, true)
							+ "0";
		log.info("报文:[" + sendMsgInfo + "]");
		
		// "报文内容长度" 4位 不足左补0
		String sendMsgInfoLen = CommonFunction.fillString(String.valueOf(sendMsgInfo.length()), '0', 4, false);
		
		// "最终发送的报文" = "报文内容长度" + "报文内容"
		String sendMsg = sendMsgInfoLen + sendMsgInfo;
		log.info("报文(send):[" + sendMsg + "]");

		Socket socket = null;
		OutputStream outputStream = null;
		InputStream inputstream = null;
		Logger synLog = Logger.getLogger(T30201Action.class);
		
		try {

			String ip = SysParamUtil.getParam("IP");
			int port = Integer.parseInt(SysParamUtil.getParam("PORT"));
			int timeout = Integer.parseInt(SysParamUtil.getParam("TIMEOUT"));
			synLog.info("目的(IP):[" + ip + "]");
			synLog.info("目的(PORT):[" + port + "]");
			socket = new Socket(ip,port);
			socket.setSoTimeout(timeout);
			outputStream = socket.getOutputStream();

			outputStream.write(sendMsg.getBytes());
			outputStream.flush();
			synLog.info("报文(SEND):[" + sendMsg + "]");

//			byte[] readBytes = new byte[256];
//			inputstream = socket.getInputStream();
//			inputstream.read(readBytes);
//			String revMsq = new String(readBytes);
//			synLog.info("报文(REV):[" + revMsq + "]");
			
		} catch (Exception e) {
			synLog.error(e);
		} finally {
			try {
				if (null != inputstream) {
					inputstream.close();
				}
			} catch (Exception e) {}
			try {
				if (null != outputStream) {
					outputStream.close();
				}
			} catch (Exception e) {}
			try {
				if (null != socket) {
					socket.close();
				}
			} catch (Exception e) {}
		}
	}

	public String getTermParam1() {
		return termParam1;
	}

	public void setTermParam1(String termParam1) {
		this.termParam1 = termParam1;
	}

	public TblRiskParamMng getTblRiskParamMng() {
		return tblRiskParamMng;
	}

	public void setTblRiskParamMng(TblRiskParamMng tblRiskParamMng) {
		this.tblRiskParamMng = tblRiskParamMng;
	}
}
