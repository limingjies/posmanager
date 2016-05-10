package com.allinfinance.bo.impl.route;

import java.util.List;
import com.allinfinance.bo.route.TblUpbrhFeeDetailBO;
import com.allinfinance.common.Constants;
import com.allinfinance.dao.iface.route.TblUpbrhFeeDetailDAO;
import com.allinfinance.po.route.TblUpbrhFeeDetail;
import com.allinfinance.system.util.CommonFunction;

public class TblUpbrhFeeDetailBORoute implements TblUpbrhFeeDetailBO {
	private TblUpbrhFeeDetailDAO tblUpbrhFeeDetailDAO;

	@Override
	public void add(TblUpbrhFeeDetail tblUpbrhFeeDetail) {

		tblUpbrhFeeDetail.setCrtTime(CommonFunction.getCurrentDateTime());
		tblUpbrhFeeDetail.setUptTime(CommonFunction.getCurrentDateTime());
		tblUpbrhFeeDetailDAO.save(tblUpbrhFeeDetail);
	}

	/**
	 * 对象修改（给修改人和修改时间赋值）
	 */
	public void update(TblUpbrhFeeDetail tblUpbrhFeeDetail) {/*
		HttpServletRequest request = ServletActionContext.getRequest();
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");       
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间       
		String time = formatter.format(curDate);  
		//更新修改时间 和 操作人ID
		tblUpbrhFeeDetail.setUptTime(time);
		tblUpbrhFeeDetail.setUptOpr(operator.getOprId());*/
		//tblUpbrhFeeDetailDAO.update(tblUpbrhFeeDetail);
		tblUpbrhFeeDetailDAO.saveOrUpdate(tblUpbrhFeeDetail);
	}
	
	@Override
	public String delete(TblUpbrhFeeDetail tblUpbrhFeeDetail) {
		if (null != tblUpbrhFeeDetail) {
			tblUpbrhFeeDetailDAO.delete(tblUpbrhFeeDetail);
			return  Constants.SUCCESS_CODE;
		}else {
			return  Constants.FAILURE_CODE;
		}
	}


	public List<TblUpbrhFeeDetail> findAll() {
		return tblUpbrhFeeDetailDAO.findAll();
	}
	
	public TblUpbrhFeeDetail get(String key) {
		return tblUpbrhFeeDetailDAO.get(key);
	}
	

	public TblUpbrhFeeDetailDAO getTblUpbrhFeeDetailDAO() {
		return tblUpbrhFeeDetailDAO;
	}

	public void setTblUpbrhFeeDetailDAO(TblUpbrhFeeDetailDAO tblUpbrhFeeDetailDAO) {
		this.tblUpbrhFeeDetailDAO = tblUpbrhFeeDetailDAO;
	}
	
	
}
