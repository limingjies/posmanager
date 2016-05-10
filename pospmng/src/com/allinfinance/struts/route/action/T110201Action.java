package com.allinfinance.struts.route.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import com.allinfinance.bo.route.TblRouteRuleMapBO;
import com.allinfinance.bo.route.TblUpbrhMchtBO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.po.route.TblUpbrhMcht;
import com.allinfinance.po.route.TblUpbrhMchtPk;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.system.util.SysParamUtil;

@SuppressWarnings("serial")
public class T110201Action extends BaseAction {
	private TblUpbrhMchtBO tblUpbrhMchtBO = (TblUpbrhMchtBO) ContextUtil.getBean("TblUpbrhMchtBO");
	private TblRouteRuleMapBO tblRouteRuleMapBO = (TblRouteRuleMapBO) ContextUtil.getBean("TblRouteRuleMapBO");
	private TblUpbrhMcht upbrhMcht;
	public String dir = SysParamUtil.getParam(
			SysParamConstants.FILE_UPLOAD_DISK).replace("\\", "/");// 出款文件存放目录
	// 文件集合
	private List<File> files;

	@Override
	protected String subExecute() throws Exception {
		return null;
	}

	public String add() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String mchtIdUp = request.getParameter("mchtIdUp");// 渠道商户号
			String mchtNameUp = request.getParameter("mchtNameUp");// 渠道商户名称
			String termIdUp = request.getParameter("termIdUp");// 渠道终端号
			String area = request.getParameter("area").substring(0, 4);// 所属地区
			String brhId3 = request.getParameter("brhId3");// 性质
			String zmk = request.getParameter("zmk");// 终端主密钥
			String discId = request.getParameter("discId");//discId 成本扣率
			String mchtId = request.getParameter("mchtId");// 合规商户
			String mchtDsp = request.getParameter("mchtDsp");// 备注
			String misc1 = request.getParameter("misc1");// 特殊计费

			TblUpbrhMchtPk pk = new TblUpbrhMchtPk();
			pk.setMchtIdUp(mchtIdUp);
			pk.setTermIdUp(termIdUp);
			pk.setBrhId3(brhId3);

			TblUpbrhMcht upbrhMcht = new TblUpbrhMcht();
			upbrhMcht.setPk(pk);
			upbrhMcht.setMchtNameUp(mchtNameUp);
			upbrhMcht.setArea(area);
			upbrhMcht.setZmk(zmk);
			upbrhMcht.setDiscId(discId);
			upbrhMcht.setMchtId(mchtId);
			upbrhMcht.setMchtDsp(mchtDsp);
			upbrhMcht.setMisc1(misc1);

			@SuppressWarnings("rawtypes")
			List list = tblUpbrhMchtBO.checkMchtIdUpUnique(upbrhMcht);
			if (list.size()>0) {
				writeErrorMsg("该支付渠道下,您输入的渠道商户号+渠道终端号已经存在,请修改!");
			}else {
				TblUpbrhMcht info = tblUpbrhMchtBO.get(pk);
				if (null != info) {
					writeErrorMsg("新增失败,对象已存在！");
				} else {
					tblUpbrhMchtBO.add(upbrhMcht);
					writeSuccessMsg("新增成功！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log("操作员编号：" + operator.getOprId()+ "渠道商户模块" + getMethod() + "新增失败。 原因："+e.getMessage());
		}
		return null;
	}

	/**
	 * 停用数据的方法
	 */
	public String stop() throws IOException {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String str = request.getParameter("str");
			String stopType = request.getParameter("stopType");
			String stopReason = request.getParameter("stopReason");
			String[] data = str.split(";");
			for (int i = 0; i < data.length; i++) {
				String string = data[i];
				String[] b = string.split(",");
				String mchtIdUp = b[0];
				String termIdUp = b[1];
				String brhId3 = b[2];
				TblUpbrhMchtPk pk = new TblUpbrhMchtPk();
				pk.setMchtIdUp(mchtIdUp);
				pk.setTermIdUp(termIdUp);
				pk.setBrhId3(brhId3);
				TblUpbrhMcht upbrhMcht = tblUpbrhMchtBO.get(pk);
				// 0--启用 1--停用
				if ("0".equals(upbrhMcht.getStatus())) {
					upbrhMcht.setStatus("1");
					upbrhMcht.setStopType(stopType);
					upbrhMcht.setStopReason(stopReason);
					tblUpbrhMchtBO.update(upbrhMcht);
				}
			}
			writeSuccessMsg("更新成功！");
		} catch (Exception e) {
			e.printStackTrace();
			log("操作员编号：" + operator.getOprId()+ "渠道商户模块" + getMethod() + "停用失败。 原因："+e.getMessage());
			writeErrorMsg("更新失败！");
		}
		return null;
	}
	

