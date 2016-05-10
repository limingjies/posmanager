/**
 * 操作员状态
 */
function oprState(val) {
	if (val == '0') {
		return '<img src="' + Ext.contextPath
				+ '/ext/resources/images/active_16.png" title="可用"/>正常';
	} else if (val == '1') {
		return '<img src="' + Ext.contextPath
				+ '/ext/resources/images/stop_16.png" title="锁定"/>锁定';
	} else if (val == '2') {
		return '添加待审查';
	} else if (val == '3') {
		return '修改待审核';
	} else if (val == '4') {
		return '审核不通过';
	}
	return '状态未知';
}
/**
 * 
 * 终端类型
 */
function termForType(val){
			switch(val){
				case '0':return '普通POS';
				case '1':return '财务POS';
				case '2':return '签约POS';
				case '3':return '电话POS';
				case '4':return 'MISPOS';
				case '5':return '移动POS';
				case '6':return '网络POS';
				case '7':return 'MPOS';
			}
		}
//结算扣率
function profitSelect(val) {
	var spe=val.split('|');
	switch (spe[0]) {
	case '0':
		return ''+spe[1]+'%';//比例：
	case '1':
		return ''+spe[1]+'%-'+spe[2];//比例+封顶：
	case '2':
		return ''+spe[2];//封顶：
	default:
		return '未知';
	}
}
function oprState_new(val) {
	if (val == '0') {
		return '<font color="green">正常</font>';
	} else if (val == '1') {
		return '<font color="red">注销</font>';
	} else if (val == '2') {
		return '<font color="green">初始化</font>';
	}
	return '状态未知';
}

function mchntBrhState(val) {
	if (val == '0') {
		return '<font color="green">商户</font>';
	} else if (val == '1') {
		return '<font color="blue">代理</font>';
	}
	return '状态未知';
}

function insetFlag(val) {
	switch (val) {
	case 'I':
		return '新增';
	case 'U':
		return '修改';
	case 'D':
		return '删除';
	default:
		return '未知';
	}
}

/**
 * 操作员性别
 */

function gender(val) {
	if (val == '0') {
		return '<img src="' + Ext.contextPath
				+ '/ext/resources/images/male.png" />';
	} else if (val == '1') {
		return '<img src="' + Ext.contextPath
				+ '/ext/resources/images/female.png" />';
	}
	return val;
}

/**
 * 商户状态转译
 */
function mchntSt(val) {
	if (val == '0') {
		return '<font color="green">正常</font>';
	} else if (val == '1') {
		return '<font color="gray">添加待终审</font>';
	} else if (val == 'B') {
		return '<font color="gray">添加待初审</font>';
	} else if (val == '3') {
		return '<font color="gray">变更待初审</font>';
	} else if (val == '5') {
		return '<font color="gray">冻结待审核</font>';
	} else if (val == '6') {
		return '<font color="red">冻结</font>';
	} else if (val == '7') {
		return '<font color="gray">恢复待审核</font>';
	} else if (val == '8') {
		return '<font color="red">注销</font>';
	} else if (val == '9') {
		return '<font color="gray">注销待审核</font>';
	} else if (val == 'A') {
		return '<font color="blue">添加初审退回</font>';
	} else if (val == '2') {
		return '<font color="blue">添加终审退回</font>';
	}else if (val == '4') {
		return '<font color="gray">变更待终审</font>';
	} else if (val == 'C') {
		return '<font color="red">拒绝</font>';
	} else if (val == 'I') {
		return '<font color="gray">批量录入待审核</font>';
	} else if (val == 'D') {
		return '<font color="gray">暂存未提交</font>';
	}else if (val == 'E') {
		return '<font color="red">变更初审退回</font>';
	}else if (val == 'F') {
		return '<font color="red">变更终审退回</font>';
	}
	return val;
}

/**
 * 直联商户状态转译
 */
function mchtCupSt(val) {
	if (val == '1') {
		return '<font color="green">正常</font>';
	} else if (val == '9') {
		return '<font color="gray">新增待审核</font>';
	} else if (val == '8') {
		return '<font color="blue">新增拒绝</font>';
	} else if (val == '7') {
		return '<font color="gray">修改待审核</font>';
	} else if (val == '6') {
		return '<font color="blue">修改拒绝</font>';
	} else if (val == '2') {
		return '<font color="red">冻结</font>';
	} else if (val == '0') {
		return '<font color="red">注销</font>';
	} else if (val == '5') {
		return '<font color="red">冻结待审核</font>';
	} else if (val == '3') {
		return '<font color="red">注销待审核</font>';
	} else if (val == 'H') {
		return '<font color="gray">恢复待审核</font>';
	}
	return val;
}

function mchtCupStInfo(val) {
	if (val == '1') {
		return '<font color="green">正常</font>';
	} else if (val == '2') {
		return '<font color="red">冻结</font>';
	} else if (val == '0') {
		return '<font color="red">注销</font>';
	}
	return val;
}

/**
 * 直联商户类型
 */
function mchtCupType(val) {
	switch (val) {
	case '00':
		return '传统POS商户';
	case '01':
		return '服务提供机构';
	case '02':
		return '接入渠道机构';
	case '03':
		return 'CUPSECURE商户';
	case '04':
		return '虚拟商户';
	case '05':
		return '行业商户';
	case '06':
		return '服务提供机构+接入渠道机构';
	case '07':
		return '多渠道直联终端商户';
	default:
		return '未知';
	}
}

