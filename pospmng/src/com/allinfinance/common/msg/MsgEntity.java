package com.allinfinance.common.msg;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.SysParamUtil;

public class MsgEntity {
	public static String charset = "GBK";
	public static int keyLength = 16;		//秘钥信息长度
	private static Logger log = Logger.getLogger(MsgEntity.class);
	public static String enterInstCode = "100000000000000"; //接入机构号
	public static String applyInstCode = "100000000000000";  //请求方机构号
	public static String openActInstCode = "100000000000000";	//开户机构号
	
	
	/**
	 * 生成发送报文头对象
	 * @return 请求报文头对象
	 * @throws Exception
	 */
	public static Msg genCommonRequestHeadMsg(String tradeCode,String subTradeCode) throws Exception{
		//获取流水号规则码
		String ruleCode="";
		if ("A0100".equals(tradeCode)&&"0".equals(subTradeCode)) {
			ruleCode="01";
		}else if ("A0100".equals(tradeCode)&&"1".equals(subTradeCode)) {
			ruleCode="02";
		}else if ("F0100".equals(tradeCode)&&"0".equals(subTradeCode)) {
			ruleCode="03";
		}else if ("F0100".equals(tradeCode)&&"1".equals(subTradeCode)) {
			ruleCode="04";
		}else if ("E0010".equals(tradeCode)&&"0".equals(subTradeCode)) {
			ruleCode="05";
		}else if ("E0020".equals(tradeCode)&&"0".equals(subTradeCode)) {
			ruleCode="06";
		}else if ("E0030".equals(tradeCode)&&"0".equals(subTradeCode)) {
			ruleCode="07";
		}else if ("M0010".equals(tradeCode)&&"1".equals(subTradeCode)) {
			ruleCode="08";
		}else {
			ruleCode="09";
		}
		String sql="SELECT SEQ_VC_TRAN_NO.NEXTVAL FROM DUAL";
		@SuppressWarnings("unchecked")
		List<BigDecimal> list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		String nextVal = list.get(0).toString();
		Msg head = new Msg(28);
		head.add(new Field(1,"版本号",2,DataType.AN,"",NLLType.M,false));
		head.add(new Field(2,"交易类型码",5,DataType.AN,"",NLLType.M,false));
		head.add(new Field(3,"子交易码",1,DataType.AN,"",NLLType.M,false));
		head.add(new Field(4,"接入机构号",15,DataType.AN,"",NLLType.M,false));
		head.add(new Field(5,"接入方交易日期",8,DataType.YYYYMMDD,"",NLLType.M,false));
		head.add(new Field(6,"接入方交易流水号",10,DataType.AN,"",NLLType.M,false));
		head.add(new Field(7,"接入方交易时间",6,DataType.AN,"",NLLType.M,false));
		head.add(new Field(8,"接入方交易码",10,DataType.ANS,"",NLLType.M,false));
		head.add(new Field(9,"交易类型+接入方检索参考号",20,DataType.ANS,"",NLLType.C,false));
		head.add(new Field(10,"请求方机构号",15,DataType.AN,"",NLLType.M,false));
		head.add(new Field(11,"请求方交易日期",8,DataType.YYYYMMDD,"",NLLType.N,false));
		head.add(new Field(12,"请求方交易流水号",10,DataType.AN,"",NLLType.N,false));
		head.add(new Field(13,"开户机构号",15,DataType.AN,"",NLLType.M,false));
		head.add(new Field(14,"开户机构交易日期",8,DataType.YYYYMMDD,"",NLLType.N,false));
		head.add(new Field(15,"开户机构交易流水号",10,DataType.AN,"",NLLType.N,false));
		head.add(new Field(16,"开户机构交易时间",6,DataType.AN,null,NLLType.N,false));
		head.add(new Field(17,"外部账号",32,DataType.ANS,"",NLLType.M,false));
		head.add(new Field(18,"外部账号类型",8,DataType.ANS,"",NLLType.O,false));
		head.add(new Field(19,"内部账号验证标志",1,DataType.AN,"",NLLType.M,false));
		head.add(new Field(20,"内部账号",10,DataType.AN,"",NLLType.N,false));
		head.add(new Field(21,"内部账号类型",8,DataType.AN,"",NLLType.N,false));
		head.add(new Field(22,"密码域1",8,DataType.ANS,"",NLLType.C,false));
		head.add(new Field(23,"密码域2",8,DataType.ANS,"",NLLType.C,false));
		head.add(new Field(24,"客户号",15,DataType.AN,"",NLLType.N,false));
		head.add(new Field(25,"金额符号",1,DataType.MP,"",NLLType.C,false));
		head.add(new Field(26,"交易额",12,DataType.N,"",NLLType.C,false));
		head.add(new Field(27,"用户支付手续费",9,DataType.N,"",NLLType.C,false));
		head.add(new Field(28,"应答码",4,DataType.N,"",NLLType.N,false));
		
		head.getField(1).setValue("00");					//版本号
		head.getField(2).setValue(tradeCode); 			//交易码
		head.getField(4).setValue(enterInstCode); 			//接入机构号;固定值，钱宝机构号
		head.getField(5).setValue(CommonFunction.getCurrentDate());			//接入方交易日期
		head.getField(6).setValue(ruleCode+CommonFunction.fillString(nextVal, '0', 8, false));		//接入方交易流水号
		head.getField(7).setValue(CommonFunction.getCurrentTime());			//接入方交易时间
		head.getField(10).setValue(applyInstCode);				//请求方机构号
		head.getField(13).setValue(openActInstCode);			//开户机构号;固定值，钱宝机构号
		head.getField(19).setValue("1");						//内部账号验证标志
		return head;
	}
	
