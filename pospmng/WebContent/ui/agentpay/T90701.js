var task = {
	//每两秒刷新一次 leftGridStore
	run: function() {
		leftGridStore.reload();
		Ext.TaskMgr.start(task2);
	},
	interval: 2000,
	repeat : 1
};
var task2 = {
	//每80秒刷新一次 leftGridStore
	run: function() {
		leftGridStore.reload();
	},
	interval: 80000
};
var fileStatComboList = new Ext.data.SimpleStore({
			fields : ["value", "text"],
			data : [["0", "未发送"], ["1", "发送成功"], ["2", "发送失败"],["3","正在发送"]]
		});
var dsfMchtNoStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	SelectOptionsDWR.getComboData('DSF_MCHT_NO',function(ret){
		dsfMchtNoStore.loadData(Ext.decode(ret));
	});
var tbarArr = new Array();
var btn1 = {
	text : '发送指定对帐文件',
	width : 85,
	iconCls : 'accept',
	disabled : true,
	handler : function() {
		var mchtNo = leftGrid.getSelectionModel().getSelected().data.mchtNo;
		var stlmDate = leftGrid.getSelectionModel().getSelected().data.stlmDate;
		var oprType = "1";
		SendStlmFiles.sendStlmFileMsg(mchtNo, oprType,stlmDate,
				function(ret) {
					if ("S" == ret) {
						leftGridStore.reload();
						 Ext.TaskMgr.start(task);
						showSuccessMsg("处理操作成功！", leftPanel);
					} else {
						leftGridStore.reload();
						 Ext.TaskMgr.stop(task);
						 Ext.TaskMgr.stop(task2);
						showErrorMsg(ret, leftPanel);
					}
				})
	}
}
var btn2 = {
	text : '发送全部对帐文件',
	width : 85,
	iconCls : 'accept',
	handler : function() {
		var mchtNo = "";
		var oprType = "0";
		var stlmDate = "";
		SendStlmFiles.sendStlmFileMsg(mchtNo, oprType,stlmDate,
				function(ret) {
					if ("S" == ret) {
						leftGridStore.reload();
						 Ext.TaskMgr.start(task);
						showSuccessMsg("处理操作成功！", leftPanel);
					} else {
						leftGridStore.reload();
						 Ext.TaskMgr.stop(task);
						  Ext.TaskMgr.stop(task2);
						showErrorMsg(ret, leftPanel);
					}
				})
	}
}
var btn3 = {
	text : '查询',
	iconCls : 'query',
	handler : function() {
		leftGridStore.load();
	}
}
tbarArr.push(btn1);
tbarArr.push("-");
tbarArr.push(btn2);
tbarArr.push("-");
tbarArr.push(btn3);
var queryConditionTbar1 = new Ext.Toolbar({
			renderTo : Ext.grid.EditorGridPanel.tbar,
			items : ["-", "起始时间:", {
						xtype : 'datefield',
						id : 'searchStartDate',
						width : 110,
						format : 'Y-m-d',
						name : 'searchStartDate',
						fieldLabel : '起始时间'
					}, "-", "截止时间:", {
						xtype : 'datefield',
						id : 'searchStopDate',
						width : 110,
						format : 'Y-m-d',
						name : 'searchStopDate',
						fieldLabel : '截止时间'
					}, "-", "商户号：", {
						id : "mchtNoId",
						name : "mchtNoNm",
						xtype: 'basecomboselect',
						editable : true,
			        	store: dsfMchtNoStore,
			        	width:120,
			        	valueField : 'valueField',// 值
						displayField : 'displayField',// 显示文本
						hiddenName: 'searchMchtNo'
					}, "-", "文件发送状态", {
						xtype : 'combo',
						id : 'searchFileStat',
						width : 110,
						name : 'searchFileStat',
						fieldLabel : '文件发送状态',
						emptyText : "请选择",
						editable : true,
						triggerAction: 'all',
						typeAhead: true,
						selectOnFocus: true,
						forceSelection: true,
						valueField : 'value',// 值
						displayField : 'text',// 显示文本
						store : fileStatComboList
					}]
		});

var leftGridStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'gridPanelStoreAction.asp?storeId=stlmFileTransInf'
					}),
			reader : new Ext.data.JsonReader({
						root : "data",
						totalProperty : "totalCount"
					}, [{
								name : "stlmDate",
								mapping : "stlmDate"
							}, {
								name : "mchtNo",
								mapping : "mchtNo"
							}, {
								name : "fileName",
								mapping : "fileName"
							}, {
								name : "fileStat",
								mapping : "fileStat"
							}, {
								name : "failDesc",
								mapping : "failDesc"
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
			header : "清算日期",
			width : 100,
			dataIndex : "stlmDate",
			sortable : false
		}, {
			header : "商户号",
			width : 120,
			dataIndex : "mchtNo",
			sortable : false
		}, {
			header : "文件名",
			width : 210,
			dataIndex : "fileName",
			sortable : false
		}, {
			header : "文件发送状态",
			width : 100,
			dataIndex : "fileStat",
			sortable : false,
			renderer:changeFileStat
		}, {
			header : "错误描述",
			width : 100,
			dataIndex : "failDesc",
			sortable : false
		}, {
			header : "最近更新柜员",
			width : 100,
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
					msg : '正在加载代收付对账文件传输信息列表......'
				},
			bbar:new Ext.PagingToolbar({
					store:leftGridStore,
					pageSize:System[QUERY_RECORD_COUNT],
					displayInfo : true,
					displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
					emptyMsg : '没有找到符合条件的记录'
			})
		});
leftGridStore.on("beforeload",function(){
	Ext.apply(this.baseParams,{
		start:0,
		startDate:typeof(Ext.getCmp('searchStartDate').getValue()) == 'string' ? '' : Ext.getCmp('searchStartDate').getValue().format('Ymd'),
		stopDate:typeof(Ext.getCmp('searchStopDate').getValue()) == 'string' ? '' : Ext.getCmp('searchStopDate').getValue().format('Ymd'),
		fileStat:Ext.getCmp('searchFileStat').getValue(),
		mchtNo:Ext.get('searchMchtNo').getValue()
	})
});
var leftPanel = new Ext.Panel({
		region: 'center',
		title:"代收付对账文件传输信息维护",
		frame: true,
		layout: 'border',
		tbar:[tbarArr],
		listeners:{
			'render' : function() {
				queryConditionTbar1.render(this.tbar);
			}
		},
		items:[leftGrid]
});
leftGrid.getSelectionModel().on({
		'rowselect': function() {
		//行高亮
		Ext.get(leftGrid.getView().getRow(leftGrid.getSelectionModel().last)).frame();
		var filestat = leftGrid.getSelectionModel().getSelected().data.fileStat;
		if(filestat =='2'){
			leftPanel.getTopToolbar().items.items[0].enable();
		}else{
			leftPanel.getTopToolbar().items.items[0].disable();
		}
		}
	});
var mainUI = new Ext.Viewport({
	layout : "border",
	items : [leftPanel]
});