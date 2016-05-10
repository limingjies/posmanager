package com.allinfinance.struts.base.action;

import java.math.BigDecimal;

import com.allinfinance.bo.base.T10103BO;
import com.allinfinance.common.CommonsConstants;
import com.allinfinance.common.StringUtil;
import com.allinfinance.po.base.TblBrhFeeCfgPK;
import com.allinfinance.po.base.TblBrhFeeCfgZlf;
import com.allinfinance.po.base.TblBrhFeeCtl;
import com.allinfinance.struts.system.action.BaseAction;
import com.allinfinance.system.util.ContextUtil;

public class T10103Action extends BaseAction {
	
	private static final long serialVersionUID = 1L;

	private T10103BO t10103BO = (T10103BO) ContextUtil.getBean("T10103BO");

	private String discId;
	private String seq;
	private TblBrhFeeCtl tblBrhFeeCtlAdd,tblBrhFeeCtlUpd;
	private TblBrhFeeCfgZlf tblBrhFeeCfgZlfAdd,tblBrhFeeCfgZlfUpd;
//	private String mchtFeeLowAdd;
//	private String mchtFeeUpAdd;
//	private String mchtCapFeeLowAdd;
//	private String mchtCapFeeUpAdd;
//	private String mchtFeeLowUpd;
//	private String mchtFeeUpUpd;
//	private String mchtCapFeeLowUpd;
//	private String mchtCapFeeUpUpd;
	//激励系数
	private String promotionRate;
	//基础月交易额
	private String baseAmtMonth;
	//提档交易额
	private String gradeAmtMonth;
	//激励系数
	private String promotionRateUpd;
	//基础月交易额
	private String baseAmtMonthUpd;
	//提档交易额
	private String gradeAmtMonthUpd;
	
	@Override
	protected String subExecute(){
		try {
			if("addCtl".equals(method)) {
				rspCode = addCtl();
			}else if("updateCtl".equals(method)) {
				rspCode = updateCtl();
			}else if("deleteCtl".equals(method)) {
				rspCode = deleteCtl();
			}else if("start".equals(method)) {			
					rspCode = start();			
			} else if("stop".equals(method)) {
				rspCode = stop();
			}else if("addCfg".equals(method)) {
				rspCode = addCfg();
			}else if("updateCfg".equals(method)) {
				rspCode = updateCfg();
			}else if("deleteCfg".equals(method)) {
				rspCode = deleteCfg();
			}
			
			
			
		} catch (Exception e) {
			log("操作员编号：" + operator.getOprId()+ "，对机构费率设置操作" + getMethod() + "失败，失败原因为："+e.getMessage());
			e.printStackTrace();
		}
		return rspCode;
	}
	
	private String addCtl() {
		// TODO Auto-generated method stub
		
		if(tblBrhFeeCtlAdd==null){
			return "新增提交失败！";
		}
		TblBrhFeeCtl tblBrhFeeCtl=t10103BO.get(tblBrhFeeCtlAdd.getDiscId());
		if(tblBrhFeeCtl!=null){
			return "该机构费率代码已经存在！";
		}
		return t10103BO.addCtl(tblBrhFeeCtlAdd,operator);
	}
	
	
	private String updateCtl() {
		// TODO Auto-generated method stub
		
		TblBrhFeeCtl tblBrhFeeCtl=t10103BO.get(discId);
		if(tblBrhFeeCtl==null){
			return "该机构费率代码不存在！";
		}
		tblBrhFeeCtl.setDiscName(tblBrhFeeCtlUpd.getDiscName());
		return t10103BO.updateCtl(tblBrhFeeCtl,operator);
	}
	
	private String deleteCtl() {
		// TODO Auto-generated method stub
		
		TblBrhFeeCtl tblBrhFeeCtl=t10103BO.get(discId);
		if(tblBrhFeeCtl==null){
			return "该机构费率代码不存在！";
		}
		return t10103BO.deleteCtl(discId);
	}
	
	
	
	private String start() {
		// TODO Auto-generated method stub
		TblBrhFeeCfgPK tblBrhFeeCfgPK=new TblBrhFeeCfgPK();
		tblBrhFeeCfgPK.setDiscId(discId);
		tblBrhFeeCfgPK.setSeq(Integer.parseInt(seq));
		TblBrhFeeCfgZlf tblBrhFeeCfgZlf=t10103BO.get(tblBrhFeeCfgPK);
		if(tblBrhFeeCfgZlf==null){
			return "该机构费率信息不存在，请重新刷新选择！";
		}
		if(CommonsConstants.ENABLE_FLAG_START.equals(tblBrhFeeCfgZlf.getEnableFlag())){
			return "该机构费率状态已为启用,请重新刷新操作！";
		}
		return t10103BO.start(tblBrhFeeCfgZlf,operator);
	}
	
