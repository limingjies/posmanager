package com.allinfinance.bo.impl.mchnt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.BeanUtils;

import com.allinfinance.bo.mchnt.T20107BO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.TblMchntInfoConstants;
import com.allinfinance.common.TblOprInfoConstants;
import com.allinfinance.dao.iface.base.TblRouteRuleDAO;
import com.allinfinance.dao.iface.mchnt.ShTblOprInfoDAO;
import com.allinfinance.dao.iface.mchnt.TblBlukImpRetInfDAO;
import com.allinfinance.dao.iface.mchnt.TblHisDiscAlgoDAO;
import com.allinfinance.dao.iface.mchnt.TblInfDiscCdDAO;
import com.allinfinance.dao.iface.mchnt.TblMchntTpDAO;
import com.allinfinance.dao.iface.term.TblTermInfDAO;
import com.allinfinance.dao.iface.term.TblTermInfTmpDAO;
import com.allinfinance.dao.iface.term.TblTermKeyDAO;
import com.allinfinance.dao.iface.term.TblTermZmkInfDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtBaseInfDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtBaseInfTmpDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtSettleInfDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtSettleInfTmpDAO;
import com.allinfinance.dwr.term.BatchGetTmk;
import com.allinfinance.dwr.term.TermAdd;
import com.allinfinance.po.ShTblOprInfo;
import com.allinfinance.po.ShTblOprInfoPk;
import com.allinfinance.po.TblTermInf;
import com.allinfinance.po.TblTermInfTmp;
import com.allinfinance.po.TblTermKey;
import com.allinfinance.po.TblTermZmkInf;
import com.allinfinance.po.mchnt.TblBlukImpRetInf;
import com.allinfinance.po.mchnt.TblHisDiscAlgo;
import com.allinfinance.po.mchnt.TblHisDiscAlgoPK;
import com.allinfinance.po.mchnt.TblInfDiscCd;
import com.allinfinance.po.mchnt.TblInfMchntTp;
import com.allinfinance.po.mchnt.TblInfMchntTpPK;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInf;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmp;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.Encryption;
import com.allinfinance.system.util.GenerateNextId;
import com.allinfinance.system.util.SysParamUtil;
import com.allinfinance.system.util.excelReport;

public class T20107BOTarget implements T20107BO {

