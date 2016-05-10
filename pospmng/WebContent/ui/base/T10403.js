Ext.onReady(function() {
	
	// 可选机构数据集
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	//	idProperty: 'valueField'
	});
	

	
	SelectOptionsDWR.getComboData('BRH_ID',function(ret){
	brhStore.loadData(Ext.decode(ret));
});
	
	var txnGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=txnInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		//	idProperty: 'oprId'
		},[
			{name: 'oprId',mapping: 'oprId'},
			{name: 'txnDate',mapping: 'txnDate'},
			{name: 'txnTime',mapping: 'txnTime'},
			{name: 'txnName',mapping: 'txnName'},
			{name: 'txnSta',mapping: 'txnSta'},
			{name: 'errMsg',mapping: 'errMsg'},
			{name: 'ipAddr',mapping: 'ipAddr'}
		])
	});
	
	txnGridStore.load({
		params:{start: 0}
	});
	
	var txnRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'错误信息：<br/>',
			'<b>{errMsg}</b>'
		)
	});
	
	var txnColModel = new Ext.grid.ColumnModel([
		txnRowExpander,
		{id: 'oprId',header: '操作员编号',dataIndex: 'oprId',align: 'center',width: 100},
		{header: '交易日期',dataIndex: 'txnDate',align: 'center',width: 120,renderer: formatDt},
		{header: '交易时间',dataIndex: 'txnTime',align: 'center',renderer: formatTs,width: 120,renderer: formatDt},
		{header: 'IP地址',dataIndex: 'ipAddr',align: 'center',width: 200},
		{header: '交易名称',dataIndex: 'txnName',align: 'center',width: 300,id:'txnName'},
		{header: '交易状态',dataIndex: 'txnSta',align: 'center',renderer: txnSta,width: 100}
	]);
	
	/**
	 * 交易状态转译
	 */
	function txnSta(val) {
		if(val == '0') {
			return '<font color="green">成功</font>';
		} else if(val == '1') {
			return '<font color="red">失败</font>';
		}
		return val;
	}
	
// 菜单集合
	var menuArr = new Array();
		var queryCondition = {
			text: '录入查询条件',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
			}
		};
		menuArr.push(queryCondition);  
	
	

	// 操作员信息列表
	var txnGrid = new Ext.grid.GridPanel({
		title: '交易日志查询',
		iconCls: 'T10403',
		region:'center',
		autoExpandColumn:'txnName',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: txnGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: txnColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		plugins: txnRowExpander,
		loadMask: {
			msg: '正在加载交易日志信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: txnGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		renderTo: Ext.getBody()
	});
	
	txnGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(txnGrid.getView().getRow(txnGrid.getSelectionModel().last)).frame();
		}
	});	
	
/***************************查询条件*************************/	
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		labelWidth: 80,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			width: 150,
			id: 'oprNo',
			name: 'oprNo',
			vtype: 'alphanum',
			fieldLabel: '操作员编号'
		},{
			xtype: 'datefield',
			width: 150,
			id: 'startDate',
			name: 'startDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			endDateField: 'endDate',
			fieldLabel: '开始日期',
			editable: false
		},{
			xtype: 'datefield',
			width: 150,
			id: 'endDate',
			name: 'endDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			startDateField: 'startDate',
			fieldLabel: '结束日期',
			editable: false
		},{
			xtype: 'basecomboselect',
			width: 150,
			baseParams: 'BRH_BELOW',
			fieldLabel: '所属机构',
			hiddenName: 'brhId'
		},{
			xtype: 'basecomboselect',
			baseParams: 'CON_TXN',
			width: 150,
			fieldLabel: '交易名称',
			hiddenName: 'conTxn'
		}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 300,
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
				txnGridStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	txnGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			oprNo: queryForm.findById('oprNo').getValue(),
			startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
			endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
			brhId: queryForm.getForm().findField('brhId').getValue(),
			conTxn: queryForm.getForm().findField('conTxn').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [txnGrid],
		renderTo: Ext.getBody()
	});
});