/**
 * 直联终端类型
 */
function termCupType(val) {
	switch (val) {
	case 'P0':
		return '无线POS';
	case 'P1':
		return '有线POS';
	case 'PA':
		return '自助终端';
	case 'PI':
		return 'IC卡终端';
	case 'PM':
		return 'MIS';
	case 'PT':
		return '电话支付终端';
	default:
		return '未知';
	}
}

/**
 * 银行转换
 */
function brhLvlRender(val) {
	switch (val) {
	case '0':
		return '钱宝金融';
	case '1':
		return '一级商户代理';
	case '2':
		return '二级商户代理';
	default:
		return '未知';
	}
}

/**
 * 翻译是或者否
 */
function yesOrNo(val) {
	switch (val) {
	case '0':
		return '是';
	case '1':
		return '否';
	default:
		return '未知';
	}
}

/**
 * 持卡人营销活动
 */
function actType(val) {
	switch (val) {
	case '0':
		return '积分消费';
	case '1':
		return '分期消费';
	case '2':
		return '折扣消费';
	case '3':
		return '持卡人抽奖';
	default:
		return '未知';
	}
}

/**
 * 银行转换
 */
function groupType(val) {
	switch (val) {
	case '1':
		return '全国性集团';
	case '2':
		return '地方性集团（省内）';
	default:
		return '未知';
	}
}

/**
 * 交易渠道转换
 */
function channelType(val) {
	switch (val) {
	case '0':
		return '间联POS';
	case '1':
		return '否';
	default:
		return '未知';
	}
}
/**
 * 卡类型转换
 */
function cardType(val) {
	switch (val) {
	case '00':
		return '全部卡种';
	case '01':
		return '本行借记卡';
	case '02':
		return '本行贷记卡';
	case '03':
		return '他行借记卡';
	case '04':
		return '他行贷记卡';
	case '05':
		return '他行准贷记卡';
	case '06':
		return '他行其他卡';
	default:
		return '未知卡';
	}
}

/**
 * 交易名称转换
 */
function funType(val) {
	switch (val) {
	case '1101':
		return '消费';
	case '1161':
		return '查询';
	case '5151':
		return '退货';
	case '2101':
		return '消费冲正';
	case '3101':
		return '消费撤消';
	case '4101':
		return '撤消冲正';
	case '1011':
		return '预授权';
	case '2011':
		return '预授权冲正';
	case '3011':
		return '预授权撤消';
	case '4011':
		return '预授权撤消冲正';
	case '1091':
		return '联机预授权完成';
	case '2091':
		return '联机预授权完成冲正';
	case '3091':
		return '联机预授权完成撤消';
	case '4091':
		return '联机预授权完成撤消冲正';
	case '1111':
		return '分期付款';
	case '2111':
		return '分期付款冲正';
	case '1171':
		return '积分查询';
	case '1121':
		return '积分消费';
	case '2121':
		return '积分消费冲正';
	case '3121':
		return '积分撤消';
	case '4121':
		return '积分撤消冲正';
	case '5161':
		return '离线预授权完成';
	case '1131':
		return '财务转账';
	case '3131':
		return '转账撤销';
	case '1001':
		return '明细查询';
	case '1000':
		return '余额查询';
	default:
		return '未知交易类型';
	}
}

function translateState(val) {

	if (val == '0') {
		return "已入库,待绑定";
	} else if (val == '1') {
		return "已绑定,使用中";
	} else if (val == '2') {
		return "维修中";
	} else if (val == '3') {
		return "已作废";
		// } else if(val == '4') {
		// return "已丢失";
	} else if (val == '5') {
		return "已上缴,待接收";
	} else if (val == '6') {
		return "已下发,待接收";
	} else {
		return "状态异常";
	}
}

// 库存申请状态
function appStat(val) {
	if (val == '0') {
		return "新申请";
	} else if (val == '1') {
		return "已取消";
	} else if (val == '2') {
		return "通过";
	} else if (val == '3') {
		return "部分通过";
	} else if (val == '4') {
		return "退回";
	} else if (val == '5') {
		return "请求完成";
	} else {
		return "状态异常";
	}
}

// 终端类型
function termType(val) {
	if (val == 'P')
		return "POS";
	if (val == 'E')
		return "EPOS";
	if (val == 'A')
		return "ATM";
	if (val == 'K')
		return "PINPAD";
	return val;
}

function formatTypes(val) {
	if (val != null && val.indexOf("-") != -1) {
		return val.substring(val.indexOf("-") + 1).trim();
	}
	return val;

};
function termPadType(val) {
	switch (val) {
	case 'W':
		return '外接';
	case 'N':
		return '内置';
	default:
		return val;
	}

};

// 直联终端状态
function termCupSta(val) {
	if (val == '1') {
		return '<font color="green">正常</font>';
	} else if (val == '9') {
		return '<font color="gray">新增待审核</font>';
	} else if (val == '8') {
		return '<font color="blue">新增拒绝</font>';
	} else if (val == '7') {
		return '<font color="gray">修改待审核</font>';
	} else if (val == '6') {
		return '<font color="blue">修改拒绝</font>';
	} else if (val == '2') {
		return '<font color="red">冻结</font>';
	} else if (val == '0') {
		return '<font color="red">注销</font>';
	} else if (val == '5') {
		return '<font color="red">冻结待审核</font>';
	} else if (val == '3') {
		return '<font color="red">注销待审核</font>';
	} else if (val == 'H') {
		return '<font color="gray">恢复待审核</font>';
	}
	return val;
}

