package com.allinfinance.struts.route.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.HibernateException;

import com.allinfinance.bo.route.T110301BO;
import com.allinfinance.bo.route.T110331BO;
import com.allinfinance.bo.route.TblRouteMchtgBO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfDAO;
import com.allinfinance.dao.iface.route.TblRouteMchtgDetailDAO;
import com.allinfinance.dao.iface.route.TblRouteRuleMapDAO;
import com.allinfinance.dao.iface.route.TblRouteRuleMapHistDAO;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.route.TblRouteMchtg;
import com.allinfinance.po.route.TblRouteMchtgDetail;
import com.allinfinance.po.route.TblRouteMchtgDetailPk;
import com.allinfinance.po.route.TblRouteRuleMap;
import com.allinfinance.po.route.TblRouteRuleMapHist;
import com.allinfinance.po.route.TblRouteRuleMapHistPk;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

@SuppressWarnings("serial")
public class T110301Action extends BaseAction{
	// 文件集合
	private List<File> files;
			// 文件名称集合
	private List<String> filesFileName;
	
	/**
	 * @return the files
	 */
	public List<File> getFiles() {
		return files;
	}
	/**
	 * @param files the files to set
	 */
	public void setFiles(List<File> files) {
		this.files = files;
	}
	/**
	 * @return the filesFileName
	 */
	public List<String> getFilesFileName() {
		return filesFileName;
	}
	/**
	 * @param filesFileName the filesFileName to set
	 */
	public void setFilesFileName(List<String> filesFileName) {
		this.filesFileName = filesFileName;
	}
	private Operator operator = (Operator) ServletActionContext.getRequest()
			.getSession().getAttribute(Constants.OPERATOR_INFO);
	private T110301BO t110301BO=(T110301BO) ContextUtil.getBean("T110301BO");
	private TblRouteRuleMapHistDAO tblRouteRuleMapHistDAO=(TblRouteRuleMapHistDAO) ContextUtil.getBean("TblRouteRuleMapHistDAO");
	private TblRouteMchtgBO tblRouteMchtgBO=(TblRouteMchtgBO) ContextUtil.getBean("TblRouteMchtgBO");
	private TblRouteMchtgDetailDAO tblRouteMchtgDetailDAO=(TblRouteMchtgDetailDAO) ContextUtil.getBean("TblRouteMchtgDetailDAO");
	private ITblMchtBaseInfDAO tblMchtBaseInfDAO=(ITblMchtBaseInfDAO) ContextUtil.getBean("tblMchtBaseInfDAO");
	private TblRouteMchtg routeMchtg;
	private TblRouteRuleMapDAO tblRouteRuleMapDAO=(TblRouteRuleMapDAO) ContextUtil.getBean("TblRouteRuleMapDAO");
	@Override
	protected String subExecute() throws Exception {
		return null;
	}
	/**
	 * 增加商户组
	 * @return
	 * @throws IOException 
	 */
	public String add() throws IOException{
		try {
			//生成主键，6位number
			int id=100000;
			int max=tblRouteMchtgBO.getMax();
			if(max!=0){
				id=max+1;
			}else if(max==999999){
				writeErrorMsg("商户组已满！");
				return null;
			}
			routeMchtg.setMchtGid(id);
			Date date=new Date();
			DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
			String crtTime=format.format(date);
			routeMchtg.setCrtTime(crtTime);
			routeMchtg.setUptTime(crtTime);
			routeMchtg.setCrtOpr(operator.getOprId());
			routeMchtg.setUptOpr(operator.getOprId());
			tblRouteMchtgBO.add(routeMchtg);
			writeSuccessMsg("商户组添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			writeErrorMsg("商户组添加失败！");
		}
		return null;
	}
	public String update() throws IOException{
		try {
			tblRouteMchtgBO.update(routeMchtg);
			writeSuccessMsg("商户组更新成功！");
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			writeErrorMsg("商户组更新失败！");;
		}
		return null;
	}
	public String delete() throws IOException{
		try {
			String mcht = tblRouteMchtgBO.getMcht(routeMchtg.getMchtGid());
			if(mcht!=null){
				writeErrorMsg("商户组不能删除，请先将商户组中的商户转出！");
				return null;
			}
			tblRouteMchtgBO.delete(routeMchtg);
			writeSuccessMsg("商户组删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			writeErrorMsg("商户组删除失败！");;
		}
		return null;
	}
	public String addMchtToGroup() throws IOException{
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			t110301BO.addMchtToGroup(request);
			writeSuccessMsg("添加商户成功！");
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			writeErrorMsg("添加商户失败！");
		} 
		return null;
	}
	public String deleteMchtFromGroup() throws IOException{
		try {
			String mchtId=ServletActionContext.getRequest().getParameter("mchtId");
			String mchtGid=ServletActionContext.getRequest().getParameter("mchtGid");
			int mchtGNo = Integer.parseInt(mchtGid);
			TblRouteMchtgDetailPk pk=new TblRouteMchtgDetailPk(mchtId, mchtGNo);
			tblRouteMchtgDetailDAO.delete(pk);
			writeSuccessMsg("删除商户成功！");
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			writeErrorMsg("删除商户失败！");
		}
		return null;
	}
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public String bacthAddMchtUpbrh() throws IOException{
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			TblRouteRuleMap routeRuleMap;
			String mchtIds = request.getParameter("mchtIds");
			String mchtUpbrhIds = request.getParameter("mchtUpbrhIds");
			String newGid = request.getParameter("newGid");
			String oldGid = request.getParameter("oldGid");
			String propertyIds = request.getParameter("propertyIds");
			String[] mchtIdStrings = mchtIds.split(",");
			String[] mchtUpbrhIdStrings = mchtUpbrhIds.split(",");
			String[] propertyIdStrings = propertyIds.split(",");
			String[] termIds = request.getParameter("termIds").split(",");
			String mchtId="";
			String mchtUpbrhId="";
			String propertyId="";
			String termId="";
			String success="转入/转出成功的商户：<div/>";
			String failure="转入/转出失败的商户：<div/>";
			int oldMchtgId=0;
			if(oldGid!=null&&oldGid!=""){
				oldMchtgId = Integer.parseInt(oldGid);
			}
			int newMchtgId = Integer.parseInt(newGid);
			
			for (int i = 0; i < mchtUpbrhIdStrings.length; i++) {
				
				mchtUpbrhId=mchtUpbrhIdStrings[i];
				propertyId=propertyIdStrings[i];
				termId=termIds[i];
					mchtId=mchtIdStrings[i];
					//查看是否已经存在
					try {
						
						String result = t110301BO.bacthAddMchtUpbrh(i,request, newMchtgId, oldGid, oldMchtgId, mchtId, mchtUpbrhId, propertyId,termId);
						if(result.equals(Constants.FAILURE_CODE)){
							writeErrorMsg("映射关系已满！");
							return null;
						}
						//成功
	                		success+="<font color=green>商户号"+mchtId+"-渠道商户号："+mchtUpbrhId+"-性质："+propertyId+"</font></div>";
					} catch (Exception e) {
						e.printStackTrace();
						log(e.getMessage());
						//失败
						failure+="<font color=red>商户号"+mchtId+"-渠道商户号："+mchtUpbrhId+"-性质："+propertyId+"</font></div>";
					}
			}
			writeSuccessMsg(success+failure);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			writeErrorMsg("添加性质失败！");
		}
		return null;
	}
	/**
	 * 根据上传文件转入商户
	 * @return
	 * @throws IOException 
	 */
	public String upload() throws IOException{
		InputStreamReader fr = null;  
	    BufferedReader br = null;  
	    FileInputStream fis = null;
	    if(files!=null&&files.size()!=0&&files.get(0)!=null){
	    	List<String> list=new ArrayList<String>();
	    	try {  
	    		fis = new FileInputStream(files.get(0));
	    		fr = new InputStreamReader(fis);  
	    		br = new BufferedReader(fr);  
	 	       String rec = null;  
	 	           while ((rec = br.readLine()) != null) {  
	 	               String mchtId = rec;  
	 	              list.add(mchtId);
	 	           }  
	 	       } catch (IOException e) { 
	 	    	   
	 	       } finally {  
	 	           try {  
	 	        	   if (br != null)  
	 	        		   br.close();  
	 	               if (fr != null)  
	 	                   fr.close();  
	 	              if (fis != null)  
	 	            	 fis.close();  
	 	           } catch (IOException ex) {  
	 	        	   ex.printStackTrace();
	 	           }  
	 	       }
	    	StringBuffer sb=new StringBuffer("");
			for (int i = 0; i < list.size(); i++) {
				if (list.size() == 1||i==list.size()-1) {
					sb.append(list.get(i));
				}else{
					sb.append(list.get(i)+",");
				}
			}
			String mchtIds=sb.toString();
			writeSuccessMsg(mchtIds);
		}
	    	
	
		return  null;
	}
	
	public TblRouteMchtg getRouteMchtg() {
		return routeMchtg;
	}
	public void setRouteMchtg(TblRouteMchtg routeMchtg) {
		this.routeMchtg = routeMchtg;
	}
}
