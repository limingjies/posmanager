package com.allinfinance.bo.impl.dataImport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.allinfinance.bo.base.T10101BO;
import com.allinfinance.bo.dataImport.DataImportBO;
import com.allinfinance.common.Constants;
import com.allinfinance.common.Operator;
import com.allinfinance.common.StringUtil;
import com.allinfinance.common.SysParamConstants;
import com.allinfinance.common.TblMchntInfoConstants;
import com.allinfinance.common.TblOprInfoConstants;
import com.allinfinance.common.msg.Msg;
import com.allinfinance.common.msg.MsgEntity;
import com.allinfinance.commquery.dao.ICommQueryDAO;
import com.allinfinance.dao.base.ITblImportMchtDao;
import com.allinfinance.dao.base.ITblImportTermDao;
import com.allinfinance.dao.iface.mchnt.ITblMchtBaseInfTmpTmpDAO;
import com.allinfinance.dao.iface.mchnt.ITblMchtSettleInfTmpTmpDAO;
import com.allinfinance.dao.iface.mchnt.ShTblOprInfoDAO;
import com.allinfinance.dao.iface.mchnt.TblHisDiscAlgoDAO;
import com.allinfinance.dao.iface.mchnt.TblInfDiscCdDAO;
import com.allinfinance.dao.iface.risk.TblRiskParamMngDAO;
import com.allinfinance.dao.iface.term.TblTermInfDAO;
import com.allinfinance.dao.iface.term.TblTermInfTmpDAO;
import com.allinfinance.dao.iface.term.TblTermKeyDAO;
import com.allinfinance.dao.iface.term.TblTermZmkInfDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtBaseInfDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtBaseInfTmpDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtSettleInfDAO;
import com.allinfinance.dao.impl.mchnt.TblMchtSettleInfTmpDAO;
import com.allinfinance.log.Log;
import com.allinfinance.po.ShTblOprInfo;
import com.allinfinance.po.ShTblOprInfoPk;
import com.allinfinance.po.TblBrhInfo;
import com.allinfinance.po.TblTermInf;
import com.allinfinance.po.TblTermInfTmp;
import com.allinfinance.po.TblTermInfTmpPK;
import com.allinfinance.po.TblTermKey;
import com.allinfinance.po.TblTermKeyPK;
import com.allinfinance.po.TblTermZmkInf;
import com.allinfinance.po.TblTermZmkInfPK;
import com.allinfinance.po.base.TblImportMcht;
import com.allinfinance.po.base.TblImportTerm;
import com.allinfinance.po.mchnt.TblHisDiscAlgo;
import com.allinfinance.po.mchnt.TblHisDiscAlgoPK;
import com.allinfinance.po.mchnt.TblInfDiscCd;
import com.allinfinance.po.mchnt.TblMchtBaseInf;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmp;
import com.allinfinance.po.mchnt.TblMchtBaseInfTmpTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInf;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmp;
import com.allinfinance.po.mchnt.TblMchtSettleInfTmpTmp;
import com.allinfinance.po.risk.TblRiskParamMng;
import com.allinfinance.po.risk.TblRiskParamMngPK;
import com.allinfinance.system.util.BeanUtils;
import com.allinfinance.system.util.CommonFunction;
import com.allinfinance.system.util.Encryption;
import com.allinfinance.system.util.GenerateNextId;
import com.allinfinance.system.util.SysParamUtil;

public class DataImportBOTarget implements DataImportBO {
	private TblMchtBaseInfDAO tblMchtBaseInfDAO;
	private TblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO;
	private ITblMchtBaseInfTmpTmpDAO tblMchtBaseInfTmpTmpDAO;
	private TblMchtSettleInfDAO tblMchtSettleInfDAO;
	private ITblMchtSettleInfTmpTmpDAO tblMchtSettleInfTmpTmpDAO;
	private TblMchtSettleInfTmpDAO tblMchtSettleInfTmpDAO;
	private TblInfDiscCdDAO tblInfDiscCdDAO;
	private TblHisDiscAlgoDAO tblHisDiscAlgoDAO;
	private ShTblOprInfoDAO shTblOprInfoDAO;
	private T10101BO t10101BO;
	private TblTermInfTmpDAO tblTermInfTmpDAO;
	private TblTermInfDAO tblTermInfDAO;
	private TblTermKeyDAO tblTermKeyDAO;
	private TblTermZmkInfDAO tblTermZmkInfDAO;
	private ICommQueryDAO commQueryDAO;
	private ITblImportMchtDao tblImportMchtDao;
	private ITblImportTermDao tblImportTermDao;
	private TblRiskParamMngDAO tblRiskParamMngDAO;

