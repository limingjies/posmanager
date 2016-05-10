package com.allinfinance.dao.impl.base;


import java.math.BigDecimal;
import java.util.List;

import com.allinfinance.dao._RootDAO;
import com.allinfinance.po.base.TblBrhSettleInf;
import com.allinfinance.po.base.TblBrhSettleInfHis;
import com.allinfinance.po.base.TblBrhSettleInfHisPK;
import com.allinfinance.system.util.CommonFunction;

public class TblBrhSettleInfHisDAO extends _RootDAO<TblBrhSettleInfHis> implements
		com.allinfinance.dao.iface.base.TblBrhSettleInfHisDAO {

	public TblBrhSettleInfHisDAO() {
	}

	public Class<TblBrhSettleInfHis> getReferenceClass() {
		return TblBrhSettleInfHis.class;
	}

	/**
	 * Cast the object as a com.allinfinance.po.brh.TblBrhInfo
	 */
	public TblBrhSettleInfHis cast(Object object) {
		return (TblBrhSettleInfHis) object;
	}

	public TblBrhSettleInfHis load(TblBrhSettleInfHisPK key) {
		return (TblBrhSettleInfHis) load(getReferenceClass(), key);
	}

	public TblBrhSettleInfHis get(TblBrhSettleInfHisPK key) {
		return (TblBrhSettleInfHis) get(getReferenceClass(), key);
	}

	public void save(TblBrhSettleInfHis tblBrhSettleInfHis) {
		 super.save(tblBrhSettleInfHis);
	}

	public void update(TblBrhSettleInfHis tblBrhSettleInfHis) {
		super.update(tblBrhSettleInfHis);
	}
	
	@Override
	public void delete(TblBrhSettleInfHisPK id) {
		super.delete((Object) load(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getNextSeqId(String brhId) {
		String sql = "select max(to_number(SEQ_ID))+1 from TBL_BRH_SETTLE_INF_HIS WHERE BRH_ID='"+brhId+"' ";
		List<BigDecimal> resultSet= CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if(resultSet.size()>0 && resultSet.get(0) != null){
			return resultSet.get(0).toString();
		}else{
			return "1";
		}
	}

	@Override
	public void delete(String brhId) {
		String sql="DELETE FROM TBL_BRH_SETTLE_INF_HIS WHERE BRH_ID='"+brhId+"' ";
		CommonFunction.getCommQueryDAO().excute(sql);		
	}
}