// 处理状态下拉列表
var procStatComboList = new Ext.data.SimpleStore({
			fields : ["value", "text"],
			data : [["","全部"],["0", "已登记包头"], ["1", "入库中"], ["2", "已入库"], ["3", "正在发送"],
					["4", "发送成功"], ["5", "发送失败"], ["6", "接收回执中"], ["7", "已收到回执"],
					["8", "待回执"], ["9", "发送回执中"], ["A", "已发送回执"],["B","已退汇"],["E", "文件错误"]]
		});
var dsfMchtNoStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	SelectOptionsDWR.getComboData('DSF_MCHT_NO',function(ret){
		dsfMchtNoStore.loadData(Ext.decode(ret));
	});
/** ********************* 左边 ************************ */
var queryConditionTbar1 = new Ext.Toolbar({
			renderTo : Ext.grid.EditorGridPanel.tbar,
			items : ["-", "批次号:", {
						xtype : 'textfield',
						id : 'searchBatchId',
						width : 110,
						name : 'searchBatchId',
						regex:/^\d{0,14}$/,
						fieldLabel : '批次号'
					}, "-", "起始时间:", {
						xtype : 'datefield',
						id : 'searchStartEntDate',
						width : 110,
						format : 'Y-m-d',
						name : 'searchStartEntDate',
						fieldLabel : '起始时间'
					}, "-", "截止时间:", {
						xtype : 'datefield',
						id : 'searchStopEntDate',
						width : 110,
						format : 'Y-m-d',
						name : 'searchStopEntDate',
						fieldLabel : '截止时间'
					},"-", "处理状态:", {
						xtype : 'combo',
						id : 'searchProcStat',
						width : 110,
						name : 'searchProcStat',
						fieldLabel : '处理状态',
						emptyText : "请选择",
						editable : true,
						triggerAction : 'all',
						typeAhead : true,
						selectOnFocus : true,
						forceSelection : true,
						valueField : 'value',// 值
						displayField : 'text',// 显示文本
						store : procStatComboList
					}, "-", "商户号:", {
						id : "mchtNoId",
						name : "mchtNoNm",
						xtype: 'basecomboselect',
						editable : true,
			        	store: dsfMchtNoStore,
			        	width:130,
			        	valueField : 'valueField',// 值
						displayField : 'displayField',// 显示文本
						hiddenName: 'searchMchtNo'
					}]
		});
var lefttbarArr = new Array();
var btn1 = {
	text : '查询',
	iconCls : 'query',
	handler : function() {
		if (Ext.getCmp('searchBatchId').isValid()
				& Ext.getCmp('searchStartEntDate').isValid()
				& Ext.getCmp('searchStopEntDate').isValid()) {
			leftGridStore.load();
		}
	}
}
var btn2 ={
	text : '重置查询条件',
	iconCls : 'reset',
	handler : function(){
		Ext.getCmp('searchBatchId').setValue("");
		Ext.getCmp('searchStartEntDate').setValue("");
		Ext.getCmp('searchStopEntDate').setValue("");
		Ext.getCmp('searchProcStat').clearValue();
		Ext.getCmp('mchtNoId').clearValue();
	}
}
lefttbarArr.push(btn1);
lefttbarArr.push("-");
lefttbarArr.push(btn2);
var leftGridStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'gridPanelStoreAction.asp?storeId=sndPacksData'
					}),
			reader : new Ext.data.JsonReader({
						root : "data",
						totalProperty : "totalCount",
						idProperty : "batchId"
					}, [{
								name : "batchId",
								mapping : "batchId"
							}, {
								name : "entDate",
								mapping : "entDate"
							}, {
								name : "mchtNo",
								mapping : "mchtNo"
							}, {
								name : "busType",
								mapping : "busType"
							}, {
								name : "comCode",
								mapping : "comCode"
							}, {
								name : "fileBatchId",
								mapping : "fileBatchId"
							}, {
								name : "bankCode",
								mapping : "bankCode"
							}, {
								name : "feeCode",
								mapping : "feeCode"
							}, {
								name : "procStat",
								mapping : "procStat"
							}, {
								name : "totCnt",
								mapping : "totCnt"
							}, {
								name : "totAmt",
								mapping : "totAmt"
							}, {
								name : "rspDate",
								mapping : "rspDate"
							}, {
								name : "succCnt",
								mapping : "succCnt"
							}, {
								name : "succAmt",
								mapping : "succAmt"
							}, {
								name : "failCnt",
								mapping : "failCnt"
							}, {
								name : "failAmt",
								mapping : "failAmt"
							}, {
								name : "busCode",
								mapping : "busCode"
							}, {
								name : "tranDate",
								mapping : "tranDate"
							}, {
								name : "curCd",
								mapping : "curCd"
							}, {
								name : "txnFileName",
								mapping : "txnFileName"
							}, {
								name : "fileName",
								mapping : "fileName"
							}, {
								name : "agentAcctNo",
								mapping : "agentAcctNo"
							}, {
								name : "agentAcctName",
								mapping : "agentAcctName"
							}, {
								name : "rNameFlag",
								mapping : "rNameFlag"
							}, {
								name : "rMchtNo",
								mapping : "rMchtNo"
							}, {
								name : "prodCode",
								mapping : "prodCode"
							}, {
								name : "regDate",
								mapping : "regDate"
							}, {
								name : "regTime",
								mapping : "regTime"
							}, {
								name : "batSsn",
								mapping : "batSsn"
							}, {
								name : "lockFlag",
								mapping : "lockFlag"
							}, {
								name : "auditFlag",
								mapping : "auditFlag"
							}, {
								name : "preAuditTlr",
								mapping : "preAuditTlr"
							}, {
								name : "checkTlr",
								mapping : "checkTlr"
							}, {
								name : "finalAuditTlr",
								mapping : "finalAuditTlr"
							}, {
								name : "flag1",
								mapping : "flag1"
							}, {
								name : "flag2",
								mapping : "flag2"
							}, {
								name : "flag3",
								mapping : "flag3"
							}, {
								name : "misc1",
								mapping : "misc1"
							}, {
								name : "misc2",
								mapping : "misc2"
							}, {
								name : "misc3",
								mapping : "misc3"
							}, {
								name : "lstUpdTlr",
								mapping : "lstUpdTlr"
							}, {
								name : "lstUpdTime",
								mapping : "lstUpdTime"
							}, {
								name : "createTime",
								mapping : "createTime"
							}]),
			autoLoad : true
		});

