
package com.allinfinance.struts.settle.action;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts2.ServletActionContext;

import com.allinfinance.bo.settle.T80711BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.msg.Msg;
import com.allinfinance.common.msg.MsgEntity;
import com.allinfinance.dao.iface.settle.TblMchtErrAdjustDAO;
import com.allinfinance.po.settle.TblMchtErrAdjust;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.SysParamUtil;

public class T80711Action extends BaseAction{
	private TblMchtErrAdjustDAO tblMchtErrAdjustDAO=(TblMchtErrAdjustDAO) ContextUtil.getBean("TblMchtErrAdjustDAO"); 
	private T80711BO service=(T80711BO) ContextUtil.getBean("T80711BO");
	private TblMchtErrAdjust tblMchtErrAdjust;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String dir = SysParamUtil.getParam(
			SysParamConstants.TEMP_FILE_DISK).replace("\\", "/");
	@Override
	protected String subExecute() throws Exception {
		return null;
	} 
	/**
	 * 添加商户手工调整
	 * @return
	 * @throws IOException 
	 */
	public String add() throws IOException{
		UUID uuid=UUID.randomUUID();
		String id=uuid.toString();
		tblMchtErrAdjust.setId(id);
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		String oprId = operator.getOprId();
		String oprName = operator.getOprName();
		String currentDateTime = CommonFunction.getCurrentDateTime();
		tblMchtErrAdjust.setCrtOpr(oprId+"-"+oprName);
		tblMchtErrAdjust.setCrtTime(currentDateTime);
		tblMchtErrAdjust.setId(id);
		tblMchtErrAdjust.setPostStatus("0");//未入账
		tblMchtErrAdjust.setApproveStatus("0");//未审批
		try {
			tblMchtErrAdjustDAO.save(tblMchtErrAdjust);
			writeSuccessMsg("新增成功！");
			return null;
		} catch (Exception e) {
			log(e.getMessage());
			writeErrorMsg("新增失败！");
			return null;
		}
	}
	/**
	 * 修改商户手工调整
	 * @return
	 * @throws IOException 
	 */
	public String upd() throws IOException{
		String id=tblMchtErrAdjust.getId();
		TblMchtErrAdjust mchtErrAdjust = tblMchtErrAdjustDAO.get(id);
		if(mchtErrAdjust==null){
			writeSuccessMsg("该条记录不存在，不能修改！");
			return null;
		}
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		String oprId = operator.getOprId();
		String oprName = operator.getOprName();
		String currentDateTime = CommonFunction.getCurrentDateTime();
		String crtOprId=mchtErrAdjust.getCrtOpr().split("-")[0];
		if(!oprId.equals(crtOprId)){
			writeSuccessMsg("无权修改不是您本人创建的记录，请确认！");
			return null;
		}
		mchtErrAdjust.setTradeType(tblMchtErrAdjust.getTradeType());
		mchtErrAdjust.setCardNo(tblMchtErrAdjust.getCardNo());
		mchtErrAdjust.setAuthNo(tblMchtErrAdjust.getAuthNo());
		mchtErrAdjust.setTradeConsultNo(tblMchtErrAdjust.getTradeConsultNo());
		mchtErrAdjust.setMoney(tblMchtErrAdjust.getMoney());
		mchtErrAdjust.setReserver(tblMchtErrAdjust.getReserver());
		mchtErrAdjust.setUpdOpr(oprId+"-"+oprName);
		mchtErrAdjust.setUpdTime(currentDateTime);
		try {
			tblMchtErrAdjustDAO.update(mchtErrAdjust);
			writeSuccessMsg("修改成功！");
			return null;
		} catch (Exception e) {
			log(e.getMessage());
			writeErrorMsg("修改失败！");
			return null;
		}
	}
	/**
	 * 商户手工差错审核
	 * @return
	 * @throws IOException 
	 */
	public String check() throws IOException{
		try {
			operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
			String oprId = operator.getOprId();
			String oprName = operator.getOprName();
			String currentDateTime = CommonFunction.getCurrentDateTime();
			tblMchtErrAdjust.setApproveOpr(oprId+"-"+oprName);
			tblMchtErrAdjust.setApproveStatus("1");
			tblMchtErrAdjust.setApproveTime(currentDateTime);
			tblMchtErrAdjust.setPostStatus("1");
			tblMchtErrAdjust.setPostTime(currentDateTime);
			tblMchtErrAdjust.setUpdOpr(oprId+"-"+oprName);
			tblMchtErrAdjust.setUpdTime(currentDateTime);
			String result = service.check(tblMchtErrAdjust);
			writeSuccessMsg(result);
		} catch (Exception e) {
			log(e.getMessage());
			writeErrorMsg("审核失败，请稍后重试！");
		}
		return null;
	}
	/**
	 * 审核不通过
	 * @return
	 * @throws IOException 
	 */
	public String refuse() throws IOException{
		if(tblMchtErrAdjust==null||tblMchtErrAdjust.getId()==null){
			writeSuccessMsg("审核出错，请稍后重试！");
			return null;
		}
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		String oprId = operator.getOprId();
		String oprName = operator.getOprName();
		String currentDateTime = CommonFunction.getCurrentDateTime();
		TblMchtErrAdjust mchtErrAdjust = tblMchtErrAdjustDAO.get(tblMchtErrAdjust.getId());
		if(mchtErrAdjust==null){
			writeSuccessMsg("审核的内容不存在！");
			return null;
		}
		if(mchtErrAdjust.getCrtOpr().trim().split("-")[0].equals(oprId)){
			writeSuccessMsg("同一操作员不能审核！");
			return null;
		}
		if(!"0".equals(mchtErrAdjust.getApproveStatus())){
			return "该记录已经被审核过，请选择其他记录！";
		}
		mchtErrAdjust.setApproveStatus("2");//不通过
		mchtErrAdjust.setApproveAdvice(tblMchtErrAdjust.getApproveAdvice());
		mchtErrAdjust.setApproveOpr(oprId+"-"+oprName);
		mchtErrAdjust.setApproveTime(currentDateTime);
		mchtErrAdjust.setUpdOpr(oprId+"-"+oprName);
		mchtErrAdjust.setUpdTime(currentDateTime);
		tblMchtErrAdjustDAO.update(mchtErrAdjust);
		
		writeSuccessMsg("审核成功！");
		return null;
	}
	public String exportData() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		String mchtNo = request.getParameter("queryMchtNo");
		String cardNo = request.getParameter("cardNo");
		String authNo = request.getParameter("authNo");
		String tradeConsultNo = request.getParameter("tradeConsultNo");
		String tradeType = request.getParameter("tradeType");
		String approveStatus = request.getParameter("approveStatus");
		String postStatus = request.getParameter("postStatus");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		if(isNotEmpty(mchtNo)){
			whereSql.append(" AND " + "MCHT_NO= '" +mchtNo + "'");
		}
		if(isNotEmpty(cardNo)){
			whereSql.append(" AND " + "CARD_NO LIKE '%" +cardNo + "%'");
		}
		if(isNotEmpty(authNo)){
			whereSql.append(" AND " + "auth_No LIKE '%" +authNo + "%'");
		}
		if(isNotEmpty(tradeConsultNo)){
			whereSql.append(" AND " + "trade_Consult_No LIKE '%" +tradeConsultNo + "%'");
		}
		if(isNotEmpty(tradeType)){
			whereSql.append(" AND " + "trade_Type = '" +tradeType + "'");
		}
		if(isNotEmpty(approveStatus)){
			whereSql.append(" AND " + "aprove_Status = '" +approveStatus + "'");
		}
		if(isNotEmpty(postStatus)){
			whereSql.append(" AND " + "post_Status =  '" +postStatus + "'  ");
		}
		if(isNotEmpty(startDate)){
			whereSql.append(" AND " + "approve_Time >=  '" +startDate + "000000' ");
		}
		if(isNotEmpty(endDate)){
			whereSql.append(" AND " + "approve_Time <=  '" +endDate + "235959'  ");
		}