	@Override
	public List<Object> mchtImportData(String[] records, Operator operator,
			String importTime) throws Exception {
		List<Object> resultList = new ArrayList<Object>();
		String orgMchtNo = records[0];// 商户编号
		String bankNo = records[1];// 合作伙伴号
		String mchtSta = records[2];// 商户状态
		String mchtType = records[3];// 商户类型
		String mchtNm = records[4];// 注册名称
		String mchtCn = records[5];// 中文简称
		String compAddr = records[6];// 注册地址
		String busAmt = records[7];// 注册资金
		String licenceNo = records[8];// 营业执照编号
		String licenceEndDate = records[9];// 营业执照有效期
		String faxNo = records[10];// 税务登记证
		String manager = records[11];// 法人代表
		String artifCritifTp = records[12];// 法人证件类型
		String identityNo = records[13];// 法人证件号码
		String prolDate = records[14];// 证件有效期
		String compNm = records[15];// 经营名称
		String addrProvince = records[16];// 装机地址-省
		String areaNo = records[17];// 装机地址-市
		String addr = records[18];// 装机地址-详细地址
		String openTime = records[19];// 营业时间-开始
		String closeTime = records[20];// 营业时间-结束
		String busArea = records[21];// 经营面积
		String contact = records[22];// 业务联系人
		String commTel = records[23];// 电话
		String commEmail = records[24];// 企业邮箱
		String settleAcctNm = records[25];// 账户户名
		String settleAcctType = records[26];// 结算账户类型
		String settleAcct = records[27];// 账户号
		String settleBankNm = records[29];// 开户银行-银行
		String openStlNo = "";// 开户银行-银行支行行号
		String mchtBak2 = records[30];// 手续费方式
		String jFee = records[31];// 借记卡-比例
		String jMaxFee = records[32];// 借记卡-封顶
		String dFee = records[33];// 贷记卡-比例
		String dMaxFee = records[34];// 贷记卡-封顶
		String speSettleTp1 = records[35];// 分润方式
		String speSettleTp2 = records[36];// 分润比例档位
		String speSettleTp3 = records[37];// 分润封顶档位
		String reserved = records[38];// 备注
		String mcc = records[39];// 商户MCC
		String risLevel = records[40];// 商户风险级别
		String compliance = records[41];// 是否完全合规
		String busInfo = records[42];// 经营内容
		String mchtGrp = "";// records[42];// 商户组别
		String country = records[43];// 是否县乡商户
		String bankStatement = records[44];// 是否需要对账单
		String bankStatementReason = records[45];// 需要对账单理由
		String integral = records[46];// 是否积分
		String integralReason = records[47];// 需要积分理由
		String emergency = records[48];// 是否紧急
		String applyNo = records[49];// 申请单号
		String tPlusN = records[50];
		// 验证必输字段
		// T+N
		if("0".equals(tPlusN)){
			tPlusN = "0 000";
		} else if ("1".equals(tPlusN)){
			tPlusN = "0 001";
		} else {
			resultList.add("T+N字段值不正确！");
			return resultList;
		}
		// 商户状态
		if (StringUtils.isNotBlank(mchtSta)) {
			if ("开通".equals(mchtSta)) {
				mchtSta = "0";
			} else if ("停用".equals(mchtSta)) {
				mchtSta = "6";
			} else if ("注销".equals(mchtSta)) {
				mchtSta = "8";
			}
		} else{
			resultList.add("商户状态为空！");
			return resultList;
		}
		// 商户类型
		if (StringUtils.isNotBlank(mchtType)) {
			if ("公司商户".equals(mchtType)) {
				mchtType = "01";
			} else if ("个体商户".equals(mchtType)) {
				mchtType = "02";
			} else if ("无执照商户".equals(mchtType)) {
				mchtType = "03";
			}
		} else{
			resultList.add("商户类型为空！");
			return resultList;
		}

		// 法人证件类型
		if (StringUtils.isNotBlank(artifCritifTp)) {
			if ("身份证".equals(artifCritifTp)) {
				artifCritifTp = "01";
			} else
				artifCritifTp = "99";
		} else
			artifCritifTp = "99";
		// 装机地址--省
		if (StringUtils.isNotBlank(addrProvince)) {
			String queryProvince = "select REGION_CODE_BUSI from TBL_REGION_INFO WHERE SUPER_REGION_CODE_BUSI='0000' AND REGION_NAME = '"
					+ addrProvince + "'";
			List<String> provinceList = CommonFunction.getCommQueryDAO()
					.findBySQLQuery(queryProvince);
			if (provinceList != null && provinceList.size() > 0) {
				addrProvince = provinceList.get(0);
			} else{
				resultList.add("装机地址--省未找到匹配的编码！");
				return resultList;
			}
		}
		// 装机地址-市
		if (StringUtils.isNotBlank(areaNo)) {
			String queryCity = "select REGION_CODE_BUSI from TBL_REGION_INFO WHERE SUPER_REGION_CODE_BUSI !='0000' AND REGION_NAME = '"
					+ areaNo + "'";
			List<String> cityList = CommonFunction.getCommQueryDAO()
					.findBySQLQuery(queryCity);
			if (cityList != null && cityList.size() > 0) {
				areaNo = cityList.get(0);
			} else{
				resultList.add("装机地址--市未找到匹配的编码！");
				return resultList;
			}
		}
		// 结算账户类型
		if (StringUtils.isNotBlank(settleAcctType)) {
			if ("对公".equals(settleAcctType)) {
				settleAcctType = "0";
			} else
				settleAcctType = "1";
		} else{
			resultList.add("结算账户类型为空！");
			return resultList;
		}
		// 开户行--支行号
		if (StringUtils.isNotBlank(settleBankNm)) {
			String subBranch = "select SUBBRANCH_ID from TBL_SUBBRANCH_INFO WHERE SUBBRANCH_NAME ='"
					+ settleBankNm + "' ";
			List<String> subBranchList = CommonFunction.getCommQueryDAO()
					.findBySQLQuery(subBranch);
			if (subBranchList != null && subBranchList.size() > 0) {
				openStlNo = subBranchList.get(0);
			} else{
				resultList.add("开户行-支行号未找到！");
				return resultList;
			}
		} else{
			resultList.add("开户行-支行号为空！");
			return resultList;
		}
		// 手续费方式
		if (StringUtils.isNotBlank(mchtBak2)) {
			if ("百分比+封顶结算".equals(mchtBak2)) {
				mchtBak2 = "1";
			} else if ("百分比结算".equals(mchtBak2)) {
				mchtBak2 = "0";
			} else {
				resultList.add("手续费方式类型不正确！");
				return resultList;
			}
		} else{
			resultList.add("手续费类型为空！");
			return resultList;
		}
		// 分润方式
		if (StringUtils.isNotBlank(speSettleTp1)
				&& StringUtils.isNotBlank(speSettleTp2)
				&& StringUtils.isNotBlank(speSettleTp3)) {
			if ("百分比+封顶结算".equals(speSettleTp1)) {
				speSettleTp1 = "1";
				String speSql1 = "SELECT RATE_ID FROM TBL_PROFIT_RATE_INFO WHERE FEE_NAME = '"
						+ speSettleTp3 + "'";// 封顶
				String speSql2 = "SELECT RATE_ID FROM TBL_PROFIT_RATE_INFO WHERE FEE_NAME = '"
						+ speSettleTp2 + "'";// 比例
				List<String> query1 = CommonFunction.getCommQueryDAO()
						.findBySQLQuery(speSql1);
				List<String> query2 = CommonFunction.getCommQueryDAO()
						.findBySQLQuery(speSql2);
				if (query1 != null && query1.size() > 0) {
					speSettleTp3 = query1.get(0).substring(0, 2);
				} else{
					resultList.add("根据分润封顶值，未查询到对应ID！");
					return resultList;
				}
				if (query2 != null && query2.size() > 0) {
					speSettleTp2 = query2.get(0).substring(0, 2);
				} else{
					resultList.add("根据分润比例值，未查询到对应ID！");
					return resultList;
				}

			} else if ("百分比结算".equals(speSettleTp1)) {
				String speSql = "SELECT RATE_ID FROM TBL_PROFIT_RATE_INFO WHERE FEE_NAME = '"
						+ speSettleTp2 + "'";// 比例
				List<String> query = CommonFunction.getCommQueryDAO()
						.findBySQLQuery(speSql);
				if (query != null && query.size() > 0) {
					speSettleTp2 = query.get(0).substring(0, 2);
				} else{
					resultList.add("根据分润比例值，未查询到对应ID！");
					return resultList;
				}
				speSettleTp3 = "  ";
				speSettleTp1 = "0";
			}

		} else{
			resultList.add("分润信息为空!");
			return resultList;
		}
		// 商户风险等级
		if (StringUtils.isNotBlank(risLevel)) {
			if ("高".equals(risLevel)) {
				risLevel = "1";
			} else if ("中".equals(risLevel)) {
				risLevel = "2";
			} else if ("低".equals(risLevel)) {
				risLevel = "3";
			} else
				risLevel = "5";
		} else{
			resultList.add("商户风险等级为空！");
			return resultList;
		}
		// 商户组别
		if (StringUtils.isNotBlank(mcc)) {
			String sql = "SELECT MCHNT_TP_GRP FROM TBL_INF_MCHNT_TP WHERE MCHNT_TP = '"
					+ mcc.trim() + "'";
			List<String> list = CommonFunction.getCommQueryDAO()
					.findBySQLQuery(sql);
			if (list != null && list.size() > 0) {
				mchtGrp = list.get(0);
			} else{
				resultList.add("根据商户MCC找不到对应的商户组别！");
				return resultList;
			}
		} else{
			resultList.add("MCC为空！");
			return resultList;
		}
		// 是否合规
		if (StringUtils.isNotBlank(compliance)) {
			if ("是".equals(compliance)) {
				compliance = "0";
			} else
				compliance = "1";
		} else
			compliance = "1";// 否
		// 是否县乡
		if (StringUtils.isNotBlank(country)) {
			if ("是".equals(country)) {
				country = "0";
			} else
				country = "1";
		} else
			country = "1";// 否
		// 是否对账单
		if (StringUtils.isNotBlank(bankStatement)) {
			if ("是".equals(bankStatement)) {
				bankStatement = "0";
			} else
				bankStatement = "1";
		} else
			bankStatement = "1";// 否
		// 是否积分
		if (StringUtils.isNotBlank(integral)) {
			if ("是".equals(integral)) {
				integral = "0";
			} else
				integral = "1";
		} else
			integral = "1";// 否
		// 是否紧急
		if (StringUtils.isNotBlank(emergency)) {
			if ("是".equals(emergency)) {
				emergency = "1";
			} else
				emergency = "0";
		} else
			emergency = "0";// 否
		// 税务登记证
		if (StringUtils.isBlank(faxNo)) {
			faxNo = "-";
		}
		// 注册地址/装机地址超过30个汉字
		if (compAddr.length() >= 30) {
			compAddr = compAddr.substring(0, 30);
		}
		if (addr.length() >= 30) {
			addr = addr.substring(0, 30);
		}
		// 业务联系人
		if (StringUtils.isBlank(contact)) {
			contact = "-";
		}
		// 税务登记证
		if (StringUtils.isBlank(faxNo)) {
			faxNo = "-";
		}
		// 生成新商户号
		String newMchtNo = "";
		if (StringUtils.isNotBlank(mcc) && StringUtils.isNotBlank(areaNo)) {
			String idStr = "848" + areaNo.trim() + mcc.trim();
			newMchtNo = GenerateNextId.getMchntId(idStr);
		} else {
			// 不能生成新商户号，记录错误
			resultList.add("生成商户号失败，mcc或地区码为空!");
			return resultList;
		}
		// 业务联系人
		if (StringUtils.isBlank(contact)) {
			contact = "-";
		}
		// 注册地址
		if (StringUtils.isBlank(compAddr)) {
			compAddr = "-";
		}
		// 装机地址
		if (StringUtils.isBlank(addr)) {
			addr = "-";
		}
		TblMchtBaseInf baseInf = new TblMchtBaseInf();// 商户
		TblMchtSettleInf settleInf = new TblMchtSettleInf();// 清算
		TblInfDiscCd infDiscCd = new TblInfDiscCd();

		TblBrhInfo tblBrhInfo = t10101BO.get(bankNo);
		if (tblBrhInfo == null) {
			resultList.add("查不到该商户对应的合作伙伴！");
			return resultList;
		}
		String createNewNo = tblBrhInfo.getCreateNewNo();
		baseInf.setMchtNo(newMchtNo);
		baseInf.setMchtGroupFlag("1");
		baseInf.setAddrProvince(addrProvince);
		baseInf.setPoundage(mchtBak2);
		baseInf.setAcqInstId(tblBrhInfo.getCupBrhId());
		baseInf.setAgrBr(bankNo);
		baseInf.setSignInstId(bankNo);
		baseInf.setContstartDate("");
		baseInf.setContendDate("");
		baseInf.setCompaddr(compAddr);// 注册地址
		baseInf.setProlDate(prolDate);// 证件有效期
		baseInf.setCompNm(compNm);// 经营名称
		baseInf.setBusArea(busArea);// 经营面积
		baseInf.setBusInfo(busInfo);// 经营内容
		// 对象赋值--商户+清算
		if (StringUtils.isNotBlank(mchtType) && "03".equals(mchtType)) {// 是否是无营业执照商户
			baseInf.setFaxNo("-");
			baseInf.setLicenceNo("-");
			baseInf.setLicenceEndDate("-");
		} else {
			baseInf.setFaxNo(faxNo);
			baseInf.setLicenceNo(licenceNo);
			baseInf.setLicenceEndDate(licenceEndDate);
		}

		baseInf.setMchtGroupId("-");//
		baseInf.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_NEW_FIRST_UNCK);
		// 修改页面营业时间
		baseInf.setOpenTime(CommonFunction.timeFormat(openTime));
		baseInf.setCloseTime(CommonFunction.timeFormat(closeTime));
		// 是否支持无磁无密交易
		baseInf.setPassFlag("0");
		// 是否支持人工授权交易
		baseInf.setManuAuthFlag("0");
		// 是否支持折扣消费
		baseInf.setDiscConsFlg("0");

