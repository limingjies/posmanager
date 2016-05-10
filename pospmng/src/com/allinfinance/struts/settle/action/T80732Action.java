
package com.allinfinance.struts.settle.action;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.allinfinance.bo.settle.T80732BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.ErrorCode;
import com.allinfinance.common.StringUtil;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;
import com.allinfinance.vo.T8073201VO;

/**
 * 调账处理Action类
 * 
 * @author luhq
 *
 */
public class T80732Action extends BaseAction{
	private static Logger log = Logger.getLogger(T80732Action.class);

	private static final long serialVersionUID = 1L;

	/**
	 * 商户号
	 */
	private String mchtIds;
	/**
	 * 商户名称
	 */
	private String mchtNms;
	/**
	 * 交易日期
	 */
	private String transDates;
	/**
	 * 交易时间
	 */
	private String transTimes;
	/**
	 * 交易金额
	 */
	private String transAmounts;
	/**
	 * 清算日期
	 */
	private String settlDates;
	/**
	 * 对账差错表主键
	 */
	private String keyInsts;
	/**
	 * 人工调账处理内容
	 */
	private String solveMsg;
	// 文件集合
	private List<File> imgFile;
	// 文件名称集合
	private List<String> imgFileFileName;
	private String imgBatchId;

	/**
	 * 渠道交易日期
	 */
	private String instTransDate;
	/**
	 * 渠道交易时间
	 */
	private String instTransTime;
	/**
	 * 通道交易金额
	 */
	private String instAmtTrans;
	

	@Override
	protected String subExecute() throws Exception {
		if("adjust".equals(this.getMethod())){
			rspCode = adjust();
		} else if("handle".equals(this.getMethod())){
			rspCode = handle();
		} else if("upload".equals(this.getMethod())){
			rspCode = upload();
		}
		return rspCode;
	}
	
	private String handle() throws Exception{
		if(StringUtils.isEmpty(this.solveMsg)){
			return "处理内容不可为空！";
		}
		T80732BO t80732BO = (T80732BO) ContextUtil.getBean("T80732BO");
		
		T8073201VO vo = new T8073201VO();
		vo.setMerchId(this.getMchtIds());
		vo.setSettlDate(this.getSettlDates().replaceAll("-", ""));
		vo.setKeyInst(this.getKeyInsts());
		vo.setOprId(operator.getOprId());
		vo.setSolveMsg(this.getSolveMsg());
		int rst = t80732BO.ajustByHand(vo,this.imgBatchId);
		String rstMsg = "";
		if (rst == 0) {
			//20160330 add by yww -- 新增判断，如果商户信息为空，则提示信息不显示商户信息，数据获取渠道时间和金额
			if (null==this.getMchtNms() || "".equals(this.getMchtNms())) {
				rstMsg += "<font color=green> " + this.getInstTransDate() + " " + this.getInstTransTime() + "进行的金额为[" + this.getInstAmtTrans() +"]的交易手工调账成功</font> ";
			}else {
				rstMsg += "<font color=green>商户[" + this.getMchtNms()+ "]于" + this.getTransDates() + " " + this.getTransTimes() + "进行的金额为[" + this.getTransAmounts() + "]的交易手工调账成功</font>";
			}
		} else if (rst == 1) {
			rstMsg += "<font color=red>商户[" + this.getMchtNms()+ "]于" + this.getTransDates() + " " + this.getTransTimes() + "进行的金额为[" + this.getTransAmounts() + "]的交易已调账</font>";
		} else {
			rstMsg += "<font color=red>商户["+this.getMchtNms()+ "]于" + this.getTransDates() + " " + this.getTransTimes() + "进行的金额为[" + this.getTransAmounts() + "]的交易手工调账失败</font>";
		}
		if (rst != 0) {
			return rstMsg;
		} else {
			writeSuccessMsg(rstMsg);
			return Constants.SUCCESS_CODE;
		}
	}
	
	/**
	 * 将上传文件保存至零时目录，同时将前30天的临时（废旧）文件删除
	 * @return
	 */
	private String upload() {
		T80732BO t80732BO = (T80732BO) ContextUtil.getBean("T80732BO");
		if (StringUtil.isNotEmpty(settlDates) && StringUtil.isNotEmpty(keyInsts)) {
			return t80732BO.upload(imgFile, imgFileFileName,settlDates,keyInsts,this.operator.getOprId(),imgBatchId);			
		}else{
			return "文件上传失败：参数不正确！";
		}
	}
	
