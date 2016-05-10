
package com.allinfinance.struts.settle.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import com.allinfinance.common.msg.Msg;
import com.allinfinance.common.msg.MsgEntity;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.base.TblBrhInfoDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtSettleInfDAO;
import com.allinfinance.po.TblBrhInfo;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.mchnt.TblMchtSettleInf;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;

/**
 * 商户批量虚拟账户开户
 * @author Jee Khan
 *
 */
@SuppressWarnings("serial")
public class T80001Action extends BaseAction {
	private static Logger log = Logger.getLogger(T80001Action.class);
	
	private String mchtIds;

	private ITblMchtBaseInfDAO tblMchtBaseInfDAO = (ITblMchtBaseInfDAO) ContextUtil.getBean("tblMchtBaseInfDAO");

	public ITblMchtSettleInfDAO tblMchtSettleInfDAO = (ITblMchtSettleInfDAO) ContextUtil.getBean("tblMchtSettleInfDAO");
	
	private TblBrhInfoDAO tblBrhInfoDAO =  (TblBrhInfoDAO) ContextUtil.getBean("BrhInfoDAO");
	
	public ICommQueryDAO commQueryDAO = CommonFunction.getCommQueryDAO();
	@Override
	protected String subExecute() throws Exception {		
		if("open".equals(method)) {
			rspCode = open();
		} 
		return rspCode;
	}


	/**
	 * 审核通过的商户，如果没有开户，则开户
	 * @return
	 * @throws Exception 
	 */
	private String open() throws Exception {	
		
		if(this.mchtIds == null || "".equals(mchtIds.trim())){
			return "请选择需要开户的商户！！";
		}	
		
		List<String> sucMchtId = new ArrayList<String>();
		Map<String,String> failMchtId = new HashMap<String,String>();
		String mchtId = null;
		TblMchtBaseInf inf = null;
		TblMchtSettleInf settle = null;
		
		String[] arrMcht = this.getMchtIds().split(",");
		for(int i=0;i<arrMcht.length;i++){
			mchtId = arrMcht[i];
			 inf = tblMchtBaseInfDAO.get(mchtId);
			 settle = tblMchtSettleInfDAO.get(mchtId);
			 if (null == inf || null == settle) {
				 failMchtId.put(mchtId, "没有找到商户的信息");
				 log.info(mchtId + ":没有找到商户的信息!");
			 } else {
				 String ret = sendMessage(inf,settle);
				 if("00".equals(ret)){
					 updateOpenFlag(inf);
					 sucMchtId.add(mchtId);
				 }else{
					 failMchtId.put(mchtId, ret);
				 }
			 }	 
		}
		
		if(sucMchtId.size() == arrMcht.length){
			 this.writeSuccessMsg("所选商户：" + sucMchtId.toString() + ",开户成功!");
			 log.info("所选商户：" + sucMchtId.toString() + ",开户成功!");
		 } else if(failMchtId.size() == arrMcht.length){
			 this.writeSuccessMsg("所选商户：" + failMchtId.keySet().toString() + ",开户失败!");
			 log.info(failMchtId);
		 } else{
			 this.writeSuccessMsg("所选商户：" + sucMchtId.toString() + ",开户成功;\n所选商户：" + failMchtId.keySet().toString() + ",开户失败!");
			 log.info(failMchtId);
			 log.info("所选商户：" + sucMchtId.toString() + ",开户成功!");
		 }		
		return null;	
	}
	