		// 是否仅营业时间内交易
		baseInf.setMchtMngMode("0");
		// 申请日期
		baseInf.setApplyDate(CommonFunction.getCurrentDate());
		// 记录修改时间
		baseInf.setRecUpdTs(importTime);
		// 记录创建时间
		baseInf.setRecCrtTs(importTime);
		// 记录修改人
		baseInf.setUpdOprId("");
		// 记录创建人
		baseInf.setCrtOprId(operator.getOprId());
		baseInf.setRislLvl("0");
		baseInf.setMchtLvl("2");// 2-普通商户
		baseInf.setSaAction("0");
		baseInf.setCupMchtFlg("0");
		baseInf.setDebMchtFlg("0");
		baseInf.setCreMchtFlg("0");
		baseInf.setCdcMchtFlg("0");
		baseInf.setManagerTel("1");
		settleInf.setMchtNo(newMchtNo);
		settleInf.setSettleAcct(settleAcctType.substring(0, 1) + settleAcct);
		settleInf.setSettleType("1");
		settleInf.setRateFlag("-");
		settleInf.setFeeType("3"); // 分段收费
		settleInf.setFeeFixed("-");
		settleInf.setFeeMaxAmt("0");
		settleInf.setFeeMinAmt("0");
		settleInf.setReserved(reserved);// 商户备注
		settleInf.setFeeDiv1("-");
		settleInf.setFeeDiv2("-");
		settleInf.setFeeDiv3("-");
		// 积分、对账单
		settleInf.setBankStatement(bankStatement);
		settleInf.setBankStatementReason(bankStatementReason);
		settleInf.setIntegral(integral);
		settleInf.setIntegralReason(integralReason);
		// 是否自动清算
		settleInf.setAutoStlFlg("0");
		// 退货是否返还手续费
		settleInf.setFeeBackFlg("1");
		settleInf.setEmergency(emergency);

