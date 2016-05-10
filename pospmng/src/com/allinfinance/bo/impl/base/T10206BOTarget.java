package com.allinfinance.bo.impl.base;

import java.util.List;

import com.allinfinance.bo.base.T10206BO;
import com.allinfinance.dao.iface.base.TblEmvParaDAO;
import com.allinfinance.po.base.TblEmvPara;
import com.allinfinance.po.base.TblEmvParaPK;
import com.allinfinance.system.util.CommonFunction;

/**
 * Title:交易码BO实现
 * 
 * Description:
 * 
 * Copyright: Copyright (c) 2010-8-9
 * 
 * Company: Shanghai allinfinance Co., Ltd.
 * 
 * @author 
 * 
 * @version 1.0
 */
public class T10206BOTarget implements T10206BO {
	
	private TblEmvParaDAO tblEmvParaDAO;

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.base.T10206BO#createTblBankBinInf(com.allinfinance.po.base.TblEmvPara)
	 */
	public void createTblEmvPara(TblEmvPara tblEmvPara) {
		tblEmvParaDAO.save(tblEmvPara);
		
		//更新终端表状态
		String key = tblEmvPara.getId().getUsageKey();
		String field = "";
		if (key.equals("1")) {
			field = "KEY_DOWN_SIGN";
		} else {
			field = "IC_DOWN_SIGN";
		}
		String sql0 = "update TBL_TERM_INF set KEY_DOWN_SIGN = '1',IC_DOWN_SIGN='1'";
		String sql1 = "update TBL_TERM_INF_TMP set KEY_DOWN_SIGN = '1',IC_DOWN_SIGN='1'";
		
		CommonFunction.getCommQueryDAO().excute(sql0);
		CommonFunction.getCommQueryDAO().excute(sql1);
		
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.base.T10206BO#delete(java.lang.Integer)
	 */
	public void delete(TblEmvParaPK id) {
		tblEmvParaDAO.delete(id);
		
		//更新终端表状态
		/*String key = id.getUsageKey();
		String field = "";
		if (key.equals("1")) {
			field = "KEY_DOWN_SIGN";
		} else {
			field = "IC_DOWN_SIGN";
		}*/
		String sql0 = "update TBL_TERM_INF set KEY_DOWN_SIGN = '1',IC_DOWN_SIGN='1' ";
		String sql1 = "update TBL_TERM_INF_TMP set KEY_DOWN_SIGN = '1',IC_DOWN_SIGN='1' ";
		
		CommonFunction.getCommQueryDAO().excute(sql0);
		CommonFunction.getCommQueryDAO().excute(sql1);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.base.T10206BO#get(java.lang.TblEmvParaPK)
	 */
	public TblEmvPara get(TblEmvParaPK id) {
		// TODO Auto-generated method stub
		return tblEmvParaDAO.get(id);
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.base.T10206BO#update(com.allinfinance.po.base.TblEmvPara)
	 */
	public void update(TblEmvPara tblEmvPara) {
		tblEmvParaDAO.update(tblEmvPara);
		
		//更新终端表状态
		String key = tblEmvPara.getId().getUsageKey();
		String field = "";
		if (key.equals("1")) {
			field = "KEY_DOWN_SIGN";
		} else {
			field = "IC_DOWN_SIGN";
		}
		String sql0 = "update TBL_TERM_INF set " + field + " = '1'";
		String sql1 = "update TBL_TERM_INF_TMP set " + field + " = '1'";
		
		CommonFunction.getCommQueryDAO().excute(sql0);
		CommonFunction.getCommQueryDAO().excute(sql1);
		
	}

	/* (non-Javadoc)
	 * @see com.allinfinance.bo.base.T10206BO#update(java.util.List)
	 */
	public void update(List<TblEmvPara> tblEmvParaList) {
		for(TblEmvPara tblEmvPara : tblEmvParaList){
			tblEmvParaDAO.update(tblEmvPara);
		}
	}

	public TblEmvParaDAO getTblEmvParaDAO() {
		return tblEmvParaDAO;
	}

	public void setTblEmvParaDAO(TblEmvParaDAO tblEmvParaDAO) {
		this.tblEmvParaDAO = tblEmvParaDAO;
	}
	
	

	
}
