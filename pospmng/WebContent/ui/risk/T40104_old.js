Ext.onReady(function() {
	
	// 风险交易数据集
	var riskTxnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=historyRiskRecords'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'saTxnCard',mapping: 'saTxnCard'},
			{name: 'saMchtNo',mapping: 'saMchtNo'},
			{name: 'saClcFlag',mapping: 'saClcFlag'},
			{name: 'saClcRsn1',mapping: 'saClcRsn1'},
			{name: 'saTermNo',mapping: 'saTermNo'},
			{name: 'saTxnNum',mapping: 'saTxnNum'},
			{name: 'saTxnAmt',mapping: 'saTxnAmt'},
			{name: 'saTxnDate',mapping: 'saTxnDate'},
			{name: 'saTxnTime',mapping: 'saTxnTime'},
			{name: 'saNoteTxt',mapping: 'saNoteTxt'}
		]),
		autoLoad: true
	}); 
	
	var riskColModel = new Ext.grid.ColumnModel([
	    {header: '交易凭证号',dataIndex: 'saNoteTxt',width: 150},
		{header: '交易卡号',dataIndex: 'saTxnCard',width: 150},
		{header: '交易商户',dataIndex: 'saMchtNo',width: 300},
		{header: '受控动作',dataIndex: 'saClcFlag',width: 70,renderer: saAction},
		{header: '触发规则',dataIndex: 'saClcRsn1',width: 250,renderer: saModelKind},
		{header: '交易终端号',dataIndex: 'saTermNo',width: 100},
		{header: '交易码',dataIndex: 'saTxnNum'},
		{header: '交易金额（元）',dataIndex: 'saTxnAmt',width: 150,renderer:formatAmt},
		{header: '交易日期',dataIndex: 'saTxnDate',width: 150,renderer: formatTs},
		{header: '交易时间',dataIndex: 'saTxnTime',width: 150,renderer: formatTs}
	]);
	
	// 转译风险模型
	function saModelKind(val) {
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
		} else if(val == '黑名单卡金额受限') {
			return '黑名单卡金额受限';
		} else if(val == '黑名单商户金额受限') {
			return '黑名单商户金额受限';
		}
	}
	
	// 转译受控动作
	function saAction(val) {
		if(val == '0') {
			return '<font color="green">关注</font>';
		} else if(val == '1') {
			return '<font color="gray">托收</font>';
		} else if(val == '2') {
			return '<font color="red">拒绝</font>';
		} else {
			return '未知的受控动作';
		}
	}
	
	var menuArr = new Array();
	
	var queryConditionMebu = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	
	menuArr.push(queryConditionMebu);  //[0]
	
	// 历史风险记录
	var grid = new Ext.grid.GridPanel({
		title: '历史风险记录',
		iconCls: 'T40104',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		store: riskTxnStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: riskColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载历史风险记录列表......'
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
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		}
	});
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 400,
		autoHeight: true,
		items: [{
			xtype: 'dynamicCombo',
			methodName: 'getMchntId',
			fieldLabel: '商户编号',
			hiddenName: 'HmchtNo',
			id: 'mchtNo',
			width: 240,
			editable: true
		},{
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			fieldLabel: '交易开始日期'
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			fieldLabel: '交易结束日期'
		}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 400,
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
				riskTxnStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	riskTxnStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			mchntNo: queryForm.findById('mchtNo').getValue(),
			startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
			endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd')
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});