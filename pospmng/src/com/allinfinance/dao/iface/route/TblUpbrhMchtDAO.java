package com.allinfinance.dao.iface.route;

import com.allinfinance.po.route.TblUpbrhMcht;
import com.allinfinance.po.route.TblUpbrhMchtPk;

public interface TblUpbrhMchtDAO {
	public Class<TblUpbrhMcht> getReferenceClass();

	public TblUpbrhMcht cast(Object object);

	public TblUpbrhMcht load(TblUpbrhMchtPk key);

	public TblUpbrhMcht get(TblUpbrhMchtPk key);

	public TblUpbrhMchtPk save(TblUpbrhMcht TblUpbrhMcht);

	public void saveOrUpdate(TblUpbrhMcht TblUpbrhMcht);

	public void update(TblUpbrhMcht TblUpbrhMcht);

	public void delete(TblUpbrhMcht TblUpbrhMcht);

	public void delete(TblUpbrhMchtPk id);

	public void execute(String sql);

}