	/**
	 * 生成响应报文头对象
	 * @return 响应报文头对象
	 * @throws Exception
	 */
	public static Msg genCommonResponseHeadMsg() throws Exception{
		Msg head = new Msg(28);
		head.add(new Field(1,"版本号",2,DataType.AN,null,NLLType.M1,false));
		head.add(new Field(2,"交易类型码",5,DataType.AN,null,NLLType.M1,false));
		head.add(new Field(3,"子交易码",1,DataType.AN,null,NLLType.M1,false));
		head.add(new Field(4,"接入机构号",15,DataType.AN,null,NLLType.M1,false));
		head.add(new Field(5,"接入方交易日期",8,DataType.YYYYMMDD,null,NLLType.M1,false));
		head.add(new Field(6,"接入方交易流水号",10,DataType.AN,null,NLLType.M1,false));
		head.add(new Field(7,"接入方交易时间",6,DataType.AN,null,NLLType.M1,false));
		head.add(new Field(8,"接入方交易码",10,DataType.ANS,null,NLLType.M1,false));
		head.add(new Field(9,"交易类型+接入方检索参考号",20,DataType.ANS,null,NLLType.C,false));
		head.add(new Field(10,"请求方机构号",15,DataType.AN,null,NLLType.M1,false));
		head.add(new Field(11,"请求方交易日期",8,DataType.YYYYMMDD,null,NLLType.O,false));
		head.add(new Field(12,"请求方交易流水号",10,DataType.AN,null,NLLType.O,false));
		head.add(new Field(13,"开户机构号",15,DataType.AN,null,NLLType.M1,false));
		head.add(new Field(14,"开户机构交易日期",8,DataType.YYYYMMDD,null,NLLType.O,false));
		head.add(new Field(15,"开户机构交易流水号",10,DataType.AN,null,NLLType.M1,false));
		head.add(new Field(16,"开户机构交易时间",6,DataType.AN,null,NLLType.O,false));
		head.add(new Field(17,"外部账号",32,DataType.ANS,null,NLLType.M1,false));
		head.add(new Field(18,"外部账号类型",8,DataType.ANS,null,NLLType.C,false));
		head.add(new Field(19,"内部账号验证标志",1,DataType.AN,null,NLLType.O,false));
		head.add(new Field(20,"内部账号",10,DataType.AN,null,NLLType.O,false));
		head.add(new Field(21,"内部账号类型",8,DataType.AN,null,NLLType.O,false));
		head.add(new Field(22,"密码域1",8,DataType.ANS,null,NLLType.O,false));
		head.add(new Field(23,"密码域2",8,DataType.ANS,null,NLLType.O,false));
		head.add(new Field(24,"客户号",15,DataType.AN,null,NLLType.O,false));
		head.add(new Field(25,"金额符号",1,DataType.MP,null,NLLType.C,false));
		head.add(new Field(26,"交易额",12,DataType.AN,null,NLLType.C,false));
		head.add(new Field(27,"用户支付手续费",9,DataType.N,null,NLLType.C,false));
		head.add(new Field(28,"应答码",4,DataType.N,null,NLLType.M,false));	
		return head;
	}
	
	
	/**
	 * 生成商户开户或商户信息变更请求包体报文对象
	 * @return 商户开户或商户信息变更请求包体报文对象
	 * @throws Exception
	 */
	public static Msg genMchtRequestBodyMsg() throws Exception{
		Msg body = new Msg(29);
		body.add(new Field(1,"商户号",15,DataType.AN,"",NLLType.M,false));
		body.add(new Field(2,"商户类型",4,DataType.AN,"",NLLType.M,false));
		body.add(new Field(3,"商户名称",60,DataType.ANS,"",NLLType.M,false));
		body.add(new Field(4,"所属合作伙伴号",12,DataType.AN,"",NLLType.C,false));
		body.add(new Field(5,"商户风险级别",1,DataType.RT,"",NLLType.M,false));
		body.add(new Field(6,"商户状态",1,DataType.MS,"",NLLType.M,false));
		body.add(new Field(7,"地址",128,DataType.ANS,"",NLLType.M,false));
		body.add(new Field(8,"邮编",6,DataType.AN,"",NLLType.O,false));
		body.add(new Field(9,"联系人姓名",60,DataType.ANS,"",NLLType.M,false));
		body.add(new Field(10,"联系人移动电话",16,DataType.ANS,"",NLLType.C,false));
		body.add(new Field(11,"联系人固定电话",20,DataType.ANS,"",NLLType.C,false));
		body.add(new Field(12,"联系人电子信箱",20,DataType.ANS,"",NLLType.C,false));
		body.add(new Field(13,"联系人传真",20,DataType.ANS,"",NLLType.O,false));
		body.add(new Field(14,"申请类别",1,DataType.AN,"",NLLType.M,false));
		body.add(new Field(15,"结算银行名称",60,DataType.ANS,"",NLLType.M,false));
		body.add(new Field(16,"结算账号开户行行号",15,DataType.AN,null,NLLType.M,false));
		body.add(new Field(17,"结算账号开户行名称",80,DataType.ANS,"",NLLType.M,false));		
		body.add(new Field(18,"结算账号类型",1,DataType.A,"",NLLType.M,false));
		body.add(new Field(19,"结算账号",32,DataType.AN,null,NLLType.M,false));
		body.add(new Field(20,"结算账号户名",60,DataType.ANS,"",NLLType.M,false));
		body.add(new Field(21,"结算方式",2,DataType.AN,"",NLLType.M,false));
		body.add(new Field(22,"T+N中的N",3,DataType.N,"",NLLType.C,false));
		body.add(new Field(23,"周结日期",1,DataType.WC,"",NLLType.C,false));
		body.add(new Field(24,"月结日期",2,DataType.MC,"",NLLType.C,false));
		body.add(new Field(25,"开户省份（和结算账号开户行对应）",40,DataType.ANS,"",NLLType.M,false));
		body.add(new Field(26,"开户城市（和结算账号开户行对应）",40,DataType.ANS,"",NLLType.M,false));
		body.add(new Field(27,"垫资日息",4,DataType.N,"",NLLType.C,false));
		body.add(new Field(28,"代付手续费",8,DataType.N,"",NLLType.C,false));
		body.add(new Field(29,"优先级",3,DataType.AN,"",NLLType.C,false));
		
		return body;
	}
	