	/**
	 * 启用数据的方法
	 */
	public String start() throws IOException {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String str = request.getParameter("str");
			String[] data = str.split(";");
			for (int i = 0; i < data.length; i++) {
				String string = data[i];
				String[] b = string.split(",");
				String mchtIdUp = b[0];
				String termIdUp = b[1];
				String brhId3 = b[2];
				TblUpbrhMchtPk pk = new TblUpbrhMchtPk();
				pk.setMchtIdUp(mchtIdUp);
				pk.setTermIdUp(termIdUp);
				pk.setBrhId3(brhId3);
				TblUpbrhMcht upbrhMcht = tblUpbrhMchtBO.get(pk);
				// 0--启用 1--停用
				if ("1".equals(upbrhMcht.getStatus())) {
					upbrhMcht.setStatus("0");
					tblUpbrhMchtBO.update(upbrhMcht);
				}
			}
			writeSuccessMsg("更新成功！");
		} catch (Exception e) {
			e.printStackTrace();
			log("操作员编号：" + operator.getOprId()+ "渠道商户模块" + getMethod() + "启用失败。 原因："+e.getMessage());
			writeErrorMsg("更新失败！");
		}
		return null;
	}

	/**
	 * (停用渠道商户前)查询渠道商户是否存在映射状态不为0的关系
	 */
	public String findMapByMchtIdUp() throws IOException {

		HttpServletRequest request = ServletActionContext.getRequest();
		String str = request.getParameter("str");
		String[] data = str.split(";");
		for (int i = 0; i < data.length; i++) {
			String string = data[i];
			String[] b = string.split(",");
			String mchtIdUp = b[0];
			String termIdUp = b[1];
			String brhId3 = b[2];
			TblUpbrhMchtPk pk = new TblUpbrhMchtPk();
			pk.setMchtIdUp(mchtIdUp);
			pk.setTermIdUp(termIdUp);
			pk.setBrhId3(brhId3);
			TblUpbrhMcht upbrhMcht = tblUpbrhMchtBO.get(pk);

			@SuppressWarnings("rawtypes")
			List list = tblRouteRuleMapBO.routeRuleMapByMchtIdUp(upbrhMcht.getPk().getMchtIdUp());
			if (list.size()>0) {
				//writeSuccessMsg("存在映射关系，不可停用");
				writeMessage("{msg:'false'}");
				return null;
			}
		}
		writeMessage("{msg:'true'}");
		return null;
	}
	
	
	/**
	 * 修改
	 */
	public String updateInfo() throws IOException {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			String mchtIdUp = request.getParameter("mchtIdUp");
			String brhId3 = request.getParameter("brhId3");
			String termIdUp = request.getParameter("termIdUp");
			String mchtNameUp = request.getParameter("mchtNameUp");
			String area = request.getParameter("area").substring(0, 4);
			String channelNm = request.getParameter("channelNm");
			String businessNm = request.getParameter("businessNm");
			String characterNm = request.getParameter("characterNm");
			String zmk = request.getParameter("zmk");
			String discId = request.getParameter("discId");//discId
			String mchtId = request.getParameter("mchtId");
			String mchtDsp = request.getParameter("mchtDsp");// 备注
			String misc1 = request.getParameter("misc1");// 特殊计费

			TblUpbrhMchtPk pk = new TblUpbrhMchtPk();
			pk.setMchtIdUp(mchtIdUp);
			pk.setBrhId3(brhId3);
			pk.setTermIdUp(termIdUp);
			TblUpbrhMcht upbrhMcht = tblUpbrhMchtBO.get(pk);
			upbrhMcht.setMchtNameUp(mchtNameUp);
			upbrhMcht.setArea(area);
			upbrhMcht.setZmk(zmk);
			upbrhMcht.setDiscId(discId);
			upbrhMcht.setMchtId(mchtId);
			upbrhMcht.setMchtDsp(mchtDsp);
			//先获取misc1的值，若该字段后面的字节被占用，修改时，特殊计费字段只改misc_1字段的第一个字节的值
			String oldMisc1 = upbrhMcht.getMisc1();
			if (StringUtils.isBlank(oldMisc1)) {
				upbrhMcht.setMisc1(misc1);
			}else {
				upbrhMcht.setMisc1(misc1+oldMisc1.substring(1, oldMisc1.length()));
			}

			if(channelNm == null || "".equals(channelNm) || channelNm.trim().length() < 4){
				this.writeErrorMsg("支付渠道：不可为空！");
				return null;
			}else{
				channelNm = channelNm.trim();
			}
			if(businessNm == null || "".equals(businessNm) || businessNm.trim().length() < 8){
				this.writeErrorMsg("业务：不可为空！");
				return null;
			}else{
				businessNm = businessNm.trim();
			}
			if(characterNm == null || "".equals(characterNm) || characterNm.trim().length() < 12){
				this.writeErrorMsg("性质：不可为空！");
				return null;
			}else{
				characterNm = characterNm.trim();
			}
			if(brhId3 == null || "".equals(brhId3) || brhId3.trim().length() < 12){
				this.writeErrorMsg("缺少原性质数据！");
				return null;
			}else{
				brhId3 = brhId3.trim();
			}
			
			//检查支付渠道 、业务、性质 是否正确
			if(!characterNm.substring(0, 4).equals(channelNm) || !characterNm.substring(0, 8).equals(businessNm)){
				this.writeErrorMsg("支付渠道 、业务、性质数据不正确！");
				return null;
			}
			//判断是否修改了渠道
			if (channelNm.equals(brhId3.trim().substring(0, 4))) {
				tblUpbrhMchtBO.updateInfo(upbrhMcht);
				if(!characterNm.equals(brhId3)){
					tblUpbrhMchtBO.updatePk(characterNm, pk);
				}
				writeSuccessMsg("更新成功！");
			}else{
				//修改了性质后的对象
				TblUpbrhMchtPk pk2 = new TblUpbrhMchtPk();
				pk2.setMchtIdUp(mchtIdUp);
				pk2.setBrhId3(characterNm.length()>12?characterNm.substring(0,12):characterNm);
				pk2.setTermIdUp(termIdUp);
				TblUpbrhMcht upbrhMcht2 = new TblUpbrhMcht();
				upbrhMcht2.setPk(pk2);
				@SuppressWarnings("rawtypes")
				List list = tblUpbrhMchtBO.checkMchtIdUpUnique(upbrhMcht2);
				if (list.size()>0) {
					writeErrorMsg("该支付渠道下,渠道商户号+渠道终端号已经存在,请选择其他支付渠道!");
					return null;
				}else{
					tblUpbrhMchtBO.updateInfo(upbrhMcht);
					tblUpbrhMchtBO.updatePk(characterNm, pk);
					writeSuccessMsg("更新成功！");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log("操作员编号：" + operator.getOprId()+ "渠道商户修改" + getMethod() + "失败，失败原因为："+e.getMessage());
			writeErrorMsg("更新失败！");
		}
		return null;
	}

	/**
	 * 导出数据
	 */
	public String dateExport() {
		// 查询数据库中所有的数据
		String sql = "SELECT a.brh_Id3,a.MCHT_ID_UP,a.MCHT_NAME_UP,a.TERM_ID_UP,ru1.name as channelNm,ru2.name as businessNm,ru3.name as characterNm,a.STATUS,c.MCHT_CITY_CODE||' - '||c.CITY_NAME as area,a.MCHT_ID,b.MCHT_NM,a.MISC_1, nvl(d.amount,0) as amount,a.RUN_TIME,a.STOP_TIME,a.STOP_TYPE,a.STOP_REASON,a.UPT_TIME,a.UPT_OPR FROM TBL_UPBRH_MCHT a left join Tbl_Route_Upbrh ru1 on substr(a.brh_id3, 1, 4) = ru1.brh_id and ru1.BRH_LEVEL='1' and ru1.STATUS='0' left join Tbl_Route_Upbrh ru2 on substr(a.brh_id3, 1, 8) = ru2.brh_id and ru2.BRH_LEVEL='2' and ru2.STATUS='0' left join Tbl_Route_Upbrh ru3 on a.brh_id3 = ru3.brh_id and ru3.BRH_LEVEL='3' and ru3.STATUS='0' left join TBL_MCHT_BASE_INF b on a.MCHT_ID = b.MCHT_NO left join cst_city_code c on a.area = c.MCHT_CITY_CODE left join (select mcht_id_up, count(distinct mcht_id) as amount from Tbl_Route_Rule_Map group by mcht_id_up) d on a.MCHT_ID_UP=d.MCHT_ID_UP where 1=1 order by a.UPT_TIME desc ";
		String countSql = "SELECT COUNT(*) FROM TBL_UPBRH_MCHT a ,TBL_MCHT_BASE_INF b  where a.MCHT_ID = b.MCHT_NO ";
		String count = CommonFunction.getcommGWQueryDAO().findCountBySQLQuery(countSql);
		@SuppressWarnings("unchecked")
		List<Object[]> dataList = CommonFunction.getcommGWQueryDAO()
				.findBySQLQuery(sql, 0, Integer.parseInt(count));
		try {
			WritableWorkbook wwb = null;
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
			String time = formatter.format(curDate);
			// 创建可写入的Excel工作簿
			String fileName = dir + File.separator + "渠道商户信息报表" + time + ".xls";
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

			// 要插入到的Excel表格的行号，默认从0开始
			Label mchtIdUp = new Label(0, 0, "渠道商户号");// 表示第
			Label mchtNameUp = new Label(1, 0, "渠道商户名称");
			Label termIdUp = new Label(2, 0, "渠道终端号");
			Label channelNm = new Label(3, 0, "支付渠道");
			Label businessNm = new Label(4, 0, "业务");
			Label characterNm = new Label(5, 0, "性质");
			Label status = new Label(6, 0, "渠道商户状态");
			Label area = new Label(7, 0, "所属地区");
			Label mchtId = new Label(8, 0, "合规商户编号");
			Label mchtNm = new Label(9, 0, "合规商户名称");
			Label misc1 = new Label(10, 0, "特殊计费");
			Label amount = new Label(11, 0, "关联本地商户数量");
			Label runTime = new Label(12, 0, "启用时间");
			Label stopTime = new Label(13, 0, "停用时间");
			Label stopType = new Label(14, 0, "停用类型");
			Label stopReason = new Label(15, 0, "停用原因");
			Label uptTime = new Label(16, 0, "最后修改时间");
			Label uptOpr = new Label(17, 0, "修改人");

			ws.addCell(mchtIdUp);
			ws.addCell(mchtNameUp);
			ws.addCell(termIdUp);
			ws.addCell(channelNm);
			ws.addCell(businessNm);
			ws.addCell(characterNm);
			ws.addCell(status);
			ws.addCell(area);
			ws.addCell(mchtId);
			ws.addCell(mchtNm);
			ws.addCell(misc1);
			ws.addCell(amount);
			ws.addCell(runTime);
			ws.addCell(stopTime);
			ws.addCell(stopType);
			ws.addCell(stopReason);
			ws.addCell(uptTime);
			ws.addCell(uptOpr);

			for (int i = 0; i < dataList.size(); i++) {
				Label mchtIdUp_i = new Label(0, i + 1, dataList.get(i)[1]==null?"":dataList.get(i)[1] + "");//当数据为null时，转换为空字符串
				Label mchtNameUp_i = new Label(1, i + 1, dataList.get(i)[2]==null?"":dataList.get(i)[2]+ "");
				Label termIdUp_i = new Label(2, i + 1, dataList.get(i)[3]==null?"":dataList.get(i)[3] + "");
				Label channelNm_i = new Label(3, i + 1, dataList.get(i)[4]==null?"":dataList.get(i)[4] + "");
				Label businessNm_i = new Label(4, i + 1, dataList.get(i)[5]==null?"":dataList.get(i)[5]+ "");
				Label characterNm_i = new Label(5, i + 1, dataList.get(i)[6]==null?"":dataList.get(i)[6]+ "");
				Label status_i = new Label(6, i + 1, "0".equals(dataList.get(i)[7])?"启用":"停用" + "");//渠道商户状态：0-启用；1-停用
				Label area_i = new Label(7, i + 1, dataList.get(i)[8]==null?"":dataList.get(i)[8] + "");
				Label mchtId_i = new Label(8, i + 1, dataList.get(i)[9]==null?"":dataList.get(i)[9] + "");
				Label mchtNm_i = new Label(9, i + 1, dataList.get(i)[10]==null?"":dataList.get(i)[10] + "");
				String misc1Render = "";//导出数据的特殊计费值转换成汉字--0：无；1：县乡普通；2：县乡三农
				if ("0".equals(dataList.get(i)[11])) {
					misc1Render = "无";
				}else if ("1".equals(dataList.get(i)[11])) {
					misc1Render = "县乡普通";
				}else if ("2".equals(dataList.get(i)[11])) {
					misc1Render = "县乡三农";
				}
				Label misc1_i = new Label(10, i + 1, misc1Render + "");
				Label amount_i = new Label(11, i + 1, dataList.get(i)[12]==null?"":dataList.get(i)[12] + "");
				Label runTime_i = new Label(12, i + 1, dataList.get(i)[13]==null?"":dataList.get(i)[13] + "");
				Label stopTime_i = new Label(13, i + 1, dataList.get(i)[14]==null?"":dataList.get(i)[14] + "");//当数据为null时，转换为空字符串

				String stopTypeRender = "";//停用类型值转换成汉字，当数据为null时，转换为空字符串
				if(null==dataList.get(i)[15]){
					stopTypeRender = "";
				}else if ("1".equals(dataList.get(i)[15])) {
					stopTypeRender = "商户被投诉";
				}else if ("2".equals(dataList.get(i)[15])) {
					stopTypeRender = "商户号被晒单";
				}else if ("3".equals(dataList.get(i)[15])) {
					stopTypeRender = "商户号被整改";
				}else if ("4".equals(dataList.get(i)[15])) {
					stopTypeRender = "交易金额过大";
				}else if ("5".equals(dataList.get(i)[15])) {
					stopTypeRender = "其他";
				}
				Label stopType_i = new Label(14, i + 1, stopTypeRender + "");
				Label stopReason_i = new Label(15, i + 1, dataList.get(i)[16]==null?"":dataList.get(i)[16] + "");//当数据为null时，转换为空字符串
				Label uptTime_i = new Label(16, i + 1, dataList.get(i)[17]==null?"":dataList.get(i)[17] + "");
				Label uptOpr_i = new Label(17, i + 1, dataList.get(i)[18]==null?"":dataList.get(i)[18] + "");
				ws.addCell(mchtIdUp_i);
				ws.addCell(mchtNameUp_i);
				ws.addCell(termIdUp_i);
				ws.addCell(channelNm_i);
				ws.addCell(businessNm_i);
				ws.addCell(characterNm_i);
				ws.addCell(status_i);
				ws.addCell(area_i);
				ws.addCell(mchtId_i);
				ws.addCell(mchtNm_i);
				ws.addCell(misc1_i);
				ws.addCell(amount_i);
				ws.addCell(runTime_i);
				ws.addCell(stopTime_i);
				ws.addCell(stopType_i);
				ws.addCell(stopReason_i);
				ws.addCell(uptTime_i);
				ws.addCell(uptOpr_i);
			}
			// 写进文档
			wwb.write();
			// 关闭Excel工作簿对象
			wwb.close();
			writeUsefullMsg(fileName);
			writeSuccessMsg("数据下载成功.");
		} catch (Exception e) {
			e.printStackTrace();
			log("操作员编号：" + operator.getOprId()+ "渠道商户模块" + getMethod() + "下载数据失败");
			return null;
		}
		return null;
	}

	public String delete() throws IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String mchtIdUp = request.getParameter("mchtIdUp");
		String termIdUp = request.getParameter("termIdUp");
		String brhId3 = request.getParameter("brhId3");
		TblUpbrhMchtPk pk = new TblUpbrhMchtPk();
		pk.setMchtIdUp(mchtIdUp);
		pk.setTermIdUp(termIdUp);
		pk.setBrhId3(brhId3);
		TblUpbrhMcht upbrhMcht = tblUpbrhMchtBO.get(pk);
		if (null != upbrhMcht) {
			tblUpbrhMchtBO.delete(upbrhMcht);
		} else {
			writeMessage("{msg:'删除失败'}");
		}
		return null;
	}

	
	/**
	 * 数据导入
	 */
	public String upload() throws IOException {
		InputStreamReader fr = null;
		BufferedReader br = null;
		FileInputStream fis = null;

		if (files != null && files.size() != 0 && files.get(0) != null) {
			List<String> list = new ArrayList<String>();
			try {
				fis = new FileInputStream(files.get(0));
				fr = new InputStreamReader(fis,"GBK");//加"GBK"指定编码格式
				br = new BufferedReader(fr);
				String rec = null;
				while ((rec = br.readLine()) != null) {
					String line = rec;
					list.add(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
				log("操作员编号：" + operator.getOprId()+ "渠道商户模块" + getMethod() + "导入数据失败。 原因："+e.getMessage());
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
					log("操作失败");
				}
			}

			// 注意：若csv文件没有文件头，从第一行开始就是数据，则下面循环i应从0开始
			for (int i = 1; i < list.size(); i++) {// 注意i取值是0还是1
				// System.out.println("第 "+i+"条数据 = "+list.get(i));
				String[] a = list.get(i).split(",");
				// for (int j = 0; j < a.length; j++) {
				String str0 = a[0];
				String str1 = a[1];
				String str2 = a[2];
				String str3 = a[3];
				String str4 = a[4];
				String str5 = a[5];
				String str6 = a[6];
				String str7 = a[7];
				String str8 = a[8];
				String str9 = a[9];

				TblUpbrhMchtPk pk = new TblUpbrhMchtPk();
				/*// 生成15位的渠道商户号
				Random random = new Random();
				int num = 0 + random.nextInt(9);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
				Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
				String time = formatter.format(curDate);
				String mchtIdUp = time + String.valueOf(num);*/

				pk.setMchtIdUp(str0);//渠道商户号
				pk.setTermIdUp(str2);//渠道终端号
				pk.setBrhId3(str3);//性质

				TblUpbrhMcht upbrhMcht = new TblUpbrhMcht();
				upbrhMcht.setPk(pk);
				upbrhMcht.setMchtNameUp(str1);//渠道商户名称
				upbrhMcht.setArea(str4);//所属地区
				upbrhMcht.setMchtId(str5);//合规商户号
				upbrhMcht.setZmk(str6);//终端主密钥
				upbrhMcht.setDiscId(str7);//成本扣率
				upbrhMcht.setMchtDsp(str8);//备注
				upbrhMcht.setMisc1(str9);//特殊计费

				TblUpbrhMcht info = tblUpbrhMchtBO.get(pk);
				if (null == info) {
					tblUpbrhMchtBO.add(upbrhMcht);
				}
			}
			writeSuccessMsg("数据导入成功！");
		}
		return null;
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

	public TblUpbrhMcht getRouteMchtg() {
		return upbrhMcht;
	}

	public void setRouteMchtg(TblUpbrhMcht routeMchtg) {
		this.upbrhMcht = routeMchtg;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

}