	private TblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO;
	private TblMchtSettleInfTmpDAO tblMchtSettleInfTmpDAO;
	private TblMchtBaseInfDAO tblMchtBaseInfDAO;
	private TblMchtSettleInfDAO tblMchtSettleInfDAO;
	private TblHisDiscAlgoDAO tblHisDiscAlgoDAO;
	private TblInfDiscCdDAO tblInfDiscCdDAO;
	private TblMchntTpDAO tblMchntTpDAO;
	private TblRouteRuleDAO tblRouteRuleDAO;
	private TblTermInfTmpDAO tblTermInfTmpDAO;
	private TblTermKeyDAO tblTermKeyDAO;
	private TblTermZmkInfDAO tblTermZmkInfDAO;
	private TblTermInfDAO tblTermInfDAO;
	private TblBlukImpRetInfDAO tblBlukImpRetInfDAO;
	private ShTblOprInfoDAO shTblOprInfoDAO;
	
	
	@SuppressWarnings("unchecked")
	public List<TblTermKey> importFile(List<TblMchtBaseInfTmp> mchtBaseInfList,
			List<TblMchtSettleInfTmp> mchtSettleInfList,
			Map<String, TblHisDiscAlgo> mchtdiskCdMap,
			Map<String, String> mccdiskCdMap,
			Map<String, String[]> termTelMap,Operator operator,boolean check) throws Exception {
		// 获取本次操作的批次号(批量导入标识位+"P_"+CommonFunction.getCurrentDate()+4位递增的序号)
		String batchHead = TblMchntInfoConstants.BLUK_IMP_MCHNT + "P_" + CommonFunction.getCurrentDate() + "_";
		String batchId = GenerateNextId.getBatchNo(batchHead);
		
		//商户号键值映射
		String mchtNo = null;
		String key = null;
		String value = null;
		Map<String, String> mchtNoMap=new HashMap<String, String>();
		//计费代码键值映射
		String diskCd = null;
		String diskCdKey = null;
		String diskCdValue = null;
		Map<String, String> diskCdMap=new HashMap<String, String>();
		
		//初始计费代码
		String diskCdInit = getDiscCd(operator.getOprBrhId());
		String discNm = null;
		
		//终端号键值映射
		String termId = null;
		String brhIdKey = null;
		String termIdValue = null;
		Map<String, String> termIdMap=new HashMap<String, String>();
		
		//待入商户终端信息
		List<TblTermInfTmp> tblTermInfList = new ArrayList<TblTermInfTmp>();
		List<TblTermKey> tblTermKeyList = new ArrayList<TblTermKey>();
		List<TblTermZmkInf> tblTermZmkInfList = new ArrayList<TblTermZmkInf>();
		@SuppressWarnings("rawtypes")
		List<List> termInfList = new ArrayList<List>(); // 以上3个List合集
		// 待入正式表的终端信息
		TblTermInf tblTermInf;
		TermAdd termAdd ;
		
/*		//初始化商户路由ID和路由规则代码
		String ruleIdIndex = getRuleId();
		String routeIndex = null;
		RouteAdd routeAdd ;
		//待入商户路由信息
		List<TblRouteRule> routeRuleList = new ArrayList<TblRouteRule>();*/
		
		//待入正式表的商户信息
		TblMchtBaseInf tblMchtBaseInf;
		TblMchtSettleInf tblMchtSettleInf;

		Iterator<TblMchtBaseInfTmp> mchtBaseIt = mchtBaseInfList.iterator();
		Iterator<TblMchtSettleInfTmp> mchtSettleIt = mchtSettleInfList.iterator();
		while(mchtBaseIt.hasNext()&&mchtSettleIt.hasNext()) {
			TblMchtBaseInfTmp tblMchtBaseInfTmpAdd = mchtBaseIt.next();
			TblMchtSettleInfTmp tblMchtSettleInfTmpAdd = mchtSettleIt.next();
			//给待批量录入的商户设商户号
			key = tblMchtBaseInfTmpAdd.getAreaNo() + tblMchtBaseInfTmpAdd.getMcc();
			if (!mchtNoMap.containsKey(key)){
				mchtNo = GenerateNextId.getBulkIptMchntId("848" + key);
				mchtNoMap.put(key, mchtNo.substring(11));
			}
			else{
				value= String.valueOf(Integer.parseInt(mchtNoMap.remove(key))+1);
				mchtNo = ("848" + key + value).toString();
				mchtNoMap.put(key, value);
			}
			diskCd = tblMchtSettleInfTmpAdd.getFeeRate();
			//给未添加费率的商户设置费率
			if (!diskCdMap.containsKey(diskCd)){
				if (mchtdiskCdMap.containsKey(diskCd)){
					diskCdKey = diskCd;
					diskCdValue = diskCdInit;
					
					tblMchtSettleInfTmpAdd.setFeeRate(diskCdValue);
					
					TblHisDiscAlgo temp = mchtdiskCdMap.remove(diskCd);
					TblHisDiscAlgoPK tblHisDiscAlgoPK = new TblHisDiscAlgoPK();
					tblHisDiscAlgoPK.setDiscId(diskCdValue);
					tblHisDiscAlgoPK.setIndexNum(0);
					temp.setId(tblHisDiscAlgoPK);
					
					TblInfDiscCd tblInfDiscCd = new TblInfDiscCd();
					tblInfDiscCd.setDiscCd(diskCdValue);
					tblInfDiscCd.setDiscOrg(operator.getOprBrhId());
					if(!String.valueOf(temp.getFlag()).equals("1")){
						if("999999999".equals(String.valueOf(temp.getMaxFee()))){
							discNm = (temp.getFeeValue() + "%").toString();
						} else{
							discNm = (temp.getFeeValue() + "%，" + String.valueOf(temp.getMaxFee()) + "封顶").toString();
						}
					} else {
						discNm = ("单笔" + temp.getFeeValue() + "元").toString();
					}
					tblInfDiscCd.setDiscNm(discNm);
					tblInfDiscCd.setLastOperIn("0");
					tblInfDiscCd.setRecUpdUserId(operator.getOprId());
					tblInfDiscCd.setRecUpdTs(CommonFunction.getCurrentDateTime());
					tblInfDiscCd.setRecCrtTs(CommonFunction.getCurrentDateTime());
					tblInfDiscCdDAO.save(tblInfDiscCd);// 商户手续费名称定义表
					tblHisDiscAlgoDAO.save(temp);
					
					diskCdMap.put(diskCdKey, diskCdValue);
					String key1 = null;
					for(Iterator<Entry<String, String>> iter = mccdiskCdMap.entrySet().iterator();iter.hasNext();){
						@SuppressWarnings("rawtypes")
						Map.Entry element = (Map.Entry)iter.next();
						if((element.getValue()).toString().contains(("," + diskCdKey + "#").toString())){
							key1 = (String) element.getKey();
							element.setValue(mccdiskCdMap.get(key1).replace(("," + diskCdKey + "#").toString(), ("," + diskCdValue).toString()));
						}
					}
					String temp1 = diskCdInit.substring(diskCdInit.length()-4);
					String temp2 = diskCdInit.substring(0,diskCdInit.length()-4);
					diskCdInit = (temp2 + CommonFunction.fillString(String.valueOf(Integer.parseInt(temp1)+1), '0', 4, false)).toString();
				}
			} else {
				tblMchtSettleInfTmpAdd.setFeeRate(diskCdMap.get(diskCd));
			}
			
			// 给商户配置路由（现已经废除 by 徐鹏飞 in 2015.01.14）
/*			routeIndex = tblMchtBaseInfTmpAdd.getReserved().split("\\|")[1];
			if(routeIndex.contains("TL006")||routeIndex.contains("TL005")){
				ruleIdIndex = String.valueOf(Integer.parseInt(ruleIdIndex)+1);
			}
			routeAdd = new RouteAdd();
			routeRuleList.addAll(routeAdd.getRouteRuleList(routeIndex, mchtNo, ruleIdIndex));
			ruleIdIndex = String.valueOf(Integer.parseInt(ruleIdIndex)+1);*/
			
			// 添加商户信息
			tblMchtBaseInfTmpAdd.setMchtNo(mchtNo);
			
			// 给待批量录入的商户添加终端
				//添加终端号
			brhIdKey = tblMchtBaseInfTmpAdd.getBankNo().substring(0, 2);
			String[] termTel = termTelMap.get(tblMchtBaseInfTmpAdd.getReserved());
			tblMchtBaseInfTmpAdd.setReserved(batchId);   // 此字段临时存放批量导入批次号，，可以作为商户批量导入的标志
			if (!termIdMap.containsKey(brhIdKey)){
				termId = getTermId(tblMchtBaseInfTmpAdd.getBankNo(), termTel.length);
				termIdMap.put(brhIdKey, termId.substring(3));
			}
			else{
				termIdValue= CommonFunction.fillString(String.valueOf(Integer.parseInt(termIdMap.remove(brhIdKey))+termTel.length), '0', 5, false);
				termId = ("0" + brhIdKey + termIdValue).toString();
				termIdMap.put(brhIdKey, termIdValue);
			}
			termAdd = new TermAdd() ;
			termInfList = termAdd.getTermInfList(termId, termTel, tblMchtBaseInfTmpAdd);
			tblTermInfList.addAll(termInfList.get(0));
			tblTermKeyList.addAll(termInfList.get(1));
			tblTermZmkInfList.addAll(termInfList.get(2));
			termInfList.clear();
			
			// 录入商户（临时表及正式表同时录入）
			tblMchtBaseInfTmpDAO.save(tblMchtBaseInfTmpAdd);
			tblMchtBaseInf = new TblMchtBaseInf();
			BeanUtils.copyProperties(tblMchtBaseInfTmpAdd, tblMchtBaseInf);
			tblMchtBaseInfDAO.save(tblMchtBaseInf);
			tblMchtSettleInfTmpAdd.setMchtNo(mchtNo);
			tblMchtSettleInfTmpDAO.save(tblMchtSettleInfTmpAdd);
			tblMchtSettleInf = new TblMchtSettleInf();
			BeanUtils.copyProperties(tblMchtSettleInfTmpAdd, tblMchtSettleInf);
			tblMchtSettleInfDAO.save(tblMchtSettleInf);
			// 系统是否自动添加此批次商户管理员(根据check值判断)
			if(check)
				addOprInfo(mchtNo);
		}
		
		// 添加商户终端信息
		Iterator<TblTermInfTmp> tblTermInfIt = tblTermInfList.iterator();
		Iterator<TblTermKey> tblTermKeyIt = tblTermKeyList.iterator();
		Iterator<TblTermZmkInf> tblTermZmkInfIt = tblTermZmkInfList.iterator();
		while(tblTermInfIt.hasNext() && tblTermKeyIt.hasNext() && tblTermZmkInfIt.hasNext()) {
			TblTermInfTmp tblTermInfTmpAdd = tblTermInfIt.next();
			TblTermKey tblTermKeyAdd = tblTermKeyIt.next();
			TblTermZmkInf tblTermZmkInfAdd = tblTermZmkInfIt.next();
			// 将终端信息复制到正式表
			tblTermInf = new TblTermInf();
			tblTermInf = (TblTermInf) tblTermInfTmpAdd.clone();
			tblTermInf.setTermPara(tblTermInf.getTermPara().replaceAll("\\|", ""));
			tblTermInfDAO.save(tblTermInf);
			tblTermInfTmpDAO.save(tblTermInfTmpAdd);
			tblTermKeyDAO.saveOrUpdate(tblTermKeyAdd);
			tblTermZmkInfDAO.saveOrUpdate(tblTermZmkInfAdd);
		}
		
		// 添加商户路由信息
/*		for(TblRouteRule tblRouteRule : routeRuleList)
			tblRouteRuleDAO.save(tblRouteRule);*/
		
		// 更新MCC对应的计费代码信息
		for(Iterator<Entry<String, String>> iter = mccdiskCdMap.entrySet().iterator();iter.hasNext();){
			@SuppressWarnings("rawtypes")
			Map.Entry element = (Map.Entry)iter.next();
			String mcc = (String) element.getKey();
			String dc = (String) element.getValue();
			// 读取MCC信息
			TblInfMchntTpPK tblInfMchntTpPK = new TblInfMchntTpPK();
			tblInfMchntTpPK.setMchntTp(mcc.substring(0,4));
			tblInfMchntTpPK.setFrnIn("0");	
			TblInfMchntTp tblInfMchntTp = tblMchntTpDAO.get(tblInfMchntTpPK);
			dc = (tblInfMchntTp.getDiscCd()+dc).toString();
			// 将计费代码集合存入MCC中
			tblInfMchntTp.setDiscCd(dc);
			tblMchntTpDAO.update(tblInfMchntTp);
		}
		// 便于回收内存
		mchtNoMap = null;
		diskCdMap = null;
		termIdMap = null;
		tblTermInfList = null;
		tblTermZmkInfList = null;
//		routeRuleList = null;
		return tblTermKeyList;
	}
	