	/**
	 * 生成商户差错调整报文体
	 * @return 商户差错调整报文体对象
	 * @throws Exception
	 */
	public static Msg genMchtErrAdjustRequestBodyMsg() throws Exception{
		Msg body = new Msg(2);
		body.add(new Field(1,"商户号",15,DataType.AN,"",NLLType.M,true));
		body.add(new Field(2,"调整类别",2,DataType.AN,"",NLLType.M,true));
		return body;
	}
	
	/**
	 * 生成合作伙伴差错调整报文体
	 * @return 商户差错调整报文体对象
	 * @throws Exception
	 */
	public static Msg genBrhErrAdjustRequestBodyMsg() throws Exception{
		Msg body = new Msg(2);
		body.add(new Field(1,"合作伙伴编号",12,DataType.AN,"",NLLType.M,true));
		body.add(new Field(2,"调整类别",2,DataType.AN,"",NLLType.M,true));
		return body;
	}
	
	/**
	 * 合作伙伴开户或合作伙伴信息变更请求包体报文对象
	 * @return 合作伙伴开户或合作伙伴信息变更请求包体报文对象
	 * @throws Exception
	 */
	public static Msg genPartnerRequestBodyMsg() throws Exception{
		Msg body = new Msg(24);
		body.add(new Field(1,"合作伙伴号",12,DataType.AN,"",NLLType.M,false));
		body.add(new Field(2,"合作伙伴名称",60,DataType.ANS,"",NLLType.M,false));
		body.add(new Field(3,"合作伙伴风险级别",1,DataType.RT,"",NLLType.M,false));
		body.add(new Field(4,"合作伙伴状态",1,DataType.MS,"",NLLType.M,false));
		body.add(new Field(5,"地址",128,DataType.ANS,"",NLLType.M,false));
		body.add(new Field(6,"邮编",6,DataType.ANS,"",NLLType.O,false));
		body.add(new Field(7,"联系人姓名",60,DataType.ANS,"",NLLType.C,false));
		body.add(new Field(8,"联系人移动电话",16,DataType.ANS,"",NLLType.C,false));
		body.add(new Field(9,"联系人固定电话",20,DataType.ANS,"",NLLType.C,false));
		body.add(new Field(10,"联系人电子信箱",20,DataType.ANS,"",NLLType.O,false));
		body.add(new Field(11,"联系人传真",20,DataType.ANS,"",NLLType.O,false));
		body.add(new Field(12,"申请类别",1,DataType.AT,"",NLLType.M,false));
		body.add(new Field(13,"结算银行名称",60,DataType.ANS,"",NLLType.M,false));
		body.add(new Field(14,"结算账号开户行行号",15,DataType.AN,null,NLLType.M,false));
		body.add(new Field(15,"结算账号开户行名称",80,DataType.ANS,"",NLLType.M,false));
		body.add(new Field(16,"结算账号类型",1,DataType.A,null,NLLType.M,false));
		body.add(new Field(17,"结算账号",32,DataType.AN,null,NLLType.M,false));
		body.add(new Field(18,"结算账号户名",60,DataType.ANS,"",NLLType.M,false));
		body.add(new Field(19,"结算方式",2,DataType.ST,"",NLLType.M,false));
		body.add(new Field(20,"T+N中的N",3,DataType.N,"",NLLType.C,false));
		body.add(new Field(21,"周结日期",1,DataType.WC,"",NLLType.C,false));
		body.add(new Field(22,"月结日期",2,DataType.MC,"",NLLType.C,false));
		body.add(new Field(23,"开户省份（和结算账号开户行对应）",40,DataType.ANS,"",NLLType.M,false));
		body.add(new Field(24,"开户城市（和结算账号开户行对应）",40,DataType.ANS,"",NLLType.M,false));
		return body;
	}
	
