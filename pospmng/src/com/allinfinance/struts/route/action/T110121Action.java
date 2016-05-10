package com.allinfinance.struts.route.action;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.allinfinance.bo.route.TblRouteUpbrh2BO;
import com.allinfinance.bo.route.TblUpbrhFeeBO;
import com.allinfinance.common.Constants;
import com.allinfinance.po.route.TblRouteUpbrh2;
import com.allinfinance.po.route.TblUpbrhFee;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T110121Action extends BaseAction{
	private TblUpbrhFeeBO tblUpbrhFeeBO=(TblUpbrhFeeBO) ContextUtil.getBean("TblUpbrhFeeBO");
	private TblRouteUpbrh2BO tblRouteUpbrh2BO=(TblRouteUpbrh2BO) ContextUtil.getBean("TblRouteUpbrh2BO");
	private TblUpbrhFee upbrhFee;
	@Override
	protected String subExecute() throws Exception {
		return null;
	}

	public String add(){
		try {
			tblUpbrhFeeBO.add(upbrhFee);
		} catch (Exception e) {
			e.printStackTrace();
			log("操作员编号：" + operator.getOprId()+ "新增" + getMethod() + "失败。 原因："+e.getMessage());
		}
		return null;
	}
	
	public String update() throws IOException{
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String discId =request.getParameter("discId");
			TblUpbrhFee info = tblUpbrhFeeBO.get(discId);
			//如果状态为1--停用，则改为0启用
			if ("1".equals(info.getStatus())) {
				info.setStatus("0");
				tblUpbrhFeeBO.update(info);
				//如果状态为0--启用，则改为1停用
			}else if ("0".equals(info.getStatus())) {
				String res = tblUpbrhFeeBO.findConnect(info);
				if (Constants.SUCCESS_CODE.equals(res)) {
					info.setStatus("1");
					tblUpbrhFeeBO.update(info);
					writeMessage("{msg:'00'}");
				}else {
					writeMessage("{msg:'failure'}");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log("操作员编号：" + operator.getOprId()+ "更新" + getMethod() + "失败。 原因："+e.getMessage());
			writeErrorMsg("更新失败！");
		}
		return null;
	}
	
	public String delete() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		String discId =request.getParameter("discId");
		TblUpbrhFee info = tblUpbrhFeeBO.get(discId);
		String brhId = info.getBrhId2();
		
		//若返回的res为-1，则“不可删除”；否则可以删除
		String res = tblUpbrhFeeBO.delete(info);
		@SuppressWarnings("rawtypes")
		List list = tblUpbrhFeeBO.findUpbrhFeeByBrhId(brhId);
		//若没有档位数据对应该渠道，则修改渠道的配置状态为0--未配置
		if (list.size()<=0) {
			TblRouteUpbrh2 routeUpbrh2 = tblRouteUpbrh2BO.get(brhId);
			//若为，则修改为1已配置
			routeUpbrh2.setIsfee("0");//设置为0--未配置
			tblRouteUpbrh2BO.update(routeUpbrh2);
		}
		if (Constants.FAILURE_CODE.equals(res)) {
			writeMessage("{msg:'failure'}");
		}else {
			writeMessage("{msg:'00'}");
		}
		return null;
	}
	

	/**
	 * 成本扣率配置
	 */
	public String config(){
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String discId =request.getParameter("discNm_hidden");
			String brhId =request.getParameter("brhId");
			/*String channelNm =request.getParameter("channelNm");
			String businessNm =request.getParameter("businessNm");
			String discNm =request.getParameter("discNm");*/
			TblUpbrhFee info = tblUpbrhFeeBO.get(discId);
			info.setBrhId2(brhId);
			info.setStatus("0");//默认为0--启用
			tblUpbrhFeeBO.update(info);
			
			//配置完成后，修改 Tbl_Route_Upbrh2(业务表)的isfee字段值为1--已配置明细扣率
			TblRouteUpbrh2 routeUpbrh2 = tblRouteUpbrh2BO.get(brhId);
			//若为0--未配置，则修改为1已配置
			if ("0".equals(routeUpbrh2.getIsfee())) {
				routeUpbrh2.setIsfee("1");
				tblRouteUpbrh2BO.update(routeUpbrh2);
			}
			writeSuccessMsg("配置成功！");
		} catch (Exception e) {
			e.printStackTrace();
			log("操作员编号：" + operator.getOprId()+ "操作成本扣率配置" + getMethod() + "导失败。 原因："+e.getMessage());
		}
		return null;
	}
	

	/**
	 * 查询渠道的终端秘钥类型
	 * @return
	 * @throws IOException
	 */
	public String queryEncType() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String brhId2 = request.getParameter("brhId2");
		TblRouteUpbrh2 routeUpbrh2 = tblRouteUpbrh2BO.get(brhId2);
		String encType = routeUpbrh2.getEncType();
		if ("0".equals(encType)) {
			//writeSuccessMsg("渠道秘钥类型为一机一密");
			writeMessage("{msg:'false'}");
		}else {
			//writeSuccessMsg("渠道秘钥类型不是一机一密");
			writeMessage("{msg:'true'}");
		}
		return null;
	}

	public TblUpbrhFee getRouteMchtg() {
		return upbrhFee;
	}
	public void setRouteMchtg(TblUpbrhFee routeMchtg) {
		this.upbrhFee = routeMchtg;
	}
}