	private String stop() {
		// TODO Auto-generated method stub
		TblBrhFeeCfgPK tblBrhFeeCfgPK=new TblBrhFeeCfgPK();
		tblBrhFeeCfgPK.setDiscId(discId);
		tblBrhFeeCfgPK.setSeq(Integer.parseInt(seq));
		TblBrhFeeCfgZlf tblBrhFeeCfgZlf=t10103BO.get(tblBrhFeeCfgPK);
		if(tblBrhFeeCfgZlf==null){
			return "该机构费率信息不存在，请重新刷新选择！";
		}
		if(CommonsConstants.ENABLE_FLAG_STOP.equals(tblBrhFeeCfgZlf.getEnableFlag())){
			return "该机构费率状态已为停用,请重新刷新操作！";
		}
		return t10103BO.stop(tblBrhFeeCfgZlf,operator);
	}
	
	
	private String addCfg() {
		// TODO Auto-generated method stub
		
		if(tblBrhFeeCfgZlfAdd==null){
			return "新增提交失败！";
		}
		TblBrhFeeCtl tblBrhFeeCtl=t10103BO.get(discId);
		if(tblBrhFeeCtl==null){
			return "该机构费率代码不存在！";
		}
//		if(StringUtil.isNotEmpty(mchtFeeLowAdd)&&StringUtil.isNotEmpty(mchtFeeUpAdd)
//				&&(new BigDecimal(mchtFeeLowAdd)).compareTo(new BigDecimal(mchtFeeUpAdd))>0){
//			return "商户费率下限不得大于商户费率上限！";
//		}
//		if(StringUtil.isNotEmpty(mchtCapFeeLowAdd)&&StringUtil.isNotEmpty(mchtCapFeeUpAdd)
//				&&(new BigDecimal(mchtCapFeeLowAdd)).compareTo(new BigDecimal(mchtCapFeeUpAdd))>0){
//			return "封顶费率下限不得大于封顶费率上限！";
//		}
//		if(StringUtil.isNotEmpty(mchtFeeLowAdd)){
//			tblBrhFeeCfgZlfAdd.setMchtFeeLow(new BigDecimal(mchtFeeLowAdd));
//		}else{
//			tblBrhFeeCfgZlfAdd.setMchtFeeLow(new BigDecimal(0));
//		}
//		
//		if(StringUtil.isNotEmpty(mchtFeeUpAdd)){
//			tblBrhFeeCfgZlfAdd.setMchtFeeUp(new BigDecimal(mchtFeeUpAdd));
//		}else{
//			tblBrhFeeCfgZlfAdd.setMchtFeeUp(new BigDecimal(0));
//		}
//		
//		if(StringUtil.isNotEmpty(mchtCapFeeLowAdd)){
//			tblBrhFeeCfgZlfAdd.setMchtCapFeeLow(new BigDecimal(mchtCapFeeLowAdd));
//		}else{
//			tblBrhFeeCfgZlfAdd.setMchtCapFeeLow(new BigDecimal(0));
//		}
//		
//		if(StringUtil.isNotEmpty(mchtCapFeeUpAdd)){
//			tblBrhFeeCfgZlfAdd.setMchtCapFeeUp(new BigDecimal(mchtCapFeeUpAdd));
//		}else{
//			tblBrhFeeCfgZlfAdd.setMchtCapFeeUp(new BigDecimal(0));
//		}
		if(StringUtil.isNotEmpty(promotionRate)){
			tblBrhFeeCfgZlfAdd.setPromotionRate(new BigDecimal(promotionRate));
		}else{
			tblBrhFeeCfgZlfAdd.setPromotionRate(BigDecimal.ZERO);
		}
		if(StringUtil.isNotEmpty(baseAmtMonth)){
			tblBrhFeeCfgZlfAdd.setBaseAmtMonth(new BigDecimal(baseAmtMonth));
		}else{
			tblBrhFeeCfgZlfAdd.setBaseAmtMonth(BigDecimal.ZERO);
		}
		if(StringUtil.isNotEmpty(gradeAmtMonth)){
			tblBrhFeeCfgZlfAdd.setGradeAmtMonth(new BigDecimal(gradeAmtMonth));
		}else{
			tblBrhFeeCfgZlfAdd.setGradeAmtMonth(BigDecimal.ZERO);
		}
		TblBrhFeeCfgPK tblBrhFeeCfgPK=new TblBrhFeeCfgPK();
		tblBrhFeeCfgPK.setDiscId(discId);
		tblBrhFeeCfgZlfAdd.setTblBrhFeeCfgPK(tblBrhFeeCfgPK);
		tblBrhFeeCfgZlfAdd.setPromotionBegDate(StringUtil.isEmpty(tblBrhFeeCfgZlfAdd.getPromotionBegDate())?"":tblBrhFeeCfgZlfAdd.getPromotionBegDate().replace("-", ""));
		tblBrhFeeCfgZlfAdd.setPromotionEndDate(StringUtil.isEmpty(tblBrhFeeCfgZlfAdd.getPromotionEndDate())?"":tblBrhFeeCfgZlfAdd.getPromotionEndDate().replace("-", ""));
		return t10103BO.addCfg(tblBrhFeeCfgZlfAdd,operator);
	}

	
	
