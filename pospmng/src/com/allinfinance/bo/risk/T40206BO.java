package com.allinfinance.bo.risk;


import java.util.List;

public interface T40206BO {

	public List<String> query(String riskLvl);

	public String add(String riskLvl,String resved,List<String> addList);
	
	public String update(String riskLvl,String resved,List<String> keepList,List<String> deleteList,List<String> addList);
	
	public String delete(String riskLvl,List<String> deleteList);
}