var leftGridColModel = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
			header : "批次号",
			width : 120,
			dataIndex : "batchId",
			sortable : false
		}, {
			header : "委托日期",
			width : 75,
			dataIndex : "entDate",
			sortable : false
		}, {
			header : "商户号",
			width : 120,
			dataIndex : "mchtNo",
			sortable : false
		}, {
			header : "业务类型",
			width : 80,
			dataIndex : "busType",
			sortable : false,
			renderer:changeTblRcvPackBusType
		}, {
			header : "企业代码",
			width : 120,
			dataIndex : "comCode",
			sortable : false
		}, {
			header : "文件批次号",
			width : 75,
			dataIndex : "fileBatchId",
			sortable : false
		}, {
			header : "代办银行代码",
			width : 60,
			dataIndex : "bankCode",
			sortable : false
		}, {
			header : "费项代码",
			width : 60,
			dataIndex : "feeCode",
			sortable : false
		}, {
			header : "处理状态",
			width : 80,
			dataIndex : "procStat",
			sortable : false,
			renderer : changeTblRcvPackProcStat
		}, {
			header : "总笔数",
			width : 60,
			dataIndex : "totCnt",
			sortable : false
		}, {
			header : "总金额",
			width : 80,
			dataIndex : "totAmt",
			sortable : false
		}, {
			header : "回执日期",
			width : 75,
			dataIndex : "rspDate",
			sortable : false
		}, {
			header : "成功笔数",
			width : 60,
			dataIndex : "succCnt",
			sortable : false
		}, {
			header : "成功金额",
			width : 80,
			dataIndex : "succAmt",
			sortable : false
		}, {
			header : "失败笔数",
			width : 60,
			dataIndex : "failCnt",
			sortable : false
		}, {
			header : "失败金额",
			width : 80,
			dataIndex : "failAmt",
			sortable : false
		}, {
			header : "业务编码",
			width : 100,
			dataIndex : "busCode",
			sortable : false,
			renderer : changeSndPackBusCode
		}, {
			header : "应划款日期",
			width : 75,
			dataIndex : "tranDate",
			sortable : false
		}, {
			header : "币种",
			width : 60,
			dataIndex : "curCd",
			sortable : false,
			renderer:changeTblRcvPackDtlCurCd
		}, {
			header : "交易文件名",
			width : 100,
			dataIndex : "txnFileName",
			sortable : false
		}, {
			header : "文件名",
			width : 200,
			dataIndex : "fileName",
			sortable : false
		}, {
			header : "代办账号",
			width : 60,
			dataIndex : "agentAcctNo",
			sortable : false
		}, {
			header : "代办账户名称",
			width : 60,
			dataIndex : "agentAcctName",
			sortable : false
		}, {
			header : "替换户名标志",
			width : 60,
			dataIndex : "rNameFlag",
			sortable : false
		}, {
			header : "替换二级商户代码",
			width : 60,
			dataIndex : "rMchtNo",
			sortable : false
		}, {
			header : "产品编码",
			width : 60,
			dataIndex : "prodCode",
			sortable : false
		}, {
			header : "登记日期",
			width : 75,
			dataIndex : "regDate",
			sortable : false
		}, {
			header : "登记时间",
			width : 75,
			dataIndex : "regTime",
			sortable : false
		}, {
			header : "联机批量批次号",
			width : 120,
			dataIndex : "batSsn",
			sortable : false
		}, {
			header : "锁定标志",
			width : 60,
			dataIndex : "lockFlag",
			sortable : false,
			renderer:changeTblRcvLockFlag
		}, {
			header : "审核标志",
			width : 80,
			dataIndex : "auditFlag",
			sortable : false,
			renderer:changeAuditFlag
		}, {
			header : "初审人",
			width : 80,
			dataIndex : "preAuditTlr",
			sortable : false
		}, {
			header : "复核人",
			width : 80,
			dataIndex : "checkTlr",
			sortable : false
		}, {
			header : "终审人",
			width : 80,
			dataIndex : "finalAuditTlr",
			sortable : false
		}, 
