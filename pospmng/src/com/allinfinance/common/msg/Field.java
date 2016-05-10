package com.allinfinance.common.msg;


public class Field{	
	private int index; 	//报文字段序号
	private String name; //字段名称
	private int length;	//字段长度
	DataType dType;   	//数据类型
	NLLType nllType;  	//必填说明
	private String value;  //报文字段值
	private String realValue; //经过处理之后的字段值
	private boolean isValid;  //字段值是否有效
	private String errMsg;    //错误信息
	private boolean condition;
	/**
	 * 
	 * @param index		报文字段序号
	 * @param length	报文字段长度
	 * @param dType		字段数据类型
	 * @param value		报文字段值
	 * @param nllType	字段必填类型
	 * @param condition		是否必填的条件，主要针对必填类型为C时需要
	 * @throws Exception
	 */
	public Field(int index,String name,int length,DataType dType,String value,NLLType nllType,boolean condition) throws Exception{
		if(index < 1){
			throw new Exception("报文字段序号需大于0.");
		}
		this.index = index;
		this.name = name;
		this.length = length;
		this.dType = dType;
		this.value = value;
		this.nllType = nllType;
		this.condition = condition;
		
		this.isValid = true;
		if(null == this.value){
			this.value = "";
		}
		this.realValue = null;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) throws Exception {
		this.length = length;
		this.genRealValue();
	}
	public DataType getDType() {
		return dType;
	}
	public void setDType(DataType dType) throws Exception {
		this.dType = dType;
		this.genRealValue();
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) throws Exception{
		this.value = value;
		if(this.value == null){
			this.value = "";
		}
		this.genRealValue();
	}
	public String getRealValue(){
		return this.realValue;
	}
	public String getErrMsg(){
		return this.errMsg;
	}
	
	/**
	 * 验证字段的格式是否正确
	 * @return
	 */
	boolean checkValid(){
		String errMsg1 = "字段" + this.index + "(" + this.name + ")：不可为空。";
		String errMsg2 = "字段" + this.index + "(" + this.name + ")：数据类型（" + this.dType.getDesc() + "）格式不正确!";
		String errMsg3 = "字段" + this.index + "(" + this.name + ")：必须为空。";
		//必填检查
		switch(this.nllType){
		case M:
		case M1:
			if(this.isValid && (null == this.value || "".equals(this.value))){
				this.errMsg = errMsg1;
				this.isValid = false;
				return this.isValid;
			}
			break;
		case C:
			if(this.isValid && (null == this.value || "".equals(this.value)) && this.condition){
				this.errMsg = errMsg1;
				this.isValid = false;
				return this.isValid;
			}
			break;
		case N:
			if(this.isValid && null != this.value && !"".equals(this.value)){
				this.errMsg = errMsg3;
				this.isValid = false;
				return this.isValid;
			}
			break;
		case O:
			break;
		default:
			break;
		}
		//数据类型检查
		if(this.nllType != NLLType.N && this.isValid && !(null == this.value || "".equals(this.value))){
			switch(this.dType){
			case A:
				if(!this.value.matches("^\\w*\\s*$")){
					this.isValid = false;
					this.errMsg = errMsg2;
					return this.isValid;
				}
				break;
			case AN:
				if( !this.value.matches("^[\\w|/|\\d]*\\s*$")){
					this.isValid = false;
					this.errMsg = errMsg2;
					return this.isValid;
				}
				break;
			case ANS:
				if(!this.value.matches(".*")){
					this.isValid = false;
					this.errMsg = errMsg2;
					return this.isValid;
				}
				break;
			case AS:
				if(!this.value.matches(".*")){
					this.isValid = false;
					this.errMsg = errMsg2;
					return this.isValid;
				}
				break;
			case DD:
				if( !this.value.matches("^[0-3]?\\d$")){
					this.isValid = false;
					this.errMsg = errMsg2;
					return this.isValid;
				} else {
					int dd = Integer.parseInt(this.value);
					if(31 < dd || 0 >= dd){
						this.isValid = false;
						this.errMsg = errMsg2;
						return this.isValid;
					}
				}
				break;
			case MM:
				if(!this.value.matches("^[0-1]?\\d$")){
					this.isValid = false;
					this.errMsg = errMsg2;
					return this.isValid;
				}else {
					int mm = Integer.parseInt(this.value);
					if(12 < mm || 0 >= mm){
						this.isValid = false;
						this.errMsg = errMsg2;
						return this.isValid;
					}
				}
				break;
			case hh:
			case mm:
			case ss:
				if(!this.value.matches("^[0-5]?\\d$")){
					this.isValid = false;
					this.errMsg = errMsg2;
					return this.isValid;
				}
				break;
			case YY:
				if(!this.value.matches("^\\d{2}$")){
					this.isValid = false;
					this.errMsg = errMsg2;
					return this.isValid;
				}
				break;
			case YYYY:
				if(!this.value.matches("^\\d{4}$")){
					this.isValid = false;
					this.errMsg = errMsg2;
					return this.isValid;
				}
				break;
			case YYYYMMDD:
				if(!this.value.matches("^\\d{4}[0-1]\\d[0-3]\\d$")){
					this.isValid = false;
					this.errMsg = errMsg2;
					return this.isValid;
				}
				break;
			case N:
				if(!this.value.matches("^\\d*$")){
					this.isValid = false;
					this.errMsg = errMsg2;
					return this.isValid;
				}
				break;
			case MP:
				if(!this.value.matches("^[+-]?$")){
					this.isValid = false;
					this.errMsg = errMsg2;
					return this.isValid;
				}
				break;
			case RT:
				if(!this.value.matches("^[1-5]?$")){
					this.isValid = false;
					this.errMsg = errMsg2;
					return this.isValid;
				}
				break;
			case MS:
				if(!this.value.matches("^[0-9]?$")){
					this.isValid = false;
					this.errMsg = errMsg2;
					return this.isValid;
				}
				break;
			case AT:
				if(!this.value.matches("^[0-2]?$")){
					this.isValid = false;
					this.errMsg = errMsg2;
					return this.isValid;
				}
				break;	
			case ST:
				if(!this.value.matches("^([01][0-2])?$")){
					this.isValid = false;
					this.errMsg = errMsg2;
					return this.isValid;
				}
				break;
			case WC:
				if(!this.value.matches("^[1-7]?$")){
					this.isValid = false;
					this.errMsg = errMsg2;
					return this.isValid;
				}
				break;
			case MC:
				if(!this.value.matches("^(([0-2][1-8])|(10|20))?$")){
					this.isValid = false;
					this.errMsg = errMsg2;
					return this.isValid;
				}
				break;
			default:
				;
			}
		}
		return this.isValid;
	}
	
