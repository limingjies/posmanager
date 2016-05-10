
package com.allinfinance.struts.settle.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import com.allinfinance.common.msg.Msg;
import com.allinfinance.common.msg.MsgEntity;
import com.allinfinance.dao.iface.base.TblBrhInfoDAO;
import com.allinfinance.dao.iface.base.TblBrhSettleInfDAO;
import com.allinfinance.po.TblBrhInfo;
import com.allinfinance.po.base.TblBrhSettleInf;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

/**
 * 合作伙伴批量虚拟账户开户
 * @author Jee Khan
 *
 */
@SuppressWarnings("serial")
public class T80002Action extends BaseAction {
	private static Logger log = Logger.getLogger(T80002Action.class);
	
	private String brhIds;

	private TblBrhInfoDAO tblBrhInfoDAO =  (TblBrhInfoDAO) ContextUtil.getBean("BrhInfoDAO");
	private TblBrhSettleInfDAO tblBrhSettleInfDAO =  (TblBrhSettleInfDAO) ContextUtil.getBean("TblBrhSettleInfDAO");
	
	@Override
	protected String subExecute() throws Exception {		
		if("open".equals(method)) {
			rspCode = open();
		} 
		return rspCode;
	}

	/**
	 * 审核通过的合作伙伴，如果没有开户，则开户
	 * @return
	 * @throws Exception 
	 */
	private String open() throws Exception {	
		
		if(this.brhIds == null || "".equals(brhIds.trim())){
			return "请选择需要开户的合作伙伴！！";
		}	
		
		List<String> sucBrhId = new ArrayList<String>();
		Map<String,String> failBrhId = new HashMap<String,String>();
		String brhId = null;
		TblBrhInfo inf = null;
		TblBrhSettleInf settle = null;
		
		String[] arrBrh = this.getBrhIds().split(",");
		for(int i=0;i<arrBrh.length;i++){
			brhId = arrBrh[i];
			 inf = tblBrhInfoDAO.get(brhId);
			 settle = tblBrhSettleInfDAO.get(brhId);
			 if (null == inf || null == settle) {
				 failBrhId.put(brhId, "没有找到合作伙伴的信息");
				 log.info(brhId + ":没有找到合作伙伴的信息!");
			 } else {
				 String ret = sendMessage(inf,settle);
				 if("00".equals(ret)){
					 updateOpenFlag(inf);
					 sucBrhId.add(brhId);
				 }else{
					 failBrhId.put(brhId, ret);
				 }
			 }	 
		}
		
		if(sucBrhId.size() == arrBrh.length){
			 this.writeSuccessMsg("所选合作伙伴：" + sucBrhId.toString() + ",开户成功!");
			 log.info("所选合作伙伴：" + sucBrhId.toString() + ",开户成功!");
		 } else if(failBrhId.size() == arrBrh.length){
			 this.writeSuccessMsg("所选合作伙伴：" + failBrhId.keySet().toString() + ",开户失败!");			 
			 log.info(failBrhId);
		 } else{
			 this.writeSuccessMsg("所选合作伙伴：" + sucBrhId.toString() + ",开户成功;\n所选合作伙伴：" + failBrhId.keySet().toString() + ",开户失败!");
			 log.info(failBrhId);
			 log.info("所选合作伙伴：" + sucBrhId.toString() + ",开户成功!");
		 }		
		return null;	
	}
	
