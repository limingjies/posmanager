package com.allinfinance.dwr.term;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.allinfinance.common.StringUtil;
import com.allinfinance.po.TblTermInfTmp;
import com.allinfinance.po.TblTermInfTmpPK;
import com.allinfinance.po.TblTermKey;
import com.allinfinance.po.TblTermKeyPK;
import com.allinfinance.po.TblTermZmkInf;
import com.allinfinance.po.TblTermZmkInfPK;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.struts.pos.TblTermInfConstants;
import com.allinfinance.system.util.BeanUtils;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.SysParamUtil;

/**
 * Title:终端维护
 * 
 * Author: 徐鹏飞
 * 
 * Copyright: Copyright (c) 2014-9-16
 * 
 * Company: Shanghai Tonglian Software Systems.
 * 
 * @version 1.0
 */
public class TermAdd {
	
	private List<List> termInfList = new ArrayList<List>();
	private List<TblTermInfTmp> tblTermInfList = new ArrayList<TblTermInfTmp>();
	private List<TblTermKey> tblTermKeyList = new ArrayList<TblTermKey>();
	private List<TblTermZmkInf> tblTermZmkInfList = new ArrayList<TblTermZmkInf>();
	
	public List<List> getTermInfList(String termId, String[] termTel, 
			TblMchtBaseInfTmp tblMchtBaseInfTmpAdd) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// 初始termId
		termId = termId.substring(0,3) + CommonFunction.fillString(String.valueOf(Integer.parseInt(termId.substring(3))-termTel.length), '0', 5, false);
		for (int i = 0 ; i < termTel.length ; i++){
			// termId进行自增
			termId = termId.substring(0,3) + CommonFunction.fillString(String.valueOf(Integer.parseInt(termId)+1), '0', 5, false);
			TblTermInfTmp tblTermInfTmp = new TblTermInfTmp();
			TblTermInfTmpPK pk = new TblTermInfTmpPK();
			pk.setTermId(termId); // TblTermInfTmp终端号
			tblTermInfTmp.setId(pk);
			tblTermInfTmp.setTermBranch(tblMchtBaseInfTmpAdd.getBankNo()); // 终端所属机构
			tblTermInfTmp.setTermAddr(""); // 终端安装地址
			tblTermInfTmp.setMchtCd(tblMchtBaseInfTmpAdd.getMchtNo()); // 商户编号
			tblTermInfTmp.setTermSta(TblTermInfConstants.TERM_STA_RUN); // 终端状态
			tblTermInfTmp.setTermSignSta(TblTermInfConstants.TERM_SIGN_DEFAULT); // 签到默认状态
			tblTermInfTmp.setTermMcc(tblMchtBaseInfTmpAdd.getMcc()); // 终端MCC = 商户MCC
			tblTermInfTmp.setKeyDownSign(TblTermInfConstants.CHECKED);  // 默认
			tblTermInfTmp.setParam1DownSign(TblTermInfConstants.UNCHECKED); // modify at 2014-10-16 by xupengfei
			tblTermInfTmp.setParamDownSign(TblTermInfConstants.UNCHECKED); // modify at 2014-10-16 by xupengfei
			tblTermInfTmp.setSupportIc(TblTermInfConstants.CHECKED); // 默认
			tblTermInfTmp.setIcDownSign(TblTermInfConstants.CHECKED); // 默认
			tblTermInfTmp.setReserveFlag1(TblTermInfConstants.UNCHECKED); // 是否绑定电话
			
			tblTermInfTmp.setConnectMode(tblMchtBaseInfTmpAdd.getConnType()); // 连接类型
			tblTermInfTmp.setContTel(tblMchtBaseInfTmpAdd.getCommTel()); // 联系电话
			tblTermInfTmp.setOprNm(tblMchtBaseInfTmpAdd.getReserved()); // 临时存放批量导入批次号，，可以作为商户批量导入的标志
			tblTermInfTmp.setEquipInvId("NN");//机具及密码键盘绑定标识，第一位机具，第二位密码键盘，Y表示绑定，N表示未绑定
			tblTermInfTmp.setEquipInvNm(""); // 默认
			tblTermInfTmp.setPropTp("0"); // 选择产权属性 (默认：0-我司产权)
			tblTermInfTmp.setPropInsNm(""); // 收单服务机构 
			tblTermInfTmp.setTermBatchNm("000001");
			tblTermInfTmp.setTermTp("0"); // 终端类型：0-普通POS；5-移动POS；6-网络POS (默认0)
			tblTermInfTmp.setMisc2(""); // if(固话POS版本号为空)-> 默认""
			
			tblTermInfTmp.setTermPara(getTermPara(tblMchtBaseInfTmpAdd)); // modify at 2014-10-16 by xupengfei
			
			tblTermInfTmp.setBindTel1(termTel[i]); //绑定电话1 不为空
			tblTermInfTmp.setBindTel2(""); //绑定电话2
			tblTermInfTmp.setBindTel3(""); //绑定电话3
			
			tblTermInfTmp.setRecUpdOpr(tblMchtBaseInfTmpAdd.getCrtOprId());
			tblTermInfTmp.setRecCrtOpr(tblMchtBaseInfTmpAdd.getCrtOprId());
			tblTermInfTmp.setTermStlmDt(CommonFunction.getCurrentDate());
			/*String termPara = parseTermPara(pk.getTermId(),tblMchtBaseInfTmpAdd);
			tblTermInfTmp.setTermPara(termPara);*/
			tblTermInfTmp.setTermPara1(getParseTermPara1()); // 第三方分成比例(%)
			tblTermInfList.add(tblTermInfTmp);
			
			// 只加入商户号和终端号字段，其他字段后台C程序更新
			TblTermKey tblTermKey = new TblTermKey();
			TblTermKeyPK keyPK = new TblTermKeyPK();
			keyPK.setTermId(termId);
			keyPK.setMchtCd(tblMchtBaseInfTmpAdd.getMchtNo()); // 商户编号
			tblTermKey.setId(keyPK);
			tblTermKey.setKeyIndex(SysParamUtil.getParam("KEY_INDEX"));
			BeanUtils.setNullValueWithLine(tblTermKey);
			tblTermKeyList.add(tblTermKey);
			
			// 终端密钥下载时更新该条记录
			TblTermZmkInf tblTermZmkInf = new TblTermZmkInf();
			TblTermZmkInfPK zmkPK = new TblTermZmkInfPK();
			zmkPK.setMchtNo(tblMchtBaseInfTmpAdd.getMchtNo()); // 商户编号
			zmkPK.setTermId(termId);
			tblTermZmkInf.setId(zmkPK);
			tblTermZmkInf.setKeyIndex(SysParamUtil.getParam("ZMK_KEY_INDEX"));
			tblTermZmkInf.setRandom(com.allinfinance.system.util.CommonFunction.getRandom(6));
			BeanUtils.setNullValueWithLine(tblTermZmkInf);
			tblTermZmkInfList.add(tblTermZmkInf);
		}
		termInfList.add(tblTermInfList);
		termInfList.add(tblTermKeyList);
		termInfList.add(tblTermZmkInfList);
		return termInfList;
	}
	
	private String formatTermPara1(String checked){
		if(StringUtil.isNotEmpty(checked)){
			return checked;
		}else{
			return "0";
		}
	}
	
	private String getParseTermPara1() {
		// TODO Auto-generated method stub
		StringBuffer resultTermPara1 = new StringBuffer();
		resultTermPara1.append(formatTermPara1("1")).append(formatTermPara1("1"))
		.append(formatTermPara1("1")).append(formatTermPara1("1"));
		String cardTp=CommonFunction.fillString(resultTermPara1.toString(), ' ', 10, true);
		
		StringBuffer termPara1 = new StringBuffer();
		termPara1.append(cardTp).append(formatTermPara1("0")).append(formatTermPara1("1"))
		.append(formatTermPara1("1")).append(formatTermPara1("0")).append(formatTermPara1("0"))
		.append(formatTermPara1("0")).append(formatTermPara1("0")).append(formatTermPara1("0")).append(formatTermPara1("0"));
		return termPara1.toString();
	}
	
	public String parseTermPara(String termId,TblMchtBaseInfTmp tblMchtBaseInfTmp)
	{
		StringBuffer result = new StringBuffer();
		result.append("14").append(CommonFunction.fillString("", ' ', 14, true)).append("|")
			.append("15").append(CommonFunction.fillString("", ' ', 14, true)).append("|")
			.append("16").append(CommonFunction.fillString("", ' ', 14, true)).append("|")
			.append("22").append(CommonFunction.fillString(tblMchtBaseInfTmp.getMchtCnAbbr().trim(), ' ', 40, true)).append("|")
			.append("26");
		
		StringBuffer txnCode = new StringBuffer();
		//4个交易权限一组组成16进制，每一个值表示该组具有的权限
		String txnCode1="0000";
		String txnCode2="0110";
		String txnCode3="0000";
		String txnCode4="0000";
		String txnCode5="0000";
		String txnCode6="0000";
		//后台读最后4个交易是第7位，控制台只有3X8 24种交易权限，实际param25New以后是无值传过来的
		String txnCode7="0000";
		String txnCode8="0000";
		txnCode.append(Integer.toHexString(Integer.valueOf(txnCode1,2)))
		.append(Integer.toHexString(Integer.valueOf(txnCode2,2)))
		.append(Integer.toHexString(Integer.valueOf(txnCode3,2)))
		.append(Integer.toHexString(Integer.valueOf(txnCode4,2)))
		.append(Integer.toHexString(Integer.valueOf(txnCode5,2)))
		.append(Integer.toHexString(Integer.valueOf(txnCode6,2)))
		.append(Integer.toHexString(Integer.valueOf(txnCode7,2)))
		.append(Integer.toHexString(Integer.valueOf(txnCode8,2)));
		result.append(CommonFunction.fillString(txnCode.toString(), '0', 16, true));
		
		result.append("|").append("27").append(CommonFunction.fillString("", ' ', 40, true)).append("|")
			.append("28").append(CommonFunction.fillString(tblMchtBaseInfTmp.getMchtNo(), ' ', 15, true)).append("|")
			.append("29").append(CommonFunction.fillString(termId, ' ', 8, true)).append("|")
			.append("31").append("000001").append("|")
			.append("32").append(CommonFunction.fillString("1", ' ', 19, true)).append("|")
			.append("33").append(CommonFunction.fillString("1", ' ', 19, true)).append("|")
			.append("34").append(CommonFunction.fillString("1", ' ', 19, true)).append("|")
			.append("35").append(CommonFunction.fillString(" ", ' ', 2, true)).append("|");
		return result.toString();
	}
	
	private String getTermPara(TblMchtBaseInfTmp tblMchtBaseInfTmp) {
		StringBuffer termPara=new StringBuffer();
		termPara.append("14").append(CommonFunction.fillString(SysParamUtil.getParam("TERM_PARA_PHONE"), ' ', 14, true))
		.append("15").append(CommonFunction.fillString(SysParamUtil.getParam("TERM_PARA_PHONE"), ' ', 14, true))
		.append("16").append(CommonFunction.fillString(SysParamUtil.getParam("TERM_PARA_PHONE"), ' ', 14, true))
		.append("22").append(CommonFunction.fillString(tblMchtBaseInfTmp.getMchtNm().trim(), ' ', 40, true))
		.append("26").append(SysParamUtil.getParam("TERM_PARA_22_KEY"));
		return termPara.toString();
		
	}
	
}