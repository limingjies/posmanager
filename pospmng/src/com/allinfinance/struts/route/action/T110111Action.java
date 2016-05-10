package com.allinfinance.struts.route.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.allinfinance.bo.route.TblUpbrhFeeBO;
import com.allinfinance.bo.route.TblUpbrhFeeDetailBO;
import com.allinfinance.common.Constants;
import com.allinfinance.po.route.TblUpbrhFee;
import com.allinfinance.po.route.TblUpbrhFeeDetail;
import com.allinfinance.po.route.TblUpbrhFeeDetailPk;
import com.allinfinance.struts.system.action.BaseSupport;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.JSONBean;

@SuppressWarnings("serial")
public class T110111Action extends BaseSupport {
	private TblUpbrhFeeDetailBO tblUpbrhFeeDetailBO=(TblUpbrhFeeDetailBO) ContextUtil.getBean("TblUpbrhFeeDetailBO");
	private TblUpbrhFeeBO tblUpbrhFeeBO=(TblUpbrhFeeBO) ContextUtil.getBean("TblUpbrhFeeBO");
	private TblUpbrhFeeDetail upbrhFeeDetail;


	public String add(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String oprId = getOperator().getOprId();
		try {

			String discNm = request.getParameter("discNm");//档位名称
			TblUpbrhFee tblUpbrhFee = new TblUpbrhFee();
			tblUpbrhFee.setDiscNm(discNm);
			tblUpbrhFee.setCrtOpr(oprId);
			tblUpbrhFee.setUptOpr(oprId);
			tblUpbrhFeeBO.add(tblUpbrhFee);
			
			JSONBean jsonBean = new JSONBean();
			jsonBean.parseJSONArrayData(data);
			int len = jsonBean.getArray().size();
			for(int i = 0; i < len; i++) {
				jsonBean.setObject(jsonBean.getJSONDataAt(i));
				//卡类型
				String cardType = jsonBean.getJSONDataAt(i).getString("cardType");
				String txnNum = jsonBean.getJSONDataAt(i).getString("txnNum");
				String flag = jsonBean.getJSONDataAt(i).getString("flag");
				String feeValue = jsonBean.getJSONDataAt(i).getString("feeValue");
				String maxFee = jsonBean.getJSONDataAt(i).getString("maxFee");
				
				TblUpbrhFeeDetailPk pk = new TblUpbrhFeeDetailPk();
				pk.setDiscId(tblUpbrhFee.getDiscId());
				BigDecimal a = new BigDecimal(i);
				pk.setIndexNum(a);

				TblUpbrhFeeDetail temp = new TblUpbrhFeeDetail();
				temp.setPk(pk);
				temp.setCardType(cardType);
				temp.setTxnNum(txnNum);
				temp.setFlag(new BigDecimal(flag));
				temp.setFeeValue(Double.parseDouble(feeValue));
				temp.setMaxFee(Double.parseDouble(maxFee));
				temp.setCrtOpr(oprId);
				temp.setUptOpr(oprId);
				tblUpbrhFeeDetailBO.add(temp);
			}
			rspCode="00";
			writeSuccessMsg("新增成功！");
		} catch (Exception e) {
			e.printStackTrace();
			log("新增操作失败");
		}
		return null;
	}
	
