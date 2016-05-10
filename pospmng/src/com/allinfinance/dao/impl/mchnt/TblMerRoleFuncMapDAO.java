/* @(#)
 *
 * Project:PFConsole
 *
 * Modify Information:
 * =============================================================================
 *   Author         Date           Description
 *   ------------ ---------- ---------------------------------------------------
 *   PanShuang      2011-6-14       first release
 *
 *
 * Copyright Notice:
 * =============================================================================
 *       Copyright 2011 allinfinance, Inc. All rights reserved.
 *
 *       This software is the confidential and proprietary information of
 *       Shanghai allinfinance Co., Ltd. ("Confidential Information").
 *       You shall not disclose such Confidential Information and shall use it
 *       only in accordance with the terms of the license agreement you entered
 *       into with allinfinance.
 *
 * Warning:
 * =============================================================================
 *
 */
package com.allinfinance.dao.impl.mchnt;
import java.io.Serializable;
import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.mchnt.TblMerRoleFuncMap;
import com.allinfinance.po.mchnt.TblMerRoleFuncMapId;

/**
 * Title:
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2011-6-14
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class TblMerRoleFuncMapDAO extends _RootDAO<TblMchtBaseInf> implements com.allinfinance.dao.iface.mchnt.TblMerRoleFuncMapDAO{

	/* (non-Javadoc)
	 * @see com.allinfinance.dao._RootDAO#getReferenceClass()
	 */
	@Override
	protected Class getReferenceClass() {
		return TblMerRoleFuncMap.class;
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.TblMerRoleFuncMapDAO#delete(java.lang.String)
	 */
	public void delete(TblMerRoleFuncMapId id) {
		super.delete((Object) load(id));
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.TblMerRoleFuncMapDAO#delete(as.allinfinance.po.base.management.mer.TblMerRoleFuncMap)
	 */
	public void delete(TblMerRoleFuncMap tblMchntInfo) {
		super.delete((Object) tblMchntInfo);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.TblMerRoleFuncMapDAO#findAll()
	 */
	public List<TblMerRoleFuncMap> findAll() {
		return super.loadAll(null);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.TblMerRoleFuncMapDAO#get(java.lang.String)
	 */
	public TblMerRoleFuncMap get(TblMerRoleFuncMapId key) {
		return  (TblMerRoleFuncMap) super.get(getReferenceClass(), (Serializable) key);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.TblMerRoleFuncMapDAO#load(java.lang.String)
	 */
	public TblMerRoleFuncMap load(TblMerRoleFuncMapId key) {
		return (TblMerRoleFuncMap) load(getReferenceClass(), (Serializable) key);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.TblMerRoleFuncMapDAO#save(as.allinfinance.po.base.management.mer.TblMerRoleFuncMap)
	 */
	public TblMerRoleFuncMapId save(TblMerRoleFuncMap tblMchntInfo) {
		return (TblMerRoleFuncMapId) super.save(tblMchntInfo);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.TblMerRoleFuncMapDAO#saveOrUpdate(as.allinfinance.po.base.management.mer.TblMerRoleFuncMap)
	 */
	public void saveOrUpdate(TblMerRoleFuncMap tblMchntInfo) {
		super.saveOrUpdate(tblMchntInfo);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.dao.iface.TblMerRoleFuncMapDAO#update(as.allinfinance.po.base.management.mer.TblMerRoleFuncMap)
	 */
	public void update(TblMerRoleFuncMap tblMchntInfo) {
		super.update(tblMchntInfo);
		
	}

}