	public boolean updateOpenFlag(TblMchtBaseInf inf){
		inf.setOpenVirtualAcctFlag("1");		
		String updateTmp = "update tbl_mcht_base_inf_tmp set mcht_bak3 = '1' where mcht_no = '" + inf.getMchtNo() + "'";
		String updateTmpTmp = "update tbl_mcht_base_inf_tmp_tmp set mcht_bak3 = '1'  where mcht_no = '" + inf.getMchtNo() + "'";
		commQueryDAO.excute(updateTmp);
		commQueryDAO.excute(updateTmpTmp);
		this.tblMchtBaseInfDAO.update(inf);
		return true;
	}
	/**
	 * 向虚拟账户发送开户请求，并同步接收虚拟账户开户结果，
	 *  1）、交易码：A0100 子交易码：0
	 *	2）、虚拟账户回复成功：更新开户标志位“1”
	 * @param inf
	 * @param settle
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public String sendMessage(TblMchtBaseInf inf,TblMchtSettleInf settle) throws Exception{
		String retMsg = "00";	//返回结果信息，00-表示成功
		String transCode = "A0100";
		String settleType = "10" ; //默认T+1
		String tnN = "1";
		String weekDay = "";
		String monthDay = "";
		Msg reqBody = MsgEntity.genMchtRequestBodyMsg();
		Msg reqHead = MsgEntity.genCommonRequestHeadMsg(transCode,"0");
		
		reqHead.getField(3).setValue("0");					//子交易码
		reqHead.getField(8).setValue("0000000000");			//接入方交易码
		reqHead.getField(9).setValue(transCode.substring(0, 4) + "");	//交易类型+接入方检索参考号
		reqHead.getField(17).setValue(inf.getMchtNo());					//外部账号

		TblBrhInfo tblBrhInfo = tblBrhInfoDAO.get(inf.getBankNo());
		if(tblBrhInfo == null){
			retMsg = "没有商户相关合作伙伴信息！";
			return retMsg;
		}
		// 取得结算类型---开始
		if (!"".equals(inf.getMchtFunction())) {
			String mixStr = inf.getMchtFunction();
			String type = mixStr.substring(0,1);
			if ("0".equals(type)) {
				// T+N
				settleType = "10";
				tnN = String.valueOf(Integer.parseInt(mixStr.substring(2, 5)));
				if("0".equals(tnN)){ //T+0
					settleType = "03";
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
		String sql = "select b.subbranch_id,b.bank_name,b.subbranch_name,b.province,b.city from tbl_subbranch_info b where b.subbranch_id = '"+ settle.getOpenStlno() +"'";
		List retList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		Object[] objBank = null;
		if(null == retList || retList != null && retList.size() < 1){
			retMsg = "获取清算账户信息失败！";
			return retMsg;
		} else{
			objBank = (Object[]) retList.get(0);
		}
		reqBody.getField(1).setValue(inf.getMchtNo());
		reqBody.getField(2).setValue(inf.getEtpsAttr());
		reqBody.getField(3).setValue(inf.getMchtNm());
		reqBody.getField(4).setValue(tblBrhInfo.getCreateNewNo());		//所属合作伙伴号
		reqBody.getField(5).setValue(inf.getRislLvl());		//商户风险级别
		reqBody.getField(6).setValue("0");					//商户状态
		reqBody.getField(7).setValue(inf.getAddr());		//地址
		reqBody.getField(8).setValue(inf.getPostCode());	//邮编
		reqBody.getField(9).setValue(inf.getContact());		//联系人姓名
		reqBody.getField(10).setValue(inf.getCommMobil());	//联系人移动电话
		reqBody.getField(11).setValue(inf.getCommTel());	//联系人固定电话
		reqBody.getField(12).setValue(inf.getCommEmail());	//联系人电子信箱
		reqBody.getField(13).setValue("");					//联系人传真
		reqBody.getField(14).setValue("1");					//申请类别	
		reqBody.getField(15).setValue((String)objBank[1]);				//结算银行名称
		reqBody.getField(16).setValue((String)objBank[0]);				//结算账号开户行行号	
		reqBody.getField(17).setValue((String)objBank[2]);				//结算账号开户行名称
		reqBody.getField(18).setValue(settle.getSettleAcct().substring(0, 1));		//结算账号类型
		reqBody.getField(19).setValue(settle.getSettleAcct().substring(1));			//结算账号
		reqBody.getField(20).setValue(settle.getSettleAcctNm());					//结算账号户名
		reqBody.getField(21).setValue(settleType);			//结算方式
		reqBody.getField(22).setValue(tnN);					//只填写N的值。如T+1填写1结算方式为10时必填
		reqBody.getField(23).setValue(weekDay);				//周一填写1。。。周日填写7结算方式为11时必填
		reqBody.getField(24).setValue(monthDay);				//介于01-28之间。不可填写29.30.31结算方式为12时必填
		reqBody.getField(25).setValue((String)objBank[3]);				//户省份（和结算账号开户行对应）
		reqBody.getField(26).setValue((String)objBank[4]);				//开户城市（和结算账号开户行对应）
		reqBody.getField(27).setValue("0000");		// 垫资日息 填写整数，万分之10，填写0010。(N4)
		reqBody.getField(28).setValue("00000000");	// 代付手续费 分为单位，无小数点 (N8)
		reqBody.getField(29).setValue("   "); 		// 暂时不用，填写全空格即可(AN3)
		
		String secretKey = "1111111111111111";
		
		String reqStr = MsgEntity.genCompleteRequestMsg(reqHead, reqBody, secretKey);
		
		Msg respHead = MsgEntity.genCommonResponseHeadMsg();
		Msg respBody = MsgEntity.genCommonResponseBodyMsg();
		
		log.info(reqStr);
		byte[] bufMsg = MsgEntity.sendMessage(reqStr + " ");
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
	
	public String getMchtIds() {
		return mchtIds;
	}
	
	public void setMchtIds(String mchtIds) {
		this.mchtIds = mchtIds;
	}
}