		// 记录修改时间
		settleInf.setRecUpdTs(importTime);
		// 记录创建时间
		settleInf.setRecCrtTs(importTime);
		// 内部账户号
		settleInf.setSettleBankNo(" ");
		settleInf.setFeeDiv1("1");
		settleInf.setFeeDiv2("2");
		settleInf.setFeeDiv3("3");
		baseInf.setMchtGrp(mchtGrp);// 商户组别
		baseInf.setMcc(mcc);
		baseInf.setRislLvl(risLevel);
		settleInf.setCompliance(compliance);
		settleInf.setCountry(country);
		baseInf.setMchtCnAbbr(mchtCn);
		baseInf.setMchtStatus(TblMchntInfoConstants.MCHNT_ST_OK);
		// T0,T1
		baseInf.setMchtFunction(tPlusN);// 结算类型
		// 提现-未开通
		baseInf.setCashFlag("0");
		baseInf.setAreaNo(areaNo);
		baseInf.setConnType("J");
		// baseInf.setMchtGroupFlag(tmp.getMchtGroupFlag());
		// baseInf.setMchtGroupId(tmp.getMchtGroupId());
		baseInf.setBankNo(bankNo);
		baseInf.setMchtNm(mchtNm);
		baseInf.setAddr(addr);
		baseInf.setEtpsAttr(mchtType);// 商户类型可能要跟本地匹配
		baseInf.setCommEmail(commEmail);
		baseInf.setManager(manager);
		baseInf.setBusAmt(busAmt);
		baseInf.setArtifCertifTp(artifCritifTp);
		baseInf.setIdentityNo(identityNo);
		baseInf.setContact(contact);
		baseInf.setCommTel(commTel);
		settleInf.setSettleBankNm(settleBankNm);
		settleInf.setSettleAcctNm(settleAcctNm);
		// 记录修改时间
		baseInf.setRecUpdTs(importTime);
		// 记录修改时间
		settleInf.setRecUpdTs(importTime);
		// 记录修改人
		baseInf.setUpdOprId(operator.getOprId());//
		// 设置费率

		settleInf.setSpeSettleTp(speSettleTp1 + speSettleTp2 + speSettleTp3);// 分润
		settleInf.setOpenStlno(openStlNo);// 开户行-支行号
		// 费率信息
		int max = 1;
		// 判断是否存在序号为0001的ID
		String sql = "select count(*) from TBL_INF_DISC_CD where trim(DISC_CD) = '"
				+ "JF" + bankNo.substring(0, 2) + "0001" + "'";
		BigDecimal c = (BigDecimal) CommonFunction.getCommQueryDAO()
				.findBySQLQuery(sql).get(0);
		if (c.intValue() != 0) {
			sql = "select nvl(MIN(SUBSTR(DISC_CD,5,4) + 1),1) from TBL_INF_DISC_CD "
					+ "where (SUBSTR(DISC_CD,5,4) + 1) not in (select (SUBSTR(DISC_CD,5,4) + 0) "
					+ "from TBL_INF_DISC_CD where substr(DISC_CD,3,2) = '"
					+ bankNo.substring(0, 2)
					+ "') "
					+ "and substr(DISC_CD,3,2) = '"
					+ bankNo.substring(0, 2)
					+ "'";
			BigDecimal bg = (BigDecimal) CommonFunction.getCommQueryDAO()
					.findBySQLQuery(sql).get(0);
			max = bg.intValue();
			if (max > 9999) {
				throw new Exception("该机构的计费算法已满");
			}
		}

		String discCd = "JF" + bankNo.substring(0, 2)
				+ CommonFunction.fillString(String.valueOf(max), '0', 4, false);
		settleInf.setFeeRate(discCd);// 根据本地规则生成
		if (StringUtils.isBlank(jFee)) {
			jFee = "0";
		}
		if (StringUtils.isBlank(dFee)) {
			dFee = "0";
		}
		if (StringUtils.isBlank(jMaxFee)) {
			jMaxFee = "0";
		}
		if (StringUtils.isBlank(dMaxFee)) {
			dMaxFee = "0";
		}
		infDiscCd.setDiscCd(discCd);
		infDiscCd.setDiscOrg(bankNo);
		infDiscCd.setDiscNm("0 001");// T+N
		infDiscCd.setLastOperIn("0");
		// inf.setRecUpdTs(CommonFunction.getCurrentDateTime());
		infDiscCd.setRecUpdTs(importTime);
		infDiscCd.setRecCrtTs(importTime);
		infDiscCd.setRecUpdUserId(operator.getOprId());

		TblHisDiscAlgo temp = new TblHisDiscAlgo();// 借记卡
		TblHisDiscAlgo temp1 = new TblHisDiscAlgo();// 贷记卡
		TblHisDiscAlgoPK key = new TblHisDiscAlgoPK();
		TblHisDiscAlgoPK key1 = new TblHisDiscAlgoPK();
		// 借记卡
		key.setDiscId(discCd);

		key.setIndexNum(0);
		temp.setId(key);
		temp.setMinFee(new BigDecimal(0));
		temp.setMaxFee(new BigDecimal(Double.parseDouble(jMaxFee)));
		temp.setTxnNum("0000");
		temp.setCardType("00");

		temp.setFeeValue(new BigDecimal(Double.parseDouble(jFee)));
		temp.setFloorMount(BigDecimal.ZERO);
		temp.setFlag(2);// 1-按笔收费2-按比例收费
		temp.setUpperMount(BigDecimal.ZERO);
		temp.setRecUpdUsrId(operator.getOprId());
		// temp.setRecUpdTs(CommonFunction.getCurrentDateTime());
		temp.setRecUpdTs(importTime);
		temp.setRecCrtTs(importTime);
		// 贷记卡
		key1.setDiscId(discCd);
		key1.setIndexNum(1);
		temp1.setId(key1);
		temp1.setMinFee(new BigDecimal(0));
		temp1.setMaxFee(new BigDecimal(Double.parseDouble(dMaxFee)));
		temp1.setTxnNum("0000");
		temp1.setCardType("01");

		temp1.setFeeValue(new BigDecimal(Double.parseDouble(dFee)));
		temp1.setFloorMount(BigDecimal.ZERO);
		temp1.setFlag(2);// 1-按笔收费2-按比例收费
		temp1.setUpperMount(BigDecimal.ZERO);
		temp1.setRecUpdUsrId(operator.getOprId());
		// temp1.setRecUpdTs(CommonFunction.getCurrentDateTime());
		temp1.setRecUpdTs(importTime);
		temp1.setRecCrtTs(importTime);