// 终端状态
function termSta(val) {
	if (val == '0')
		return "<font color='gray'>新增未审核</font>";
	if (val == '1')
		return "<font color='green'>启用</font>";
	if (val == '2')
		return "<font color='gray'>修改未审核</font>";
	if (val == '3')
		return "<font color='gray'>冻结未审核</font>";
	if (val == '4')
		return "<font color='red'>冻结</font>";
	if (val == '5')
		return "<font color='gray'>恢复未审核</font>";
	if (val == '6')
		return "<font color='gray'>注销未审核</font>";
	if (val == '7')
		return "<font color='red'>注销</font>";
	if (val == '8')
		return "新增审核拒绝";
	if (val == '9')
		return "修改审核拒绝";
	if (val == 'A')
		return "冻结审核拒绝";
	if (val == 'B')
		return "恢复审核拒绝";
	if (val == 'C')
		return "注销审核拒绝";
	if (val == 'D')
		return "复制未修改";
	return val;
}
// 终端签到状态
function termSignSta(val) {
	if (val == '0')
		return "未签到";
	if (val == '1')
		return "已签到";
	if (val == '2')
		return "已签退";
	return val;
}
function termState(val) {
	if (val == '0')
		return "已申请";
	if (val == '1')
		return "已审核";
	return val;
}

function clcAction(val) {
	if (val == '0')
		return "<font color='green'>关注</font>";
	if (val == '2')
		return "<font color='red''>拒绝</font>";
	if (val == '3')
		return "<font color='blue'>预警</font>";
	return val;
}

function settleFlag(val) {
	if (val == '0')
		return "未处理";
	if (val == '1')
		return "<font color='red'>划拨失败</font>";
	if (val == '2')
		return "<font color='green'>划拨成功</font>";
	return val;
}
function actState(val) {
	if (val == '0')
		return "<font color='green'>正常</font>";
	if (val == '1')
		return "未审核";
	if (val == '2')
		return "<font color='red'>关闭</font>";
	return val;
}

function actOprState(val) {
	if (val == '2')
		return "关闭";
	if (val == '1')
		return "修改";
	if (val == '0')
		return "新增";
	return "";
}

function mchntOprState(val) {
	if (val == '2')
		return "删除";
	if (val == '1')
		return "追加";
	if (val == '0')
		return "新增";
	return val;
}

function usageKey(val) {
	if (val == '0')
		return "有效";
	if (val == '1')
		return "无效";
	return val;
}

function msgType(val) {
	if (val == '1')
		return "功能提示信息";
	if (val == '2')
		return "操作提示信息";
	if (val == '3')
		return "错误提示信息";
	return val;
}

function opreator(val) {
	if (val == '1')
		return "<font color='green'>启用</font>";
	if (val == '0')
		return "<font color='red''>作废</font>";
	return val;
}
function menuLevel(val) {
	if (val == '1')
		return "一级菜单";
	if (val == '2')
		return "二级菜单";
	if (val == '3')
		return "三级菜单";
	return val;
}
function conFlag(val) {
	if (val == '1')
		return "<font color='green'>启用</font>";
	if (val == '0')
		return "<font color='red'>未启用</font>";
	return val;
}
function rateType(val) {
	if (val == 1)
		return "算法2：应付银联手续费*费率";
	if (val == 0)
		return "算法1：应收商户手续费*费率";
	if (val == 2)
		return "算法3：手续费净额*费率";
	return val;
}

function BeUse(val) {
	if (val == '1')
		return "<font color='green'>启用</font>";
	if (val == '0')
		return "<font color='red'>停用</font>";
	return val;
}

function riskIdName(val) {
	if (val == 'A00' || val == 'A01' || val == 'A02')
		return val + "-频繁试卡";
	else if (val == 'A03' || val == 'A04')
		return val + "-大额交易";
	else if (val == 'A05' || val == 'A06')
		return val + "-频繁小额交易";
	else if (val == 'A07')
		return val + "-频繁整数交易";
	else if (val == 'A08')
		return val + "-高失败率";
	else if (val == 'A09')
		return val + "-可疑交易";
	else if (val == 'A10' || val == 'A11' || val == 'A12' || val == 'A13'
			|| val == 'A14' || val == 'A15' || val == 'A16')
		return val + "-信用卡高风险交易";
	else if (val == 'A17')
		return val + "-交易时间";
	else if (val == 'A18' || val == 'A19' || val == 'A20')
		return val + "-交易超限";
	else if (val == 'T01' || val == 'T02')
		return val + "-事中频繁试卡";
	else if (val == 'T03' || val == 'T04' || val == 'T05')
		return val + "-事中信用卡高风险";
	else if (val == 'T06' || val == 'T07')
		return val + "-事中交易时间";
	else
		return val;
}