	private String adjust() throws Exception{	
		HttpServletRequest request = ServletActionContext.getRequest();
		String browser = request.getHeader("User-agent").toLowerCase();
		
		T80732BO t80732BO = (T80732BO) ContextUtil.getBean("T80732BO");
		
		String[] mchtIdAry = mchtIds.split("###");
		String[] settlDateAry = settlDates.split("###");
		String[] keyInstAry = keyInsts.split("###");

		String[] mchtNmAry = mchtNms.split("###");
		String[] transDateAry = transDates.split("###");
		String[] transTimeAry = transTimes.split("###");
		String[] transAmountAry = transAmounts.split("###");
		int selRowCnt = mchtIdAry.length;
		if (selRowCnt != settlDateAry.length 
				|| selRowCnt != keyInstAry.length 
				|| selRowCnt != mchtNmAry.length
				|| selRowCnt != transDateAry.length
				|| selRowCnt != transTimeAry.length
				|| selRowCnt != transAmountAry.length
				) {
			log.error("参数解析错误：mchtIdAry：" + mchtIdAry 
					+ ", settlDateAry：" + settlDateAry 
					+ ", keyInstAry：" + keyInstAry 
					+ ", mchtNmAry：" + mchtNmAry 
					+ ", transDateAry：" + transDateAry 
					+ ", transTimeAry：" + transTimeAry 
					+ ", transAmountAry：" + transAmountAry);
			return ErrorCode.T80732_01;
		}
		String rstMsg = "";
		boolean hasFail = false;
		for (int i=0; i<mchtIdAry.length; i++) {
			T8073201VO vo = new T8073201VO();
			vo.setMerchId(mchtIdAry[i]);
			vo.setSettlDate(settlDateAry[i].replaceAll("-", ""));
			vo.setKeyInst(keyInstAry[i]);
			vo.setOprId(operator.getOprId());
			int rst = t80732BO.ajust(vo);
			if (rst == 0) {
				rstMsg += "<font color=green>商户["+mchtNmAry[i]+ "]于" + transDateAry[i] + " " + transTimeAry[i] + "进行的金额为[" + transAmountAry[i] +"]的交易调账成功</font></div>";
			} else if (rst == 1) {
				hasFail = true;
				rstMsg += "<font color=red>商户["+mchtNmAry[i]+ "]于" + transDateAry[i] + " " + transTimeAry[i] + "进行的金额为[" + transAmountAry[i] +"]的交易已调账</font></div>";
			} else {
				hasFail = true;
				rstMsg += "<font color=red>商户["+mchtNmAry[i]+ "]于" + transDateAry[i] + " " + transTimeAry[i] + "进行的金额为[" + transAmountAry[i] +"]的交易调账失败</font></div>";
			}
			if (browser.indexOf("msie") < 0) {
				rstMsg +="<br>";
			}
		}
		if (hasFail) {
			return rstMsg;
		} else {
			writeSuccessMsg(rstMsg);
			return Constants.SUCCESS_CODE;
		}
	}


	public String getMchtIds() {
		return mchtIds;
	}


	public void setMchtIds(String mchtIds) {
		this.mchtIds = mchtIds;
	}


	public String getSettlDates() {
		return settlDates;
	}


	public void setSettlDates(String settlDates) {
		this.settlDates = settlDates;
	}


	public String getKeyInsts() {
		return keyInsts;
	}


	public void setKeyInsts(String keyInsts) {
		this.keyInsts = keyInsts;
	}


	public String getMchtNms() {
		return mchtNms;
	}


	public void setMchtNms(String mchtNms) {
		this.mchtNms = mchtNms;
	}


	public String getTransDates() {
		return transDates;
	}


	public void setTransDates(String transDates) {
		this.transDates = transDates;
	}


	public String getTransTimes() {
		return transTimes;
	}


	public void setTransTimes(String transTimes) {
		this.transTimes = transTimes;
	}


	public String getTransAmounts() {
		return transAmounts;
	}


	public void setTransAmounts(String transAmounts) {
		this.transAmounts = transAmounts;
	}


	public String getSolveMsg() {
		return solveMsg;
	}


	public void setSolveMsg(String solveMsg) {
		this.solveMsg = solveMsg;
	}

	public List<File> getImgFile() {
		return imgFile;
	}

	public void setImgFile(List<File> imgFile) {
		this.imgFile = imgFile;
	}

	public List<String> getImgFileFileName() {
		return imgFileFileName;
	}

	public void setImgFileFileName(List<String> imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}

	public String getImgBatchId() {
		return imgBatchId;
	}

	public void setImgBatchId(String imgBatchId) {
		this.imgBatchId = imgBatchId;
	}

	public String getInstTransDate() {
		return instTransDate;
	}

	public void setInstTransDate(String instTransDate) {
		this.instTransDate = instTransDate;
	}

	public String getInstTransTime() {
		return instTransTime;
	}

	public void setInstTransTime(String instTransTime) {
		this.instTransTime = instTransTime;
	}

	public String getInstAmtTrans() {
		return instAmtTrans;
	}

	public void setInstAmtTrans(String instAmtTrans) {
		this.instAmtTrans = instAmtTrans;
	}
	

}
