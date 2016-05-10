package com.allinfinance.bo.route;

import java.util.List;
import com.allinfinance.po.route.TblUpbrhMcht;
import com.allinfinance.po.route.TblUpbrhMchtPk;

public interface TblUpbrhMchtBO {

	void add(TblUpbrhMcht tblUpbrhMcht);

	void update(TblUpbrhMcht tblUpbrhMcht);

	void delete(TblUpbrhMcht tblUpbrhMcht);

	public TblUpbrhMcht get(TblUpbrhMchtPk key);
	
	public void updateInfo(TblUpbrhMcht upbrhMcht);
	
	public void updatePk(String brhId3,TblUpbrhMchtPk pk);
	
	public List<String> checkMchtIdUpUnique(TblUpbrhMcht upbrhMcht);
}
