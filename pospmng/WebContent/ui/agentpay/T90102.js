//处理状态下拉列表 
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
var lefttbarArr = new Array();

var btn1 = {
	text : '查询',
	iconCls : 'query',
	handler : function() {
		if (Ext.getCmp('searchBatchId').isValid()
				& Ext.getCmp('searchStartEntDate').isValid()
				&Ext.getCmp('searchStopEntDate').isValid()) {
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
var queryConditionTbar1 = new Ext.Toolbar({
			renderTo : Ext.grid.EditorGridPanel.tbar,
			items : ["-", "批次号:", {
						xtype : 'textfield',
						id : 'searchBatchId',
						width : 110,
						maxLength : 14,
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
						triggerAction: 'all',
						typeAhead: true,
						selectOnFocus: true,
						forceSelection: true,
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
/** *******************************数据************************************* */
var leftGridStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'gridPanelStoreAction.asp?storeId=rcvPacksData'
					}),
			reader : new Ext.data.JsonReader({
						root : "data",
						totalProperty : "totalCount",
						idProperty:"batchId"
					},[{
								name : "entDate",
								mapping : "entDate"
							}, {
								name : "batchId",
								mapping : "batchId"
							}, {
								name : "busType",
								mapping : "busType"
							}, {
								name : "mchtNo",
								mapping : "mchtNo"
							}, {
								name : "rcvDate",
								mapping : "rcvDate"
							}, {
								name : "rcvBatchId",
								mapping : "rcvBatchId"
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
								name : "feeCode",
								mapping : "feeCode"
							}, {
								name : "curCd",
								mapping : "curCd"
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
								name : "ver",
								mapping : "ver"
							}, {
								name : "rcvFileName",
								mapping : "rcvFileName"
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

/** *******************************模版************************************* */
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
			header : "业务类型",
			width : 80,
			dataIndex : "busType",
			sortable : false,
			renderer:changeTblRcvPackBusType
		}, {
			header : "商户号",
			width : 120,
			dataIndex : "mchtNo",
			sortable : false
		}, {
			header : "来帐日期",
			width : 75,
			dataIndex : "rcvDate",
			sortable : false
		}, {
			header : "来帐批次号",
			width : 75,
			dataIndex : "rcvBatchId",
			sortable : false
		}, {
			header : "处理状态",
			width : 75,
			dataIndex : "procStat",
			sortable : false,
			renderer:changeTblRcvPackProcStat
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
		},  {
			header : "费用代码",
			width : 60,
			dataIndex : "feeCode",
			sortable : false
		}, {
			header : "货币类型",
			width : 60,
			dataIndex : "curCd",
			sortable : false,
			renderer:changeTblRcvPackDtlCurCd
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
		},  {
			header : " 失败笔数",
			width : 60,
			dataIndex : "failCnt",
			sortable : false
		}, {
			header : "失败金额",
			width : 80,
			dataIndex : "failAmt",
			sortable : false
		},{
			header : "版本号",
			width : 60,
			dataIndex : "ver",
			hidden : true,
			sortable : false
		}, {
			header : "来帐文件名",
			width : 270,
			dataIndex : "rcvFileName",
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
			hidden : true,
			sortable : false,
			renderer:changeTblRcvLockFlag
		}, {
			header : "审核标志",
			width : 100,
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
			{
			header : "FLAG1",
			width : 60,
			dataIndex : "flag1",
			hidden : true,
			sortable : false
		}, {
			header : "FLAG2",
			width : 60,
			dataIndex : "flag2",
			hidden:true,
			sortable : false
		}, {
			header : "FLAG3",
			width : 60,
			dataIndex : "flag3",
			hidden:true,
			sortable : false
		}, 
//			{
//			header : "保留域1",
//			width : 60,
//			dataIndex : "misc1",
//			hidden:true,
//			sortable : false
//		}, {
//			header : "保留域2",
//			width : 60,
//			dataIndex : "misc2",
//			hidden:true,
//			sortable : false
//		}, {
//			header : "保留域3",
//			width : 60,
//			dataIndex : "misc3",
//			hidden:true,
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

/** *******************************界面************************************* */

var leftGrid = new Ext.grid.EditorGridPanel({
//			title : "代收付来帐包登记簿",
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
					msg : '正在加载代收付来帐包登记簿列表......'
				},
//			tbar:queryConditionArr,
			bbar:new Ext.PagingToolbar({
					store:leftGridStore,
					pageSize:System[QUERY_RECORD_COUNT],
					displayInfo : true,
					displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
					emptyMsg : '没有找到符合条件的记录'
			})
		});

var leftPanel = new Ext.Panel({
		region: 'center',
		title:"代收付来帐包登记簿",
		frame: true,
		layout: 'border',
		tbar :[lefttbarArr],
		listeners:{
			'render' : function() {
				queryConditionTbar1.render(this.tbar);
			}
		},
		items:[leftGrid]
});
var mainUI = new Ext.Viewport({
			layout : 'border',
			items : [leftPanel]
		});
		
/** *******************************事件处理************************************* */
		
leftGrid.getSelectionModel().on({
	'rowselect' : function() {
		// 选中后数据行高亮
		Ext.get(leftGrid.getView().getRow(leftGrid.getSelectionModel().last)).frame();
	}
});
leftGridStore.on("beforeload",function(){
	Ext.apply(this.baseParams,{
		start:0,
		batchId:Ext.getCmp('searchBatchId').getValue(),
		startEntDate:typeof(Ext.getCmp('searchStartEntDate').getValue()) == 'string' ? '' : Ext.getCmp('searchStartEntDate').getValue().format('Ymd'),
		stopEntDate:typeof(Ext.getCmp('searchStopEntDate').getValue()) == 'string' ? '' : Ext.getCmp('searchStopEntDate').getValue().format('Ymd'),
		procStat:Ext.getCmp('searchProcStat').getValue(),
		mchtNo:Ext.get('searchMchtNo').getValue()
	})
});