	/**
	 * 1.验证字段是否正确，
	 * 2.生成格式化后的字段值
	 * @return
	 * @throws Exception
	 */
	 boolean genRealValue() throws Exception{
		boolean  b = this.checkValid();
		if(!b){
			return b;
		}
		String strValue = "";
		if(length > value.getBytes("GBK").length){
			int padLen = length - value.getBytes("GBK").length ;
			String pad = "";
			switch(dType){
			case A:
			case AN:
			case ANS:
			case AS:
				pad = " ";
				strValue = value + Field.getPadding(padLen, pad);
				break;
			case DD:
			case MM:
				if(!"".equals(this.value)){
					if (1 == value.length()){
						strValue = "0" + value;
					}else if (2 == value.length()){
						strValue = value;
					}else{
						throw new Exception("数值不正确，超长。");
					}
				} else {
					pad = " ";
					strValue = Field.getPadding(padLen, pad);
				}
				break;
			case hh:
			case mm:
			case ss:
				if(!"".equals(this.value)){
					if (1 == value.length()){
						strValue = "0" + value;
					}else if (2 == value.length()){
						strValue = value;
					}else{
						throw new Exception("数值不正确，超长。");
					}
				} else {
					pad = " ";
					strValue = Field.getPadding(padLen, pad);
				}
				break;
			case YY:
				if(!"".equals(this.value)){
					if (2 == value.length()){
						strValue = value;;
					}else{
						throw new Exception("数值不正确(类型：YY)。");
					}
				} else {
					pad = " ";
					strValue = Field.getPadding(padLen, pad);
				}
				break;
			case YYYY:
				if(!"".equals(this.value)){
					if (4 == value.length()){
						strValue = value;;
					}else{
						throw new Exception("数值不正确(类型：YYYY)。");
					}
				} else {
					pad = " ";
					strValue = Field.getPadding(padLen, pad);
				}
				break;
			case YYYYMMDD:
				if(!"".equals(this.value)){
					if (8 == value.length()){
						strValue = value;;
					}else{
						throw new Exception("数值不正确(类型：YYYYMMDD)。");
					}
				} else {
					pad = " ";
					strValue = Field.getPadding(padLen, pad);
				}
				break;
			case N:
				pad = "0";
				strValue = Field.getPadding(padLen, pad) + value;
				break;
			default:
				strValue = Field.getPadding(padLen, " ") + value;
			}		
		} else if (length < value.getBytes("GBK").length){
			throw new Exception("数据值的长度超过指定长度。");
		}else{
			strValue =  value;
		}
		this.realValue = strValue;
		return true;
	}
	
	/*
	 * 使用填充字符pad拼接padLen长度的字符串
	 */
	public static String getPadding(int padLen,String pad){
		StringBuilder buf = new StringBuilder();
		for(int i=0;i<padLen;i++){
			buf.append(pad);
		}
		return buf.toString(); 
	}
}

