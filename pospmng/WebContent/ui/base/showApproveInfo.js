function showApproveInfo(brhId){
	var reasonStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=brhApproveProcess'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'txnTime',mapping: 'txnTime'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'brhName',mapping: 'brhName'},
			{name: 'brhLevel',mapping: 'brhLevel'},
			{name: 'upBrhId',mapping: 'upBrhId'},
			{name: 'oprId',mapping: 'oprId'},
			{name: 'oprType',mapping: 'oprType'},
			{name: 'oprInfo',mapping: 'oprInfo'}
		])
	});
	
	reasonStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryBrhId: brhId
		});
	});

	reasonStore.load({
		params: {
			start: 0,
			queryBrhId: brhId
		}
	});
	
	var reasonColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '操作时间',dataIndex: 'txnTime',sortable: true,width: 150,renderer: formatTs},
		{header: '操作结果',dataIndex: 'oprType',width: 150},
		{header: '批注',dataIndex: 'oprInfo',width: 200},
		{header: '合作伙伴编号',dataIndex: 'brhId',width: 150,id:'brhId'},
		{header: '合作伙伴名称',dataIndex: 'brhName',width: 200},
		{header: '级别',dataIndex: 'brhLevel',width: 100,hidden:true,renderer : brhLvlRender},
		{header: '上级合作伙伴',dataIndex: 'upBrhId',hidden:true,width: 100},
		{header: '交易操作员',dataIndex: 'oprId'}
	]);
	

	var grid = new Ext.grid.GridPanel({
		region: 'center',
		height:410,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: reasonStore,
		//autoExpandColumn: 'oprInfo',
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: reasonColModel,
		loadMask: {
			msg: '正在加载合作伙伴审批记录信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: reasonStore,
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
	
	// window
	var popupWin = new Ext.Window({
		initHidden: true,
		header: true,
		frame: true,
		modal: true,
		width: 850,
		height:480,
		autoScroll: true,
		title: '合作伙伴审批流程查询',
		iconCls: 'T20103',
		items: [grid],
		buttonAlign: 'center',
		closeAction: 'close',
		closable: true,
		resizable: false,
		buttons : [{
			text : '关闭',
			id: 'btnOk',
				disable: true,
				handler : function() {
					popupWin.hide();
				}
			}]
	});
	
	popupWin.show();
}