	/**
	 * 通用请求响应包体报文对象：仅包含应答信息字段
	 * @return 通用请求响应包体报文对象
	 * @throws Exception
	 */
	public static Msg genCommonResponseBodyMsg() throws Exception{
		Msg body = new Msg(1);
		body.add(new Field(1,"应答信息",40,DataType.ANS,"",NLLType.C,false));		
		return body;
	}
	
	/**
	 * 交易入账请求包体报文对象
	 * @return 交易入账请求包体报文对象
	 * @throws Exception
	 */
	public static Msg genDealInFileRequestBodyMsg() throws Exception{
		Msg body = new Msg(4);
		body.add(new Field(1,"商户号",15,DataType.AN,"",NLLType.M,false));
		body.add(new Field(2,"商户类型",4,DataType.AN,"",NLLType.M,false));
		body.add(new Field(3,"交易币种",3,DataType.AN,"",NLLType.M,false));
		body.add(new Field(4,"结算日",3,DataType.N,"",NLLType.M,false));
		return body;
	}
	

	/**
	 * 取文件通知（posp和渠道对账后）
	 * create by yww 20151230
	 */
	public static Msg getFileNoticeMsg() throws Exception{
		Msg body = new Msg(2);//3
		body.add(new Field(1,"文件路径",60,DataType.ANS,"",NLLType.M,false));	
		body.add(new Field(2,"文件名",60,DataType.ANS,"",NLLType.M,false));		
		//body.add(new Field(3,"对账流水日期",60,DataType.ANS,"",NLLType.M,false));		
		return body;
	}
	
