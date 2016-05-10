Ext.onReady(function() {
	
	// 风险交易数据集
	var riskTxnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskTxnInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'saTxnCard',mapping: 'saTxnCard'},
			{name: 'saMchtNo',mapping: 'saMchtNo'},
			{name: 'mchtName',mapping: 'mchtName'},
			{name: 'saTermNo',mapping: 'saTermNo'},
			{name: 'saTxnNum',mapping: 'saTxnNum'},
			{name: 'saTxnAmt',mapping: 'saTxnAmt'},
			{name: 'saTxnDate',mapping: 'saTxnDate'},
			{name: 'saTxnTime',mapping: 'saTxnTime'},
			{name: 'saClcAction',mapping: 'saClcAction'},
			{name: 'rtMsg',mapping: 'rtMsg'}
		])
	}); 
	
	var riskColModel = new Ext.grid.ColumnModel([
		{header: '交易卡号',dataIndex: 'saTxnCard',width: 150,id:'saTxnCard'},
		{header: '交易商户号',dataIndex: 'saMchtNo',width: 150},
		{header: '交易商户名',dataIndex: 'mchtName',width: 250},
		{header: '交易终端号',dataIndex: 'saTermNo',width: 100},
		{header: '交易码',dataIndex: 'saTxnNum'},
		{header: '交易金额（元）',dataIndex: 'saTxnAmt',width: 150},
		{header: '交易日期',dataIndex: 'saTxnDate',width: 100,renderer: formatDt},
		{header: '交易时间',dataIndex: 'saTxnTime',width: 100,renderer: formatDt},
		{header: '受控动作',dataIndex: 'saClcAction',width: 100,renderer: clcAction},
		{header: '触发的模型',dataIndex: 'rtMsg',width: 300,renderer: msg}
	]);
	
	// 转译风险模型
	function msg(val) {
		if(val == 'C1') {
			return '3日内，同一卡号在同一商户内交易限制';
		} else if(val == 'C2') {
			return '3日内，同一卡号在同一受理行内交易限制';
		} else if(val== 'C3') {
			return '3日内，同一卡号交易限制';
		} else if(val == 'M1') {
			return '同一商户当日某笔授权回应为"查询发卡方"后，继续进行同金额同卡号交易';
		} else if(val == 'M2') {
			return '同一商户当日内发生的授权回应在受控范围内';
		} else if(val == 'M3') {
			return '同一商户当日同一卡号交易限制';
		} else if(val == 'M4') {
			return '同一商户当日交易金额限制';
		} else if(val == 'M5') {
			return '同一商户当日有超过一笔同金额的限制';
		} else if(val == 'R1') {
			return '同卡在几家商户交易间隔短于正常时间';
		} else {
			return '未知的模型描述';
		}
	}
	
	var menuArr = new Array();
	
	var monitorMenu = new Ext.Button({
		iconCls: 'play',
		text: '启动监控模式',
		enableToggle: true,
		toggleHandler: function(bt,state) {
			// 监控模式
			if(state) {
				this.setText('停止监控模式');
				this.setIconClass('monitor');
				//queryWin.hide();
				//grid.getTopToolbar().items.first().disable();
				Ext.TaskMgr.start(task);
			} else { // 手工查询模式
				this.setText('启动监控模式');
				this.setIconClass('play');
				//grid.getTopToolbar().items.first().enable();
				Ext.TaskMgr.stop(task);
			}
		}
	});
	
	var queryConditionMebu = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	
	menuArr.push(queryConditionMebu);  
	menuArr.push(''); 
	menuArr.push(monitorMenu); 
	
	// 监控模式定时器
	var task = {
		run: function() {
			riskTxnStore.load({
				params: {start: 0}
			})
		},
		interval: 10000
	}
	
	// 风险交易监控
	var grid = new Ext.grid.GridPanel({
		title: '风险交易监控',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
//		autoExpandColumn:'saTxnCard',
		clicksToEdit: true,
		store: riskTxnStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: riskColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载风险交易列表......'
		},
		tbar: 	menuArr,
		bbar: new Ext.PagingToolbar({
			store: riskTxnStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	riskTxnStore.load();
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		}
	});
	
	// 查询条件
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			id: 'cardNo',
			name: 'cardNo',
			vtype: 'alphanum',
			width: 200,
			fieldLabel: '交易卡号'
		},{
			xtype: 'textfield',
			id: 'mchntId',
			name: 'mchntId',
			vtype: 'alphanum',
			width: 200,
			fieldLabel: '交易商户号'
		},{
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			width: 200,
			fieldLabel: '交易开始日期'
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			width: 200,
			fieldLabel: '交易结束日期'
		}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 360,
		autoHeight: true,
		items: [queryForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		closable: true,
		animateTarget: 'query',
		tools: [{
			id: 'minimize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.maximize.show();
				toolEl.hide();
				queryWin.collapse();
				queryWin.getEl().pause(1);
				queryWin.setPosition(10,Ext.getBody().getViewSize().height - 30);
			},
			qtip: '最小化',
			hidden: false
		},{
			id: 'maximize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.minimize.show();
				toolEl.hide();
				queryWin.expand();
				queryWin.center();
			},
			qtip: '恢复',
			hidden: true
		}],
		buttons: [{
			text: '查询',
			handler: function() {
				riskTxnStore.load({
					params: {
						start: 0,
						saTxnCard: queryForm.findById('cardNo').getValue(),
						saMchtNo: queryForm.findById('mchntId').getValue(),
						startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
						endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd')
					}
				});
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
})