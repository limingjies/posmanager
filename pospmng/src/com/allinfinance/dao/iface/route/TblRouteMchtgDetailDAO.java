package com.allinfinance.dao.iface.route;

import com.allinfinance.po.route.TblRouteMchtgDetail;
import com.allinfinance.po.route.TblRouteMchtgDetailPk;

public interface TblRouteMchtgDetailDAO {
	public Class<TblRouteMchtgDetail> getReferenceClass();

	public TblRouteMchtgDetail cast(Object object);

	public TblRouteMchtgDetail load(TblRouteMchtgDetailPk key);

	public TblRouteMchtgDetail get(TblRouteMchtgDetailPk key);

	public TblRouteMchtgDetailPk save(TblRouteMchtgDetail TblRouteMchtgDetail);

	public void saveOrUpdate(TblRouteMchtgDetail TblRouteMchtgDetail);

	public void update(TblRouteMchtgDetail TblRouteMchtgDetail);

	public void delete(TblRouteMchtgDetail TblRouteMchtgDetail);

	public void delete(TblRouteMchtgDetailPk id);

	public TblRouteMchtgDetail getByMchtId(String mchtId);

}