		// 操作员
		ShTblOprInfo shTblOprInfo = new ShTblOprInfo();
		ShTblOprInfoPk shTblOprInfoPk = new ShTblOprInfoPk();
		shTblOprInfoPk.setOprId(TblOprInfoConstants.DEFAULT_OPR_NO);
		shTblOprInfoPk.setBrhId("-");
		shTblOprInfoPk.setMchtNo(newMchtNo);
		shTblOprInfo.setId(shTblOprInfoPk);
		shTblOprInfo.setOprName(TblOprInfoConstants.DEFAULT_OPR_NAME);
		shTblOprInfo.setMchtBrhFlag("0");
		shTblOprInfo.setCreateTime(CommonFunction.getCurrentDateTime());
		shTblOprInfo.setOprPwd(Encryption.encryptadd(SysParamUtil
				.getParam(SysParamConstants.OPR_DEFAULT_PWD)));
		shTblOprInfo.setPwdOutDate(CommonFunction.getOffSizeDate(
				CommonFunction.getCurrentDate(),
				SysParamUtil.getParam(SysParamConstants.OPR_PWD_OUT_DAY)));
		shTblOprInfo.setPwdWrTm(TblOprInfoConstants.PWD_WR_TM);
		shTblOprInfo.setRoleId(TblOprInfoConstants.SUP_MCHT_ROLE);
		shTblOprInfo.setPwdWrTmTotal(TblOprInfoConstants.PWD_WR_TM_TOTAL);
		shTblOprInfo.setOprStatus(TblOprInfoConstants.STATUS_INIT);
		shTblOprInfo
				.setCurrentLoginTime(TblOprInfoConstants.CURRENT_LOGIN_INFO);
		shTblOprInfo.setCurrentLoginIp(TblOprInfoConstants.CURRENT_LOGIN_INFO);
		shTblOprInfo
				.setCurrentLoginStatus(TblOprInfoConstants.CURRENT_LOGIN_INFO);
		shTblOprInfo.setPwdWrTmContinue(TblOprInfoConstants.PWD_WR_TM_CONTINUE);
		// 插入数据
		// 调用虚拟账户
		TblMchtBaseInfTmp baseInfTmp = new TblMchtBaseInfTmp();
		TblMchtBaseInfTmpTmp baseInfTmpTmp = new TblMchtBaseInfTmpTmp();
		TblMchtSettleInfTmp settleInfTmp = new TblMchtSettleInfTmp();
		TblMchtSettleInfTmpTmp settleInfTmpTmp = new TblMchtSettleInfTmpTmp();
		BeanUtils.copyProperties(baseInfTmp, baseInf);
		BeanUtils.copyProperties(baseInfTmpTmp, baseInf);
		BeanUtils.copyProperties(settleInfTmp, settleInf);
		BeanUtils.copyProperties(settleInfTmpTmp, settleInf);

		baseInf.setOpenVirtualAcctFlag("1");// 虚拟账户开通标志
		baseInfTmp.setOpenVirtualAcctFlag("1");// 虚拟账户开通标志
		baseInfTmpTmp.setOpenVirtualAcctFlag("1");// 虚拟账户开通标志
		
		// 风控参数
		TblRiskParamMng tblRiskParamMng = new TblRiskParamMng();
		TblRiskParamMngPK tblRiskParamMngId = new TblRiskParamMngPK();
		tblRiskParamMngId.setMchtId(newMchtNo);
		tblRiskParamMngId.setRiskType("0");
		tblRiskParamMngId.setTermId("00000000");
		tblRiskParamMng.setId(tblRiskParamMngId);
		tblRiskParamMng.setCreditDayAmt(0.0);
		tblRiskParamMng.setCreditMonAmt(0.0);
		tblRiskParamMng.setCreditDayCount(0);
		tblRiskParamMng.setCreditMonCount(0);
		tblRiskParamMng.setCreditSingleAmt(0.0);
		tblRiskParamMng.setDebitDayAmt(0.0);
		tblRiskParamMng.setDebitMonAmt(0.0);
		tblRiskParamMng.setDebitSingleAmt(0.0);
		tblRiskParamMng.setDebitDayCount(0);
		tblRiskParamMng.setDebitMonCount(0);
		tblRiskParamMng.setMchtAmt(0.0);
		tblRiskParamMng.setMchtDayAmt(0.0);
		tblRiskParamMng.setMchtPosAmt(0.0);
		tblRiskParamMng.setRegTime(importTime);
		tblRiskParamMng.setUpdTime(importTime);
		tblRiskParamMng.setRegOpr(operator.getOprId());
		tblRiskParamMng.setUpdOpr(operator.getOprId());
		