function riskName(val) {
	if (val == 'A00' || val == 'A01' || val == 'A02')
		return "频繁试卡";
	else if (val == 'A03' || val == 'A04')
		return "大额交易";
	else if (val == 'A05' || val == 'A06')
		return "频繁小额交易";
	else if (val == 'A07')
		return "频繁整数交易";
	else if (val == 'A08')
		return "高失败率";
	else if (val == 'A09')
		return "可疑交易";
	else if (val == 'A10' || val == 'A11' || val == 'A12' || val == 'A13')
		return "信用卡高风险交易";
	else
		return val;
}

// 交易结果
function txnSta(val) {
	if (val == '0')
		return '<font color="blue">请求中</font>';
	else if (val == '1')
		return '<font color="green">成功</font>';
	else if (val == '2')
		return '<font color="red">卡拒绝</font>';
	else if (val == '3')
		return '<font color="red">超时</font>';
	else if (val == '4')
		return '<font color="red">主机拒绝</font>';
	else if (val == '5')
		return '<font color="red">pin/mac错</font>';
	else if (val == '6')
		return '<font color="red">前置拒绝</font>';
	else if (val == '7')
		return '<font color="blue">记账中</font>';
	else if (val == '8')
		return '<font color="red">记账超时</font>';
	else if (val == '9')
		return '<font color="blue">交易确认</font>';
	else if (val == 'R')
		return '<font color="gray">已退货</font>';
	return val;
}
//20160504 guoyu add
//交易结果(失败)
function txnSta_ForFail(val) {
	if (val == '0')
		return '<font color="blue">请求中</font>';
	else if (val == '1')
		return '<font color="green">成功</font>';
	else if (val == '9')
		return '<font color="blue">交易确认</font>';
	else if (val == 'R')
		return '<font color="gray">已退货</font>';
	return '<font color="red">失败</font>';
}
// 渠道商户
function mchntTmpSt(val) {
	if (val == '0') {
		return '<font color="green">审核通过</font>';
	} else if (val == '1') {
		return '<font color="gray">申请待审核</font>';

	} else if (val == '2') {
		return '<font color="red">审核回退</font>';

	}
	return val;
}

// 路由规则

function routeRule(val) {
	var va = val.substring(0, 1);
	if (va == '*') {
		return '<font color="gray">无限制</font>';
	} else {
		return val;
	}
}

function routeTxnTp(val) {
	var va = val.substring(0, 1);
	if (va == '*') {
		return '<font color="gray">无限制</font>';
	} else {
		return val.substring(val.indexOf('-') + 1);
	}
}

function routeCardTp(val) {
	if (val == '*') {
		return '<font color="gray">无限制</font>';
	} else if (val == '00') {
		return '借记卡';
	} else if (val == '01') {
		return '贷记卡';
	} else if (val == '02') {
		return '准借记卡';
	} else if (val == '03') {
		return '预付费卡';
	} else if (val == '04') {
		return '公务卡';
	} else {
		return val;
	}
}

// 路由有限级别
function routePriority(val) {
	switch (val) {
	case '0':
		return '低';
	case '1':
		return '中低';
	case '2':
		return '中';
	case '3':
		return '中高';
	case '4':
		return '高';
	default:
		return val;
	}
}

// 路由状态
function routeStatus(val) {
	switch (val) {
	case '0':
		return '<font color="red">停用</font>';
	case '1':
		return '<font color="green">启用</font>';

	default:
		return val;
	}
}
//支付渠道状态
function branchStatus(val) {
	switch (val) {
	case '0':
		return '<font color="green">启用</font>';
	case '1':
		return '<font color="red">停用</font>';

	default:
		return val;
	}
}

//成本扣率配置（业务成本扣率主表Tbl_Upbrh_Fee表）状态
function feeStatus(val) {
	switch (val) {
	case '1':
		return '<font color="red">停用</font>';
	case '0':
		return '<font color="green">启用</font>';

	default:
		return val;
	}
}

//渠道商户停用类型(渠道商户表Tbl_Upbrh_Mcht)
function stopTypeRender(val) {
	switch (val) {
	case '1':
		return '<font color="red">商户被投诉</font>';
	case '2':
		return '<font color="red">商户号被晒单</font>';
	case '3':
		return '<font color="red">商户号被整改</font>';
	case '4':
		return '<font color="red">交易金额过大</font>';
	case '5':
		return '<font color="red">其他</font>';

	default:
		return val;
	}
}

//成本扣率配置左边页面是否已配置 -- 业务表Tbl_Route_Upbrh2的status字段的状态
function isFeeStatus(val) {
	switch (val) {
	case '1':
		return '<font color="green">已配置</font>';
	case '0':
		return '<font color="red">未配置</font>';
	default:
		return val;
	}
}


/**
 * 改变 TBL_RCV_PACK 的BUS_TYPE(业务类型)字段 批量代付：303；批量代收：304
 * 
 * @param {}
 *            val
 */
function changeTblRcvPackBusType(val) {
	if (val == '303') {
		return '批量代付';
	} else if (val = '304') {
		return '批量代收';
	} else {
		return val;
	}
}
/**
 * 改变 TBL_RCV_PACK 的PROC_STAT(处理状态)字段
 * 
 * @param {}
 *            val
 */