	public void batchGetTmk(List<TblTermKey> tblTermKeyList) throws Exception{
		BatchGetTmk batchGetTmk = new BatchGetTmk();
		String brhId = null;
		String flag = null;
		List<TblTermKey> tblTermKeyOkList = new ArrayList<TblTermKey>();
		for(TblTermKey tblTermKey : tblTermKeyList){
			brhId = tblMchtBaseInfTmpDAO.get(tblTermKey.getId().getMchtCd()).getBankNo();
			flag = batchGetTmk.checkSpecial(brhId);
			if("0".equals(flag)){
				tblTermKeyOkList.add(tblTermKey);
			} else {
				tblTermKeyDAO.update(batchGetTmk.getTermKeyUpd(tblTermKey, flag));
			}
		}
		if(!tblTermKeyOkList.isEmpty()){
			// 批量生成终端密钥_by socket
			batchGetTmk.crtTmkBySocket(tblTermKeyOkList);
			// 生成TMK成功的数据更新tmk_st='1'
			TblTermKey tblTermKeyUpd;
			for(TblTermKey tblTermKey : tblTermKeyOkList){
				tblTermKeyUpd = tblTermKeyDAO.get(tblTermKey.getId());
				tblTermKeyUpd.setTmkSt("1");
				tblTermKeyDAO.update(tblTermKeyUpd);
			}
		}
		// 便于回收内存
		tblTermKeyOkList = null;
	};
	