	public boolean updateOpenFlag(TblBrhInfo inf){
		inf.setResv3("1");	
		this.tblBrhInfoDAO.update(inf);
		return true;
	}
	/**
	 * 向虚拟账户发送开户请求，并同步接收虚拟账户开户结果，
	 *  1）、交易码：A0100 子交易码：1
	 *	2）、虚拟账户回复成功：更新开户标志位“1”
	 * @param inf
	 * @param settle
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public String sendMessage(TblBrhInfo brhInfo,TblBrhSettleInf settleInfo) throws Exception{
		String retMsg = "00";	//返回结果信息，00-表示成功
		String transCode = "A0100";
		String settleType = "10" ; //T+1
		String tnN = "1";
		String weekDay = "";
		String monthDay = "";
		// 取得结算类型---开始
		if (!"".equals(brhInfo.getResv4())) {
			String mixStr = brhInfo.getResv4();
			String type = mixStr.substring(0,1);
			if ("0".equals(type)) {
				// T+N
				settleType = "10";
				tnN = String.valueOf(Integer.parseInt(mixStr.substring(2, 5)));
				if("0".equals(tnN)){ //T+0
					settleType = "01";
					tnN = "";
				}
			} else if ("1".equals(type)) {
				String periodType = mixStr.substring(1, 2);
				// 周结
				if ("0".equals(periodType)) {
					settleType = "11";
					weekDay = "1";
				// 月结
				} else if ("1".equals(periodType)) {
					settleType = "12";
					monthDay = "01";
				// 季结
				} else if ("2".equals(periodType)) {
					settleType = "13";
				}
			}
		}
		// 取得结算类型---结束
		Msg reqBody = MsgEntity.genPartnerRequestBodyMsg();
		Msg reqHead = MsgEntity.genCommonRequestHeadMsg(transCode,"1");
//		reqHead.getField(2).setValue(transCode);			//交易类型码
		reqHead.getField(3).setValue("1");					//子交易码
		reqHead.getField(8).setValue("0000000000");			//接入方交易码
		reqHead.getField(9).setValue(transCode.substring(0, 4) + "");	//交易类型+接入方检索参考号
		reqHead.getField(17).setValue(brhInfo.getCreateNewNo());		//外部账号

		
		String sql = "select b.subbranch_id,b.bank_name,b.subbranch_name,b.province,b.city from tbl_subbranch_info b where b.subbranch_id = '"+ settleInfo.getSettleBankNo() +"'";
		List retList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		Object[] objBank = null;
		if(null == retList || retList != null && retList.size() < 1){
			retMsg = "获取清算账户信息失败。";
			return retMsg;
		} else{
			objBank = (Object[]) retList.get(0);
		}
		reqBody.getField(1).setValue(brhInfo.getCreateNewNo());		//合作伙伴号	
		reqBody.getField(2).setValue(brhInfo.getBrhName());		//合作伙伴名称
		reqBody.getField(3).setValue("3");						//合作伙伴风险级别:低风险
		reqBody.getField(4).setValue("0");						//合作伙伴状态
		reqBody.getField(5).setValue(brhInfo.getBrhAddr());		//地址
		reqBody.getField(6).setValue(brhInfo.getPostCd());		//邮编
		reqBody.getField(7).setValue(brhInfo.getBrhContName());	//联系人姓名
		reqBody.getField(8).setValue("");						//联系人移动电话
		reqBody.getField(9).setValue(brhInfo.getBrhTelNo());	//联系人固定电话
		reqBody.getField(10).setValue("");						//联系人电子信箱
		reqBody.getField(11).setValue("");						//联系人传真
		reqBody.getField(12).setValue("1");						//申请类别
		reqBody.getField(13).setValue((String)objBank[1]);				//结算银行名称
		reqBody.getField(14).setValue((String)objBank[0]);				//结算账号开户行行号	
		reqBody.getField(15).setValue((String)objBank[2]);				//结算账号开户行名称
		reqBody.getField(16).setValue(settleInfo.getSettleFlag());		//结算账号类型
		reqBody.getField(17).setValue(settleInfo.getSettleAcct());		//结算账号
		reqBody.getField(18).setValue(settleInfo.getSettleAcctNm());	//结算账号户名
		reqBody.getField(19).setValue(settleType);						//结算方式
		reqBody.getField(20).setValue(tnN);								//只填写N的值。如T+1填写1结算方式为10时必填
		reqBody.getField(21).setValue(weekDay);							//周一填写1。。。周日填写7结算方式为11时必填
		reqBody.getField(22).setValue(monthDay);						//介于01-28之间。不可填写29.30.31结算方式为12时必填
		reqBody.getField(23).setValue((String)objBank[3]);				//户省份（和结算账号开户行对应）
		reqBody.getField(24).setValue((String)objBank[4]);				//开户城市（和结算账号开户行对应）
		String secretKey = "1111111111111111";
		
		String reqStr = MsgEntity.genCompleteRequestMsg(reqHead, reqBody, secretKey);
		
		Msg respHead = MsgEntity.genCommonResponseHeadMsg();
		Msg respBody = MsgEntity.genCommonResponseBodyMsg();
		
		log.info(reqStr);
		byte[] bufMsg = MsgEntity.sendMessage(reqStr);
		String strRet = new String(bufMsg,"gb2312");
		log.info(strRet);
		MsgEntity.parseResponseMsg(bufMsg, respHead, respBody);
		
		String respCode = respHead.getField(28).getRealValue();
		if("0000".equals(respCode)) { //交易成功
			retMsg = "00";
		} else{
			retMsg = respBody.getField(1).getRealValue();
		}			
		return retMsg;
	}


	public String getBrhIds() {
		return brhIds;
	}


	public void setBrhIds(String brhIds) {
		this.brhIds = brhIds;
	}
	
}