function changeTblRcvPackProcStat(val) {
	if (val == '0') {
		return '已登记包头';
	} else if (val == '1') {
		return '入库中';
	} else if (val == '2') {
		return '已入库';
	} else if (val == '3') {
		return '正在发送';
	} else if (val == '4') {
		return '发送成功';
	} else if (val == '5') {
		return '发送失败';
	} else if (val == '6') {
		return '接收回执中';
	} else if (val == '7') {
		return '已收到回执';
	} else if (val == '8') {
		return '待回执';
	} else if (val == '9') {
		return '发送回执中';
	} else if (val == 'A') {
		return '已发送回执';
	} else if (val == 'B') {
		return '已退汇';
	} else if (val == 'E') {
		return '文件错误';
	} else {
		return val;
	}
}
function changeTblRcvLockFlag(val) {
	if (val == '0') {
		return '未锁定';
	} else if (val == '1') {
		return '已锁定'
	} else {
		return val;
	}
}

/**
 * 改变 TBL_RCV_PACK_DTL 的PROC_STAT(处理状态)字段
 * 
 * @param {}
 *            val
 */
function changeTblRcvPackDtlProcStat(val) {
	if (val == '0') {
		return '已登记包头';
	} else if (val == '1') {
		return '入库中';
	} else if (val == '2') {
		return '已入库';
	} else if (val == '3') {
		return '正在发送';
	} else if (val == '4') {
		return '发送成功';
	} else if (val == '5') {
		return '发送失败';
	} else if (val == '6') {
		return '接收回执中';
	} else if (val == '7') {
		return '已收到回执';
	} else if (val == '8') {
		return '待回执';
	} else if (val == '9') {
		return '发送回执中';
	} else if (val == 'A') {
		return '已发送回执';
	} else if (val == 'B') {
		return '已退汇';
	} else if (val == 'E') {
		return '文件错误';
	} else {
		return val;
	}
}
function ModelGroup(val) {
	if (val == 'M01')
		return val + "-频繁试卡";
	else if (val == 'M02')
		return val + "-大额交易";
	else if (val == 'M03')
		return val + "-频繁小额交易";
	else if (val == 'M04')
		return val + "-频繁整数交易";
	else if (val == 'M05')
		return val + "-高失败率";
	else if (val == 'M06')
		return val + "-可疑交易";
	else if (val == 'M07')
		return val + "-信用卡高风险交易";
	else if (val == 'M08')
		return val + "-交易时间";
	else if (val == 'M09')
		return val + "-交易超限";
	else if (val == 'M10')
		return val + "-移动终端疑似移机";
	else if (val == 'M51')
		return val + "-事中频繁试卡";
	else if (val == 'M52')
		return val + "-事中信用卡高风险";
	else if (val == 'M53')
		return val + "-事中交易时间";
	else if (val == 'M54')
		return val + "-事中大额交易";
	else
		return val;
}
/**
 * 改变 TBL_RCV_PACK_DTL 的ACCT_TYPE(账户类型)字段
 * 
 * @param {}
 *            val
 * @return {String}
 */
function changeTblRcvPackDtlAcctType(val) {
	if (val == '00') {
		return "银行卡"
	} else if (val == '01') {
		return "存折"
	} else {
		return val;
	}
}
/**
 * 改变 TBL_RCV_PACK_DTL 的CUST_TYPE(账户类型)字段
 * 
 * @param {}
 *            val
 * @return {String}
 */
function changeTblRcvPackDtlCustType(val) {
	if (val == '0') {
		return "私人"
	} else if (val == '1') {
		return "公司"
	} else {
		return val;
	}
}

/**
 * 改变 TBL_RCV_PACK_DTL 的CUR_CD(货币类型)字段
 * 
 * @param {}
 *            val
 * @return {String}
 */
function changeTblRcvPackDtlCurCd(val) {
	val = Ext.util.Format.uppercase(val);
	if (val == "CNY") {
		return "人民币";
	} else if (val == "HKD") {
		return "港元";
	} else if (val == "USD") {
		return "美元";
	} else {
		return "人民币";
	}
}

function changeTblRcvPackDtlIdType(val) {
	if (val == '0') {
		return "身份证";
	} else if (val == '1') {
		return "户口簿";
	} else if (val == '2') {
		return "护照";
	} else if (val == '3') {
		return "军官证";
	} else if (val == '4') {
		return "士兵证";
	} else if (val == '5') {
		return "港澳居民来往内地通行证";
	} else if (val == '6') {
		return "台湾同胞来往内地通行证";
	} else if (val == '7') {
		return "临时身份证";
	} else if (val == '8') {
		return "外国人居留证";
	} else if (val == '9') {
		return "警官证";
	} else if (val == 'X' || val == 'x') {
		return "其他证件";
	} else {
		return val;
	}
}