	private String updateCfg() {
		// TODO Auto-generated method stub
		
		if(tblBrhFeeCfgZlfUpd==null){
			return "修改提交失败！";
		}
		TblBrhFeeCfgPK tblBrhFeeCfgPK=new TblBrhFeeCfgPK();
		tblBrhFeeCfgPK.setDiscId(discId);
		tblBrhFeeCfgPK.setSeq(Integer.parseInt(seq));
		
		TblBrhFeeCfgZlf tblBrhFeeCfgZlf=t10103BO.get(tblBrhFeeCfgPK);
		if(tblBrhFeeCfgZlf==null){
			return "该机构费率代码规则不存在！";
		}
//		if(StringUtil.isNotEmpty(mchtFeeLowUpd)&&StringUtil.isNotEmpty(mchtFeeUpUpd)
//				&&(new BigDecimal(mchtFeeLowUpd)).compareTo(new BigDecimal(mchtFeeUpUpd))>0){
//			return "商户费率下限不得大于商户费率上限！";
//		}
//		if(StringUtil.isNotEmpty(mchtCapFeeLowUpd)&&StringUtil.isNotEmpty(mchtCapFeeUpUpd)
//				&&(new BigDecimal(mchtCapFeeLowUpd)).compareTo(new BigDecimal(mchtCapFeeUpUpd))>0){
//			return "封顶费率下限不得大于封顶费率上限！";
//		}
//		if(StringUtil.isNotEmpty(mchtFeeLowUpd)){
//			tblBrhFeeCfgZlfUpd.setMchtFeeLow(new BigDecimal(mchtFeeLowUpd));
//		}else{
//			tblBrhFeeCfgZlfUpd.setMchtFeeLow(new BigDecimal(0));
//		}
//		
//		if(StringUtil.isNotEmpty(mchtFeeUpUpd)){
//			tblBrhFeeCfgZlfUpd.setMchtFeeUp(new BigDecimal(mchtFeeUpUpd));
//		}else{
//			tblBrhFeeCfgZlfUpd.setMchtFeeUp(new BigDecimal(0));
//		}
//		
//		if(StringUtil.isNotEmpty(mchtCapFeeLowUpd)){
//			tblBrhFeeCfgZlfUpd.setMchtCapFeeLow(new BigDecimal(mchtCapFeeLowUpd));
//		}else{
//			tblBrhFeeCfgZlfUpd.setMchtCapFeeLow(new BigDecimal(0));
//		}
//		
//		if(StringUtil.isNotEmpty(mchtCapFeeUpUpd)){
//			tblBrhFeeCfgZlfUpd.setMchtCapFeeUp(new BigDecimal(mchtCapFeeUpUpd));
//		}else{
//			tblBrhFeeCfgZlfUpd.setMchtCapFeeUp(new BigDecimal(0));
//		}
		if(StringUtil.isNotEmpty(promotionRateUpd)){
			tblBrhFeeCfgZlfUpd.setPromotionRate(new BigDecimal(promotionRateUpd));
		}else{
			tblBrhFeeCfgZlfUpd.setPromotionRate(BigDecimal.ZERO);
		}
		if(StringUtil.isNotEmpty(baseAmtMonthUpd)){
			tblBrhFeeCfgZlfUpd.setBaseAmtMonth(new BigDecimal(baseAmtMonthUpd));
		}else{
			tblBrhFeeCfgZlfUpd.setBaseAmtMonth(BigDecimal.ZERO);
		}
		if(StringUtil.isNotEmpty(gradeAmtMonthUpd)){
			tblBrhFeeCfgZlfUpd.setGradeAmtMonth(new BigDecimal(gradeAmtMonthUpd));
		}else{
			tblBrhFeeCfgZlfUpd.setGradeAmtMonth(BigDecimal.ZERO);
		}
		tblBrhFeeCfgZlfUpd.setTblBrhFeeCfgPK(tblBrhFeeCfgPK);
		tblBrhFeeCfgZlfUpd.setCreateTime(tblBrhFeeCfgZlf.getCreateTime());
		tblBrhFeeCfgZlfUpd.setEnableFlag(tblBrhFeeCfgZlf.getEnableFlag());
		tblBrhFeeCfgZlfUpd.setPromotionBegDate(StringUtil.isEmpty(tblBrhFeeCfgZlfUpd.getPromotionBegDate())?"":tblBrhFeeCfgZlfUpd.getPromotionBegDate().replace("-", ""));
		tblBrhFeeCfgZlfUpd.setPromotionEndDate(StringUtil.isEmpty(tblBrhFeeCfgZlfUpd.getPromotionEndDate())?"":tblBrhFeeCfgZlfUpd.getPromotionEndDate().replace("-", ""));
		return t10103BO.updateCfg(tblBrhFeeCfgZlfUpd,operator);
	}
	
	
	private String deleteCfg() {
		// TODO Auto-generated method stub
		
		TblBrhFeeCfgPK tblBrhFeeCfgPK=new TblBrhFeeCfgPK();
		tblBrhFeeCfgPK.setDiscId(discId);
		tblBrhFeeCfgPK.setSeq(Integer.parseInt(seq));
		
		TblBrhFeeCfgZlf tblBrhFeeCfgZlf=t10103BO.get(tblBrhFeeCfgPK);
		if(tblBrhFeeCfgZlf==null){
			return "该机构费率代码规则不存在！";
		}
		if(CommonsConstants.ENABLE_FLAG_START.equals(tblBrhFeeCfgZlf.getEnableFlag())){
			return "请先停用该机构费率规则！";
		}
		return t10103BO.deleteCfg(tblBrhFeeCfgPK);
	}
	
	
	public String getDiscId() {
		return discId;
	}