	public String createRetFile(List<TblTermKey> tblTermKeyList,Operator operator) throws Exception{
		
		//商户号—终端键值映射
		String key = null;
		String[] value ;
		Map<String, String[]> mchtTermMap=new HashMap<String, String[]>();
		// 存放待反馈文件商户数据
		List<String[]> dataList = new ArrayList<String[]>();
		//待入查询的商户基本信息
		TblMchtBaseInfTmp tblMchtBaseInfTmp;
		String posTmk;
		for(TblTermKey tblTermKey : tblTermKeyList){
			key = tblTermKey.getId().getMchtCd();
			tblTermKey = tblTermKeyDAO.get(tblTermKey.getId());
			if("1".equals(tblTermKey.getTmkSt())){
				posTmk = tblTermKey.getPosTmk().trim();
			}
			else{
				posTmk = "未生成";
			}
			if (!mchtTermMap.containsKey(key)){
				tblMchtBaseInfTmp = tblMchtBaseInfTmpDAO.get(key);
				value = new String[8];
				value[0] = tblMchtBaseInfTmp.getReserved();
				value[1] = key;
				value[2] = tblTermKey.getId().getTermId();
				value[3] = tblMchtBaseInfTmp.getMchtNm();
				value[4] = tblInfDiscCdDAO.get(tblMchtSettleInfTmpDAO.get(key).getFeeRate()).getDiscNm();
				value[5] = tblMchtBaseInfTmp.getContact();
				value[6] = tblMchtBaseInfTmp.getCommTel();
				value[7] = posTmk;
				mchtTermMap.put(key, value);
			}
			else{
				mchtTermMap.get(key)[2]= mchtTermMap.get(key)[2]+"，"+tblTermKey.getId().getTermId();
				mchtTermMap.get(key)[7]= mchtTermMap.get(key)[7]+"，"+posTmk;
			}
		}
		for(Iterator<Entry<String, String[]>> iter = mchtTermMap.entrySet().iterator();iter.hasNext();){
			@SuppressWarnings("rawtypes")
			Map.Entry element = (Map.Entry)iter.next();
			String[] date = (String[]) element.getValue();
			dataList.add(date);
		}
		String batchId = dataList.get(0)[0].toString();
		
		// 统一文件名称
		String dateTime = CommonFunction.getCurrentDateTime();
		String fileName = batchId.substring(0, 3) + dateTime ;
		
		// 记录批量导入商户回执信息
		TblBlukImpRetInf tblBlukImpRetInf = new TblBlukImpRetInf();
		tblBlukImpRetInf.setBatchNo(batchId); //批次号
		tblBlukImpRetInf.setBlukDate(dateTime);  // 时间
		tblBlukImpRetInf.setBrhId(operator.getOprBrhId());  // 执行机构
		tblBlukImpRetInf.setOprId(operator.getOprId());  // 执行人员
		tblBlukImpRetInf.setBlukFileName(fileName);  // 回执文件名称
		tblBlukImpRetInf.setBlukMchnNum(String.valueOf(dataList.size()));  // 批导商户数量
		tblBlukImpRetInf.setMisc1(TblMchntInfoConstants.BLUK_IMP_MCHNT);  // 批量导入标识位：["M","商户批量导入"],["R","路由批量导入"]
		tblBlukImpRetInf.setMisc2("");  // 默认
		tblBlukImpRetInf.setMisc3("");  // 默认
		tblBlukImpRetInfDAO.save(tblBlukImpRetInf);
		
        String[] title={"批次号","批导商户号","拥有终端号","公司注册名称","费率信息","联系人名称","联系电话","TMK"};
        String head="商户批量导入回执文件_"+dateTime;
        String downUrl=SysParamUtil.getParam(SysParamConstants.FILE_DOWNLOAD_DISK) + SysParamUtil.getParam(SysParamConstants.MCHNT_BLUK_IMP_RET);
        fileName=downUrl + fileName + ".xls";
        
        HashMap<String, Object> map=new HashMap<String, Object>();
        
        map.put("dataList", dataList);
        map.put("title", title);
        map.put("fileName", fileName);
        map.put("head", head);
        
        excelReport.bulkIptDownload(map,downUrl);
		// 便于回收内存
        mchtTermMap = null;
        map = null;
        
		return Constants.SUCCESS_CODE;
	};
	