//			{
//			header : "FLAG1",
//			width : 60,
//			dataIndex : "flag1",
//			sortable : false
//		}, {
//			header : "FLAG2",
//			width : 60,
//			dataIndex : "flag2",
//			sortable : false
//		}, {
//			header : "FLAG3",
//			width : 60,
//			dataIndex : "flag3",
//			sortable : false
//		}, 
		{
			header : "拒绝原因",
			width : 60,
			dataIndex : "misc1",
			sortable : false
		}, 
//			{
//			header : "保留域2",
//			width : 60,
//			dataIndex : "misc2",
//			sortable : false
//		}, {
//			header : "保留域3",
//			width : 60,
//			dataIndex : "misc3",
//			sortable : false
//		}, 
			{
			header : "最近更新柜员",
			width : 80,
			dataIndex : "lstUpdTlr",
			sortable : false
		}, {
			header : "最近更新时间",
			width : 120,
			dataIndex : "lstUpdTime",
			sortable : false
		}, {
			header : "创建时间",
			width : 120,
			dataIndex : "createTime",
			sortable : false
		}]);
var leftGrid = new Ext.grid.EditorGridPanel({
			region : "center",
			frame : true,
			border : true,
			columnLines : true,
			stripeRows : true,
			store : leftGridStore,
			sm : new Ext.grid.RowSelectionModel({
						singleSelect : true
					}),
			cm : leftGridColModel,
			forceValidation : true,
			clicksToEdit : true,
			loadMask : {
				msg : '正在加载代收付往帐包登记簿列表......'
			},
			// tbar:queryConditionArr,
			bbar : new Ext.PagingToolbar({
						store : leftGridStore,
						pageSize : System[QUERY_RECORD_COUNT],
						displayInfo : true,
						displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
						emptyMsg : '没有找到符合条件的记录'
					})
		});

var leftPanel = new Ext.Panel({
			region : 'center',
			title : "代收付往帐包登记簿",
			frame : true,
			layout : 'border',
			 tbar:[lefttbarArr],
			listeners : {
				'render' : function() {
					queryConditionTbar1.render(this.tbar);
				}
			},
			items : [leftGrid]
		});
/** *******************************事件处理************************************* */
leftGrid.getSelectionModel().on({
	'rowselect' : function() {
		// 选中后数据行高亮
		Ext.get(leftGrid.getView().getRow(leftGrid.getSelectionModel().last))
				.frame();
	}
});
leftGridStore.on("beforeload", function() {
	Ext.apply(this.baseParams, {
		start : 0,
		batchId : Ext.getCmp('searchBatchId').getValue(),
		startEntDate : typeof(Ext.getCmp('searchStartEntDate').getValue()) == 'string'
				? ''
				: Ext.getCmp('searchStartEntDate').getValue().format('Ymd'),
		stopEntDate : typeof(Ext.getCmp('searchStopEntDate').getValue()) == 'string'
				? ''
				: Ext.getCmp('searchStopEntDate').getValue().format('Ymd'),
		procStat : Ext.getCmp('searchProcStat').getValue(),
		mchtNo:Ext.get('searchMchtNo').getValue()
	})
});
/** ******************** 主页面 *************************** */
var mainUI = new Ext.Viewport({
			layout : 'border',
			items : [leftPanel]
		});