function changeAuditFlag(val) {
	if (val == '00') {
		return '初审未审核';
	} else if (val == '10') {
		return '初审通过'
	} else if (val == '20') {
		return '初审不通过';
	} else if (val == '30') {
		return '重新审核';
	} else if (val == '01') {
		return '复审未审核';
	} else if (val == '11') {
		return '复审通过';
	} else if (val == '21') {
		return '复审不通过';
	} else if (val == '31') {
		return '重新审核';
	} else {
		return val;
	}
}
function changeSndPackBusCode(val) {
	if (val == '30301') {
		return '人民币批量代付';
	} else if (val == '30401') {
		return '人民币批量代收';
	} else {
		return val;
	}
}
function changeMchtInfoStat(val) {
	if (val == '0') {
		return '正常';
	} else if (val == '1') {
		return '商户新增待审核';
	} else if (val == '2') {
		return '商户新增退回';
	} else if (val == '3') {
		return '商户新增退回修改待审核';
	} else if (val == '4') {
		return '商户变更待审核';
	} else if (val == '5') {
		return '商户变更退回';
	} else if (val == '6') {
		return '商户停用待审核';
	} else if (val == '7') {
		return '商户恢复待审核';
	} else if (val == '8') {
		return '商户停用状态';
	} else if (val == '9') {
		return '注销';
	} else {
		return val
	}
}
function changeMchtInfoFlag(val) {
	if (val == '00') {
		return '不开通代付业务';
	} else if (val == '01') {
		return '不开通代收业务';
	} else if (val == '02') {
		return '不需要替换户名';
	} else if (val == '03') {
		return '不开通单笔代收付业务';
	} else if (val == '04') {
		return '不开通批量代收付业务';
	} else if (val == '10') {
		return '开通代付业务';
	} else if (val == '11') {
		return '开通代收业务';
	} else if (val == '12') {
		return '需要替换户名';
	} else if (val == '13') {
		return '开通单笔代收付业务';
	} else if (val == '14') {
		return '开通批量代收付业务';
	} else {
		return val
	}
}
function changeFileStat(val) {
	if (val == '0') {
		return '未发送';
	} else if (val == '1') {
		return '发送成功';
	} else if (val == '2') {
		return '发送失败';
	} else if (val == '3') {
		return '正在发送';
	} else {
		return val;
	}
}
function changeComboShow(val, cmstore) {
	if (null == val)
		return '';
	var record = cmstore.getById(val);
	if (null != record) {
		return record.data.displayField;
	} else {
		return val;
	}
}

// 风控级别
function riskLevl(val) {
	switch (val) {
	case '0':
		return '低';
	case '1':
		return '中低';
	case '2':
		return '中';
	case '3':
		return '中高';
	case '4':
		return '高';
	default:
		return val;
	}
}

// 差错退货审核状态
function applyState(val) {
	switch (val) {

	case '0':
		return '<font color="gray">未审核</font>';
	case '1':
		return '<font color="green">审核通过</font>';
	case '2':
		return '<font color="red">审核拒绝</font>';

	default:
		return val;
	}
}

// 差错退货交易结果状态
function backState(val) {
	switch (val) {

	case '1':
		return '<font color="green">退货成功</font>';
	case '0':
		return '<font color="red">退货失败</font>';

	default:
		return val;
	}
}

// 差错退货审核状态
function runRiskType(val) {
	switch (val) {

	case '0':
		return '<font color="green">单笔限额</font>';
	case '1':
		return '<font color="green">日累计限额</font>';

	default:
		return val;
	}
}

function countryInfo(val) {
	if (val == '460') {
		return '中国';
	} else {
		return val;
	}
}
function networkInfo(val) {
	if (val == '00') {
		return '移动TD';
	} else if (val == '01') {
		return '联通GSM';
	} else if (val == '02') {
		return '移动GSM';
	} else if (val == '03') {
		return '电信CDMA';
	} else {
		return val;
	}
}

// 退货类型
function amtBackFlag(val) {
	switch (val) {

	case 'C':
		return '<font color="green">差错退货</font>';
	case 'S':
		return '<font color="green">手工退货</font>';
	case 'Y':
		return '<font color="green">业务退货</font>';

	default:
		return val;
	}
}

// 商户种类
function mchtGroupFlag(val) {
	switch (val) {

	case '1':
		return '<font color="green">普通商户</font>';
	case '2':
		return '<font color="green">集团商户</font>';
	case '3':
		return '<font color="green">集团子商户</font>';

	default:
		return val;
	}
}

// 退货交易结果状态
function resState(val) {
	switch (val) {
	case '2':
		return '<font color="red">受理拒绝</font>';
	case '1':
		return '<font color="green">受理成功</font>';
	case '0':
		return '<font color="gray">待受理</font>';
	default:
		return val;
	}
}

/**
 * 机构转换
 */
function insId(val) {
	switch (val) {
	case '1601':
		return '1601-银生宝';
	case '1602':
		return '1602-轩宸科技';
	case '1603':
		return '1603-聚财通';
	case '1604':
		return '1604-鑫银盛';
	case '1605':
		return '1605-全能付';
	case '1606':
		return '1606-畅捷支付';
	case '1701':
		return '1701-中信银行';
	default:
		return val;
	}
}

/**
 * 警报级别
 */
function alarmLvl(val) {
	switch (val) {
	case '1':
		return '<font color="green">低</font>';
	case '2':
		return '<font color="green">中</font>';
	case '3':
		return '<font color="green">高</font>';
	default:
		return val;
	}
}

/**
 * 报警状态
 */
function alarmSta(val) {
	switch (val) {
	case '00':
		return '<font color="red">未处理</font>';
	case '01':
		return '<font color="gray">处理中</font>';
	case '02':
		return '<font color="green">已处理</font>';
	default:
		return val;
	}
}

/**
 * 是否支持IC卡
 */
function ICFlag(val) {
	switch (val) {
	case '0':
		return '<font color="gray">不支持</font>';
	case '1':
		return '<font color="green">支持</font>';
	default:
		return val;
	}
}
/**
 * 冻结交易状态
 */