	public String getDiscCd(String oprBrhId){
		try {
			int max = 1;
			
			//判断是否存在序号为0001的ID
			String sql = "select count(*) from TBL_INF_DISC_CD where trim(DISC_CD) = '" + "JF" + oprBrhId.substring(0,2) + "0001" + "'" ;
			BigDecimal c = (BigDecimal) CommonFunction.getCommQueryDAO().findBySQLQuery(sql).get(0);
			if (c.intValue() != 0) {
				sql = "select nvl(MAX(SUBSTR(DISC_CD,5,4) + 1),1) from TBL_INF_DISC_CD " +
					"where (SUBSTR(DISC_CD,5,4) + 1) not in (select (SUBSTR(DISC_CD,5,4) + 0) " +
					"from TBL_INF_DISC_CD where substr(DISC_CD,3,2) = '" + oprBrhId.substring(0,2) + "') " +
					"and substr(DISC_CD,3,2) = '" + oprBrhId.substring(0,2) + "'";
				BigDecimal bg = (BigDecimal) CommonFunction.getCommQueryDAO().findBySQLQuery(sql).get(0);
				max = bg.intValue();
				if(max > 9999) {
					return "该机构的计费算法已满";
				}
			}
			
			String dc = "JF" + oprBrhId.substring(0,2) + CommonFunction.fillString(String.valueOf(max), '0', 4, false);

			return dc;
			
		} catch (Exception e) {
			e.printStackTrace();
			return "查询计费算法出错";
		}
	}
	
/*	public String getRuleId() {
		// TODO Auto-generated method stub
		String sql1 = "select count(*) from TBL_ROUTE_RULE " ;
		BigDecimal count = (BigDecimal) CommonFunction.getCommQueryDAO().findBySQLQuery(sql1).get(0);
		if (count.intValue() == 0 ) {
			return "1";
		}
		String sql = "select max(rule_id + 1) from TBL_ROUTE_RULE where (rule_id + 1) not in " +
		  "(select rule_id from TBL_ROUTE_RULE  ) " ;
		BigDecimal countNew = (BigDecimal) CommonFunction.getCommQueryDAO().findBySQLQuery(sql).get(0);
		return String.valueOf(countNew.intValue());
	}*/
	
