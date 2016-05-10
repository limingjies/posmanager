package com.allinfinance.common.msg;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Msg {
	private Map<Integer,Field> fieldMap = null;
	private int fieldCount;
	private int msgLength;
	
	/**
	 * @param fieldCount 报文字段个数
	 */
	public Msg(int fieldCount){
		this.fieldCount = fieldCount;
		fieldMap = new HashMap<Integer,Field>();
		this.msgLength = 0;
	}
	/**
	 * 添加一个报文字段
	 * @param field
	 */
	public void add(Field field){
		fieldMap.put(field.getIndex(),field);
		this.msgLength += field.getLength();
	}
	/**
	 * 删除指定序号的报文字段
	 * @param index
	 * @return
	 */
	public Field remove(int index){
		this.msgLength -= fieldMap.get(index).getLength();
		return fieldMap.remove(index);
	}
	/**
	 * 删除指定的报文字段
	 * @param field
	 * @return
	 */
	public Field remove(Field field){
		this.msgLength -= field.getLength();
		return fieldMap.remove(field.getIndex());
	}
	/**
	 * 检索是否包含指定序号的报文字段
	 * @param index
	 * @return
	 */
	public boolean contain(int index){
		return fieldMap.containsKey(index);
	}
	/**
	 * 获取指定序号的报文字段
	 * @param index
	 * @return
	 */
	public Field getField(int index){
		return fieldMap.get(index);
	}
	/**
	 * 返回所有的报文字段序号组成的字符串
	 * @return
	 */
	public String getIndexes(){
		return this.fieldMap.keySet().toArray().toString();
	}
	/**
	 * 返回报文字段个数
	 * @return
	 */
	public int getFieldCount() {
		return fieldCount;
	}
	/**
	 * 设置报文字段个数
	 * @param fieldCount
	 */
	public void setFieldCount(int fieldCount) {
		this.fieldCount = fieldCount;
	}
	/**
	 * 返回报文长度
	 * @return
	 */
	public int getMsgLength() {
		return this.msgLength;
	}
	
	/**
	 * 1. 验证报文的字段格式是否正确；
	 * 2. 将所有报文字段组接成报文字符串。
	 * @return	报文字符串
	 * @throws Exception
	 */
	public String getMessage() throws Exception{
		if(this.fieldMap.size() < this.fieldCount){
			throw new Exception("报文字段个数不正确,缺少" + (this.fieldCount-this.fieldMap.size()) + "个字段。");
		}
		if(this.fieldMap.size() > this.fieldCount){
			throw new Exception("报文字段个数不正确,多出" + (this.fieldMap.size()-this.fieldCount) + "个字段。");
		}
		if(this.fieldMap.size() == this.fieldCount){
			if(this.fieldCount != Collections.max(this.fieldMap.keySet())){
				throw new Exception("报文字段序号不正确。");
			}else{
				if(validFields()){  //所有字段都是格式正确的
					StringBuilder buf = new StringBuilder();
					for(int i = 1;i<=this.fieldCount ;i++){
						buf.append(fieldMap.get(i).getRealValue());
						//this.msgLength += fieldMap.get(i).getLength();
					}
					return buf.toString();
				}
			}
		}
		return null;
	}
	/**
	 * 验证报文对象的字段的格式是否正确
	 * @return
	 * @throws Exception
	 */
	private boolean validFields() throws Exception{
		boolean b = true;
		for(int i=1;i<=this.getFieldCount();i++){
			b = this.getField(i).genRealValue();
			if(!b){
				throw new Exception(this.getField(i).getErrMsg());
			}
		}
		return b;
	}
}



