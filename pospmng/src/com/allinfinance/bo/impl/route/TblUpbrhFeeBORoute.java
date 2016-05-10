package com.allinfinance.bo.impl.route;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import com.allinfinance.bo.route.TblUpbrhFeeBO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.route.TblUpbrhFeeDAO;
import com.allinfinance.po.route.TblUpbrhFee;
import com.allinfinance.system.util.CommonFunction;

public class TblUpbrhFeeBORoute implements TblUpbrhFeeBO {
	private TblUpbrhFeeDAO tblUpbrhFeeDAO;
	private ICommQueryDAO commGWQueryDAO;

	@Override
	public void add(TblUpbrhFee tblUpbrhFee) {
		//获取对象，依据DISC_ID(转换成数字类型)倒叙排序
		String hql = "From TblUpbrhFee order by to_number(DISC_ID) desc ";
		@SuppressWarnings("unchecked")
		List<TblUpbrhFee> list = commGWQueryDAO.findByHQLQuery(hql);
		if (list.size()>0) {
			TblUpbrhFee info = list.get(0);
			int x = Integer.parseInt(info.getDiscId());
			x = x+1;
			tblUpbrhFee.setDiscId(x+"");
		}else {
			tblUpbrhFee.setDiscId("1");
		}
		tblUpbrhFee.setBrhId2("0");//默认值
		tblUpbrhFee.setStatus("0");//--默认状态为0--启用
		tblUpbrhFee.setCrtTime(CommonFunction.getCurrentDateTime());
		tblUpbrhFee.setUptTime(CommonFunction.getCurrentDateTime());
		
		tblUpbrhFeeDAO.save(tblUpbrhFee);
	}

	/**
	 * 对象修改（给修改人和修改时间赋值）
	 */
	public void update(TblUpbrhFee tblUpbrhFee) {
		HttpServletRequest request = ServletActionContext.getRequest();
		Operator operator = (Operator) request.getSession().getAttribute(Constants.OPERATOR_INFO);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");       
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间       
		String time = formatter.format(curDate);  
		//更新修改时间 和 操作人ID
		tblUpbrhFee.setUptTime(time);
		tblUpbrhFee.setUptOpr(operator.getOprId());
		tblUpbrhFeeDAO.update(tblUpbrhFee);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public String delete(TblUpbrhFee tblUpbrhFee) {
		if (null != tblUpbrhFee) {
			//删除前判断是否有渠道商户对应该档位，有则提示“不可删除”
			String sql = "select * from Tbl_Upbrh_Mcht where status=0 and disc_id = '"+tblUpbrhFee.getDiscId()+"'";
			List list = commGWQueryDAO.findBySQLQuery(sql);
			if (list.size()>0) {
				return  Constants.FAILURE_CODE;
			}else {
				tblUpbrhFeeDAO.delete(tblUpbrhFee);
				return  Constants.SUCCESS_CODE;
			}
		}else {
			return  Constants.FAILURE_CODE;
		}
	}

	/**
	 * 查询 是否有渠道商户为0（启用状态）的记录对应对象的档位，若存在对应，则返回错误应答码，否则返回正确应答码
	 * @param tblUpbrhFee
	 * @return
	 */
	public String findConnect(TblUpbrhFee tblUpbrhFee) {
		String res = Constants.SUCCESS_CODE;
		if (null != tblUpbrhFee) {
			//删除前判断是否有渠道商户对应该档位，有则提示“不可删除”
			String sql = "select * from Tbl_Upbrh_Mcht where status=0 and disc_id = '"+tblUpbrhFee.getDiscId()+"'";
			@SuppressWarnings("rawtypes")
			List list = commGWQueryDAO.findBySQLQuery(sql);
			if (list.size()>0) {
				res = Constants.FAILURE_CODE;
			}
		}
		return res;
	}

	/**
	 * 判断某一渠道是否在档位(业务成本扣率主表Tbl_Upbrh_Fee表)中存在对应数据
	 * @param tblUpbrhFee.getBrhId2() 渠道号
	 * @return list
	 */
	@SuppressWarnings("rawtypes")
	public List findUpbrhFeeByBrhId(String brhId) {
		if (!StringUtils.isBlank(brhId)) {
			//删除前判断是否有渠道商户对应该档位，有则提示“不可删除”
			String sql = "select * from Tbl_Upbrh_Fee where brh_id2 = '"+brhId+"'";
			List list = commGWQueryDAO.findBySQLQuery(sql);
			return list;
		}else {
			return null;
		}
	}
	
	public TblUpbrhFee get(String key) {
		return tblUpbrhFeeDAO.get(key);
	}
	
	public TblUpbrhFeeDAO getTblUpbrhFeeDAO() {
		return tblUpbrhFeeDAO;
	}
	public void setTblUpbrhFeeDAO(TblUpbrhFeeDAO tblUpbrhFeeDAO) {
		this.tblUpbrhFeeDAO = tblUpbrhFeeDAO;
	}

	public ICommQueryDAO getCommGWQueryDAO() {
		return commGWQueryDAO;
	}

	public void setCommGWQueryDAO(ICommQueryDAO commGWQueryDAO) {
		this.commGWQueryDAO = commGWQueryDAO;
	}

	
}