	/**
	 * 渠道对账调整（渠道无posp有）
	 * create by luhq 20160104
	 */
	public static Msg getBalanceAjustMsg() throws Exception{
		Msg body = new Msg(8);
		body.add(new Field(1,"商户号",15,DataType.AN,"",NLLType.M,false));	
		body.add(new Field(2,"商户类型",4,DataType.AN,"",NLLType.M,false));		
		body.add(new Field(3,"交易币种",3,DataType.AN,"",NLLType.M,false));	
		body.add(new Field(4,"结算日",3,DataType.AN,"",NLLType.M,false));		
		body.add(new Field(5,"原交易接入方机构号",15,DataType.N,"",NLLType.M,false));	
		body.add(new Field(6,"原交易接入方日期",8,DataType.YYYYMMDD,"",NLLType.M,false));		
		body.add(new Field(7,"原交易接入方交易流水号",10,DataType.AN,"",NLLType.M,false));	
		body.add(new Field(8,"原交易接入方交易类型+原交易接入方检索参考号",20,DataType.ANS,"",NLLType.M,false));		
		return body;
	}
	
	/**
	 * 根据报文头、报文体、秘钥生成完整的报文字符串
	 * @param head		报文头对象
	 * @param body		报文体对象
	 * @param secret	秘钥字节数组
	 * @return
	 * @throws Exception 
	 */
	public static String genCompleteRequestMsg(Msg head,Msg body,String secretKey) throws Exception{
		StringBuilder sb = new StringBuilder();
		int length = 0;
		if(null == head){
			throw new Exception("报文头不可为空。");
		}else{
			sb.append(head.getMessage());
			length += head.getMsgLength();
		}
		
		if(null != body){
			sb.append(body.getMessage());
			length += body.getMsgLength();
		}
		
		if(null == secretKey || secretKey.getBytes(charset).length != keyLength){
			throw new Exception("秘钥长度不正确。");
		} else {
			sb.append(secretKey);
			length += keyLength;
		}
		if((4-(""+length).length()) < 0){
			throw new Exception("报文不正确：超长。");
		}
		String lenStr = Field.getPadding(4-(""+length).length(), "0") + length;
			
		return lenStr + sb.toString();
	}
	