	public void setDiscId(String discId) {
		this.discId = discId;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public TblBrhFeeCtl getTblBrhFeeCtlAdd() {
		return tblBrhFeeCtlAdd;
	}

	public void setTblBrhFeeCtlAdd(TblBrhFeeCtl tblBrhFeeCtlAdd) {
		this.tblBrhFeeCtlAdd = tblBrhFeeCtlAdd;
	}

	public TblBrhFeeCtl getTblBrhFeeCtlUpd() {
		return tblBrhFeeCtlUpd;
	}

	public void setTblBrhFeeCtlUpd(TblBrhFeeCtl tblBrhFeeCtlUpd) {
		this.tblBrhFeeCtlUpd = tblBrhFeeCtlUpd;
	}

	public TblBrhFeeCfgZlf getTblBrhFeeCfgZlfAdd() {
		return tblBrhFeeCfgZlfAdd;
	}

	public void setTblBrhFeeCfgZlfAdd(TblBrhFeeCfgZlf tblBrhFeeCfgZlfAdd) {
		this.tblBrhFeeCfgZlfAdd = tblBrhFeeCfgZlfAdd;
	}

	public TblBrhFeeCfgZlf getTblBrhFeeCfgZlfUpd() {
		return tblBrhFeeCfgZlfUpd;
	}

	public void setTblBrhFeeCfgZlfUpd(TblBrhFeeCfgZlf tblBrhFeeCfgZlfUpd) {
		this.tblBrhFeeCfgZlfUpd = tblBrhFeeCfgZlfUpd;
	}

	public String getPromotionRate() {
		return promotionRate;
	}

	public void setPromotionRate(String promotionRate) {
		this.promotionRate = promotionRate;
	}

	public String getBaseAmtMonth() {
		return baseAmtMonth;
	}

	public void setBaseAmtMonth(String baseAmtMonth) {
		this.baseAmtMonth = baseAmtMonth;
	}

	public String getGradeAmtMonth() {
		return gradeAmtMonth;
	}

	public void setGradeAmtMonth(String gradeAmtMonth) {
		this.gradeAmtMonth = gradeAmtMonth;
	}

	public String getPromotionRateUpd() {
		return promotionRateUpd;
	}

	public void setPromotionRateUpd(String promotionRateUpd) {
		this.promotionRateUpd = promotionRateUpd;
	}

	public String getBaseAmtMonthUpd() {
		return baseAmtMonthUpd;
	}

	public void setBaseAmtMonthUpd(String baseAmtMonthUpd) {
		this.baseAmtMonthUpd = baseAmtMonthUpd;
	}

	public String getGradeAmtMonthUpd() {
		return gradeAmtMonthUpd;
	}

	public void setGradeAmtMonthUpd(String gradeAmtMonthUpd) {
		this.gradeAmtMonthUpd = gradeAmtMonthUpd;
	}

}
