/*
 * 业务性质维护
 * zhaofachun
 * 2015-10-27
 */
package com.allinfinance.struts.route.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.allinfinance.bo.route.TblRouteUpbrhBO;
import com.allinfinance.common.StringUtil;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.iface.route.TblRouteUpbrhDAO;
import com.allinfinance.po.route.TblRouteUpbrh;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T110131Action extends BaseAction{

	private TblRouteUpbrh tblRouteUpbrh;
	private TblRouteUpbrhBO tblRouteUpbrhBO = (TblRouteUpbrhBO)ContextUtil.getBean("TblRouteUpbrhBO");
	private String brhId;
	private String name;
	private String brhDsp;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	public void setTblRouteUpbrh(TblRouteUpbrh tblRouteUpbrh){
		this.tblRouteUpbrh = tblRouteUpbrh;
	}
	public TblRouteUpbrh getTblRouteUpbrh(){
		return this.tblRouteUpbrh;
	}
	public void setBrhId(String brhId){
		this.brhId = brhId;
	}
	public String getBrhId(){
		return this.brhId;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	public void setBrhDesc(String brhDsp){
		this.brhDsp = brhDsp;
	}
	public String getBrhDesc(){
		return this.brhDsp;
	}
	@Override
	protected String subExecute() throws Exception {
		rspCode = paramValid();
		if("00".equals(rspCode)){
			if("start".equals(getMethod())) {	 //启用业务性质		
				rspCode = updateStatus("0");	
			} else if("stop".equals(getMethod())) { //停用业务性质
				rspCode = updateStatus("1");
			}else if("add".equals(getMethod())) {
				rspCode = add();
			}else if("update".equals(getMethod())) {
				rspCode = update();
			}else if("delete".equals(getMethod())) {
				rspCode = delete();
			}
		}
			
		return rspCode;
	}

	
	private String updateStatus(String status) throws IOException{
		String retCode = this.tblRouteUpbrhBO.updateStatus(status,this.brhId);
		if("00".equals(retCode)){
			writeSuccessMsg("业务性质状态更新成功！");
		}else{
			this.writeErrorMsg("业务性质状态更新失败！");
		}
		return retCode;
	}
	
	private String add() throws IOException{
		this.tblRouteUpbrh = new TblRouteUpbrh();
		this.tblRouteUpbrh.setBrhId(this.getBrhId());
		this.tblRouteUpbrh.setName(this.getName());
		this.tblRouteUpbrh.setBrhDsp(this.getBrhDesc());
		String retCode = this.tblRouteUpbrhBO.addCharacter(tblRouteUpbrh);
		if("00".equals(retCode)){
			this.writeSuccessMsg("业务性质保存成功！");
		}else if("01".equals(retCode)){
			this.writeErrorMsg("该性质名称已被使用，请使用其他的名称！");
		}else if("02".equals(retCode)){
			this.writeErrorMsg("保存失败！获取取业务性质基本信息失败！");
		}else if("03".equals(retCode)){
			this.writeErrorMsg("保存失败！出现异常！");
		}
		return retCode;
	}
	
	private String delete() throws IOException{
		this.tblRouteUpbrh = new TblRouteUpbrh();
		this.tblRouteUpbrh.setBrhId(this.getBrhId());
		String retCode = this.tblRouteUpbrhBO.deleteCharacter(tblRouteUpbrh);
		if("00".equals(retCode)){
			writeSuccessMsg("业务性质删除成功！");
		}else{
			this.writeErrorMsg("业务性质删除失败！");
		}
		return retCode;
	}
	
	private String update() throws IOException{
		this.tblRouteUpbrh = new TblRouteUpbrh();
		this.tblRouteUpbrh.setBrhId(this.getBrhId());
		this.tblRouteUpbrh.setName(this.getName());
		this.tblRouteUpbrh.setBrhDsp(this.getBrhDesc());
		String retCode = this.tblRouteUpbrhBO.updateCharacter(tblRouteUpbrh);
		if("00".equals(retCode)){
			writeSuccessMsg("业务性质修改成功！");
		}else if("01".equals(retCode)){
			this.writeErrorMsg("该性质名称已被使用，请使用其他的名称！");
		}else if("02".equals(retCode)){
			this.writeErrorMsg("保存失败！获取取业务性质基本信息失败！");
		}else if("03".equals(retCode)){
			this.writeErrorMsg("保存失败！出现异常！");
		}
		return retCode;
	}
	
	private String paramValid() throws IOException{
		String name = this.getName();
		String desc = this.getBrhDesc();
		if("add".equals(this.getMethod()) || "update".equals(this.getMethod())){
			if(StringUtil.isNotEmpty(name)){
				int len = name.getBytes("gbk").length;
				if(len > 40){
					this.writeErrorMsg("业务名称：长度大于40字节（一个汉字为两个字节）！");
					return "02";
				}
				//this.name = name.replaceAll("'", "''");
			}else{
				this.writeErrorMsg("业务名称：不可为空！");
				return "01";
			}
			if(StringUtil.isNotEmpty(desc)){
				int len = desc.getBytes("gbk").length;
				if(len > 120){
					this.writeErrorMsg("业务描述：长度大于120字节（一个汉字为两个字节）！");
					return "02";
				}
				//this.brhDsp = desc.replaceAll("'", "''");
			}else{
				this.writeErrorMsg("业务描述：不可为空！");
				return "01";
			}
			
		}
		return "00";
	}
}