	/**
	 * 将相应报文字符串进行解析，
	 * 	1.报文头数据解析后放入head参数中，
	 * 	2.报文体数据解析后放入body参数中。
	 * @param strResp	待解析的相应报文
	 * @param head		报文头对象
	 * @param body		报文体对象
	 * @return			报文长度
	 * @throws Exception
	 */
	public static int parseResponseMsg(byte[] msgBuf,Msg head,Msg body) throws Exception{
		if(null == head){
			throw new Exception("报文头不可为空。");
		}
		if(null == msgBuf){
			throw new Exception("响应报文不可为空。");
		}
		
		int lenMsg = 0; 	//报文长度
		if(msgBuf.length < 4){
			throw new Exception("响应报文格式不正确。");
		} else {
			lenMsg = new Integer(new String(Arrays.copyOfRange(msgBuf, 0, 4),charset));
		}
		// 解析报文头		
		if(msgBuf.length <(head.getMsgLength() + 4) ){
			throw new Exception("响应报文格式不正确。");
		} else {
			byte[] headBuf = Arrays.copyOfRange(msgBuf, 4, head.getMsgLength() + 4);
			head = Byte2Msg(head,headBuf);
		}
		// 解析报文体
		if(null != body){
			if(msgBuf.length <(body.getMsgLength() + head.getMsgLength() + 4) ){
				throw new Exception("响应报文格式不正确。");
			} else {
				byte[] bodyBuf = Arrays.copyOfRange(msgBuf, (4 + head.getMsgLength()), (body.getMsgLength() + head.getMsgLength() + 4));
				body = Byte2Msg(body,bodyBuf);
			}
		}
		return 	lenMsg;	
	}
	
	/**
	 * 将报文对象转换为报文字符串
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public static String Msg2Str(Msg msg) throws Exception{
		return msg.getMessage();
	}
	
	/**
	 * 将报文字符串转换为指定报文对象
	 * @param msg		报文对象
	 * @param buf		需解析的报文字节数组
	 * @return
	 * @throws Exception
	 */
	public static Msg Byte2Msg(Msg msg,byte[] buf) throws Exception{
		if(null == buf || buf.length < msg.getMsgLength()){
			throw new Exception("响应报文头格式不正确.");
		}
		for(int i=1 ;i<=msg.getFieldCount();i++){
			msg.getField(i).setValue(getFileValueFromString(i, msg, buf).trim());
		}
		return msg;
	}
	