	@SuppressWarnings("rawtypes")
	public String getTermId(String brhId, int termTelNum) {
		if(brhId == null || brhId.trim().equals(""))
			return Constants.FAILURE_CODE;
		if(brhId.length()>2)
			brhId = brhId.substring(0, 2);
		String sql = "select max(substr(term_Id,4,5))+" + termTelNum + " from TBL_TERM_INF_TMP where substr(term_id,2,2)='"+brhId+"'";
		List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		if(list == null || list.isEmpty() || list.get(0) == null)
			return 0+brhId+CommonFunction.fillString(String.valueOf(termTelNum), '0', 5, false);
		BigDecimal maxB = (BigDecimal)list.get(0);
		int max = maxB.intValue();
		if(max > 99999)
			max = 1;
		return 0+brhId + CommonFunction.fillString(String.valueOf(max), '0', 5, false);
	}
	
	// 给正式的商户添加初始商户管理员
	public void addOprInfo(String mchtNo) throws Exception {
		ShTblOprInfo shTblOprInfo = new ShTblOprInfo();
		ShTblOprInfoPk shTblOprInfoPk = new ShTblOprInfoPk();
		shTblOprInfoPk.setOprId(TblOprInfoConstants.DEFAULT_OPR_NO);
		shTblOprInfoPk.setBrhId("-");
		shTblOprInfoPk.setMchtNo(mchtNo);
		shTblOprInfo.setId(shTblOprInfoPk);
		shTblOprInfo.setOprName(TblOprInfoConstants.DEFAULT_OPR_NAME);
		shTblOprInfo.setMchtBrhFlag("0");
		shTblOprInfo.setCreateTime(CommonFunction.getCurrentDateTime());
		shTblOprInfo.setOprPwd(Encryption.encryptadd(SysParamUtil
				.getParam(SysParamConstants.OPR_DEFAULT_PWD)));
		shTblOprInfo.setPwdOutDate(CommonFunction.getOffSizeDate(CommonFunction
				.getCurrentDate(), SysParamUtil
				.getParam(SysParamConstants.OPR_PWD_OUT_DAY)));
		shTblOprInfo.setPwdWrTm(TblOprInfoConstants.PWD_WR_TM);
		shTblOprInfo.setRoleId(TblOprInfoConstants.SUP_MCHT_ROLE);
		shTblOprInfo.setPwdWrTmTotal(TblOprInfoConstants.PWD_WR_TM_TOTAL);
		shTblOprInfo.setOprStatus(TblOprInfoConstants.STATUS_INIT);
		shTblOprInfo.setCurrentLoginTime(TblOprInfoConstants.CURRENT_LOGIN_INFO);
		shTblOprInfo.setCurrentLoginIp(TblOprInfoConstants.CURRENT_LOGIN_INFO);
		shTblOprInfo.setCurrentLoginStatus(TblOprInfoConstants.CURRENT_LOGIN_INFO);
		shTblOprInfo.setPwdWrTmContinue(TblOprInfoConstants.PWD_WR_TM_CONTINUE);
		shTblOprInfoDAO.save(shTblOprInfo);
	}
	
