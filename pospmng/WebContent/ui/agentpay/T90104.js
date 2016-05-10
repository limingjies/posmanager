//处理状态下拉列表 
var procStatComboList = new Ext.data.SimpleStore({
			fields : ["value", "text"],
			data : [["","全部"],["0", "已登记包头"], ["1", "入库中"], ["2", "已入库"], ["3", "正在发送"],
					["4", "发送成功"], ["5", "发送失败"], ["6", "接收回执中"], ["7", "已收到回执"],
					["8", "待回执"], ["9", "发送回执中"], ["A", "已发送回执"],["B","已退汇"],["E", "文件错误"]]
		});
//明细处理结果 
var  rspCodeComboList = new Ext.data.SimpleStore({
			fields : ["value", "text"],
			data : [["","全部"],["0", "处理成功"], ["1", "处理失败"],["2","未处理"]]
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
				& Ext.getCmp('searchAcctNo').isValid()
				& Ext.getCmp('searchBankCode').isValid()
				&Ext.getCmp('searchStartEntDate').isValid()
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
		Ext.getCmp('searchRspCode').clearValue();
		Ext.getCmp('mchtNoId').clearValue();
		Ext.getCmp('searchBankCode').setValue("");
		Ext.getCmp('searchAcctNo').setValue("");
		Ext.getCmp('searchName').setValue("");
	}
}
lefttbarArr.push(btn1);
lefttbarArr.push("-");
lefttbarArr.push(btn2);
var queryConditionTbar1 = new Ext.Toolbar({
			renderTo : Ext.grid.EditorGridPanel.tbar,
			items : ["批次号:", {
						xtype : 'textfield',
						id : 'searchBatchId',
						width : 110,
						maxLength : 14,
						regex:/^\d{0,14}$/,
						name : 'searchBatchId',
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
					},"-", "明细处理结果:", {
						xtype : 'combo',
						id : 'searchRspCode',
						width : 110,
						name : 'searchRspCode',
						fieldLabel : '明细处理结果',
						emptyText : "请选择",
						editable : true,
						triggerAction: 'all',
						typeAhead: true,
						selectOnFocus: true,
						forceSelection: true,
						valueField : 'value',// 值
						displayField : 'text',// 显示文本
						store : rspCodeComboList
					}]
		});
var queryConditionTbar2 = new Ext.Toolbar({
			renderTo : Ext.grid.EditorGridPanel.tbar,
			items : ["-", "商户号:", {
						id : "mchtNoId",
						name : "mchtNoNm",
						xtype: 'basecomboselect',
			        	store: dsfMchtNoStore,
			        	width:140,
			        	valueField : 'valueField',// 值
						displayField : 'displayField',// 显示文本
						hiddenName: 'searchMchtNo'
					},"-", "银行代码:", {
						xtype : 'textfield',
						id : 'searchBankCode',
						width : 110,
						maxLength : 14,
						regex:/^\d{0,14}$/,
						name : 'searchBankCode',
						fieldLabel : '银行代码'
					}, "-", "账号:", {
						xtype : 'textfield',
						id : 'searchAcctNo',
						width : 110,
						maxLength : 32,
						regex:/^\d{0,32}$/,
						name : 'searchAcctNo',
						fieldLabel : '账号'
					}, "-", "户名:", {
						xtype : 'textfield',
						id : 'searchName',
						width : 110,
						maxLength : 40,
						name : 'searchName',
						fieldLabel : '户名'
					}]
		});
/** *******************************数据************************************* */

var leftGridStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : "gridPanelStoreAction.asp?storeId=rcvPackDtlData"
					}),
			reader : new Ext.data.JsonReader({
						root : "data",
						totalProperty : "totalCount"
					}, [{
								name : "entDate",
								mapping : "entDate"
							}, {
								name : "batchId",
								mapping : "batchId"
							}, {
								name : "seq",
								mapping : "seq"
							}, {
								name : "mchtNo",
								mapping : "mchtNo"
							}, {
								name : "rcvDate",
								mapping : "rcvDate"
							}, {
								name : "procStat",
								mapping : "procStat"
							}, {
								name : "rcvBatch_id",
								mapping : "rcvBatch_id"
							}, {
								name : "custId",
								mapping : "custId"
							}, {
								name : "bankCode",
								mapping : "bankCode"
							}, {
								name : "acctNo",
								mapping : "acctNo"
							}, {
								name : "name",
								mapping : "name"
							}, {
								name : "province",
								mapping : "province"
							}, {
								name : "city",
								mapping : "city"
							}, {
								name : "opnOrgName",
								mapping : "opnOrgName"
							}, {
								name : "acctType",
								mapping : "acctType"
							}, {
								name : "custType",
								mapping : "custType"
							}, {
								name : "amt",
								mapping : "amt"
							}, {
								name : "curCd",
								mapping : "curCd"
							}, {
								name : "agtNo",
								mapping : "agtNo"
							}, {
								name : "agtCustNo",
								mapping : "agtCustNo"
							}, {
								name : "idType",
								mapping : "idType"
							}, {
								name : "idNo",
								mapping : "idNo"
							}, {
								name : "mobile",
								mapping : "mobile"
							}, {
								name : "custSelfNo",
								mapping : "custSelfNo"
							}, {
								name : "remark",
								mapping : "remark"
							}, {
								name : "rspCode",
								mapping : "rspCode"
							}, {
								name : "rspDesc",
								mapping : "rspDesc"
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
			sortable : true
		},  {
			header : "明细序号",
			width : 60,
			dataIndex : "seq",
			sortable : false
		},{
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
			header : "来帐日期",
			width : 80,
			dataIndex : "rcvDate",
			sortable : false
		}, {
			header : "处理状态",
			width : 80,
			dataIndex : "procStat",
			sortable : false,
			renderer:changeTblRcvPackDtlProcStat
		}, {
			header : "来帐批次号",
			width : 80,
			dataIndex : "rcvBatch_id",
			sortable : false
		}, {
			header : "用户编号",
			width : 80,
			dataIndex : "custId",
			sortable : false
		}, {
			header : "银行代码",
			width : 80,
			dataIndex : "bankCode",
			sortable : false
		}, {
			header : "账号",
			width : 100,
			dataIndex : "acctNo",
			sortable : false
		}, {
			header : "户名",
			width : 80,
			dataIndex : "name",
			sortable : false
		}, {
			header : "省",
			width : 80,
			dataIndex : "province",
			sortable : false
		}, {
			header : "市",
			width : 80,
			dataIndex : "city",
			sortable : false
		}, {
			header : "开户行名称",
			width : 100,
			dataIndex : "opnOrgName",
			sortable : false
		}, {
			header : "账户类型",
			width : 80,
			dataIndex : "acctType",
			sortable : false,
			renderer:changeTblRcvPackDtlAcctType
		}, {
			header : "客户类型",
			width : 80,
			dataIndex : "custType",
			sortable : false,
			renderer:changeTblRcvPackDtlCustType
		}, {
			header : "金额",
			width : 100,
			dataIndex : "amt",
			sortable : false
		}, {
			header : "货币类型",
			width : 80,
			dataIndex : "curCd",
			sortable : false,
			renderer:changeTblRcvPackDtlCurCd
		}, {
			header : "协议号",
			width : 100,
			dataIndex : "agtNo",
			sortable : false
		}, {
			header : "协议用户编号",
			width : 100,
			dataIndex : "agtCustNo",
			sortable : false
		}, {
			header : "证件类型",
			width : 80,
			dataIndex : "idType",
			sortable : false,
			renderer:changeTblRcvPackDtlIdType
		}, {
			header : "证件号",
			width : 100,
			dataIndex : "idNo",
			sortable : false
		}, {
			header : "手机号",
			width : 100,
			dataIndex : "mobile",
			sortable : false
		}, {
			header : "自定义用户号",
			width : 80,
			dataIndex : "custSelfNo",
			sortable : false
		}, {
			header : "备注",
			width : 120,
			dataIndex : "remark",
			sortable : false
		}, {
			header : "响应状态",
			width : 65,
			dataIndex : "rspCode",
			sortable : false,
			renderer: function(val){
				if(val ==""){
					return '未处理';
				}else	if(val =='0000'){
					return '<font color="green">处理成功</font>';
				}else{
					return '<font color="red">处理失败</font>';
				}
			}
		}, {
			header : "原因",
			width : 270,
			dataIndex : "rspDesc",
			sortable : false
		}, 
//			{
//			header : "FLAG1",
//			width : 60,
//			dataIndex : "flag1",
//			hidden:true,
//			sortable : false
//		}, {
//			header : "FLAG2",
//			width : 60,
//			dataIndex : "flag2",
//			hidden:true,
//			sortable : false
//		}, {
//			header : "FLAG3",
//			width : 60,
//			dataIndex : "flag3",
//			hidden:true,
//			sortable : false
//		}, 
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
					msg : '正在加载代收付来帐明细登记簿列表......'
				},
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
		title:"代收付来帐明细登记簿",
		frame: true,
		layout: 'border',
		tbar :[lefttbarArr],
		listeners:{
			'render' : function() {
				queryConditionTbar1.render(this.tbar);
				queryConditionTbar2.render(this.tbar);
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
		Ext.get(leftGrid.getView().getRow(leftGrid.getSelectionModel().last))
				.frame();
	}
});
leftGridStore.on("beforeload", function() {
	leftGridStore.rejectChanges();
	Ext.apply(this.baseParams, {
		start : 0,
		batchId : Ext.getCmp('searchBatchId').getValue(),
		startEntDate : typeof(Ext.getCmp('searchStartEntDate').getValue()) == 'string'? '': Ext.getCmp('searchStartEntDate').getValue().format('Ymd'),
		stopEntDate : typeof(Ext.getCmp('searchStopEntDate').getValue()) == 'string'? '': Ext.getCmp('searchStopEntDate').getValue().format('Ymd'),
		procStat : Ext.getCmp('searchProcStat').getValue(),
		rspCode : Ext.getCmp('searchRspCode').getValue(),
		mchtNo : Ext.get('searchMchtNo').getValue(),
		bankCode : Ext.getCmp('searchBankCode').getValue(),
		acctNo : Ext.getCmp('searchAcctNo').getValue(),
		name : Ext.getCmp('searchName').getValue()
	})
});