	/**
	 * 从待解析的报文字字节数组中获取报文对象的指定序号的字段值
	 * @param index		报文字段序号
	 * @param msg		报文对象
	 * @param buf		需解析的报文字节数组
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String getFileValueFromString(int index,Msg msg,byte[] buf) throws UnsupportedEncodingException{
		int begin = 0;
		for(int i=1;i<index;i++){
			begin += msg.getField(i).getLength();
		}
		return new String(Arrays.copyOfRange(buf, begin, begin+msg.getField(index).getLength()),charset);
	}
	
	/**
	 * 
	 * @param msg		请求信息
	 * @return			响应信息
	 * @throws Exception 
	 */
	public static byte[] sendMessage(String msg) throws Exception{
		String ip = SysParamUtil.getParam("IP_VC");
		//发送地址端口
		int port = Integer.parseInt(SysParamUtil.getParam("PORT_VC"));
		Socket sokect = new Socket(ip,port);
		byte[] allMsgBytes;
		//超时时间
		int timeout = Integer.parseInt(SysParamUtil.getParam("TIMEOUT_VC"));
		sokect.setSoTimeout(timeout*1000);
		try{
			InputStream is = sokect.getInputStream();
			OutputStream os = sokect.getOutputStream();
			
			os.write(msg.getBytes(charset));
			os.flush();

			log.info("发送报文：" + Arrays.toString(msg.getBytes(charset)));			
			log.info("发送时间：" + CommonFunction.getCurrentDateTime());
			byte[] buf= new byte[1024];
			byte[] msgLenByte = new byte[4]; //获取报文头的四个长度字节，确定返回报文的长度
			
			is.read(msgLenByte);	
			int msgLen = Integer.parseInt(new String(msgLenByte,charset));  //返回的报文的长度
			
			allMsgBytes = new byte[msgLen + 5]; //初始化报文数组
			int count = 0;  //累计已经读取的报文长度
			int len = 0;	//单次实际读取的长度
			
			allMsgBytes = arrayConcat(null,msgLenByte);
			while(count < msgLen && (len = is.read(buf)) != -1){
				allMsgBytes = arrayConcat(allMsgBytes,Arrays.copyOfRange(buf, 0, len));
				count += len;
			}
			log.info("接收报文：" + Arrays.toString(allMsgBytes));
			log.info("接收时间：" + CommonFunction.getCurrentDateTime());
		} finally{
			if(sokect != null){
				sokect.shutdownInput();
				sokect.shutdownOutput();
				sokect.close();
			}		
		}
		return allMsgBytes;
	}
	/**
	 * 将arr1数组和arr2数组拼接为一个新数组
	 * @param dest 合成的数组
	 */
	private static byte[] arrayConcat(byte[] arr1,byte[] arr2){
		if(arr1 == null){
			return arr2;
		}
		if(arr2 == null){
			return arr1;
		}
		int len = arr1.length + arr2.length;
		byte[] array = new byte[len];
		int i=0;int j=0;
		for(;i<arr1.length;i++){
			array[i] = arr1[i];
		}
		for(j=0;j<arr2.length;j++){
			array[i+j] = arr2[j];
		}
		return array;
	}
	/**
	 * 更新商户信息变更请求包体报文对象
	 * @return 更新商户信息变更请求包体报文对象
	 * @throws Exception
	 */
	public static Msg genMchtUpdRequestBodyMsg() throws Exception{
		Msg body = new Msg(29);
		body.add(new Field(1,"商户号",15,DataType.AN,"",NLLType.M,false));
		body.add(new Field(2,"商户类型",4,DataType.AN,"",NLLType.M,false));
		body.add(new Field(3,"商户名称",60,DataType.ANS,"",NLLType.M,false));
		body.add(new Field(4,"所属合作伙伴编号",12,DataType.AN,"",NLLType.C,false));
		body.add(new Field(5,"商户风险级别",1,DataType.RT,"",NLLType.M,false));
		body.add(new Field(6,"商户状态",1,DataType.MS,"",NLLType.M,false));
		body.add(new Field(7,"地址",128,DataType.ANS,"",NLLType.M,false));
		body.add(new Field(8,"邮编",6,DataType.AN,"",NLLType.O,false));
		body.add(new Field(9,"联系人姓名",60,DataType.ANS,"",NLLType.M,false));
		body.add(new Field(10,"联系人移动电话",16,DataType.ANS,"",NLLType.C,false));
		body.add(new Field(11,"联系人固定电话",20,DataType.ANS,"",NLLType.C,false));
		body.add(new Field(12,"联系人电子信箱",20,DataType.ANS,"",NLLType.C,false));
		body.add(new Field(13,"联系人传真",20,DataType.ANS,"",NLLType.O,false));
		body.add(new Field(14,"申请类别",1,DataType.AN,"",NLLType.M,false));
		body.add(new Field(15,"结算银行名称",60,DataType.ANS,"",NLLType.M,false));
		body.add(new Field(16,"结算账号开户行行号",15,DataType.AN,null,NLLType.M,false));
		body.add(new Field(17,"结算账号开户行名称",80,DataType.ANS,"",NLLType.M,false));		
		body.add(new Field(18,"结算账号类型",1,DataType.A,"",NLLType.M,false));
		body.add(new Field(19,"结算账号",32,DataType.AN,null,NLLType.M,false));
		body.add(new Field(20,"结算账号户名",60,DataType.ANS,"",NLLType.M,false));
		body.add(new Field(21,"结算方式",2,DataType.AN,"",NLLType.M,false));
		body.add(new Field(22,"T+N中的N",3,DataType.N,"",NLLType.C,false));
		body.add(new Field(23,"周结日期",1,DataType.WC,"",NLLType.O,false));
		body.add(new Field(24,"月结日期",2,DataType.MC,"",NLLType.O,false));
		body.add(new Field(25,"开户省份（和结算账号开户行对应）",40,DataType.ANS,"",NLLType.M,false));
		body.add(new Field(26,"开户城市（和结算账号开户行对应）",40,DataType.ANS,"",NLLType.M,false));
		body.add(new Field(27,"垫资日息",4,DataType.N,"",NLLType.C,false));
		body.add(new Field(28,"代付手续费",8,DataType.N,"",NLLType.C,false));
		body.add(new Field(29,"优先级",3,DataType.AN,"",NLLType.C,false));
		
		
		return body;
	}
}