function blockFlag(val) {
	switch (val) {

	case '0':
		return '<font color="gray">未冻结</font>';
	case '1':
		return '<font color="red">已冻结</font>';
	case '2':
		return '<font color="green">已解冻</font>';
	default:
		return val;
	}
}
/**
 *商户资金冻结状态
 */
function freezekFlag(val) {
	switch (val) {

	case '0':
		return '<font color="gray">待冻结</font>';
	case '1':
		return '<font color="red">部分冻结</font>';
	case '2':
		return '<font color="green">完全冻结</font>';
	default:
		return val;
	}
}
/**
 *商户资金明细冻结标志
 */
function FreezekFlagDtl(val) {
	switch (val) {
	case '1':
		return '<font color="red">冻结</font>';
	case '2':
		return '<font color="green">解冻</font>';
	default:
		return val;
	}
}
/**
 *商户资金解冻状态
 */
function unFreezekFlag(val) {
	switch (val) {

	case '0':
		return '<font color="gray">待解冻</font>';
	case '1':
		return '<font color="red">不解冻</font>';
	case '2':
		return '<font color="green">解冻已结算</font>';
	default:
		return val;
	}
}
/**
 * 提示状态
 */
function cautionFlag(val) {
	switch (val) {
	case '0':
		return '<font color="gray">未提示</font>';
	case '1':
		return '<font color="green">已提示</font>';
	default:
		return val;
	}
}

/**
 * 调单状态
 */
function receiptFlag(val) {
	switch (val) {
	case '0':
		return '<font color="gray">未调单</font>';
	case '1':
		return '<font color="green">已调单</font>';
	case '2':
		return '<font color="green">已回执</font>';
	default:
		return val;
	}
}

/**
 * 冻结商户状态
 */
function blockMchtFlag(val) {
	switch (val) {
	case '0':
		return '<font color="gray">未冻结</font>';
	case '1':
		return '<font color="red">已冻结</font>';
	default:
		return val;
	}
}

/**
 * 白名单商户申请审核状态
 */
function whiteMchtCS(val) {
	if (val == '1') {
		return '<font color="gray">未审核</font>';
	} else if (val == '2') {
		return '<font color="blue">初审通过</font>';
	} else if (val == '3') {
		return '<font color="red">初审不通过</font>';
	} else if (val == '4') {
		return '<font color="green">终审通过</font>';
	} else if (val == '5') {
		return '<font color="red">终审不通过</font>';
	}
	return val;
}

/**
 * 结算通道使用状态
 */
function payChanlStatus(val) {
	switch (val) {
	case '0':
		return '<font color="red">停用</font>';
	case '1':
		return '<font color="green">启用</font>';
	default:
		return val;
	}
}

/**
 * 风控交易清算状态
 */
function settleStatus(val) {
	switch (val) {
	case '0':
		return '<font color="gray">待结算</font>';
	case '1':
		return '<font color="red">不可结算</font>';
	case '2':
		return '<font color="green">已结算</font>';
	case '3':
		return '<font color="green">已提现</font>';
	default:
		return val;
	}
}

/**
 * 开户行号及名称
 */
function cnapsIdName(val) {
	var va = val.substring(0, 1);
	if (va == '*') {
		return '<font color="gray">默认</font>';
	} else {
		return val;
	}
}

/**
 * 风控卡黑名单状态
 */
function blackCard(val) {
	switch (val) {
	case '0':
		return '<font color="green">正常</font>';
	case '1':
		return '<font color="red">已入黑名单</font>';
	default:
		return val;
	}
}

/**
 * 风控模型修改关联项
 */
function saModelconn(val) {
	if (val == 'A') {
		return '<font color="gray">- 无限制 -</font>';
	} else if (val == 'S') {
		return '<font color="black">- 风控模型状态 -</font>';
	} else if (val == 'W') {
		return '- 风控警报级别 -';
	} else if (val == 'P') {
		return '- 参数值重定义 -';
	}
	return '- 风控等级 [ ' + val + ' ] -';
}

/**
 * 卡类型
 */
function cardTypeTl(val) {
	switch (val) {
	case '00':
		return '借记卡';
	case '01':
		return '贷记卡';
	case '02':
		return '准贷记卡';
	case '03':
		return '预付费卡';
	case '04':
		return '公务卡';
	default:
		return '未知卡';
	}
}

/**
 * 银行卡黑名单操作类型
 */

function blackCardOptFlag(val) {
	if (val == '1') {
		return '<font color="green">新增</font>';
	} else if (val == '0') {
		return '<font color="red">删除</font>';
	}
	return val;
}

/**
 * 终端添加状态
 */

function termAddSta(val) {
	if (val == '0') {
		return '<font color="red">未添加</font>';
	} else {
		return '<font color="green">已添加</font>';
	}
	return val;
}

// 机构分润费率状态
function feeCfgStatus(val) {
	switch (val) {
	case '0':
		return '<font color="red">停用</font>';
	case '1':
		return '<font color="green">启用</font>';
	default:
		return val;
	}
}

//分润费率标志
function feeType(val) {
	switch (val) {
	case '0':
		return '<font>比例%</font>';
	case '1':
		return '<font>封顶</font>';
	default:
		return val;
	}
}