	public String update() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		String oprId = getOperator().getOprId();
		try {
			String discId = request.getParameter("discId");//手续费类型ID
			String discNm = request.getParameter("discNm");//档位名称
			
			TblUpbrhFee tblUpbrhFee = tblUpbrhFeeBO.get(discId);
			tblUpbrhFee.setDiscNm(discNm);
			tblUpbrhFee.setUptOpr(oprId);
			tblUpbrhFeeBO.update(tblUpbrhFee);
			
			JSONBean jsonBean = new JSONBean();
			jsonBean.parseJSONArrayData(data);
			int len = jsonBean.getArray().size();
			 
			//先查询出TblUpbrhFeeDetail所有记录，判断DiscId与现在修改记录的DiscId是否相同，
			//相同则先全部删除，后续新增
			List<TblUpbrhFeeDetail> list = tblUpbrhFeeDetailBO.findAll();
			for (TblUpbrhFeeDetail tblUpbrhFeeDetail : list) {
				if (tblUpbrhFeeDetail.getPk().getDiscId().equals(tblUpbrhFee.getDiscId())) {
					tblUpbrhFeeDetailBO.delete(tblUpbrhFeeDetail);
				}
			}
			
			for(int i = 0; i < len; i++) {
				jsonBean.setObject(jsonBean.getJSONDataAt(i));
				//卡类型
				String cardType = jsonBean.getJSONDataAt(i).getString("cardType");
				String txnNum = jsonBean.getJSONDataAt(i).getString("txnNum");
				String flag = jsonBean.getJSONDataAt(i).getString("flag");
				String feeValue = jsonBean.getJSONDataAt(i).getString("feeValue");
				String maxFee = jsonBean.getJSONDataAt(i).getString("maxFee");
				
				TblUpbrhFeeDetailPk pk = new TblUpbrhFeeDetailPk();
				pk.setDiscId(tblUpbrhFee.getDiscId());
				pk.setIndexNum(new BigDecimal(i));

				//依据主键查询对象是否存在，存在则先删除，后新增。
				TblUpbrhFeeDetail temp = new TblUpbrhFeeDetail();
				temp.setPk(pk);
				temp.setCardType(cardType);
				temp.setTxnNum(txnNum);
				temp.setFlag(new BigDecimal(flag));
				temp.setFeeValue(Double.parseDouble(feeValue));
				temp.setMaxFee(Double.parseDouble(maxFee));
				temp.setCrtOpr(oprId);
				temp.setUptOpr(oprId);
				tblUpbrhFeeDetailBO.add(temp);
				//tblUpbrhFeeDetailBO.update(temp);//-- 修改业务层调用的saveOrUpdate方法
			}
			writeSuccessMsg("修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			log("修改操作失败");
			return returnService(rspCode, e);
		}
		return null;
	}
	
	public String delete(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String discId =request.getParameter("discId");
		TblUpbrhFeeDetail info = tblUpbrhFeeDetailBO.get(discId);
		String res = tblUpbrhFeeDetailBO.delete(info);
		return res;
	}

	public List<TblUpbrhFeeDetail> buildDiscAlgo(){
		List<TblUpbrhFeeDetail> listHis = new ArrayList<TblUpbrhFeeDetail>();
		JSONBean jsonBean = new JSONBean();
		jsonBean.parseJSONArrayData(data);
		int len = jsonBean.getArray().size();
		for(int i = 0; i < len; i++) {
			jsonBean.setObject(jsonBean.getJSONDataAt(i));
			//卡类型
			String cardType = jsonBean.getJSONDataAt(i).getString("cardType");
			String txnNum = jsonBean.getJSONDataAt(i).getString("txnNum");
			String flag = jsonBean.getJSONDataAt(i).getString("flag");
			String feeValue = jsonBean.getJSONDataAt(i).getString("feeValue");
			String maxFee = jsonBean.getJSONDataAt(i).getString("maxFee");
			
			TblUpbrhFeeDetail temp = new TblUpbrhFeeDetail();
			temp.setCardType(cardType);
			temp.setTxnNum(txnNum);
			temp.setFlag(new BigDecimal(flag));
			temp.setFeeValue(Double.parseDouble(feeValue));
			temp.setMaxFee(Double.parseDouble(maxFee));
			listHis.add(temp);
		}
		return listHis;
	}

	protected void  writeErrorMsg(String msg) throws IOException {
		JSONBean jsonBean = new JSONBean();
		jsonBean.addJSONElement(Constants.SUCCESS_HEADER, false);
		jsonBean.addJSONElement(Constants.PROMPT_MSG, msg);
		writeMessage(jsonBean.toString());
	}
	
	protected void writeMessage(String message) throws IOException {
		PrintWriter printWriter = ServletActionContext.getResponse().getWriter();
		printWriter.write(message);
		printWriter.flush();
		printWriter.close();
	}
	
	protected void  writeSuccessMsg(String msg) throws IOException {
		JSONBean jsonBean = new JSONBean();
		jsonBean.addJSONElement(Constants.SUCCESS_HEADER, true);
		jsonBean.addJSONElement(Constants.PROMPT_MSG, msg);
		writeMessage(jsonBean.toString());
	}
	

	public String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	/*public TblUpbrhFeeDetailBO getTblUpbrhFeeDetailBO() {
		return tblUpbrhFeeDetailBO;
	}

	public void setTblUpbrhFeeDetailBO(TblUpbrhFeeDetailBO tblUpbrhFeeDetailBO) {
		this.tblUpbrhFeeDetailBO = tblUpbrhFeeDetailBO;
	}*/


	public TblUpbrhFeeDetail getUpbrhFeeDetail() {
		return upbrhFeeDetail;
	}

	public void setUpbrhFeeDetail(TblUpbrhFeeDetail upbrhFeeDetail) {
		this.upbrhFeeDetail = upbrhFeeDetail;
	}

}