		String sql =   "SELECT UUID,MCHT_NO,CARD_NO,auth_No,trade_Consult_No,trade_Type,money,aprove_Status,aprove_Opr,approve_Time,approve_Advice,post_Time,post_Status,reserver,VC_TRAN_NO FROM TBL_MCHT_ERR_ADJUST" 
						+ whereSql.toString()+" order by aprove_Status asc,crt_time desc";

		String countSql = "SELECT count(1) FROM TBL_MCHT_ERR_ADJUST"	
							+ whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		@SuppressWarnings("unchecked")
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, 0, Integer.parseInt(count));
		try {

			WritableWorkbook wwb = null;
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
			String time = formatter.format(curDate);
			// 创建可写入的Excel工作簿
			String fileName = dir + File.separator + "商户差错手工调整信息报表" + time + ".xls";
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			} else {
				file.delete();
			}
			// 以fileName为文件名来创建一个Workbook
			wwb = Workbook.createWorkbook(file);
			// 创建工作表
			WritableSheet ws = wwb.createSheet("Sheet1", 0);
			ws.setColumnView(0, 20);
			ws.setColumnView(1, 20);
			ws.setColumnView(2, 20);
			ws.setColumnView(3, 20);
			ws.setColumnView(4, 20);
			ws.setColumnView(5, 20);
			ws.setColumnView(6, 20);
			ws.setColumnView(7, 20);
			ws.setColumnView(8, 20);
			ws.setColumnView(9, 20);
			ws.setColumnView(10, 20);
			ws.setColumnView(11, 20);
			ws.setColumnView(12, 20);
			ws.setColumnView(13, 20);
			ws.setName("商户差错手工调整信息");
			// 要插入到的Excel表格的行号，默认从0开始
			Label mchtNo_head = new Label(0, 0, "商户号");// 表示第
			Label cardNo_head = new Label(1, 0, "卡号");
			Label authNo_head = new Label(2, 0, "授权号");
			Label tradeConsultNo_head = new Label(3, 0, "交易参考号");
			Label tradeType_head = new Label(4, 0, "交易类型");
			Label money_head = new Label(5, 0, "金额");
			Label approveStatus_head = new Label(6, 0, "审批状态");
			Label approveOpr_head = new Label(7, 0, "审批人");
			Label approveTime_head = new Label(8, 0, "审批时间");
			Label approveAdvice_head = new Label(9, 0, "审批意见");
			Label postStatus_head = new Label(10, 0, "入账状态");
			Label postTime_head = new Label(11, 0, "入账时间");
			Label reserver_head = new Label(12, 0, "备注");
			Label vcTranNo_head = new Label(13, 0, "交易流水号");

			ws.addCell(mchtNo_head);
			ws.addCell(cardNo_head);
			ws.addCell(authNo_head);
			ws.addCell(tradeConsultNo_head);
			ws.addCell(tradeType_head);
			ws.addCell(money_head);
			ws.addCell(approveStatus_head);
			ws.addCell(approveOpr_head);
			ws.addCell(approveTime_head);
			ws.addCell(approveAdvice_head);
			ws.addCell(postStatus_head);
			ws.addCell(postTime_head);
			ws.addCell(reserver_head);
			ws.addCell(vcTranNo_head);
			

			for (int i = 0; i < dataList.size(); i++) {
				String tradeType_record=dataList.get(i)[5]==null?"":dataList.get(i)[5]+ "";
				tradeType_record=exchageTradeType(tradeType_record);
				String approveStatus_record=dataList.get(i)[7]==null?"":dataList.get(i)[7]+ "";
				approveStatus_record=exchageApproveSta(approveStatus_record);
				String postStatus_record=dataList.get(i)[12]==null?"":dataList.get(i)[12]+ "";
				postStatus_record=exchagePostSta(postStatus_record);
				Label mchtNo_i = new Label(0, i + 1, dataList.get(i)[1] + "");
				Label cardNo_i = new Label(1, i + 1, dataList.get(i)[2]==null?"":dataList.get(i)[2]+ "");
				Label authNo_i = new Label(2, i + 1, dataList.get(i)[3]==null?"":dataList.get(i)[3] + "");
				Label tradeConsultNo_i = new Label(3, i + 1, dataList.get(i)[4]==null?"":dataList.get(i)[4] + "");
				Label tradeType_i = new Label(4, i + 1,tradeType_record );
				Label money_i = new Label(5, i + 1, dataList.get(i)[6] + "");
				Label approveStatus_i = new Label(6, i + 1, approveStatus_record+"");
				Label approveOpr_i = new Label(7, i + 1, dataList.get(i)[8]==null?"":dataList.get(i)[8]+ "");
				Label approveTime_i = new Label(8, i + 1, dataList.get(i)[9]==null?"":dataList.get(i)[9] + "");
				Label approveAdvice_i = new Label(9, i + 1, dataList.get(i)[10]==null?"":dataList.get(i)[10] + "");
				Label postStatus_i = new Label(10, i + 1,postStatus_record );
				Label postTime_i = new Label(11, i + 1, dataList.get(i)[11]==null?"":dataList.get(i)[11] + "");
				Label reserver_i = new Label(12, i + 1, dataList.get(i)[13]==null?"":dataList.get(i)[13]+"");
				Label vcTranNo_i = new Label(13, i + 1, dataList.get(i)[14]==null?"":dataList.get(i)[14]+"");
				
				ws.addCell(mchtNo_i);
				ws.addCell(cardNo_i);
				ws.addCell(authNo_i);
				ws.addCell(tradeConsultNo_i);
				ws.addCell(tradeType_i);
				ws.addCell(money_i);
				ws.addCell(approveStatus_i);
				ws.addCell(approveOpr_i);
				ws.addCell(approveTime_i);
				ws.addCell(approveAdvice_i);
				ws.addCell(postStatus_i);
				ws.addCell(postTime_i);
				ws.addCell(reserver_i);
				ws.addCell(vcTranNo_i);
			
			}
			// 写进文档
			wwb.write();
			// 关闭Excel工作簿对象
			wwb.close();
			writeUsefullMsg(fileName);
			writeSuccessMsg("数据下载成功.");
		
		} catch (Exception e) {
			log(e.getMessage());
			writeErrorMsg("数据下载失败,请稍后重试！");
		}
		
		return null;
	}
	private String exchageTradeType(String val){
		if("00".equals(val)){
			return "误划款正向(+)";
		}else if("01".equals(val)){
			return "误划款反向(-)";
		}else if("02".equals(val)){
			return "退货(-)";
		}else if("03".equals(val)){
			return "商户冻结";
		}else if("04".equals(val)){
			return "商户资金冻结";
		}else if("05".equals(val)){
			return "商户解冻";
		}else if("06".equals(val)){
			return "商户资金解冻";
		}else return "";
	}
	private String exchageApproveSta(String val){
		if("0".equals(val)){
			return "未审批";
		}else if("1".equals(val)){
			return "审批通过";
		}else if("2".equals(val)){
			return "审批未通过";
		}else return "";
	}
	private String exchagePostSta(String val){
		if("0".equals(val)){
			return "未入账";
		}else if("1".equals(val)){
			return "已入账";
		}else return "";
	}
	
	protected void writeUsefullMsg(String msg) throws IOException {
		jsonBean.addJSONElement(Constants.SUCCESS_HEADER, true);
		jsonBean.addJSONElement(Constants.PROMPT_MSG, msg);
		PrintWriter printWriter = ServletActionContext.getResponse()
				.getWriter();
		printWriter.write(jsonBean.toString());
		printWriter.flush();
		printWriter.close();
	}
	public TblMchtErrAdjust getTblMchtErrAdjust() {
		return tblMchtErrAdjust;
	}
	public void setTblMchtErrAdjust(TblMchtErrAdjust tblMchtErrAdjust) {
		this.tblMchtErrAdjust = tblMchtErrAdjust;
	}
}