	/**
	 * @return the tblMchtBaseInfTmpDAO
	 */
	public TblMchtBaseInfTmpDAO getTblMchtBaseInfTmpDAO() {
		return tblMchtBaseInfTmpDAO;
	}

	/**
	 * @param tblMchtBaseInfTmpDAO the tblMchtBaseInfTmpDAO to set
	 */
	public void setTblMchtBaseInfTmpDAO(TblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO) {
		this.tblMchtBaseInfTmpDAO = tblMchtBaseInfTmpDAO;
	}

	/**
	 * @return the tblMchtSettleInfTmpDAO
	 */
	public TblMchtSettleInfTmpDAO getTblMchtSettleInfTmpDAO() {
		return tblMchtSettleInfTmpDAO;
	}

	/**
	 * @param tblMchtSettleInfTmpDAO the tblMchtSettleInfTmpDAO to set
	 */
	public void setTblMchtSettleInfTmpDAO(
			TblMchtSettleInfTmpDAO tblMchtSettleInfTmpDAO) {
		this.tblMchtSettleInfTmpDAO = tblMchtSettleInfTmpDAO;
	}

	/**
	 * @return the tblMchtBaseInfDAO
	 */
	public TblMchtBaseInfDAO getTblMchtBaseInfDAO() {
		return tblMchtBaseInfDAO;
	}

	/**
	 * @param tblMchtBaseInfDAO the tblMchtBaseInfDAO to set
	 */
	public void setTblMchtBaseInfDAO(TblMchtBaseInfDAO tblMchtBaseInfDAO) {
		this.tblMchtBaseInfDAO = tblMchtBaseInfDAO;
	}

	/**
	 * @return the tblMchtSettleInfDAO
	 */
	public TblMchtSettleInfDAO getTblMchtSettleInfDAO() {
		return tblMchtSettleInfDAO;
	}

	/**
	 * @param tblMchtSettleInfDAO the tblMchtSettleInfDAO to set
	 */
	public void setTblMchtSettleInfDAO(TblMchtSettleInfDAO tblMchtSettleInfDAO) {
		this.tblMchtSettleInfDAO = tblMchtSettleInfDAO;
	}

	/**
	 * @return the tblHisDiscAlgoDAO
	 */
	public TblHisDiscAlgoDAO getTblHisDiscAlgoDAO() {
		return tblHisDiscAlgoDAO;
	}

	/**
	 * @param tblHisDiscAlgoDAO the tblHisDiscAlgoDAO to set
	 */
	public void setTblHisDiscAlgoDAO(TblHisDiscAlgoDAO tblHisDiscAlgoDAO) {
		this.tblHisDiscAlgoDAO = tblHisDiscAlgoDAO;
	}