		TblImportMcht tblImportMcht = new TblImportMcht();
		tblImportMcht.setOrgId(orgMchtNo);
		tblImportMcht.setNewId(newMchtNo);
		tblImportMcht.setOrderNo(applyNo);
		tblImportMcht.setPicFlag("0");
		tblImportMchtDao.save(tblImportMcht);
		tblMchtBaseInfDAO.save(baseInf);
		tblMchtBaseInfTmpDAO.save(baseInfTmp);
		tblMchtBaseInfTmpTmpDAO.save(baseInfTmpTmp);
		tblMchtSettleInfDAO.save(settleInf);
		tblMchtSettleInfTmpTmpDAO.save(settleInfTmpTmp);
		tblMchtSettleInfTmpDAO.save(settleInfTmp);
		tblInfDiscCdDAO.save(infDiscCd);
		tblHisDiscAlgoDAO.save(temp);
		tblHisDiscAlgoDAO.save(temp1);
		shTblOprInfoDAO.saveOrUpdate(shTblOprInfo);
		tblRiskParamMngDAO.save(tblRiskParamMng);
		resultList.add(newMchtNo);
		resultList.add(baseInfTmp);
		resultList.add(baseInf);
		resultList.add(settleInfTmp);
		resultList.add(settleInf);
		resultList.add(createNewNo);
		resultList.add(infDiscCd.getDiscCd());
		return resultList;
	}

	/**
	 * 1、客户在POSP入网时，在终审环节增加向虚拟账户发送开户请求，并同步接收虚拟账户开户结果， 0）、交易码：A0100 子交易码：0
	 * 1）、虚拟账户回复成功：终审通过 2）、虚拟账户回复失败：终审不通过，并页面提示原因
	 * 2、客户在POSP结算信息并更时，在终审环节增加向虚拟账户发送开户请求，并同步接收虚拟账户开户结果， 0）、交易码：F0100 子交易码：0
	 * 1）、虚拟账户回复成功：终审通过 2）、虚拟账户回复失败：终审不通过，并页面提示原因
	 * 
	 * @param newMchtNo
	 *
	 * @param tmp
	 * @param tmpSettle
	 * @param createNewNo
	 * @return
	 * @throws Exception
	 */
	public String sendMessage(String newMchtNo, TblMchtBaseInfTmp tmp,
			TblMchtBaseInf inf, TblMchtSettleInfTmp tmpSettle,
			TblMchtSettleInf settle, String createNewNo) throws Exception {
		String retMsg = "00"; // 返回结果信息，00-表示成功
		String transCode = "";
		String settleType = "10"; // 默认T+1
		String tnN = "1";
		String weekDay = "";
		String monthDay = "";

		transCode = "A0100";
		Log.log("商户开户。");
		// 取得结算类型---开始
		if (!"".equals(tmp.getMchtFunction())) {
			String mixStr = tmp.getMchtFunction();
			String type = mixStr.substring(0, 1);
			if ("0".equals(type)) {
				// T+N
				settleType = "10";
				tnN = String.valueOf(Integer.parseInt(mixStr.substring(2, 5)));
				if ("0".equals(tnN)) { // T+0
					settleType = "03";
					tnN = "";
				}
			} else if ("1".equals(type)) {
				String periodType = mixStr.substring(1, 2);
				// 周结
				if ("0".equals(periodType)) {
					settleType = "11";
					weekDay = "1";
					// 月结
				} else if ("1".equals(periodType)) {
					settleType = "12";
					monthDay = "01";
					// 季结
				} else if ("2".equals(periodType)) {
					settleType = "13";
				}
			}
		}
		// 取得结算类型---结束

		Msg reqBody = MsgEntity.genMchtRequestBodyMsg();
		Msg reqHead = MsgEntity.genCommonRequestHeadMsg(transCode, "0");
		// reqHead.getField(1).setValue("00"); //版本号
		// reqHead.getField(2).setValue(transCode); //交易类型码
		reqHead.getField(3).setValue("0"); // 子交易码
		// reqHead.getField(4).setValue(institution); //接入机构号;固定值，钱宝机构号
		// reqHead.getField(5).setValue(CommonFunction.getCurrentDate());
		// //接入方交易日期
		// reqHead.getField(6).setValue(CommonFunction.getRandomNum(reqHead.getField(6).getLength()));
		// //接入方交易流水号
		// reqHead.getField(7).setValue(CommonFunction.getCurrentTime());
		// //接入方交易时间
		reqHead.getField(8).setValue("0000000000"); // 接入方交易码
		reqHead.getField(9).setValue(transCode.substring(0, 4) + ""); // 交易类型+接入方检索参考号
		// reqHead.getField(10).setValue(institution); //请求方机构号
		// reqHead.getField(11).setValue(""); //请求方交易日期
		// reqHead.getField(12).setValue(""); //请求方交易流水号
		// reqHead.getField(13).setValue(institution); //开户机构号;固定值，钱宝机构号
		// reqHead.getField(14).setValue(""); //开户机构交易日期
		// reqHead.getField(15).setValue(""); //开户机构交易流水号
		// reqHead.getField(16).setValue(""); //开户机构交易时间
		reqHead.getField(17).setValue(newMchtNo); // 外部账号
		// reqHead.getField(18).setValue(""); //外部账号类型
		// reqHead.getField(19).setValue("1"); //内部账号验证标志
		// reqHead.getField(20).setValue(""); //内部账号
		// reqHead.getField(21).setValue(""); //内部账号类型
		// reqHead.getField(22).setValue(""); //密码域1
		// reqHead.getField(23).setValue(""); //密码域2
		// reqHead.getField(24).setValue(""); //客户号
		// reqHead.getField(25).setValue("+"); //金额符号
		// reqHead.getField(26).setValue(""); //交易额
		// reqHead.getField(27).setValue(""); //用户支付手续费
		// reqHead.getField(28).setValue(""); //应答码
		String sql = "select b.subbranch_id,b.bank_name,b.subbranch_name,b.province,b.city from tbl_subbranch_info b where b.subbranch_id = '"
				+ tmpSettle.getOpenStlno() + "'";
		List retList = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		Object[] objBank = null;
		if (null == retList || retList != null && retList.size() < 1) {
			retMsg = "获取清算账户信息失败。";
			return retMsg;
		} else {
			objBank = (Object[]) retList.get(0);
		}
		reqBody.getField(1).setValue(newMchtNo);
		reqBody.getField(2).setValue(tmp.getEtpsAttr());
		reqBody.getField(3).setValue(tmp.getMchtNm());
		reqBody.getField(4).setValue(createNewNo); // 所属合作伙伴编号
		reqBody.getField(5).setValue(tmp.getRislLvl()); // 商户风险级别
		reqBody.getField(6).setValue("0"); // 商户状态
		reqBody.getField(7).setValue(tmp.getAddr()); // 地址
		reqBody.getField(8).setValue(tmp.getPostCode()); // 邮编
		reqBody.getField(9).setValue(tmp.getContact()); // 联系人姓名
		reqBody.getField(10).setValue(tmp.getCommMobil()); // 联系人移动电话
		reqBody.getField(11).setValue(tmp.getCommTel()); // 联系人固定电话
		reqBody.getField(12).setValue(tmp.getCommEmail()); // 联系人电子信箱
		reqBody.getField(13).setValue(""); // 联系人传真
		reqBody.getField(14).setValue("1"); // 申请类别
		reqBody.getField(15).setValue((String) objBank[1]); // 结算银行名称
		reqBody.getField(16).setValue((String) objBank[0]); // 结算账号开户行行号
		reqBody.getField(17).setValue((String) objBank[2]); // 结算账号开户行名称
		reqBody.getField(18)
				.setValue(tmpSettle.getSettleAcct().substring(0, 1)); // 结算账号类型
		reqBody.getField(19).setValue(tmpSettle.getSettleAcct().substring(1)); // 结算账号
		reqBody.getField(20).setValue(tmpSettle.getSettleAcctNm()); // 结算账号户名
		reqBody.getField(21).setValue(settleType); // 结算方式
		reqBody.getField(22).setValue(tnN); // 只填写N的值。如T+1填写1结算方式为10时必填
		reqBody.getField(23).setValue(weekDay); // 周一填写1。。。周日填写7结算方式为11时必填
		reqBody.getField(24).setValue(monthDay); // 介于01-28之间。不可填写29.30.31结算方式为12时必填
		reqBody.getField(25).setValue((String) objBank[3]); // 户省份（和结算账号开户行对应）
		reqBody.getField(26).setValue((String) objBank[4]); // 开户城市（和结算账号开户行对应）
		reqBody.getField(27).setValue("0000");		// 垫资日息 填写整数，万分之10，填写0010。(N4)
		reqBody.getField(28).setValue("00000000");	// 代付手续费 分为单位，无小数点 (N8)
		reqBody.getField(29).setValue("   "); 		// 暂时不用，填写全空格即可(AN3)
		String secretKey = "1111111111111111";

		String reqStr = MsgEntity.genCompleteRequestMsg(reqHead, reqBody,
				secretKey);

		Msg respHead = MsgEntity.genCommonResponseHeadMsg();
		Msg respBody = MsgEntity.genCommonResponseBodyMsg();

		Log.log(reqStr);
		byte[] bufMsg = MsgEntity.sendMessage(reqStr + " ");
		String strRet = new String(bufMsg, "GBK");
		Log.log(strRet);
		MsgEntity.parseResponseMsg(bufMsg, respHead, respBody);

		String respCode = respHead.getField(28).getRealValue();
		if ("0000".equals(respCode)) { // 交易成功
			retMsg = "00";
		} else {
			retMsg = respBody.getField(1).getRealValue();
		}

		return retMsg;

	}

	public String importTermInfo(String orgTermId, TblTermInfTmp tblTermInfTmp)
			throws Exception {

		tblTermInfTmpDAO.saveOrUpdate(tblTermInfTmp);

		TblTermInf tblTermInf = (TblTermInf) tblTermInfTmp.clone();// 复制的是数据库的数据，在传过来之前已将状态置为正常

		tblTermInfDAO.saveOrUpdate(tblTermInf);

		makeTermKeyInfo(tblTermInfTmp.getId().getTermId(),
				tblTermInfTmp.getMchtCd(), tblTermInfTmp.getRecCrtTs());
		
		// 将新旧终端号关系记入临时表
		TblImportTerm tblImportTerm = new TblImportTerm();
		tblImportTerm.setOrgId(orgTermId);
		tblImportTerm.setNewId(tblTermInfTmp.getId().getTermId());
		tblImportTermDao.save(tblImportTerm);

		// 风控参数
		TblRiskParamMng tblRiskParamMng = new TblRiskParamMng();
		TblRiskParamMngPK tblRiskParamMngId = new TblRiskParamMngPK();
		tblRiskParamMngId.setMchtId(tblTermInf.getMchtCd());
		tblRiskParamMngId.setRiskType("1");
		tblRiskParamMngId.setTermId(tblTermInf.getId());
		tblRiskParamMng.setId(tblRiskParamMngId);
		tblRiskParamMng.setCreditDayAmt(0.0);
		tblRiskParamMng.setCreditMonAmt(0.0);
		tblRiskParamMng.setCreditSingleAmt(0.0);
		tblRiskParamMng.setDebitDayAmt(0.0);
		tblRiskParamMng.setDebitMonAmt(0.0);
		tblRiskParamMng.setDebitSingleAmt(0.0);
		tblRiskParamMng.setRegTime(tblTermInf.getRecCrtTs());
		tblRiskParamMng.setUpdTime(tblTermInf.getRecCrtTs());
		tblRiskParamMng.setRegOpr(tblTermInf.getRecCrtOpr());
		tblRiskParamMng.setUpdOpr(tblTermInf.getRecCrtOpr());
		
		tblRiskParamMngDAO.save(tblRiskParamMng);

		return Constants.SUCCESS_CODE;
	}

	public String updateSN(String termId, String recCrtTs, String sn) {

		TblTermInfTmpPK pk = new TblTermInfTmpPK(CommonFunction.fillString(
				termId, ' ', 12, true), recCrtTs);
		TblTermInfTmp tblTermInfTmp = tblTermInfTmpDAO.get(pk);
		TblTermInf tblTermInf = tblTermInfDAO.get(CommonFunction.fillString(
				termId, ' ', 12, true));

		if (tblTermInfTmp == null || tblTermInf == null) {
			return Constants.FAILURE_CODE;
		}

		tblTermInfTmp.setProductCd(sn);
		tblTermInf.setProductCd(sn);

		tblTermInfTmpDAO.update(tblTermInfTmp);
		tblTermInfDAO.update(tblTermInf);

		return Constants.SUCCESS_CODE;
	}

	/**
	 * @return the tblMchtBaseInfDAO
	 */
	public TblMchtBaseInfDAO getTblMchtBaseInfDAO() {
		return tblMchtBaseInfDAO;
	}

	/**
	 * @param tblMchtBaseInfDAO
	 *            the tblMchtBaseInfDAO to set
	 */
	public void setTblMchtBaseInfDAO(TblMchtBaseInfDAO tblMchtBaseInfDAO) {
		this.tblMchtBaseInfDAO = tblMchtBaseInfDAO;
	}

	/**
	 * @return the tblMchtBaseInfTmpDAO
	 */
	public TblMchtBaseInfTmpDAO getTblMchtBaseInfTmpDAO() {
		return tblMchtBaseInfTmpDAO;
	}

	/**
	 * @param tblMchtBaseInfTmpDAO
	 *            the tblMchtBaseInfTmpDAO to set
	 */
	public void setTblMchtBaseInfTmpDAO(
			TblMchtBaseInfTmpDAO tblMchtBaseInfTmpDAO) {
		this.tblMchtBaseInfTmpDAO = tblMchtBaseInfTmpDAO;
	}

	/**
	 * @return the tblMchtBaseInfTmpTmpDAO
	 */
	public ITblMchtBaseInfTmpTmpDAO getTblMchtBaseInfTmpTmpDAO() {
		return tblMchtBaseInfTmpTmpDAO;
	}

	/**
	 * @param tblMchtBaseInfTmpTmpDAO
	 *            the tblMchtBaseInfTmpTmpDAO to set
	 */
	public void setTblMchtBaseInfTmpTmpDAO(
			ITblMchtBaseInfTmpTmpDAO tblMchtBaseInfTmpTmpDAO) {
		this.tblMchtBaseInfTmpTmpDAO = tblMchtBaseInfTmpTmpDAO;
	}

	/**
	 * @return the tblMchtSettleInfDAO
	 */
	public TblMchtSettleInfDAO getTblMchtSettleInfDAO() {
		return tblMchtSettleInfDAO;
	}

	/**
	 * @param tblMchtSettleInfDAO
	 *            the tblMchtSettleInfDAO to set
	 */
	public void setTblMchtSettleInfDAO(TblMchtSettleInfDAO tblMchtSettleInfDAO) {
		this.tblMchtSettleInfDAO = tblMchtSettleInfDAO;
	}

	/**
	 * @return the tblMchtSettleInfTmpTmpDAO
	 */
	public ITblMchtSettleInfTmpTmpDAO getTblMchtSettleInfTmpTmpDAO() {
		return tblMchtSettleInfTmpTmpDAO;
	}

	/**
	 * @param tblMchtSettleInfTmpTmpDAO
	 *            the tblMchtSettleInfTmpTmpDAO to set
	 */
	public void setTblMchtSettleInfTmpTmpDAO(
			ITblMchtSettleInfTmpTmpDAO tblMchtSettleInfTmpTmpDAO) {
		this.tblMchtSettleInfTmpTmpDAO = tblMchtSettleInfTmpTmpDAO;
	}

	/**
	 * @return the tblMchtSettleInfTmpDAO
	 */
	public TblMchtSettleInfTmpDAO getTblMchtSettleInfTmpDAO() {
		return tblMchtSettleInfTmpDAO;
	}

	/**
	 * @param tblMchtSettleInfTmpDAO
	 *            the tblMchtSettleInfTmpDAO to set
	 */
	public void setTblMchtSettleInfTmpDAO(
			TblMchtSettleInfTmpDAO tblMchtSettleInfTmpDAO) {
		this.tblMchtSettleInfTmpDAO = tblMchtSettleInfTmpDAO;
	}

	/**
	 * @return the tblInfDiscCdDAO
	 */
	public TblInfDiscCdDAO getTblInfDiscCdDAO() {
		return tblInfDiscCdDAO;
	}

	/**
	 * @param tblInfDiscCdDAO
	 *            the tblInfDiscCdDAO to set
	 */
	public void setTblInfDiscCdDAO(TblInfDiscCdDAO tblInfDiscCdDAO) {
		this.tblInfDiscCdDAO = tblInfDiscCdDAO;
	}

	/**
	 * @return the tblHisDiscAlgoDAO
	 */
	public TblHisDiscAlgoDAO getTblHisDiscAlgoDAO() {
		return tblHisDiscAlgoDAO;
	}

	/**
	 * @param tblHisDiscAlgoDAO
	 *            the tblHisDiscAlgoDAO to set
	 */
	public void setTblHisDiscAlgoDAO(TblHisDiscAlgoDAO tblHisDiscAlgoDAO) {
		this.tblHisDiscAlgoDAO = tblHisDiscAlgoDAO;
	}

	/**
	 * @return the shTblOprInfoDAO
	 */
	public ShTblOprInfoDAO getShTblOprInfoDAO() {
		return shTblOprInfoDAO;
	}

	/**
	 * @param shTblOprInfoDAO
	 *            the shTblOprInfoDAO to set
	 */
	public void setShTblOprInfoDAO(ShTblOprInfoDAO shTblOprInfoDAO) {
		this.shTblOprInfoDAO = shTblOprInfoDAO;
	}

	/**
	 * @return the t10101BO
	 */
	public T10101BO getT10101BO() {
		return t10101BO;
	}

	/**
	 * @param t10101bo
	 *            the t10101BO to set
	 */
	public void setT10101BO(T10101BO t10101bo) {
		t10101BO = t10101bo;
	}

	@Override
	// table_name:tbl_import_brh,tbl_import_mcht,tbl_import_term
	public String getNewId(String orgId, String tableName) {

		String sql = "select new_id from " + tableName + " where org_id = '"
				+ orgId + "'";

		List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);

		if (null != list && !list.isEmpty() && !StringUtil.isNull(list.get(0))) {

			return list.get(0).toString();
		}

		return null;
	}

	@Override
	// table_name:tbl_import_brh,tbl_import_mcht,tbl_import_term
	public String getOldId(String newId, String tableName) {

		String sql = "select org_id from " + tableName + " where new_id = '"
				+ newId + "'";

		List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);

		if (null != list && !list.isEmpty() && !StringUtil.isNull(list.get(0))) {

			return list.get(0).toString();
		}

		return null;
	}

	public String getTermId(String areaNo, String termType) {
		if (StringUtil.isEmpty(areaNo) || areaNo.length() < 2) {
			return Constants.FAILURE_CODE;
		}
		if (StringUtil.isEmpty(termType) || termType.length() > 1) {
			return Constants.FAILURE_CODE;
		}
		String topAreaNo = "";
		if (areaNo.length() > 2) {
			topAreaNo = areaNo.substring(0, 2);
		} else {
			topAreaNo = areaNo;
		}
		String termNoHead = topAreaNo + termType;// 地区（2）+ 终端类型（1）
		String sql = "select max(substr(t.term_id, 4,5)) from TBL_TERM_INF_TMP t where t.term_id like '"
				+ termNoHead + "%'";
		List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);
		int maxSeqNo = 0;
		if (list != null && !list.isEmpty() && list.get(0) != null) {
			Object maxValObj = (Object) list.get(0);
			maxSeqNo = Integer.parseInt(maxValObj.toString());
		}
		return termNoHead
				+ StringUtil.beforFillValue(String.valueOf(++maxSeqNo), 5, '0');// 地区（2）+
																				// 终端类型（1）+序列（5）
	}

	private void makeTermKeyInfo(String termId, String newMchtId, String time)
			throws Exception {

		// 只加入商户号和终端号字段，其他字段后台C程序更新
		TblTermKey tblTermKey = new TblTermKey();
		TblTermKeyPK keyPK = new TblTermKeyPK();
		keyPK.setTermId(termId);
		keyPK.setMchtCd(newMchtId);
		tblTermKey.setId(keyPK);
		tblTermKey.setKeyIndex(SysParamUtil.getParam("KEY_INDEX"));
		tblTermKey.setRecCrtTs(time);
		tblTermKey.setRecUpdTs(time);
		BeanUtils.setNullValueWithLine(tblTermKey);
		tblTermKeyDAO.saveOrUpdate(tblTermKey);
		// log("插入POS终端密钥表成功");

		// 终端密钥下载时更新该条记录
		TblTermZmkInf tblTermZmkInf = new TblTermZmkInf();
		TblTermZmkInfPK zmkPK = new TblTermZmkInfPK();
		zmkPK.setMchtNo(newMchtId);
		zmkPK.setTermId(termId);
		tblTermZmkInf.setId(zmkPK);
		tblTermZmkInf.setKeyIndex(SysParamUtil.getParam("ZMK_KEY_INDEX"));
		tblTermZmkInf.setRandom(com.allinfinance.system.util.CommonFunction
				.getRandom(6));
		tblTermZmkInf.setRecCrtTs(time);
		tblTermZmkInf.setRecUpdTs(time);
		BeanUtils.setNullValueWithLine(tblTermZmkInf);
		tblTermZmkInfDAO.saveOrUpdate(tblTermZmkInf);
		// log("插入POS终端密钥索引表成功");
	}

	@Override
	// mchntCd
	// "":已经导入过了
	// null:没找到对应的商户编号
	public String getMchntCd(String orderName) {

		String sql = "select new_id,pic_flag from tbl_import_mcht where order_no = '"
				+ orderName + "'";

		List list = CommonFunction.getCommQueryDAO().findBySQLQuery(sql);

		if (null != list && !list.isEmpty() && !StringUtil.isNull(list.get(0))) {

			Object[] obj = (Object[]) list.get(0);
			String mchntCd = (String) obj[0];
			String picFlag = (String) obj[1];
			if ("0".equals(picFlag)) {
				return mchntCd;
			} else if ("1".equals(picFlag)) {
				return "";
			}
		}

		return null;
	}

	@Override
	public String updatePicFlag(String mchntCd) {

		TblImportMcht tblImportMcht = tblImportMchtDao.get(getOldId(mchntCd,
				"tbl_import_mcht"));
		tblImportMcht.setPicFlag("1");

		tblImportMchtDao.saveOrUpdate(tblImportMcht);

		return null;
	}

	@Override
	public TblTermInf getTermInf(String termId) {
		return tblTermInfDAO.get(termId);
	}

	public TblTermKeyDAO getTblTermKeyDAO() {
		return tblTermKeyDAO;
	}

	public void setTblTermKeyDAO(TblTermKeyDAO tblTermKeyDAO) {
		this.tblTermKeyDAO = tblTermKeyDAO;
	}

	public TblTermZmkInfDAO getTblTermZmkInfDAO() {
		return tblTermZmkInfDAO;
	}

	public void setTblTermZmkInfDAO(TblTermZmkInfDAO tblTermZmkInfDAO) {
		this.tblTermZmkInfDAO = tblTermZmkInfDAO;
	}

	public TblTermInfTmpDAO getTblTermInfTmpDAO() {
		return tblTermInfTmpDAO;
	}

	public void setTblTermInfTmpDAO(TblTermInfTmpDAO tblTermInfTmpDAO) {
		this.tblTermInfTmpDAO = tblTermInfTmpDAO;
	}

	public TblTermInfDAO getTblTermInfDAO() {
		return tblTermInfDAO;
	}

	public void setTblTermInfDAO(TblTermInfDAO tblTermInfDAO) {
		this.tblTermInfDAO = tblTermInfDAO;
	}

	/**
	 * @return the commQueryDAO
	 */
	public ICommQueryDAO getCommQueryDAO() {
		return commQueryDAO;
	}

	/**
	 * @param commQueryDAO
	 *            the commQueryDAO to set
	 */
	public void setCommQueryDAO(ICommQueryDAO commQueryDAO) {
		this.commQueryDAO = commQueryDAO;
	}

	public ITblImportMchtDao getTblImportMchtDao() {
		return tblImportMchtDao;
	}

	public void setTblImportMchtDao(ITblImportMchtDao tblImportMchtDao) {
		this.tblImportMchtDao = tblImportMchtDao;
	}

	public ITblImportTermDao getTblImportTermDao() {
		return tblImportTermDao;
	}

	public void setTblImportTermDao(ITblImportTermDao tblImportTermDao) {
		this.tblImportTermDao = tblImportTermDao;
	}

	public TblRiskParamMngDAO getTblRiskParamMngDAO() {
		return tblRiskParamMngDAO;
	}

	public void setTblRiskParamMngDAO(TblRiskParamMngDAO tblRiskParamMngDAO) {
		this.tblRiskParamMngDAO = tblRiskParamMngDAO;
	}
}
