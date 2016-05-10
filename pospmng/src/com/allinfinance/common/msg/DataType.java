package com.allinfinance.common.msg;


/**
 * 各数据元类型的定义:
 * 1. 所有不需要填写的字段域数值N类型缺省填全0，其他类型缺省填空格。
 * 2. 所有涉及金额字段，没有特殊说明，都是以分为单位，不带小数点.
 * @author Jee Khan
 */
public enum DataType {
	A("字母向左靠，右部多余部分填空格"),
	AN("字母和/或数字，左靠，右部多余部分填空格"),
	ANS("字母、数字和/或特殊符号，左靠，右部多余部分填空格"),
	AS("字母和/或特殊符号，左靠，右部多余部分填空格"),
	DD("日"),MM("月"),
	hh("时"),mm("分"),ss("秒"),
	YY("年（2位）"),YYYY("年（4位）"),YYYYMMDD("8位年月日"),
	N(" 数值，右靠，首位有效数字前充零;若表示金额，则最右二位为角分"),
	LL("可变长数据元的长度值(二位数)"),
	LLL("可变长数据元的长度值(三位数) "),
	VAR("可变长数据元所有数据元从左向右计数，即最左边位置为1"),
	MP("金额符号（+/-）"),
	MS("商户状态"),AT("申请类别"),ST("结算方式"),WC("周结日期"),MC("月结日期"),
	RT("商户风险级别");
	private String desc;
	private DataType(String desc){
		this.desc = desc;
	}
	public String getDesc(){
		return this.desc;
	}
}
/**
 * 是否必填说明
 * @author Jee Khan
 */
enum NLLType{
	M("必填"),M1("原样返回"),C("条件成立时必填"),O("能够提供时填写"),N("必须空值");
	private String desc;
	private NLLType(String desc){
		this.desc = desc;
	}
	public String getDesc(){
		return this.desc;
	}
}

/**
 * 交易种类
 */
enum TransType{
	A("激活类交易"),B("消费类交易"),C("充值类交易"),D("查询类交易"),E("特殊类涉及余额交易"),F("信息更改交易");
	private String desc;
	private TransType(String desc){
		this.desc = desc;
	}
	public String getDesc(){
		return this.desc;
	}
}
