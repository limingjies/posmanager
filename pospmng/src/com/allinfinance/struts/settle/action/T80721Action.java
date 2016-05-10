
package com.allinfinance.struts.settle.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts2.ServletActionContext;

import com.allinfinance.bo.settle.T80721BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.po.settle.TblBrhErrAdjust;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.SysParamUtil;

public class T80721Action extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private T80721BO service=(T80721BO) ContextUtil.getBean("T80721BO");
	private TblBrhErrAdjust tblBrhErrAdjust;
	
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
		tblBrhErrAdjust.setId(id);
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		String oprId = operator.getOprId();
		String currentDateTime = CommonFunction.getCurrentDateTime();
		tblBrhErrAdjust.setCrtOpr(oprId);
		tblBrhErrAdjust.setCrtTime(currentDateTime);
		tblBrhErrAdjust.setId(id);
		tblBrhErrAdjust.setPostStatus("0");//未入账
		tblBrhErrAdjust.setApproveStatus("0");//未审批
		try {
			service.save(tblBrhErrAdjust);
			writeSuccessMsg("新增成功！");
			return null;
		} catch (Exception e) {
			log(e.getMessage());
			writeErrorMsg("新增失败！");
			return null;
		}
	}
	
	/**
	 * 添加商户手工调整
	 * @return
	 * @throws IOException 
	 */
	public String edit() throws IOException{
		TblBrhErrAdjust brhErrAdjust = service.get(tblBrhErrAdjust.getId());
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		String oprId = operator.getOprId();
		String currentDateTime = CommonFunction.getCurrentDateTime();
		
		if (!oprId.equals(brhErrAdjust.getCrtOpr())) {
			writeSuccessMsg("无权修改不是您本人创建的记录，请确认！");
			return null;
		}
		
		brhErrAdjust.setTradeType(tblBrhErrAdjust.getTradeType());
		brhErrAdjust.setMoney(tblBrhErrAdjust.getMoney());
		brhErrAdjust.setReserver(tblBrhErrAdjust.getReserver());
		brhErrAdjust.setUpdOpr(oprId);
		brhErrAdjust.setUpdTime(currentDateTime);
		try {
			service.update(brhErrAdjust);
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
	public String accept() throws IOException{
		try {
			operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
			String oprId = operator.getOprId();
			String currentDateTime = CommonFunction.getCurrentDateTime();
			TblBrhErrAdjust brhErrAdjust = service.get(tblBrhErrAdjust.getId());
			
			if (!"0".equals(brhErrAdjust.getApproveStatus())) {
				writeErrorMsg("本条记录已被其他操作员审核过，请刷新页面后重试！");
				return null;
			}
			
			brhErrAdjust.setApproveOpr(oprId);
			brhErrAdjust.setApproveStatus("1");
			brhErrAdjust.setApproveTime(currentDateTime);
			brhErrAdjust.setApproveAdvice(tblBrhErrAdjust.getApproveAdvice());
			brhErrAdjust.setPostStatus("1");
			brhErrAdjust.setPostTime(currentDateTime);
			brhErrAdjust.setUpdOpr(oprId);
			brhErrAdjust.setUpdTime(currentDateTime);
			// 审核通过
			String result = service.accept(brhErrAdjust);
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
		if(tblBrhErrAdjust==null||tblBrhErrAdjust.getId()==null){
			writeErrorMsg("审核出错，请稍后重试！");
			return null;
		}
		operator = (Operator) getSessionAttribute(Constants.OPERATOR_INFO);
		String oprId = operator.getOprId();
		String currentDateTime = CommonFunction.getCurrentDateTime();
		TblBrhErrAdjust brhErrAdjust = service.get(tblBrhErrAdjust.getId());
		
		if (!"0".equals(brhErrAdjust.getApproveStatus())) {
			writeErrorMsg("本条记录已被其他操作员审核过，请刷新页面后重试！");
			return null;
		}
		
		brhErrAdjust.setApproveStatus("2");//不通过
		brhErrAdjust.setApproveAdvice(tblBrhErrAdjust.getApproveAdvice());
		brhErrAdjust.setApproveOpr(oprId);
		brhErrAdjust.setApproveTime(currentDateTime);
		brhErrAdjust.setUpdOpr(oprId);
		brhErrAdjust.setUpdTime(currentDateTime);
		// 审核不通过（拒绝）
		String result = service.refuse(brhErrAdjust);
		writeSuccessMsg(result);
		return null;
	}
	/**
	 * 导出Excel
	 * @return
	 * @throws IOException 
	 */
	public String exportData() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String querybrhNo = request.getParameter("querybrhNo");
		String queryTradeType = request.getParameter("queryTradeType");
		String queryAproveStatus = request.getParameter("queryAproveStatus");
		String queryPostStatus = request.getParameter("queryPostStatus");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		StringBuffer whereSql = new StringBuffer(" WHERE 1=1 ");
		
		if (isNotEmpty(querybrhNo)) {
			whereSql.append(
					" AND brh_no = ")
					.append("'")
					.append(querybrhNo)
					.append("'");
		}
		if (isNotEmpty(queryTradeType)) {
			whereSql.append(
					" AND trade_type=")
					.append("'")
					.append(queryTradeType)
					.append("'");
		}
		
		if (isNotEmpty(queryAproveStatus)) {
			whereSql.append(
					" AND aprove_status=")
					.append("'")
					.append(queryAproveStatus)
					.append("'");
		}
		
		if (isNotEmpty(queryPostStatus)) {
			whereSql.append(
					" AND post_status=")
					.append("'")
					.append(queryPostStatus)
					.append("'");
		}
		
		if (isNotEmpty(startDate)) {
			whereSql.append(" AND approve_time ").append(" >= ").append("'")
					.append(startDate).append("'");
		}
		if (isNotEmpty(endDate)) {
			whereSql.append(" AND approve_time").append(" <= ").append("'")
					.append(endDate).append("'");
		}
		
		String sql = "SELECT b.CREATE_NEW_NO||'-'||BRH_NAME as brh_no,trade_type,to_char(money,'9999999990.00'),nvl(aprove_status,' '),nvl(aprove_opr,' '),nvl(approve_time,' '),nvl(approve_advice,' '),nvl(post_status,' '),nvl(post_time,' '),nvl(vc_tran_no,' '),nvl(reserver,' ') FROM TBL_BRH_ERR_ADJUST a";
		sql += " LEFT JOIN TBL_BRH_INFO b ON b.brh_id = a.brh_no";
		sql = sql + whereSql.toString();
		sql = sql + " order by aprove_status,post_time desc,approve_time desc";

		String countSql = "SELECT count(1) FROM TBL_BRH_ERR_ADJUST"	
							+ whereSql.toString();
		String count = CommonFunction.getCommQueryDAO().findCountBySQLQuery(
				countSql);
		@SuppressWarnings("unchecked")
		List<Object[]> dataList = CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql, 0, Integer.parseInt(count));
		
		DecimalFormat df = new DecimalFormat("0.00");
		try {

			WritableWorkbook wwb = null;
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
			String time = formatter.format(curDate);
			// 创建可写入的Excel工作簿
			String fileName = dir + File.separator + "合作伙伴差错手工调整信息报表" + time + ".xls";
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
			ws.setName("合作伙伴差错手工调整信息");
			// 要插入到的Excel表格的行号，默认从0开始
			Label mchtNo_head = new Label(0, 0, "合作伙伴号");// 表示第
			Label tradeType_head = new Label(1, 0, "交易类型");
			Label money_head = new Label(2, 0, "金额");
			Label approveStatus_head = new Label(3, 0, "审批状态");
			Label approveOpr_head = new Label(4, 0, "审批人");
			Label approveTime_head = new Label(5, 0, "审批时间");
			Label approveAdvice_head = new Label(6, 0, "审批意见");
			Label postStatus_head = new Label(7, 0, "入账状态");
			Label postTime_head = new Label(8, 0, "入账时间");
			Label vcTranNo_head = new Label(9, 0, "交易流水号");
			Label reserver_head = new Label(10, 0, "备注");

			ws.addCell(mchtNo_head);
			ws.addCell(tradeType_head);
			ws.addCell(money_head);
			ws.addCell(approveStatus_head);
			ws.addCell(approveOpr_head);
			ws.addCell(approveTime_head);
			ws.addCell(approveAdvice_head);
			ws.addCell(postStatus_head);
			ws.addCell(postTime_head);
			ws.addCell(vcTranNo_head);
			ws.addCell(reserver_head);
			

			for (int i = 0; i < dataList.size(); i++) {
				String tradeType = getTradeType(dataList.get(i)[1]+ "");
				String approveStatus = getAproveStatus(dataList.get(i)[3]+ "");
				String postStatus = getPostStatus(dataList.get(i)[7]+ "");
				Label brhNo_i = new Label(0, i + 1, dataList.get(i)[0] + "");
				Label tradeType_i = new Label(1, i + 1,tradeType );
				Label money_i = new Label(2, i + 1, df.format(Double.parseDouble(dataList.get(i)[2].toString())) + "");
				Label approveStatus_i = new Label(3, i + 1, approveStatus+"");
				Label approveOpr_i = new Label(4, i + 1, dataList.get(i)[4]+ "");
				Label approveTime_i = new Label(5, i + 1, dataList.get(i)[5] + "");
				Label approveAdvice_i = new Label(6, i + 1, dataList.get(i)[6] + "");
				Label postStatus_i = new Label(7, i + 1,postStatus );
				Label postTime_i = new Label(8, i + 1, dataList.get(i)[8] + "");
				Label vcTranNo_i = new Label(9, i + 1, dataList.get(i)[9] + "");
				Label reserver_i = new Label(10, i + 1, dataList.get(i)[10]+"");
				
				ws.addCell(brhNo_i);
				ws.addCell(tradeType_i);
				ws.addCell(money_i);
				ws.addCell(approveStatus_i);
				ws.addCell(approveOpr_i);
				ws.addCell(approveTime_i);
				ws.addCell(approveAdvice_i);
				ws.addCell(postStatus_i);
				ws.addCell(postTime_i);
				ws.addCell(vcTranNo_i);
				ws.addCell(reserver_i);
			
			}
			// 写进文档
			wwb.write();
			// 关闭Excel工作簿对象
			wwb.close();
			writeUsefullMsg(fileName);
			writeSuccessMsg("数据下载成功.");
		
		} catch (Exception e) {
			writeErrorMsg("数据下载失败");
		}
		
		return null;
	}
	
	private String getTradeType(String val) {
		if ("00".equals(val))
			return "分润内扣(-)";
		else if ("01".equals(val))
			return "营销费用扣除(-)";
		else if ("02".equals(val))
			return "调退单损失(-)";
		else if ("03".equals(val))
			return "追偿损失(-)";
		else if ("04".equals(val))
			return "误划款正(+)";
		else if ("05".equals(val))
			return "误划款负(-)";
		return val;
	}
	
	private String getAproveStatus(String val) {
		if ("0".equals(val))
			return "未审批";
		else if ("1".equals(val))
			return "审批通过";
		else if ("2".equals(val))
			return "审批未通过";
		return val;
	}
  
	private String getPostStatus(String val) {
		if ("0".equals(val))
			return "未入账";
		else if ("1".equals(val))
			return "已入账";
		return val;
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

	public TblBrhErrAdjust getTblBrhErrAdjust() {
		return tblBrhErrAdjust;
	}

	public void setTblBrhErrAdjust(TblBrhErrAdjust tblBrhErrAdjust) {
		this.tblBrhErrAdjust = tblBrhErrAdjust;
	}
}