	/**
	 * @return the tblInfDiscCdDAO
	 */
	public TblInfDiscCdDAO getTblInfDiscCdDAO() {
		return tblInfDiscCdDAO;
	}

	/**
	 * @param tblInfDiscCdDAO the tblInfDiscCdDAO to set
	 */
	public void setTblInfDiscCdDAO(TblInfDiscCdDAO tblInfDiscCdDAO) {
		this.tblInfDiscCdDAO = tblInfDiscCdDAO;
	}

	/**
	 * @return the tblMchntTpDAO
	 */
	public TblMchntTpDAO getTblMchntTpDAO() {
		return tblMchntTpDAO;
	}

	/**
	 * @param tblMchntTpDAO the tblMchntTpDAO to set
	 */
	public void setTblMchntTpDAO(TblMchntTpDAO tblMchntTpDAO) {
		this.tblMchntTpDAO = tblMchntTpDAO;
	}

	/**
	 * @return the tblRouteRuleDAO
	 */
	public TblRouteRuleDAO getTblRouteRuleDAO() {
		return tblRouteRuleDAO;
	}

	/**
	 * @param tblRouteRuleDAO the tblRouteRuleDAO to set
	 */
	public void setTblRouteRuleDAO(TblRouteRuleDAO tblRouteRuleDAO) {
		this.tblRouteRuleDAO = tblRouteRuleDAO;
	}

	/**
	 * @return the tblTermInfTmpDAO
	 */
	public TblTermInfTmpDAO getTblTermInfTmpDAO() {
		return tblTermInfTmpDAO;
	}

	/**
	 * @param tblTermInfTmpDAO the tblTermInfTmpDAO to set
	 */
	public void setTblTermInfTmpDAO(TblTermInfTmpDAO tblTermInfTmpDAO) {
		this.tblTermInfTmpDAO = tblTermInfTmpDAO;
	}

	/**
	 * @return the tblTermKeyDAO
	 */
	public TblTermKeyDAO getTblTermKeyDAO() {
		return tblTermKeyDAO;
	}

	/**
	 * @param tblTermKeyDAO the tblTermKeyDAO to set
	 */
	public void setTblTermKeyDAO(TblTermKeyDAO tblTermKeyDAO) {
		this.tblTermKeyDAO = tblTermKeyDAO;
	}

	/**
	 * @return the tblTermZmkInfDAO
	 */
	public TblTermZmkInfDAO getTblTermZmkInfDAO() {
		return tblTermZmkInfDAO;
	}

	/**
	 * @param tblTermZmkInfDAO the tblTermZmkInfDAO to set
	 */
	public void setTblTermZmkInfDAO(TblTermZmkInfDAO tblTermZmkInfDAO) {
		this.tblTermZmkInfDAO = tblTermZmkInfDAO;
	}

	/**
	 * @return the tblTermInfDAO
	 */
	public TblTermInfDAO getTblTermInfDAO() {
		return tblTermInfDAO;
	}

	/**
	 * @param tblTermInfDAO the tblTermInfDAO to set
	 */
	public void setTblTermInfDAO(TblTermInfDAO tblTermInfDAO) {
		this.tblTermInfDAO = tblTermInfDAO;
	}

	/**
	 * @return the tblBlukImpRetInfDAO
	 */
	public TblBlukImpRetInfDAO getTblBlukImpRetInfDAO() {
		return tblBlukImpRetInfDAO;
	}

	/**
	 * @param tblBlukImpRetInfDAO the tblBlukImpRetInfDAO to set
	 */
	public void setTblBlukImpRetInfDAO(TblBlukImpRetInfDAO tblBlukImpRetInfDAO) {
		this.tblBlukImpRetInfDAO = tblBlukImpRetInfDAO;
	}

	/**
	 * @return the shTblOprInfoDAO
	 */
	public ShTblOprInfoDAO getShTblOprInfoDAO() {
		return shTblOprInfoDAO;
	}

	/**
	 * @param shTblOprInfoDAO the shTblOprInfoDAO to set
	 */
	public void setShTblOprInfoDAO(ShTblOprInfoDAO shTblOprInfoDAO) {
		this.shTblOprInfoDAO = shTblOprInfoDAO;
	}
	
}
