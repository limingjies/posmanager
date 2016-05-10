Ext.onReady(function() {
	
	var txnGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=txnTodayInfo_wap'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'instDate',mapping: 'instDate'},
			{name: 'instDateTime',mapping: 'instDateTime'},
			{name: 'txnName',mapping: 'txnName'},
			{name: 'transState',mapping: 'transState'},
			{name: 'respCode',mapping: 'respCode'},
			{name: 'settleBrh',mapping: 'settleBrh'},
			{name: 'amtTrans',mapping: 'amtTrans'},
			{name: 'pan',mapping: 'pan'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'cardAccpId',mapping: 'cardAccpId'},
			{name: 'mchtName',mapping: 'mchtName'},
			{name: 'cardAccpTermId',mapping: 'cardAccpTermId'},
			{name: 'termSsn',mapping: 'termSsn'},
			{name: 'sysSeqNum',mapping: 'sysSeqNum'}
		]),
		autoLoad: true
	});
	
	/*txnGridStore.load({
		params:{start: 0}
	});
	*/
	
	
	
	var txnColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: 'TIME',dataIndex: 'instDateTime',align: 'center',renderer: formatDt,width:70},
		{header: 'TYPE',dataIndex: 'txnName',align: 'center',width: 60},
		{header: 'ST',dataIndex: 'transState',align: 'center',width: 60},
		{header: 'RP',dataIndex: 'respCode',align: 'center',width: 30},
		{header: 'DEST',dataIndex: 'settleBrh',align: 'center',width: 50},
		{header: 'AMT',dataIndex: 'amtTrans',align: 'right',width: 80},
		{header: 'PAN',dataIndex: 'pan',align: 'center',width: 120},
		{header: 'MCBH',dataIndex: 'brhId',align: 'left',width: 120},									
//		{id:'cardAccpId',header: '商户号',dataIndex: 'cardAccpId',align: 'center',width: 120},
		{header: 'MCNM',dataIndex: 'mchtName',width: 180,align: 'left'},
		{header: 'TERMID',dataIndex: 'cardAccpTermId',width: 65,align: 'left'},
		{header: 'TERMSN',dataIndex: 'termSsn',width: 60,align: 'left'},
		{header: 'SYSSN',dataIndex: 'sysSeqNum',width: 50,align: 'left'}
	]);
	
	 var tbar1 = new Ext.Toolbar({
            renderTo: Ext.grid.EditorGridPanel.tbar,  
            items:['-','交易通道：',{
					xtype: 'basecomboselect',
					id: 'querySettleBrhId',
					baseParams: 'SETTLE_BRH',
					hiddenName: 'querySettleBrh',
					width: 120
				},'-','交易结果：',{
					xtype: 'basecomboselect',
					id: 'idqueryTransState',
					baseParams: 'TXN_STATUS',
					hiddenName: 'queryTransState',
					width: 120
				},'-','交易应答：',{
					xtype: 'textfield',
					id: 'queryRespCode',
					name: 'queryRespCode',
					maxLength: 2,
					minLength: 2,
					width: 120
				}
			]
     });
          
	var txnGrid = new Ext.grid.EditorGridPanel({
		iconCls: 'T104',
		region:'center',
//		autoExpandColumn:'txnName',
//		gridautoScroll:true,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: txnGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: txnColModel,
		clicksToEdit: true,
		forceValidation: true,
//        renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载交易信息列表......'
		},
		tbar: ['-',{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				txnGridStore.load();
			    }
			},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('idqueryTransState').setValue('');
				Ext.getCmp('queryRespCode').setValue('');
				Ext.getCmp('querySettleBrhId').setValue('');
			}	
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {
				tbar1.render(this.tbar);
            },
            'cellclick':selectableCell
        },
		bbar: new Ext.PagingToolbar({
			store: txnGridStore,
			pageSize: System[QUERY_SELECT_COUNT],
			displayInfo: true,
			displayMsg: '共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	txnGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(txnGrid.getView().getRow(txnGrid.getSelectionModel().last)).frame();
		}
	});
	

	
	txnGridStore.on('beforeload', function(){
		
		Ext.apply(this.baseParams, {
			start: 0,			
			queryTransState: Ext.get('queryTransState').getValue(),
			queryRespCode: Ext.getCmp('queryRespCode').getValue(),
			querySettleBrh: Ext.get('querySettleBrh').getValue()
		});
		
		
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [txnGrid],
		renderTo: Ext.getBody()
	});
});