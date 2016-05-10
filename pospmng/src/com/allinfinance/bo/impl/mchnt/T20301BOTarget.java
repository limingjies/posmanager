package com.allinfinance.bo.impl.mchnt;

import java.util.List;

import com.allinfinance.bo.mchnt.T20301BO;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.mchnt.TblMerRoleFuncMapDAO;
import com.allinfinance.po.mchnt.TblMerRoleFuncMap;
import com.allinfinance.po.mchnt.TblMerRoleFuncMapId;

/**
 * Title:商户权限维护
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-10
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author liuxianxian
 * 
 * @version 1.0
 */
public class T20301BOTarget implements T20301BO {

private TblMerRoleFuncMapDAO tblMerRoleFuncMapDAO;
private ICommQueryDAO commQueryDAO;
	
	public void addRoleFuncMap(List<TblMerRoleFuncMap> tblRoleFuncMapList) {
		for(TblMerRoleFuncMap tblMerRoleFuncMap : tblRoleFuncMapList) {
			this.tblMerRoleFuncMapDAO.save(tblMerRoleFuncMap);
		}
	}

	public void updateRoleFuncMap(List<TblMerRoleFuncMap> tblMerRoleFuncMapList,
			List<TblMerRoleFuncMapId> tblMerRoleFuncMapIdList) {
		for(TblMerRoleFuncMap tblRoleFuncMap : tblMerRoleFuncMapList) {
			this.tblMerRoleFuncMapDAO.saveOrUpdate(tblRoleFuncMap);
		}
		for(TblMerRoleFuncMapId tblMerRoleFuncMapId : tblMerRoleFuncMapIdList) {
			this.tblMerRoleFuncMapDAO.delete(tblMerRoleFuncMapId);
		}
	}

	public TblMerRoleFuncMapDAO getTblRoleFuncMapDAO() {
		return tblMerRoleFuncMapDAO;
	}

	public List<TblMerRoleFuncMap> getAllRoleMapId(Long mchntTp){
		String hql = " from "+TblMerRoleFuncMap.class+" tblMerRoleFuncMap where tblMerRoleFuncMap.id.keyId = " + mchntTp;
		return commQueryDAO.findByHQLQuery(hql);
	}
	
	public List<Object> getRoleMap(Long mchntTp){
		String sql = "select VALUE_ID from TBL_MER_ROLE_FUNC where KEY_ID = " + mchntTp;
		return commQueryDAO.findBySQLQuery(sql);
	}

	public void setTblMerRoleFuncMapDAO(TblMerRoleFuncMapDAO tblMerRoleFuncMapDAO) {
		this.tblMerRoleFuncMapDAO = tblMerRoleFuncMapDAO;
	}

	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	public TblMerRoleFuncMapDAO getTblMerRoleFuncMapDAO() {
		return tblMerRoleFuncMapDAO;
	}
	
	
}