// 机构分润费率设置 无限制
function feeCtl(val) {
	if (val == '*') {
		return '<font color="gray">无限制</font>';
	} else {
		return val;
	}
}

// 是否标准入网
function joinType(val) {
	if (val == '0') {
		return '<font color="green">标准</font>';
	} else if (val == '1') {
		return '<font color="gray">非标</font>';
	} else {
		return val;
	}
}
/**
 * 提现状态
 */

function wdStatus(val) {
	if (val == '0') {
		return '<font color="green">已完成</font>';
	} else if (val == '1') {
		return '<font color="gray">待审核</font>';
	} else if (val == '3') {
		return '<font color="red">初审拒绝</font>';
	} else if (val == '2') {
		return '<font color="blue">处理中</font>';
	} else if (val == '4') {
		return '<font color="red">处理失败</font>';
	} else if (val == '5') {
		return '<font color="red">待审核</font>';
	} else if (val == '6') {
		return '<font color="red">处理中</font>';
	} else if (val == '7') {
		return '<font color="red">拒绝</font>';
	}
	return '<font color="red">未知状态</font>';
}
/**
 * 审核返回错误状态
 */

function wdAppStatus(val) {
	if (val == '06') {
		return '<font color="red">代付失败！</font>';
	} else if (val == '07') {
		return '<font color="red">代付超时！</font>';
	} else if (val == '08') {
		return '<font color="red">未设置提现限额！</font>';
	} else if (val == '09') {
		return '<font color="red">未设置提现单笔限额！</font>';
	} else if (val == '10') {

		return '<font color="red">账户系统无法访问！</font>';
	} else if (val == '11') {

		return '<font color="red">提现单笔限额超限！</font>';

	} else if (val == '12') {

		return '<font color="red">提现交易不存在！</font>';
	} else if (val == '13') {

		return '<font color="red">提现交易重复提交！</font>';
	} else if (val == '14') {

		return '<font color="red">提现交易消费已被提现！</font>';

	} else if (val == '00') {
		return '<font color="green">此批交易可提现</font>';

	} else if (val == '01') {

		return '<font color="red">提现金额超过提现限额！</font>';
	} else if (val == '02') {

		return '<font color="red">商户未设置手续费！</font>';
	} else if (val == '03') {

		return '<font color="red">商户为考通T+0！</font>';
	} else if (val == '04') {

		return '<font color="red">提现金额超过可提现！</font>';
	} else if (val == '05') {

		return '<font color="red">提现金额超过可提现头寸金额！</font>';
	} else if (val == '99') {
		return '<font color="red">此批交易不可提现：由于下面列表中交易被拒绝提现！</font>';
	} else if (val == '16') {
		return '<font color="red">未设置提现交易时段！</font>';
	} else if (val == '17') {
		return '<font color="red">未在交易时段！</font>';
	}
	return '';
}

/**
 * 新清结算结算标志
 */
function stlmFlag(val) {
	switch (val) {
	case '0':
		return '<font color="gray">待结算</font>';
	case '1':
		return '<font color="red">不可结算</font>';
	case '2':
		return '<font color="green">已结算</font>';
	default:
		return val;
	}
}

/**
 * 启用grid表格中的数据复制功能
 * 使用方式：给数据显示gird添加cellclick事件，示例：'cellclick':selectableCell
 */
function selectableCell(){
	var ele = Ext.EventObject.getTarget();
	if(ele && ele.nodeType == 1 && ele.hasAttribute("unselectable")){
		if(ele.getAttributeNode("unselectable").value == 'on'){
			ele.getAttributeNode("unselectable").value = "off";
		}
	}
	var selRule = 'div[unselectable]';
	if(!Ext.util.CSS.getRule(selRule, false)){ 
		Ext.util.CSS.createStyleSheet(selRule + '{-khtml-user-select:text!important;-moz-user-select:text!important;-webkit-user-select:text!important}','selectableCell'); 
	}
}

function currType(val) {
	switch (val) {
	case '156':
		return '人民币';
	default:
		return val;
	}
}

function getGridRowDetail(record, columns){

	var content = '<table>';
	var count = columns.getColumnCount();
	var displayColumns = null;
	var colMArray = new Array();
	var getColumnContent = function() {
		for (var i = 2; i < count; i++){
			var tmp = "";
			if (columns.getDataIndex(i) == null || columns.getDataIndex(i) == '' || columns.isHidden(i) == true) {
				continue;
			}
			tmp += "<td align='right'width='200'>" + columns.getColumnHeader(i) + ':</td>' + "<td align='left' width='200'>";
			var rendererFunc = columns.getRenderer(i);
			if (rendererFunc != null  && rendererFunc != 'undefined') {
				tmp += rendererFunc(record.get(columns.getDataIndex(i))) + '<br/>';
			} else {
				tmp += record.get(columns.getDataIndex(i)) + '<br/>';
			}
			tmp += '</td>'
			colMArray.push(tmp);;
		}
	}
	getColumnContent();
	var i = 0, j = 1;
	count = colMArray.length;
	for(; i < count/2; i++,j++){
		content += '<tr>';
		content += colMArray[i];
		content += colMArray[count - j];
		content += '</tr>';
	}
	content += '<tr>';
	if (i == count - j){
		content += colMArray[i];
	} 
	content += '</tr>';
	content += '</table>';
	return content;
}
