function showApproveInfo(mchntId){
	var reasonStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntRefuseInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'txnTime',mapping: 'txnTime'},
			{name: 'mchntId',mapping: 'mchntId'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'oprId',mapping: 'oprId'},
			{name: 'refuseType',mapping: 'refuseType'},
			{name: 'refuseInfo',mapping: 'refuseInfo'}
		])
	});
	
	reasonStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryMchtNo: mchntId
		});
	});
	
	reasonStore.load({
		params: {
			start: 0,
			queryMchtNo: mchntId
		}
	});
	
	var reasonColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '操作时间',dataIndex: 'txnTime',sortable: true,width: 150,renderer: formatTs},
		{header: '商户编号',dataIndex: 'mchntId',width: 150},
		{header: '商户名称',dataIndex: 'mchtNm',width: 200},
		{header: '商户所属机构',dataIndex: 'brhId',width: 100},
		{header: '交易操作员',dataIndex: 'oprId'},
		{header: '操作结果',dataIndex: 'refuseType',width: 150},
		{header: '批注',dataIndex: 'refuseInfo',width: 200,id:'refuseInfo'}
	]);
	

	var grid = new Ext.grid.GridPanel({
		region: 'center',
		height:410,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: reasonStore,
		//autoExpandColumn: 'refuseInfo',
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: reasonColModel,
		loadMask: {
			msg: '正在加载商户退回（拒绝）信息列表......'
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
		title: '商户审批流程查询',
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